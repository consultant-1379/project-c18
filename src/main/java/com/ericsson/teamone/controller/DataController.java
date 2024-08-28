package com.ericsson.teamone.controller;

import com.ericsson.teamone.entities.ContributorData;
import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.entities.RepoData;
import com.ericsson.teamone.repositories.ContributorDataRepository;
import com.ericsson.teamone.repositories.PerFileDataRepository;
import com.ericsson.teamone.repositories.RepoDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
public class DataController {

    @Autowired
    private RepoDataRepository repoDataRepository;

    @Autowired
    private PerFileDataRepository perFileDataRepository;

    @Autowired
    private ContributorDataRepository contributorDataRepository;

    @Autowired
    private Environment env;

    @GetMapping(value = "/commit",
            produces = {"application/json"})
    public RepoData showCommit(Model model,
                               @RequestParam("from") String from,
                               @RequestParam("to") String to,
                               @RequestParam("repUrl") String repUrl) {

        String dateRange = from + "-" + to;
        //https://github.com/Angelitus02/OOPTSD 20180101 20200101
        Optional<RepoData> result = repoDataRepository.findByUrlAndDateRange(repUrl, dateRange);

        RepoData repoData = null;

        if (!result.isPresent()) {
            //call Python Script, makes csv
            String command =
                    "python3 " + env.getProperty("PYTHON_SCRIPT") + " " + repUrl + " " + from + " " + to;
            try {
                System.out.println("Requesting data from python");
                Process p = Runtime.getRuntime().exec(command);

                p.waitFor();
                Thread.sleep(1500);
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getStackTrace());
                Thread.currentThread().interrupt();
            }
        }

        result = repoDataRepository.findByUrlAndDateRange(repUrl, dateRange);

        if(result.isPresent()){
            repoData = result.get();
        }

        return repoData;

    }

    @GetMapping(value = "/fileData",
            produces = {"application/json"})
    public List<PerFileData> getAllDataPerFile(Model model,
                                               @RequestParam("repoDataId") Long repoDataId){

        List<PerFileData> listOfFileData = new ArrayList<>();
        Optional<RepoData> result = repoDataRepository.findById(repoDataId);

        if (!result.isPresent()) {
            return listOfFileData;
        }

        RepoData repoData = result.get();

        List<Optional<PerFileData>> result2 = perFileDataRepository.findByRepoData(repoData);

        for  (Optional<PerFileData> o: result2){
            if(o.isPresent()){
                listOfFileData.add(o.get());
            }
        }

        System.out.println(perFileDataRepository.findAll());
        return listOfFileData;
    }

    @GetMapping(value = "/contributorData",
            produces = {"application/json"})
    public List<ContributorData> getAllContributorData(Model model,
                                               @RequestParam("repoDataId") Long repoDataId){
        List<ContributorData> listOfContributorData = new ArrayList<>();

        Optional<RepoData> result = repoDataRepository.findById(repoDataId);
        if (!result.isPresent()) {
            return listOfContributorData;
        }

        RepoData repoData = result.get();

        List<Optional<ContributorData>> result3 = contributorDataRepository.findByRepoData(repoData);


        for  (Optional<ContributorData> o: result3){
            if(o.isPresent()){
                listOfContributorData.add(o.get());
            }
        }

        System.out.println(contributorDataRepository.findAll());
        return listOfContributorData;
    }

}
