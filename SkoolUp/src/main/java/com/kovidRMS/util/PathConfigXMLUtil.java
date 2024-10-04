package com.kovidRMS.util;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts2.ServletActionContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PathConfigXMLUtil {
	static HttpServletRequest request = ServletActionContext.getRequest();

	static String configPath = null;

	public static String getConfigPath() {

		ServletContext context = request.getServletContext();

		String realPath = context.getRealPath("/");

		System.out.println("real path is ::::: " + realPath);

		File configXMLFile = new File(realPath + File.separator + "configuration.xml");

		System.out.println("...." + configXMLFile.getAbsolutePath());

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document doc = dbBuilder.parse(configXMLFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("kovidRMS-configuration");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					configPath = eElement.getElementsByTagName("config-file-path").item(0).getTextContent();
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return configPath;
	}
}
