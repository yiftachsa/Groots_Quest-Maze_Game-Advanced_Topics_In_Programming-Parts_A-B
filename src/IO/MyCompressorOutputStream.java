package IO;


import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public List<Integer> resultTEMP = new ArrayList<Integer>();
    public Map<List<Byte>, Integer> dictionaryTEMP = new HashMap<List<Byte>, Integer>();

    public MyCompressorOutputStream(OutputStream outputStream) {
        out = outputStream;
    }


    @Override
    public void write(byte[] bytes) throws IOException {
        // Build the dictionary.
        int dictSize = 2; //###!!!
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
                //if(currentBytes.size()!=0) {
                Integer test = dictionary.get(currentBytes);
                result.add(dictionary.get(currentBytes));
                //}
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
        System.out.println("\n"+"result: ");
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.toArray()[i]);
        }
        resultTEMP = result;
        dictionaryTEMP = dictionary;
    }

    public static void main(String[] args) throws IOException {

        byte [] test = {1,0,0,1,0,0,0,0,1,1};
        System.out.println("before compression: ");
        for (int i = 0; i < test.length; i++) {
            System.out.print(test[i]);
        }
        OutputStream out = new ByteArrayOutputStream();
        MyCompressorOutputStream testMainCompressor=new MyCompressorOutputStream(out);
        testMainCompressor.write(test);

        InputStream in = new PipedInputStream();
        MyDecompressorInputStream testMainDecompressor = new MyDecompressorInputStream(in);
        testMainDecompressor.read(testMainCompressor.resultTEMP);
    }

    @Override
    public void write(int argByte) throws IOException {
        byte[] bytes = BigInteger.valueOf(argByte).toByteArray();
        write(bytes);
    }

}
