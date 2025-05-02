package edu.sjsu.android.group4trippytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddedItemsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AddedItemsFragment() {
        // Required empty public constructor
    }

    public static AddedItemsFragment newInstance(String param1, String param2) {
        AddedItemsFragment fragment = new AddedItemsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_added_items, container, false);

        // ✅ Bottom navigation setup
        BottomNavigationView bottomNavigation = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.addedItemsFragment);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                NavHostFragment.findNavController(AddedItemsFragment.this)
                        .navigate(R.id.homeFragment);
                return true;
            } else if (itemId == R.id.searchResultsFragment) {
                NavHostFragment.findNavController(AddedItemsFragment.this)
                        .navigate(R.id.searchResultsFragment);
                return true;
            } else if (itemId == R.id.addedItemsFragment) {
                return true; // already on this page
            } else if (itemId == R.id.settingsFragment) {
                NavHostFragment.findNavController(AddedItemsFragment.this)
                        .navigate(R.id.settingsFragment);
                return true;
            }
            return false;
        });

        return rootView;
    }
}
