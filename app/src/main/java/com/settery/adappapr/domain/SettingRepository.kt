package com.settery.adappapr.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

var i = 0

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    private val USER_ID = stringPreferencesKey("user_id")
    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

    val userId: Flow<String> = dataStore.data.map { it[USER_ID] ?: generateAndSaveNewId() }
    val isOnboardingCompleted: Flow<Boolean> = dataStore.data.map { it[ONBOARDING_COMPLETED] == true }

    suspend fun setOnboardingCompleted() {
        dataStore.edit { settings -> settings[ONBOARDING_COMPLETED] = true }
    }
}

private fun SettingsRepository.generateAndSaveNewId(): String {
    return i++.toString()
}
