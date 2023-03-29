/*
 * Suleyman Tolga Acar
 * 2021400237
 * 29.03.2023
 * 
 * This class is for the arrow that is used to shoot the balls.
 * It has a draw method that draws the arrow and a checkCollision method that checks if the arrow hit a ball.
 * It also has a setActive method that sets the arrow active and a setInactive method that sets the arrow inactive.
 */

import java.util.ArrayList;

public class Arrow {
    // Constants
    public static final int PERIOD_OF_ARROW = 1500; // 1.5 seconds
    
    // Variables
    private double position;
    private int startTime;
    private double height;

    private boolean active = false;
    
    /**
     * Sets the arrow active and sets the position and the start time of the arrow.
     * @params position The position of the player when the arrow is shot.
     */
    public void setActive(double position) {
        active = true;
        this.position = position;
        startTime = (int) System.currentTimeMillis();
    }

    /**
     * Sets the arrow inactive.
     */
    public void setInactive() {
        active = false;
        height = 0;
    }

    /**
     * Returns whether the arrow is active or not.
     * @return  Whether the arrow is active or not.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Draws the arrow.
     */
    public void draw() {
        if (active) {
            int currentTime = (int) System.currentTimeMillis();
            int elapsedTime = currentTime - startTime;
            // The height of the arrow is calculated by the elapsed time.
            height = elapsedTime * Environment.SCALE_Y / PERIOD_OF_ARROW;
            if (height > Environment.SCALE_Y) {
                setInactive();
            }
            StdDraw.picture(position, height / 2, "arrow.png",
                    0.2, height);
        }
    }
    
    /**
     * Checks if the arrow hit a ball.
     * @param balls The list of balls.
     */
    public void checkCollision(ArrayList<Ball> balls) {
        // A new list of balls is created to add the new balls that are created when a ball is hit.
        ArrayList<Ball> newBalls = new ArrayList<Ball>();
        if (active) {
            for (Ball ball : balls) {
                if (ball.isActive()) {
                    // If the ball's and the arrow's x coordinates are closer than the ball's radius and the arrow's height is higher than the ball's bottom point's y coordinate, the ball is hit.
                    if (Math.abs(ball.getX() - position) < ball.getRadius()
                            && height > ball.getY() - ball.getRadius()) {
                        // If the ball is level 0, it is set inactive.
                        if (ball.getLevel() == 0)
                            ball.setInactive();
                        // If the ball is not level 0, it is set inactive and two new balls are created.
                        else {
                            ball.setInactive();
                            newBalls.add(new Ball(ball.getLevel() - 1, ball.getX(), ball.getY(), ball.getVelocityX()));
                            newBalls.add(new Ball(ball.getLevel() - 1, ball.getX(), ball.getY(), -ball.getVelocityX()));
                        }
                        setInactive();
                    }
                }
            }
        }
        balls.addAll(newBalls);
    }
}
