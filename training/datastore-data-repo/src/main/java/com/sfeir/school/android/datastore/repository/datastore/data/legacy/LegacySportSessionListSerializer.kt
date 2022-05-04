package com.sfeir.school.android.datastore.repository.datastore.data.legacy

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sfeir.school.android.datastore.base.util.FileNameConst.SPORT_SESSIONS_FILE_NAME
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File
import java.lang.reflect.Type

class LegacySportSessionListSerializer(
  context: Context,
  private val gson: Gson
) {
  private val mutex: Mutex = Mutex()

  private val file: File = File(context.cacheDir, SPORT_SESSIONS_FILE_NAME)

  suspend fun read(): List<LegacySportSession> {
    if (!file.exists()) return emptyList()

    mutex.withLock {
      val json: String = file.readText()

      val type: Type = object : TypeToken<List<LegacySportSession>>() {}.type

      return gson.fromJson(json, type)
    }
  }

  suspend fun clear() {
    mutex.withLock {
      file.delete()
    }
  }
}
