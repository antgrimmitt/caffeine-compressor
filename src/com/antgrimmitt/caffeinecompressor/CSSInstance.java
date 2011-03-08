package com.antgrimmitt.caffeinecompressor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.yahoo.platform.yui.compressor.CssCompressor;

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

    private CssCompressor cssCompressor = new CssCompressor();

    public CSSInstance() {
    }

    public String renderString(File cssFile, boolean imported) {
        if (!imported) {
            cssLines = new StringBuilder();
        }
        cssCompressor = new CssCompressor();

        boolean alreadyProcessed = false;

        for (String processedFile : processedCSS) {
            System.out.println("processedFile = " + processedFile);
            System.out.println("cssFile.getAbsolutePath() = " + cssFile.getAbsolutePath());
            if (cssFile.getAbsolutePath().equals(processedFile)) {
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
                    if (line.startsWith("@import")) {
                        String inlineCSSFile;
                        if (line.contains("\"")) {
                            inlineCSSFile = line.split("\"")[1];
                            System.out.println("inlineCSSFile = " + inlineCSSFile);
                        } else {
                            inlineCSSFile = line.split("\\(")[1];
                        }
                        renderString(new File(cssFile.getAbsolutePath().replace(cssFile.getName(),(inlineCSSFile.replaceAll("\\);", "")))), true);
//                        processedCSS.add(i.getAbsolutePath());
                    } else {
//                        cssLines.append(cssCompressor.compress(line));
                        cssLines.append(line);
                    }
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            System.out.println("final cssFile = " + cssFile.getAbsolutePath());
            return cssLines.toString();
        } else {
            return null;
        }
    }
}
