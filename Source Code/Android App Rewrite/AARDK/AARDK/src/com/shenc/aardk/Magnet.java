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

public class Magnet extends Activity implements SensorEventListener {
	
	private SensorManager magSensorManager;
    private float magx;
    private float magy;
    private float magz;
    TextView magX;
    TextView magY;
    TextView magZ;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magnet);
        
        magX = (TextView) findViewById(R.id.magx);
        magY = (TextView) findViewById(R.id.magy);
        magZ = (TextView) findViewById(R.id.magz);
        
        magSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
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
	        
	        magSensorManager.registerListener(this,magSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_FASTEST);
	    }
	 
	 @Override
	    protected void onPause() {
	        super.onPause();

	        magSensorManager.unregisterListener(this);
	        
	    }
	 
	 @Override
	    public void onSensorChanged(SensorEvent event) {
	    	magx = event.values[0];
	    	magy = event.values[1];
	    	magz = event.values[2];
	    	magx = (float)Math.round(magx * 1000) / 1000;
	    	magy = (float)Math.round(magy * 1000) / 1000;
	    	magz = (float)Math.round(magz * 1000) / 1000;
	    	
	    	if(magx<0){
	    		magX.setText(magx+"μT");
	    	}else{
	    		magX.setText(" "+magx+"μT");
	    	}
	    	if(magy<0){
	    		magY.setText(magy+"μT");
	    	}else{
	    		magY.setText(" "+magy+"μT");
	    	}
	    	if(magz<0){
	    		magZ.setText(magz+"μT");
	    	}else{
	    		magZ.setText(" "+magz+"μT");
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
