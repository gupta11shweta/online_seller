package com.test.myapplication.constants;



public enum DBConstants {
    DB_NAME("pe_orders"), DB_VERSION("1");

    private final String value_;

    DBConstants(String value) {
        value_ = value;
    }

    public String getValue() {
        return value_;
    }
}
