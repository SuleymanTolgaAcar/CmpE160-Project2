public class Bar {
    public static void drawBar(int startTime) {
        int currentTime = (int) System.currentTimeMillis();
        int elapsedTime = currentTime - startTime;
        int greenValue = 255 - elapsedTime * 255 / Environment.TOTAL_GAME_DURATION;
        double barWidth = elapsedTime * Environment.SCALE_X / Environment.TOTAL_GAME_DURATION;
        StdDraw.picture(Environment.SCALE_X / 2, -0.5, "bar.png", Environment.SCALE_X, Environment.SCALE_Y / 9);
        StdDraw.setPenColor(255, greenValue, 0);
        StdDraw.filledRectangle(barWidth / 2, -0.5, barWidth / 2, 0.25);
    }
}
