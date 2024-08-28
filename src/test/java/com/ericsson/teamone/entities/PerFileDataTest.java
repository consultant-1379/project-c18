package com.ericsson.teamone.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerFileDataTest {

    private String from = "20180101";
    private String to = "20210101";

    private String dateRange = from + "-" + to;

    RepoData repoData;
    RepoData repoData2;
    PerFileData perFileData;

    @BeforeEach
    void setUp() {
        repoData = new RepoData(dateRange, "https://github.com/Angelitus02/OOPTSD",1,2,3);
        repoData2 = new RepoData(dateRange, "repUrl", 3, 2, 1);
        perFileData = new PerFileData(repoData, "file1.txt", 50, 50, 50.00, 20.00, 3, 1);
    }
    @AfterEach
    void tearDown() {
        repoData = null;
        repoData2 = null;
        perFileData = null;
    }

    @Test
    void getId() {
        perFileData.setId(8L);
        assertEquals(8L, perFileData.getId());
    }

    @Test
    void setId() {
        perFileData.setId(9L);
        assertEquals(9L, perFileData.getId());
    }

    @Test
    void getRepoData() {
        assertEquals(repoData, perFileData.getRepoData());
    }

    @Test
    void setRepoData() {
        perFileData.setRepoData(repoData2);
        assertEquals(repoData2, perFileData.getRepoData());
    }

    @Test
    void getFileName() {
        assertEquals("file1.txt", perFileData.getFileName());
    }

    @Test
    void setFileName() {
        perFileData.setFileName("file2.txt");
        assertEquals("file2.txt", perFileData.getFileName());
    }

    @Test
    void getTotalCodeChurn() {
        assertEquals(50, perFileData.getTotalCodeChurn());
    }

    @Test
    void setTotalCodeChurn() {
        perFileData.setTotalCodeChurn(40);
        assertEquals(40, perFileData.getTotalCodeChurn());
    }

    @Test
    void getMaxCodeChurn() {
        assertEquals(50, perFileData.getMaxCodeChurn());
    }

    @Test
    void setMaxCodeChurn() {
        perFileData.setMaxCodeChurn(40);
        assertEquals(40, perFileData.getMaxCodeChurn());
    }

    @Test
    void getAvgCodeChurn() {
        assertEquals(50.00, perFileData.getAvgCodeChurn());
    }

    @Test
    void setAvgCodeChurn() {
        perFileData.setAvgCodeChurn(40.00);
        assertEquals(40.00, perFileData.getAvgCodeChurn());
    }

    @Test
    void getHunkCount() {
        assertEquals(20.00, perFileData.getHunkCount());
    }

    @Test
    void setHunkCount() {
        perFileData.setHunkCount(10.00);
        assertEquals(10.00, perFileData.getHunkCount());
    }

    @Test
    void getContributorCount() {
        assertEquals(3, perFileData.getContributorCount());
    }

    @Test
    void setContributorCount() {
        perFileData.setContributorCount(2);
        assertEquals(2, perFileData.getContributorCount());
    }

    @Test
    void getMinorContributorCount() {
        assertEquals(1, perFileData.getMinorContributorCount());
    }

    @Test
    void setMinorContributorCount() {
        perFileData.setMinorContributorCount(2);
        assertEquals(2, perFileData.getMinorContributorCount());
    }

    @Test
    void equals() {
        RepoData testRepoData = new RepoData();
        PerFileData testData = new PerFileData(testRepoData, "test", 1, 1, 1, 1, 1, 1);
        PerFileData copy = new PerFileData(testRepoData, "test", 1, 1, 1, 1, 1, 1);

        assertEquals(testData, copy);
    }

    @Test
    void equalsNull() {
        RepoData testRepoData = new RepoData();
        PerFileData testData = new PerFileData(testRepoData, "test", 1, 1, 1, 1, 1, 1);
        PerFileData copy = null;

        assertNotEquals(testData, copy);
    }

    @Test
    void hashcode() {
        RepoData testRepoData = new RepoData();
        PerFileData testData = new PerFileData(testRepoData, "test", 1, 1, 1, 1, 1, 1);
        PerFileData copy = new PerFileData(testRepoData, "test", 1, 1, 1, 1, 1, 1);

        assertEquals(testData.hashCode(), copy.hashCode());
    }
}