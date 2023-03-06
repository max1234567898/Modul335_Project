package ch.digitecgalaxus.modul_335_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.util.List;

import ch.digitecgalaxus.modul_335_projekt.Service.ApiService;

public class MainActivity extends AppCompatActivity {

    EditText input = null;
    private ApiService apiService;
    private boolean serviceBound;

    private List<Double> latitudeAndLongitude;

    private double latitude;

    private double longitude;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);

        Intent intent = new Intent(MainActivity.this, ApiService.class);
        intent.putExtra("input", input.getText());
        startActivity(intent);

        Intent connIntent = new Intent(this, ApiService.class);
        bindService(connIntent, connection, Context.BIND_AUTO_CREATE);

        try {
            latitudeAndLongitude = apiService.implementGeocoder(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchView(View view) throws IOException {

        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("location", input.getText().toString());
        System.out.println(input.getText());
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        serviceBound = false;
    }
    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ApiService.LocalBinder binder = (ApiService.LocalBinder) service;
            apiService = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            serviceBound = false;
        }
    };
}