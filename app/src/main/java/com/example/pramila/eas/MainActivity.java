package com.example.pramila.eas;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btn;
    Context context;
    String NAME, PASSWORD;
    String NAme = null, PAssword = null;
    int tmp;
    SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editText = (EditText)findViewById(R.id.Edittextemail);
        EditText editText1 =(EditText)findViewById(R.id.Edittextpassword);

        final EditText Edittextemail = (EditText)findViewById(R.id.Edittextemail);
        final EditText Edittextpassword =(EditText)findViewById(R.id.Edittextpassword);

        preference = this.getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);




        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Edittextemail.getText().length()<=0)
                {
                    Toast.makeText(MainActivity.this, "Enter email address", Toast.LENGTH_SHORT).show();
                }

                else if(Edittextpassword.getText().length()<=0)
                {
                    Toast.makeText(MainActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(view.getContext(), homepage.class);
                    view.getContext().startActivity(intent);
                }
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




       // BackGround b = new BackGround();
        //b.execute(type, EMAIL, PASSWORD);



    }

    }
   /* class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String name = params[0];
            String password = params[1];
            String data="";
            int tmp;

            try {
                URL url = new URL("http://localhost:8080/login.php");
                String urlParams = "username="+name+"&password="+password;

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                os.write(urlParams.getBytes());
                os.flush();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                while((tmp=is.read())!=-1){
                    data+= (char)tmp;
                }

                is.close();
                httpURLConnection.disconnect();

                return data;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception: "+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            String err=null;
            try {
                JSONObject root = new JSONObject(s);
                JSONObject tblemployee = root.getJSONObject("tblemployee");
                NAme = tblemployee.getString("username");
                PAssword = tblemployee.getString("password");

            } catch (JSONException e) {
                e.printStackTrace();
                err = "Exception: "+e.getMessage();
            }

            Intent i = new Intent(context, homepage.class);
            i.putExtra("name", NAme);
            i.putExtra("password", PAssword);

            i.putExtra("err", err);
            startActivity(i);

        }
    }
}
*/

