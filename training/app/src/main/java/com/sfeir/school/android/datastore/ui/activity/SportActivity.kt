package com.sfeir.school.android.datastore.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sfeir.school.android.datastore.databinding.ActivitySportBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SportActivity : AppCompatActivity() {

    private val binding: ActivitySportBinding by lazy { ActivitySportBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
