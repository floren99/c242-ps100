const getAllQuestion = require("../services/getAllQuestion");

const getRandomQuestionsKemampuanAnalitik = async (request, h) => {
  try {
    const questionList = await getAllQuestion("kemampuan_analitik");

    // Shuffle and select 10 random questions
    const shuffled = questionList.sort(() => 0.5 - Math.random());
    const randomQuestions = shuffled.slice(0, 5);

    // Return the 10 random questions
    return h
      .response({
        questions: randomQuestions.map((question) => ({
          question_id: question.id,
          question_text: question.question_text,
          options: question.options,
          correct_answer: question.correct_answer
        })),
      })
      .code(200);
  } catch (error) {
    console.error("Error fetching random questions:", error);
    return h.response({ error: "Failed to fetch questions" }).code(500);
  }
};

module.exports = { getRandomQuestionsKemampuanAnalitik };