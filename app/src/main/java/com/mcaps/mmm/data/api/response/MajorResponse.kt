package com.mcaps.mmm.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MajorResponse(

	@field:SerializedName("data")
	val data: List<MajorDataItem> = emptyList(),

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

@Parcelize
data class MajorDataItem(
	@SerializedName("image")
	val image: List<String> = emptyList(),

	@SerializedName("skillsRequired")
	val skillsRequired: List<String> = emptyList(),

	@SerializedName("universities")
	val universities: List<String> = emptyList(),

	@SerializedName("description")
	val description: String? = null,

	@SerializedName("webLinks")
	val webLinks: List<WebLinksItem> = emptyList(),

	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("title")
	val title: String? = null,

	@SerializedName("careerProspects")
	val careerProspects: List<CareerProspectsItem> = emptyList(),

	@SerializedName("tips")
	val tips: String? = null
) : Parcelable {

	fun formattedSkillsRequired(): String {
		return skillsRequired.joinToString(separator = "\n- ", prefix = "\n- ")
	}

	fun formattedUniversities(): String {
		return universities.joinToString(separator = "\n- ", prefix = "- ")
	}

	fun formattedCareerProspects(): String {
		return careerProspects.joinToString(separator = "\n- ", prefix = "\n- ") { item ->
			"${item.careerName}: ${item.salaryRange}\n${item.description}"
		}
	}

	@Parcelize
	data class WebLinksItem(
		@SerializedName("title")
		val title: String? = null,

		@SerializedName("url")
		val url: String? = null
	) : Parcelable

	@Parcelize
	data class CareerProspectsItem(
		@SerializedName("salaryRange")
		val salaryRange: String? = null,

		@SerializedName("description")
		val description: String? = null,

		@SerializedName("careerName")
		val careerName: String? = null
	) : Parcelable }

