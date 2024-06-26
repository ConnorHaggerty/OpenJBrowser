package org.example;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.util.ArrayList;


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
    private JButton newTabButton;
    private JButton homeButton;
    private JToolBar toolBar;


    //Variables
    private WebView webView;
    private WebEngine engine;

    ArrayList<WebEngine> engines = new ArrayList<>();

    JFrame frame;
    private String url = "https://www.google.com/";
    private String homeurl ="https://www.google.com/";


    int validindexes=0;


    PluginLoader pluginLoader = new PluginLoader();

    public mainPanel(JFrame frame) {

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

            //loading home url
            engine.load(homeurl);

            //Setting home tab name to the url
            hometab.setName(homeurl);//TODO make this work

            //Setting jfx scene
            jfxPanel.setScene(new Scene(webView));

            //adding to our list of engines
            engines.add(engine);

            //Action listener for the location properties
            engine.locationProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    SwingUtilities.invokeLater(() -> urlField.setText(newValue));

                    hometab.doLayout();
                }
            });

            engine.titleProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    SwingUtilities.invokeLater(() -> {
                        tabs.setTitleAt(tabs.getSelectedIndex(), newValue);
                    });
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
                url = urlField.getText();
                Platform.runLater(() -> {
                    engine.load(url);
                });
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(() -> {
                    engine.reload();
                });
            }
        });

        //New Tab Action Listener
        newTabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO make new tab

                //new JFX Panel
                JFXPanel jfxPanelnew = new JFXPanel();


                tabs.addTab("New Tab", jfxPanelnew);


                Platform.runLater(() -> {

                    //Creating a new webveiw and getting the engine
                    webView = new WebView();
                    WebEngine enginenew = webView.getEngine();

                    //loading home url
                    enginenew.load(homeurl);

                    //Setting jfx scene
                    jfxPanelnew.setScene(new Scene(webView));

                    //Increasing number of valid indexes
                    validindexes++;

                    //Adding the new engine to our list of engines
                    engines.add(enginenew);

                    //Setting home tab name to the url

                    enginenew.locationProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                            SwingUtilities.invokeLater(() -> {
                                urlField.setText(newValue);

                            });


                            enginenew.titleProperty().addListener(new ChangeListener<String>() {
                                @Override
                                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                                    SwingUtilities.invokeLater(() -> {
                                        tabs.setTitleAt(tabs.getSelectedIndex(), newValue);
                                    });
                                }
                            });

                        }
                    });

                });


            }
        });

        //Home Button Listener
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(() -> {
                    engines.get(tabs.getSelectedIndex()).load(homeurl);
                });
            }
        });

        //Close Tab Button
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validindexes > 0) {
                    tabs.removeTabAt(tabs.getSelectedIndex());
                    validindexes--;
                } else {
                    System.exit(0);
                }

            }
        });

    }


//commit

    public JPanel getPanel()
    {
        return this.mainpanel;
    }//end of function "getPanel"



}
