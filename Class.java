package com.example.lab6;

import java.io.Serializable;
import java.util.*;

public class Class implements Serializable {

    private static final long serialVersionUID = 5433649007081786727L;
    @Exportable
    public String groupName;
    @Exportable
    public int maxStudents;
    List<Student> studentsList;

    public String getGroupName() {
        return groupName;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }
    public Student findStudent(int num){
        for (Student s :studentsList){
            if(s.diaryNumber==num)return s;
        }
        return null;
    }
    public Class(String groupName, int maxStudents) {
        this.groupName = groupName;
        this.maxStudents = maxStudents;
        this.studentsList = new ArrayList<>();
    }

    public boolean addNewStudent(Student student, ArrayList<Integer> pts, StudentCondition cond){
        if(studentsList.size()>= maxStudents){
            System.out.println("Group size exceeded - can't add anymore");
            System.err.println("Group size exceeded - can't add anymore");
            return false;
        }
        if(ClassContainer.getInstance().allStudents.studentsList.stream().anyMatch((st) -> st.diaryNumber==student.diaryNumber)){
            System.out.println("Student already exists in this group");
            return false;
        }
        if(!ClassContainer.getInstance().allStudents.studentsList.contains(student))ClassContainer.getInstance().allStudents.studentsList.add(student);
        studentsList.add(student);
        student.conditionsMap.put(this.groupName,cond);
        student.pointsMap.put(this.groupName,new PointsList(pts));
        student.averagePoints = student.calculateAverage();
        return true;
    }
    public boolean addExistingStudent(Student student){
        if(studentsList.size()>= maxStudents){
            System.out.println("Group size exceeded - can't add anymore");
            System.err.println("Group size exceeded - can't add anymore");
            return false;
        }
        if(this.studentsList.stream().anyMatch((st) -> st.diaryNumber==student.diaryNumber)){
            System.out.println("Student already exists in this group");
            return false;
        }
        studentsList.add(student);
        student.conditionsMap.put(this.groupName,StudentCondition.NEW);
        student.pointsMap.put(this.groupName,new PointsList(new ArrayList<>()));
        student.averagePoints = student.calculateAverage();
        return true;
    }
    public void addPoints(Student student, int pts){
        student.pointsMap.get(this.groupName).points.add(pts);
        student.averagePoints = student.calculateAverage();
        student.pointsMap.get(this.groupName).updateAverage();
    }
    public void changeCondition(Student student, StudentCondition cond){
        student.conditionsMap.replace(this.groupName,cond);
    }
    public void removePoints(Student student, int pts){
        student.pointsMap.get(this.groupName).points.remove(Integer.valueOf(pts));
        student.averagePoints = student.calculateAverage();
        student.pointsMap.get(this.groupName).updateAverage();
    }
    public Student search(String srname){
        return this.studentsList.stream().filter((student) -> student.surname.compareTo(srname)==0).findAny().orElse(null);
    }
    public List<Student>searchPartial(String pattern){
        return this.studentsList.stream().filter((student) -> student.surname.contains(pattern)).toList();
    }
    public void summary(){
        studentsList.forEach(st -> System.out.println(st.toString()));
    }
    public  void sortByName(){
        Collections.sort(this.studentsList);
    }
    public void sortByPoints() {
        this.studentsList.sort((o1, o2) -> -Double.compare(o1.averagePoints, o2.averagePoints));
    }
    public Student max(){
        return Collections.max(this.studentsList , Comparator.comparingDouble(o -> o.averagePoints));
    }
}
