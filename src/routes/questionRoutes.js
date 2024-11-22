const Hapi = require('@hapi/hapi');
const { getRandomQuestion } = require('../handler/questionControllerHandler');

const questionRoutes = [
  {
    method: 'GET',
    path: '/questions',
    handler: getRandomQuestion,
    options: {
      description: 'Get a random question',
      notes: 'Returns a random question for the prediction test',
      tags: ['api', 'questions'],
    },
  },
];

module.exports = questionRoutes;