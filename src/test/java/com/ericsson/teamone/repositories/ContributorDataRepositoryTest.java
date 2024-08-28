package com.ericsson.teamone.repositories;

import com.ericsson.teamone.entities.ContributorData;
import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.entities.RepoData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ContributorDataRepositoryTest {

    @Autowired
    private RepoDataRepository repoDataRepository;

    @Autowired
    private ContributorDataRepository contributorDataRepository;

    @Test
    public void addNewRecordInDatabase(){

        RepoData repoData = new RepoData("123-123","github",10,5,2);
        repoData = repoDataRepository.save(repoData);

        ContributorData contributorData = new ContributorData(repoData, "John", 5);
        contributorData = contributorDataRepository.save(contributorData);

        assertEquals(repoData, contributorData.getRepoData());
        assertEquals("John", contributorData.getContributorName());
        assertEquals(5, contributorData.getCommitCount());
    }

    @Test
    void removeRecordInDatabase(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        ContributorData contributorData = new ContributorData(repoData, "John", 5);
        contributorData = contributorDataRepository.save(contributorData);

        contributorDataRepository.delete(contributorData);

        List<ContributorData> results = new ArrayList<>();
        for(ContributorData dr : contributorDataRepository.findAll()){
            results.add(dr);
        }

        assertEquals(0, results.size());

    }

    @Test
    void updateRecordInDatabase(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        ContributorData contributorData = new ContributorData(repoData, "John", 5);
        contributorData = contributorDataRepository.save(contributorData);

        contributorData.setContributorName("Steve");
        contributorData = contributorDataRepository.save(contributorData);

        List<ContributorData> results = new ArrayList<>();
        for(ContributorData dr : contributorDataRepository.findAll()){
            results.add(dr);
        }

        assertEquals(1, results.size());
        assertEquals("Steve", contributorData.getContributorName());

    }

    @Test
    void findById(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        ContributorData contributorData = new ContributorData(repoData, "John", 5);
        contributorData = contributorDataRepository.save(contributorData);

        Long id = contributorData.getId();

        assertEquals(true, contributorDataRepository.findById(id).isPresent());
        assertEquals("John", contributorDataRepository.findById(id).get().getContributorName());

    }

    @Test
    void findByIdARecordNotInDB(){

        assertEquals(false, contributorDataRepository.findById(1L).isPresent());

    }

    @Test
    void findByRepoData() {
        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        ContributorData contributorData = new ContributorData(repoData, "John", 5);
        contributorData = contributorDataRepository.save(contributorData);

        assertEquals(contributorData, contributorDataRepository.findByRepoData(repoData).get(0).get());
        assertEquals("John", contributorDataRepository.findByRepoData(repoData).get(0).get().getContributorName());
    }

    @Test
    void findByRepoDataARecordNotInDB() {
        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        ContributorData contributorData = new ContributorData(repoData, "John", 5);

        assertEquals(true, contributorDataRepository.findByRepoData(repoData).isEmpty());
    }
}