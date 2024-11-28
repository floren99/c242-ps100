const admin = require('../services/firebaseAdmin');
const uploadImage = require('../services/uploadImage');

// Update user profile handler
async function updateProfileHandler(request, h) {
  try {
    // Extract Firebase ID token from headers
    const idToken = request.headers.authorization?.split(' ')[1];
    if (!idToken) {
      return h.response({ status: 'fail', message: 'Missing or invalid token' }).code(401);
    }

    // Verify Firebase ID token
    const decodedToken = await admin.auth().verifyIdToken(idToken);
    const userId = decodedToken.uid;

    // Extract username and file from the request
    const { username } = request.payload; // New username
    const file = request.payload.file; // Uploaded image

    let profilePictureUrl;

    // If a file is uploaded, process it
    if (file) {
      profilePictureUrl = await uploadImage(file, userId);
    }

    // Update user profile in Firestore
    const userRef = admin.firestore().collection('users').doc(userId);
    const updatedData = { username };

    // Add the profile picture URL if uploaded
    if (profilePictureUrl) {
      updatedData.profilePicture = profilePictureUrl;
    }

    await userRef.set(updatedData, { merge: true });

    return h.response({
      status: 'success',
      message: 'Profile updated successfully',
      data: updatedData,
    }).code(200);
  } catch (error) {
    console.error('Error in profile update:', error.message);
    return h.response({ status: 'error', message: error.message }).code(500);
  }
}

module.exports = { updateProfileHandler };
