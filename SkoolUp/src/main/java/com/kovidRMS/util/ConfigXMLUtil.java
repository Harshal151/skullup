package com.kovidRMS.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigXMLUtil extends PathConfigXMLUtil {

	HttpServletRequest request = ServletActionContext.getRequest();

	/*
	 * Fetching filePath from configuration XML file
	 */
	static String filePath = getConfigPath();

	// File fXmlFile = new File(
	// "C:\\Program Files\\Apache Software Foundation\\Tomcat
	// 7.0\\conf\\kovidRMS.xml");

	// File practiceXMLFile = new File(
	// "C:\\Program Files\\Apache Software Foundation\\Tomcat
	// 7.0\\conf\\PracticeConfig.xml");

	// File prescriptionXMLFile = new File(
	// "C:\\Program Files\\Apache Software Foundation\\Tomcat
	// 7.0\\conf\\Prescription.xml");

	// File billXMLFile = new File(
	// "C:\\Program Files\\Apache Software Foundation\\Tomcat
	// 7.0\\conf\\BillConfig.xml");

	File fXmlFile = new File(filePath + File.separator + "kovidRMS.xml");

	/*
	 * File practiceXMLFile = new File(filePath + File.separator +
	 * "PracticeConfig.xml");
	 * 
	 * File prescriptionXMLFile = new File(filePath + File.separator +
	 * "Prescription.xml");
	 * 
	 * File billXMLFile = new File(filePath + File.separator + "BillConfig.xml");
	 */

	/**
	 * 
	 * @return DBIP
	 */
	public String getDBIP() {

		String DBIP = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBIP = eElement.getElementsByTagName("DB-IP").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBIP;
	}

	/**
	 * 
	 * @return DBPort
	 */
	public String getDBPort() {
		String DBPort = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBPort = eElement.getElementsByTagName("DB-Port").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBPort;
	}

	/**
	 * 
	 * @return DBName
	 */
	public String getDBName() {
		String DBName = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBName = eElement.getElementsByTagName("DB-Name").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBName;
	}

	/**
	 * 
	 * @return DBUsername
	 */
	public String getDBUsername() {
		String DBUsername = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBUsername = eElement.getElementsByTagName("DB-Username").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBUsername;
	}

	/**
	 * 
	 * @return DBPassword
	 */
	public String getDBPassword() {
		String DBPassword = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBPassword = eElement.getElementsByTagName("DB-Password").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBPassword;
	}

	public LinkedHashMap<String, String> sortHashMap(HashMap<String, String> map) {

		LinkedHashMap<String, String> sortedMap = new LinkedHashMap<String, String>();

		List<String> mapKeys = new ArrayList<String>(map.keySet());
		List<String> mapValues = new ArrayList<String>(map.values());
		Collections.sort(mapValues);
		Collections.sort(mapKeys);

		// Sorting hashmap by values in alphabetical order
		Iterator<String> valueIt = mapValues.iterator();
		while (valueIt.hasNext()) {
			String val = valueIt.next();
			Iterator<String> keyIt = mapKeys.iterator();

			while (keyIt.hasNext()) {
				String key = keyIt.next();
				String comp1 = map.get(key);
				String comp2 = val;

				if (comp1.equals(comp2)) {
					keyIt.remove();
					sortedMap.put(key, val);
					break;
				}
			}
		}

		return sortedMap;

	}

	/**
	 * 
	 * @return
	 */
	public String getS3RDMLReportFilePath() {

		String reportPath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					reportPath = eElement.getElementsByTagName("S3-FILE-PATH").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return reportPath;
	}
	

	/**
	 * 
	 * @return
	 */
	public String getS3BucketName() {

		String bucketName = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketName = eElement.getElementsByTagName("S3BUCKET").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketName;
	}

	/**
	 * 
	 * @return
	 */
	public String getS3RDMLFilePath() {

		String logoPath = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					logoPath = eElement.getElementsByTagName("S3-LOGO-PATH").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return logoPath;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getS3BucketRegion() {

		String bucketRegion = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketRegion = eElement.getElementsByTagName("S3BUCKET-REGION").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketRegion;
	}

	
	/**
	 * 
	 * @return
	 */
	public String getAccessKey() {

		String bucketRegion = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketRegion = eElement.getElementsByTagName("ACCESS-KEY").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketRegion;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSecreteKey() {

		String bucketRegion = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovdRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					bucketRegion = eElement.getElementsByTagName("SECRET-KEY").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return bucketRegion;
	}
}
