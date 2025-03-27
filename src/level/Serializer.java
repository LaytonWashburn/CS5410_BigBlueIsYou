package level;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Serializer implements Runnable {
    private enum Activity {
        Nothing,
        Load
    }

    private boolean done = false;
    private final Lock lockSignal = new ReentrantLock();
    private final Condition doSomething = lockSignal.newCondition();
    private Activity doThis = Activity.Nothing;

    private LevelState levelState;
    private Runnable onLoadComplete;

    private final Thread tInternal;

    public Serializer() {
        this.tInternal = new Thread(this);
        this.tInternal.start();
    }

    public void setOnLoadComplete(Runnable callback) {
        this.onLoadComplete = callback;
    }

    @Override
    public void run() {
        try {
            while (!done) {
                // Wait for a signal to do something
                lockSignal.lock();
                doSomething.await();
                lockSignal.unlock();

                // Based on what was requested, do something
                switch (doThis) {
                    case Nothing -> {}
                    case Load -> {
                        loadLevels();
                        if (onLoadComplete != null) {
                            onLoadComplete.run();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.printf("Something bad happened: %s\n", ex.getMessage());
        }
    }

    /// Public method used the client code to request the game state is loaded.
    /// NOTE: Same comment about race conditions as above.
    public void loadLevelState(LevelState state) {
        lockSignal.lock();

        this.levelState = state;
        doThis = Activity.Load;
        doSomething.signal();

        lockSignal.unlock();
    }

    /// Public method used to signal this code to perform a graceful shutdown
    public void shutdown() {
        try {
            System.out.println("In the shutdown levels serializer");
            lockSignal.lock();

            doThis = Activity.Nothing;
            done = true;
            doSomething.signal();

            lockSignal.unlock();

            tInternal.join();
        } catch (Exception ex) {
            System.out.printf("Failure to gracefully shutdown thread: %s\n", ex.getMessage());
        }
    }

    /// This is where the actual deserialization of the game state is performed.
    /// Same note as above regarding the choice to use JSON formatting.
    private synchronized void loadLevels() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/levels-all.bbiy"))) {
            while (true) {
                String name = reader.readLine();
                if (name == null) break; // End of file

                String sizeLine = reader.readLine();
                String[] sizeParts = sizeLine.split(" x ");
                int width = Integer.parseInt(sizeParts[0]);
                int height = Integer.parseInt(sizeParts[1]);

                char[][] group1 = new char[height][width];
                char[][] group2 = new char[height][width];

                for (int i = 0; i < height; i++) {
                    group1[i] = reader.readLine().toCharArray();
                }

                for (int i = 0; i < height; i++) {
                    group2[i] = reader.readLine().toCharArray();
                }

                levelState.add(new Level(name, width, height, group1, group2));
            }
        } catch (Exception ex) {
            this.levelState.errorOccurred = true;
            System.out.println(ex.getMessage());
        }
    }
}