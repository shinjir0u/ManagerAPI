package com.repository.manager.service.jasper;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface JasperService {
	byte[] exportFile(List<?> data, String format, String jrxmlFileName) throws Exception;
}
