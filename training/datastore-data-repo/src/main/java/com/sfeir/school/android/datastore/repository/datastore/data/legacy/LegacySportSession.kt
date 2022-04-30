package com.sfeir.school.android.datastore.repository.datastore.data.legacy

import java.util.*

data class LegacySportSession(
	val id: Int,
	val type: LegacySportType,
	val distance: Float, // in meters
	val startedAt: Date,
	val duration: Long, // in seconds
	val speed: Float, // in km/h
	val calories: Int,
	val bpm: Int,
	val place: LegacyGpsCoordinates,
)
