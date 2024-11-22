package utn.dacs.ms.conector.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class GitInfoDTO {

	@JsonProperty("git.branch")
	String branch;

	@JsonProperty("git.build.host")
	String buildHost; 
	@JsonProperty("git.build.time")
	String buildTime; 
	@JsonProperty("git.build.user.email") 
	String buildUserEmail;

	@JsonProperty("git.build.user.name") 
	String buildUsername;

	@JsonProperty("git.build.version")
	String buildVersion;

	@JsonProperty("git.closest.tag.commit.count") 
	String closestTagCommitCount;

	@JsonProperty("git.closest.tag.name") 
	String closestTagName;

	@JsonProperty("git.commit.id") 
	String commitId;

	@JsonProperty("git.commit.id.abbrev") 
	String commitIdAbbrev;

	@JsonProperty("git.commit.id.describe") 
	String commitIdDescribe;

	@JsonProperty("git.commit.id.describe-short") 
	String commitIdDescribeShort;

	@JsonProperty("git.commit.message.full") 
	String commitMessageFull;

	@JsonProperty("git.commit.message.short") 
	String commitMessageShort;

	@JsonProperty("git.commit.time") 
	String commitTime;

	@JsonProperty("git.commit.user.email") 
	String commitUserEmail;

	@JsonProperty("git.commit.user.name") 
	String commitUsername;

	@JsonProperty("git.dirty") 
	String dirty;

	@JsonProperty("git.remote.origin.url") 
	String remoteOriginUrl;

	@JsonProperty("git.tags") 
	String tags;
}
