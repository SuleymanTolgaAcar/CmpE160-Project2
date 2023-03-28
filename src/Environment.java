public class Environment {
    public static int TOTAL_GAME_DURATION = 40000; // 40 seconds
    public static int PAUSE_DURATION = 15; // 15 milliseconds
    public static int CANVAS_WIDTH = 800;
    public static int CANVAS_HEIGHT = 500;
    public static double SCALE_X = 16.0;
    public static double SCALE_Y = 9.0;
    public static int startTime = (int) System.currentTimeMillis();

    public static void initCanvas() {
        StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        StdDraw.setXscale(0.0, SCALE_X);
        StdDraw.setYscale(-1.0, SCALE_Y);
        StdDraw.enableDoubleBuffering();
        draw();
    }
    
    public static void draw() {
        StdDraw.picture(SCALE_X / 2, (SCALE_Y - 1) / 2, "background.png", SCALE_X, SCALE_Y + 1);
        Bar.drawBar(startTime);
        StdDraw.show();
    }

    public static void gameLoop() {
        while (true) {
            draw();
            StdDraw.pause(PAUSE_DURATION);
        }
    }
}
