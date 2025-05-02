package edu.sjsu.android.group4trippytrips;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.net.SearchByTextRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private Button hotelsButton, flightsButton, activitiesButton;
    private EditText editTravelers, editDate;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        hotelsButton = view.findViewById(R.id.hotelsButton);
        flightsButton = view.findViewById(R.id.flightsButton);
        activitiesButton = view.findViewById(R.id.activitiesButton);
        editTravelers = view.findViewById(R.id.editTravelers);
        editDate = view.findViewById(R.id.editDate);

        hotelsButton.setOnClickListener(v -> selectCategory(hotelsButton));
        flightsButton.setOnClickListener(v -> selectCategory(flightsButton));
        activitiesButton.setOnClickListener(v -> selectCategory(activitiesButton));

        editTravelers.setOnClickListener(v -> showTravelerPicker());
        editDate.setOnClickListener(v -> showDatePicker());

        selectCategory(hotelsButton);

        LinearLayout beachTrips = view.findViewById(R.id.beachTripsLayout);
        LinearLayout cityTours = view.findViewById(R.id.cityToursLayout);
        LinearLayout adventure = view.findViewById(R.id.adventureLayout);

        beachTrips.setOnClickListener(v -> Toast.makeText(getContext(), "Beach Trips clicked!", Toast.LENGTH_SHORT).show());
        cityTours.setOnClickListener(v -> Toast.makeText(getContext(), "City Tours clicked!", Toast.LENGTH_SHORT).show());
        adventure.setOnClickListener(v -> Toast.makeText(getContext(), "Adventure clicked!", Toast.LENGTH_SHORT).show());

        // ✅ Bottom Navigation logic using if-else instead of switch
        BottomNavigationView bottomNavigation = view.findViewById(R.id.bottom_navigation);

        // ✅ Set currently selected item to Home
        bottomNavigation.setSelectedItemId(R.id.homeFragment);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                return true;
            } else if (itemId == R.id.searchResultsFragment) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.searchResultsFragment);
                return true;
            } else if (itemId == R.id.addedItemsFragment) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.addedItemsFragment);
                return true;
            } else if (itemId == R.id.settingsFragment) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.settingsFragment);
                return true;
            }
            return false;
        });

        // --------------------------- TEST CODE ONLY ----------------------------- //
        // Define a variable to hold the Places API key.
        String apiKey = BuildConfig.GROUP_PROJECT_GOOGLE_API_KEY;

        // Initialize the SDK
        Places.initializeWithNewPlacesApiEnabled(container.getContext(), apiKey);

        // Create a new PlacesClient instance
        PlacesClient placesClient = Places.createClient(container.getContext());

        // Specify the list of fields to return.
        final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.DISPLAY_NAME);

        // Define latitude and longitude coordinates of the search area.
        LatLng southWest = new LatLng(37.38816277477739, -122.08813770258874);
        LatLng northEast = new LatLng(37.39580487866437, -122.07702325966572);

        // Use the builder to create a SearchByTextRequest object.
        final SearchByTextRequest searchByTextRequest = SearchByTextRequest.builder("Spicy Vegetarian Food", placeFields)
                .setMaxResultCount(10)
                .setLocationRestriction(RectangularBounds.newInstance(southWest, northEast)).build();

        // Call PlacesClient.searchByText() to perform the search.
        // Define a response handler to process the returned List of Place objects.
        placesClient.searchByText(searchByTextRequest)
                .addOnSuccessListener(response -> {
                    List<Place> places = response.getPlaces();
                });

        return view;
    }

    private void selectCategory(Button selectedButton) {
        Button[] buttons = {hotelsButton, flightsButton, activitiesButton};

        for (Button btn : buttons) {
            btn.setSelected(false);
            btn.setTextColor(getResources().getColor(android.R.color.black));
            btn.setBackgroundResource(R.drawable.category_button_selector);
            btn.setCompoundDrawablesWithIntrinsicBounds(null,
                    ContextCompat.getDrawable(requireContext(), getIconForButton(btn, false)),
                    null, null);
        }

        selectedButton.setSelected(true);
        selectedButton.setTextColor(getResources().getColor(android.R.color.white));
        selectedButton.setCompoundDrawablesWithIntrinsicBounds(null,
                ContextCompat.getDrawable(requireContext(), getIconForButton(selectedButton, true)),
                null, null);
    }

    private int getIconForButton(Button button, boolean selected) {
        if (button.getId() == R.id.hotelsButton) {
            return selected ? R.drawable.ic_hotel_white : R.drawable.ic_hotel;
        } else if (button.getId() == R.id.flightsButton) {
            return selected ? R.drawable.ic_flight_white : R.drawable.ic_flight;
        } else {
            return selected ? R.drawable.ic_activity_white : R.drawable.ic_activity;
        }
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
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    editDate.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }
}
