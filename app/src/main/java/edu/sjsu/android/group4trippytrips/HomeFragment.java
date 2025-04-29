package edu.sjsu.android.group4trippytrips;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private Button hotelsButton, flightsButton, activitiesButton;
    private EditText editTravelers, editDate;

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

        // Connect EditTexts
        editTravelers = view.findViewById(R.id.editTravelers);
        editDate = view.findViewById(R.id.editDate);

        // Set button click listeners
        hotelsButton.setOnClickListener(v -> selectCategory(hotelsButton));
        flightsButton.setOnClickListener(v -> selectCategory(flightsButton));
        activitiesButton.setOnClickListener(v -> selectCategory(activitiesButton));

        // Set travelers field click listener
        editTravelers.setOnClickListener(v -> showTravelerPicker());

        // Set date field click listener
        editDate.setOnClickListener(v -> showDatePicker());

        // Optional: Select Hotels by default at start
        selectCategory(hotelsButton);

        return view;
    }

    private void selectCategory(Button selectedButton) {
        Button[] buttons = {hotelsButton, flightsButton, activitiesButton};

        for (Button btn : buttons) {
            btn.setSelected(false);
            btn.setTextColor(getResources().getColor(android.R.color.black));
            btn.setBackgroundResource(R.drawable.category_button_selector);
        }

        selectedButton.setSelected(true);
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));
    }


    private void showTravelerPicker() {
        NumberPicker numberPicker = new NumberPicker(requireContext());
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(8);
        numberPicker.setWrapSelectorWheel(false);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select Number of Travelers");
        builder.setView(numberPicker);
        builder.setPositiveButton("OK", (dialog, which) -> {
            int selectedValue = numberPicker.getValue();
            editTravelers.setText(selectedValue + " Traveler" + (selectedValue > 1 ? "s" : ""));
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // 0-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Month is 0-based, so add 1
                    String date = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    editDate.setText(date);
                }, year, month, day);

        datePickerDialog.show();
    }
}
