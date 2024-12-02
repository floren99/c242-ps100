const majorsRoutes = require('./majorsRoutes');
const questionRoutes = require('./questionRoutes');
const registerloginRoutes = require('./registerloginRoutes');

const routes = [
    ...majorsRoutes,
    ...questionRoutes,
    ...registerloginRoutes
  ];
  
  module.exports = routes;