package sort.Marge;

/**
 * @author howyoung
 * @ClassName UpToDownMarge
 * @Date 2021/3/8 11:19
 **/
public class UpToDownMerge<T extends Comparable<T>> extends Merge<T> {
    /**
     * 使用自顶向下归并排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        //创建辅助数组
        T[] auxiliary = (T[]) new Comparable[array.length];
        sort(array, 0, array.length - 1, auxiliary);
    }

    /**
     * 使用递归对数组进行划分
     * @param array 任意类型的原数组
     * @param low   需要进行划分部分的初始下标
     * @param high  需要进行划分部分的结束下标
     * @param aux   辅助数组
     */
    private void sort(T[] array, int low, int high, T[] aux) {
        if (high <= low){
            return;
        }

        int mid = (low + high) / 2;
        sort(array, low, mid, aux);
        sort(array, mid + 1, high, aux);
        merge(array, low, mid, high, aux);
    }
}
