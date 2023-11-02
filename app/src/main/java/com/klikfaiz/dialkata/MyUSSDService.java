package com.klikfaiz.dialkata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyUSSDService  {
//
//    @Override
//    public void onReceiveUSSD(String message) {
//        // Called when a USSD message is received
//        // Show the message in a dialog or TextView
//        showDialog(message);
//    }
//
//    @Override
//    public void onUSSDDismissed() {
//        // Called when the USSD session is dismissed
//        // Do something here if needed
//    }
//
//    @Override
//    public void onUSSDFailed() {
//        // Called when the USSD session fails
//        // Do something here if needed
//    }
//
//    private void showDialog(String message) {
//        // Create and show the custom dialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        // Create the custom dialog view
//        View dialogView = LayoutInflater.from(this).inflate(R.layout.custom_dialog_layout, null);
//        builder.setView(dialogView);
//        AlertDialog dialog = builder.create();
//
//
//        // Get references to the views in the custom dialog
//        TextView headerTextView = dialogView.findViewById(R.id.header_textview);
//        TextView responseTextView = dialogView.findViewById(R.id.response_textview);
//        EditText inputEditText = dialogView.findViewById(R.id.input_edittext);
//        Button cancelButton = dialogView.findViewById(R.id.cancel_button);
//        Button okButton = dialogView.findViewById(R.id.ok_button);
//
//        // Set the message and header text in the custom dialog
//        headerTextView.setText("USSD");
//        responseTextView.setText(message);
//
//        // Set the onClickListener for the OK button
//        okButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Get the user input from the EditText view
//                String userInput = inputEditText.getText().toString();
//
//
//                // Close the dialog
//                dialog.dismiss();
//            }
//        });
//
//        // Set the onClickListener for the cancel button
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // Close the dialog
//                dialog.dismiss();
//            }
//        });
//
//
//
//        dialog.show();
//    }

}
