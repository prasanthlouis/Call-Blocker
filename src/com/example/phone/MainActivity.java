package com.example.phone;

import java.lang.reflect.Method;



import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
int i=0;
RelativeLayout back;
ArrayAdapter<String> listAdapter;
ListView listView;
ArrayAdapter<String> listAdapter1;
ListView listView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		TelephonyMgr.listen(new TeleListener(), PhoneStateListener.LISTEN_CALL_STATE);
		listView=(ListView) findViewById(R.id.black);
		listView1=(ListView) findViewById(R.id.ListView01);
		listAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,0);
		listAdapter1= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,0);
		listView.setAdapter(listAdapter);
		listView1.setAdapter(listAdapter1);
		back=(RelativeLayout) findViewById(R.id.back1);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public void wl(View v)
	{
		EditText ed=(EditText) findViewById(R.id.editText1);
		String s= ed.getText().toString();
		listAdapter.add(s);
	
		
	}
	public void bl(View v)
	{
		EditText ed=(EditText) findViewById(R.id.editText1);
		String s= ed.getText().toString();
		listAdapter1.add(s);
	
		
	}
	public void accept(View v)
	{
		TextView tv=(TextView) findViewById(R.id.tv);
		tv.setText("ACCEPTING CALLS\nOnly Numbers on the\nBlackList will not be\nautomatically accepted");
		i=2;
		back.setBackgroundResource(R.drawable.accept);
	}
	public void reject(View v)
	{
		i=1;
		TextView tv=(TextView) findViewById(R.id.tv);
		tv.setText("REJECTING CALLS\nOnly Numbers on the\nWhiteList will be Accepted");
		back.setBackgroundResource(R.drawable.reject);
	}
	public void emer(View v)
	{
		Uri number = Uri.parse("tel:5551234");
		Intent callIntent = new Intent(Intent.ACTION_CALL, number);
		
		startActivity(callIntent);
	}
//	public class PhoneCallReceiver extends BroadcastReceiver {
	//	private ITelephony telephonyService;
	//}

	class TeleListener extends PhoneStateListener {
		  public void onCallStateChanged(int state, String incomingNumber) {
		   super.onCallStateChanged(state, incomingNumber);
		   switch (state) {
		   case TelephonyManager.CALL_STATE_IDLE:
		    // CALL_STATE_IDLE;
		    Toast.makeText(getApplicationContext(), "CALL_STATE_IDLE",
		      Toast.LENGTH_LONG).show();
		    break;
		   case TelephonyManager.CALL_STATE_OFFHOOK:
		    // CALL_STATE_OFFHOOK;
		    Toast.makeText(getApplicationContext(), "CALL_STATE_OFFHOOK",
		      Toast.LENGTH_LONG).show();
		    break;
		   case TelephonyManager.CALL_STATE_RINGING:
		    // CALL_STATE_RINGING
			   if(i==1)
			   {
				   
				   
				   if(listAdapter.getCount()>=0)
				   {
					   Toast.makeText(getApplicationContext(), ""+listAdapter.getPosition(incomingNumber), Toast.LENGTH_SHORT).show();
					   if(listAdapter.getPosition(incomingNumber)!=-1)
				   Toast.makeText(getApplicationContext(), "Call is on WhiteList", Toast.LENGTH_SHORT).show();
					   
					   else
						   endcal();
				   }
				 else
				  
				   endcal();
			   
			   }
			   else if(i==2)
			   {
				   
				   if(listAdapter1.getCount()>=0)
				   {
					   Toast.makeText(getApplicationContext(), ""+listAdapter1.getPosition(incomingNumber), Toast.LENGTH_SHORT).show();
					   if(listAdapter1.getPosition(incomingNumber)!=-1)
				   Toast.makeText(getApplicationContext(), "Call is on BlackList", Toast.LENGTH_SHORT).show();
					   else
						   acceptcall();
				   }
				 else
				  
			   
				   acceptcall();
			   }
		    Toast.makeText(getApplicationContext(), incomingNumber,
		      Toast.LENGTH_LONG).show();
		    Toast.makeText(getApplicationContext(), "CALL_STATE_RINGING",
		      Toast.LENGTH_LONG).show();
		    break;
		   default:
		    break;
		   }
		  }

		private void endcal() {
			TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
	try{
			
		Class c = Class.forName(tm.getClass().getName());
		Method m = c.getDeclaredMethod("getITelephony");
		m.setAccessible(true);
		Object telephonyService = m.invoke(tm); // Get the internal ITelephony object
		c = Class.forName(telephonyService.getClass().getName()); // Get its class
		m = c.getDeclaredMethod("endCall"); // Get the "endCall()" method
		m.setAccessible(true); // Make it accessible
		m.invoke(telephonyService); // invoke endCall()
		Toast.makeText(getApplicationContext(), "CALL IS REJECTED", Toast.LENGTH_SHORT).show();
	}
	catch (Exception e){}// TODO Auto-generated method stub
			
		}
		private void acceptcall() {
		TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		
	try{
		Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
	    buttonUp.putExtra(Intent.EXTRA_KEY_EVENT, 
	      new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
	    getApplicationContext().sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");
		Toast.makeText(getApplicationContext(), "CALL IS accepted", Toast.LENGTH_SHORT).show();
	}
	catch (Exception e){}// TODO Auto-generated method stub
			
		}
		 }
	}


