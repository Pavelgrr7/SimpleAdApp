package com.settery.adappapr.presentation

import androidx.lifecycle.ViewModel
import com.settery.adappapr.domain.GetContentUseCase
import com.settery.adappapr.domain.MainScreenState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class MainViewModel(
    private val getContentUseCase: GetContentUseCase // use-case через DI
) : ViewModel(), ContainerHost<MainScreenState, MainScreenSideEffect> {

    //    MainScreenState - это наш класс-состояние.
    override val container = container<MainScreenState, MainScreenSideEffect>(
        initialState = MainScreenState(isLoading = true),)

    fun loadContent(tab: String) = intent {
        reduce {
            state.copy(isLoading = true, error = null)
        }

        // use-case для получения данных
        try {
            val items = getContentUseCase.execute(tab)
            reduce {
                state.copy(isLoading = false, items = items)
            }
        } catch (e: Exception) {
            reduce {
                state.copy(isLoading = false, error = "Произошла ошибка")
            }
        }
    }

//    fun onRetryClicked() {
//        loadContent(container.stateFlow.value.currentTab)
//    }

//    fun onArticleClicked(article: Article) = intent {
//        postSideEffect(MainScreenSideEffect.NavigateToArticle(article))
//    }

//    fun onAdClicked(ad: Ad) = intent {
//        val url = "https://example.com/ad?app_key=MY_KEY&ad_id=${ad.id}&placement=inline"
//        postSideEffect(MainScreenSideEffect.OpenBrowser(url))
//    }

}