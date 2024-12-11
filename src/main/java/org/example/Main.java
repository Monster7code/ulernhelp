package org.example;

import org.example.db.DbRepositoty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String filePath = "/home/kirill/IdeaProjects/ulernhelp/basicprogramming_2.csv";

        //List<Student> students = Parser.parseCSV(filePath);
        DbRepositoty.connect();

        //DbRepositoty.createTableStudents();

       // DbRepositoty.saveStudents(students);
        HashMap<String, Double[]> students_on_bd = DbRepositoty.getStudentsOrderBy();
        printStudents(students_on_bd);

    }
    public static void printStudents(HashMap<String, Double[]> students_on_bd){
        for (Map.Entry<String, Double[]> entry : students_on_bd.entrySet()) {
            String studentName = entry.getKey();
            Double[] distance_marks = entry.getValue();

            System.out.println("Студент: " + studentName + "\nДистанция: " + distance_marks[0]+
                    "\nСредняя оценка:" + distance_marks[1]);

            System.out.println("______________________");
        }
    }
}