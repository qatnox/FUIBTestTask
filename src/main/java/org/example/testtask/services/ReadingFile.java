//package org.example.testtask.services;
//
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvException;
//import org.apache.commons.io.FilenameUtils;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.xml.stream.XMLInputFactory;
//import javax.xml.stream.XMLStreamException;
//import javax.xml.stream.XMLStreamReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//@Component
//public class ReadingFile {
//
//    @Value("${readingFile.uploadDir}")
//    private String uploadDir;
//
//    public String getUploadDir() {
//        return uploadDir;
//    }
//
//    public void extensionCheck(MultipartFile file) throws FileNotFoundException {
//        String fileName = file.getOriginalFilename();
//        if (FilenameUtils.getExtension(fileName).equals("csv")) {
//            readingCSV(file);
//        } else if (FilenameUtils.getExtension(fileName).equals("xml")) {
//            readingXML(file);
//        }
//    }
//
//    public void readingCSV(MultipartFile file) {
//        String filePath = getUploadDir() + file.getOriginalFilename();
//
//        List<String[]> list;
//        try (FileReader fileReader = new FileReader(filePath);
//             CSVReader csvReader = new CSVReader(fileReader)) {
//
//            list = csvReader.readAll();
//        } catch (IOException | CsvException e) {
//            throw new RuntimeException("Failed to read CSV file", e);
//        }
//
//        for (String[] str : list) {
//            System.out.println(Arrays.toString(str));
//        }
//    }
//
//    public void readingXML(MultipartFile file) {
//
//        String filePath = getUploadDir() + file.getOriginalFilename();
//        try {
//            XMLStreamReader xmlr = XMLInputFactory.newInstance().createXMLStreamReader(filePath, new FileInputStream(filePath));
//
//            while (xmlr.hasNext()) {
//                xmlr.next();
//                if (xmlr.isStartElement()) {
//                    System.out.println("<" + xmlr.getLocalName() + ">");
//                } else if (xmlr.isEndElement()) {
//                    System.out.println("</" + xmlr.getLocalName() + ">");
//                } else if (xmlr.hasText() && !xmlr.getText().trim().isEmpty()) {
//                    System.out.println("   " + xmlr.getText());
//                }
//            }
//        } catch (XMLStreamException | FileNotFoundException e) {
//            throw new RuntimeException("Failed to read XML", e);
//        }
//    }
//}
