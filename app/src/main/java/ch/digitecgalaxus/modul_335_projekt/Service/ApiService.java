package ch.digitecgalaxus.modul_335_projekt.Service;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Binder;
import android.os.IBinder;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.digitecgalaxus.modul_335_projekt.WeatherActivity;

public class ApiService extends Service {
    public static final String LongAndAti = "LongAndAti";

    public List<Double> implementGeocoder(EditText inputFromField) throws IOException {
        List<Double> resultList = new ArrayList<>();
        Geocoder geocoder = new Geocoder(this);
        List<Address> geocode = geocoder.getFromLocationName(String.valueOf(inputFromField), 1);

        Double latitude = geocode.get(0).getLatitude();
        Double longitude = geocode.get(0).getLongitude();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(WeatherActivity.LongAndAti);
        broadcastIntent.putExtra("longitude", longitude);
        broadcastIntent.putExtra("latitude", latitude);
        sendBroadcast(broadcastIntent);

        resultList.add(latitude);
        resultList.add(longitude);
        return resultList;
    }

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

    // Binder given to clients
    private final IBinder binder = new LocalBinder();
    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public ApiService getService() {
            // Return this instance of LocalService so clients can call public methods
            return ApiService.this;
        }
    }
    public ApiService() {
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

}
