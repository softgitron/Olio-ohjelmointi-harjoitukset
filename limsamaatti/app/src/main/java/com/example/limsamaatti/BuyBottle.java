package com.example.limsamaatti;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class BuyBottle extends AppCompatActivity {

    private BottleDispenser dispenser = BottleDispenser.getInstance();
    private TextView balanceView;
    private Spinner selectBottleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_bottle);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        balanceView = findViewById(R.id.balanceViewBuy);
        selectBottleView = findViewById(R.id.selectBottle);
        updateUIStatus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void updateUIStatus() {
        balanceView.setText(String.format("Balance: %.2f€", dispenser.getBalance()));
    }

    public void buyBottle(View view) {
        // https://mkyong.com/android/android-spinner-drop-down-list-example/
        String bottle = String.valueOf(selectBottleView.getSelectedItem());
        String results = dispenser.buyBottle(bottle);
        Snackbar.make(view, results, Snackbar.LENGTH_SHORT)
                .show();
        updateUIStatus();
    }
}
