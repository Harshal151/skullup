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
		
		int AcademicYearID = loginform.getAcademicYearID();
		
		String StandardName = daoinf.retrieveStandardName(loginform.getUserID(), AcademicYearID);
		
		int StandardID = daoinf.retrieveStandardID(loginform.getUserID(), AcademicYearID);
	
		String AcademicYearName = daoinf.retrieveAcademicYearName(loginform.getOrganizationID());
    	String AcademicYearName1 = daoinf.retrieveAcademicYearName1();
    	HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	
    	StuduntDAOInf daoInf1 = new StudentDAOImpl();
		HashMap<String, String> CreativeActivitiesList = daoInf1.getCreativeActivitiesList(loginform.getOrganizationID());
    	
    	HashMap<String, String> PhysicalActivitiesList = daoInf1.getPhysicalActivitiesList(loginform.getOrganizationID());
    	
    	int draftactiveAYID = configurationDAOInf.retriveInactiveAcademicYearID(loginform.getOrganizationID());
    	int activeAYID = loginform.getAcademicYearID();
    
    	List<StudentForm> StudentList = (List<StudentForm>) request.getAttribute("StudentList");
	 
	 	int standardID = (Integer) request.getAttribute("standardID");
	 	
	 	String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		} 
	 
		int nextStandardID = (Integer) request.getAttribute("nextStandardID");
		
		String nextStandard = (String) request.getAttribute("nextStandard");
		
		HashMap<Integer, String> divisionMap =  daoinf.getDivision(nextStandardID);
		
		String loadStudentSearch = (String) request.getAttribute("loadStudentSearch");
		
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
    %>
    
  <title><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थी ट्रान्स्फर - SkoolUp<% }else{ %>Transfer Students - SkoolUp <% } %></title>
  
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
            list-style:none;RenderClassStudents
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
    	  document.location="RendertransferStudent";
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
		  
    	  $("#loadStudentFOrm").attr("action","TransferStudent");
	  	  $("#loadStudentFOrm").submit(); 
	  	  
   	 	 $("#"+subID).attr("disabled", "disabled");
     }
      
      function checkStandard(sid, subID){
  		
  		if(sid == "-1"){
  			alert("Please select Standard.");
  			
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
			
    	  $("#validate-basic").attr("action","LoadTransferStudent");
    	  $("#validate-basic").submit();
    	  
    	  $("#"+subID).attr("disabled", "disabled");
    	  
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

<body>

<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="google_translate_element" style="display:none"></div>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थी ट्रान्स्फर<% }else{ %>Transfer Students <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थी ट्रान्स्फर<% }else{ %>Transfer Students <% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                <% if(loginform.getMedium().equals("mr")){ %>विद्यार्थ्याची  माहिती <% }else{ %>Student Details <% } %>
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
			
              <form id="validate-basic" action="LoadTransferStudent" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
				<input type="hidden" name="ayID" value="<%=draftactiveAYID %>">
				<input type="hidden" name="oldAyID" value="<%=activeAYID%>">
				<div class="form-group">
					<div>
						<label for="academicYearList"> <% if(loginform.getMedium().equals("mr")){ %>शैक्षणिक वर्षापासून  <% }else{ %>From Academic Year <% } %> : <b style="color: blue;"><%=AcademicYearName%></b></label>
					</div>
					<div>
                		<label for="academicYearList"><% if(loginform.getMedium().equals("mr")){ %>शैक्षणिक वर्ष  <% }else{ %>To Academic Year <% } %> : <b style="color: blue;"><%=AcademicYearName1 %></b></label>
               		</div>	
                </div>
                
                <div class="row">
                		<%
                			if(loginform.getRole().equals("administrator")){
                		%>
				  	<div class="col-md-2">
				  	<% if(loginform.getMedium().equals("mr")){ %>
						<s:select list="StandardList" class="form-control" headerKey="-1" headerValue="इयत्ता निवडा" name="standardID" id="SID"></s:select>
				 <% }else{ %>
				 		<s:select list="StandardList" class="form-control" headerKey="-1" headerValue="Select Standard" name="standardID" id="SID"></s:select>
				  <% } %>
				  	
					</div>
					<%}else{ %>
					
					<div class="col-md-2">
						<input type="hidden" class="form-control" name="standardID" value="<%=StandardID%>" >
						<input type="text" class="form-control" name="" value="<%=StandardName%>" readonly="readonly">
					</div>
					<%} %>
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" id="submitID" onclick="return checkStandard(SID.value, 'submitID')"><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थी पहा  <% }else{ %>Load Students<% } %></button>
					</div>
				</div>
			
			</form>
			<%} %>
			<hr>
			
			<div class="tab-pane fade in active">
			<form id= "loadStudentFOrm" action="TransferStudent" method="POST">
				<input type="hidden" name="ayID" value="<%=draftactiveAYID %>">
				<input type="hidden" name="oldAyID" value="<%=activeAYID%>">
				
				
				<%
					if(loadStudentSearch.equals("Enabled")){
				%>
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <th style="width: 6%;"><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थ्याचे नाव  <% }else{ %> Student Name <% } %></th>
	                          
	                          <th style="width: 5%;"><% if(loginform.getMedium().equals("mr")){ %>फ्रॉम इयत्ता <% }else{ %> From Standard <% } %></th>
	                          
	                          <th style="width: 5%;"><% if(loginform.getMedium().equals("mr")){ %>फ्रॉम तुकडी   <% }else{ %> From Division <% } %></th>
	                          
	                          <th style="width: 6%;"><% if(loginform.getMedium().equals("mr")){ %>फ्रॉम हजेरी क्रमांक  <% }else{ %> From Roll No <% } %></th>
	                          
	                          <th style="width: 8%;">To Standard
	                          	<select id="studentStandardID" name="" class="form-control" onchange="changeStandard(this.value);">
					               <option value="-1"><% if(loginform.getMedium().equals("mr")){ %>इयत्ता निवडा<% }else{ %> Select Standard <% } %></option>
					                <% 
					                	int loopCheck1 = 0;
					                
					                	if(StandardList != null){
							         		for(Integer StandardFormName : StandardList.keySet()){
							         			if(nextStandardID == StandardFormName){
							         				loopCheck1 = 1;
										         	%>
										         	<option value="<%=StandardFormName%>" selected="selected"><%=nextStandard%></option>
										         	<%
						         				}else{
						         					
						         					if(loopCheck1 == 1){
											         	%>
											         	<option value="<%=StandardFormName%>"><%=StandardList.get(StandardFormName)%></option>
											         	<%
						         					}else{
							         					continue;
							         				}
							         			}
							         		}
					                	}
						         	%>
				                </select>
				              </th>
				              
	                          <th style="width: 8%;">To Division
	                          	<select id="divisionID" name="" class="form-control" onchange="changeDivisions(this.value);" >
					               <option value="-1"><% if(loginform.getMedium().equals("mr")){ %>तुकडी निवडा<% }else{ %> Select Division <% } %></option>
					                <%
						         		for(Integer divID : divisionMap.keySet()){
						         	%>
						         	<option value="<%=divID%>"><%=divisionMap.get(divID)%></option>
						         	<%
						         		}
						         	%>
				                </select>
			               	  </th>
			               	</tr>
	                      </thead>
	                      <tbody>
	                      
	                   <% 
				       		for(StudentForm form:StudentList){	
				        %>
				       <tr id="newTRID<%=form.getStudentID()%>" style="font-size: 14px;width: 6%;">
				       		<td style="text-align: left;">
				       			<input type="hidden" name="newStudentID" value="<%=form.getStudentID()%>">
				       			<input type="hidden" name="newCreativeActivities" value="<%= form.getCreativeActivities()%>">
								<input type="hidden" name="newPhysicalActivities" value="<%= form.getPhysicalActivities()%>">
								<input type="hidden" name="newCompulsoryActivities" value="<%= form.getCompulsoryActivities()%>">
								<input type="hidden" name="newWeight" value="<%= form.getWeight()%>">
								<input type="hidden" name="newHeight" value="<%= form.getHeight()%>">
				       			<%=form.getStudentName()%>
				       		</td>
				       		
				       		<td style="text-align: center;width: 5%;">
				       			<%=form.getStandardName()%>
				       			<input type="hidden" name="oldStandardID" value="<%=form.getStandardID()%>">
				       		</td>	
				       		
				       		<td style="text-align: center;width: 5%;">
				       			<%=form.getDivisionName()%>
				       			<input type="hidden" name="oldDivisionID" value="<%=form.getDivisionID()%>">
				       		</td>	
				       		
				       		<td style="text-align: center;width: 6%;">
				       			<%=form.getRollNumber()%>
				       		</td>	
				       		
				       		<td style="text-align: center;width: 8%;">
				       			<select  name="newStandardID" class="form-control newStandardClass" onchange="retrieveDivisionByStandard('divTDID<%=form.getStudentID()%>',this.value);" >
					               <option value="-1"><% if(loginform.getMedium().equals("mr")){ %>इयत्ता निवडा<% }else{ %> Select Standard <% } %></option>
					                <%
					                	int loopCheck = 0;
						         		for(Integer StandardFormName : StandardList.keySet()){
						         			if(form.getNextStandardID() == StandardFormName){
						         				loopCheck = 1;
									         	%>
									         	<option value="<%=StandardFormName%>" selected="selected"><%=StandardList.get(StandardFormName)%></option>
									         	<%
						         			}else{
						         				
						         				if(loopCheck == 1){
										         	%>
										         	<option value="<%=StandardFormName%>"><%=StandardList.get(StandardFormName)%></option>
										         	<%
						         				}else{
						         					continue;
						         				}
						         			}
						         		}
						         	%>
				                </select>
				       		</td>
				       		
				       		<td style="text-align: center;width: 8%;" id="divTDID<%=form.getStudentID()%>">
				       			<select name="newDivisionID" class="form-control newDivisionClass">
					               <option value="-1"><% if(loginform.getMedium().equals("mr")){ %>तुकडी निवडा<% }else{ %> Select Division <% } %> </option>
					                <%
						         		for(Integer divisionID : divisionMap.keySet()){
						         			if(form.getDivisionName().equals(divisionMap.get(divisionID))){
						         	%>
						         	<option value="<%=divisionID%>" selected="selected"><%=divisionMap.get(divisionID)%></option>
						         	<%
						         			}else{
						         	%>
						         	<option value="<%=divisionID%>"><%=divisionMap.get(divisionID)%></option>
						         	<%
						         			}
						         		}
						         	%>
				                </select>
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
                  <button type="submit" class="btn btn-success" id="submitID" onclick="submitForm('submitID');"><% if(loginform.getMedium().equals("mr")){ %> सेव करा    <% }else{ %> Save <% } %></button>
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
  
</body>
</html>
