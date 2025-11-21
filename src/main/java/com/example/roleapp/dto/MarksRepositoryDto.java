package com.example.roleapp.dto;

public class MarksRepositoryDto {
    private String subjectName;

    private Integer ia1;
    private Integer ia2;
    private Integer ia3;
    private Integer special;

    private Double bestTwoAverage;

    public MarksRepositoryDto(String subjectName,Integer ia1,Integer ia2,Integer ia3,Integer special,Double bestTwoAverage){
        this.subjectName=subjectName;
        this.ia1=ia1;
        this.ia2=ia2;
        this.ia3=ia3;
        this.special=special;
        this.bestTwoAverage=bestTwoAverage;
    }
    //Getters
    public String getSubjectName(){return subjectName;}
    public Integer getIa1(){return ia1;}
    public Integer getIa2(){return ia2;}
    public Integer getIa3(){return ia3;}
    public Integer  getSpecial(){return special;}
    public Double getBestTwoAverage(){return bestTwoAverage;}

    //Setters
    public void setSubjectName(String subjectName){this.subjectName=subjectName;}
    public void setIa1(Integer ia1){this.ia1=ia1;}
    public void setIa2(Integer ia2){this.ia2=ia2;}
    public void setIa3(Integer ia3){this.ia3=ia3;}
    public void setSpecial(Integer special){this.special=special;}
    public void setBestTwoAverage(Double bestTwoAverage){this.bestTwoAverage=bestTwoAverage;}
}
