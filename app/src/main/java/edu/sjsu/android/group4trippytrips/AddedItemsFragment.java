package edu.sjsu.android.group4trippytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddedItemsFragment extends Fragment {

    private LinearLayout cityResultsContainer;

    private final String[] savedNames = {
            "Hotel A", "Pizza Place", "Mountain Hike", "Book Café", "City Museum"
    };
    private final String[] savedRatings = {
            "4/5", "5/5", "3/5", "4.5/5", "5/5"
    };
    private final String[] savedLocations = {
            "New York", "Rome", "Denver", "Kyoto", "Berlin"
    };

    public AddedItemsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_added_items, container, false);
        cityResultsContainer = rootView.findViewById(R.id.cityResultsContainer);

        for (int i = 0; i < savedNames.length; i++) {
            addAddedCard(savedNames[i], savedRatings[i], savedLocations[i]);
        }

        BottomNavigationView bottomNavigation = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.addedItemsFragment);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            NavController navController = NavHostFragment.findNavController(this);
            if (itemId == navController.getCurrentDestination().getId()) return true;

            if (itemId == R.id.homeFragment) {
                navController.navigate(R.id.homeFragment);
            } else if (itemId == R.id.searchResultsFragment) {
                navController.navigate(R.id.searchResultsFragment);
            } else if (itemId == R.id.settingsFragment) {
                navController.navigate(R.id.settingsFragment);
            }
            return true;
        });

        return rootView;
    }

    private void addAddedCard(String name, String rating, String location) {
        if (getContext() == null) return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cardView = inflater.inflate(R.layout.item_added_card, cityResultsContainer, false);

        TextView nameView = cardView.findViewById(R.id.resultName);
        TextView locationView = cardView.findViewById(R.id.resultLocation);
        TextView ratingView = cardView.findViewById(R.id.resultRating);

        nameView.setText(name);
        locationView.setText(location);
        ratingView.setText(rating);

        ImageView checkIcon = cardView.findViewById(R.id.checkIcon);
        checkIcon.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Removed: " + name, Toast.LENGTH_SHORT).show();
        });

        cityResultsContainer.addView(cardView);
    }
}
