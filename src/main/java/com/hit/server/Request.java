package main.java.com.hit.server;

import java.io.Serializable;
import java.util.Map;

public class Request<T> extends Object implements Serializable {
    private Map<String, String> headers;
    private T body;

    Request(Map<String, String> headers, T body) {
        this.headers = headers;
        this.body = body;
    }

    T getBody() {
        return this.body;
    }

    Map<String, String> getHeaders() {
        return headers;
    }

    void setBody(T body) {
        this.body = body;
    }

    void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public String toString() {
        return "Request[" + "headers=" + headers.toString() + ",body= " + body.toString() + "] ;";
    }
}
