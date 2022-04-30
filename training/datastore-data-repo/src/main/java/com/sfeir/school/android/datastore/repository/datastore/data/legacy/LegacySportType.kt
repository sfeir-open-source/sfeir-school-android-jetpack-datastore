package com.sfeir.school.android.datastore.repository.datastore.data.legacy

import com.google.gson.annotations.SerializedName

enum class LegacySportType {
	@SerializedName("WALKING")
	WALKING,

	@SerializedName("RUNNING")
	RUNNING,

	@SerializedName("BIKING")
	BIKING,

	@SerializedName("SWIMMING")
	SWIMMING;
}
