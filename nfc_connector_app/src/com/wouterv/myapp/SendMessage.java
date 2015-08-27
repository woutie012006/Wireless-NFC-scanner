package com.wouterv.myapp;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by woute on 26-8-2015.
 */
public class SendMessage extends AsyncTask<String, Integer, String>
{
    EditText ip;

    public SendMessage(EditText e)
    {
        ip = e;
    }

    @Override
    protected String doInBackground(String... params)
    {
        try {
            Socket ss;
            ss = new Socket(ip.getText().toString(), 444);
            PrintStream ps = new PrintStream(ss.getOutputStream());
            ps.println(params);
        }catch (Exception e){ System.out.println("fuck " + e);}

        return "";
    }
}
