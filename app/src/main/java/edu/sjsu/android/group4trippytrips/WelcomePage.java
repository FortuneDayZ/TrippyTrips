package edu.sjsu.android.group4trippytrips;

import android.os.Bundle;
import android.text.Html; // Required for parsing HTML
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView; // Required for TextViews

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class WelcomePage extends Fragment {

    public WelcomePage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find Views
        Button getStartedButton = view.findViewById(R.id.getStartedButton);
        TextView welcomeText = view.findViewById(R.id.welcomeText);
        TextView welcomeInstruction = view.findViewById(R.id.welcomeInstruction);

        // Set Text for Headline (parsing HTML)
        String welcomeHeadlineHtml = getString(R.string.welcome_headline);
        welcomeText.setText(Html.fromHtml(welcomeHeadlineHtml, Html.FROM_HTML_MODE_LEGACY));

        // Set Text for Instructions
        welcomeInstruction.setText(R.string.welcome_instructions_detailed);

        // Set Button Click Listener
        getStartedButton.setOnClickListener(v ->
                NavHostFragment.findNavController(WelcomePage.this)
                        .navigate(R.id.action_welcomePage_to_loginFragment)
        );
    }
}
