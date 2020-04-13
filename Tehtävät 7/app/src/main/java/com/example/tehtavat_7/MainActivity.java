package com.example.tehtavat_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView text;
    TextView updateText;
    EditText updateField;
    EditText updateByEnter;
    EditText fileName;
    EditText fileContent;
    Context context = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.helloText);
        updateText = findViewById(R.id.updateText);
        updateField = findViewById(R.id.enterText);
        updateByEnter = findViewById(R.id.updateByEnter);

        // https://stackoverflow.com/questions/1489852/android-handle-enter-in-an-edittext
        updateByEnter.setOnEditorActionListener(enterListener);
        fileName = findViewById(R.id.fileName);
        fileContent = findViewById(R.id.fileContent);
        context = MainActivity.this;
    }

    public void helloWorldButton(View view) {
        System.out.println("Hello World!");
    }

    public void changeTextField(View view) {
        text.setText("Hello World!");
    }

    public void updateTextBox(View view) {
        String text = updateField.getText().toString();
        updateText.setText(text);
    }

    public void loadFile(View view) {
        String name = fileName.getText().toString();
        String content = "";
        try {
            InputStream ins = context.openFileInput(name);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));
            String line;
            while ((line = br.readLine()) != null) {
                content += line;
            }
            ins.close();
            fileContent.setText(content);
        } catch (IOException e) {
            Snackbar.make(view, "File not found or another file error.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    public void saveFile(View view) {
        String name = fileName.getText().toString();
        String content = fileContent.getText().toString();
        try {
            OutputStreamWriter ows = new OutputStreamWriter(context.openFileOutput(name, Context.MODE_PRIVATE));
            ows.write(content);
            Snackbar.make(view, "File written.", Snackbar.LENGTH_SHORT)
                    .show();
            ows.close();
        } catch (IOException e) {
            // https://material.io/develop/android/components/snackbar/
            Snackbar.make(view, "Error during file write", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    TextView.OnEditorActionListener enterListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_NULL || actionId == EditorInfo.IME_ACTION_DONE) {
                String text = updateByEnter.getText().toString();
                updateText.setText(text);
                // https://stackoverflow.com/questions/41422954/setoneditoractionlistener-not-working-with-soft-keyboard-submit-button-but-does
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return true;
        }
    };
}
