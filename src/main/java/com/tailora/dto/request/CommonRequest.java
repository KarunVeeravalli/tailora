package com.tailora.dto.request;

import java.time.LocalDateTime;

import com.tailora.common.Header;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonRequest {

	private Header requestHeader;
	
	public String requestedDateTime = LocalDateTime.now().toString();
	
	public Long userId;
	
//	public String username = RepoHelper.isLoggedIn()?RepoHelper.getUser()!=null?RepoHelper.getUser().getUsername():null:null;
	
//	public String jwt = RepoHelper.isLoggedIn()?RepoHelper.getUser()!=null?RepoHelper.getUser().getJwtToken():null:null;
	
	public Long id;
}
