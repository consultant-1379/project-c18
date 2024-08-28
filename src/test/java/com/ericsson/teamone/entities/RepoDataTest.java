package com.ericsson.teamone.entities;

import com.ericsson.teamone.repositories.ContributorDataRepository;
import com.ericsson.teamone.repositories.RepoDataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RepoDataTest {

    private String from = "20180101";
    private String to = "20210101";

    private String dateRange = from + "-" + to;

    RepoData repoData;
    RepoData repoData2;

//    @Test
//    public void should_not_allow_null_dateRange() {
//        assertEquals(null, repoData.getDateRange());
//    }

    @BeforeEach
    void setUp() {
        repoData = new RepoData(dateRange, "https://github.com/Angelitus02/OOPTSD",1,2,3);
        repoData2 = new RepoData(dateRange, "repUrl", 3, 2, 1);
    }
    @AfterEach
    void tearDown() {
        repoData = null;
        repoData2 = null;
    }

    @Test
    void getId() {
        repoData.setId(1L);
        assertEquals(1L, repoData.getId());
    }

    @Test
    void setId() {
        repoData.setId(1L);
        assertEquals(1L, repoData.getId());
    }

    @Test
    void getDateRange() {
        assertEquals("20180101-20210101", repoData.getDateRange());
    }

    @Test
    void setDateRange() {
        repoData.setDateRange("20180102-20210102");
        assertEquals("20180102-20210102", repoData.getDateRange());
    }

    @Test
    void getRepUrl() {
        assertEquals("https://github.com/Angelitus02/OOPTSD", repoData.getRepUrl());
    }

    @Test
    void setRepUrl() {
        repoData.setRepUrl("repUrl");
        assertEquals("repUrl", repoData.getRepUrl());
    }

    @Test
    void getNoOfCommits() {
        assertEquals(1, repoData.getNoOfCommits());
    }

    @Test
    void setNoOfCommits() {
        repoData.setNoOfCommits(2);
        assertEquals(2, repoData.getNoOfCommits());
    }

    @Test
    void getLinesAdded() {
        assertEquals(2, repoData.getLinesAdded());
    }

    @Test
    void setLinesAdded() {
        repoData.setLinesAdded(4);
        assertEquals(4, repoData.getLinesAdded());
    }

    @Test
    void getLinesRemoved() {
        assertEquals(3, repoData.getLinesRemoved());
    }

    @Test
    void setLinesRemoved() {
        repoData.setLinesRemoved(5);
        assertEquals(5, repoData.getLinesRemoved());
    }

    @Test
    void getMaxChangeSetSize() {
        repoData.setMaxChangeSetSize(50);
        assertEquals(50, repoData.getMaxChangeSetSize());
    }

    @Test
    void setMaxChangeSetSize() {
        repoData.setMaxChangeSetSize(60);
        assertEquals(60, repoData.getMaxChangeSetSize());
    }

    @Test
    void getAvgChangeSetSize() {
        repoData.setAvgChangeSetSize(20.00);
        assertEquals(20.00, repoData.getAvgChangeSetSize());
    }

    @Test
    void setAvgChangeSetSize() {
        repoData.setAvgChangeSetSize(30.00);
        assertEquals(30.00, repoData.getAvgChangeSetSize());
    }

    @Test
    void testToString() {
        assertEquals("RepoData{" +
                "id=" + repoData.getId() +
                ", dateRange='" + repoData.getDateRange() + '\'' +
                ", repUrl='" + repoData.getRepUrl() + '\'' +
                ", noOfCommits=" + repoData.getNoOfCommits() +
                ", linesAdded=" + repoData.getLinesAdded() +
                ", linesRemoved=" + repoData.getLinesRemoved() +
                ", maxChangeSetSize=" + repoData.getMaxChangeSetSize() +
                ", avgChangeSetSize=" + repoData.getAvgChangeSetSize() +
                '}', repoData.toString());
    }

    @Test
    void equals() {
        RepoData test = new RepoData("test", "test", 1, 1, 1);
        RepoData copy = new RepoData("test", "test", 1, 1, 1);
        assertEquals(test, copy);
    }

    @Test
    void notEquals() {
        RepoData test = new RepoData("test1", "test", 1, 1, 1);
        RepoData copy = new RepoData("test", "test", 1, 1, 1);
        assertNotEquals(test, copy);
    }

    @Test
    void equalsNull() {
        RepoData test = new RepoData("test1", "test", 1, 1, 1);
        RepoData copy = null;
        assertNotEquals(test, copy);
    }

    @Test
    void hashcode() {
        RepoData test = new RepoData("test", "test", 1, 1, 1);
        RepoData copy = new RepoData("test", "test", 1, 1, 1);
        assertEquals(test.hashCode(), copy.hashCode());
    }
}