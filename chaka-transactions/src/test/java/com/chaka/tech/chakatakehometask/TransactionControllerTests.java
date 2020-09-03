package com.chaka.tech.chakatakehometask;

import com.chaka.tech.chakatakehometask.controller.TransactionController;
import com.chaka.tech.chakatakehometask.exception.ValueNotFoundException;
import com.chaka.tech.chakatakehometask.model.ActionType;
import com.chaka.tech.chakatakehometask.service.StatisticsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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

import static org.junit.Assert.assertEquals;

/**
 * *  Created by Adewale Adeleye on 2020-09-03
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TransactionController.class)
@ContextConfiguration(classes = { ChakaTakehomeTaskApplication.class })
public class TransactionControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StatisticsService statisticsService;

    @Test
    public void returns201WhenTransactionIsSuccessful() throws Exception, ValueNotFoundException {
        Mockito.when(statisticsService.addTransaction(Mockito.any())).thenReturn(ActionType.REGISTERED);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" + "{" + "   \"amount\": 12.65,\n" + "   \"timestamp\": 1529822905186\n" + "}");

        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals("Returned an invalid status code.", 201, response.getStatus());
    }

    @Test
    public void returns204WhenTransactionIsNotSuccessful() throws Exception, ValueNotFoundException {
        Mockito.when(statisticsService.addTransaction(Mockito.any())).thenReturn(ActionType.DISCARDED);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" + "{" + "   \"amount\": 12.65,\n" + "   \"timestamp\": 1529822905186\n" + "}");

        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals("Returned an invalid status code.", 204, response.getStatus());
    }

    @Test
    public void returns400WhenIllegalArgumentExceptionOccurs() throws Exception, ValueNotFoundException {
        Mockito.when(statisticsService.addTransaction(Mockito.any())).thenThrow(new IllegalArgumentException());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("" + "{" + "   \"amount\": 12.3343,,\n" + "   \"timestamp\": 1529822905186\n" + "}");

        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals("Returned an invalid status code.", 400, response.getStatus());
    }

    @Test
    public void returns400OnMissingParameters() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON).content("" + "{" + "   \"amount\": 12.3343,\n" + "}");

        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals("Returned an invalid status code.", 400, response.getStatus());

        requestBuilder = MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON)
                .content("" + "{" + "   \"timestamp\": 1529822905186\n" + "}");

        response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals("Returned an invalid status code.", 400, response.getStatus());
    }

    @Test
    public void returns422OnJsonPropertyNotParseable() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON).content("" + "{" + "   \"amount\": \"hhh\",\n"
                        + "   \"timestamp\": 1529822905186\n" + "}");

        MockHttpServletResponse response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals("Returned an invalid status code.", 422, response.getStatus());

        requestBuilder = MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON).content(
                "" + "{" + "   \"amount\": 12.3343,\n" + "   \"timestamp\": \"super-invalid-timestamp\"\n" + "}");

        response = mvc.perform(requestBuilder).andReturn().getResponse();
        assertEquals("Returned an invalid status code.", 422, response.getStatus());
    }
}
