package com.rss.cliente.escola.gradecurricular.model;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorMapResponse {
	
	private Map<String, String> erros;
	private int httpStatus;
	private Long timeStamp;
}
