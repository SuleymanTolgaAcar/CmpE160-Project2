/*
 * Suleyman Tolga Acar
 * 2021400237
 * 29.03.2023
 * 
 * This class is for the player.
 * It has a draw method that draws the player and a move method that moves the player if the left or right arrow keys are pressed.
 * It also has a checkCollision method that checks if the player hit a ball.
 * It also has a shoot method that shoots the arrow if the space bar is pressed.
 */

import java.util.ArrayList;

public class Player {
    // Constants
    public static final int PERIOD_OF_PLAYER = 6000; // 6 seconds
    public static final double PLAYER_HEIGHT_WIDTH_RATE = 37.0 / 27.0;
    public static final double PLAYER_HEIGHT_SCALEY_RATE = 1.0 / 8.0;

    // Variables
    private final double height = Environment.SCALE_Y * PLAYER_HEIGHT_SCALEY_RATE;
    private final double width = height * (1 / PLAYER_HEIGHT_WIDTH_RATE);
    private final double speed = Environment.SCALE_X / PERIOD_OF_PLAYER;

    private double positionX = Environment.SCALE_X / 2;
    private int lastTime = (int) System.currentTimeMillis();

    /**
     * Draws the player.
     */
    public void draw() {
        StdDraw.picture(positionX, height / 2, "player_back.png",
                width, height);
    }
    
    /**
     * Moves the player if the left or right arrow keys are pressed.
     */
    public void move() {
        // I calculated the movement using the elapsed time since the last frame. Because if I didn't do that, 
        // the speed of the movement would be different depending on the FPS (frames per second).
        int currentTime = (int) System.currentTimeMillis();
        int elapsedTime = currentTime - lastTime;
        lastTime = currentTime;
        if (StdDraw.isKeyPressed(37)) {
            positionX -= speed * elapsedTime;
        }
        if (StdDraw.isKeyPressed(39)) {
            positionX += speed * elapsedTime;
        }
        // Check if the player is out of the screen.
        if (positionX - width / 2 < 0) {
            positionX = width / 2;
        }
        if (positionX + width / 2 > Environment.SCALE_X) {
            positionX = Environment.SCALE_X - width / 2;
        }
    }
    
    /**
     * Sets the arrow active if the space bar is pressed and the arrow is not active already.
     * @param arrow The arrow that is used to shoot the balls.
     */
    public void shoot(Arrow arrow) {
        if (StdDraw.isKeyPressed(32) && !arrow.isActive()) {
            arrow.setActive(positionX);
        }
    }

    /**
     * Checks if the player hit a ball.
     * @param balls The list of balls.
     * @return  Whether the player hit a ball or not.
     */
    public boolean checkCollision(ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            if (ball.isActive()) {
                // Find the nearest point on the player to the ball.
                double nearestX = Math.max(positionX - width / 2, Math.min(ball.getX(), positionX + width / 2));
                double nearestY = Math.max(0, Math.min(ball.getY(), height));
                // Check if the distance between the nearest point and the ball is less than the radius of the ball.
                if (Math.pow(nearestX - ball.getX(), 2) + Math.pow(nearestY - ball.getY(), 2) < Math.pow(ball.getRadius(), 2)) {
                    return true;
                }
            }
        }
        // If the player didn't hit any ball, then return false.
        return false;
    }
}
