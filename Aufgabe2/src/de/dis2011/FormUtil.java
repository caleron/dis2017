package de.dis2011;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Kleine Helferklasse zum Einlesen von Formulardaten
 */
public class FormUtil {
    /**
     * Liest einen String vom standard input ein
     *
     * @param label Zeile, die vor der Eingabe gezeigt wird
     * @return eingelesene Zeile
     */
    public static String readString(String label) {
        String ret = null;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print(label + ": ");
            ret = stdin.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * Liest einen Integer vom standard input ein
     *
     * @param label Zeile, die vor der Eingabe gezeigt wird
     * @return eingelesener Integer
     */
    public static int readInt(String label) {
        int ret = 0;
        boolean finished = false;

        while (!finished) {
            String line = readString(label);

            try {
                ret = Integer.parseInt(line);
                finished = true;
            } catch (NumberFormatException e) {
                System.err.println("Ungültige Eingabe: Bitte geben Sie eine Zahl an!");
            }
        }

        return ret;
    }

    public static boolean readBool(String label) {
        boolean ret = false;
        boolean finished = false;

        while (!finished) {
            String line = readString(label + " (1/y oder 0/n)").toLowerCase();

            switch (line) {
                case "1":
                case "y":
                case "j":
                case "ja":
                    ret = true;
                    finished = true;
                    break;
                case "0":
                case "n":
                case "no":
                case "nein":
                    ret = false;
                    finished = true;
                    break;
                default:
                    System.out.println("Falsches format!");
                    break;
            }
        }

        return ret;
    }

    public static Date readDate(String label) {
        System.out.println(label + ": ");
        Date date = null;
        while (date == null) {
            int year = readInt(" -Jahr");
            int month = readInt(" -Monat");
            int day = readInt(" -Tag");

            //ist deprecated, ich weiß
            try {
                date = new Date(year, month, day);
            } catch (Exception e) {
                System.out.println("Ungültiges Datum!");
            }
        }
        return date;
    }
}
