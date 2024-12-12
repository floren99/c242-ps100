import numpy as np
import pickle
import tensorflow as tf
from flask import Flask, request, jsonify

# Initialize Flask app
app = Flask(__name__)

# Load the trained model, scaler, and label encoder
model = tf.keras.models.load_model('model.keras')  # Load pre-trained model

with open('scaler.pkl', 'rb') as f:
    scaler = pickle.load(f)

with open('label_encoder.pkl', 'rb') as f:
    label_encoder = pickle.load(f)

# Route for prediction
@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Check if the 'input' field is in the request data
        data = request.get_json()

        if 'input' not in data:
            return jsonify({'error': 'No input data provided'}), 400

        # Input data should be a list (e.g., [1.0, 2.0, 3.0])
        input_data = np.array(data['input'])
        if len(input_data.shape) != 1:
            return jsonify({'error': 'Input data should be a 1D list of numerical values'}), 400

        # Reshape the input data for LSTM (1, 1, n_features)
        input_data = input_data.reshape(1, 1, len(input_data))

        # Scale the input data using the saved scaler
        input_data_scaled = scaler.transform(input_data.reshape(-1, input_data.shape[-1])).reshape(input_data.shape)

        # Make the prediction using the model
        prediction = model.predict(input_data_scaled)

        # Decode the predicted label from one-hot encoding
        predicted_label = np.argmax(prediction, axis=1)
        predicted_label = label_encoder.inverse_transform(predicted_label)

        # Return the prediction as JSON response
        return jsonify({'predicted_label': predicted_label[0]})

    except Exception as e:
        # Catch all unexpected errors and return them as a response
        return jsonify({'error': f'An error occurred: {str(e)}'}), 500

if __name__ == '__main__':
    # Set debug=False for production
    app.run(debug=False, host='0.0.0.0', port=5000)
