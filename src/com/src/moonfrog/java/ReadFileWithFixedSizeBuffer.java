package com.src.moonfrog.java;



import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.print.attribute.HashAttributeSet;
 
public class ReadFileWithFixedSizeBuffer 
{
    public static void main(String[] args) throws IOException 
    {
        int len = args.length;
        String filename = "";
        String searchkey = "";
        int nThread= 0;
        System.out.println(args[0]);
        for(int i=0; i<len; i++){
        	if(i==0)
        		filename = args[0];
        	
        	else if(i == len -1)
        		nThread= Integer.parseInt(args[i]);
        	
        	else{
        		searchkey += args[i] + " ";
        	}
        }
        searchkey= searchkey.trim();
        
        int size = searchkey.getBytes().length;
        
    	RandomAccessFile aFile = new RandomAccessFile(filename, "r");
        
        long fileSize = aFile.length();
        long chunkSize =  fileSize / nThread;
        HashMap<Integer,ArrayList<Long>> map = new HashMap<Integer,ArrayList<Long>>();
       
        SearchString searchString = new SearchString(map, aFile, nThread, chunkSize, fileSize, searchkey.trim());
        System.out.println(searchkey.length());
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
        	Thread worker = new Thread(searchString,""+i); 
            executor.execute(worker);
            
         }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        aFile.close();
      
        System.out.println("Result : ");
        
        for (Map.Entry<Integer, ArrayList<Long>> entry : map.entrySet()) {
            for(int k=0 ; k < entry.getValue().size();k++){
            	if(k !=0 && entry.getValue().get(k)<=size){
            		//do nothing
            	}
            	else{
            		long value= entry.getValue().get(k)+((entry.getKey()-1)*chunkSize);
            		System.out.print(value +" ,");
            	}
            	
            }
        }

    }
}
