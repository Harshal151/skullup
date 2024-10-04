<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
     <%@page import="java.util.HashMap"%>
     <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="java.util.List"%>
     <%@page import="com.kovidRMS.form.LoginForm"%>
     <%@page import="com.kovidRMS.form.ConfigurationForm"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
  
   <%
     	LoginForm loginform = (LoginForm) session.getAttribute("USER");
     
    	String componentMsg = (String) request.getAttribute("componentMsg");
    	if(componentMsg == null || componentMsg == ""){
    		componentMsg = "dummy";
    	}
    	
    	String componentEdit = (String) request.getAttribute("componentEdit");
    	if(componentEdit == null || componentEdit == ""){
    		componentEdit = "add";
    	}
  
		LoginDAOInf daoinf = new LoginDAOImpl();
    	
		String AcademicYearName = daoinf.retrieveAcademicYearName(loginform.getOrganizationID());
    	HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	HashMap<Integer, String> AcademicYearNameList = daoinf.getAcademicYearNameList(loginform.getOrganizationID());
    	HashMap<Integer, String> AppuserList = daoinf.getAppUser(loginform.getOrganizationID());
    	StuduntDAOInf daoinf1 = new StudentDAOImpl();
    	List<String> subjectList = daoinf1.retrievesubjectList(loginform.getOrganizationID());
  
    	List<ConfigurationForm> AcademicYearClassList = (List<ConfigurationForm>) request.getAttribute("AcademicYearClassList");
    
	    String AcademicYear = (String) request.getAttribute("AcademicYear");
		
		if(AcademicYear == null || AcademicYear == ""){
			AcademicYear = "dummy";
		}
    %>
    
  <title><% if(loginform.getMedium().equals("mr")){ %>शैक्षणिक वर्ष कॉन्फीग्युर करा - SkoolUp <% }else{ %>Configure Academic Year - SkoolUp<% } %></title>
  
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

  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
  

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
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
	
	function submitForm(subID){
  	 	 $("#loadStudentFOrm").attr("action","ConfigureAcademicYear");
	  	 $("#loadStudentFOrm").submit(); 
	  	  
 	 	 $("#"+subID).attr("disabled", "disabled");
 	 	 
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
    	
 	 	return true;
   }
	
	function windowOpen1(){
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	    }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="RenderconfigureAcademicYear";
     }
	
	function loadAcademicYear(academicYearID){
		
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
		
		document.location="RenderLoadAcademicYear?academicYearID="+ academicYearID;
		
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
		
	function addConfigRow(subject,teacher1){
		
		if(subject == "-1"){
			alert("Please select Subject.");
		}else if(teacher1 == "-1"){
			alert("Please select Teacher.");
		}else{
	
			retrieveTeacher1NameByTeacherID(subject,teacher1, "Add","");
		}
	}
    
    var divCounter = 1;
	
	function addConfigRow1(subject,subjectName,teacher1,teacher1Name){
		
		var trID = "newDIvTRID"+divCounter;
		
		var trTag = "";
		
		var stringToAppend = "*" + subject + "$" + subjectName + "$" + teacher1 + "$" + teacher1Name;
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+subjectName+"<input type='hidden' name='newsubject' value='"+subject+"'></td>"
			  + "<td style='text-align:center;'>"+teacher1Name+"<input type='hidden' name='newteacher1' value='"+teacher1+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + stringToAppend + "\",\"" + trID + "\");'/></td>"
			  + "<tr>";
			  
		$(trTag).insertAfter($('#divisionTRID'));
				
		//appending values to editConf 
		$('#confID').val($('#confID').val()+stringToAppend);
		
		divCounter++;
		
		$("#subDivID").val("-1");
		$("#teachID").val("-1");
		
		//unchecking all check boxes from subject teacher dropdown
		$('input:checkbox').removeAttr('checked');
		
		//Removing class active in order to remove highlited area
		$(".multiselect-container").find("li").removeClass("active");
		
		$(".multiselect-selected-text").html("None selected");
		
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
	
    var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	//standardID.value, stdDivID.value, teacherID.value, confID.value
	function addConfig(){
		
		$("#tableTRID").find("tr:gt(1)").remove();
  		
  		var standard = $("#standardID").val();
  		
  		var DivisionID = $("#stdDivID").val();
  		
  		var TeacherID = $("#teacherID").val();
  		
  		if(standard == "-1"){
  			alert('Please select Standard name.');
  		}else if(DivisionID == "-1"){
  			alert('Please select Division name.');
  		}else if(TeacherID == "-1"){
  			alert('Please select Teacher name.');
  		}else{
  			$("#myModal").modal('show');
  		}
  	}
    	
		function retrieveDivision(standardID){
			
			if(standardID == "-1"){
				alert("No standard is selected. Please select standard.");
				
				var array_element = "<select name='' id='' class='form-control'"+
				"> <option value='-1'>Select Division</option></select>";
				
				document.getElementById("stdDivID").innerHTML = array_element;
				
				/* For Subject...*/
				var array_element1 = "<select name='' id='' class='form-control'"+
				"> <option value='-1'>Select Subject</option></select>";
				
				document.getElementById("subDivID").innerHTML = array_element;
			}else{
				retrieveDivision1(standardID);
			}
			
		}
	
		function retrieveDivision1(standardID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "<select name='division' class='form-control'"+
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
							
							var array_element = "<select name='' id='' class='form-control'"+
							"> <option value='-1'>Select Division</option></select>";
							
							document.getElementById("stdDivID").innerHTML = array_element;
							
						}else{
							
							document.getElementById("stdDivID").innerHTML = array_element;	
														
						}
						
						retrieveSubjectByStandardID(standardID);
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
		
		/* For retrieving Subjects list with there standardID */
		
		function retrieveSubjectByStandardID(standardID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
						var array_element = "<select name='' id='subDivID' class='form-control'"+
						"> <option value='-1'>Select Subject</option>";
						
						var subjectList = ""; 
						var subjectID = 0;
						
						for ( var i = 0; i < array.Release.length; i++) {
							
							subjectList = array.Release[i].subjectList;
							subjectID = array.Release[i].subjectID;
							array_element += "<option value='"+subjectID+"'>"+subjectList.trim()+"</option>";
						}
						
					document.getElementById("subjectDIvID").innerHTML = array_element;	
				}
			};
			xmlhttp.open("GET", "RetrieveSubjectListForStandard?standardID="
					+ standardID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
    </script>
   
   <script type="text/javascript">
   var academicYearCounter = 1;
	
	function addAcademicYearRow(editStandard, editDivision, editTeacher, editConf){
		
		if(editStandard == "-1"){
			alert("Please select Standard.");
		}else if(editDivision == ""){
			alert("Please select Division.");
		}else if(editTeacher == "-1"){
			alert("Please select Teacher.");
		}else{
			//addAcademicYearRow1(editStandard, editDivision, editTeacher, editConf);
			retrieveStandardNameByStandardID(editStandard, editDivision, editTeacher, editConf);
		}
	}
	
	function addAcademicYearRow1(editStandard, editDivision, editTeacher, editConf, standardName, divisionName, teacherName){
		
		var trID = "newExamTRID"+academicYearCounter;
		
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+standardName+"<input type='hidden' name='newStandard' value='"+editStandard+"'></td>"
			  + "<td style='text-align:center;'>"+divisionName+"<input type='hidden' name='newDivision' value='"+editDivision+"'></td>"
			  + "<td style='text-align:center;'>"+teacherName+"<input type='hidden' name='newTeacher' value='"+editTeacher+"'></td>"
			  + "<td style='text-align:center;'><a href='javascript:viewConfiguration(\"" + editConf + "\")'>View Configuration</a><input type='hidden' name='newConfID' value='"+editConf+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeAcademicYearTR(\"" + trID + "\");'/></td>"
			  + "</tr>";
		
		$(trTag).insertAfter($('#standardTRID'));
		academicYearCounter++;
		
		$("#standardID").val("-1");
		$("#stdDivID").val("-1");
		$("#teacherID").val("-1");
		$("#confID").val("");
		
	}
	
	function removeAcademicYearTR(trID){
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
	
		function retrieveStandardNameByStandardID(editStandard, editDivision, editTeacher, editConf) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var standardName = "";
	
					for ( var i = 0; i < array.Release.length; i++) {

						standardName = array.Release[i].standard;
						
					}
					
					retrieveDivisionNameByDivisionID(editStandard, editDivision, editTeacher, editConf, standardName);
					
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
		
		/*retreiving Division Name  */
		
		function retrieveDivisionNameByDivisionID(editStandard, editDivision, editTeacher, editConf, standardName){
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var divisionName = "";
	
					for ( var i = 0; i < array.Release.length; i++) {

						divisionName = array.Release[i].division;
						
					}
					
					retrieveTeacherNameByTeacherID(editStandard, editDivision, editTeacher, editConf, standardName,divisionName);
					
				}
			};
			xmlhttp.open("GET", "RetrieveDivisionName?divisionID="
					+ editDivision, true);
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
		
	/*retreiving Teacher Name  */
		
		function retrieveTeacherNameByTeacherID(editStandard, editDivision, editTeacher, editConf, standardName,divisionName){
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var teacherName = "";
	
					for ( var i = 0; i < array.Release.length; i++) {

						teacherName = array.Release[i].fullName;
						
					}
					
					addAcademicYearRow1(editStandard, editDivision, editTeacher, editConf, standardName, divisionName, teacherName);
					
				}
			};
			xmlhttp.open("GET", "RetrieveTeacherName?teacherID="
					+ editTeacher, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
   </script>
   
    <!-- retrieving Subject Teacher name from SubjectID -->
   <script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveTeacher1NameByTeacherID(subject,teacher1,functionCheck,configHiddenTextID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var teacher1Name = "";
	
					for ( var i = 0; i < array.Release.length; i++) {

						teacher1Name = array.Release[i].fullName;
						
					}
					
					retrieveSubjectNameBySubjectID(subject,teacher1,teacher1Name,functionCheck,configHiddenTextID);
					
				}
			};
			xmlhttp.open("GET", "RetrieveSubjectTeacherName?teacherNameID="
					+ teacher1, true);
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
	
		function retrieveSubjectNameBySubjectID(subject,teacher1,teacher1Name,functionCheck,configHiddenTextID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var subjectName = "";
	
					for ( var i = 0; i < array.Release.length; i++) {

						subjectName = array.Release[i].name;
						
					}
					
					if(functionCheck == "Add"){
						addConfigRow1(subject,subjectName,teacher1,teacher1Name);
					}else{
						addEditConfigRow1(subject,teacher1,configHiddenTextID,teacher1Name,subjectName);
					}
					
				}
			};
			xmlhttp.open("GET", "RetrieveSubjectName?subjectID="
					+ subject, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
		</script>
   
    <!-- function to view div list -->
    <script type="text/javascript">
    	function viewConfiguration(confText){
    		
    		//check wther division string starts with *, if so,
    		//remove first * and proceed further
    		if(confText.startsWith("*")){
    			confText = confText.substr(1);
    		}
    		
    		//check whether division string contains * after removing 
    		//first *, if so, then split division string by * and display
    		// one by one divisions else display only single division
    		if(confText.includes("*")){
    			
    			var subjectArr = confText.split("*");
    			
    			var tableTag = "<table border='1' style='font-size: 14px;'>"
    						 + "<tr style='padding:5px;'><th>Subject</th><th>Teacher</th></tr>";
    						 
    				for(var i = 0; i < subjectArr.length; i++){
    					
    					var array = subjectArr[i].split("$");
    					
    					tableTag += "<tr><td style='padding:5px;'>"+array[1]+"</td>";
    					tableTag += "<td style='padding:5px;'>"+array[3]+"</td></tr>";
    					
    				}
    				
    				tableTag += "</table>";
    		}else{
    			
    			var array = confText.split("$");
    			
    			var tableTag = "<table border='1' style='font-size: 14px;'>"
					 + "<tr style='padding:5px;'><th>Subject</th><th>Teacher</th></tr>"
					 + "<tr><td style='padding:5px;'>"+array[1]+"</td><td style='padding:5px;'>"+array[3]+"</td></tr></table>";
    			
    		}
    		
    		$("#divTableID").html(tableTag);
    		
    		$("#viewDivModalID").modal("show");
    		
    	}
    	
    </script>
    <!-- Ends -->
    
    <!-- Retrieve division list based on standard ID -->
    <script type="text/javascript">
    
    var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	 function retrieveDivisionByStandard(divTDID, standardID){
		 if(standardID == "-1"){
			 var array_element = "<select name='editDivisionID' id='' class='form-control'"+
				"> <option value='-1'>Select Division</option></select>";
			
			document.getElementById(divTDID).innerHTML = array_element;
				
		 }else{
			 retrieveDivisionByStandard1(divTDID, standardID);
		 }
	 }
    
    function retrieveDivisionByStandard1(divTDID, standardID) {
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "<select name='editDivisionID' class='form-control'"+
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
						
						var array_element = "<select name='editDivisionID' id='' class='form-control'"+
						"> <option value='-1'>Select Division</option></select>";
						
						document.getElementById(divTDID).innerHTML = array_element;
						
					}else{
						
						document.getElementById(divTDID).innerHTML = array_element;	
													
					}
					//retrieveSubjectByStandardID1(standardID);
			}
		};
		xmlhttp.open("GET", "RetrieveDivisionListForStandard?standardID="
				+ standardID, true);
		xmlhttp.setRequestHeader("ContentaddConfigRow1-type", "charset=UTF-8");
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
	
	function retrieveSubjectByStandardID1(standardID) {

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
						array_element += "<option value='"+subjectID+"'>"+subjectList.trim()+"</option>";
						
					}
				document.getElementById("editSubjectDivID").innerHTML = array_element;	
			}
		};
		xmlhttp.open("GET", "RetrieveSubjectListForStandard?standardID="
				+ standardID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
    
    </script>
    <!-- End -->
    
    <!--View Configuration Edit list from database by AYClssID  -->
    <script type="text/javascript">
    
    function viewEditConfiguration(AYClassID, configHiddenTextID, standardID){
    	//removing all TR except first tr from tbody
    	$("#editConfigTableID").find("tr:gt(1)").remove();
    	
    	//retrieving subject list based on standardID
    	viewEditConfiguration1(AYClassID, configHiddenTextID, standardID);
    }
    
    var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
    
    function viewEditConfiguration1(AYClassID, configHiddenTextID, standardID){ 
    	
    	xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(AYClassID);
				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "";
					
					var check = 0;
					/* For division */
					for ( var i = 0; i < array.Release.length; i++) {
						
						check = array.Release[i].check;
						var subject = array.Release[i].subject;
						//var teacherID = array.Release[i].teacherID;
						var aySubjectID = array.Release[i].aySubjectID;
						var teacherName = array.Release[i].teacherName;
						
						var trID = "aySubTRID"+aySubjectID;
						
						array_element += "<tr id='"+trID+"' style='font-size: 14px;'>"
									   + "<td style='text-align:center'>"+subject+"</td>"
									   + "<td style='text-align:center'>"+teacherName+"</td>"
									   + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeAYSUbjectRow(\"" + trID + "\",\"" + aySubjectID + "\");'/></td>"
									   + "</tr>";
					}

					if(check == 0){
						
						$("#hiddenTextIDVal").val(configHiddenTextID);
						$("#addViewConfigurationModalID").modal("show");
						
					}else{
						
						$(array_element).insertAfter($("#editConfigTRID"));
						$("#hiddenTextIDVal").val(configHiddenTextID);
						
						$("#addViewConfigurationModalID").modal("show");			
					}
					
					//retrieving sbuject list based on standardID
					retrieveSubjectByStandardID1(standardID);
			}
		};
		xmlhttp.open("GET", "RetrieveSubTeacherListByAYClassID?AYClassID="
				+ AYClassID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
    	
	}
    
    </script>
    <!-- ENd -->
    
    <!-- Function to remove AYSubject row based on AYSubjectID -->
    <script type="text/javascript">
    
    	function removeAYSUbjectRow(TRID, aySubjectID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeAYSUbjectRow1(TRID, aySubjectID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeAYSUbjectRow1(TRID, aySubjectID){ 
	    	
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
			xmlhttp.open("GET", "RemoveAYSubjectRow?aySubjectID="
					+ aySubjectID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
	    	
		}
    
    </script>
    <!--End -->
    
    <!-- Add edit configuration row -->
    
    <script type="text/javascript">
   
		function addEditConfigRow(subject,teacher1,configHiddenTextID){
			
			if(subject == "-1"){
				alert("Please select Subject.");
			}else if(teacher1 == "-1"){
				alert("Please select Teacher.");
			}else{
		
				retrieveTeacher1NameByTeacherID(subject,teacher1,"Edit",configHiddenTextID);
			}
		}
	    
	    var editDivCounter = 1;
		
		function addEditConfigRow1(subject,teacher1,configHiddenTextID,teacher1Name,subjectName){
			
			var trID = "newEditTDID"+editDivCounter;
			
			var trTag = "";
			
			var stringToAppend = "*" + subject + "$" + subjectName + "$" + teacher1 + "$" + teacher1Name;
			
			trTag += "<tr id='"+trID+"'>"
				  + "<td style='text-align:center;'>"+subjectName+"<input type='hidden' name='newsubject' value='"+subject+"'></td>"
				  + "<td style='text-align:center;'>"+teacher1Name+"<input type='hidden' name='newteacher1' value='"+teacher1+"'></td>"
				  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeEditConfigTR(\"" + stringToAppend + "\",\"" + trID + "\",\"" + configHiddenTextID + "\");'/></td>"
				  + "<tr>";
				  
			$(trTag).insertAfter($('#editConfigTRID'));
			
			console.log("...val..."+stringToAppend);
			console.log("...hiddenID..."+configHiddenTextID);
					
			//appending values to editConf 
			$("#"+configHiddenTextID+"").val($("#"+configHiddenTextID+"").val()+stringToAppend);
			//alert(configHiddenTextID);
			editDivCounter++;
			
			$("#editSubDivID").val("-1");
			$("#editTeachID").val("-1");
			
			//unchecking all check boxes from subject teacher dropdown
			$('input:checkbox').removeAttr('checked');
			
			//Removing class active in order to remove highlited area
			$(".multiselect-container").find("li").removeClass("active");
			
			$(".multiselect-selected-text").html("None selected");
			
		}
		
		function removeEditConfigTR(stringToBeRemoved, trID, configHiddenTextID){
			
			if(confirm("Are you sure you want to delete this row?")){
			
	    		var configText = $("#"+configHiddenTextID+"").val();
	    		var newValue = configText.replace(stringToBeRemoved,'');
	        	
	        	$("#"+configHiddenTextID+"").val(newValue);
	        	
	        	$("#"+trID+"").remove();
	        }
	    }
    
   </script>
    
    <!-- Row -->
 
  <script type="text/javascript">
    	function storeValues(hiddenInputID, selectID){
    		var finalValue = "";
    		$("#"+selectID+" option:selected").each(function(){
    			finalValue += "," + $(this).val();
    			
    		});
    		
    		if(finalValue.startsWith(",")){
    			finalValue = finalValue.substr(1);
    		}
    		
    		$("#"+hiddenInputID).val(finalValue);
    	}
    	
    	function storeValues1(hiddenInputID, selectID){
    		var finalValue = "";
    		$("#"+selectID+" option:selected").each(function(){
    			finalValue += "," + $(this).val();
    			
    		});
    		
    		if(finalValue.startsWith(",")){
    			finalValue = finalValue.substr(1);
    		}
    		
    		$("#"+hiddenInputID).val(finalValue);
    	}
    </script>

<script type="text/javascript">

function maxAllowedMultiselect(obj, maxAllowedCount) {
	//alert('hiii');
	var val = jQuery('#'+obj.id).val();
	//alert(val);
    var selectedOptions = jQuery('#'+obj.id+" option[value!=\'\']:selected");
    if (selectedOptions.length >= maxAllowedCount) {
        if (selectedOptions.length > maxAllowedCount) {
            selectedOptions.each(function(i) {
                if (i >= maxAllowedCount) {
                    jQuery(this).prop("selected",false);
                }
            });
        }
        jQuery('#'+obj.id+' option[value!=\'\']').not(':selected').prop("disabled",true);
    } else {
        jQuery('#'+obj.id+' option[value!=\'\']').prop("disabled",false);
    }
   storeValues('teachID1','teachID');
}

function maxAllowedMultiselect1(obj, maxAllowedCount) {
	//alert('hiii');
    var selectedOptions = jQuery('#'+obj.id+" option[value!=\'\']:selected");
    if (selectedOptions.length >= maxAllowedCount) {
        if (selectedOptions.length > maxAllowedCount) {
            selectedOptions.each(function(i) {
                if (i >= maxAllowedCount) {
                    jQuery(this).prop("selected",false);
                }
            });
        }
        jQuery('#'+obj.id+' option[value!=\'\']').not(':selected').prop("disabled",true);
    } else {
        jQuery('#'+obj.id+' option[value!=\'\']').prop("disabled",false);
    }
    storeValues1('editTeachID1','editTeachID');
}

/* Multiselect Checkbox dropdown list*/
 $(document).ready(function(){
 	$(function () {
	 	//alert('hiii');
            $('#teachID').multiselect({
                includeSelectAllOption: true
            });
            
        });
        
   $(function () {
      $('#editTeachID').multiselect({
          includeSelectAllOption: true
      });
      
  }); 
 });
 
</script>
   
</head>

<body>
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<% if(loginform.getMedium().equals("mr")){ %>

	<!-- MOdal to add new subject teacher configuration -->
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header"> कॉन्फीग्युर करा	</div>
			<div class="modal-body">
				<table border="1" id="tableTRID">
					<tr>
						<td>विषय</td>
						<td>शिक्षक</td>
						<td>कृती</td>
					</tr>
					<tr id="divisionTRID">
					    <td >
					        <div class="form-group" id="subjectDIvID">
								<select class="form-control" id="subDivID">
								    <option value="-1">विषय निवडा</option>
							     </select>
							</div>
					    </td>
					    <td style="padding: 10px;">
					        <div class="form-group" >
					        <input type="hidden" id="teachID1" name="" >
					        
								<select  class="form-control" id="teachID" onchange="maxAllowedMultiselect(this, 2)" multiple="multiple">
								         <%
									         for(Integer AppuserName : AppuserList.keySet()){
									     %>
									 		<option value="<%=AppuserName%>"><%=AppuserList.get(AppuserName)%></option>
									         				
									      <%
									         }
									      %>
							    </select>
							</div>
					   </td>
					   <td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
  								  onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="ऍड कॉन्फीगुरेशन"
									title="ऍड कॉन्फीगुरेशन" onclick="addConfigRow(subDivID.value, teachID1.value);"/>
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
				कॉन्फीगुरेशन एडिट करा			
			</div>
			<div class="modal-body" align="center">
				<table border="1" id="editConfigTableID">
					<thead>
						<tr>
							<td>विषय</td>
							<td>शिक्षक</td>
							<td>कृती</td>
						</tr>
					</thead>
					<tbody>
						<tr id="editConfigTRID">
						    <td>
						        <div class="form-group" id="editSubjectDivID">
									<select class="form-control" id="editSubDivID">
									    <option value="-1">विषय निवडा</option>
								     </select>
								</div>
						    </td>
						   
						    <td style="padding: 10px;">
						        <div class="form-group" >
						        	<input type="hidden" id="editTeachID1" name="" >
									<select  class="form-control" id="editTeachID" onchange="maxAllowedMultiselect1(this, 2)" multiple>
									         <%
										         for(Integer AppuserName : AppuserList.keySet()){
										     %>
										 		<option value="<%=AppuserName%>"><%=AppuserList.get(AppuserName)%></option>
										         				
										      <%
										         }
										      %>
								    </select>
								</div>
								<input type="hidden" id="hiddenTextIDVal">	
						   	</td>
						   	<td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
	  								  onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="ऍड कॉन्फीगुरेशन"
										title="ऍड कॉन्फीगुरेशन" onclick="addEditConfigRow(editSubDivID.value, editTeachID1.value, hiddenTextIDVal.value);"/>
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
	<!-- End -->
	
	<div id="viewDivModalID" class="modal fade" tabindex="-1" role="diviewConfigurationalog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					कॉन्फीगुरेशन यादी				
				</div>
				<div class="modal-body">
					
					<div id="divTableID" align="center"></div>
					
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal">बंद करा</button>
				</div>
			</div>
		</div>
	</div>
	
<% }else{ %>

	<!-- MOdal to add new subject teacher configuration -->
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				Configuration				
			</div>
			<div class="modal-body">
				<table border="1" id="tableTRID">
					<tr>
						<td>Subject</td>
						<td>Teacher</td>
						<td>Action</td>
					</tr>
					<tr id="divisionTRID">
					    <td >
					        <div class="form-group" id="subjectDIvID">
								<select class="form-control" id="subDivID">
								    <option value="-1">Select Subject</option>
							     </select>
							</div>
					    </td>
					    <td style="padding: 10px;">
					        <div class="form-group" >
					        <input type="hidden" id="teachID1" name="" >
					        
								<select  class="form-control" id="teachID" onchange="maxAllowedMultiselect(this, 2)" multiple="multiple">
								         <%
									         for(Integer AppuserName : AppuserList.keySet()){
									     %>
									 		<option value="<%=AppuserName%>"><%=AppuserList.get(AppuserName)%></option>
									         				
									      <%
									         }
									      %>
							    </select>
							</div>
					   </td>
					   <td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
  								  onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
									title="Add Configuration" onclick="addConfigRow(subDivID.value, teachID1.value);"/>
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
				EDIT CONFIGURATION				
			</div>
			<div class="modal-body" align="center">
				<table border="1" id="editConfigTableID">
					<thead>
						<tr>
							<th>Subject</th>
							<th>Teacher</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<tr id="editConfigTRID">
						    <td>
						        <div class="form-group" id="editSubjectDivID">
									<select class="form-control" id="editSubDivID">
									    <option value="-1">Select Subject</option>
								     </select>
								</div>
						    </td>
						   
						    <td style="padding: 10px;">
						        <div class="form-group" >
						        	<input type="hidden" id="editTeachID1" name="" >
									<select  class="form-control" id="editTeachID" onchange="maxAllowedMultiselect1(this, 2)" multiple>
									         <%
										         for(Integer AppuserName : AppuserList.keySet()){
										     %>
										 		<option value="<%=AppuserName%>"><%=AppuserList.get(AppuserName)%></option>
										         				
										      <%
										         }
										      %>
								    </select>
								</div>
								<input type="hidden" id="hiddenTextIDVal">	
						   	</td>
						   	<td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
	  								  onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
										title="Add Configuration" onclick="addEditConfigRow(editSubDivID.value, editTeachID1.value, hiddenTextIDVal.value);"/>
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
	<!-- End -->
	
	<div id="viewDivModalID" class="modal fade" tabindex="-1" role="diviewConfigurationalog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					Configuration List				
				</div>
				<div class="modal-body">
					
					<div id="divTableID" align="center"></div>
					
				</div>
				<div class="modal-footer">
					<button type="button" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>	
	
<% } %>
	
<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %> शैक्षणिक वर्ष कॉन्फीग्युर करा<% }else{ %>Configure Academic Year <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %> शैक्षणिक वर्ष कॉन्फीग्युर करा<% }else{ %>Configure Academic Year <% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">
				
              <h3>
               <div class="col-md-12">
               
               <% if(loginform.getMedium().equals("mr")){ %>
               		<s:select list="AcademicYearNameList" required="required" class="form-control" headerKey="-1" 
						name="academicYearID" headerValue="शैक्षणिक वर्ष  निवडा " onchange="loadAcademicYear(this.value)">
					</s:select>
               		 
               <% }else{ %>
	               <s:select list="AcademicYearNameList" required="required" class="form-control" headerKey="-1" 
						name="academicYearID" headerValue="Select Academic Year" onchange="loadAcademicYear(this.value)">
					</s:select> 
				<% } %>
               
                </div>
                
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
		              
	       	<!-- Add and Update Div -->
	     
	     <%if(AcademicYear.equals("Enabled")){ %>  	
		    
		    <div class="col-md-12" >
		      <form id="loadStudentFOrm" action="ConfigureAcademicYear" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
				
			<% if(loginform.getMedium().equals("mr")){ %>
				
				<div class="form-group">
					<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
				       		<td>इयत्ता</td>
				       		<td>तुकडी</td>
				       		<td>वर्ग शिक्षक</td>
				       		<td>कॉन्फीग्युर</td>
				       		<td>कृती</td>
				       	</tr>
				       	
				       	
				       	<tr id="standardTRID"><input type="hidden" id="academicID" class="form-control" name = "academicYearID" value="<s:property value="academicYearID"/>"> 
				     
				       		<td>
				       			<div class="form-group">
				       			
									<select id="standardID" class="form-control" onchange="retrieveDivision(this.value);" >
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
				           <td >
				           	<div class="form-group" >
			               		<select name="" id="stdDivID" class="form-control">
			               			<option value="-1">तुकडी निवडा</option>
			               		</select>
		                	</div>
				           </td>
				           <td>
					          <div class="form-group" >
									<select  class="form-control" id="teacherID">
					                	<option value="-1">वर्ग शिक्षक निवडा</option>
					                	<%
						         			for(Integer AppuserName : AppuserList.keySet()){
						         		%>
						         		<option value="<%=AppuserName%>"><%=AppuserList.get(AppuserName)%></option>
						         				
						         		<%
						         			}
						         		%>
				                	</select>
				                </div>
					       </td>
					       <td>
					       	<div class="form-group">
					       		 <a data-toggle="modal" href="javascript:addConfig();">कॉन्फीग्युर</a>
					       		 <input type="hidden" id="confID">
					       	</div>
					     </td>
					       	
						<td> 
			       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="ऍड करा" title="ऍड  करा" 
				       			onclick="addAcademicYearRow(standardID.value, stdDivID.value, teacherID.value, confID.value);"/>
						</td>
						
				       </tr>
				       
				       <% 
				       		for(ConfigurationForm form:AcademicYearClassList){
				       			
				       			//getting division list based on standardID
				       			HashMap<Integer, String> divisionList = daoinf.getDivision(form.getStandardID());
				       %>
				       		<tr id="newTRID<%=form.getAYClassID()%>">
				       			<td>
				       			<input type="hidden" name="AYClassID1" value="<%=form.getAYClassID()%>">
					       		<select id="editStandardListID<%=form.getAYClassID()%>" name="editStandardID" class="form-control" onchange="retrieveDivisionByStandard('divTDID<%=form.getAYClassID()%>',this.value);">
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
				       			<td id="divTDID<%=form.getAYClassID()%>">
					       			<select id="" name="editDivisionID" class="form-control">
					       				<option value="-1">तुकडी निवडा</option>
					       			<%
						         		
					       				for(Integer divisionIDName:divisionList.keySet()){
					       					if(divisionIDName == form.getDivisionID()){
					       			%>
					       				<option value="<%=divisionIDName %>" selected="selected"><%=divisionList.get(divisionIDName) %></option>
					       			<%
					       					}else{
					       			%>
					       				<option value="<%=divisionIDName %>"><%=divisionList.get(divisionIDName) %></option>
					       			<%
					       					}
					       				}
					       			%>
					       			</select>
				       			</td>
				       			<td>
					       			<select id="" name="editTeacherID" class="form-control">
					       				<option value="-1">वर्ग शिक्षक निवडा</option>
				       					<%
							         		for(Integer AppuserName:AppuserList.keySet()){
						       					if(AppuserName == form.getTeacherID()){
						       			%>
						       				<option value="<%=AppuserName %>" selected="selected"><%=AppuserList.get(AppuserName) %></option>
						       			<%
						       					}else{
						       			%>
						       				<option value="<%=AppuserName %>"><%=AppuserList.get(AppuserName) %></option>
						       			<%
						       					}
							         		}
						       			%>
						       		</select>
				       			</td>
				       			<td>
				       				<a href="javascript:viewEditConfiguration(<%=form.getAYClassID()%>, 'configHiddenTextID<%=form.getAYClassID()%>',editStandardListID<%=form.getAYClassID()%>.value);">कॉन्फीगुरेशन पहा</a>
				       				<input type="hidden" name="editConfText" id="configHiddenTextID<%=form.getAYClassID()%>">		
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
		              <button type="submit" class="btn btn-success" id="submitID" onclick="submitForm('submitID');" >सेव करा</button>
		            </div>
		        </div>
		        
			<% }else{ %>	
			
				<div class="form-group">
					<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
				       		<td>Standard</td>
				       		<td>Division</td>
				       		<td>Class Teacher</td>
				       		<td>Configure</td>
				       		<td>Action</td>
				       	</tr>
				       	
				       	
				       	<tr id="standardTRID"><input type="hidden" id="academicID" class="form-control" name = "academicYearID" value="<s:property value="academicYearID"/>"> 
				     
				       		<td>
				       			<div class="form-group">
				       			
									<select id="standardID" class="form-control" onchange="retrieveDivision(this.value);" >
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
				           <td >
				           	<div class="form-group" >
			               		<select name="" id="stdDivID" class="form-control">
			               			<option value="-1">Select Division</option>
			               		</select>
		                	</div>
				           </td>
				           <td>
					          <div class="form-group" >
									<select  class="form-control" id="teacherID">
					                	<option value="-1">Select Class Teacher</option>
					                	<%
						         			for(Integer AppuserName : AppuserList.keySet()){
						         		%>
						         		<option value="<%=AppuserName%>"><%=AppuserList.get(AppuserName)%></option>
						         				
						         		<%
						         			}
						         		%>
				                	</select>
				                </div>
					       </td>
					       <td>
					       	<div class="form-group">
					       		 <a data-toggle="modal" href="javascript:addConfig();">Configure</a>
					       		 <input type="hidden" id="confID">
					       	</div>
					     </td>
					       	
						<td> 
			       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="Add AcademicYear" title="Add AcademicYear" 
				       			onclick="addAcademicYearRow(standardID.value, stdDivID.value, teacherID.value, confID.value);"/>
						</td>
						
				       </tr>
				       
				       <% 
				       		for(ConfigurationForm form:AcademicYearClassList){
				       			
				       			//getting division list based on standardID
				       			HashMap<Integer, String> divisionList = daoinf.getDivision(form.getStandardID());
				       %>
				       		<tr id="newTRID<%=form.getAYClassID()%>">
				       			<td>
				       			<input type="hidden" name="AYClassID1" value="<%=form.getAYClassID()%>">
					       		<select id="editStandardListID<%=form.getAYClassID()%>" name="editStandardID" class="form-control" onchange="retrieveDivisionByStandard('divTDID<%=form.getAYClassID()%>',this.value);">
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
				       			<td id="divTDID<%=form.getAYClassID()%>">
					       			<select id="" name="editDivisionID" class="form-control">
					       				<option value="-1">Select Division</option>
					       			<%
						         		
					       				for(Integer divisionIDName:divisionList.keySet()){
					       					if(divisionIDName == form.getDivisionID()){
					       			%>
					       				<option value="<%=divisionIDName %>" selected="selected"><%=divisionList.get(divisionIDName) %></option>
					       			<%
					       					}else{
					       			%>
					       				<option value="<%=divisionIDName %>"><%=divisionList.get(divisionIDName) %></option>
					       			<%
					       					}
					       				}
					       			%>
					       			</select>
				       			</td>
				       			<td>
					       			<select id="" name="editTeacherID" class="form-control">
					       				<option value="-1">Select Teacher</option>
				       					<%
							         		for(Integer AppuserName:AppuserList.keySet()){
						       					if(AppuserName == form.getTeacherID()){
						       			%>
						       				<option value="<%=AppuserName %>" selected="selected"><%=AppuserList.get(AppuserName) %></option>
						       			<%
						       					}else{
						       			%>
						       				<option value="<%=AppuserName %>"><%=AppuserList.get(AppuserName) %></option>
						       			<%
						       					}
							         		}
						       			%>
						       		</select>
				       			</td>
				       			<td>
				       				<a href="javascript:viewEditConfiguration(<%=form.getAYClassID()%>, 'configHiddenTextID<%=form.getAYClassID()%>',editStandardListID<%=form.getAYClassID()%>.value);">View Configuration</a>
				       				<input type="hidden" name="editConfText" id="configHiddenTextID<%=form.getAYClassID()%>">		
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
		              <button type="submit" class="btn btn-success" id="submitID" onclick="submitForm('submitID');" >Save</button>
		            </div>
		        </div>	
			<% } %>        
              </form>
              
     		</div>
          <%} %>   
                    
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

  <!-- App JS -->
  <script src="./js/target-admin.js"></script>
  
	<%-- <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script> --%>
    <script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>   
    <script type="text/javascript" src="js/bootstrap-multiselect.js"></script>

  
</body>
</html>
