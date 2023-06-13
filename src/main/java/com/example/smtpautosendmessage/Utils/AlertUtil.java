package com.example.smtpautosendmessage.Utils;

import javafx.scene.control.Alert;

public class AlertUtil {
    /**
     * showAlert -- функция отображения всплывающих сообщений
     * @param headMessage -- текст сообщения
     */
    public static void showAlert(String headMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headMessage);
        alert.show();
    }

    /**
     * showAlert -- функция отображения всплывающих сообщений с заданым типом сообщений
     * @param headMessage -- заголовок сообщения
     * @param alertType -- типа сообщения
     */
    public static void showAlert(String headMessage, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headMessage);
        alert.show();
    }
    /**
     * showAlert -- функция отображения всплывающих сообщений с заданым типом сообщений
     * @param headMessage -- заголовок сообщения
     * @param alertType -- типа сообщения
     * @param message -- текст сообщения
     */
    public static void showAlert(String headMessage, Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headMessage);
        alert.setContentText(message);
        alert.show();
    }
}
