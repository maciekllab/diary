package com.example.lab6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContainer {
    Map<String,Class> groups;
    Class allStudents;
   public static int groupsAmount=0;
   public static ClassContainer instance;

    private ClassContainer() {
        groups = new HashMap<>();
        allStudents = new Class("all",100000);
    }
    public static ClassContainer getInstance(){
        if(instance==null)instance=new ClassContainer();
        return instance;
    }

    public void addClass(String name, int amount){
        Class newClass = new Class(name,amount);
        groups.put(name,newClass);
        groupsAmount++;
    }
    public void removeClass(String name){
        Class temp = this.groups.get(name);
        groups.remove(name);
        groupsAmount--;
        for (Student a: temp.studentsList) {
            a.pointsMap.remove(temp.groupName);
            a.conditionsMap.remove(temp.groupName);
            a.averagePoints= a.calculateAverage();
        }
    }
    public List<Class> findEmpty(){
        List<Class> toReturn = new ArrayList<>();
        for(Map.Entry<String,Class> entry : groups.entrySet()){
            if(entry.getValue().studentsList.size()==0)toReturn.add(entry.getValue());
        }
        return toReturn;
    }
    public List<Class> findFull(){
        List<Class> toReturn = new ArrayList<>();
        for(Map.Entry<String,Class> entry : groups.entrySet()){
            if(entry.getValue().studentsList.size()==entry.getValue().maxStudents)toReturn.add(entry.getValue());
        }
        return toReturn;
    }
    public void summary(){
        System.out.println("Number of groups: "+groupsAmount);
        for (Map.Entry<String,Class> entry : groups.entrySet()){
            double percentage= entry.getValue().studentsList.size()/(double)(entry.getValue().maxStudents)*100;
            System.out.println(entry.getKey()+" "+percentage+ " % full");
        }
    }
}
