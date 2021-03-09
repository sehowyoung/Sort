package sort.Quick;

import sort.Sort;

/**
 * @author howyoung
 * @ClassName MiddleOfThree
 * @Date 2021/3/8 16:15
 **/
public class MiddleOfThree<T extends Comparable<T>> extends Sort<T> {
    /**
     * 使用三数取中快速排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }

    /**
     * 使用递归对目标数组 array 进行排序
     * @param array 目标数组
     * @param low   左指针
     * @param high  右指针
     */
    private void sort(T[] array, int low, int high) {
        dealPivot(array, low, high);
        //使用下列判断去除一下已经排好序的情况，节省运行时间
        if (high <= low + 1){
            return;
        }
        int pivot = high - 1;
        int left = low + 1;
        int right = pivot - 1;
        while (true){
            while (lessThan(array[left], array[pivot]) && left < high){
                left++;
            }
            while (right > low && moreThen(array[right], array[pivot])){
                right--;
            }
            if (right > left){
                swap(array, left, right);
            } else {
                break;
            }
        }
        swap(array, left, pivot);
        sort(array, low, left - 1);
        sort(array, left + 1, high);
    }

    /**
     * 找到枢纽值，并对左端值、枢纽值、右端值进行排序，并将枢纽值放到右端值左侧
     * 通过三次判断就保证了三个值的升序排列
     * @param array 目标数组
     * @param low   左端指针
     * @param high  右端指针
     */
    private void dealPivot(T[] array, int low, int high) {
        int mid = (low + high) / 2;
        //对左端值、枢纽值、右端值三个数进行排序
        if (lessThan(array[mid], array[low])){
            swap(array, low, mid);
        }
        if (lessThan(array[high], array[mid])){
            swap(array, mid, high);
        }
        if (lessThan(array[mid], array[low])){
            swap(array, low, mid);
        }
        //将枢纽值放到右端值左侧
        swap(array, mid, high - 1);
    }
}
