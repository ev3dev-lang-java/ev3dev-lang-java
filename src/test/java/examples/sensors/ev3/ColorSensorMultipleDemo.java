package examples.sensors.ev3;

import ev3dev.sensors.ev3.EV3ColorSensor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public @Slf4j class ColorSensorMultipleDemo {

	//Robot Configuration
	private static EV3ColorSensor color1 = new EV3ColorSensor(SensorPort.S1);
	private static EV3ColorSensor color2 = new EV3ColorSensor(SensorPort.S2);

	//Configuration
	private static int HALF_SECOND = 500;
	
	public static void main(String[] args) {

        for (int i = 0; i < 5; i++) {
            taskInParallel1();
            taskInParallel2();
        }

		Delay.msDelay(HALF_SECOND);
	}

    private static void taskInParallel1(){
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        final Future<Integer> future1 = executor.submit(() -> {
            runAmbientMode(color2);
            return 1;
        });

        final Future<Integer> future2 = executor.submit(() -> {
            runRGBMode(color1);
            return 1;
        });

        try {
            future1.get();
            future2.get();
            executor.shutdownNow();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static void taskInParallel2(){
        final ExecutorService executor = Executors.newFixedThreadPool(2);

        final Future<Integer> future1 = executor.submit(() -> {
            runAmbientMode(color1);
            return 1;
        });

        final Future<Integer> future2 = executor.submit(() -> {
            runRGBMode(color2);
            return 1;
        });

        try {
            future1.get();
            future2.get();
            executor.shutdownNow();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

	private static void runRGBMode(final EV3ColorSensor sensor){
		//RGB
		log.info("Switching to RGB Mode");
		SampleProvider sp = sensor.getRGBMode();

		int sampleSize = sp.sampleSize();
		float[] sample = new float[sampleSize];

		// Takes some samples and prints them
		for (int i = 0; i < 5; i++) {
			sp.fetchSample(sample, 0);
			log.info("N={} Sample={}", i, (int)sample[0]);
			log.info("N={} Sample={}", i, (int)sample[1]);
			log.info("N={} Sample={}", i, (int)sample[2]);
		}
	}

	private static void runAmbientMode(final EV3ColorSensor sensor){

		//Ambient Mode
		log.info("Switching to Ambient Mode");
		SampleProvider sp = sensor.getAmbientMode();

		int sampleSize = sp.sampleSize();
		float[] sample = new float[sampleSize];

		// Takes some samples and prints them
		for (int i = 0; i < 5; i++) {
			sp.fetchSample(sample, 0);
			log.info("N={} Sample={}", i, (int)sample[0]);
		}
	}

}
