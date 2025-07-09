package com.example.reviewgame;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.reviewgame.Classes.CounterTime;

public class MainPage extends AppCompatActivity {
    //Create a laucher to request Permission( lauch a requestion if the permission is or not is granted )
    //In that first line it create a laucher and said that laucher is a permission request
    private final ActivityResultLauncher<String> REQUEST_PERMITION_LAUCHER = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    //If the permission is granted show a menssage to the user
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    //ELse show a menssage too
                    Toast.makeText(this, "Permission denied. The counter cannot show notification.", Toast.LENGTH_SHORT).show();
                }
    });


    //Variables

    private boolean isColorChanged = false;

    private ColorStateList defaultButtonColor;

    //END Variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_page);
        //Calls ask for permition method
        requestNotificationPermission();


        //OBJECTS

        Button start_Review = findViewById(R.id.StartReviewButton);

        //END OBJECTS


        // START BUTTON CODE

        defaultButtonColor = start_Review.getBackgroundTintList();

        // Change button color when click
        // When click in the button
        start_Review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent asks Android, and Android asks CounterTime to execute its routine.
                //And that routine is start the count and update notification bar every second
                Intent serviceCounter = new Intent(MainPage.this, CounterTime.class);
                if(!isColorChanged) {
                    start_Review.setText("Stop Review");
                    int colorWhenClick = Color.parseColor("#FF0000");
                    start_Review.setBackgroundTintList(ColorStateList.valueOf(colorWhenClick));
                    isColorChanged = true;
                    ContextCompat.startForegroundService(MainPage.this, serviceCounter);
                }else{
                    start_Review.setText("Start Review");
                    start_Review.setBackgroundTintList(defaultButtonColor);
                    isColorChanged = false;
                    stopService(serviceCounter);
                }
            }
        });

        //END START BUTTON CODE

    }



    //ASK FOR PERMITION TO USE NOTIFICATION
    private void requestNotificationPermission() {
        //Check if android version needs to accept permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            //Check the permission status and see if the result is different of Permission granted ( permissao aceita )
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                //Calls the request_permission_launcher
                REQUEST_PERMITION_LAUCHER.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

}



// REQUEST_PERMITION_LAUCHER Declara o "agente" ou "gerenciador" (`ActivityResultLauncher`) que vai cuidar do processo de pedir uma permissão e receber a resposta.

// No requestNotificationPermission o .lauch e o metodo que de fato dispara ou "lança" a ação, mostrando a caixa de diálogo de permissão para o usuário.

// Passa como argumento do launch a permissão exata que queremos pedir. A classe 'Manifest' contém as constantes com os nomes oficiais das permissões listadas no AndroidManifest.xml.

//O registerFOrActivityResult

// Prepara o lançador com um "contrato" e um "callback".
// O Contrato (new ActivityResultContracts.RequestPermission()): Define que o trabalho a ser feito é pedir uma única permissão.
// O Callback (isGranted -> { ... }): É o bloco de código que será executado com o resultado (true ou false) DEPOIS que o usuário responder ao pedido.

// Contrato no register e o que ele pede o callback e o que faz com a resposta do contrato.