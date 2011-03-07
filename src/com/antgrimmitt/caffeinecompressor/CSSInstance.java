package com.antgrimmitt.caffeinecompressor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ant
 * Date: 07/03/11
 * Time: 22:21
 * To change this template use File | Settings | File Templates.
 */
public class CSSInstance {
    public static final String directory = "C:\\Users\\ant\\IdeaProjects\\caffeine-compessor\\css\\";
    StringBuilder cssLines = new StringBuilder();
    protected static List<String> processedCSS = new ArrayList<String>();

    public CSSInstance() {
    }

    public String renderString(File cssFile, boolean imported) {
        if (!imported) {
            cssLines = new StringBuilder();
        }

        boolean alreadyProcessed = false;

        for (String processedFile : processedCSS) {
            System.out.println("processedFile = " + processedFile);
            if (cssFile.getAbsoluteFile().equals(processedFile)) {
                System.out.println("File already processed");
                alreadyProcessed = true;
                break;
            }
        }


        if (!alreadyProcessed) {
            processedCSS.add(cssFile.getAbsolutePath());


            try {

                BufferedReader br = new BufferedReader(new FileReader(cssFile));

                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("@import")) {
                        String inlineCSSFile;
                        if (line.contains("\"")) {
                            inlineCSSFile = line.split("\"")[2];
                        } else {
                            inlineCSSFile = line.split("\\(")[1];
                        }
                        System.out.println("inlineCSSFile = " + inlineCSSFile);
                        cssLines.append(renderString(new File(directory + inlineCSSFile.replaceAll("\\);", "")), true));
//                        processedCSS.add(i.getAbsolutePath());
                    }
                    cssLines.append(line.trim());
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return cssLines.toString();
    }
}
