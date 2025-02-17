<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.kovidRMS.daoInf.*"%>
<%@page import="com.kovidRMS.daoImpl.*"%>
<%@page import="java.util.*"%>
<%@page import="com.kovidRMS.form.*"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
<%
	    LoginForm loginform = (LoginForm) session.getAttribute("USER");
	
		LoginDAOInf daoinf = new LoginDAOImpl();
		StuduntDAOInf daoInf1 = new StudentDAOImpl();
    	String AcademicYearName = daoinf.retrieveAcademicYearName(loginform.getOrganizationID());
    	
    	ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	
    	int AcademicYearID = loginform.getAcademicYearID();
    	
		String StandardName = daoinf.retrieveStandardName(loginform.getUserID(), AcademicYearID);
    	
    	int StandardID = daoinf.retrieveStandardID(loginform.getUserID(), AcademicYearID);
    	
		String DivisionName = daoinf.retrieveDivisionName(loginform.getUserID(), AcademicYearID);
    	
    	int DivisionID = daoinf.retrieveDivisionID(loginform.getUserID(), AcademicYearID);
    	
    	HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	
    	ConfigurationForm form = new ConfigurationForm();
    	
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
    %>

  <title><% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्यांची नोंदणी करा - SkoolUp <%}else{ %> Register Students - SkoolUp <%} %></title>

<!-- Favicon -->
 	<link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  
   <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">
   

  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/morris/morris.css">
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">
  <link rel="stylesheet" href="./js/plugins/select2/select2.css">
  <link rel="stylesheet" href="./js/plugins/fullcalendar/fullcalendar.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
  
 <link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" /> 
 <link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />

 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

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

<script type="text/javascript">
	$(document).ready(function(){
		$('#studentLiID').addClass("active");
		//alert("hiii");	
		
		var Board = '<%= loginform.getBoard() %>';
		if(Board == "SSC"){
			 
			$("#birthPlaceID").attr("required", "required");
		}else{
			
			$("#birthPlaceID").removeAttr("required", "required");
		}
		
		$('.ui-datepicker').datepicker({
			autoclose: true,
		}).on('changeDate', function(e) {
			
			var arr = e.format().split("/");
        	var date = arr[0]; 
        	var month = arr[1];
        	var year = arr[2];
        	
        	calculateAge(date, month, year);
		    
		});
	});
</script>

  <script type="text/javascript">
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
	
	 .loadingImage{
        	position: absolute;
          	left: 50%;
           	top: 25%;
         	margin-left: -50px; /*half the image width*/
          	margin-top: 10%; /*half the image height*/
          	color: black;
           	z-index: 1;
            display: none;
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
	
      		document.location="RenderaddStudent";
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
	    	
	    	$("#loadStudentFOrm").attr("action","AddStudent");
			$("#loadStudentFOrm").submit(); 
			  	  
		 	$("#"+subID).attr("disabled", "disabled");
		 	
	    	return true;
	   }
      
      /* Commutation List (Drivers name list)*/
      function openCommutation(cID){
    	 
    	 if(cID =="On your own" || cID =="-1"){
    		
    		 $("#commutationDivID").hide();
    		 $('#commutationID').val("-1");
    		 $("#commutationID").removeAttr("required", "required");
    	 }else {
    		 $("#commutationDivID").show();
    		 
    		 $("#commutationID").attr("required", "required");
    		 
    	 }
      }
      
      /* End */
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
			"> <option value=''>Select Division</option></select>";
			
			document.getElementById("stdDivID").innerHTML = array_element;
		
		}else{
			retrieveDivision1(standardID);
		}
		
	}

	function retrieveDivision1(standardID) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "<select name='divisionID' class='form-control' required = 'required' "+
					"> <option value=''>Select Division</option>";
					
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
						"> <option value=''>Select Division</option></select>";
						
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
	
function retrieveDivisionNew(standardID1){
	
	if(standardID1 == "-1"){
		alert("No standard is selected. Please select standard.");
		
		var array_element = "<select name='divisionID' id='' class='form-control'"+
		"> <option value='-1'>Select Division</option></select>";
		
		document.getElementById("stdDivIDNew").innerHTML = array_element;
		
	}else{
		retrieveDivisionNew1(standardID1);
	}
	
}

function retrieveDivisionNew1(standardID1) {

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
					
					document.getElementById("stdDivIDNew").innerHTML = array_element;
					
				}else{
					
					document.getElementById("stdDivIDNew").innerHTML = array_element;	
												
				}
		}
	};
	xmlhttp.open("GET", "RetrieveDivisionListForStandardForSibling?standardID="
			+ standardID1, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>	

<script type="text/javascript">
function retrieveStudents(standardID1,divisionID){
	
	if(standardID1 == "-1"){
		alert("No standard is selected. Please select standard.");
	
	}else if(divisionID == "-1"){
		alert("No division is selected. Please select division.");
		
		var array_element = "<select name='' id='' class='form-control'"+
		"> <option value='-1'>Select Student</option></select>";
		
		document.getElementById("studNameID").innerHTML = array_element;
		
	}else{
		retrieveStudents1(standardID1,divisionID);
	}
	
}

function retrieveStudents1(standardID1,divisionID) {

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			
				var array_element = "<select name='studentName' class='form-control'"+
				"> <option value='-1'>Select Student</option>";
				
				var check = 0;
				/* For Student */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

					array_element += "<option value='"+array.Release[i].studentID+"'>"+array.Release[i].studentName+"</option>";
				}

				array_element += " </select>";
				
				if(check == 0){
					
					alert("No Student found");
					
					var array_element = "<select name='' id='' class='form-control'"+ 
					
					"> <option value='-1'>Select Student</option></select>";
					
					document.getElementById("studNameID").innerHTML = array_element;
					
				}else{
					
					document.getElementById("studNameID").innerHTML = array_element;	
												
				}
		}
	};
	xmlhttp.open("GET", "RetrieveStudentListForStandardAndDivision?standardID="
			+ standardID1 + "&divisionID=" + divisionID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>

<script type="text/javascript">
	var StudentCounter = 1;

	function addStudentRow(editStandard, editDivision, editStudent){
		
		if(editStandard == "-1"){
			alert("Please select Standard.");
		}else if(editDivision == ""){
			alert("Please select Division.");
		}else if(editStudent == "-1"){
			alert("Please select Student.");
		}else{
			
			retrieveStandardNameByStandardID(editStandard, editDivision, editStudent);
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

	function retrieveStandardNameByStandardID(editStandard, editDivision, editStudent) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var standardName = "";

				for ( var i = 0; i < array.Release.length; i++) {
					
					standardName = array.Release[i].standard;
					
				}
				
				retrieveDivisionNameByDivisionID(editStandard, editDivision, editStudent, standardName);
				
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
	
	function retrieveDivisionNameByDivisionID(editStandard, editDivision, editStudent, standardName){
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var divisionName = "";

				for ( var i = 0; i < array.Release.length; i++) {

					divisionName = array.Release[i].division;
					
				}
				retrieveStudentNameByStandardIDAndDivisionID(editStandard, editDivision, editStudent, standardName, divisionName);
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
	
	/*retreiving Student Name  */
	
	function retrieveStudentNameByStandardIDAndDivisionID(editStandard, editDivision, editStudent, standardName, divisionName){
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var studentName = "";

				for ( var i = 0; i < array.Release.length; i++) {

					studentName = array.Release[i].fullName;
				}
				
				addStudentRowRow1(editStandard, editDivision, editStudent, standardName, divisionName, studentName);
			}
		};
		xmlhttp.open("GET", "RetrieveStudentName?studentNameID="
				+ editStudent, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	
	</script>
	
	<script type="text/javascript">
	function addStudentRowRow1(editStandard, editDivision, editStudent, standardName, divisionName, studentName){
		
		var trID = "newExamTRID"+StudentCounter;
		
		var trTag = "";
		
		/* var stringToAppend = "," +  studentName; */
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+standardName+"</td>"
			  + "<td style='text-align:center;'>"+divisionName+"</td>"
			  + "<td style='text-align:center;'>"+studentName+"</td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeStudentTR(\"" + trID + "\");'/></td>"
			  + "</tr>";
		
		$(trTag).insertAfter($('#standardTRID'));
		
		//appending values to siblingID 
		$('#siblingID').val($('#siblingID').val()+stringToAppend);
		
		StudentCounter++;
		
		$("#standardIDNew").val("-1");
		$("#stdDivIDNew").val("-1");
		var array_element = "<select name='' id='' class='form-control'"+
		"> <option value='-1'>Select Student</option></select>";
		
		document.getElementById("studNameID").innerHTML = array_element;
		
	}
	
	function removeStudentTR(trID){
			
		if(confirm("Are you sure you want to delete this row?")){
			
	    	//Updating new value to dummySettingTextID field
	    	$("#"+trID+"").remove();
		}
	}
  
	/* Store multiple comma saperated students name*/
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
	var ConditionCounter = 1;

	function addConditionRow(Condition){
		
		if(Condition == ""){
			alert("Please insert Condition.");
		}else{
			
			addConditionRow1(Condition);
		}
	}
	</script>
	
	<script type="text/javascript">
	function addConditionRow1(Condition){
		
		var trID = "newExamTRID"+ConditionCounter;
		
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+Condition+"<input type='hidden' name='newCondition' value='"+Condition+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeStudentTR(\"" + trID + "\");'/></td>"
			  + "</tr>";
		
		$(trTag).insertAfter($('#conditionTRID'));
		
		ConditionCounter++;
		
		$("#medConditionID").val("");
		
	}
	
	function removeStudentTR(trID){
		if(confirm("Are you sure you want to delete this row?")){
			$("#"+trID+"").remove();
		}
	}
  
</script>
<!-- for phisical & creative activity mutiselct section -->

<script type="text/javascript">
        $(function () {
            $('#creativeID').multiselect({
                includeSelectAllOption: true
            });
      });
        
        $(function () {
            $('#physicalID').multiselect({
                includeSelectAllOption: true
            });
        });
        
        $(function () {
            $('#compulsoryID').multiselect({
                includeSelectAllOption: true
            });
        });
        
        
        //BirthDate Split function
        
        function dateSplit(dateOfBirth){
        
        	var arr = dateOfBirth.split("/");
        	var date = arr[0]; 
        	var month = arr[1];
        	var year = arr[2];
        	
        	calculateAge(date, month, year);
        	
        }
        
      function calculateAge(date, month, year){
			
			var date1 = "<%= daoinf.retrieveAcademicYearAgeFromDate(AcademicYearID)%>";
			
			var date1Arr = date1.split("-");

			var date2= date1Arr[0];
			var month1 = date1Arr[1];
			var year1=date1Arr[2];
			
			var birthYear;
			
			//alert(year1+"---"+year);
			
			if(month>month1||month==month1){
				
			    if(month==month1){
			    	
			        if(date>date2){	
			        	
			            birthYear = parseInt(parseInt(year1)-parseInt(year)-1);
			        }
			        else{
			            birthYear = parseInt(parseInt(year1)-parseInt(year));
			        }
			    }else{
			        birthYear = parseInt(parseInt(year1)-parseInt(year)-1);
			    }
			}else{
			    birthYear = parseInt(parseInt(year1)-parseInt(year));
			}
			
		//alert(birthYear);
			$('#ageID').val(birthYear);
			$('#ageID').attr('readonly', true);
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
      	<h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्यांची नोंदणी करा  <%}else{ %> Register Students<%} %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %> मुख्यपृष्ठ <%}else{ %>  Home<%} %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्यांची नोंदणी करा  <%}else{ %> Register Students<%} %></li>
        </ol>
       
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

          <div class="portlet">
			
            <div class="portlet-header">
			
              <h3>
                <i class="fa fa-tasks"></i>
                	<% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्यांचा तपशील <%}else{ %>  Student Details<%} %>
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
		<% if(classTeacherCheck.equals("Yes")){ %> 
		
		  <form id="loadStudentFOrm" action="AddStudent" method="POST" class="form parsley-form" onsubmit="submitForm('submitID');">
	 
	 	<% if(loginform.getMedium().equals("mr")){ %>
	 
                <div class="form-group">
                  <label for="name">नाव<span class="required">*</span></label>
                  <div>
	                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="आडनाव" required="required" name="lastName" class="form-control" data-required="true" >
	                  <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="पहिले नाव" required="required"  name="firstName" class="form-control" data-required="true" >
	                  <input type="text" id="name" style="width: 32%;display: inline-block;" placeholder="मधले नाव" name="middleName" class="form-control" data-required="true" >
	                  
                 </div>
                </div>
				
				 <div class="form-group">
                  <label for="textarea-input">पत्ता</label>
                  <textarea data-required="true" data-minlength="5" name="address" id="textarea-input" cols="10" rows="2" placeholder="पत्ता" class="form-control"></textarea>
                </div>
				
                <div class="form-group">
                  <label for="name">शहर</label>
                  <input type="text" name="city" value="पुणे" placeholder="शहर" class="form-control" data-required="true" >
                </div>
                <div class="form-group">
                  <label for="name">राज्य</label>
                  <input type="text" name="state" value="महाराष्ट्र" placeholder="राज्य" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">देश</label>
                  <input type="text" name="country" value="भारत" readonly="readonly" placeholder="देश" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">पिन कोड</label>
                  <input type="number" name="pinCode" placeholder="पिन कोड" class="form-control" data-required="true" >
                </div> 
                
                <div class="form-group">
                  <label for="aadhaar">आधार क्रमांक</label>
                  <input type="text" name="aadhaar" placeholder="आधार क्रमांक" class="form-control" data-required="true" >
                </div>
               
                <div class="form-group">
                  <label for="name">जीआर क्रमांक</label>
                  <input type="text" name="grNumber" class="form-control" placeholder="जीआर क्रमांक" data-required="true" >
                </div>
               
				<div class="form-group">
                  <label for="date-2">जन्म तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="dateOfBirth" name="dateOfBirth" class="form-control" required="required" placeholder="जन्म तारीख(dd/MM/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>

			<% if(loginform.getBoard().equals("SSC")){ %>
	       		<div class="form-group">
                   <label for="age">जन्म स्थान<span class="required">*</span></label>
                   <input type="text" name="birthPlace" placeholder="जन्म स्थान" id="birthPlaceID" class="form-control">
               </div>
	       	<% } %>
	       	
 				<div class="form-group">
                   <label for="age">वय<span class="required">*</span></label>
                   
                    <input type="text" name="age" placeholder="वय" value="<s:property value='age'/>" readonly="readonly" required="required" id="ageID" class="form-control">
               </div>
                      
                <div class="form-group">
                  <label for="gender">लिंग<span class="required">* :&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
                  <s:radio list="#{'Male':'पुरुष','Female':'स्त्री'}" name="gender" required="required" ></s:radio>
                </div>
				
          		<div class="form-group">
                  <label for="bloodgroup">रक्त गट</label>
                  <s:select list="#{'A+ve':'A+ve','A-ve':'A-ve','B+ve':'B+ve','B-ve':'B-ve','O+ve':'O+ve','O-ve':'O-ve','AB+ve':'AB+ve','AB-ve':'AB-ve'}" required="required" name="bloodgroup" class="form-control" headerKey="-1" value="-1" headerValue="रक्त  गट  निवडा"></s:select>
                </div>
                
                <div class="form-group">
                  <label for="category">धर्म</label>
                  <s:select list="#{'Hindu':'हिंदू','Bouddha':'बुद्ध','Jain':'जैन','Sikh':'शीख','Parsi':'पारशी','Muslim':'मुसलमान','Christian':'ख्रिश्चन'}" required="required" name="religion" class="form-control" headerKey="-1" value="-1" headerValue="धर्म  निवडा"></s:select>
                </div>

                <div class="form-group">
                  <label for="category">श्रेणी</label>
                  <s:select list="#{'General':'सामान्य','SC':'अनुसूचित जाती','ST':'एसटी','OBC':'ओबिसी','SBC':'एसबीसी','VJ':'व्हीजे','NT':'एनटी'}" required="required" name="category" class="form-control" headerKey="-1" value="-1" headerValue="श्रेणी  निवडा"></s:select>
                </div> 

				<div class="form-group">
                	<label for="hasSpectacles">चष्मा आहे<span class="required">* :&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
                    <s:radio list="#{'Yes':'होय','No':'नाही'}" name="hasSpectacles" required="required"></s:radio>
                </div> 
				 
				<div class="form-group">
                	 <label for="name">घर</label>
                   <s:select list="#{'Blue':'निळा','Yellow':'पिवळा','Red':'लाल','Green':'हिरवा'}" required="required" name="house" class="form-control" headerKey="-1" value="-1" headerValue="घर निवडा"></s:select>
				</div>
               
				<div class="form-group" >
                	 <label for="name">प्रवास मोड<span class="required">*</span></label>
                   <s:select list="#{'On your own':'स्वतः हुन','Bus':'बस','Van':'व्हॅन ','Auto-rickshaw':'ऑटो रिक्षा'}" required="required" name="commutationMode" class="form-control" headerKey="" value="" headerValue="प्रवास मोड निवडा" onchange="openCommutation(this.value);"></s:select>
				</div>
				
				<div class="form-group" style="display: none;" id="commutationDivID">
                	<label for="CommutationList">ड्रायव्हरचे नाव<span class="required">*</span></label>
                    <s:select list="CommutationList" headerKey="" value="" headerValue="ड्रायव्हरचे नाव निवडा" id="commutationID"  name="commutationID" class="form-control"></s:select>
                </div>
                
              <div class="form-group" id="openDIvID2"> 
					
		            <label for="name" style="color: blue">वडीलांचा तपशील :</label>
					 
					<div class="form-group">
	                  <label for="name">वडीलांचे नावं</label>
	                  <div>
		                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="आडनाव" name="parentlastName" class="form-control" data-required="true" >
		                 <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="पहिले नाव" name="parentfirstName" class="form-control" data-required="true" >
		                  <input type="text" id="name" style="width: 32%;display: inline-block;" placeholder="मधले नाव" name="parentmiddleName" class="form-control" data-required="true" >
		                  
	                  </div>
                	</div>
					<div class="form-group">
	                  <label for="relation">नाते</label>
	                  <input type="text" name="relation" value="वडील" readonly="readonly" class="form-control" placeholder="नाते" data-required="true" >
	                </div>
	                
	                <div class="form-group">
	                  <label for="name">ई - मेल आयडी</label>
	                  <input type="email" name="emailId"  placeholder="ईमेल आयडी" class="form-control" data-required="true">
	                </div>
                
	               <div class="form-group">
	                  <label for="mobile">मोबाईल</label>
	                  <input type="number" name="mobile" class="form-control" placeholder="मोबाईल" data-required="true" onKeyPress="if(this.value.length==10) return false;">
	                </div> 
	                
	                <div class="form-group">
	                  <label for="occupation">व्यवसाय</label>
	                  <input type="text" name="occupation" placeholder="व्यवसाय" class="form-control" data-required="true" >
	                </div>
                
                	<label for="name" style="color: blue">आईचा तपशील :</label>
					
					<div class="form-group">
	                  <label for="name">आईचे नाव</label>
	                  <div>
		                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="आडनाव" name="motherlastName" class="form-control" data-required="true" >
		                 <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="पहिले नाव" name="motherfirstName" class="form-control" data-required="true" >
		                  <input type="text" id="name" style="width: 32%;display: inline-block;" placeholder="मधले नाव" name="mothermiddleName" class="form-control" data-required="true" >
		                  
	                  </div>
                	</div>
					<div class="form-group">
	                  <label for="relation">नाते</label>
	                  <input type="text" name="motherrelation" value="आई" readonly="readonly" class="form-control" placeholder="नाते" data-required="true" >
	                </div>
	                
	                <div class="form-group">
	                  <label for="name">ई - मेल आयडी</label>
	                  <input type="email" name="motheremailId" placeholder="ईमेल आयडी" class="form-control" data-required="true" >
	                </div>
                
	                <div class="form-group">
	                  <label for="mobile">मोबाईल</label>
	                  <input type="number" name="mothermobile" class="form-control" placeholder="मोबाईल" data-required="true" onKeyPress="if(this.value.length==10) return false;">
	                </div> 
	                
	                 <div class="form-group">
	                  <label for="occupation">व्यवसाय</label>
	                  <input type="text" name="motheroccupation" placeholder="व्यवसाय" class="form-control" data-required="true" >
	                </div>
					
  				</div>
               <div class="form-group" > 
              	<b style="color: blue">आपत्कालीन संपर्क विभाग:</b>
              	<div class="form-group">
	               <label for="name">आपत्कालीन संपर्क नाव</label>
	               <input type="text" name="emergencyName" class="form-control" placeholder="आपत्कालीन संपर्क नाव" data-required="true" >
	            </div>
	                
	            <div class="form-group">
	               <label for="phone">आपत्कालीन संपर्क क्रमांक</label>
	               <input type="text" name="emergencyPhone" placeholder="आपत्कालीन संपर्क क्रमांक" class="form-control" data-required="true" onKeyPress="if(this.value.length==10) return false;">
	            </div>
              </div>
              
              <div class="form-group" > 
              	<b style="color: blue">वैद्यकीय विभाग:</b>
              	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
				       		<td>वैद्यकीय स्थिती</td>
				       		<td>कृती</td>
				       	</tr>
				       	
				       	<tr id="conditionTRID">
				       		<td>
				       			<input type="text" name="medCondition" id="medConditionID" placeholder="वैद्यकीय स्थिती"> 
				       		</td>
				          
					      	<td> 
			       				<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="स्थिती जोडा" title="स्थिती जोडा" 
				       			onclick="addConditionRow(medConditionID.value);"/>
						  	</td>
				       </tr>
				       
				    </table>
              </div>
              
              <b style="color: blue">वर्ग नोंदणी करा:</b>
				
				 <div class="form-group" id="openDIvID" > 
					<div class="form-group">
                		<label for="academicYearList">शैक्षणिक वर्ष : <b style="color: blue;"><%=AcademicYearName%></b></label>
                	</div>
                		<%
                			if(loginform.getRole().equals("administrator")){
                		%>
						<tr>
						<td>
							<div> <label for="Standard">इयत्ता<span class="required">*</span></label></div>
							<div>
								<s:select list="StandardList" headerKey="" value="" headerValue="इयत्ता निवडा" id="standardID" required="required" name="standardID" class="form-control" onchange="retrieveDivision(this.value);"></s:select>
							</div>
						</td>
				       </tr>
				       <tr>
				           	<td >
				           		<div> <label for="Division">तुकडी<span class="required">*</span></label></div>
				           		<div class="form-group" >
			               			<select name="divisionID" required="required" id="stdDivID" class="form-control">
			               				<option value="">तुकडी निवडा</option>
			               			</select>
		                		</div>
				           </td>
				         </tr>
				         <%
                			}else{
				         %>
				         
				         <tr>
						<td>
							<div> <label for="Standard">इयत्ता<span class="required">*</span></label></div>
							<div>
								<input type="hidden" class="form-control" name="standardID" value="<%=StandardID%>" >
								<input type="text" class="form-control" name="" value="<%=StandardName%>" readonly="readonly">
							</div>
						</td>
				       </tr>
				       <tr>
				           	<td >
				           		<div> <label for="Division">तुकडी<span class="required">*</span></label></div>
				           		<div class="form-group" >
				           		<input type="hidden" class="form-control" name="divisionID" value="<%=DivisionID%>" >
								<input type="text" class="form-control" name="" value="<%=DivisionName%>" readonly="readonly">
			               			
		                		</div>
				           </td>
				         </tr>
				         
				         <%
                			}
				         %>
				         <tr>
				           <td>
				           		<div> <label for="rollNumber">हजेरी क्रमांक</label></div>
				           		<div>
				           			 <input type="text" name="rollNumber" placeholder="हजेरी क्रमांक" class="form-control" data-required="true" >
				           			
				           		</div>
				           </td>
				         </tr>
				         
				         <tr>
				           <td>
				           		<div> <label for="weight">वजन</label></div>
				           		<div>
				           			 <input type="double" name="weight" placeholder="वजन" class="form-control" data-required="true" >
				           			
				           		</div>
				           </td>
				         </tr>
				         
				         <tr>
				           <td>
				           		<div> <label for="height">उंची</label></div>
				           		<div>
				           			 <input type="double" name="height" placeholder="उंची" class="form-control" data-required="true" >
				           			
				           		</div>
				           </td>
				         </tr>
				         <tr>
				           <td>
				           <div class="form-group">
				           		<div> <label for="CreativeActivitiesList">कला उपक्रम</label></div>
				           		<s:select list="CreativeActivitiesList" headerKey="-1" id="creativeID" cssClass="form-control" cssStyle="width: 100%" name="creativeActivities" multiple="true" ></s:select>
				         	</div>
				           	</td>
				         </tr>
				         <tr>
				           <td>
				           <div class="form-group">
				           		<div> <label for="PhysicalActivitiesList">शारीरिक उपक्रम</label></div>
				           			<s:select list="PhysicalActivitiesList" headerKey="-1" id="physicalID" cssClass="form-control" cssStyle="width: 100%" name="physicalActivities" multiple="true" ></s:select>
				           	</div>
				           		
				           </td>
				         </tr>
				         <tr>
				           <td>
				           <div class="form-group">
				           		<div> <label for="CompulsoryActivitiesList">अनिवार्य उपक्रम<</label></div>
				           		
				           		<s:select list="CompulsoryActivitiesList" headerKey="-1" id="compulsoryID" cssClass="form-control" cssStyle="width: 100%" name="compulsoryActivities" multiple="true" ></s:select>
				           	</div>
				           		
				           </td>
				         </tr>
				  </div>
				<br>
				<input type="hidden" id="siblingID" name="sibling" >
				<b style="color: blue">भावंड:</b>
				 <div class="form-group" id="openDIvID1" > 
					
					<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr> 
				       		<td>इयत्ता</td>
				       		<td>तुकडी </td>
				       		<td>विद्यार्थी</td>
				       		<td>कृती</td>
				       	</tr>
				       	
				       	<tr id="standardTRID">
				       		<td>
				       			<div class="form-group">
				       			 	<select id="standardIDNew" class="form-control" name="" onchange="retrieveDivisionNew(this.value);" >
					                	<option value="-1">इयत्ता</option>
					                	<%
						         			for(Integer StandardFormName : StandardList.keySet()){
						         		%>
						         		<option value="<%=StandardFormName%>"><%=StandardList.get(StandardFormName)%></option>
						         				
						         		<%
						         			}
						         		%>
				                	</select> 
				                </div>
				           </td>
				           <td >
				           		<div class="form-group" >
			               			<select id="stdDivIDNew" name="" class="form-control" onchange="retrieveStudents(standardIDNew.value,stdDivIDNew.value);">
			               				<option value="-1">तुकडी</option>
			               			</select>
		                		</div>
				           </td>
				          <td>
					          <div class="form-group" >
					          	<input type="hidden" id="editstudNameID" name="" >
									<select name="" id="studNameID" class="form-control" onchange="storeValues1('editstudNameID','studNameID')" >
					                	<option value="-1">विद्यार्थी निवडा</option>
					                </select>
				                </div>
					       </td>
					      
					       <td> 
			       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="विद्यार्थी जोडा" title="विद्यार्थी जोडा" 
				       			onclick="addStudentRow(standardIDNew.value, stdDivIDNew.value, editstudNameID.value);"/>
						  </td>
				       </tr>
					</table>
				 </div>	
          		
          		<div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="submitID">विद्यार्थी सेव करा</button>
                </div>
                
          <% }else{ %>
          
          	<div class="form-group">
                  <label for="name">Name<span class="required">*</span></label>
                  <div>
	                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="LastName" required="required" name="lastName" class="form-control" data-required="true" >
	                  <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="FirstName" required="required"  name="firstName" class="form-control" data-required="true" >
	                  <input type="text" id="name" style="width: 32%;display: inline-block;" placeholder="MiddleName" name="middleName" class="form-control" data-required="true" >
	              </div>
                </div>
				
				 <div class="form-group">
                  <label for="textarea-input">Address</label>
                  <textarea data-required="true" data-minlength="5" name="address" id="textarea-input" cols="10" rows="2" placeholder="Address" class="form-control"></textarea>
                </div>
				
                <div class="form-group">
                  <label for="name">City</label>
                  <input type="text" name="city" value="Pune" placeholder="City" class="form-control" data-required="true" >
                </div>
                <div class="form-group">
                  <label for="name">State</label>
                  <input type="text" name="state" value="Maharashtra" placeholder="State" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">Country</label>
                  <input type="text" name="country" value="India" readonly="readonly" placeholder="Country" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">PinCode</label>
                  <input type="number" name="pinCode" placeholder="PIN" class="form-control" data-required="true" >
                </div> 
                
                <div class="form-group">
                  <label for="aadhaar">Aadhaar No</label>
                  <input type="text" name="aadhaar" placeholder="Aadhaar No" class="form-control" data-required="true" >
                </div>
               
                <div class="form-group">
                  <label for="name">GR. Number</label>
                  <input type="text" name="grNumber" class="form-control" placeholder="GR. Number" data-required="true" >
                </div>
               
				<div class="form-group">
                  <label for="date-2">Date Of Birth<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="dateOfBirth" name="dateOfBirth" class="form-control" required="required" placeholder="Date Of Birth(dd/MM/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>

 				<div class="form-group">
                   <label for="age">Age<span class="required">*</span></label>
                   
                    <input type="text" name="age" placeholder="Age" value="<s:property value='age'/>" readonly="readonly" required="required" id="ageID" class="form-control">
               </div>
                 
           	<% if(loginform.getBoard().equals("SSC")){ %>
	       		<div class="form-group">
                   <label for="age">Birth Place<span class="required">*</span></label>
                   <input type="text" name="birthPlace" placeholder="Birth Place" id="birthPlaceID" class="form-control">
               </div>
	       	<% } %>
			
                <div class="form-group">
                  <label for="gender">Gender<span class="required">* :&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
                  <s:radio list="#{'Male':'Male','Female':'Female'}" name="gender" required="required" ></s:radio>
                </div>
				
          		<div class="form-group">
                  <label for="bloodgroup">Blood Group</label>
                  <s:select list="#{'A+ve':'A+ve','A-ve':'A-ve','B+ve':'B+ve','B-ve':'B-ve','O+ve':'O+ve','O-ve':'O-ve','AB+ve':'AB+ve','AB-ve':'AB-ve'}" required="required" name="bloodgroup" class="form-control" headerKey="-1" value="-1" headerValue="Select Blood Group"></s:select>
                </div>
                
                <div class="form-group">
                  <label for="category">Religion</label>
                  <s:select list="#{'Hindu':'Hindu','Bouddha':'Bouddha','Jain':'Jain','Sikh':'Sikh','Parsi':'Parsi','Muslim':'Muslim','Christian':'Christian'}" required="required" name="religion" class="form-control" headerKey="-1" value="-1" headerValue="Select Religion"></s:select>
                </div>

                <div class="form-group">
                  <label for="category">Category</label>
                  <s:select list="#{'General':'General','SC':'SC','ST':'ST','OBC':'OBC','SBC':'SBC','VJ':'VJ','NT':'NT'}" required="required" name="category" class="form-control" headerKey="-1" value="-1" headerValue="Select Category"></s:select>
                </div> 

				<div class="form-group">
                	<label for="hasSpectacles">Has Spectacles<span class="required">* :&nbsp;&nbsp;&nbsp;&nbsp;</span></label>
                    <s:radio list="#{'Yes':'Yes','No':'No'}" name="hasSpectacles" required="required"></s:radio>
                </div> 
				 
				<div class="form-group">
                	 <label for="name">House</label>
                   <s:select list="#{'Blue':'Blue','Yellow':'Yellow','Red':'Red','Green':'Green'}" required="required" name="house" class="form-control" headerKey="-1" value="-1" headerValue="Select House"></s:select>
				</div>
               
				<div class="form-group" >
                	 <label for="name">Commutation Mode<span class="required">*</span></label>
                   <s:select list="#{'On your own':'On your own','Bus':'Bus','Van':'Van','Auto-rickshaw':'Auto-rickshaw'}" required="required" name="commutationMode" class="form-control" headerKey="" value="" headerValue="Select Commutation Mode" onchange="openCommutation(this.value);"></s:select>
				</div>
				
				<div class="form-group" style="display: none;" id="commutationDivID">
                	<label for="CommutationList">Driver Name<span class="required">*</span></label>
                    <s:select list="CommutationList" headerKey="" value="" headerValue="Select Driver Name" id="commutationID"  name="commutationID" class="form-control"></s:select>
                </div>
                
              <div class="form-group" id="openDIvID2"> 
					
		            <label for="name" style="color: blue">Father Details :</label>
					 
					<div class="form-group">
	                  <label for="name">Father Name</label>
	                  <div>
		                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="LastName" name="parentlastName" class="form-control" data-required="true" >
		                 <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="FirstName" name="parentfirstName" class="form-control" data-required="true" >
		                  <input type="text" id="name" style="width: 32%;display: inline-block;" placeholder="MiddleName" name="parentmiddleName" class="form-control" data-required="true" >
		                  
	                  </div>
                	</div>
					<div class="form-group">
	                  <label for="relation">Relation</label>
	                  <input type="text" name="relation" value="Father" readonly="readonly" class="form-control" placeholder="Relation" data-required="true" >
	                </div>
	                
	                <div class="form-group">
	                  <label for="name">Email ID</label>
	                  <input type="email" name="emailId"  placeholder="Email Address" class="form-control" data-required="true">
	                </div>
                
	               <div class="form-group">
	                  <label for="mobile">Mobile</label>
	                  <input type="number" name="mobile" class="form-control" placeholder="Mobile" data-required="true" onKeyPress="if(this.value.length==10) return false;">
	                </div> 
	                
	                <div class="form-group">
	                  <label for="occupation">Occupation</label>
	                  <input type="text" name="occupation" placeholder="Occupation" class="form-control" data-required="true" >
	                </div>
                
                	<label for="name" style="color: blue">Mother Details :</label>
					
					<div class="form-group">
	                  <label for="name">Mother Name</label>
	                  <div>
		                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="LastName" name="motherlastName" class="form-control" data-required="true" >
		                 <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="FirstName" name="motherfirstName" class="form-control" data-required="true" >
		                  <input type="text" id="name" style="width: 32%;display: inline-block;" placeholder="MiddleName" name="mothermiddleName" class="form-control" data-required="true" >
		                  
	                  </div>
                	</div>
					<div class="form-group">
	                  <label for="relation">Relation</label>
	                  <input type="text" name="motherrelation" value="Mother" readonly="readonly" class="form-control" placeholder="Relation" data-required="true" >
	                </div>
	                
	                <div class="form-group">
	                  <label for="name">Email ID</label>
	                  <input type="email" name="motheremailId" placeholder="Email Address" class="form-control" data-required="true" >
	                </div>
                
	                <div class="form-group">
	                  <label for="mobile">Mobile</label>
	                  <input type="number" name="mothermobile" class="form-control" placeholder="Mobile" data-required="true" onKeyPress="if(this.value.length==10) return false;">
	                </div> 
	                
	                 <div class="form-group">
	                  <label for="occupation">Occupation</label>
	                  <input type="text" name="motheroccupation" placeholder="Occupation" class="form-control" data-required="true" >
	                </div>
					
  				</div>
               <div class="form-group" > 
              	<b style="color: blue">Emergency Contact Section:</b>
              	<div class="form-group">
	               <label for="name">Emergency Contact Name</label>
	               <input type="text" name="emergencyName" class="form-control" placeholder="Emergency Contact Name" data-required="true" >
	            </div>
	                
	            <div class="form-group">
	               <label for="phone">Emergency Contact Number</label>
	               <input type="text" name="emergencyPhone" placeholder="Emergency Contact Number" class="form-control" data-required="true" onKeyPress="if(this.value.length==10) return false;">
	            </div>
              </div>
              
              <div class="form-group" > 
              	<b style="color: blue">Medical Section:</b>
              	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
				       		<td>Condition</td>
				       		<td>Action</td>
				       	</tr>
				       	
				       	<tr id="conditionTRID">
				       		<td>
				       			<input type="text" name="medCondition" id="medConditionID" placeholder="Medical Condition"> 
				       		</td>
				          
					      	<td> 
			       				<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="Add Condition" title="Add Condition" 
				       			onclick="addConditionRow(medConditionID.value);"/>
						  	</td>
				       </tr>
				       
				    </table>
              </div>
              
              <b style="color: blue">Register to class:</b>
				
				 <div class="form-group" id="openDIvID" > 
					<div class="form-group">
                		<label for="academicYearList">Academic Year : <b style="color: blue;"><%=AcademicYearName%></b></label>
                	</div>
                		<%
                			if(loginform.getRole().equals("administrator")){
                		%>
						<tr>
						<td>
							<div> <label for="Standard">Standard<span class="required">*</span></label></div>
							<div>
								<s:select list="StandardList" headerKey="" value="" headerValue="Select Standard" id="standardID" required="required" name="standardID" class="form-control" onchange="retrieveDivision(this.value);"></s:select>
							</div>
						</td>
				       </tr>
				       <tr>
				           	<td >
				           		<div> <label for="Division">Division<span class="required">*</span></label></div>
				           		<div class="form-group" >
			               			<select name="divisionID" required="required" id="stdDivID" class="form-control">
			               				<option value="">Select Division</option>
			               			</select>
		                		</div>
				           </td>
				         </tr>
				         <%
                			}else{
				         %>
				         
				         <tr>
						<td>
							<div> <label for="Standard">Standard<span class="required">*</span></label></div>
							<div>
								<input type="hidden" class="form-control" name="standardID" value="<%=StandardID%>" >
								<input type="text" class="form-control" name="" value="<%=StandardName%>" readonly="readonly">
								<%-- <s:select list="StandardList" headerKey="-1" value="-1" headerValue="Select Standard" id="standardID"  name="standardID" class="form-control" required="required" onchange="retrieveDivision(this.value);"></s:select> --%>
							</div>
						</td>
				       </tr>
				       <tr>
				           	<td >
				           		<div> <label for="Division">Division<span class="required">*</span></label></div>
				           		<div class="form-group" >
				           		<input type="hidden" class="form-control" name="divisionID" value="<%=DivisionID%>" >
								<input type="text" class="form-control" name="" value="<%=DivisionName%>" readonly="readonly">
			               			
		                		</div>
				           </td>
				         </tr>
				         
				         <%
                			}
				         %>
				         <tr>
				           <td>
				           		<div> <label for="rollNumber">Roll Number</label></div>
				           		<div>
				           			 <input type="text" name="rollNumber" placeholder="Roll Number" class="form-control" data-required="true" >
				           			
				           		</div>
				           </td>
				         </tr>
				         
				         <tr>
				           <td>
				           		<div> <label for="weight">Weight</label></div>
				           		<div>
				           			 <input type="double" name="weight" placeholder="Weight" class="form-control" data-required="true" >
				           			
				           		</div>
				           </td>
				         </tr>
				         
				         <tr>
				           <td>
				           		<div> <label for="height">Height</label></div>
				           		<div>
				           			 <input type="double" name="height" placeholder="Height" class="form-control" data-required="true" >
				           			
				           		</div>
				           </td>
				         </tr>
				         <tr>
				           <td>
				           <div class="form-group">
				           		<div> <label for="CreativeActivitiesList">Creative Activity</label></div>
				           			<s:select list="CreativeActivitiesList" headerKey="-1" id="creativeID" cssClass="form-control" cssStyle="width: 100%" name="creativeActivities" multiple="true" ></s:select>
				         	</div>
				           	</td>
				         </tr>
				         <tr>
				           <td>
				           <div class="form-group">
				           		<div> <label for="PhysicalActivitiesList">Physical Activity</label></div>
				           		
				           		<s:select list="PhysicalActivitiesList" headerKey="-1" id="physicalID" cssClass="form-control" cssStyle="width: 100%" name="physicalActivities" multiple="true" ></s:select>
				           	</div>
				           		
				           </td>
				         </tr>
				         <tr>
				           <td>
				           <div class="form-group">
				           		<div> <label for="CompulsoryActivitiesList">Compulsory Activity</label></div>
				           		
				           		<s:select list="CompulsoryActivitiesList" headerKey="-1" id="compulsoryID" cssClass="form-control" cssStyle="width: 100%" name="compulsoryActivities" multiple="true" ></s:select>
				           	</div>
				           		
				           </td>
				         </tr>
				  </div>
				<br>
				<input type="hidden" id="siblingID" name="sibling" >
				<b style="color: blue">Siblings:</b>
				 <div class="form-group" id="openDIvID1" > 
					
					<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
				       		<td>Standard</td>
				       		<td>Division</td>
				       		<td>Students</td>
				       		<td>Action</td>
				       	</tr>
				       	
				       	<tr id="standardTRID">
				       		<td>
				       			<div class="form-group">
				       			 	<select id="standardIDNew" class="form-control" name="" onchange="retrieveDivisionNew(this.value);" >
					                	<option value="-1">Standard</option>
					                	<%
						         			for(Integer StandardFormName : StandardList.keySet()){
						         		%>
						         		<option value="<%=StandardFormName%>"><%=StandardList.get(StandardFormName)%></option>
						         				
						         		<%
						         			}
						         		%>
				                	</select> 
				                </div>
				           </td>
				           <td >
				           		<div class="form-group" >
			               			<select id="stdDivIDNew" name="" class="form-control" onchange="retrieveStudents(standardIDNew.value,stdDivIDNew.value);">
			               				<option value="-1">Division</option>
			               			</select>
		                		</div>
				           </td>
				          <td>
					          <div class="form-group" >
					          	<input type="hidden" id="editstudNameID" name="" >
									<select name="" id="studNameID" class="form-control" onchange="storeValues1('editstudNameID','studNameID')" >
					                	<option value="-1">Select Students</option>
					                </select>
				                </div>
					       </td>
					      
					       <td> 
			       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="Add Siblings" title="Add Siblings" 
				       			onclick="addStudentRow(standardIDNew.value, stdDivIDNew.value, editstudNameID.value);"/>
						  </td>
				       </tr>
				       
				    </table>
					
				 </div>	
          		
          		<div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID">Add Student</button>
                  
                </div>
                
              <% } %>
          </form>
	
	<% } %>
	
            </div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->
          
        </div> <!-- /.col -->

      </div> <!-- /.row -->

	</div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

	<script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  
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
  
  <script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>  
  <script type="text/javascript" src="js/bootstrap-multiselect.js"></script>
  
</body>
</html>
