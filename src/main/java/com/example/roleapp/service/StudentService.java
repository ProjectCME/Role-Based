package com.example.roleapp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roleapp.dto.MarkDto;
import com.example.roleapp.model.Marks;
import com.example.roleapp.repository.MarksRepository;
import com.example.roleapp.model.Subject;

@Service
public class StudentService {

    @Autowired
    private MarksRepository marksRepository;

    public List<MarkDto> getGrades(Integer studentUniqueId, Integer year, Integer semester) {

        List<Marks> marksList =
                marksRepository.findByStudentUniqueIdAndSubjectAcademicYearAndSubjectSemester(
                        studentUniqueId, year, semester);  

        if (marksList.isEmpty()) {
            return List.of(); // no results
        }

        Map<Long, List<Marks>> grouped = marksList.stream()
                .collect(Collectors.groupingBy(m -> m.getSubject().getId()));

        List<MarkDto> response = new ArrayList<>();

        for (var entry : grouped.entrySet()) {
            List<Marks> subjectMarks = entry.getValue();
            Subject subject = subjectMarks.get(0).getSubject();

            String ia1 = "Absent";
            String ia2 = "Absent";
            String ia3 = "Absent";
            String special = "Absent";

            List<Integer> iaValues = new ArrayList<>();

            for (Marks m : subjectMarks) {
                if (m.getMarks() == null) continue;

                switch (m.getExamType()) {
                    case IA1 -> { ia1 = String.valueOf(m.getMarks()); iaValues.add(m.getMarks()); }
                    case IA2 -> { ia2 = String.valueOf(m.getMarks()); iaValues.add(m.getMarks()); }
                    case IA3 -> { ia3 = String.valueOf(m.getMarks()); iaValues.add(m.getMarks()); }
                    case SPECIAL -> special = String.valueOf(m.getMarks());
                }
            }

            Double avgBestTwo = null;
            if (iaValues.size() >= 2) {
                iaValues.sort(Collections.reverseOrder());
                avgBestTwo = (iaValues.get(0) + iaValues.get(1)) / 2.0;
            }

            MarkDto dto = new MarkDto();
            dto.setSubjectName(subject.getSubjectName());
            dto.setIa1(ia1);
            dto.setIa2(ia2);
            dto.setIa3(ia3);
            dto.setSpecial(special);
            dto.setBestTwoAvg(avgBestTwo);

            response.add(dto);
        }

        return response;
    }
}
