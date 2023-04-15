package com.jostea.zomboid.whitelist.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Logger {

    public static void log(String message) {
        try {

            LocalDateTime currentDateTime = LocalDateTime.now();

            File file = new File("log.txt");
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("----" + currentDateTime + "----:" + message);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}