package com.tailora.dto.request;

import java.io.Serializable;

import com.tailora.common.Header;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Request<T> implements Serializable{

	private static final long serialVersionUID = 6541910013577175536L;
	
	private Header requestHeader;
	
	private T requestBody;
}
