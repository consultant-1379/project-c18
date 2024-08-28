package com.ericsson.teamone.repositories;

import com.ericsson.teamone.entities.RepoData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepoDataRepository extends CrudRepository<RepoData, Long> {

    @Query("select rd from RepoData rd where repUrl= ?1 and dateRange= ?2")
    public Optional<RepoData> findByUrlAndDateRange(String url, String dateRange);


}
