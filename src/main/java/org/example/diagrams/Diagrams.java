package org.example.diagrams;
import org.jfree.chart.ChartPanel;
import org.jfree.data.general.DefaultPieDataset;
import org.example.Student;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeriesCollection;

import java.lang.reflect.Array;
import java.util.List;

public class Diagrams {

    public static JFreeChart createDiagram(List<Student>students_on_bd){
        XYSeries series = new XYSeries("Correlation");
        for (
                Student student : students_on_bd) {
            series.add(student.getDistance(), student.getAvgMark());
        }

        // Создание коллекции данных
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Correlation between Distance and Average Mark", // Заголовок
                "Distance", // Подпись оси X
                "Average Mark", // Подпись оси Y
                dataset, // Данные
                PlotOrientation.VERTICAL, // Ориентация графика
                true, // Показывать легенду
                true, // Показывать подсказки
                false // Показывать URL
        );
        return chart;
    }




    public static <T extends Number> ChartPanel createHistogram(double[] dataForSet, String nameLegend){

        // Создание набора данных для гистограммы
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries(nameLegend, dataForSet, 10); // 10 - количество бинов

        // Создание гистограммы
        JFreeChart histogram = ChartFactory.createHistogram(
                (nameLegend + "Histogram"),
                nameLegend,
                "Frequency",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Создание панели для отображения гистограммы
        return new ChartPanel(histogram);
    }
}
