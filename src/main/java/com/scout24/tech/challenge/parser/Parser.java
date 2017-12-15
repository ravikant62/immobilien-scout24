package com.scout24.tech.challenge.parser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.scout24.tech.challenge.exception.URLException;
import com.scout24.tech.challenge.model.Heading;

public class Parser {
	
	public static boolean getForms ( Document page ) {

		boolean isIdFieldPresent		=	false;
		boolean isPasswordFieldPresent	=	false;
		boolean isSubmitButtonPresent	=	false;
		boolean isLoginForm				=	false;

		Elements	elements	=	page.select("form");
		for( Element element : elements){
			
			Elements allInputFields = element.getElementsByTag("input");
			Elements allButtonFields = element.getElementsByTag("button");
			
			for( Element inputField : allInputFields){
				if( inputField.attr("name").toLowerCase().contains("id") ||  
						inputField.attr("name").toLowerCase().contains("email") || 
						inputField.attr("name").toLowerCase().contains("login") ){
					isIdFieldPresent	=	true;
				}else if( inputField.attr("name").toLowerCase().contains("password") ||  
						inputField.attr("type").toLowerCase().contains("password") ){
					isPasswordFieldPresent	=	true;
				}else if( inputField.attr("value").toLowerCase().contains("submit") ||  
						inputField.attr("type").toLowerCase().contains("submit") ){
					isSubmitButtonPresent	=	true;
				}
				
				for( Element button : allButtonFields ){
					if ( button.attr("value").toLowerCase().contains("login") ||
						button.attr("value").toLowerCase().contains("submit") ||
						button.attr("type").toLowerCase().contains("submit") ){
					}
				}
				
				if( isIdFieldPresent && isPasswordFieldPresent && isSubmitButtonPresent ){
					isLoginForm = true;
					break;
				}
			}
		}
		return isLoginForm;
	}
	
	
	public static void getLinks(Document page, String hostName, Set<String> internalLinks, Set<String> externalLinks) throws MalformedURLException {

		Elements links = page.select("a[href]");

		for( Element link : links){
			
			String str	=	link.attr("abs:href");
			// if href javascript:void(0), it does not return href
			if( str.length() > 0 ){
				URL url	=	new URL(str);

				if( url.getHost().equals(hostName)){
					internalLinks.add(str);
					System.out.println("*****Internal links"+str);
				}else{
					System.out.println("*****External links"+str);
					externalLinks.add(str);
				}
			}
		}
	}
	

	public static void getHeadingTags(Document page, List<Heading> headingTagsCount) {


		Elements h1Tags	=	page.select("h1");
		Elements h2Tags	=	page.select("h2");
		Elements h3Tags	=	page.select("h3");
		Elements h4Tags	=	page.select("h4");
		Elements h5Tags	=	page.select("h5");
		Elements h6Tags	=	page.select("h6");
		

		if( h1Tags.size()>0){setHeadings("H1", h1Tags.size(), headingTagsCount);}
		if( h2Tags.size()>0){setHeadings("H2", h2Tags.size(), headingTagsCount);}
		if( h3Tags.size()>0){setHeadings("H3", h3Tags.size(), headingTagsCount);}
		if( h4Tags.size()>0){setHeadings("H4", h4Tags.size(), headingTagsCount);}
		if( h5Tags.size()>0){setHeadings("H5", h5Tags.size(), headingTagsCount);}
		if( h6Tags.size()>0){setHeadings("H6", h6Tags.size(), headingTagsCount);}
	}
	
	
	private static void setHeadings(String headingName, Integer count, List<Heading> headingList){
		Heading heading	=	new Heading();
		heading.setHeadingName(headingName);
		heading.setCount(count);
		headingList.add(heading);
	}
	
	public static String getPageTitle ( String title ){
		
		if( title.trim().length() == 0 ){
			title	=	" No Page Title present ";
		}
		return removeHtmlTags(title);
	}
	
	
	public static String generateHtmlVersion(Node node) { 
		DocumentType documentType = (DocumentType) node; 
		String htmlVersion = documentType.attr("publicid"); 
		return "".equals(htmlVersion) ? "HTML5" : htmlVersion; }
	
	public static boolean validateResponse ( Response response) {
		
		if( response.statusCode()!= 200 ){
			return false;
		}
		return true;
	}
	
	
	private static String removeHtmlTags( String content){
		
		if( content.toLowerCase().contains("<td>")){
			return content.replace("<td>", "");
		}else if( content.toLowerCase().contains("<th>")){
			return content.replace("<th>", "");
		}else if( content.toLowerCase().contains("<tr>")){
			return content.replace("<tr>", "");
		}else {
			return content;
		}
	}
}
