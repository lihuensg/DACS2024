package utn.dacs.ms.bff.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import utn.dacs.ms.bff.configuration.ApplicationContextProvider;

@RestController
@RequestMapping(value = "/")
public class HomeController {
	
	@GetMapping(value = "/ping")
    public Object ping() {
        return "Hello from DACS MS BFF ping";
    }

	@GetMapping(value = "/version")
    public Object version() {
        return ApplicationContextProvider.getApplicationContext().getBean("buildInfo");
    }

	@GetMapping(value = "/prueba/ping")
    public Object ping1() {
        return "Hello from DACS MS BFF ping";
    }
	
}
