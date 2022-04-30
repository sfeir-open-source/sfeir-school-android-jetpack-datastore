package com.sfeir.school.android.datastore.base.serializer.gson

import com.google.gson.*
import timber.log.Timber
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTypeAdapter : JsonSerializer<Date?>, JsonDeserializer<Date?> {

	private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

	override fun serialize(date: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
		val jsonString: String = date?.let { dateFormatter.format(it) } ?: ""

		return JsonPrimitive(jsonString)
	}

	@Throws(JsonParseException::class)
	override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
		val jsonString: String = json.asString

		return try {
			dateFormatter.parse(jsonString)
		} catch (e: ParseException) {
			Timber.e(e, "Failed to parse Date")
			null
		}
	}
}
