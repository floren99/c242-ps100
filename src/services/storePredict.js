const { Firestore } = require('@google-cloud/firestore');
 
async function storePredict(userId, data) {
  const db = new Firestore();
 
  const predictCollection = db.collection('predictions');
  return predictCollection.doc(userId).set(data);
}
 
module.exports = storePredict