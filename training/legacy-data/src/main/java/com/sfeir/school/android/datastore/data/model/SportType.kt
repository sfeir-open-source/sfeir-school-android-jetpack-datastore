package com.sfeir.school.android.datastore.data.model

import com.google.gson.annotations.SerializedName

enum class SportType {
	@SerializedName("WALKING")
	WALKING,

	@SerializedName("RUNNING")
	RUNNING,

	@SerializedName("BIKING")
	BIKING,

	@SerializedName("SWIMMING")
	SWIMMING;
}
