const majorRoutes = require('./majorRoutes');
const questionRoutes = require('./questionRoutes');
const registerloginRoutes = require('./registerloginRoutes');
const resetPasswordRoutes = require('./resetPasswordRoutes')

const routes = [
    ...majorRoutes,
    ...questionRoutes,
    ...registerloginRoutes,
    ...resetPasswordRoutes
  ];
  
  module.exports = routes;