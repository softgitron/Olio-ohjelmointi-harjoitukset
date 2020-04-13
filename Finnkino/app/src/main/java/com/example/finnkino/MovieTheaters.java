package com.example.finnkino;

import android.app.Activity;
import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MovieTheaters {
    private static final MovieTheaters ourInstance = new MovieTheaters();
    private final String THEATER_LIST_URL = "https://www.finnkino.fi/xml/TheatreAreas/";
    public static final Integer ALL_THEATHERS = 9999;
    // https://stackoverflow.com/questions/8068470/java-initialize-an-int-array-in-a-constructor
    public static final Integer[] ALL_THEATHERS_IDS = new Integer[]{1014, 1015, 1016, 1017, 1041, 1018, 1019, 1021, 1022};

    private AsyncHelper asyncHelper;
    private List<MovieTheater> movieTheaters = new ArrayList<MovieTheater>();

    public static MovieTheaters getInstance() {
        return ourInstance;
    }

    private MovieTheaters() {

    }

    public void initTheathers() {
        Thread theaterThread = new Thread(() -> {
            try {
                readTheaters();
                // Add all theather option to the list
                MovieTheater theater = new MovieTheater(ALL_THEATHERS, "Kaikki");
                movieTheaters.add(theater);

                asyncHelper.sendResponse(AsyncInterface.AsyncType.THEATHERS, AsyncInterface.AsyncStatus.OK, "");
            } catch (Exception e) {
                asyncHelper.sendResponse(AsyncInterface.AsyncType.THEATHERS, AsyncInterface.AsyncStatus.ERROR, "");
            }
        });
        theaterThread.start();
    }

    public void registerEventListener(Context context)
    {
        asyncHelper = new AsyncHelper(context);
    }

    private void readTheaters() throws Exception {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(THEATER_LIST_URL);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getDocumentElement().getElementsByTagName("TheatreArea");

            for (int item = 0; item < nodes.getLength(); item++) {
                Node node = nodes.item(item);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    Integer id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    if (id != null && name != null) {
                        MovieTheater theater = new MovieTheater(id, name);
                        movieTheaters.add(theater);
                    } else {
                        throw new Exception("XML is malformed");
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            throw new Exception("Parser error.");
        } catch (IOException e) {
            throw new Exception("Problem with the network");
        } catch (SAXException e) {
            throw new Exception("Unknown error.");
        }

    }

    public String[] getTheaterNames() {
        String[] names = new String[movieTheaters.size()];
        for (int index = 0; index < movieTheaters.size(); index++) {
            names[index] = movieTheaters.get(index).getName();
        }
        return names;
    }

    public Integer getMovieTheatherId(String name) {
        for (MovieTheater movieTheater : movieTheaters) {
            if (movieTheater.getName().equals(name)) {
                return movieTheater.getId();
            }
        }
        return null;
    }

    public List<MovieTheater> getMovieTheaters() {
        return movieTheaters;
    }
}