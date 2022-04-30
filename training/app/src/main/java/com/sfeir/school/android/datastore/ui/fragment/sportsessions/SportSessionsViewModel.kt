package com.sfeir.school.android.datastore.ui.fragment.sportsessions

import androidx.lifecycle.ViewModel
import com.sfeir.school.android.datastore.base.model.SportSessionSort
import com.sfeir.school.android.datastore.data.model.SportType
import com.sfeir.school.android.datastore.base.model.SportTypeFilter
import com.sfeir.school.android.datastore.data.model.SportSession
import com.sfeir.school.android.datastore.repository.shared.SettingsRepository
import com.sfeir.school.android.datastore.repository.shared.SportSessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SportSessionsViewModel @Inject constructor(
	private val sportSessionRepository: SportSessionRepository,
	private val settingsRepository: SettingsRepository
) : ViewModel() {

	@OptIn(FlowPreview::class)
	fun getSportSessions(): Flow<List<SportSession>> {
		return combine(
			sportSessionRepository.getSportSessions(),
			settingsRepository.filterBySportType,
			settingsRepository.filterByDistanceRange,
			settingsRepository.sortBy,
			settingsRepository.isSortDescending
		) { sportSessions, sportTypeFilter, distanceRangeFilter, sortBy, isSortDescending ->
			Timber.d("----- sportSessions=$sportSessions")
			Timber.d("----- sportTypeFilter=$sportTypeFilter")
			Timber.d("----- distanceRangeFilter=$distanceRangeFilter")
			Timber.d("----- sortBy=$sortBy")
			Timber.d("----- isSortDescending=$isSortDescending")

			val filteredSportSessions: List<SportSession> = sportSessions
				.filter { sportSession ->
					sportSession.type == when (sportTypeFilter) {
						SportTypeFilter.NONE -> return@filter true
						SportTypeFilter.WALKING -> SportType.WALKING
						SportTypeFilter.RUNNING -> SportType.RUNNING
						SportTypeFilter.BIKING -> SportType.BIKING
						SportTypeFilter.SWIMMING -> SportType.SWIMMING
					}
				}
				.filter {
					it.distance in distanceRangeFilter.min..distanceRangeFilter.max
				}

			Timber.d("----- filteredSportSessions: $filteredSportSessions")

			when (sortBy) {
				SportSessionSort.DATE -> {
					if (!isSortDescending) {
						filteredSportSessions.sortedBy { it.startedAt }
					} else {
						filteredSportSessions.sortedByDescending { it.startedAt }
					}
				}
				SportSessionSort.DISTANCE -> {
					if (!isSortDescending) {
						filteredSportSessions.sortedBy { it.distance }
					} else {
						filteredSportSessions.sortedByDescending { it.distance }
					}
				}
				SportSessionSort.DURATION -> {
					if (!isSortDescending) {
						filteredSportSessions.sortedBy { it.duration }
					} else {
						filteredSportSessions.sortedByDescending { it.duration }
					}
				}
				SportSessionSort.SPEED -> {
					if (!isSortDescending) {
						filteredSportSessions.sortedBy { it.speed }
					} else {
						filteredSportSessions.sortedByDescending { it.speed }
					}
				}
				SportSessionSort.BPM -> {
					if (!isSortDescending) {
						filteredSportSessions.sortedBy { it.bpm }
					} else {
						filteredSportSessions.sortedByDescending { it.bpm }
					}
				}
				SportSessionSort.CALORIES -> {
					if (!isSortDescending) {
						filteredSportSessions.sortedBy { it.calories }
					} else {
						filteredSportSessions.sortedByDescending { it.calories }
					}
				}
			}
		}.debounce(500L)
	}

	fun addNewRandomSportSession() {
		sportSessionRepository.addNewRandomSportSession()
	}

	fun deleteRandomSportSession() {
		sportSessionRepository.deleteRandomSportSession()
	}
}
