package uj.java.kindergarten;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class ChildImpl extends Child implements Runnable {

    private final Lock leftFork;
    private final Lock rightFork;

    private ChildImpl leftChild;
    private ChildImpl rightChild;

    private boolean isHungriest() {
        return leftChild.happiness() >= this.happiness() && rightChild.happiness() >= this.happiness();
    }

    public void addNearChildren(ChildImpl leftChild, ChildImpl rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    public ChildImpl(String name, int hungerSpeedMs, ReentrantLock leftFork, ReentrantLock rightFork) {
        super(name, hungerSpeedMs);
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (isHungriest()) {
                    if (leftFork.tryLock()) {
                        Thread.sleep(1);

                        if (isHungriest()) {
                            if (rightFork.tryLock()) {
                                eat();
                                rightFork.unlock();
                            }
                        }

                        leftFork.unlock();
                    }
                }
                Thread.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
    }

}
