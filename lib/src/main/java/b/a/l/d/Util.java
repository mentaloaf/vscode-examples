package b.a.l.d;

import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;
import java.net.HttpURLConnection;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Date;
import java.util.List;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Enumeration;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringBufferInputStream;

import java.math.BigInteger;

import java.nio.charset.StandardCharsets;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;

import org.apache.commons.lang.ArrayUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;



public class Util
{
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final int NUM_HTTP_RETRIES = 3;


	public static class XPath
	{
		private static javax.xml.xpath.XPath xpath = XPathFactory.newInstance().newXPath();
		private Document doc;

		public XPath(String xml)
		{
			try
			{
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				factory.setNamespaceAware(true);
				DocumentBuilder builder = factory.newDocumentBuilder();
				doc = builder.parse(new StringBufferInputStream(xml));

				//XPathFactory xfactory = XPathFactory.newInstance();
				//xpath = xfactory.newXPath();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public NodeList compile(String query)
		{
			NodeList nodes = null;
			try
			{
				XPathExpression expr = xpath.compile(query);
				Object result = expr.evaluate(doc, XPathConstants.NODESET);
				nodes = (NodeList) result;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			return nodes;
		}

		public static NodeList findNodes(final Document mydoc, final String query)
		{
			NodeList nodes = null;
			try
			{
				XPathExpression expr = xpath.compile(query);
				Object result = expr.evaluate(mydoc, XPathConstants.NODESET);
				nodes = (NodeList) result;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			return nodes;
		}
	}

	public static org.w3c.dom.Document getDocument(final String url) throws Exception
	{
		Exception lastException = null;
		for (int i = 0; i < NUM_HTTP_RETRIES; i++)
		{
			try
			{
				final org.jsoup.nodes.Document jsoupdoc = Jsoup.connect(url).get();
				final W3CDom w3cDom = new W3CDom();
				return w3cDom.fromJsoup(jsoupdoc);
			}
			catch (Exception e)
			{
				lastException = e;
			}
		}
		if (lastException != null)
			throw(lastException);

		return null;
	}
}
