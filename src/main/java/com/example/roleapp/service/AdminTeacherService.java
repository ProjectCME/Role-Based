package com.example.roleapp.service;

import com.example.roleapp.dto.AdminTeacherSubjectDto;
import com.example.roleapp.model.TeacherSubject;
import com.example.roleapp.repository.TeacherSubjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminTeacherService {

    private final TeacherSubjectRepository teacherSubjectRepository;

    public AdminTeacherService(TeacherSubjectRepository teacherSubjectRepository) {
        this.teacherSubjectRepository = teacherSubjectRepository;
    }

    public List<AdminTeacherSubjectDto> getAllTeacherSubjectMappings() {
        List<TeacherSubject> mappings = teacherSubjectRepository.findAll();
        List<AdminTeacherSubjectDto> out = new ArrayList<>();

        for (TeacherSubject t : mappings) {
            AdminTeacherSubjectDto dto = new AdminTeacherSubjectDto();

            if (t.getTeacher() != null) {
                dto.setTeacherName(t.getTeacher().getName());
                dto.setTeacherEmail(t.getTeacher().getEmail());
            } else {
                dto.setTeacherName("Unknown");
                dto.setTeacherEmail("");
            }

            if (t.getSubject() != null) {
                dto.setSubjectName(t.getSubject().getSubjectName());
                dto.setSemester(t.getSubject().getSemester());
                dto.setAcademicYear(t.getSubject().getAcademicYear());
            } else {
                dto.setSubjectName("Unknown");
                dto.setSemester(null);
                dto.setAcademicYear(null);
            }

            out.add(dto);
        }

        return out;
    }
}
