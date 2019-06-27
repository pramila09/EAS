package com.example.pramila.eas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import java.net.URLEncoder;

public class BackgroundTask extends AsyncTask<String, Void, String> {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;

    @Override
    protected String doInBackground(String... params) {

        preferences = context.getSharedPreferences("MYPREFS",Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("flag","0");
        editor.commit();

        String urlLogin = "http://localhost:8080/login.php";
        String task = params[0];
        if(task.equals("login")){
            String loginEmail = params[1];
            String loginPassword = params[2];

            try{
                URL url = new URL (urlLogin);
                HttpURLConnection httpURLConnection =  (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                String myData = URLEncoder.encode("identifier_loginEmail","UTF-8")+"="+URLEncoder.encode(loginEmail,"UTF-8")+"&"
                +URLEncoder.encode("identifier_loginPassword","UTF-8")+"="+URLEncoder.encode(loginPassword,"UTF-8");
                bufferedWriter.write(myData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String dataResponse = "";
                String inputLine = "";
                while((inputLine = bufferedReader.readLine())!=null){
                    dataResponse += inputLine;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                System.out.println("!!!!!!!!!!!!!!!!!!!!!");
                System.out.println(dataResponse);

                editor.putString("flag","login");
                editor.commit();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        String flag = preferences.getString("flag","0");
        if(flag.equals("tblemployee")){
            Toast.makeText(context,s,Toast.LENGTH_LONG).show();
        }


        if(flag.equals("tblemployee")) {
            String test = "false";
            String emailid = "";
            //String password = "";
            String[] serverResponse = s.split("[,]");
            test = serverResponse[0];
            emailid = serverResponse[1];
            if (test.equals("true")) {
                editor.putString("email", emailid);
                editor.commit();
                Intent intent = new Intent(context, homepage.class);
                context.startActivity(intent);
            } else {
                display("Login failed.....", "That email and password do not match our records.");
            }

        }else{
            display("Login failed...","Something weird happened");


        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
    public void display(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);




}}
