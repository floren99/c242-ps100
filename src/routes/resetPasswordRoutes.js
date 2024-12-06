const {
  resetPassword
} = require("../handler/resetPasswordHandler");

const resetPasswordRoutes = [
  {
    method: "POST",
    path: "/reset-password",
    handler: resetPassword,
  }
];

module.exports = resetPasswordRoutes;