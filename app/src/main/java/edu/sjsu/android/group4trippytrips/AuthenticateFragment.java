package edu.sjsu.android.group4trippytrips;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.Base64;

public class AuthenticateFragment extends Fragment {

    public AuthenticateFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authenticate, container, false);


        //Button for login
        EditText usernameField = view.findViewById(R.id.Username);
        EditText passwordField = view.findViewById(R.id.Password);
        Button loginButton = view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(getActivity(), "Log In Failed.", Toast.LENGTH_LONG).show();
            }
            else {
                try (Cursor cursor = requireContext().getContentResolver().query(
                        Uri.parse("content://edu.sjsu.android.group4trippytrips.authenticate"),
                        null, // projection
                        username,
                        null,
                        null
                )) {
                    if (cursor != null && cursor.moveToFirst()) {
                        String testPassword = "";
                        String userHash = "";
                        try {
                            String salt = cursor.getString(cursor.getColumnIndexOrThrow("salt"));
                            testPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                userHash = AuthenticateProvider.hashPassword(password.toCharArray(), Base64.getDecoder().decode(salt));
                            }
                        } catch (IllegalArgumentException e) {
                            Toast.makeText(getActivity(), "Log In Failed.", Toast.LENGTH_LONG).show();
                        }
                        if(!userHash.equals(testPassword)){
                            Toast.makeText(getActivity(), "Log In Failed.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                            prefs.edit().putString("username", username).apply();
                            cursor.close();
                            NavHostFragment.findNavController(AuthenticateFragment.this)
                                    .navigate(R.id.action_loginFragment_to_homeFragment);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Log In Failed.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //Button for sign up
        EditText sUsernameField = view.findViewById(R.id.sUsername);
        EditText sPasswordField = view.findViewById(R.id.sPassword);
        Button signupButton = view.findViewById(R.id.signupButton);
        signupButton.setOnClickListener(v -> {
            String username = sUsernameField.getText().toString().trim();
            String password = sPasswordField.getText().toString().trim();
            if(username.isEmpty() || password.isEmpty()){
                Toast.makeText(getActivity(), "Sign Up Failed", Toast.LENGTH_LONG).show();
            }
            else{
                try (Cursor sCursor = requireContext().getContentResolver().query(
                        Uri.parse("content://edu.sjsu.android.group4trippytrips.authenticate"),
                        null, // projection
                        username,
                        null,
                        null
                )) {
                    ContentValues values = new ContentValues();
                    values.put("username", username);
                    values.put("password", password);
                    if (sCursor != null && sCursor.moveToFirst()) {
                        Toast.makeText(getActivity(), "Sign Up Failed.", Toast.LENGTH_LONG).show();
                    } else {
                        Uri uri = requireContext().getContentResolver().insert(
                                Uri.parse("content://edu.sjsu.android.group4trippytrips.authenticate"),
                                values
                        );
                        if (uri == null) {
                            Toast.makeText(getActivity(), "Sign Up Failed.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Sign Up Success! Please Log In.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        return view;
    }
}
