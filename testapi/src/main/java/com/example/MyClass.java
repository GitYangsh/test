package com.example;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyClass extends HttpServlet{

    public static void main(String args[]) {
        System.out.println("{\"describe\":\"请求成功\"}");

//        Stack<String> stack = new Stack<>();
//        stack.push("1");
//        stack.push("2");
//        stack.push("3");
//        stack.push("4");
//        stack.push("5");
//        System.out.println(stack);
//        stack.remove(0);
//        System.out.println(stack);
//        stack.push("6");
//        System.out.println(stack);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("param1=" + req.getParameter("param1") + ",param2=" + req.getParameter("param2"));
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("{\"describe\":\"请求成功\"}");
        out.flush();
        out.close();
    }

}
