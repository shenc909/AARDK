package com.shenc.aardk;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import ioio.lib.api.DigitalOutput;
import ioio.lib.api.IOIO;
import ioio.lib.api.Uart;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOService;

public class AARDKService extends IOIOService implements SensorEventListener {
	
	 private SensorManager mSensorManager;
	 private SensorManager accelSensorManager;
	 private SensorManager luxSensorManager;
	 private SensorManager magSensorManager;
	 private SensorManager proxSensorManager;
	 private int degree;
	 private int accelx;
	 private int accely;
	 private int accelz;
	 private int magx;
	 private int magy;
	 private int magz;
	 private int prox;
	 private int lux;
	
	 @Override
	 public void onSensorChanged(SensorEvent event) {
	    if (event.sensor.getType() == Sensor.TYPE_ORIENTATION){
	    	// get the angle around the z-axis rotated
	    	degree = Math.round(event.values[0]);
	    }
	    if(event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
	    	accelx = Math.round(event.values[0]*1000);
	    	accely = Math.round(event.values[1]*1000);
	    	accelz = Math.round(event.values[2]*1000);
	    }
	    if(event.sensor.getType() == Sensor.TYPE_LIGHT){
	    	lux = Math.round(event.values[0]);
	    }
	    if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
	    	prox = Math.round(event.values[0]);
	    	if(prox>5){
	    		prox=0;
	    	}else{
	    		prox=1;
	    	}
	    }
	    if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
	    	magx = Math.round(event.values[0]);
	    	magy = Math.round(event.values[1]);
	    	magz = Math.round(event.values[2]);
	    }
	}
	 
	 @Override
	 public void onAccuracyChanged(Sensor sensor, int accuracy) {
		 // not in use
	 }
	 
	 
	@Override
	protected IOIOLooper createIOIOLooper() {
		return new BaseIOIOLooper() {
			private DigitalOutput led_;
			
			/**
			 * Initialization of required variables
			 */
			private int rxPin;
			private int txPin;
			private int baud;
			private Uart uart;
		    private InputStream in;
		    private OutputStream out;
		    private int function;
		    //private int command;
		    //private int startbyte;
		    //private int endbyte;
		    private int accept;
		    //private int relativehead;

			@Override
			protected void setup() throws ConnectionLostException, InterruptedException {
				led_ = ioio_.openDigitalOutput(IOIO.LED_PIN);
				
				rxPin = 9;
				txPin = 10;
				baud = 9600;
				uart = ioio_.openUart(rxPin, txPin, baud, Uart.Parity.NONE, Uart.StopBits.ONE);
				in = uart.getInputStream();
		        out = uart.getOutputStream();
		        
		        try {
					out.write(("hello world").getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
			}

			@Override
			public void loop() throws ConnectionLostException, InterruptedException {
				/**
				* Diagnostics
				* Used to determine if IOIO is responsive to commands sent by the android device
				*/
				
				/**
				 * Request Feature (Get request from IOIO and output required information)
				 * 
				 * Legend:
				 * 
				 * 1 -- Compass
				 */
				try {
					/*if(in.available()!=0){
						function = in.read();
						function=function-48;
						function=1;
					}*/
					
					
					in = uart.getInputStream();
			        out = uart.getOutputStream();
					
					if(in.available()!=0){
						function=in.read();
						//function=function-48;
						//uart.close();
					}
					
					
					/*
					 * Debugging
					 * Brute Force Area Below 
					 */
					
					accept = 1;
					//function = 1;
					
					if(accept==1){
						switch (function) {
							case 1:
								int marker = 0;
								int tempp = degree;
								if(tempp==0){
									tempp=360;
								}
								if(tempp>255){
									marker = 1;
									tempp=tempp-255;
								}
							
								if (marker==1){
									out.write(0);
								}
								out.write(tempp);
							break;
							
							case 2:
								out.write(3);
								out.write(accelx);
							break;
									
							case 3:
								out.write(3);
								out.write(accely);
							break;
									
							case 4:
								out.write(3);
								out.write(accelz);
							break;
							
							case 5:
								out.write(prox);
							break;
							
							case 6:
								out.write(lux);
							break;
							
							case 7:
								out.write(3);
								out.write(magx);
							break;
							
							case 8:
								out.write(3);
								out.write(magy);
							break;
							
							case 9:
								out.write(3);
								out.write(magz);
							break;
						}
					}
					
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				/**
				 * Pauses code for a while lol
				 */
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
			}
			}
		};
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        luxSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        proxSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_FASTEST);
        accelSensorManager.registerListener(this,accelSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_FASTEST);
        luxSensorManager.registerListener(this, luxSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),SensorManager.SENSOR_DELAY_FASTEST);
        magSensorManager.registerListener(this, magSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),SensorManager.SENSOR_DELAY_FASTEST);
        proxSensorManager.registerListener(this, proxSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_FASTEST);
        
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		if (intent != null && intent.getAction() != null && intent.getAction().equals("stop")) {
			// User clicked the notification. Need to stop the service.
			nm.cancel(0);
	        mSensorManager.unregisterListener(this);
	        accelSensorManager.unregisterListener(this);
	        luxSensorManager.unregisterListener(this);
	        proxSensorManager.unregisterListener(this);
	        magSensorManager.unregisterListener(this);
			stopSelf();
		} else {
			// Service starting. Create a notification.
		Notification notification = new Notification(R.drawable.ic_launcher, "AARDK is Sending Values...", System.currentTimeMillis());
		notification.setLatestEventInfo(this, "AARDK Service", "Tap here to stop", PendingIntent.getService(this, 0, new Intent("stop", null, this, this.getClass()), 0));
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		nm.notify(0, notification);
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
