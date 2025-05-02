package edu.sjsu.android.group4trippytrips;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        // ✅ Bottom Navigation Logic
        BottomNavigationView bottomNavigation = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.searchResultsFragment);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                NavHostFragment.findNavController(SearchResultsFragment.this)
                        .navigate(R.id.homeFragment);
                return true;
            } else if (itemId == R.id.searchResultsFragment) {
                return true; // already here
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

        // Simulated hotel data
        String[] hotelNames = {"Hotel A", "Hotel B", "Hotel C", "Hotel D", "Hotel E", "Hotel F", "Hotel G", "Hotel H", "Hotel I", "Hotel J"};
        String[] ratings = {"4/5", "3/5", "5/5", "4/5", "4/5", "3/5", "5/5", "2/5", "4/5", "5/5"};
        String[] cities = {"New York", "Los Angeles", "Chicago", "Miami", "Austin", "Seattle", "Boston", "San Francisco", "Denver", "Atlanta"};

        for (int i = 0; i < hotelNames.length; i++) {
            addHotelResult(hotelNames[i], ratings[i], cities[i]);
        }

        return rootView;
    }

    private void addHotelResult(String name, String rating, String city) {
        if (getContext() == null) return;
        Context context = getContext();

        // Entire card container
        LinearLayout itemLayout = new LinearLayout(context);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));
        itemLayout.setBackgroundColor(Color.parseColor("#E0F2F1"));
        itemLayout.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(0, 0, 0, dpToPx(12));
        itemLayout.setLayoutParams(itemParams);

        // Text column
        LinearLayout textContainer = new LinearLayout(context);
        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f
        ));

        // Hotel name
        TextView hotelName = new TextView(context);
        hotelName.setText(name);
        hotelName.setTextSize(28);
        hotelName.setTypeface(null, Typeface.BOLD);
        hotelName.setTextColor(Color.BLACK);
        textContainer.addView(hotelName);

        // City
        TextView locationView = new TextView(context);
        locationView.setText(city);
        locationView.setTextSize(20);
        locationView.setTextColor(Color.parseColor("#666666"));
        locationView.setPadding(0, dpToPx(4), 0, 0);
        textContainer.addView(locationView);

        // Rating
        LinearLayout ratingRow = new LinearLayout(context);
        ratingRow.setOrientation(LinearLayout.HORIZONTAL);
        ratingRow.setGravity(Gravity.CENTER_VERTICAL);
        ratingRow.setPadding(0, dpToPx(8), 0, 0);

        ImageView starIcon = new ImageView(context);
        starIcon.setImageResource(android.R.drawable.btn_star_big_on);
        starIcon.setColorFilter(Color.parseColor("#FFD700"));
        LinearLayout.LayoutParams starParams = new LinearLayout.LayoutParams(
                dpToPx(18), dpToPx(18));
        starIcon.setLayoutParams(starParams);
        ratingRow.addView(starIcon);

        TextView ratingView = new TextView(context);
        ratingView.setText(" " + rating);
        ratingView.setTextSize(20);
        ratingView.setTextColor(Color.parseColor("#555555"));
        ratingView.setPadding(dpToPx(6), 0, 0, 0);
        ratingRow.addView(ratingView);

        textContainer.addView(ratingRow);
        itemLayout.addView(textContainer);

        // Square background for plus icon
        FrameLayout squareFrame = new FrameLayout(context);
        LinearLayout.LayoutParams frameParams = new LinearLayout.LayoutParams(
                dpToPx(48), dpToPx(48));
        frameParams.setMargins(dpToPx(8), 0, 0, 0);
        squareFrame.setLayoutParams(frameParams);
        squareFrame.setBackgroundColor(Color.parseColor("#B0BEC5"));
        squareFrame.setPadding(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4));

        // Plus icon
        ImageButton addButton = new ImageButton(context);
        addButton.setImageResource(android.R.drawable.ic_input_add);
        addButton.setBackgroundColor(Color.TRANSPARENT);
        addButton.setColorFilter(Color.BLACK);
        addButton.setScaleType(ImageView.ScaleType.CENTER);
        addButton.setPadding(0, 0, 0, 0);
        addButton.setContentDescription("Add Hotel");

        FrameLayout.LayoutParams btnParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addButton.setLayoutParams(btnParams);
        squareFrame.addView(addButton);

        itemLayout.addView(squareFrame);
        cityResultsContainer.addView(itemLayout);
    }

    // Helper method to convert dp to px
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
