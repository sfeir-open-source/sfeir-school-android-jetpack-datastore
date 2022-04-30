package com.sfeir.school.android.datastore.ui.fragment.sportsessions

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.sfeir.school.android.datastore.BuildConfig
import com.sfeir.school.android.datastore.databinding.FragmentSportSessionsBinding
import com.sfeir.school.android.datastore.ui.fragment.base.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SportSessionsFragment : ViewBindingFragment<FragmentSportSessionsBinding>() {

	private val viewModel: SportSessionsViewModel by viewModels()

	private val sportSessionsAdapter: SportSessionsAdapter by lazy { SportSessionsAdapter() }

	override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
		FragmentSportSessionsBinding.inflate(inflater, container, false)

	@SuppressLint("SetTextI18n")
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		viewLifecycleOwner.lifecycleScope.launch {
			viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.getSportSessions().collect { sportSessions ->
					sportSessionsAdapter.submitList(sportSessions)
					binding.infoText.text = "Flavor: ${BuildConfig.FLAVOR}   /   Count: ${sportSessions.size}"
				}
			}
		}

		with(binding) {
			list.apply {
				addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
				adapter = sportSessionsAdapter
			}

			newButton.setOnClickListener {
				viewModel.addNewRandomSportSession()
			}

			deleteButton.setOnClickListener {
				viewModel.deleteRandomSportSession()
			}

			settingsButton.setOnClickListener {
				findNavController().navigate(
					SportSessionsFragmentDirections.actionSportSessionsToSettings()
				)
			}
		}
	}
}
