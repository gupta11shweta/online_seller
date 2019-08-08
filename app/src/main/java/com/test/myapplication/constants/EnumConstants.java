package com.test.myapplication.constants;

public class EnumConstants {

    public enum dialogTagEnum {
        STATUS_TYPE("Status"), CITY_DESTINATION("Destination"), FirstApprover("Approver Level 1");
        private String i;

        dialogTagEnum(String select) {
            i = select;
        }

        public String value() {
            return i;
        }
    }

}
