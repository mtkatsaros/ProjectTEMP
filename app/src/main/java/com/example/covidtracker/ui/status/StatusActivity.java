package com.example.covidtracker.ui.status;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.example.covidtracker.R;
import com.example.covidtracker.ui.exposure.ExposureActivity;
import com.example.covidtracker.ui.map.MapActivity;

import androidx.appcompat.app.AppCompatActivity;

public class StatusActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);

        Button button = (Button) findViewById(R.id.button_sta);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StatusActivity.this, MapActivity.class));
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.positive:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.negative:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
}
