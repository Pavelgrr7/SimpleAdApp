package com.settery.adappapr.domain.impl

import com.settery.adappapr.data.ApiService
import com.settery.adappapr.domain.ContentRepository
import com.settery.adappapr.domain.ListItem

class ContentRepositoryImpl(apiService: ApiService) : ContentRepository {

    override fun getTab1Content(): List<ListItem> {
        return listOf(
            ListItem.ArticleItem("1" ,"Hello1", "world", "https://random.imagecdn.app/600/600"),
            ListItem.ArticleItem("2" ,"Good", "Morning", "https://random.imagecdn.app/600/600"),

            )
    }

    override fun getTab2Content(): List<ListItem> {
        return listOf(
            ListItem.ArticleItem("3", "Article3", "desc", "https://random.imagecdn.app/600/600"),

            )
    }
}