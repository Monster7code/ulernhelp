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

<b>Парсинг файлаБ</b>

Для моей задачи оказалось, что нужно не так много данных, а именно все баллы за задания

Использовал стандартный инструмент, здесь из особенности можно лишь выделить этот учатсток кода


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

В нём получаю только индексы тех колонок, которые нужны.

<h2>Третий этап</h2>
<b>Работа с API</b>
Поскольку в моей задачи нужно было узнать дистанцию от института, то я использую его именно для этого.
В момента парсера каждой строки я отправляю в API VK имя и фамилию пользователя и получаю его город, после этого я использую CageGeocode для того чтобы выявлять дистанцию от родного города для института, для этого заранее я выделил константу с координатой института, и через специальный метод API вычисляю дистанцию, после этого возвращаю обратно дистанцию, и записываю данные в класс для добавление его экземпляра в списом.

<h2>Четвёртый этап</h2>

<b>Работа с БД</b>

Поскольку я использую SqдLite, то добавляю соответсвующий контектор, после создаю таблицу и пишу запросы

```
public static void createTableStudents() {

        String sql = "CREATE TABLE IF NOT EXISTS students (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	distance_hometown real,\n"
                + " avg_mark real \n"
                + ");";

        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
                System.out.println("Table 'students' created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saveStudents(List<Student> students) {
        String sql = "INSERT INTO students(name,distance_hometown, avg_mark) VALUES(?,?,?)";

        for (var student : students) {
            try (Connection conn = DriverManager.getConnection(dbPath);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, student.getName());
                    pstmt.setDouble(2, student.getDistance());
                    pstmt.setDouble(3, student.getAvgMark());
                    pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
```
И записываю данные в таблицу для этого передаю заготовленный список студентов, который появился в процессе обработки

И пишу запросы к бд, которые мне будут нужны в следующей итерации

```
 public static List<Student> getStudentsOrderBy() {
        String sql = "SELECT name, distance_hometown, avg_mark FROM students WHERE " +
                "distance_hometown > 0 ORDER BY distance_hometown DESC";

        List<Student> res = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(dbPath);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("name");
                double distanceHometown = rs.getDouble("distance_hometown");
                double avgMark = rs.getDouble("avg_mark");

                Student nwst = new Student(name, distanceHometown, avgMark);
                res.add(nwst);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return res;
    }
```

![image](https://github.com/user-attachments/assets/0899e1c9-1cbf-40f0-9b1f-b75da7483592)

<h2>Пятый этап</h2>

<b>Добавление окон и диаграмм</b>

Для создания окон я использую JFrame

Для графиков использую Jfree

Я построил три графика на разных вкладках

Первый и основной график это корреляция между дистанцией и оценкой, вывод по нему будет чуть ниже

![image](https://github.com/user-attachments/assets/944f162b-7e0c-4eae-a355-abd13c0e31fc)

Второй график показывает распеделение дистанции, по сути он логичный так как в основном люди из Екатеринбурга

![image](https://github.com/user-attachments/assets/e96c06df-8214-4754-9df1-f26069e6648c)

И третий графки показывает распеделение баллов

![image](https://github.com/user-attachments/assets/0318df6b-d631-449f-b033-392ee3b3086c)


<h1>Вывод:</h1>

Как видно из графика гипотеза - не подтвердилась, нет никакой корреляции оценки от расстояния.<br>

<b>Причины?</b>

Не у всех в ВК указан город, а если и указан не факт, что родной, из-за этого выборка становится некорретной, также можно заменить большое количество людей из одного города, это тоже портит выборку и её представление
