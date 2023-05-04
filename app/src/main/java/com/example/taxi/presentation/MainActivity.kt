package com.example.taxi.presentation

import com.example.taxi.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.taxi.presentation.fragment.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

