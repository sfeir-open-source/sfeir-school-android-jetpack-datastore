package com.sfeir.school.android.datastore.data.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.sfeir.school.android.datastore.data.model.SportSession
import com.sfeir.school.android.datastore.data.model.SportSessionList

import java.io.InputStream
import java.io.OutputStream

object SportSessionListDataStoreSerializer : Serializer<List<SportSession>> {

	override val defaultValue: List<SportSession> = SportSessionList.getDefaultInstance().sportSessionsList

	@Suppress("BlockingMethodInNonBlockingContext")
	override suspend fun readFrom(input: InputStream): List<SportSession> {
		try {
			return SportSessionList.parseFrom(input).sportSessionsList
		} catch (exception: InvalidProtocolBufferException) {
			throw CorruptionException("Cannot read proto.", exception)
		}
	}

	@Suppress("BlockingMethodInNonBlockingContext")
	override suspend fun writeTo(t: List<SportSession>, output: OutputStream) {
		val sportSessionList: SportSessionList = SportSessionList.newBuilder()
			.addAllSportSessions(t)
			.build()

		sportSessionList.writeTo(output)
	}
}
