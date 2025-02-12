package com.payroll.payrollservices.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()       // This makes JSON output readable
            .disableHtmlEscaping()     // prevents escaping HTML characters
            .serializeNulls()          // makes sure null values are included
            .create();

    private GsonUtil() {
        // Private constructor to prevent instantiation
    }

    public static Gson getInstance() {
        return GSON;
    }
}
