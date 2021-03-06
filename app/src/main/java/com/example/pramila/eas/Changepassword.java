package com.example.pramila.eas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Changepassword extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private static String KEY_SUCCESS = "success";

    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    EditText oldpassword, newpassword,confirmpassword;
    String sessionid;
    String tempold,tempnew,tempconfirm;
    String password;
    Button btnchange;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_changepassword);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        Log.e("eas", "sessionid:" + sessionid);


        oldpassword = (EditText) findViewById(R.id.oldpassword);
        newpassword = (EditText) findViewById(R.id.newpassword);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        btnchange = (Button) findViewById(R.id.btnchange);

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(newpassword.getText().toString().equals(confirmpassword.getText().toString())){
                    getdata();
                    insertdata(tempold,tempnew,tempconfirm);
                }
                else
                    Toast.makeText(Changepassword.this,"Password do not match",Toast.LENGTH_LONG).show();

            }
        });

    }
    public void getdata()
    {
        tempold=oldpassword.getText().toString();
        tempnew=newpassword.getText().toString();
        tempconfirm=confirmpassword.getText().toString();
        Log.e("old password:",tempold);
        Log.e("new password:",tempnew);
        Log.e("confirm password:",tempconfirm);

    }
    public void insertdata(final String oldpass, final String newpass, final String confirmpass)
    {
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String > {
            HttpURLConnection conn;
            URL url = null;

            @Override
            protected String doInBackground(String... params) {
                String oldpassholder = oldpass;
                String newpassholder = newpass;
                String confirmpassholder = confirmpass;

                try {
                    url = new URL("http://" + Server.address + "/project/admin/changepasswordapp.php");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.e("eas", e.getMessage());

                    return "exception";
                }
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("password", oldpassholder));
                nameValuePairs.add(new BasicNameValuePair("newpassword", newpassholder));
                nameValuePairs.add(new BasicNameValuePair("newpassword", confirmpassholder));
                try {
                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cookie", sessionid);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("password", oldpassholder)
                            .appendQueryParameter("newpassword", newpassholder)
                            .appendQueryParameter("newpassword", confirmpassholder);
                    String query = builder.build().getEncodedQuery();
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));

                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();

                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                try {

                    int response_code = conn.getResponseCode();

                    // Check if successful connection made
                    if (response_code == HttpURLConnection.HTTP_OK) {

                        // Read data sent from server
                        InputStream input = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                        StringBuilder result = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        // Pass data to onPostExecute method
                        return (result.toString());

                    } else {

                        return ("unsuccessful");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("eas", e.getMessage());
                    return "exception";
                } finally {
                    conn.disconnect();
                }

            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Log.e("eas", result);
                Log.e("newpassword", newpass);
                Log.e("confirmpassword", confirmpass);

                if (result.contains("true")) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent i = new Intent(Changepassword.this, MainActivity.class);
                    startActivity(i);
                    Toast.makeText(Changepassword.this, "Password Changed: Please Login Again", Toast.LENGTH_LONG).show();
                } else if (result.contains("false"))
                    Toast.makeText(Changepassword.this, "Current Password do not match", Toast.LENGTH_LONG).show();




            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(oldpass, newpass, confirmpass);

            }


}
