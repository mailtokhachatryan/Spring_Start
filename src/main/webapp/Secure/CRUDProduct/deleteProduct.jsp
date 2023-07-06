<%@ page import="am.myOffice.shopJDBC.util.constants.Parameter" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 6/20/2023
  Time: 9:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Delete Product</title>
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
            flex-direction: column;
            display: flex;
            justify-content: center;
            align-items: center;
            background: #009579;
        }

        .container {
            max-width: 430px;
            width: 100%;
            background: #fff;
            border-radius: 7px;
            box-shadow: 0 5px 10px rgba(0,0,0,0.3);
        }

        .container .form {
            padding: 2rem;
        }

        .form header {
            font-size: 2rem;
            font-weight: 500;
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .form input {
            height: 60px;
            width: 100%;
            padding: 0 15px;
            font-size: 17px;
            margin-bottom: 1.3rem;
            border: 1px solid #ddd;
            border-radius: 6px;
            outline: none;
        }

        .form input:focus {
            box-shadow: 0 1px 0 rgba(0,0,0,0.2);
        }

        .form a {
            font-size: 16px;
            color: #009579;
            text-decoration: none;
        }

        .form a:hover {
            text-decoration: underline;
        }

        .form input.button {
            color: #fff;
            background: #009579;
            font-size: 1.2rem;
            font-weight: 500;
            letter-spacing: 1px;
            margin-top: 1.7rem;
            cursor: pointer;
            transition: 0.4s;
        }

        .message {
            padding-bottom: 30px;
            color: red;
        }
    </style>
</head>
<body>
    <h3 class="message" ><%= request.getAttribute(Parameter.MESSAGE_ATTRIBUTE) != null ?
            request.getAttribute(Parameter.MESSAGE_ATTRIBUTE) : "" %></h3>

    <div class="container">
        <div class="login form">
            <header>Delete Product</header>
            <form method="post" action="/deleteProduct">
                <input type="text" placeholder="id" name="id">
                <input type="submit" class="button" value="delete">
            </form>
        </div>
    </div>

</body>
</html>