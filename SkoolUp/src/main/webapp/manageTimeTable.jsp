<%@page import="com.kovidRMS.form.LoginForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
     <%@page import="java.util.HashMap"%>
     <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="java.util.List"%>
     <%@page import="com.kovidRMS.form.ConfigurationForm"%>
     <%@page import="com.kovidRMS.daoImpl.ConfigurationDAOImpl"%>
<%@page import="com.kovidRMS.daoInf.ConfigurationDAOInf"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%
    	LoginForm loginform = (LoginForm) session.getAttribute("USER");
     	List<LoginForm> TimeTableList = (List<LoginForm>) request.getAttribute("TimeTableList");
   
		LoginDAOInf daoinf = new LoginDAOImpl();
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
    	
		HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	
    	StuduntDAOInf daoinf1 = new StudentDAOImpl();
    
    	int AcademicYearID = loginform.getAcademicYearID();
		
		int check = daoInf.verifyTermIEndDate(AcademicYearID);
		
		String Term = "";
		
		if(check == 1){
			Term = "Term I";
			
		}else{
			
			Term = "Term II";
		}
		
		HashMap<Integer, String> ExamList = daoInf.getExaminationListByTerm(AcademicYearID, Term);
    	
    %>
    
  <title><% if(loginform.getMedium().equals("mr")){ %>वेळापत्रक व्यवस्थापन - SkoolUp <% }else{ %>Manage Time Table - SkoolUp<% } %></title>
  
<!-- Favicon -->
  <link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  <!-- <link rel="stylesheet" href="./css/bootstrap.min.css"> -->
  
  <link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" />
  <link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />
  <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">

  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
  

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
  </script>
  <script type="text/javascript">
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
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
	
	function windowOpen1(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="RenderManageTimeTable";
     }
	
	function submitForm(subID){
	  	
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
		
		$("#loadStudentFOrm").attr("action","AddTimeTable");
	  	$("#loadStudentFOrm").submit();
	    
	  	$("#"+subID).attr("disabled", "disabled");
	  	
    	return true;
	}
	
	function logoPicShow(){
		$('#logoID1').hide();
		$('#logoID').show();
		$('#logoClickID').click();
	}
    </script>
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableUser(url){
			if(confirm("Disabled users cannot access application. Are you sure you want to disable this user?")){
				document.location = url;
			}
    	}
    
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete user?")) {
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
	
	function retrieveSubject(examID, dateID, standardID){
	
		if(examID == "-1"){
			alert("No exam is selected. Please select exam.");
			
		}else if(dateID == ""){
			alert("No Date is selected. Please select Date.");
			
		}else if(standardID == "-1"){
			alert("No standard is selected. Please select standard.");
			
		}else{
			
			retrieveSubjectByStandardID(examID, dateID, standardID);
		}
	
	}
		/* For retrieving Subjects list with there standardID */
		
		function retrieveSubjectByStandardID(examID, dateID, standardID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
						var array_element = "<select name='' id='editSubDivID' class='form-control'"+
						"> <option value='-1'>Select Subject</option>";
						
						var subjectList = ""; 
						var subjectID = 0;
						
						for ( var i = 0; i < array.Release.length; i++) {
							
							subjectList = array.Release[i].subjectList;
							subjectID = array.Release[i].subjectID;
							array_element += "<option value='"+subjectList.trim()+"'>"+subjectList.trim()+"</option>";
						}
						
					document.getElementById("editSubjectDivID").innerHTML = array_element;	
				}
			};
			xmlhttp.open("GET", "RetrieveScholasticSubjectListForStandard?standardID="
					+ standardID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>
   
   <script type="text/javascript">
   var TimeTableCounter = 1;
	
	function addTimeTableRow(editExam, editDate, editStandard, editSubject){
		
		if(editExam == "-1"){
			alert("Please select Exam.");
		}else if(editDate == ""){
			alert("Please select Date.");
		}else if(editStandard == "-1"){
			alert("Please select Standard.");
		}else if(editSubject == "-1"){
			alert("Please select Subject.");
		}else{
			
			retrieveExamNameByExamID(editExam, editDate, editStandard, editSubject);
			//retrieveStandardNameByStandardID(editExam, editDate, editStandard, editSubject);
		}
	}
	
	function addTimeTableRow1(editExam, editDate, editStandard, editSubject, examName, standardName){
		
		var trID = "newExamTRID"+TimeTableCounter;
		
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
		      + "<td style='text-align:center;'>"+examName+"<input type='hidden' name='newExam' value='"+editExam+"'></td>"
			  + "<td style='text-align:center;'>"+editDate+"<input type='hidden' name='newDate' value='"+editDate+"'></td>"
			  + "<td style='text-align:center;'>"+standardName+"<input type='hidden' name='newstandardID' value='"+editStandard+"'></td>"
			  + "<td style='text-align:center;'>"+editSubject+"<input type='hidden' name='newsubjectID' value='"+editSubject+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeTimeTableTR(\"" + trID + "\");'/></td>"
			  + "</tr>";
		
		$(trTag).insertAfter($('#examTRID'));
		TimeTableCounter++;
		   
		$("#examID").val("-1");
		$("#examDateID").val("");
		$("#standardID").val("-1");
		$("#editSubDivID").val("-1");
		
	}
	
	function removeTimeTableTR(trID){
		if(confirm("Are you sure you want to delete this row?")){
			$("#"+trID+"").remove();
		}
	}
   
	/*For deleting the row from database with academicYearID*/
	
	function deleteRow1(deleteID, TRID){
   	if(confirm("Are you sure you want to remove this row?")){
   		deleteRow2(deleteID, TRID);
   	}
   }
  
   </script>

	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function deleteRow2(deleteID, TRID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var check = 0;
	
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						
					}
					
					if(check == 1){
						
						$("#"+TRID+"").remove();
						
					}else{
						alert("Failed to delete row. Please check server logs for more details.")
					}
					
				}
			};
			xmlhttp.open("GET", "DeleteAYClassRow?deleteID="
					+ deleteID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	
   </script>
   
   
   <script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveExamNameByExamID(editExam, editDate, editStandard, editSubject) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var examName = "";
	
					for ( var i = 0; i < array.Release.length; i++) {

						examName = array.Release[i].examName;
						
					}
					
					retrieveStandardNameByStandardID(editExam, editDate, editStandard, editSubject, examName);
				}
			};
			xmlhttp.open("GET", "RetrieveExamNameByExamID?ExaminationID="
					+ editExam, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
		</script>
		
   <script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveStandardNameByStandardID(editExam, editDate, editStandard, editSubject, examName) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var standardName = "";
	
					for ( var i = 0; i < array.Release.length; i++) {

						standardName = array.Release[i].standard;
						
					}
					
					addTimeTableRow1(editExam, editDate, editStandard, editSubject, examName, standardName);
					
				}
			};
			xmlhttp.open("GET", "RetrieveStandardName?standardID="
					+ editStandard, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
		</script>
	
 
    <script type="text/javascript">
    
    var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	 function retrieveEditSubjectByStandard(divTDID, standardID){
		 if(standardID == "-1"){
			 var array_element = "<select name='editSubject' id='' class='form-control'"+
				"> <option value='-1'>Select Subject</option></select>";
			
			document.getElementById(divTDID).innerHTML = array_element;
				
		 }else{
			 retrieveEditSubjectByStandard1(divTDID, standardID);
		 }
	 }
    
    function retrieveEditSubjectByStandard1(divTDID, standardID) {
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "<select name='editSubject' class='form-control'"+
					"> <option value='-1'>Select Subject</option>";
					
					var subjectList = ""; 
					var subjectID = 0;
					
					for ( var i = 0; i < array.Release.length; i++) {
						
						subjectList = array.Release[i].subjectList;
						subjectID = array.Release[i].subjectID;
						array_element += "<option value='"+subjectList.trim()+"'>"+subjectList.trim()+"</option>";
					}
					
				document.getElementById(divTDID).innerHTML = array_element;	
			}
		};
		xmlhttp.open("GET", "RetrieveScholasticSubjectListForStandard?standardID="
				+ standardID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
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
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>वेळापत्रक व्यवस्थापन<% }else{ %>Manage Time Table <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>वेळापत्रक व्यवस्थापन<% }else{ %>Manage Time Table <% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

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
		              
	       	<!-- Add and Update Div -->
	    
		    <div class="col-md-12" >
		      <form id="loadStudentFOrm" action="AddTimeTable" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
				
				<% if(loginform.getMedium().equals("mr")){ %>
					<div class="form-group">
					<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
							<td>परीक्षा</td>
							<td>परीक्षेची तारीख</td>
				       		<td>इयत्ता</td>
				       		<td>विषय</td>
				       		<td>कृती</td>
				       	</tr>
				       	
				       	<tr id="examTRID"> 
				     		<td>
				       			<div class="form-group">
							  		
									<select id="examID" class="form-control" >
					                	<option value="-1">परीक्षा निवडा</option>
					                	<%  
						         			for(Integer ExamFormName : ExamList.keySet()){
						         		%>
						         		<option value="<%= ExamFormName%>"><%=ExamList.get(ExamFormName)%></option>
						         				
						         		<%
						         			}
						         		%>
				                	</select>
								</div>
				           </td>
				           <td style=" text-align: center;padding: 10px;">
						      <div class="input-group date ui-datepicker">
									<input type="text" id="examDateID" name="examDate" class="form-control" placeholder="परीक्षेची तारीख" data-required="true">
			                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                 	  </div> 
					       </td>
				           <td>
					         <div class="form-group">
				       			
								<select id="standardID" class="form-control"  onchange="retrieveSubject(examID.value, examDateID.value, this.value);" >
					                <option value="-1">इयत्ता निवडा</option>
					                <%  
						         		for(Integer StandardFormName : StandardList.keySet()){
						         	%>
						         	<option value="<%= StandardFormName%>"><%=StandardList.get(StandardFormName)%></option>
						         				
						         	<%
						         		}
						         	%>
				                	</select>
				                </div>
					       </td>
					       <td>
					       		<div class="form-group" id="editSubjectDivID">
		            				<select name="" id="editSubDivID" class="form-control">
			               				<option value="-1">विषय निवडा</option>
			               			</select>
		            			</div>
					     </td>
					       	
						<td> 
			       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="परीक्षा ऍड करा" title="परीक्षा ऍड करा" 
				       			onclick="addTimeTableRow(examID.value, examDateID.value, standardID.value, editSubDivID.value);"/>
						</td>
						
				       </tr>
				       
				       <% 
				       		for(LoginForm form:TimeTableList){
				       		//getting subject list based on standardID
				       			HashMap<String, String> subjectList = daoinf.getsubject(form.getStandardID());
				       %>
				       		<tr id="newTRID<%=form.getTimeTableID()%>">
				       			<td>
				       			<input type="hidden" name="TimeTableID1" value="<%=form.getTimeTableID()%>">
					       		<select id="editExamID<%=form.getTimeTableID()%>" name="editExamID" class="form-control" >
					       			<%
						         		
					       			for(Integer ExamFormName : ExamList.keySet()){
					       					if(ExamFormName == form.getExamID()){
					       			%>
					       				<option value="<%=ExamFormName %>" selected="selected"><%=ExamList.get(ExamFormName) %></option>
					       			<%
					       					}else{
					       			%>
					       				<option value="<%=ExamFormName %>"><%=ExamList.get(ExamFormName) %></option>
					       			<%
					       					}
					       				}
					       			%>
					       		</select>
			       				</td>
			       				<td>
			       					<div class="input-group date ui-datepicker">
										<input type="text" id = "editExamDateID<%=form.getTimeTableID()%>" name="editExamDate" class="form-control" value="<%=form.getExamDate()%>">
				                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			                 		</div>
			       				</td>
			       				
				       			<td>
				       			
					       		<select id="editStandardListID<%=form.getTimeTableID()%>" name="editStandardID" class="form-control" onchange="retrieveEditSubjectByStandard('divTDID<%=form.getTimeTableID()%>',this.value);">
					       			<option value="-1">इयत्ता निवडा</option>
					       			<%
						         		
					       				for(Integer StandardFormName:StandardList.keySet()){
					       					if(StandardFormName == form.getStandardID()){
					       			%>
					       				<option value="<%=StandardFormName %>" selected="selected"><%=StandardList.get(StandardFormName) %></option>
					       			<%
					       					}else{
					       			%>
					       				<option value="<%=StandardFormName %>"><%=StandardList.get(StandardFormName) %></option>
					       			<%
					       					}
					       				}
					       			%>
					       		</select>
			       				</td>
				       			<td id="divTDID<%=form.getTimeTableID()%>">
					       			<select id="" name="editSubject" class="form-control">
					       				<option value="-1">विषय निवडा</option>
					       			<%
						         		
					       				for(String SubjectName:subjectList.keySet()){
					       					if(SubjectName.equals(form.getSubject().trim())){
					       			%>
					       				<option value="<%=SubjectName %>" selected="selected"><%=subjectList.get(SubjectName) %></option>
					       			<%
					       					}else{
					       			%>
					       				<option value="<%=SubjectName %>"><%=subjectList.get(SubjectName) %></option>
					       			<%
					       					}
					       				}
					       			%>
					       			</select>
				       			</td>
				       			
				       			<td>
				       				<img src='images/delete.png' style='height:24px;cursor: pointer;'
									onclick="deleteRow1(<%=form.getTimeTableID()%>,'newTRID<%=form.getTimeTableID()%>');" 
									alt='डीलीट करा' title='डीलीट करा'/>
							   </td>
				       		
				       		
				       		</tr>		
			       		<% 
			       			} 
			       		%>  
					</table>
					
				</div>
                
                <div class="form-group">
		           <div class="col-md-12" align="center">
		              <button type="button" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
		              <button type="submit"  class="btn btn-success" id="submitID" onclick="submitForm('submitID');">सेव करा</button>
		            </div>
		        </div>
				
			<% }else{ %>
				<div class="form-group">
					<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
							<td>Exam</td>
							<td>Date</td>
				       		<td>Standard</td>
				       		<td>Subject</td>
				       		<td>Action</td>
				       	</tr>
				       	
				       	<tr id="examTRID"> 
				     		<td>
				       			<div class="form-group">
							  		
									<select id="examID" class="form-control" >
					                	<option value="-1">Select Exam</option>
					                	<%  
						         			for(Integer ExamFormName : ExamList.keySet()){
						         		%>
						         		<option value="<%= ExamFormName%>"><%=ExamList.get(ExamFormName)%></option>
						         				
						         		<%
						         			}
						         		%>
				                	</select>
								</div>
				           </td>
				           <td style=" text-align: center;padding: 10px;">
						      <div class="input-group date ui-datepicker">
									<input type="text" id="examDateID" name="examDate" class="form-control" placeholder="Exam Date" data-required="true">
			                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                 	  </div> 
					       </td>
				           <td>
					         <div class="form-group">
				       			
								<select id="standardID" class="form-control"  onchange="retrieveSubject(examID.value, examDateID.value, this.value);" >
					                <option value="-1">Select Standard</option>
					                <%  
						         		for(Integer StandardFormName : StandardList.keySet()){
						         	%>
						         	<option value="<%= StandardFormName%>"><%=StandardList.get(StandardFormName)%></option>
						         				
						         	<%
						         		}
						         	%>
				                	</select>
				                </div>
					       </td>
					       <td>
					       		<div class="form-group" id="editSubjectDivID">
		            				<select name="" id="editSubDivID" class="form-control">
			               				<option value="-1">Select Subject</option>
			               			</select>
		            			</div>
					     </td>
					       	
						<td> 
			       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="Add AcademicYear" title="Add AcademicYear" 
				       			onclick="addTimeTableRow(examID.value, examDateID.value, standardID.value, editSubDivID.value);"/>
						</td>
				       </tr>
				       
				       	<% 
				       		for(LoginForm form:TimeTableList){
				       		//getting subject list based on standardID
				       			HashMap<String, String> subjectList = daoinf.getsubject(form.getStandardID());
				       	%>
				       		<tr id="newTRID<%=form.getTimeTableID()%>">
				       			<td>
				       			<input type="hidden" name="TimeTableID1" value="<%=form.getTimeTableID()%>">
					       		<select id="editExamID<%=form.getTimeTableID()%>" name="editExamID" class="form-control" >
					       			<%
						         		
					       			for(Integer ExamFormName : ExamList.keySet()){
					       					if(ExamFormName == form.getExamID()){
					       			%>
					       				<option value="<%=ExamFormName %>" selected="selected"><%=ExamList.get(ExamFormName) %></option>
					       			<%
					       					}else{
					       			%>
					       				<option value="<%=ExamFormName %>"><%=ExamList.get(ExamFormName) %></option>
					       			<%
					       					}
					       				}
					       			%>
					       		</select>
			       				</td>
			       				<td>
			       					<div class="input-group date ui-datepicker">
										<input type="text" id = "editExamDateID<%=form.getTimeTableID()%>" name="editExamDate" class="form-control" value="<%=form.getExamDate()%>">
				                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			                 		</div>
			       				</td>
			       				
				       			<td>
				       			
					       		<select id="editStandardListID<%=form.getTimeTableID()%>" name="editStandardID" class="form-control" onchange="retrieveEditSubjectByStandard('divTDID<%=form.getTimeTableID()%>',this.value);">
					       			<option value="-1">Select Standard</option>
					       			<%
						         		
					       				for(Integer StandardFormName:StandardList.keySet()){
					       					if(StandardFormName == form.getStandardID()){
					       			%>
					       				<option value="<%=StandardFormName %>" selected="selected"><%=StandardList.get(StandardFormName) %></option>
					       			<%
					       					}else{
					       			%>
					       				<option value="<%=StandardFormName %>"><%=StandardList.get(StandardFormName) %></option>
					       			<%
					       					}
					       				}
					       			%>
					       		</select>
			       				</td>
				       			<td id="divTDID<%=form.getTimeTableID()%>">
					       			<select id="" name="editSubject" class="form-control">
					       				<option value="-1">Select Subject</option>
					       			<%
						         		
					       				for(String SubjectName:subjectList.keySet()){
					       					if(SubjectName.equals(form.getSubject().trim())){
					       			%>
					       				<option value="<%=SubjectName %>" selected="selected"><%=subjectList.get(SubjectName) %></option>
					       			<%
					       					}else{
					       			%>
					       				<option value="<%=SubjectName %>"><%=subjectList.get(SubjectName) %></option>
					       			<%
					       					}
					       				}
					       			%>
					       			</select>
				       			</td>
				       			
				       			<td>
				       				<img src='images/delete.png' style='height:24px;cursor: pointer;'
									onclick="deleteRow1(<%=form.getTimeTableID()%>,'newTRID<%=form.getTimeTableID()%>');" 
									alt='Delete row' title='Delete row'/>
							   </td>
				       		</tr>		
			       		<% 
			       			} 
			       		%>  
					</table>
				</div>
                
                <div class="form-group">
		           <div class="col-md-12" align="center">
		              <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
		              <button type="submit"  class="btn btn-success" id="submitID" onclick="submitForm('submitID');">Save</button>
		            </div>
		        </div>	
		        
		   <% } %>     
		   
              </form>
     		</div>
                
			</div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->

        </div> <!-- /.col -->

      </div> <!-- /.row -->
	 </div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

<%-- <script src="./js/libs/jquery-1.10.1.min.js"></script>  --%>
  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  <%-- <script src="./js/libs/bootstrap.min.js"></script> --%>

  <!--[if lt IE 9]>
  <script src="./js/libs/excanvas.compiled.js"></script>
  <![endif]-->
  
  <!-- Plugin JS -->
  <script src="./js/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="./js/plugins/datatables/DT_bootstrap.js"></script>
  <script src="./js/plugins/tableCheckable/jquery.tableCheckable.js"></script>
  <script src="./js/plugins/icheck/jquery.icheck.min.js"></script>
  <script src="./js/plugins/datepicker/bootstrap-datepicker.js"></script>
  <!-- App JS -->
  <script src="./js/target-admin.js"></script>
  
	<%-- <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script> --%>
    <script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>   
    <script type="text/javascript" src="js/bootstrap-multiselect.js"></script>

  
</body>
</html>
