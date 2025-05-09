package utils;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


public class KeyBindSerializer implements Runnable {

    private enum Activity {
        Nothing,
        Load,
        Save
    }

    private boolean done = false;
    private final Lock lockSignal = new ReentrantLock();
    private final Condition doSomething = lockSignal.newCondition();
    private Activity doThis = Activity.Nothing;

    public KeyBinds keybinds;

    private final Thread tInternal;

    public KeyBindSerializer(KeyBinds binds) {
        this.tInternal = new Thread(this);
        this.tInternal.start();
        this.keybinds = binds; // Set the key binds to the serializer
        this.loadSomething();
        // this.saveSomething(); // Save the game state
    }

    public KeyBinds getKeybinds() {
        return this.keybinds;
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
                    case Activity.Nothing -> {}
                    case Activity.Save -> saveSomething();
                    case Activity.Load -> loadSomething();
                }
            }
        } catch (Exception ex) {
            java.lang.System.out.printf("Something bad happened: %s\n", ex.getMessage());
        }
    }

    /// Public method used by client code to request the game state is saved
    /// NOTE: This does not prevent against race conditions if the gameState object
    ///       is modified while the saving is taking place.  A production level
    ///       approach would have an event held by the client signaled when
    ///       the saving is complete.
    public void saveGameState(KeyBinds keybinds) { // GameScores state
        lockSignal.lock();

        this.keybinds = keybinds;
        doThis = Activity.Save;
        doSomething.signal();

        lockSignal.unlock();
    }

    /// Public method used the client code to request the game state is loaded.
    /// NOTE: Same comment about race conditions as above.
    public void loadGameState(KeyBinds keybinds) { // GameScores state
        lockSignal.lock();

        this.keybinds = keybinds;
        doThis = Activity.Load;
        doSomething.signal();

        lockSignal.unlock();
    }


    /// Public method used to signal this code to perform a graceful shutdown
    public void shutdown() {
        try {
            lockSignal.lock();

            doThis = Activity.Nothing;
            done = true;
            doSomething.signal();

            lockSignal.unlock();

            tInternal.join();
        } catch (Exception ex) {
            java.lang.System.out.printf("Failure to gracefully shutdown thread: %s\n", ex.getMessage());
        }
    }

    /// This is where the actual serialization of the game state is performed.  Have
    /// chosen to save in JSON format for readability for the demo, but the state
    /// could have been stored using a binary serializer for more efficient storage
    public synchronized void saveSomething() {
        try (FileWriter writer = new FileWriter("src/data/keybinds.json")) {
            Gson gson = new Gson();
            gson.toJson(this.keybinds, writer); // this.gameScores
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    /// This is where the actual deserialization of the game state is performed.
    /// Same note as above regarding the choice to use JSON formatting.
    public synchronized void loadSomething() {
        try (FileReader reader = new FileReader("src/data/keybinds.json")) {

            this.keybinds = (new Gson()).fromJson(reader, KeyBinds.class); // Load the current key binds
            this.keybinds.initialized = true;


        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}