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
import java.util.List;
import java.util.Map;

public class Changepassword extends AppCompatActivity {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private static String KEY_SUCCESS = "success";

    private ProgressDialog pDialog;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    private static String url_change_password = "http://" + Server.address + "/final/final/admin/changepasswordapp.php";


    EditText etoldpassword, etnewpassword;
    String sessionid;
    Button btnchange;
    String oldpass, newpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_changepassword);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        sessionid = intent.getStringExtra("sessionid");
        Log.e("eas", "sessionid:" + sessionid);




        etoldpassword = (EditText) findViewById(R.id.oldpassword);
        etnewpassword = (EditText) findViewById(R.id.newpassword);
        btnchange = (Button) findViewById(R.id.btnchange);

        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etoldpassword.getText().length() <= 0) {
                    Toast.makeText(Changepassword.this, "Enter correct password", Toast.LENGTH_SHORT).show();
                } else {
                    checkPassword(view);
                }

            }


        });

    }

    public void checkPassword(View arg0) {
        final String oldpassword = etoldpassword.getText().toString();
        final String newpassword = etnewpassword.getText().toString();
        if (oldpassword.equals("")) {
            updateDatabase(newpassword);
        } else {
            Log.e("eas", "failed");
        }
    }

    private void updateDatabase(final String password) {
        class AsyncChange extends AsyncTask<String, String, String> {
            ProgressDialog pdLoading = new ProgressDialog(Changepassword.this);
            HttpURLConnection conn;
            URL url = null;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(Changepassword.this);
                pDialog.setMessage("Changing... Please wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {

                String password = etoldpassword.getText().toString();
                String newpassword = etnewpassword.getText().toString();

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("sessionid", sessionid));
                params.add(new BasicNameValuePair("oldpassword", password));
                params.add(new BasicNameValuePair("newpassword", newpassword));
                JSONObject json = jParser.makeHttpRequest(url_change_password, "POST", params);
                return null;

            }
            protected void onPostExecute (String file_url){
                    // dismiss the dialog once product uupdated
                pDialog.dismiss();
                }
            }
        }
    }



               /* try {

                    // Enter URL address where your php file resides
                    url = new URL("http://" + Server.address + "/final/final/admin/changepasswordapp.php");


                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return "exception";
                }
                try {

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                    nameValuePairs.add(new BasicNameValuePair("password", newpasswordholder));

                    // Setup HttpURLConnection class to send and receive data from php and mysql
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(READ_TIMEOUT);
                    conn.setConnectTimeout(CONNECTION_TIMEOUT);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cookie", sessionid);

                    // setDoInput and setDoOutput method depict handling of both send and receive
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // Append parameters to URL
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("password", newpasswordholder);

                    String query = builder.build().getEncodedQuery();

                    // Open connection for sending data
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                    conn.connect();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                    return "exception";
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
                    return "exception";
                } finally {
                    conn.disconnect();
                }


            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Log.e("eas", result);

                Toast.makeText(Changepassword.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        AsyncChange sendPostReqAsyncTask = new AsyncChange();

        sendPostReqAsyncTask.execute(password);


    }*/

