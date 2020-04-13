package com.example.finnkino;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Movies {
    private static final Movies ourInstance = new Movies();
    private final String MOVIE_LIST_URL = "https://www.finnkino.fi/xml/Schedule/?area=%d&dt=%s";

    private AsyncHelper asyncHelper;
    private static final List<Movie> movies = new ArrayList<Movie>();
    private static final List<Movie> filteredMovies = new ArrayList<Movie>();

    public static Movies getInstance() {
        return ourInstance;
    }

    private Movies() {
    }

    public void registerEventListener(Context context)
    {
        asyncHelper = new AsyncHelper(context);
    }

    public void getMovies(SearchParameters parameters) {
        // Date must be in dd.mm.yyyy and time must be in hh:mm form.

        // Reset old list
        resetMovies();
        Thread theaterThread = new Thread(() -> {
            try {
                if (parameters.theatherId != MovieTheaters.ALL_THEATHERS) {
                    List<Movie> newMovies = readMovies(parameters.theatherId, parameters.date);
                    movies.addAll(newMovies);
                } else {
                    // Handle all theathers case
                    for (Integer id : MovieTheaters.ALL_THEATHERS_IDS) {
                        List<Movie> newMovies = readMovies(id, parameters.date);
                        movies.addAll(newMovies);
                    }
                }
                filterMovies(parameters);
                asyncHelper.sendResponse(AsyncInterface.AsyncType.MOVIES, AsyncInterface.AsyncStatus.OK, "");
            } catch (Exception e) {
                asyncHelper.sendResponse(AsyncInterface.AsyncType.MOVIES, AsyncInterface.AsyncStatus.ERROR, "");
            }
        });
        theaterThread.start();
    }

    private List<Movie> readMovies(Integer theatherId, String date) throws Exception {
        List<Movie> newMovies = new ArrayList<Movie>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String movieList = String.format(MOVIE_LIST_URL, theatherId, date);
            Document doc = builder.parse(movieList);
            doc.getDocumentElement().normalize();

            // General details.
            NodeList nodes = doc.getDocumentElement().getElementsByTagName("Show");
            for (int item = 0; item < nodes.getLength(); item++) {
                Node node = nodes.item(item);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    Integer id = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    String startTimeUnformatted = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    String endTimeUnformatted = element.getElementsByTagName("dttmShowEnd").item(0).getTextContent();
                    String theatre = element.getElementsByTagName("Theatre").item(0).getTextContent();
                    String auditorium = element.getElementsByTagName("TheatreAuditorium").item(0).getTextContent();


                    if (id != null && title != null && theatre != null && auditorium != null && startTimeUnformatted != null && endTimeUnformatted != null) {
                        // https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
                        Date startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(startTimeUnformatted);
                        Date endTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(endTimeUnformatted);
                        Movie movie = new Movie(id, title, theatre, auditorium, startTime, endTime);
                        newMovies.add(movie);
                    } else {
                        throw new Exception("XML is malformed");
                    }
                }
            }

            // Picture details.
            nodes = doc.getDocumentElement().getElementsByTagName("Images");
            for (int item = 0; item < nodes.getLength(); item++) {
                Node node = nodes.item(item);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element)node;
                    String picture = element.getElementsByTagName("EventSmallImageLandscape").item(0).getTextContent();


                    if (picture != null) {
                        // Fix to https
                        if (picture.startsWith("http://")) {
                            picture = "https://" + picture.substring(7);
                        }
                        newMovies.get(item).setPicture(picture);
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
        return newMovies;
    }

    private void filterMovies(SearchParameters parameters) throws Exception {
        filteredMovies.clear();
        Date startTime = null;
        Date endTime = null;
        if (!parameters.startTime.isEmpty()) {
            startTime = new SimpleDateFormat("dd.MM.yyyy,HH:mm").parse(parameters.date + "," + parameters.startTime);
        }
        if (!parameters.endTime.isEmpty()) {
            endTime = new SimpleDateFormat("dd.MM.yyyy,HH:mm").parse(parameters.date + "," + parameters.endTime);
        }

        // Go trough every movie candidate
        for (Movie movie :movies) {
            // Check start time
            if (!(startTime == null || startTime.before(movie.getStartTime()))) {}
            // Check end time
            else if (!(endTime == null || endTime.after(movie.getEndTime()))) {}
            // Check title status
            else if (!parameters.title.isEmpty() && !movie.getTitle().contains(parameters.title)) {}
            // Add to filtered movies if everything passes
            else {
                filteredMovies.add(movie);
            }
        }
    }

    public List getMovies() {
        return filteredMovies;
    }

    public void resetMovies() {
        movies.clear();
        filteredMovies.clear();
    }
}

class SearchParameters {
    final Integer theatherId;
    final String date;
    final String startTime;
    final String endTime;
    final String title;

    public SearchParameters(Integer theatherId, String date, String startTime, String endTime, String title) {
        this.theatherId = theatherId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
    }
}