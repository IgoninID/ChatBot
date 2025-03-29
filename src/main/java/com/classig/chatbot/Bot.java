package com.classig.chatbot;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;

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
     * APIKEY для запросов в OpenWeather
     */
    private final String API_KEY = "b8ac38c459f72202edfdf776458b8f2b";

    /**
     * Клиент для запросов в OpenWeather
     */
    OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient(API_KEY);

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
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\+\\s*\\-?\\d+\\s*")); // сложение двух чисел
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\*\\s*\\-?\\d+\\s*")); // умножение двух чисел
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\:\\s*\\-?\\d+\\s*")); // деление двух чисел
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s+\\-\\s+\\-?\\d+\\s*")); // вычитание двух чисел
        patterns.add(Pattern.compile("Погода в [а-яА-я]+|погода в [а-яА-Я]+")); // запрос погоды в городе
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
     * @param message - сообщение пользователя
     * @return ответ чат бота
     */
    @Override
    public String answer(String message)
    {
        for (int i = 0; i < patterns.size(); i++) // проверка по всем ключам
        {
            Matcher m = patterns.get(i).matcher(message); // сверяем сообщение пользователя с ключами
            if (m.matches()) // если есть совпадение
            {
                List<String> variants = answers.get(i); // получаем варианты ответов
                if (i == 2) // если пользователь запросил сложение двух чисел
                {
                    return variants.get(rnd.nextInt(variants.size()))+summ(message);
                }
                if (i == 3) // если пользователь запросил умножение двух чисел
                {
                    return variants.get(rnd.nextInt(variants.size()))+mult(message);
                }
                if (i == 4) // если пользователь запросил деление двух чисел
                {
                    return variants.get(rnd.nextInt(variants.size()))+div(message);
                }
                if (i == 5) // если пользователь запросил вычитание двух чисел
                {
                    return variants.get(rnd.nextInt(variants.size()))+minus(message);
                }
                if (i == 6) // если пользователь запросил погоду в городе
                {
                    return variants.get(rnd.nextInt(variants.size()))+weathercity(message);
                }
                return variants.get(rnd.nextInt(variants.size()));
            }
        }
        return "??"; // если нет совпадений
    }
}
