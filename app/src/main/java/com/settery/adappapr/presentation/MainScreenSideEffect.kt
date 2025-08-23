package com.settery.adappapr.presentation

import com.settery.adappapr.Article

sealed class MainScreenSideEffect {
    data class NavigateToArticle(val article: Article) : MainScreenSideEffect()

    data class OpenBrowser(val url: String) : MainScreenSideEffect()

    data class ShowErrorSnackbar(val message: String) : MainScreenSideEffect()
}