package org.example;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class mainPanel extends JFrame {
    private JPanel mainpanel;
    private JTextField urlField;
    private JButton goButton;
    private JFXPanel jfxPanel;
    private JTabbedPane tabs;
    private JPanel hometab;
    private JPanel pluginsPanel;
    private JPopupMenu hamburger;
    private JButton hamburgerButton;
    private JButton refreshButton;
    private JButton xButton;


    //Variables
    private WebView webView;
    private WebEngine engine;
    JFrame frame;
    private String url = "https://www.google.com/";

    PluginLoader pluginLoader = new PluginLoader();

    public mainPanel(JFrame frame)  {

        urlField.setText(url);
        this.frame = frame;
        frame.setVisible(true);




        //Hamburger loading

/*
       //Plugin Loading
            try
            {
                //load the plugins
                pluginLoader.loadPlugins("C:\\Users\\lolca\\Desktop\\plugins\\src\\main\\org\\example",engine);

                //For every Plugin we find
                for (Method plugin : pluginLoader.getPlugins())
                {
                    //Initalize every Plugin
                    Thread th = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                plugin.invoke(pluginLoader.getClaxx());
                            } catch (Throwable th) {
                                // TODO make error
                            }
                        }
                    });

                    th.setContextClassLoader(pluginLoader.getClassLoader());
                    th.start();

                    th.join();

                    JMenuItem pluginItem = new JMenuItem(plugin.getName());
                    hamburger.add(pluginItem);

                    System.out.println("Loaded plugin: " + plugin.getName());
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }


*/
        // Create and initialize the JFXPanel on the Swing thread
        Platform.runLater(() -> {

            //Creating a new webveiw and getting the engine
            webView = new WebView();
            engine = webView.getEngine();

            //webView.resize(1900,1000);

            //loading home url
            engine.load(url);

            //Setting home tab name to the url
            hometab.setName(url);

            //Setting jfx scene
            jfxPanel.setScene(new Scene(webView));

        //Action listener for the location properties
        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                SwingUtilities.invokeLater(() -> urlField.setText(newValue));
                hometab.doLayout();
            }
        });



        });//end of JFX thread

        hamburgerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hamburger.show(hamburgerButton, 0, hamburgerButton.getHeight());
            }
        });


        //Action Listener for go
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Gets the text from URL bar and reloads the engine
                url=urlField.getText();
                Platform.runLater(()->{engine.load(url);});
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(() -> {engine.reload();});
            }
        });
    }
    public JPanel getPanel()
    {
        return this.mainpanel;
    }//end of function "getPanel"



}
