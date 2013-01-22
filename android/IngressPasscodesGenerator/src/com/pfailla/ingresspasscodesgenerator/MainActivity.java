package com.pfailla.ingresspasscodesgenerator;

import java.util.ArrayList;

import com.pfailla.engine.ModelPasscodeType1;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
* @Author: Pierluigi Failla <pierluigi.failla@gmail.com>
* https://sites.google.com/site/zeta/ingress-passcodes
* Started 21 Jan 2013
*
*/

public class MainActivity extends Activity {
	
	private String TAG = "IngressPasscodesGenerator";
	
	ListView listView;
	ArrayAdapter<String> arrayAdapter;
	ArrayList<String> passcodeList = new ArrayList<String>();
	
	ModelPasscodeType1 model1 = new ModelPasscodeType1();
	
    private class downloadTask extends AsyncTask<Void, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Fetching data...");
            this.dialog.show();

        }
        @Override
        protected String doInBackground(Void...params) {
            model1.load();
        	return null;
        }

        @Override
        protected void onPostExecute(String string) {
            if(this.dialog.isShowing())
                this.dialog.dismiss();
            
            addPasscode();
        }

    }
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        new downloadTask().execute();
        
        listView = (ListView) findViewById(R.id.listView);
        Button button_generate = (Button) findViewById(R.id.button_generate);
        button_generate.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(final View v) {
            	addPasscode();
            }
        });
        
        Button button_info = (Button) findViewById(R.id.button_info);
        button_info.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(final View v) {
        		Intent infoIntent = new Intent(Intent.ACTION_VIEW);
    			infoIntent.setData(Uri.parse("https://sites.google.com/site/zeta/ingress-passcodes"));
    			MainActivity.this.startActivity(infoIntent);
            }
        });
                    
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, passcodeList);
        listView.setAdapter(arrayAdapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
        		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
	        	sharingIntent.setType("text/plain");
	        	sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, passcodeList.get(position));
	        	startActivity(Intent.createChooser(sharingIntent, "Share using..."));
        	}
        });
        
    }
    
    public void addPasscode(){
    	passcodeList.add(model1.generate());
    	arrayAdapter.notifyDataSetChanged();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
    
}
