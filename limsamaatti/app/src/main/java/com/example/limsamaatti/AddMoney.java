package com.example.limsamaatti;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddMoney extends AppCompatActivity {

    private BottleDispenser dispenser = BottleDispenser.getInstance();
    private TextView balanceView;
    private SeekBar moneyBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        // https://freakycoder.com/android-notes-24-how-to-add-back-button-at-toolbar-941e6577418e
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        balanceView = findViewById(R.id.balanceViewMoney);
        moneyBar = findViewById(R.id.discreteMoneyBar);
        updateUIStatus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void handleMoneyButtons(View view) {
    if (!(view instanceof Button)) {
        return;
    }

    Button button = (Button)view;
    switch (button.getId()) {
        case R.id.cent1Button:
            dispenser.addMoney(0.01f);
            break;
        case R.id.cent2Button:
            dispenser.addMoney(0.02f);
            break;
        case R.id.cent5Button:
            dispenser.addMoney(0.05f);
            break;
        case R.id.cent10Button:
            dispenser.addMoney(0.1f);
            break;
        case R.id.cent50Button:
            dispenser.addMoney(0.5f);
            break;
        case R.id.euro1Button:
            dispenser.addMoney(1.0f);
            break;
        case R.id.euro2Button:
            dispenser.addMoney(2.0f);
            break;
        case R.id.euro5Button:
            dispenser.addMoney(5.0f);
            break;
        case R.id.addMoneyDiscrete:
            float amount;
            amount = moneyBar.getProgress();
            dispenser.addMoney(amount);
            break;
        }
        updateUIStatus();
    }

    private void updateUIStatus() {
        balanceView.setText(String.format("Balance: %.2f€", dispenser.getBalance()));
    }
}
