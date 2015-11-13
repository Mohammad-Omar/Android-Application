package com.igaitapp.virtualmd.igait;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


public class RegisterActivity extends AppCompatActivity {
    TextView signUpTextView;
    EditText firstNameET;
    EditText lastnameET;
    EditText emailET;
    EditText mobileNoET;
    EditText officeNoET;
    EditText officeAddET;
    EditText passwordET;
    Button submitRegisterButton;
    NewUserDetails nud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        signUpTextView = (TextView) findViewById(R.id.signUptextView);
        firstNameET = (EditText) findViewById(R.id.fNameEditText);
        lastnameET = (EditText) findViewById(R.id.lNameEditText);
        emailET = (EditText) findViewById(R.id.emailEditText);
        mobileNoET = (EditText) findViewById(R.id.mobileEditText);
        officeNoET = (EditText) findViewById(R.id.officeNumberEditText);
        officeAddET = (EditText) findViewById(R.id.addressEditText);
        passwordET = (EditText) findViewById(R.id.passwordRegisterEditText);
        submitRegisterButton = (Button) findViewById(R.id.btnRegisterSubmit);
        //ServerConnect serverConnect = new ServerConnect();

        // serverConnect.execute("https://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com:80/api/account/register");
        //   serverConnect.execute("http://52.88.43.90:80/api/account/register");

        submitRegisterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 String json = "";
                String fname = firstNameET.getText().toString();
                Log.d("Server", "name is" + fname);
                String lname = lastnameET.getText().toString();
                String email = emailET.getText().toString();
                String mobile = mobileNoET.getText().toString();
                String officeNo = officeNoET.getText().toString();
                String address = officeAddET.getText().toString();
                String password = passwordET.getText().toString();
                new ServerConnect().execute("http://ubuntu@ec2-52-88-43-90.us-west-2.compute.amazonaws.com/api/account/register",fname,lname, email, password );
                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(registerIntent);

            }
        });

    }

    public  static String POST(String urlPaths, NewUserDetails nud)  {
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
            JSONObject jsonObjectName = new JSONObject();
            JSONObject jsonObjectDoctor = new JSONObject();
            try {
                jsonObject.put("email", nud.getEmail());
                jsonObject.put("password", nud.getPassword());
                jsonObjectName.put("first", nud.getFirst_name());
                jsonObjectName.put("last", nud.getLast_name());
                jsonObject.put("name", jsonObjectName);
                jsonObjectDoctor.put("doctor", jsonObject);
                json = jsonObjectDoctor.toString();

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


// ...

           /* OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();*/

            // 10. convert inputstream to string
            if(inputStream != null) {
                result = convertInputStreamToString(inputStream);
                Log.d("Server","The result in inputStream is "+ result);
            }
            else
                result = "Did not work!";
            // conn.disconnect();

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



    private class ServerConnect extends AsyncTask<String, Void, String> {
        //HttpURLConnection conn;
        String k;
        /*public void sendToServer(String j)throws IOException{


           Log.d("Server","the value is" + j);

            OutputStream out = new BufferedOutputStream(conn.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            writer.write(j);

        }*/
        @Override
        protected String doInBackground(String... urls){
            //  Log.d("Server", "The response code was");



            nud = new NewUserDetails();
            String json = "";
            String fname = urls[1];
            Log.d("Server", "name is" + fname);
            nud.setFirst_name(fname);
            String lname = urls[2];;
            nud.setLast_name(lname);
            String email = urls[3];;
            nud.setEmail(email);
            //String mobile = mobileNoET.getText().toString();
            //nud.setMobile_phone(mobile);
           // String officeNo = officeNoET.getText().toString();
           // nud.setOffice_phone(officeNo);
            //String address = officeAddET.getText().toString();
           // nud.setOffice_address(address);
            String password = urls[4];
            nud.setPassword(password);
            // RegisterActivity ra = new RegisterActivity();
            return POST(urls[0], nud);

           /* try {
                k = connectToServer(urls[0]);
            } catch (IOException e) {
                return "Unable to connect to erver";
            }
            return "";*/


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





/*
        URL url = new URL("http://www.android.com/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();
       URL url = new URL("http://www.android.com/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();

        HttpURLConnection con = (HttpURLConnection) ( new URL("http://www.android.com/")).openConnection();
        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.connect();
        */



/*
        String fname = firstNameET.getText().toString();
        String lname = lastnameET.getText().toString();
        String email = emailET.getText().toString();
        String mobile = mobileNoET.getText().toString();
        String officeNo = officeNoET.getText().toString();
        String address = officeAddET.getText().toString();
        String password = passwordET.getText().toString();
        String json ="";


        // 3. build jsonObject
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectName = new JSONObject();
        JSONObject jsonObjectDoctor = new JSONObject();

        try {
            jsonObject.put("email", email);
            jsonObject.put("password", password);
            jsonObjectName.put("first", fname);
            jsonObjectName.put("last", lname);
            jsonObject.put("name",jsonObjectName );
            jsonObjectDoctor.put("doctor", jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        json = jsonObjectDoctor.toString();
        Log.d("Server", "json obect is "+ json);
        */





/*
                email: { type: String, required: true, unique: true},
                password: { type: String, required: true},
                name: {
                    first: { type: String, required: true, trim: true},
                    last: { type: String, required: true, trim: true}

*/