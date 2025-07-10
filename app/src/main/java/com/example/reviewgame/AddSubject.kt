package com.example.reviewgame

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class AddSubject : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_theme)

        val addThemeSubject: Button = findViewById(R.id.saveSubject)
        val subjectType: TextInputEditText = findViewById(R.id.subjectType)

    }
}