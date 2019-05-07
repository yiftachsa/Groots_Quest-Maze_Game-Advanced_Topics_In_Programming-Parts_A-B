package IO;


import javafx.util.Pair;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCompressorOutputStream extends OutputStream {

    private OutputStream out;

    public List<Pair<Integer,Integer>> resultTEMP2 = new ArrayList<Pair<Integer,Integer>>();
    byte[] resultTEMP3;

    public MyCompressorOutputStream(OutputStream outputStream) {
        out = outputStream;
    }


    @Override
    public void write(byte[] bytes) throws IOException {
        /*
        System.out.println("before compression: ");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
        }
        */

        // Build the dictionary.
        int dictSize = 1; //###!!!
        int resultInd=0;
        int counter=0;
        Map<List<Byte>, Integer> dictionary = new HashMap<List<Byte>, Integer>();

      /*  for (int i = -128; i <= 127; i++) {
            List<Byte> temp=new ArrayList<>();
            byte initializeByte=(byte) i;
            temp.add(initializeByte);
            dictionary.put(temp,dictSize++);
        }
        */

        List<Byte> currentBytes=new ArrayList<Byte>();
        List<Pair<Integer,Integer>> result = new ArrayList<Pair<Integer,Integer>>();

        for (byte b : bytes) {
            counter++;
            List<Byte> currentBytesB = new ArrayList<>(currentBytes); //new byte[currentBytes.length +1];
            currentBytesB.add(b);
            // System.arraycopy(currentBytes ,0,currentBytesB,0,currentBytes.length);
            // currentBytesB[currentBytesB.length-1]=b;
            if(dictionary.containsKey(currentBytesB) && counter == bytes.length)
            {
                if(currentBytesB.size() == 1)
                {
                    result.add(new Pair<>(0, b & 0xFF));

                }
                else {
                    currentBytesB.remove(currentBytesB.size() - 1);
                    resultInd = dictionary.get(currentBytesB);
                    result.add(new Pair<>(resultInd, b & 0xFF));
                }
            }
            else if(dictionary.containsKey(currentBytesB)){
                currentBytes = currentBytesB;
                resultInd=dictionary.get(currentBytesB);
            }
            else {
                // Add currentBytesB to the dictionary.
                dictionary.put(currentBytesB, dictSize++);
                result.add(new Pair<>(resultInd,b & 0xFF));
                resultInd=0;
                currentBytes = new ArrayList<>();
                //currentBytes.add(b);
            }
        }
        resultTEMP2=result;
        // Output the code for w.
        //if (currentBytes.size()!=0)
        //    result.add(dictionary.get(currentBytes));
        // return result;

        //resultTEMP = result;
        //dictionaryTEMP = dictionary;
        //From int TO byte
        //TODO: maybe need only 3 byte's, change byteResult size to result.size()*4 , byteString size to 24 , j=24
        byte [] byteResult = new byte[result.size()*5];
        int index=0;
        for (int i = 0; i < result.size(); i++) {
            String byteString = Integer.toBinaryString(result.get(i).getKey());
            while (byteString.length()<32){
                byteString = "0" + byteString;
            }
            for (int j = 0; j < 32; j= j+8) {
                String stringByte = byteString.substring(j, j+8);
                int intValue = Integer.parseInt(stringByte,2);
                byte byteValue = (byte)intValue;
                byteResult[index++]=byteValue;
            }
            byte addValue = (byte)((int) result.get(i).getValue());
            byteResult[index++]=addValue;
        }

        resultTEMP3 = byteResult;
        /*
        System.out.println("\n"+"after compression: ");
        for (int i = 0; i < result.size(); i++) {
            System.out.print("("+result.toArray()[i]+")           ,");
        }
        System.out.println("");
        for (int i = 0; i < byteResult.length; i=i+5) {
            System.out.print("key:"+byteResult[i]+""+byteResult[i+1]+""+byteResult[i+2]+""+byteResult[i+3]+" value:"+byteResult[i+4]+",");
        }
        */
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
        objectOutputStream.writeObject(byteResult);
    }


    public static void main(String[] args) throws IOException {

        byte [] test = {1,0,1,1,0,-1,127,-127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,1,0,127,127,127,11,10,1,0,1,1,0,127,127,127,11,10,1,0,1,0,1,0,0,1,0,11,0,1,0,0,1,0,1,0,1,0,0,1,0,11,0,1,0,0};

        OutputStream out = new ByteArrayOutputStream();
        MyCompressorOutputStream testMainCompressor=new MyCompressorOutputStream(out);
        testMainCompressor.write(test);

        InputStream in = new PipedInputStream();
        MyDecompressorInputStream testMainDecompressor = new MyDecompressorInputStream(in);
        List<Pair<Integer, Integer>> decompressList= testMainDecompressor.fromByteToPairs(testMainCompressor.resultTEMP3);
        /*
        System.out.println("after fromByteToPairs");
        for (int i = 0; i < decompressList.size(); i++) {
            System.out.print("("+decompressList.toArray()[i]+")           ,");
        }
        */
        testMainDecompressor.read(testMainCompressor.resultTEMP2);
    }

    @Override
    public void write(int argByte) throws IOException {
        byte[] bytes = BigInteger.valueOf(argByte).toByteArray();
        write(bytes);
    }

}
