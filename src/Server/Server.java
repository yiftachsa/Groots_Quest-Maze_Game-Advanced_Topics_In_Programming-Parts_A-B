package IO;

import javafx.util.Pair;

import java.io.IOException;
import java.io.InputStream;
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

    public byte[] read(List<Integer> compressed) {
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
        */
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
    public byte[] read2(List<Pair<Integer,Integer>> compressed) {
        // byte [] test = {15,0,0,0,0,1,1,1,1,1,1,1};
        List<String> temp=new ArrayList<String>();
        temp.add("NULL");
        int numberOfByte=0;
        for (int i = 0; i < compressed.size(); i++) {
            /*for (int j = 0; j < compressed.get(i).getKey()-1; j++) {
                int check=temp.get(compressed.get(i).getKey()+j);
                temp.add(temp.get(compressed.get(i).getKey()+j));
            }
            */
            if(compressed.get(i).getKey()!=0){
                temp.add(temp.get(compressed.get(i).getKey())+","+compressed.get(i).getValue()+"");
                numberOfByte=numberOfByte+temp.get(i+1).length()-temp.get(i+1).replace(",","").length()+1;
            }
            else
            {
                temp.add(compressed.get(i).getValue()+"");
                numberOfByte++;

            }

        }
        byte[] test=new byte[numberOfByte];
        int k = 0;
        for (int i = 1; i < temp.size() ; i++) {
            String[] afterSplit=temp.get(i).split(",");
            for (int j = 0; j < afterSplit.length; j++) {
                int covertStringToInt=Integer.parseInt(afterSplit[j]);
                test[k] = (byte) covertStringToInt;
                k++;
            }
        }
        return test;
    }
}
