package com.example.roleapp.dto;

public class MarkDto {

    private String subjectName;

    private String ia1;
    private String ia2;
    private String ia3;
    private String special;

    private Double bestTwoAvg;

    // Getters

    
    public String getSubjectName() {
        return subjectName;
    }
    public String getIa1() {
        return ia1;
    }
    public String getIa2() {
        return ia2;
    }
    public String getIa3() {
        return ia3;
    }
    public String getSpecial() {
        return special;
    }
    public Double getBestTwoAvg() {
        return bestTwoAvg;
    }


    // Setters
    
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    public void setIa1(String ia1) {
        this.ia1 = ia1;
    }
    public void setIa2(String ia2) {
        this.ia2 = ia2;
    }
    public void setIa3(String ia3) {
        this.ia3 = ia3;
    }
    public void setSpecial(String special) {
        this.special = special;
    }
    public void setBestTwoAvg(Double bestTwoAvg) {
        this.bestTwoAvg = bestTwoAvg;
    }


}

