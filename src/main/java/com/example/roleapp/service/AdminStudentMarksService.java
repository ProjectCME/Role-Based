package com.example.roleapp.service;

import com.example.roleapp.dto.AdminMarkViewDto;
import com.example.roleapp.model.Marks;
import com.example.roleapp.repository.MarksRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminStudentMarksService {

    private final MarksRepository marksRepository;

    public AdminStudentMarksService(MarksRepository marksRepository) {
        this.marksRepository = marksRepository;
    }

    public List<AdminMarkViewDto> getAllMarksForAdmin() {
        List<Marks> marks = marksRepository.findAll();
        List<AdminMarkViewDto> list = new ArrayList<>();

        for (Marks m : marks) {
            AdminMarkViewDto dto = new AdminMarkViewDto();

            dto.setStudentName(m.getStudent().getName());
            dto.setStudentUniqueId(m.getStudent().getUniqueId());

            dto.setSubjectName(m.getSubject().getSubjectName());
            dto.setAcademicYear(m.getSubject().getAcademicYear());
            dto.setSemester(m.getSubject().getSemester());

            dto.setExamType(m.getExamType().name());
            dto.setMarks(m.getMarks());

            dto.setTeacherName(m.getTeacher().getName());

            list.add(dto);
        }

        return list;
    }
}
