package edu.sjsu.android.group4trippytrips;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        MaterialButton changeButton = rootView.findViewById(R.id.button);
        EditText passwordField = rootView.findViewById(R.id.newPassword);
        changeButton.setOnClickListener(v -> {
            v.setEnabled(false); // Prevent double tap
            String password = passwordField.getText().toString().trim();
            ContentValues content = new ContentValues();
            content.put("password", password);
            if(password.isEmpty())
            {
                Toast.makeText(getActivity(), "New Password Needed!", Toast.LENGTH_LONG).show();
            }
            else {
                int result = requireContext().getContentResolver().update(
                        Uri.parse("content://edu.sjsu.android.group4trippytrips.authenticate"),
                        content,
                        username,
                        null
                );
                if(result > 0){
                    Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getActivity(), "Password not changed, please try again later.", Toast.LENGTH_LONG).show();
                }
            }
        });

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

                        if (username != null) {
                            // Now you can delete the account
                            int result = requireContext().getContentResolver().delete(
                                    Uri.parse("content://edu.sjsu.android.group4trippytrips.authenticate"),
                                    username,
                                    null
                            );
                            int locationResult = requireContext().getContentResolver().delete(
                                    Uri.parse("content://edu.sjsu.android.group4trippytrips.locations"),
                                    username,
                                    null
                            );
                            //Delete successful
                            if(result > 0 && locationResult >= 0) {
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

        // Uninstall App
        MaterialButton uninstall = rootView.findViewById(R.id.uninstallButton);
        uninstall.setOnClickListener(v -> {
            Intent delete = new Intent(Intent.ACTION_DELETE,
                    Uri.parse("package:" + requireActivity().getPackageName()));
            startActivity(delete);
        });

        return rootView;
    }

    private void hideBottomNav() {
        BottomNavigationView bottomNav = requireActivity().findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setVisibility(View.GONE);
        }
    }
}
