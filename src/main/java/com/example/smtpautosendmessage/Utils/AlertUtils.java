package com.example.smtpautosendmessage.Utils;

import javafx.scene.control.Alert;

public class AlertUtils {
    /**
     * showAlert -- функция отображения всплывающих сообщений
     * @param message -- текст сообщения
     */
    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * showAlert -- функция отображения всплывающих сообщений с заданым типом сообщений
     * @param message -- текст сообщения
     * @param alertType -- типа сообщения
     */
    public static void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
