package com.ericsson.teamone.repositories;

import com.ericsson.teamone.entities.RepoData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RepoDataRepositoryTest {


    @Autowired
    private RepoDataRepository repoDataRepository;

    @Test
    void addNewRecordInDatabase(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        assertEquals("123-123", repoData.getDateRange());
        assertEquals("github", repoData.getRepUrl());
        assertEquals(10, repoData.getNoOfCommits());
        assertEquals(5, repoData.getLinesAdded());
        assertEquals(2, repoData.getLinesRemoved());
        assertEquals(5, repoData.getMaxChangeSetSize());
        assertEquals(6, repoData.getAvgChangeSetSize());
    }

    @Test
    void removeRecordInDatabase(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        repoDataRepository.delete(repoData);

        List<RepoData> results = new ArrayList<>();
        for(RepoData dr : repoDataRepository.findAll()){
            results.add(dr);
        }

        assertEquals(0, results.size());

    }

    @Test
    void updateRecordInDatabase(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        repoData.setRepUrl("github 2");
        repoData = repoDataRepository.save(repoData);

        List<RepoData> results = new ArrayList<>();
        for(RepoData dr : repoDataRepository.findAll()){
            results.add(dr);
        }

        assertEquals(1, results.size());
        assertEquals("github 2", repoData.getRepUrl());

    }

    @Test
    void findById(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        Long id = repoData.getId();

        assertEquals(true,repoDataRepository.findById(id).isPresent());
        assertEquals("github",repoDataRepository.findById(id).get().getRepUrl());

    }

    @Test
    void findByIdARecordNotInDB(){

        assertEquals(false,repoDataRepository.findById(1L).isPresent());

    }

    @Test
    void findByUrlAndDateRange() {

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        assertEquals(true,repoDataRepository.findByUrlAndDateRange("github","123-123").isPresent());
        assertEquals("github",repoDataRepository.findByUrlAndDateRange("github","123-123").get().getRepUrl());
        assertEquals("123-123",repoDataRepository.findByUrlAndDateRange("github","123-123").get().getDateRange());

    }

    @Test
    void findByUrlAndDateRangeARecordNotDB() {

        assertEquals(false,repoDataRepository.findByUrlAndDateRange("github","123-123").isPresent());

    }
}