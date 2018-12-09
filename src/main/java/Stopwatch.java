/** Stopwatch class keeps the elapsed time in seconds
 *  Pause/resume available
 */

class Stopwatch {
    private long startTime;
    private long totalPausedTime;
    private long pauseStart;

    Stopwatch() {
        pauseStart = 0;
        totalPausedTime = 0;
    }

    void start() {
        startTime = System.currentTimeMillis() / 1000;
    }

    long getElapsedTime() {
        long now = System.currentTimeMillis() / 1000;
        return now - startTime - getTotalPausedTime();
    }

    void pause() {
        pauseStart = System.currentTimeMillis() / 1000;
    }

    // Works for the first time only but why?
    void resume() {
        long now = System.currentTimeMillis() / 1000;
        totalPausedTime = now - pauseStart;
    }

    private long getTotalPausedTime() {
        return totalPausedTime;
    }
}