// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.wangchang.testbottomnavigationbar.enity;

import java.util.List;

public class GankBeautyResult {
    public boolean error;
    private List<ResultsEntity> results;

    public void setError(boolean error) {
        this.error = error;
    }

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public boolean isError() {
        return error;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }



}
