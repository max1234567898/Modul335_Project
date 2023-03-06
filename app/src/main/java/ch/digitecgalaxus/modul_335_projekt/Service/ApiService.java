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

public class ApiService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<Double> implementGeocoder(EditText inputFromField) throws IOException {
        List<Double> resultList = new ArrayList<>();
        Geocoder geocoder = new Geocoder(this);
        List<Address> geocode = geocoder.getFromLocationName(String.valueOf(inputFromField), 1);

        Double latitude = geocode.get(0).getLatitude();
        Double longitude = geocode.get(0).getLongitude();

        resultList.add(latitude);
        resultList.add(longitude);
        return resultList;
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
