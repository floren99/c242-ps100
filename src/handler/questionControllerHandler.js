    const getAllQuestion = require('./getAllQuestion');

    const getRandomQuestion = async (request, h) => {
    try {
        // Call getAllData to fetch data from the 'questions' collection
        const questionList = await getAllQuestion('questions');

        // Select a random question from the list
        const randomQuestion = questionList[Math.floor(Math.random() * questionList.length)];

        // Return the random question as the response
        return h.response({
        question_id: randomQuestion.id,
        question_text: randomQuestion.question_text,
        options: randomQuestion.options,
        //   image_url: randomQuestion.image_url  // If the question has an associated image URL
        }).code(200);

    } catch (error) {
        console.error('Error fetching random question:', error);
        return h.response({ error: 'Failed to fetch question' }).code(500);
    }
    };

    module.exports = { getRandomQuestion };