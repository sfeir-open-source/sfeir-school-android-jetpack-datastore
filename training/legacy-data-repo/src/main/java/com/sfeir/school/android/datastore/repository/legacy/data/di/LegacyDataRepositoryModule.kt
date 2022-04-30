package com.sfeir.school.android.datastore.repository.legacy.data.di

import com.sfeir.school.android.datastore.data.SportSessionListLegacySerializer
import com.sfeir.school.android.datastore.repository.legacy.data.SportSessionFileRepository
import com.sfeir.school.android.datastore.repository.shared.SportSessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LegacyDataRepositoryModule {

	@Provides
	@Singleton
	fun provideSportSessionFileRepository(
		legacySerializer: SportSessionListLegacySerializer,
		coroutineScopeIO: CoroutineScope,
	): SportSessionRepository {
		return SportSessionFileRepository(
			serializer = legacySerializer,
			coroutineScopeIO = coroutineScopeIO
		)
	}
}
