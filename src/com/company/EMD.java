package com.company;

import com.company.entity.*;

import java.util.ArrayList;
import java.util.List;


public class EMD {
    public static int size_1,size_2;//记录产地与销地的数量
    public static List<flow_t> list = new ArrayList<flow_t>();//记录整个流动过程
    public static double distance[][];//两分布间所有点的距离
    public static boolean isUsed[][];//标记该传输路径是否已经使用
    public static double res = 0;//最终的emd距离
    public static double yield=0;//总产量
    public static double sale=0;//总销量
    public static double trans=0;//剩余传输量总值，等于总产量或总销量中较小的那个值,随着算法运行逐步减小


    /*使用最小元素法，计算两分布间emd距离*/
    public static double emd(signature_t s1,signature_t s2){
        init(s1,s2);//初始化
        System.out.println("distance矩阵分别是");
        for (int i =0; i< distance.length; i++){//行数
            for (int j = 0; j<distance[0].length; j++){//列数
                System.out.print(distance[i][j] +' ');
            }
        }
        double test=0.0;
        //循环处理至产量变为0
        while(trans>0.0000001){
            int []path = getMinDis();//获取当前最小代价路径
            int i = path[0];int j = path[1];
            double amount = 0;//此次传输量
            if(s1.Weights[i]!=0 && s2.Weights[j]!=0){
                if(s1.Weights[i]>s2.Weights[j]){//i地产量大于j地销量
                    amount = s2.Weights[j];

                    s1.Weights[i]-=amount;
                    s2.Weights[j]=0;
                }else {//i地产量小于等于j地销量 s1.Weights[i] <= s2.Weights[j]
                    amount = s1.Weights[i];
                    s2.Weights[j]-=amount;
                    s1.Weights[i]=0;
                }
                res+=amount*distance[i][j];
                list.add(new flow_t(i,j,amount));
                trans-=amount;
                test += amount;
//                System.out.println("trans:"+trans);//记录传输量的减少变化
            }

            isUsed[i][j]=true;//标记该路径不能再使用
        }
//        System.out.println(test);
        //调试：输出list
        System.out.println("传输流向：");
        printList();


        return res/Math.min(yield,sale);
    }

    /*输出list*/
    public static void printList(){
        for(flow_t f:list){
            System.out.println("("+f.from+","+f.to+","+f.amount+")");
        }
    }

    /*初始化*/
    public static void init(signature_t s1,signature_t s2){
        size_1 = s1.n;//产地的数量
        size_2 = s2.n;//销地的数量
        distance = new double[size_1][size_2];//初始化distance
        isUsed= new boolean[size_1][size_2];//初始化isUsed

        calculateDis(s1,s2);//填充distance数组
        calculateTrans(s1,s2);//计算传输量总值
    }

    /*计算，产量，销量传输量总值=min(产量，销量)*/
    public static void calculateTrans(signature_t s1,signature_t s2){
        for(double w:s1.Weights)
            yield += w;

        for(double w:s2.Weights)
            sale += w;

        trans = Math.min(yield,sale);
        System.out.print("yield==");
        System.out.println(yield);
        System.out.print("sale===");
        System.out.println(sale);

        System.out.println("传输量总值："+trans);
    }

    /*获得当前distance数组中，最小的未被使用的传输路径*/
    public static int[] getMinDis(){
        double min = Integer.MAX_VALUE;
        int []path = new int[2];
        for(int i=0;i<size_1;i++){
            for(int j=0;j<size_2;j++){
                if(distance[i][j]<min && !isUsed[i][j]){
                    min = distance[i][j];
                    path[0]=i;path[1]=j;
                }
            }
        }
        return path;
    }


    /*计算两分布间所有点之间的距离，填充distance二维数组*/
    public static void calculateDis(signature_t s1,signature_t s2){
        for(int i=0;i<size_1;i++){
            for(int j=0;j<size_2;j++){
                distance[i][j] = disBetweenTwoPoint(s1.Features[i],s2.Features[j]);
            }
        }
    }

    /* 计算两点间欧几里得距离 */
    public static double disBetweenTwoPoint(feature_t F1, feature_t F2) {
        int dX = F1.X - F2.X;
        int dY = F1.Y - F2.Y;
        return Math.sqrt((double)(dX*dX + dY*dY));
    }
}
