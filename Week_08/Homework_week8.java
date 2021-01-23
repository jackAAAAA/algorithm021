public class Homework_week8 {

    //Number of 1 Bits
    public int hammingWeight(int n) {

        //method_1
        // int count = 0;
        // while (n != 0) {
        //     n &= (n - 1);
        //     ++count;
        // }
        // return count;

        //method_2
        return n == 0 ? 0 : 1 + hammingWeight(n & (n-1));

    }

    //Power of Two
    public boolean isPowerOfTwo(int n) {

        //method1:n-posive integer or negative integer
        // if (n == 0) return false;
        // while (n % 2 == 0) n /= 2;
        // return n == 1;

        //method2:n-Only positive integer
        return n > 0 && (n & (n-1)) == 0;

    }



}
