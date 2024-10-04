<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="java.util.HashMap"%>
<%@page import="com.kovidRMS.daoInf.*"%>
<%@page import="com.kovidRMS.daoImpl.*"%>
<%@page import="com.kovidRMS.form.*"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
      
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Issue Staff Books - SkoolUp</title>
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
  <%
	LoginDAOInf daoinf = new LoginDAOImpl();

	String ShelfListCheck = (String) request.getAttribute("ShelfListCheck");
  	
  	if(ShelfListCheck == null || ShelfListCheck == ""){
  		ShelfListCheck = "dummy";
	}
  	
  %>
<script type="text/javascript">
	$(document).ready(function(){
		$('#issueLiID').addClass("active");
		//alert("hiii");	
	});

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
		
        document.location="RenderconfigureStudentIssue";
      }
	
	 
	function submitForm(loadFormID, subID, Action){
		console.log("inside log.....");
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
	 
</script>
    
<script type="text/javascript">
	
	function retrieveStaff(role){
			
		if(role == "-1"){
			alert("No class is selected. Please select class.");
				
			var array_element = "<select name='' id='' class='form-control'"+
			"> <option value='-1'>Select Staff</option></select>";
				
			document.getElementById("StudID").innerHTML = array_element;
	
			$("#TableDivID").hide();
			$("#TableDiv1ID").hide();
			$("#scanValueID").val("");
			$("#divTableID").hide();
			$("#BookDetailsIssueID").hide();
			$("#BookDetailsID").html("");
			$("#showIssueBookID").attr("disabled",false);
			
		}else{
				
			retrieveStaff1(role);
			$("#TableDivID").hide();
			$("#TableDiv1ID").hide();
			$("#scanValueID").val("");
			$("#divTableID").hide();
			$("#BookDetailsIssueID").hide();
			$("#BookDetailsID").html("");
			$("#showIssueBookID").attr("disabled",false);
		}
			
	}
	
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function retrieveStaff1(role) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "<select name='staffID' class='form-control'"+
						"> <option value='-1'>Select Staff</option>";
						
						var check = 0;
						var StudentID=0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
							StaffID = array.Release[i].staffID;
							array_element += "<option value='"+StaffID+"'>"+array.Release[i].staffName+"</option>";
						}
	
						array_element += " </select>";
						
						if(check == 0){
							
							alert("No Staff found");
							
							var array_element = "<select name='' id='' class='form-control'"+
							"> <option value='-1'>Select Staff</option></select>";
							
							document.getElementById("StudID").innerHTML = array_element;
							$("#TableDivID").hide();
							$("#TableDiv1ID").hide();
							$("#scanValueID").val("");
							$("#BookDetailsIssueID").hide();
							
						}else{
							
							document.getElementById("StudID").innerHTML = array_element;	
							
							$("#TableDivID").hide();
							$("#TableDiv1ID").hide();
							$("#scanValueID").val("");
							$("#BookDetailsIssueID").hide();
						}
				}
			};
			xmlhttp.open("GET", "RetrieveStaffDetailsByRole?role="
					+ role+"&status=Staff", true);
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
	
	function retrieveRulesDetails(staffID) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var BookCount = 0;;
				var IssueDays = 0;
				var FinePerDay = 0;

				for ( var i = 0; i < array.Release.length; i++) {

					BookCount = array.Release[i].bookCount;
					IssueDays = array.Release[i].issueDays;
					FinePerDay = array.Release[i].finePerDay;
					check = array.Release[i].check;
				}
				
				if(check == 1){
					
					$("#finePerDayID").val(FinePerDay);
					
					$("#issueDaysNewID").val(IssueDays);
					
					if(IssueDays != 0 ){
				
						var date = new Date();
						
						var NewDate = new Date(date);
						
						NewDate.setDate(NewDate.getDate() + IssueDays);
						    
						var dd = NewDate.getDate();
						var mm = NewDate.getMonth() + 1;
						var y = NewDate.getFullYear();
		
						if(dd<10) {
							dd='0'+dd
						}
						if(mm<10) {
							mm='0'+mm
						}
						
						var DateToBeFormatted = dd + '/' + mm + '/' + y;
						
						$("#expectedReturnDateID").val(DateToBeFormatted);
					}
					
					retrieveStaffsBookIssueCount(staffID, BookCount);
				}
				
			}
		};
		xmlhttp.open("GET", "RetrieveRulesDetailsByClassLevel?level=Staff" + "&genre=''", true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	
	
	function retrieveStaffsBookIssueCount(staffID, BookCount) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var Count = 0;

				for ( var i = 0; i < array.Release.length; i++) {

					check = array.Release[i].check;
				}
				
				if(check == 0){
					
					$("#showIssueBookID").attr("disabled",false);
				
				}else{
					
					$("#showIssueBookID").attr("disabled",true);
				}
				
			}
			
		};
		xmlhttp.open("GET", "RetrieveStaffsBookIssueCount?staffID="
				+ staffID  +"&bookCount=" + BookCount, true);
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
	
		function retrieveIssuedBooks(staffID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var check = 0;
					
					var StaffIssueID;
					var BookID;
					var BookName;
					var AccNo;
					var IssueDate;
					var ExpectedDate
					var count = 1;
					
					
					var tableTag = "<div class='form-group' style='margin-top: 74px;width: 93%;padding-left: 75px;'><table class='table table-striped table-bordered table-hover table-highlight table-checkable' "
	                			+"data-provide='datatable' data-display-rows='10' data-info='true' data-search='true' data-length-change='true'"
	                    		+"data-paginate='true' border='1' style='font-size: 14px;'>"
								+ "<thead><tr style='padding:5px;'><th data-sortable='true'>Sr No.</th><th data-sortable='true'>Book Name</th><th data-sortable='true'>Accession No.</th><th data-sortable='true'>Issue Date</th><th data-sortable='true'>Expected Date</th><th style='text-align:center;'>Action</th></tr></thead>";
								
					for ( var i = 0; i < array.Release.length; i++) {

						 StaffIssueID = array.Release[i].staffIssueID;
						 BookID = array.Release[i].bookID;
						 BookName = array.Release[i].bookName;
						 AccNo = array.Release[i].accNum;
					 	 IssueDate = array.Release[i].issueDate;
						 ExpectedDate = array.Release[i].expectedReturnDate;
						 check = array.Release[i].check;
						
						tableTag += "<tbody><tr><td>"+count+"</td>";

						tableTag += "<td><input type='hidden' name='bookID' value='"+BookID+"'>"+BookName+"</td>";
	                     	
						tableTag += "<td>"+AccNo+"</td>";
						
						tableTag += "<td>"+IssueDate+"</td>";
                     	
						tableTag += "<td>"+ExpectedDate+"</td>";
						
						tableTag += "<td align='center'><a href='javascript:returnBook(\"" + StaffIssueID + "\", \"" + BookID + "\");'>Return</a></td>";
						tableTag += "</tr></tbody>";
						
						count++;
					}
					tableTag += "</table><div class='col-md-6 col-lg-6 col-sm-6 col-xs-6' style='margin-top:15px;'>";
					
					if(check == 0){
						
						alert("Please Issue books first.");
					
						$("#divTableID").html("");
						$("#BookDetailsID").html("");
						$("#BookDetailsIssueID").hide();
						$("#TableDivID").hide();
						$("#TableDiv1ID").hide();
						$("#scanValueID").val("");
						
					}else{
						
						$("#divTableID").show();
						$("#BookDetailsID").html("");
						$("#BookDetailsIssueID").hide();
						$("#TableDivID").hide();
						$("#TableDiv1ID").hide();
						$("#scanValueID").val("");
						$("#divTableID").html(tableTag);
					}
				
					retrieveRulesDetails(staffID);
				}
					
			};
			xmlhttp.open("GET", "RetrieveStaffsIssuedBooks?staffID="
					+ staffID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
	</script>
	
<script type="text/javascript">
	
	function returnBook(StaffIssueID, BookID) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
				var check = 0;
				var BookName;
				var IssueDate;
				var ExpectedDate;
				var ExpectedDateNew;
				var StaffID;
				var Fine=0;
				
				var trTag = "";
				
				var trID = "";

				var date = new Date();
				
				var dd = date.getDate();
				var mm = date.getMonth() + 1;
				var y = date.getFullYear();

				if(dd<10) {
					dd='0'+dd
				}
				if(mm<10) {
					mm='0'+mm
				}
					
				var DateToBeFormatted = dd + '-' + mm + '-' + y;
				
				var DateToBeFormattedNew = mm + '-' + dd + '-' + y;
				
				var DelayDays;
				var FinalDelayDays;
				
				var FinePerDay = $("#finePerDayID").val();
			
				for ( var i = 0; i < array.Release.length; i++) {

					check = array.Release[i].check;
					BookName = array.Release[i].bookName;
					IssueDate = array.Release[i].issueDate;
					ExpectedDate = array.Release[i].expectedReturnDate;
					ExpectedDateNew = array.Release[i].expectedReturnDateNew; 
					
					var date1 = new Date(ExpectedDateNew);
					var date2 = new Date(DateToBeFormattedNew);
					DelayDays = date2.getDate()  - date1.getDate() ; 
					
					//console.log(ExpectedDate+"--"+ExpectedDateNew);
					if(DelayDays>0){
						
						FinalDelayDays = DelayDays;
						Fine = FinePerDay*DelayDays;
					}else{
						
						FinalDelayDays = 0;
						Fine=0;
					}
				}
				
				trTag += "<tr style='font-size: 15px; '><td >Book Name </td><td > : </td></b><td style='padding-left: 20px;'><input type='hidden' id='returnbookIssueID' name='bookID' value='"+BookID+"' ><input type='hidden' id='returnStaffIssueID' name='staffIssueID' value='"+StaffIssueID+"' >"+BookName+"</td></tr>"
				+"<tr style='font-size: 15px; '><td style='padding-top: 20px;'>Issue Date </td><td style='padding-top: 20px;'> : </td><td style='padding-left: 20px;padding-top: 20px;'>"+IssueDate+"</td></tr>"
				+"<tr style='font-size: 15px; '><td style='padding-top: 20px;'>Expected Return Date </td><td style='padding-top: 20px;'> : </td><td style='padding-left: 20px;padding-top: 20px;'><input type='hidden' id='expectedDateID' name='expectedReturnDate' value='"+ExpectedDate+"' >"+ExpectedDate+"</td></tr>"  
				+"<tr style='font-size: 15px; '><td style='padding-top: 20px;'>Return Date </td><td style='padding-top: 20px;'> : </td><td style='padding-left: 20px;padding-top: 20px;'><div class='row'><div class='col-md-6'><input type='text' name='returnDate' id='returnDateID' onkeyup='retrieveDate(this.value, expectedDateID.value, "+FinePerDay+");' class='form-control' style='width: 100px;' value='"+DateToBeFormatted+"' placeholder='Return Date(dd/mm/yyyy)' data-required='true'></div><div class='col-md-6' style='padding-top: 5px;padding-left: 5px'>(dd-mm-yyyy)</div></div></td></tr>"
		        +"<tr style='font-size: 15px; '><td style='padding-top: 20px;'>Delay Days </td><td style='padding-top: 20px;'> : </td><td style='padding-left: 20px;padding-top: 20px;'><input type='number' id='delayID' name='delayDays' class='form-control' style='width: 75px;' readonly='readonly' value='"+FinalDelayDays+"'></td></tr>"
				+"<tr style='font-size: 15px; '><td style='padding-top: 20px;'>Fine </td><td style='padding-top: 20px;'> : </td><td style='padding-left: 20px;padding-top: 20px;'><div class='row'><div class='col-md-4'><input type='number' id='FineID' name='fineAmount'  class='form-control' style='width: 75px;' readonly='readonly' value='"+Fine+"' ></div><div class='col-md-6' style='padding-top: 5px;'><span style='font-size: 17px;'>&#8377</span></div></div></td></tr>";
						
				trTag += "</tr>";
				
				$("#TableTRID").html(trTag);
				
				$("#myModal").modal("show");  
			  	
			}
			
		};
		xmlhttp.open("GET", "RetrieveStaffDetailsForBookReturn?staffIssueID="
				+ StaffIssueID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	
</script>


<script type="text/javascript">
// calculate difference between two dates on changing the current date

	function retrieveDate(ReturnDate, ExpectedDate, FinePerDay){
		var a = moment(ExpectedDate, 'DD/MM/YYYY');
		var b = moment(ReturnDate, 'DD/MM/YYYY');
		
		var DelayDays;
		var FinalDelayDays;
		var Fine=0;
		
		var DelayDays = b.diff(a, 'days');
		
		//console.log("Date :"+DelayDays);
		
		if(DelayDays>0){
			
			FinalDelayDays = DelayDays;
			Fine = FinePerDay*DelayDays;
			//console.log(FinePerDay+"=>"+FinalDelayDays+"=>"+Fine);
			$("#delayID").val(FinalDelayDays);
			$("#FineID").val(Fine);
			
		}else{
				
			FinalDelayDays = 0;
			Fine=0;
			//console.log(FinalDelayDays+"=>"+Fine);
			$("#delayID").val(FinalDelayDays);
			$("#FineID").val(Fine);
			
		}

	}	
	
	//retrieve expected date on changing the current date 
	
	function RetrieveexpectedDate(IssueDate, IssueDays){
		
		var new_date = moment(IssueDate, "DD-MM-YYYY").add(IssueDays, 'days');

		var day = new_date.format('DD');
		var month = new_date.format('MM');
		var year = new_date.format('YYYY');
		
		var NewDate = day + '/' + month + '/' + year;
		
		//console.log("Final date : "+NewDate);
		$("#expectedReturnDateID").val(NewDate);
	} 
</script>

<script type="text/javascript">
	function showIssueBook(roleID, staffID){
		
		if(roleID == "-1"){
			alert("Please select role.");
		}else if(staffID == "-1"){
			alert("Please select staff.");
		}else{
	
			$("#TableDivID").show();
			$("#TableDiv1ID").show();
			$("#BookDetailsIssueID").hide();
			$("#staffHiddenID").val(staffID);
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

	function retrieveBooks(ScansValue) {
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var check = 0;
				
				var BookID;
				var BookName;
				var AccNo;
				var Barcode;
				var Genre;
				var Status
				
				var tableTag = "<div class='col-md-6' style='margin-top:15px;padding-left: 20%;width: 82%;align:centre;'><table class='table table-striped table-bordered table-hover table-highlight table-checkable' "
                			+"data-provide='datatable' data-display-rows='10' data-info='true' data-search='true' data-length-change='true'"
                    		+"data-paginate='true' border='1' style='font-size: 14px;'>"
							+ "<thead><tr style='padding:5px;'><th data-sortable='true' style='width: 30%;'>Book Name</th><th data-sortable='true' style='width: 25%;'>Accession No.</th><th data-sortable='true' style='width: 25%;'>Barcode</th><th data-sortable='true' style='width: 25%;'>Genre</th><th style='width: 25%;'>Status</th></tr></thead>";
							 
				for ( var i = 0; i < array.Release.length; i++) {

					 BookID = array.Release[i].bookID;
					 BookName = array.Release[i].bookName;
					 AccNo = array.Release[i].accNum;
					 Barcode = array.Release[i].barcode;
					 Genre = array.Release[i].genre;
					 Status = array.Release[i].status;
					 check = array.Release[i].check;
					
					tableTag += "<tbody><tr><td>"+BookName+"</td>";

					tableTag += "<td>"+AccNo+"</td>";
                     	
					tableTag += "<td>"+Barcode+"</td>";
					
					tableTag += "<td>"+Genre+"</td>";
                 	
					tableTag += "<td>"+Status+"</td>";
					
					tableTag += "</tr></tbody>";
					
				}
				tableTag += "</table><div class='col-md-6 col-lg-6 col-sm-6 col-xs-6' style='margin-top:15px;'>";
				
				if(check == 0){
					
					alert("Please enter valid Barcode/Accession No.");
				
					$("#BookDetailsID").html("");
					$("#BookDetailsIssueID").hide();
					
				}else{
					
					if(Status == "available"){
						
						$("#BookDetailsID").html(tableTag);
						$("#staffBookID").val(BookID);
						$("#BookDetailsIssueID").show();
						
					}else{
						
						$("#BookDetailsID").html(tableTag);
						$("#BookDetailsIssueID").hide();
					}
					
				}
			
			}
				
		};
		xmlhttp.open("GET", "RetrieveBooksByScanValue?bookScanValue="
				+ ScansValue, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	
</script>
</head>

<body>
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<form id="loadStudentFOrmNew" action="ReturnStaffBooks" method="POST" onsubmit="submitForm('loadStudentFOrmNew', 'submitID', 'ReturnStaffBooks');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

	<div id="myModal" class="modal fade" tabindex="-1"  role="dialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-body">
					<table  id="TableTRID" >
						<input type="hidden" name="finePerDay" id="finePerDayID">
									
					</table>
				</div>
				<div class="modal-footer"style="text-align: center;">
					<button type="button" data-dismiss="modal" onclick="windowOpen1();" class="btn btn-primary">Cancel</button>
		            <button type="submit" id="submitID" class="btn btn-success" >Return</button>
				</div>
			</div>
		</div>
	</div>
</form>

<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Issue Books - Staff</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Issue Books - Staff</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                Staff Books List
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
		              
	           <div class ="row">   
                
                	<div class ="col-md-1">
	                 	<label for="author">Role :<span class="required">*</span></label>
	                </div>
	                 <div class ="col-md-2">
	                 	<s:select class="form-control" list="#{'Teaching':'Teaching', 'NonTeaching':'Non-Teaching'}" id="roleID" value="-1" headerKey="-1" 
	                 		headerValue="Select Role" name="role" onchange="retrieveStaff(this.value);" ></s:select>
	                </div>
	             	
	          		<div class="col-md-1" id ="staffdivID" style="width: 80px;">
		              <label for="Student">Staff :</label>
		            </div>
		            <div class ="col-md-3">
		              <select name="staffID" id="StudID" class="form-control" onchange="retrieveIssuedBooks(this.value);">
					      <option value="-1" >Select Staff</option>
					  </select>
				    </div>
			        
	               <div class ="col-md-2" id ="updateID" style="width: 12%;">
					  <button type="button" class="btn btn-success" id="showIssueBookID" onclick="showIssueBook(roleID.value, StudID.value);">Issue Book</button>
	               </div> 
	              
	               <div class ="col-md-1" id="TableDivID" style="display: none;">
	                 <label>Book<span class="required">*</span>: </label>
	               </div>
	                 	
	               <div class ="col-md-1" id="TableDiv1ID" style="display: none;width: 20%;" >
	                <input type="text" name="bookScanValue" id="scanValueID" required="required" class="form-control" onchange="retrieveBooks(this.value);">
	               </div>
	                 	
	               <div class="row" id="divTableID">
	               <hr>
	               </div>
             </div>    
               
              <form id="loadStudentFOrm" action="IssueStaffBooks" method="POST" onsubmit="submitForm('loadStudentFOrm', 'submitID', 'IssueStaffBooks');" data-validate="parsley" class="form parsley-form">
                    
                 <div class="row" id="BookDetailsID" style=" padding-top: 25px;">
                 	
                 </div>
                 	
                 <div class="row" id="BookDetailsIssueID" style="display: none;">
                 	<hr>
	                 <div class ="col-md-1" ></div>
                 	
                 	<%
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
						SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
							
						Date date = new java.util.Date();
							
						String NewDate = dateFormat.format(date);
						
					%>
					<div class ="col-md-3" >
						<label>Date: </label>
						 <input type="hidden" id="staffBookID" name="bookID">
		                 <input type="hidden" name="staffID" id="staffHiddenID">
						 <input type="hidden" name="issueDays" id="issueDaysNewID">
						 
						 <div class="input-group date ui-datepicker">
						   	<input type="text" name="issueDate" id="issueDateID" class="form-control" value="<%= NewDate %>" onchange="RetrieveexpectedDate(this.value, issueDaysNewID.value);" placeholder="Issue Date(dd/mm/yyyy)" data-required="true">
				            <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
				         </div>
					</div>
						 
					<div class ="col-md-1" >
		                 	
		            </div>
		            <div class ="col-md-3" >
		                <label>Expected Return Date: </label>
		          
		              	<div class="input-group date ui-datepicker ">
				           <input type="text" name="expectedReturnDate" id="expectedReturnDateID" class="form-control" placeholder="Expected Return Date(dd/mm/yyyy)" data-required="true">
				           <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
				        </div>
		            </div>  	
                 	
                 	<div class="col-md-4" align="center" style="margin-top: 2%;">
	                 	<button type="submit" class="btn btn-success" id="submitID" >Issue Book</button>
						<button type="button"class="btn btn-primary" onclick="windowOpen1();" >Cancel</button>
					</div>
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
