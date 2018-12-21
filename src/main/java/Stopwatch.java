/** Stopwatch class keeps the elapsed time in seconds
 *  Pause/resume available
 */

class Stopwatch {
    private long startTime;
    private long totalPausedTime;
    private long pauseStart;

    Stopwatch() {
        reset();
    }

    void start() {
        startTime = System.currentTimeMillis() / 1000;
    }

    int getElapsedTime() {
        long now = System.currentTimeMillis() / 1000;
        return (int) (now - startTime - getTotalPausedTime());
    }

    void reset(){
        startTime = System.currentTimeMillis() / 1000;
        totalPausedTime = 0;
        pauseStart = 0;
    }
    void pause() {
        pauseStart = System.currentTimeMillis() / 1000;
    }

    // Works for the first time only but why?
    void resume() {
        long now = System.currentTimeMillis() / 1000;
        totalPausedTime = totalPausedTime + now - pauseStart;
    }

    private long getTotalPausedTime() {
        return totalPausedTime;
    }
}