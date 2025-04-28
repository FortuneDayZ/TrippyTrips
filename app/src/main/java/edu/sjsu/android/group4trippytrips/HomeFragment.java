package edu.sjsu.android.group4trippytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        hotelsButton = view.findViewById(R.id.hotelsButton);
        flightsButton = view.findViewById(R.id.flightsButton);
        activitiesButton = view.findViewById(R.id.activitiesButton);

        hotelsButton.setOnClickListener(v -> selectCategory(hotelsButton));
        flightsButton.setOnClickListener(v -> selectCategory(flightsButton));
        activitiesButton.setOnClickListener(v -> selectCategory(activitiesButton));

        // OPTIONAL: Make Hotels selected by default
        selectCategory(hotelsButton);

        return view;
    }

    private void selectCategory(Button selectedButton) {
        // Reset all to normal style
        hotelsButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        flightsButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        activitiesButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        // Highlight only the selected one
        selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
    }
}
