package ch.digitecgalaxus.modul_335_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weatheroutput);

        Intent intent = getIntent();
        String name = intent.getStringExtra("location");
        System.out.println(name);
        TextView nameField = findViewById(R.id.cityname);
        nameField.setText(name);
    }

}
