package utn.dacs.ms.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import utn.dacs.ms.bff.api.client.MsApiConectorClient;
import utn.dacs.ms.bff.dto.BuildInfoDTO;
import utn.dacs.ms.bff.dto.ItemDto;
import utn.dacs.ms.bff.exceptions.BffException;
import utn.dacs.ms.bff.exceptions.ErrorEnum;

@Service
@Slf4j
public class MsApiConectorService {

	@Autowired
    private MsApiConectorClient msApiConectorClient;
	
	
    public String ping() {
        try {
            return this.msApiConectorClient.ping();
        } catch (Exception e) {
           log.error("Error producido al solicitar un recurso a /conector/ping", e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }
    
    public BuildInfoDTO version() {
        try {
            return this.msApiConectorClient.version();
        } catch (Exception e) {
            log.error("Error producido al solicitar un recurso a /conector/version", e);
            throw new BffException(ErrorEnum.ERROR_API);
        }
    }    
    
//    public List<ItemDto> getItems() {
//        try {
//            return this.msApiConectorClient.todos();
//        } catch (Exception e) {
//            log.error("Error producido al solicitar un recurso a /conector/todos", e);
//            throw new BffException(ErrorEnum.ERROR_API);
//        }
//    } 
}
