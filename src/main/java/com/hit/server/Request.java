package main.java.com.hit.server;


public class Request<T> extends java.lang.Object implements java.io.Serializable{
    private java.util.Map<java.lang.String,java.lang.String> headersmap=null;
    private  T body;

    Request(java.util.Map<java.lang.String,java.lang.String> headers, T body){
        this.headersmap=headers;
        this.body=body;
    }
    T getBody(){
        return this.body;
    }

    java.util.Map<java.lang.String,java.lang.String>	getHeaders() {

        return headersmap;
    }
    void setBody(T body)
    {
        this.body=body;
    }

    void setHeaders(java.util.Map<java.lang.String,java.lang.String> headers)
    {
        this.headersmap=headers;
    }

    @Override
    public String toString() {
        return "Request["+
                "headers=" +headersmap.toString()+
                ",body= "+ body.toString()+"] ;";

    }

    //  java.lang.String toString(){}


}
