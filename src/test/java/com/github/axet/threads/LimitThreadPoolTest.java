package com.github.axet.threads;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.axet.threads.LimitThreadPool;

/**
 * example hungs on max osx due to java bug. and works on linux hosts.
 * 
 * @author axet
 * 
 */
public class LimitThreadPoolTest {

    static AtomicInteger c = new AtomicInteger();

    public static void main(String[] args) {
        try {
            LimitThreadPool l = new LimitThreadPool(1);

            for (int i = 0; i < 10000; i++) {
                System.out.println(i + "enter " + Thread.currentThread().getId());
                l.blockExecute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        c.addAndGet(1);
                        System.out.println("done " + Thread.currentThread().getId());
                    }
                });
                System.out.println("exit " + Thread.currentThread().getId());
            }

            l.waitUntilTermination();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(c);
    }
}