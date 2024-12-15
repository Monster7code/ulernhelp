package org.example;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Parser {


    public static List<Student> parseCSV(String filePath) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            ArrayList<Integer> list_index_mark = new ArrayList<Integer>();
            String[] first_line = br.readLine().split(";");

            int index = 0;
            for (String itVar : first_line)
            {
                index++;
                if (itVar.contains("ДЗ:") || itVar.contains(("Упр:"))) {
                    list_index_mark.add(index);
                }
            }
            while ((line = br.readLine()) != null) {

                String[] values = line.split(";");
                double avgMark = 0.0;

                if (Objects.equals(values[0], "Максимум:")) continue;

                for (int indexMark: list_index_mark) {
                    avgMark += Double.parseDouble(values[indexMark]);
                }

                Distance distance = new Distance();

                String nameStudent = values[0];
                Double distance_native_city = distance.getDistance(nameStudent);

                Student student = new Student(nameStudent, distance_native_city, avgMark);

                students.add(student);
                //System.out.println(values[0]);
                //System.out.println(avgMark);
                //System.out.println(list_index_mark.size());

                //System.out.println(values[0]);
                //System.out.println(Arrays.toString(values));
                // Предполагая, что в CSV есть заголовки, пропускаем первую строку
                //
                // Student student = new Student(values[0], values[1] /* передайте дополнительные значения */);
                // students.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            throw new RuntimeException(e);
        } catch (ApiException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
