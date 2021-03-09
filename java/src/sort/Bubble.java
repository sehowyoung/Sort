package sort;

/**
 * @author howyoung
 * @ClassName Bubble
 * @Date 2021/3/8 10:04
 **/
public class Bubble<T extends Comparable<T>> extends Sort<T>{
    /**
     * 用冒泡排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        int n = array.length;
        boolean isSorted = false;
        for (int i = 0; i < n - 1; i++) {
            isSorted = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (lessThan(array[j + 1], array[j])){
                    swap(array, j + 1, j);
                    isSorted = true;
                }
            }
            if (!isSorted){
                break;
            }
        }
    }
}
