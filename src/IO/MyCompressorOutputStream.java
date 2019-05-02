package IO;


import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream outputStream) {
        out = outputStream;
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        //TODO: append to out a phrase after compression.
    }

    @Override
    public void write(int i) throws IOException {
        byte[] bytes = BigInteger.valueOf(i).toByteArray();
        write(bytes);
    }

}
