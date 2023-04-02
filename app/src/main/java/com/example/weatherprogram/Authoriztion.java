package com.example.weatherprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.DriverManager;

public class Authoriztion extends AppCompatActivity {
    Button btnLogIn;
    EditText Login, Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authoriztion);
        btnLogIn = findViewById(R.id.btnLogIN);
        Login = findViewById(R.id.Login);
        Password = findViewById(R.id.Password);
        btnLogIn.setOnClickListener(view -> {
            String login = Login.getText().toString();
            String password = Password.getText().toString();
            System.out.println("Проверка" + password + " " + login);
            if (login.equals("") || password.equals("")){
                Toast.makeText(Authoriztion.this, "Введите значения", Toast.LENGTH_LONG).show();
            }
            else {
                try (
                        Socket socket = new Socket("10.0.2.2", 8080);
                        BufferedWriter writer =
                                new BufferedWriter(
                                        new OutputStreamWriter(
                                                socket.getOutputStream()
                                        )
                                );
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()
                                )
                        );
                ) {
                    System.out.println("CLient started");
                    String request = "Android";
                    writer.write(request);
                    writer.newLine();
                    writer.flush();
                    System.out.println("Messege sent");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}