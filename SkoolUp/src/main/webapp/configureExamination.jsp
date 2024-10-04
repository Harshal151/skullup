<%@page import="java.util.ArrayList"%>
<%@page import="com.kovidRMS.form.ConfigurationForm"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
     <%@page import="com.kovidRMS.daoImpl.ConfigurationDAOImpl"%>
<%@page import="com.kovidRMS.daoInf.ConfigurationDAOInf"%>
<%@page import="com.kovidRMS.form.ConfigurationForm"%>
     <%@page import="java.util.HashMap"%>
     <%@page import="com.kovidRMS.form.LoginForm"%>
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
   	
    	ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();	
    
    	ConfigurationForm form1 = new ConfigurationForm();
    	
    	List<ConfigurationForm> examinatrionList = (List<ConfigurationForm>) request.getAttribute("examinationList");
   	
    %>
    
  <title><% if(loginform.getMedium().equals("mr")){ %>परीक्षा  कॉन्फीग्युर करा - SkoolUp <% }else{ %>Configure Examination - SkoolUp<% } %></title>
  
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
	
	function windowOpen1(){
		
		 $('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); 
			
        document.location="RenderconfigureExamination";
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
		
		$("#loadStudentFOrm").attr("action","ConfigureExamination");
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
    var examinationCounter = 1;
	
	function addExaminationRow(editTerm, editExamName, editExamType, editStartDate, editEndDate){
		if(editTerm == "-1"){
			alert("Please enter term.");
		}else if(editExamName == ""){
			alert("Please select examName.");
		}else if(editExamType == "-1"){
			alert("Please select examType.");
		}else if(editStartDate == ""){
			alert("Please select StartDate.");
		}else if(editEndDate == ""){
			alert("Please select EndDate.");
		}else{
		
			addExaminationRow1(editTerm, editExamName, editExamType, editStartDate, editEndDate);
		}
	}
	
	function addExaminationRow1(editTerm, editExamName, editExamType, editStartDate, editEndDate){
		
		var trID = "newExamTRID"+examinationCounter;
		
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+editTerm+"<input type='hidden' name='newTerm' value='"+editTerm+"'></td>"
			  + "<td style='text-align:center;'>"+editExamName+"<input type='hidden' name='newExamName' value='"+editExamName+"'></td>"
			  + "<td style='text-align:center;'>"+editExamType+"<input type='hidden' name='newExamType' value='"+editExamType+"'></td>"
			  + "<td style='text-align:center;'>"+editStartDate+"<input type='hidden' name='newStartDate' value='"+editStartDate+"'></td>"
			  + "<td style='text-align:center;'>"+editEndDate+"<input type='hidden' name='newEndDate' value='"+editEndDate+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeExamTR(\"" + trID + "\");'/></td>"
			  + "</tr>";
		
		$(trTag).insertAfter($('#examTRID'));
		examinationCounter++;
		
		$("#termID").val("-1");
		$("#examNameID").val("");
		$("#examTypeID").val("-1");
		$("#startDateID").val("");
		$("#endDateID").val("");
		
	}
	
	function removeExamTR(trID){
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
			xmlhttp.open("GET", "DeleteRow?deleteID="
					+ deleteID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	
    </script>
    
    <!-- Ends -->
    
    <!-- Delete user alert function -->
    
    <script type="text/javascript">
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete user?")) {
				document.location = url;
			}
		}

	function addTimeTable(examName){
	
		$("#tableTRID").find("tr:gt(1)").remove();
		
		var term = $("#termID").val();
		
		var examName = $("#examNameID").val();
		  
		var examType = $("#examTypeID").val();
		
		var startDate = $("#startDateID").val();
		 
		var endDate = $("#endDateID").val();
		
		if(term == "-1"){
			alert('Please select term.');
		}else if(examName == ""){
			alert('Please add exam name.');
		}else if(examType == "-1"){
			alert('Please select exam type.');
		}else if(startDate == ""){
			alert('Please add start date.');
		}else if(endDate == ""){
			alert('Please select end date.');
		}else{
			$("#myModal").modal('show');
		}
	}

</script>
 
<script type="text/javascript">

var PrimaryCounter = 1;
function addPrimaryRow(Date, Day, Subject){
	
	if(Date == ""){
		alert("Please select Date.");
	}else{
		addPrimaryRow1(Date, Day, Subject);
	}

}

function addPrimaryRow1(Date, Day, Subject){
	
	var trID = "newExamTRID"+PrimaryCounter;
	
	var trTag = "";
	//alert(Subject);
	
	var stringToAppend = "*" + Date;
	var stringToAppend1 = "";
	var stringToAppendNew = "";
	var sub = Subject.split("=");
	for(i=0; i<sub.length; i++){
		
		var newSubID = $('#'+sub[i]).val();
		var newSubText = $("#"+sub[i]+" [value='"+newSubID+"']").text();
		
		var standard = sub[i].split("_");
		
		if(newSubID == ""){
			
			stringToAppend1 = stringToAppend1 + "*" + Date + "$ " + standard[1];
		}else{
			stringToAppend1 = stringToAppend1 + "*" + Date + "$" + newSubText.trim() + "$" + standard[1];
		}
	}
	
	/* stringToAppendNew = stringToAppend + stringToAppend1; */
	
	trTag += "<tr id='"+trID+"'>"
		  + "<td style='text-align:center;'>"+Date+"</td>"
		  + "<td style='text-align:center;'>"+Day+"</td>";
	
			for(i=0; i<sub.length; i++){
				var newSubID = $('#'+sub[i]).val();
				var newSubText = $("#"+sub[i]+" [value='"+newSubID+"']").text();
				if(newSubID == ""){
					trTag += "<td style='text-align:center;'></td>";
				}else{
					trTag += "<td style='text-align:center;'>"+newSubText.trim()+"</td>";
				}
				
			}
		 
		trTag += "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeExamTR(\"" + stringToAppend1 + "\",\"" + trID + "\");'/></td>"
		  + "</tr>";
	$(trTag).insertAfter($('#timeTableTRID'));
	
	//appending values to term hiddenID 
	$('#timeTableID').val($('#timeTableID').val()+stringToAppend1);
	
	PrimaryCounter++;

	$('#examDateID').val("");
	$('#dayID').val("");
	
	for(i=0; i<sub.length; i++){
		$('#'+sub[i]).val("");
	}
	
}

function removeExamTR(stringToAppend1, trID){
	if(confirm("Are you sure you want to delete this row?")){
		
		var timetableText = $('#timeTableID').val();
		var newValue = timetableText.replace(stringToAppend1,'');
    	
    	//Updating new value to dummySettingTextID field
    	$('#timeTableID').val(newValue);
    	
		$("#"+trID+"").remove();
	}
}

/* Secondary Section */

var SecondaryCounter = 1;
function addSecondaryRow(Date1, Day1, Subject1){
	
	if(Date1 == ""){
		alert("Please select Date.");
	}else{
		addSecondaryRow1(Date1, Day1, Subject1);
	}
}

function addSecondaryRow1(Date1, Day1, Subject1){
	
	var trID = "newExamTRID"+SecondaryCounter;
	
	var trTag = "";
	//alert(Subject);
	
	var stringToAppend1 = "*" + Date;
	var stringToAppend12 = "";
	var stringToAppendNew1 = "";
	var sub1 = Subject1.split("=");
	for(i=0; i<sub1.length; i++){
		
		var newSubID1 = $('#'+sub1[i]).val();
		var newSubText1 = $("#"+sub1[i]+" [value='"+newSubID1+"']").text();
		
		if(newSubID1 == ""){
			stringToAppend12 = stringToAppend12 + "$ ";
			
		}else{
			stringToAppend12 = stringToAppend12 + "$" + newSubText1;
		}
	}
	
	stringToAppendNew1 = stringToAppend1 + stringToAppend12;
	
	trTag += "<tr id='"+trID+"'>"
		  + "<td style='text-align:center;'>"+Date1+"</td>"
		  + "<td style='text-align:center;'>"+Day1+"</td>";
	
			for(i=0; i<sub1.length; i++){
				var newSubID1 = $('#'+sub1[i]).val();
				var newSubText1 = $("#"+sub1[i]+" [value='"+newSubID1+"']").text();
				if(newSubID1 == ""){
					trTag += "<td style='text-align:center;'></td>";
				}else{
					trTag += "<td style='text-align:center;'>"+newSubText1.trim()+"</td>";
				}
				
			}
		 
		trTag += "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeExamTR1(\"" + stringToAppendNew1 + "\",\"" + trID + "\");'/></td>"
		  + "</tr>";
		$(trTag).insertAfter($('#timeTableTRID1'));
		
		//appending values to term hiddenID 
		$('#timeTableID').val($('#timeTableID').val()+stringToAppendNew1);
		
		SecondaryCounter++;
	
		$('#examDateID1').val("");
		$('#dayID1').val("");
		
		for(i=0; i<sub1.length; i++){
			$('#'+sub1[i]).val("");
		}
	}

	function removeExamTR1(stringToAppendNew1, trID){
		if(confirm("Are you sure you want to delete this row?")){
		
			var timetableText = $('#timeTableID').val();
			var newValue = timetableText.replace(stringToAppendNew1,'');
	    	
	    	//Updating new value to dummySettingTextID field
	    	$('#timeTableID').val(newValue);
			$("#"+trID+"").remove();
		}
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
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %> परीक्षा कॉन्फीग्युर करा<% }else{ %>Configure Examination <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %> परीक्षा कॉन्फीग्युर करा<% }else{ %>Configure Examination <% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class=""></i>
                <div class="form-group">
                	<label for="academicYearList"><% if(loginform.getMedium().equals("mr")){ %> शैक्षणिक वर्ष <% }else{ %>Academic Year <% } %> : <b style="color: blue;"><%=AcademicYearName%></b></label>
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
		       <div class="col-md-12">
		       <form id="loadStudentFOrm" action="ConfigureExamination" method="POST">
		   
		    <% if(loginform.getMedium().equals("mr")){ %> 
				
				<div class="form-group">
			       <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
			       	<tr>
			       		<td>टर्म </td>
			       		<td>परीक्षेचे नाव</td>
			       		<td>परीक्षेचा  प्रकार</td>
			       		<td>सुरुवातीची तारीख</td>
			       		<td>शेवटची तारीख</td>
			       		<!-- <td>Time Table</td> -->
			       		<td>Action</td>
			       	</tr>
			       	
			       	<tr id="examTRID">
			       		<td><select id="termID" name="term" class="form-control" style="width: 120px;">
			       			<option value="-1">टर्म निवडा</option>
			       			<option value="Term I">टर्म १</option>
			       			<option value="Term II">टर्म २</option>
			       		</select></td>
			       		
			       		<td><input type="text" name="examName" id="examNameID" class="form-control" placeholder="परीक्षेचे नाव"> </td>
			       		
			       		<td><select id="examTypeID" name="examType" class="form-control">
			       			<option value="-1">परीक्षेचा  प्रकार निवडा</option>
			       			<option value="Unit Test">चाचणी परीक्षा</option>
			       			<option value="Notebook">Notebook</option>
			       			<option value="Subject Enrichment">Subject Enrichment</option>
			       			<option value="Term End">सहामाही/वार्षिक परीक्षा</option>
			       			<option value="Portfolio">Portfolio</option>
			       			<option value="Multiple Assessment">Multiple Assessment</option>
			       		</select></td>
			       		
			       		<td>
			       		<div class="input-group date ui-datepicker">
							<input type="text" id="startDateID" name="startDate" class="form-control" placeholder="सुरुवातीची तारीख" data-required="true">
	                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                 		</div>
                 		</td>
                 		
                 		<td >
                 		<div class="input-group date ui-datepicker">
	               			<input type="text" id="endDateID" name="endDate" class="form-control" placeholder="शेवटची तारीख" data-required="true">
	                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  		</div>
                  		</td>
                  		
			       		<td> 
			       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="Add Examination" title="ऍड करा" 
				       			onclick="addExaminationRow(termID.value, examNameID.value, examTypeID.value, startDateID.value, endDateID.value);"/>
						</td>
					</tr>
			       	
			       <%
				       List<String> examTypeList = new ArrayList<String>();
				       examTypeList.add("Unit Test");
				       examTypeList.add("Notebook");
				       examTypeList.add("Subject Enrichment");
				       examTypeList.add("Term End");
				       examTypeList.add("Portfolio");
				       examTypeList.add("Multiple Assessment");
				       
				       	for(ConfigurationForm form:examinatrionList){
			       %>
			       
			       	<tr id="newTRID<%=form.getExaminationID()%>">
			       		
			       		<td>
			       			<input type="hidden" name="editExamID" value="<%=form.getExaminationID()%>">
			       			<select id="" name="editTerm" class="form-control">
			       				<option value="-1">टर्म निवडा</option>
			       				<%
			       					if(form.getTerm().equals("Term I")){
			       				%>
			       				<option value="Term I" selected="selected">टर्म १</option>
			       				<option value="Term II">टर्म २</option>
			       				<%
			       					}else if(form.getTerm().equals("Term II")){
			       				%>
			       				<option value="Term I" >टर्म १</option>
			       				<option value="Term II" selected="selected">टर्म २</option>
			       				<%
			       					}else{
			       				%>
			       				<option value="Term I" >टर्म १</option>
			       				<option value="Term II">टर्म २</option>
			       				<%
			       					}
			       				%>
			       			</select>
			       		</td>
			       		
			       		<td>
			       			<input type="text" name="editExamNameID" class="form-control" value="<%=form.getExamName() %>">
			       		</td>
			       		
			       		<td>
			       			<select id="" name="editExamTypeID" class="form-control">
			       				<option value="-1">परीक्षेचा  प्रकार निवडा</option>
			       			<%
			       				for(String examType:examTypeList){
			       					if(examType.equals(form.getExamType())){
			       			%>
			       				<option value="<%=examType %>" selected="selected"><%=examType %></option>
			       			<%
			       					}else{
			       			%>
			       				<option value="<%=examType %>"><%=examType %></option>
			       			<%
			       					}
			       				}
			       			%>
			       			</select>
			       		</td>
			       		
			       		<td>
			       		<div class="input-group date ui-datepicker">
							<input type="text" name="editStartDateID" class="form-control" value="<%=form.getStartDate() %>">
	                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                 		</div>
                 		</td>
                 		
                 		<td >
                 		<div class="input-group date ui-datepicker">
	               			<input type="text" name="editEndDateID" class="form-control" value="<%=form.getEndDate() %>">
	                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  		</div>
                  		</td>
			       		
			       		<td><img src='images/delete.png' style='height:24px;cursor: pointer;'
							onclick="deleteRow1(<%=form.getExaminationID()%>,'newTRID<%=form.getExaminationID()%>');" 
							alt='डीलीट करा' title='डीलीट करा'/></td>
			       	</tr>
			      
			       <%
			       	}
			       %>
			       
			       </table>
			    </div>
		       	<div class="form-group">
		            <div class="col-md-12" align="center">
		  	          <button type="button" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
		               <button type="submit"  class="btn btn-success" id="submitID" onclick="submitForm('submitID');" >सेव करा</button>
		            </div>
		        </div>
		        
			<% }else{ %>
		       
		       <div class="form-group">
			       <table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
			       	<tr>
			       		<td>Term</td>
			       		<td>Examination Name</td>
			       		<td>Examination Type</td>
			       		<td>Start Date</td>
			       		<td>End Date</td>
			       		<!-- <td>Time Table</td> -->
			       		<td>Action</td>
			       	</tr>
			       	
			       	<tr id="examTRID">
			       		<td><select id="termID" name="term" class="form-control" style="width: 120px;">
			       			<option value="-1">Select Term</option>
			       			<option value="Term I">Term I</option>
			       			<option value="Term II">Term II</option>
			       		</select></td>
			       		
			       		<td><input type="text" name="examName" id="examNameID" class="form-control" placeholder="Examination Name"> </td>
			       		
			       		<td><select id="examTypeID" name="examType" class="form-control">
			       			<option value="-1">Select Examination Type</option>
			       			<option value="Unit Test">Unit Test</option>
			       			<option value="Notebook">Notebook</option>
			       			<option value="Subject Enrichment">Subject Enrichment</option>
			       			<option value="Term End">Term End</option>
			       			<option value="Portfolio">Portfolio</option>
			       			<option value="Multiple Assessment">Multiple Assessment</option>
			       		</select></td>
			       		
			       		<td>
			       		<div class="input-group date ui-datepicker">
							<input type="text" id="startDateID" name="startDate" class="form-control" placeholder="Start Date" data-required="true">
	                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                 		</div>
                 		</td>
                 		
                 		<td >
                 		<div class="input-group date ui-datepicker">
	               			<input type="text" id="endDateID" name="endDate" class="form-control" placeholder="End Date" data-required="true">
	                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  		</div>
                  		</td>
                  		
			       		<td> 
			       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       			onmouseout="this.src='images/addBill.png'" alt="Add Examination" title="Add Examination" 
				       			onclick="addExaminationRow(termID.value, examNameID.value, examTypeID.value, startDateID.value, endDateID.value);"/>
						</td>
					
					</tr>
			       	
			       <%
			       	List<String> examTypeList = new ArrayList<String>();
			       examTypeList.add("Unit Test");
			       examTypeList.add("Notebook");
			       examTypeList.add("Subject Enrichment");
			       examTypeList.add("Term End");
			       examTypeList.add("Portfolio");
			       examTypeList.add("Multiple Assessment");
			       
			       	for(ConfigurationForm form:examinatrionList){
			       %>
			       
			       	<tr id="newTRID<%=form.getExaminationID()%>">
			       		
			       		<td>
			       			<input type="hidden" name="editExamID" value="<%=form.getExaminationID()%>">
			       			<select id="" name="editTerm" class="form-control">
			       				<option value="-1">Select Term</option>
			       				<%
			       					if(form.getTerm().equals("Term I")){
			       				%>
			       				<option value="Term I" selected="selected">Term I</option>
			       				<option value="Term II">Term II</option>
			       				<%
			       					}else if(form.getTerm().equals("Term II")){
			       				%>
			       				<option value="Term I" >Term I</option>
			       				<option value="Term II" selected="selected">Term II</option>
			       				<%
			       					}else{
			       				%>
			       				<option value="Term I" >Term I</option>
			       				<option value="Term II">Term II</option>
			       				<%
			       					}
			       				%>
			       			</select>
			       		</td>
			       		
			       		<td>
			       			<input type="text" name="editExamNameID" class="form-control" value="<%=form.getExamName() %>">
			       		</td>
			       		
			       		<td>
			       			<select id="" name="editExamTypeID" class="form-control">
			       				<option value="-1">Select Examination Type</option>
			       			<%
			       				for(String examType:examTypeList){
			       					if(examType.equals(form.getExamType())){
			       			%>
			       				<option value="<%=examType %>" selected="selected"><%=examType %></option>
			       			<%
			       					}else{
			       			%>
			       				<option value="<%=examType %>"><%=examType %></option>
			       			<%
			       					}
			       				}
			       			%>
			       			</select>
			       		</td>
			       		
			       		<td>
			       		<div class="input-group date ui-datepicker">
							<input type="text" name="editStartDateID" class="form-control" value="<%=form.getStartDate() %>">
	                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                 		</div>
                 		</td>
                 		
                 		<td >
                 		<div class="input-group date ui-datepicker">
	               			<input type="text" name="editEndDateID" class="form-control" value="<%=form.getEndDate() %>">
	                      	<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  		</div>
                  		</td>
			       		
			       		<td><img src='images/delete.png' style='height:24px;cursor: pointer;'
							onclick="deleteRow1(<%=form.getExaminationID()%>,'newTRID<%=form.getExaminationID()%>');" 
							alt='Delete row' title='Delete row'/></td>
			       		
			       	</tr>
			       <!-- <td>Time Table</td> -->
			       <%
			       	}
			       %>
			       
			       </table>
			    </div>
		       	<div class="form-group">
		            <div class="col-md-12" align="center">
		  	          <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
		               <button type="submit"  class="btn btn-success" id="submitID" onclick="submitForm('submitID');" >Save</button>
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

<%-- <script src="./js/libs/jquery-1.10.1.min.js"></script> --%>
  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  <script src="./js/libs/bootstrap.min.js"></script>

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
  


  
</body>
</html>
