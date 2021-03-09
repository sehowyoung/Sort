package sort;

/**
 * @author howyoung
 * @ClassName Sort
 * @Date 2021/3/8 9:25
 **/
public abstract class Sort<T extends Comparable<T>>  {
    /**
     * 对任意类型的数组进行排序
     * @param array 目标数组
     */
    public abstract void sort(T[] array);

    /**
     * @param v T类型的对象
     * @param w T类型的对象
     * @return 如果v<w，返回true，否则返回false
     */
    protected boolean lessThan(T v, T w){
        return v.compareTo(w) < 0;
    }

    /**
     * 比较同一类型的两个示例的大小
     * @param v T类型的示例
     * @param w T类型的示例
     * @return 如果v>w，返回true，否则返回false
     */
    protected boolean moreThen(T v, T w){
        return v.compareTo(w) > 0;
    }

    /**
     * 用于交换数组中的两个数
     * @param array 任意类型的数组
     * @param i 数组下标
     * @param j 数组下标
     */
    protected void swap(T[] array, int i, int  j){
        T t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
