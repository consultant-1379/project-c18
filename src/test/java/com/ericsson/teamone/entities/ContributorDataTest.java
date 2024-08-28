package com.ericsson.teamone.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContributorDataTest {

    private String from = "20180101";
    private String to = "20210101";

    private String dateRange = from + "-" + to;

    RepoData repoData;
    RepoData repoData2;
    ContributorData contributorData;

    @BeforeEach
    void setUp() {
        repoData = new RepoData(dateRange, "https://github.com/Angelitus02/OOPTSD",1,2,3);
        repoData2 = new RepoData(dateRange, "repUrl", 3, 2, 1);
        contributorData = new ContributorData(repoData, "AngelContributor", 2);
    }
    @AfterEach
    void tearDown() {
        repoData = null;
        repoData2 = null;
        contributorData = null;
    }

    @Test
    void getId() {
        contributorData.setId(5L);
        assertEquals(5L, contributorData.getId());
    }

    @Test
    void setId() {
        contributorData.setId(6L);
        assertEquals(6L, contributorData.getId());
    }

    @Test
    void getRepoData() {
        assertEquals(repoData, contributorData.getRepoData());
    }

    @Test
    void setRepoData() {
        contributorData.setRepoData(repoData2);
        assertEquals(repoData2, contributorData.getRepoData());
    }

    @Test
    void getContributorName() {
        assertEquals("AngelContributor", contributorData.getContributorName());
    }

    @Test
    void setContributorName() {
        contributorData.setContributorName("NotAngelContributor");
        assertEquals("NotAngelContributor", contributorData.getContributorName());
    }

    @Test
    void getCommitCount() {
        assertEquals(2, contributorData.getCommitCount());
    }

    @Test
    void setCommitCount() {
        contributorData.setCommitCount(1);
        assertEquals(1, contributorData.getCommitCount());
    }

    @Test
    void equals() {
        RepoData testRepoData = new RepoData();
        ContributorData testData = new ContributorData(testRepoData, "test", 1);
        ContributorData copy = new ContributorData(testRepoData, "test", 1);
        assertEquals(testData, copy);
    }

    @Test
    void equalsNull() {
        RepoData testRepoData = new RepoData();
        ContributorData testData = new ContributorData(testRepoData, "test", 1);
        ContributorData copy = null;
        assertNotEquals(testData, copy);
    }

    @Test
    void hashcode() {
        RepoData testRepoData = new RepoData();
        ContributorData testData = new ContributorData(testRepoData, "test", 1);
        ContributorData copy = new ContributorData(testRepoData, "test", 1);

        assertEquals(testData.hashCode(), copy.hashCode());
    }
}