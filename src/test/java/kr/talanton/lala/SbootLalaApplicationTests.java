package kr.talanton.lala;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class SbootLalaApplicationTests {

	@Test
	void contextLoads() {
		log.info("contextLoads()");
	}
}