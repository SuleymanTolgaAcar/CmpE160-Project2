import java.util.ArrayList;

public class Ball {
    public static final int PERIOD_OF_BALL = 15000; // 15 second
    public static final double HEIGHT_MULTIPLIER = 1.75;
    public static final double RADIUS_MULTIPLIER = 2.0;
    public static final double GRAVITY = Environment.SCALE_Y * 0.000003;
    public static final double MIN_POSSIBLE_RADIUS = Player.PLAYER_HEIGHT_SCALEY_RATE * 1.4;
    public static final double MIN_POSSIBLE_HEIGHT = Environment.SCALE_Y * 0.000003;

    private int level;
    private double radius;
    private double position_x;
    private double position_y;
    private double velocity_x;
    private double velocity_y;
    private boolean active = true;
    private int lastTime = (int) System.currentTimeMillis();
    private double speed = Environment.SCALE_X / PERIOD_OF_BALL;

    Ball(int level) {
        this.level = level;
        position_y = 0.5;
        switch (this.level) {
            case 0:
                radius = MIN_POSSIBLE_RADIUS;
                position_x = Environment.SCALE_X / 4;
                velocity_x = speed;
                velocity_y = speed * 2;
                break;
            case 1:
                radius = MIN_POSSIBLE_RADIUS * RADIUS_MULTIPLIER;
                position_x = Environment.SCALE_X / 3;
                velocity_x = -speed;
                velocity_y = speed * 3;
                break;
            case 2:
                radius = MIN_POSSIBLE_RADIUS * RADIUS_MULTIPLIER * RADIUS_MULTIPLIER;
                position_x = Environment.SCALE_X / 4;
                velocity_x = speed;
                velocity_y = speed * 4;
                break;
        }
    }

    Ball(int level, double position_x, double position_y, double velocity_x) {
        this.level = level;
        this.position_x = position_x;
        this.position_y = position_y;
        this.velocity_x = velocity_x;
        this.velocity_y = speed * 2;
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
        return "Ball [level=" + level + ", radius=" + radius + ", position_x=" + position_x + ", position_y="
                + position_y + ", velocity_x=" + velocity_x + ", velocity_y=" + velocity_y + ", lastTime=" + lastTime
                + ", speed=" + speed + "]";
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setInactive() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }

    public double getX() {
        return position_x;
    }

    public double getY() {
        return position_y;
    }

    public double getVelocityX() {
        return velocity_x;
    }

    public double getVelocityY() {
        return velocity_y;
    }

    public double getRadius() {
        return radius;
    }

    public int getLevel(){
        return level;
    }
    
    public void move() {
        int currentTime = (int) System.currentTimeMillis();
        int elapsedTime = currentTime - lastTime;
        lastTime = currentTime;
        position_x += velocity_x * elapsedTime;
        velocity_y -= GRAVITY * elapsedTime / Environment.PAUSE_DURATION;
        position_y += velocity_y * elapsedTime;
        if (position_y - radius < 0) {
            position_y = radius;
            //*find the correct number*/
            velocity_y = -velocity_y * 1.1;
        }
        if (position_x - radius < 0) {
            position_x = radius;
            velocity_x = -velocity_x;
        }
        if (position_x + radius > Environment.SCALE_X) {
            position_x = Environment.SCALE_X - radius;
            velocity_x = -velocity_x;
        }
    }

    public void draw() {
        if (active) {
            StdDraw.picture(position_x, position_y, "ball.png", radius * 2, radius * 2);
        }
    }

    public static void drawBalls(ArrayList<Ball> balls) {
        for(Ball ball : balls) {
            ball.draw();
        }
    }

    public static void moveBalls(ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            ball.move();
        }
    }
    
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
