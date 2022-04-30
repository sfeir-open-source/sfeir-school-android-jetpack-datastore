package com.sfeir.school.android.datastore.repository.datastore.data

import androidx.datastore.core.DataStore
import com.sfeir.school.android.datastore.base.model.SportTypeFilter
import com.sfeir.school.android.datastore.base.util.DistanceRangeConst
import com.sfeir.school.android.datastore.data.model.GpsCoordinates
import com.sfeir.school.android.datastore.data.model.SportSession
import com.sfeir.school.android.datastore.data.model.SportType
import com.sfeir.school.android.datastore.repository.shared.SportSessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class SportSessionProtoDataStoreRepository(
	private val dataStore: DataStore<List<SportSession>>,
	private val coroutineScopeIO: CoroutineScope
) : SportSessionRepository {

	override fun getSportSessions(): Flow<List<SportSession>> = dataStore.data

	override fun addNewRandomSportSession() {
		coroutineScopeIO.launch {
			dataStore.updateData { sportSessions ->
				val newSportSession = SportSession.newBuilder()
					.setId(Random.nextInt(0, Int.MAX_VALUE))
					.setType(
						SportType.forNumber(
							Random.nextInt(0, SportTypeFilter.values().size - 1)
						)
					)
					.setDistance(Random.nextFloat() * DistanceRangeConst.MAX_VALUE)
					.setStartedAt(Calendar.getInstance().timeInMillis)
					.setDuration(Random.nextLong(TimeUnit.HOURS.toSeconds(12)))
					.setSpeed(Random.nextFloat() * 35)
					.setCalories(Random.nextInt(2500))
					.setBpm(Random.nextInt(200))
					.setPlace(
						GpsCoordinates.newBuilder()
							.setLatitude(Random.nextDouble(-90.0, 90.0))
							.setLongitude(Random.nextDouble(-180.0, 180.0))
							.build()
					)
					.build()

				sportSessions.toMutableList().apply {
					add(newSportSession)
				}
			}
		}
	}

	override fun deleteRandomSportSession() {
		coroutineScopeIO.launch {
			dataStore.updateData { sportSessions ->
				if (sportSessions.isEmpty()) return@updateData sportSessions

				sportSessions.toMutableList().apply {
					removeAt(Random.nextInt(0, size))
				}
			}
		}
	}
}
