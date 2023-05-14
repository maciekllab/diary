package com.example.lab6;

import java.beans.Transient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Comparable<Student>, Serializable {
    private static final long serialVersionUID = 8180806379041295298L;
    @Exportable
    String name;
    @Exportable
    String surname;
    @Exportable
    int birthYear;
    @Exportable
    int diaryNumber;
    int averagePoints;
    Map<String,PointsList> pointsMap;
    transient Map<String,StudentCondition> conditionsMap;

     public Student(String name, String surname,  int birthYear, int diaryNumber) {
         this.name = name;
         this.surname = surname;
         this.birthYear = birthYear;
         this.averagePoints=0;
         this.diaryNumber = diaryNumber;
         this.pointsMap = new HashMap<>();
         this.conditionsMap = new HashMap<>();
     }
     int calculateAverage(){
         int sum =0;
         int count=0;
         for (Map.Entry<String, PointsList> entry : pointsMap.entrySet()) {
             sum+= entry.getValue().average;
             if(!entry.getValue().points.isEmpty())count++;
         }
         if(count!=0) return sum/count;
         return 0;
     }

     @Override
     public String toString() {
         return "Student{" +
                 "name='" + name + '\'' +
                 ", surname='" + surname + '\'' +
                 ", birthYear=" + birthYear +
                 ", diaryNumber=" + diaryNumber +
                 ", points=" + averagePoints +
                 '}';
     }

     @Override
     public int compareTo(Student student) {
         return this.surname.compareTo(student.surname);
     }

}
