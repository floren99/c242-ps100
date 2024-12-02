const { getMajor } = require('../handler/majorsHandler');

const majorRoutes = [
  {
    method: 'GET',
    path: '/major',
    handler: getMajor,
  },
];

module.exports = majorRoutes;
