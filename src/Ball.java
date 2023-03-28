public class Ball {
    public static int PERIOD_OF_BALL = 15000; // 15 second
    public static double HEIGHT_MULTIPLIER = 1.75;
    public static double RADIUS_MULTIPLIER = 2.0;
    public static double GRAVITY = Environment.SCALE_Y * 0.000003;
    public static double MIN_POSSIBLE_RADIUS = Player.PLAYER_HEIGHT_SCALEY_RATE * 1.4;
    public static double MIN_POSSIBLE_HEIGHT = Environment.SCALE_Y * 0.000003;
}
