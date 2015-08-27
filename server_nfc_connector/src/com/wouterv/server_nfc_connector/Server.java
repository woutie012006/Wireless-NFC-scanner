package com.wouterv.server_nfc_connector;

import java.io.*;
import java.lang.management.ThreadMXBean;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by woute on 26-8-2015.
 */
public class Server extends Thread
{
    private Thread t;
    private String threadName;
    //MainForm mf;
    private String lastTag;

    public String getLastTag()
    {
        return lastTag;
    }

    public ArrayList<String> getLastTech()
    {
        return lastTech;
    }

    private ArrayList<String> lastTech = new ArrayList<>();

    public Server(String name){
        threadName = name;
        //this.mf = mf;
        System.out.println("Creating " +  threadName );

    }
    public void runServer() throws  Exception
    {

        //start server
        ServerSocket srvSock  = new ServerSocket(444);

        while(true)
        {
            //Reading the message from the client
            Socket sock = srvSock.accept();
            InputStream is = sock.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String number = br.readLine();
            System.out.println("Message received from client is "+number);

            if(number.length()>0)
            {
             //   mf.setLbl1(number);
                lastTag = number;
            }
            else
            {
                System.out.println("nothing : " + number);

            }
        }
    }
    public void run()
    {
        try {
            runServer();
        }catch (Exception e ){System.out.println(e);}
    }

    public void start ()
    {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

}
