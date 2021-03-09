package sort.Quick;

import sort.Sort;

import java.util.Arrays;

/**
 * @author howyoung
 * @ClassName ThreeWay
 * @Date 2021/3/8 17:48
 **/
public class ThreeWay<T extends Comparable<T>> extends Sort<T> {
    /**
     * 实现Sort的sort接口
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }

    /**
     * 递归使用三向切分快速排序算法进行排序
     * @param array 目标数组
     * @param low   左指针
     * @param high  右指针
     */
    private void sort(T[] array, int low, int high) {
        if (high <= low){
            return;
        }
        //lt:less than  gt:greater than
        int lt = low;
        int index = low + 1;
        int gt = high;
        T temp = array[low];
        while (index <= gt){
            int cmp = array[index].compareTo(temp);
            if (cmp < 0){
                swap(array, lt++, index++);
            } else if (cmp > 0){
                swap(array, index, gt--);
            } else {
                index++;
            }
        }
        System.out.println(Arrays.toString(array));
        sort(array, low, lt - 1);
        sort(array, gt + 1, high);
    }
}
