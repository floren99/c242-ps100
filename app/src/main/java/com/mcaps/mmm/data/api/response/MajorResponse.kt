package com.mcaps.mmm.data.api.response

import com.google.gson.annotations.SerializedName

data class MajorResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class WebLinksItem(

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class CareerProspectsItem(

	@field:SerializedName("salaryRange")
	val salaryRange: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("careerName")
	val careerName: String? = null
)

data class DataItem(

	@field:SerializedName("image")
	val image: List<Any?>? = null,

	@field:SerializedName("skillsRequired")
	val skillsRequired: List<String?>? = null,

	@field:SerializedName("universities")
	val universities: List<String?>? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("webLinks")
	val webLinks: List<WebLinksItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("careerProspects")
	val careerProspects: List<CareerProspectsItem?>? = null,

	@field:SerializedName("tips")
	val tips: String? = null
)
