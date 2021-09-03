package com.example.mycards.utility;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorService that uses the current thread so can be injected into test classes that are dependent on ExecutorService.
 * Sources:
 * + (Basis for below) https://stackoverflow.com/questions/6581188/is-there-an-executorservice-that-uses-the-current-thread
 * + (Back up, to try if the below doesn't work) https://gist.github.com/vladimir-bukhtoyarov/38d6b4b277d0a0cfb3af
 */
public class CurrentThreadExecutor extends AbstractExecutorService {

    public CurrentThreadExecutor() { }

    @Override
    public void execute(Runnable command) {
        command.run();
    }

    @Override
    public void shutdown() {
    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }
}
