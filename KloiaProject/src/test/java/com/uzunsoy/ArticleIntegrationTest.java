package com.uzunsoy;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uzunsoy.model.Article;
import com.uzunsoy.services.ArticleService;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { KloiaProjectApplication.class})
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
public class ArticleIntegrationTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();
	private Article article = new Article();

	
	@MockBean 
	private ArticleService articleServiceMock;
 
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void articleControllerTest() {
		ServletContext servletContext = webApplicationContext.getServletContext();

		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(webApplicationContext.getBean("articleController"));
	}

	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void articleGetTest() throws Exception {
		
		 Article article = new Article(12345L,"Test1","Test",7);
		 when(articleServiceMock.findArticleByIdDynamic(12345L)).thenReturn(article);
		
		
		  mockMvc.perform(get("/rest/articles/12345")
		           .contentType(MediaType.APPLICATION_JSON)		           
		           .accept(MediaType.APPLICATION_JSON))		  		   
		  		   .andExpect(status().isOk());
		  		   
		
	}
	
	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void articlePostTest() throws Exception {
		
		 Article article = new Article("Ornek 1","İçerik 1",6);
		 when(articleServiceMock.create(article)).thenReturn(article);
		
		
		  mockMvc.perform(post("/rest/articles")
		           .contentType(MediaType.APPLICATION_JSON)	
		           .content("{\r\n"
		           		+ "    \"title\": \"Ornek 1\",\r\n"
		           		+ "    \"articleContent\": \"İçerik 1\",\r\n"
		           		+ "    \"starCount\": 6\r\n"
		           		+ "}") 
		           .accept(MediaType.APPLICATION_JSON)) 
		  		   .andExpect(status().isOk());
		  		   
		
	}
	
	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void articlePutTest() throws Exception {
		
		 Article article = new Article(2L,"Ornek 1","İçerik 1",6);
		 when(articleServiceMock.create(article)).thenReturn(article);
		
		
		  mockMvc.perform(put("/rest/articles/2")
		           .contentType(MediaType.APPLICATION_JSON)	
		           .content("{\r\n"
		           		+ "    \"title\": \"Ornek 2\",\r\n"
		           		+ "    \"articleContent\": \"İçerik 1\",\r\n"
		           		+ "    \"starCount\": 6\r\n"
		           		+ "}") 
		           .accept(MediaType.APPLICATION_JSON));
		  		   
		
	}
 
	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void articleDeleteTest() throws Exception {
		
		 Article article = new Article(2L,"Ornek 1","İçerik 1",6);
		 when(articleServiceMock.create(article)).thenReturn(article);
		
		
		  mockMvc.perform(delete("/rest/articles/2")
		           .contentType(MediaType.APPLICATION_JSON)		            
		           .accept(MediaType.APPLICATION_JSON));
		  		   
		
	}
	
}
