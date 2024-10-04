package com.kovidRMS.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PathConfigListenerUtil {

	/**
	 * 
	 * @param realPath
	 * @return
	 */
	public static String getConfigPath(String realPath) {

		String configPath = "";

		File configXMLFile = new File(realPath + "configuration.xml");

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(configXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc
					.getElementsByTagName("kovidRMS-configuration");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					configPath = eElement
							.getElementsByTagName("config-file-path").item(0)
							.getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return configPath;
	}

}
