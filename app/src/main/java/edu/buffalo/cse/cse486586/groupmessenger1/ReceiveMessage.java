package edu.buffalo.cse.cse486586.groupmessenger1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by user on 2/12/16.
 */
public class ReceiveMessage extends AsyncTask<ServerSocket, String, Void> {
    static final String TAG = GroupMessengerActivity.class.getSimpleName();
    private final TextView mTextView;
    private final ContentResolver mContentResolver;
    private final Uri mUri;
    private int num=0;


    public ReceiveMessage(TextView _tv,ContentResolver _cr ) {

        mTextView=_tv;
        mContentResolver=_cr;
        mUri = Uri.parse("content://edu.buffalo.cse.cse486586.groupmessenger1.provider");
//        mContentResolver = _cr;
//        mUri = buildUri("content", "edu.buffalo.cse.cse486586.groupmessenger1.provider");
//        mContentValues = initTestValues();
    }

    @Override
    protected Void doInBackground(ServerSocket... sockets)  {
        ServerSocket serverSocket = sockets[0];


        String Rec_msg="";
        Socket Rec_in;
        boolean isListening=true;

        while(isListening) {

            try {

                        /*
                        http://developer.android.com/reference/java/net/ServerSocket.html
                         */
                Rec_in = serverSocket.accept();
                BufferedReader Bu_read=new BufferedReader(new InputStreamReader(Rec_in.getInputStream(),"utf8"));

                Rec_msg = Bu_read.readLine();
                Bu_read.close();
                Rec_in.close();
                //System.out.println(kk);
                publishProgress(Rec_msg);
                //Rec_msg= "";





            } catch (IOException e) {

                Log.e(TAG, " connect failed");
            }

        }

        return null;

    }


    protected void onProgressUpdate(String...strings) {
            /*
             * The following code displays what is received in doInBackground().
             */
        String strReceived =strings[0].trim();
        mTextView.append(strReceived+"\n");



            /*
             * The following code creates a file in the AVD's internal storage and stores a file.
             *
             * For more information on file I/O on Android, please take a look at
             * http://developer.android.com/training/basics/data-storage/files.html
             */

        ContentValues cv = new ContentValues();
        cv.put("key", Integer.toString(num));
        cv.put("value", strReceived);
        mContentResolver.insert(mUri, cv);
        num++;

        return ;
    }
}
