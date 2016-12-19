package com.shivamjaiswal;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shivam.jaiswal on 12/12/16.
 */
public class ScrapPdf {
    private static final String toWriteFilePath = "/Users/shivam.jaiswal/Downloads/b.txt";
    private static final String filesFolderPath = "/Users/shivam.jaiswal/Downloads/work/";
    private static final String startingTexts = "UN number:\n";
    private static final String endingTexts = " \n" +
            "  Flammability";

    public static void main(String[] args) {
        List<String> files = getFiles();
        for (String file : files) {
            scrap(file);
        }
    }

    public static void writeToFile(String text, String fileName) throws FileNotFoundException {

        if(null!=text){
            text = text.replace("\n", "");
        }
        System.out.println("writing to file : " + text);
        String lineNew = "\n";
        String tab = "\t";
        try {
            if(null!=text){
                Files.write(Paths.get(toWriteFilePath), text.getBytes(), StandardOpenOption.APPEND);
            } else{
                Files.write(Paths.get(toWriteFilePath), "null".getBytes(), StandardOpenOption.APPEND);
            }
            Files.write(Paths.get(toWriteFilePath), tab.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(toWriteFilePath), fileName.getBytes(), StandardOpenOption.APPEND);
            Files.write(Paths.get(toWriteFilePath), lineNew.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void scrap(String file) {
        try {
            PDDocument document = null;
            System.out.println("File : " + file);
            document = PDDocument.load(new File(filesFolderPath + file));
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper Tstripper = new PDFTextStripper();
                String st = Tstripper.getText(document);
//                System.out.println(st);
                String iwant = StringUtils.substringBetween(st, startingTexts, endingTexts);
                writeToFile(iwant, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getFiles() {
        List<String> files = new ArrayList<>();
        File folder = new File(filesFolderPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
//                System.out.println("File " + listOfFiles[i].getName());
                files.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
            }
        }
        return files;
    }
}
