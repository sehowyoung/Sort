package sort;

/**
 * @author howyoung
 * @ClassName Selection
 * @Date 2021/3/8 9:13
 **/
public class Selection<T extends Comparable<T>> extends Sort<T> {
    /**
     * 使用快速排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (lessThan(array[j], array[i])){
                    min = j;
                }
            }
            swap(array, i, min);
        }
    }
}
