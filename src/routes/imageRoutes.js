const { uploadImageHandler } = require('../handler/uploadImageHandler');

const imageroutes = [
  {
    method: 'POST',
    path: '/upload-image',
    handler: uploadImageHandler,
  },
];

module.exports = imageroutes;