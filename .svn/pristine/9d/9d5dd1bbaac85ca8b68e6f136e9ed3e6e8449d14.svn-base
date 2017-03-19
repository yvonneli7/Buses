package ca.ubc.cs.cpsc210.translink.providers;

import ca.ubc.cs.cpsc210.translink.BusesAreUs;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static ca.ubc.cs.cpsc210.translink.BusesAreUs.TRANSLINK_API_KEY;

/**
 * Wrapper for Translink Arrival Data Provider
 */
public class HttpArrivalDataProvider extends AbstractHttpDataProvider {
    private Stop stop;

    public HttpArrivalDataProvider(Stop stop) {
        super();
        this.stop = stop;
    }


    @Override
    /**
     * Produces URL used to query Translink web service for expected arrivals at
     * the stop specified in call to constructor.
     *
     * @returns URL to query Translink web service for arrival data
     *
     *
     */
    protected URL getURL() throws MalformedURLException {
        Stop s = stop;
        Integer stopNo = s.getNumber();
        String listNo = "";
        String url="";

        for (Route x : s.getRoutes()) {
            listNo= listNo + x.getNumber() + ";";
        }

        {
            listNo.split(";");
            for (int i = 1; i < listNo.length(); i++) {
                url = "http://api.translink.ca/rttiapi/v1/stops/" + stopNo + "/estimates?apikey=" + TRANSLINK_API_KEY + "&count=3&timeframe=340";

            }

        }
            System.out.println(url);
            return new URL(url);

    }





    @Override
    public byte[] dataSourceToBytes() throws IOException {
        return new byte[0];
    }
}
