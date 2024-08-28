package com.ericsson.teamone.repositories;

import com.ericsson.teamone.entities.ContributorData;
import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.entities.RepoData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PerFileDataRepositoryTest {

    @Autowired
    private RepoDataRepository repoDataRepository;

    @Autowired
    private PerFileDataRepository perFileDataRepository;

    @Autowired
    private ContributorDataRepository contributorDataRepository;

    @Test
    public void addNewRecordInDatabase(){

        //Long id, RepoData repoData, String fileName, int totalCodeChurn, int maxCodeChurn, double avgCodeChurn, int hunkCount
        RepoData repoData = new RepoData("123-123","github",10,5,2);
        repoData = repoDataRepository.save(repoData);

        PerFileData perFileData = new PerFileData(repoData, "test.txt", 2, 4, 3, 2, 0, 0);
        perFileData = perFileDataRepository.save(perFileData);

        assertEquals(repoData, perFileData.getRepoData());
        assertEquals("test.txt", perFileData.getFileName());
        assertEquals(2,perFileData.getTotalCodeChurn());
        assertEquals(4,perFileData.getMaxCodeChurn());
        assertEquals(3,perFileData.getAvgCodeChurn());
        assertEquals(2,perFileData.getHunkCount());
        assertEquals(0,perFileData.getContributorCount());
        assertEquals(0,perFileData.getMinorContributorCount());
    }

    @Test
    void removeRecordInDatabase(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        PerFileData perFileData = new PerFileData(repoData, "test.txt", 2, 4, 3, 2, 0, 0);
        perFileData = perFileDataRepository.save(perFileData);

        perFileDataRepository.delete(perFileData);

        List<PerFileData> results = new ArrayList<>();
        for(PerFileData dr : perFileDataRepository.findAll()){
            results.add(dr);
        }

        assertEquals(0, results.size());

    }

    @Test
    void updateRecordInDatabase(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        PerFileData perFileData = new PerFileData(repoData, "test.txt", 2, 4, 3, 2, 0, 0);
        perFileData = perFileDataRepository.save(perFileData);

        perFileData.setFileName("test2.txt");
        perFileData = perFileDataRepository.save(perFileData);

        List<PerFileData> results = new ArrayList<>();
        for(PerFileData dr : perFileDataRepository.findAll()){
            results.add(dr);
        }

        assertEquals(1, results.size());
        assertEquals("test2.txt", perFileData.getFileName());

    }

    @Test
    void findById(){

        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        PerFileData perFileData = new PerFileData(repoData, "test.txt", 2, 4, 3, 2, 0, 0);
        perFileData = perFileDataRepository.save(perFileData);

        Long id = perFileData.getId();

        assertEquals(true,perFileDataRepository.findById(id).isPresent());
        assertEquals("test.txt",perFileDataRepository.findById(id).get().getFileName());

    }

    @Test
    void findByIdARecordNotInDB(){

        assertEquals(false, perFileDataRepository.findById(1L).isPresent());

    }

    @Test
    void findByRepoData() {
        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        PerFileData perFileData = new PerFileData(repoData, "test.txt", 2, 4, 3, 2, 0, 0);
        perFileData = perFileDataRepository.save(perFileData);

        assertEquals(perFileData, perFileDataRepository.findByRepoData(repoData).get(0).get());
        assertEquals("test.txt", perFileDataRepository.findByRepoData(repoData).get(0).get().getFileName());
    }

    @Test
    void findByRepoDataARecordNotInDB() {
        RepoData repoData = new RepoData("123-123","github",10,5,2, 5, 6);
        repoData = repoDataRepository.save(repoData);

        PerFileData perFileData = new PerFileData(repoData, "test.txt", 2, 4, 3, 2, 0, 0);

        assertEquals(true, perFileDataRepository.findByRepoData(repoData).isEmpty());
    }
}