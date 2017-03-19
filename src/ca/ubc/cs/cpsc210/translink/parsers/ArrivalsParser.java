package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.DataFormatException;

/**
 * A parser for the data returned by the Translink arrivals at a stop query
 */
public class ArrivalsParser {

    /**
     * Parse arrivals from JSON response produced by TransLink query.  All parsed arrivals are
     * added to the given stop assuming that corresponding JSON object has a RouteNo: and an
     * array of Schedules:
     * Each schedule must have an ExpectedCountdown, ScheduleStatus, and Destination.  If
     * any of the aforementioned elements is missing, the arrival is not added to the stop.
     *
     * @param stop         stop to which parsed arrivals are to be added
     * @param jsonResponse the JSON response produced by Translink
     * @throws JSONException                when JSON response does not have expected format
     * @throws ArrivalsDataMissingException when no arrivals are found in the reply
     */
    public static void parseArrivals(Stop stop, String jsonResponse)
            throws JSONException, ArrivalsDataMissingException {

        JSONArray arrivals;
        try {
            arrivals = new JSONArray(jsonResponse);
        } catch (NullPointerException e){
            throw new JSONException("Not an Array");
        }

        int count=0;

        for (int index = 0; index < arrivals.length(); index++) {
            JSONObject arrival = arrivals.getJSONObject(index);
            try{ parseArrival(stop, arrival);}
            catch(JSONException e){
                count++;
            }
        }

        if( count == arrivals.length()){
            throw new ArrivalsDataMissingException();
        }


    }

    public static void parseArrival(Stop stop, JSONObject arrival) throws JSONException, ArrivalsDataMissingException {


        String routeNo = arrival.getString("RouteNo");

        JSONArray schedules;
        try {
            schedules= arrival.getJSONArray("Schedules");
        } catch (NullPointerException e){
            throw new JSONException("Not an array");
        }

        for (int index = 0; index < schedules.length(); index++) {
            JSONObject p = schedules.getJSONObject(index);


            Integer expected;
            String destination;
            String status;



            expected = p.getInt("ExpectedCountdown");
            destination = p.getString("Destination");
            status = p.getString("ScheduleStatus");


            Arrival a = new Arrival(expected, destination, RouteManager.getInstance().getRouteWithNumber(routeNo));
            a.setStatus(status);
            stop.addArrival(a);


        }
    }
}
