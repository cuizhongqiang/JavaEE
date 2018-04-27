package com.cbmie.lh.logistic.web;

import java.util.ArrayList;
import java.util.List;

public class Test {
	
	public static void main(String[] args) {
		int m = 6;
		int n = 4;
		
		List<Integer> list = new ArrayList<Integer>();
		
		for (int i = 1; i <= m; i++) {
			list.add(i);
		}
		
		count(list, n, 0);
	}
	
	public static void count (List<Integer> list, int n, int sub) {
		
		for (int i = 1; i <= n; i++) {
			
			if (sub > list.size() - 1) {
				sub = 0;
			}
			
			if (i == n) {
				if (list.size() == 1) {
					System.out.println(list.get(0));
					return;
				}
				list.remove(sub);
				count(list, n, sub);
			}
			
			sub ++;
			
		}
		
	}

}
