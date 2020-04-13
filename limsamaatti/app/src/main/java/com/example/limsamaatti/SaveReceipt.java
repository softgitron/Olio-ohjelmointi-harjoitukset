package com.example.limsamaatti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class SaveReceipt extends AppCompatActivity {

    private static final int CREATE_FILE = 1;

    private BottleDispenser dispenser = BottleDispenser.getInstance();
    View rootView;
    EditText fileName;
    TextView receiptViewer;
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_receipt);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // https://stackoverflow.com/questions/5273436/how-to-get-activitys-content-view
        rootView = findViewById(android.R.id.content);
        fileName = findViewById(R.id.fileName);
        receiptViewer = findViewById(R.id.receiptViewer);
        context = SaveReceipt.this;

        updateUIStatus();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUIStatus();
    }

    private void updateUIStatus() {
        receiptViewer.setText(dispenser.getReceipt());
    }

    // https://developer.android.com/training/data-storage/shared/documents-files
    private void createFile(String name) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/txt");
        intent.putExtra(Intent.EXTRA_TITLE, name);

        startActivityForResult(intent, CREATE_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == CREATE_FILE && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                // Perform operations on the document using its URI.
                saveFile(uri);
                // https://stackoverflow.com/questions/5568874/how-to-extract-the-file-name-from-uri-returned-from-intent-action-get-content/53170119
                String path = uri.getPath();
                int cut = path.lastIndexOf("/");
                if (cut != -1) {
                    String name = path.substring(cut + 1);
                    fileName.setText(name);
                }
            }
        } else {
            Snackbar.make(rootView, "Error during file creation", Snackbar.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, resultData);
    }

    private void saveFile(Uri uri) {
        String content = dispenser.getReceipt();
        try {
            ParcelFileDescriptor txt = getContentResolver().openFileDescriptor(uri, "w");
            FileOutputStream fileOutputStream = new FileOutputStream(txt.getFileDescriptor());
            fileOutputStream.write(content.getBytes());
            // Let the document provider know you're done by closing the stream.
            fileOutputStream.close();
            txt.close();
            Snackbar.make(rootView, "File written successfully!", Snackbar.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Snackbar.make(rootView, "Unknown file error.", Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            Snackbar.make(rootView, "Error while writing file to disk.", Snackbar.LENGTH_SHORT).show();
        }
    }



    public void saveFileClick(View view) {
        createFile(fileName.getText().toString());
/*        String name = fileName.getText().toString();
        String content = dispenser.getReceipt();
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
        }*/
    }
}
