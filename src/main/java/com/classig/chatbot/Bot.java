package com.classig.chatbot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot implements IBot{

    List<Pattern> patterns = new ArrayList<>();

    List<List<String>> answers = new ArrayList<>();

    Random rnd = new Random();

    public Bot()
    {
        patterns.add(Pattern.compile("Привет|привет"));
        patterns.add(Pattern.compile("Что ты можешь\\?|что ты можешь\\?"));
        patterns.add(Pattern.compile("\\s*\\d+\\s*\\+\\s*\\d+\\s*"));
        patterns.add(Pattern.compile("\\s*\\d+\\s*\\*\\s*\\d+\\s*"));
        patterns.add(Pattern.compile("\\s*\\d+\\s*\\:\\s*\\d+\\s*"));
        patterns.add(Pattern.compile("\\s*\\d+\\s*\\-\\s*\\d+\\s*"));
        answers.add(List.of("Привет! Я чат бот, чем могу помочь? (Напишите что ты можешь? для показа возможностей)", "Здравствуй, я чат бот, чем могу помочь? (Напишите что ты можешь? для показа возможностей)"));
        answers.add(List.of("Сложить/вычесть/умножить/поделить два числа"));
        answers.add(List.of("Результат суммы = "));
        answers.add(List.of("Результат вычитания = "));
        answers.add(List.of("Результат умножения = "));
        answers.add(List.of("Результат деления = "));

    }

    public String summ(String s)
    {
        if (s != null)
        {
            String wosp = s.trim();
            String[] num = wosp.split("\\+");
            double rez = Double.parseDouble(num[0]) + Double.parseDouble(num[1]);
            return Double.toString(rez);
        }
        return "Пустая строка";
    }

    public String mult(String s)
    {
        if (s != null)
        {
            String wosp = s.trim();
            String[] num = wosp.split("\\*");
            double rez = Double.parseDouble(num[0]) + Double.parseDouble(num[1]);
            return Double.toString(rez);
        }
        return "Пустая строка";
    }

    public String div(String s)
    {
        if (s != null)
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

    public String minus(String s)
    {
        if (s != null)
        {
            String wosp = s.trim();
            String[] num = wosp.split("\\-");
            double rez = Double.parseDouble(num[0]) - Double.parseDouble(num[1]);
            return Double.toString(rez);
        }
        return "Пустая строка";
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
                return variants.get(rnd.nextInt(variants.size()));
            }
        }
        return "??";
    }
}
