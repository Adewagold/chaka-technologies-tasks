package com.chaka.tech.chakatakehometask.controller;

import com.chaka.tech.chakatakehometask.model.Statistics;
import com.chaka.tech.chakatakehometask.model.StatisticsResponse;
import com.chaka.tech.chakatakehometask.service.StatisticsService;
import com.chaka.tech.chakatakehometask.util.FormatStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * *  Created by Adewale Adeleye on 2020-09-02
 **/
@RestController
public class StatisticsController {

    StatisticsService statisticsService;
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "/statistics", produces = "application/json")
    public ResponseEntity<StatisticsResponse> getLatestStatistics() {
        Statistics statistics = statisticsService.getStatistics();
        logger.debug("fetching all statistics for last 60 seconds");
        StatisticsResponse response = FormatStatistics.formStatisticsResponse(statistics);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
