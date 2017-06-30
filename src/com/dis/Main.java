package com.dis;

import com.dis.data.DatabaseInitializer;
import com.opencsv.CSVReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
//        DatabaseInitializer.init();
        readCsv();
    }

    private static void readCsv() {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("sales.csv"), "windows-1252"), ';');
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                System.out.println(nextLine[0] + nextLine[1] + "etc...");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
