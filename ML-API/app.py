import os
import numpy as np
import tensorflow as tf
import pickle
from flask import Flask, request, jsonify
from google.cloud import storage
from google.oauth2 import service_account

# Setup Flask app
app = Flask(__name__)

# Tentukan path ke Service Account JSON key file
SERVICE_ACCOUNT_JSON = "./serviceAccountKey.json"

# Set kredensial secara eksplisit untuk Google Cloud
credentials = service_account.Credentials.from_service_account_file(SERVICE_ACCOUNT_JSON)

# Setup client Google Cloud Storage menggunakan kredensial
storage_client = storage.Client(credentials=credentials, project=credentials.project_id)

# Fungsi untuk mengunduh file dari Google Cloud Storage
def download_file_from_gcs(bucket_name, file_path, local_path):
    bucket = storage_client.bucket(bucket_name)
    blob = bucket.blob(file_path)
    blob.download_to_filename(local_path)
    return local_path

# Variabel global untuk menyimpan model, scaler, dan label encoder
model = None
scaler = None
label_encoder = None

# Fungsi untuk memuat model dan file terkait hanya sekali
def load_model_and_files():
    global model, scaler, label_encoder

    if model is not None and scaler is not None and label_encoder is not None:
        # Jika model dan file lainnya sudah dimuat, tidak perlu mengunduh ulang
        return model, scaler, label_encoder

    # Tentukan path file di GCS dan lokal
    model_file_path = 'models/model.keras'
    scaler_file_path = 'models/scaler.pkl'
    label_encoder_file_path = 'models/label_encoder.pkl'

    # Tentukan path lokal untuk menyimpan file yang diunduh
    model_local_path = '/tmp/model.keras'
    scaler_local_path = '/tmp/scaler.pkl'
    label_encoder_local_path = '/tmp/label_encoder.pkl'

    # Download file dari GCS
    download_file_from_gcs('ml-models-rekomendasi-jurusan', model_file_path, model_local_path)
    download_file_from_gcs('ml-models-rekomendasi-jurusan', scaler_file_path, scaler_local_path)
    download_file_from_gcs('ml-models-rekomendasi-jurusan', label_encoder_file_path, label_encoder_local_path)

    # Muat model, scaler, dan label encoder
    model = tf.keras.models.load_model(model_local_path)

    with open(scaler_local_path, 'rb') as f:
        scaler = pickle.load(f)

    with open(label_encoder_local_path, 'rb') as f:
        label_encoder = pickle.load(f)

    return model, scaler, label_encoder

# Muat model dan file terkait saat aplikasi dimulai
model, scaler, label_encoder = load_model_and_files()

# Route untuk prediksi
@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Ambil data JSON dari permintaan
        data = request.get_json()

        if 'input' not in data:
            return jsonify({'error': 'No input data provided'}), 400

        # Input data seharusnya berupa daftar (misal: [1.0, 2.0, 3.0])
        input_data = np.array(data['input'])
        if len(input_data.shape) != 1:
            return jsonify({'error': 'Input data should be a 1D list of numerical values'}), 400

        # Reshape data input untuk LSTM (1, 1, n_features)
        input_data = input_data.reshape(1, 1, len(input_data))

        # Skalakan data input menggunakan scaler yang telah dimuat
        input_data_scaled = scaler.transform(input_data.reshape(-1, input_data.shape[-1])).reshape(input_data.shape)

        # Lakukan prediksi menggunakan model
        prediction = model.predict(input_data_scaled)

        # Dekode label yang diprediksi dari one-hot encoding
        predicted_label = np.argmax(prediction, axis=1)
        predicted_label = label_encoder.inverse_transform(predicted_label)

        # Kembalikan hasil prediksi dalam format JSON
        return jsonify({'predicted_label': predicted_label[0]})

    except Exception as e:
        # Tangkap kesalahan tak terduga dan kembalikan sebagai respons
        return jsonify({'error': f'An error occurred: {str(e)}'}), 500

if __name__ == '__main__':
    # Jalankan aplikasi Flask
    app.run(debug=False, host='0.0.0.0', port=8080)
