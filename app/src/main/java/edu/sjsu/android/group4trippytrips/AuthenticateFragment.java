package edu.sjsu.android.group4trippytrips;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.net.HttpCookie;

public class AuthenticateFragment extends Fragment {

    public AuthenticateFragment() {
        // Required empty public constructor
    }

    //TODO: deal with sign up and login
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


            Cursor cursor = getContext().getContentResolver().query(
                    Uri.parse("content://edu.sjsu.android.group4trippytrips/authenticate"),
                    null, // projection
                    username,
                    new String[]{password},
                    null
            );
            if(cursor != null){

                NavHostFragment.findNavController(AuthenticateFragment.this)
                        .navigate(R.id.action_loginFragment_to_homeFragment);
            }
            else{

            }

        });

        //Button for sign up
        Button signupButton = view.findViewById(R.id.signupButton);
        signupButton.setOnClickListener(v -> {
            NavHostFragment.findNavController(AuthenticateFragment.this)
                    .navigate(R.id.action_loginFragment_to_homeFragment);
        });

        // TEMPORARY: Button to skip to HomeFragment
        Button tempButtonGoHome = view.findViewById(R.id.tempButtonGoHome);
        tempButtonGoHome.setOnClickListener(v ->
                NavHostFragment.findNavController(AuthenticateFragment.this)
                        .navigate(R.id.action_loginFragment_to_homeFragment)
        );

        return view;
    }
}
