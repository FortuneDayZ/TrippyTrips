package edu.sjsu.android.group4trippytrips;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    private Button hotelsButton, restaurantButton, activitiesButton;
    private MaterialButton submitButton;
    private String selectedCategoryPrefix = "Hotels in "; // Default prefix

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        hotelsButton = view.findViewById(R.id.hotelsButton);
        restaurantButton = view.findViewById(R.id.restaurantButton);
        activitiesButton = view.findViewById(R.id.activitiesButton);
        submitButton = view.findViewById(R.id.submitButton);

        hotelsButton.setOnClickListener(v -> selectCategory(hotelsButton));
        restaurantButton.setOnClickListener(v -> selectCategory(restaurantButton));
        activitiesButton.setOnClickListener(v -> selectCategory(activitiesButton));



        submitButton.setOnClickListener(v -> {
            EditText input = view.findViewById(R.id.searchBar);
            String userInput = input.getText().toString().trim();

            // Check for missing fields
            StringBuilder errorMessage = new StringBuilder();
            if (userInput.isEmpty()) {
                errorMessage.append("• Please enter a destination.\n");
            }
            if (errorMessage.length() > 0) {
                Toast.makeText(getContext(), errorMessage.toString().trim(), Toast.LENGTH_LONG).show();
                return;
            }

            // If all fields are filled, save and navigate
            String fullQuery = selectedCategoryPrefix + userInput;

            SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            prefs.edit().putString("location", fullQuery).apply();

            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.searchResultsFragment);
        });

        selectCategory(hotelsButton); // default selection

        LinearLayout beachTrips = view.findViewById(R.id.beachTripsLayout);
        LinearLayout cityTours = view.findViewById(R.id.cityToursLayout);
        LinearLayout adventure = view.findViewById(R.id.adventureLayout);

        beachTrips.setOnClickListener(v -> showCategoryConfirmation("Beaches nearby"));
        cityTours.setOnClickListener(v -> showCategoryConfirmation("Landmarks nearby"));
        adventure.setOnClickListener(v -> showCategoryConfirmation("Activities nearby"));

        BottomNavigationView bottomNavigation = view.findViewById(R.id.bottom_navigation);
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

        return view;
    }

    private void showCategoryConfirmation(String keyword) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Confirm Search")
                .setMessage("Search for \"" + keyword + "\"?")
                .setPositiveButton("Yes", (dialog, which) -> autoSearch(keyword))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void autoSearch(String keyword) {
        Calendar calendar = Calendar.getInstance();
        String today = (calendar.get(Calendar.MONTH) + 1) + "/" +
                calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                calendar.get(Calendar.YEAR);

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        prefs.edit()
                .putString("location", keyword)
                .apply();

        if (!isLocationEnabled()) {
            showSettingAlert();
        }
        else {
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.searchResultsFragment);
        }
    }

    private boolean isLocationEnabled() {
        LocationManager manager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showSettingAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setMessage("Please enable location service then try again.");
        alertDialog.setPositiveButton("Enable", (dialog, which) -> {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            requireContext().startActivity(intent);
        });
        alertDialog.setNegativeButton("Cancel", (dialog, which) ->
                dialog.cancel());
        alertDialog.show();
    }

    private void selectCategory(Button selectedButton) {
        Button[] buttons = {hotelsButton, restaurantButton, activitiesButton};

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

        // Update invisible category prefix
        if (selectedButton.getId() == R.id.hotelsButton) {
            selectedCategoryPrefix = "Hotels in ";
        } else if (selectedButton.getId() == R.id.restaurantButton) {
            selectedCategoryPrefix = "Restaurants in ";
        } else if (selectedButton.getId() == R.id.activitiesButton) {
            selectedCategoryPrefix = "Activities in ";
        }
    }

    private int getIconForButton(Button button, boolean selected) {
        if (button.getId() == R.id.hotelsButton) {
            return selected ? R.drawable.ic_hotel_white : R.drawable.ic_hotel;
        } else if (button.getId() == R.id.restaurantButton) {
            return selected ? R.drawable.ic_restaurant_white : R.drawable.ic_restaurant;
        } else {
            return selected ? R.drawable.ic_activity_white : R.drawable.ic_activity;
        }
    }
}
