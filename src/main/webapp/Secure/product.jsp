<%@ page import="am.myOffice.shopJDBC.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="am.myOffice.shopJDBC.util.constants.Parameter" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/22/2023
  Time: 1:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Page</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:wght@200;300;400;500;600;700&display=swap');
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            min-height: 100vh;
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            background: #009579;
        }

        h1 {
            color: #fff;
            margin-top: 25px;
        }

        .product_crud {
            max-width: 48%;
            width: 100%;
            justify-content: space-between;
        }

        .product_crud div {
            justify-content: space-between;
        }

        .crud_item {
            background-color: #fff;
            border-radius: 10px;
            padding: 25px 20px;
        }

        a {
            text-decoration: none;
            color: #006653;
        }

        .main {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            align-items: center;
            margin: 70px 0;
            max-width: 60%;
            width: 100%;
        }

        .table-wrapper{
            box-shadow: 0px 35px 50px rgba( 0, 0, 0, 0.2 );
            padding: 2px;
        }

        .fl-table {
            border-radius: 5px;
            font-size: 12px;
            font-weight: normal;
            border: none;
            border-collapse: collapse;
            width: 100%;
            max-width: 100%;
            white-space: nowrap;
            background-color: white;
            padding: 2px;
        }

        .fl-table td, .fl-table th {
            text-align: center;
            padding: 8px;
        }

        .fl-table td {
            border-right: 1px solid #f8f8f8;
            font-size: 12px;
            padding: 16px !important;
        }

        .fl-table thead th {
            color: #ffffff;
            background: #324960;
            padding: 12px !important;
        }
    </style>
</head>
<body>

    <%
        List<Product> allProducts = (List<Product>) request.getAttribute(Parameter.PRODUCTS_ATTRIBUTE);
        List<String> columns = (List<String>)request.getAttribute(Parameter.COLUMNS_ATTRIBUTE);
    %>

    <h1>Product Page</h1>
    <div class="main">
        <div class="table-wrapper">
            <table class="fl-table">
                <thead>
                <tr>
                    <%
                        for (String column : columns) { %>
                    <th>
                        <%=column%>
                    </th>
                    <%
                        }
                    %>
                </tr>
                </thead>
                <tbody>
                <%
                    for (Product pr: allProducts) { %>
                <tr>
                    <td><%= pr.getId() %></td>
                    <td><%= pr.getName() %></td>
                    <td><%= pr.getPrice() %></td>
                    <td><%= pr.getCategory() %></td>
                    <td><%= pr.isExists() %></td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
        <div class="product_crud">
            <div style="display: flex">
                <a href="/Secure/CRUDProduct/createProduct.jsp"><div class="crud_item"><span>CREATE</span></div></a>
                <a href="/Secure/CRUDProduct/readProduct.jsp"><div class="crud_item"><span>READ</span></div></a>
                <a href="/Secure/CRUDProduct/updateProduct.jsp"><div class="crud_item"><span>UPDATE</span></div></a>
                <a href="/Secure/CRUDProduct/deleteProduct.jsp"><div class="crud_item"><span>DELETE</span></div></a>
            </div>
        </div>
    </div>
</body>
</html>
