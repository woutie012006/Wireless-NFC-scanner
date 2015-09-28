package com.wouterv.myapp;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.PrintStream;
import java.net.Socket;
import org.json.simple.*;
/**
 * Created by woute on 26-8-2015.
 */
public class SendMessage extends AsyncTask<String, Integer, String> {
    EditText ip;

    public SendMessage(EditText e) {
        ip = e;
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            JSONObject obj = new JSONObject();
            try {
                obj.put("0", params[0]);
                obj.put("1", params[1]);
                obj.put("2", params[2]);
            }catch(Exception e){}

            Socket ss;
            ss = new Socket(ip.getText().toString(), 444);
            PrintStream ps = new PrintStream(ss.getOutputStream());
            ps.println(obj);
        } catch (Exception e) {
            System.out.println("fuck " + e);
        }

        return "";
    }
}
