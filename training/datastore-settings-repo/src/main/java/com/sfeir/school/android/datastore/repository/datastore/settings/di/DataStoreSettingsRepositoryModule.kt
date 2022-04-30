package com.sfeir.school.android.datastore.repository.datastore.settings.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.sfeir.school.android.datastore.base.util.FileNameConst
import com.sfeir.school.android.datastore.repository.datastore.settings.SettingsPreferencesDataStoreRepository
import com.sfeir.school.android.datastore.repository.shared.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreSettingsRepositoryModule {

	@Provides
	@Singleton
	fun providePreferencesDataStore(
		@ApplicationContext context: Context,
		oldSharedPrefs: SharedPreferences
	): DataStore<Preferences> {
		return PreferenceDataStoreFactory.create(
			produceFile = { context.preferencesDataStoreFile(FileNameConst.PREFS_DATASTORE_FILE_NAME) },
			migrations = listOf(
				SharedPreferencesMigration(
					produceSharedPreferences = { oldSharedPrefs }
				)
			),
			corruptionHandler = ReplaceFileCorruptionHandler(
				produceNewData = { emptyPreferences() }
			)
		)
	}

	@Provides
	@Singleton
	fun provideSettingsPreferencesDataStoreRepository(
		preferencesDataStore: DataStore<Preferences>,
		coroutineScopeIO: CoroutineScope
	): SettingsRepository {
		return SettingsPreferencesDataStoreRepository(
			prefsDataStore = preferencesDataStore,
			coroutineScopeIO = coroutineScopeIO
		)
	}
}
