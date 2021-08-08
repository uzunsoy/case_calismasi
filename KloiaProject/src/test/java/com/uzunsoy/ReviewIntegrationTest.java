package com.uzunsoy;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.uzunsoy.model.Review;
import com.uzunsoy.services.ReviewService;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { KloiaProjectApplication.class})
@WebAppConfiguration
@AutoConfigureMockMvc
@SpringBootTest
public class ReviewIntegrationTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();
	private Review review = new Review();

	
	@MockBean 
	private ReviewService reviewServiceMock;
 
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void reviewControllerTest() {
		ServletContext servletContext = webApplicationContext.getServletContext();

		Assert.assertNotNull(servletContext);
		Assert.assertTrue(servletContext instanceof MockServletContext);
		Assert.assertNotNull(webApplicationContext.getBean("reviewController"));
	}

	@Test
	@WithMockUser(username = "user", password = "12346", roles = "USER")
	public void reviewGetTest() throws Exception {
		
		 Review review = new Review(12345L,"Test1");
		 when(reviewServiceMock.findById(12345L)).thenReturn(review);
		
		
		  mockMvc.perform(get("/rest/reviews/12345")
		           .contentType(MediaType.APPLICATION_JSON)		           
		           .accept(MediaType.APPLICATION_JSON))		  		   
		  		   .andExpect(status().isOk()); 
		  		   
		
	}
	
	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void reviewPostTest() throws Exception {
		
		 Review review = new Review(2L,"Ornek 1","Ted");
		 when(reviewServiceMock.create(review)).thenReturn(review);
	  
		 
		 
		  mockMvc.perform(post("/rest/reviews")
		           .contentType(MediaType.APPLICATION_JSON)	
		           .content("{\r\n"
		           		+ "    \"reviewContent\": \"Ornek 1\",\r\n"
		           		+ "    \"reviewer\": \"İçerik 1\",\r\n"
		           		+ "    \"articleId\": \"2\"\r\n"
		           		+ "}") 
		           .accept(MediaType.APPLICATION_JSON)) 
		  		   .andExpect(status().isOk());
		  		   
		
	}
	
	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void reviewPutTest() throws Exception {
		
		 Review review = new Review("Ornek 1","Ted");
		 when(reviewServiceMock.create(review)).thenReturn(review);
		
		
		  mockMvc.perform(put("/rest/reviews/2")
		           .contentType(MediaType.APPLICATION_JSON)	
		           .content("{\r\n"
			           		+ "    \"reviewContent\": \"Ornek 1\",\r\n"
			           		+ "    \"reviewer\": \"İçerik 1\",\r\n"
			           		+ "}") 
		           .accept(MediaType.APPLICATION_JSON));
		  		   
		
	}
 
	@Test
	@WithMockUser(username = "user", password = "{noop}12346", roles = "USER")
	public void reviewDeleteTest() throws Exception {
		
		 Review review = new Review("Ornek 1","Ted");
		 when(reviewServiceMock.create(review)).thenReturn(review);
		
		
		  mockMvc.perform(delete("/rest/reviews/2")
		           .contentType(MediaType.APPLICATION_JSON)		            
		           .accept(MediaType.APPLICATION_JSON));
		  		   
		
	}
	
}
