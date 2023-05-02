<%--
  Created by IntelliJ IDEA.
  User: Alshie
  Date: 4/7/2023
  Time: 10:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <title>Jquery multiple select with checkboxes using bootstrap-multiselect.js</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script type="text/javascript" src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/js/bootstrap-multiselect.js"></script>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/css/bootstrap-multiselect.css">
</head>

<body>

<div class="container">
    <strong>Select Language:</strong>
    <select class="multiple-checkboxes" multiple="multiple">
        <option value="php">PHP</option>
        <option value="javascript">JavaScript</option>
        <option value="java">Java</option>
        <option value="sql">SQL</option>
        <option value="jquery">Jquery</option>
        <option value=".net">.Net</option>
    </select>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $('.multiple-checkboxes').multiselect();
    });
</script>

</body>

</html>
