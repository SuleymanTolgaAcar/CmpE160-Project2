/*
 * Suleyman Tolga Acar
 * 2021400237
 * 29.03.2023
 * 
 * This class is for the environment of the game.
 * It has a draw method that draws the environment and a endGameScreen method that draws the end game screen.
 * It also has a initCanvas method that initializes the canvas.
 */

import java.awt.Font;
import java.util.ArrayList;

public class Environment {
    // Constants
    public static final int TOTAL_GAME_DURATION = 40000; // 40 seconds
    public static final int PAUSE_DURATION = 15; // 15 milliseconds
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 500;
    public static final double SCALE_X = 16.0;
    public static final double SCALE_Y = 9.0;

    // Variables
    private Player player;
    private Bar bar;
    private Arrow arrow;
    private ArrayList<Ball> balls;
    private boolean won;

    /**
     * Initializes the canvas.
     */
    public void initCanvas() {
        StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
        StdDraw.setXscale(0.0, SCALE_X);
        StdDraw.setYscale(-1.0, SCALE_Y);
        StdDraw.enableDoubleBuffering();
        draw();
    }
    
    /**
     * Draws the environment.
     */
    public void draw() {
        StdDraw.picture(SCALE_X / 2, (SCALE_Y - 1) / 2, "background.png", SCALE_X, SCALE_Y + 1);
        bar.draw();
        arrow.draw();
        player.draw();
        Ball.drawBalls(balls);
        StdDraw.show();
    }

    /**
     * Draws the end game screen.
     */
    public void endGameScreen() {
        StdDraw.picture(SCALE_X / 2, SCALE_Y / 2.18, "game_screen.png", SCALE_X / 3.8, SCALE_Y / 4);
        StdDraw.setPenColor();
        StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
        // If the player won, it will show "You Won!" on the screen. If not, it will show "Game Over!".
        if (won) {
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.0, "You Won!");
        } else {
            StdDraw.text(SCALE_X / 2, SCALE_Y / 2.0, "Game Over!");
        }
        StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
        StdDraw.text(SCALE_X / 2, SCALE_Y / 2.3, "To Replay Click “Y”");
        StdDraw.text(SCALE_X / 2, SCALE_Y / 2.6, "To Quit Click “N”");
        StdDraw.show();
    }

    /**
     * The main game loop to run the game.
     */
    public void game() {
        // Initializes the objects.
        player = new Player();
        bar = new Bar();
        arrow = new Arrow();
        balls = new ArrayList<Ball>();
        // Initializes the canvas.
        initCanvas();
        // Adds the balls that are present at the start of the game to the array list.
        balls.add(new Ball(0));
        balls.add(new Ball(1));
        balls.add(new Ball(2));

        while (true) {
            // Handles player actions if the corresponding key is pressed.
            player.move();
            player.shoot(arrow);
            // Checks if the player won or lost.
            if (player.checkCollision(balls)) {
                // If the player hit a ball, game is over.
                won = false;
                break;
            }
            if (Ball.countBalls(balls) == 0) {
                // If there are no balls left, player won.
                won = true;
                break;
            }
            if (bar.getTimeLeft() <= 0) {
                // If the time is up, game is over.
                won = false;
                break;
            }
            // Checks if the arrow hit a ball and does the necessary actions.
            arrow.checkCollision(balls);
            // Moves the balls each frame.
            Ball.moveBalls(balls);
            // Draws the environment.
            draw();
            // Pause duration to make the game run smoothly.
            StdDraw.pause(PAUSE_DURATION);
        }
        // If the while loop above is broken, it means the game is over.
        // Draws the end game screen.
        endGameScreen();
        // Waits for the player to press a key.
        while (true) {
            if (StdDraw.isKeyPressed(89)) {
                // If the player presses "Y", the game will restart by calling the game method again.
                game();
            }
            if (StdDraw.isKeyPressed(78)) {
                // If the player presses "N", the program will terminate.
                System.exit(0);
            }
        }
    }
}
