package IO;

import javafx.util.Pair;
import sun.misc.IOUtils;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyDecompressorInputStream  extends InputStream {

    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] mazeByteArray) throws IOException {

        ObjectInputStream objectInputStream = new ObjectInputStream(in);
        byte[] compressedMazeBytes = new byte[0];
        try {
            compressedMazeBytes = (byte[])objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*
        List<Byte> byteList = new ArrayList<>();
        while(in.available() >0){
            int integerByte = in.read();
            byte b = (byte) integerByte;
            byteList.add(b);
        }
        byte[] compressedMazeBytes = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            compressedMazeBytes[i] = byteList.get(i);
        }
        */
        //byte[] compressedMazeBytes = (byte[])inputStream.();
        List<Pair<Integer,Integer>> compressedMazePairs = fromByteToPairs(compressedMazeBytes);
        byte[] tempByteArray = read(compressedMazePairs);
        for (int i = 0; i < tempByteArray.length; i++) {
            mazeByteArray[i] = tempByteArray[i];
        }

        if(tempByteArray.length<mazeByteArray.length)
        {
            mazeByteArray[tempByteArray.length]=1;
        }
        System.out.println();
        return 1;
    }

    /**
     * Recives compressed byte array and return a list of pairs.
     * @param compressedMazeBytes - byte[]
     * @return - List<Pair<Integer, Integer>>
     */
    public List<Pair<Integer, Integer>> fromByteToPairs(byte[] compressedMazeBytes) { //TODO:Change to private
        List<Pair<Integer, Integer>> compressedMazePairs = new ArrayList<>();
        for (int i = 0; i < compressedMazeBytes.length; i=i+5) {
            //key
            String stringByteKey = "";
            for (int j = 0; j < 4; j++) {
                String binaryString = String.format("%8s" , Integer.toBinaryString(compressedMazeBytes[i+j] & 0xFF)).replace(' ', '0');
                stringByteKey += binaryString;
            }
            int key = Integer.parseInt(stringByteKey, 2);
            //value
            int value = compressedMazeBytes[i+4] & 0xFF;

            compressedMazePairs.add(new Pair<>(key,value));
        }
        return compressedMazePairs;
    }

    //public List<Pair<Integer,Integer>> read(byte[] bytes) {

    //    return null;
    //}
    /*
        // Build the dictionary.
        int dictSize = 0;
        Map<Integer, List<Byte>> dictionary = new HashMap<Integer, List<Byte>>();

        for (int i = -128; i <= 127; i++) {
            List<Byte> temp = new ArrayList<>();
            byte initializeByte = (byte) i;
            temp.add(initializeByte);
            dictionary.put(dictSize++, temp);
        }
        /*
        List<Byte> temp=new ArrayList<>();
        byte initializeByte=0;
        temp.add(initializeByte);
        dictionary.put(0,temp);
        temp=new ArrayList<>();
        initializeByte=1;
        temp.add(initializeByte);
        dictionary.put(1,temp);

        Byte firstByte = (byte) (int) compressed.remove(0);//:FIXME:!!!

        List<Byte> currentBytes = new ArrayList<Byte>();
        currentBytes.add(firstByte);

        List<Byte> result = new ArrayList<Byte>();
        result.add(firstByte);

        for (int i : compressed) {
            List<Byte> entryBytes = new ArrayList<>();
            if (dictionary.containsKey(i)) {
                entryBytes = dictionary.get(i);
            } else if (i == dictSize) {
                entryBytes = currentBytes;
                entryBytes.add(currentBytes.get(0));//###??
            } else {
                throw new IllegalArgumentException("Bad compressed i: " + i);
            }

            result.addAll(entryBytes);
            //result.addAll(entryBytes.subList(1,entryBytes.size()));

            // Add w+entry[0] to the dictionary.
            List<Byte> dictEntry = new ArrayList<>();
            dictEntry.addAll(currentBytes);
            dictEntry.add(entryBytes.get(0));
            dictionary.put(dictSize++, dictEntry);

            currentBytes = entryBytes;
        }

        System.out.println("\n" + "after decompression: ");
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.toArray()[i]);
        }
        return null;
    }
    */
    public byte[] read(List<Pair<Integer,Integer>> compressed) {
        List<String> decopressedPairsList=new ArrayList<String>();
        decopressedPairsList.add("NULL");
        int numberOfByte=0;
        for (int i = 0; i < compressed.size(); i++) {
            /*for (int j = 0; j < compressed.get(i).getKey()-1; j++) {
                int check=decopressedPairsList.get(compressed.get(i).getKey()+j);
                decopressedPairsList.add(decopressedPairsList.get(compressed.get(i).getKey()+j));
            }
            */
            if(compressed.get(i).getKey()!=0){
                decopressedPairsList.add(decopressedPairsList.get(compressed.get(i).getKey())+","+compressed.get(i).getValue()+"");
                numberOfByte=numberOfByte+decopressedPairsList.get(i+1).length()-decopressedPairsList.get(i+1).replace(",","").length()+1;
            }
            else
            {
                decopressedPairsList.add(compressed.get(i).getValue()+"");
                numberOfByte++;

            }

        }
        byte[] decompressedByteArray=new byte[numberOfByte];
        int k = 0;
        for (int i = 1; i < decopressedPairsList.size() ; i++) {
            String[] afterSplit=decopressedPairsList.get(i).split(",");
            for (int j = 0; j < afterSplit.length; j++) {
                int covertStringToInt=Integer.parseInt(afterSplit[j]);
                decompressedByteArray[k] = (byte) covertStringToInt;
                k++;
            }
        }
        /*
        System.out.println("\n" + "after decompression: ");
        for (int i = 0; i < decompressedByteArray.length; i++) {
            System.out.print(decompressedByteArray[i]);
        }
        */

        return decompressedByteArray;
    }
}
