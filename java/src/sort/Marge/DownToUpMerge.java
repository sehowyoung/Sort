package sort.Marge;

/**
 * @author howyoung
 * @ClassName DownToUpMarge
 * @Date 2021/3/8 13:51
 **/
public class DownToUpMerge<T extends Comparable<T>> extends Merge<T> {
    /**
     * 使用自底向上归并排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        T[] auxiliary = (T[]) new Comparable[array.length];

        //len表示有len个元素归并，例如len=1表示一个一个元素的进行归并
        for (int len = 1; len < array.length; len += len) {
            //按照len的长度归并，归并后长度翻倍
            for (int start = 0; start < array.length - len; start += len + len) {
                merge(array, start, start + len - 1, Math.min(start + len + len -1, array.length - 1), auxiliary);
            }
        }
    }
}
