package com.antgrimmitt.caffeinecompressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
    public static final String directory = "C:\\Users\\ant\\IdeaProjects\\caffeine-compessor\\css\\";

    public Main() {

        properties = new Properties();

        try {
            properties.load(new FileInputStream("project.properties"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        System.out.println("properties = " + properties.getProperty("file.stylesheets"));

        String[] cssFiles = properties.getProperty("file.stylesheets").split(",");

        CSSInstance instance = new CSSInstance();

        for (String cssFile : cssFiles) {
            String css = instance.renderString(new File(directory + cssFile.trim()), false);
            System.out.println(cssFile + " contents = " + css);
        }



//        File[] cssFiles = cssDirectory.listFiles(new FilenameFilter() {
//            public boolean accept(File dir, String name) {
//                boolean accept = false;
//                if (name.endsWith(".css")) {
//                    accept = true;
//                }
//                return accept;
//            }
//        });
//
//        /*
//         * an instance of a file that has been minified
//         */
//        List<CSSInstance> cssInstanceses = new ArrayList<CSSInstance>();
//
//        for (File cssFile : cssFiles) {
//
//            CSSInstance instance = new CSSInstance();
//            instance.setFile(cssFile);
//            instance.renderString();
//            if(instance.hasImports())
//
//            cssInstanceses.add();
//        }
    }

    //
    public static void main(String[] args) {
//        if (args != null) {
//            for (String arg : args) {
//                //css dir argument
//                if (arg.startsWith("-c")) {
//                    cssDirectory = new File(arg.substring(2, arg.length()));
//                } else if (arg.startsWith("-o")) { //output directory
//                    cssOutput = new File(arg.substring(2, arg.length()));
//                } else {
//                    System.err.println("No arguments");
//                    System.exit(-1);
//                }
//            }
//        }
        Main main = new Main();
    }
}
