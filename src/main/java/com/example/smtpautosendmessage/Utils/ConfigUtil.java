package com.example.smtpautosendmessage.Utils;

import javafx.beans.property.Property;
import javafx.scene.control.Alert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
    static public Properties getConfig() {
        Properties prop = new Properties();
        String fileName = "src/main/resources/config.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
        } catch (FileNotFoundException ex) {
            // FileNotFoundException catch is optional and can be collapsed
            AlertUtils.showAlert("Config not found in \""+fileName+"\"", Alert.AlertType.ERROR);
            return null;
        } catch (IOException ex) {
            AlertUtils.showAlert(ex.getMessage()+"\n\n"+ex.getStackTrace(), Alert.AlertType.ERROR);
        }
        return prop;
    }
}
