package utn.dacs.ms.bff.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import utn.dacs.ms.bff.dto.BuildInfoDTO;
import utn.dacs.ms.bff.dto.ItemDto;
import utn.dacs.ms.bff.service.MsApiConectorService;

@RestController
@RequestMapping("/conector")
@Slf4j
public class MsConectorController {

    @Autowired
    private MsApiConectorService apiConectorService;

    @GetMapping("/ping")
    public String ping() {
        return apiConectorService.ping();
    }
    
    @GetMapping("/version")
    public BuildInfoDTO version() {
        return apiConectorService.version();
    }
    
//    @GetMapping("/items")
//    public List<ItemDto> getItems() {
//        return apiConectorService.getItems();
//    }
   
}
