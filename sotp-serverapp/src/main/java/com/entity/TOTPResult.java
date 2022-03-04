package com.entity;

public class TOTPResult {
    private boolean valid;
    private long shift;

    public TOTPResult(boolean valid, long shift) {
        this.valid = valid;
        this.shift = shift;
    }

    public boolean isValid() {
        return this.valid;
    }

    public long getShift() {
        return this.shift;
    }
}
