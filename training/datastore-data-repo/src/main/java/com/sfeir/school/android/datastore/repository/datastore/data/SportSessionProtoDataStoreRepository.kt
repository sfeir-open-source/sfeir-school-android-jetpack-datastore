package com.sfeir.school.android.datastore.repository.datastore.data

import androidx.datastore.core.DataStore
import com.sfeir.school.android.datastore.data.model.SportSession
import com.sfeir.school.android.datastore.repository.shared.SportSessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class SportSessionProtoDataStoreRepository(
	private val dataStore: DataStore<List<SportSession>>,
	private val coroutineScopeIO: CoroutineScope
) : SportSessionRepository {

	override fun getSportSessions(): Flow<List<SportSession>> {
		TODO("Implement here sport sessions fetching from Proto Datastore.")
	}

	override fun addNewRandomSportSession() {
//      val newSportSession = SportSession.newBuilder()
//        .setId(Random.nextInt(0, Int.MAX_VALUE))
//        .setType(
//          SportType.forNumber(
//            Random.nextInt(0, SportTypeFilter.values().size - 1)
//          )
//        )
//        .setDistance(Random.nextFloat() * DistanceRangeConst.MAX_VALUE)
//        .setStartedAt(Calendar.getInstance().timeInMillis)
//        .setDuration(Random.nextLong(TimeUnit.HOURS.toSeconds(12)))
//        .setSpeed(Random.nextFloat() * 35)
//        .setCalories(Random.nextInt(2500))
//        .setBpm(Random.nextInt(200))
//        .setPlace(
//          GpsCoordinates.newBuilder()
//            .setLatitude(Random.nextDouble(-90.0, 90.0))
//            .setLongitude(Random.nextDouble(-180.0, 180.0))
//            .build()
//        )
//        .build()
		TODO("Implement here adding new random sport session on Proto Datastore. The randomizer generator is provided on the snippet above.")
	}

	override fun deleteRandomSportSession() {
		TODO("Implement here deleting a random sport session on Proto DataStore.")
	}
}
