package com.antgrimmitt.caffeinecompressor;

import com.yahoo.platform.yui.compressor.CssCompressor;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: ant
 * Date: 07/03/11
 * Time: 22:03
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    private static File cssDirectory;
    private static File cssOutput;
    private Properties properties;
    private static File homedir;
    private static boolean monitorFolder = false;
    private static boolean mergeAll;

    public Main() {

        properties = new Properties();

        try {
            properties.load(new FileInputStream("caffeine.properties"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        /**
         * Overwrite command lines args if present in properties file
         */

        if (properties.get("cssoutput") != null) {
            cssOutput = new File(properties.get("cssoutput").toString().replace("/", File.separator));
        }

        if (properties.get("cssinput") != null) {
            cssDirectory = new File(properties.get("cssinput").toString().replace("/", File.separator));
        }

        if (properties.getProperty("monitercssfolder") != null) {
            monitorFolder = Boolean.valueOf(properties.getProperty("monitorcssfolder").toString());
        }

        if(properties.get("caffeine.home.directory") != null) {
            homedir = new File(properties.get("caffeine.home.directory").toString());
        }


        String[] cssFiles;
        if (monitorFolder) {
            cssFiles = cssDirectory.list(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    if (name.endsWith(".css")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } else {
            cssFiles = properties.getProperty("file.stylesheets").split(",");
        }
        CSSInstance instance = new CSSInstance();

        HashMap<String, String> compressedCss = new HashMap<String, String>();
        for (String cssFile : cssFiles) {
            String css = instance.renderString(new File(homedir + File.separator + cssDirectory + File.separator
                    + cssFile.trim()), false);

            String content = new CssCompressor().compress(css);
            System.out.println("content = " + content);
            if (!content.equals("null")) {
                compressedCss.put(cssFile, content);
            }
        }


        mergeAll = Boolean.valueOf(properties.get("file.stylesheets.mergeall").toString());

        if (!mergeAll) {

            for (String compressedCssFile : compressedCss.keySet()) {
                System.out.println("compressedCssFile = " + compressedCssFile);
                writeCSSFile(compressedCss.get(compressedCssFile), compressedCssFile);
            }
        } else {
            String outputFile = properties.getProperty("file.stylesheets.mergeall.filename").toString();
            StringBuilder sb = new StringBuilder();
            for (String cssFile : compressedCss.keySet()) {
                sb.append(compressedCss.get(cssFile));
                writeCSSFile(sb.toString(), outputFile);
            }
        }
    }

    public boolean writeCSSFile(String css, String outputFile) {
        boolean sucess = false;
        try {
            BufferedWriter bw = new BufferedWriter(new PrintWriter(new File(homedir + File.separator +
                     cssOutput + File.separator +  outputFile.replaceAll(".css", ".min.css"))));
            bw.write(css);
            bw.close();
            sucess = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return sucess;

    }

    //
    public static void main(String[] args) {

        if (args != null) {

            for (String arg : args) {
                System.out.println("arg = " + arg);
                //css dir argument
                if (arg.startsWith("-c")) {
                    cssDirectory = new File(arg.substring(2, arg.length()));
                    System.out.println("cssDirectory = " + cssDirectory);
                } else if (arg.startsWith("-o")) { //output directory
                    cssOutput = new File(arg.substring(2, arg.length()));
                    System.out.println("cssOutput = " + cssOutput);
                } else if (arg.startsWith("-h")) {
                    homedir = new File(arg.substring(2, arg.length()));
                    System.out.println("homedir = " + homedir);
                } else if (arg.startsWith("-M")) {
                    monitorFolder = true;
                } else if (arg.startsWith("-A")) {
                    mergeAll = true;
                }
            }
        }
        Main main = new Main();
    }
}
