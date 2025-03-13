module com.classig.chatbot {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.classig.chatbot to javafx.fxml;
    exports com.classig.chatbot;
}