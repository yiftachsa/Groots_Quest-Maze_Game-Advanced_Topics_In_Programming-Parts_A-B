package IO;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream outputStream) {
        out = outputStream;
    }

    @Override
    public void write(byte[] bytes) throws IOException {
        //TODO: append to out a phrase after compression.
            // Build the dictionary.
            int dictSize = 0;
            Map<List<Byte>, Integer> dictionary = new HashMap<List<Byte>, Integer>();

            //for (int i = 0; i < 256; i++)
            //    dictionary.put("" + (char)i, i);
            List<Byte> temp=new ArrayList<>();
            byte initializeByte=0;
            temp.add(initializeByte);
            dictionary.put(temp,0);
            temp=new ArrayList<>();
            initializeByte=1;
            temp.add(initializeByte);
            dictionary.put(temp,1);
            List<Byte> currentBytes=new ArrayList<Byte>();
            List<Integer> result = new ArrayList<Integer>();
            for (byte b : bytes) {
                List<Byte> currentBytesB = new ArrayList<>(currentBytes); //new byte[currentBytes.length +1];
                currentBytesB.add(b);
               // System.arraycopy(currentBytes ,0,currentBytesB,0,currentBytes.length);
               // currentBytesB[currentBytesB.length-1]=b;
                if (dictionary.containsKey(currentBytesB))
                    currentBytes = currentBytesB;
                else {
                    if(currentBytes.size()!=0) {
                        Integer test = dictionary.get(currentBytes);
                        result.add(dictionary.get(currentBytes));
                    }
                    // Add currentBytesB to the dictionary.
                    dictionary.put(currentBytesB, dictSize++);
                    currentBytes = new ArrayList<>();
                    currentBytes.add(b);
                }
            }
            // Output the code for w.
            if (currentBytes.size()!=0)
                result.add(dictionary.get(currentBytes));
           // return result;
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.toArray()[i]);
        }

    }

    public static void main(String[] args) throws IOException {

        byte [] test = {1,0,0};
        OutputStream out = new ByteArrayOutputStream();
        MyCompressorOutputStream testMain=new MyCompressorOutputStream(out);
        testMain.write(test);
    }

        @Override
    public void write(int argByte) throws IOException {
        byte[] bytes = BigInteger.valueOf(argByte).toByteArray();
        write(bytes);
    }

}
