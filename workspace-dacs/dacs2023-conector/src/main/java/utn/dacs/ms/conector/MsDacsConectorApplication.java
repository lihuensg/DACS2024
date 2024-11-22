package utn.dacs.ms.conector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsDacsConectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDacsConectorApplication.class, args);
	}

}
