package com.ericsson.teamone;


import com.ericsson.teamone.entities.PerFileData;
import com.ericsson.teamone.entities.RepoData;
import com.ericsson.teamone.repositories.PerFileDataRepository;
import com.ericsson.teamone.repositories.RepoDataRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.ericsson.teamone.services.WriterService;

@SpringBootApplication
public class TeamoneApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TeamoneApplication.class, args);

		WriterService service = ctx.getBean(WriterService.class);
		service.start();

	}

}
