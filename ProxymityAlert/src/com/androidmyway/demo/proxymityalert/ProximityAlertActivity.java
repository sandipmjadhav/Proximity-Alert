package com.androidmyway.demo.proxymityalert;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProximityAlertActivity extends Activity {
    private static final long POINT_RADIUS = 100; // in Meters
    private static final long PROX_ALERT_EXPIRATION = -1; // It will never expire
    private static final String PROX_ALERT_INTENT = "com.androidmyway.demo.ProximityAlert";
    private LocationManager locationManager;
    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private Button addAlertButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.activity_proxymity);

    		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    		latitudeEditText = (EditText) findViewById(R.id.point_latitude);
    		longitudeEditText = (EditText) findViewById(R.id.point_longitude);
    		addAlertButton = (Button) findViewById(R.id.add_alert_button);

    		addAlertButton.setOnClickListener(new OnClickListener() {
                  public void onClick(View v) {
                         addProximityAlert();
                  }
    		});

    }

    private void addProximityAlert() {
           double latitude = Double.parseDouble(latitudeEditText.getText().toString());
           double longitude = Double.parseDouble(longitudeEditText.getText().toString());
           Intent intent = new Intent(PROX_ALERT_INTENT);
           PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
           locationManager.addProximityAlert(
                  latitude, // the latitude of the central point of the alert region
                  longitude, // the longitude of the central point of the alert region
                  POINT_RADIUS, // the radius of the central point of the alert region, in meters
                  PROX_ALERT_EXPIRATION, // time for this proximity alert, in milliseconds, or -1 to indicate no                           expiration
                  proximityIntent // will be used to generate an Intent to fire when entry to or exit from the alert region is detected
           );

           IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
           registerReceiver(new ProximityIntentReceiver(), filter);
           Toast.makeText(getApplicationContext(),"Alert Added",Toast.LENGTH_SHORT).show();
    }
}
