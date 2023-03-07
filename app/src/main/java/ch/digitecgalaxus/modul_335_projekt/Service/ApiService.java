package ch.digitecgalaxus.modul_335_projekt.Service;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.IBinder;
import androidx.annotation.Nullable;
import java.io.IOException;
import java.util.List;

import ch.digitecgalaxus.modul_335_projekt.WeatherActivity;

public class ApiService extends Service {
    public static final String LongAndAti = "LongAndAti";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(LongAndAti)) {
            Geocoder geocoder = new Geocoder(this);
            String inputFromField = intent.getStringExtra("location");

            List<Address> geocode = null;
            try {
                geocode = geocoder.getFromLocationName(inputFromField, 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Double latitude = geocode.get(0).getLatitude();
            Double longitude = geocode.get(0).getLongitude();

            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(WeatherActivity.LongAndAti);
            broadcastIntent.putExtra("longitude", longitude);
            broadcastIntent.putExtra("latitude", latitude);
            sendBroadcast(broadcastIntent);
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
