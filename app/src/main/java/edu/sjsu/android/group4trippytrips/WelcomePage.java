package edu.sjsu.android.group4trippytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class WelcomePage extends Fragment {

    public WelcomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_page, container, false);

        Button loginButton = view.findViewById(R.id.LogIn);
        Button signUpButton = view.findViewById(R.id.signUp);

        loginButton.setOnClickListener(v ->
                NavHostFragment.findNavController(WelcomePage.this)
                        .navigate(R.id.action_welcomePage_to_loginFragment)
        );

        return view;
    }
}
