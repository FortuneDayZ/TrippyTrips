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
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchResultsFragment extends Fragment {

    private LinearLayout cityResultsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        cityResultsContainer = rootView.findViewById(R.id.cityResultsContainer);

        // Bottom Navigation setup
        BottomNavigationView bottomNavigation = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.searchResultsFragment);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                NavHostFragment.findNavController(SearchResultsFragment.this)
                        .navigate(R.id.homeFragment);
                return true;
            } else if (itemId == R.id.searchResultsFragment) {
                return true;
            } else if (itemId == R.id.addedItemsFragment) {
                NavHostFragment.findNavController(SearchResultsFragment.this)
                        .navigate(R.id.addedItemsFragment);
                return true;
            } else if (itemId == R.id.settingsFragment) {
                NavHostFragment.findNavController(SearchResultsFragment.this)
                        .navigate(R.id.settingsFragment);
                return true;
            }
            return false;
        });

        // Temporary mock data (for testing layout only — replace with real data later)
        String[] names = {"Hotel A", "Sushi Bar", "Cafe 21", "Bike Rentals", "Art Gallery"};
        String[] ratings = {"4/5", "5/5", "4/5", "3/5", "5/5"};
        String[] locations = {"New York", "New York", "New York", "New York", "New York"};

        for (int i = 0; i < names.length; i++) {
            addResultCard(names[i], ratings[i], locations[i]);
        }

        return rootView;
    }

    // This method inflates your XML card and populates it with dynamic data
    private void addResultCard(String name, String rating, String location) {
        if (getContext() == null) return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cardView = inflater.inflate(R.layout.item_result_card, cityResultsContainer, false);

        // Set dynamic values
        TextView nameView = cardView.findViewById(R.id.resultName);
        TextView locationView = cardView.findViewById(R.id.resultLocation);
        TextView ratingView = cardView.findViewById(R.id.resultRating);

        nameView.setText(name);
        locationView.setText(location);
        ratingView.setText(rating);

        // Optional: click action for plus icon
        ImageView plusIcon = cardView.findViewById(R.id.plusIcon);
        plusIcon.setOnClickListener(v ->
                Toast.makeText(getContext(), name + " added!", Toast.LENGTH_SHORT).show()
        );

        // Add the card to the scrollable container
        cityResultsContainer.addView(cardView);
    }
}
