package com.sfeir.school.android.datastore.ui.fragment.settings

import androidx.lifecycle.ViewModel
import com.sfeir.school.android.datastore.base.model.SportSessionSort
import com.sfeir.school.android.datastore.base.model.SportTypeFilter
import com.sfeir.school.android.datastore.base.util.FloatRange
import com.sfeir.school.android.datastore.repository.shared.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val settingsRepository: SettingsRepository
) : ViewModel() {

	fun getFilterBySportType(): Flow<SportTypeFilter> =
		settingsRepository.filterBySportType

	fun setFilterBySportType(sportTypeFilter: SportTypeFilter) {
		settingsRepository.setFilterBySportType(sportTypeFilter)
	}

	fun getFilterByDistanceRange(): Flow<FloatRange> =
		settingsRepository.filterByDistanceRange

	fun setFilterByDistanceRange(distanceRange: FloatRange) {
		settingsRepository.setFilterByDistanceRange(distanceRange)
	}

	fun getSortBy(): Flow<SportSessionSort> =
		settingsRepository.sortBy

	fun setSortBy(sportSessionSort: SportSessionSort) {
		settingsRepository.setSortBy(sportSessionSort)
	}

	fun getIsSortDescending(): Flow<Boolean> =
		settingsRepository.isSortDescending

	fun setIsSortDescending(isSortDescending: Boolean) {
		settingsRepository.setIsSortDescending(isSortDescending)
	}
}
