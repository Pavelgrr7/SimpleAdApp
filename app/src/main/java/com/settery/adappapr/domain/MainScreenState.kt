package com.settery.adappapr.domain

data class MainScreenState(
    val isLoading: Boolean = false,
    val items: List<ListItem> = emptyList(),
    val error: String? = null,
    val currentTab: String?
)
