package utn.dacs.ms.conector;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import utn.dacs.ms.conector.dto.BuildInfoDTO;
import utn.dacs.ms.conector.dto.GitInfoDTO;

@Configuration
@NoArgsConstructor
public class MsDacsConectorConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MsDacsConectorConfiguration.class);

	final ObjectMapper mapper = new ObjectMapper();
    
    private static final String GIT_PROPERTIES_FILE = "git.properties";

    @Bean(name = "buildInfo")
    public BuildInfoDTO getBuildInfo(@Value("${application.name}") String appName) {
		BuildInfoDTO versionInfo = new BuildInfoDTO();
		versionInfo.setApplicationName(appName);
		try {
			InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(GIT_PROPERTIES_FILE);
			
			GitInfoDTO gitInfo = mapper.readValue(is, GitInfoDTO.class);
			
			versionInfo.setVersion(gitInfo.getBuildVersion());
			versionInfo.setLastBuild(gitInfo.getBuildTime());
			
			String branchWithCommitId = String.format("%s %s",gitInfo.getCommitIdAbbrev(),gitInfo.getBranch());
			versionInfo.setBranchWithCommitId(branchWithCommitId);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return versionInfo;
	}
}
