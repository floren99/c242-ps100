const admin = require('../services/firebaseAdmin');

// Register User
const registerUser = async (request, h) => {
  try {
    const { email, password } = request.payload;

    // Create user using Firebase Admin SDK
    const userRecord = await admin.auth().createUser({
      email,
      password,
    });

    return h.response({
      message: "Registration successful.",
      userId: userRecord.uid,
    }).code(201);
  } catch (error) {
    console.error("Registration Error:", error.message);
    return h.response({ error: error.message }).code(400);
  }
};

// Login User
const loginUser = async (request, h) => {
  try {
    const { idToken } = request.payload; // ID Token dari frontend login

    // Verify ID Token using Admin SDK
    const decodedToken = await admin.auth().verifyIdToken(idToken);

    return h.response({
      message: "Login successful.",
      userId: decodedToken.uid,
    }).code(200);
  } catch (error) {
    console.error("Login Error:", error.message);
    return h.response({ error: error.message }).code(400);
  }
};

module.exports = { registerUser, loginUser };