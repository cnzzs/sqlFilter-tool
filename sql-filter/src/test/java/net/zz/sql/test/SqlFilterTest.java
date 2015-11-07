package net.zz.sql.test;

import net.zz.dao.params.Order;
import net.zz.dao.params.QueryParams;
import net.zz.sql.filter.SqlFilter;
import org.junit.Test;

import java.util.Map;

/**
 * Created by ZaoSheng on 2015/11/7.
 */
public class SqlFilterTest {
    @Test
    public void test() {

        SqlFilter filter = new SqlFilter();
        filter.addFilter("QUERY^t#id^|^EQ",1);
        filter.addFilter("QUERY^t#name^!|^EQ","张三");
        filter.setColumn("uid");
        filter.setOrder(Order.OrderAD.DESC);
        filter.setOrder();
        QueryParams queryParams = filter.getQueryParams();
        System.out.println(queryParams.builderAttrs().getSqlString());
        Map<String, Object> attrs = queryParams.getAttrs();
        for (String key : attrs.keySet()){
            System.out.println(String.format("%s=%s",key, attrs.get(key)));
        }
    }
}
