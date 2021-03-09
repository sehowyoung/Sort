package sort;

/**
 * @author howyoung
 * @ClassName Shell
 * @Date 2021/3/8 10:55
 **/
public class Shell<T extends Comparable<T>> extends Sort<T> {
    /**
     * 使用希尔排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        int gap = array.length / 2;
        while(gap >= 1){
            for (int i = gap; i < array.length; i++) {
                for (int j = i; j > gap - 1; j -= gap) {
                    if (lessThan(array[j], array[j - gap])){
                        swap(array, j, j - gap);
                    }
                }
            }
            gap /= 2;
        }
    }
}
