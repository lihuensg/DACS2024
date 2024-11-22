package utn.dacs.ms.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utn.dacs.ms.backend.configuration.ApplicationContextProvider;

@RestController
@RequestMapping(value = "/")
public class HomeController {
	
	
	@GetMapping(value = "/ping")
    public Object ping() {
        return "Hello from DACS MS BACKEND ping";
    }

	@GetMapping(value = "/version")
    public Object version() {
        return ApplicationContextProvider.getApplicationContext().getBean("buildInfo");
    }

}
