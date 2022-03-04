package com.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public static List<String> readFromInputStream(InputStream inputStream) throws IOException {
        List<String> allLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                allLines.add(line);
            }
        }
        return allLines;
    }

    public static void writeStringToFile(FileOutputStream outputStream, String data) throws IOException {
        byte[] strToBytes = data.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();
    }

}
