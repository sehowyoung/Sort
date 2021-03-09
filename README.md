[TOC]

# 算法

## 一、排序

### 1.comparable和comparator

>1.Comparable是排序接口；若一个类实现了Comparable接口，就意味着“该类支持排序”。
>
>（作用在实体类上）
>2.而Comparator是比较器；我们若需要控制某个类的次序，可以建立一个“该类的比较器”来进行排序。
>
>（作用在排序类上）
>
>总结：Comparable相当于“内部比较器”，而Comparator相当于“外部比较器”。

### 2.约定

待排序的元素需要实现 Java 的 Comparable 接口，该接口有 compareTo() 方法，可以用它来判断两个元素的大小关系。

使用辅助函数 lessThan() 和 swap() 来进行比较和交换的操作，使得代码的可读性和可移植性更好。

排序算法的成本模型是比较和交换的次数。

```java
public abstract class Sort<T extends Comparable<T>>  {
    /**
     * 对目标数组进行排序
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
    protected boolean moreThn(T v, T w){
        return v.compareTo(w) > 0;
    }

    /**
     * 用于交换数组中的两个数
     * @param array 目标数组
     * @param i 数组下标
     * @param j 数组下标
     */
    protected void swap(T[] array, int i, int  j){
        T t = array[i];
        array[i] = array[j];
        array[j] = t;
    }
}
```

### 3.选择排序算法

**原理：**

每次从数组中选择最小元素，将它与数组的第i个元素交换位置。

选择排序需要 ~N^2^/2 次比较和 ~N 次交换，它的运行时间与输入无关，这个特点使得它对一个已经排序的数组也需要这么多的比较和交换操作。

![](https://i.imgur.com/fYPsoKv.gif)

**代码实现：**

```java
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
```

### 4.冒泡排序算法

**原理：**

从左到右不断交换相邻的逆序的元素，在经过一轮的循环之后，可以让最大的元素上浮到最右边。

在一轮循环中，如果没有发生交换，那么说明数组已经是有序的，此时可以直接退出。

![](https://i.imgur.com/vkZ5GS7.gif)

**代码实现：**

```java
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
```

### 5.插入排序算法

**原理：**

从数组的第i（i>1）个数据开始向前比较，如果比j（j<i）小，交换位置。

![](https://i.imgur.com/bncpJ2B.gif)

**代码实现：**

```java
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
```

### 6.希尔排序算法

**原理：**

希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。

![](https://i.imgur.com/3ymhb30.png)

**代码实现：**

```java
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
```

### 7.归并排序算法

**原理：**

归并排序（MERGE-SORT）是利用归并的思想实现的排序方法，该算法采用经典的分治（divide-and-conquer）策略（分治法将问题分(divide)成一些小的问题然后递归求解，而治(conquer)的阶段则将分的阶段得到的各答案"修补"在一起，即分而治之)。

**归并部分代码：**

```java
public abstract class Marge<T extends Comparable<T>> extends Sort<T> {
    /**
     * 归并算法
     * @param array 原数组
     * @param low   需要进行归并部分的开始下标
     * @param mid   需要进行归并部分的中间下标
     * @param high  需要进行归并部分的结束下标
     * @param aux   辅助数组
     */
    protected void marge(T[] array, int low, int mid, int high, T[] aux){
        for (int i = low; i <= high; i++) {
            aux[i] = array[i];
        }

        //右指针
        int right = mid + 1;
        //左指针
        int left = low;
        for (int i = low; i <= high; i++) {
            if (left > mid){
                //将右序列剩余元素添加进原数组
                array[i] = aux[right++];
            } else if (right > high){
                //将左序列剩余元素添加进原数组
                array[i] = aux[left++];
            } else if (aux[left].compareTo(aux[right]) <= 0){
                //添加小的元素进入原数组
                array[i] = aux[left++];
            } else {
                //添加小的元素进入原数组
                array[i] = aux[right++];
            }
        }
    }
}
```



#### 7.1 自顶向上归并排序

**原理：**

自顶向下的排序算法就是把数组元素不断的二分，直到子数组的元素个数为一个，因为这个时候子数组必定是已有序的，然后将两个有序的序列合并成一个新的有序的序列，两个新的有序序列又可以合并成另一个新的有序序列，以此类推，直到合并成一个有序的数组。

![](https://i.imgur.com/b5Dgq52.png)

![](https://images2015.cnblogs.com/blog/1024555/201612/1024555-20161218194508761-468169540.png)



![](https://images2015.cnblogs.com/blog/1024555/201612/1024555-20161218194621308-588010220.png)

**代码实现：**

```java
public class UpToDownMarge<T extends Comparable<T>> extends Marge<T>{
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
        marge(array, low, mid, high, aux);
    }
}
```

#### 7.2 自底向上归并排序

**原理：**

自底向上的排序是归并排序的一种实现方式，将一个无序的N长数组切个成N个有序子序列，然后再两两合并，然后再将合并后的N/2（或者N/2 + 1）个子序列继续进行两两合并，以此类推得到一个完整的有序数组。下图详细的分解了自底向上的合并算法的实现过程：

![](https://i.imgur.com/57XtOjM.jpg)

```java
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
```

### 8.快速排序算法

#### 8.1 基本算法

**基本思想：**

+ 先从数列中取出一个数作为基准数。
+ 分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
+ 再对左右区间重复第二步，直到各区间只有一个数。

![](https://i.imgur.com/L1UTzxn.png)

+ 切分：取 array[low] 作为切分元素，然后从数组的左端向右端扫描知道找到第一个大于等于它的元素，再从数组的右端向左扫描找到第一个小于它的元素，交换这两个元素。不断重复进行这个过程就可以保证左指针 left 的左侧元素都不大于切分元素，右指针 right 的右侧元素都不小于切分元素。当两个指针相遇时，将切分元素 a[low] 和 a[right] 交换位置。

```java
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
        //左指针
        int left = low + 1;
        //右指针
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
}
```

#### 8.2 性能分析

快速排序是原地排序，不需要辅助数组，但是递归调用需要辅助栈。

快速排序最好的情况下是每次都正好将数组对半分，这样递归调用次数才是最少的。这种情况下比较次数为 CN=2CN/2+N，复杂度为 O(NlogN)。

最坏的情况下，第一次从最小的元素切分，第二次从第二小的元素切分，如此这般。因此最坏的情况下需要比较 N2/2。为了防止数组最开始就是有序的，在进行快速排序时需要随机打乱数组。

#### 8.3 算法改进

##### 8.3.1 切换到插入排序

在面对小数组时，使用快速排序会浪费更多的时间、空间，可以学习jdk1.8的排序算法，当数组长度小于某个值时，使用插入排序算法

##### 8.3.2 三数取中算法

在快排的过程中，每一次我们要取一个元素作为枢纽值，以这个数字来将序列划分为两部分。在此我们采用三数取中法，也就是取左端、中间、右端三个数，然后进行排序，将中间数作为枢纽值。

**基本原理：**

![](https://i.imgur.com/KeNroIf.png)

![](https://i.imgur.com/xnQscCO.png)

![](https://i.imgur.com/yLQ3aMX.png)

**代码实现：**

```java
public class MiddleOfThree<T extends Comparable<T>> extends Sort<T> {
    /**
     * 使用三数取中快速排序算法进行排序
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }

    /**
     * 使用递归对目标数组 array 进行排序
     * @param array 目标数组
     * @param low   左指针
     * @param high  右指针
     */
    private void sort(T[] array, int low, int high) {
        dealPivot(array, low, high);
        //使用下列判断去除一下已经排好序的情况，节省运行时间
        if (high <= low + 1){
            return;
        }
        int pivot = high - 1;
        int left = low + 1;
        int right = pivot - 1;
        while (true){
            while (lessThan(array[left], array[pivot]) && left < high){
                left++;
            }
            while (right > low && moreThen(array[right], array[pivot])){
                right--;
            }
            if (right > left){
                swap(array, left, right);
            } else {
                break;
            }
        }
        swap(array, left, pivot);
        sort(array, low, left - 1);
        sort(array, left + 1, high);
    }

    /**
     * 找到枢纽值，并对左端值、枢纽值、右端值进行排序，并将枢纽值放到右端值左侧
     * 通过三次判断就保证了三个值的升序排列
     * @param array 目标数组
     * @param low   左端指针
     * @param high  右端指针
     */
    private void dealPivot(T[] array, int low, int high) {
        int mid = (low + high) / 2;
        //对左端值、枢纽值、右端值三个数进行排序
        if (lessThan(array[mid], array[low])){
            swap(array, low, mid);
        }
        if (lessThan(array[high], array[mid])){
            swap(array, mid, high);
        }
        if (lessThan(array[mid], array[low])){
            swap(array, low, mid);
        }
        //将枢纽值放到右端值左侧
        swap(array, mid, high - 1);
    }
}
```

##### 8.3.3 三向切分算法

对于有大量重复元素的数组，可以将数组切分为三部分，分别对应小于、等于和大于切分元素。

三向切分快速排序对于有大量重复元素的随机数组可以在线性时间内完成排序。

**基本原理：**

对于每次切分：从数组的左边到右边遍历一次，维护三个指针，其中lt指针使得元素（arr[0]-arr[lt-1]）的值均小于切分元素；gt指针使得元素（arr[gt+1]-arr[N-1]）的值均大于切分元素；index指针使得元素（arr[lt]-arr[index-1]）的值均等于切分元素，（arr[index]-arr[gt]）的元素还没被扫描，切分算法执行到index>gt为止。每次切分之后，位于gt指针和lt指针之间的元素的位置都已经被排定，不需要再去处理了。之后将（lo,lt-1）,（gt+1,hi）分别作为处理左子数组和右子数组的递归函数的参数传入，递归结束，整个算法也就结束。

![](https://i.imgur.com/6tWXYdr.png)

```java
public class ThreeWay<T extends Comparable<T>> extends Sort<T> {
    /**
     * 实现Sort的sort接口
     * @param array 目标数组
     */
    @Override
    public void sort(T[] array) {
        sort(array, 0, array.length - 1);
    }

    /**
     * 递归使用三向切分快速排序算法进行排序
     * @param array 目标数组
     * @param low   左指针
     * @param high  右指针
     */
    private void sort(T[] array, int low, int high) {
        if (high <= low){
            return;
        }
        //lt:less than  gt:greater than
        int lt = low;
        int index = low + 1;
        int gt = high;
        T temp = array[low];
        while (index <= gt){
            int cmp = array[index].compareTo(temp);
            if (cmp < 0){
                swap(array, lt++, index++);
            } else if (cmp > 0){
                swap(array, index, gt--);
            } else {
                index++;
            }
        }
        sort(array, low, lt - 1);
        sort(array, gt + 1, high);
    }
}
```

#### 8.4 基于切分的快速选择算法

快速排序的 partition() 方法，会返回一个整数 j 使得 a[l..j-1] 小于等于 a[j]，且 a[j+1..h] 大于等于 a[j]，此时 a[j] 就是数组的第 j 大元素。

可以利用这个特性找出数组的第 k 个元素。

该算法是线性级别的，假设每次能将数组二分，那么比较的总次数为 (N+N/2+N/4+..)，直到找到第 k 个元素，这个和显然小于 2N。

```java
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
```

### 9.堆排序

堆中某个节点的值总是大于等于或小于等于其子节点的值，并且堆是一颗完全二叉树。

堆可以用数组来表示，这是因为堆是完全二叉树，而完全二叉树很容易就存储在数组中。位置 k 的节点的父节点位置为 k/2，而它的两个子节点的位置分别为 2k 和 2k+1。这里不使用数组索引为 0 的位置，是为了更清晰地描述节点的位置关系。

**基本原理：**

1. 构建堆：根据初始数组去构造初始堆（构建一个完全二叉树，保证所有的父结点都比它的孩子结点数值大）。
2. 每次交换第一个和最后一个元素，输出最后一个元素（最大值），然后把剩下元素重新调整为大根堆。 
3. 当输出完最后一个元素后，这个数组已经是按照从小到大的顺序排列了。

具体可以查看博客：[图解排序算法(三)之堆排序](https://www.cnblogs.com/chengxiao/p/6129630.html)

```java
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
```

**分析：**

+ 一个堆的高度为 logN，因此在堆中插入元素和删除最大元素的复杂度都为 logN。

+ 对于堆排序，由于要对 N 个节点进行下沉操作，因此复杂度为 NlogN。

+ 堆排序是一种原地排序，没有利用额外的空间。

+ 现代操作系统很少使用堆排序，因为它无法利用局部性原理进行缓存，也就是数组元素很少和相邻的元素进行比较和交换。

### 10.上述排序算法的比较

| 算法                                               | 稳定性 | 时间复杂度                  | 空间复杂度 | 备注                     |
| -------------------------------------------------- | ------ | --------------------------- | ---------- | ------------------------ |
| <a href="#3.选择排序算法">选择排序</a>             | ×      | N^2^                        | 1          |                          |
| <a href="#4.冒泡排序算法">冒泡排序</a>             | √      | N^2^                        | 1          |                          |
| <a href="#5.插入排序算法">插入排序</a>             | √      | N~N^2^                      | 1          | 时间复杂度和初始顺序有关 |
| <a href="#6.希尔排序算法">希尔排序</a>             | ×      | N的若干倍乘于递增序列的长度 | 1          | 改进版插入排序           |
| <a href="#7.归并排序算法">归并排序</a>             | √      | NlogN                       | N          |                          |
| <a href="#8.快速排序算法">快速排序</a>             | ×      | NlogN                       | logN       |                          |
| <a href="#8.3.2 三数取中算法">三数取中快速排序</a> | ×      | NlogN                       | logN       |                          |
| <a href="#8.3.3 三向切分算法">三向切分快速排序</a> | ×      | N ~ NlogN                   | logN       | 适用于有大量重复数据     |
| <a href="#9.堆排序">堆排序</a>                     | ×      | NlogN                       | 1          | 无法利用局部性原理       |

+ 排序算法的稳定性：俗地讲就是能保证排序前两个相等的数据其在序列中的先后位置顺序与排序后它们两个先后位置顺序相同。即：如，如果A i == A j，Ai 原来在 Aj 位置前，排序后 Ai 仍然是在 Aj 位置前。

## 二、JDK自带的DualPivotQuickSort

jdk版本：1.8.0_281

DualPivotQuickSort 类实现了由 Vladimir Yaroslavskiy、Jon Bentley和Josh Bloch的Dual-Pivot Quicksort算法。该算法在许多数据集上提供了O(nlog(n))的性能，这些数据集会导致其他quicksort的性能下降到二次方，并且通常比传统的（单枢轴）Quicksort实现更快。所有暴露的方法都是包私有的，设计为在执行任何必要的数组边界检查并将参数扩展为所需形式后，从公共方法（在类Arrays中）调用。

整个算法的思路是：首先检查数组的长度，比一个阈值小的时候直接使用双轴快排。其它情况下，先检查数组中数据的顺序连续性。把数组中连续升序或者连续降序的信息记录下来，顺便把连续降序的部分倒置。这样数据就被切割成一段段连续升序的数列。

以下是int类型排序的源码：

```java
final class DualPivotQuicksort {
    /**
     * 保护这个类不被实例化
     */
    private DualPivotQuicksort() {}

    /**
     * 待归并的序列的最大数量
     */
    private static final int MAX_RUN_COUNT = 67;

    /**
     * 待归并的序列的最大长度
     */
    private static final int MAX_RUN_LENGTH = 33;

    /**
     * 如果参与排序的数组长度小于这个值，优先使用快速排序而不是归并排序
     */
    private static final int QUICKSORT_THRESHOLD = 286;

    /**
     * 如果参与排序的数组长度小于这个值，优先使用插入排序而不是快速排序
     */
    private static final int INSERTION_SORT_THRESHOLD = 47;

    /**
     * 如果要排序的字节数组的长度大于这个常数，则优先使用计数排序，而不是插入排序。
     */
    private static final int COUNTING_SORT_THRESHOLD_FOR_BYTE = 29;

    /**
     * 如果要排序的短数组或char数组的长度大于这个常数，则优先使用计数排序，而不是Quicksort。
     */
    private static final int COUNTING_SORT_THRESHOLD_FOR_SHORT_OR_CHAR = 3200;

   	/**
     * 如果可能的话，使用给定的工作空间数组片对数组的指定范围进行排序，以便合并。
     *
     * @param a         目标数组
     * @param left the  第一个元素的索引，包括在内，要进行排序。
     * @param right the 要排序的数组的最后一个元素的索引，包括在内。
     * @param work 	    工作区阵列
     * @param workBase  工作阵列中可用空间的来源
     * @param workLen   工作阵列的可用长度
     */
    static void sort(int[] a, int left, int right,
                     int[] work, int workBase, int workLen) {
        // 在小数组上使用Quicksort
        if (right - left < QUICKSORT_THRESHOLD) {
            sort(a, left, right, true);
            return;
        }

        /*
         * Index run[i] is the start of i-th run
         * (ascending or descending sequence).
         */
        int[] run = new int[MAX_RUN_COUNT + 1];
        int count = 0; run[0] = left;

        // 检查数组是否接近排序
        for (int k = left; k < right; run[count] = k) {
            if (a[k] < a[k + 1]) { // 升序
                while (++k <= right && a[k - 1] <= a[k]);
            } else if (a[k] > a[k + 1]) { // 降序
                while (++k <= right && a[k - 1] >= a[k]);
                for (int lo = run[count] - 1, hi = k; ++lo < --hi; ) {
                    int t = a[lo]; a[lo] = a[hi]; a[hi] = t;
                }
            } else { // equal
                for (int m = MAX_RUN_LENGTH; ++k <= right && a[k - 1] == a[k]; ) {
                    if (--m == 0) {
                        sort(a, left, right, true);
                        return;
                    }
                }
            }

            /*
             * 数组结构不高，用Quicksort代替合并排序。
             */
            if (++count == MAX_RUN_COUNT) {
                sort(a, left, right, true);
                return;
            }
        }

        // 检查特殊情况
        // 实施说明：变量 "right "增加1。
        if (run[count] == right++) { // The last run contains one element
            run[++count] = right;
        } else if (count == 1) { // The array is already sorted
            return;
        }

        // 确定合并的交替基础
        byte odd = 0;
        for (int n = 1; (n <<= 1) < count; odd ^= 1);

        // 使用或创建临时数组b进行合并
        int[] b;                 // temp array; alternates with a
        int ao, bo;              // array offsets from 'left'
        int blen = right - left; // space needed for b
        if (work == null || workLen < blen || workBase + blen > work.length) {
            work = new int[blen];
            workBase = 0;
        }
        if (odd == 0) {
            System.arraycopy(a, left, work, workBase, blen);
            b = a;
            bo = 0;
            a = work;
            ao = workBase - left;
        } else {
            b = work;
            ao = 0;
            bo = workBase - left;
        }

        // Merging
        for (int last; count > 1; count = last) {
            for (int k = (last = 0) + 2; k <= count; k += 2) {
                int hi = run[k], mi = run[k - 1];
                for (int i = run[k - 2], p = i, q = mi; i < hi; ++i) {
                    if (q >= hi || p < mi && a[p + ao] <= a[q + ao]) {
                        b[i + bo] = a[p++ + ao];
                    } else {
                        b[i + bo] = a[q++ + ao];
                    }
                }
                run[++last] = hi;
            }
            if ((count & 1) != 0) {
                for (int i = right, lo = run[count - 1]; --i >= lo;
                    b[i + bo] = a[i + ao]
                );
                run[++last] = right;
            }
            int[] t = a; a = b; b = t;
            int o = ao; ao = bo; bo = o;
        }
    }
        
    /**
     * 使用双轴快速排序给指定数组的指定范围排序
     *
     * @param          目标数组
     * @param left     范围内最左边的元素的位置(包括该元素)
     * @param right    范围内最右边的元素的位置(包括该元素)
     * @param leftmost 指定的范围是否在数组的最左边
     */
    private static void sort(int[] a, int left, int right, boolean leftmost) {
        int length = right - left + 1;

        //小数组使用插入排序
        if (length < INSERTION_SORT_THRESHOLD) {
            if (leftmost) {
                /*
                 * 传统的（没有哨兵）插入排序，针对服务器虚拟机进行了优化，在最左边的情况下使用。
                 */
                for (int i = left, j = i; i < right; j = ++i) {
                    int ai = a[i + 1];
                    while (ai < a[j]) {
                        a[j + 1] = a[j];
                        if (j-- == left) {
                            break;
                        }
                    }
                    a[j + 1] = ai;
                }
            } else {
                /*
                 * 跳过开头的升序。
                 */
                do {
                    if (left >= right) {
                        return;
                    }
                } while (a[++left] >= a[left - 1]);

                /*
                 * 来自相邻部分的每个元素都扮演着哨兵的角色，因此，这使得我们可以避免
                 * 每次迭代的左范围检查。此外，我们使用了更优化的算法，即所谓的成对插入
                 * 排序，它比传统的插入排序的实现更快（在Quicksort的上下文中）。
                 */
                for (int k = left; ++left <= right; k = ++left) {
                    int a1 = a[k], a2 = a[left];

                    if (a1 < a2) {
                        a2 = a1; a1 = a[left];
                    }
                    //先把两个数字中较大的那个移动到合适的位置
                    while (a1 < a[--k]) {
                        //这里每次需要向左移动两个元素
                        a[k + 2] = a[k];
                    }
                    a[++k + 1] = a1;
				  //再把两个数字中较小的那个移动到合适的位置
                    while (a2 < a[--k]) {
                        //这里每次需要向左移动一个元素
                        a[k + 1] = a[k];
                    }
                    a[k + 1] = a2;
                }
                int last = a[right];

                while (last < a[--right]) {
                    a[right + 1] = a[right];
                }
                a[right + 1] = last;
            }
            return;
        }

        // length / 7的一种低复杂度实现
        int seventh = (length >> 3) + (length >> 6) + 1;

        /*
         * 在范围内的中心元素周围（并包括中心元素）排序五个均匀间隔的元素。
         * 这些元素将用于下文所述的枢轴选择。这些元素间距的选择是根据经验确定的，
         * 对各种输入都能很好地发挥作用。
         */
        // 中间值
        int e3 = (left + right) >>> 1; 
        int e2 = e3 - seventh;
        int e1 = e2 - seventh;
        int e4 = e3 + seventh;
        int e5 = e4 + seventh;

        // 对这些元素使用插入排序
        if (a[e2] < a[e1]) { int t = a[e2]; a[e2] = a[e1]; a[e1] = t; }

        if (a[e3] < a[e2]) { int t = a[e3]; a[e3] = a[e2]; a[e2] = t;
            if (t < a[e1]) { a[e2] = a[e1]; a[e1] = t; }
        }
        if (a[e4] < a[e3]) { int t = a[e4]; a[e4] = a[e3]; a[e3] = t;
            if (t < a[e2]) { a[e3] = a[e2]; a[e2] = t;
                if (t < a[e1]) { a[e2] = a[e1]; a[e1] = t; }
            }
        }
        if (a[e5] < a[e4]) { int t = a[e5]; a[e5] = a[e4]; a[e4] = t;
            if (t < a[e3]) { a[e4] = a[e3]; a[e3] = t;
                if (t < a[e2]) { a[e3] = a[e2]; a[e2] = t;
                    if (t < a[e1]) { a[e2] = a[e1]; a[e1] = t; }
                }
            }
        }

        //指针
        int less  = left;  // 中间区域首个元素的下标
        int great = right; // 右边区域首个元素的下标

        if (a[e1] != a[e2] && a[e2] != a[e3] && a[e3] != a[e4] && a[e4] != a[e5]) {
            /*
             * 使用五个排序元素中的第二个和第四个作为支点。
             * 这些值是数组的第一和第二特码的廉价近似值。
             * 请注意，pivot1 <= pivot2。
             */
            int pivot1 = a[e2];
            int pivot2 = a[e4];

            /*
             * 要排序的第一个和最后一个元素被移动到以前被枢轴占用的位置。
             * 分割完成后，将枢轴换回其最终位置，并将其排除在后续排序之外。
             */
            a[e2] = a[left];
            a[e4] = a[right];

            /*
             * 跳过一些队首的小于pivot1的值，跳过队尾的大于pivot2的值
             */
            while (a[++less] < pivot1);
            while (a[--great] > pivot2);

            /*
             * Partitioning:
             *
             *   left part           center part                   right part
             * +--------------------------------------------------------------+
             * |  < pivot1  |  pivot1 <= && <= pivot2  |    ?    |  > pivot2  |
             * +--------------------------------------------------------------+
             *               ^                          ^       ^
             *               |                          |       |
             *              less                        k     great
             *
             * Invariants:
             *
             *              all in (left, less)   < pivot1
             *    pivot1 <= all in [less, k)     <= pivot2
             *              all in (great, right) > pivot2
             *
             * Pointer k is the first index of ?-part.
             */
            outer:
            for (int k = less - 1; ++k <= great; ) {
                int ak = a[k];
                // 将a[k]移动到左部区域
                if (ak < pivot1) { 
                    a[k] = a[less];
                    /*
                     * Here and below we use "a[i] = b; i++;" instead
                     * of "a[i++] = b;" due to performance issue.
                     */
                    a[less] = ak;
                    ++less;
                // 移动 a[k]到右侧区域
                } else if (ak > pivot2) { 
                    while (a[great] > pivot2) {
                        if (great-- == k) {
                            break outer;
                        }
                    }
                    // a[great] <= pivot2
                    if (a[great] < pivot1) { 
                        a[k] = a[less];
                        a[less] = a[great];
                        ++less;
                    } else { // pivot1 <= a[great] <= pivot2
                        a[k] = a[great];
                    }
                    /*
                     * Here and below we use "a[i] = b; i--;" instead
                     * of "a[i--] = b;" due to performance issue.
                     */
                    a[great] = ak;
                    --great;
                }
            }

            // 交换他们最后的下标
            a[left]  = a[less  - 1]; a[less  - 1] = pivot1;
            a[right] = a[great + 1]; a[great + 1] = pivot2;

            // 递归地对左右两部分进行排序，排除已知的支点。
            sort(a, left, less - 2, leftmost);
            sort(a, great + 2, right, false);

            /*
             * If center part is too large (comprises > 4/7 of the array),
             * swap internal pivot values to ends.
             * 如果中心区域太大，超过数组长度的 4/7。就先进行预处理，再参与递归排序。
             * 预处理的方法是把等于pivot1的元素统一放到左边，等于pivot2的元素统一
             * 放到右边,最终产生一个不包含pivot1和pivot2的数列，再拿去参与快排中的递归。
             */
            if (less < e1 && e5 < great) {
                /*
                 * 跳过等于枢轴值的元素。
                 */
                while (a[less] == pivot1) {
                    ++less;
                }

                while (a[great] == pivot2) {
                    --great;
                }

                /*
                 * Partitioning:
                 *
                 *   left part         center part                  right part
                 * +----------------------------------------------------------+
                 * | == pivot1 |  pivot1 < && < pivot2  |    ?    | == pivot2 |
                 * +----------------------------------------------------------+
                 *              ^                        ^       ^
                 *              |                        |       |
                 *             less                      k     great
                 *
                 * Invariants:
                 *
                 *              all in (*,  less) == pivot1
                 *     pivot1 < all in [less,  k)  < pivot2
                 *              all in (great, *) == pivot2
                 *
                 * Pointer k is the first index of ?-part.
                 */
                outer:
                for (int k = less - 1; ++k <= great; ) {
                    int ak = a[k];
                    if (ak == pivot1) { // Move a[k] to left part
                        a[k] = a[less];
                        a[less] = ak;
                        ++less;
                    } else if (ak == pivot2) { // Move a[k] to right part
                        while (a[great] == pivot2) {
                            if (great-- == k) {
                                break outer;
                            }
                        }
                        if (a[great] == pivot1) { // a[great] < pivot2
                            a[k] = a[less];
                            /*
                             * 尽管a[great]等于pivot1，但如果a[great]和
                             * pivot1是不同符号的浮点零点，那么a[less]=pivot1
                             * 的赋值可能是错误的。因此在浮点数和双数排序方法中，
                             * 我们必须使用更准确的赋值a[less] = a[great]。
                             */
                            a[less] = pivot1;
                            ++less;
                        } else { // pivot1 < a[great] < pivot2
                            a[k] = a[great];
                        }
                        a[great] = ak;
                        --great;
                    }
                }
            }

            // 中心部分递归排序
            sort(a, less, great, false);

        } else { // 用一个枢轴进行分区
            /*
             * 使用五个排序元素中的第三个作为中枢。
			* 这个值是中位数的廉价近似值。
             */
            int pivot = a[e3];

            /*
             * Partitioning degenerates to the traditional 3-way
             * (or "Dutch National Flag") schema:
             *
             *   left part    center part              right part
             * +-------------------------------------------------+
             * |  < pivot  |   == pivot   |     ?    |  > pivot  |
             * +-------------------------------------------------+
             *              ^              ^        ^
             *              |              |        |
             *             less            k      great
             *
             * Invariants:
             *
             *   all in (left, less)   < pivot
             *   all in [less, k)     == pivot
             *   all in (great, right) > pivot
             *
             * Pointer k is the first index of ?-part.
             */
            for (int k = less; k <= great; ++k) {
                if (a[k] == pivot) {
                    continue;
                }
                int ak = a[k];
                if (ak < pivot) { // Move a[k] to left part
                    a[k] = a[less];
                    a[less] = ak;
                    ++less;
                } else { // a[k] > pivot - Move a[k] to right part
                    while (a[great] > pivot) {
                        --great;
                    }
                    if (a[great] < pivot) { // a[great] <= pivot
                        a[k] = a[less];
                        a[less] = a[great];
                        ++less;
                    } else { // a[great] == pivot
                        /*
                         * 尽管a[great]等于pivot，但如果a[great]和pivot是不同符号
                         * 的浮点零点，那么a[k]=pivot的赋值可能是错误的。因此在浮点数
                         * 和双数排序方法中，我们必须使用更准确的赋值a[k] = a[great]。
                         */
                        a[k] = pivot;
                    }
                    a[great] = ak;
                    --great;
                }
            }

            /*
             * 左右部分递归排序。从中心部分开始的所有元素都是相等的，因此，已经进行了排序。
             */
            sort(a, left, less - 1, leftmost);
            sort(a, great + 1, right, false);
        }
    }
}
```



## 三、学习参考：

1. [算法-排序](https://www.cyc2018.xyz/%E7%AE%97%E6%B3%95/%E5%9F%BA%E7%A1%80/%E7%AE%97%E6%B3%95%20-%20%E6%8E%92%E5%BA%8F.html#%E5%BD%92%E5%B9%B6%E6%8E%92%E5%BA%8F)
2. [图解排序算法(二)之希尔排序](https://www.cnblogs.com/chengxiao/p/6104371.html)
3. [图解排序算法(四)之归并排序](https://www.cnblogs.com/chengxiao/p/6194356.html)
4. [自底向上的归并排序](https://www.cnblogs.com/ningvsban/p/3789479.html)
5. [图解排序算法(五)之快速排序——三数取中法](https://www.cnblogs.com/chengxiao/p/6262208.html)
6. [图解排序算法(三)之堆排序](https://www.cnblogs.com/chengxiao/p/6129630.html)
7. [常用算法稳定性分析](https://blog.csdn.net/weixin_41552752/article/details/90410749?spm=1001.2014.3001.5501)
8. [常用排序算法稳定性、时间复杂度分析](https://www.cnblogs.com/dion-90/articles/8547688.html)

