package com.ericsson.teamone.services;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.internal.Maps;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CSVParserTest {

    @Autowired
    CSVParser parser;

    @Test
    void parsePerFileData() {
        BufferedReader br = new BufferedReader(new StringReader("test,1,2,3\n"));
        Map<String, double []> expected = new HashMap<>();
        expected.put("test", new double[]{1, 2, 3});
        Map<String, double[]> actual = parser.parsePerFileData(br);
        assertTrue(Arrays.equals(actual.get("test"), expected.get("test")));
    }

    @Test
    void parseRepoData() {
        BufferedReader br = new BufferedReader(new StringReader("test,1,2,3\n"));
        String [] actual = parser.parseRepoData(br);
        String [] expected = new String[] {"test","1","2","3"};
        assertTrue(Arrays.equals(actual, expected));
    }

    @Test
    void parseContributorData() {
        BufferedReader br = new BufferedReader(new StringReader("test,1,2,3\n"));
        String [][] expected = {{"test", "1", "2", "3"}};
        ArrayList<String []> actual = parser.parseContributorData(br);
        assertTrue(Arrays.equals(expected[0], actual.get(0)));
    }
}