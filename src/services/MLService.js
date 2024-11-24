const axios = require('axios');

async function MLService(data) {
  const flaskBaseUrl = 'http://localhost:5000'; // Replace with your Flask ML API's base URL

  try {
    // Make a POST request to the Flask API's prediction endpoint
    const response = await axios.post(`${flaskBaseUrl}/predict`, data, {
      headers: {
        'Content-Type': 'application/json',
      },
    });

    // Return the Flask API response
    return response.data;
  } catch (error) {
    console.error('Error calling Flask API:', error.response?.data || error.message);
    throw new Error('Failed to connect to Flask API');
  }
}

module.exports = { MLService };
