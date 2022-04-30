package com.sfeir.school.android.datastore.repository.datastore.data.di

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.google.gson.Gson
import com.sfeir.school.android.datastore.base.util.FileNameConst
import com.sfeir.school.android.datastore.data.model.GpsCoordinates
import com.sfeir.school.android.datastore.data.model.SportSession
import com.sfeir.school.android.datastore.data.model.SportType
import com.sfeir.school.android.datastore.data.serializer.SportSessionListDataStoreSerializer
import com.sfeir.school.android.datastore.repository.datastore.data.SportSessionProtoDataStoreRepository
import com.sfeir.school.android.datastore.repository.datastore.data.legacy.LegacySportSessionListSerializer
import com.sfeir.school.android.datastore.repository.shared.SportSessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreDataRepositoryModule {

	@Provides
	@Singleton
	fun provideLegacySportSessionListSerializer(
		@ApplicationContext context: Context,
		gson: Gson
	): LegacySportSessionListSerializer {
		return LegacySportSessionListSerializer(
			context = context,
			gson = gson
		)
	}

	@Provides
	@Singleton
	fun provideProtoDataStore(
		@ApplicationContext context: Context,
		serializer: LegacySportSessionListSerializer
	): DataStore<List<SportSession>> {

		val dataMigration: DataMigration<List<SportSession>> = object : DataMigration<List<SportSession>> {
			override suspend fun shouldMigrate(currentData: List<SportSession>): Boolean {
				return currentData.isEmpty()
			}

			override suspend fun migrate(currentData: List<SportSession>): List<SportSession> {
				return serializer.read().map { legacySportSession ->
					SportSession.newBuilder()
						.setId(legacySportSession.id)
						.setType(SportType.forNumber(legacySportSession.type.ordinal))
						.setDistance(legacySportSession.distance)
						.setStartedAt(legacySportSession.startedAt.time)
						.setDuration(legacySportSession.duration)
						.setSpeed(legacySportSession.speed)
						.setCalories(legacySportSession.calories)
						.setBpm(legacySportSession.bpm)
						.setPlace(
							GpsCoordinates.newBuilder()
								.setLatitude(legacySportSession.place.latitude)
								.setLongitude(legacySportSession.place.longitude)
								.build()
						)
						.build()
				}
			}

			override suspend fun cleanUp() {
				serializer.clear()
			}
		}

		return DataStoreFactory.create(
			produceFile = { context.dataStoreFile(FileNameConst.PROTO_DATASTORE_FILE_NAME) },
			serializer = SportSessionListDataStoreSerializer,
			migrations = listOf(
				dataMigration
			),
			corruptionHandler = ReplaceFileCorruptionHandler(
				produceNewData = { emptyList() }
			)
		)
	}

	@Provides
	@Singleton
	fun provideSportSessionProtoDataStoreRepository(
		protoDataStore: DataStore<List<SportSession>>,
		coroutineScopeIO: CoroutineScope
	): SportSessionRepository {
		return SportSessionProtoDataStoreRepository(
			dataStore = protoDataStore,
			coroutineScopeIO = coroutineScopeIO
		)
	}
}
