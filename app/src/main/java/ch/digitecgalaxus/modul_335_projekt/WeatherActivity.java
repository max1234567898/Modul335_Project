package ch.digitecgalaxus.modul_335_projekt;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import ch.digitecgalaxus.modul_335_projekt.Service.ApiService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    public static final String LongAndAti = "LongAndAti";

    private OkHttpClient client;

    private Double longitude;

    private Double latitude;

    private Response response;

    private String[] responseSplit;

    private TextView cityName;
    private TextView temperature;
    private String input;

    private JSONObject json;

    private String result;

    private String test = "\\{";





    private BroadcastReceiver longAndAtiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             longitude = intent.getDoubleExtra("longitude", 0);
             latitude = intent.getDoubleExtra("latitude", 0);

             temperature = findViewById(R.id.temperature);
            String Url = "https://api.open-meteo.com/v1/forecast?latitude="+latitude+"&longitude="+longitude+"&current_weather=true";
            response = createCall(Url);
            try {
                responseSplit = response.body().string().split(",");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String[] splitResult = responseSplit[7].split(test);
            String temp = splitResult[1];
            String  test2 = "\"";
            result = temp.replaceAll(test2, "");


            temperature.setText(result+" Grad Celcius");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weatheroutput);

        cityName = findViewById(R.id.cityname);

        Intent intent = getIntent();
        String location = intent.getStringExtra("input");

        cityName.setText(location);

        IntentFilter filter = new IntentFilter(LongAndAti);
        registerReceiver(longAndAtiReceiver, filter);
        Intent bmiIntent = new Intent(this, ApiService.class);
        bmiIntent.setAction(ApiService.LongAndAti);
        bmiIntent.putExtra("location", location);
        startService(bmiIntent);
    }

    private Response createCall(String url) {

        this.client = new OkHttpClient();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Response> res = executor.submit(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                return client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        try{
            return res.get();
        } catch (ExecutionException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}


