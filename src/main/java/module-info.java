module com.example.smtpautosendmessage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires activation;
    requires java.desktop;

    opens com.example.smtpautosendmessage to javafx.fxml;
    exports com.example.smtpautosendmessage;
}