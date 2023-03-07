package ch.digitecgalaxus.modul_335_projekt;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText input = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = findViewById(R.id.input);
    }

    public void switchView(View view) {

        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
        intent.putExtra("input", input.getText().toString());
        startActivity(intent);
    }
}