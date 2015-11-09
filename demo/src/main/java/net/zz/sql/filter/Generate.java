package net.zz.sql.filter;

import net.zz.dao.params.Params;
import net.zz.dao.params.QueryParams;
import net.zz.dao.params.enums.Restriction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ZaoSheng on 2015/7/15.
 */
public class Generate {

    protected String getSqlExceptSelect(Params params)
    {
        QueryParams queryParams = params.builderParas();
        return  String.format(" from %s %s %s", getTableName(), queryParams.alias(), queryParams.getSqlString());
    }




    /**
    *
    *  例子：
    *  queryOrNamedQuery="select * from zz z where z.name = :name"
    * attrs.put("name", "张三")
    * findFirstBySQLQuery(queryOrNamedQuery, attrs)
    * @param queryOrNamedQuery
    * @param attrs
    * @return List
    */
    public String findBySQLQuery(String queryOrNamedQuery, Map<String, Object> attrs) {
        List<Object> params = new ArrayList<Object>();
        String sql = QueryParams.toFormatSQL(queryOrNamedQuery, attrs, params);
        return sql;

    }




    /**
     * @param params 查询参数
     * @return List
     */
    public String findByProperty(Params params) {
        String sqlExceptSelect = getSqlExceptSelect(params);
       // return find("SELECT * " + sqlExceptSelect, params.getParas().toArray());
        return sqlExceptSelect;
    }

    /**
     * 通过orm实体属性名称查询全部
     *
     * @param propertyName orm实体属性名称
     * @param value        值
     * @return List
     */
    public String findByProperty(String propertyName, Object value) {

        return findByProperty( Restriction.EQ, propertyName, value);
    }

    /**
     * 通过orm实体属性名称查询全部
     *
     * @param propertyName orm实体属性名称
     * @param isNull true 为 value=null
     * @return M
     */
    public String findFirstByIsNull(String propertyName, boolean isNull) {
      //  List<M> result = findByProperty(isNull ? Restriction.NUL : Restriction.NNUL, propertyName);
       // return result.size() > 0 ? result.get(0) : null;
        return findByProperty(isNull ? Restriction.NUL : Restriction.NNUL, propertyName);
    }
    /**
     * 通过orm实体属性名称查询全部
     *
     * @param propertyName orm实体属性名称
     * @param isNull true 为 value=null
     * @return  List<M>
     */
    public String  findByIsNull(String propertyName, boolean isNull) {
        return findByProperty(isNull ? Restriction.NUL : Restriction.NNUL, propertyName);
    }

    /**
     * 通过orm实体属性名称查询全部
     *
     * @param propertyName orm实体属性名称
     * @param value        值
     * @return M
     */
    public String findFirst(String propertyName, Object value) {
//        List<M> result = findByProperty(Restriction.EQ, propertyName, value);
//        return result.size() > 0 ? result.get(0) : null;
        return findByProperty(Restriction.EQ, propertyName, value);
    }

    /**
     * 通过orm实体属性名称查询全部
     * @param restriction 规则
     * @param propertyName orm实体属性名称
     * @param value        值
     * @return List
     */
    public String findByProperty( Restriction restriction, String propertyName, Object ... value) {
      String whereSQL =propertyName + restriction.toMatchString(propertyName);
        Matcher matcher = Pattern.compile(":(\\w+)").matcher(whereSQL);
        String group;
        for(String rexp = null; matcher.find(); whereSQL = whereSQL.replace(String.format(":%s", group), rexp)) {
            group = matcher.group(1);
            StringBuilder sb = new StringBuilder();
            for (Object v : value)
            {
                sb.append("?,");
            }
            sb.deleteCharAt(sb.length() - 1);
            rexp = sb.toString();

        }
       // return find(String.format("SELECT * from %s where 1=1 and %s", getTableName(), whereSQL), value);
        return String.format("SELECT * from %s where 1=1 and %s", getTableName(), whereSQL);
    }




    protected String getTableName() {
        return "test";
    }



}
