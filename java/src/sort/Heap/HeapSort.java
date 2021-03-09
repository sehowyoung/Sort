package sort.Heap;

import sort.Sort;

/**
 * @author howyoung
 * @ClassName HeapSort
 * @Date 2021/3/8 20:14
 **/
public class HeapSort<T extends Comparable<T>> extends Sort<T> {
    /**
     * 使用堆排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        //构建大顶堆
        int len = array.length;
        for (int i = len / 2 - 1; i >= 0; i--) {
            //从第一个非叶子结点从下至上，从右至左调整结构
            adjust(array, i, len);
        }
        //调整堆结构+交换堆顶元素与末尾元素
        for (int i = len - 1; i > 0; i--) {
            swap(array, 0, i);
            adjust(array, 0, i);
        }
    }

    /**
     * 对第i个节点以及其子节点进行调整调整
     * @param array 目标数组
     * @param i     第i个节点
     * @param len   数组长度
     */
    private void adjust(T[] array, int i, int len) {
        //获取当前值
        T temp = array[i];
        //从i节点的左子节点开始，即2i+1处
        for (int j = i * 2 + 1; j < len; j = j * 2 + 1) {
            //如果存在右子节点，并且左子节点小于右子节点，j指向右子节点
            if (j + 1 < len && lessThan(array[j], array[j + 1])){
                j++;
            }
            //如果子节点大于父节点，将子节点值赋给父节点（不进行交换）
            if (lessThan(temp, array[j])){
                array[i] = array[j];
                i = j;
            } else {
                break;
            }
        }
        //将temp的值放到最终的位置
        array[i] = temp;
    }
}
