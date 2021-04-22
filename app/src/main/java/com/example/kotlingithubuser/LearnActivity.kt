package com.example.kotlingithubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlingithubuser.databinding.ActivityLearnBinding

class LearnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLearnBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.dicoding.com")
    }
}