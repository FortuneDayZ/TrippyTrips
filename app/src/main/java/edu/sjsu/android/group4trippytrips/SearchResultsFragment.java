package edu.sjsu.android.group4trippytrips;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SearchResultsFragment extends Fragment {

    private LinearLayout cityResultsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_results, container, false);
        cityResultsContainer = rootView.findViewById(R.id.cityResultsContainer);

        // Example: Simulated top 10 city/hotel results
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

        // Parent horizontal layout
        LinearLayout itemLayout = new LinearLayout(getContext());
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);
        itemLayout.setPadding(16, 16, 16, 16);
        itemLayout.setBackgroundColor(Color.parseColor("#F2F2F2"));
        LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        itemParams.setMargins(0, 0, 0, 24);
        itemLayout.setLayoutParams(itemParams);
        itemLayout.setGravity(Gravity.CENTER_VERTICAL);

        // Vertical container for text
        LinearLayout textContainer = new LinearLayout(getContext());
        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        // Hotel name
        TextView hotelName = new TextView(getContext());
        hotelName.setText(name);
        hotelName.setTextSize(18);
        hotelName.setTypeface(null, Typeface.BOLD);
        hotelName.setTextColor(Color.BLACK);
        textContainer.addView(hotelName);

        // Rating and City row
        LinearLayout subRow = new LinearLayout(getContext());
        subRow.setOrientation(LinearLayout.HORIZONTAL);
        subRow.setGravity(Gravity.CENTER_VERTICAL);
        subRow.setPadding(0, 8, 0, 0);

        ImageView starIcon = new ImageView(getContext());
        starIcon.setImageResource(android.R.drawable.btn_star_big_on);
        starIcon.setColorFilter(Color.parseColor("#FFD700"));
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(40, 40);
        starIcon.setLayoutParams(iconParams);
        subRow.addView(starIcon);

        TextView ratingView = new TextView(getContext());
        ratingView.setText(" " + rating + "  •  " + city);
        ratingView.setTextSize(14);
        ratingView.setTextColor(Color.DKGRAY);
        ratingView.setPadding(8, 0, 0, 0);
        subRow.addView(ratingView);

        textContainer.addView(subRow);
        itemLayout.addView(textContainer);

        // Add button
        ImageButton addButton = new ImageButton(getContext());
        addButton.setImageResource(android.R.drawable.ic_input_add);
        addButton.setBackground(null);
        addButton.setColorFilter(Color.BLACK);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(100, 100);
        addButton.setLayoutParams(buttonParams);
        addButton.setContentDescription("Add Hotel");
        itemLayout.addView(addButton);

        // Add the view to container
        cityResultsContainer.addView(itemLayout);
    }
}

