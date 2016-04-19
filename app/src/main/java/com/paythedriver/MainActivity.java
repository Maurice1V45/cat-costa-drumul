package com.paythedriver;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paythedriver.dialog.HelpDialog;
import com.paythedriver.dialog.ResultDialog;
import com.paythedriver.view.CustomToast;

public class MainActivity extends AppCompatActivity {

    private EditText distanceField;
    private EditText consumptionField;
    private EditText priceField;
    private EditText peopleField;
    private View distanceLayout;
    private View consumptionLayout;
    private View priceLayout;
    private View peopleLayout;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initViews();
        initListeners();
    }

    private void initViews() {
        distanceField = (EditText) findViewById(R.id.distance_field);
        consumptionField = (EditText) findViewById(R.id.consumption_field);
        priceField = (EditText) findViewById(R.id.price_field);
        peopleField = (EditText) findViewById(R.id.people_field);
        calculateButton = (Button) findViewById(R.id.calculate_button);
        distanceLayout = findViewById(R.id.distance_layout);
        consumptionLayout = findViewById(R.id.consumption_layout);
        priceLayout = findViewById(R.id.price_layout);
        peopleLayout = findViewById(R.id.people_layout);
    }

    private void initListeners() {
        distanceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusEditText(distanceField);
            }
        });
        consumptionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusEditText(consumptionField);
            }
        });
        priceLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusEditText(priceField);
            }
        });
        peopleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusEditText(peopleField);
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    ResultDialog resultDialog = new ResultDialog();
                    resultDialog.setTotalSum(calculateSum());
                    resultDialog.setSumPerPassenger(calculateSumPerPassenger(peopleField.getText().toString()));
                    resultDialog.show(getFragmentManager(), "");
                }
            }
        });
    }

    private long calculateSum() {
        double distance = Double.valueOf(distanceField.getText().toString());
        double consumption = Double.valueOf(consumptionField.getText().toString());
        double price = Double.valueOf(priceField.getText().toString());

        return Math.round(distance * consumption / 100 * price);
    }

    private long calculateSumPerPassenger(String passengerNr) {
        if (passengerNr.isEmpty()) {
            return -1;
        } else {
            return (long) (Math.ceil((Double.valueOf(calculateSum()) / Double.valueOf(passengerNr))));
        }
    }

    private boolean validateFields() {
        String distance = distanceField.getText().toString();
        if (distance.isEmpty() || Double.valueOf(distance) <= 0) {
            CustomToast.makeText(MainActivity.this, R.string.error_invalid_distance, Toast.LENGTH_SHORT).show();
            return false;
        }
        String consumption = consumptionField.getText().toString();
        if (consumption.isEmpty() || Double.valueOf(consumption) <= 0) {
            CustomToast.makeText(MainActivity.this, R.string.error_invalid_consumption, Toast.LENGTH_SHORT).show();
            return false;
        }
        String price = priceField.getText().toString();
        if (price.isEmpty() || Double.valueOf(price) <= 0) {
            CustomToast.makeText(MainActivity.this, R.string.error_invalid_price, Toast.LENGTH_SHORT).show();
            return false;
        }
        String people = peopleField.getText().toString();
        if (!people.isEmpty() && Double.valueOf(people) <= 0) {
            CustomToast.makeText(MainActivity.this, R.string.error_invalid_people, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void focusEditText(EditText editText) {
        editText.requestFocus(View.FOCUS_RIGHT);
        editText.setSelection(editText.getText().length());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.help_action:
                HelpDialog dialog = new HelpDialog();
                dialog.show(getFragmentManager(), "");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
