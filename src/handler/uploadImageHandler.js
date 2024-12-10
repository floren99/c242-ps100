const { Storage } = require('@google-cloud/storage');  // Import Google Cloud Storage client
const path = require('path');
const admin = require("../services/firebaseAdmin");
const db = admin.firestore(); // Import your Firebase Firestore instance

// Initialize Google Cloud Storage client with Service Account credentials
const storage = new Storage({
  keyFilename: '../../cloudStorageKey.json',  // Path to your service account key JSON file
});

const bucket = storage.bucket('profile_pict');  // Replace with your bucket name

// Supported MIME types for image files
const supportedMimeTypes = ['image/png', 'image/jpeg', 'image/jpg'];

// Handler to upload image to Google Cloud Storage and link to Firebase user
const uploadImageHandler = async (request, h) => {
  try {
    const userId = request.payload.userId; // Assuming userId is passed in the request payload
    if (!userId) {
      return h.response({ error: 'User ID is required' }).code(400);
    }

    // Check if file is present in the request payload
    if (!request.payload.file) {
      return h.response({ error: 'No file uploaded' }).code(400);
    }

    const file = request.payload.file;

    // Validate the MIME type of the file
    if (!supportedMimeTypes.includes(file.hapi.headers['content-type'])) {
      return h.response({ error: 'Invalid file type. Only PNG, JPG, and JPEG are allowed.' }).code(400);
    }

    // Optionally, check file size (limit to 5MB)
    const maxFileSize = 5 * 1024 * 1024; // 5MB
    if (file._data.length > maxFileSize) {
      return h.response({ error: 'File is too large. Maximum size is 5MB.' }).code(400);
    }

    const fileName = Date.now() + path.extname(file.hapi.filename);  // Ensure unique file name
    const fileUpload = bucket.file(fileName);

    // Create a stream to upload the file to Cloud Storage
    const stream = fileUpload.createWriteStream({
      resumable: false,
      contentType: file.hapi.headers['content-type'],
    });

    // Pipe the file buffer to the stream to upload it to Cloud Storage
    stream.on('error', (err) => {
      console.error('Error uploading file to Google Cloud Storage', err);
      return h.response({ error: 'Error uploading file' }).code(500);
    });

    stream.on('finish', async () => {
      try {
        // Optionally make the file public
        await fileUpload.makePublic();

        // Generate a public URL for the uploaded file
        const publicUrl = `https://storage.googleapis.com/${bucket.name}/${fileUpload.name}`;

        // Update Firestore with the image URL for the specific user
        const userRef = db.collection('users').doc(userId);  // Assuming your Firestore collection is 'users'
        await userRef.update({
          profileImageUrl: publicUrl,  // Store the image URL in the user's document
        });

        return h.response({
          message: 'File uploaded successfully',
          fileUrl: publicUrl,
        }).code(200);

      } catch (err) {
        console.error('Error updating Firestore with image URL', err);
        return h.response({ error: 'Error saving image URL to Firestore' }).code(500);
      }
    });

    // Pipe the file buffer to the stream
    stream.end(file._data);

  } catch (error) {
    console.error('Error uploading image', error);
    return h.response({ error: 'Error uploading image' }).code(500);
  }
};

module.exports = { uploadImageHandler };
