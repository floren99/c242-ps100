const firebase = require('../services/firebase');

// Register User
const registerUser = async (request, h) => {
  try {
    const { email, password } = request.payload;

    // Create user using Firebase Client SDK
    const userCredential = await firebase.auth().createUserWithEmailAndPassword(email, password);

    // Optional: Send email verification
    await userCredential.user.sendEmailVerification();

    return h.response({
      message: 'Registration successful. Please verify your email.',
      userId: userCredential.user.uid,
    }).code(201);
  } catch (error) {
    console.error('Registration Error:', error.message);
    return h.response({ error: error.message }).code(400);
  }
};

// Login User
const loginUser = async (request, h) => {
  try {
    const { email, password } = request.payload;

    // Sign in using Firebase Client SDK
    const userCredential = await firebase.auth().signInWithEmailAndPassword(email, password);

    // Get Firebase Auth token
    const idToken = await userCredential.user.getIdToken();

    return h.response({
      message: 'Login successful',
      token: idToken,
    }).code(200);
  } catch (error) {
    console.error('Login Error:', error.message);
    return h.response({ error: error.message }).code(400);
  }
};

module.exports = { registerUser, loginUser };