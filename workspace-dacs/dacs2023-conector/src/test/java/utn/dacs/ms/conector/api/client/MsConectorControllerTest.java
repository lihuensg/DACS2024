package utn.dacs.ms.conector.api.client;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import utn.dacs.ms.conector.BaseIntegrationTest;

@AutoConfigureMockMvc
class MsConectorControllerTest extends BaseIntegrationTest {

	private final String baseUrl = "/";

	@Autowired
	public MockMvc mockMvc;
	
	@Test
	void shouldReturnPingOk() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(this.baseUrl + "ping").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello from RUS MS PORTAL PROVEEDORES BFF ping"));
	}

	@Test
	void shouldReturnCrmPingOk() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(this.baseUrl + "crm/ping").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello from RUS API-CRM - ping"));
	}
	
}
