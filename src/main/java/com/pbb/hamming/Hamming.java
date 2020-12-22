package com.pbb.hamming;


import java.util.ArrayList;


public class Hamming {
    // 用以存海明码
    static int[] hamming;

    // 明文在海明码中的位置映射关系
    static ArrayList<Integer> mapping;

    // P - 接受方可以通过 P 得知一位错在哪
    static ArrayList<Integer> P;

    // 奇偶校验位
    static int even, flag = 0, tail;

    static String position;

    /**
     * 初始化映射，第一个明文映射到第三个位置，第二个明文映射到第五个位置...
     *
     * @param len 明文长度
     */
    static void initmapping(int len) {
        mapping = new ArrayList<>();
        int count = 3;
        int index = 2;
        for (int i = 1; i <= len; i++) {
            if (count == (int) Math.pow(2, index)) {
                count++;
                index++;
            }
            mapping.add(count);
            count++;
        }
    }

    /**
     * 获得 k 值，即 P 的个数
     *
     * @param origin 编码前的原文
     * @return k 值
     */
    static Integer getK(String origin) {
        int len = origin.length();
        if (len == 1) return 2;
        else if (len == 2) return 3;
        double tmp = Math.ceil(Math.log(len) / Math.log(2));
        if (tmp + len + 1 <= Math.pow(2, tmp))
            return (int) tmp;
        return (int) tmp + 1;
    }

    /**
     * 获得在解码中的 k 值
     *
     * @param origin 海明码
     * @return k 值
     */
    static Integer getDecodeK(String origin) {
        return (int) Math.ceil(Math.log(origin.length()) / Math.log(2));
    }

    /**
     * 将明文塞入 hamming 数组后，计算 P
     *
     * @param k P 的个数
     */
    static void initP(int k) {
        P = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            int count = 0;
            for (int j = 0; j < mapping.size(); j++) {
                if (mapping.get(j) / (int) Math.pow(2, i) % 2 == 1)
                    count += hamming[mapping.get(j)];
            }
            P.add(count % 2);
        }
    }

    /**
     * 将 hamming 数组转换成 string 类型
     *
     * @return 海明码
     */
    static String show() {
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for (int i : hamming) {
            sum += i;
            sb.append(i);
        }
        sb.deleteCharAt(0).reverse();

        // 偶校验
        if (sum % 2 == 0) sb.append(0);
        else sb.append(1);
        return sb.toString();
    }

    /**
     * 海明码的编码
     *
     * @param origin 编码前的原文
     * @return 海明码
     */
    public static String encode(String origin) {
        origin = new StringBuilder(origin).reverse().toString();
        Integer k = getK(origin);
        hamming = new int[origin.length() + k + 1];
        initmapping(origin.length());
        for (int i = 0; i < origin.length(); i++) {
            hamming[mapping.get(i)] = Integer.parseInt(String.valueOf(origin.charAt(i)));
        }
        initP(k);
        for (int i = 0; i < k; i++) {
            hamming[(int) Math.pow(2, i)] = P.get(i);
        }
        return show();
    }

    /**
     * 可能的明文字符串
     *
     * @param origin 收到的反转后的海明码字符串
     * @return 获得明文字符串（可能含有错误）
     */
    public static String getDs(String origin) {
        StringBuilder sb = new StringBuilder();
        for (int i : mapping) {
            sb.append(origin.charAt(i - 1));
        }
        return sb.toString();
    }


    /**
     * 可能的 P
     *
     * @param origin 收到的反转后的海明码
     * @param k      P 的个数
     * @return 可能的 P
     */
    static String getPs(String origin, int k) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            sb.append(origin.charAt((int) Math.pow(2, i) - 1));
        }
        return sb.reverse().toString();
    }

    /**
     * @return 根据可能的明文计算出的新的 P
     */
    static String getNewPs() {
        StringBuilder sb = new StringBuilder();
        for (int i : P)
            sb.append(i);
        return sb.reverse().toString();
    }

    /**
     * 字符串异或操作，我很想知道 C++ 异或更容易的写法？
     *
     * @param a 第一个字符串
     * @param b 第二个字符串
     * @return 异或后的结果
     */
    static String ORX(String a, String b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length(); i++)
            if (a.charAt(i) == b.charAt(i))
                sb.append('0');
            else sb.append('1');
        return sb.toString();
    }

    static void checkEven(String origin) {
        int sum = 0;
        for (int i = 0; i < origin.length(); i++) {
            sum += (origin.charAt(i) - '0');
        }
        if (sum % 2 == 0) even = 0;
        else even = 1;
    }

    /**
     * 海明码的解码
     *
     * @param origin 收到的海明码
     * @return 可能的错误的位置
     */
    public static void decode(String origin) {
        checkEven(origin);
        int len = origin.length() - 1;
        tail = origin.charAt(len) - '0';
        origin = origin.substring(0, len);
        origin = new StringBuilder(origin).reverse().toString();
        Integer k = getDecodeK(origin);
        initmapping(len - k);
        String Ds = getDs(origin);

        String Ps = getPs(origin, k);
        hamming = new int[Ds.length() + k + 1];
        for (int i = 0; i < Ds.length(); i++) {
            hamming[mapping.get(i)] = Integer.parseInt(String.valueOf(Ds.charAt(i)));
        }
        initP(k);
        position =  ORX(Ps, getNewPs());
    }

    /**
     * 二进制转换成十进制
     *
     * @param str 解码所得到的错误的位置
     * @return 十进制的位置
     */
    static int bitoDe(String str) {
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        int sum = 0;
        for (int i = 0; i < sb.length(); i++) {
            sum += ((sb.charAt(i) - '0') * Math.pow(2, i));
        }
        return sum;
    }

    /**
     * 正确的海明码
     *
     * @param origin 收到的海明码
     * @return 正确的海明码
     */
    public static String truth(String origin) {
        flag = 0;
        decode(origin);
        int pos = bitoDe(position);
        int length = origin.length();
        origin = origin.substring(0, length - 1);
        StringBuilder sb = new StringBuilder(origin);
        int len = origin.length();


        if (pos != 0) {
            if (even == 0) {
                // TODO: 有两位错
                flag = 2;
            } else flag = 1;
            sb.replace(len - pos,
                    len - pos + 1,
                    origin.charAt(len - pos) == '0' ? "1" : "0");
        } else if (even != 0) {
            // TODO: 奇偶校验位错
            flag = 3;
        }

        return sb.toString();
    }


    /**
     * 获得正确的明文
     *
     * @param origin 正确的海明码
     * @return 正确的明文
     */
    public static String getTrueDs(String origin) {
//        origin = truth(origin);
        StringBuilder tmp = new StringBuilder(origin).reverse();
        StringBuilder sb = new StringBuilder();
        for (int i : mapping) {
            sb.append(tmp.charAt(i - 1));
        }
        return sb.reverse().toString();
    }


    /**
     * 展示 Decode 结果
     *
     * @param origin 收到的海明码
     * @return
     */
    public static String showDecode(String origin) {
        String truth = truth(origin);
        String ans;
        if (flag == 2) {
            return "有两位错，无法纠正。";
        } else if (flag == 3) {
            ans = "正确的海明码：(奇偶校验位错)\n" + truth + " - " + ((tail == 0) ? "1" : "0")
                    + "\n位置：\n" + new StringBuilder(position)
                    + "\n原文：\n" + getTrueDs(truth);
        } else if (flag == 1) {
            ans = "正确的海明码：\n" + truth + " - " + tail
                    + "\n位置：\n" + new StringBuilder(position)
                    + "\n原文：\n" + getTrueDs(truth);
        } else {
            ans = "正确的海明码：\n" + truth + " - " + tail
                    + "\n位置：\n" + new StringBuilder(position)
                    + "\n原文：\n" + getTrueDs(truth);
        }
        return ans;
    }

    /**
     * 预检查是否有错
     *
     * @param origin 输入的内容
     * @return false 意为输入有误
     */
    public static boolean check(String origin) {
        for (int i = 0; i < origin.length(); i++)
            if (origin.charAt(i) < '0' || origin.charAt(i) > '1')
                return false;
        return true;
    }


}