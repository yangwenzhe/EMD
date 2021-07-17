package com.company;
import com.company.entity.*;
public class virtual {
    public static double[][] getDistMatrix(signature_t s1, signature_t s2){
        double[][] groundDistance = new double[s1.n][s2.n];
        for(int i =0; i<s1.n; i++){
            int x1 = s1.Features[i].X;
            int y1 = s1.Features[i].Y;
            for(int j =0; j<s2.n; j++){
                int x2 = s2.Features[j].X;
                int y2 = s2.Features[j].Y;
                groundDistance[i][j] = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
            }
        }

        return groundDistance;
    }
    public static double getTotalWeights(signature_t s1){
        double total = 0;
        for (int i =0; i<s1.n; i++){
            total = total+s1.Weights[i];
        }
        return total;
    }
    public static double EMDcalculate(signature_t s1, signature_t s2){
        int n1 = s1.n;
        int n2 = s2.n;
        double[] minDistance = new double[n1];
        int[] indexArray = new int[n1];
        double[][] distanceMatrix = getDistMatrix(s1,s2);
        for (int i = 0; i<distanceMatrix.length; i++){
            double temp = 1000000000000000.0;
            int index = -1;
            //找矩阵中每行的最小值
            for (int j=0; j < distanceMatrix[0].length; j++){
                if(temp > distanceMatrix[i][j]){
                    temp = distanceMatrix[i][j];
                    index = j;
                }
            }
            minDistance[i] = temp;
            indexArray[i] = index;
        }
        System.out.println("s2的总权值===");
        System.out.println(getTotalWeights(s2));
        double emd = 0.0;
        for(int i = 0 ; i<n1; i++){
            int j = indexArray[i];
            if(s1.Weights[i] >= s2.Weights[j]){
                emd = emd + s2.Weights[j] * distanceMatrix[i][j];
                s2.Weights[j] = 0;
            }
            if (s1.Weights[i] < s2.Weights[j]){
                emd = emd + s1.Weights[i] * distanceMatrix[i][j];
                s2.Weights[j] = Math.abs(s1.Weights[i]-s2.Weights[j]);
            }
        }

        double hausdorff1 = 100000000000000000000.0;
        double hausdorff2 = 0.0;
        for(int i = 0; i<minDistance.length; i++){
            if(hausdorff1 > minDistance[i]){
                hausdorff1 = minDistance[i]; //找min(min(dij))
            }
        }
        System.out.println("hausdorff1====");
        System.out.println(hausdorff1);
        for(int i = 0; i<minDistance.length; i++){
            if(hausdorff2 < minDistance[i]){
                hausdorff2 = minDistance[i];//找max(min(dij))
            }
        }
        System.out.println("hausdorff2====");
        System.out.println(hausdorff2);
        double avg = 0.0;
        for(int i = 0; i<minDistance.length; i++){
            avg = avg + minDistance[i];
            }
        avg = avg/minDistance.length;
        System.out.println("avg=====");
        System.out.println(avg);

        System.out.println(" (hausdorff1+hausdorff2)/2");
        System.out.println( (hausdorff1+hausdorff2)/2);

        System.out.println("s2的总权值===");
        System.out.println(getTotalWeights(s2));
        emd = emd + getTotalWeights(s2)*(hausdorff1+hausdorff2)/2;
        return emd;

    }

    public static double getEMD(signature_t s1, signature_t s2){
        int n1 = s1.n;
        int n2 = s2.n;
        System.out.println("s1的总权值===");
        System.out.println(getTotalWeights(s1));
        System.out.println("s2的总权值===");
        System.out.println(getTotalWeights(s2));
        double sSum = getTotalWeights(s1);
        double dSum = getTotalWeights(s2);
        double diff = dSum - sSum;
        double emd;
        //如果diff>0,表示s2的总权值大于s1，那么 S1 < S2,在 s1 中增加一个虚拟节点，接收多余的weights
        if (diff >0){
            emd = EMDcalculate(s2,s1);

        }
        else{ //如果diff < 0,那么 S1 > S2, 在 s2 中增加一个虚拟节点，接收多余的weights
            emd = EMDcalculate(s1,s2);

        }
        return emd;


    }
}
