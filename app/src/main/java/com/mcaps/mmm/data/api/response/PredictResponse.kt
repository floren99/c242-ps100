package com.mcaps.mmm.data.api.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

	@field:SerializedName("predicted_label")
	val predictedLabel: String? = null
)
