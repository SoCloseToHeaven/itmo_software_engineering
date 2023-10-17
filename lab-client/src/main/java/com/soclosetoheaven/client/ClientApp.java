/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.soclosetoheaven.client;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.settings.ThemeSettingsMenuItem;
import com.github.weisj.darklaf.theme.SolarizedDarkTheme;
import com.soclosetoheaven.client.locale.LocaledUI;
import com.soclosetoheaven.client.locale.Localizer;
import com.soclosetoheaven.client.ui.application.ElementEditorPanel;
import com.soclosetoheaven.client.ui.command.*;
import com.soclosetoheaven.client.net.connection.UDPClientConnection;
import com.soclosetoheaven.client.ui.connection.UIConnectionManager;
import com.soclosetoheaven.client.ui.DialogManager;
import com.soclosetoheaven.common.command.*;
import com.soclosetoheaven.client.commandmanager.ClientCommandManager;
import com.soclosetoheaven.common.exception.ManagingException;
import com.soclosetoheaven.common.io.BasicIO;
import com.soclosetoheaven.common.net.auth.AuthCredentials;
import com.soclosetoheaven.common.net.messaging.Request;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

/**
 *
 * @author Дмитрий
 */
public class ClientApp extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public ClientApp(String address, int port) throws IOException {
        this.localizer = Localizer.getInstance();
        this.dialogManager = new DialogManager();
        this.connectionManager = new UIConnectionManager(
                new UDPClientConnection(address, port), dialogManager
        );
        initComponents();
        this.commandManager = UICommandManager();
        registerCommandListeners();
        applicationContainerPanel.setVisible(false);
        connectionManager.registerObserver(applicationContainerPanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        LafManager.install(new SolarizedDarkTheme());

        languageComboBox = new javax.swing.JComboBox<>();
        languageIconLabel = new javax.swing.JLabel();
        overlayPanel = new javax.swing.JPanel();
        loginRegisterContainerPanel = new com.soclosetoheaven.client.ui.auth.LoginRegisterContainerPanel();
        applicationContainerPanel = new com.soclosetoheaven.client.ui.application.ApplicationContainer();
        menuBar = new javax.swing.JMenuBar();
        optionsMenu = new javax.swing.JMenu();
        removeAllByAgeItem = new javax.swing.JMenuItem();
        clearItem = new javax.swing.JMenuItem();
        historyItem = new javax.swing.JMenuItem();
        helpItem = new javax.swing.JMenuItem();
        executeScriptItem = new javax.swing.JMenuItem();
        logoutItem = new javax.swing.JMenuItem();
        themeSettingsMenuItem = new ThemeSettingsMenuItem(localizer.getStringByKey(LocaledUI.THEME_SETTINGS.key));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        languageComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(Arrays.stream(Localizer.Locales.values()).map(i -> i.locale).toArray(Locale[]::new)));

        languageComboBox.setSelectedItem(Localizer.getInstance().getCurrentLocale());

        languageComboBox.addActionListener((event) -> {
            Locale newLocale = (Locale) languageComboBox.getSelectedItem();
            Localizer.getInstance().setCurrentLocale(newLocale);
            setDefaultText();
        });


        languageIconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/language-icon.png"))); // NOI18N

        overlayPanel.setLayout(new javax.swing.OverlayLayout(overlayPanel));
        overlayPanel.add(loginRegisterContainerPanel);
        overlayPanel.add(applicationContainerPanel);

        optionsMenu.add(removeAllByAgeItem);

        optionsMenu.add(clearItem);

        optionsMenu.add(historyItem);

        optionsMenu.add(helpItem);

        optionsMenu.add(executeScriptItem);

        optionsMenu.add(logoutItem);

        menuBar.add(optionsMenu);
        optionsMenu.setVisible(false);

        menuBar.add(themeSettingsMenuItem);
        setJMenuBar(menuBar);

        setDefaultText();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(945, Short.MAX_VALUE)
                .addComponent(languageIconLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(overlayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(languageIconLabel)
                    .addComponent(languageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(overlayPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void switchPanels() {
        SwingUtilities.invokeLater(() -> {
            loginRegisterContainerPanel.setVisible(!loginRegisterContainerPanel.isVisible());
            optionsMenu.setVisible(!optionsMenu.isVisible());
            applicationContainerPanel.setVisible(!applicationContainerPanel.isVisible());
        });
    }

    private ClientCommandManager UICommandManager() {
        ClientCommandManager commandManager = new ClientCommandManager();
        commandManager.addCommand(new ShowCommand());
        commandManager.addCommand(new UIRemoveByIDCommand(applicationContainerPanel.getElementEditorPanel()));
        commandManager.addCommand(new UIUpdateCommand(applicationContainerPanel.getElementEditorPanel()));
        commandManager.addCommand(new UIAddCommand(applicationContainerPanel.getElementEditorPanel()));
        commandManager.addCommand(new UILoginCommand(loginRegisterContainerPanel.getLoginPanel()));
        commandManager.addCommand(new UIRegisterCommand(loginRegisterContainerPanel.getRegisterPanel()));
        commandManager.addCommand(new UIRemoveAllByAgeCommand(dialogManager));
        commandManager.addCommand(new ClearCommand());
        commandManager.addCommand(new LogoutCommand());
        UIExecuteScriptCommand executeScriptCommand = new UIExecuteScriptCommand(dialogManager, connectionManager);
        commandManager.addCommand(executeScriptCommand);
        commandManager.addCommand(new UIHistoryCommand(executeScriptCommand.EXECUTE_SCRIPT_MANAGER, dialogManager));
        commandManager.addCommand(new UIHelpCommand(executeScriptCommand.EXECUTE_SCRIPT_MANAGER, dialogManager));
        return commandManager;
    }

    private void registerBasicButtonCommand(AbstractButton button, String commandName) {
        ActionListener listener = (event) -> {
            try {
                Request request = commandManager.manage(commandName);
                if (request != null)
                    connectionManager.manageRequest(request);
            } catch (ManagingException e) {
                dialogManager.showText(e.getMessage());
            }
        };
        button.addActionListener(listener);
    }

    private void registerAuthButtonCommand(AbstractButton button, String commandName) {
        ActionListener listener = (event) -> {
            try {
                Request request = commandManager.manage(commandName);
                AuthCredentials auth = null;
                if (request != null) {
                    auth = request.getAuthCredentials();
                    connectionManager.manageRequest(request);
                    connectionManager.setAuthCredentials(auth);
                    connectionManager.manageRequestWithoutResponse(commandManager.manage(ShowCommand.NAME));
                } else {
                    connectionManager.setAuthCredentials(auth);
                }
                switchPanels();
            } catch (ManagingException e) {
                dialogManager.showText(e.getMessage());
            }
        };
        button.addActionListener(listener);
    }

    private void registerCommandListeners() {
        ElementEditorPanel panel = applicationContainerPanel.getElementEditorPanel();
        registerAuthButtonCommand(loginRegisterContainerPanel.getLoginButton(), UILoginCommand.NAME);
        registerAuthButtonCommand(loginRegisterContainerPanel.getRegisterButton(), UIRegisterCommand.NAME);
        registerBasicButtonCommand(panel.getAddButton(), UIAddCommand.NAME);
        registerBasicButtonCommand(panel.getRemoveButton(), UIRemoveByIDCommand.NAME);
        registerBasicButtonCommand(panel.getUpdateButton(), UIUpdateCommand.NAME);
        registerBasicButtonCommand(historyItem, UIHistoryCommand.NAME);
        registerBasicButtonCommand(removeAllByAgeItem, UIRemoveAllByAgeCommand.NAME);
        registerBasicButtonCommand(clearItem, ClearCommand.NAME);
        registerAuthButtonCommand(logoutItem, LogoutCommand.NAME);
        registerBasicButtonCommand(executeScriptItem, UIExecuteScriptCommand.NAME);
        registerBasicButtonCommand(helpItem, UIHelpCommand.NAME);
    }

    public void setDefaultText() {
        loginRegisterContainerPanel.setDefaultText();
        applicationContainerPanel.setDefaultText();
        clearItem.setText(localizer.getStringByKey(LocaledUI.CLEAR.key));
        optionsMenu.setText(localizer.getStringByKey(LocaledUI.OPTIONS.key));
        removeAllByAgeItem.setText(localizer.getStringByKey(LocaledUI.REMOVE_ALL_BY_AGE.key));
        logoutItem.setText(localizer.getStringByKey(LocaledUI.LOGOUT.key));
        helpItem.setText(localizer.getStringByKey(LocaledUI.HELP.key));
        executeScriptItem.setText(localizer.getStringByKey(LocaledUI.EXECUTE_SCRIPT.key));
        historyItem.setText(localizer.getStringByKey(LocaledUI.HISTORY.key));
        themeSettingsMenuItem.setText(localizer.getStringByKey(LocaledUI.THEME_SETTINGS.key));
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("client.encoding.override", "UTF-8");
        if (args.length < MIN_ARGS_SIZE) {
            System.err.println("Invalid args size");
            return;
        } else if (!args[PORT_INDEX].chars().allMatch(Character::isDigit)) {
            System.err.println("Invalid port format");
            return;
        }
        ClientApp frame;
        try {
            frame = new ClientApp(SERVER_IP, Integer.parseInt(args[PORT_INDEX]));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.soclosetoheaven.client.ui.application.ApplicationContainer applicationContainerPanel;
    private javax.swing.JMenuItem clearItem;
    private javax.swing.JMenuItem executeScriptItem;
    private javax.swing.JMenuItem helpItem;
    private javax.swing.JMenuItem historyItem;
    private javax.swing.JComboBox<Locale> languageComboBox;
    private javax.swing.JLabel languageIconLabel;
    private com.soclosetoheaven.client.ui.auth.LoginRegisterContainerPanel loginRegisterContainerPanel;
    private javax.swing.JMenuItem logoutItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JPanel overlayPanel;
    private javax.swing.JMenuItem removeAllByAgeItem;

    private ThemeSettingsMenuItem themeSettingsMenuItem;

    // End of variables declaration//GEN-END:variables

    private final DialogManager dialogManager;

    private final UIConnectionManager connectionManager;

    private final ClientCommandManager commandManager;

    private final Localizer localizer;


    private static final String SERVER_IP = "127.0.0.1";

    private static final int MIN_ARGS_SIZE = 1;

    private static final int PORT_INDEX = 0;

}
