package com.sfeir.school.android.datastore.base.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sfeir.school.android.datastore.base.serializer.gson.DateTypeAdapter
import com.sfeir.school.android.datastore.base.util.FileNameConst
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

	@Provides
	@Singleton
	@OptIn(DelicateCoroutinesApi::class)
	fun provideCoroutineScopeIO(): CoroutineScope = GlobalScope + Dispatchers.IO

	@Provides
	@Singleton
	fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
		return context.getSharedPreferences(
			FileNameConst.SHARED_PREFS_FILE_NAME,
			Context.MODE_PRIVATE
		)
	}

	@Provides
	@Singleton
	fun provideGson(): Gson {
		return GsonBuilder()
			.registerTypeAdapter(Date::class.java, DateTypeAdapter)
			.create()
	}
}
