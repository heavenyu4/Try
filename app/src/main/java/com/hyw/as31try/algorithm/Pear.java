package com.hyw.as31try.algorithm;

/*
猴子第一天摘了n个桃，当即吃下所有桃的一半，又多吃一个；以后每天都吃前一天剩下的一半，多一个；第10天时，发现只剩1个；问第一天摘多少个桃?

代码
解析:
第1天的桃子数量，等于第2天的桃子数量加1然后乘以2;
第2天的桃子数量，等于第3天的桃子数量加1然后乘以2;
第3天的桃子数量，等于第4天的桃子数量加1然后乘以2;
…
第十天桃子的数量已知为1个.
很明显，前一天的桃子数量等于后一天的数量加1然后乘以2
————————————————
版权声明：本文为CSDN博主「DYSLzx」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/weixin_44154163/article/details/109061865
 */
class Pear {
    public static void main(String[] args) {
        countTotal();
    }

    /**
     * 已知总数, 算吃几天
     */
    private static void theTotal() {
        int N = 27;
        int mid;
        int day = 0;
        int tanchiCnt = 1;  //贪吃的量
        int sum = N;
        while (true) {
            day++;
            mid = (sum) / 2;
            int eat;
            if (mid == 0) { //最后一天
                eat = sum;
            } else {
                eat = mid + tanchiCnt;
            }
            if (eat == 0) {
                break;
            }
            System.out.println("第" + day + "天: " + "总共" + sum + "个梨" + " 吃掉" + eat + "梨");
            System.out.println();
            sum = sum - eat;
        }
    }

    /*
     已知吃了10天, 最后剩1个, 计算总数是多少
     猴子第一天摘了n个桃，当即吃下所有桃的一半，又多吃一个；以后每天都吃前一天剩下的一半，多一个；第10天时，发现只剩1个；问第一天摘多少个桃?
     */
    private static void countTotal() {

        int n = 1;//最后一天只剩1个桃子
        //第十天还没吃就省了一个桃子,只吃了九天循环九次就可以了.
        for (int i = 0; i < 9; i++) {
            n = (n + 1) * 2;
            System.out.println((9 - i) + " : " + n);
        }
        System.out.println("sum:" + n);
    }
}
