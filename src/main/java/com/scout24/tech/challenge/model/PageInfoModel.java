package com.scout24.tech.challenge.model;

import java.util.List;
import java.util.Set;


public class PageInfoModel {
	
	private String url;
	private String htmlVersion;
	private String pageTitle;
	private List<Heading> headingsGroup;
	private Set<String> links;
	private Integer	internalDomainLinkCount;
	private Integer externalDomainLinkCount;
	private Boolean	isloginForm;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHtmlVersion() {
		return htmlVersion;
	}
	public void setHtmlVersion(String htmlVersion) {
		this.htmlVersion = htmlVersion;
	}
	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
	public List<Heading> getHeadingsGroup() {
		return headingsGroup;
	}
	public Set<String> getLinks() {
		return links;
	}
	public void setLinks(Set<String> links) {
		this.links = links;
	}
	public Boolean getIsloginForm() {
		return isloginForm;
	}
	public void setHeadingsGroup(List<Heading> headingsGroup) {
		this.headingsGroup = headingsGroup;
	}
	public Integer getInternalDomainLinkCount() {
		return internalDomainLinkCount;
	}
	public void setInternalDomainLinkCount(Integer internalDomainLinkCount) {
		this.internalDomainLinkCount = internalDomainLinkCount;
	}
	public Integer getExternalDomainLinkCount() {
		return externalDomainLinkCount;
	}
	public void setExternalDomainLinkCount(Integer externalDomainLinkCount) {
		this.externalDomainLinkCount = externalDomainLinkCount;
	}
	public Boolean isIsloginForm() {
		return isloginForm;
	}
	public void setIsloginForm(Boolean isloginForm) {
		this.isloginForm = isloginForm;
	}
}
