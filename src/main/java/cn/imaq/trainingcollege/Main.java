package cn.imaq.trainingcollege;

import cn.imaq.tompuss.TomPuss;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        TomPuss tomPuss = new TomPuss();
        tomPuss.setPort(8080);
        tomPuss.setResourceRoot(new File("webroot"));
        tomPuss.start();
    }
}
