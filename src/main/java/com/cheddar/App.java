package com.cheddar;

import com.cheddar.robinhood.advanced.Investments;
import com.cheddar.robinhood.client.RobinhoodClient;
import com.cheddar.robinhood.exception.RobinhoodException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class App {
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String DEFAULT_FILE_NAME = "config.properties";

	public static void main(String[] args) throws RobinhoodException {
		// Calculate options investment and return on sold options
		// Get amount of money invested into stock over the given time window
		// Get the total money transferred till now
		// Current investment divided in stocks and options
		String fileName = DEFAULT_FILE_NAME;

		if (args.length > 1) {
			fileName = args[1];
		}

		Map<String, String> prop = null;
		try {
			prop = getUserNameAndPassword(fileName);
		} catch (IOException e) {
			log.error("Unable to get the properties, please check the properties file " + fileName + " is " +
					"present!", e);
			System.exit(1);
		}

		RobinhoodClient robinhoodClient = new RobinhoodClient(prop.get(USERNAME), prop.get(PASSWORD));
		Investments investments = new Investments(robinhoodClient);
		investments.printTransferInfo();

	}

	private static Map<String, String> getUserNameAndPassword(String fileName) throws IOException {
		Map<String, String> properties = new HashMap<>();

		try (InputStream input = App.class.getClassLoader().getResourceAsStream((fileName))) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			properties.put(USERNAME, prop.getProperty(USERNAME));
			properties.put(PASSWORD, prop.getProperty(PASSWORD));


		} catch (IOException ex) {

			throw ex;
		}

		System.out.println("Read properties successfully");

		return properties;
	}
}
