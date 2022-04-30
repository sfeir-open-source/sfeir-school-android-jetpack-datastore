package com.sfeir.school.android.datastore.data.model

import java.util.*

data class SportSession(
	val id: Int,
	val type: SportType,
	val distance: Float, // in meters
	val startedAt: Date,
	val duration: Long, // in seconds
	val speed: Float, // in km/h
	val calories: Int,
	val bpm: Int,
	val place: GpsCoordinates,
)
