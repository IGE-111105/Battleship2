package battleship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.Duration;
import java.time.Instant;

public class GameTimer {
    private static final Logger logger = LogManager.getLogger(GameTimer.class);

    private Instant startTime;
    private Instant pauseTime;
    private Duration totalPausedDuration;
    private boolean isRunning;

    public GameTimer() {
        this.totalPausedDuration = Duration.ZERO;
        this.isRunning = false;
    }

    public void start() {
        if (!isRunning) {
            this.startTime = Instant.now();
            this.isRunning = true;
            logger.info("Cronômetro iniciado");
        }
    }
    public void pause() {
        if (isRunning) {
            this.pauseTime = Instant.now();
            this.isRunning = false;
            logger.info("Cronômetro pausado");
        }
    }
    public void resume() {
        if (!isRunning && pauseTime != null) {
            Duration pausedDuration = Duration.between(pauseTime, Instant.now());
            this.totalPausedDuration = this.totalPausedDuration.plus(pausedDuration);
            this.isRunning = true;
            logger.info("Cronômetro retomado");
        }
    }

    public long getElapsedSeconds() {
        if (startTime == null) return 0;

        Instant endTime = isRunning ? Instant.now() : pauseTime;
        Duration elapsed = Duration.between(startTime, endTime).minus(totalPausedDuration);
        return Math.max(0, elapsed.getSeconds());
    }
    public String getFormattedTime() {
        long seconds = getElapsedSeconds();
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    public void reset() {
        this.startTime = null;
        this.pauseTime = null;
        this.totalPausedDuration = Duration.ZERO;
        this.isRunning = false;
        logger.info("Cronômetro redefinido");
    }
}
