package edu.sjsu.android.group4trippytrips;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import edu.sjsu.android.group4trippytrips.R;

public class HomeActivity extends AppCompatActivity {

    Button btnDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnDates = findViewById(R.id.btnDates);

        btnDates.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); // Note: zero-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                HomeActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    btnDates.setText(date); // Optional: Set picked date to button text
                    Toast.makeText(this, "Selected: " + date, Toast.LENGTH_SHORT).show();
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}
