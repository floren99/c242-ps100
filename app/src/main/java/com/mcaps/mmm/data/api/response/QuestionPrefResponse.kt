package com.mcaps.mmm.data.api.response

import com.google.gson.annotations.SerializedName

data class QuestionPrefResponse(

	@field:SerializedName("questions")
	val questions: List<QuestionsItem> = emptyList()
)

data class QuestionsItem(

	@field:SerializedName("question_text")
	val questionText: String? = null,

	@field:SerializedName("options")
	val options: List<String> = emptyList(),

	@field:SerializedName("correct_answer")
	val correctAnswer: String? = null,

	@field:SerializedName("question_id")
	val questionId: String? = null
)
