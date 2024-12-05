const { getMajor } = require('../handler/majorHandler');
const { getMajorAll } = require('../handler/majorHandlerAll');

const majorRoutes = [
  {
    method: 'GET',
    path: '/major/{id}',
    handler: getMajor,
  },
  {
    method: "GET",
    path: "/major",
    handler: getMajorAll,
  },
];

module.exports = majorRoutes;
