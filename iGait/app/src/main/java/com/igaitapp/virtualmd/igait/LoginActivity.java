package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private TextView welcomeTextView;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private CheckBox checkBoxEditText;
    private TextView showPasswordTextView;
    private Button submitBtn;
    private Button registerBtn;
    private Button forgotPasswordBtn;
    UserLogin ua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcomeTextView = (TextView) findViewById(R.id.welcomTextView);
        userNameEditText = (EditText) findViewById(R.id.userNameLoginEditText);
        passwordEditText = (EditText) findViewById(R.id.enterPasswordLoginEditText);
        checkBoxEditText = (CheckBox) findViewById(R.id.checkBox);
        showPasswordTextView = (TextView) findViewById(R.id.enterPasswordLoginEditText);
        submitBtn = (Button) findViewById(R.id.btnLoginSubmit);
        registerBtn = (Button) findViewById(R.id.btnRegister);
        forgotPasswordBtn = (Button)findViewById(R.id.btnForgotPassword);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = userNameEditText.getText().toString();
                String userPassword = passwordEditText.getText().toString();
                if ((userEmail.length()<=0) && (userPassword.length()>=1)) {
                    Toast.makeText(LoginActivity.this, "Please enter e-mail.", Toast.LENGTH_SHORT).show();
                    userNameEditText.requestFocus();
                } else if((userPassword.length()<=0) && (userEmail.length()>=1)) {
                    Toast.makeText(LoginActivity.this, "Please enter password.", Toast.LENGTH_SHORT).show();
                    passwordEditText.requestFocus();
                } else if((userEmail.length()>=3) && (userPassword.length()>=1)) {
                    Toast.makeText(LoginActivity.this, "Loging in...", Toast.LENGTH_SHORT).show();
                    new ServerLogin().execute("http://52.88.43.90:80/api/account/authentication", userEmail, userPassword);


                } else {
                    Toast.makeText(LoginActivity.this, "Please enter a valid e-mail and/or password.", Toast.LENGTH_SHORT).show();
                    userNameEditText.requestFocus();
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getPasswordIntent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(getPasswordIntent);
            }
        });



    }

    public  static String POST(String urlPaths, UserLogin login)  {
        InputStream inputStream = null;
        String result = "";

        try {
            Log.d("Server", "1111");
            URL url = new URL(urlPaths);
            Log.d("Server", "2222");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.d("Server", "3333");
            conn.setRequestMethod("POST");
            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", login.getUserName());
                Log.d("Server", "email is "+login.getUserName());

                jsonObject.put("password", login.getUserPassword());
                Log.d("Server", "password is "+ login.getUserPassword());

                json = jsonObject.toString();
                Log.d("Server", "The json is " + json);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            conn.setDoOutput(true);
            conn.setConnectTimeout(30000);
            // conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            int response = conn.getResponseCode();
            Log.d("Server", "The response returned is: " + response);



            // 9. receive response as inputStream
            inputStream = new BufferedInputStream(conn.getInputStream());

            // 10. convert inputstream to string
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Log.d("Server","The result in inputStream is "+ result);
            }
            else
                result = "Did not work!";

            return result;
        } catch (IOException e) {
            Log.d("Server", "IOException reading data " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (SecurityException e) {
            Log.d("Server", "sec exception: needs permisssion");
            return null;
        }


    }





    private class ServerLogin extends AsyncTask<String, Void, String> {
        //HttpURLConnection conn;
        String k;

        @Override
        protected String doInBackground(String... urls){
            //  Log.d("Server", "The response code was");
            ua =new UserLogin();
            String json = "";
            String user = urls[1];
            Log.d("Server", "name is" + user);
            ua.setUserName(user);

            String password = urls[2];
            Log.d("Server", "password is" + urls[2]);
            ua.setUserPassword(password);
            return POST(urls[0], ua);

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("Server", "The result is "+ result);
            // JSONObject mainObject = new JSONObject();
            String message;
            String in;
            try {
                JSONObject reader = new JSONObject(result);
                message = reader.getString("success").toString();

                if(message.equals("true")){

                    Log.d("Server","ok i m comg");
                    Toast.makeText(getBaseContext(), reader.getString("message"), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "Failure", Toast.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            //Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }

        private String connectToServer(String urlPath) throws IOException {
            try {
                Log.d("Server", "1111");
                URL url = new URL(urlPath);
                Log.d("Server", "2222");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                Log.d("Server", "3333");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setConnectTimeout(30000);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setInstanceFollowRedirects(false);
                conn.connect();
                int response = conn.getResponseCode();
                Log.d("Server", "The response returned is: " + response);
                // conn.disconnect();

                return "bravo";
            } catch (IOException e) {
                Log.d("Server", "IOException reading data " + e.getMessage());
                e.printStackTrace();
                return null;
            } catch (SecurityException e) {
                Log.d("Server", "sec exception: neds permisssion");
                return null;
            }


        }


    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}
