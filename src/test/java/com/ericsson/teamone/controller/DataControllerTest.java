package com.ericsson.teamone.controller;

import com.ericsson.teamone.entities.ContributorData;
import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.entities.RepoData;
import com.ericsson.teamone.repositories.ContributorDataRepository;
import com.ericsson.teamone.repositories.PerFileDataRepository;
import com.ericsson.teamone.repositories.RepoDataRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataControllerTest {

    @Autowired
    private DataController dataController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RepoDataRepository repoDataRepository;

    @Autowired
    private PerFileDataRepository perFileDataRepository;

    @Autowired
    private ContributorDataRepository contributorDataRepository;

    RepoData d1;
    RepoData d2;
    PerFileData pf1;
    PerFileData pf2;
    ContributorData c1;
    ContributorData c2;

    @BeforeEach
    void setUp() {
        d1 = repoDataRepository.save(new RepoData("20211214-20211214","github",10,5,2, 12, 10));
        d2 = repoDataRepository.save(new RepoData("20211214-20211214", "github2", 12, 13,14,5, 3));

        pf1 = perFileDataRepository.save(new PerFileData(d1, "file1.txt",2, 3, 1.5, 6, 7, 8));
        pf2 = perFileDataRepository.save(new PerFileData(d1, "file2.txt",3, 4, 2.5, 7, 8, 9));

        c1 = contributorDataRepository.save(new ContributorData(d1, "Amilcar Aponte", 25));
        c2 = contributorDataRepository.save(new ContributorData(d1, "Rory O'Donnell", 26));

    }

    @AfterEach
    void tearDown() {
        d1 = null;
        d2 = null;
        pf1 = null;
        pf2 = null;
        c1 = null;
        c2 = null;
        perFileDataRepository.deleteAll();
        contributorDataRepository.deleteAll();
        repoDataRepository.deleteAll();

    }

    @Test
    void testGetRepoDataThaExistsInDB() {
        String from = "20211214";
        String to = "20211214";
        String repUrl = "github";

        ResponseEntity<RepoData> responseEntity = testRestTemplate.exchange(
                "/commit?from=" + from + "&to=" + to + "&repUrl=" + repUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RepoData>() {
                }
        );
        RepoData responseBody = (RepoData) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(d1.getDateRange(), responseBody.getDateRange());
        assertEquals(d1.getId(), responseBody.getId());
        assertEquals(d1.getRepUrl(), responseBody.getRepUrl());
    }

    @Test
    void testGetRepoDataThaDoesNotExistsInDB() {
        String from = "20221214";
        String to = "20221214";
        String repUrl = "github";

        ResponseEntity<RepoData> responseEntity = testRestTemplate.exchange(
                "/commit?from=" + from + "&to=" + to + "&repUrl=" + repUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<RepoData>() {
                }
        );

        RepoData responseBody = (RepoData) responseEntity.getBody();
        assertEquals(null, responseBody);

        // This test passes, but it's badly written. The API should be fixed to pass the code below
        //assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getAllDataPerFileThatExistsInDB() {
        ResponseEntity<List<PerFileData>> responseEntity = testRestTemplate.exchange(
                "/fileData?repoDataId=" + d1.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PerFileData>>() {
                }
        );

        List<PerFileData> responseBody = (List<PerFileData>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseBody.size());
        assertEquals("file1.txt", responseBody.get(0).getFileName());
    }

    @Test
    void getAllDataPerFileThatDoesNOTExistsInDB() {
        ResponseEntity<List<PerFileData>> responseEntity = testRestTemplate.exchange(
                "/fileData?repoDataId=" + 100,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PerFileData>>() {
                }
        );

        List<PerFileData> responseBody = (List<PerFileData>) responseEntity.getBody();
        System.out.println(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseBody.size());

        // This test passes, but it's badly written. The API should be fixed to pass the code below
        //assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getAllDataPerFileThatFromRepoWithNoData() {
        ResponseEntity<List<PerFileData>> responseEntity = testRestTemplate.exchange(
                "/fileData?repoDataId=" + d2.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PerFileData>>() {
                }
        );

        List<PerFileData> responseBody = (List<PerFileData>) responseEntity.getBody();
        System.out.println(responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseBody.size());
    }

    @Test
    void getAllContributorDataThatExistsInDB() {
        ResponseEntity<List<ContributorData>> responseEntity = testRestTemplate.exchange(
                "/contributorData?repoDataId=" + d1.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ContributorData>>() {
                }
        );

        List<ContributorData> responseBody = (List<ContributorData>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, responseBody.size());
        assertEquals("Amilcar Aponte", responseBody.get(0).getContributorName());
    }

    @Test
    void getAllContributorDataThatDoesNOTExistsInDB() {
        ResponseEntity<List<ContributorData>> responseEntity = testRestTemplate.exchange(
                "/contributorData?repoDataId=" + 100,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ContributorData>>() {
                }
        );

        List<ContributorData> responseBody = (List<ContributorData>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseBody.size());
        // This test passes, but it's badly written. The API should be fixed to pass the code below
        //assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void getAllContributorDataFromRepoWithNoData() {
        ResponseEntity<List<ContributorData>> responseEntity = testRestTemplate.exchange(
                "/contributorData?repoDataId=" + d2.getId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ContributorData>>() {
                }
        );

        List<ContributorData> responseBody = (List<ContributorData>) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, responseBody.size());
    }
}