<%@page import="javassist.expr.NewArray"%>
<%@page import="com.kovidRMS.daoImpl.ConfigurationDAOImpl"%>
<%@page import="com.kovidRMS.daoInf.ConfigurationDAOInf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
     <%@page import="com.kovidRMS.form.LoginForm"%>
     <%@page import="java.util.HashMap"%>
     <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="com.kovidRMS.form.StudentForm"%>
      <%@page import="com.kovidRMS.form.ConfigurationForm"%>
     <%@page import="java.util.List"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>

<% 
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
	
		LoginDAOInf daoInf1 = new LoginDAOImpl();
		
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
		
		int AcademicYearID = loginform.getAcademicYearID();
		
		HashMap<Integer, String> StandardList = daoInf1.getStandard(loginform.getOrganizationID());
		
		int StandardID = daoInf1.retrieveStandardID(loginform.getUserID(), AcademicYearID);
		
		HashMap<Integer, String> DivisionList = daoInf1.getDivision(StandardID);
		
	 	List<ConfigurationForm> subjectNewList = (List<ConfigurationForm>) request.getAttribute("subjectNewList");
	 
	 	List<ConfigurationForm> subjectAssessmentListNew = (List<ConfigurationForm>) request.getAttribute("subjectAssessmentListNew");
		
	 	String loadSubjectSearch = (String) request.getAttribute("loadSubjectSearch");
	
		if(loadSubjectSearch == null || loadSubjectSearch == ""){
			loadSubjectSearch = "dummy";
		}
	%>
	
  <title><% if(loginform.getMedium().equals("mr")){ %>विषय मूल्यमापन कॉन्फीग्युर करा - SkoolUp <% }else{ %>Configure Subject Assessment - SkoolUp<% } %></title>
  
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

  <script type="text/javascript">
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
        
         /* Chrome, Safari, Edge, Opera */
		input::-webkit-outer-spin-button,
		input::-webkit-inner-spin-button {
		  -webkit-appearance: none;
		  margin: 0;
		}
		
		/* Firefox */
		input[type=number] {
		  -moz-appearance: textfield;
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
      
      function windowOpen1(){
    	  
    	  $('html, body').animate({
  	        scrollTop: $('body').offset().top
  	   }, 1000);
  		
  		$('body').css('background', '#FCFAF5');
  		$(".container").css("opacity","0.2");
  		$("#loader").show();
  		$("body").find("button").attr("disabled","disabled"); 
  		
          document.location="RenderconfigureSubjectAssessment";
        }
      
   
     /* Load student standard and division ID */ 
     
      function checkStandard(sid, subject, exam){
    		
    	 	if(exam == "-1"){
				alert("Please select examination name.");
  			
	  			return false;
	  		}
    	  	else if(sid == "-1"){
    			alert("Please select Standard.");
    			
    			return false;
    		}else if(subject == "-1"){
				alert("Please select Division.");
    			
    			return false;
    		}else{
    			
    			$("html, body").animate({ scrollTop: 0 }, "fast");
    			$(".loadingImage").show();
    			$(".container").css("opacity","0.1");
    			$(".navbar").css("opacity","0.1");
    			return true;
    		}
    	}
      /* End */
      
      function submitForm(loadFormID, subID, Action){
    	  
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
	    	
	    	$("#"+loadFormID).attr("action", Action);
			$("#"+loadFormID).submit(); 
			  	  
		 	$("#"+subID).attr("disabled", "disabled");
		 	
			return true;
      }
      
      function restoreValue(radioVal, editMaxMarkID, editSceleToID){
	  		if(radioVal == "0"){
	  			$("#"+editMaxMarkID).attr("readonly",false);
	  	    	
	  	    	$("#"+editSceleToID).attr("readonly",false);
	  		}else{
	  			$("#"+editMaxMarkID).attr("readonly",true);
	  	    	
	  	    	$("#"+editSceleToID).attr("readonly",true);
	  	    	
	  			$("#"+editMaxMarkID).val("0");
	  	    	
	  	    	$("#"+editSceleToID).val("0");
	  		}
  		}
      
      function storeValue(gradeBase, hiddenInputID){
			$("#"+hiddenInputID).val(gradeBase);
		}
      
      
      /* When Gradebased radio button has value 1 then disable the maxmark and scaleto columns*/
      function disable(GradeID,MaxMarkID,ScaleID){
      	
  	    if (GradeID == "0") {
  	    	
  	    	$("#"+MaxMarkID).attr("readonly",false);
  	    	
  	    	$("#"+ScaleID).attr("readonly",false);
  	    	
  	      
  	    } else {
  			$("#"+MaxMarkID).attr("readonly",true);
  	    	
  	    	$("#"+ScaleID).attr("readonly",true);
  	    	
  	    }
      }
      
      /* End */
    
      function restoreValue1(radioVal1, editMaxMarkID1, editScaleID1){	
  	    if (radioVal1 == "0") {
  	    	
  	    	$("#"+editMaxMarkID1).attr("readonly",false);
  	    	$("#"+editScaleID1).attr("readonly",false);
  	      
  	    } else {
  			$("#"+editMaxMarkID1).attr("readonly",true);
  			$("#"+editScaleID1).attr("readonly",true);
  	    	
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
			
			var array_element = "<select name='divisionID' id='' class='form-control'"+
			"> <option value='-1'>Select Division</option></select>";
			
			document.getElementById("stdDivID").innerHTML = array_element;
			
			/* For Subject...*/
			var trID = "";
			trID += "<tr style='font-size: 14px;'>"
			    + "<td style='text-align:center'>"+subjectList+"</td>"
			  	+ "</tr>";
			
			document.getElementById("subDivID").innerHTML = array_element1;
			
			$("#newTBodyID").html("<tr id='newTRID'></tr>");
			
		}else{
			$("#newTBodyID").html("<tr id='newTRID'></tr>");
			
			retrieveDivision1(standardID);
		}
	}

	function retrieveDivision1(standardID) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "<select name='divisionID' class='form-control'"+
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
						
						var array_element = "<select name='divisionID' id='' class='form-control'"+
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
  
  var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
  function retrieveExamList(term){
		
		if(term == ""){
			alert("No term is selected. Please select term.");
			
			var array_element = "<select name='ExaminationID' id='' class='form-control'"+
			"> <option value='-1'>Select Exam</option></select>";
			
			document.getElementById("examID").innerHTML = array_element;
			
		}else{
			
			retrieveExamList1(term);
		}
	}

	function retrieveExamList1(term) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "<select name='ExaminationID' id='' class='form-control'"+
					"> <option value='-1'>Select Exam</option></select>";
					
					var check = 0;
					/* For division */
					for ( var i = 0; i < array.Release.length; i++) {
						
						check = array.Release[i].check;
						 
						array_element += "<option value='"+array.Release[i].examID+"'>"+array.Release[i].examName+"</option>";
					}

					array_element += " </select>";
					
					if(check == 0){
						
						alert("No exam found");
						
						var array_element = "<select name='ExaminationID' id='' class='form-control'"+
						"> <option value='-1'>Select Exam</option></select>";
						
						document.getElementById("examID").innerHTML = array_element;
						
					}else{
						
						document.getElementById("examID").innerHTML = array_element;	
					}
				}
		};
		xmlhttp.open("GET", "RetrieveExaminationListByTerm?term="
				+ term, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}	
  
  </script> 
  
	<script type="text/javascript">
		
		function activityMarks(subjectID){
			
			$("#tableTRID").find("tr:gt(1)").remove();
			
			$("#myModal").modal("show");
			
			$("#ActiveID").val(subjectID);
			
			retrieveActivities(subjectID, "Add","");
		}
		
		function editActivityMarks(editsubjectID, editSubjectAssessmentID){
			
			//removing all TR except first tr from tbody
	    	$("#editConfigTableID").find("tr:gt(1)").remove();
	    	
	    	//retrieving subject list based on standardID
	    	viewEditActivityMarks(editsubjectID, editSubjectAssessmentID);
		}
	
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function viewEditActivityMarks(editsubjectID, editSubjectAssessmentID){ 
	    	
	    	xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//alert(AYClassID);
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
							var activity = array.Release[i].activity;
							var activityAssessmentID = array.Release[i].activityAssessmentID;
							var totalMarks = array.Release[i].totalMarks;
							
							var trID = "aySubTRID"+activityAssessmentID;
							
							//alert(array.Release[i].subDivID + ' '+array.Release[i].subject);
							array_element += "<tr id='"+trID+"' style='font-size: 14px;'>"
										   + "<td style='text-align:center'>"+activity+"</td>"
										   + "<td style='text-align:center'>"+totalMarks+"</td>"
										   + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeactivityAssessmentRow(\"" + trID + "\",\"" + activityAssessmentID + "\");'/></td>"
										   + "</tr>";
						}
						
						if(check == 0){
							
							$("#editsubActiveID").val(editSubjectAssessmentID);
							$("#addViewConfigurationModalID").modal("show");
							
						}else{
							
							$(array_element).insertAfter($("#editConfigTRID"));
							
							$("#editsubActiveID").val(editSubjectAssessmentID);
							
							$("#addViewConfigurationModalID").modal("show");	
							
						}
						
						//retrieving sbuject list based on standardID
						retrieveEditActivities(editsubjectID, editSubjectAssessmentID);
				}
			};
			xmlhttp.open("GET", "RetrieveActivitiesListBysubjAssmntID?subjectAssessmentID="
					+ editSubjectAssessmentID, true);
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
		
		/* For retrieving Subjects list with there standardID */
		
		function retrieveEditActivities(editsubjectID, editSubjectAssessmentID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
						var array_element = "<select name='' id='editactivityID' class='form-control'"+
						"> <option value='-1'>Select Activity</option>";
						
						var activities = ""; 
						var activityID = 0;
						
						for ( var i = 0; i < array.Release.length; i++) {
							
							activities = array.Release[i].activity;
							activityID = array.Release[i].activityID;
							array_element += "<option value='"+activityID+"'>"+activities.trim()+"</option>";
						}
						
					document.getElementById("editactivityDIvID").innerHTML = array_element;	
				}
			};
			xmlhttp.open("GET", "RetrieveActivitiesName?subjectID="
				+ editsubjectID, true);
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
		
		/* For retrieving Subjects list with there standardID */
		
		function retrieveActivities(subjectID, functionCheck) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
						var array_element = "<select name='' id='activityID' class='form-control'"+
						"> <option value='-1'>Select Activity</option>";
						
						var activities = ""; 
						var activityID = 0;
						
						for ( var i = 0; i < array.Release.length; i++) {
							
							activities = array.Release[i].activity;
							activityID = array.Release[i].activityID;
							array_element += "<option value='"+activityID+"'>"+activities.trim()+"</option>";
						}
						
					document.getElementById("activityDIvID").innerHTML = array_element;	
					
				}
			};
			xmlhttp.open("GET", "RetrieveActivitiesName?subjectID="
				+ subjectID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>
    <script type="text/javascript">
    function addEditActivitiesRow(activityID, totalMarks, subjectID, editSubjectAssessmentID){
    	
    	if(activityID == "-1"){
			alert("Please select Activity.");
		}else if(totalMarks == ""){
			alert("Please add Total Marks.");
		}else{
	
			retrieveActivitiesName(activityID, totalMarks, subjectID, "Edit", editSubjectAssessmentID);
		}
    }
    
var divCounter1 = 1;
	
	function addEditActivitiesRow1(activityID, totalMarks, subjectID, editSubjectAssessmentID, activityName){

		var trID = "newDIvTRID"+divCounter1;
		
		var trTag = "";
		
		var stringToAppend = "*"+ editSubjectAssessmentID + "$" + activityID + "$" + totalMarks ;
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+activityName+"<input type='hidden' name='newactivityID' value='"+activityID+"'></td>"
			  + "<td style='text-align:center;'>"+totalMarks+"<input type='hidden' name='newtotalMarks' value='"+totalMarks+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + stringToAppend + "\",\"" + trID + "\");'/></td>"
			  + "<tr>";
			  
		$(trTag).insertAfter($('#editConfigTRID'));
		
		$('#confID').val($('#confID').val()+stringToAppend);
		//appending values to editConf 
		
		divCounter++;
		
		$("#editactivityID").val("-1");
		$("#edittotalMarksID").val("");
	} 
	
	function removeDivTR(stringToBeRemoved, trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
    		var configText = $('#confID').val();
    		var newValue = configText.replace(stringToBeRemoved,'');
        	
        	//Updating new value to dummySettingTextID field
        	$('#confID').val(newValue);
        	
        	$("#"+trID+"").remove();
        	
		}
    }
    </script>
    
    <script type="text/javascript">
    
    	function removeactivityAssessmentRow(TRID, activityAssessmentID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeactivityAssessmentRow1(TRID, activityAssessmentID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeactivityAssessmentRow1(TRID, activityAssessmentID){ 
	    	
	    	xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//alert(AYClassID);
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
						}
	
						
						if(check == 0){
							alert("Exception occurred while removing row. Please check server logs for more details.");
						}else{
							$("#"+TRID+"").remove();
							alert("Row removed successfully.");
						}
						
				}
			};
			xmlhttp.open("GET", "RemoveactivityAssessmentRow?activityAssessmentID="
					+ activityAssessmentID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
	    	
		}
    
    </script>
    <!--End -->
     <script type="text/javascript">
   
	function addActivitiesRow(activityID, totalMarks, subjectID){
		
		if(activityID == "-1"){
			alert("Please select activity.");
		}else if(totalMarks == ""){
			alert("Please add total marks.");
		}else{
			
			retrieveActivitiesName(activityID, totalMarks, subjectID, "Add", "");
		}
	}
    
    var divCounter = 1;
	
	function addActivitiesRow1(activityID, totalMarks, subjectID, activityName){

		
		var trID = "newDIvTRID"+divCounter;
		
		var trTag = "";
		
		var stringToAppend = "*" + subjectID + "$" + activityID + "$" + totalMarks ;
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+activityName+"<input type='hidden' name='newactivityID' value='"+activityID+"'></td>"
			  + "<td style='text-align:center;'>"+totalMarks+"<input type='hidden' name='newtotalMarks' value='"+totalMarks+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + stringToAppend + "\",\"" + trID + "\");'/></td>"
			  + "<tr>";
			  
		$(trTag).insertAfter($('#activitiesTRID'));
		
		//appending values to editConf 
		$('#confID').val($('#confID').val()+stringToAppend);
		divCounter++;
		
		$("#activityID").val("-1");
		$("#totalMarksID").val("");
	} 
	
	function removeDivTR(stringToBeRemoved, trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
    		var configText = $('#confID').val();
    		var newValue = configText.replace(stringToBeRemoved,'');
        	
        	//Updating new value to dummySettingTextID field
        	$('#confID').val(newValue);
        	
        	$("#"+trID+"").remove();
        	
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
	
		function retrieveActivitiesName(activityID, totalMarks, subjectID, functionCheck, editSubjectAssessmentID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var activityName = "";
	
					for ( var i = 0; i < array.Release.length; i++) {

						activityName = array.Release[i].activity;
					}
					
					if(functionCheck == "Add"){
						addActivitiesRow1(activityID, totalMarks, subjectID, activityName);
					}else{
						addEditActivitiesRow1(activityID, totalMarks, subjectID, editSubjectAssessmentID, activityName);
					}
				}
			};
			xmlhttp.open("GET", "RetrieveActivityNameByactivityID?activityID="
					+ activityID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
	</script>
	
</head>

<body>

<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<!-- Modal to add Standard attendance  -->
<% if(loginform.getMedium().equals("mr")){ %>
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				उपक्रम व्यवस्थापन			
			</div>
			<div class="modal-body">
				<table border="1" id="tableTRID">
					<tr>
						<td>उपक्रम</td>
						<td>एकूण गुण</td>
						<td>कृती</td>
					</tr>
					<tr id="activitiesTRID">
					    <td >
					    <input type="hidden" name="subjectID" id ="ActiveID">
					        <div class="form-group" id="activityDIvID">
								<select class="form-control" id="activityID">
								    <option value="-1">उपक्रम निवडा</option>
							     </select>
							</div>
					    </td>
					    <td style="padding: 10px;">
					        <div class="form-group" >
						  		<input type='number' name='' class='form-control' id = 'totalMarksID'>
							</div>
					   </td>
					   <td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="उपक्रम ऍड  करा"
									title="उपक्रम ऍड  करा" onclick="addActivitiesRow(activityID.value, totalMarksID.value, ActiveID.value);"/>
					  </td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal">बंद करा</button>
			</div>
		</div>
	</div>
	</div>	
	<!-- End -->
	
	<!-- MOdal to add new subject teacher and view existing subject teacher configuration -->
	<div id="addViewConfigurationModalID" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				उपक्रम व्यवस्थापन एडिट करा					
			</div>
			<div class="modal-body" align="center">
				<table border="1" id="editConfigTableID">
					<thead>
						<tr>
							<td>उपक्रम</td>
							<td>एकूण गुण</td>
							<td>कृती</td>
						</tr>
					</thead>
					<tbody>
						<tr id="editConfigTRID">
						     <td >
					    <input type="hidden" name="subjectID" id ="editActiveID">
					    <input type="hidden" name="subjectAssessmentID" id ="editsubActiveID"> 
					        <div class="form-group" id="editactivityDIvID">
								<select class="form-control" id="editactivityID">
								    <option value="-1">उपक्रम निवडा</option>
							     </select>
							</div>
					    </td>
					    <td style="padding: 10px;">
					        <div class="form-group" >
						  		<input type='number' name='' class='form-control' id = 'edittotalMarksID'>
							</div>
						</td>
					   
					    <td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
	  						onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="उपक्रम ऍड  करा"
								title="उपक्रम ऍड  करा" onclick="addEditActivitiesRow(editactivityID.value, edittotalMarksID.value, editActiveID.value, editsubActiveID.value);"/>
						</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal">बंद करा</button>
			</div>
		</div>
	</div>
	</div>
	<% }else{ %>
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				Activities Table			
			</div>
			<div class="modal-body">
				<table border="1" id="tableTRID">
					<tr>
						<td>Activities</td>
						<td>Total Marks</td>
						<td>Action</td>
					</tr>
					<tr id="activitiesTRID">
					    <td >
					    <input type="hidden" name="subjectID" id ="ActiveID">
					        <div class="form-group" id="activityDIvID">
								<select class="form-control" id="activityID">
								    <option value="-1">Select Activity</option>
							     </select>
							</div>
					    </td>
					    <td style="padding: 10px;">
					        <div class="form-group" >
						  		<input type='number' name='' class='form-control' id = 'totalMarksID'>
							</div>
					   </td>
					   <td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Activities"
									title="Add Activities" onclick="addActivitiesRow(activityID.value, totalMarksID.value, ActiveID.value);"/>
					  </td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
	</div>	
	<!-- End -->
	
	<!-- MOdal to add new subject teacher and view existing subject teacher configuration -->
	<div id="addViewConfigurationModalID" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				Edit Activities Table				
			</div>
			<div class="modal-body" align="center">
				<table border="1" id="editConfigTableID">
					<thead>
						<tr>
							<td>Activities</td>
							<td>Total Marks</td>
							<td>Action</td>
						</tr>
					</thead>
					<tbody>
						<tr id="editConfigTRID">
						     <td >
					    <input type="hidden" name="subjectID" id ="editActiveID">
					    <input type="hidden" name="subjectAssessmentID" id ="editsubActiveID"> 
					        <div class="form-group" id="editactivityDIvID">
								<select class="form-control" id="editactivityID">
								    <option value="-1">Select Activity</option>
							     </select>
							</div>
					    </td>
					    <td style="padding: 10px;">
					        <div class="form-group" >
						  		<input type='number' name='' class='form-control' id = 'edittotalMarksID'>
							</div>
						</td>
					   
					    <td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
	  						onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
								title="Add Activities" onclick="addEditActivitiesRow(editactivityID.value, edittotalMarksID.value, editActiveID.value, editsubActiveID.value);"/>
						</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
	</div>
	
<% } %>
	<!-- End -->
	
<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>विषय मूल्यमापन कॉन्फीग्युर करा<% }else{ %>Configure Subject Assessment <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>विषय मूल्यमापन <% }else{ %>Subject Assessment <% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
              <% if(loginform.getMedium().equals("mr")){ %>विषय मूल्यमापन माहिती  <% }else{ %>Subject Assessment Details <% } %> 
              </h3>

            </div> <!-- /.portlet-header -->

            <div class="portlet-content">
            
            <div style="margin-top:15px;">
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
			 
            <form id="loadSubjectFOrm" action="ConfigureSubjectAssessment" method="POST" data-validate="parsley" class="form parsley-form">
				
				 <div class="row">
				 <% if(loginform.getMedium().equals("mr")){ %> 

					 <div class="col-md-2">
	                	<s:select list=" #{'Term I':'टर्म १','Term II':'टर्म २'}" class="form-control" headerKey="-1" id="termID" headerValue="टर्म निवडा"  name="term" onchange="retrieveExamList(this.value);" ></s:select>
					</div>
					
				  	<div class="col-md-2">
				  		<%
							if(loadSubjectSearch.equals("Enabled")){
							
						%>
						<s:select list="ExamList" class="form-control" headerKey="-1" id="examID" headerValue="परीक्षा  निवडा " name="ExaminationID" ></s:select>
						<%
							}else{
						%>
						<select name="ExaminationID" id="examID" class="form-control">
			               	<option value="-1">परीक्षा  निवडा</option>
			             </select>
			             <%
							}
						%>
					</div>
					
					<div class="col-md-2">
						<s:select list="StandardList" class="form-control" headerKey="-1"  headerValue="इयत्ता निवडा"  name="standardID" id="SID" onchange="retrieveDivision(this.value);"></s:select>
					</div>
					
					<div class="col-md-2" >
					
						<%
							if(loadSubjectSearch.equals("Enabled")){
							
						%>
						<s:select list="DivisionList" name="divisionID" id="stdDivID" class="form-control" headerKey="-1" headerValue="तुकडी निवडा "></s:select>
						<%
							}else{
						%>
						<select name="divisionID" id="stdDivID" class="form-control">
			               	<option value="-1">तुकडी निवडा</option>
			             </select>
						<%
							}
						%>
			             
		            </div>
					
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" onclick="return checkStandard(SID.value, stdDivID.value, examID.value);">विषय  पहा</button>
					</div>
				 <% }else{ %> 
			
					 <div class="col-md-2">
	                	<s:select list=" #{'Term I':'Term I','Term II':'Term II'}" class="form-control" headerKey="-1" id="termID" headerValue="Select Term"  name="term" onchange="retrieveExamList(this.value);" ></s:select>
					</div>
					
				  	<div class="col-md-2">
				  		<%
							if(loadSubjectSearch.equals("Enabled")){
							
						%>
						<s:select list="ExamList" class="form-control" headerKey="-1" id="examID" headerValue="Select Exam" name="ExaminationID" ></s:select>
						<%
							}else{
						%>
						<select name="ExaminationID" id="examID" class="form-control">
			               	<option value="-1">Select Exam</option>
			             </select>
			             <%
							}
						%>
					</div>
					
					<div class="col-md-2">
						<s:select list="StandardList" class="form-control" headerKey="-1"  headerValue="Select Standard"  name="standardID" id="SID" onchange="retrieveDivision(this.value);"></s:select>
					</div>
					
					<div class="col-md-2" >
					
						<%
							if(loadSubjectSearch.equals("Enabled")){
							
						%>
						<s:select list="DivisionList" name="divisionID" id="stdDivID" class="form-control" headerKey="-1" headerValue="Select Division"></s:select>
						<%
							}else{
						%>
						<select name="divisionID" id="stdDivID" class="form-control">
			               	<option value="-1">Select Division</option>
			             </select>
						<%
							}
						%>
			             
		            </div>
					
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" onclick="return checkStandard(SID.value, stdDivID.value, examID.value);">Load Subjects</button>
					</div>
				
				 <% } %>
				 
				</div>
			
			<div class="tab-pane fade in active">
				
				<%
					if(loadSubjectSearch.equals("Enabled")){
				%>
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          <input type="hidden" id="confID" name = "activityString">
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                        
	                          <th><% if(loginform.getMedium().equals("mr")){ %> विषय <% }else{ %> Subject <% } %></th>
	                          
	                          <th><% if(loginform.getMedium().equals("mr")){ %> ग्रेड-आधारित <% }else{ %> Grade Based <% } %></th>
	                          
	                          <th><% if(loginform.getMedium().equals("mr")){ %> जास्तीत जास्त गुण <% }else{ %> Maximum Marks <% } %></th>
	                          
	                          <th><% if(loginform.getMedium().equals("mr")){ %> Scale to <% }else{ %> Scale to <% } %></th>
	                          
	                          <!-- <th>Grade</th> -->
	                          
	                        </tr>
	                      </thead>
	                      <tbody>
	                      
		                  <%--  <s:iterator value="subjectNewList" > --%>
		                   <% for(ConfigurationForm form:subjectNewList){ %>
		                   	<tr>
		                   	
		                   	<%
		                   
		                   	if(form.getSubject().equals("Computer") || form.getSubjectType().equals("Co-scholastic")){ %>
		                   		
		                   		<td><a style="text-decoration: underline;" href="javascript:activityMarks('<%=form.getSubjectID() %>');"><%=form.getSubject() %></a>
		                   			<input type="hidden" name="newSubjectName" value="<%=form.getSubject() %>">
		                   			<input type="hidden" name="newSubjectID" value="<%=form.getSubjectID() %>">
		                   		</td>
		                   		
		                   	<%}else{ %>	 
		                   	
		                   		<td><%=form.getSubject() %>
		                   			<input type="hidden" name="newSubjectName" value="<%=form.getSubject() %>">
		                   			<input type="hidden" name="newSubjectID" value="<%=form.getSubjectID() %>">
		                   		</td>
		                   	<%} %>
		                   		<td>
		                   		<div>
			                   		<input type="radio" name="radioBtn<%=form.getSubjAssmntID() %>"  value="1" 
			                   		onchange="disable(this.value, 'MaxMarkID<%=form.getSubjAssmntID() %>','ScaleID<%=form.getSubjAssmntID() %>');" 
			                   		onclick="storeValue(this.value,'newGradeBaseID<%=form.getSubjAssmntID() %>');" />Yes
	    							<input type="radio" name="radioBtn<%=form.getSubjAssmntID() %>"  value="0" 
	    							onchange="disable(this.value, 'MaxMarkID<%=form.getSubjAssmntID() %>','ScaleID<%=form.getSubjAssmntID() %>');" 
	    							onclick="storeValue(this.value,'newGradeBaseID<%=form.getSubjAssmntID() %>');" />No
	    							<input type="hidden" name="newGradeBase" id="newGradeBaseID<%=form.getSubjAssmntID() %>"> 
		                   		</div>	
		                   		
								</td>
		                   		
		                 		<td>
		                   			<input type="number" class='form-control' id='MaxMarkID<%=form.getSubjAssmntID() %>' name='newMaximumMark'>
		                   		</td>
		                   		
								<td>
									<input type='number' class='form-control' id='ScaleID<%=form.getSubjAssmntID() %>' name='newScaleTo' >
								</td>
		                   		
		 					</tr>
		                   <%} %>
		                   
		                   <% 
		                   	//System.out.println("..."+subjectAssessmentListNew.size());
		                   		for(ConfigurationForm form:subjectAssessmentListNew){ 
		                   %>
		                   
		                  <tr>
		                   	
		                   	<% if(form.getSubject().equals("Computer") || form.getSubjectType().equals("Co-scholastic")){ %>
		                   		
			                   	<td><a style="text-decoration: underline;" href="javascript:editActivityMarks('<%=form.getSubjectID() %>','<%=form.getSubjectAssessmentID()%>');"><%=form.getSubject() %></a>
			                   		<input type="hidden" name="editSubjectAssessmentID" value="<%=form.getSubjectAssessmentID()%>">
			                   		<input type="hidden" name="editSubjectName" value="<%=form.getSubject()%>">
			                   		<input type="hidden" name="editSubjectID" value="<%=form.getSubjectID()%>">
			                   	</td>
		                   		
		                   	<%}else{ %>	 
		                   	
		                   		<td>
			                   		<%=form.getSubject()%><input type="hidden" name="editSubjectAssessmentID" value="<%=form.getSubjectAssessmentID()%>">
			                   		<input type="hidden" name="editSubjectName" value="<%=form.getSubject()%>">
			                   		<input type="hidden" name="editSubjectID" value="<%=form.getSubjectID()%>">
		                   		</td>
		                   	<%} %>
		                   	
		                   	<%
		                   		//System.out.println("...."+form.getGradeBased());
		                   		if(form.getGradeBased() == 1){
		                   		//System.out.println("inside...");
		                   	%>
		                   	<td>
		                   		<div>
		                   			<input type="radio" name="editgradeBased<%=form.getSubjectAssessmentID()%>"  checked="checked" value="1" 
		                   			onchange="restoreValue(this.value, 'editMaxMarkID<%=form.getSubjectAssessmentID()%>', 'editSceleToID<%=form.getSubjectAssessmentID()%>');" 
		                   			onclick="storeValue(this.value, 'editGradeBaseID<%=form.getSubjectAssessmentID()%>');" />Yes
		    						<input type="radio" name="editgradeBased<%=form.getSubjectAssessmentID()%>"  value="0" 
		    						onchange="restoreValue(this.value, 'editMaxMarkID<%=form.getSubjectAssessmentID()%>', 'editSceleToID<%=form.getSubjectAssessmentID()%>');" 
		    						onclick="storeValue(this.value, 'editGradeBaseID<%=form.getSubjectAssessmentID()%>');" />No
		    						
		    						<input type="hidden" name="editGradeBase" value="<%=form.getGradeBased()%>" id="editGradeBaseID<%=form.getSubjectAssessmentID()%>">
								</div>
							</td>
							
							<td>
		                   		<input type='number' readonly="readonly" class='form-control' id='editMaxMarkID<%=form.getSubjectAssessmentID()%>' name='editMaximumMark' value="<%=form.getTotalMarks()%>" >
		                   	</td>
		                   		
							<td>
								<input type='number' readonly="readonly" class='form-control' id='editSceleToID<%=form.getSubjectAssessmentID()%>' name='editScaleTo' value="<%=form.getScaleTo()%>">
							</td>
		                   			<%
		                   				}else{
		                   			%>
		                   	<td>
		                   		<div>
		                   			<input type="radio" name="editgradeBased<%=form.getSubjectAssessmentID()%>" value="1" 
		                   			onchange="restoreValue(this.value, 'editMaxMarkID<%=form.getSubjectAssessmentID()%>', 'editSceleToID<%=form.getSubjectAssessmentID()%>');"
		                   			onclick="storeValue(this.value, 'editGradeBaseID<%=form.getSubjectAssessmentID()%>');" />Yes
		    						<input type="radio" name="editgradeBased<%=form.getSubjectAssessmentID()%>" checked="checked" value="0" 
		    						onchange="restoreValue(this.value, 'editMaxMarkID<%=form.getSubjectAssessmentID()%>', 'editSceleToID<%=form.getSubjectAssessmentID()%>');"
		    						onclick="storeValue(this.value, 'editGradeBaseID<%=form.getSubjectAssessmentID()%>');" />No
		    						
		    						<input type="hidden" name="editGradeBase" value="<%=form.getGradeBased()%>" id="editGradeBaseID<%=form.getSubjectAssessmentID()%>">
		                   		</div>
		                   	</td>
		                   	
		                   	<td>
		                   		<input type='number' class='form-control' id='editMaxMarkID<%=form.getSubjectAssessmentID()%>' name='editMaximumMark' value="<%=form.getTotalMarks()%>" >
		                   	</td>
		                   		
							<td>
								<input type='number' class='form-control' id='editSceleToID<%=form.getSubjectAssessmentID()%>' name='editScaleTo' value="<%=form.getScaleTo()%>">
							</td>
		    			<%
		                   	}
		    			%>
		                 </tr>
		                <% } %>
	                      
	                      </tbody>
	                    </table>
		                
			        </div>
			        
			    <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default"><% if(loginform.getMedium().equals("mr")){ %> रद्द करा <% }else{ %> Cancel <% } %></button>
                  <button type="submit" class="btn btn-success" id="submitID" onclick="submitForm('loadSubjectFOrm', 'submitID', 'AddSubjectAssessment');"><% if(loginform.getMedium().equals("mr")){ %> सेव करा <% }else{ %> Save <% } %></button>
                </div>
			       <%
						}
			       %>
				 </div>
				 </form>
                
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
