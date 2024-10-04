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
    <%@page import="java.util.Map.Entry"%> 
     
<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%
	
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		LoginDAOInf daoinf = new LoginDAOImpl();
	
    	HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	ConfigurationForm conform = new ConfigurationForm();
    	
    	int AcademicYearID = loginform.getAcademicYearID();
		
		String StandardName = daoinf.retrieveStandardName(loginform.getUserID(), AcademicYearID);
    	
    	int StandardID = daoinf.retrieveStandardID(loginform.getUserID(), AcademicYearID);
    	
		String DivisionName = daoinf.retrieveDivisionName(loginform.getUserID(), AcademicYearID);
    	
    	int DivisionID = daoinf.retrieveDivisionID(loginform.getUserID(), AcademicYearID);
    	
    	HashMap<String, Integer> ExaminationList = (HashMap<String, Integer>) request.getAttribute("ExaminationList");
    	
    	List<String> SubjectEnrichmentMarksObtainedValue = (List<String>) request.getAttribute("SubjectEnrichmentMarksObtainedValue");
    	
    	List<String> NotebookMarksObtainedValue = (List<String>)  request.getAttribute("NotebookMarksObtainedValue");
    	
    	List<String> PortfolioLMarksObtainedValue = (List<String>)  request.getAttribute("PortfolioLMarksObtainedValue");
    	
    	List<String> MultipleAssessmentMarksObtainedValue = (List<String>)  request.getAttribute("MultipleAssessmentMarksObtainedValue");
    	
    	List<ConfigurationForm> studentsCustomReportList = (List<ConfigurationForm>)  request.getAttribute("studentsCustomReportList");
   
		int GradeBased = (Integer)(request.getAttribute("GradeBased"));
		
		String loadStudentSearch = (String) request.getAttribute("StudentsFound");
	
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
		
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
		
		String standardName = (String) request.getAttribute("standardName");
		
		if(standardName == null || standardName == ""){
			standardName = "";
		}
		
		int ayClassID = (Integer) request.getAttribute("ayClassID");
		
		String subjectTypeCheck = (String) request.getAttribute("subjectTypeCheck");
		if(subjectTypeCheck == null || subjectTypeCheck == ""){
			subjectTypeCheck = "notFound";
		}
		
    %>
    
  <title><% if(loginform.getMedium().equals("mr")){ %>माझा वर्ग - SkoolUp <% }else{ %>My Class - SkoolUp<% } %></title>
  
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

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
</script>

  <script type="text/javascript">
  
  $(document).ready(function(){
		$('#myExamLiID').addClass("active");
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
   
      function checkStandard(term, subject, subID){
    	
    	  if(term == "-1"){
				alert("Please select Term.");
				return false;
			
    	  }else if(subject == "-1"){
  				alert("Please select Subject.");
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
  		    	
  		    	$("#validate-basic").attr("action","LoadMyClassStudent");
  				$("#validate-basic").submit();
  			
  			 	$("#"+subID).attr("disabled", "disabled");
  					
  			 	return true;
  			}
  		}
      
      
    </script>
	 
<script type="text/javascript">

	function submitReportForm1(){
			
		$("#validate-basic").attr("action","StudentsCustomReportExcel");
		$("#validate-basic").submit();
		
		$("#ExcelButtonID").attr("disabled", "disabled");
	}

var xmlhttp;
if (window.XMLHttpRequest) {
	xmlhttp = new XMLHttpRequest();
} else {
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

function retrieveSubject(termID, standardValue){

	if(termID == "-1"){
		alert("No term is selected. Please select term.");
		
	}else if(standardValue == "-1"){
		alert("No standard is selected. Please select standard.");
		
		var array_element1 = "<select name='' id='editSubDivID' class='form-control'"+
		"> <option value='-1'>Select Subject</option></select>";
		
		document.getElementById("editSubjectDivID").innerHTML = array_element1;
		
	}else{
		
		var x = $("#SID option:selected").text();
		$("#newSID").val(x);
		retrieveSubject1(termID, standardValue);
	}

}

	function retrieveSubject1(termID,standardValue){
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var array_element = "<select name='subjectID' id='editSubDivID' class='form-control'"+
				"> <option value='0'>Select Subject</option>";
				
				var check = 0;
				/* For division */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

					array_element += "<option value='"+array.Release[i].subjectID+"'>"+array.Release[i].subjectList+"</option>";
				}

				array_element += " </select>";
				
				if(check == 0){
					
					alert("No Subject found");
					
					var array_element = "<select name='' id='editSubDivID' class='form-control'"+
					"> <option value='0'>Select Subject</option></select>";
					Scaled
					document.getElementById("editSubjectDivID").innerHTML = array_element;
					
				}else{
				
					
					document.getElementById("editSubjectDivID").innerHTML = array_element;	
					
				}
			}
		};
		xmlhttp.open("GET", "RetrieveSubjectListByUserIDandStandardID?check="
				+ standardValue, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}

</script>

</head>

<body>

<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="google_translate_element" style="display:none"></div>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>माझा वर्ग <% }else{ %>My Class <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>माझा वर्ग <% }else{ %>My Class <% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                <% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्यांची माहिती<% }else{ %> Student Details <% } %>
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
		    
		   <form id="validate-basic" action="LoadMyClassStudent" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
				
              <div class="row">
               
                <input type="hidden" name="gradeBased" value="<%=GradeBased%>">
 				
 		<% if(loginform.getMedium().equals("mr")){ %> 

			<% if(classTeacherCheck.equals("Yes")){ %>
                <div class="col-md-2">
                	<s:select list=" #{'Term I':'टर्म १','Term II':'टर्म २'}" class="form-control" headerKey="-1" id="termID" headerValue="टर्म निवडा"  name="term" ></s:select>
				</div>
				
					<div class="col-md-2">
						<s:hidden name="standardDivision" id="newSID"></s:hidden>
						<s:select list="StandardListByTeacher" onchange="retrieveSubject(termID.value, this.value);" class="form-control" headerKey="-1" id="SID" headerValue="इयत्ता निवडा" name="standardDivName" ></s:select>
					</div>
					
					<div class="col-md-2" id="editSubjectDivID">
		            	<s:select list="subjectNameList" class="form-control" headerKey="-1" id="editSubDivID"  headerValue="विषय निवडा" name="subjectID" ></s:select>
		            </div>
		            
		            <div class="col-md-2">
						<button type="submit" class="btn btn-warning" id="submitButtonID" onclick="return checkStandard(termID.value, editSubDivID.value, 'submitButtonID')">विद्यार्थी पहा</button>
					</div>
			<% }else if(classTeacherCheck.equals("No")){ %> 
				
				<div class="col-md-2">
                		
				  	<s:select list=" #{'Term I':'टर्म १','Term II':'टर्म २'}" class="form-control" headerKey="-1" id="termID" headerValue="टर्म निवडा"  name="term" ></s:select>
				</div>
				
				<div class="col-md-2">
						<s:hidden name="standardDivision" id="newSID"></s:hidden>
						<s:select list="StandardListByTeacher" onchange="retrieveSubject(termID.value, this.value);" class="form-control" headerKey="-1" id="SID" headerValue="इयत्ता निवडा" name="standardDivName" ></s:select>
				</div>
				
					<div class="col-md-2" id="editSubjectDivID">
		            	
						<s:select list="subjectNameList" class="form-control" headerKey="0" id="editSubDivID"  headerValue="विषय निवडा" name="subjectID" ></s:select>
		            </div>
		            
		            <div class="col-md-2">
						<button type="submit" class="btn btn-warning" onclick="return checkStandard(termID.value, editSubDivID.value)">विद्यार्थी पहा</button>
					</div>
			<% }%>	
					
			<% if(loadStudentSearch.equals("Found")){ %>
				
					<div class="col-md-2">
						<button class="btn btn-success" type="button" id="ExcelButtonID" onclick="submitReportForm1();">एक्सेल रिपोर्ट</button>
					</div>
					
			<%}else if(loadStudentSearch.equals("NotFound")){ %>
		    			
		    		<div class="col-md-2">
						<button class="btn btn-success" type="button" id="ExcelButtonID" onclick="submitReportForm1();">एक्सेल रिपोर्ट</button>
					</div>
			<%}%>
			
		<% }else{ %> 
				
				<% if(classTeacherCheck.equals("Yes")){ %>
				
                <div class="col-md-2">
                	<s:select list=" #{'Term I':'Term I','Term II':'Term II'}" class="form-control" headerKey="-1" id="termID" headerValue="Select Term"  name="term" ></s:select>
				</div>
				
					<div class="col-md-2">
						<s:hidden name="standardDivision" id="newSID"></s:hidden>
						<s:select list="StandardListByTeacher" onchange="retrieveSubject(termID.value, this.value);" class="form-control" headerKey="-1" id="SID" headerValue="Select Standard" name="standardDivName" ></s:select>
					</div>
					
					<div class="col-md-2" id="editSubjectDivID">
		            
						<s:select list="subjectNameList" class="form-control" headerKey="-1" id="editSubDivID"  headerValue="Select Subject" name="subjectID" ></s:select>
		            </div>
		            
		            <div class="col-md-2">
						<button type="submit" class="btn btn-warning" id="submitButtonID" onclick="return checkStandard(termID.value, editSubDivID.value, 'submitButtonID')">Load Students</button>
					</div>
				<% }else if(classTeacherCheck.equals("No")){ %> 
				
				<div class="col-md-2">
                		
				  	<s:select list=" #{'Term I':'Term I','Term II':'Term II'}" class="form-control" headerKey="-1" id="termID" headerValue="Select Term"  name="term" ></s:select>
				</div>
				
				<div class="col-md-2">
						<s:hidden name="standardDivision" id="newSID"></s:hidden>
						<s:select list="StandardListByTeacher" onchange="retrieveSubject(termID.value, this.value);" class="form-control" headerKey="-1" id="SID" headerValue="Select Standard" name="standardDivName" ></s:select>
				</div>
				
					<div class="col-md-2" id="editSubjectDivID">
		            	
						<s:select list="subjectNameList" class="form-control" headerKey="0" id="editSubDivID"  headerValue="Select Subject" name="subjectID" ></s:select>
		            </div>
		            
		            <div class="col-md-2">
						<button type="submit" class="btn btn-warning" onclick="return checkStandard(termID.value, editSubDivID.value)">Load Students</button>
					</div>
				<% }%>	
					
				<% if(loadStudentSearch.equals("Found")){ %>
				
					<div class="col-md-2">
						<button class="btn btn-success" type="button" id="ExcelButtonID" onclick="submitReportForm1();">Excel Report</button>
					</div>
					
				<%}else if(loadStudentSearch.equals("NotFound")){ %>
		    			
		    		<div class="col-md-2">
						<button class="btn btn-success" type="button" id="ExcelButtonID" onclick="submitReportForm1();">Excel Report</button>
					</div>
				<%}%>
			<% } %>
 				
			  </div>
			</form>
			
			<hr>
			
			<div class="tab-pane fade in active">
			
				<%
					if(loadStudentSearch.equals("Found")){
				%>
				
				<!-- <div class="row">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12" align="right">
						<button class="btn btn-success" type="button" onclick="submitReportForm();">Download Report</button>
					</div>
				</div> -->
			      <div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;overflow: auto;">
		          <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                         <% if(GradeBased == 0){ %>
	                         
	                         	<th style="text-align: center;"><% if(loginform.getMedium().equals("mr")){ %> हजेरी क्रमांक<% }else{ %> Roll No. <% } %></th>
	                         
	                          	<th style="text-align: center;"><% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्याचे नाव<% }else{ %> Student Name <% } %></th>
	                      		
	                      		<%
	                      		for (Entry<String, Integer> SubjectEnrichmententry : ExaminationList.entrySet()) {
	        						if (SubjectEnrichmententry.getKey().startsWith("Subject Enrichment")) { %>
	                      			
	                      			<th style="width:5%;"><%= SubjectEnrichmententry.getKey() %>(5)</th>
	                         	
	                         		<% }
	        					} %>
	        					
	        					<% for (Entry<String, Integer> Notebookentry : ExaminationList.entrySet()) {
	        						if (Notebookentry.getKey().startsWith("Notebook")) { %>
	                      			
	                      			<th style="width:5%;"><%= Notebookentry.getKey() %>(10)</th>
	                         	
	                         		<% }
	        					} %>
	        					
			                  	<th style="width:8%;">SEA+NB<br>(5)</th>
					                          
					            <th style="width:8%;">Periodic Test Marks Obtained(20)</th>
					            
					            <th style="width:8%;">Periodic Test Marks Scaled(5)</th>
					            
					            <th style="width:8%;">Term End Marks Obtained(40)</th>
					            
					            <th style="width:8%;">Term End Marks Scaled(40)</th>
					             
					            <th style="width:8%;">Total Marks Scaled<br>(50)</th>
					            
					            <th style="width:8%;"><% if(loginform.getMedium().equals("mr")){ %> ग्रेड <% }else{ %> Grade <% } %></th>
			               		
			               		<% }else{ %>
			               		
			               		<th style="text-align: left;"><% if(loginform.getMedium().equals("mr")){ %> हजेरी क्रमांक<% }else{ %> Roll No. <% } %></th>
	                         
	                          	<th style="text-align: left;"><% if(loginform.getMedium().equals("mr")){ %> Student नाव<% }else{ %> Student Name <% } %></th>
			               		
			               		<th style="text-align: center;width:15%;"><% if(loginform.getMedium().equals("mr")){ %> ग्रेड <% }else{ %> Grade <% } %></th>
			               		<% } %>
			               
			            </tr>
	                      </thead>
	                      <tbody>
	                     
	                      <%-- <s:iterator value="studentsCustomReportList" > --%>
	                     <% 
	                      	int k=0;
	                      	for(ConfigurationForm form:studentsCustomReportList){ %>
		                   
		                   	<tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		
				       		<% if(GradeBased == 0){ %>
				       		
				       			<td style="text-align: left;width: 5%;">
					       			<%= form.getRollNumber() %>
					       		</td>
				       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getStudentName() %>
					       		</td>
					       		
					       		<% 
					       		
					       		if(SubjectEnrichmentMarksObtainedValue.get(k).contains(",")){
									String[] SubjectEnrichmentMarks = SubjectEnrichmentMarksObtainedValue.get(k).split(",");
					       			
					       			for(int i=0;i<SubjectEnrichmentMarks.length;i++){
					       				
					       				if(SubjectEnrichmentMarks[i].contains("$")){
					       					
					       					String[] SubjectEnrichmentMarksValue = SubjectEnrichmentMarks[i].split("\\$");
					       				
						       				if(SubjectEnrichmentMarksValue[2].equals("1")){  %>
		                      			
		                      				<td style="text-align: left;width: 85%;">ex</td>
		                         	
		                         		 <% }else{ %>
		                         			
		                         			<td style="text-align: left;width: 85%;">
						       					<%= Integer.parseInt(SubjectEnrichmentMarksValue[1]) %>
						       				</td>
		                         			
		                         		 <% }
						       				
					       			  }else{ %>
					       				  
					       				<td style="text-align: left;width: 85%;">0</td>
	                         	
	                         		<% }
					       			
					       			} 
					       			
					       		}else{
					       			
					       			if(SubjectEnrichmentMarksObtainedValue.get(k).contains("$")){
					       				
					       				String[] SubjectEnrichmentMarksValue = SubjectEnrichmentMarksObtainedValue.get(k).split("\\$");
					       				
					       				if(SubjectEnrichmentMarksValue[2].equals("1")){  %>
		                      			
	                      				<td style="text-align: left;width: 85%;">ex</td>
	                         	
	                         		 <% }else{ %>
	                         			
	                         			<td style="text-align: left;width: 85%;">
					       					<%= Integer.parseInt(SubjectEnrichmentMarksValue[1]) %>
					       				</td>
	                         			
	                         		 <% } 
					       				
					       			}else{ %>
	                         			<td style="text-align: left;width: 85%;">0</td>
	                         			
	                         	<% }
					       				
					       	}%>
					       			
	        					
	        					<%
	        					if(NotebookMarksObtainedValue.get(k).contains(",")){
									String[] NotebookMarksMarks = NotebookMarksObtainedValue.get(k).split(",");
					       			
					       			for(int i=0;i<NotebookMarksMarks.length;i++){
					       				
					       				String[] NotebookMarksMarksValue = NotebookMarksMarks[i].split("\\$");
					       				
					       				if(NotebookMarksMarksValue[2].equals("1")){  %>
	                      			
	                      				<td style="text-align: left;width: 85%;">ex</td>
	                         	
	                         		 <% }else{ %>
	                         			
	                         			<td style="text-align: left;width: 85%;">
					       					<%= Integer.parseInt(NotebookMarksMarksValue[1]) %>
					       				</td>
	                         			
	                         		 <% } 
					       			} 
	        					}else{
	        						
	        						if(NotebookMarksObtainedValue.get(k).contains("$")){
	        							
	        							String[] NotebookMarksMarksValue = NotebookMarksObtainedValue.get(k).split("\\$");
					       				
					       				if(NotebookMarksMarksValue[2].equals("1")){  %>
	                      			
	                      				<td style="text-align: left;width: 85%;">ex</td>
	                         	
	                         		 <% }else{ %>
	                         			
	                         			<td style="text-align: left;width: 85%;">
					       					<%= Integer.parseInt(NotebookMarksMarksValue[1]) %>
					       				</td>
	                         			
	                         		 <% } 
	        						}else{ %>
	        							<td style="text-align: left;width: 85%;">0</td>
	        						<% }
	        						
	        					} %>
					       			
	        					<td style="text-align: left;width: 85%;">
					       			<%= form.getNotebookMarks() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getUnitTestMarksObtained() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getUnitTestMarks() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getTermEndMarksObtained() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getTermEndMarks()%>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getFinalTotalMarks() %>
					       		</td>	
					       		
					       		<td style="text-align: center;">
					       			<%= form.getGrade() %>
					       		</td>
					       		
					       <% }else{ %>
					       		
					       		<td style="text-align: left;width: 20%;">
					       			<%= form.getRollNumber() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 55%;">
					       			<%= form.getStudentName() %>
					       		</td>
					       		
					       		<td style="text-align: center;width: 25%;">
					       			<%= form.getGrade() %>
					       		</td>
					       		
					       	<% } %>
				       		</tr> 
		                  <% 
		                  
		                 k++; 
		                  
	                     }%>
		                   
	                      </tbody>
	                    </table>
		                
			        </div>
			     
				 <%
		    		}else if(loadStudentSearch.equals("NotFound")){ 
		    			//System.out.println("NotFound");
				 %>
				 
				 <!-- <div class="row">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12" align="right">
						<button class="btn btn-success" type="button" onclick="submitReportForm();">Download Report</button>
					</div> -->
				</div>
				 <div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;overflow: auto; ">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <% if(GradeBased == 0){ %>
	                          	<th style="text-align: center;"><% if(loginform.getMedium().equals("mr")){ %> हजेरी क्रमांक<% }else{ %> Roll No. <% } %></th>
	                         
	                          	<th style="text-align: center;"><% if(loginform.getMedium().equals("mr")){ %> Student नाव<% }else{ %> Student Name <% } %></th>
	                      
	                      	<%
	                      		for (Entry<String, Integer> SubjectEnrichmententry : ExaminationList.entrySet()) {
	        						if (SubjectEnrichmententry.getKey().startsWith("Subject Enrichment")) { %>
	                      			
	                      			<th style="width:5%;"><%= SubjectEnrichmententry.getKey() %>(5)</th>
	                         	
	                         		<% }
	        					} %>
	        					
	        					<th style="width:8%;">Subject Enrichment Marks Scaled(5)</th>
	        						
	        					<% for (Entry<String, Integer> Notebookentry : ExaminationList.entrySet()) {
	        						if (Notebookentry.getKey().startsWith("Notebook")) { %>
	                      			
	                      			<th style="width:5%;"><%= Notebookentry.getKey() %>(5)</th>
	                         	
	                         		<% }
	        					} %>
	        					
	        					<th style="width:5%;">Notebook Marks Scaled(5)</th>
		                      	
		                      	<% 
		                      	
		                      		for (Entry<String, Integer> Portfolioentry : ExaminationList.entrySet()) {
		        						if (Portfolioentry.getKey().startsWith("Portfolio")) { %>
		                      			
		                      			<th style="width:5%;"><%= Portfolioentry.getKey() %>(5)</th>
		                         	
		                         		<% }
		        					} %>
		        					
		        					<th style="width:8%;">Portfolio Marks Scaled(5)</th>
		        				<% 
	        					
	        					for (Entry<String, Integer> MultipleAssessmententry : ExaminationList.entrySet()) {
	        						if (MultipleAssessmententry.getKey().startsWith("Multiple Assessment")) { %>
	                      			
	                      			<th style="width:5%;"><%= MultipleAssessmententry.getKey() %>(5)</th>
	                         	
	                         		<% }
	        					} %>
	        					
	        					<th style="width:5%;">MultipleAssessment Marks Scaled(5)</th>
		                      	
		                      	<th style="width:8%;">Periodic Test Marks Obtained(40)</th>
					            
					            <th style="width:8%;">Periodic Test Marks Scaled(10)</th>
					            
					            <th style="width:8%;">Term End Marks Obtained(30/80)</th>
					            
					            <th style="width:8%;">Term End Marks Scaled(30/80)</th>
					             
					            <th style="width:8%;">Total Marks Scaled(50/100)</th>
			               
			            		<th style="width:8%;"><% if(loginform.getMedium().equals("mr")){ %> ग्रेड <% }else{ %> Grade <% } %></th>
			            		<%}else{ %>
			            		
			            		<th style="text-align: left;"><% if(loginform.getMedium().equals("mr")){ %> हजेरी क्रमांक<% }else{ %> Roll No. <% } %></th>
	                         
	                          	<th style="text-align: left;"><% if(loginform.getMedium().equals("mr")){ %> Student नाव<% }else{ %> Student Name <% } %></th>
			            		
			            		<th style="text-align: center;"><% if(loginform.getMedium().equals("mr")){ %> ग्रेड <% }else{ %> Grade <% } %></th>
			            		<%} %>
					    	</tr>
	                      </thead>
	                      
	                      <tbody>
	                      <% 
	                      	int j=0;
	                      
	                      	for(ConfigurationForm form:studentsCustomReportList){ %>
		                   
		                   	<tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		<% if(GradeBased == 0){ %>
				       		
				       			<td style="text-align: left;width: 5%;">
					       			<%= form.getRollNumber() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getStudentName() %>
					       		</td>
					       		
					       		<% 
					       		
					       		if(SubjectEnrichmentMarksObtainedValue.get(j).contains(",")){
					       			
					       			String[] SubjectEnrichmentMarks = SubjectEnrichmentMarksObtainedValue.get(j).split(",");
					       			
					       			for(int i=0;i<SubjectEnrichmentMarks.length;i++){
					       				
					       				if(SubjectEnrichmentMarks[i].contains("$")){
					       					
					       					String[] SubjectEnrichmentMarksValue = SubjectEnrichmentMarks[i].split("\\$");
						       				
						       				if(SubjectEnrichmentMarksValue[2].equals("1")){  %>
		                      			
		                      				<td style="text-align: left;width: 85%;">ex</td>
		                         	
		                         		 <% }else{ %>
		                         			
		                         			<td style="text-align: left;width: 85%;">
						       					<%= Integer.parseInt(SubjectEnrichmentMarksValue[1]) %>
						       				</td>
		                         			
		                         		 <% } 
					       				}else{ %>
						       				  
						       				<td style="text-align: left;width: 85%;">0</td>
		                         	
		                         		<% }
					       				
					       			} 
					       		}else{
					       			
					       			if(SubjectEnrichmentMarksObtainedValue.get(j).contains("$")){
					       				
					       				String[] SubjectEnrichmentMarksValue = SubjectEnrichmentMarksObtainedValue.get(j).split("\\$");
					       				
					       				if(SubjectEnrichmentMarksValue[2].equals("1")){  %>
	                      			
	                      				<td style="text-align: left;width: 85%;">ex</td>
	                         	
	                         		 <% }else{ %>
	                         			
	                         			<td style="text-align: left;width: 85%;">
					       					<%= Integer.parseInt(SubjectEnrichmentMarksValue[1]) %>
					       				</td>
	                         			
	                         		 <% } 
					       			}else{ %>
					       			
                         				<td style="text-align: left;width: 85%;">0</td>
                         			
                         			<% }
					       		}%>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getSubjectEnrichmentMarks() %>
					       		</td>	
	        						
	        					<% 
	        					if(NotebookMarksObtainedValue.get(j).contains(",")){
									String[] NotebookMarksMarks = NotebookMarksObtainedValue.get(j).split(",");
					       			
					       			for(int i=0;i<NotebookMarksMarks.length;i++){
					       				
					       				String[] NotebookMarksMarksValue = NotebookMarksMarks[i].split("\\$");
					       				
					       				if(NotebookMarksMarksValue[2].equals("1")){  %>
	                      			
	                      				<td style="text-align: left;width: 85%;">ex</td>
	                         	
	                         		 <% }else{ %>
	                         			
	                         			<td style="text-align: left;width: 85%;">
					       					<%= Integer.parseInt(NotebookMarksMarksValue[1]) %>
					       				</td>
	                         			
	                         		 <% } 
					       			} 
	        					}else{
	        						
	        						if(NotebookMarksObtainedValue.get(j).contains("$")){
										
	        							String[] NotebookMarksMarksValue = NotebookMarksObtainedValue.get(j).split("\\$");
					       				
					       				if(NotebookMarksMarksValue[2].equals("1")){  %>
	                      			
	                      				<td style="text-align: left;width: 85%;">ex</td>
	                         	
	                         		 <% }else{ %>
	                         			
	                         			<td style="text-align: left;width: 85%;">
					       					<%= Integer.parseInt(NotebookMarksMarksValue[1]) %>
					       				</td>
	                         			
	                         		 <% } 
	        						}else{ %>
	        							<td style="text-align: left;width: 85%;">0</td>
	        						<% }
	        					} %>
	        						
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getNotebookMarks() %>
					       		</td>
					       		
					       		<%
						       		if(PortfolioLMarksObtainedValue.get(j).contains(",")){
						       			
						       			String[] PortfolioMarks = PortfolioLMarksObtainedValue.get(j).split(",");
						       			
						       			for(int i=0;i<PortfolioMarks.length;i++){
						       				
						       				if(PortfolioMarks[i].contains("$")){
						       					
						       					String[] PortfolioMarksMarksValue = PortfolioMarks[i].split("\\$");
							       				
							       				if(PortfolioMarksMarksValue[2].equals("1")){  %>
			                      			
			                      				<td style="text-align: left;width: 85%;">ex</td>
			                         	
			                         		 <% }else{ %>
			                         			
			                         			<td style="text-align: left;width: 85%;">
							       					<%= Integer.parseInt(PortfolioMarksMarksValue[1]) %>
							       				</td>
			                         			
			                         		 <% } 
						       				}else{ %>
						       				  
						       				<td style="text-align: left;width: 85%;">0</td>
		                         	
		                         		<% }
					       			
						       			} 
						       		}else{
						       			
						       			if(PortfolioLMarksObtainedValue.get(j).contains("$")){
						       				
						       				String[] PortfolioMarksMarksValue = PortfolioLMarksObtainedValue.get(j).split("\\$");
						       				
						       				if(PortfolioMarksMarksValue[2].equals("1")){  %>
		                      			
		                      				<td style="text-align: left;width: 85%;">ex</td>
		                         	
		                         		 <% }else{ %>
		                         			
		                         			<td style="text-align: left;width: 85%;">
						       					<%= Integer.parseInt(PortfolioMarksMarksValue[1]) %>
						       				</td>
		                         			
		                         		 <% }
						       			}
						       		}%>
						       		
						       		<td style="text-align: left;width: 85%;">
						       			<%= form.getPortfolioFinalMarks() %>
						       		</td>
						       		
					       		<% 
					       		
					       		if(MultipleAssessmentMarksObtainedValue.get(j).contains(",")){
					       			
					       			String[] MultipleAssessmentMarks = MultipleAssessmentMarksObtainedValue.get(j).split(",");
					       			
					       			for(int i=0;i<MultipleAssessmentMarks.length;i++){
					       				
					       				if(MultipleAssessmentMarks[i].contains("$")){
					       					
					       					String[] MultipleAssessmentMarksMarksValue = MultipleAssessmentMarks[i].split("\\$");
						       				
						       				if(MultipleAssessmentMarksMarksValue[2].equals("1")){  %>
		                      			
		                      				<td style="text-align: left;width: 85%;">ex</td>
		                         	
		                         		 <% }else{ %>
		                         			
		                         			<td style="text-align: left;width: 85%;">
						       					<%= Integer.parseInt(MultipleAssessmentMarksMarksValue[1]) %>
						       				</td>
		                         			
		                         		 <% } 
					       				}else{ %>
					       				  
					       				<td style="text-align: left;width: 85%;">0</td>
	                         	
	                         		<% }
				       			
					       			} 
					       		}else{
					       			
					       			if(MultipleAssessmentMarksObtainedValue.get(j).contains("$")){
					       				
					       				String[] MultipleAssessmentMarksMarksValue = MultipleAssessmentMarksObtainedValue.get(j).split("\\$");
					       				
					       				if(MultipleAssessmentMarksMarksValue[2].equals("1")){  %>
	                      			
	                      			<td style="text-align: left;width: 85%;">ex</td> 
	                         	
	                         		 <% }else{ %>
	                         			
	                         		<td style="text-align: left;width: 85%;">
					       					<%= Integer.parseInt(MultipleAssessmentMarksMarksValue[1]) %>
					       				</td> 
	                         			
	                         		 <% } 
					       			}
					       		}%>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getMultipleAssessmentFinalMarks() %>
					       		</td>
					       		
					      		<td style="text-align: left;width: 85%;">
					       			<%= form.getUnitTestMarksObtained() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getUnitTestMarks() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getTermEndMarksObtained() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getTermEndMarks()%>
					       		</td>
					       		
					       		<td style="text-align: left;width: 85%;">
					       			<%= form.getFinalTotalMarks() %>
					       		</td>	
					       		
					       		<td style="text-align: center;">
					       			<%= form.getGrade() %>
					       		</td>
					       		
					       		<%}else{ %>
					       		
					       		<td style="text-align: left;width: 20%;">
					       			<%= form.getRollNumber() %>
					       		</td>
					       		
					       		<td style="text-align: left;width: 55%;">
					       			<%= form.getStudentName() %>
					       		</td>
					       		
					       		<td style="text-align: center;width: 25%;">
					       			<%= form.getGrade() %>
					       		</td>
					       		
					       		<%} %>
				       		</tr> 
		                 <%
		               
		                 j++; 
	                   
	                   } %>
	                      </tbody>
	                    </table>
		                
			        </div>
			        
               	<% } %>
               	</div>
				
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
  <script src="./js/plugins/datepicker/bootstrap-datepicker.js"></script>
  
</body>
</html>
