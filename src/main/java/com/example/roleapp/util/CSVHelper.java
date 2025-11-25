package com.example.roleapp.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static class CsvRecord {
        public Integer studentUniqueId;
        public Integer marks;
        public CsvRecord(Integer studentUniqueId, Integer marks) {
            this.studentUniqueId = studentUniqueId;
            this.marks = marks;
        }
    }

    public static List<CsvRecord> parse(MultipartFile file) throws IOException {
        List<CsvRecord> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 2) continue; 

                try {
                    Integer studentId = Integer.parseInt(data[0].trim());
                    String marksStr = data[1].trim();

                    Integer marks = null;
                    // If entered "Ab", "Absent", or left empty, keep marks as null
                    if (marksStr.equalsIgnoreCase("Ab") || 
                        marksStr.equalsIgnoreCase("Absent") || 
                        marksStr.isEmpty()) {
                        marks = null;
                    } else {
                        // Otherwise parse
                        marks = Integer.parseInt(marksStr);
                    }

                    records.add(new CsvRecord(studentId, marks));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid row: " + line);
                }
            }
        }
        return records;
    }
}