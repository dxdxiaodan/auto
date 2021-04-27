package com.auto.selenium.sort;

/**
 * @author
 * @date
 * @desc
 */

public class QuickSort {
    private int[] array;
    public QuickSort(int[] array) {
        this.array = array;
    }
    public void mergeSort(){
        mergrSort(array,0,array.length-1,new int[array.length]);
    }
    public void sort() {
        quickSortDesc(array, 0, array.length - 1);
    }
    public void shellSort() {
        shellSort(array);
    }
    public void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    /**升序快速排序
     * 递归排序
     * @param src
     * @param begin
     * @param end
     */
    private void quickSort(int[] src, int begin, int end) {
        if (begin < end) {
            int key = src[begin];
            int i = begin;
            int j = end;
            while (i < j) {
                while (i < j && src[j] > key) {
                    j--;
                }
                if (i < j) {
                    src[i] = src[j];
                    i++;
                }
                while (i < j && src[i] < key) {
                    i++;
                }
                if (i < j) {
                    src[j] = src[i];
                    j--;
                }
            }
            src[i] = key;
            quickSort(src, begin, i - 1);
            quickSort(src, i + 1, end);
        }
    }

    /**
     * 降序快速排序
     * @param src
     * @param left
     * @param right
     */
    private void quickSortDesc(int[] src,int left,int right){
        if(left < right){
            int key = src[left];
            int begin = left;
            int end = right;
            while(left < right){
                while(left < right && src[right] < key){
                    right--;
                }
                if(left < right){
                    src[left] = src[right];
                    left++;
                }
                while(left < right && src[left] > key){
                    left++;
                }
                if(left < right){
                    src[right] = src[left];
                    right--;
                }
            }
            src[left] = key;
            quickSortDesc(src,begin,left-1);
            quickSortDesc(src,left+1,end);

        }


    }

    /**
     * 希尔排序，asc
     * @param arr
     */
    private void shellSort(int[] arr){
        int length = arr.length;
        for(int g = length/2;g > 0;g/=2){
            for(int i = g;i < length;i++){
                int t = arr[i];
                int j = i;
                while(j-g >= 0 && t < arr[j-g]){
                    arr[j] = arr[j-g];
                    j-=g;
                }
                arr[j] = t;
            }
        }
    }

    private void mergrSort(int[] arr,int left,int right,int[] temp){
        if(left < right){
            int mid = (left+right)/2;
            mergrSort(arr,left,mid,temp);
            mergrSort(arr,mid+1,right,temp);
            merge(arr,left,mid,right,temp);
        }
    }
    private void merge(int[] arr,int left,int mid,int right,int[] temp){
        int t=0;
        int l=0,r=mid+1;
        while(l<=mid && r <=right){
            if(arr[l]<arr[r]){
                temp[t++] = arr[l++];
            }else{
                temp[t++] = arr[r++];
            }
        }
        while(l<=mid){
            temp[t++] = arr[l++];
        }
        while(r<=right){
            temp[t++] = arr[r++];
        }
        t--;
        while (t>=0){
            arr[t--] = temp[t--];
        }

    }
    private void swap(int[] arr,int l,int r){
        int temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }
    public static void main(String[] args) {
        testQuickSort();
    }
    /**
     * 快速排序
     */
    private static void testQuickSort() {
        int[] array = {5, 9, 1, 9, 5,0, 3, 7, 6, 1};
        QuickSort quickSort = new QuickSort(array);
        //quickSort.sort();
        quickSort.mergeSort();
        quickSort.print();
    }

}