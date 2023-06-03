package com.exact;

import com.exact.entity.*;

import java.io.IOException;

public class Main {
    public static double totalWeights(signature_t s1){
        double total = 0;
        for (int i =0; i<s1.n; i++){
            total = total+s1.Weights[i];
        }
        return total;
    }
    public static void main(String[] args) {

        //两张图片地址
        String path1 = "D:\\datasets\\train-images\\train0.png";
        String path2 = "D:\\datasets\\train-images\\train35.png";

        //t图片大小
        int t1 = 2;
        int t2 = 2;

        //读取图片，获得两个分布
        ReadPicture readPicture1 = null;
        ReadPicture readPicture2 = null;
        signature_t s1 = null;
        signature_t s2 = null;

        try {
            readPicture1 = new ReadPicture(path1);
            s1 = readPicture1.getSignature(t1);
            readPicture2 = new ReadPicture(path2);
            s2 = readPicture2.getSignature(t2);
//            System.out.println("s2.Weights[i]");
//            for (int i =0;  i<s2.n; i++){
//                System.out.println(s2.Weights[i]);
//            }
            System.out.println("s1的特征个数n1="+s1.n);
            System.out.println("s2的特征个数n1="+s2.n);
            System.out.println("s1的总权重=====");
            System.out.println(totalWeights(s1));
            System.out.println("s2的总权重=====");
            System.out.println(totalWeights(s2));

        } catch (IOException e) {
            e.printStackTrace();
        }

//        //手动test
//
//        int size1 = 11;
//        int size2 = 9;
//        feature_t []f1 = new feature_t[size1];
//        feature_t []f2 = new feature_t[size2];
//
//
//        f1[0] = new feature_t(4,23);f1[1] = new feature_t(4,24);
//        f1[2] = new feature_t(5,23);f1[3] = new feature_t(5,24);
//        f1[4] = new feature_t(6,22);f1[5] = new feature_t(6,23);
//        f1[6] = new feature_t(6,24);f1[7] = new feature_t(7,7);
//        f1[8] = new feature_t(7,8);f1[9] = new feature_t(7,22);
//        f1[10] = new feature_t(8,0);
//
//
//        f2[0] = new feature_t(6,15);f2[1] = new feature_t(6,16);
//        f2[2] = new feature_t(6,17);f2[3] = new feature_t(6,18);
//        f2[4] = new feature_t(6,19);f2[5] = new feature_t(6,20);
//        f2[6] = new feature_t(6,21);f2[7] = new feature_t(6,22);
//        f2[8] = new feature_t(7,0);
//
//        double []w1 = {55,136,172,253,18,226,253,49,18,171,123};
//        double []w2 = {76,85,85,85,86,85,85,28,123};
//
//        signature_t s1 = new signature_t(size1,f1,w1);
//        signature_t s2 = new signature_t(size2,f2,w2);
//


        //使用virtual计算的代码.在权重较小的分布上加虚拟节点
//        double sumWeights1 = virtual.getTotalWeights(s1);
//        double sumWeights2 = virtual.getTotalWeights(s2);
//        double e = virtual.getEMD(s1,s2)/Math.min(sumWeights1,sumWeights2);
//        System.out.println("EMD距离:"+e);

        //使用test1计算  效果奇差，不要考虑
//        double sumWeights1 = test1.getTotalWeights(s1);
//        double sumWeights2 = test1.getTotalWeights(s2);
//        double e = test1.getEMD(s1,s2)/Math.min(sumWeights1,sumWeights2);
//        System.out.println("EMD距离:"+e);

        //调用emd_class.java得到的准确结果。
        //hhhhhh


        int select  =  1;//1是Exact， 0是最小元素法

        if (select == 1){
            double e2 = 0;
            emd_class hh = new emd_class();
            long startTime=System.currentTimeMillis();   //获取开始时间
            e2 = hh.emd(s1,s2,null,null);
            System.out.println("Exact EMD距离:"+e2);
            long endTime=System.currentTimeMillis(); //获取结束时间
            System.out.println("simplex算法程序运行时间： "+(endTime-startTime)+"ms");

        }else{
            //调用EMD.java计算EMD距离（是否传入存储流的flow，与流的大小）
            long startTime=System.currentTimeMillis();   //获取开始时间
            double e1 = EMD.emd(s1,s2);
            System.out.println("最小元素法EMD距离:"+e1);
            long endTime=System.currentTimeMillis(); //获取结束时间
            System.out.println("最小元素法程序运行时间： "+(endTime-startTime)+"ms");
        }

    }
}
