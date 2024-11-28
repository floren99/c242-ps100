const { Storage } = require('@google-cloud/storage');

// Initialize Google Cloud Storage
const storage = new Storage({
  keyFilename: "../../serviceAccountKey.json",
});
const bucketName = 'profil_pict'; // Replace with your bucket name
const bucket = storage.bucket(bucketName);

/**
 * Uploads an image to Google Cloud Storage.
 *
 * @param {Object} file - The file object from the request payload.
 * @param {string} userId - The user's ID (used to generate a unique filename).
 * @returns {Promise<string>} - The public URL of the uploaded image.
 */
async function uploadImage(file, userId) {
  return new Promise((resolve, reject) => {
    // Generate a unique filename
    const fileName = `profile_pictures/${userId}-${Date.now()}.jpg`;

    // Create a write stream for uploading the file
    const fileUpload = bucket.file(fileName);
    const stream = fileUpload.createWriteStream({
      metadata: { contentType: file.hapi.headers['content-type'] },
    });

    stream.on('error', (err) => {
      console.error('Error uploading file:', err);
      reject(new Error('Failed to upload image'));
    });

    stream.on('finish', async () => {
      try {
        // Make the file public
        await fileUpload.makePublic();

        // Return the public URL of the uploaded image
        const publicUrl = `https://storage.googleapis.com/${bucketName}/${fileName}`;
        resolve(publicUrl);
      } catch (err) {
        console.error('Error making file public:', err);
        reject(new Error('Failed to make image public'));
      }
    });

    // Write the file data
    stream.end(file._data);
  });
}

module.exports = uploadImage;
