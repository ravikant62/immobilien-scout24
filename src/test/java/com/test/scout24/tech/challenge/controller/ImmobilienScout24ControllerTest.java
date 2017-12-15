package com.test.scout24.tech.challenge.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.text.AbstractDocument.Content;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scout24.tech.challenge.controller.ImmobilienScout24Controller;
import com.scout24.tech.challenge.model.Heading;
import com.scout24.tech.challenge.model.PageInfoModel;
import com.scout24.tech.challenge.model.UrlState;

@RunWith(SpringJUnit4ClassRunner.class)
public class ImmobilienScout24ControllerTest {

	private MockMvc mockMvc;
	
	private static final String URLPARAM = "url=" + "https://github.com/login";
	
	private static final String URLINFO404	 = "link=" + "https://webmasters.stackexchange.com/notapage";

	private static final String URLINFO200	 = "link=" + "https://www.google.co.in";
		
	
	@Before
	  public void setup() {
	    this.mockMvc = MockMvcBuilders.standaloneSetup(new ImmobilienScout24Controller()).build();
	  }
	
	
	@Test
	  public void testHomePage() throws Exception {
		ResultActions resultAction =  mockMvc.perform(get("/scout24/home"));
		resultAction.andExpect(status().isOk()).andExpect(view().name("index"))
        .andDo(print());
		
	  }
	
	@Test
	  public void testHtmlInfo() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		
		PageInfoModel pageInfoModel	=	new PageInfoModel();
		pageInfoModel.setUrl("https://github.com/login");
		pageInfoModel.setHtmlVersion("HTML5");
		pageInfoModel.setPageTitle("Sign in to GitHub · GitHub");
		
		List<Heading> list = new ArrayList<Heading>();
		Heading heading1 = new Heading();
		heading1.setCount(1);
		heading1.setHeadingName("H1");
		list.add(heading1);
		
		Heading heading2	= new Heading();
		heading2.setCount(1);
		heading2.setHeadingName("H5");
		list.add(heading2);
		pageInfoModel.setHeadingsGroup(list);
		
		Set<String> links = new HashSet<String>();
		
		links.add("https://github.com/join?source=login");
		links.add("https://github.com/contact");
		links.add("https://github.com/login#start-of-content");
		links.add("https://github.com/");
		links.add("https://github.com/login");
		links.add("https://chrome.google.com");
		links.add("https://mozilla.org/firefox/");
		links.add("https://github.com/password_reset");
		links.add("https://github.com/site/terms");
		links.add("https://help.github.com/articles/supported-browsers");
		links.add("https://github.com/site/privacy");
		links.add("https://github.com/security");
		
		pageInfoModel.setLinks(links);
		
		pageInfoModel.setInternalDomainLinkCount(9);
		pageInfoModel.setExternalDomainLinkCount(3);
		pageInfoModel.setIsloginForm(true);
		
		mockMvc
        .perform(get("/scout24/getHtmlInfo?" + URLPARAM))
        .andExpect(status().isOk())
        .andExpect(content().string(mapper.writeValueAsString(pageInfoModel)));
		
	  }
	
	
	  @Test
	  public void testUnreachableLinkState404() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		UrlState urlState	=	new UrlState();
		urlState.setUrl("https://webmasters.stackexchange.com/notapage");
		urlState.setStatusCode(404);
		urlState.setIsReachable(false);
		urlState.setStatusMessage("Not Found");
		
		
		mockMvc
        .perform(get("/scout24/fetch/unreachable/link?" + URLINFO404))
        .andExpect(status().isOk())
        .andExpect(content().string(mapper.writeValueAsString(urlState)));
	}
	
	@Test
	  public void testUnreachableLinkState200() throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		UrlState urlState	=	new UrlState();
		urlState.setUrl("https://www.google.co.in");
		urlState.setStatusCode(200);
		urlState.setIsReachable(true);
		urlState.setStatusMessage("OK");
		
		
		mockMvc
      .perform(get("/scout24/fetch/unreachable/link?" + URLINFO200))
      .andExpect(status().isOk())
      .andExpect(content().string(mapper.writeValueAsString(urlState)));
	}
	
	
	
}
