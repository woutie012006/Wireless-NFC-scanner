package com.wouterv.myapp;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Formatter;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity
{
    //thanks to https://github.com/flomio/nfc_example_android

    private static final String TAG = MyActivity.class
            .getSimpleName();
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFiltersArray;
    private String[][] mTechListsArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // setup for foreground dispatch of NFC
        setupForgroundDispatch();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntnet()");
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

        // enable NFC foreground dispatch handling
        enableNfcForegroundDispatch();

        // check for nfc tech on the intent
        Intent intent = getIntent();
        handleNfcEvent(intent);

        // clear intent so it's not reused again
        setIntent(null);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        disableNfcForgroundDispatch();
    }


    private void setupForgroundDispatch() {
        // TODO, determine which nfc tech and tags we want to register for.

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // register the nfc tech we will respond to
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter tag = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndef.addDataScheme("http");
        ndef.addDataAuthority("www.flomio.com", null);

        mIntentFiltersArray = new IntentFilter[] { ndef, tech, tag };
        mTechListsArray = new String[][] { new String[] { NfcF.class.getName(),
                NfcA.class.getName(), NfcB.class.getName(),
                IsoDep.class.getName(), NdefFormatable.class.getName() } };
    }

    private void enableNfcForegroundDispatch() {
        if (mAdapter != null)
            mAdapter.enableForegroundDispatch(this, mPendingIntent,
                    mIntentFiltersArray, mTechListsArray);
    }

    private void disableNfcForgroundDispatch() {
        if (mAdapter != null)
            mAdapter.disableForegroundDispatch(this);
    }

    private void handleNfcEvent(Intent intent) {
        Log.d(TAG, "checkForTag; intent:" + intent);
        if (intent == null) {
            // nothing to do
            return;
        }

        // Parse the intent
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // grab the tag id
            Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            byte[] tagIDBytes = tag.getId();
            String tagId = mBytesToHexString(tagIDBytes);

            // build up the output string
            StringBuilder sb = new StringBuilder();

            sb.append(tagId);
            sb.append("\n\n");

            sb.append(action);
            sb.append("\n\n");
            //////////////////////////doesnt send the techlist yet
            for (int y = 0; y < tag.getTechList().length; y++) {
                sb.append('\n'); // a comma
                sb.append(tag.getTechList()[y]);
            }

            TextView t = (TextView) findViewById(R.id.Text1);
            t.setText(sb);

            new SendMessage((EditText)findViewById(R.id.ip)).execute(tagId);
            return;
        }
    }

    private static String mBytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return "0x" + sb.toString().toUpperCase();

    }

}
