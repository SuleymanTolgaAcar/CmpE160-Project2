import java.util.ArrayList;

public class Player {
    public static final int PERIOD_OF_PLAYER = 6000; // 6 seconds
    public static final double PLAYER_HEIGHT_WIDTH_RATE = 37.0 / 27.0;
    public static final double PLAYER_HEIGHT_SCALEY_RATE = 1.0 / 8.0;

    private final double height = Environment.SCALE_Y * PLAYER_HEIGHT_SCALEY_RATE;
    private final double width = height * (1 / PLAYER_HEIGHT_WIDTH_RATE);
    private final double speed = Environment.SCALE_X / PERIOD_OF_PLAYER;
    private double position = Environment.SCALE_X / 2;

    private int lastTime = (int) System.currentTimeMillis();

    public void draw() {
        StdDraw.picture(position, Environment.SCALE_Y * PLAYER_HEIGHT_SCALEY_RATE / 2, "player_back.png",
                width, height);
    }

    public double getPosition() {
        return position;
    }
    
    public void move() {
        int currentTime = (int) System.currentTimeMillis();
        int elapsedTime = currentTime - lastTime;
        lastTime = currentTime;
        if (StdDraw.isKeyPressed(37)) {
            position -= speed * elapsedTime;
        }
        if (StdDraw.isKeyPressed(39)) {
            position += speed * elapsedTime;
        }
        if (position - width / 2 < 0) {
            position = width / 2;
        }
        if (position + width / 2 > Environment.SCALE_X) {
            position = Environment.SCALE_X - width / 2;
        }
    }
    
    public void shoot(Arrow arrow) {
        if (StdDraw.isKeyPressed(32) && !arrow.isActive()) {
            arrow.setActive(position);
        }
    }

    public boolean checkCollision(ArrayList<Ball> balls) {
        for (Ball ball : balls) {
            if (ball.isActive()) {
                if (Math.abs(ball.getX() - position) < ball.getRadius() + width / 2
                        && ball.getY() - ball.getRadius() < height) {
                    return true;
                }
            }
        }
        return false;
    }
}
