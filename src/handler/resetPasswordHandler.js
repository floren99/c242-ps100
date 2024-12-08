const axios = require("axios"); 
const admin = require("../services/firebaseAdmin"); 

const resetPassword = async (request, h) => {
  try {
    const { email } = request.payload; 

    if (!email) {
      return h.response({
        error: true,
        message: "Email is required."
      }).code(400);
    }

    // Check if the email exists in Firebase Authentication
    try {
      // Use the Firebase Admin SDK to check if the email exists
      const userRecord = await admin.auth().getUserByEmail(email);

      // If  successfully get the user record, the email exists
      console.log('User found:', userRecord.toJSON());

      const response = await axios.post(
        `https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=AIzaSyA4r5XOKBTcvaywm021xXViGBij_w18yso`, // Replace with your Firebase Web API Key
        {
          requestType: "PASSWORD_RESET",
          email,
        }
      );

      // Extract the response (e.g., success confirmation)
      const { emailVerified } = response.data;

      return h
        .response({
          error: false,
          message: "Password reset email sent successfully.",
          emailVerified, // Include whether the email is verified
        })
        .code(200);

    } catch (error) {
      console.error("User not found:", error.message);
      
      return h.response({
        error: true,
        message: "This email is not registered with our service.",
      }).code(400);
    }

  } catch (error) {
    console.error(
      "Password Reset Error:",
      error.response?.data || error.message
    );

    const errorMessage =
      error.response?.data?.error?.message ||
      "An error occurred during password reset.";

    return h
      .response({
        error: true,
        message: errorMessage,
      })
      .code(400);
  }
};


module.exports = { resetPassword };
