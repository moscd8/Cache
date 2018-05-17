package main.java.com.hit.dao;

import java.io.*;

public class NoHeaderObjectInPutStream extends ObjectInputStream {


    public NoHeaderObjectInPutStream(InputStream in) throws IOException {

        super(in);
    }

    @Override
    protected void readStreamHeader() throws IOException, StreamCorruptedException {
      //  super.readStreamHeader();
    }

}
