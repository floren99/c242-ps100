const { getRandomQuestionsKemampuanAnalitik } = require("../handler/questionControllerHandlerKemampuanAnalitik");
const { getRandomQuestions } = require("../handler/questionControllerHandler");
const { getRandomQuestionsBerpikirKritis } = require("../handler/questionControllerHandlerBerpikirKritis");
const { getRandomQuestionsTeknologiInovasi } = require("../handler/questionControllerHandlerTeknologiInovasi");
const { getRandomQuestionsKomunikasiLiterasi } = require("../handler/questionControllerHandlerKomunikasiLiterasi");


const questionRoutes = [
  {
    method: "GET",
    path: "/questions-4",
    handler: getRandomQuestionsKemampuanAnalitik,
    options: {
      description: "Get a random question",
      notes: "Returns a random question for the prediction test",
    },
  },
  {
    method: "GET",
    path: "/questions",
    handler: getRandomQuestions,
    options: {
      description: "Get a random question",
      notes: "Returns a random question for the prediction test",
    },
  },
  {
    method: "GET",
    path: "/questions-1",
    handler: getRandomQuestionsBerpikirKritis,
    options: {
      description: "Get a random question",
      notes: "Returns a random question for the prediction test",
    },
  },
  {
    method: "GET",
    path: "/questions-2",
    handler: getRandomQuestionsTeknologiInovasi,
    options: {
      description: "Get a random question",
      notes: "Returns a random question for the prediction test",
    },
  },
  {
    method: "GET",
    path: "/questions-3",
    handler: getRandomQuestionsKomunikasiLiterasi,
    options: {
      description: "Get a random question",
      notes: "Returns a random question for the prediction test",
    },
  },
];

module.exports = questionRoutes;
