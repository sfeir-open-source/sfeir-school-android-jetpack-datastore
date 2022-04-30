package com.sfeir.school.android.datastore.ui.fragment.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.RangeSlider
import com.sfeir.school.android.datastore.base.model.SportSessionSort
import com.sfeir.school.android.datastore.base.model.SportTypeFilter
import com.sfeir.school.android.datastore.base.util.DistanceRangeConst
import com.sfeir.school.android.datastore.base.util.FloatRange
import com.sfeir.school.android.datastore.databinding.FragmentSettingsBinding
import com.sfeir.school.android.datastore.ui.fragment.base.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment : ViewBindingFragment<FragmentSettingsBinding>() {

	private val viewModel: SettingsViewModel by viewModels()

	private val spinnerListener = object : AdapterView.OnItemSelectedListener {
		override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
			when (parent) {
				binding.filterBySportTypeField -> {
					val selectedSportTypeFilter: SportTypeFilter = SportTypeFilter.values()[pos]

					viewModel.setFilterBySportType(selectedSportTypeFilter)
				}
				binding.sortByField -> {
					val selectedSortBy: SportSessionSort = SportSessionSort.values()[pos]

					viewModel.setSortBy(selectedSortBy)
				}
			}
		}

		override fun onNothingSelected(parent: AdapterView<*>) {
			// No-op
		}
	}

	private val rangeSliderListener = object : RangeSlider.OnChangeListener {
		@SuppressLint("RestrictedApi")
		override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
			if (!fromUser) return

			when (slider) {
				binding.filterByDistanceRangeField -> {
					viewModel.setFilterByDistanceRange(
						FloatRange(
							slider.values[0],
							slider.values[1]
						)
					)
				}
			}
		}
	}

	override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentSettingsBinding.inflate(inflater, container, false)

	@SuppressLint("SetTextI18n")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		var optionsList: List<String> = SportTypeFilter.values()
			.map { it.name }
		var arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
			/* context */ requireContext(),
			/* resource */ android.R.layout.simple_spinner_item,
			/* objects */ optionsList
		).apply {
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		}
		binding.filterBySportTypeField.apply {
			adapter = arrayAdapter
			onItemSelectedListener = spinnerListener
		}

		binding.filterByDistanceRangeField.apply {
			valueFrom = DistanceRangeConst.MIN_VALUE
			valueTo = DistanceRangeConst.MAX_VALUE
			stepSize = DistanceRangeConst.STEP_SIZE
			setMinSeparationValue(DistanceRangeConst.MIN_SEPARATION_VALUE)
			addOnChangeListener(rangeSliderListener)
		}

		optionsList = SportSessionSort.values()
			.map { it.name }
			.toList()
		arrayAdapter = ArrayAdapter(
			/* context */ requireContext(),
			/* resource */ android.R.layout.simple_spinner_item,
			/* objects */ optionsList
		).apply {
			setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		}
		binding.sortByField.apply {
			adapter = arrayAdapter
			onItemSelectedListener = spinnerListener
		}

		binding.isSortDescendingField.setOnCheckedChangeListener { _, value ->
			viewModel.setIsSortDescending(value)
		}

		binding.backButton.setOnClickListener {
			findNavController().popBackStack()
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.getFilterBySportType().collect { sportTypeFilter ->
					binding.filterBySportTypeField.setSelection(sportTypeFilter.ordinal)
				}
			}
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.getFilterByDistanceRange().collect { distanceRange ->
					binding.filterByDistanceRangeLabel.text =
						"${distanceRange.min} - ${distanceRange.max} m"

					binding.filterByDistanceRangeField.setValues(
						distanceRange.min,
						distanceRange.max
					)
				}
			}
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.getSortBy().collect { sportSessionSort ->
					binding.sortByField.setSelection(sportSessionSort.ordinal)
				}
			}
		}

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.getIsSortDescending().collect { isSortDescending ->
					binding.isSortDescendingField.isChecked = isSortDescending
				}
			}
		}
	}
}
