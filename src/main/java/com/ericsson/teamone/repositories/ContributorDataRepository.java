package com.ericsson.teamone.repositories;

import com.ericsson.teamone.entities.ContributorData;
import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.entities.RepoData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContributorDataRepository extends CrudRepository<ContributorData, Long> {

    public List<Optional<ContributorData>> findByRepoData(RepoData repoData);

}
