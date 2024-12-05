const majorRoutes = require('./majorRoutes');
const questionRoutes = require('./questionRoutes');
const registerloginRoutes = require('./registerloginRoutes');

const routes = [
    ...majorRoutes,
    ...questionRoutes,
    ...registerloginRoutes
  ];
  
  module.exports = routes;