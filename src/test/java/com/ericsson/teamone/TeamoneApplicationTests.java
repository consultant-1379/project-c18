package com.ericsson.teamone;

import com.ericsson.teamone.repositories.RepoDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamoneApplicationTests {

	@Autowired
	private RepoDataRepository repoDataRepository;

	@Test
	void contextLoads() {
		assertTrue(repoDataRepository != null);

	}


}
