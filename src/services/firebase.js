// firebase.js

const firebase = require('firebase/app');
require('firebase/auth');

const firebaseConfig = {
  apiKey: 'YOUR_API_KEY',
  authDomain: 'YOUR_PROJECT_ID.firebaseapp.com',
  projectId: 'YOUR_PROJECT_ID',
};

// Initialize Firebase Client SDK
firebase.initializeApp(firebaseConfig);

module.exports = firebase;
