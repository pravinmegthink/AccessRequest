package com.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;


public class Utils {
	
	private static final Logger logger = LogManager.getLogger(Utils.class.getName());
	
	public String readProprtyFromFle(String property) {
		String propVal = "";
		try {
			Properties properties = loadPropertyFromFile();
			if(null != properties) {
				propVal = properties.getProperty(property);
			}else {
				logger.error("Failed retrieve property.");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return propVal;	
	}

	public Properties loadPropertyFromFile() throws IOException {
		// TODO Auto-generated method stub
		StandardPBEStringEncryptor encyptor = new StandardPBEStringEncryptor();
		
		Properties encProperties = null;
		encProperties = loadEncPropertyFromProperties();
		encyptor.setPassword(encProperties.getProperty("ENC_KEY"));
		encyptor.setAlgorithm(encProperties.getProperty("ENC_ALGO"));
		
		Properties properties = new EncryptableProperties(encyptor);
		InputStream file = null;
		try {
			file = getClass().getClassLoader().getResourceAsStream(Constants.PROP_FILE_NAME);
			properties.load(file);
		}catch (Exception e) {
			throw new IOException();
		}finally {
			if(file != null) {
				file.close();
			}
		}

		return properties;
	}

	private Properties loadEncPropertyFromProperties() throws IOException {
		Properties properties = new Properties();
		InputStream file = null;
		try {
			file = getClass().getClassLoader().getResourceAsStream(Constants.ENC_PROP_FILE_NAME);
			properties.load(file);
		}catch (Exception e) {
			throw new IOException();
		}finally {
			if(file != null) {
				file.close();
			}
		}
		return properties;
	}

}
