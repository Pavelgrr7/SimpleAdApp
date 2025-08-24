package com.settery.adappapr.domain

class GetContentUseCase(val repository: ContentRepository) {
    fun execute(tab: String): List<ListItem> {
        return when(tab) {
            "tab1" -> repository.getTab1Content()
            "tab2" -> repository.getTab2Content()
            else -> throw IllegalArgumentException("Unknown tab: $tab")
        }
    }
}

class ShouldShowOnboardingUseCase(settingsRepository: SettingsRepository) {
}

class CompleteOnboardingUseCase(settingsRepository: SettingsRepository) {

}