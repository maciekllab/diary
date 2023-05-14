package com.example.lab6;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

public class Data {
    private static Data instance;
    int groupsSelectedRow=-1;
    int studentsSelectedRow=-1;
    int updateSelectedRow=-1;
    ClassContainer container;
    Student client ;
    final File studentsCSV;
    final File classesCSV;
    final File pointsCSV;
    final File conditionsCSV;
    final String csvSeparator;
    private Data() throws Exception {
        csvSeparator=";";
        studentsCSV = new File("studentsCSV.csv");
        classesCSV = new File("classesCSV.csv");
        pointsCSV = new File("pointsCSV.csv");
        conditionsCSV = new File("conditionsCSV.csv");
//       readFromCSVFiles();
        //container=setInitialGroups();
        //serializeData();
//       writeToCSVFiles();
       deserializeDataNoCond();
    }
    public void writeToCSVFiles() throws Exception {
        writeStudentsToCSVFile(studentsCSV);
        writePointsToCSVFile(pointsCSV);
        writeConditionsToCSVFile(conditionsCSV);
        writeClassesToCSVFile(classesCSV);
    }
    public void readFromCSVFiles() throws Exception {
            container=ClassContainer.getInstance();
            readClassesFromCSVFile();
            readStudentsFromCSVFile();
            readConditionsFromCSVFile();
            readPointsFromCSVFile();
            client=container.allStudents.findStudent(3124);
        for (Student s:container.allStudents.studentsList) {
            s.conditionsMap.remove("all");
            s.pointsMap.remove("all");
            s.averagePoints=s.calculateAverage();
        }
    }
    public void readStudentsFromCSVFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(studentsCSV.getPath()));
        bufferedReader.readLine();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(csvSeparator);
            Student newStudent = new Student(parts[0],parts[1],Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
            container.allStudents.addExistingStudent(newStudent);
        }
        bufferedReader.close();

    }
    public void readClassesFromCSVFile() throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(classesCSV.getPath()));
            bufferedReader.readLine();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(csvSeparator);
                Class newClass = new Class(parts[0],Integer.parseInt(parts[1]));
                container.groups.put(newClass.groupName,newClass);
            }
            bufferedReader.close();
    }
    public void readConditionsFromCSVFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(conditionsCSV.getPath()));
        bufferedReader.readLine();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(csvSeparator);
            Student student = container.allStudents.findStudent(Integer.parseInt(parts[0]));
            for (int i=1;i<parts.length;i+=2){
                student.conditionsMap.put(parts[i],StudentCondition.valueOf(parts[i+1]));
            }
        }
        bufferedReader.close();
    }
    public void readPointsFromCSVFile() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(pointsCSV.getPath()));
        bufferedReader.readLine();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(csvSeparator);
            Student student = container.allStudents.findStudent(Integer.parseInt(parts[0]));
            for (int i=1;i<parts.length;i+=2){
                String pts=parts[i+1].substring(1,parts[i+1].length()-1);
                List<Integer> ptsList = new ArrayList<>();
                String[] points = pts.split(",");
                for(int j=0;j<points.length;j++){
                    points[j]=points[j].replace(" ","");
                    if(!points[j].equals(""))ptsList.add(Integer.parseInt(points[j]));
                }
                container.groups.get(parts[i]).studentsList.add(student);
                student.pointsMap.put(parts[i],new PointsList(new ArrayList<Integer>(ptsList)));
            }
        }
        bufferedReader.close();
    }
    public void writeStudentsToCSVFile(File file) throws Exception {
        FileWriter writer = new FileWriter(file);
        writeCSVLabel(Student.class,writer);
        writeCSV(container.allStudents.studentsList,Student.class,writer);
        writer.close();
    }
    public void writeConditionsToCSVFile(File file) throws Exception {
        FileWriter writer = new FileWriter(file);
        writer.write("diaryNumber"+csvSeparator+"groupName"+csvSeparator +"studentCondition\n");
        for(Student student : container.allStudents.studentsList){
            StringBuilder line = new StringBuilder();
            line.append(student.diaryNumber).append(csvSeparator);
            for(Map.Entry<String,StudentCondition> entry : student.conditionsMap.entrySet())
                line.append(entry.getKey()).append(csvSeparator).append(entry.getValue()).append(csvSeparator);
            line.append("\n");
            writer.write(line.toString());
        }
        writer.close();
    }
    public void writePointsToCSVFile(File file) throws Exception {
        FileWriter writer = new FileWriter(file);
        writer.write("diaryNumber"+csvSeparator+"groupName"+csvSeparator);
        writeCSVLabel(PointsList.class,writer);
        for(Student student : container.allStudents.studentsList){
            StringBuilder line = new StringBuilder();
            line.append(student.diaryNumber).append(csvSeparator);
            for(Map.Entry<String,PointsList> entry : student.pointsMap.entrySet())
                line.append(entry.getKey()).append(csvSeparator).append(entry.getValue().points).append(csvSeparator);
            line.append("\n");
            writer.write(line.toString());
        }
        writer.close();
    }
    public void writeClassesToCSVFile(File file) throws Exception {
        FileWriter writer = new FileWriter(file);
        List<Class> classesList = new ArrayList<>(container.groups.values());
        writeCSVLabel(Class.class,writer);
        writeCSV(classesList, Class.class,writer);
        writer.close();
    }
    public void writeCSVLabel(java.lang.Class c, FileWriter writer) throws IOException {
        List<Field> fieldsList = new ArrayList<>(Arrays.asList(c.getDeclaredFields()));
        fieldsList.removeIf(fld-> !fld.isAnnotationPresent(Exportable.class));
        StringBuilder line = new StringBuilder();
        for(Field f : fieldsList)line.append(f.getName()).append(csvSeparator);
        line.append("\n");
        writer.write(line.toString());
    }
    public void writeCSV(List<?> objectsList, java.lang.Class c, FileWriter writer) throws IOException, IllegalAccessException {
        List<Field> fieldsList = new ArrayList<>(Arrays.asList(c.getDeclaredFields()));
        fieldsList.removeIf(fld-> !fld.isAnnotationPresent(Exportable.class));
        for (Object obj : objectsList){
            StringBuilder line= new StringBuilder();
            for (Field field: fieldsList) {
                line.append(field.get(obj)).append(csvSeparator);
            }
            line.append("\n");
            writer.write(line.toString());
        }
    }
    public void deserializeStudents() throws Exception {
            FileInputStream input = new FileInputStream("studentsData.ser");
            ObjectInputStream in = new ObjectInputStream(input);
            container.allStudents.studentsList = (List<Student>) in.readObject();
            in.close();
            input.close();
    }
    public void deserializeClasses() throws Exception {
            FileInputStream input = new FileInputStream("classesData.ser");
            ObjectInputStream in = new ObjectInputStream(input);
            container.groups = (Map<String, Class>) in.readObject();
            in.close();
            input.close();
    }
    public void serializeData() throws Exception {
        serializeStudents();
        serializeClasses();
    }
    public void deserializeData()throws Exception{
        container=ClassContainer.getInstance();
        deserializeClasses();
        deserializeStudents();
        client = container.allStudents.findStudent(3124);
        for (Map.Entry<String, Class> entry : container.groups.entrySet()){
            Class m = entry.getValue();
            for (int i = 0; i < m.studentsList.size(); i++) {
                Student s = entry.getValue().studentsList.get(i);
                m.studentsList.set(i, container.allStudents.findStudent(s.diaryNumber));
            }
        }
    }
    public void deserializeDataNoCond() throws Exception{
        container=ClassContainer.getInstance();
        deserializeClasses();
        deserializeStudents();
        client = container.allStudents.findStudent(3124);
        for (Map.Entry<String, Class> entry : container.groups.entrySet()){
            Class m = entry.getValue();
            for (int i = 0; i < m.studentsList.size(); i++) {
                Student s = entry.getValue().studentsList.get(i);
                m.studentsList.set(i, container.allStudents.findStudent(s.diaryNumber));
            }
        }
        for (Student s: container.allStudents.studentsList) {
            s.conditionsMap = new HashMap<>();
            //for (String c: s.pointsMap.keySet())s.conditionsMap.put(container.groups.get(c).groupName,StudentCondition.INACTIVE);
        }
        readConditionsFromCSVFile();
    }
    public void serializeStudents() throws Exception{
        FileOutputStream output = new FileOutputStream("studentsData.ser");
        ObjectOutputStream out = new ObjectOutputStream(output);
        out.writeObject(container.allStudents.studentsList);
        out.close();
        output.close();
    }
    public void serializeClasses() throws Exception{
        FileOutputStream output = new FileOutputStream("classesData.ser");
        ObjectOutputStream out = new ObjectOutputStream(output);
        out.writeObject(container.groups);
        out.close();
        output.close();
    }
    public static Data getInstance() {
        if (instance == null) {
            try {
                instance = new Data();
            } catch (Exception e) {}
        }
        return instance;
    }
    public ClassContainer setInitialGroups(){
        client = new Student("Maciej","Łabuz",2002,3124);
        Student s1 = new Student("Jakub","Nowak",2002,3331);
        ClassContainer classContainer = ClassContainer.getInstance();
        classContainer.addClass("Maths",30);
        classContainer.addClass("Physics",10);
        classContainer.addClass("Biology",15);
        classContainer.addClass("IT",20);
        classContainer.addClass("PE",10);
        Class a1 = classContainer.groups.get("Maths");
        a1.addNewStudent(client, new ArrayList<>(Arrays.asList(75,72,91)),StudentCondition.PRESENT);
        a1.addNewStudent(new Student("Waldemar","Krzysztofiak",2002,6432), new ArrayList<>(Arrays.asList(81,72,39)),StudentCondition.SICK);
        a1.addNewStudent(new Student("Wojciech","Słuszny",1998,9876),new ArrayList<>(Arrays.asList(45,72,55)),StudentCondition.ABSENT);
        a1.addNewStudent(new Student("Kamil","Janik",2001,1589),new ArrayList<>(Arrays.asList(45,54,87)),StudentCondition.PRESENT);
        a1.addNewStudent(new Student("Jan","Robak",2003,1008), new ArrayList<>(Arrays.asList(45,53,12)),StudentCondition.CATCHINGUP);
        a1.addNewStudent(new Student("Jacek","Lemaniak",1999,2417), new ArrayList<>(Arrays.asList(54,73,39)),StudentCondition.RESIGNED);
        a1.addNewStudent(new Student("Kacper","Roch",2001,7160), new ArrayList<>(Arrays.asList(48,72,89)),StudentCondition.PRESENT);
        a1.addNewStudent(new Student("Michał","Filipiec",2001,7380), new ArrayList<>(Arrays.asList(51,56,98)),StudentCondition.ABSENT);
        a1.addNewStudent(new Student("Władysław","Filipiec",2003,1567), new ArrayList<>(Arrays.asList(12,43,39)),StudentCondition.ABSENT);
        a1.addNewStudent(new Student("Filip","Filipiec",2003,9264), new ArrayList<>(Arrays.asList(45,39)),StudentCondition.ABSENT);
        a1.addNewStudent(new Student("Bartosz","Filipiec",2003,4712), new ArrayList<>(Arrays.asList(41,72)),StudentCondition.RESIGNED);
        a1.addNewStudent(new Student("Bartłomiej","Filipiec",2003,5413),new ArrayList<>(Arrays.asList(22,39)),StudentCondition.ABSENT);
        a1.addNewStudent(new Student("Jerzy","Filipiec",2003,2567),new ArrayList<>(Arrays.asList(17,39)),StudentCondition.ABSENT);
        a1.addNewStudent(new Student("Adrian","Filipiec",2003,7325),new ArrayList<>(Arrays.asList(45,74)),StudentCondition.SICK);
        a1.addNewStudent(new Student("Sebastian","Filipiec",2003,8457),new ArrayList<>(Arrays.asList(45,99)),StudentCondition.ABSENT);
        a1.addNewStudent(new Student("Szymon","Filipiec",2003,3888),new ArrayList<>(Arrays.asList(45)),StudentCondition.RESIGNED);
        a1.addNewStudent(new Student("Bartek","Filipiec",2003,4580),new ArrayList<>(Arrays.asList(45,21)),StudentCondition.CATCHINGUP);
        a1.addNewStudent(new Student("Piotr","Filipiec",2003,1524),new ArrayList<>(),StudentCondition.WAITING);
        a1.addNewStudent(new Student("Paweł","Filipiec",2003,7355),new ArrayList<>(Arrays.asList(41,15,74)),StudentCondition.ABSENT);
        Class b1 = classContainer.groups.get("Physics");
        b1.addNewStudent(new Student("Maksymilian","Lem",2004,8241),new ArrayList<>(Arrays.asList(97)),StudentCondition.PRESENT);
        b1.addNewStudent(new Student("Kacper","Lubicz",2002,5783), new ArrayList<>(Arrays.asList(32)),StudentCondition.RESIGNED);
        b1.addNewStudent(new Student("Ignacy","Wajda",2001,2221),new ArrayList<>(Arrays.asList(61,47)),StudentCondition.CATCHINGUP);
        Class b2= classContainer.groups.get("Biology");
        b2.addExistingStudent(client);b2.addPoints(client,53);b2.addPoints(client,20);
        b2.addNewStudent(s1,new ArrayList<>(Arrays.asList(48,77,19)),StudentCondition.PRESENT);
        b1.addNewStudent(new Student("Wojciech","Słuszny",2003,4561),new ArrayList<>(Arrays.asList(53,72,23)),StudentCondition.ABSENT);
        b2.addNewStudent(new Student("Maciej","Lazarczyk",2002,7369),new ArrayList<>(Arrays.asList(21)),StudentCondition.PRESENT);
        b2.addNewStudent(new Student("Kacper","Lubicz",2002,1535),new ArrayList<>(Arrays.asList(95,32)),StudentCondition.SICK);
        Class c1 = classContainer.groups.get("IT");
        c1.addNewStudent(new Student("Kacper","Roch",2001,8451),new ArrayList<>(Arrays.asList(45,60,20)),StudentCondition.PRESENT);
        c1.addExistingStudent(s1);
        c1.addNewStudent(new Student("Michał","Filipiec",2000,4126),new ArrayList<>(Arrays.asList(27,31)),StudentCondition.ABSENT);
        c1.addNewStudent(new Student("Jacek","Makłowicz",1999,5252),new ArrayList<>(Arrays.asList(43,22)),StudentCondition.RESIGNED);
        c1.addExistingStudent(client);
        //c1.addPoints(client,3);c1.addPoints(client,4);c1.addPoints(client,2);c1.addPoints(client,2);c1.addPoints(client,1);
        Class c2 = classContainer.groups.get("PE");
        return classContainer;
    }
}
