package com.example.webbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.webbrowser.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    public static final String INDEX_URL = "file:///android_asset/index.html";
    View rootView;
    // https://developer.android.com/topic/libraries/view-binding#java
    // https://medium.com/androiddevelopers/use-view-binding-to-replace-findviewbyid-c83942471fc
    private ActivityMainBinding binding;
    private WebView webView;
    private History history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        rootView = findViewById(android.R.id.content);
        binding.urlInput.setOnEditorActionListener(enterListener);

        webView = binding.webView;
        webView.getSettings().setJavaScriptEnabled(true);
        // https://stackoverflow.com/questions/3181843/how-can-i-check-from-android-webview-if-a-page-is-a-404-page-not-found
        webView.setWebViewClient(new WebViewClient() {
            @Override
            // https://developer.android.com/reference/android/webkit/WebViewClient
            public void onReceivedError(WebView view, WebResourceRequest resourceRequest, WebResourceError webResourceError)  {
                if (webResourceError.getErrorCode() == WebViewClient.ERROR_HOST_LOOKUP || webResourceError.getErrorCode() == WebViewClient.ERROR_TIMEOUT ) {
                    String url = webView.getUrl();
                    if (url.startsWith("https://")) {
                        url = "http://" + url.substring(8);
                        webView.loadUrl(url);
                    } else {
                        Snackbar.make(rootView, "Page could not be found!", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
        history = new History(INDEX_URL);
        webView.loadUrl(INDEX_URL);
    }

    public void handleRefresh(View view) {
        webView.reload();
    }

    public void handleBack(View view) {
        try {
            String previousUrl = history.goBack();
            webView.loadUrl(previousUrl);
            updateCurrentUrl(previousUrl);
        } catch (Exception e) {
            Snackbar.make(rootView, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void handleForward(View view) {
        try {
            String nextUrl = history.goForward();
            webView.loadUrl(nextUrl);
            updateCurrentUrl(nextUrl);
        } catch (Exception e) {
            Snackbar.make(rootView, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void handleShoutOutButton(View view) {
        webView.evaluateJavascript("javascript:shoutOut()", null);
    }

    public void handleInitializeButton(View view) {
        webView.evaluateJavascript("javascript:initialize()", null);
    }

    TextView.OnEditorActionListener enterListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_NULL || actionId == EditorInfo.IME_ACTION_DONE) {
                String url = binding.urlInput.getText().toString();
                changeUrl(url);
                // https://stackoverflow.com/questions/41422954/setoneditoractionlistener-not-working-with-soft-keyboard-submit-button-but-does
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            return true;
        }
    };

    private void updateCurrentUrl(String newUrl) {
        if (newUrl != INDEX_URL) {
            binding.urlInput.setText(newUrl);
        } else {
            binding.urlInput.setText("index.html");
        }
    }

    private void changeUrl(String newUrl) {
        binding.shoutOutButton.setVisibility(View.GONE);
        binding.initializeButton.setVisibility(View.GONE);
        if (newUrl.equals("index.html")) {
            newUrl = INDEX_URL;
            binding.shoutOutButton.setVisibility(View.VISIBLE);
            binding.initializeButton.setVisibility(View.VISIBLE);
        }
        else if (!(newUrl.startsWith("https://") || newUrl.startsWith("http://"))) {
            newUrl = "https://" + newUrl;
        }
        history.addToHistory(newUrl);
        updateCurrentUrl(newUrl);
        webView.loadUrl(newUrl);
    }
}
