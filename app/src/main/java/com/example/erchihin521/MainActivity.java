package com.example.erchihin521;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText loginEdTxt;
    private EditText passwordEdTxt;
    private Button loginBtn;
    private Button registrationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialView();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream loginInputStream = null;
                FileInputStream passwordInputStream = null;

                try {
                    loginInputStream = openFileInput("login.txt");
                    passwordInputStream = openFileInput("password.txt");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                InputStreamReader loginStreamReader = new InputStreamReader(loginInputStream);
                InputStreamReader passwordStreamReader = new InputStreamReader(passwordInputStream);

                BufferedReader loginReader = new BufferedReader(loginStreamReader);
                BufferedReader passwordReader = new BufferedReader(passwordStreamReader);

                if (isInputFieldEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.error_input_empty, Toast.LENGTH_SHORT).show();
                } else {
                    String enterLogin = loginEdTxt.getText().toString();
                    String enterPassword = passwordEdTxt.getText().toString();
                    try {
                        String userLogin = loginReader.readLine();
                        String userPassword = passwordReader.readLine();
                        if (userLogin == null || userPassword == null) {
                            Toast.makeText(MainActivity.this, R.string.toast_not_registered, Toast.LENGTH_SHORT).show();
                        } else {
                            if (userLogin.equals(enterLogin) && userPassword.equals(enterPassword)) {
                                Toast.makeText(MainActivity.this, R.string.toast_enter_ok, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, R.string.toast_enter_failed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream loginOutputStream = null;
                FileOutputStream passwordOutputStream = null;

                try {
                    loginOutputStream = openFileOutput("login.txt", MODE_PRIVATE);
                    passwordOutputStream = openFileOutput("password.txt", MODE_PRIVATE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                OutputStreamWriter loginOutputStreamWriter = new OutputStreamWriter(loginOutputStream);
                OutputStreamWriter passwordOutputStreamWriter = new OutputStreamWriter(passwordOutputStream);
                BufferedWriter loginBw = new BufferedWriter(loginOutputStreamWriter);
                BufferedWriter passwordBw = new BufferedWriter(passwordOutputStreamWriter);

                if (isInputFieldEmpty()) {
                    Toast.makeText(MainActivity.this, R.string.error_input_empty, Toast.LENGTH_SHORT).show();
                } else {
                    String login = loginEdTxt.getText().toString();
                    String password = passwordEdTxt.getText().toString();
                    try {
                        loginBw.write(login);
                        passwordBw.write(password);
                        Toast.makeText(MainActivity.this, R.string.toast_registration_ok, Toast.LENGTH_SHORT).show();
                        loginEdTxt.setText("");
                        passwordEdTxt.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        loginBw.close();
                        passwordBw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public boolean isInputFieldEmpty() {
        if (loginEdTxt.getText().toString().equals("") || passwordEdTxt.getText().toString().equals("")) {
            return true;
        }
        return false;
    }

    public boolean isNoSaveData() {
        return false;
    }

    public void initialView() {
        loginEdTxt = findViewById(R.id.edtxt_login);
        passwordEdTxt = findViewById(R.id.edtxt_password);
        loginBtn = findViewById(R.id.btn_login);
        registrationBtn = findViewById(R.id.btn_registration);
    }
}