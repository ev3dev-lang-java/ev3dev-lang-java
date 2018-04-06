package examples;

import ev3dev.sensors.Button;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class InternetStreamingDemo {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternetStreamingDemo.class);

	static List<Player> playersList = new ArrayList<>();

	private static Player player;

	public static void main(String[] args) {

		LOGGER.info("Example of Radio Streaming with EV3 Brick");

		registerEV3ButtonEvents();

		final List<String> radioStationList = getRadioStations();
		final String radioStation = getRadioStation(radioStationList);

		playRadioStation(radioStation);

		Button.UP.waitForPress();
	}

	private static void registerEV3ButtonEvents() {

		LOGGER.debug("Registering key listeners...");

		Button.ESCAPE.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key key) {
				LOGGER.info("Program finished.");
				System.exit(0);
			}

			@Override
			public void keyReleased(Key key) {
				//Empty
			}

		});

		Button.ENTER.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key key) {

				final List<String> radioStationList = getRadioStations();
				final String radioStation = getRadioStation(radioStationList);

				playRadioStation(radioStation);
			}

			@Override
			public void keyReleased(Key key) {
				//Empty
			}

		});

	}

	private static List<String> getRadioStations() {

		return Arrays.asList(
				"http://kpft.org:8000/kpfa_16",
				//"http://sumerki.su:8000/Sumerki16",
				"http://killradio.org:8000/killradio16.mp3"
				//"http://trentradio.ca:8800/lo-fi"
		);

	}

	private static String getRadioStation(final List<String> radioStationList) {

		final Random randomGenerator = new Random();
		final int index = randomGenerator.nextInt(radioStationList.size());
		final String radioStation = radioStationList.get(index);
		LOGGER.debug("Radio Station selectee: {}", radioStation);

		return radioStation;
	}

	private static void playRadioStation(final String radioStation) {

		try {
			final URLConnection urlConnection = new URL(radioStation).openConnection();
			urlConnection.connect();

			if(player != null) {
				player.close();
			}

			player = new Player(urlConnection.getInputStream ());
			player.play();

		} catch (JavaLayerException | IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		} catch (Exception e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	}

}