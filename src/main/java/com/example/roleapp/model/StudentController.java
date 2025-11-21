package com.example.roleapp.model;

import com.example.roleapp.dto.MarksRepositoryDto;
import com.example.roleapp.service.StudentMarksService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentMarksService studentMarksService;

    public StudentController(StudentMarksService studentMarksService) {
        this.studentMarksService = studentMarksService;
    }

    @GetMapping("/{studentId}/marks")
    public List<MarksRepositoryDto> getMarks(
            @PathVariable Long studentId,
            @RequestParam Integer academicYear,
            @RequestParam Integer semester
    ) {
        return studentMarksService.getSemesterMarks(studentId, academicYear, semester);
    }
}
