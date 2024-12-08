const { Firestore } = require("@google-cloud/firestore");
const db = new Firestore();

async function getAllQuestion(collectionName) {
  try {
    const collection = db.collection(collectionName);
    const snapshot = await collection.get();

    const dataList = snapshot.docs.map((doc) => ({
      id: doc.id, // Include document ID 
      ...doc.data(), // Spread the document data
    }));

    return dataList; // Return an array of document data
  } catch (error) {
    console.error(`Error fetching data from ${collectionName}:`, error);
    throw new Error(`Failed to fetch data from ${collectionName}`);
  }
}

module.exports = getAllQuestion;
