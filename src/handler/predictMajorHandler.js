const predictClassification = require('../services/inferenceService');
const crypto = require('crypto');
const storePredict = require('../services/storePredict');
const getAllPredict = require('../services/getAllPredict');
 
async function postPredictHandler(request, h) {
  const { answer } = request.payload;
  const { model } = request.server.app;
 
  const { label, percent } = await predictMajor(model, answer);
  const userId = crypto.randomUUID(); //Id bakal pindah jika pake register dan login
  const createdAt = new Date().toISOString();
 
  const data = {
    "id": userId,
    "major": label,
    "percent": percent,
    "createdAt": createdAt
  }
 
  await storePredict(userId, data);

  const response = h.response({
    status: 'success',
    message: 'Model is predicted successfully',
    data
  })
  response.code(201);
  return response;
}
 
async function postPredictHistoriesHandler(request, h) {
  const allData = await getAllPredict();
  
  const formatAllData = [];
  allData.forEach(doc => {
      const data = doc.data();
      formatAllData.push({
          userId: doc.id,
          history: {
              result: data.result,
              createdAt: data.createdAt,
              suggestion: data.suggestion,
              userId: doc.id
          }
      });
  });
  
  const response = h.response({
    status: 'success',
    data: formatAllData
  })
  response.code(200);
  return response;
}

module.exports = { postPredictHandler, postPredictHistoriesHandler };