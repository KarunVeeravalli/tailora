package com.tailora.common;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Header implements Serializable {

	private static final long serialVersionUID = 1L;

	private String channel; // mobile or web
	private String deviceId;
	private String locale; // en_us
	private String trackingId; // uuid
	private String timestamp;
	private String browserDetails;
	private String browserVersion;

}
