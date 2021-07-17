package com.company;
import com.company.entity.*;
public class test1 {
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
    public static double getEMD(signature_t s1, signature_t s2){
        int n1 = s1.n;
        int n2 = s2.n;
        double sSum = getTotalWeights(s1);
        double dSum = getTotalWeights(s2);


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
        double hausdorff = 0.0;

        for(int i = 0; i<minDistance.length; i++){
            if(hausdorff < minDistance[i]){
                hausdorff = minDistance[i]; //找到hausdorff
            }
        }
        System.out.println("hausdorff====");
        System.out.println(hausdorff);

        double emd1 = 0.0;
        double emd2 = 0.0;
        for(int i =0;i<n1;i++){
            emd1 = emd1 + s1.Weights[i] * hausdorff;
        }
//        for(int i =0;i<n1;i++){
//            emd2 = emd2 + s1.Weights[i]*hausdorff;
//        }

        return emd1;


    }
}
