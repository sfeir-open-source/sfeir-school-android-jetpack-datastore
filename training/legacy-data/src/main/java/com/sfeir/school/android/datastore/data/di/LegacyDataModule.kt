package com.sfeir.school.android.datastore.data.di

import android.content.Context
import com.google.gson.Gson
import com.sfeir.school.android.datastore.data.SportSessionListLegacySerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LegacyDataModule {

	@Provides
	@Singleton
	fun provideSportSessionListSerializer(
		@ApplicationContext context: Context,
		gson: Gson,
		coroutineScopeIO: CoroutineScope
	): SportSessionListLegacySerializer {
		return SportSessionListLegacySerializer(
			context = context,
			gson = gson,
			coroutineScopeIO = coroutineScopeIO
		)
	}
}
