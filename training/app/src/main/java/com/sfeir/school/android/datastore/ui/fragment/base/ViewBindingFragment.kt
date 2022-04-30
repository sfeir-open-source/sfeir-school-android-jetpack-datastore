package com.sfeir.school.android.datastore.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class ViewBindingFragment<T : ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateViewBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
