package com.sfeir.school.android.datastore.base.model

enum class SportSessionSort {
	DATE, DISTANCE, DURATION, SPEED, BPM, CALORIES;

	companion object {
		fun getDefaultValue(): SportSessionSort = DATE
	}
}
