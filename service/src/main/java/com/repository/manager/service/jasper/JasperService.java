package com.repository.manager.service.jasper;

public interface JasperService {
	byte[] exportFile(String authorizationToken, String format) throws Exception;
}
