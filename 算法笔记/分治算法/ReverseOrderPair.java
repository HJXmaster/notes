package my.test;

import java.util.Arrays;
import java.util.Scanner;

public class ReverseOrderPair {
	/**
	 * 
	 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
		示例 1:
		
		输入: [7,5,6,4]
		输出: 5
		 
		
		限制：
		
		0 <= 数组长度 <= 50000
		
		解题思路：分治算法，利用归并排序，合并两个有序数组中的比较，计算逆序对
	 * 
	 */
	
	
	public static void main(String[] args){
		Solution function=new Solution();
		int nums[]={2,3,5,7,1,4,6,8};
		function.reversePairs(nums);
		System.out.println(Arrays.toString(nums));
	}
}


class Solution {
    public int reversePairs(int[] nums) {
    	return merge(nums,0,nums.length-1);
    }
    //归并排序
    public int merge(int[] num,int low,int high){
    	int result=0;
        if(low<high){
            int middle=(low+high)/2;
            result+=merge(num,low,middle);
            result+=merge(num,middle+1,high);
            int tempNum[]=new int[high-low+1];
            int index1=low;
            int index2=middle+1;
            int index=0;
            while(index1<=middle&&index2<=high){
                if(num[index1]>num[index2]){
                    tempNum[index++]=num[index2++];
                    result+=(middle-index1+1);//nums中下标index1~middle这几个数分别于nums[index2]形成逆序对
                }else
                    tempNum[index++]=num[index1++];
            }
            while(index1<=middle){
                tempNum[index++]=num[index1++];
            }
                
            while(index2<=high)
                tempNum[index++]=num[index2++];

            for(int i=0,count=high-low+1;i<count;i++)
                num[low+i]=tempNum[i];
        }
        return result;
    }

}