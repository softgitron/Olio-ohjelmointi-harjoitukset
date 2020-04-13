package com.example.activitypractise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Locale;

public class SettingsStore {
    private static final SettingsStore instance = new SettingsStore();
    private String font = "open_sans";
    private Integer fontSize = 18;
    private Boolean bold = false;
    private Boolean italic = false;
    private static MutableLiveData<Boolean> secondFieldEnabled;
    private static MutableLiveData<String> displayText;
    private static MutableLiveData<Integer> language;

    private SettingsStore() {
        secondFieldEnabled = new MutableLiveData<>();
        displayText = new MutableLiveData<>();
        language = new MutableLiveData<>();

        secondFieldEnabled.setValue(true);
        displayText.setValue("");

        String languageCode = Locale.getDefault().getLanguage();
        if (languageCode.equals("en")) {
            setLanguage(0);
        } else if (languageCode.equals("fi")) {
            setLanguage(1);
        } else {
            setLanguage(0);
        }
    }

    public static SettingsStore getInstance(){
        return instance;
    }

    public String getFontString() {
        String settingsString = font;
        if (bold) {
            settingsString += "_bold";
        }
        if (italic) {
            settingsString += "_italic";
        }
        return settingsString;
    }

    public void setFont(String font) { this.font = font; }
    public void setFontSize(Integer fontSize) { this.fontSize = fontSize; }
    public void setBold(Boolean bold) { this.bold = bold; }
    public void setItalic(Boolean italic) { this.italic = italic; }
    public void setSecondFieldEnabled(Boolean enabled) { secondFieldEnabled.setValue(enabled); }
    public void setDisplayText(String displayText) { this.displayText.setValue(displayText); }
    public void setLanguage(Integer language) {
        if (this.language.getValue() != language) {
            this.language.setValue(language);
        }
    }
    public String getFont() { return font; }
    public Integer getFontSize() { return fontSize; }
    public Boolean getBold() { return bold; }
    public Boolean getItalic() { return italic; }
    public LiveData<Boolean> getSecondFieldEnabled() { return secondFieldEnabled; }
    public LiveData<String> getDisplayText() { return displayText; }
    public LiveData<Integer> getLanguage() { return language; }
}
