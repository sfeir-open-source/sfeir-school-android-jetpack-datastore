package com.sfeir.school.android.datastore.repository.shared

import com.sfeir.school.android.datastore.base.model.SportSessionSort
import com.sfeir.school.android.datastore.base.model.SportTypeFilter
import com.sfeir.school.android.datastore.base.util.FloatRange
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
	val filterBySportType: Flow<SportTypeFilter>

	val filterByDistanceRange: Flow<FloatRange>

	val sortBy: Flow<SportSessionSort>

	val isSortDescending: Flow<Boolean>

	fun setFilterBySportType(sportTypeFilter: SportTypeFilter)

	fun setFilterByDistanceRange(distanceRange: FloatRange)

	fun setSortBy(sportSessionSort: SportSessionSort)

	fun setIsSortDescending(isSortDescending: Boolean)
}
