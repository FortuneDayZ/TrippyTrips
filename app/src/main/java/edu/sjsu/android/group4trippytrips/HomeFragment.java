package edu.sjsu.android.group4trippytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private Button hotelsButton, flightsButton, activitiesButton;

    public HomeFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Connect buttons
        hotelsButton = view.findViewById(R.id.hotelsButton);
        flightsButton = view.findViewById(R.id.flightsButton);
        activitiesButton = view.findViewById(R.id.activitiesButton);

        // Set button click listeners
        hotelsButton.setOnClickListener(v -> selectCategory(hotelsButton));
        flightsButton.setOnClickListener(v -> selectCategory(flightsButton));
        activitiesButton.setOnClickListener(v -> selectCategory(activitiesButton));

        // Optional: Select Hotels by default at start
        selectCategory(hotelsButton);

        return view;
    }

    private void selectCategory(Button selectedButton) {
        // Reset all buttons to normal
        hotelsButton.setBackgroundResource(R.drawable.category_button_background);
        flightsButton.setBackgroundResource(R.drawable.category_button_background);
        activitiesButton.setBackgroundResource(R.drawable.category_button_background);

        // Set the selected one to special "selected" background
        selectedButton.setBackgroundResource(R.drawable.category_button_selected);
    }
}
