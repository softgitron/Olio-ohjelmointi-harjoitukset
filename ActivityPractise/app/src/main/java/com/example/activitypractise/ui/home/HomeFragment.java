package com.example.activitypractise.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.activitypractise.R;
import com.example.activitypractise.SettingsStore;
import com.example.activitypractise.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private View root;
    private SettingsStore settings = SettingsStore.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        settings.getDisplayText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.displayText.setText(getString(R.string.settings_display_text_title) + " " + s);
            }
        });
        settings.getSecondFieldEnabled().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {
                if (!b) {
                    binding.editText.setText(binding.editText2.getText().toString());
                }
                binding.editText2.setEnabled(b);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Typeface typeface = getResources().getFont(R.font.open_sans);;
        switch(settings.getFontString()) {
            case "open_sans":
                typeface = getResources().getFont(R.font.open_sans);
                break;
            case "open_sans_bold":
                typeface = getResources().getFont(R.font.open_sans_bold);
                break;
            case "open_sans_italic":
                typeface = getResources().getFont(R.font.open_sans_italic);
                break;
            case "open_sans_bold_italic":
                typeface = getResources().getFont(R.font.open_sans_bold_italic);
                break;
            case "tinos":
                typeface = getResources().getFont(R.font.tinos);
                break;
            case "tinos_bold":
                typeface = getResources().getFont(R.font.tinos_bold);
                break;
            case "tinos_italic":
                typeface = getResources().getFont(R.font.tinos_italic);
                break;
            case "tinos_bold_italic":
                typeface = getResources().getFont(R.font.tinos_bold_italic);
                break;
        }
        binding.editText.setTypeface(typeface);
        binding.editText.setTextSize(settings.getFontSize());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
