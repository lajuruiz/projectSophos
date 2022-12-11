package com.example.projectSophos;

import com.example.projectSophos.controllers.TestsController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProjectSophosApplicationTests {

	@Autowired
	private TestsController testController;
	@Autowired
	private TestsController affiliateController;
	@Autowired
	private TestsController appointmentsController;


	@Test
	void contextLoads() {
		Assertions.assertThat(testController).isNotNull();
		Assertions.assertThat(affiliateController).isNotNull();
		Assertions.assertThat(appointmentsController).isNotNull();
	}

}
