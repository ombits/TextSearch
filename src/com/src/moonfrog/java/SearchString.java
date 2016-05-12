package com.src.moonfrog.java;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchString implements Runnable{

	Map<Integer, ArrayList<Long>> map;
	RandomAccessFile rfile;
	int nThread;
	long chunkSize;
	long filesize;
	String key;
	int size;
	
	public SearchString(HashMap<Integer, ArrayList<Long>> map,RandomAccessFile rfile,int nThread,
			long chunkSize, long filesize,String skey) {
		this.map =map;
		this.rfile = rfile;
		this.nThread = nThread;
		this.chunkSize =chunkSize;
		this.filesize= filesize;
		this.key = skey;
		this.size = skey.getBytes().length;
		
	}
	
	@Override
    public void run() {
        processCommand();

    }
 
    private  void processCommand() {
        try {
        	
        	FileChannel inChannel = rfile.getChannel();
        	ByteBuffer buffer = ByteBuffer.allocate((int) (chunkSize+ size));
        	
        	for( int j=1 ; j<nThread+1;j++){
        		if(Thread.currentThread().getName().contains("d-"+j)){
        			inChannel.read(buffer, (j-1)*(chunkSize));
        			buffer.flip();
        			String data = new String(buffer.array());
        			List<Long> list = KMPSearch.KMPSearch(key, data);
        			map.put(j, (ArrayList<Long>) list);
        			
        		}
        	}
        	
            buffer.clear();
            
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
  

}
