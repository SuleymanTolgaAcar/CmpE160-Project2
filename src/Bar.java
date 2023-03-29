/*
 * Suleyman Tolga Acar
 * 2021400237
 * 29.03.2023
 * 
 * This class is for the bar that is used to show the time left.
 * It has a draw method that draws the bar and a getTimeLeft method that returns the time left.
 */

public class Bar {
    /**
     * The time in miliseconds when the bar initialized.
     */
    private final int startTime = (int) System.currentTimeMillis();

    /**
     * Returns the time left.
     * @return The time left.
     */
    public int getTimeLeft() {
        return Environment.TOTAL_GAME_DURATION - ((int) System.currentTimeMillis() - startTime);
    }

    /**
     * Draws the bar.
     */
    public void draw() {
        int currentTime = (int) System.currentTimeMillis();
        int elapsedTime = currentTime - startTime;
        // The green value and the width of the bar is calculated by the elapsed time. I clamped them at zero because the game could crash
        // by a few miliseconds depending on the priority of the calculations.
        int greenValue = 255 - elapsedTime * 255 / Environment.TOTAL_GAME_DURATION;
        greenValue = Math.max(greenValue, 0);
        double barWidth = Environment.SCALE_X - elapsedTime * Environment.SCALE_X / Environment.TOTAL_GAME_DURATION;
        barWidth = Math.max(barWidth, 0);
        StdDraw.picture(Environment.SCALE_X / 2, -0.5, "bar.png", Environment.SCALE_X, Environment.SCALE_Y / 9);
        StdDraw.setPenColor(255, greenValue, 0);
        StdDraw.filledRectangle(barWidth / 2, -0.5, barWidth / 2, 0.25);
    }
}
