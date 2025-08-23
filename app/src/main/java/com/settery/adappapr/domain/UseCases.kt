package com.settery.adappapr.domain

class GetContentUseCase(val repository: ContentRepository) {
    fun execute(tab: String): List<ListItem> {
        when(tab) {
            "tab1" -> return repository.getTab1Content()
            "tab2" -> return repository.getTab2Content()
            else -> throw IllegalArgumentException("Unknown tab: $tab")
        }
    }
}

class ShouldShowOnboardingUseCase(settingsRepository: SettingsRepository) {
}

class CompleteOnboardingUseCase(settingsRepository: SettingsRepository) {

}