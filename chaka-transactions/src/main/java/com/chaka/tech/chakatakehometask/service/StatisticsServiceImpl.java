package com.chaka.tech.chakatakehometask.service;

import com.chaka.tech.chakatakehometask.exception.ValueNotFoundException;
import com.chaka.tech.chakatakehometask.model.ActionType;
import com.chaka.tech.chakatakehometask.model.Statistics;
import com.chaka.tech.chakatakehometask.model.StatisticsResponse;
import com.chaka.tech.chakatakehometask.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * *  Created by Adewale Adeleye on 2020-09-02
 **/
@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final Duration START_TIME;
    private final Duration END_TIME;
    private int sampleSize;

    public StatisticsServiceImpl() {
        this.START_TIME = Duration.parse("PT0.05S");
        this.END_TIME = Duration.parse("PT60S");
        this.sampleSize = ((int) (this.END_TIME.toNanos() / this.START_TIME.toNanos())) + 1;
        this.samples = Collections.emptyMap();
    }



    private Map<Instant, Statistics> samples;
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Override
    public void removeAll() {
        samples.clear();
    }

    @Override
    public Statistics getStatistics() {
        synchronized (this) {
            return fetchStatistics();
        }
    }

    private Statistics fetchStatistics() {
        if (this.samples.size() == 0)
            return new Statistics();

        Instant now = Instant.now();
        checkSamples(now);

        return getSamplesStatistics();
    }


    private Statistics getSamplesStatistics() {
        Statistics aggregate = new Statistics();
        this.samples.forEach((key, sample) -> aggregate.add(sample));
        return aggregate;
    }


    private ActionType registerTransaction(Transaction transaction) throws ValueNotFoundException {
        Instant now = Instant.now();

        logger.debug("create transaction in map kind of storage");
        if (transactionHappensInTheFuture(transaction, now))
            throw new ValueNotFoundException("Transactions can't have timestamps of the future");

        checkSamples(now);

        if (transactionIsOutsideSamplingPeriod(transaction, now))
            return ActionType.DISCARDED;

        Instant sampleKey = getSampleKey(transaction.getTimestamp()).orElseThrow(IllegalStateException::new);
        updateStatistics(sampleKey, transaction);

        return ActionType.REGISTERED;
    }

    private boolean transactionHappensInTheFuture(Transaction transaction, Instant now) {
        return transaction.getTimestamp().isAfter(now);
    }

    private boolean transactionIsOutsideSamplingPeriod(Transaction transaction, Instant now) {
        return now.minus(this.END_TIME).isAfter(transaction.getTimestamp());
    }

    private void checkSamples(Instant now) {
        if (this.samples.size() == 0) {
            initializeSamples(now);
        } else if (outsideCurrentSamplingPeriod(now)) {
            if (allSamplesAreInvalid(now))
                initializeSamples(now);
            else
                renewSamples(now);
        }
    }
    private void initializeSamples(Instant now) {
        this.samples = new LinkedHashMap<>(sampleSize, 0.75f, false);

        logger.debug("checking initalized samples");
        Instant sampleKey = now.minus(END_TIME);
        for (int i = 0; i < this.sampleSize; i++) {
            this.samples.put(sampleKey, new Statistics());
            sampleKey = sampleKey.plus(START_TIME);
        }
    }

    private boolean outsideCurrentSamplingPeriod(Instant now) {
        return getPenultimateSampleKey()
                .map(penultimateSampleKey -> now.minus(this.END_TIME).isAfter(penultimateSampleKey))
                .orElse(false);
    }

    private Optional<Instant> getOldestSampleKey() {
        // noinspection LoopStatementThatDoesntLoop
        for (Instant sampleKey : this.samples.keySet()) {
            return Optional.of(sampleKey);
        }
        return Optional.empty();
    }

    private Optional<Instant> getPenultimateSampleKey() {
        Iterator<Instant> keys = this.samples.keySet().iterator();
        if (keys.hasNext()) {
            // Discard last key
            keys.next();
            return Optional.of(keys.next());
        } else {
            return Optional.empty();
        }
    }

    private boolean allSamplesAreInvalid(Instant now) {
        return getNewestSampleKey().map(newestSampleKey -> now.minus(this.END_TIME).isAfter(newestSampleKey))
                .orElseThrow(IllegalStateException::new);
    }

    private Optional<Instant> getNewestSampleKey() {
        Instant newestSampleKey = null;

        for (Instant sampleKey : this.samples.keySet())
            newestSampleKey = sampleKey;

        return Optional.ofNullable(newestSampleKey);
    }

    private void renewSamples(Instant now) {
        int removed = 0;

        Iterator<Instant> sampleStarts = this.samples.keySet().iterator();
        while (sampleStarts.hasNext()) {
            Instant sampleStart = sampleStarts.next();
            if (!now.minus(END_TIME).isAfter(sampleStart.plus(START_TIME)))
                break;

            sampleStarts.remove();
            removed++;
        }

        Instant newestSampleStart = getNewestSampleKey().orElseThrow(IllegalStateException::new);
        for (int i = 0; i < removed; i++) {
            newestSampleStart = newestSampleStart.plus(START_TIME);
            this.samples.put(newestSampleStart, new Statistics());
        }
    }

    private Optional<Instant> getSampleKey(Instant timestamp) {
        return getOldestSampleKey().flatMap(oldestSampleKey -> {
            long delta = Duration.between(oldestSampleKey, timestamp).toNanos();
            int sampleKeyIndex = delta != 0 ? (int) (delta / this.START_TIME.toNanos()) : 0;

            return getSampleKey(sampleKeyIndex);
        });
    }

    private Optional<Instant> getSampleKey(int index) {
        // Is the index out of range?
        if (index >= this.samples.size())
            return Optional.empty();

        Duration difference = Duration.ZERO;
        for (int i = 0; i < index; i++) {
            difference = difference.plus(this.START_TIME);
        }

        // Assign the difference to a different variable that is effectively final to
        // use it inside lambda
        Duration finalDifference = difference;

        return this.getOldestSampleKey().map(oldestSampleKey -> oldestSampleKey.plus(finalDifference));
    }

    private void updateStatistics(Instant sampleKey, Transaction transaction) {
        Statistics statistics = Optional.ofNullable(this.samples.get(sampleKey))
                .orElseThrow(IllegalArgumentException::new);
        statistics.add(transaction.getAmount());
    }

    @Override
    public ActionType addTransaction(Transaction transaction) throws ValueNotFoundException {
        synchronized (this) {
            return registerTransaction(transaction);
        }
    }

    private static String initialValue="0.00";

    @Override
    public StatisticsResponse getFormatedStartistics(Statistics statistics) {
            StatisticsResponse res = new StatisticsResponse();

            String avg = String.valueOf(statistics.getAvg());
            String max = null;
            if (statistics.getMax() != null)
                max = String.valueOf(statistics.getMax());
            String min = null;
            if (statistics.getMin() != null)
                min = String.valueOf(statistics.getMin());
            String sum = String.valueOf(statistics.getSum());
            if (max == null)
                max = "0";
            if (min == null)
                min = "0";
            String[] arr = avg.split("\\.");
            if (arr.length < 2) {
                if (avg != null)
                    avg = avg.concat(".00");
                else
                    avg = initialValue;

            }
            arr = sum.split("\\.");
            if (arr.length < 2) {
                if (sum != null)
                    sum = sum.concat(".00");
                else
                    sum = initialValue;

            }
            arr = min.split("\\.");
            if (arr.length < 2) {
                if (min != null)
                    min = min.concat(".00");
                else
                    min = initialValue;

            }
            arr = max.split("\\.");
            if (arr.length < 2) {
                if (max != null)
                    max = max.concat(".00");
                else
                    max = initialValue;
            }

            res.setAvg(avg);
            res.setSum(sum);
            res.setMax(max);
            res.setMin(min);
            res.setCount(statistics.getCount());
            return res;
    }

    public void reset() {
        synchronized (this) {
            this.samples = Collections.emptyMap();
        }
    }
}
