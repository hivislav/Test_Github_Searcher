package com.hivislav.testgithubsearcher.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hivislav.testgithubsearcher.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TestGithubSearcher)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}