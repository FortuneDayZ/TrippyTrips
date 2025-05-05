package edu.sjsu.android.group4trippytrips;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class SettingsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        //Setup username of current user for setting changes
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);

        // Setup bottom navigation
        BottomNavigationView bottomNavigation = rootView.findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.settingsFragment);

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.homeFragment) {
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.homeFragment);
                return true;
            } else if (itemId == R.id.searchResultsFragment) {
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.searchResultsFragment);
                return true;
            } else if (itemId == R.id.addedItemsFragment) {
                NavHostFragment.findNavController(SettingsFragment.this)
                        .navigate(R.id.addedItemsFragment);
                return true;
            } else if (itemId == R.id.settingsFragment) {
                return true; // already on this fragment
            }
            return false;
        });

        // Navigation controller
        NavController navController = NavHostFragment.findNavController(SettingsFragment.this);

        // Logout button
        MaterialButton logoutButton = rootView.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            v.setEnabled(false); // Prevent double tap
            hideBottomNav();     // Optional: hide nav bar
            prefs.edit().clear().apply();
            navController.navigate(R.id.action_settingsFragment_to_welcomePage);
        });

        // Delete account button with confirmation dialog
        MaterialButton deleteAccountButton = rootView.findViewById(R.id.deleteAccountButton);
        deleteAccountButton.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Account?")
                    .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        v.setEnabled(false); // Prevent double tap
                        hideBottomNav();     // Optional
                        // TODO: delete account from database or backend if needed

                        if (username != null) {
                            // Now you can delete the account
                            int result = requireContext().getContentResolver().delete(
                                    Uri.parse("content://edu.sjsu.android.group4trippytrips.authenticate"),
                                    username,
                                    null
                            );
                            //Delete successful
                            if(result > 0) {
                                prefs.edit().clear().apply();
                                navController.navigate(R.id.action_settingsFragment_to_welcomePage);
                            }
                            else{
                                Toast.makeText(getActivity(), "Account not deleted. Try again later.", Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        return rootView;
    }

    private void hideBottomNav() {
        BottomNavigationView bottomNav = getActivity().findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.GONE);
        }
    }
}
