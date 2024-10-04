<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
      <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="java.util.List"%>
     <%@page import="java.util.HashMap"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<head>
    <title>Multiselect_Dropdown-With_Checkbox</title>
	
	<link  rel="stylesheet" type="text/css" href="bootstrap-3.0.3.min.css" />
	<link  rel="stylesheet" type="text/css" href="bootstrap-multiselect.css" />
	
	<script type="text/javascript" src="jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="bootstrap-3.0.3.min.js"></script>   
    <script type="text/javascript" src="bootstrap-multiselect.js"></script>
	
	<% 
    	StuduntDAOInf daoinf = new StudentDAOImpl();
    
    	//HashMap<Integer, String> subjectTypeList = daoinf.retrieveScholasticsubjectList();  Change made on 30-07-2024 10:30 AM
   %>
	<script type="text/javascript">
        $(function () {
            $('#lstFruits').multiselect({
                includeSelectAllOption: true
            });
            $('#btnSelected').click(function () {
                var selected = $("#lstFruits option:selected");
                var message = "";
                selected.each(function () {
                    message += $(this).text() + "\n";
                });
                alert(message);
            });
        });
    </script>
	
</head>
<body>
	
	<select id="lstFruits" multiple="multiple">
        <option value="1">Mango</option>
        <option value="2">Apple</option>
        <option value="3">Banana</option>
        <option value="4">Guava</option>
        <option value="5">Orange</option>
    </select>

    <%-- <s:select list="subjectTypeList" headerKey="-1" id="subjectID"  headerValue="Select Subject" name=""  multiple="multiple" >
       <!--<option value="1">Mango</option>
        <option value="2">Apple</option>
        <option value="3">Banana</option>
        <option value="4">Guava</option>
        <option value="5">Orange</option>--->
   </s:select> --%>
    <input type="button" id="btnSelected" value="Get Selected" />


</body>
</html>