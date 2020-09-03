package com.chaka.tech.chakatakehometask;


import com.chaka.tech.chakatakehometask.controller.StatisticsController;
import com.chaka.tech.chakatakehometask.model.Statistics;
import com.chaka.tech.chakatakehometask.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * @author MiguelAraCo
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StatisticsController.class)
@ContextConfiguration(classes = { ChakaTakehomeTaskApplication.class })
public class StatisticsControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private StatisticsService statisticsService;

	@Test
	public void returns200WithResponse() throws Exception {
		Statistics statistics = new Statistics();
		statistics.setMax(new BigDecimal("200000.49"));
		statistics.setMin(new BigDecimal("50.23"));
		statistics.setSum(new BigDecimal("1000.00"));
		statistics.setCount(10L);
		statistics.setAvg(new BigDecimal("100.53"));

		Mockito.when(statisticsService.getStatistics()).thenReturn(statistics);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/statistics").accept(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
		assertEquals("Returned an invalid status code.", 200, response.getStatus());

		String expected = "" + "{" + "   max: 200000.49," + "   min: 50.23," + "   sum: 1000.0," + "   count: 10,"
				+ "   avg: 100.53" + "}";

		JSONAssert.assertNotEquals("get: /statistics didn't return the expected response", expected, response.getContentAsString(),
				false);
	}

}
