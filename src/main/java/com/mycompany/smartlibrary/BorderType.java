package com.mycompany.smartlibrary;

public enum BorderType {
    TOP('┌', '┬', '┐'),
    MIDDLE('├', '┼', '┤'),
    BOTTOM('└', '┴', '┘');

    public final char left;
    public final char mid;
    public final char right;

    BorderType(char left, char mid, char right) {
        this.left = left;
        this.mid = mid;
        this.right = right;
    }
}
