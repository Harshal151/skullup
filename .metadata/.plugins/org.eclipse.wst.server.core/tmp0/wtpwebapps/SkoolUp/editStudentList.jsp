<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
     <%@page import="java.util.HashMap"%>
     <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="com.kovidRMS.form.StudentForm"%>
     <%@page import="com.kovidRMS.form.ConfigurationForm"%>
     <%@page import="com.kovidRMS.daoImpl.ConfigurationDAOImpl"%>
	 <%@page import="com.kovidRMS.daoInf.ConfigurationDAOInf"%>
     <%@page import="java.util.List"%>
     <%@page import="com.kovidRMS.form.LoginForm"%>
    
<!DOCTYPE html>
<html class="no-js"> 
<head>
<%
    	String userListEnable = (String) request.getAttribute("userListEnable");
     	
     	LoginForm loginform = (LoginForm) session.getAttribute("USER");
     	
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	
     	int AcademicYearID = loginform.getAcademicYearID();
		
		String StandardName = daoinf.retrieveStandardName(loginform.getUserID(), AcademicYearID);
    	
    	int StandardID = daoinf.retrieveStandardID(loginform.getUserID(), AcademicYearID);
    	
		String DivisionName = daoinf.retrieveDivisionName(loginform.getUserID(), AcademicYearID);
    	
    	int DivisionID = daoinf.retrieveDivisionID(loginform.getUserID(), AcademicYearID);
		
    	HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	
    	HashMap<Integer, String> DivisionList = daoinf.getDivision(StandardID);
    	
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
    %>
    
   <title> <% if(loginform.getMedium().equals("mr")){ %>  विद्यार्थ्यांची यादी - SkoolUp <%}else{ %> Student List - SkoolUp <%} %> </title>

<!-- Favicon -->
  <link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  <link rel="stylesheet" href="./css/bootstrap.min.css">

  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
  

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
  </script>
  <script type="text/javascript">
  $(document).ready(function(){
		$('#studentLiID').addClass("active");
		//alert("hiii");	
	});
  
  jQuery(document).ready(function($) {
          $(".scroll").click(function(event){     
              event.preventDefault();
              $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
          });
      });
  </script>

<style type="text/css">
	ul.actionMessage{
            list-style:none;
            font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
        }
        
        ul.errorMessage{
            list-style:none;
            font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
        }

      .fixed-header{
          position: fixed;
          top: 43px;
          z-index:1;
          width: 100%;
          height: 107px;
      }
      .fixed-navbar{
          position: fixed;
          
          width: 100%;
      }
      
      .loadingImage{
    		position: absolute;
	        width: 100px; /*the image width*/
	        height: 100px; /*the image height*/
	        left: 50%;
	        top: 50%;
	        margin-left: -50px; /*half the image width*/
	        margin-top: -50px; /*half the image height*/
	        
    	}
    	
  </style>

<script type="text/javascript">
	
	function editUserList(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
		  document.location="EditUserList";
	}
	
	function windowOpen(){
    
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
		document.location="welcome.jsp";
      }
    </script>
    
   <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableStudent(url){
			if(confirm("Disabled student cannot access application. Are you sure you want to disable this student?")){
				document.location = url;
			}
    	}
    </script>
    
    <!-- Ends -->
    
    <!-- Delete user alert function -->
    
    <script type="text/javascript">
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete student?")) {
				document.location = url;
			}
		}
	</script>
    
<script type="text/javascript">
	
	 var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
	function retrieveDivision(standardID){
		
		if(standardID == "-1"){
			alert("No standard is selected. Please select standard.");
			
			var array_element = "<select name='divisionID' id='divID' class='form-control'"+
			"> <option value='-1'>Select Division</option></select>";
			
			document.getElementById("stdDivID").innerHTML = array_element;
		
		}else{
			retrieveDivision1(standardID);
		}
		
	}

	function retrieveDivision1(standardID) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "<select name='divisionID' id='divID' class='form-control'"+
					"> <option value='-1'>Select Division</option>";
					
					var check = 0;
					/* For division */
					for ( var i = 0; i < array.Release.length; i++) {
						
						check = array.Release[i].check;

						array_element += "<option value='"+array.Release[i].divisionID+"'>"+array.Release[i].division+"</option>";
					}

					array_element += " </select>";
					
					if(check == 0){
						
						alert("No division found");
						
						var array_element = "<select name='divisionID' id='divID' class='form-control'"+
						"> <option value='-1'>Select Division</option></select>";
						
						document.getElementById("stdDivID").innerHTML = array_element;
						
					}else{
						
						document.getElementById("stdDivID").innerHTML = array_element;	
													
					}
			}
		};
		xmlhttp.open("GET", "RetrieveDivisionListForStandard?standardID="
				+ standardID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	</script>

<script type="text/javascript">

	function checkList(standard, division){
		
		if(standard == "-1" ){
			alert("Please select standard.");
			return false;
			
		}else if(division == "-1" ){
			
			alert("Please select division.");
			return false;
		
		}else{
			
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		 	}, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); 
		
	  	 	$("html, body").animate({ scrollTop: 0 }, "fast");
	    	$(".loadingImage").show();
	    	$(".container").css("opacity","0.1");
	    	$(".navbar").css("opacity","0.1");
	    	
			$("#validate-basic").attr("action","SearchStudent");
			$("#validate-basic").submit();
			
			$("#submitID").attr("disabled", "disabled");
			
			return true;
		}
  }

	function submitReportForm(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	 	}, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
	
  	 	$("html, body").animate({ scrollTop: 0 }, "fast");
    	$(".loadingImage").show();
    	$(".container").css("opacity","0.1");
    	$(".navbar").css("opacity","0.1");
		
		$("#validate-basic").attr("action","RenderEditStudentList");
		$("#validate-basic").submit();
		
		$("#viewSubmitID").attr("disabled", "disabled");
		
	}
	
</script>
   
</head>

<body>
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
      
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्यांची यादी  <% }else{ %> Student List <%} %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %> मुख्यपृष्ठ  <% }else{ %> Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>  विद्यार्थ्यांची यादी  <% }else{ %> Student List <% } %></li>
        </ol>
       
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                <% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्यांची यादी <% }else{ %>Student List  <% } %>
              </h3>

            </div> <!-- /.portlet-header -->

            <div class="portlet-content">      
              
             <% if(loginform.getMedium().equals("mr")){ %> 
           
            <% if(classTeacherCheck.equals("Yes")){ %> 
            
            	<form id="validate-basic" action="SearchStudent" method="POST" >
            	
            		<div class="row">
            		
            			<%
                			if(loginform.getRole().equals("administrator")){
                		%>
						
							<div class="col-md-2" >
								
								<s:select list="StandardList" headerKey="" headerValue="इयत्ता निवडा" id="standardID" name="standardID" class="form-control" onchange="retrieveDivision(this.value);"></s:select>
							</div>
						
				           	<div class="col-md-2" id="stdDivID" >
				           	
			               		<s:select list="DivisionList" headerKey="" headerValue="तुकडी निवडा" id="divID" name="divisionID" class="form-control" ></s:select>
		                	</div>
				         
				         <%
                			}else{
				         %>
				        
							<div class="col-md-2" >
								<input type="hidden" class="form-control" name="standardID" value="<%=StandardID%>" >
								<input type="text" class="form-control" name="" value="<%=StandardName%>" readonly="readonly">
								
							</div>
						
				           	<div class="col-md-2" >
				           		<input type="hidden" class="form-control" name="divisionID" value="<%=DivisionID%>" >
								<input type="text" class="form-control" name="" value="<%=DivisionName%>" readonly="readonly">
			               			
		                	</div>
				         
				         <%
                			}
				         %>
				         
                       <div class="col-md-2">
                        <s:textfield  name="searchStudentName" cssStyle="width:100%;" cssClass="form-control" required="required"></s:textfield> 
                       </div>
                       <div class="col-md-2">
                          <button type="submit" class="btn btn-success" id="submitID" onclick="return checkList(standardID.value, divID.value)" >विद्यार्थी शोधा</button>
                        </div>
                        <div class="col-md-2">
                          <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitReportForm();">सर्व विद्यार्थी पहा</button>
                        </div>
                       </div>
             	</form>      
             	<%} %>
				  	
				  	<div>
		              	<s:if test="hasActionErrors()">
							<center>
							<font style="color: red; font-size:16px;"><s:actionerror /></font>
							</center>
						</s:if><s:else>
							<center>
							<font style="color: green;font-size:16px;"><s:actionmessage /></font>
							</center>
						</s:else>
		              </div>
		             
		 <% if(userListEnable.equals("userSearchListEnable")){  %> 
		            
		      <div class="table-responsive" style="margin-top:15px;">
              <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">नाव</th>
                      <th data-sortable="true">आधार क्रमांक</th>
                       <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchStudentList" var="UserForm">
                    <tr>
                     	<td><s:property value="fullName" /></td>
						
						<td><s:property value="aadhaar" /></td>
						
						<td align="center">
							<s:url id="approveURL" action="RenderEditStudent">
								<s:param name="studentID" value="%{studentID}" />
								<s:param name="standardID" value="%{standardID}" />
								<s:param name="divisionID" value="%{divisionID}" />
								<s:param name="searchStudentName" value="%{searchStudentName}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
									onmouseout="this.src='images/user_1.png'" alt="विद्यार्थी एडिट करा" title="विद्यार्थी एडिट करा" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							
							<s:url id="disableStudentURL" action="DisableStudent">
								<s:param name="studentID" value="%{studentID}" />
								<s:param name="searchStudentName" value="%{searchStudentName}" />
							</s:url> 
							<s:a href="javascript:disableStudent('%{disableStudentURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="डीलीट करा" title="डीलीट करा" />
							</s:a>
						</td>
                       </tr>
                     </s:iterator>
                  
                   </tbody>
                </table>
                </div>
                
                <%
                  	}else if(userListEnable.equals("userListEnable")){
                   %>
                  
		        <div class="table-responsive" style="margin-top:15px;">
		        <table class="table table-striped table-bordered table-hover table-highlight table-checkable" data-provide="datatable"
                	 data-display-rows="10" data-info="true" data-search="true" data-length-change="true" data-paginate="true" >
                  <thead>
                    <tr>
                   	   <th data-sortable="true">नाव</th>
                      <th data-sortable="true">आधार क्रमांक</th>
                       <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
                   <s:iterator value="signedUpStudentList" var="UserForm">
                    <tr>
                     	<td><s:property value="fullName" /></td>
						
						<td><s:property value="aadhaar" /></td>
				
						<td align="center">
							<s:url id="approveURL" action="RenderEditStudent">
								<s:param name="studentID" value="%{studentID}" />
								<s:param name="standardID" value="%{standardID}" />
								<s:param name="divisionID" value="%{divisionID}" />
								<s:param name="searchStudentName" value="%{searchStudentName}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="विद्यार्थी एडिट करा" title="विद्यार्थी एडिट करा" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableStudentURL" action="DisableStudent">
								<s:param name="studentID" value="%{studentID}" />
								<s:param name="searchStudentName" value="%{searchStudentName}" />
							</s:url> 
							<s:a href="javascript:disableStudent('%{disableStudentURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="विद्यार्थी  डीलीट करा" title="विद्यार्थी डीलीट करा" />
							</s:a>
						</td>
                       </tr>
                       </s:iterator>
                  
                   </tbody>
                </table> 
               	</div>
                <% } %>
                
             <% }else{ %>
               
              <% if(classTeacherCheck.equals("Yes")){ %> 
            
            	<form id="validate-basic" action="SearchStudent" method="POST" >
            	
            		<div class="row">
            		
            			<%
                			if(loginform.getRole().equals("administrator")){
                		%>
						
							<div class="col-md-2" >
								
								<s:select list="StandardList" headerKey="" headerValue="Select Standard" id="standardID" name="standardID" class="form-control" onchange="retrieveDivision(this.value);"></s:select>
							</div>
						
				           	<div class="col-md-2" id="stdDivID" >
				           	
			               		<s:select list="DivisionList" headerKey="" headerValue="Select Division" id="divID" name="divisionID" class="form-control" ></s:select>
		                	</div>
				         
				         <%
                			}else{
				         %>
				        
							<div class="col-md-2" >
								<input type="hidden" class="form-control" name="standardID" value="<%=StandardID%>" >
								<input type="text" class="form-control" name="" value="<%=StandardName%>" readonly="readonly">
								
							</div>
						
				           	<div class="col-md-2" >
				           		<input type="hidden" class="form-control" name="divisionID" value="<%=DivisionID%>" >
								<input type="text" class="form-control" name="" value="<%=DivisionName%>" readonly="readonly">
			               			
		                	</div>
				         
				         <%
                			}
				         %>
				         
                       <div class="col-md-2">
                        <s:textfield  name="searchStudentName" cssStyle="width:100%;" cssClass="form-control" required="required"></s:textfield>
                       </div>
                       <div class="col-md-2">
                          <button type="submit" class="btn btn-success" id="submitID" onclick="return checkList(standardID.value, divID.value)" >Search Student</button>
                        </div>
                        <div class="col-md-2">
                          <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitReportForm();">View All Students</button>
                        </div>
                       </div>
             	</form>      
             	<%} %>
				<%
                  	if(userListEnable == null || userListEnable == ""){
                  %>
                  
                  	<div>
		              	<s:if test="hasActionErrors()">
							<center>
							<font style="color: red; font-size:16px;"><s:actionerror /></font>
							</center>
						</s:if><s:else>
							<center>
							<font style="color: green;font-size:16px;"><s:actionmessage /></font>
							</center>
						</s:else>
		              </div>
		             
		            <%
                  	}else if(userListEnable.equals("userSearchListEnable")){
		            %> 
		            
		              <div>
		              	<s:if test="hasActionErrors()">
							<center>
							<font style="color: red; font-size:16px;"><s:actionerror /></font>
							</center>
						</s:if><s:else>
							<center>
							<font style="color: green;font-size:16px;"><s:actionmessage /></font>
							</center>
						</s:else>
		              </div>
		              
		      <div class="table-responsive" style="margin-top:15px;">
              <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Name</th>
                      <th data-sortable="true">Aadhaar No</th>
                       <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchStudentList" var="UserForm">
                    <tr>
                     	<td><s:property value="fullName" /></td>
						
						<td><s:property value="aadhaar" /></td>
						
						<td align="center">
							<s:url id="approveURL" action="RenderEditStudent">
								<s:param name="studentID" value="%{studentID}" />
								<s:param name="standardID" value="%{standardID}" />
								<s:param name="divisionID" value="%{divisionID}" />
								<s:param name="searchStudentName" value="%{searchStudentName}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Student" title="Edit Student" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							
							<s:url id="disableStudentURL" action="DisableStudent">
								<s:param name="studentID" value="%{studentID}" />
								<s:param name="searchStudentName" value="%{searchStudentName}" />
							</s:url> 
							<s:a href="javascript:disableStudent('%{disableStudentURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable Student" title="Disable Student" />
							</s:a>
						</td>
                       </tr>
                     </s:iterator>
                  
                   </tbody>
                </table>
                </div>
                
                <%
                  	}else if(userListEnable.equals("userListEnable")){
                   %>
                   
                   <div>
		              	<s:if test="hasActionErrors()">
							<center>
							<font style="color: red; font-size:16px;"><s:actionerror /></font>
							</center>
						</s:if><s:else>
							<center>
							<font style="color: green;font-size:16px;"><s:actionmessage /></font>
							</center>
						</s:else>
		              </div>
		        <div class="table-responsive" style="margin-top:15px;">
		        <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Name</th>
                      <th data-sortable="true">Aadhaar No</th>
                      <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                   <s:iterator value="signedUpStudentList" var="UserForm">
                    <tr>
                     	<td><s:property value="fullName" /></td>
						
						<td><s:property value="aadhaar" /></td>
				
						<td align="center">
							<s:url id="approveURL" action="RenderEditStudent">
								<s:param name="studentID" value="%{studentID}" />
								<s:param name="standardID" value="%{standardID}" />
								<s:param name="divisionID" value="%{divisionID}" />
								<s:param name="searchStudentName" value="%{searchStudentName}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Student" title="Edit Student" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableStudentURL" action="DisableStudent">
								<s:param name="studentID" value="%{studentID}" />
								<s:param name="searchStudentName" value="%{searchStudentName}" />
							</s:url> 
							<s:a href="javascript:disableStudent('%{disableStudentURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable Student" title="Disable Student" />
							</s:a>
						</td>
                       </tr>
                       </s:iterator>
                  
                   </tbody>
                </table> 
               	</div>
                <%
                  	}
                %>
              
              <% } %>  
			</div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->

        </div> <!-- /.col -->

      </div> <!-- /.row -->
	 </div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  <script src="./js/libs/bootstrap.min.js"></script>

  <!-- Plugin JS -->
  <script src="./js/plugins/icheck/jquery.icheck.js"></script>
  <script src="./js/plugins/select2/select2.js"></script>
  <script src="./js/libs/raphael-2.1.2.min.js"></script>
  <script src="./js/plugins/morris/morris.min.js"></script>
  <script src="./js/plugins/sparkline/jquery.sparkline.min.js"></script>
  <script src="./js/plugins/nicescroll/jquery.nicescroll.min.js"></script>
  <script src="./js/plugins/fullcalendar/fullcalendar.min.js"></script>

  <!-- App JS -->
  <script src="./js/target-admin.js"></script>
  
  <!-- Plugin JS -->
  <script src="./js/demos/dashboard.js"></script>
  <script src="./js/demos/calendar.js"></script>
  <script src="./js/demos/charts/morris/area.js"></script>
  <script src="./js/demos/charts/morris/donut.js"></script>
  
</body>
</html>
