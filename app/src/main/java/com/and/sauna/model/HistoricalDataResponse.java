package com.and.sauna.model;

import java.util.List;
public class HistoricalDataResponse{

    private class SaunaBit {
        String saunaName;
        long id;
        long timestamp;
        float temp;
        float co2;
        float humidity;
    }
}

