const { registerUser, loginUser } = require("../handler/authControllerHandler");

const registerloginRoutes = [
  {
    method: "POST",
    path: "/register",
    handler: registerUser,
    options: {
      description: "Register a new user",
      notes: "Creates a new user using Firebase Authentication with userId output",
    },
  },
  {
    method: "POST",
    path: "/login",
    handler: loginUser,
    options: {
      description: "Login a user",
      notes: "Verifies a Firebase Authentication ID Token", 
    },
  },
];

module.exports = registerloginRoutes;
