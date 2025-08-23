package com.settery.adappapr.domain

sealed class ListItem {
    abstract val id: String

    data class ArticleItem(
        override val id: String,
        val title: String,
        val description: String,
        val imageUrl: String
    ) : ListItem()

    data class AdItem(
        override val id: String,
        val title: String,
        val description: String,
        val imageUrl: String,
        val label: String // типа "Реклама"
    ) : ListItem()
}