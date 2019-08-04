package com.ws.lock;

import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;

/**
 * @author Jun
 * data  2019-08-04 0:27
 */
public class TestShareData {

    public static String text = "thisistestsharethread";

    public static void main(String[] args) {

        ShareData shareData = new ShareData(50);

        new Thread(() -> {
            IntStream.range(0, text.length()).forEach(i -> {
                try {
                    char c = text.charAt(i);
                    shareData.write(c);
                    System.out.println(currentThread() + " write -> " + c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }).start();


        IntStream.range(0, 10).forEach(i -> {

            new Thread(() -> {
                try {
                    while (true) {
                        char[] read = shareData.read();
                        java.lang.String s = new java.lang.String(read);
                        System.out.println(new java.lang.String(read));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        });

    }
}
