<%@page import="javassist.expr.NewArray"%>
<%@page import="com.kovidRMS.daoImpl.ConfigurationDAOImpl"%>
<%@page import="com.kovidRMS.daoInf.ConfigurationDAOInf"%>
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
     <%@page import="java.util.List"%>
      <%@page import="com.kovidRMS.form.LoginForm"%>
      <%@page import="com.kovidRMS.util.ConfigXMLUtil"%> 
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Report Page - SkoolUp</title>
<!-- Favicon -->
  <link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  <link rel="stylesheet" href="./css/bootstrap.min.css">
  <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">
  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/morris/morris.css">
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">
  <link rel="stylesheet" href="./js/plugins/select2/select2.css">
  <link rel="stylesheet" href="./js/plugins/fullcalendar/fullcalendar.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"> </script>
 
	<%
		LoginForm form = (LoginForm) session.getAttribute("USER");

		int userID = form.getUserID();
		
		LoginDAOInf daoInf = new LoginDAOImpl();
		
		String AcademicYearName = daoInf.retrieveAcademicYearName(form.getOrganizationID());
		
		String signOnRC = daoInf.retrieveSignOnRC(form.getOrganizationID());
		
		String GradeValue = (String) request.getAttribute("GradeValue");
		
		String loadStudents = (String) request.getAttribute("StudentsFound");
		
		if(loadStudents == null || loadStudents == ""){
			loadStudents = "dummy";
		}
		
		String classTeacher10thCheck = (String) request.getAttribute("classTeacher10thCheck");
		
		if(classTeacher10thCheck == null || classTeacher10thCheck == ""){
			classTeacher10thCheck = "dummy";
		}
		
		String classTeacher9th10thCheck = (String) request.getAttribute("classTeacher9th10thCheck");
		
		int extraCurricularCheck = (Integer) request.getAttribute("extraCurricularCheck");
		
		String divAdditionalCss = "";
		
		if(classTeacher9th10thCheck == null || classTeacher9th10thCheck == ""){
			classTeacher9th10thCheck = "dummy";
			divAdditionalCss = "";
		}else{
			divAdditionalCss = "display: none;";
		}
		
		String StdDivName = (String) request.getAttribute("StdDivName");
		
		String statusValue = (String) request.getAttribute("statusValue");
		
    	List<ConfigurationForm> PersonalityDevelopmentGradeListNew = (List<ConfigurationForm>) request.getAttribute("PersonalityDevelopmentGradeListNew");
    	
    	int standardID = Integer.parseInt(request.getParameter("standardID"));
    	
    	String sectionName = daoInf.getStandardStageByStandardID(standardID);
    	
    	System.out.println("section name :::: "+sectionName+" , "+signOnRC);
    	
    %>
	
	
	
  <script type="text/javascript">
  	$(document).ready(function(){
		$('#reportLiID').addClass("active");
	});
  
    jQuery(document).ready(function($) {
        $(".scroll").click(function(event){     
            event.preventDefault();
            $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
        });
    });
</script>
 
<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>

<script type="text/javascript">
	
	function googleTranslateElementInit() {
		  new google.translate.TranslateElement({pageLanguage: 'en'}, 'google_translate_element');
	}
	
	function changeLanguageByButtonClick(language) {
		setTimeout(function(){
			var selectField = document.querySelector("#google_translate_element select");
			
			  for(var i=0; i < selectField.children.length; i++){
			    var option = selectField.children[i];
			    // find desired langauge and change the former language of the hidden selection-field 
			    
			    if(option.value==language){
			       selectField.selectedIndex = i;
			       // trigger change event afterwards to make google-lib translate this side
			       selectField.dispatchEvent(new Event('change'));
			       break;
			    }
			  }
		},1000);
	}
</script>

<style>
	.goog-te-banner-frame,.custom-translate {
	        display: none;
	}
	
	body {
	        top: 0 !important;
	    }
	.goog-tooltip {
	    display: none !important;
	}
	.goog-tooltip:hover {
	    display: none !important;
	}
	.goog-text-highlight {
	    background-color: transparent !important;
	    border: none !important; 
	    box-shadow: none !important;
	}

</style> 

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
     
</style>

<script type="text/javascript">
	
      function windowOpen(){
        document.location="welcome.jsp";
      }
  	
      function windowOpen1(){
         document.location="RenderClassStudents";
      }
      
	function gradeCkeck(gradeValue) {
			
		if(confirm("This student has scored E grade in "+gradeValue+". Are you sure you want to print this student's report?")){
			$("#validate-basic").submit();	
		}  
	}
</script>
	
</head>

<body onload="changeLanguageByButtonClick('<%=form.getMedium()%>');">

<img src="images/loading.gif" alt="Loading..." class="loadingImage" style="z-index:1;display: none;">
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="google_translate_element" style="display:none"></div>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Report Page</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li><a href="RenderClassStudents">Student Details</a></li>
          <li class="active">Report Page</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                Student Details
              </h3>

            </div> <!-- /.portlet-header -->

            <div class="portlet-content">
			
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
		    
		<form id="validate-basic" action="StudentReportPDF" method="POST" data-validate="parsley" class="form parsley-form">
		  <input type="hidden" name="standardID" value="<s:property value="standardID"/>">
		<div class="row">  
		  
			 <div class="col-lg-2" style="text-align: center;"> 
				<img src= "<%= daoInf.retrieveBoardLogo(form.getOrganizationID()) %>" style="height: 100px;" alt="Board Logo">
			</div>
			  
			<div class="col-lg-8" style="text-align: center;font-weight: bold;">
			  	
			    <%= daoInf.retrieveOrganizationTagLine(form.getOrganizationID()) %>
			  	<br>
			    <%= daoInf.retrieveOrganizationName(form.getOrganizationID()) %>
			    <br>
				<%= daoInf.retrieveOrganizationAddress(form.getOrganizationID()) %>
				<br>
				<%= daoInf.retrieveOrganizationPhone(form.getOrganizationID()) %>
				
			</div>	
				
			<div class="col-lg-2" style="text-align: center;">
				<img src="<%= daoInf.retrieveOrganizationLogo(form.getOrganizationID()) %>" style="height: 100px;" alt="Organization Logo">
			</div>
		
		</div>
		
		<hr style="border: 1px solid gray;">
		
		<div class="row" style="margin: 0px;">
			<s:iterator  value="studentDetailsList">
		      <input type="hidden" name="studentName" value="<s:property value="studentName"/>">
		      <input type="hidden" name="studentID" value="<s:property value="studentID"/>">
		      <input type="hidden" name="registrationID" value="<s:property value="registrationID"/>">
		      <input type="hidden" name="AYClassID" value="<s:property value="AYClassID"/>">
		      <input type="hidden" name="term" value="<s:property value="term"/>">
		      <input type="hidden" name="result" value="<s:property value="result"/>">
		      
		      <script type="text/javascript">
			  	$(document).ready(function(){
			  		$('#stdDivID').text("<s:property value='standard'/><s:property value='division'/>");
			  	});
		      </script>
		      
		      	<div class="row">
					<div class="col-lg-12" style="text-align: center;font-weight: bold;">
	             		Report Card <s:property value="term"/>&nbsp;&nbsp;&nbsp;(<%=AcademicYearName%>)
	        		</div>
        		</div>	        
				<%-- <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;"> --%>
				<div class="row" style="font-weight: bold;">
					<div class="col-lg-4" >Student's Name : <s:property value="studentName"/></div>
				                          
				   	<div class="col-lg-3">STD : <s:property value="standard"/></div>
				                          
				   	<div class="col-lg-3">Div : <s:property value="division"/></div>
				                          
				   	<div class="col-lg-2" >Roll.No : <s:property value="rollNumber"/></div>
				</div>
				<div class="row" style="font-weight: bold;">
				<%-- <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;"> --%>
				
				   	<div class="col-lg-4" >DOB : <s:property value="dateOfBirth"/></div>
				   	
				   	<div class="col-lg-3"></div>
				                          
				   	<div class="col-lg-3"></div>
				    
				  	<div class="col-lg-2" >GR.No : <s:property value="grNumber"/></div>
			   
			 	</div>
			</s:iterator>
		</div>
		  	<%
					if(loadStudents.equals("Found")){
			%>
		  	<div class="row">
		    <div class="col-lg-12" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		      <b>Scholastic Areas : </b>   
		    <table id="" class="table table-striped table-bordered dt-responsive nowrap" >
		    
			    <thead>
					<tr>
						<th style="text-align: center;">Sr No.</th>
						
						<th style="width:10%;">Subjects</th>
			            
			            <th style="width:5%;text-align: center;">Internal Marks<br>(10)</th>
			            
			            <th style="width:8%;text-align: center;">Term End<br>(40)</th>
			                           
			            <th style="width:8%;text-align: center;">Marks Obtained<br>(50)</th>
			               
			            <th style="width:8%;text-align: center;">Grade</th>
					</tr>
				</thead>
				
				<tbody>
				<%  int counter1 = 1; %>
					<s:iterator  value="ScholasticGradeList">
					
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 8%;">
				       			<%= counter1++ %>
				       		</td>
				       		
				       		<td style="text-align: left;width: 40%;">
				       			<s:property value="subject"/>
				       		</td>
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="unitTestMarks"/>
				       		</td>
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="termEndMarks"/>
				       		</td>
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="finalTotalMarks"/>
				       		</td>	
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="grade"/>
				       		</td>
				       </tr> 
				        
				       </s:iterator>
				</tbody>
			</table>
		</div>  
		
		</div>
		  	<%}else if(loadStudents.equals("NotFound")){ %>
		    <div class="row">
		    <div class="col-lg-12" style="background:white;margin-left:80px;margin-right:0px;margin-bottom:20px;padding-top: 20px;width: 82%;">
		      <b>Scholastic Areas : </b>   
		    <table id="" class="table table-striped table-bordered dt-responsive nowrap" >
		    
			    <thead>
					<tr>
						<th style="text-align: center;">Sr No.</th>
						
						<th style="width:20%;">Subjects</th>
			             
			   			<th style="width:5%;text-align: center;">Internal Marks<br>(20)</th>
			           
			           	<th style="width:5%;text-align: center;">Term End<br>(30/80)</th>
			           	
			           	<th style="width:5%;text-align: center;">Marks Obtained<br>(50/100)</th>
			                          
			            <th style="width:5%;text-align:center">Grade</th>
			        
			    <%--   <% } %>
			         --%>
					</tr>
				</thead>
				
				<tbody>
				<%  int counter1 = 1; %>
				
					<s:iterator  value="ScholasticGradeList">
					
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		<td style="text-align: center;width: 8%;">
				       			<%= counter1++ %>
				       		</td>
				       		
				       		<td style="text-align: left;width: 40%;">
				       			<s:property value="subject"/>
				       		</td>
				       	
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="unitTestMarks"/>
				       		</td>
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="termEndMarks"/>
				       		</td>
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="finalTotalMarks"/>
				       		</td>	
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="grade"/>
				       		</td>
				  
				       </tr> 
				        
				       </s:iterator>
				</tbody>
			</table>
		</div>  
		
		</div>
		<%} %>
		<div class="row">
		<%  int counter1 = 1; %>
		<%
			if(classTeacher9th10thCheck.equals("Yes")){
		%>
		<div class="col-lg-3"></div>
		<div class="col-lg-6" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		    <b>Co - Scholastic Areas :</b>      
		    <table id="" class="table table-striped table-bordered dt-responsive nowrap" >
		    
			    <thead>
					<tr>
						<th style="text-align: center;">Sr No.</th>
						<th>Subjects</th>
			                          
			            <th style="text-align: center;">Grade</th>
					</tr>
				</thead>
				
				<tbody>
					 	<s:iterator  value="coScholasticGradeList">
					 	
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 12%;">
				       			<%= counter1++ %>
				       		</td>
				       		
				       		<td style="text-align: left;width: 80%;">
				       			<s:property value="subject"/>
				       		</td>	
				       		
				       		<td style="text-align: center;">
				       			<s:property value="grade"/>
				       		</td>
				       </tr> 
				        
				        </s:iterator>
				</tbody>
			</table>
		</div>
		<div class="col-lg-3"></div>
		
		<%
			}else{
		%>
		
		<%-- <div class="col-lg-6" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		    <b>Co - Scholastic Areas :</b>      
		    <table id="" class="table table-striped table-bordered dt-responsive nowrap" >
		    
			    <thead>
					<tr>
						<th style="text-align: center;">Sr No.</th>
						<th>Subjects</th>
			                          
			            <th style="text-align: center;">Grade</th>
					</tr>
				</thead>
				
				<tbody>
					 	<s:iterator  value="coScholasticGradeList">
					 	
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 12%;">
				       			<%= counter1++ %>
				       		</td>
				       		
				       		<td style="text-align: left;width: 80%;">
				       			<s:property value="subject"/>
				       		</td>	
				       		
				       		<td style="text-align: center;">
				       			<s:property value="grade"/>
				       		</td>
				       </tr> 
				        
				        </s:iterator>
				</tbody>
			</table>
		</div> --%>
		
		<s:if test="%{getExtraCurricularGradeList().isEmpty()}"> 
		
			<div class="col-lg-3"></div>
			<div class="col-lg-6" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
			    <b>Co - Scholastic Areas :</b>      
			    <table id="" class="table table-striped table-bordered dt-responsive nowrap" >
			    
				    <thead>
						<tr>
							<th style="text-align: center;">Sr No.</th>
							<th>Subjects</th>
				                          
				            <th style="text-align: center;">Grade</th>
						</tr>
					</thead>
					
					<tbody>
						 	<s:iterator  value="coScholasticGradeList">
						 	
					       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
					       		
					       		<td style="text-align: center;width: 12%;">
					       			<%= counter1++ %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 80%;">
					       			<s:property value="subject"/>
					       		</td>	
					       		
					       		<td style="text-align: center;">
					       			<s:property value="grade"/>
					       		</td>
					       </tr> 
					        
					        </s:iterator>
					</tbody>
				</table>
			</div>
			<div class="col-lg-3"></div>
		</s:if>
		
		<s:else>
		
		<div class="col-lg-6" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		    <b>Co - Scholastic Areas :</b>      
		    <table id="" class="table table-striped table-bordered dt-responsive nowrap" >
		    
			    <thead>
					<tr>
						<th style="text-align: center;">Sr No.</th>
						<th>Subjects</th>
			                          
			            <th style="text-align: center;">Grade</th>
					</tr>
				</thead>
				
				<tbody>
					 	<s:iterator  value="coScholasticGradeList">
					 	
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 12%;">
				       			<%= counter1++ %>
				       		</td>
				       		
				       		<td style="text-align: left;width: 80%;">
				       			<s:property value="subject"/>
				       		</td>	
				       		
				       		<td style="text-align: center;">
				       			<s:property value="grade"/>
				       		</td>
				       </tr> 
				        
				        </s:iterator>
				</tbody>
			</table>
		</div>
		
		<div class="col-lg-6" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">	
			<b>Extra Curricular Activities :</b>
			<table id="" class="table table-striped table-bordered dt-responsive nowrap" >
		    	<thead>
					<tr>
						<th style="text-align: center;">Sr No.</th>
						<th>Subjects</th>
			                          
			            <th style="text-align: center;">Grade</th>
					</tr>
				</thead>
				
				<tbody>
				<%  counter1 = 1; %>
				      <s:iterator  value="ExtraCurricularGradeList">
					 	
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 12%;">
				       			<%= counter1++ %>
				       		</td>
				       		
				       		<td style="text-align: left;width: 80%;">
				       			<s:property value="subject"/>
				       		</td>	
				       		
				       		<td style="text-align: center;">
				       			<s:property value="grade"/>
				       		</td>
				       </tr> 
				        
				   </s:iterator> 
				       
				</tbody>
			</table>
		</div>
		
		</s:else>
		<%
			}
		%>
		</div>
		
		<div class="row">
		<div class="col-lg-6" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		    <b>Personality Development :</b>      
		    <table id="" class="table table-striped table-bordered dt-responsive nowrap"  >
		    	<thead>
					<tr>
						
						<th style="text-align: center;">Sr No.</th>
				       		
						<th>Subjects</th>
			                          
			            <th style="text-align: center;">Grade</th>
					</tr>
				</thead>
				<%  counter1 = 1; %>
				<tbody>
					
				     <s:iterator  value="PersonalityDevelopmentGradeList">
					 	
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		<td style="text-align: center;width: 12%;">
				       			<%= counter1++ %>
				       		</td>
				       		<td style="text-align: left;width: 80%;">
				       			<s:property value="subject"/>
				       		</td>	
				       		
				       		<td style="text-align: center;">
				       			<s:property value="grade"/>
				       		</td>
				       </tr> 
				        
				    </s:iterator> 
				    
				 <% for(ConfigurationForm form1: PersonalityDevelopmentGradeListNew){ %>
				   
					 <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
			       		
			       		<td style="text-align: center;width: 12%;">
				       		<%= counter1++ %>
				       	</td>
				       		
			       		<td style="text-align: left;width: 80%;">
			       			<%= form1.getSubject()%>
			       		</td>	
			       		
			       		<td style="text-align: center;">
			       			<%= form1.getGrade()%>
			       		</td>
			       </tr> 
				   
				<%  } %>
				    
				</tbody>
			</table>
			</div>
		<div class="col-lg-6" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		
			<s:if test="%{getAttendanceList().isEmpty()}"> </s:if>
			<s:else>
					
			<b>Attendance :</b>
			<table id="" class="table table-striped table-bordered dt-responsive nowrap"  >
		    	<tbody>
				     <s:iterator  value="AttendanceList">
					 	<tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		<td style="text-align: left;width: 85%;"><s:property value="workingMonth"/></td>	
				       		<td style="text-align: center;"><s:property value="studentWorkingDays"/></td>
				        </tr> 
				     </s:iterator> 
				</tbody>
			</table>
			
			<br>
			</s:else>
			<%
					if(loadStudents.equals("Found")){
			%>
			
			<b>Grading Scale :</b>
			<table id="" class="table table-striped table-bordered dt-responsive nowrap" style="text-align: center;">
		    	<thead>
					<tr>
						<th style="text-align: center;">Marks</th>
			                          
			            <th style="text-align: center;">Grade</th>
			            
			            <th style="text-align: center;">Marks</th>
			                          
			            <th style="text-align: center;">Grade</th>
					</tr>
				</thead>
				
				<tbody>
				<tr>
					<td>46-50</td>
					
					<td>A1</td>
					
					<td>26-30</td>
					
					<td>C1</td>
				</tr>
				<tr>
					<td>41-46</td>
					
					<td>A2</td>
					
					<td>21-25</td>
					
					<td>C2</td>
				</tr>
				<tr>
					<td>36-40</td>
					
					<td>B1</td>
					
					<td>17-20</td>
					
					<td>D</td>
				</tr>
				<tr>
					<td>31-35</td>
					
					<td>B2</td>
					
					<td>16 & Below</td>
					
					<td>E</td>
				</tr>
			  </tbody>
			</table>
			
			<%
					}else{
			%>
			
			<b>Grading Scale :</b>
			<table id="" class="table table-striped table-bordered dt-responsive nowrap" style="text-align: center;">
		    	<thead>
					<tr>
						<th style="text-align: center;">Marks</th>
			                          
			            <th style="text-align: center;">Grade</th>
			            
			            <th style="text-align: center;">Marks</th>
			                          
			            <th style="text-align: center;">Grade</th>
					</tr>
				</thead>
				
				<tbody>
				<tr>
					<td>91-100</td>
					
					<td>A1</td>
					
					<td>51-60</td>
					
					<td>C1</td>
				</tr>
				<tr>
					<td>81-90</td>
					
					<td>A2</td>
					
					<td>41-50</td>
					
					<td>C2</td>
				</tr>
				<tr>
					<td>71-80</td>
					
					<td>B1</td>
					
					<td>33-40</td>
					
					<td>D</td>
				</tr>
				<tr>
					<td>61-70</td>
					
					<td>B2</td>
					
					<td>32 & Below</td>
					
					<td>E</td>
				</tr>
			  </tbody>
			</table>
			
			<%
					}
			%>
			
			<br>
			
			<table id="" class="table table-striped table-bordered dt-responsive nowrap"  >
				
				<thead><tr><th>Grade Code for Personality Development</th></tr></thead>
				<tbody><tr><td>G = Good; S = Satisfactory; NI = Needs Improvement</td></tr></tbody>
			</table>
			
			</div>
			</div>
			<div class="row" >
				<%
					String Status = "";
        			if(StdDivName != null && StdDivName != ""){ %> 
        			
	        			<div class="col-lg-12" style="text-align: center;font-weight: bold;">
	        			
	        			<% if(statusValue == null || statusValue ==""){ %>
	        				
	        				Promoted to - <%=StdDivName %>
	        				
	        			<% }else if(statusValue.isEmpty()){ %>
	        				
	        				Promoted to - <%=StdDivName %>
	        				
	        			<%}else if(statusValue.equals("null")){ %>
	        			
	        				Promoted to - <%=StdDivName %>
	        				
	        			<% }else{ 
	        					if(statusValue.equals("Detained in")){
	        			%>
	        				<%= statusValue %> - <span id="stdDivID"><s:property value="standard"/><s:property value="division"/></span>
	        			<%
	        					}else{
	        			%>
		             	
		             		<%= statusValue %> - <%=StdDivName %>
		             	
		             	<% 		} 
	        				}
		             	%>
		             		
		        		</div>
	        	<%} %>
	        </div>
	        <br><br>
	        <%
	            if(sectionName.equals("Primary")  && signOnRC.equals("sectionHead"))
	            {
	            	%>
	            		
	            		<div class="col-lg-4" style="text-align: center;" >
							<b>Signature of Class Teacher</b>
						</div>
						
						<div class="col-lg-4" style="text-align: center;">
							<b>Signature of Principal</b><br>
							<!-- <b>(Primary)</b> -->
						</div>
					 	
						<div class="col-lg-4" style="text-align: center;">
							<b>Signature of Parent</b>
						</div>	
						</div>
	            	<% 
	            }else if(sectionName.equals("Secondary")  && signOnRC.equals("sectionHead") )
	            {
	            	%>
	            		<div class="col-lg-3" >
							<b>Signature of Class Teacher</b>
						</div>
						
	            		<div class="col-lg-5" style="text-align: center;">
							<b>Signature of Principal</b><br>
							<!-- <b>(Secondary)</b> -->
						</div>
					 	
						<div class="col-lg-4" style="text-align: center;">
							<b>Signature of Parent</b>
						</div>	
						</div>
            		<% 
	            }else
	            {
	            	%>
	            	<div class="col-lg-5" >
						<b>Signature of Class Teacher</b>
					</div>
				 		 
					<div class="col-lg-3" style="text-align: center;">
						<b>Signature of Principal</b>
					</div>
					
					<div class="col-lg-4" style="text-align: center;">
						<b>Signature of Parent</b>
					</div>	
					</div>
            	<% 
	            }
	        %>
			
			
			
			<%if(GradeValue == null || GradeValue == ""){ %>
				<div  class="row" align="center" style="margin-top: 5%;">
					  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
	                  <button type="submit" class="btn btn-success">Print</button>
	            </div>
				
			<%}else{ %>
			
				<div  class="row" align="center" style="margin-top: 5%;">
					  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
	                  <button type="button" onclick="gradeCkeck('<%=GradeValue %>');" class="btn btn-success">Print</button>
	            </div>
            <%} %>
            
		</form>
             </div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->
          
        </div> <!-- /.col -->

      </div> <!-- /.row -->

	</div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

  <%-- <script src="./js/libs/jquery-1.10.1.min.js"></script> --%>
  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  <script src="./js/libs/bootstrap.min.js"></script>

  <!--[if lt IE 9]>
  <script src="./js/libs/excanvas.compiled.js"></script>
  <![endif]-->
  
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
  <script src="./js/plugins/datepicker/bootstrap-datepicker.js"></script>
  
</body>
</html>
