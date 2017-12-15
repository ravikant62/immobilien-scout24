package com.scout24.tech.challenge.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scout24.tech.challenge.exception.URLException;
import com.scout24.tech.challenge.model.Heading;
import com.scout24.tech.challenge.model.PageInfoModel;
import com.scout24.tech.challenge.model.UrlState;
import com.scout24.tech.challenge.parser.Parser;

@Controller
public class ImmobilienScout24Controller {
	
	
	@RequestMapping(method=RequestMethod.GET, value="/scout24/home")
	 public String displayHomePage ( ) {
		return "index";
	 }
	
	@RequestMapping(method=RequestMethod.GET, value="/scout24/getHtmlInfo")
	@ResponseBody
	public PageInfoModel getHtmlInfo(@RequestParam("url") String url, Model model) throws Exception  {

		System.out.println(" Inside getHTMLINFO ");
		PageInfoModel	pageInfoModel	=	new PageInfoModel();
		try{
			Response response;
			response = Jsoup.connect(url)
					.timeout(100000)
					.ignoreHttpErrors(true)
					.ignoreContentType(true)
					.execute();
			
			
			if( !Parser.validateResponse( response ) ){
				
				int statusCode			=	response.statusCode();
				String statusMessage	=	response.statusMessage();
				
				throw new URLException(url, statusCode, statusMessage);
			}
			
			
			Document page		=	Jsoup.connect(url).
					userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
					.ignoreHttpErrors(true)
					.ignoreContentType(true)
					.get();
			URL aURL = new URL(url);

			System.out.println("Host name"+aURL.getHost());

			List<Node> nodes = page.childNodes(); 

			List<Heading>	headingTagsCount	=	new ArrayList<Heading>();

			Set<String> internalLinks	=	new HashSet<String>();
			Set<String> externalLinks	=	new HashSet<String>();

			Boolean	isLoginForm		=	false;

			String title	=	Parser.getPageTitle(page.title());

			if( title.trim().length() == 0 ){
				title	=	" No Page Title present ";
			}

			String htmlVersion	=	
					(nodes.stream().filter
							(node -> node instanceof DocumentType).map
							(Parser::generateHtmlVersion) .collect
							(Collectors.joining()));

			if( htmlVersion.trim().length() == 0 ){
				htmlVersion	=	"HTML 5";
			}



			Parser.getHeadingTags(page, headingTagsCount);

			Parser.getLinks(page, aURL.getHost(), internalLinks, externalLinks);

			isLoginForm	=	Parser.getForms(page);

			pageInfoModel.setUrl(url);
			pageInfoModel.setPageTitle(title);
			pageInfoModel.setHtmlVersion(htmlVersion);
			pageInfoModel.setHeadingsGroup(headingTagsCount);
			pageInfoModel.setInternalDomainLinkCount(internalLinks.size());
			pageInfoModel.setExternalDomainLinkCount(externalLinks.size());
			pageInfoModel.setLinks(internalLinks);
			pageInfoModel.getLinks().addAll(externalLinks);
			pageInfoModel.setIsloginForm(isLoginForm);
		}catch(URLException e){
			throw new URLException(e.getUrl(), e.getErrorCode(),e.getMessage());
		}
		catch ( Exception e){
			throw e;
		}

		return pageInfoModel;
	}

	@RequestMapping(method=RequestMethod.GET, value="/scout24/fetch/unreachable/link")
	@ResponseBody
	public UrlState fetchUnreachableLinkState(@RequestParam("link") String url, Model model)  {
		
		Response response;
		UrlState urlState;
		try{
			response = Jsoup.connect(url).followRedirects(true)
					.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
					.timeout(100000)
					.ignoreHttpErrors(true)
					.ignoreContentType(true)
					.execute();
			
			urlState =	new UrlState();
			urlState.setUrl(url);
			urlState.setStatusCode(response.statusCode());
			urlState.setStatusMessage(response.statusMessage());
			urlState.setIsReachable(true);	
			
			if( response.statusCode() != 200 ){
				urlState.setIsReachable(false);
				return urlState;
			}
			
		}catch ( Exception e){
			System.out.println( e );
			throw new URLException( e.getMessage() );
		}
		
		return urlState;
	}
}
