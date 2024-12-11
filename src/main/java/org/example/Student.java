package org.example;

import java.util.Random;


public class Student {
    private final String name;
    //private final Integer age;
    //private final String city;
    private final Double distance_native_city;
    private double avgMark;

    public Student(String name, Double distance_native_city, Double avgMark ) {
        this.name = name;
        this.avgMark = avgMark;
        //this.city = city;
        this.distance_native_city = distance_native_city;//GetRandomDistance();
    }

    public String getName(){
        return this.name;
    }

    public double getDistance(){
        return this.distance_native_city;
    }

    public double getAvgMark(){
        return this.avgMark;
    }
    public int GetRandomDistance(){
        // Создаем объект Random
        Random random = new Random();

        // Генерируем случайное число от 1 до 70000
        int randomNumber = random.nextInt(70000) + 1; // +1, чтобы включить 1 в диапазон

        // Выводим сгенерированное число
        return randomNumber;
    }
}
