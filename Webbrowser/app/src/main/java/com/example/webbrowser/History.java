package com.example.webbrowser;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class History {
    private String currentUrl;
    private List<String> siteHistory = new ArrayList<String>();
    private ListIterator historyStatus = siteHistory.listIterator();

    public History(String initialUrl) {
        currentUrl = initialUrl;
        historyStatus.add(initialUrl);
        historyStatus.previous();
    }

    public String goBack() throws Exception {
        while (historyStatus.hasPrevious()) {
            String previousUrl = (String) historyStatus.previous();
            if (previousUrl != currentUrl) {
                currentUrl = previousUrl;
                return previousUrl;
            }
        }
        throw new Exception("No previous site available");
    }

    public String goForward() throws Exception {
        while (historyStatus.hasNext()) {
            String nextUrl = (String)historyStatus.next();
            if (nextUrl != currentUrl) {
                currentUrl = nextUrl;
                return nextUrl;
            }
        }
        throw new Exception("No next site available");
    }

    public void addToHistory(String newUrl) {
        if (!newUrl.equals(currentUrl)) {
            // Delete all next pages from history
            if (historyStatus.hasNext()) {
                historyStatus.next();
            }
            while (historyStatus.hasNext()) {
                historyStatus.next();
                historyStatus.remove();
            }
            historyStatus.add(newUrl);
            historyStatus.previous();
        }
        currentUrl = newUrl;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }
}
