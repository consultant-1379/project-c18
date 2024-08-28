package com.ericsson.teamone.services;

import com.ericsson.teamone.entities.ContributorData;
import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.repositories.ContributorDataRepository;
import com.ericsson.teamone.repositories.PerFileDataRepository;
import com.ericsson.teamone.repositories.RepoDataRepository;
import com.ericsson.teamone.entities.RepoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class WriterService implements Runnable{

    @Autowired
    CSVParser parser;

    @Autowired
    RepoDataRepository repoDataRepository;
    @Autowired
    PerFileDataRepository perFileDataRepository;
    @Autowired
    ContributorDataRepository contributorDataRepository;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Value("${REPO_CSV}")
    private String repoDataPath;
    @Value("${PER_FILE_CSV}")
    private String perFileDataPath;
    @Value("${CONTRIBUTORS_CSV}")
    private String contributorDataPath;

    // check if file given by path exists
    public boolean findFile(String path) {
        File check = new File(path);
        return check.exists();
    }

    // create and return data object constructed from csv file and delete the file
    public RepoData createRepoData(BufferedReader br) {
        String [] atts = parser.parseRepoData(br);

        return new RepoData(atts[1], atts[0],
                Integer.parseInt(atts[2]),
                Integer.parseInt(atts[3]),
                Integer.parseInt(atts[4]),
                Integer.parseInt(atts[5]),
                Integer.parseInt(atts[6]));

    }

    // creates an array of PerFileData objects created from given csv and
    // corresponding RepoData
    public PerFileData[] createPerFileDataArray(BufferedReader br, RepoData repoData) {
        Map<String, double[]> data = parser.parsePerFileData(br);
        double [] atts;
        PerFileData [] out = new PerFileData[data.size()];
        int i = 0;
        for(Map.Entry<String,double[]> entry: data.entrySet()) {
            atts = entry.getValue();
            out[i] = new PerFileData(repoData, entry.getKey(), (int) atts[0], (int) atts[1], atts[2], atts[3], (int)atts[4], (int)atts[5]);
            i++;
        }
        return out;

    }
    // creates an array of ContributorData objects created from given csv and
    // corresponding RepoData
    public ContributorData[] createContributorDataArray(BufferedReader br, RepoData repoData) {
        ArrayList<String []> data = parser.parseContributorData(br);

        return data.stream().map(d -> new ContributorData(repoData, d[0], Integer.parseInt(d[1]))).toArray(ContributorData[]::new);
    }

    public void insertData(RepoData repoData) {
        repoDataRepository.save(repoData);
    }

    public void insertPerFileDataArray(PerFileData [] pfda) {
        for(PerFileData pfd : pfda) {
            perFileDataRepository.save(pfd);
        }
    }

    public void insertContributorDataArray(ContributorData [] cda) {
        for(ContributorData cd : cda) {
            contributorDataRepository.save(cd);
        }
    }

    @Override
    public void run() {
        if(findFile(repoDataPath)) {
            try {
                File repoFile = new File(repoDataPath);
                BufferedReader br = new BufferedReader(new FileReader(repoFile));
                RepoData repoData = createRepoData(br);
                insertData(repoData);
                if(!repoFile.delete()){
                    System.out.println(repoDataPath + " can't be deleted");
                }
                if (findFile(perFileDataPath)) {
                    File perFileFile = new File(perFileDataPath);
                    br = new BufferedReader(new FileReader(perFileFile));
                    insertPerFileDataArray(createPerFileDataArray(br, repoData));
                    if(!perFileFile.delete()){
                        System.out.println(perFileDataPath + " can't be deleted");
                    }
                }
                if (findFile(contributorDataPath)) {
                    File contributorFile = new File(contributorDataPath);
                    br = new BufferedReader(new FileReader(contributorFile));
                    insertContributorDataArray(createContributorDataArray(br, repoData));
                    if(!contributorFile.delete()){
                        System.out.println(contributorDataPath + " can't be deleted");
                    }

                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    public void start() {
        executorService.scheduleAtFixedRate(this, 5, 1, TimeUnit.SECONDS);
    }

    public void stop() {executorService.shutdownNow();}
}
