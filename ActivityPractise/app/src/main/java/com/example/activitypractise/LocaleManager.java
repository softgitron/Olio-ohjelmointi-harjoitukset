package com.example.activitypractise;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.LocaleList;

import java.util.Locale;

public class LocaleManager {
    public static Context updateLocale(Context context) {
        Locale locale = Locale.forLanguageTag("fi-FI");
        SettingsStore setting = SettingsStore.getInstance();
        switch (setting.getLanguage().getValue()) {
            case 0:
                locale = Locale.forLanguageTag("en-US");
                break;
            case 1:
                locale = Locale.forLanguageTag("fi-FI");
                break;
        }
        // https://proandroiddev.com/change-language-programmatically-at-runtime-on-android-5e6bc15c758
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        LocaleList localeList = new LocaleList(locale);
        localeList.setDefault(localeList);
        config.setLocales(localeList);
        context = context.createConfigurationContext(config);
        return context;
    }
}
