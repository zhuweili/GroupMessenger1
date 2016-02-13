package edu.buffalo.cse.cse486586.groupmessenger1;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by user on 2/11/16.
 */
public class SendMessageClickListener implements View.OnClickListener {
    static final String TAG = GroupMessengerActivity.class.getSimpleName();
    private final EditText myeditText;
    private final TextView mTextView;


    public SendMessageClickListener(EditText _et, TextView _tv ) {
        myeditText = _et;
        mTextView=_tv;
//        mContentResolver = _cr;
//        mUri = buildUri("content", "edu.buffalo.cse.cse486586.groupmessenger1.provider");
//        mContentValues = initTestValues();
    }

    @Override
    public void onClick(View v) {
        //mTextView.append("click once\n");

            mTextView.append("click once\n");
            String msg = myeditText.getText().toString() + "\n";
            myeditText.setText("");
            new ClientTask().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, msg, "11112");



    }


    private class ClientTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... msgs) {
            Integer[] PORT={11108,11112,11116,11120,11124};
            for (int i=0;i<PORT.length;i++) {
                try {


                    Socket socket = new Socket("10.0.2.2", PORT[i]);

                    String msgToSend = msgs[0];

                    OutputStream Send_out = socket.getOutputStream();
                    Send_out.write(msgToSend.getBytes("utf-8"));
                    Send_out.close();

                    socket.close();
                    System.out.println("message send");

                } catch (UnknownHostException e) {
                    Log.e(TAG, "ClientTask UnknownHostException");
                } catch (IOException e) {
                    Log.e(TAG, "ClientTask socket IOException");
                }
            }

            return null;
        }
    }
}
