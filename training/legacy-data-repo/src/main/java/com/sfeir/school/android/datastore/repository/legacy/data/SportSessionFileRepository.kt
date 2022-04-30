package com.sfeir.school.android.datastore.repository.legacy.data

import com.sfeir.school.android.datastore.base.util.DistanceRangeConst
import com.sfeir.school.android.datastore.data.model.GpsCoordinates
import com.sfeir.school.android.datastore.data.model.SportSession
import com.sfeir.school.android.datastore.data.model.SportType
import com.sfeir.school.android.datastore.data.SportSessionListLegacySerializer
import com.sfeir.school.android.datastore.repository.shared.SportSessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class SportSessionFileRepository(
	private val serializer: SportSessionListLegacySerializer,
	private val coroutineScopeIO: CoroutineScope
) : SportSessionRepository {

	private val _sportSessions: MutableStateFlow<List<SportSession>> = MutableStateFlow(emptyList())

	init {
		updateFlow()
	}

	private fun updateFlow() {
		coroutineScopeIO.launch {
			_sportSessions.emit(serializer.read())
		}
	}

	override fun getSportSessions(): Flow<List<SportSession>> = _sportSessions

	override fun addNewRandomSportSession() {
		coroutineScopeIO.launch {
			_sportSessions.update { sportSessions ->
				val updatedSportSessions: MutableList<SportSession> = sportSessions.toMutableList()

				val sportTypeEnums = SportType.values()

				val newSportSession = SportSession(
					id = Random.nextInt(0, Int.MAX_VALUE),
					type = sportTypeEnums[Random.nextInt(sportTypeEnums.size)],
					distance = Random.nextFloat() * DistanceRangeConst.MAX_VALUE,
					startedAt = Calendar.getInstance().time,
					duration = Random.nextLong(TimeUnit.HOURS.toSeconds(12)),
					speed = Random.nextFloat() * 35,
					calories = Random.nextInt(2500),
					bpm = Random.nextInt(200),
					place = GpsCoordinates(
						latitude = Random.nextDouble(-90.0, 90.0),
						longitude = Random.nextDouble(-180.0, 180.0)
					)
				)

				updatedSportSessions.add(newSportSession)

				serializer.write(updatedSportSessions)

				updatedSportSessions
			}

			updateFlow()
		}
	}

	override fun deleteRandomSportSession() {
		coroutineScopeIO.launch {
			val sportSessionList: List<SportSession> = _sportSessions.first()
			if (sportSessionList.isEmpty()) return@launch

			_sportSessions.update { sportSessions ->
				val updatedSportSessions: MutableList<SportSession> = sportSessions.toMutableList()

				updatedSportSessions.removeAt(Random.nextInt(0, sportSessions.size))

				serializer.write(updatedSportSessions)

				updatedSportSessions
			}

			updateFlow()
		}
	}
}
