package com.ericsson.teamone.repositories;

import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.entities.RepoData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PerFileDataRepository extends CrudRepository<PerFileData, Long> {

    public List<Optional<PerFileData>> findByRepoData(RepoData repoData);

}
