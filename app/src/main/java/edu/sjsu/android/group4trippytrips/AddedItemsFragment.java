package edu.sjsu.android.group4trippytrips;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
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

    public AddedItemsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_added_items, container, false);
        cityResultsContainer = rootView.findViewById(R.id.cityResultsContainer);

        // Get current username for filtering saved items (if needed)
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);

        Cursor cursor = requireContext().getContentResolver().query(
                Uri.parse("content://edu.sjsu.android.group4trippytrips.locations"),
                null,
                username,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
                addAddedCard(name, rating, address);
            } while (cursor.moveToNext());

            cursor.close();
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

    private void addAddedCard(String name, float rating, String location) {
        if (getContext() == null) return;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View cardView = inflater.inflate(R.layout.item_added_card, cityResultsContainer, false);

        TextView nameView = cardView.findViewById(R.id.resultName);
        TextView locationView = cardView.findViewById(R.id.resultLocation);
        TextView ratingView = cardView.findViewById(R.id.resultRating);

        nameView.setText(name);
        locationView.setText(location);
        ratingView.setText(String.format("%.1f/5", rating));

        ImageView checkIcon = cardView.findViewById(R.id.checkIcon);
        checkIcon.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Removed: " + name, Toast.LENGTH_SHORT).show();
            // Optional: implement deletion from DB here
        });

        cityResultsContainer.addView(cardView);
    }
}
