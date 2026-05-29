package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import java.text.DecimalFormat;




public class MainActivity extends AppCompatActivity {

    EditText etWeight, etValue;
    Spinner spinnerType;
    Button btnCalculate, btnAbout;

    TextView tvGoldValue, tvUruf, tvPayable, tvZakat;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.menu_share){

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("text/plain");

            shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out my Zakat Gold Calculator App:\n" +
                            "https://github.com/yourusername/ZakatGoldCalculator"
            );

            startActivity(
                    Intent.createChooser(
                            shareIntent,
                            "Share via"
                    )
            );

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setIcon(R.drawable.zg_icon);

            getSupportActionBar().setTitle(" ZG Calculator");

            getSupportActionBar().setDisplayShowHomeEnabled(true);

            getSupportActionBar().setDisplayUseLogoEnabled(false);
        }

        etWeight = findViewById(R.id.etWeight);
        etValue = findViewById(R.id.etValue);
        spinnerType = findViewById(R.id.spinnerType);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnAbout = findViewById(R.id.btnAbout);



        tvGoldValue = findViewById(R.id.tvGoldValue);
        tvUruf = findViewById(R.id.tvUruf);
        tvPayable = findViewById(R.id.tvPayable);
        tvZakat = findViewById(R.id.tvZakat);

        String[] types = {"Keep", "Wear"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                types
        );

        spinnerType.setAdapter(adapter);


        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =
                        new Intent(MainActivity.this,
                                AboutActivity.class);

                startActivity(intent);
            }
        });



        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etWeight.getText().toString().isEmpty() ||
                        etValue.getText().toString().isEmpty()) {

                    Toast.makeText(MainActivity.this,
                            "Please enter all inputs",
                            Toast.LENGTH_SHORT).show();

                    return;
                }

                double weight = Double.parseDouble(
                        etWeight.getText().toString());

                double value = Double.parseDouble(
                        etValue.getText().toString());

                String type =
                        spinnerType.getSelectedItem().toString();

                int uruf;

                if (type.equals("Keep")) {
                    uruf = 85;
                }
                else {
                    uruf = 200;
                }

                double totalGoldValue =
                        weight * value;

                double zakatWeight =
                        weight - uruf;

                if (zakatWeight < 0) {
                    zakatWeight = 0;
                }

                double zakatPayable =
                        zakatWeight * value;

                double totalZakat =
                        zakatPayable * 0.025;

                DecimalFormat df = new DecimalFormat("#,##0.00");

                tvGoldValue.setText(
                        "RM " + df.format(totalGoldValue));

                tvUruf.setText(
                        df.format(zakatWeight) + " gram");

                tvPayable.setText(
                        "RM " + df.format(zakatPayable));

                tvZakat.setText(
                        "RM " + df.format(totalZakat));
            }
        });
    }
}

