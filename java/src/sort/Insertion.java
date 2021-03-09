package sort;

/**
 * @author howyoung
 * @ClassName Insertion
 * @Date 2021/3/8 10:39
 **/
public class Insertion<T extends Comparable<T>> extends Sort<T> {
    /**
     * 使用插入排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0; j--) {
                if (lessThan(array[j], array[j - 1])){
                    swap(array, j, j - 1);
                }
            }
        }
    }
}
