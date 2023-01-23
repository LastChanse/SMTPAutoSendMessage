module com.example.smtpautosendmessage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires activation;

    opens com.example.smtpautosendmessage to javafx.fxml;
    exports com.example.smtpautosendmessage;
}