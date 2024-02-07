package com.example.noteframe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteframe.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}