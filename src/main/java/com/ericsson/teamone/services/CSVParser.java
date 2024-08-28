package com.ericsson.teamone.services;


import org.springframework.stereotype.Component;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class CSVParser{

    // returns a map of filename to filedata from a given csv file
    public Map<String, double []> parsePerFileData(BufferedReader br) {
        Map<String, double[]> fileDataMap = new HashMap<>();
        try {
            String line;
            while ((line = br.readLine()) != null) {
                String[] contents = line.split(",");
                String key = contents[0];
                double[] values = new double[contents.length-1];
                for (int i = 1; i < contents.length; i++) {
                    values[i-1] = Double.parseDouble(contents[i]);
                }
                fileDataMap.put(key, values);
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        return fileDataMap;
    }

    // returns a string array of tokens from a given csv file
    public String [] parseRepoData(BufferedReader br) {
        String [] data = null;
        String line;
        try {
                data = br.readLine().split(",");
                br.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return data;
    }

    // returns an arraylist of [[contributor name, noCommits]] from a given csv file
    // 2D array here instead of map in case of two with same name
    public ArrayList<String []> parseContributorData(BufferedReader br) {
        ArrayList data = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] contents = line.split(",");
                data.add(contents);
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }

        return data;
    }

}
