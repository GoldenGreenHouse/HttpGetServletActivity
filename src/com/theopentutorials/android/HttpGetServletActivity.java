package com.theopentutorials.android;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HttpGetServletActivity extends Activity implements OnClickListener{

	Button button;
    TextView outputText;
 
    public static final String URL = "http://172.16.57.16:8080/CoHome-war/JSONServlet";
 
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        findViewsById();
 
        button.setOnClickListener(this);
    }
 
    private void findViewsById() {
        button = (Button) findViewById(R.id.button);
        outputText = (TextView) findViewById(R.id.outputTxt);
    }
 
    public void onClick(View view) {
        GetXMLTask task = new GetXMLTask();
        task.execute(new String[] { URL });
    }
 
    private class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }
 
        private String getOutputFromUrl(String url) {
            String output = null;
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                output = EntityUtils.toString(httpEntity);
                
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return output;
        }
 
        @Override
        protected void onPostExecute(String output) {
            outputText.setText(output);
        	
        }
    }

}
