package edu.sjsu.android.group4trippytrips;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class AuthenticateFragment extends Fragment {

    public AuthenticateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authenticate, container, false);

        // TEMPORARY: Button to skip to HomeFragment
        Button tempButtonGoHome = view.findViewById(R.id.tempButtonGoHome);
        tempButtonGoHome.setOnClickListener(v ->
                NavHostFragment.findNavController(AuthenticateFragment.this)
                        .navigate(R.id.action_loginFragment_to_homeFragment)
        );

        return view;
    }
}
