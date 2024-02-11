package com.example.noteframe

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.noteframe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.mainBtn.setOnClickListener {
            val intent = Intent(this@MainActivity,NoteActivity::class.java)
            startActivity(intent)
        }
        setContentView(binding.root)
    }
}