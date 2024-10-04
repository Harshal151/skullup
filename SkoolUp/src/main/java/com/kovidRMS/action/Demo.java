package com.kovidRMS.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Demo {



		public static Integer getMyMaxValue(int a,int b,int c) {

		Integer sum = 0;	
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(a,b,c));

		System.out.println("list == "+list);


		for(int i=0;i<2;i++) {
		sum = sum + Collections.max(list);
		list.remove(list.indexOf(Collections.max(list)));
		}
		System.out.println("sum == "+sum);
		return sum;
		}

		public static void main(String[] args) {
		Integer a = getMyMaxValue(5,3,2);	// Normal case	it should return	a = 8
		System.out.println("a == "+a);

		Integer b = getMyMaxValue(0,7,7);	// 2 values same	it should return	b = 14
		System.out.println("b == "+b);

		Integer c = getMyMaxValue(9,9,9);	// 3 values same	it should return	c = 18
		System.out.println("c == "+c);

		Integer d = getMyMaxValue(5,3,3);	// 2 values same	it should return	d = 8
		System.out.println("d == "+d);

		Integer e = getMyMaxValue(5,5,3);	// 2 values same	it should return	e = 10
		System.out.println("e == "+e);

		Integer f = getMyMaxValue(1,2,3);	// 3 values same	it should return	f = 5
		System.out.println("f == "+f);
		
		String[] standardString = new String[10];
		standardString[0] = "I";
		standardString[1] = "II";
		standardString[2] = "III";
		standardString[3] = "IV";
		standardString[4] = "V";
		standardString[5] = "VI";
		standardString[6] = "VII";
		standardString[7] = "VIII";
		standardString[8] = "IX";
		standardString[9] = "X";
		
		int indexVal = standardString.toString().indexOf("V");
		System.out.println("....."+findIndex(standardString,"IX"));
		}

		public static int findIndex(String arr[], String t)
		   {
		       int index = Arrays.binarySearch(arr, t);
		       return (index < 0) ? -1 : index;
		   }

}
