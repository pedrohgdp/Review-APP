package com.example.reviewgame

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.reviewgame.Classes.CounterTime

class MainPage : AppCompatActivity() {

    // Launcher to request a permission; it will launch a small screen to accept or deny the permission.
    // Create a final (val) variable to make the request.
    // `registerForActivityResult` is for registering an activity result.
    // `ActivityResultContracts` is a class with contracts, and `RequestPermission` is one of them.
    // We send the name of the permission we want to request, and `RequestPermission` will return a boolean.
    // That boolean is put in the 'isGranted' variable.
    private val REQUEST_PERMITION_LAUCHER: ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            // If the permission is granted, show a toast to the user. If not too
            if (isGranted) {
                Toast.makeText(this, "Permissão concedida!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissão negada. O app não pode mostrar notificações.", Toast.LENGTH_SHORT).show()
            }
        }



    // Variabless to control the button color
    private var isColorChanged = false
    private var defaultButtonColor: ColorStateList? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_page)

        // Calls the method to request the notification permission
        requestNotificationPermission()

        // Buttons
        val startReviewButton: Button = findViewById(R.id.StartReviewButton)
        val addThemeButton: Button = findViewById(R.id.AddTheme)

        // Tooks the normal button color
        defaultButtonColor = startReviewButton.backgroundTintList

        // When the button startReviewButton is clicked
        startReviewButton.setOnClickListener {
            // Creates the intent to start the service
            val serviceCounter = Intent(this, CounterTime::class.java)

            if (!isColorChanged) {
                startReviewButton.text = "Stop Review"
                val colorWhenClick = Color.parseColor("#FF0000")
                startReviewButton.backgroundTintList = ColorStateList.valueOf(colorWhenClick)
                isColorChanged = true
                ContextCompat.startForegroundService(this, serviceCounter)
            } else {
                startReviewButton.text = "Start Review"
                startReviewButton.backgroundTintList = defaultButtonColor
                isColorChanged = false
                stopService(serviceCounter)
            }
        }


        //When addTheme buttom is clicked
        addThemeButton.setOnClickListener {
            val intent = Intent(this, AddSubject::class.java)
            startActivity(intent)
            //setContentView(R.layout.add_theme)
        }

    }

    // Function to request the notification permission.
    private fun requestNotificationPermission() {
        // If your android version need the request
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // We will see if the permission is granted or not
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // If yes we send to laucher permission variable the request that we want
                //Manifest.permission.POST_NOTIFICATIONS will be sent to the RequestPermition
                REQUEST_PERMITION_LAUCHER.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

//ContextCompart é uma classe que fornece retrocompatibilidade, com ele é como um benjamin para tomada
//Tudo que depende de ter retrocompatibilidade vem dele
//PackageManager é o diretorio oficial de aplicativos
//O permission_granted é um inteiro 0 que no sistema android representa que a permissao foi concebida

