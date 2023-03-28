import java.awt.Font;
import java.util.ArrayList;

public class Environment {
    public static final int TOTAL_GAME_DURATION = 40000; // 40 seconds
    public static final int PAUSE_DURATION = 15; // 15 milliseconds
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 500;
    public static final double SCALE_X = 16.0;
    public static final double SCALE_Y = 9.0;
    public static final int startTime = (int) System.currentTimeMillis();

    private static Player player;
    private static Bar bar;
    private static Arrow arrow;
    private static ArrayList<Ball> balls;
    private static boolean won;

    public static void initCanvas() {
        StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        StdDraw.setXscale(0.0, SCALE_X);
        StdDraw.setYscale(-1.0, SCALE_Y);
        StdDraw.enableDoubleBuffering();
        draw();
    }
    
    public static void draw() {
        StdDraw.picture(SCALE_X / 2, (SCALE_Y - 1) / 2, "background.png", SCALE_X, SCALE_Y + 1);
        bar.draw();
        player.draw();
        arrow.draw();
        Ball.drawBalls(balls);
        StdDraw.show();
    }

    public static void endGameScreen() {
        StdDraw.picture(SCALE_X / 2, SCALE_Y / 2.18, "game_screen.png", SCALE_X / 3.8, SCALE_Y / 4);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
        if (won) {
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.0, "You Won!");
        }
        else {
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.0, "Game Over!");
        }
        StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
        StdDraw.text(SCALE_X / 2, SCALE_Y / 2.3, "To Replay Click “Y”");
        StdDraw.text(SCALE_X / 2, SCALE_Y / 2.6, "To Quit Click “N”");
        StdDraw.show();
    }

    public static void game() {
        player = new Player();
        bar = new Bar();
        arrow = new Arrow();
        balls = new ArrayList<Ball>();
        initCanvas();
        balls.add(new Ball(0));
        balls.add(new Ball(1));
        balls.add(new Ball(2));
        while (true) {
            player.move();
            player.shoot(arrow);
            if (player.checkCollision(balls)) {
                won = false;
                break;
            }
            if (Ball.countBalls(balls) == 0) {
                won = true;
                break;
            }
            if (bar.getTimePercentage() <= 0) {
                won = false;
                break;
            }
            arrow.checkCollision(balls);
            Ball.moveBalls(balls);
            draw();
            StdDraw.pause(PAUSE_DURATION);
        }
        endGameScreen();
        while (true) {
            if (StdDraw.isKeyPressed(89)) {
                game();
            }
            if (StdDraw.isKeyPressed(78)) {
                System.exit(0);
            }
        }
    }
}
