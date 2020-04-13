package com.example.activitypractise.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.activitypractise.R;
import com.example.activitypractise.SettingsStore;
import com.example.activitypractise.databinding.FragmentSettingsBinding;
import com.google.android.material.snackbar.Snackbar;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private View root;
    private SettingsStore settings = SettingsStore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // https://stackoverflow.com/questions/21192386/android-fragment-onclick-button-method
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String font = binding.fontType.getItemAtPosition(binding.fontType.getSelectedItemPosition()).toString();
                Integer fontSize = Integer.parseInt(binding.fontSize.getText().toString());
                Boolean bold = Boolean.parseBoolean(binding.fontIsBold.getItemAtPosition(binding.fontIsBold.getSelectedItemPosition()).toString());
                Boolean italic = Boolean.parseBoolean(binding.fontIsItalic.getItemAtPosition(binding.fontIsItalic.getSelectedItemPosition()).toString());
                Boolean secondFieldIsEnabled = binding.secondFieldIsEnabled.getSelectedItemPosition() == 1 ? true : false;
                String displayText = binding.displayText.getText().toString();
                Integer language = binding.languageSelector.getSelectedItemPosition();
                settings.setFont(font);
                settings.setFontSize(fontSize);
                settings.setBold(bold);
                settings.setItalic(italic);
                settings.setSecondFieldEnabled(secondFieldIsEnabled);
                settings.setDisplayText(displayText);
                settings.setLanguage(language);

                Snackbar.make(v, getString(R.string.snackbar_settings_saved), Snackbar.LENGTH_SHORT).show();
            }
        });
        /* Populate fonts spinner
        // https://developer.android.com/reference/android/graphics/fonts/SystemFonts
        Set<Font> fonts = SystemFonts.getAvailableFonts();
        List<String> fontNames = new ArrayList();
        for (Font font: fonts) {
            fontNames.add(font.toString());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_spinner_item, fontNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fontType.setAdapter(adapter); */
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set values from settings store
        String font = settings.getFont();
        Integer fontSize = settings.getFontSize();
        Boolean bold = settings.getBold();
        Boolean italic = settings.getItalic();
        Boolean secondFieldIsEnabled = settings.getSecondFieldEnabled().getValue();
        String displayText = settings.getDisplayText().getValue();
        Integer language = settings.getLanguage().getValue();
        binding.fontType.setSelection(getSpinnerIndex(binding.fontType, font));
        binding.fontSize.setText(fontSize.toString());
        binding.fontIsBold.setSelection(bold ? 1 : 0);
        binding.fontIsItalic.setSelection(italic ? 1 : 0);
        binding.secondFieldIsEnabled.setSelection(secondFieldIsEnabled ? 1 : 0);
        binding.displayText.setText(displayText);
        binding.languageSelector.setSelection(language);
    }

    // https://stackoverflow.com/questions/8769368/how-to-set-position-in-spinner
    private int getSpinnerIndex(Spinner spinner, String string) {
        for (int item = 0; item < spinner.getCount(); item++) {
            if (spinner.getItemAtPosition(item).equals(string)) {
                return item;
            }
        }
        return 0;
    }
}
