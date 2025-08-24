package com.settery.adappapr.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.settery.adappapr.data.ApiService

interface ContentRepository {
    fun getTab1Content(): List<ListItem>
    fun getTab2Content(): List<ListItem>
}