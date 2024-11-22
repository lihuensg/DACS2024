package utn.dacs.ms.backend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsDacBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDacBackendApplication.class, args);
	}
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
