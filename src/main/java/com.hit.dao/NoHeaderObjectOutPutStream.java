package main.java.com.hit.dao;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class NoHeaderObjectOutPutStream extends ObjectOutputStream {

    public NoHeaderObjectOutPutStream(OutputStream out) throws IOException {
        super(out);
    }

    @Override
    protected void writeStreamHeader() throws IOException {

        //super.writeStreamHeader();
    }
}