package com.tailora.security.service;

public interface TokenBlackList {
	void addToBlacklist(String token);
    boolean isBlacklisted(String token);
}
