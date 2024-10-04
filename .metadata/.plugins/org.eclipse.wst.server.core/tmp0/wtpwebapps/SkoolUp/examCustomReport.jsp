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
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Exam Based Report - SkoolUp</title>
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
		$('#reportLiID').addClass("active");
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
    
    .checkboxStyle {
		color:grey;
		font-size: 15px;
		padding: 2px;
			
	}

	input[type=checkbox]{
			
		margin: 10px;
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

	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	ConfigurationForm conform = new ConfigurationForm();
    	
    	StudentForm form = new StudentForm();
    	 
		int AcademicYearID = loginform.getAcademicYearID();
		
    	String StandardName = daoinf.retrieveStandardName(loginform.getUserID(), AcademicYearID);
    	
    	int StandardID = daoinf.retrieveStandardID(loginform.getUserID(), AcademicYearID);
    	
		String DivisionName = daoinf.retrieveDivisionName(loginform.getUserID(), AcademicYearID);
    	
    	int DivisionID = daoinf.retrieveDivisionID(loginform.getUserID(), AcademicYearID);
		
    	String checkBoxNameList = (String) request.getAttribute("checkBoxList");
    	
    	HashMap<String, String> ExamNameList = (HashMap<String, String>) request.getAttribute("ExamList");
    	
    	String ContainsName = (String) request.getAttribute("ContainsName");
    	
    	if(ContainsName == null || ContainsName == ""){
    		ContainsName = "dummy";
		}
    	
		String Value = (String) request.getAttribute("Value");
    	
    	if(Value == null || Value == ""){
    		Value = "dummy";
		}
    	
		String Marks = (String) request.getAttribute("Marks");
    	
    	if(Marks == null || Marks == ""){
    		Marks = "dummy";
		}
    	
    	int subjectType = (Integer) request.getAttribute("subjectType");
    %>

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
      
      function checkList(listID, subID){
  		
  		if(listID == "-1" ){
  			alert("Please select Contains value.");
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
	    	
	    	$("#validate-basic").attr("action","LoadExamCustomStudents");
  			$("#validate-basic").submit();
			  	  
		 	$("#"+subID).attr("disabled", "disabled");
		 	
  			return true;
  		}
      }
  	
 </script>
	 
	<%
		List<String> studentFinalExamCustomReportList = (List<String>) request.getAttribute("studentFinalExamCustomReportList");
	
		String ExamCustomList = (String) request.getAttribute("ExamCustomList");
	
		String loadStudentSearch = (String) request.getAttribute("loadStudentSearch");
	
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
		
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
	%>

<script type="text/javascript">

	function radioCheck(value) {  
		
		$("input[name=checkBoxList]").removeAttr("checked");
		
	    if (value == "Student") {
	    	
	    	$("#StudentID").show();
	    	$("#ParentID").hide();
	    	$("#EmergencyID").hide();
	    	$("#CommutationID").hide();
	    	$("#MedicalID").hide();
	        
	    } else if (value == "Parent") {
	    	
	    	 $("#ParentID").show();
	    	 $("#StudentID").hide();
	    	 $("#EmergencyID").hide();
	    	 $("#CommutationID").hide();
	    	 $("#MedicalID").hide();
	   
	    }else if (value == "Emergency") {
	    	
	    	$("#EmergencyID").show();
	    	$("#StudentID").hide();
	    	$("#ParentID").hide();
	    	$("#CommutationID").hide();
	    	$("#MedicalID").hide();
	   
	    }else if (value == "Commutation") {
	    	
	    	 $("#CommutationID").show();
	    	 $("#StudentID").hide();
	    	 $("#ParentID").hide();
	    	 $("#EmergencyID").hide();
	    	 $("#MedicalID").hide();
	   
	    }else if (value == "Medical") {
	    	
	    	$("#MedicalID").show();
	    	$("#StudentID").hide();
	    	$("#ParentID").hide();
	    	$("#EmergencyID").hide();
	    	$("#CommutationID").hide();
	    }
	}

	function CheckAge(Value, term, standardID, divisionID){
		
		 if(Value =="MarksBased"){
			
			 $("#ageValueDivID").show();
			 $("#marksValueDivID").show();
			 $("#gradeValueDivID").hide(); 
			 
			 retrieveExamList(term, standardID, divisionID);
			 
		}else {
			 $("#ageValueDivID").hide();
			 $("#marksValueDivID").hide();
			 $("#CheckBoxTotalDivID").hide();
			 $("#gradeValueDivID").show(); 
	
			 retrieveExamTermEnd(term, standardID);
		 }
	 }
 
	function gradeCheck(subjectID){ 
		if(subjectID =="-1"){
			alert("Please select Subject.");
		}else{
			retrieveGradeCheck(subjectID);
		}
	}

	function CheckAgeFormat(ageValue){
		 
		 if(ageValue =="between"){
			
			 $("#ageDivID").show();
			 
		 }else {
			 
			 $("#ageDivID").hide();
		}
	}
	
	function CheckmarksFormat(marksValue){
		 
		 if(marksValue =="marksScaled"){
			
			 $("#CheckBoxTotalDivID").show();
			 
		 }else {
			 
			 $("#CheckBoxTotalDivID").hide();
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

function retrieveGradeCheck(subjectID){

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			var array_element = "";
			var check = 0;
			
			for ( var i = 0; i < array.Release.length; i++) {
				
				check = array.Release[i].check;
			}

			if(check == 0){
				console.log("check:"+check);
				array_element += "<select name='gradeValue' id='gradeValueID' class='form-control'"+
					"> <option value='-1'>Select Grade</option>"+
					"<option value='A1'>A1</option>"+
					"<option value='A2'>A2</option>"+
					"<option value='B1'>B1</option>"+
					"<option value='B2'>B2</option>"+
					"<option value='C1'>C1</option>"+
					"<option value='C2'>C2</option>"+
					"<option value='D'>D</option>"+
					"<option value='E'>E</option></select>";
					
				document.getElementById("gradeValueDivID").innerHTML = array_element;
				
			}else{
				console.log("check1:"+check);
				array_element += "<select name='gradeValue' id='grade1ValueID' class='form-control'"+
					"> <option value='-1'>Select Grade</option>"+
					"<option value='S'>S</option>"+
					"<option value='G'>G</option>"+
					"<option value='NI'>NI</option>";
					
				document.getElementById("gradeValueDivID").innerHTML = array_element;	
			}
		}
	};
	
	xmlhttp.open("GET", "VerifySubjeType?subjectID="
			+ subjectID, true);
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

	function retrieveExamList(term, standardID, divisionID){

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				var array_element = "";
				var check = 0;
				var examName = "";
				/* For division */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;
					
					examName = array.Release[i].ExamList.split("\\$");
					
					array_element += "<input name='checkBoxList' value='"+array.Release[i].ExamList+"' id ='CheckBoxListID' type='checkbox'>"+
									 "<label for='checkBoxList-25' class='checkboxLabel'>"+examName[0]+"</label>";
				}

				if(check == 0){
					
					alert("No Exam found");
					
					var array_element = "";
					
					document.getElementById("CheckBoxDivID").innerHTML = array_element;
					
				}else{
				
					document.getElementById("CheckBoxDivID").innerHTML = array_element;	
					
				}
				retrieveSubject(standardID);
			}
		};
		
		xmlhttp.open("GET", "RetrieveExamListByTermAndAcademicYearID?term="
				+ term+"&standardID="+standardID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	
	
	function retrieveExamTermEnd(term, standardID){

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				var array_element = "";
				var check = 0;
				var examName = "";
				
				for ( var i = 0; i < array.Release.length; i++) {
				
					check = array.Release[i].check;
					
					examName = array.Release[i].ExamList.split("\\$");
					
					array_element += "<input name='checkBoxList' value='"+array.Release[i].ExamList+"' id ='CheckBoxListID' type='checkbox'>"+
									 "<label for='checkBoxList-25' class='checkboxLabel'>"+examName[0]+"</label>";
				}

				if(check == 0){
					
					alert("No Exam found");
					
					var array_element = "<input name='' value='"+array.Release[i].ExamList+"' id ='CheckBoxListID' type='checkbox'>"+
					 					"<label for='checkBoxList-25' class='checkboxLabel'>"+array.Release[i].ExamList+"</label>";
					
					document.getElementById("CheckBoxDivID").innerHTML = array_element;
					
				}else{
				
					document.getElementById("CheckBoxDivID").innerHTML = array_element;	
					
				}
				
				retrieveSubjectForNonScholastic(standardID);
			}
		};
		xmlhttp.open("GET", "RetrieveExamTermEnd?term="
				+ term, true);
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
	
	function retrieveSubject(standardID){

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var array_element = "<select name='subjectID' id='editSubDivID'  class='form-control'"+
				"> <option value='0'>Select Subject</option>";
				
				var check = 0;
				var subject;
				/* For division */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;
					
					subject = array.Release[i].subjectList.split("\\$");
					
					
					array_element += "<option value='"+subject[1]+"'>"+subject[0]+"</option>";
				}

				array_element += " </select>";
				
				if(check == 0){
					
					alert("No Subject found");
					
					var array_element = "<select name='' id='editSubDivID' class='form-control'"+
					"> <option value='0'>Select Subject</option></select>";
					
					document.getElementById("editSubjectDivID").innerHTML = array_element;
					
				}else{
					
					document.getElementById("editSubjectDivID").innerHTML = array_element;	
				}
			}
		};
		
		xmlhttp.open("GET", "RetrieveSubjectListByUserIDAndstandard?standardID="
				+ standardID, true);
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
	
	function retrieveSubjectForNonScholastic(standardID){

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var array_element = "<select name='subjectID' id='editSubDivID' onchange='gradeCheck(this.value);' class='form-control'"+
				"> <option value='0'>Select Subject</option>";
				
				var check = 0;
				var subject;
				
				/* For division */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;
					
					subject = array.Release[i].subjectList.split("\\$");
					
					
					array_element += "<option value='"+subject[1]+"'>"+subject[0]+"</option>";
				}

				array_element += " </select>";
				
				if(check == 0){
					
					alert("No Subject found");
					
					var array_element = "<select name='' id='editSubDivID' class='form-control'"+
					"> <option value='0'>Select Subject</option></select>";
					
					document.getElementById("editSubjectDivID").innerHTML = array_element;
					
				}else{
					document.getElementById("editSubjectDivID").innerHTML = array_element;	
				}
			}
		};
		
		xmlhttp.open("GET", "RetrieveSubjectListBystandardForNonScholastic?standardID="
				+ standardID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
</script>

<script type="text/javascript">

	function submitReportForm(subID){
		
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
    	
    	$("#validate-basic").attr("action","GenerateAllExamReportExcel");
		$("#validate-basic").submit();
		  	  
	 	$("#"+subID).attr("disabled", "disabled");
	}

</script>

</head>	

<body>

<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Exam Based Report</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Student Details</li>
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
		   <%
		    	if(classTeacherCheck.equals("Yes")){
		    		
		    %> 
		    
		  <form id="validate-basic" action="LoadExamCustomStudents" method="POST" data-validate="parsley" class="form parsley-form">
				
              <div class="row">
               <input type="hidden" name="totalValueString" value='<s:property value="totalValue"/>'>
				<div class="col-md-2 ">
				<input type="hidden" class="form-control" id="standardDivID" name="standardID" value="<%=StandardID%>" >
					<input type="text" class="form-control" name="standard" value="<%=StandardName%>" readonly="readonly">
				</div>
					
				<div class="col-md-2">
					<s:select list="StandardDivisionList" class="form-control" headerKey="-1" id="divisionID" headerValue="Select Division" name="division"></s:select>
				  
		      	</div>
		      	
		      	<div class="col-md-2">
                	<s:select list=" #{'Term I':'Term I','Term II':'Term II','All':'All'}" class="form-control" headerKey="-1" id="termID" headerValue="Select Term"  name="term" ></s:select>
				</div>
				
				<div class="col-md-2" >
		      		<s:select list="#{'GradeBased':'Grade Based','MarksBased':'Marks Based'}" required="required" id="listID" class="form-control" headerKey="-1" 
						name="containsName" headerValue="Contains" onchange="CheckAge(this.value, termID.value, standardDivID.value, divisionID.value)"></s:select>
               </div>
               
		     	<div class="col-md-2" id="editSubjectDivID">
					<s:select list="SubjectListByStandard" class="form-control" headerKey="0" id="editSubDivID"  headerValue="Select Subject" name="subjectID" onchange="gradeCheck(this.value)"></s:select>
				</div>
				
				<% if(ContainsName.equals("GradeBased")){%>
					<div class="col-md-2" id="gradeValueDivID">
				
					<% if(subjectType==1){ %>
				
					<s:select list="#{'G':'G', 'S':'S','NI':'NI'}" required="required" id="grade1ValueID" class="form-control" 
						headerKey="-1" name="gradeValue" headerValue="Select Grade" ></s:select>		
				
					<%}else{ %>
				
						<s:select list="#{'A1':'A1', 'A2':'A2', 'B1':'B1','B2':'B2','C1':'C1','C2':'C2','D':'D','E':'E'}" required="required" id="gradeValueID" class="form-control" 
							headerKey="-1" name="gradeValue" headerValue="Select Grade" ></s:select>
					<% } %>
				
					</div>
				<%}else{%>
					<div class="col-md-2" id="gradeValueDivID" style="display: none">
				
					<% if(subjectType==1){ %>
				
					<s:select list="#{'G':'G', 'S':'S','NI':'NI'}" required="required" id="grade1ValueID" class="form-control" 
						headerKey="-1" name="gradeValue" headerValue="Select Grade" ></s:select>		
				
					<%}else{ %>
				
						<s:select list="#{'A1':'A1', 'A2':'A2', 'B1':'B1','B2':'B2','C1':'C1','C2':'C2','D':'D','E':'E'}" required="required" id="gradeValueID" class="form-control" 
							headerKey="-1" name="gradeValue" headerValue="Select Grade" ></s:select>
					<% } %>
				
					</div>
				
				<%}%>
			</div>
			<div class="row"  style="padding-top: 20px;">
				<%
					if(ContainsName.equals("MarksBased")){%>
					
						<div class="col-md-2" id="ageValueDivID">
						<s:select list="#{'=':'=', '<':'<','between':'BETWEEN','>':'>'}" required="required" id="listValueID" class="form-control" 
							headerKey="-1" name="ageValue" headerValue="Select criteria" onchange="CheckAgeFormat(this.value)"></s:select>
						</div>
				
						<div class="col-md-2" id="marksValueDivID">
						<s:select list="#{'marksObtained':'Marks Obtained', 'marksScaled':'Marks Scaled'}" required="required" id="listMarksID" class="form-control" 
							headerKey="-1" name="marks" headerValue="Select marks format" onchange="CheckmarksFormat(this.value)"></s:select>
						</div>
				<%	}else{ %>
					
					<div class="col-md-2" style="display: none" id="ageValueDivID">
						<s:select list="#{'=':'=', '<':'<','between':'BETWEEN','>':'>'}" required="required" id="listID" class="form-control" 
							headerKey="-1" name="ageValue" headerValue="Select format" onchange="CheckAgeFormat(this.value)"></s:select>
					</div>
					
					<div class="col-md-2" style="display: none" id="marksValueDivID">
						<s:select list="#{'marksObtained':'Marks Obtained', 'marksScaled':'Marks Scaled'}" required="required" id="listMarksID" class="form-control" 
							headerKey="-1" name="marks" headerValue="Select marks format" onchange="CheckmarksFormat(this.value)"></s:select>
					</div>
						
				<%} %>
				
			</div>
			
			<hr>
			<div class="row">
			 <div id="CheckBoxDivID" class="col-md-10">
				<%
				 for(String ExamFormName:ExamNameList.keySet()){
					if(checkBoxNameList==null){
					%>
					     <input type="checkbox" id="CheckBoxListID" value="<%=ExamFormName %>" name="checkBoxList" >
					     <label for='checkBoxList-25' class='checkboxLabel'><%=ExamNameList.get(ExamFormName)%></label>
					<%
					}else{
						
						if(checkBoxNameList.contains(ExamFormName)){
							%>
								<input type="checkbox" id="CheckBoxListID" value="<%=ExamFormName %>" checked="checked" name="checkBoxList">
							  	<label for='checkBoxList-25' class='checkboxLabel'><%=ExamNameList.get(ExamFormName)%></label>
							<% 
						}else{
						%>
						     <input type="checkbox" id="CheckBoxListID" value="<%=ExamFormName %>" name="checkBoxList" >
						     <label for='checkBoxList-25' class='checkboxLabel'><%=ExamNameList.get(ExamFormName)%></label>
						<%
						}
					}
				} 
			 %>
			 </div>
			 
			 <%if(Marks.equals("marksScaled")){ %>
				 <div class="col-md-2" id="CheckBoxTotalDivID">
					 <s:checkbox name="totalValue" fieldValue="Total"   id="CheckBoxListID" value="Total" label="Total"/>
				 </div>
     		
     		<%}else{ %>
     		
     			<div class="col-md-2" style="display: none" id="CheckBoxTotalDivID">
     				<s:checkbox name="totalValue" fieldValue="Total" id="CheckBoxListID" value="Total" label="Total"/>
				</div>
	     	<%} %>
	     	
     		</div>
			<hr>
			<div class="row">
				<div class="col-md-2">
					<s:textfield name="searchFields" cssClass="form-control" cssStyle="width:100%;" ></s:textfield>
	            </div>
		     <%if(Value.equals("between")){ %>
		     	
		     	<div class="col-md-2" id="ageDivID">
					<s:textfield name="searchFieldsNew" cssClass="form-control" cssStyle="width:100%;" ></s:textfield>
	            </div> 
	        
	         <%}else{ %>   
	         	
	         	<div class="col-md-2" style="display: none" id="ageDivID">
					<s:textfield name="searchFieldsNew" cssClass="form-control" cssStyle="width:100%;" ></s:textfield>
	            </div> 
	        
	         <%} %>
	            
	            <div class="col-md-2">
					<button type="submit" class="btn btn-warning" id="submitID" onclick="return checkList(listID.value, 'submitID')" >Search Report</button>
				</div>
			</div>
			
		</form>	
		<hr>
			
			<div class="tab-pane fade in active">
				<%
					if(loadStudentSearch.equals("Enabled")){
				%>
				<div class="row">
	                <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12" align="right">
						<button class="btn btn-success" type="button" id="exportSubmitID" onclick="submitReportForm('exportSubmitID');">Export to Excel</button>
	                </div>
	           </div>
	           
				<div class="row" style="overflow:auto; margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                       
	                       	<%
	                       		String[] StudentDetails = ExamCustomList.split(",");
	                       	
	                       		for (int i = 0; i < StudentDetails.length; i++) 
		          		      	{%>
		          		      
	                       			<th ><%=StudentDetails[i]%></th>
	                       			
		          		      	<%}%>
	                       
	                      	 </tr>
	                      </thead>
	                  <tbody>
	                      
	                 <%
	                 	
	                     for (String FinalStudentsReportData : studentFinalExamCustomReportList) { 
	                     	if(FinalStudentsReportData.isEmpty()){
	                     		
	                     	}else{ %>
	                    
		                    <tr id="newTRID<%=form.getStudentID()%>" style="font-size: 14px;width: 6%;">		
		                    
		                      <% String[] List = FinalStudentsReportData.split("=");
		                      		
		                      	for (int j = 0; j < List.length; j++){ %>
		                      	
			                      <td style="width: 6%;"><%=List[j]%></td>
			                  		
		         		   		<% }%>
		         		   		
		                     </tr> 	
	                   	<% } 
	                 }%>	
	         		   
	                  </tbody>
	                </table>
		           </div>
			       
			       <%
						}
			       %>
			      
				 </div>
				 <%} %>
				 
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
