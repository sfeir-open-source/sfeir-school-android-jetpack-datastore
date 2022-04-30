package com.sfeir.school.android.datastore.repository.legacy.settings

import android.content.SharedPreferences
import androidx.core.content.edit
import com.sfeir.school.android.datastore.base.model.SportSessionSort
import com.sfeir.school.android.datastore.base.model.SportTypeFilter
import com.sfeir.school.android.datastore.base.util.DistanceRangeConst
import com.sfeir.school.android.datastore.base.util.FloatRange
import com.sfeir.school.android.datastore.base.util.SettingsKeys
import com.sfeir.school.android.datastore.repository.shared.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SettingsSharedPrefsRepository(
	private val sharedPrefs: SharedPreferences,
	private val coroutineScopeIO: CoroutineScope
) : SettingsRepository {

	private val _filterBySportType: MutableStateFlow<SportTypeFilter> = MutableStateFlow(SportTypeFilter.getDefaultValue())
	override val filterBySportType: Flow<SportTypeFilter>
		get() = _filterBySportType

	private val _filterByDistanceRange: MutableStateFlow<FloatRange> = MutableStateFlow(
		FloatRange(DistanceRangeConst.MIN_VALUE, DistanceRangeConst.MAX_VALUE)
	)
	override val filterByDistanceRange: Flow<FloatRange>
		get() = _filterByDistanceRange

	private val _sortBy: MutableStateFlow<SportSessionSort> =
		MutableStateFlow(SportSessionSort.getDefaultValue())
	override val sortBy: Flow<SportSessionSort>
		get() = _sortBy

	private val _isSortDescending: MutableStateFlow<Boolean> = MutableStateFlow(false)
	override val isSortDescending: Flow<Boolean>
		get() = _isSortDescending

	private val sharedPrefsListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
		when (key) {
			SettingsKeys.KEY_FILTER_BY_SPORT_TYPE -> syncFilterBySportTypeFlow()
			SettingsKeys.KEY_FILTER_BY_DISTANCE_RANGE_MIN,
			SettingsKeys.KEY_FILTER_BY_DISTANCE_RANGE_MAX -> syncFilterByDistanceRangeFlow()
			SettingsKeys.KEY_SORT_BY -> syncSortByFlow()
			SettingsKeys.KEY_IS_SORT_DESCENDING -> syncIsSortDescendingFlow()
		}
	}

	init {
		syncFlows()
		sharedPrefs.registerOnSharedPreferenceChangeListener(sharedPrefsListener)
	}

	private fun syncFilterBySportTypeFlow() {
		coroutineScopeIO.launch {
			val sportTypeFilter: SportTypeFilter =
				sharedPrefs.getString(
					SettingsKeys.KEY_FILTER_BY_SPORT_TYPE,
					null
				)?.let { SportTypeFilter.valueOf(it) }
					?: SportTypeFilter.getDefaultValue()
			_filterBySportType.emit(sportTypeFilter)
		}
	}

	private fun syncFilterByDistanceRangeFlow() {
		coroutineScopeIO.launch {
			_filterByDistanceRange.emit(
				FloatRange(
					sharedPrefs.getFloat(
						SettingsKeys.KEY_FILTER_BY_DISTANCE_RANGE_MIN,
						DistanceRangeConst.MIN_VALUE
					),
					sharedPrefs.getFloat(
						SettingsKeys.KEY_FILTER_BY_DISTANCE_RANGE_MAX,
						DistanceRangeConst.MAX_VALUE
					)
				)
			)
		}
	}

	private fun syncSortByFlow() {
		coroutineScopeIO.launch {
			val sort: SportSessionSort =
				sharedPrefs.getString(
					SettingsKeys.KEY_SORT_BY,
					null
				)?.let { SportSessionSort.valueOf(it) }
					?: SportSessionSort.getDefaultValue()
			_sortBy.emit(sort)
		}
	}

	private fun syncIsSortDescendingFlow() {
		coroutineScopeIO.launch {
			val isSortDescending: Boolean =
				sharedPrefs.getBoolean(
					SettingsKeys.KEY_IS_SORT_DESCENDING,
					false
				)
			_isSortDescending.emit(isSortDescending)
		}
	}

	private fun syncFlows() {
		syncFilterBySportTypeFlow()
		syncFilterByDistanceRangeFlow()
		syncSortByFlow()
		syncIsSortDescendingFlow()
	}

	override fun setFilterBySportType(sportTypeFilter: SportTypeFilter) {
		sharedPrefs.edit(commit = true) {
			putString(
				SettingsKeys.KEY_FILTER_BY_SPORT_TYPE,
				sportTypeFilter.name
			)
		}
	}

	override fun setFilterByDistanceRange(distanceRange: FloatRange) {
		sharedPrefs.edit(commit = true) {
			putFloat(
				SettingsKeys.KEY_FILTER_BY_DISTANCE_RANGE_MIN,
				distanceRange.min
			)
			putFloat(
				SettingsKeys.KEY_FILTER_BY_DISTANCE_RANGE_MAX,
				distanceRange.max
			)
		}
	}

	override fun setSortBy(sportSessionSort: SportSessionSort) {
		sharedPrefs.edit(commit = true) {
			putString(
				SettingsKeys.KEY_SORT_BY,
				sportSessionSort.name
			)
		}
	}

	override fun setIsSortDescending(isSortDescending: Boolean) {
		sharedPrefs.edit(commit = true) {
			putBoolean(
				SettingsKeys.KEY_IS_SORT_DESCENDING,
				isSortDescending
			)
		}
	}
}
