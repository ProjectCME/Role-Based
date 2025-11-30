package com.example.roleapp.service;

import com.example.roleapp.dto.MarkDto;
import com.example.roleapp.model.*;
import com.example.roleapp.repository.*;
import com.example.roleapp.util.CSVHelper;
import com.example.roleapp.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private MarksRepository marksRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private TeacherSubjectRepository teacherSubjectRepository;

    // Helper to find teacher by Unique ID (Single Source of Truth)
    private User getTeacherById(String teacherIdString) {
        Integer teacherId = Integer.parseInt(teacherIdString);
        return Optional.ofNullable(userRepository.findByUniqueId(teacherId))
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + teacherId));
    }

    public List<Subject> getAssignedSubjects(String teacherIdString) {
        User teacher = getTeacherById(teacherIdString);
        return teacherSubjectRepository.findByTeacher(teacher).stream()
                .map(TeacherSubject::getSubject)
                .collect(Collectors.toList());
    }

    // CSV Upload Logic
    @Transactional
    public void saveMarks(MultipartFile file, Long subjectId, String examTypeStr, String teacherIdString)
            throws IOException {

        // Logic updated to use ID, comment preserved
        User teacher = getTeacherById(teacherIdString);

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found"));

        Marks.ExamType examType = Marks.ExamType.valueOf(examTypeStr);
        List<CSVHelper.CsvRecord> records = CSVHelper.parse(file);

        for (CSVHelper.CsvRecord record : records) {
            User student = Optional.ofNullable(userRepository.findByUniqueId(record.studentUniqueId))
                    .orElse(null);

            if (student == null) continue;

            Marks markEntity = marksRepository
                    .findByStudentAndSubjectAndExamType(student, subject, examType)
                    .orElse(new Marks());

            if (markEntity.getId() == null) {
                markEntity.setStudent(student);
                markEntity.setSubject(subject);
                markEntity.setTeacher(teacher);
                markEntity.setExamType(examType);
            }

            markEntity.setMarks(record.marks);
            marksRepository.save(markEntity);
        }
    }

    // Fetch assigned subjects
    public List<Subject> getAssignedSubjectsById(String teacherIdString) {
        // Logic updated to use ID, comment preserved
        User teacher = getTeacherById(teacherIdString);
        return teacherSubjectRepository.findByTeacher(teacher).stream()
                .map(TeacherSubject::getSubject)
                .collect(Collectors.toList());
    }

    // Creating Views
    public List<MarkDto> getTeacherViewData(String teacherIdString, Integer year, Integer semester, Long subjectId) {
        // Logic updated to use ID, comment preserved
        User teacher = getTeacherById(teacherIdString);
        
        List<Marks> rawMarks = marksRepository.findByTeacher(teacher);

        // Filter Logic
        if (subjectId != null) {
            rawMarks = rawMarks.stream().filter(m -> m.getSubject().getId().equals(subjectId)).toList();
        }
        if (year != null) {
            rawMarks = rawMarks.stream().filter(m -> m.getSubject().getAcademicYear().equals(year)).toList();
        }
        if (semester != null) {
            rawMarks = rawMarks.stream().filter(m -> m.getSubject().getSemester().equals(semester)).toList();
        }

        // Grouping Logic
        Map<String, List<Marks>> grouped = rawMarks.stream()
                .collect(Collectors.groupingBy(m -> m.getStudent().getUniqueId() + "-" + m.getSubject().getId()));

        List<MarkDto> response = new ArrayList<>();

        for (List<Marks> entry : grouped.values()) {
            if (entry.isEmpty()) continue;

            Marks ref = entry.get(0);
            MarkDto dto = new MarkDto();
            dto.setSubjectName(ref.getStudent().getName() + " (" + ref.getSubject().getSubjectName() + ")");

            String ia1 = "Absent", ia2 = "Absent", ia3 = "Absent", special = "Absent";

            for (Marks m : entry) {
                String val = (m.getMarks() == null) ? "Absent" : String.valueOf(m.getMarks());
                switch(m.getExamType()) {
                    case IA1 -> ia1 = val;
                    case IA2 -> ia2 = val;
                    case IA3 -> ia3 = val;
                    case SPECIAL -> special = val;
                }
            }

            dto.setIa1(ia1);
            dto.setIa2(ia2);
            dto.setIa3(ia3);
            dto.setSpecial(special);
            dto.setBestTwoAvg(null);

            response.add(dto);
        }
        return response;
    }
}