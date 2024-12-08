const admin = require("../services/firebaseAdmin");
const axios = require("axios");
const db = admin.firestore(); 

const registerUser = async (request, h) => {
  try { 
    const { email, password, username }= request.payload;

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
      email: userRecord.email,
      userId: userRecord.uid,
      createdAt: new Date().toISOString(),
    });

    return h
      .response({
        error: false,
        message: "Registration successful.",
      })
      .code(201);
  } catch (error) {
    console.error("Registration Error:", error.message);
    // Handle Firebase specific errors
    let errorMessage = "An error occurred during registration.";
    if (error.code === "auth/email-already-exists") {
      errorMessage = "The email address is already in use.";
    } else if (error.code === "auth/invalid-password") {
      errorMessage = "The password is invalid or too weak.";
    }
    return h.response({ error: true, message: errorMessage }).code(400);
  }
};

// Login User
const loginUser = async (request, h) => {
  try {
    const { email, password } = request.payload; // Make sure email and password are in the payload

    const response = await axios.post(
      `https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyA4r5XOKBTcvaywm021xXViGBij_w18yso`,
      {
        email,
        password,
        returnSecureToken: true,
      }
    );

    const { idToken, localId, displayName } = response.data; // Extract idToken and other details

    return h
      .response({
        error: false,
        message: "Login successful.",
        loginResult: {
          userId: localId,
          username: displayName,
          token: idToken,
        },
      })
      .code(200);
  } catch (error) {
    console.error("Login Error:", error.response?.data || error.message);

    // Log the error details for debugging
    const errorMessage =
      error.response?.data?.error?.message || "An error occurred during login.";

    return h
      .response({
        error: true,
        message: errorMessage,
      })
      .code(400);
  }
};

module.exports = { registerUser, loginUser };
