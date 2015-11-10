<%--
  Created by IntelliJ IDEA.
  User: ZaoSheng
  Date: 2015/11/7
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <style type="text/css">
      .top{

          width: 100%;
          height: 50px;
          text-align: center;
      }
      .content{
          border: 1px solid #666;
          width: 100%;
          height: 600px;
      }




  </style>
  <body>
<div class="top">Dynamically generated sql</div>
 <div class="content">

        <div class="column">
            <form action="/testServlet" method="post">
            name(and ; right like):<input name="QUERY^t#name^!|^RLK"/><br/>
            age(and ; >=):<input name="QUERY^t#age^!|^GE"/><br/>
            sex(or ; =):<input name="QUERY^t#sex^|^EQ"/><br/>
                <input type="submit" value="generated">
            </form>
        </div>

 </div>
  </body>
</html>
