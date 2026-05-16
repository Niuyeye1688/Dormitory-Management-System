package com.dormitory.util;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static void success(HttpServletResponse response) throws IOException {
        write(response, 200, "操作成功", null);
    }

    public static void success(HttpServletResponse response, Object data) throws IOException {
        write(response, 200, "操作成功", data);
    }

    public static void success(HttpServletResponse response, String msg, Object data) throws IOException {
        write(response, 200, msg, data);
    }

    public static void error(HttpServletResponse response, String msg) throws IOException {
        write(response, 500, msg, null);
    }

    public static void error(HttpServletResponse response, int code, String msg) throws IOException {
        write(response, code, msg, null);
    }

    public static void write(HttpServletResponse response, int code, String msg, Object data) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        out.print(gson.toJson(result));
        out.flush();
    }
}
