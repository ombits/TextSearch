package com.src.moonfrog.java;

import java.util.ArrayList;
import java.util.List;

public class KMPSearch {
	
	public static  List<Long>  KMPSearch(String pat, String txt)
	{
		List<Long> index = new ArrayList<Long>();
		System.out.println("pat :"+pat + " , data" +txt);
		char[] pa = pat.toCharArray();
		char[] tx = txt.toCharArray();
		
		int M = pat.length();
		int N = txt.length();

		
		int[] lps = new int[M];
		int j = 0; // index for pat[]

		// Preprocess the pattern (calculate lps[] array)
		computeLPSArray(pa, M, lps);

		int i = 0; // index for txt[]
		while (i < N)
		{
		if (pa[j] == tx[i])
		{
			j++;
			i++;
		}

		if (j == M)
		{
			System.out.println("Found pattern at index"+ (i-j));
			index.add((long) (i-j));
			j = lps[j-1];
		}

	
		else if(i < N && pa[j] != tx[i])
		{
			
			if (j != 0)
			j = lps[j-1];
			else
			i = i+1;
		}
		}
		
		
		return index;
	}

	static void computeLPSArray(char[] pat, int M, int[] lps)
	{
		int len = 0; // length of the previous longest prefix suffix
		int i;

		lps[0] = 0; // lps[0] is always 0
		i = 1;

		// the loop calculates lps[i] for i = 1 to M-1
		while (i < M)
		{
		if (pat[i] == pat[len])
		{
			len++;
			lps[i] = len;
			i++;
		}
		else // (pat[i] != pat[len])
		{
			if (len != 0)
			{
			// This is tricky. Consider the example 
			// AAACAAAA and i = 7.
			len = lps[len-1];

			// Also, note that we do not increment i here
			}
			else // if (len == 0)
			{
			lps[i] = 0;
			i++;
			}
		}
		}
	}

	
}
