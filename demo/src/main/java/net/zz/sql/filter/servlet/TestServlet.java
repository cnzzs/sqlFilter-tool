package net.zz.sql.filter.servlet;

import net.zz.sql.filter.Generate;
import net.zz.sql.filter.SqlFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ZaoSheng on 2015/11/10.
 */
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        SqlFilter sqlFilter = new SqlFilter(req);
        sqlFilter.setOrder();
        Generate generate = new Generate();
        String sql = generate.findByProperty(sqlFilter.setAlias("n").getQueryParams());
        PrintWriter writer = resp.getWriter();
        writer.print("sql:" + sql);
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
