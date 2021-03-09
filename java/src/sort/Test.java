package sort;

import sort.Heap.HeapSort;
import sort.Quick.MiddleOfThree;
import sort.Quick.Quick;
import sort.Quick.ThreeWay;

import java.util.Arrays;

/**
 * @author howyoung
 * @ClassName Test
 * @Date 2021/3/8 9:56
 **/
public class Test {
    public static void main(String[] args) {
//        Sort<Integer> sort = new Shell<>();
//        Integer[] arr = {3,1,2,4,5};
//        sort.sort(arr);
//        System.out.println(Arrays.toString(arr));

//        Sort<Integer> so = new DownToUpMerge<>();
//        Integer[] arr = {8,4,5,7,1,3,6,2};
//        so.sort(arr);
//        System.out.println(Arrays.toString(arr));

//        Sort<Integer> sort1 = new Quick<>();
//        Integer[] arr = {8,4,5,7,1,3,6,2};
//        sort1.sort(arr);
//        System.out.println(Arrays.toString(arr));

//        Quick<Integer> q = new Quick<>();
//        Integer[] arr = {8,5,2,3,4,6,9,1};
//        System.out.println(q.select(arr, 2));

        Sort<Integer> hs = new HeapSort<>();
//        Integer[] arr = {8,7,6,5,4,3,2,1};
        Integer[] arr = { 1, 3, 4, 5, 2, 6, 9, 7, 8, 0 };
        hs.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
