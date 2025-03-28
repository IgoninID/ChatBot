package com.classig.chatbot;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.Language;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apiguardian.api.API;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatController
{

    private final String API_KEY = "b8ac38c459f72202edfdf776458b8f2b";

    public String name;

    @FXML
    public TextArea ChatArea;

    @FXML
    public TextField MessageText;

    OpenWeatherMapClient openWeatherMapClient = new OpenWeatherMapClient(API_KEY);

    IBot bot = new Bot();

    @FXML
    void onSendClick(ActionEvent event)
    {
        String text = MessageText.getText();
        if (!text.isEmpty())
        {
            LocalDateTime now = LocalDateTime.now();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            String formatnow = now.format(formatter);

            ChatArea.appendText(formatnow+". "+name+": "+text+"\n");

            formatnow = now.format(formatter);

            ChatArea.appendText(formatnow+". "+"Bot: "+bot.answer(text)+"\n");
        }
        MessageText.clear();
    }

    @FXML
    void onSaveClick()
    {
        final String H_FILE = name+".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(H_FILE)))
        {
            writer.write(ChatArea.getText());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void onLoadClick()
    {
        final String H_FILE = name+".txt";
        if (Files.exists(Paths.get(H_FILE)))
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(H_FILE)))
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    ChatArea.appendText(line+"\n");
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void initialize()
    {
        onLoadClick();
    }

}