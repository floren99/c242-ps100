const axios = require('axios');

async function MLService(data) {
  // Replace with your actual Cloud Run URL
  const flaskBaseUrl = 'https://ml-api-capstone-xyz123.a.run.app';  // Cloud Run URL

  try {
    // Prepare the payload as per the Flask API's input format
    const payload = {
      input: data,
    };

    // Make a POST request to the Flask API's prediction endpoint
    const response = await axios.post(`${flaskBaseUrl}/predict`, payload, {
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
