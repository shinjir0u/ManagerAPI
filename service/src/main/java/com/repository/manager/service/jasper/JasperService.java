package com.repository.manager.service.jasper;

import java.util.List;

public interface JasperService {
	byte[] exportFile(List<?> data, String format, String jrxmlFileName) throws Exception;
}
