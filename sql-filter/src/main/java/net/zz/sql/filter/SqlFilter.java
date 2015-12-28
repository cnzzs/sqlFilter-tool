package net.zz.sql.filter;

import net.zz.dao.params.Order;
import net.zz.dao.params.Order.OrderAD;
import net.zz.dao.params.QueryParams;
import net.zz.dao.params.Where;
import net.zz.dao.params.enums.Restriction;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by ZaoSheng on 2015/10/25.
 */
public class SqlFilter {
    private HttpServletRequest request;// 为了获取request里面传过来的动态参数
    private String column;
    private Order.OrderAD order;
    Where params = new Where().where();

    /**
     * 默认构造
     */
    public SqlFilter() {
    }

    /**
     * 带参构造
     *
     * @param request
     */
    public SqlFilter(HttpServletRequest request) {
        this.request = request;
        addFilter(request);
    }

    public boolean isBlank(String str) {
        return null == str || str.trim().isEmpty();
    }

    private void setOrderValue(String prefix) {
        switch (order) {
            case DESC:
                params.order(column, prefix);
                break;
            case ASC:
                params.order().ASC(column, prefix);
                break;
        }
    }

    /**
     * 获得添加过滤字段后加上排序字段的Sql
     *
     * @return
     */
    public void setOrder() {


        if (!isBlank(column) && null != order) {
            int index = column.indexOf(".");
            if (index < 1) {
                setOrderValue(null);
            } else {
                setOrderValue(column.substring(index));
            }

        } else {
            if (request != null) {
                String s = request.getParameter("sort");
                String o = request.getParameter("order");
                if (isBlank(s) || isBlank(o)) {
                    return;
                }
                column = s;
                order = OrderAD.valueOf(o.toUpperCase());
                int index = column.indexOf(".");
                if (index < 1) {
                    setOrderValue(null);
                } else {
                    setOrderValue(column.substring(index));
                }

            }
        }

    }

    /**
     * 添加过滤
     *
     * @param request
     */
    public void addFilter(HttpServletRequest request) {
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
//            String value = request.getParameter(name);
            String[] values = request.getParameterValues(name);//需要对应值

            if (values.length > 1) {
                addFilter(name, Arrays.asList(values));
            } else {
                addFilter(name, values[0]);
            }


        }
    }


    /**
     * 添加过滤
     * <p/>
     * 举例，name传递：QUERY^t#id^|^EQ
     * 举例，name传递：QUERY^t#id^!|^EQ
     * <p/>
     * 举例，value传递：0
     *
     * @param name
     * @param value
     */
    public void addFilter(String name, Object value) {
		
        if (name != null && value != null) {
            if (name.startsWith("QUERY^")) {// 如果有需要过滤的字段
                String[] filterParams = name.split("\\^");
//				String[] filterParams = StringUtils.split(name, "_");
                int length = filterParams.length;
                if (length >= 3) {
                    String[] ppn = filterParams[1].split("\\#");
                    String prefix = ppn[0]; //表的别名
                    String propertyName = ppn[1];// 要过滤的字段名称
                    String ao = filterParams[2];// 操作的逻辑
                    Restriction restriction = null;
                    net.zz.sql.filter.Type type = null;
                    try {
                        restriction = Restriction.valueOf(filterParams[3].toUpperCase());// SQL操作符
                    } catch (Exception e) {
                        restriction = Restriction.EQ;
                    }
                    try{
                        type = net.zz.sql.filter.Type.valueOf(filterParams[4]);// 参数类型
                    } catch (Exception e) {
                        type = net.zz.sql.filter.Type.S;
                    }
                    switch (restriction) {
                        case BW:
                            List list = (List) value;
                            int size = list.size();
                            Object[] os = new Object[size];
                            for (int i = 0; i < size; i++) {
                                os[i] = type.parse(list.get(i).toString());
                            }
                            value = os;
                            break;
                        case IN:
                        case NIN:
                             list = (List) value;
                             size = list.size();
                            List<Object> vs = new ArrayList<Object>(size);
                            for (int i = 0; i < size; i++) {
                                vs.add(type.parse(list.get(i).toString()));
                            }
                            value = vs;
                            break;
                        default:
                            value = type.parse(value.toString());
                            break;
                    }

                    if ("|".equals(ao)) {
                        params.or(propertyName, value, restriction, prefix);
                    } else {
                        params.and(propertyName, value, restriction, prefix);
                    }
                }
            }
        }
    }

    public String constructCol(String str) {
        String A = "^[A-Z]";
        StringBuffer col = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            //str.charAt(i)

            if (String.valueOf(str.charAt(i)).matches(A)) {
                col.append("_");
            }
            col.append(str.charAt(i));
        }
        return col.toString();
    }

    public SqlFilter setAlias(String alias) {
        params.setAlias(alias);
        return this;
    }


    public QueryParams getQueryParams() {
        return params;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public OrderAD getOrder() {
        return order;
    }

    public void setOrder(OrderAD order) {
        this.order = order;
    }

    public static void main(String[] args) {
        String a = "1";

    }
}
