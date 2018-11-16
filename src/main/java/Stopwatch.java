/** Stopwatch class keeps the elapsed time in seconds
 *  Pause/resume available
 */

class Stopwatch {
    private long startTime;
    private long totalPausedTime;
    private long pauseStart;

    Stopwatch() {
        startTime = System.currentTimeMillis() / 1000;
        pauseStart = 0;
        totalPausedTime = 0;
    }

    long getElapsedTime() {
        long now = System.currentTimeMillis() / 1000;
        return now - startTime - getTotalPausedTime();
    }

    void pause() {
        pauseStart = System.currentTimeMillis() / 1000;
    }

    // Is this the right place or should I move this to controller?
    void resume() {
        long now = System.currentTimeMillis() / 1000;
        totalPausedTime = now - pauseStart;
        pauseStart = 0;
    }

    private long getTotalPausedTime() {
        return totalPausedTime;
    }
}