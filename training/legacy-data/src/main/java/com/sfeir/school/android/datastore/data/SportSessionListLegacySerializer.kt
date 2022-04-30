package com.sfeir.school.android.datastore.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sfeir.school.android.datastore.base.util.FileNameConst.SPORT_SESSIONS_FILE_NAME
import com.sfeir.school.android.datastore.data.model.SportSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.io.InputStream
import java.lang.reflect.Type

class SportSessionListLegacySerializer(
	context: Context,
	private val gson: Gson,
	coroutineScopeIO: CoroutineScope
) {
	private val mutex: Mutex = Mutex()

	private val file: File = File(context.cacheDir, SPORT_SESSIONS_FILE_NAME).apply {
		coroutineScopeIO.launch {
			mutex.withLock {
				if (!exists()) {
					val jsonInitInputStream: InputStream = context.resources.openRawResource(R.raw.sport_sessions_init)

					writeBytes(jsonInitInputStream.readBytes())
				}
			}
		}
	}

	suspend fun read(): List<SportSession> {
		mutex.withLock {
			val json: String = file.readText()

			val type: Type = object : TypeToken<List<SportSession>>() {}.type

			return gson.fromJson(json, type)
		}
	}

	suspend fun write(list: List<SportSession>) {
		mutex.withLock {
			val json: String = gson.toJson(list)

			file.writeText(json)
		}
	}
}
