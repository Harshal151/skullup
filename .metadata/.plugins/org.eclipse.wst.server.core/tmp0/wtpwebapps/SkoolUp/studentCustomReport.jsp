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
  <title>Students Based Report - SkoolUp</title>
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
        
</style>

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
    
    .checkboxStyle {
			color:grey;
			font-size: 15px;
			padding: 2px;
	}

</style>

	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	ConfigurationForm conform = new ConfigurationForm();
    	
    	StudentForm form = new StudentForm();
		
		String ContainsName = (String) request.getAttribute("ContainsName");
    	
    	if(ContainsName == null || ContainsName == ""){
    		ContainsName = "dummy";
		}
    	
		String Value = (String) request.getAttribute("Value");
    	
    	if(Value == null || Value == ""){
    		Value = "dummy";
		}
    	
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
        document.location="welcome.jsp";
      }
     
      function checkList(contains, Value){
  		
  		if(contains == "-1" ){
  			alert("Please select Contains value.");
  			return false;
  		}else{
	    	 $("input[name=searchFields]").attr("required", "false");
  			$("#validate-basic").attr("action","LoadCustomStudents");
  			$("#validate-basic").submit();
  			
  			$("html, body").animate({ scrollTop: 0 }, "fast");
  			$(".loadingImage").show();
  			$(".container").css("opacity","0.1");
  			$(".navbar").css("opacity","0.1");
  			return true;
  		}
      }
  	
 </script>
	 
	<%
		List<String> studentsBasedCustomReportList = (List<String>) request.getAttribute("studentsBasedCustomReportList");
	
		String StudentCustomList = (String) request.getAttribute("StudentCustomList");
	
		String loadStudentSearch = (String) request.getAttribute("loadStudentSearch");
	
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
		
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
		
	%>
	
	<style type="text/css">
	
		input[type=checkbox]{
			
			margin: 10px;
			
		}
	
	</style>

<script type="text/javascript">

	function CheckAge(Value){
	
		$("#mainDivID").hide();
		
		var studentString = 'StudentName,gender,age,bloodgroup,aadhaar,grNumber,hasSpectacles,weight,height,house,creativeActivities,'
								+'physicalActivities,religion,category,address,siblingID,commutationMode';
		
		var parentString ='Parent5,Parent1,Parent3,Parent4,Parent2';
		
		var emergencyString ='name,phone';
		
		var commutationString ='Commutation2,Commutation1,Commutation3';
		
		var medConditionString ='MedicalHistory';
		
	    if(studentString.includes(Value)){
			
			 if(Value =="age" || Value =="height" || Value =="weight"){
					
				 $("#ageValueDivID").show();
				 $("#ageValueID").attr("required","required");
				 $("input[name=searchFields]").attr("required","required");
				 $("input[name=searchFields]").prop("type","number");
				 
			 }else {
				 $("#ageValueDivID").hide();
				 $("#ageValueID").removeAttr("required");
				 $("input[name=searchFields]").removeAttr("required");
				 $("input[name=searchFields]").prop("type","text");
			
			 }    
			
			$("#StudentSection").attr("checked","checked");
			$("input[name=checkBoxList]").removeAttr("checked");
			
			$("#StudentID").show();
			$("#ParentID").hide();
			$("#EmergencyID").hide();
			$("#CommutationID").hide();
			$("#MedicalID").hide();
			
		}else if(parentString.includes(Value)){
			
			$("#ParentSection").attr("checked","checked");
			$("input[name=checkBoxList]").removeAttr("checked");
			
			$("#ParentID").show();
			$("#StudentID").hide();
			$("#EmergencyID").hide();
			$("#CommutationID").hide();
			$("#MedicalID").hide();
			 
		}else if(emergencyString.includes(Value)){
		
			$("#EmergencyContactSection").attr("checked","checked");
			$("input[name=checkBoxList]").removeAttr("checked");
			
			$("#EmergencyID").show();
			$("#StudentID").hide();
			$("#ParentID").hide();
			$("#CommutationID").hide();
			$("#MedicalID").hide();
			
			 $("#ageValueDivID").hide();
	
		}else if(commutationString.includes(Value)){
		  	
			$("#CommutationSection").attr("checked","checked");
			$("input[name=checkBoxList]").removeAttr("checked");
			
			$("#CommutationID").show();
			$("#StudentID").hide();
			$("#ParentID").hide();
			$("#EmergencyID").hide();
			$("#MedicalID").hide();
			
			 $("#ageValueDivID").hide();
			 
		}else if(medConditionString.includes(Value)){
			
			$("#MedicalCondition").attr("checked","checked");
			$("input[name=checkBoxList]").removeAttr("checked");
			
			$("#MedicalID").show();
			$("#StudentID").hide();
			$("#ParentID").hide();
			$("#EmergencyID").hide();
			$("#CommutationID").hide();
			
			 $("#ageValueDivID").hide();
		}
	 }
 
	function CheckAgeFormat(ageValue){
		 
		 if(ageValue =="between"){
			
			 $("#ageDivID").show();
			 
		 }else {
			 
			 $("#ageDivID").hide();
		}
	}

</script>

<%
	String radioValue = (String) request.getAttribute("radioValue");
	
	if(radioValue == null || radioValue == ""){
		radioValue = "dummy";
	}else {
		String checkboxDivID = radioValue+"ID";
		
%>
<script type="text/javascript">
    jQuery(document).ready(function($) {
    	
        $("input[name=radioValue][value='<%=radioValue%>']").attr("checked","checked");
        $("#<%=checkboxDivID%>").show();
    });
</script>
<%
	}
	
	String checkBoxList = (String) request.getAttribute("checkBoxList");
	
	if(checkBoxList == null || checkBoxList == ""){
		checkBoxList = "dummy";
	}else{
%>
<script type="text/javascript">
    jQuery(document).ready(function($) {
    	
    <%
    	String[] array = checkBoxList.split(", ");
    	for(int i = 0; i < array.length; i++){
    %>
        $("input[name=checkBoxList][value='<%=array[i].trim()%>']").attr("checked","checked");
     <%
    	}
     %>
    });
</script>
<%
	}
%>

<script type="text/javascript">

	function submitReportForm(){
		
		$("#validate-basic").attr("action","GenerateAllStudentReportExcel");
		$("#validate-basic").submit();
	
	}

</script>

<script type="text/javascript">

var xmlhttp;
if (window.XMLHttpRequest) {
	xmlhttp = new XMLHttpRequest();
} else {
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

function loadStandardDivision(academicYearID, searchID){
	
	if(academicYearID == "-1"){
		alert("No academicYear is selected. Please select academicYear.");
		
		var array_element = "<input type='text' id = 'stdID' class='form-control' name='standard' readonly='readonly'>";
		
		document.getElementById("stdDivID").innerHTML = array_element;
		
		/* For Division...*/
		var array_element1 = "<input type='text' id = 'DivID' class='form-control' name='division' readonly='readonly'>";
		
		document.getElementById("subDivID").innerHTML = array_element;
	}else{
		retrieveStandard(academicYearID, searchID);
	}
	
}

function retrieveStandard(academicYearID, searchID) {

	var mysearchID = document.getElementById(searchID);
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			
				var array_element = "<input type='hidden' class='form-control' name='standardID' >"+
								"<input type='text' id = 'stdID' class='form-control' name='standardID' readonly='readonly'>";
				
				var check = 0;
				/* For division */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

					array_element = "<input type='hidden' class='form-control' name='standardID' value='"+array.Release[i].standardID+"' >"+
					"<input type='text' id = 'stdID' readonly='readonly' name='standard' class='form-control' value='"+array.Release[i].standard+"'>";
				}

				array_element += " </select>";
				
				if(check == 0){
					
					alert("You are not assigned as a class teacher to any class.");
					
					mysearchID.disabled = "disabled";
					
					var array_element = "<input type='hidden' class='form-control' name='standardID'  >"+
					"<input type='text' name='standard' readonly='readonly' id = 'stdID' class='form-control' >";
					
					document.getElementById("stdDivID").innerHTML = array_element;
					
				}else{
					
					mysearchID.disabled = "";
					
					document.getElementById("stdDivID").innerHTML = array_element;	
												
				}
				
				retrieveDivisionByStandardIDAndacademicYearID(academicYearID, searchID);
		}
	};
	xmlhttp.open("GET", "RetrieveStandardForacademicYear?academicYearID="
			+ academicYearID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}


function retrieveDivisionByStandardIDAndacademicYearID(academicYearID,searchID) {

	var mysearchID = document.getElementById(searchID);
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			
				var array_element = "<input type='hidden' class='form-control' name='divisionID' >"+
							"<input type='text' id = 'DivID' class='form-control' name='division' readonly='readonly'>";
				
				var check = 0;
				/* For division */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

					array_element = "<input type='hidden' class='form-control' name='divisionID' value='"+array.Release[i].divisionID+"' >"+
					"<input type='text' id = 'DivID' name='division' readonly='readonly' class='form-control' value='"+array.Release[i].division+"'>";
				}

				array_element += " </select>";
				
				if(check == 0){
					
					alert("You are not assigned as a class teacher to any class.");
					
					mysearchID.disabled = "disabled";
					
					var array_element = "<input type='hidden' class='form-control' name='divisionID'  >"+
					"<input type='text' name='division'  readonly='readonly' id = 'DivID' class='form-control' >";
					
					document.getElementById("subDivID").innerHTML = array_element;
					
				}else{
					
					mysearchID.disabled = "";

					document.getElementById("subDivID").innerHTML = array_element;	
												
				}
				
		}
	};
	xmlhttp.open("GET", "RetrieveDivisionForacademicYear?academicYearID="
			+ academicYearID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>

<style type="text/css">
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
   
</head>	

<body onload="changeLanguageByButtonClick('<%=loginform.getMedium()%>');">

<img src="images/loading.gif" alt="Loading..." class="loadingImage" style="z-index:1;display: none;">
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="google_translate_element" style="display:none"></div>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Students Based Report</h2>
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
		 
		  <form id="validate-basic" action="LoadCustomStudents" method="POST" data-validate="parsley" class="form parsley-form">
				
              <div class="row">
              	<div class="col-md-2 ">
               	<s:select list="AcademicYearNameList" required="required" class="form-control" headerKey="-1" 
					name="academicYearID" headerValue="Select Academic Year" onchange="loadStandardDivision(this.value, 'searchID')">
				</s:select>
				</div> 
				<div class="col-md-1 " id = "stdDivID">
				<input type="hidden" class="form-control" name="standardID" value="<s:property value="standardID"/>" >
					<input type="text" id = "stdID" class="form-control" name="standard" value="<s:property value="standard"/>" readonly="readonly">
				</div>
					
				<div class="col-md-1" id = "subDivID">
				   <input type="hidden" class="form-control" name="divisionID" value="<s:property value="divisionID"/>" >
				   <input type="text" class="form-control" id = "DivID" name="division" value="<s:property value="division"/>" readonly="readonly">	
		      	</div>

		      	<div class="col-md-2" >
		      	
			      <s:select list="#{'StudentName':'Student Name','gender':'Gender','age':'Age','bloodgroup':'Blood Group','aadhaar':'Aadhaar No.'
						,'grNumber':'GR. Number','hasSpectacles':'Has Spectacles','weight':'Weight','height':'Height','house':'House','creativeActivities':'Creative Activities',
						'physicalActivities':'Physical Activities','MedicalHistory':'Medical Condition','religion':'Religion','category':'Category',
						'address':'Address','name':'Emergency Contact Name','phone':'Emergency Contact Phone','siblingID':'Siblings','Parent5':'Parents Name',
						'Parent1':'Parents Phone','Parent3':'Parents Relation','Parent4':'Parents Email Id','Parent2':'Parents Occupation',
						'commutationMode':'Commutation Mode','Commutation2':'Name Of Driver','Commutation1':'Vehicle Reg. No',
						'Commutation3':'Driver Mobile'}" required="required" id="listID" class="form-control" headerKey="-1" 
						name="containsName" headerValue="Contains" onchange="CheckAge(this.value)"></s:select>
                
                </div>
				<% if(ContainsName.equals("age") || ContainsName.equals("weight") || ContainsName.equals("height")){%>
				
					<div class="col-md-2" id="ageValueDivID">
						<s:select list="#{'=':'=', '<':'<','between':'BETWEEN','>':'>'}" id="ageValueID" class="form-control" headerKey="" 
							name="ageValue" headerValue="Select format" onchange="CheckAgeFormat(this.value)"></s:select>
					</div>
				<%}else{ %>
				
					<div class="col-md-2" style="display: none" id="ageValueDivID">
						<s:select list="#{'=':'=', '<':'<','between':'BETWEEN','>':'>'}" id="ageValueID" class="form-control" headerKey="" 
							name="ageValue" headerValue="Select format" onchange="CheckAgeFormat(this.value)"></s:select>
					</div>
						
				<%} %>
				
				<div class="col-md-2">
					<s:textfield name="searchFields" id="searchFieldID" cssClass="form-control" cssStyle="width:100%;" ></s:textfield>
	            	<%-- <s:text name="searchFields" ></s:text> --%>
	                <!-- <input type="text" name="searchFields" class="form-control" style="width:100%;" placeholder="Search Fields" required="required"> -->
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
	           
	           <%
					if(loadStudentSearch.equals("Enabled")){
				%>
			      	<div class="col-md-2">
						<button type="submit" class="btn btn-warning" id = "searchID"  onsubmit="return checkList(listID.value, ageValueID.value);">Search Report</button>
					</div>
				<%}else{ %>
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" id = "searchID" disabled="disabled"  onsubmit="return checkList(listID.value, ageValueID.value);">Search Report</button>
					</div>
				<%} %>
		   </div>
			
		<hr>
			
	      <span id="StudentID" style="display:none">
	      <input name="radioValue" id="StudentSection" value="Student" type="radio" >
	     	<label for="ParentSection">Student Section: </label>
	     	<div>
	     		<input name="checkBoxList" value="s.gender AS Student_Gender" id="checkBoxList-1" type="checkbox">
	     		<label for="checkBoxList-1" class="checkboxLabel">Gender</label>
				<input name="checkBoxList" value="r.age AS Student_Age" id="checkBoxList-2" type="checkbox">
				<label for="checkBoxList-2" class="checkboxLabel">Age</label>
				<input name="checkBoxList" value="s.dateOfBirth AS Student_Date_Of_Birth" id="checkBoxList-25" type="checkbox">
				<label for="checkBoxList-25" class="checkboxLabel">Date Of Birth</label>
				<input name="checkBoxList" value="s.bloodgroup AS Student_Bloodgroup" id="checkBoxList-3" type="checkbox">
				<label for="checkBoxList-3" class="checkboxLabel">Blood Group</label>
				<input name="checkBoxList" value="s.aadhaar AS Student_Aadhaar_No" id="checkBoxList-4" type="checkbox">
				<label for="checkBoxList-4" class="checkboxLabel">Aadhaar No</label>
				<input name="checkBoxList" value="sd.grNumber AS Student_GR_Number" id="checkBoxList-5" type="checkbox">
				<label for="checkBoxList-5" class="checkboxLabel">GR Number</label>
				<input name="checkBoxList" value="s.hasSpectacles AS Student_Has_Spectacles" id="checkBoxList-6" type="checkbox">
				<label for="checkBoxList-6" class="checkboxLabel">Has Spectacles</label>
				<input name="checkBoxList" value="r.weight AS Student_Weight" id="checkBoxList-7" type="checkbox">
				<label for="checkBoxList-7" class="checkboxLabel">Weight</label>
				<input name="checkBoxList" value="r.height AS Student_Height" id="checkBoxList-8" type="checkbox">
				<label for="checkBoxList-8" class="checkboxLabel">Height</label>
				<input name="checkBoxList" value="sd.house AS Student_House" id="checkBoxList-9" type="checkbox">
				<label for="checkBoxList-9" class="checkboxLabel">House</label>
				<input name="checkBoxList" value="s.religion AS Student_Religion" id="checkBoxList-10" type="checkbox">
				<label for="checkBoxList-10" class="checkboxLabel">Religion</label>
				<input name="checkBoxList" value="s.category AS StudentCategory" id="checkBoxList-11" type="checkbox">
				<label for="checkBoxList-11" class="checkboxLabel">Category</label>
				<input name="checkBoxList" value="(SELECT GROUP_CONCAT(lastName,' ',firstName,' ',middleName) FROM Student WHERE id IN(s.siblingID)) AS Student_Siblings" id="checkBoxList-12" type="checkbox">
				<label for="checkBoxList-12" class="checkboxLabel">Siblings</label>
				<input name="checkBoxList" value="sc.address AS Student_Address" id="checkBoxList-13" type="checkbox">
				<label for="checkBoxList-13" class="checkboxLabel">Address</label>
				<input name="checkBoxList" value="r.creativeActivities AS Creative_Activities" id="checkBoxList-21" type="checkbox">
				<label for="checkBoxList-21" class="checkboxLabel">Creative Activities</label>
				<input name="checkBoxList" value="r.physicalActivities AS Physical_Activities" id="checkBoxList-22" type="checkbox">
				<label for="checkBoxList-22" class="checkboxLabel">Physical Activities</label>
				<input name="checkBoxList" value="sd.commutationMode AS Commutation_Mode" id="checkBoxList-24" type="checkbox">
				<label for="checkBoxList-24" class="checkboxLabel">Commutation Mode</label>
			</div>
		</span>
			
		<span id="ParentID" style="display:none">
		<input name="radioValue" id="ParentSection" value="Parent" type="radio" >
			<label for="ParentSection">Parent Section: </label>
	     	<div>
				<input name="checkBoxList" value="GROUP_CONCAT(p.lastName,' ',p.firstName,' ',p.middleName SEPARATOR ' $ ') AS Father_Name$Mother_Name" id="checkBoxList-14" type="checkbox">
				<label for="checkBoxList-14" class="checkboxLabel">Parents Name</label>
				<input name="checkBoxList" value="GROUP_CONCAT(p.mobile SEPARATOR ' $ ') AS Father_Mobile$Mother_Mobile" id="checkBoxList-15" type="checkbox">
				<label for="checkBoxList-15" class="checkboxLabel">Parents Phone</label>
				<input name="checkBoxList" value="GROUP_CONCAT(p.relation SEPARATOR ' $ ') AS Father_Relation$Mother_Relation" id="checkBoxList-16" type="checkbox">
				<label for="checkBoxList-16" class="checkboxLabel">Parents Relation</label>
				<input name="checkBoxList" value="GROUP_CONCAT(p.emailId SEPARATOR ' $ ') AS Father_Email_Id$Mother_Email_Id" id="checkBoxList-17" type="checkbox">
				<label for="checkBoxList-17" class="checkboxLabel">Parents Email Id</label>
				<input name="checkBoxList" value="GROUP_CONCAT(p.occupation SEPARATOR ' $ ') AS Father_Occupation$Mother_Occupation" id="checkBoxList-18" type="checkbox">
				<label for="checkBoxList-18" class="checkboxLabel">Parents Occupation</label>
			</div>	
		</span>
			
		<span id="EmergencyID" style="display:none">
		
		<input name="radioValue" id="EmergencyContactSection" value="Emergency" type="radio" >
			<label for="ParentSection">Emergency Contact Section: </label>
	     	<div>
				<input name="checkBoxList" value="e.name AS Emergency_Contact_Name" id="checkBoxList-19" type="checkbox">
				<label for="checkBoxList-19" class="checkboxLabel">Emergency Contact Name</label>
				<input name="checkBoxList" value="e.phone AS Emergency_Contact_Phone" id="checkBoxList-20" type="checkbox">
				<label for="checkBoxList-20" class="checkboxLabel">Emergency Contact Phone</label>
			</div>	
		</span>
			
		<span id="CommutationID" style="display:none">
		<input name="radioValue" id="CommutationSection" value="Commutation" type="radio" >
			<label for="ParentSection">Commutation Section: </label>
	     	<div>
				<input name="checkBoxList" value="c.nameOfDriver AS Name_Of_Driver" id="checkBoxList-25" type="checkbox">
				<label for="checkBoxList-25" class="checkboxLabel">Name Of Driver</label>
				<input name="checkBoxList" value="c.vehicleRegNumber AS Vehicle_Reg_Number" id="checkBoxList-26" type="checkbox">
				<label for="checkBoxList-26" class="checkboxLabel">Vehicle Reg No</label>
				<input name="checkBoxList" value="c.driverMobile AS Driver_Mobile_No" id="checkBoxList-27" type="checkbox">
				<label for="checkBoxList-27" class="checkboxLabel">Driver Mobile No</label>
			</div>
		</span> 
			
		<span id="MedicalID" style="display:none">
			<input name="radioValue" id="MedicalCondition" value="Medical" type="radio" >
			<label for="ParentSection">Medical Section: </label>
	     	<div>
				<input name="checkBoxList" value="m.medCondition AS Medical_Condition" id="checkBoxList-23" type="checkbox">
				<label for="checkBoxList-23" class="checkboxLabel">Medical Condition</label>
			</div>
		</span>
		
	</form>	
	
		<hr>
			
			<div class="tab-pane fade in active" id ="mainDivID">
				<%
					if(loadStudentSearch.equals("Enabled")){
				%>
				<div class="row">
	                <div class="col-sm-12 col-md-12 col-lg-12 col-xs-12" align="right">
						<button class="btn btn-success" type="button" onclick="submitReportForm();">Export to Excel</button>
	                </div>
	           </div>
	           
				<div class="row" style="overflow:auto; margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                       
	                       	<%
	                       		String[] StudentDetails = StudentCustomList.split(",");
	                       	
	                       		for (int i = 0; i < StudentDetails.length; i++) 
		          		      	{%>
		          		      
	                       			<th ><%=StudentDetails[i]%></th>
	                       			
		          		      	<%}%>
	                       
	                      	 </tr>
	                      </thead>
	                  <tbody>
	                      
	                  <%
	                     for (String FinalStudentsReportData : studentsBasedCustomReportList) { %>
	                    
	                    <tr id="newTRID<%=form.getStudentID()%><%=form.getAyclassID()%>" style="font-size: 14px;width: 6%;">		
	                    
	                      <% String[] List = FinalStudentsReportData.split("\\$");
	                      		
	                      	for (int j = 0; j < List.length; j++){ %>
	                      	
		                      <td style="width: 6%;"><%=List[j]%></td>
		                  		
	         		   		<% }%>
	         		   		
	                     </tr> 	
	                   <% }%>	
	         		   
	                   <%-- <% 
				       		for(StudentForm form:studentsBasedCustomReportList){
				       			
				       %>
				        
				       <tr id="newTRID<%=form.getStudentID()%><%=form.getAyclassID()%>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 6%;">
				       			<%=form.getRollNumber()%>
				       		</td>	
				       		
				       		<td style="text-align: left;">
				       			<input type="hidden" name="newStudentID" value="<%=form.getStudentID()%>">
				       			<%=form.getStudentName()%>
				       		</td>
				       	
					      
					   </tr> 
				        
				        <% 
				        	}
				        %> --%>
	                  
	                      </tbody>
	                    </table>
		                
			        </div>
			       
			       <%
						}
			       %>
			      
				 </div>
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
