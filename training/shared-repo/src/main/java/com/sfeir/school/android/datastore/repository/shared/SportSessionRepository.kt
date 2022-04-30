package com.sfeir.school.android.datastore.repository.shared

import com.sfeir.school.android.datastore.data.model.SportSession
import kotlinx.coroutines.flow.Flow

interface SportSessionRepository {
	fun getSportSessions(): Flow<List<SportSession>>
	fun addNewRandomSportSession()
	fun deleteRandomSportSession()
}
