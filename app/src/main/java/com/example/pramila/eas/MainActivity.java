package com.example.pramila.eas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";

    private EditText etemail, etpassword;
    private String username, pass;
    private Button btn;
    private static String URL_LOGIN = "";
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etemail = (EditText) findViewById(R.id.email);
        etpassword = (EditText) findViewById(R.id.password);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(loginPreferences.getBoolean("logged",false)){
           Intent intent = new Intent(MainActivity.this, Homepage.class);
            startActivity(intent);
            //MainActivity.this.finish();
        }
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if(saveLogin == true){
            loginPrefsEditor = loginPreferences.edit();
            etemail.setText(loginPreferences.getString("username", ""));
            etpassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }


        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(etemail.getText().length()<=0)
                {
                    Toast.makeText(MainActivity.this, "Enter email address", Toast.LENGTH_SHORT).show();
                }

                else if(etpassword.getText().length()<=0)
                {
                    Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }else {
                    checkLogin(view);
                }
               /* String e= etemail.getText().toString();

                SharedPreferences.Editor editor = loginPreferences.edit();
                editor.putString(Email, e);

                if((!etemail.getText().toString().equals(""))&&
                        (!etpassword.getText().toString().equals(""))){

                    if(saveLoginCheckBox.isChecked()){
                        Boolean booleanChecked = saveLoginCheckBox.isChecked();
                        SharedPreferences.Editor editor1  = loginPreferences.edit();
                        editor1.putString("pref_name", etemail.getText().toString());
                        editor1.putString("pref_pass",etpassword.getText().toString());
                        editor1.putBoolean("pref_check", booleanChecked);
                        editor1.apply();


                    }else{
                        loginPreferences.edit().clear().apply();
                    }
                    Log.i("username", etemail.getText().toString());
                    Log.i("password", etpassword.getText().toString());
                    Context context = getApplicationContext();

                    String string="Username:" + etemail.getText().toString()
                            + "\nPassword: "+ etpassword.getText().toString();
                      Intent intent=new Intent(getBaseContext(),Homepage.class);
                      intent.putExtra("ux",etemail.getText().toString());

                      startActivity(intent);




                }*/
            }


        });
        Button btnlearn = (Button) findViewById(R.id.btnlearn);
        btnlearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), learn.class);
                view.getContext().startActivity(intent);
            }
        });

    }

    public void onStart(){
        super.onStart();
        //getUser();
    }





   public void checkLogin(View arg0) {
        final String email = etemail.getText().toString();
        final String password = etpassword.getText().toString();
       Toast.makeText(getApplicationContext(),"result: connecting",Toast.LENGTH_LONG).show();
       if (arg0 == btn) {
               InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
               imm.hideSoftInputFromWindow(etemail.getWindowToken(), 0);

               username = etemail.getText().toString();
               pass= etpassword.getText().toString();


               if (saveLoginCheckBox.isChecked()) {
                   loginPrefsEditor.putBoolean("saveLogin", true);
                   loginPrefsEditor.putString("username", username);
                   loginPrefsEditor.putString("password", pass);
                   loginPrefsEditor.commit();
               } else {
                   loginPrefsEditor.clear();
                   loginPrefsEditor.commit();
               }
           }
       loginPreferences.edit().putBoolean("logged",true).apply();



        new AsyncLogin().execute(email, password);
    }

    private class AsyncLogin extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://192.168.1.119:8080/EmpAdmin/login.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
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
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
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
            Toast.makeText(getApplicationContext(),"result:"+result,Toast.LENGTH_LONG).show();
            Log.e("eas","Result: "+result);
            Log.e("eas","true found"+result);


            if (result.contains("true")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */


               Intent intent = new Intent(MainActivity.this, Homepage.class);
                startActivity(intent);
                MainActivity.this.finish();
                Log.e("eas","true found"+result);
                Toast.makeText(MainActivity.this, "Successful login", Toast.LENGTH_LONG).show();



            } else if (result.contains("false")) {

                // If username and password does not match display a error message
                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem."+result, Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();



        }

    }

   /* public void getUser() {

        SharedPreferences loginPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        String username = loginPreferences.getString(Email,null);
        String password = loginPreferences.getString(Password,null);
        }


    public void rememberMe(String email, String password) {
        getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit().putString(Email, email).putString(Password, password).commit();
    }*/
}












