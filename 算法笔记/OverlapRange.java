package my.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class OverlapRange {
/**
题目：
给定多个可能重叠的区间，找出重叠区间的个数。

举例如下：

输入：
4
1 5
10 15
5 10
20 30

输出：2

说明：题意应该是找出重叠区间中区间的最大个数，当没有区间重叠时，重叠个数最大为1，比如

输入为：
2
1 5
10 15

则输出为1；

输入为：
4
1 2
2 3
3 4
4 5

则输出为2（重叠区间相互之间都要有交集）；

输入为：
6
1 7
2 5
3 4
8 15
9 17
20 25

则输出为3。

解题思路:
	将区间分隔成各个点，每个点有两个属性，一个是值，一个是标志（0起点，1止点），然后对这些点排序，最后，从头开始扫描排序的结果，遇到起点重叠个数加1，遇到止点重叠个数减1，并且记录好重叠个数的最大值。
 * 
 */
	public static void main(String[] args){
		Scanner cin = new Scanner(System.in);
		int n=cin.nextInt();
		//2*n的数组保存每个区间的左右短点
		int nums[][]=new int[n*2][2];
		for(int i=0;i<n*2;i=i+2){
			nums[i][0]=cin.nextInt();//起点
			nums[i+1][0]=cin.nextInt();//终点
			nums[i][1]=0;//区间起点,用0表示
			nums[i+1][1]=1;//区间终点，用1表示
		}
		
		Arrays.sort(nums,new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[0]>o2[0])
					return 1;
				else if(o1[0]<o2[0])
					return -1;
				else
					return (o1[1]>o2[1])?1:((o1[1]<o2[1])?-1:0);
			}
		});
		int maxCount=0;
		for(int i=0;i<n*2;){
			int count=0;
			while(i<n*2&&nums[i][1]==0){
				i++;
				count++;
			}
			if(maxCount<count)maxCount=count;
			while(i<n*2&&nums[i][1]==1){
				i++;
			}
		}
		System.out.println(maxCount);
	}
	
	
}
