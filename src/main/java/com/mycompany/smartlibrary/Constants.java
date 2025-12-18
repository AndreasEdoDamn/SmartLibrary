package com.mycompany.smartlibrary;

import java.util.regex.Pattern;

public final class Constants {

    private Constants() {};

    public static final class FINE_AMOUNT {
        public static final int BOOK = 2000;
        public static final int DEFAULT_WIDTH = 80;
    }

    public static final class BOX_DRAWING {
        public static final String HEADER_FORMAT = "│ %-10s │ %-20s │%n";
        public static final int DEFAULT_WIDTH = 80;
    }

    public static final class VALIDATION {
        public static final String HEADER_FORMAT = "│ %-10s │ %-20s │%n";
        public static final int DEFAULT_WIDTH = 80;
    }

    static final Pattern FORMAT_PATTERN = Pattern.compile("%-?(\\d+)");
    static final int MAX_RETURN_DAYS = 3;
}
