package com.shivamjaiswal;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by shivam.jaiswal on 12/12/16.
 */
public class DownloadPdf {
    static List<String> fileNameSet = new ArrayList<>();
    static int atomic = 1;

    public static void main(String[] args) throws IOException {

        List<String> filePaths = getFilePaths();
        for (String filePath : filePaths) {
            download(filePath, StringUtils.substringAfter(filePath, "MSDS/"));
        }
    }

    private static List<String> getFilePaths() throws IOException {
        List<String> filePaths = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get("/Users/shivam.jaiswal/Downloads/a.txt"))) {
            stream.forEach(filePaths::add);
        }
        return filePaths;
    }

    public static void download(String filePath, String fileName) throws IOException {
        fileName = getFileName(fileName);
        System.out.println("PUPU : "+fileName);
        fileName = fileName.concat(".pdf");

        System.out.println("Downloading file : " + fileName);
        URL url = new URL(filePath);
        InputStream in = url.openStream();
        Files.copy(in, Paths.get("/Users/shivam.jaiswal/Downloads/work/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        in.close();
    }

    private static String getFileName(String filename) {

        if (fileNameSet.contains(filename)) {
            atomic++;
            return filename + "(" + atomic + ")";
        } else {
            atomic = 0;
            fileNameSet.add(filename);
            return filename;
        }
    }
}
