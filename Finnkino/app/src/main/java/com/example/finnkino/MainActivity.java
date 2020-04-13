package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AsyncInterface {

    View rootView;
    private MovieTheaters movieTheaters = MovieTheaters.getInstance();
    private Movies movies = Movies.getInstance();
    private EditText dateText;
    private EditText timeView;
    private EditText startTimeView;
    private EditText endTimeView;
    private EditText titleText;
    private Spinner theatersSpinner;

    private RecyclerView movieView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = findViewById(android.R.id.content);

        movieTheaters.registerEventListener(this);
        movies.registerEventListener(this);
        movieTheaters.initTheathers();

        dateText = findViewById(R.id.dateText);
        startTimeView = findViewById(R.id.startTimeView);
        timeView = startTimeView;
        endTimeView = findViewById(R.id.endTimeView);
        titleText = findViewById(R.id.titleText);
        theatersSpinner = findViewById(R.id.theatersSpinner);

        // Recycler view stuff
        // https://developer.android.com/guide/topics/ui/layout/recyclerview
        // Some tips taken from
        // https://github.com/android/views-widgets-samples/tree/master/RecyclerView
        // https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
        movieView = findViewById(R.id.movieView);
        movieView.setNestedScrollingEnabled(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        movieView.setLayoutManager(layoutManager);

        // Set decoration to Recycler view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(movieView.getContext(), LinearLayoutManager.VERTICAL);
        movieView.addItemDecoration(dividerItemDecoration);

        // specify an adapter (see also next example)
        mAdapter = new MovieAdapter(movies.getMovies());
        movieView.setAdapter(mAdapter);

        resetFields();
    }

    public void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void dateFieldClick(View view) {
        showDatePickerDialog();
    }

    public void startTimeFieldClick(View view) {
        timeView = startTimeView;
        showTimePickerDialog();
    }

    public void endTimeFieldClick(View view) {
        timeView = endTimeView;
        showTimePickerDialog();
    }

    public void clearButton(View view) {
        resetFields();
    }

    public void searchButton(View view) {
        String theater = theatersSpinner.getItemAtPosition(theatersSpinner.getSelectedItemPosition()).toString();
        Integer theaterId = movieTheaters.getMovieTheatherId(theater);
        String date = dateText.getText().toString();
        String startTime = startTimeView.getText().toString();
        String endTime = endTimeView.getText().toString();
        String title = titleText.getText().toString();

        SearchParameters parameters = new SearchParameters(theaterId, date, startTime, endTime, title);
        movies.getMovies(parameters);
    }

    private void resetFields() {
        // Set default date
        // https://stackoverflow.com/questions/8654990/how-can-i-get-current-date-in-android
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat newFormat = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = newFormat.format(date);
        dateText.setText(formattedDate);
        startTimeView.setText("");
        endTimeView.setText("");
        titleText.setText("");
        theatersSpinner.setSelection(0);
        movies.resetMovies();
        movieView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onAsyncComplete(AsyncType type, AsyncStatus status, String args) {
        switch (type) {
            case THEATHERS:
                if (status == AsyncStatus.OK) {
                    // Update theatre list
                    String[] theaters = movieTheaters.getTheaterNames();
                    // https://stackoverflow.com/questions/5241660/how-to-add-items-to-a-spinner-in-android
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_spinner_item, theaters);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    theatersSpinner.setAdapter(adapter);
                }
                break;
            case MOVIES:
                if (status == AsyncStatus.OK) {
                    Snackbar.make(rootView, "Search completed succefully", Snackbar.LENGTH_SHORT).show();
                    movieView.getAdapter().notifyDataSetChanged();
                }
                break;
            case DATE:
                if (status == AsyncStatus.OK) {
                    dateText.setText(args);
                }
                break;
            case TIME:
                if (status == AsyncStatus.OK) {
                    timeView.setText(args);
                }
                break;
        }
    }
}

