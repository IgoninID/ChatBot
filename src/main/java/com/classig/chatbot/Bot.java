package com.classig.chatbot;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс Чат-бота наследует абстрактный класс бота
 */
public class Bot implements IBot{

    /**
     * Список с историей сообщений
     * Final - указывает что указатель на список не меняется, элементы могут меняться
     */
    private final List<String> Messages = new ArrayList<>();

    /**
     * Получение размера истории сообщений
     * @return Количество сообщений в истории сообщений
     */
    @Override
    public int getMessagesSize()
    {
        return Messages.size();
    }

    /**
     * Получение сообщения из истории сообщений
     * @param i индекс сообщения в списке
     * @return Сообщение из истории сообщений
     */
    @Override
    public String getMessage(int i)
    {
        return Messages.get(i);
    }

    /**
     * Получение истории сообщений
     * @return Список с историей сообщений
     */
    @Override
    final public List<String> getMessages()
    {
        return Messages;
    }


    /**
     * Загрузка apikey из файла
     * @param namef - имя файла с api ключом
     * @return api ключ
     */
    private String loadapi(String namef)
    {
        if (Files.exists(Paths.get(namef))) // если файл существует
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(namef))) // инициализация чтения файла
            {
                return reader.readLine();
            }
            catch (IOException e) // если не сработал readline
            {
                e.printStackTrace(); // выводим где ошибка
            }
        }
        return "no files";
    }

    /**
     * APIKEY для запросов в OpenWeather
     */
    private final String API_KEY = loadapi("api_key.txt");          /// .env

    ///  key=tetretretrertert5464654

    /**
     * Клиент для запросов в OpenWeather
     */
    private final OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient(API_KEY);

    /**
     * Список с ключами для сообщений пользователя
     */
    List<Pattern> patterns = new ArrayList<>();

    /**
     * Список с вариантами ответов на сообщения пользователя
     */
    List<List<String>> answers = new ArrayList<>();

    /**
     * Инициализируем random для случайной выборки варианта ответа на сообщение пользователя
     */
    Random rnd = new Random();

    /**
     * Конструктор бота без параметров
     */
    public Bot()
    {
        patterns.add(Pattern.compile("Привет|привет"));
        patterns.add(Pattern.compile("Что ты можешь\\?|что ты можешь\\?"));
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\+\\s*\\-?\\d+\\s*")); // сложение двух чисел (\ перед \s - экранирование для метасимвола регулярного выражения )
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\*\\s*\\-?\\d+\\s*")); // умножение двух чисел например 2243+2423
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\:\\s*\\-?\\d+\\s*")); // деление двух чисел 2243*2423
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s+\\-\\s+\\-?\\d+\\s*")); // вычитание двух чисел 2243:2423
        patterns.add(Pattern.compile("Погода в [а-яА-я]+|погода в [а-яА-Я]+")); // запрос погоды в городе 2243 - 2423
        answers.add(List.of("Привет! Я чат бот, чем могу помочь? (Напишите что ты можешь? для показа возможностей)")); // ответ на привет
        answers.add(List.of("Сложить/вычесть/умножить/поделить два числа, узнать погоду в городе(погода в город(именительный падеж))")); // ответ на что ты можешь?
        answers.add(List.of("Результат суммы = ")); // ответ на запрос суммы
        answers.add(List.of("Результат умножения = ")); // ответ на запрос умножения
        answers.add(List.of("Результат деления = ")); // ответ на запрос деления
        answers.add(List.of("Результат вычитания = ")); // ответ на запрос вычитания
        answers.add(List.of("Погода: ")); // ответ на запрос погоды

    }

    /**
     * Сложение двух чисел
     * @param s - сообщение пользователя
     * @return результат сложения
     */
    public static String summ(String s)
    {
        if (!s.isEmpty()) // если сообщение не пустое
        {
            String wosp = s.trim(); // удаляем пробелы в начале и конце строки
            String[] num = wosp.split("\\+"); // делим сообщение на две подстроки (делитель знак +)
            double rez = Double.parseDouble(num[0]) + Double.parseDouble(num[1]); // слаживаем два числа
            return Double.toString(rez); // возвращаем результат сложения как строку
        }
        return "Пустая строка"; // если сообщение пустое
    }

    /**
     * Умножение двух чисел
     * @param s - сообщение пользователя
     * @return результат умножения
     */
    public static String mult(String s)
    {
        if (!s.isEmpty()) // если сообщение не пустое
        {
            String wosp = s.trim(); // удаляем пробелы в начале и конце строки
            String[] num = wosp.split("\\*"); // делим сообщение на две подстроки (делитель знак *)
            double rez = Double.parseDouble(num[0]) * Double.parseDouble(num[1]); // умножаем два числа
            return Double.toString(rez); // возвращаем результат умножения как строку
        }
        return "Пустая строка"; // если сообщение пустое
    }

    /**
     * Деление двух чисел
     * @param s - сообщение пользователя
     * @return результат деления
     */
    public static String div(String s)
    {
        if (!s.isEmpty()) // если сообщение не пустое
        {
            String wosp = s.trim(); // удаляем пробелы в начале и конце строки
            String[] num = wosp.split("\\:"); // делим сообщение на две подстроки (делитель знак :)
            if (Double.parseDouble(num[1]) != 0) // проверка делителя на 0
            {
                double rez = Double.parseDouble(num[0]) / Double.parseDouble(num[1]); // делим два числа
                return Double.toString(rez); // возвращаем результат деления как строку
            }
            return "Делить на 0 нельзя"; // если делим на 0
        }
        return "Пустая строка"; // если сообщение пустое
    }

    /**
     * Вычитание двух чисел
     * @param s - сообщение пользователя
     * @return результат вычитания
     */
    public static String minus(String s)
    {
        if (!s.isEmpty()) // если сообщение не пустое
        {
            String wosp = s.trim(); // удаляем пробелы в начале и конце строки
            String[] num = wosp.split(" \\- "); // делим сообщение на две подстроки (делитель знак - с пробелами по бокам)
            double rez = Double.parseDouble(num[0]) - Double.parseDouble(num[1]); // вычитаем два числа
            return Double.toString(rez); // возвращаем результат вычитания как строку
        }
        return "Пустая строка"; // если сообщение пустое
    }

    /**
     * Запрос погоды в городе
     * @param s - сообщение пользователя
     * @return Информация о погоде в городе
     */
    public String weathercity(String s)
    {
        if (!s.isEmpty()) // если сообщение не пустое
        {
            String wosp = s.trim(); // удаляем пробелы в начале и конце строки
            String[] s1 = wosp.split("Погода в "); // делим сообщение на две подстроки (делитель "Погода в ")

            final Weather weather = openWeatherMapClient // отправляем запрос в OpenWeatherMap
                    .currentWeather() // текущая погода
                    .single() //
                    .byCityName(s1[1]) // наименование города
                    .language(Language.RUSSIAN) // язык ответа
                    .unitSystem(UnitSystem.IMPERIAL) // единицы измерения ответа
                    .retrieve() // получить
                    .asJava(); // как java (класс weather)
            return weather.toString(); // возвращение информации о всей доступной информации о погоде
        }
        return "Пустая строка"; // если сообщение пустое
    }

    /**
     * Ответ чат бота
     * @param message сообщение пользователя
     * @param name имя пользователя
     * @param formatnow текущее время в формате yyyy-MM-dd HH:mm:ss
     * @return ответ чат бота
     */
    @Override
    public String answer(String message, String name, String formatnow)
    {

        Messages.add(formatnow+". "+name+": "+message); // добавляем сообщение пользователя в историю сообщений

        String fullans = formatnow+". "+"Bot: " + "??"; // инициализируем полный ответ бота как без совпадений

        for (int i = 0; i < patterns.size(); i++) // проверка по всем ключам
        {
            Matcher m = patterns.get(i).matcher(message); // сверяем сообщение пользователя с ключами
            if (m.matches()) // если есть совпадение
            {
                String ansfunc = ""; // инициализируем строку для хранения ответа различных функций
                List<String> variants = answers.get(i); // получаем варианты ответов
                if (i == 2) // если пользователь запросил сложение двух чисел
                {
                    ansfunc = summ(message);
                }
                if (i == 3) // если пользователь запросил умножение двух чисел
                {
                    ansfunc = mult(message);
                }
                if (i == 4) // если пользователь запросил деление двух чисел
                {
                    ansfunc = div(message);
                }
                if (i == 5) // если пользователь запросил вычитание двух чисел
                {
                    ansfunc = minus(message);
                }
                if (i == 6) // если пользователь запросил погоду в городе
                {
                    ansfunc = weathercity(message);
                }
                if (!ansfunc.isEmpty()) // если пользователь запросил функцию
                {
                    fullans = formatnow + ". " + "Bot: " + variants.get(rnd.nextInt(variants.size())) + ansfunc; // формируем строку полного ответа
                }
                else // если пользователь не запросил функцию
                {
                    fullans = formatnow + ". " + "Bot: " + variants.get(rnd.nextInt(variants.size())); // формируем строку полного ответа
                }
                Messages.add(fullans); // добавляем полный ответ чат бота в историю сообщений
                return fullans; // возвращаем полный ответ чат бота
            }
        }
        Messages.add(fullans); // добавляем полный ответ чат бота если нет совпадений в историю сообщений
        return fullans; // если нет совпадений
    }

    /**
     * Статический метод сохранения диалога в текстовый файл с именем пользователя+.txt
     * @param name имя пользователя (имя файла без txt)
     */
    @Override
    public void Save(String name)
    {
        final String H_FILE = name+".txt"; // название файла
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(H_FILE, true))) // инициализация записи в файл
        {
            for (int i = 0; i < Messages.size(); i++)
            {
                writer.write(Messages.get(i)); // записываем в файл текст из поля диалога
                writer.newLine();
            }
        }
        catch (IOException e) // если не сработал filewriter
        {
            e.printStackTrace(); // выводим где ошибка
        }
    }

    /**
     * Статический метод загрузки диалога из текстового файла с именем пользователя+.txt
     * @param name имя пользователя (имя файла без txt)
     */
    @Override
    public void Load(String name)
    {
        final String H_FILE = name+".txt"; // название файла
        if (Files.exists(Paths.get(H_FILE))) // если файл существует
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(H_FILE))) // инициализация чтения файла
            {
                String line; // строка текста в файле
                while ((line = reader.readLine()) != null) // до конца файла
                {
                    Messages.add(line+"\n"); // записываем строки в поле диалога
                }
            }
            catch (IOException e) // если не сработал readline
            {
                e.printStackTrace(); // выводим где ошибка
            }
        }
    }
}
