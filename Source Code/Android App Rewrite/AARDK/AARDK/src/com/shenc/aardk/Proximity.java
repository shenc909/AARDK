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

public class Proximity extends Activity implements SensorEventListener {
	
	private SensorManager proxSensorManager;
    private float prox;
    TextView proximity;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);
        
        proximity = (TextView) findViewById(R.id.proximity);
        
        proxSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
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
	        
	        proxSensorManager.registerListener(this,proxSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_FASTEST);
	    }
	 
	 @Override
	    protected void onPause() {
	        super.onPause();

	        proxSensorManager.unregisterListener(this);
	        
	    }
	 
	 @Override
	    public void onSensorChanged(SensorEvent event) {
	    	prox = event.values[0];
	    	prox = (float)Math.round(prox * 1000) / 1000;
	    	
	    	if(prox>5){
	    		proximity.setText("Far");
	    	}else{
	    		proximity.setText("Near");
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
