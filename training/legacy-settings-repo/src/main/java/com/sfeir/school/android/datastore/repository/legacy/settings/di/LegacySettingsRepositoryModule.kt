package com.sfeir.school.android.datastore.repository.legacy.settings.di

import android.content.SharedPreferences
import com.sfeir.school.android.datastore.repository.legacy.settings.SettingsSharedPrefsRepository
import com.sfeir.school.android.datastore.repository.shared.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LegacySettingsRepositoryModule {

	@Provides
	@Singleton
	fun provideSportSessionFiltersSharedPreferencesRepository(
		sharedPrefs: SharedPreferences,
		coroutineScopeIO: CoroutineScope
	): SettingsRepository {
		return SettingsSharedPrefsRepository(
			sharedPrefs = sharedPrefs,
			coroutineScopeIO = coroutineScopeIO
		)
	}
}
