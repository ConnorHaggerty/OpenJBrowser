package org.example;

import javafx.scene.web.WebEngine;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PluginLoader
{

    public Class<?> getClaxx() {
        return claxx;
    }

    Class<?> claxx;

    //URL Loader and URL
    URL url;

    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    URLClassLoader classLoader;
    private List<Method> plugins = new ArrayList<>();

    public void loadPlugins(String addonDirectory, WebEngine webEngine)
    {
        //Variables

        //File Directory
        File dir = new File(addonDirectory);

        //Names of Classes in the Jars
        ArrayList<String> classes = new ArrayList<>();



        //If it is not a Proper Directory
        if (!dir.isDirectory())
        {
            System.out.println("Not a Directory"); //TODO Error Message
        }
        else
        {

            try {
                //For every File in the Directory
                for (File file : dir.listFiles())
                {
                    //Only Loading it if we have the Jar
                    if (file.getName().endsWith(".jar"))
                    {
                        System.out.println(file.getName());
                        // getting file url
                        url = file.toURI().toURL();

                        //Setting the class loader up for this jar
                        classLoader = new URLClassLoader(new URL[]{url});

                        //Adding the Classes from the jar file
                        classes.addAll(getClassNamesFromJar(file));



                    }//end of if file is jar

                }//end of for every file

                //loading all the classes
                loadObtainedClasses(classes);

            }//end of try[1]
            catch (Exception e)
            {
                e.printStackTrace();//TODO Error Message
            }//end of catch [1]
        }
        //debug

            System.out.println(classes.get(0));

    }//end of function "loadPlugins"

    public static ArrayList<String> getClassNamesFromJar(File givenFile) throws IOException
    {

        ArrayList<String> classNames = new ArrayList<>();

        try (JarFile jarFile = new JarFile(givenFile))
        {
            Enumeration<JarEntry> e = jarFile.entries();

            while (e.hasMoreElements())
            {
                JarEntry jarEntry = e.nextElement();
                if (jarEntry.getName().endsWith(".class"))
                {
                    String className = jarEntry.getName().replace("/", ".").replace(".class", "");
                    classNames.add("org.example."+className);
                }
            }

            return classNames;
        }
    }

    public void  loadObtainedClasses(ArrayList<String> classes)
    {
        for (int i=0;i<classes.size();i++)
        {
            try
            {
                claxx = classLoader.loadClass(classes.get(i));
                Method initialize = claxx.getMethod("initialize");
                initialize.invoke(null);
                plugins.add(initialize);
            }
            catch  (Exception e)
            {
                e.printStackTrace();//TODO Error MEssage
            }
        }
    }

    public List<Method> getPlugins()
    {
        return plugins;
    }
}