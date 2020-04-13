package com.example.limsamaatti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private BottleDispenser dispenser = BottleDispenser.getInstance();
    private TextView balanceView;
    private TextView bottlesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // UI
        balanceView = findViewById(R.id.balanceView);
        bottlesView = findViewById(R.id.bottlesView);

        initializeDispenser();
        updateUIStatus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUIStatus();
    }

    private void initializeDispenser(){
        String hello = dispenser.listBottles();
        if (!dispenser.listBottles().isEmpty()) {
            return;
        }
        Bottle pepsi = new Bottle("Pepsi Max", "Pepsi", 0.3, 0.5, 1.8);
        Bottle pepsiBig = new Bottle("Pepsi Max", "Pepsi", 0.3, 1.5, 2.2);
        Bottle cola = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.1, 0.5, 2.0);
        Bottle colaBig = new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3, 1.5, 2.5);
        Bottle fanta = new Bottle("Fanta Zero", "Fanta", 0.7, 0.5, 1.95);
        dispenser.addBottle(pepsi);
        dispenser.addBottle(pepsiBig);
        dispenser.addBottle(cola);
        dispenser.addBottle(colaBig);
        dispenser.addBottle(fanta);
    }

    private void updateUIStatus() {
        String bottles = dispenser.listBottles();
        bottlesView.setText("Bottles available:\n" + bottles);
        balanceView.setText(String.format("Balance: %.2f€", dispenser.getBalance()));
    }

    public void addMoneyButton(View view) {
        Intent intent = new Intent(this, AddMoney.class);
        startActivity(intent);
    }

    public void buyBottleButton(View view) {
        Intent intent = new Intent(this, BuyBottle.class);
        startActivity(intent);
    }

    public void saveReceiptButton(View view) {
        Intent intent = new Intent(this, SaveReceipt.class);
        startActivity(intent);
    }

    public void takeMoneyOutButton(View view) {
        Snackbar.make(view, dispenser.returnMoney(), Snackbar.LENGTH_SHORT)
                .show();
        updateUIStatus();
    }
}
