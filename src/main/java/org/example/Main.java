package org.example;
import org.example.diagrams.Diagrams;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import org.example.db.DbRepositoty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {


        String filePath = "/home/kirill/IdeaProjects/ulernhelp/basicprogramming_2.csv";

        //List<Student> students = Parser.parseCSV(filePath);
        DbRepositoty.connect();

        //DbRepositoty.createTableStudents();

        //DbRepositoty.saveStudents(students);
        List<Student> students_on_bd = DbRepositoty.getStudentsOrderBy();
        //printStudents(students_on_bd);



        JFreeChart chart = Diagrams.createDiagram(students_on_bd);
        // Создание панели для отображения графика
        ChartPanel chartPanel = new ChartPanel(chart);

        JFreeChart chartHistDist = Diagrams.createHistogram(getAllFeacher(students_on_bd, "Дистанция"),
                "Distance").getChart();
        ChartPanel chartPanelHistDist = new ChartPanel(chartHistDist);


        JFreeChart chartHistMark = Diagrams.createHistogram(getAllFeacher(students_on_bd, "Оценка"),
                "Mark").getChart();
        ChartPanel chartPanelHistMark = new ChartPanel(chartHistMark);


        JFrame frame = new JFrame("Charts in Tabs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);;

        // Создание вкладок
        JTabbedPane tabbedPane = new JTabbedPane();

        // Добавление первого графика (график рассеяния)
        tabbedPane.addTab("Scatter Plot", chartPanel);

        // Добавление второго графика Гистограмма дистанции
        tabbedPane.addTab("Гистограмма для дистанции", chartPanelHistDist);

        // Добавление третьего графика Гистограмма оценок
        tabbedPane.addTab("Гистограмма для оценок", chartPanelHistMark);

        // Добавление вкладок в основное окно
        frame.add(tabbedPane);
        frame.setVisible(true);

    }

    public static double[] getAllFeacher(List<Student> students, String nameFeach){
        double[] listFeach = new double[students.size()];
        int index = 0;
        if (Objects.equals(nameFeach, "Дистанция")){
            for (Student sud: students){
                listFeach[index] = sud.getDistance();
                index++;
            }
        }else {
            for (Student sud: students){
                listFeach[index] = sud.getAvgMark();
                index++;
            }
        }
        return listFeach;
    }

    public static void printStudents(List<Student> students_on_bd){
        for (Student entry : students_on_bd) {


            System.out.println("Студент: " + entry.getName() + "\nДистанция: " + entry.getDistance()+
                    "\nСредняя оценка:" + entry.getAvgMark());

            System.out.println("______________________");
        }
    }
}