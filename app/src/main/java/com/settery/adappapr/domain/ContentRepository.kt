package com.settery.adappapr.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class ContentRepository(private val ds: DataStore<Preferences>) {

    fun getTab1Content(): List<ListItem> {
        return listOf()
    }

    fun getTab2Content(): List<ListItem> {
        return listOf()
    }


}