package com.settery.adappapr.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class ApiResponse(
    @Json(name = "ad_top_item") val topAds: List<AdDto>,
    @Json(name = "ad_down_item") val bottomAds: List<AdDto>,
    @Json(name = "recycler_item") val listItems: List<ListItemDto>
)

// Модель для статьи
data class ArticleDto(
    val title: String,
    val description: String,
    val image: String,
    val showTopAd: Boolean,
    val showBottomAd: Boolean
)

// Модель для рекламы
data class AdDto(
    val id: String, // Предположим, что есть ID
    val title: String,
    val description: String,
    val image: String,
    val label: String, // "Реклама"
    val extraInfo: String?,
    val buttonText: String?
)

@JsonClass(generateAdapter = true)
sealed class ListItemDto {
    @JsonClass(generateAdapter = true)
    data class ArticleItem(val article: ArticleDto) : ListItemDto()

    @JsonClass(generateAdapter = true)
    data class AdItem(val ad: AdDto) : ListItemDto()
}

// todo Moshi: добавить PolymorphicJsonAdapterFactory