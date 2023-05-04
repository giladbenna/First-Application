package com.example.myapplication.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.myapplication.Interfaces.StepCallback;

public class StepDetector {
    private Sensor sensor;

    private SensorManager sensorManager;

    private StepCallback stepCallback;

    private long timestamp = 0;

    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, StepCallback stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        initEventListener();

    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                // float y = event.values[1];

                calculateStep(x);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    private void calculateStep(float x) {
        if (System.currentTimeMillis() - timestamp > 1000) {
            timestamp = System.currentTimeMillis();
            if (x > 10.0) {
                if (stepCallback != null)
                    stepCallback.stepXRight();
            }
            if(x < 10.0){
                if (stepCallback != null)
                    stepCallback.stepXLeft();
            }
        }
    }


    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }
}
