package com.sfeir.school.android.datastore.base.model

enum class SportTypeFilter {
	NONE, WALKING, RUNNING, BIKING, SWIMMING;

	companion object {
		fun getDefaultValue(): SportTypeFilter = NONE
	}
}
