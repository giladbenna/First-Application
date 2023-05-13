package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;

public class LoginPage extends AppCompatActivity {
    public String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        movingFromLoginPage();
    }

    private void movingFromLoginPage(){
        EditText editText = findViewById(R.id.editTextName);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String playerName = editText.getText().toString();

                // Start the new activity here
                Intent intent = new Intent(LoginPage.this, OpeningPage.class);
                intent.putExtra("playerName", playerName); // Pass the name as an extra if needed
                startActivity(intent);

                return true; // Return true to consume the event
            }
            return false; // Return false if you want the event to propagate further
        });

    }

}
