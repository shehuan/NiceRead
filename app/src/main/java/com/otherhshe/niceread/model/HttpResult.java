package com.otherhshe.niceread.model;

/**
 * Author: Othershe
 * Time:  2016/8/11 13:34
 */
public class HttpResult<T> {
    private boolean error;
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
