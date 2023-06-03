package com.exact.entity;

//分布，包含点的数量，每个点的位置坐标及权重
public class signature_t implements Cloneable {
    public int n;//数量
    public feature_t[]Features;//每个点的坐标
    public double []Weights;//点的权重

    public signature_t(int n, feature_t[] features, double[] weights) {
        this.n = n;
        Features = features;
        Weights = weights;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        int i;
        signature_t o = (signature_t) super.clone();
        o.Features = (feature_t[]) o.Features.clone();

        for (i = 0; i < n; i++)
            o.Features[i] = (feature_t) o.Features[i].clone();
        o.Weights = (double[]) o.Weights.clone();

        return o;
    }
}
