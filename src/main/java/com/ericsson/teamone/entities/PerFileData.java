package com.ericsson.teamone.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class PerFileData {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RepoData repoData;

    private String fileName;
    private int totalCodeChurn;
    private int maxCodeChurn;
    private double avgCodeChurn;
    private double hunkCount;
    private int contributorCount;
    private int minorContributorCount;

    public PerFileData() {
    }

    public PerFileData(RepoData repoData, String fileName, int totalCodeChurn, int maxCodeChurn,
                       double avgCodeChurn, double hunkCount, int contributorCount, int minorContributorCount) {
        this.repoData = repoData;
        this.fileName = fileName;
        this.totalCodeChurn = totalCodeChurn;
        this.maxCodeChurn = maxCodeChurn;
        this.avgCodeChurn = avgCodeChurn;
        this.hunkCount = hunkCount;
        this.contributorCount = contributorCount;
        this.minorContributorCount = minorContributorCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RepoData getRepoData() {
        return repoData;
    }

    public void setRepoData(RepoData repoData) {
        this.repoData = repoData;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTotalCodeChurn() {
        return totalCodeChurn;
    }

    public void setTotalCodeChurn(int totalCodeChurn) {
        this.totalCodeChurn = totalCodeChurn;
    }

    public int getMaxCodeChurn() {
        return maxCodeChurn;
    }

    public void setMaxCodeChurn(int maxCodeChurn) {
        this.maxCodeChurn = maxCodeChurn;
    }

    public double getAvgCodeChurn() {
        return avgCodeChurn;
    }

    public void setAvgCodeChurn(double avgCodeChurn) {
        this.avgCodeChurn = avgCodeChurn;
    }

    public double getHunkCount() {
        return hunkCount;
    }

    public void setHunkCount(double hunkCount) {
        this.hunkCount = hunkCount;
    }

    public int getContributorCount() {
        return contributorCount;
    }

    public void setContributorCount(int contributorCount) {
        this.contributorCount = contributorCount;
    }

    public int getMinorContributorCount() {
        return minorContributorCount;
    }

    public void setMinorContributorCount(int minorContributorCount) {
        this.minorContributorCount = minorContributorCount;
    }

    @Override
    public String toString() {
        return "PerFileData{" +
                "id=" + id +
                ", repoData=" + repoData +
                ", fileName='" + fileName + '\'' +
                ", totalCodeChurn=" + totalCodeChurn +
                ", maxCodeChurn=" + maxCodeChurn +
                ", avgCodeChurn=" + avgCodeChurn +
                ", hunkCount=" + hunkCount +
                ", contributorCount=" + contributorCount +
                ", minorContributorCount=" + minorContributorCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerFileData that = (PerFileData) o;
        return totalCodeChurn == that.totalCodeChurn && maxCodeChurn == that.maxCodeChurn && Double.compare(that.avgCodeChurn, avgCodeChurn) == 0 && Double.compare(that.hunkCount, hunkCount) == 0 && contributorCount == that.contributorCount && minorContributorCount == that.minorContributorCount && Objects.equals(id, that.id) && Objects.equals(repoData, that.repoData) && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, repoData, fileName, totalCodeChurn, maxCodeChurn, avgCodeChurn, hunkCount, contributorCount, minorContributorCount);
    }
}
