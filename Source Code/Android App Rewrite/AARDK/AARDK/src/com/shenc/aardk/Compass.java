package com.shenc.aardk;

import java.io.IOException;

import java.io.InputStream;
import java.io.OutputStream;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.Uart;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Compass extends Activity implements SensorEventListener {
	
	private SensorManager compassSensorManager;
	private int degree;
	TextView heading;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        
        heading = (TextView) findViewById(R.id.heading);
        
        compassSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.splash, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	    
	 @Override
	    protected void onResume() {
	        super.onResume();
	        overridePendingTransition(0,0);
	        
	        compassSensorManager.registerListener(this, compassSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
	    }
	 
	 @Override
	 protected void onPause() {
	        super.onPause();

	        // to stop the listener and save battery
	        compassSensorManager.unregisterListener(this);
	        
	    }
	 
	 @Override
	    public void onSensorChanged(SensorEvent event) {
	    		degree = Math.round(event.values[0]);
	    		
	    		heading.setText(Html.fromHtml(degree+"<sup><small>o</small></sup>"));
	    		
	    }
	    
	    @Override
	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	        // not in use
	    }
	 
	 public void compassStart(View view){
	    	Intent intent = new Intent(this , Compass.class);
	        startActivity(intent);
	    }
	 
	 public void accelStart(View view){
	    	Intent intent = new Intent(this , Accel.class);
	        startActivity(intent);
	    }
	    
	 public void proximityStart(View view){
	    	Intent intent = new Intent(this , Proximity.class);
	        startActivity(intent);
	    }
	 
	 public void luxStart(View view){
	    	Intent intent = new Intent(this , Lux.class);
	        startActivity(intent);
	    }
	 
	 public void magnetStart(View view){
	    	Intent intent = new Intent(this , Magnet.class);
	        startActivity(intent);
	    }
	 
	 
	 
	 
	 
	 
		
		
		
		
}
