package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.*;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {

    private String filename;

    public StopParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse stop data from the file and add all stops to stop manager.
     */
    public void parse() throws IOException, StopDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseStops(dataProvider.dataSourceToString());
    }

    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param jsonResponse string encoding JSON data to be parsed
     * @throws JSONException            when JSON data does not have expected format
     * @throws StopDataMissingException when
     *                                  <ul>
     *                                  <li> JSON data is not an array </li>
     *                                  <li> JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop</li>
     *                                  </ul>
     */

    public void parseStops(String jsonResponse)
            throws JSONException, StopDataMissingException {

        JSONArray stops;

        try {
            stops = new JSONArray(jsonResponse);
        } catch (NullPointerException e){
            throw new JSONException("not an array");
        }


        for (int index = 0; index < stops.length(); index++) {
            JSONObject stop = stops.getJSONObject(index);
            parseStop(stop);


        }
    }



    public void parseStop(JSONObject stop) throws JSONException, StopDataMissingException {

        String name;
        Integer stopno;
        Double lat;
        Double lon;
        String routes;

        try {
            name = stop.getString("Name");
            stopno = stop.getInt("StopNo");
            lat = stop.getDouble("Latitude");
            lon = stop.getDouble("Longitude");



            routes = stop.getString("Routes");


            String[] route = routes.split(",");
            for ( int i=0;i< route.length; i += 1){


                StopManager.getInstance().getStopWithId(stopno, name, new LatLon(lat, lon)).addRoute(RouteManager.getInstance().getRouteWithNumber(route[i].trim()));

            }

        } catch (JSONException e) {
            throw new StopDataMissingException();
        }

    }
}
