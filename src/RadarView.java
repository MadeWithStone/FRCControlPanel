/**
 * PowerAiming: Creates a view that shows the detected power cells and where they are in frame
 */

import java.awt.Color;

public class RadarView {
    private final static int canvasWidth = 640;
    private final static int canvasHeight = 320;
    private final static int fov = 80; // degrees

    private final static double[][] testSizes = {{300, 15}, {40, 300}};
    private static double sizeDistRel = 0;
    private static Draw view;

    /**
     * Draws where powercells are in relationship to the robot on the radar view
     * @param view
     * @param detections
     */
    public static void drawPowerCell(Draw view, double[][] detections) {
        view.setPenColor(255, 255, 0);
        for (int i = 0; i < detections.length; i++) {
            double centerX = ((detections[i][2]-detections[i][0])/2)+detections[i][0];
            double width = (detections[i][2]-detections[i][0]);
            double height = (detections[i][3]-detections[i][1]);
            double[] coords = polarToRect(new double[]{(90+(fov/2)-getAngle(centerX)), sizeToDistance((width+height)/2)*(canvasHeight/(400*1.0))});
            //double centerY = ((detections[i][3]-detections[i][1])/2)+detections[i][1];
            //double radius = ((detections[i][3]-detections[i][1])+(detections[i][2]-detections[i][0]))/4;
            view.setPenColor(Draw.BOOK_RED);
            view.line(canvasWidth/2, 0, coords[0], coords[1]);
            view.setPenColor(255, 255, 0);
            view.filledCircle(coords[0], coords[1], 20);
            view.setPenColor(Draw.BLACK);
            view.text(coords[0], coords[1], (i+1)+": "+String.valueOf(Math.round(sizeToDistance(Math.abs((width+height)/2)))));

        }

    }

    /**
     * Uses the size to distance ratio calculated earlier to find how far the powercells are from the robot
     * @param size
     * @return
     */
    public static double sizeToDistance(double size) {
        double dist = (Math.abs(size)*sizeDistRel)+testSizes[1][1];
        return dist;
    }

    /**
     * uses the center x coordinate to find the angle relative to the camera pov
     * @param x
     * @return
     */
    public static double getAngle(double x) {
        double xPercent = x/canvasWidth;
        double xDegree = xPercent*fov;
        return xDegree;
    }

    /**
     * Calculates the size to distance ratio using input calibration data
     */
    public static void sizeRel() {
        double distChange = testSizes[1][1] - testSizes[0][1];
        double sizeChange = testSizes[1][0] - testSizes[0][0];
        sizeDistRel = distChange/sizeChange;
        System.out.println("Size Dist Rel: "+sizeDistRel);
    }

    /**
     * Calculates the canvas coordinates based on the angle and distance provide
     * @param coords
     * @return
     */
    public static double[] polarToRect(double[] coords) {
        // translate to center of screen
        double[] newCords = new double[2];
        newCords[0] = (Math.cos(Math.toRadians(coords[0]))*coords[1])+canvasWidth/2;
        newCords[1] = Math.sin(Math.toRadians(coords[0]))*coords[1];
        return newCords;
    }

    /**
     * launches and sets up the dashboard
     */
    public static void launchDash() {
        // init view
        view = new Draw("Radar Dashboard");

        // set location of view on screen

        view.enableDoubleBuffering();
        double[][] detections = {{0, 0, 40, 40}, {80, 30, 200, 150}};
        updateView(detections);
        // set canvas size (proportional to camera view) (camera is 640x480)
        view.setCanvasSize(canvasWidth, canvasHeight);
        view.setXscale(0, canvasWidth);
        view.setYscale(0, canvasHeight);
        sizeRel();

    }

    /**
     * Puts setup lines in the view like the center aiming line
     */
    public static void setUpView() {


        // put a lines down on the screen for aiming
        view.setLocationOnScreen(641, 1);
        Color penColor = new Color(0, 255, 0, 30);
        view.setPenColor(penColor);
        view.filledCircle(canvasWidth/2, 0, canvasWidth/2);
        view.setPenColor(255, 0, 0);
        view.line(canvasWidth/2, 0, canvasWidth/2, canvasHeight); // vertical center line
        view.setPenColor(0, 0, 255);
        view.line(0, 1, canvasWidth, 1); // horizontal bottom line
        double[][] fovLines = new double[2][2];
        fovLines[0] = polarToRect(new double[]{90+fov/2, canvasWidth/2});
        fovLines[1] = polarToRect(new double[]{90-fov/2, canvasWidth/2});
        view.line(canvasWidth/2, 0, fovLines[0][0], fovLines[0][1]);
        view.line(canvasWidth/2, 0, fovLines[1][0], fovLines[1][1]);



    }

    /**
     * Updates the view with new detections provide from the main class
     * @param detections
     */
    public static void updateView(double[][] detections) {
        view.clear();
        setUpView();
        drawPowerCell(view, detections);
        view.show();
    }
}
