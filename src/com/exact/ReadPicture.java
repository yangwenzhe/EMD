package com.exact;

import com.exact.entity.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ReadPicture {
    int[][] temp;//temp表示每个像素点的像素值，Mnist图片是28*28的图片
    final int weight;
    final int height;
    public ReadPicture(String s) throws IOException {
        BufferedImage image = ImageIO.read(new File(s));
        weight = image.getWidth();
        height = image.getHeight();
        temp  = new int[weight][height];
        int pixel;
        for (int i=0; i < height; i++){
            for(int j=0; j< weight; j++){
                pixel = 255 - image.getRGB(j, i) & 0xff;
                temp[j][i] = pixel;
            }
        }

    }

    public signature_t getSignature(int t) throws IOException {

        feature_t[] Features; /* Pointer to the features vector */
        double[] Weights; /* Pointer to the weights of the features */
        int partWeight = weight/t;
        int partHeight = height/t;
        int[][] tempAfterPartition = new int[partWeight][partHeight]; //t = 2; //分区之后各个区域的坐标和权重

        for (int X= 0; X < partWeight; X++) {
            for (int Y = 0; Y < partHeight; Y++){
                int sum = 0;
                for (int i = t*X; i< t*(X+1); i++){
                    for (int j = t*Y; j < t*(Y+1); j++){
                        sum = sum + temp[i][j];

                    }
                }
                tempAfterPartition[X][Y] = sum;//sum/(t*t);

            }
        }



        //计算图片中值不为0的数量
        int n = 0;
        double totalWeights = 0.0; //求像素值总和
        for (int i = 0; i < tempAfterPartition.length; i++){
            for (int j = 0; j < tempAfterPartition[0].length; j++){
                if(tempAfterPartition[i][j] !=0){
                    totalWeights+=tempAfterPartition[i][j];
                    n++;
                }
            }
        }
        System.out.println("未归一化之前的总权重=="+totalWeights);
        Features = new feature_t[n];
        Weights = new double[n];
        n = 0; //得到Features和Weights的值
        for (int i = 0; i < tempAfterPartition.length; i++){
            for (int j = 0; j < tempAfterPartition[0].length; j++){
                if(tempAfterPartition[i][j] !=0){
                    Features[n] = new feature_t(i,j);
                    Weights[n]=tempAfterPartition[i][j]/totalWeights;  //归一化：除以像素总和：
                    n++;
                }
            }
        }
        signature_t signature = new signature_t(n, Features, Weights);
        return signature;

    }

}
