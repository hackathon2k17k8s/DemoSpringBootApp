package com.example.demo.services;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class ActionService {

    static Logger log = Logger.getLogger(ActionService.class);

    public static void createThread(int count) {
        for(int i = 0; i < count; i++) {
            (new Thread(new DemoThread())).start();
        }
    }

    public static void gc() {
        log.info("Running GC");
        System.gc();
    }

    public static byte[] loadRam(int mem) {
        byte[] stab = new byte[1024 * mem];
        log.info("Generated RAM load");
        return stab;
    }

    public static void loadCpu() {
        log.warn("Generating high CPU load for 30 seconds");
        long sleepTime = 30000000000L;
        long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) < sleepTime) {}
        
    }
}

class DemoThread implements Runnable {

    static Logger log = Logger.getLogger(DemoThread.class);

    public void run() {
        BasicConfigurator.configure();
        log.debug("Thread created, sleeping for 30 seconds");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
            log.debug(e.getStackTrace());
        }
    }
}