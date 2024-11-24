const { registerUser, loginUser } = require("../handler/authControllerHandler");

const registerloginRoutes = [
  {
    method: "POST",
    path: "/register",
    handler: registerUser,
    options: {
      description: "Register a new user",
      notes: "Creates a new user using Firebase Authentication with userId output",
      validate: {
        payload: {
          email: Joi.string().email().required().description("User email"),
          password: Joi.string().min(6).required().description("User password"), //password minimal 6
        },
      },
    },
  },
  {
    method: "POST",
    path: "/login",
    handler: loginUser,
    options: {
      description: "Login a user",
      notes: "Verifies a Firebase Authentication ID Token",
      validate: {
        payload: {
          idToken: Joi.string().required().description("Firebase ID Token"),
        },
      },
    },
  },
];

module.exports = registerloginRoutes;
