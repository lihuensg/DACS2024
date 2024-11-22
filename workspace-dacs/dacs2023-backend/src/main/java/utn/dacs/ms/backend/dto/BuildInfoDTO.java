package utn.dacs.ms.backend.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildInfoDTO {
	String applicationName;
	String version;
	String lastBuild;
	String branchWithCommitId;
	public void setApplicationName(String appName) {
		// TODO Auto-generated method stub
		
	}
}
