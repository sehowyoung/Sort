package sort.Marge;

import sort.Sort;

/**
 * @author howyoung
 * @ClassName Marge
 * @Date 2021/3/8 11:15
 **/
public abstract class Merge<T extends Comparable<T>> extends Sort<T> {
    /**
     * 归并算法
     * @param array 原数组
     * @param low   需要进行归并部分的开始下标
     * @param mid   需要进行归并部分的中间下标
     * @param high  需要进行归并部分的结束下标
     * @param aux   辅助数组
     */
    protected void merge(T[] array, int low, int mid, int high, T[] aux){
        for (int i = low; i <= high; i++) {
            aux[i] = array[i];
        }

        //右指针
        int right = mid + 1;
        //左指针
        int left = low;
        for (int i = low; i <= high; i++) {
            if (left > mid){
                //将右序列剩余元素添加进原数组
                array[i] = aux[right++];
            } else if (right > high){
                //将左序列剩余元素添加进原数组
                array[i] = aux[left++];
            } else if (aux[left].compareTo(aux[right]) <= 0){
                //添加小的元素进入原数组
                array[i] = aux[left++];
            } else {
                //添加小的元素进入原数组
                array[i] = aux[right++];
            }
        }
    }
}
