/*
 * Suleyman Tolga Acar
 * 2021400237
 * 29.03.2023
 * 
 * This class is for the balls.
 * It has a draw method that draws the ball.
 * It also has a move method that moves the ball each frame.
 * It also has  a few static methods that are used to apply the object methods to all the balls. (drawBalls, moveBalls, countBalls)
 */

import java.util.ArrayList;

public class Ball {
    // Constants
    public static final int PERIOD_OF_BALL = 15000; // 15 second
    public static final double HEIGHT_MULTIPLIER = 1.75;
    public static final double RADIUS_MULTIPLIER = 2.0;
    public static final double GRAVITY = Environment.SCALE_Y * 0.000003;
    public static final double MIN_POSSIBLE_RADIUS = Player.PLAYER_HEIGHT_SCALEY_RATE * 1.4;
    public static final double MIN_POSSIBLE_HEIGHT = Environment.SCALE_Y * 0.000003;

    // Variables
    private int level;
    private double radius;
    private double positionX;
    private double positionY;
    private double velocityX;
    private double velocityY;

    private boolean active = true;
    private int lastTime = (int) System.currentTimeMillis();
    private double speed = Environment.SCALE_X / PERIOD_OF_BALL;

    /**
     * Constructor for the balls that are initially on the screen when the game starts.
     * @param level The level of the ball (0, 1, 2). 
     * It is used to determine the radius of the ball and how mant times it will be split when it is hit.
     */
    Ball(int level) {
        this.level = level;
        positionY = 0.5;
        // Determine the radius and the position of the ball depending on the level.
        switch (this.level) {
            case 0:
                radius = MIN_POSSIBLE_RADIUS;
                positionX = Environment.SCALE_X / 4;
                velocityX = speed;
                velocityY = speed * 2;
                break;
            case 1:
                radius = MIN_POSSIBLE_RADIUS * RADIUS_MULTIPLIER;
                positionX = Environment.SCALE_X / 3;
                velocityX = -speed;
                velocityY = speed * 3;
                break;
            case 2:
                radius = MIN_POSSIBLE_RADIUS * RADIUS_MULTIPLIER * RADIUS_MULTIPLIER;
                positionX = Environment.SCALE_X / 4;
                velocityX = speed;
                velocityY = speed * 4;
                break;
        }
    }

    /**
     * Constructor for the balls that are created when a ball is hit.
     * @param level The level of the ball (0, 1, 2). 
     * It is used to determine the radius of the ball and how mant times it will be split when it is hit.
     * @param positionX The x position of the ball when it is created.
     * @param positionY The y position of the ball when it is created.
     * @param velocityX The x velocity of the ball.
     */
    Ball(int level, double positionX, double positionY, double velocityX) {
        this.level = level;
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX;
        this.velocityY = speed * 2;
        // Determine the radius of the ball depending on the level.
        switch (this.level) {
            case 0:
                radius = MIN_POSSIBLE_RADIUS;
                break;
            case 1:
                radius = MIN_POSSIBLE_RADIUS * RADIUS_MULTIPLIER;
                break;
            case 2:
                radius = MIN_POSSIBLE_RADIUS * RADIUS_MULTIPLIER * RADIUS_MULTIPLIER;
                break;
        }
    }

    public String toString() {
        return "Ball [level=" + level + ", radius=" + radius + ", positionX=" + positionX + ", positionY="
                + positionY + ", velocityX=" + velocityX + ", velocityY=" + velocityY + ", lastTime=" + lastTime
                + ", speed=" + speed + "]";
    }

    public void setActive() {
        this.active = true;
    }

    public void setInactive() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public double getX() {
        return positionX;
    }

    public double getY() {
        return positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getRadius() {
        return radius;
    }

    public int getLevel() {
        return level;
    }
    
    /**
     * This method is used to move the ball each frame.
     */
    public void move() {
        // I calculated the movement using the elapsed time since the last frame. Because if I didn't do that, 
        // the speed of the movement would be different depending on the FPS.
        int currentTime = (int) System.currentTimeMillis();
        int elapsedTime = currentTime - lastTime;
        lastTime = currentTime;
        positionX += velocityX * elapsedTime;
        velocityY -= GRAVITY * elapsedTime / Environment.PAUSE_DURATION;
        positionY += velocityY * elapsedTime;
        // If the ball hits the ground, set its velocityY to -velocityY.
        if (positionY - radius < 0) {
            positionY = radius;
            // I added the right part of the plus sign to make the ball bounce a little bit higher.
            // Because otherwise the ball would bounce lower and lower each time it hits the ground.
            velocityY = -velocityY + GRAVITY * elapsedTime / Environment.PAUSE_DURATION / 2;
        }
        //  If the ball hits the left side of the screen, set its velocityX to -velocityX.
        if (positionX - radius < 0) {
            positionX = radius;
            velocityX = -velocityX;
        }
        // If the ball hits the right side of the screen, set its velocityX to -velocityX.
        if (positionX + radius > Environment.SCALE_X) {
            positionX = Environment.SCALE_X - radius;
            velocityX = -velocityX;
        }
    }

    /**
     * This method is used to draw the ball each frame.
     */
    public void draw() {
        if (active) {
            StdDraw.picture(positionX, positionY, "ball.png", radius * 2, radius * 2);
        }
    }

    /**
     * This method is used to draw all the balls in the ArrayList.
     * @param balls The ArrayList of balls.
     */
    public static void drawBalls(ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            ball.draw();
        }
    }

    /**
     * This method is used to move all the balls in the ArrayList.
     * @param balls The ArrayList of balls.
     */
    public static void moveBalls(ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            ball.move();
        }
    }
    
    /**
     * This method is used to count the number of active balls in the ArrayList.
     * @param balls The ArrayList of balls.
     * @return The number of active balls.
     */
    public static int countBalls(ArrayList<Ball> balls) {
        int count = 0;
        for (Ball ball : balls) {
            if (ball.isActive()) {
                count++;
            }
        }
        return count;
    }
}
