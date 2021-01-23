# 学习笔记
## 位运算
* 位运算

|含义  |运算符  |示例  |
| :---: | :---: | :---: |
|左移  |<<  |0011左移1位 =>0110  |
|右移  |>>  |0110右移1位 =>0011  |
|按位或（有一个为1时，结果位就为1）|I |0011 1011 =>1011  |
|按位与（都为1时，结果位才为1） |&  |0011 1011 =>0011  |
|按位取反  |~  |0011=>1100  |
|按位异或（相同为0，相异为1）|^  |0011 1011 =>1000  |

* 异或操作的一些特点： x ^ 0 = x x ^ 1s = ~x // 注意 1s = ~0 (全为1) x ^ (~x) = 1s x ^ x = 0 c = a ^ b => a ^ c = b, b ^ c = a // 交换两个数 a ^ b ^ c = a ^ (b ^ c) = (a ^ b) ^ c // associative

* 指定位置地位运算
1. 将 x 最右边的 n 位清零：x & (~0 << n)
2. 获取 x 的第 n 位值（0 或者 1）： (x >> n) & 1
3. 获取 x 的第 n 位的幂值：x & (1 << n)
4. 仅将第 n 位置为 1：x | (1 << n)
5. 仅将第 n 位置为 0：x & (~ (1 << n))
6. 将 x 最高位至第 n 位（含）清零：x & ((1 << n) - 1)

* 实战位运算要点
    * 判断奇偶：
        * x % 2 == 1 —> (x & 1) == 1​ x % 2 == 0 —> (x & 1) == 0
    * x >> 1 —> x / 2. 即： x = x / 2; —> x = x >> 1; mid = (left + right) / 2; —> mid = (left + right) >> 1;
    * X = X & (X-1) 清零最低位的 1
    * X & -X => 得到最低位的 1
    * X & ~X => 0

# 布隆过滤器
* 示意图
![image_1](https://user-images.githubusercontent.com/37928802/105571056-dde9fe80-5d87-11eb-8724-b24921f60bc6.png)

* Bloom Filter vs Hash Table
一个很长的二进制向量和一系列随机映射函数。布隆过滤器可以用于检索一个元素是否在一个集合中。 优点是空间效率和查询时间都远远超过一般的算法， 缺点是有一定的误识别率和删除困难。
![image_2](https://user-images.githubusercontent.com/37928802/105571104-1c7fb900-5d88-11eb-8503-fb4c2f35bd6d.png)
* 案例：
1. 比特币网络
2. 分布式系统（Map-Reduce） —  Hadoop、search engine
3. Redis 缓存
4. 垃圾邮件、评论等的过滤
* 参考链接
    * [布隆过滤器的原理和实现](https://www.cnblogs.com/cpselvis/p/6265825.html)
    * [使用布隆过滤器解决缓存击穿、垃圾邮件识别、集合判重](https://blog.csdn.net/tianyaleixiaowu/article/details/74721877)
    * [布隆过滤器 Python 代码示例](https://shimo.im/docs/UITYMj1eK88JCJTH/read)
    * [布隆过滤器 Python 实现示例](https://www.geeksforgeeks.org/bloom-filters-introduction-and-python-implementation/)
    * [高性能布隆过滤器 Python 实现示例](https://github.com/jhgg/pybloof)
    * [布隆过滤器 Java 实现示例 1](https://github.com/lovasoa/bloomfilter/blob/master/src/main/java/BloomFilter.java)
    * [布隆过滤器 Java 实现示例 2](https://github.com/Baqend/Orestes-Bloomfilter)

# LRU Cache
Cache 缓存

1. 记忆
2. 钱包 - 储物柜
3. 代码模块
4. 两个要素： 大小 、替换策略
5. Hash Table + Double LinkedList
6. O(1) 查询 O(1) 修改、更新

![image](https://user-images.githubusercontent.com/37928802/105571286-d7f51d00-5d89-11eb-9ee7-34f7e6eb55b9.png)

* 参考链接
    * [Understanding the Meltdown exploit](https://www.sqlpassion.at/archive/2018/01/06/understanding-the-meltdown-exploit-in-my-own-simple-words/)
    * [替换算法总揽](https://en.wikipedia.org/wiki/Cache_replacement_policies)
    * [LRU Cache Python 代码示例](https://shimo.im/docs/CoyPAyXooGcDuLQo/read)

```ruby
class LRUCache {
    /**
     * 缓存映射表
     */
    private Map<Integer, DLinkNode> cache = new HashMap<>();
    /**
     * 缓存大小
     */
    private int size;
    /**
     * 缓存容量
     */
    private int capacity;
    /**
     * 链表头部和尾部
     */
    private DLinkNode head, tail;

    public LRUCache(int capacity) {
        //初始化缓存大小，容量和头尾节点
        this.size = 0;
        this.capacity = capacity;
        head = new DLinkNode();
        tail = new DLinkNode();
        head.next = tail;
        tail.prev = head;
    }

    /**
     * 获取节点
     * @param key 节点的键
     * @return 返回节点的值
     */
    public int get(int key) {
        DLinkNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        //移动到链表头部
        (node);
        return node.value;
    }

    /**
     * 添加节点
     * @param key 节点的键
     * @param value 节点的值
     */
    public void put(int key, int value) {
        DLinkNode node = cache.get(key);
        if (node == null) {
            DLinkNode newNode = new DLinkNode(key, value);
            cache.put(key, newNode);
        //添加到链表头部
            addToHead(newNode);
            ++size;
        //如果缓存已满，需要清理尾部节点
            if (size > capacity) {
                DLinkNode tail = removeTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            node.value = value;
           //移动到链表头部
            moveToHead(node);
        }
    }

    /**
     * 删除尾结点
     *
     * @return 返回删除的节点
     */
    private DLinkNode removeTail() {
        DLinkNode node = tail.prev;
        removeNode(node);
        return node;
    }

    /**
     * 删除节点
     * @param node 需要删除的节点
     */
    private void removeNode(DLinkNode node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    /**
     * 把节点添加到链表头部
     *
     * @param node 要添加的节点
     */
    private void addToHead(DLinkNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * 把节点移动到头部
     * @param node 需要移动的节点
     */
    private void moveToHead(DLinkNode node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * 链表节点类
     */
    private static class DLinkNode {
        Integer key;
        Integer value;
        DLinkNode prev;
        DLinkNode next;

        DLinkNode() {
        }

        DLinkNode(Integer key, Integer value) {
            this.key = key;
            this.value = value;
        }
    }
}
```
# 排序
## 排序算法
1. 比较类排序： 通过比较来决定元素间的相对次序，由于其时间复杂度不能突破O(nlogn)，因此也称为非线性时间比较类排序。
2. 非比较类排序： 不通过比较来决定元素间的相对次序，它可以突破基于比较排序的时间下界，以线性时间运行，因此也称为线性时间非比较类排序。
![image_3](https://user-images.githubusercontent.com/37928802/105571434-b5173880-5d8a-11eb-9572-30e7f0beced3.png)
3. 时间复杂度
![image_4](https://user-images.githubusercontent.com/37928802/105571550-b6953080-5d8b-11eb-9df3-1dc1fc8b7a5c.png)

## 参考链接
* [十大经典排序算法](https://www.cnblogs.com/onepixel/p/7674659.html)
* [9种经典排序算法可视化动画](https://www.bilibili.com/video/av25136272)
* [6分钟看完15种排序算法动画展示](https://www.bilibili.com/video/av63851336)

```ruby
    /**
     * @author dongxujiao
     * @description 选择排序
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 以此类推，直到所有元素均排序完毕。
     *
     * @date 2021-01-22 11:10
     * @param arr
     * @return int[]
     */
    public static int[] selectionSort(int[] arr){
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = tmp;
        }
        System.out.println(Arrays.toString(arr));
        return arr;
    }

    /**
     * @author dongxujiao
     * @description 插入排序
     * 首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 以此类推，直到所有元素均排序完毕。
     *
     * @date 2021-01-22 11:15
     * @param arr
     * @return int[]
     */
    public static int[] insertionSort(int[] arr){
        int len = arr.length;
        for (int i = 1; i < len ; i++) {
            int preIndex = i - 1;
            int cur = arr[i];
            while (preIndex >= 0 && arr[preIndex]>cur) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex -- ;
            }
            arr[preIndex + 1] = cur;
        }
        System.out.println(Arrays.toString(arr));
        return arr;
    }



    /**
     * @author dongxujiao
     * @description 冒泡排序
     * 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * 针对所有的元素重复以上的步骤，除了最后一个；
     * 重复步骤1~3，直到排序完成。
     *
     * @date 2021-01-22 11:20
     * @param arr
     * @return int[]
     */
    public static int[] bubbleSort(int[] arr){
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - i -1; j++) {
                if (arr[j] > arr[j+1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
        return arr;
    }
```


