package com.sfeir.school.android.datastore.repository.datastore.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.sfeir.school.android.datastore.base.model.SportSessionSort
import com.sfeir.school.android.datastore.base.model.SportTypeFilter
import com.sfeir.school.android.datastore.base.util.DistanceRangeConst
import com.sfeir.school.android.datastore.base.util.FloatRange
import com.sfeir.school.android.datastore.repository.shared.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsPreferencesDataStoreRepository(
	private val prefsDataStore: DataStore<Preferences>,
	private val coroutineScopeIO: CoroutineScope
) : SettingsRepository {

	override val filterBySportType: Flow<SportTypeFilter>
		get() = prefsDataStore.data.map { preferences ->
			preferences[SettingsPreferencesDataStoreKeys.KEY_FILTER_BY_SPORT_TYPE]?.let { enumName ->
				SportTypeFilter.valueOf(enumName)
			} ?: SportTypeFilter.getDefaultValue()
		}

	override val filterByDistanceRange: Flow<FloatRange>
		get() = prefsDataStore.data.map { preferences ->
			FloatRange(
				min = preferences[SettingsPreferencesDataStoreKeys.KEY_FILTER_BY_DISTANCE_RANGE_MIN] ?: DistanceRangeConst.MIN_VALUE,
				max = preferences[SettingsPreferencesDataStoreKeys.KEY_FILTER_BY_DISTANCE_RANGE_MAX] ?: DistanceRangeConst.MAX_VALUE
			)
		}

	override val sortBy: Flow<SportSessionSort>
		get() = prefsDataStore.data.map { preferences ->
			preferences[SettingsPreferencesDataStoreKeys.KEY_SORT_BY]?.let { enumName ->
				SportSessionSort.valueOf(enumName)
			} ?: SportSessionSort.getDefaultValue()
		}

	override val isSortDescending: Flow<Boolean>
		get() = prefsDataStore.data.map { preferences ->
			preferences[SettingsPreferencesDataStoreKeys.KEY_IS_SORT_DESCENDING] ?: false
		}

	override fun setFilterBySportType(sportTypeFilter: SportTypeFilter) {
		coroutineScopeIO.launch {
			prefsDataStore.edit { preferences ->
				preferences[SettingsPreferencesDataStoreKeys.KEY_FILTER_BY_SPORT_TYPE] = sportTypeFilter.name
			}
		}
	}

	override fun setFilterByDistanceRange(distanceRange: FloatRange) {
		coroutineScopeIO.launch {
			prefsDataStore.edit { preferences ->
				preferences[SettingsPreferencesDataStoreKeys.KEY_FILTER_BY_DISTANCE_RANGE_MIN] = distanceRange.min
				preferences[SettingsPreferencesDataStoreKeys.KEY_FILTER_BY_DISTANCE_RANGE_MAX] = distanceRange.max
			}
		}
	}

	override fun setSortBy(sportSessionSort: SportSessionSort) {
		coroutineScopeIO.launch {
			prefsDataStore.edit { preferences ->
				preferences[SettingsPreferencesDataStoreKeys.KEY_SORT_BY] = sportSessionSort.name
			}
		}
	}

	override fun setIsSortDescending(isSortDescending: Boolean) {
		coroutineScopeIO.launch {
			prefsDataStore.edit { preferences ->
				preferences[SettingsPreferencesDataStoreKeys.KEY_IS_SORT_DESCENDING] = isSortDescending
			}
		}
	}
}
