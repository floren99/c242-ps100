const { Firestore } = require('@google-cloud/firestore'); // Import Firestore SDK
const db = new Firestore();  // Initialize Firestore client

async function getAllPredict(userId) {
  try {
    const predictionsRef = db.collection('predictions'); // Reference to the 'predictions' collection

    // Fetch the document by userId (document ID)
    const docSnapshot = await predictionsRef.doc(userId).get();

    if (!docSnapshot.exists) {
      console.log('No prediction found for this user.');
      return null; // Return null if no document exists
    }

    // Return the data of the prediction document
    return docSnapshot.data(); // Return the prediction data
  } catch (error) {
    console.error('Error retrieving prediction:', error);
    throw new Error('Failed to retrieve prediction');
  }
}

module.exports = getAllPredict;
