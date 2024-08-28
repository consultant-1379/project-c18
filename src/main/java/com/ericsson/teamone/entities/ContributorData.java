package com.ericsson.teamone.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ContributorData {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RepoData repoData;

    private String contributorName;
    private int commitCount;

    public ContributorData() {
    }

    public ContributorData(RepoData repoData, String contributorName, int commitCount) {
        this.repoData = repoData;
        this.contributorName = contributorName;
        this.commitCount = commitCount;
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

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public int getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(int commitCount) {
        this.commitCount = commitCount;
    }

    @Override
    public String toString() {
        return "ContributorData{" +
                "id=" + id +
                ", repoData=" + repoData +
                ", contributorName='" + contributorName + '\'' +
                ", commitCount=" + commitCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContributorData that = (ContributorData) o;
        return commitCount == that.commitCount && Objects.equals(id, that.id) && Objects.equals(repoData, that.repoData) && Objects.equals(contributorName, that.contributorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, repoData, contributorName, commitCount);
    }
}

