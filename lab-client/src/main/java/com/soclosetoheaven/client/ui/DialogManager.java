package com.soclosetoheaven.client.ui;

import com.soclosetoheaven.client.locale.LocaledUI;
import com.soclosetoheaven.client.locale.Localizer;

import javax.swing.*;
import java.nio.charset.StandardCharsets;

public class DialogManager {

    private final Localizer localizer;

    public DialogManager() {
        this.localizer = Localizer.getInstance();
    }

    public synchronized void showText(String text) {
        SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(null,
                localizer.getStringByKey(text),
                localizer.getStringByKey(LocaledUI.MESSAGE.key),
                JOptionPane.INFORMATION_MESSAGE
            )
        );
    }

    public synchronized String showTextWithInput(String text) {
        JTextField textField = new JTextField();
        Object[] message = {
                localizer.getStringByKey(text),
                textField
        };
        String line = null;
        int option = JOptionPane.showConfirmDialog(null,
                message,
                localizer.getStringByKey(LocaledUI.INPUT.key),
                JOptionPane.OK_CANCEL_OPTION
        );
        if (option == JOptionPane.OK_OPTION) {
            line = new String(textField.getText().getBytes(), StandardCharsets.UTF_8);
        }
        if (line == null || line.isEmpty()) {
            return null;
        }
        return line;
    }
}