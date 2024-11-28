const admin = require("../services/firebaseAdmin");
const { Firestore } = require('@google-cloud/firestore');
const db = admin.firestore(); // Initialize Firestore
// Register User


const registerUser = async (request, h) => {
  try {
    const { email, password, username } = request.payload;

    // Create user using Firebase Admin SDK
    const userRecord = await admin.auth().createUser({
      email,
      password,
      displayName: username, // Add displayName for the username
    });

    // Save the user information in Firestore
    const userRef = db.collection("users").doc(userRecord.uid); // Use UID as the document ID
    await userRef.set({
      username: userRecord.displayName,
      userId: userRecord.uid,
    });

    return h
      .response({
        message: "Registration successful.",
        userId: userRecord.uid,
        username: userRecord.displayName,
      })
      .code(201);
  } catch (error) {
    console.error("Registration Error:", error.message);
    return h.response({ error: error.message }).code(400);
  }
};

// Login User
const loginUser = async (request, h) => {
  try {
    const { idToken } = request.payload; // ID Token from frontend login

    // Verify ID Token using Admin SDK
    const decodedToken = await admin.auth().verifyIdToken(idToken);

    return h
      .response({
        message: "Login successful.",
        userId: decodedToken.uid,
        username: userRecord.displayName, // Return the username
      })
      .code(200);
  } catch (error) {
    console.error("Login Error:", error.message);
    return h.response({ error: error.message }).code(400);
  }
};

module.exports = { registerUser, loginUser };
