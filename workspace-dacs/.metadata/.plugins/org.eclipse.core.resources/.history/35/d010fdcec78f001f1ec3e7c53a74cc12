package utn.dacs.ms.conector.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(
			name = "apiClient", 
			url = "${feign.client.config.apiClient.url}"
			)

public interface ApiClient {

    @GetMapping("/todos")
    <ItemDto> List<ItemDto> todos();
    
    
}
