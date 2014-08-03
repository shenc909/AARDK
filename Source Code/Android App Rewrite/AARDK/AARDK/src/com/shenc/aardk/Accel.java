package com.shenc.aardk;

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

public class Accel extends Activity implements SensorEventListener{
	
    private SensorManager accelSensorManager;
    private float accelx;
    private float accely;
    private float accelz;
    TextView accelX;
    TextView accelY;
    TextView accelZ;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel);
        
        accelX = (TextView) findViewById(R.id.accelx);
        accelY = (TextView) findViewById(R.id.accely);
        accelZ = (TextView) findViewById(R.id.accelz);
        
        accelSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
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
	        
	        accelSensorManager.registerListener(this,accelSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_UI);
	        
	    }
	 
	 @Override
	    protected void onPause() {
	        super.onPause();

	        accelSensorManager.unregisterListener(this);
	        
	    }
	 
	 @Override
	    public void onSensorChanged(SensorEvent event) {
	    	accelx = event.values[0];
	    	accely = event.values[1];
	    	accelz = event.values[2];
	    	accelx = (float)Math.round(accelx * 1000) / 1000;
	    	accely = (float)Math.round(accely * 1000) / 1000;
	    	accelz = (float)Math.round(accelz * 1000) / 1000;
	    	if(accelx<0){
	    		accelX.setText(Html.fromHtml(accelx+"m/s"+"<sup><small>2</small></sup>"));
	    	}else{
	    		accelX.setText(Html.fromHtml("&nbsp;"+accelx+"m/s"+"<sup><small>2</small></sup>"));
	    	}
	    	if(accely<0){
	    		accelY.setText(Html.fromHtml(accely+"m/s"+"<sup><small>2</small></sup>"));
	    	}else{
	    		accelY.setText(Html.fromHtml("&nbsp;"+accely+"m/s"+"<sup><small>2</small></sup>"));
	    	}
	    	if(accelz<0){
	    		accelZ.setText(Html.fromHtml(accelz+"m/s"+"<sup><small>2</small></sup>"));
	    	}else{
	    		accelZ.setText(Html.fromHtml("&nbsp;"+accelz+"m/s"+"<sup><small>2</small></sup>"));
	    	}
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
