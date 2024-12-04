package com.mcaps.mmm.data.api.response

import com.google.gson.annotations.SerializedName

data class MajorResponse(

	@field:SerializedName("data")
	val data: List<MajorDataItem> = emptyList(),

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

data class MajorDataItem(

	@field:SerializedName("image")
	val image: List<Any> = emptyList(),

	@field:SerializedName("skillsRequired")
	val skillsRequired: List<String> = emptyList(),

	@field:SerializedName("universities")
	val universities: List<String> = emptyList(),

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("webLinks")
	val webLinks: List<WebLinksItem> = emptyList(),

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("careerProspects")
	val careerProspects: List<CareerProspectsItem> = emptyList(),

	@field:SerializedName("tips")
	val tips: String? = null
)
