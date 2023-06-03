package com.exact;
import java.io.FileWriter;
import com.exact.entity.signature_t;

public class writeFeature {

    public static void main(String[] args){
        ReadPicture rp1,rp2,rp3;
        int t =4;
        int t2 = 4;
        try{
            rp1 = new ReadPicture("D:\\datasets\\train-images\\train0.png");
            rp2 = new ReadPicture("D:\\datasets\\train-images\\train1.png");
            rp3 = new ReadPicture("D:\\datasets\\train-images\\train2.png");
            signature_t s1 = rp1.getSignature(t);
            signature_t s2 = rp2.getSignature(t);
            signature_t s3 = rp3.getSignature(t);
//            System.out.println("start");
//            System.out.println(_2arrayToString(s1.Features));
//            System.out.println(_1arrayToString(s1.Weights));
            FileWriter fw = new FileWriter("D:/feature.txt");
            for (int i = 0;i< s1.n; i++){
                fw.write(String.valueOf(s1.Features[i].X));
                fw.write(",");
                fw.write(String.valueOf(s1.Features[i].Y));
                fw.write("\t");
            }
            fw.write("\n");
            for (int i = 0;i< s1.n; i++){
                fw.write(s1.Weights[i]+",");

            }
            fw.write("\n");
            for (int i = 0;i< s2.n; i++){
                fw.write(String.valueOf(s2.Features[i].X));
                fw.write(",");
                fw.write(String.valueOf(s2.Features[i].Y));
                fw.write("\t");
            }
            fw.write("\n");
            for (int i = 0;i< s2.n; i++){
                fw.write(s2.Weights[i]+",");

            }
            fw.write("\n");
            System.out.println("complete");
            fw.close();

////            String[] args1 = new String[]{"python", "C:\\Users\\57482\\.spyder-py3\\EMDCalculate.py",rp1._2arrayToString(s1.Features),rp1._1arrayToString(s1.Weights),rp1._2arrayToString(s2.Features),rp1._1arrayToString(s2.Weights)};
//            String[] args1 = new String[]{"python", "C:/Users/57482/.spyder-py3/testjava.py"};
//            Process pr = Runtime.getRuntime().exec(args1);//("C:\\Users\\57482\\.spyder-py3\\EMDCalculate.py"+s1.Features+s1.Weights+s2.Features+s2.Weights);
//            BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//            String line= null;
//            System.out.println(in.readLine());
//            while((line=in.readLine()) !=null){
//                System.out.println(line);
//            }
//            in.close();
//            pr.waitFor();
//            System.out.println("end");
        }catch (Exception e){
            System.out.println("there is something wrong");
            System.out.println(e);
        }

    }
}
