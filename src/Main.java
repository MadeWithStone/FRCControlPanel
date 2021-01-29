/**
 * Main file for the PowerCellMapping program. Launches all the dashboards and runs the program.
 *
 * @author Maxwell Stone
 */

public class Main {

    /** Main Method: starts the program **/
    public static void main(String[] args) {
        PowerAiming.launchDash();
        RadarView.launchDash();
        runSimulation1();
    }

    public static void runSimulation1() {
        int size = 100;
        for (int i = 0; i < 480; i++) {
            double ballSize = size * ((480-i) / (480 * 1.0));
            double ball2Size = size * ((0+i) / (480 * 1.0));
            //System.out.println(ballSize);
            double[][] testDetections = {{i, i, i + ballSize, i + ballSize}, {480+ball2Size-i, 480+ball2Size-i, 480-i, 480-i}, {320-ballSize/2, i, 320 + ballSize/2, i+ballSize}};
            RadarView.updateView(testDetections);

            PowerAiming.updateView(testDetections);
            for (int j = 0; j < 50; j++) {}
        }
    }

    public static void runSimulation2() {
        int size = 100;
        for (int i = 0; i < 480; i++) {
            double ballSize = size * ((480-i) / (480 * 1.0));
            double ball2Size = size * ((0+i) / (480 * 1.0));
            //System.out.println(ballSize);
            double[][] testDetections = {{i, i, i + ballSize, i + ballSize}, {480-i, 480-i, 480-i - ball2Size, 480-i - ball2Size}, {320-ballSize/2, i, 320 + ballSize/2, i+ballSize}};
            RadarView.updateView(testDetections);

            PowerAiming.updateView(testDetections);
            for (int j = 0; j < 50; j++) {}
        }
    }
}
