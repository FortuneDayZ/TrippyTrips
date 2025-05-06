package edu.sjsu.android.group4trippytrips;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.api.net.SearchByTextRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class SearchResultsFragment extends Fragment {

    private LinearLayout cityResultsContainer;
    private TextView searchResultsTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);

        cityResultsContainer = rootView.findViewById(R.id.cityResultsContainer);
        searchResultsTitle = rootView.findViewById(R.id.searchResultsTitle);

        // Bottom nav setup
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

        getLocations(container);

        return rootView;
    }

    private void getLocations(ViewGroup container) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String locationQuery = prefs.getString("location", null);

        if (locationQuery == null || locationQuery.trim().isEmpty()) return;

        // Extract and display just the city name (remove category prefix)
        String cityOnly = locationQuery
                .replace("Hotels in ", "")
                .replace("Restaurants in ", "")
                .replace("Activities in ", "")
                .trim();
        searchResultsTitle.setText("Search Results for \"" + cityOnly + "\"");

        // Google Places setup
        String apiKey = BuildConfig.GROUP_PROJECT_GOOGLE_API_KEY;
        Places.initializeWithNewPlacesApiEnabled(container.getContext(), apiKey);
        PlacesClient placesClient = Places.createClient(container.getContext());

        final List<Place.Field> placeFields = Arrays.asList(
                Place.Field.DISPLAY_NAME,
                Place.Field.FORMATTED_ADDRESS,
                Place.Field.RATING
        );

        final SearchByTextRequest searchByTextRequest = SearchByTextRequest.builder(locationQuery, placeFields)
                .setMaxResultCount(10)
                .build();

        placesClient.searchByText(searchByTextRequest)
                .addOnSuccessListener(response -> {
                    List<Place> places = response.getPlaces();
                    for (Place p : places) {
                        String name = p.getDisplayName();
                        String address = p.getFormattedAddress();
                        double rating = p.getRating();
                        addResultCard(name, String.valueOf(rating), address);
                    }
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Error retrieving results.", Toast.LENGTH_SHORT).show());
    }

    private void addResultCard(String name, String rating, String location) {
        if (getContext() == null) return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cardView = inflater.inflate(R.layout.item_result_card, cityResultsContainer, false);

        TextView nameView = cardView.findViewById(R.id.resultName);
        TextView locationView = cardView.findViewById(R.id.resultLocation);
        TextView ratingView = cardView.findViewById(R.id.resultRating);

        nameView.setText(name);
        locationView.setText(location);
        ratingView.setText(rating);

        ImageView plusIcon = cardView.findViewById(R.id.plusIcon);
        plusIcon.setOnClickListener(v ->
                ContentValues content = new ContentValues();
        content.put("name", name);
        content.put("address", location);
        content.put("rating", rating);
        Uri result = requireContext().getContentResolver().insert(
                Uri.parse("content://edu.sjsu.android.group4trippytrips.locations"),
                content
        );
        if(result != null) {
            Toast.makeText(getContext(), name + " added!", Toast.LENGTH_SHORT).show();
        }

        cityResultsContainer.addView(cardView);
    }
}
