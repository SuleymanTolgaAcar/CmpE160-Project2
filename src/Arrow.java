import java.util.ArrayList;

public class Arrow {
    public static final int PERIOD_OF_ARROW = 1500; // 1.5 seconds
    
    private double position;
    private boolean active = false;
    private int startTime;
    private double height;
    
    public void setActive(double position) {
        active = true;
        this.position = position;
        startTime = (int) System.currentTimeMillis();
    }

    public void setInactive() {
        active = false;
        height = 0;
    }

    public boolean isActive() {
        return active;
    }

    public void draw() {
        if (active) {
            int currentTime = (int) System.currentTimeMillis();
            int elapsedTime = currentTime - startTime;
            height = elapsedTime * Environment.SCALE_Y / PERIOD_OF_ARROW;
            if (height > Environment.SCALE_Y) {
                setInactive();
            }
            StdDraw.picture(position, height / 2, "arrow.png",
                    0.2, height);
        }
    }
    
    public void checkCollision(ArrayList<Ball> balls) {
        ArrayList<Ball> newBalls = new ArrayList<Ball>();
        if (active) {
            for (Ball ball : balls) {
                if (ball.isActive()) {
                    if (Math.abs(ball.getX() - position) < ball.getRadius()
                            && height > ball.getY() - ball.getRadius()) {
                        if (ball.getLevel() == 0)
                            ball.setActive(false);
                        else {
                            ball.setActive(false);
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
