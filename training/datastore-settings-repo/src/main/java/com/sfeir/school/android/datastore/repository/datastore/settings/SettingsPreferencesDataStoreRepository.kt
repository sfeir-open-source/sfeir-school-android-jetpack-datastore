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
		get() = TODO("Implement here the sport type filter getter.")

	override val filterByDistanceRange: Flow<FloatRange>
		get() = TODO("Implement here the distance range filter getter.")

	override val sortBy: Flow<SportSessionSort>
		get() = TODO("Implement here the sorting getter.")

	override val isSortDescending: Flow<Boolean>
		get() = TODO("Implement here the sorting direction getter.")

	override fun setFilterBySportType(sportTypeFilter: SportTypeFilter) {
		TODO("Implement here the sport type filter setter.")
	}

	override fun setFilterByDistanceRange(distanceRange: FloatRange) {
		TODO("Implement here the distance range filter setter.")
	}

	override fun setSortBy(sportSessionSort: SportSessionSort) {
		TODO("Implement here the sorting setter.")
	}

	override fun setIsSortDescending(isSortDescending: Boolean) {
		TODO("Implement here the sorting direction setter.")
	}
}
