package net.zz.sql.filter.servlet;

import net.zz.dao.params.Params;
import net.zz.sql.filter.Generate;
import net.zz.sql.filter.SqlFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by ZaoSheng on 2015/11/10.
 */
@WebServlet(name="testServlet", urlPatterns="/testServlet")
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        SqlFilter sqlFilter = new SqlFilter(req);
        sqlFilter.setOrder();
        Generate generate = new Generate();
        Params params = sqlFilter.setAlias("t").getQueryParams();
        String sql = generate.findByProperty(params);

        PrintWriter writer = resp.getWriter();
        writer.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "<body>");
        writer.println("------? Instead of the form ------<br/>");
        writer.println("sql:" + sql);
        int i = 0;
        writer.println("<br/>");
        for (Object v: params.getParas()){
            writer.print(String.format("P%s:%s &nbsp;&nbsp;", ++i, v));
        }
        writer.println("<br/>");
        writer.println("------The key, the value form ------<br/>");
        params.builderAttrs();
        writer.println("sql:from test " + params.alias() + params.toSQL());
        writer.println("<br/>");

        Map<String, Object> attrs = params.getAttrs();
        for (Object key: attrs.keySet()){
            writer.println(String.format("key:%s; value:%s ", key, attrs.get(key)));
            writer.println("<br/>");
        }
        writer.write("</body>\n" +
                "</html>");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
