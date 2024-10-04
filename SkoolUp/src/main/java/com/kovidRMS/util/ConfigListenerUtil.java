package com.kovidRMS.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigListenerUtil extends PathConfigListenerUtil{

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public String getDBIP(String realPath) {

		String DBIP = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator
					+ "kovidRMS.xml"));

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovidRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBIP = eElement.getElementsByTagName("DB-IP").item(0)
							.getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBIP;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public String getDBPort(String realPath) {
		String DBPort = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator
					+ "kovidRMS.xml"));

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovidRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBPort = eElement.getElementsByTagName("DB-Port").item(0)
							.getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBPort;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public String getDBName(String realPath) {
		String DBName = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator
					+ "kovidRMS.xml"));

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovidRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBName = eElement.getElementsByTagName("DB-Name").item(0)
							.getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBName;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public String getDBUsername(String realPath) {
		String DBUsername = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator
					+ "kovidRMS.xml"));

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovidRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBUsername = eElement.getElementsByTagName("DB-Username")
							.item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBUsername;
	}

	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public String getDBPassword(String realPath) {
		String DBPassword = null;

		String filePath1 = getConfigPath(realPath);

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new File(filePath1 + File.separator
					+ "kovidRMS.xml"));

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovidRMS-config");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					DBPassword = eElement.getElementsByTagName("DB-Password")
							.item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return DBPassword;
	}

}