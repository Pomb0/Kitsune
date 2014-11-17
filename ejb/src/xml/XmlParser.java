package xml;

/**
 * User: Jaime
 * Date: 16/11/2014 - 07:50
 */

import bean.Article;
import bean.Author;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class XmlParser {
	public List<Article> parse(String xml) {
		List<Article> list = new LinkedList<>();
		DocumentBuilder dBuilder = null;
		Document doc = null;
		NodeList articles = null;

		int j, k;
		NodeList nlist;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) { e.printStackTrace(); }

		if (dBuilder != null) {
			try {
				doc = dBuilder.parse(new InputSource(new StringReader(xml)));
			} catch (SAXException | IOException e) { e.printStackTrace(); }
		}

		if (doc != null) {
			doc.getDocumentElement().normalize();
			articles = doc.getElementsByTagName("article");
		}

		for (j = 0; articles != null && j < articles.getLength(); j++) {
			Article temp = new Article();
			Element element = (Element) articles.item(j);

			temp.setTitle(getValue(element, "title"));
			temp.setUrl(getValue(element, "url"));
			temp.setTopic(getValue(element, "topic"));
			temp.setDate(new Date(getDate(element)));

			if(element.getElementsByTagName("media").item(0) != null) {
				nlist = element.getElementsByTagName("media").item(0).getChildNodes();
				List<String> images = new LinkedList<>();
				List<String> videos = new LinkedList<>();
				for (k = 0; k < nlist.getLength(); k++) {
					if (nlist.item(k).getNodeName().equals("video")) {
						videos.add(nlist.item(k).getTextContent());
					}
					if (nlist.item(k).getNodeName().equals("image")) {
						images.add(nlist.item(k).getTextContent());
					}
				}
				temp.setImages(images);
				temp.setVideos(videos);
			}

			if(element.getElementsByTagName("author").item(0) != null) {
				nlist = element.getElementsByTagName("author").item(0).getChildNodes();
				LinkedList<Author> author = new LinkedList<>();
				for (k = 0; k < nlist.getLength(); k++) {
					if (nlist.item(k).getNodeName().equals("value")) {
						author.add(new Author(0,nlist.item(k).getTextContent()));
					}
				}
				temp.setAuthors(author);
			}

			if(element.getElementsByTagName("body").item(0) != null) {
				nlist = element.getElementsByTagName("body").item(0).getChildNodes();
				StringBuilder body = new StringBuilder();
				for (k = 0; k < nlist.getLength(); k++) {
					if (nlist.item(k).getNodeName().equals("value")) {
						body.append(nlist.item(k).getTextContent());
						body.append("\n");
					}
				}
				temp.setBody(body.toString());
			}

			if(element.getElementsByTagName("highlights").item(0) != null) {
				nlist = element.getElementsByTagName("highlights").item(0).getChildNodes();
				LinkedList<String> highlights = new LinkedList<>();
				for (k = 0; k < nlist.getLength(); k++) {
					if (nlist.item(k).getNodeName().equals("value")) {
						highlights.add(nlist.item(k).getTextContent());
					}
				}
				temp.setHighlights(highlights);
			}

			list.add(temp);
		}
		return list;
	}


	public String  getValue(Element e, String key) {
		if (e.getElementsByTagName(key).item(0) == null) return null;
		return e.getElementsByTagName(key).item(0).getTextContent();
	}

	public long getDate(Element e) {
		long res = 0;
		if (e.getElementsByTagName("date").item(0) == null) return 0;

		try {
			res = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'").parse(e.getElementsByTagName("date").item(0).getTextContent()).getTime();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return res;
	}
}
