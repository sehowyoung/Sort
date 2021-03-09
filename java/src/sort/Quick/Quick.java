package sort.Quick;

import sort.Sort;

/**
 * @author howyoung
 * @ClassName Quick
 * @Date 2021/3/8 15:14
 **/
public class Quick<T extends Comparable<T>> extends Sort<T> {
    /**
     * 使用快速排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }

    /**
     * 通过递归不断交换元素的位置
     * @param array 被操作的数组
     * @param low   从左边开始执行的元素下标
     * @param high  从右边开始执行的元素下标
     */
    private void sort(T[] array, int low, int high) {
        if (high <= low){
            return;
        }
        //获取被发生交换的元素的位置
        int index = partition(array, low, high);
        sort(array, low, index - 1);
        sort(array, index + 1, high);
    }

    /**
     * 对原数组进行切分，获取与array[low]进行交换的元素的下标
     * @param array 操作数组
     * @param low   从左边开始执行的元素下标
     * @param high  从右边开始执行的元素下标
     * @return 返回与array[low]进行交换的元素的下标
     */
    private int partition(T[] array, int low, int high) {
        int left = low + 1;
        int right = high;
        T flag = array[low];
        while (true){
            while (lessThan(array[left], flag) && left != high){
                left++;
            }
            while (lessThan(flag, array[right]) && right != low){
                right--;
            }
            if (left >= right){
                break;
            }
            swap(array, left, right);
        }
        swap(array, low, right);
        return right;
    }

    /**
     * 搜索数组中第k个小的元素
     * @param array 目标数组
     * @param k     第k个元素
     * @return      第k个元素
     */
    public T select(T[] array, int k){
        int low = 0;
        int high = array.length - 1;
        while (high > low){
            int index = partition(array, low, high);

            if (index == k - 1){
                return array[k - 1];
            } else if (index > k - 1){
                high = index - 1;
            } else {
                low = index + 1;
            }
        }
        return array[k - 1];
    }
}
