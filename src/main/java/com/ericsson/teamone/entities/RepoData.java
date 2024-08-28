package com.ericsson.teamone.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Entity
public class RepoData {

    @Id
    @GeneratedValue
    private Long id;
    private String dateRange;
    private String repUrl;
    private int noOfCommits;
    private int linesAdded;
    private int linesRemoved;
    private int maxChangeSetSize;
    private double avgChangeSetSize;


    //cons
    public RepoData() {
    }

    public RepoData(String dateRange, String repUrl, int noOfCommits, int linesAdded, int linesRemoved) {
        this.dateRange = dateRange;
        this.repUrl = repUrl;
        this.noOfCommits = noOfCommits;
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
    }

    public RepoData(String dateRange, String repUrl, int noOfCommits, int linesAdded,
                    int linesRemoved, int maxChangeSetSize, double avgChangeSetSize) {

        this.id = id;
        this.dateRange = dateRange;
        this.repUrl = repUrl;
        this.noOfCommits = noOfCommits;
        this.linesAdded = linesAdded;
        this.linesRemoved = linesRemoved;
        this.maxChangeSetSize = maxChangeSetSize;
        this.avgChangeSetSize = avgChangeSetSize;

    }


    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public String getRepUrl() {
        return repUrl;
    }

    public void setRepUrl(String repUrl) {
        this.repUrl = repUrl;
    }

    public int getNoOfCommits() {
        return noOfCommits;
    }

    public void setNoOfCommits(int noOfCommits) {
        this.noOfCommits = noOfCommits;
    }

    public int getLinesAdded() {
        return linesAdded;
    }

    public void setLinesAdded(int linesAdded) {
        this.linesAdded = linesAdded;
    }

    public int getLinesRemoved() {
        return linesRemoved;
    }

    public void setLinesRemoved(int linesRemoved) {
        this.linesRemoved = linesRemoved;
    }

    public int getMaxChangeSetSize() {
        return maxChangeSetSize;
    }

    public void setMaxChangeSetSize(int maxChangeSetSize) {
        this.maxChangeSetSize = maxChangeSetSize;
    }


    public double getAvgChangeSetSize() {
        return avgChangeSetSize;
    }

    public void setAvgChangeSetSize(double avgChangeSetSize) {
        this.avgChangeSetSize = avgChangeSetSize;
    }

    @Override
    public String toString() {
        return "RepoData{" +
                "id=" + id +
                ", dateRange='" + dateRange + '\'' +
                ", repUrl='" + repUrl + '\'' +
                ", noOfCommits=" + noOfCommits +
                ", linesAdded=" + linesAdded +
                ", linesRemoved=" + linesRemoved +
                ", maxChangeSetSize=" + maxChangeSetSize +
                ", avgChangeSetSize=" + avgChangeSetSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepoData repoData = (RepoData) o;
        return noOfCommits == repoData.noOfCommits && linesAdded == repoData.linesAdded && linesRemoved == repoData.linesRemoved && maxChangeSetSize == repoData.maxChangeSetSize && Double.compare(repoData.avgChangeSetSize, avgChangeSetSize) == 0 && Objects.equals(id, repoData.id) && Objects.equals(dateRange, repoData.dateRange) && Objects.equals(repUrl, repoData.repUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateRange, repUrl, noOfCommits, linesAdded, linesRemoved, maxChangeSetSize, avgChangeSetSize);
    }
}
