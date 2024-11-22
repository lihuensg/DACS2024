package utn.dacs.ms.bff.api.client;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import utn.dacs.ms.bff.BaseIntegrationTest;

@AutoConfigureMockMvc
class MsBffControllerTest extends BaseIntegrationTest {

	private final String baseUrl = "/";

	@Autowired
	public MockMvc mockMvc;
	
	@Test
	void shouldReturnPingOk() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(this.baseUrl + "ping").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello from DACS MS BFF ping"));
	}
	
	@Test
	void shouldReturnConectorPingOk() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(this.baseUrl + "conector/ping").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello from DACS MS CONECTOR ping"));
	}
	
	@Test
	void shouldReturnBackendPingOk() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get(this.baseUrl + "backend/ping").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello from DACS MS BACKEND ping"));
	}
	
}
