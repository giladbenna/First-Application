package com.example.myapplication.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Models.ScoreItem;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.PremissionsManager;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

public class LoginPage extends AppCompatActivity {
    private PremissionsManager premissionsManager;

    private Location location;
    public LoginPage() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        premissionsManager = new PremissionsManager(this);
        premissionsManager.setLocationRequest();
        movingFromLoginPage();
    }

    private void movingFromLoginPage(){
        EditText editText = findViewById(R.id.editTextName);
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String playerName = editText.getText().toString();

                location = premissionsManager.getLast_location();
                // Start the new activity here
                Intent intent = new Intent(LoginPage.this, OpeningPage.class);
                intent.putExtra("playerName", playerName); // Pass the name as an extra if needed
                intent.putExtra("location", location); // Pass the name as an extra if needed

                startActivity(intent);

                return true; // Return true to consume the event
            }
            return false; // Return false if you want the event to propagate further
        });

    }

    //onCreate() is called when the when the activity is first created. onStart() is called when the activity is becoming visible to the user.
    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //get last location if there are permissions
            premissionsManager.setLastLocation();
            premissionsManager.checkSettingsAndStartLocationUpdates();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
                } else {
                    ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 10001);
                }
            }
        }
    }

}

