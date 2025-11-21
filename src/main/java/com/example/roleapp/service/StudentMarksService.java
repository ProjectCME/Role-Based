package com.example.roleapp.service;

import com.example.roleapp.model.Marks;
import com.example.roleapp.model.Student;
import com.example.roleapp.repository.MarksRepository;
import com.example.roleapp.repository.StudentRepository;
import com.example.roleapp.dto.MarksRepositoryDto;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentMarksService {
    private final MarksRepository marksRepository;
    private final StudentRepository studentRepository;

    public StudentMarksService(MarksRepository marksRepository, StudentRepository studentRepository){
        this.marksRepository=marksRepository;
        this.studentRepository=studentRepository;
    }
    public List<MarksRepositoryDto> getSemesterMarks(Long studentId,Integer academicYear,Integer semester){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Marks> marksList =
                marksRepository.findByStudentAndSubject(
                        student, academicYear, semester);

        if (marksList.isEmpty()) {
            throw new RuntimeException("Marks not uploaded yet for this year/semester");
        }

        Map<Long, List<Marks>> groupedBySubject = new HashMap<>();
        for (Marks m : marksList) {
            groupedBySubject.computeIfAbsent(m.getSubject().getId(), k -> new ArrayList<>()).add(m);
        }

        List<MarksRepositoryDto> response = new ArrayList<>();

        for (List<Marks> subjectMarks : groupedBySubject.values()) {

            String subjectName = subjectMarks.get(0).getSubject().getSubjectName();

            Integer ia1 = null, ia2 = null, ia3 = null, special = null;
            List<Integer> iaMarks = new ArrayList<>();

            for (Marks m : subjectMarks) {
                switch (m.getExamType()) {
                    case IA1 -> { ia1 = m.getMarks(); iaMarks.add(m.getMarks()); }
                    case IA2 -> { ia2 = m.getMarks(); iaMarks.add(m.getMarks()); }
                    case IA3 -> { ia3 = m.getMarks(); iaMarks.add(m.getMarks()); }
                    case SPECIAL -> special = m.getMarks();
                }
            }

            // Best 2 IA marks average
            Double bestTwoAvg = null;
            if (iaMarks.size() >= 2) {
                iaMarks.sort(Collections.reverseOrder());
                bestTwoAvg = (iaMarks.get(0) + iaMarks.get(1)) / 2.0;
            }

            response.add(new MarksRepositoryDto(
                    subjectName, ia1, ia2, ia3, special, bestTwoAvg
            ));
        }

        return response;
    }
}
