<h1>Гушшамов Кирилл РИ-230933</h1>
<h2><b>Тема моего проекта:</b></h2>
Зависимость успеваимости от расстояния до учебы

<b>Гипотеза:</b> чем дальше живет студент от института тем ему тяжелее учиться и из-за этого его отметки должны быть относительно студентов живущих не далеко

<h2>Первый этап</h2>

Описание архитектуры моего приложение закладывания логики, описание классов.

![image](https://github.com/user-attachments/assets/223b6c9b-9940-4115-83fd-2b2da05eda8e)

<b>В последующем классы сильно изменились</b>

```
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
```
<h2>Второй этап</h2>

Парсинг файла

Для моей задачи оказалось, что нужно не так много данных, а именно все баллы за задания

Использовал стандартный интструмент, здесь из особенности можно лишь выделить этот учатсток кода


```
int index = 0;
for (String itVar : first_line)
{
     index++;
     if (itVar.contains("ДЗ:") || itVar.contains(("Упр:"))) {
        list_index_mark.add(index);
     }
}
```

В нём получаю только индексы тех колонок, которые нужны


![image](https://github.com/user-attachments/assets/0899e1c9-1cbf-40f0-9b1f-b75da7483592)
