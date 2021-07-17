package com.company.entity;
//点的位置坐标
public class feature_t implements Cloneable {
    public int X;
    public int Y;
    public feature_t(int x, int y) {
        this.X = x;
        this.Y = y;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        feature_t o = (feature_t) super.clone();

        return o;
    }

}
