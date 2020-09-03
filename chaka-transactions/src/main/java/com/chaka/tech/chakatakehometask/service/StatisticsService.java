package com.chaka.tech.chakatakehometask.service;

import com.chaka.tech.chakatakehometask.exception.ValueNotFoundException;
import com.chaka.tech.chakatakehometask.model.ActionType;
import com.chaka.tech.chakatakehometask.model.Statistics;
import com.chaka.tech.chakatakehometask.model.StatisticsResponse;
import com.chaka.tech.chakatakehometask.model.Transaction;

/**
 * *  Created by Adewale Adeleye on 2020-09-02
 **/
public interface StatisticsService {
    void removeAll();
    Statistics getStatistics();
    ActionType addTransaction(Transaction transaction) throws ValueNotFoundException;
    StatisticsResponse getFormatedStartistics(Statistics statistics);
}
