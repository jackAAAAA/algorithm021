# 学习笔记
* 字典树基本结构

    * 字典树，即 Trie 树，又称单词查找树或键树，是一种树形结构。典型应用是用于统计和排序大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。

    * 优点：最大限度地减少无谓的字符串比较，查询效率比哈希表高。

* 基本性质：

    * 结点本身不存完整单词；

    * 从根结点到某一结点，路径上经过的字符连接起来，为该结点对应的字符串；

    * 每个结点的所有子结点路径代表的字符都不相同。

* 核心思想：空间换时间。

    * 利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。
    ![image_1](https://user-images.githubusercontent.com/37928802/104834383-e058de00-58d9-11eb-99d1-ca3280393b58.png)

Trie树代码模板：https://shimo.im/docs/DP53Y6rOwN8MTCQH/read
    
```ruby
class Trie {   
    private boolean isEnd;    
    private Trie[] next;
        
    /** Initialize your data structure here. */    
    public Trie() {        
        isEnd = false;        
        next = new Trie[26];    
    }  
          
    /** Inserts a word into the trie. */
    public void insert(String word) {        
        if (word == null || word.length() == 0) return;        
        Trie curr = this;        
        char[] words = word.toCharArray();  
        for (int i = 0;i < words.length;i++) {            
            int n = words[i] - 'a';
            if (curr.next[n] == null) 
                curr.next[n] = new Trie();curr = curr.next[n];        
        }        
        curr.isEnd = true;    
    } 
           
    /** Returns if the word is in the trie. */
    public boolean search(String word) {        
        Trie node = searchPrefix(word);return node != null && node.isEnd;    
    }   
         
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix)    {        
        Trie node = searchPrefix(prefix);
        return node != null;    
    }    
    private Trie searchPrefix(String word) {        
        Trie node = this;        
        char[] words = word.toCharArray();        
        for (int i = 0;i < words.length;i++) {            
            node = node.next[words[i] - 'a'];            
            if (node == null) return null;        
        }        
        return node;    
    }
}

```

# 并查集
适用场景：组团、配对问题
* 基本操作
    * makeSet(s)：建立一个新的并查集，其中包含 s 个单元素集合。
    * unionSet(x, y)：把元素 x 和元素 y 所在的集合合并，要求 x 和 y 所在的集合不相交，如果相交则不合并。
    * find(x)：找到元素 x 所在的集合的代表，该操作也可以用于判断两个元素是否位于同一个集合，只要将它们各自的代表比较一下就可以了。
    
代码模板：https://shimo.im/docs/VtcxL0kyp04OBHak/read

···ruby
class UnionFind {
    private int count = 0;
    private int[] parent;

    public UnionFind(int n) {
        count = n;
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        parent[rootP] = rootQ;
        count--;
    }
}

···

# 双向BFS代码模板
根据单词接龙进行默写
```ruby
public int ladderLength(String beginWord, String endWord, List<String> wordList1) {
    // List的查询复杂度为O(n) 转为HashSet,查询复杂度为O(1)
    Set<String> wordList = new HashSet<String>(wordList1);
    //定义begin end
    Set<String> beginSet = new HashSet<String>();
    Set<String> endSet = new HashSet<String>();

    int len = 1;
    int strLen = beginWord.length();
    //定义visited
    Set<String> visited = new HashSet<String>();

    beginSet.add(beginWord);
    endSet.add(endWord);
    while (!beginSet.isEmpty() && !endSet.isEmpty()) {
        //从比较小的set开始遍历
        if (beginSet.size() > endSet.size()) {
            Set<String> tmp = beginSet;
            beginSet = endSet;
            endSet = tmp;
        }

        Set<String> temp = new HashSet<String>();
        for (String word : beginSet) {
            char[] chs = word.toCharArray();

            for (int i = 0; i < chs.length; i++) {
                for (char c = 'a'; c <= 'z'; c++) {
                    char old = chs[i];
                    chs[i] = c;
                    String target = String.valueOf(chs);

                    //如果与endSet重合，则找到目标元素
                    if (endSet.contains(target)) {
                        return len + 1;
                    }

                    //如果没有，则继续下一层搜索
                    if (!visited.contains(target) && wordList.contains(target)) {
                        temp.add(target);
                        visited.add(target);
                    }
                    chs[i] = old;
                }
            }
        }
        beginSet = temp;
        len++ ;
    }

    return  0;
}

```
# 高级树、AVL 树和红黑树
## AVL树
1. 发明者 G. M. Adelson-Velsky和 Evgenii Landis
2. Balance Factor（平衡因子）：是它的左子树的高度减去它的右子树的高度（有时相反）。balance factor = {-1, 0, 1}
3. 通过旋转操作来进行平衡（四种）：左旋、右旋、左右旋、右左旋。
4. https://en.wikipedia.org/wiki/Self-balancing_binary_search_tree
5. 不足：结点需要存储额外信息、且调整次数频繁

![image_2](https://user-images.githubusercontent.com/37928802/104835147-8d822500-58df-11eb-83c5-181d2ac02b61.png)

# 红黑树
红黑树是一种近似平衡的二叉搜索树（BinarySearch Tree），它能够确保任何一个结点的左右子树的高度差小于两倍。具体来说，红黑树是满足如下条件的二叉搜索树：
* 每个结点要么是红色，要么是黑色
* 根结点是黑色
* 每个叶结点（NIL结点，空结点）是黑色的。
* 不能有相邻接的两个红色结点
* 从任一结点到其每个叶子的所有路径都包含相同数目的黑色结点。
* 关键性质：从根到叶子的最长的可能路径不多于最短的可能路径的两倍长。

![image_3](https://user-images.githubusercontent.com/37928802/104835185-d20dc080-58df-11eb-9e2f-490940113692.png)
