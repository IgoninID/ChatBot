module com.classig.chatbot {
    requires javafx.controls;
    requires javafx.fxml;
    requires openweathermap.api;
    requires org.apiguardian.api;


    opens com.classig.chatbot to javafx.fxml;
    exports com.classig.chatbot;
}