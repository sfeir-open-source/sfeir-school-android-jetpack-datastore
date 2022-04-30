package com.sfeir.school.android.datastore.repository.datastore.settings

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sfeir.school.android.datastore.base.util.SettingsKeys

object SettingsPreferencesDataStoreKeys {
	val KEY_FILTER_BY_SPORT_TYPE = stringPreferencesKey(SettingsKeys.KEY_FILTER_BY_SPORT_TYPE)
	val KEY_FILTER_BY_DISTANCE_RANGE_MIN = floatPreferencesKey(SettingsKeys.KEY_FILTER_BY_DISTANCE_RANGE_MIN)
	val KEY_FILTER_BY_DISTANCE_RANGE_MAX = floatPreferencesKey(SettingsKeys.KEY_FILTER_BY_DISTANCE_RANGE_MAX)
	val KEY_SORT_BY = stringPreferencesKey(SettingsKeys.KEY_SORT_BY)
	val KEY_IS_SORT_DESCENDING = booleanPreferencesKey(SettingsKeys.KEY_IS_SORT_DESCENDING)
}
