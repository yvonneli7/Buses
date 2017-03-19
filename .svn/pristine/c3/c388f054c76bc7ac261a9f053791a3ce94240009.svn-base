package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.zip.DataFormatException;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;

    public RouteParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse route data from the file and add all route to the route manager.
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());
    }

    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.
     *
     * @param jsonResponse string encoding JSON data to be parsed
     * @throws JSONException             when JSON data does not have expected format
     * @throws RouteDataMissingException when
     *                                   <ul>
     *                                   <li> JSON data is not an array </li>
     *                                   <li> JSON data is missing Name, StopNo, Routes or location elements for any stop</li>
     *                                   </ul>
     */

    public void parseRoutes(String jsonResponse)
            throws JSONException, RouteDataMissingException {

        JSONArray data;

        try {
            data = new JSONArray(jsonResponse);
        } catch (NullPointerException e){
            throw new JSONException("not an array");
        }


        for (int index = 0; index < data.length(); index++) {
            JSONObject route = data.getJSONObject(index);

            parseRoute(route);

        }
    }

    public void parseRoute(JSONObject route) throws JSONException, RouteDataMissingException {

        String name;
        String routeNo;
        JSONArray pattern;

        try {
            name = route.getString("Name");
            routeNo = route.getString("RouteNo");
            pattern = route.getJSONArray("Patterns");


            RouteManager.getInstance().getRouteWithNumber(routeNo, name);

        }catch (JSONException e){
            throw new RouteDataMissingException();
        }

        for (int index = 0; index < pattern.length(); index++) {
            JSONObject p = pattern.getJSONObject(index);

            String patternNo;
            String destination;
            String direction;

            try {
                patternNo = p.getString("PatternNo");
                destination = p.getString("Destination");
                direction = p.getString("Direction");


                RoutePattern rpattern = new RoutePattern(patternNo, destination, direction, RouteManager.getInstance().getRouteWithNumber(routeNo));
                RouteManager.getInstance().getRouteWithNumber(routeNo).addPattern(rpattern);
            } catch (JSONException e){
                throw new RouteDataMissingException();
            }

        }
    }
}
