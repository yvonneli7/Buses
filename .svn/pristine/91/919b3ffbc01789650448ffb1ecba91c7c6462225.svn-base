package ca.ubc.cs.cpsc210.translink.util;


/**
 * Compute relationships between points, lines, and rectangles represented by LatLon objects
 */
public class Geometry {
    /**
     * Return true if the point is inside of, or on the boundary of, the rectangle formed by northWest and southeast
     * @param northWest         the coordinate of the north west corner of the rectangle
     * @param southEast         the coordinate of the south east corner of the rectangle
     * @param point             the point in question
     * @return                  true if the point is on the boundary or inside the rectangle
     */
    public static boolean rectangleContainsPoint(LatLon northWest, LatLon southEast, LatLon point) {
        if( between (southEast.getLatitude(), northWest.getLatitude(), point.getLatitude())) {
            if(between ( northWest.getLongitude(), southEast.getLongitude(), point.getLongitude())){
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if the rectangle intersects the line
     * @param northWest         the coordinate of the north west corner of the rectangle
     * @param southEast         the coordinate of the south east corner of the rectangle
     * @param src               one end of the line in question
     * @param dst               the other end of the line in question
     * @return                  true if any point on the line is on the boundary or inside the rectangle
     */


    public static boolean rectangleIntersectsLine(LatLon northWest, LatLon southEast, LatLon src, LatLon dst) {
        Double x1 = dst.getLongitude();
        Double y1 = dst.getLatitude();
        Double x2 = src.getLongitude();
        Double y2 = src.getLongitude();
        Double rectangleMinX = northWest.getLongitude();
        Double rectangleMaxX = southEast.getLongitude();
        Double rectangleMaxY = northWest.getLatitude();
        Double rectangleMinY = southEast.getLatitude();

        Double slope = (y1 - y2)/(x1 - x2);
        Double intercept = y2 - x2*slope;


        return (rectangleContainsPoint(northWest, southEast, src)
                || between(rectangleMaxX, rectangleMinX, (rectangleMaxY - intercept)/slope)
                || between(rectangleMaxX, rectangleMinY, (rectangleMaxX - intercept)/slope)
                || between(rectangleMaxY, rectangleMinY, (rectangleMinX - intercept)/slope)
                || rectangleContainsPoint(northWest, southEast, dst)
                || between(rectangleMaxX, rectangleMinX, (rectangleMinY - intercept)/slope));
    }


    /**
     * A utility method that you might find helpful in implementing the two previous methods
     * Return true if x is >= lwb and <= upb
     * @param lwb      the lower boundary
     * @param upb      the upper boundary
     * @param x         the value in question
     * @return          true if x is >= lwb and <= upb
     */
    private static boolean between(double lwb, double upb, double x) {
        return lwb <= x && x <= upb;
    }
}
