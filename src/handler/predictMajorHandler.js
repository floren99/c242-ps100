const predictMajor = require("../services/inferenceService");
const storePredict = require("../services/storePredict");
const getAllPredict = require("../services/getAllPredict");
const admin = require("firebase-admin");

async function postPredictHandler(request, h) {
  try {
    const { answer } = request.payload;
    const { model } = request.server.app;

    // Get Firebase ID token from headers
    const idToken = request.headers.authorization?.split(" ")[1];
    if (!idToken) {
      return h
        .response({ status: "fail", message: "Missing or invalid token" })
        .code(401);
    }

    // Verify Firebase ID token
    const decodedToken = await admin.auth().verifyIdToken(idToken);
    const userId = decodedToken.uid;

    const { label, percent } = await predictMajor(model, answer);
    const createdAt = new Date().toISOString();

    const data = {
      id: userId,
      major: label,
      percent,
      createdAt,
    };

    // Save prediction to Firestore
    await storePredict(userId, data);

    const response = h.response({
      status: "success",
      message: "Model is predicted successfully",
      data,
    });
    response.code(201);
    return response;
  } catch (error) {
    console.error("Error in prediction handler:", error.message);
    return h.response({ status: "error", message: error.message }).code(500);
  }
}

async function postPredictHistoriesHandler(request, h) {
  try {
    // Get Firebase ID token from headers
    const idToken = request.headers.authorization?.split(" ")[1];
    if (!idToken) {
      return h
        .response({ status: "fail", message: "Missing or invalid token" })
        .code(401);
    }

    // Verify Firebase ID token
    const decodedToken = await admin.auth().verifyIdToken(idToken);
    const userId = decodedToken.uid;

    // Retrieve all predictions for the authenticated user
    const allData = await getAllPredict(userId);

    const formatAllData = allData.map((doc) => {
      const data = doc.data();
      return {
        userId: doc.id,
        history: {
          result: data.result,
          createdAt: data.createdAt,
          suggestion: data.suggestion,
        },
      };
    });

    const response = h.response({
      status: "success",
      data: formatAllData,
    });
    response.code(200);
    return response;
  } catch (error) {
    console.error("Error in prediction histories handler:", error.message);
    return h.response({ status: "error", message: error.message }).code(500);
  }
}

module.exports = { postPredictHandler, postPredictHistoriesHandler };
