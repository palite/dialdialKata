package com.klikfaiz.dialkata;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class USSDReceiver extends BroadcastReceiver {
    private static final String TAG = "USSDReceiver";
    private boolean isStarted = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            isStarted = true;
        } else {
            String ussdResponse = getResultData();
            if (ussdResponse != null && isStarted) {
                Log.d(TAG, "USSD Response: " + ussdResponse);
                isStarted = false;
                // Do something with the USSD response here
                Toast.makeText(context, ussdResponse, Toast.LENGTH_LONG).show();
            }
        }
    }
}

