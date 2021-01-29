/**
 * PowerAiming: Creates a view that shows the detected power cells and where they are in frame
 */

public class PowerAiming {
    private final static int canvasWidth = 640;
    private final static int canvasHeight = 480;
    private static Draw view;

    public static void drawPowerCell(Draw view, double[][] detections) {
        view.setPenColor(255, 255, 0);
        for (int i = 0; i < detections.length; i++) {
            double centerX = ((detections[i][2]-detections[i][0])/2)+detections[i][0];
            double centerY = ((detections[i][3]-detections[i][1])/2)+detections[i][1];
            double radius = ((detections[i][3]-detections[i][1])+(detections[i][2]-detections[i][0]))/4;
            view.setPenColor(255,255,0);
            view.filledCircle(centerX, centerY, Math.abs(radius));
            view.setPenColor(Draw.BLACK);
            view.text(centerX, centerY, (i+1)+": "+Math.round(Math.abs(radius)));
        }

    }

    public static void launchDash() {
        // init view
        view = new Draw("Power Aiming Dashboard");

        // set location of view on screen
        view.setLocationOnScreen(1, 1);
        view.enableDoubleBuffering();
        // set canvas size (proportional to camera view) (camera is 640x480)
        view.setCanvasSize(canvasWidth, canvasHeight);
        view.setXscale(0, canvasWidth);
        view.setYscale(0, canvasHeight);

        double[][] detections = {{0, 0, 40, 40}, {80, 30, 200, 150}};
        updateView(detections);


    }

    public static void updateView(double[][] detections) {
        view.clear();
        // put a lines down on the screen for aiming
        view.setPenColor(255, 0, 0);
        view.line(canvasWidth/2, 0, canvasWidth/2, canvasHeight); // vertical center line
        view.setPenColor(0, 0, 255);
        view.line(0, 1, canvasWidth, 1); // horizontal bottom line

        drawPowerCell(view, detections);
        view.show();
    }
}
