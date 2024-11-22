const {
  saveRegisterInfoHandler,
  saveUserProfilePictureHandler,
  getProfileByIdHandler ,
  editProfileByIdHandler,
} = require(`./handler`);

const routes = [
  {
    method: "POST",
    path: `/register`,
    handler: saveRegisterInfoHandler,
  },
  {
    method: "POST",
    path: `/profile/{userId}`,
    handler: saveUserProfilePictureHandler,
  },
  {
    method: `GET`,
    path: `/profile/{userId}`,
    handler: getProfileByIdHandler ,
  },
  {
    method: `PUT`,
    path: `/profile/{userId}`,
    handler: editProfileByIdHandler, // picture and username
  },
];

module.exports = routes;
