package ev3dev.sensors.nxt;

import ev3dev.hardware.EV3DevFileSystem;
import ev3dev.hardware.EV3DevPlatform;
import fake_ev3dev.ev3dev.sensors.FakeBattery;
import fake_ev3dev.ev3dev.sensors.nxt.FakeNXTTemperatureSensor;
import java.io.IOException;
import java.util.Arrays;
import lejos.hardware.port.SensorPort;
import lejos.robotics.SampleProvider;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.BDDAssertions.then;

public class NXTTemperatureSensorTest {

    @Before
    public void resetTest() throws IOException {

        System.setProperty(EV3DevFileSystem.EV3DEV_TESTING_KEY, FakeBattery.EV3DEV_FAKE_SYSTEM_PATH);

        FakeBattery.resetEV3DevInfrastructure();
        new FakeBattery(EV3DevPlatform.EV3BRICK);
    }

    @Test
    public void given_sensor_when_call_getName_then_Ok() throws Exception {

        //Given
        FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);

        //When
        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        //Then
        then(temp1.getName()).isEqualTo("C");
    }

    @Test
    public void given_sensor_when_call_getAvailableModes_then_Ok() throws Exception {

        //Given
        FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);

        //When
        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        //Then
        var expectedModes = Arrays.asList("C", "F");
        then(temp1.getAvailableModes()).isEqualTo(expectedModes);
    }

    @Test
    public void given_sensor_when_switchToCelsiusMode_and_fetchSample_then_Ok() throws Exception {

        //Given
        float sensorRawValue = 100f;
        FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);
        fakeNXTTemperatureSensor.populateSensorData(sensorRawValue);

        //When
        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        //Then
        int expectedSampleSize = 1;
        SampleProvider sp = temp1.getCelsiusMode();
        then(sp.sampleSize()).isEqualTo(expectedSampleSize);

        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        float temperatureValue = sample[0];

        float expectedTemperature = sensorRawValue / 10f;
        then(temperatureValue).isEqualTo(expectedTemperature);
        then(temperatureValue).isGreaterThanOrEqualTo(-55.0f);
        then(temperatureValue).isLessThanOrEqualTo(128.0f);
    }

    @Test
    public void given_sensor_when_sensorReturnBadHighValues_then_Ok() throws Exception {

        //Given
        float sensorRawValue = 100000f;
        FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);
        fakeNXTTemperatureSensor.populateSensorData(sensorRawValue);

        //When
        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        //Then
        int expectedSampleSize = 1;
        SampleProvider sp = temp1.getCelsiusMode();
        then(sp.sampleSize()).isEqualTo(expectedSampleSize);

        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        float temperatureValue = sample[0];

        float expectedTemperature = sensorRawValue / 10f;
        then(temperatureValue).isEqualTo(Float.POSITIVE_INFINITY);
    }

    @Test
    public void given_sensor_when_sensorReturnBadLowValues_then_Ok() throws Exception {

        //Given
        float sensorRawValue = -100000f;
        FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);
        fakeNXTTemperatureSensor.populateSensorData(sensorRawValue);

        //When
        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        //Then
        int expectedSampleSize = 1;
        SampleProvider sp = temp1.getCelsiusMode();
        then(sp.sampleSize()).isEqualTo(expectedSampleSize);

        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        float temperatureValue = sample[0];

        float expectedTemperature = sensorRawValue / 10f;
        then(temperatureValue).isEqualTo(Float.NEGATIVE_INFINITY);
    }

    @Test
    public void given_sensor_when_switchToFahrenheitMode_and_fetchSamp_then_Ok() throws Exception {

        //Given
        float sensorRawValue = 100f;
        FakeNXTTemperatureSensor fakeNXTTemperatureSensor = new FakeNXTTemperatureSensor(EV3DevPlatform.EV3BRICK);
        fakeNXTTemperatureSensor.populateSensorData(sensorRawValue);

        //When
        NXTTemperatureSensor temp1 = new NXTTemperatureSensor(SensorPort.S1);

        //Then
        int expectedSampleSize = 1;
        SampleProvider sp = temp1.getFahrenheitMode();
        then(sp.sampleSize()).isEqualTo(expectedSampleSize);

        float [] sample = new float[sp.sampleSize()];
        sp.fetchSample(sample, 0);
        float temperatureValue = sample[0];

        float expectedTemperature = sensorRawValue / 10f;
        then(temperatureValue).isEqualTo(expectedTemperature);
        then(temperatureValue).isGreaterThanOrEqualTo(-55.0f);
        then(temperatureValue).isLessThanOrEqualTo(128.0f);
    }

}
