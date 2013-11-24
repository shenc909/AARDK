package com.more.asquared;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.Uart;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.BaseIOIOLooper;
import ioio.lib.util.IOIOLooper;
import ioio.lib.util.android.IOIOActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * This is the main activity of the HelloIOIO example application.
 * 
 * It displays a toggle button on the screen, which enables control of the
 * on-board LED. This example shows a very simple usage of the IOIO, by using
 * the {@link IOIOActivity} class. For a more advanced use case, see the
 * HelloIOIOPower example.
 */
public class MainActivity extends IOIOActivity implements SensorEventListener{
	private ToggleButton button_;
    private SensorManager mSensorManager;
    TextView tvHeading;
    private int degree;

	/**
	 * Called when the activity is first created. Here we normally initialize
	 * our GUI.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button_ = (ToggleButton) findViewById(R.id.button);
		
        // TextView that will tell the user what degree is he heading
        tvHeading = (TextView) findViewById(R.id.tvHeading);
        
        // initialize your android device sensor capabilities
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tvHeading.setText("Please Connect the IOIO");
	}
	
	/**
	 * This is where we set up our sensors for use later. This includes onPause, onResume, onSensorChanged and onAccuracyChanged.
	 */
	@SuppressWarnings("deprecation")
	@Override
    protected void onResume() {
        super.onResume();

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }
	
    @Override
    protected void onPause() {
        super.onPause();

        // to stop the listener and save battery
        mSensorManager.unregisterListener(this);
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {

        // get the angle around the z-axis rotated
        degree = Math.round(event.values[0]);
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }
	
	/**
	 * This is the thread on which all the IOIO activity happens. It will be run
	 * every time the application is resumed and aborted when it is paused. The
	 * method setup() will be called right after a connection with the IOIO has
	 * been established (which might happen several times!). Then, loop() will
	 * be called repetitively until the IOIO gets disconnected.
	 */
	class Looper extends BaseIOIOLooper {
		
		/** The on-board LED. */
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
	    private int requested;

		/**
		 * Called every time a connection with IOIO has been established.
		 * Typically used to open pins.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#setup()
		 */
		@Override
		protected void setup() throws ConnectionLostException {
			led_ = ioio_.openDigitalOutput(0, true);
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

		/**
		 * Called repetitively while the IOIO is connected.
		 * 
		 * @throws ConnectionLostException
		 *             When IOIO connection is lost.
		 * 
		 * @see ioio.lib.util.AbstractIOIOActivity.IOIOThread#loop()
		 */
		@Override
		public void loop() throws ConnectionLostException {
			
			/**
			* Diagnostics
			* Used to determine if IOIO is responsive to commands sent by the android device
			*/
			led_.write(!button_.isChecked());
			
			/**
			 * Request Feature (Get request from IOIO and output required information)
			 * 
			 * Legend:
			 * 
			 * 1 -- Compass
			 */
			try {
				if(in.available()!=0){
				requested = in.read();
				requested=requested-48;
				}
				if(button_.isChecked()){
			        runOnUiThread(new Runnable(){
			            @Override
			            public void run() {
			                tvHeading.setText(String.valueOf(requested));

			            }   
			        });
				}else{
			        runOnUiThread(new Runnable(){
			            @Override
			            public void run() {
			                tvHeading.setText(requested + " lol");

			            }
			        });
				}
				
				switch (requested) {
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
	}

	/**
	 * A method to create our IOIO thread.
	 * 
	 * @see ioio.lib.util.AbstractIOIOActivity#createIOIOThread()
	 */
	@Override
	protected IOIOLooper createIOIOLooper() {
		return new Looper();
	}
}