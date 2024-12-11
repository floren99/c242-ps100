package com.mcaps.mmm.view.dashboard.path

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mcaps.mmm.data.api.response.MajorDataItem
import com.mcaps.mmm.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postponeEnterTransition()

        val pathData: MajorDataItem? = intent.getParcelableExtra("path_data")

        if (pathData != null) {
            Log.d("DetailActivity", "Data received: $pathData")

            binding.tvDetailName.text = pathData.title ?: "N/A"
            binding.tvDetailDescription.text = pathData.description ?: "No description available"
            binding.tvDetailTips.text = pathData.tips ?: "No tips available"

            binding.tvDetailUniversitas.text = if (pathData.universities.isNotEmpty()) {
                pathData.universities.joinToString("\n") { "- ${it}" } // Menambahkan "-" sebelum setiap universitas
            } else {
                "No universities listed"
            }

            binding.tvDetailSkill.text = if (pathData.skillsRequired.isNotEmpty()) {
                pathData.skillsRequired.joinToString("\n") { "- ${it}" } // Menambahkan "-" sebelum setiap skill
            } else {
                "No skills required listed"
            }

            val careerInfo = if (pathData.careerProspects.isNotEmpty()) {
                pathData.careerProspects.joinToString("\n\n") { career ->
                    "- ${career.careerName ?: "Unknown Career"}:\n" +
                            "${career.description ?: "No description"}\n" +
                            "- Salary: ${career.salaryRange ?: "N/A"}"
                }
            } else {
                "No career prospects listed"
            }
            binding.tvDetailCareer.text = careerInfo

            val webLinks = if (pathData.webLinks.isNotEmpty()) {
                pathData.webLinks.joinToString("\n\n") { link ->
                    """
                    ${link.title ?: "Untitled"}<br>
                    <a href="${link.url ?: "#"}">${link.url ?: "No URL"}</a><br>
                    """.trimIndent()
                }
            } else {
                "No web links available"
            }

            binding.tvDetailArticle.text = Html.fromHtml(webLinks, Html.FROM_HTML_MODE_COMPACT)
            binding.tvDetailArticle.movementMethod = LinkMovementMethod.getInstance()

            if (pathData.image.isNotEmpty()) {
                val firstImage = pathData.image.firstOrNull() as? String
                if (!firstImage.isNullOrEmpty()) {
                    Glide.with(this)
                        .load(firstImage)
                        .into(binding.ivDetailImage)
                        .clearOnDetach()
                        .run { startPostponedEnterTransition() }
                }
            } else {
                startPostponedEnterTransition()
            }
        } else {
            startPostponedEnterTransition()
            Log.e("DetailActivity", "No data received from intent")
        }
    }
}
