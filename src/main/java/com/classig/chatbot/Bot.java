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

public class Bot implements IBot{

    private final String API_KEY = "b8ac38c459f72202edfdf776458b8f2b";

    OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient(API_KEY);

    List<Pattern> patterns = new ArrayList<>();

    List<List<String>> answers = new ArrayList<>();

    Random rnd = new Random();

    public Bot()
    {
        patterns.add(Pattern.compile("Привет|привет"));
        patterns.add(Pattern.compile("Что ты можешь\\?|что ты можешь\\?"));
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\+\\s*\\-?\\d+\\s*"));
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\*\\s*\\-?\\d+\\s*"));
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\:\\s*\\-?\\d+\\s*"));
        patterns.add(Pattern.compile("\\s*\\-?\\d+\\s*\\-\\s*\\-?\\d+\\s*"));
        patterns.add(Pattern.compile("Погода в [а-яА-я]+|погода в [а-яА-Я]+"));
        answers.add(List.of("Привет! Я чат бот, чем могу помочь? (Напишите что ты можешь? для показа возможностей)"));
        answers.add(List.of("Сложить/вычесть/умножить/поделить два числа, узнать погоду в городе(погода в город(именительный падеж))"));
        answers.add(List.of("Результат суммы = "));
        answers.add(List.of("Результат умножения = "));
        answers.add(List.of("Результат деления = "));
        answers.add(List.of("Результат вычитания = "));
        answers.add(List.of("Погода: "));

    }

    public static String summ(String s)
    {
        if ((s != null) && (s != ""))
        {
            String wosp = s.trim();
            String[] num = wosp.split("\\+");
            double rez = Double.parseDouble(num[0]) + Double.parseDouble(num[1]);
            return Double.toString(rez);
        }
        return "Пустая строка";
    }

    public static String mult(String s)
    {
        if ((s != null) && (s != ""))
        {
            String wosp = s.trim();
            String[] num = wosp.split("\\*");
            double rez = Double.parseDouble(num[0]) * Double.parseDouble(num[1]);
            return Double.toString(rez);
        }
        return "Пустая строка";
    }

    public static String div(String s)
    {
        if ((s != null) && (s != ""))
        {
            String wosp = s.trim();
            String[] num = wosp.split("\\:");
            if (Double.parseDouble(num[1]) != 0)
            {
                double rez = Double.parseDouble(num[0]) / Double.parseDouble(num[1]);
                return Double.toString(rez);
            }
            return "Делить на 0 нельзя";
        }
        return "Пустая строка";
    }

    public static String minus(String s)
    {
        if ((s != null) && (s != ""))
        {
            String wosp = s.trim();
            // todo вычитание с отрицательными числами
            String[] num = wosp.split("\\-");
            double rez = Double.parseDouble(num[0]) - Double.parseDouble(num[1]);
            return Double.toString(rez);
        }
        return "Пустая строка";
    }

    public String weathercity(String s)
    {
        String[] s1 = s.split("Погода в ");

        final Weather weather = openWeatherMapClient
                .currentWeather()
                .single()
                .byCityName(s1[1])
                .language(Language.RUSSIAN)
                .unitSystem(UnitSystem.IMPERIAL)
                .retrieve()
                .asJava();
        return weather.toString();
    }

    @Override
    public String answer(String message)
    {
        for (int i = 0; i < patterns.size(); i++)
        {
            Matcher m = patterns.get(i).matcher(message);
            if (m.matches())
            {
                List<String> variants = answers.get(i);
                if (i == 2)
                {
                    return variants.get(rnd.nextInt(variants.size()))+summ(message);
                }
                if (i == 3)
                {
                    return variants.get(rnd.nextInt(variants.size()))+mult(message);
                }
                if (i == 4)
                {
                    return variants.get(rnd.nextInt(variants.size()))+div(message);
                }
                if (i == 5)
                {
                    return variants.get(rnd.nextInt(variants.size()))+minus(message);
                }
                if (i == 6)
                {
                    return variants.get(rnd.nextInt(variants.size()))+weathercity(message);
                }
                return variants.get(rnd.nextInt(variants.size()));
            }
        }
        return "??";
    }
}
