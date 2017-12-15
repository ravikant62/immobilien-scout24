package com.scout24.tech.challenge.model;

public class UrlState {

	private String url;
	private Integer statusCode;
	private Boolean isReachable;
	private String statusMessage;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public Boolean getIsReachable() {
		return isReachable;
	}
	public void setIsReachable(Boolean isReachable) {
		this.isReachable = isReachable;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
}
