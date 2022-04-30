package com.sfeir.school.android.datastore.ui.fragment.sportsessions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfeir.school.android.datastore.data.model.SportSession
import com.sfeir.school.android.datastore.databinding.ItemSportSessionBinding
import java.text.DateFormat
import java.text.SimpleDateFormat

class SportSessionsAdapter :
	ListAdapter<SportSession, SportSessionsAdapter.SportSessionViewHolder>(sportSessionsDiffCallback) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportSessionViewHolder {
		val binding =
			ItemSportSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return SportSessionViewHolder(binding)
	}

	override fun onBindViewHolder(holder: SportSessionViewHolder, position: Int) {
		holder.bind(currentList[position])
	}

	inner class SportSessionViewHolder(private val binding: ItemSportSessionBinding) :
		RecyclerView.ViewHolder(binding.root) {

		private val dateFormat: DateFormat =
			SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)

		@SuppressLint("SetTextI18n")
		fun bind(sportSession: SportSession) {
			with(binding) {
				typeText.text = sportSession.type.name
				distanceText.text = String.format("%.2f km", sportSession.distance / 1000)
				speedText.text = String.format("%.2f km/h", sportSession.speed)
				bpmText.text = "${sportSession.bpm} bpm"
				caloriesText.text = "${sportSession.calories} kcal"
				dateText.text = dateFormat.format(sportSession.startedAt)
				durationText.text = String.format("%.2f min", sportSession.duration / 60.0)
			}
		}
	}
}

private val sportSessionsDiffCallback = object : DiffUtil.ItemCallback<SportSession>() {
	override fun areItemsTheSame(
		oldItem: SportSession,
		newItem: SportSession
	): Boolean {
		return oldItem == newItem
	}

	override fun areContentsTheSame(
		oldItem: SportSession,
		newItem: SportSession
	): Boolean {
		return oldItem.id == newItem.id
	}
}
