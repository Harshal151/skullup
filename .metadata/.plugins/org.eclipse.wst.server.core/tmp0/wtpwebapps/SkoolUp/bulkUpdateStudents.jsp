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
     <%@page import="java.util.List"%>
     <%@page import="com.kovidRMS.form.LoginForm"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%
	
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
		
		String AcademicYearName = daoinf.retrieveAcademicYearName(loginform.getOrganizationID());
    	String AcademicYearName1 = daoinf.retrieveAcademicYearName1();
    	HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	
    	StuduntDAOInf daoInf1 = new StudentDAOImpl();
		HashMap<String, String> CreativeActivitiesList = daoInf1.getCreativeActivitiesList(loginform.getOrganizationID());
    	
    	HashMap<String, String> PhysicalActivitiesList = daoInf1.getPhysicalActivitiesList(loginform.getOrganizationID());
    	
    	HashMap<String, String> CompulsoryActivitiesList = daoInf1.getCompulsoryActivitiesList(loginform.getOrganizationID());
    	
    	int draftactiveAYID = configurationDAOInf.retriveInactiveAcademicYearID(loginform.getOrganizationID());
    
		 StudentForm form1 =new StudentForm();
	 
    	List<StudentForm> StudentList = (List<StudentForm>) request.getAttribute("StudentList");
	 
	 	HashMap<Integer, String> AcademicYearNameList = daoinf.getAcademicYearNameList(loginform.getOrganizationID());
	 	
	 	String StandardName = (String) request.getAttribute("StandardName");
		
		String DivisionName = (String) request.getAttribute("DivisionName");
		
		int DivisionID = (Integer) request.getAttribute("DivisionID");
		
		int StandardID = (Integer) request.getAttribute("StandardID");
		
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
	
		String loadStudentSearch = (String) request.getAttribute("loadStudentSearch");
	
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
		
		String AcademicYearCheck = (String) request.getAttribute("AcademicYearCheck");
		
		if(AcademicYearCheck == null || AcademicYearCheck == ""){
			AcademicYearCheck = "dummy";
		}
    %>
    
  <title><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थ्यांचे बल्क अपडेट  - SkoolUp<% }else{ %>Bulk Update Students - SkoolUp<% } %></title>
  
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
  <link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" />
  <link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />

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
			
          document.location="RenderBulkUpdateStudent";
        }
      
      function submitForm(subID, standardName){
    	  
    	  var index = 0;
    	  
    	  var valid = 0;
    	  
    	  var totalInputIndex = 0;
    	  
    	  var map = new Object();
    	  
    	  $("input[name=toRollNumber]").each(function(){
    		  
    		  if($(this).val().trim() != ""){
    			  
    			  map[totalInputIndex] = $("input[name=toRollNumber]").index(this);
    			  totalInputIndex = totalInputIndex + 1;
    		  }
    	  });
    	  var msg = "Following data is empty. Kindly add those details:";
    	  var grNoMsg = "";
    	  var cactMsg = "";
    	  var pActMsg = "";
    	  var compActMsg = "";
    	  var heightMsg = "";
    	  var weightMsg = "";
    	  
    	  $.each( map, function( key, value ) {
    		  var grNo = $("input[name=newGrNumber]").eq(value).val();
    		  
    		  if(grNo.trim() == ""){
    			  if(grNoMsg.includes("<b>GR No.</b> of student:")){
    				  grNoMsg = grNoMsg + ",<b>" +$(".studentNameClass").eq(value).val()+"</b>";
    			  }else{
    				  grNoMsg = grNoMsg + "<br>" + "<b>GR No.</b> of student: <b>" +$(".studentNameClass").eq(value).val()+"</b>";  
    			  }
    			  valid = 1;
    		  }
    		  
    		  var newCreativeActivities = $("select[name=newCreativeActivities]").eq(value).val();
    		 
    		  if(newCreativeActivities == "-1" || newCreativeActivities == null){
    			  if(cactMsg.includes("<b>Creative Activity</b> of student:")){
    				  cactMsg = cactMsg + ",<b>"+$(".studentNameClass").eq(value).val()+"</b>";
    			  }else{
    				  cactMsg = cactMsg + "<br>" + "<b>Creative Activity</b> of student: <b>"+$(".studentNameClass").eq(value).val()+"</b>";  
    			  }
    			  valid = 1;
    		  }
    		  
			var newPhysicalActivities = $("select[name=newPhysicalActivities]").eq(value).val();
			
    		  if(newPhysicalActivities == "-1" || newPhysicalActivities == null){
    			  if(pActMsg.includes("<b>Physical Activity</b> of student:")){
    				  pActMsg = pActMsg + ",<b>"+$(".studentNameClass").eq(value).val()+"</b>";
    			  }else{
    				  pActMsg = pActMsg + "<br>" + "<b>Physical Activity</b> of student: <b>"+$(".studentNameClass").eq(value).val()+"</b>";  
    			  }
    			  valid = 1;
    		  }
    		  
    		if(standardName == "VIII" || standardName == "IX" || standardName == "X"){
    			compActMsg = "";
    		}else{
    			
    			var newCompulsoryActivities = $("select[name=newCompulsoryActivities]").eq(value).val();
      			
	      		  if(newCompulsoryActivities == "-1" || newCompulsoryActivities == null){
	      			  if(compActMsg.includes("<b>Compulsory Activity</b> of student:")){
	      				  compActMsg = compActMsg + ",<b>"+$(".studentNameClass").eq(value).val()+"</b>";
	      			  }else{
	      				  compActMsg = compActMsg + "<br>" + "<b>Compulsory Activity</b> of student: <b>"+$(".studentNameClass").eq(value).val()+"</b>";  
	      			  }
	      			  valid = 1;
	      		  }
    			
    		}
    		  
			var height = $("input[name=newHeight]").eq(value).val();
    		  
    		  if(height.trim() == ""){
    			  if(heightMsg.includes("<b>Height</b> of student:")){
    				  heightMsg = heightMsg + ",<b>"+$(".studentNameClass").eq(value).val()+"</b>";
    			  }else{
    				  heightMsg = heightMsg + "<br>" + "<b>Height</b> of student: <b>"+$(".studentNameClass").eq(value).val()+"</b>";
    			  }
    			  valid = 1;
    		  }
    		  
			var newWeight = $("input[name=newWeight]").eq(value).val();
    		  
    		  if(newWeight.trim() == ""){
    			  if(weightMsg.includes("<b>Weight</b> of student:")){
    				  weightMsg = weightMsg + ",<b>"+$(".studentNameClass").eq(value).val()+"</b>";
    			  }else{
    				  weightMsg = weightMsg + "<br>" + "<b>Weight</b> of student: <b>"+$(".studentNameClass").eq(value).val()+"</b>";  
    			  }
    			  valid = 1;
    		  }
    		  
    		});
    	  
    	  if(msg.startsWith(",")){
    		  msg = msg.substr(1);
    	  }
    	  
    	  msg = msg+grNoMsg+cactMsg+pActMsg+compActMsg+heightMsg+weightMsg;
    	  
	  	  if(msg.trim() == "Following data is empty. Kindly add those details:"){
	  		console.log("inside if....");
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
	    	
	  		 $("#"+subID).attr("disabled", "disabled");
	  		$("#loadStudentFOrm").attr("action","BulkUpdateStudent");
		  	$("#loadStudentFOrm").submit(); 
			
	  	  }else{
	  		  
	  		console.log("inside else....");
	  		$('html, body').animate({
			    scrollTop: $('body').offset().top
			}, 1000);
			
	  		$("#errFontID").css("margin-bottom","15px");
	  		$("#errFontID").html(msg);
	  		
		  }

 	}
      
      function checkStandard(AcademicID){
  		
  		if(AcademicID == "-1"){
  			alert("Please select AcademicYear.");
  			
  			return false;
  		}else{
  	
  			$("html, body").animate({ scrollTop: 0 }, "fast");
  			$(".loadingImage").show();
  			$(".container").css("opacity","0.1");
  			$(".navbar").css("opacity","0.1");
  			return true;
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
			
			var array_element = "<select name='' id='' class='form-control'"+
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
			}
		};
		xmlhttp.open("GET", "RetrieveDivisionListForStandard?standardID="
				+ standardID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	</script>
	
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
			 var array_element = "<select name='newDivisionID' id='' class='form-control'"+
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
				
					var array_element = "<select name='newDivisionID' class='form-control'"+
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
						
						var array_element = "<select name='newDivisionID' id='' class='form-control'"+
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
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	
    </script>
    
    <!-- Ends -->
    
    <!-- function to change all standard once standard from <th> tag is changed -->
    
    <script type="text/javascript">
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
    
    	function changeStandard(stdID){
    		//alert(stdID);
    		$(".newStandardClass").val(stdID);
    		
    		retrieveDivisionByStandardID(stdID);
    	}
    	
    	function changeDivisions(divID){
    		$(".newDivisionClass").val(divID);
    	}
    	
    	function retrieveDivisionByStandardID(stdID) {
    		
    		xmlhttp.onreadystatechange = function() {
    			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

    				var array = JSON.parse(xmlhttp.responseText);   $(function () {
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
    				
    					var array_element = "<select name='newDivisionID' class='form-control'"+
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
    						
    						var array_element = "<select name='newDivisionID' id='' class='form-control'"+
    						"> <option value='-1'>Select Division</option></select>";
    						
    						$(".newDivisionClass").html(array_element);
    						$("#divisionID").html(array_element);
    						
    					}else{
    						
    						$(".newDivisionClass").html(array_element);
    						$("#divisionID").html(array_element);
    													
    					}
    					//retrieveSubjectByStandardID1(standardID);
    			}
    		};
    		xmlhttp.open("GET", "RetrieveDivisionListForStandard?standardID="
    				+ stdID, true);
    		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
    		xmlhttp.send();
    	}
    
    </script>
    
    <!-- Ends -->
    
    <script type="text/javascript">
    	function storeValues(hiddenInputID, selectID){
    		var finalValue = "";
    		$("#"+selectID+" option:selected").each(function(){
    			finalValue += "$" + $(this).text();
    			//alert('hii');
    		});
    		
    		if(finalValue.startsWith("$")){
    			finalValue = finalValue.substr(1);
    		}
    		
    		$("#"+hiddenInputID).val(finalValue);
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
function loadAcademicYear(academicYearID){
	
	document.location="BulkUpdateStudentsByAcademicYear?academicYearID="+ academicYearID;
	
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
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थ्यांचे बल्क अपडेट<% }else{ %>Bulk Update Students <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थ्यांचे बल्क अपडेट<% }else{ %>Bulk Update Students <% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
              <div class="col-md-6">
                <i class="fa fa-tasks"></i>
               <% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्याची माहिती <% }else{ %> Student Details <% } %>
               </div>
                
                <div class="col-md-6">
                 <% if(loginform.getMedium().equals("mr")){ %> 
					<s:select list="AcademicYearNameList" required="required" id="AcademicID" class="form-control" headerKey="-1" 
							name="academicYearID" headerValue="शैक्षणिक वर्ष  निवडा" onchange="loadAcademicYear(this.value)">
					</s:select>
				 <% }else{ %>
				  	<s:select list="AcademicYearNameList" required="required" id="AcademicID" class="form-control" headerKey="-1" 
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
				<center>
						<font style="color: red;font-size:16px;" id="errFontID"></font>
					</center>
		    </div>
			
			<%
			
		    	if(classTeacherCheck.equals("Yes")){ 
		    		if(AcademicYearCheck.equals("Yes"))	{	
		    %>
		    
              <form id="validate-basic" action="LoadBulkStudent" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
				<input type="hidden" id="academicID" class="form-control" name = "academicYearID" value="<s:property value="academicYearID"/>">
				<input type="hidden" name="ayID" value="<%= draftactiveAYID%>">
				<input type="hidden" name="oldAyID" value="<%=loginform.getAcademicYearID() %>">
			 
                <div class="row">
                
                	<div class="col-md-2">
						<input type="hidden" class="form-control" name="standardID" value="<%=StandardID%>" >
						<input type="text" class="form-control" name="" value="<%=StandardName%>" readonly="readonly">
					</div>
					 <div class="col-md-2">
					 	<input type="hidden" class="form-control" name="divisionID" value="<%=DivisionID%>" >
						<input type="text" class="form-control" name="" value="<%=DivisionName%>" readonly="readonly">
					</div>
					
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" onclick="return checkStandard(AcademicID.value)"><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थी पहा <% }else{ %>Load Students <% } %></button>
					</div>
				</div>
			
			</form>
			<%		} 
		    	}%>
			<hr>
			
			<div class="tab-pane fade in active">
			<form id="loadStudentFOrm" action="BulkUpdateStudent" method="POST">
				
				<input type="hidden" name="standardID" value="<%= StandardID%>">
				<input type="hidden" name="divisionID" value="<%= DivisionID%>">
				<input type="hidden" class="form-control" name = "academicYearID" value="<s:property value="academicYearID"/>">
				<%
					if(loadStudentSearch.equals("Enabled")){
				%>
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <th style="width: 6%;"><% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्याचे नाव<% }else{ %> Student Name <% } %></th>
	                          
	                          <th style="width: 5%;"><% if(loginform.getMedium().equals("mr")){ %>हजेरी क्रमांक  <% }else{ %> Roll No <% } %></th>
	                          
	                          <th style="width: 5%;"><% if(loginform.getMedium().equals("mr")){ %>जीआर क्रमांक  <% }else{ %> GR No <% } %></th>
	                          
	                          <th style="width: 5%;"> <% if(loginform.getMedium().equals("mr")){ %>कला <br> उपक्रम <% }else{ %> GR No <% } %>Creative <br>Activity</th>
	                          
	                          <th style="width: 5%;"><% if(loginform.getMedium().equals("mr")){ %>शारीरिक <br> उपक्रम  <% }else{ %> Physical <br>Activity <% } %></th> 
	                          
	                          <th style="width: 5%;"><% if(loginform.getMedium().equals("mr")){ %>अनिवार्य <br> उपक्रम  <% }else{ %> Compulsory <br>Activity <% } %></th> 
	                           
	                          <th style="width: 5%;"><% if(loginform.getMedium().equals("mr")){ %>उंची  <% }else{ %> Height <% } %></th>
	                          
	                          <th style="width: 5%;"><% if(loginform.getMedium().equals("mr")){ %> वजन  <% }else{ %> Weight <% } %></th>
	                       </tr>
	                      </thead>
	                      <tbody>
	                      
	                   <%  for(StudentForm form:StudentList){ %>
	                   
				        <tr id="newTRID<%=form.getStudentID()%>" style="font-size: 14px;width: 6%;">
				       		<td style="text-align: left;">
				       			<input type="hidden" name="newStudentID" value="<%=form.getStudentID()%>">
				       			<%=form.getStudentName()%>
				       			<input type="hidden" class="studentNameClass" value="<%=form.getStudentName()%>">
				       		</td>
				       		 
							<td style="text-align: center;width: 6%;">
				       			<input type="text" name="toRollNumber" value="<%=form.getRollNumber()%>" placeholder="Roll Number" class="form-control">
				       		</td>	
				       		
				       		<td style="text-align: center;width: 6%;">
				       			<input type="text" name="newGrNumber" value="<%=form.getGrNumber()%>" class="form-control" placeholder="GR. Number"  >
				       		</td>
				       		
				       		<td style="text-align: center;width: 5%;">
				       			<select id="creativeID" name="newCreativeActivities" class="form-control" multiple="true">
					       			<option value="-1">Select Creative Activity</option>
				       				<%
				       				
							         	for(String CreativeActivitiesName : CreativeActivitiesList.keySet()){
											String creativeActivity = form.getCreativeActivities().replace("=", ",");
							         		
						       				if(creativeActivity.contains(CreativeActivitiesName)){
						       					
						       		%>
						       		<option value="<%=CreativeActivitiesName %>" selected="selected"><%=CreativeActivitiesList.get(CreativeActivitiesName) %></option>
						       		<%
						       			}else{
						       				
						       		%>
						       		<option value="<%=CreativeActivitiesName %>"><%=CreativeActivitiesList.get(CreativeActivitiesName) %></option>
						       		<%
						       				}
							         	}
						       		%>
						      </select>
				       		</td>	
				       		
				       		<td style="text-align: center;width: 5%; ">
				       			<select id="physicalID" name="newPhysicalActivities" class="form-control" multiple="multiple">
					       			<option value="-1">Select Physical Activity</option>
				       				<%
				       				
							         	for(String PhysicalActivitiesName : PhysicalActivitiesList.keySet()){
							         		String physicalActivity = form.getPhysicalActivities().replace("=", ",");
							         		
						       				if(physicalActivity.contains(PhysicalActivitiesName)){
						       					
						       		%>
						       		<option value="<%=PhysicalActivitiesName %>" selected="selected"><%=PhysicalActivitiesList.get(PhysicalActivitiesName) %></option>
						       		<%
						       			}else{
						       				
						       		%>
						       		<option value="<%=PhysicalActivitiesName %>"><%=PhysicalActivitiesList.get(PhysicalActivitiesName) %></option>
						       		<%
						       				}
							         	}
						       		%>
						      </select>
						      
						    </td>
						    
						    <td style="text-align: center;width: 5%; ">
				       			<select id="compulsoryID" name="newCompulsoryActivities" class="form-control" multiple="multiple">
					       			<option value="-1">Select Compulsory Activity</option>
				       				<%
				       				
							         	for(String CompulsoryActivitiesName : CompulsoryActivitiesList.keySet()){
							         		if(form.getCompulsoryActivities() == null || form.getCompulsoryActivities() == ""){
							        %>
							        	<option value="<%=CompulsoryActivitiesName %>"><%=CompulsoryActivitiesList.get(CompulsoryActivitiesName) %></option>
							        <%
							         		}else if(form.getCompulsoryActivities().isEmpty()){
							        
				         			%>
							        	<option value="<%=CompulsoryActivitiesName %>"><%=CompulsoryActivitiesList.get(CompulsoryActivitiesName) %></option>
							        <%
							         		}else{
					         					String compulsoryActivity = form.getCompulsoryActivities().replace("=", ",");
								         		
							       				if(compulsoryActivity.contains(CompulsoryActivitiesName)){
							       					
							       		%>
							       		<option value="<%=CompulsoryActivitiesName %>" selected="selected"><%=CompulsoryActivitiesList.get(CompulsoryActivitiesName) %></option>
							       		<%
							       			}else{
							       				
							       		%>
							       		<option value="<%=CompulsoryActivitiesName %>"><%=CompulsoryActivitiesList.get(CompulsoryActivitiesName) %></option>
							       		<%
							       				}
								         	}
							         	}
						       		%>
						      </select>
						      
						    </td>	
				       		
				       		<td>
				       			<input type="number" name="newHeight" value="<%=form.getHeight()%>" placeholder="Height" class="form-control"  >
				       		</td>
				       		
				       		<td>
				       			<input type="number" name="newWeight" value="<%=form.getWeight()%>" placeholder="Weight" class="form-control"  >
				       		</td>
				       		
				       </tr> 
				        
				        <% 
				        	}
				        %>
	                      
	                      </tbody>
	                    </table>
		                
			        </div>
			        <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default"><% if(loginform.getMedium().equals("mr")){ %> रद्द करा   <% }else{ %> Cancel <% } %></button>
                  <button type="button" class="btn btn-success" id="submitID" onclick="submitForm('submitID','<%=StandardName%>');"><% if(loginform.getMedium().equals("mr")){ %> अपडेट करा    <% }else{ %> Update <% } %></button>
                </div>
			       <%
						}
			       %>
			       </form>
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
  
  <%-- <script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>  
  <script type="text/javascript" src="js/bootstrap-multiselect.js"></script> --%>
  
</body>
</html>
