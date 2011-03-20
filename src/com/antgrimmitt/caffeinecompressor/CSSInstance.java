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
            if (cssFile.getAbsolutePath().equals(processedFile)) {
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
                        } else {
                            inlineCSSFile = line.split("\\(")[1];
                        }
                        renderString(new File(cssFile.getAbsolutePath().replace(cssFile.getName(), (inlineCSSFile.replaceAll("\\);", "")))), true);
                    } else {
                        cssLines.append(line);
                    }
                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            return cssLines.toString();
        } else {
            return null;
        }
    }
}
