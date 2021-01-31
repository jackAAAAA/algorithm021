# 学习笔记
## 高级动态规划
### 递归
``` ruby
public void recur(int level, int param) {
    // terminator 1 递归终止条件
    if (level > MAX_LEVEL) {
        // process result
        return;
    }
    // process current logic 2 处理当前层逻辑
    process(level, param);
    // drill down  3 到下一层
    recur( level: level + 1, newParam);
    // restore current status 4 清理当前层
}
```
### 分治
``` ruby
private static int divide_conquer(Problem problem, ) {
  
    if (problem == NULL) {
        int res = process_last_result();
        return res;     
    }
    subProblems = split_problem(problem)
  
    res0 = divide_conquer(subProblems[0])
    res1 = divide_conquer(subProblems[1])
  
     result = process_result(res0, res1);
  
    return result;
}
```

* 感触
1. 人肉递归低效、很累
2. 找到最近最简方法，将其拆解成可重复解决的问题
3. 数学归纳法思维

* 本质：寻找重复性 —> 计算机指令集

## 动态规划
* 关键点
    * 动态规划 和 递归或者分治 没有根本上的区别（关键看有无最优的子结构） 拥有共性：找到重复子问题
    * 差异性：最优子结构、中途可以淘汰次优解

## 常见的DP题目和状态方程
1. 爬楼梯 f(n) = f(n - 1) + f(n - 2) , f(1) = 1, f(0) = 0
2. 不同路径 f(x, y) = f(x-1, y) + f(x, y-1)
3. 打家劫舍
``` ruby
dp[i]状态的定义： max $ of robbing A[0 -> i]
dp[i] = max(dp[i - 2] + nums[i], dp[i - 1])
dp[i][0]状态定义：max $ of robbing A[0 -> i] 且没偷 nums[i]
dp[i][1]状态定义：max $ of robbing A[0 -> i] 且偷了 nums[i]
dp[i][0] = max(dp[i - 1][0], dp[i - 1][1]);
dp[i][1] = dp[i - 1][0] + nums[i];
```
4. 最小路径和
``` ruby
dp[i][j]状态的定义： minPath(A[1 -> i][1 -> j])
dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + A[i][j]
```
5. 股票买卖
``` ruby
dp[i][k][0 or 1] (0 <= i <= n-1, 1 <= k <= K)
• i 为天数
• k 为最多交易次数
• [0,1] 为是否持有股票
总状态数： n * K * 2 种状态
for 0 <= i < n:
for 1 <= k <= K:
for s in {0, 1}:
dp[i][k][s] = max(buy, sell, rest)
```
* 高阶DP问题
    * 复杂度来源
    1. 状态拥有更多维度（二维、三维、或者更多、甚至需要压缩）
    2. 状态方程更加复杂

## 字符串
### 相关算法
1. atoi(ascii to integer)将字符串转换成整型数
``` ruby
public static int myAtoi(String str){
    int index = 0,sign = 1,total = 0;
    //1. 空串
    if (str.length() == 0) {
        return  0;
    }
    //2. 移除空格
    while(str.charAt(index) == ' ' && index < str.length()){
        index++;
    }
    //3. 处理标志
    if (str.charAt(index) == '+' || str.charAt(index) == '-'){
        sign = str.charAt(index) == '+' ? 1 : -1;
        index++;
    }
    //4. 转换数字同时避免溢出
    while (index<str.length()){
        int digit = str.charAt(index) - '0';
        if (digit < 0 || digit>9){
            break;
        }

        //检查total，并添加数字
        if(Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE%10 < digit){
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        }
        total = 10 * total + digit;
        index++;
    }
    return total * sign;
}
```
2. 字符串匹配算法
    1. 暴力法 (brute force）- O(mn))
    ![image](https://camo.githubusercontent.com/a32c58292857021957b6929c4a322cef3f856b6751ad98606c14d5fb07ec4c9c/68747470733a2f2f67697465652e636f6d2f646f6e673631382f626c6f672f7261772f6d61737465722f696d672f3230323130312f32303231303132373137313034372e706e67)

    2. Rabin-Karp 算法
        * 算法的思想：
            * 假设子串的长度为M (pat), 目标字符串的长度为N (txt)
            * 计算子串的hash值hash_pat
            * 计算目标字符串txt中每个长度为M的子串的hash值(共需要计算N-M+1次)
            * 比较hash值：如果hash值不同，字符串必然不匹配；如果hash值相同，还需要使用朴素算法再次判断
    
    3. KMP算法
    ``` ruby
    KMP算法(Knuth-Morris-Pratt)的思想就是,当子串与目标字符串不匹配时,其实你已经知道了前面已经匹配成功那一部分的字符(包括子串与目标字符串)。以阮一峰的文章为例,当空格与D不匹配时,你其实知道前面六个字符是"ABCDAB" .KMP算法的想法是,设法利用这个已知信息,不要把"搜索位置"移回已经比较过的位置,继续把它向后移,这样就提高了效率。
    ```

    ## 不同路径II 状态转移方程
    ``` ruby
    二维数组方式
    dp[0][0] = obstacleGrid[0][0] == 1 ? 0 : 1;
    dp[i][0] = obstacleGrid[i][0] == 1 ? 0 : dp[i - 1][0]; (i > 0 && j == 0)
    dp[0][j] = obstacleGrid[0][j] == 1 ? 0 : dp[j - 1];(i == 0 && j > 0)
    dp[i][j] = obstacleGrid[i][j] == 1 ? 0 : dp[i - 1][j] + dp[i][j - 1]; (i > 0 && j > 0)

    一维数组方式
    dp[0] = obstacleGrid[0][0] == 1 ? 0 : 1;
    dp[0] = obstacleGrid[i][0] == 1 ? 0 : dp[0];(j > 0)
    dp[j] = obstacleGrid[i][j] == 1 ? 0 : dp[j] + dp[j - 1];(j > 0)
    ```
