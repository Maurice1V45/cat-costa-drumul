package com.paythedriver.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.paythedriver.R;

public class ResultDialog extends DialogFragment {

    private long totalSum;
    private long sumPerPassenger;

    /**
     * Constructor
     */
    public ResultDialog() {
        // empty constructor
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.result_layout, null);

        builder.setView(view);

        TextView totalSumText = (TextView) view.findViewById(R.id.total_sum_text);
        totalSumText.setText(String.valueOf(totalSum));

        TextView passengerSumText = (TextView) view.findViewById(R.id.passenger_sum_text);
        if (sumPerPassenger == -1) {
            passengerSumText.setVisibility(View.GONE);
        } else {
            passengerSumText.setText(getString(R.string.result_dialog_description).replace("%sum%", String.valueOf(sumPerPassenger)));
        }

        // set create/edit button
        builder.setPositiveButton(android.R.string.ok, new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setTotalSum(long totalSum) {
        this.totalSum = totalSum;
    }

    public void setSumPerPassenger(long sumPerPassenger) {
        this.sumPerPassenger = sumPerPassenger;
    }
}
