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

    EditText oldpass, newpass;
    String sessionid;
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


        oldpass = (EditText) findViewById(R.id.oldpassword);
        newpass = (EditText) findViewById(R.id.newpassword);
        btnchange = (Button) findViewById(R.id.btnchange);

        btnchange.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             change_pass(view);
                                         }

                                         public void change_pass(View view) {
                                             ChangePass();
                                         }


                                         public void ChangePass() {
                                             if (password.equals(oldpass.getText().toString())) {

                                                 SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                                                 final String check = sharedPreferences.getString("username", "null");
                                                 RequestQueue queue = Volley.newRequestQueue(Changepassword.this);
                                                 String url = "http://" + Server.address + "/admin/changepasswordapp.php";

                                                 StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                                                         new Response.Listener<String>() {
                                                             @Override
                                                             public void onResponse(String response) {
                                                                 Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                                             }
                                                         },
                                                         new Response.ErrorListener() {
                                                             @Override
                                                             public void onErrorResponse(VolleyError error) {

                                                             }
                                                         }) {
                                                     @Override
                                                     protected Map<String, String> getParams() {
                                                         Map<String, String> params = new HashMap<String, String>();
                                                         params.put("username", check);
                                                         params.put("password", newpass.getText().toString());

                                                         return params;
                                                     }
                                                 };

                                                 queue.add(strRequest);
                                             } else {
                                                 Toast.makeText(getApplicationContext(), "Old Passwword is not Matching", Toast.LENGTH_LONG).show();
                                             }
                                         }
                                     });
    };
}

  //  }




        /*btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (oldpass.getText().length() <= 0) {
                    Toast.makeText(Changepassword.this, "Enter old password", Toast.LENGTH_SHORT).show();
                } else if (newpass.getText().length() <= 0) {
                    Toast.makeText(Changepassword.this, "Enter new password", Toast.LENGTH_SHORT).show();
                } else {
                    change_pass(view);
                }

            }


        });
    }

    public void change_pass(View view) {
        final String password = oldpass.getText().toString();
        final String newpassword = newpass.getText().toString();
        if (password.equals("")) {
            //session.createUserLoginSession("sessionid");
            Toast.makeText(this, "Password matched", Toast.LENGTH_LONG).show();
            //prefManager.writeLoginStatus(true);

        } else {
            Toast.makeText(this, "Password change failed", Toast.LENGTH_LONG).show();
        }

        new Asyncchangepass().execute(password, newpassword);
    }

    private class Asyncchangepass extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(Changepassword.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {

            String newpasswordholder=password;

            try {

                // Enter URL address where your php file resides
                url = new URL("http://" + Server.address + "/admin/changepasswordapp.php");


            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("password", newpasswordholder));
            try {


                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                      //  .appendQueryParameter("password", params[0])
                        .appendQueryParameter("password",newpasswordholder);

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
            pdLoading.dismiss();
            Toast.makeText(getApplicationContext(), "result:" + result, Toast.LENGTH_LONG).show();
            Log.e("eas", "Result: " + result);
            Log.e("eas", "true found" + result);


            if (result.contains("true")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                // Log.e("eas", "" + result);

                //  Toast.makeText(Changepassword.this, "Successful update", Toast.LENGTH_LONG).show();


           /* } else if (result.contains("false")) {

                // If username and password does not match display a error message
                Toast.makeText(Changepassword.this, "Invalid password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(Changepassword.this, "OOPs! Something went wrong. Connection Problem." + result, Toast.LENGTH_LONG).show();

            }
        }*/

       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();


        }
    }
}
*/
/*if (password.equals(oldpass.getText().toString())) {

            SharedPreferences preferences = getSharedPreferences("MYPREFS", MODE_PRIVATE);
            final String check = preferences.getString("myusername", "null");
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://" + Server.address + "/admin/changepasswordapp.php";
            StringRequest strRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", check);
                    params.put("pass", newpass.getText().toString());

                    return params;
                }
            };

            queue.add(strRequest);
        } else {
            Toast.makeText(getApplicationContext(), "Old Passwword is not Matching", Toast.LENGTH_LONG).show();
        }
    }
}*/


                //btnchange.setOnClickListener(new View.OnClickListener() {
                //@Override
                /*if (etoldpassword.getText().length() <= 0) {
                    Toast.makeText(Changepassword.this, "Enter correct password", Toast.LENGTH_SHORT).show();
                } else {
                    checkPassword(view);
                }*/




   /* public void checkPassword(View arg0) {
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

