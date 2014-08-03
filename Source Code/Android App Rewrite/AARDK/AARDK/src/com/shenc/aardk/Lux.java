package com.shenc.aardk;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Lux extends Activity implements SensorEventListener {
	
	private SensorManager luxSensorManager;
	private int lux;
	TextView Lux;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lux);
        
        Lux = (TextView) findViewById(R.id.lux);
        
        luxSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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
	        
	        luxSensorManager.registerListener(this, luxSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_FASTEST);
	    }
	 
	 @Override
	    protected void onPause() {
	        super.onPause();
	        
	        luxSensorManager.unregisterListener(this);
	    }
	    
	    @Override
	    public void onSensorChanged(SensorEvent event) {
	    	lux = Math.round(event.values[0]);
	    	Lux.setText(lux+" lux");
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
