package com.ericsson.teamone.services;

import com.ericsson.teamone.entities.ContributorData;
import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.entities.RepoData;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.beans.SamePropertyValuesAs;

@SpringBootTest
class WriterServiceTest {

    @Autowired
    WriterService service;

    static RepoData repoData;
    static PerFileData pfd;
    static ContributorData cd;

    @BeforeAll
    static void setup() {
        repoData = new RepoData("test", "test", 1, 2, 3, 4, 5);
        pfd = new PerFileData(repoData, "test", 1, 2, 3, 4, 5, 6);
        cd = new ContributorData(repoData, "test", 1);

    }

    @Test
    void createRepoData() {
        RepoData expected = repoData;
        BufferedReader br = new BufferedReader(new StringReader("test,test,1,2,3,4,5\n"));
        RepoData actual = service.createRepoData(br);
        assertEquals(expected, actual);
    }

    @Test
    void createPerFileDataArray() {
        PerFileData [] expected = new PerFileData[]{pfd};

        BufferedReader br = new BufferedReader(new StringReader("test,1,2,3,4,5,6\n"));
        PerFileData [] actual = service.createPerFileDataArray(br, repoData);

        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    void createContributorDataArray() {
        ContributorData[] expected = new ContributorData[]{cd};

        BufferedReader br = new BufferedReader(new StringReader("test,1\n"));
        ContributorData [] actual = service.createContributorDataArray(br, repoData);

        //AssertTrue(Arrays.equals(actual, expected));
        assertTrue(Arrays.equals(expected, actual));
    }

    @Test
    void insertData() {
        service.insertData(repoData);
        List<RepoData> rps = new ArrayList<>();
        for(RepoData rp : service.repoDataRepository.findAll()){
            rps.add(rp);
        }

        assertEquals(1, rps.size());
    }

    @Test
    void insertPerFileDataArray() {
        PerFileData [] data = new PerFileData[]{pfd};
        service.insertPerFileDataArray(data);
        List<PerFileData> pfds = new ArrayList<>();
        for (PerFileData p : service.perFileDataRepository.findAll()){
            pfds.add(p);
        }
        assertEquals(1, pfds.size());
    }

    @Test
    void insertContributorDataArray() {
        ContributorData [] data = new ContributorData[]{cd};
        service.insertContributorDataArray(data);
        List<ContributorData> cds = new ArrayList<>();
        for (ContributorData c : service.contributorDataRepository.findAll()){
            cds.add(c);
        }
        assertEquals(1, cds.size());
    }

}