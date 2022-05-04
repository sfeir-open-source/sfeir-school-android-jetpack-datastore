package com.sfeir.school.android.datastore.repository.datastore.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.google.gson.Gson
import com.sfeir.school.android.datastore.data.model.SportSession
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
		TODO("Implement here Proto Datastore initialization. Use FileNameConst.PROTO_DATASTORE_FILE_NAME as file name.")
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
