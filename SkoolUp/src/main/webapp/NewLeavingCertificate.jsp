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
  <title>शाळा सोडल्याचे प्रमाणपत्र - SkoolUp</title>
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
		$('#leavingCertificateLiID').addClass("active");
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
		LoginForm form = (LoginForm) session.getAttribute("USER");

		int userID = form.getUserID();
		
		LoginDAOInf daoInf = new LoginDAOImpl();
		
		String AcademicYearName = daoInf.retrieveAcademicYearName(form.getOrganizationID());
		
		String LeavingCertificatePrint = (String) request.getAttribute("LeavingCertificatePrint");
		
		if(LeavingCertificatePrint == null || LeavingCertificatePrint == ""){
			LeavingCertificatePrint = "dummy";
		}
	%>

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
  		
         document.location="manageleavingCertificate.jsp";
      }
      
 
      function PrintCertificate(subID){
    	 
	    	$("#validate-basic").attr("action","LeavingCertificatePDF");
	  		$("#validate-basic").submit();
			  	  
		 	$("#"+subID).attr("disabled", "disabled");
  		}
      
      function SubmitForm(subID){
    	  
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
	    	
	    	$("#validate-basic").attr("action","ConfigureLeavingCertificate");
	    	$("#validate-basic").submit();
			  	  
		 	$("#"+subID).attr("disabled", "disabled");
      }
	   
 </script>
	
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
        <h2 class="content-header-title">शाळा सोडल्याचे प्रमाणपत्र</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">मुख्यपृष्ठ</a></li>
          <li><a href="RenderloadLeavingStudent">विद्यार्थ्यांची माहिती</a></li>
          <li class="active">शाळा सोडल्याचे प्रमाणपत्र</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
           		     विद्यार्थ्यांची माहिती
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
		    
		<form id="validate-basic" action="ConfigureLeavingCertificate" method="POST" data-validate="parsley" class="form parsley-form">
		  
		<div class="row">  
		  
			 <div class="col-lg-2" style="text-align: center;"> 
				   
				<img src="<%= daoInf.retrieveBoardLogo(form.getOrganizationID()) %>" style="height: 100px;" alt="Board Logo">
			</div>
			  
			<div class="col-lg-8" style="text-align: center;font-weight: bold;">
			  	
			    <%= daoInf.retrieveOrganizationTagLine(form.getOrganizationID()) %>
			  	<br>
			    <%= daoInf.retrieveOrganizationName(form.getOrganizationID()) %>
			    <br>
				<%= daoInf.retrieveOrganizationAddress(form.getOrganizationID()) %>
				<br>
				
				<div class="col-md-4">
				<%= daoInf.retrieveOrganizationPhone(form.getOrganizationID()) %></div>
				<div class="col-md-6">Email:&nbsp;<%= daoInf.retrieveOrganizationEmail(form.getOrganizationID()) %></div>
			</div>	
				
			<div class="col-lg-2" style="text-align: center;">
				<img src="<%= daoInf.retrieveOrganizationLogo(form.getOrganizationID()) %>" style="height: 100px;" alt="Organization Logo">
			</div>
		
		</div>
		
		<hr style="border: 1px solid gray;">
		
		<div class="row" style="margin: 0px;">
			 
			 <input type="hidden" name="studentID" value="<s:property value="studentID"/>">
		      <input type="hidden" name="registrationID" value="<s:property value="registrationID"/>">
		      <input type="hidden" name="AYClassID" value="<s:property value="AYClassID"/>">
		      <input type="hidden" name="studentName" value="<s:property value="studentName"/>">
		      
			<s:iterator  value="studentDetailsList">
			  <div class="row" style="font-weight: bold;" align="center">
					<div class="col-lg-4">यु डायस क्र.27251400611</div>
				                          
				   <div class="col-lg-4">दाखल क्र.1130690</div>
				
					<div class="col-lg-4">शाळा सांकेतिक क्रमांक.३०५९६</div>
			 </div>
		      
		     <div class="row">
				<div class="col-lg-12" style="text-align: center;font-weight: bold;">
	             	<h1 style=" padding-top: 10px;  padding-bottom: 10px; ">शाळा सोडल्याचे प्रमाणपत्र</h1>
	        	</div>
        	 </div>	        
			<div><p>(या प्रमाणपत्रात कोणत्याही प्रवेशामध्ये कोणताही बदल वगळता अन्य गोष्टींचा समावेश नाही प्राधिकरणाद्वारे ते जारी करणे आणि कोणतेही उल्लंघन जबाबदार असल्यास दंड लागू.)</p>
			</div>	
			<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		       <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					<tr>
						<td style="width: 3%;">अ क्र.</td>
						<td style="width: 7%;">
							<div class="col-md-4">
								<input type="text" name="serialNo" class="form-control" value="<s:property value="serialNo"/>" placeholder="Serial No" data-required="true" >
							</div>
							<div class="col-md-4" align="right" style="padding-top: 7px;">बुक क्र.:</div>
							<div class="col-md-4">
								<input type="text" name="bookNo" class="form-control" value="<s:property value="bookNo"/>" placeholder="Book No." data-required="true" >
							</div>
						</td>
					</tr>
					<tr>
						<td style="width: 3%;">स्टुडन्ट आय डी.</td>
						<td style="width: 7%;">
							<input type="number" name="studentNo" class="form-control" value="<s:property value="studentNo"/>" placeholder="Student Number" 
							data-required="true" maxlength="19" oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);" >
						</td>
					</tr>
					<tr>
						<td style="width: 3%;">ऍडमिशन नं .</td>
						<td style="width: 7%;"><s:property value="grNumber"/></td>
					</tr>
					<tr>
						<td style="width: 3%;">विद्यार्थ्याचे संपूर्ण नाव</td>
						<td style="width: 7%;"><s:property value="studentName"/></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">आईचे संपूर्ण नाव</td>
						<td style="width: 7%;"><s:property value="motherName"/></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">वडिलांचे संपूर्ण नाव</td>
						<td style="width: 7%;"><s:property value="fatherName"/></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">यू आय डी नं (आधार कार्ड क्रमांक) </td>
						<td style="width: 7%;"><s:property value="aadhaar"/></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">जन्मतारीख(इ सनाप्रमाणे)</td>
						<td style="width: 7%;"><div class="col-md-3"><s:property value="dateOfBirth"/><br>(dd/MM/yyyy)</div>
							<div class="col-md-2" align="right" style="padding-top: 7px;">In Words:</div>
							<div class="col-md-7" style="padding-top: 7px;"><s:property value="dateOfBirthInWords"/></div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">जन्मस्थान.(ता .डिस्ट. स्टेट)</td>
						<td style="width: 7%;">
							<input type="text" name="birthPlace" class="form-control" value="<s:property value="birthPlace"/>" placeholder="जन्मस्थान" data-required="true" >
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">राष्ट्रीयत्व</td>
						<td style="width: 7%;">
							<div class="col-md-4">
								<input type="text" name="nationality" class="form-control" value="<s:property value="nationality"/>" placeholder="Nationality" data-required="true" value="भारत">
							</div>
							<div class="col-md-4" align="right" style="padding-top: 7px;">मातृभाषा:</div>
							<div class="col-md-4">
								<input type="text" name="motherTongue" class="form-control" value="<s:property value="motherTongue"/>" placeholder="Mother Tongue" data-required="true" value="मराठी">
							</div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">धर्म, जाती, उपजाती</td>
						<td style="width: 7%;">
							<div class="col-md-2">धर्म:&nbsp;<s:property value="religion"/></div>
							<div class="col-md-2" align="right" style="padding-top: 7px;">जाती:</div>
							<div class="col-md-3"><input type="text" name="caste" class="form-control" value="<s:property value="caste"/>" placeholder="Caste" data-required="true" ></div>
							<div class="col-md-2" align="right" style="padding-top: 7px;">उपजाती:</div>
							<div class="col-md-3"><input type="text" name="subCaste" class="form-control" value="<s:property value="subCaste"/>" placeholder="Sub Caste" data-required="true" ></div>
						</td>
					</tr>
					
		<script type="text/javascript">
		
			function categoryCheck(radioValue) {
					
				$("#categoryID").val(radioValue);
					
			}
			
				document.addEventListener('DOMContentLoaded', function() {
					var Details = $("#categoryID").val();
					
					$("input[name = categoryYes][value = "+Details+"]").prop("checked", true);
				});
				
		</script>
		
					<tr>
						<td style="width: 3%;">उमेदवार अनुसूचित जातीचा किंवा अनुसूची जमातीचा असो</td>
						<td style="width: 7%;">
							<input type="radio" name="categoryYes" value="Yes" id="categoryYesID" onclick="categoryCheck(this.value);"/>Yes
						    <input type="radio" name="categoryYes" value="No" id="categoryNoID" onclick="categoryCheck(this.value);"/>No
						    <input type="hidden" name="category" value="<s:property value="category"/>" id="categoryID" />
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">शेवटची शाळा हजेरी लावली</td>
						<td style="width: 7%;">
							<div class="col-md-6">
								<input type="text" name="lastSchoolAttended" class="form-control" value="<s:property value="lastSchoolAttended"/>" placeholder="Last School Attended" data-required="true" >
							</div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">शाळा व वर्गात प्रवेशाची तारीख</td>
						<td style="width: 7%;">
						  <div class="col-md-6">
							<div class="input-group date ui-datepicker">
			                   <input type="text" id="admissionDate" name="admissionDate" value="<s:property value="admissionDate"/>" class="form-control" placeholder="Date Of Admission(dd/MM/yyyy)" data-required="true">
			                   <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			                </div>
			              </div>
			              <div class="col-md-2" align="right" style="padding-top: 7px;">वर्ग:</div>
			              <div class="col-md-4">
			              	<input type="text" name="firstClass" class="form-control" value="<s:property value="firstClass"/>" placeholder="Class" data-required="true" >
			              </div>
			            </td>
					</tr>
					
					<tr>
					 <td style="width: 3%;">विद्यार्थ्याने शेवटचा अभ्यास केलेला वर्ग</td>
					 <td>
						<div class="col-md-5">
							<input type="text" name="lastStudiedClass" class="form-control" value="<s:property value="lastStudiedClass"/>" placeholder="In Figures" data-required="true" >
						</div>
						<div class="col-md-5">
							<input type="text" name="lastStudiedClassWords" class="form-control" value="<s:property value="lastStudiedClassWords"/>" placeholder="In Words" data-required="true" >
						</div>
					  </td>
					</tr>
					
					<tr>
						<td style="width: 3%;">शाळा /बोर्डाची वार्षिक परीक्षा अंतिम निकालासह घेण्यात आली</td>
						<td style="width: 7%;">
						<div class="col-md-5">
							<input type="text" name="result" class="form-control" value="<s:property value="result"/>" placeholder="School/Board" data-required="true" value="CBSC">
						</div>
						</td>
					</tr>
					 
					<tr>
						<td style="width: 3%;">विषय</td>
						<td style="width: 7%;"><s:property value="subject"/></td>
					</tr>
					
		<script type="text/javascript">
		
			function radioCheck(radioValue) {
					
				$("#higherClassID").val(radioValue);
				
				if(radioValue == "Yes"){
					
					$("#wichClassRadio").show();
				}else if(radioValue == "No"){
					
					$("#wichClassRadio").hide();
					$("#wichClassID").val("-"); 
					$("#wichClassWordsID").val("-");
				}else{
					
					$("#wichClassRadio").hide();
					$("#wichClassID").val(""); 
					$("#wichClassWordsID").val("");
				}
			}
			
				document.addEventListener('DOMContentLoaded', function() {
					var Details = $("#higherClassID").val();
					
					$("input[name = higherClassYes][value = "+Details+"]").prop("checked", true);
				
					if(Details == "Yes"){
						$("#wichClassRadio").show();
					}else if(radioValue == "No"){
						$("#wichClassRadio").hide();
						$("#wichClassID").val("-"); 
						$("#wichClassWordsID").val("-");
					}else{
						
						$("#wichClassRadio").hide();
						$("#wichClassID").val(""); 
						$("#wichClassWordsID").val("");
					}
				});
				
		</script>
	
					<tr>
						<td style="width: 3%;">उच्च वर्गात पदोन्नतीसाठी पात्र असो की नाही</td>
						<td style="width: 7%;">
							<div class="col-md-2" >
								<input type="radio" name="higherClassYes" value="Yes" id="higherClassYesID" onclick="radioCheck(this.value);" />होय
						        <input type="radio" name="higherClassYes" value="No" id="higherClassNoID" onclick="radioCheck(this.value);" />नाही
						        
						        <input type="hidden" name="higherClass" value="<s:property value="higherClass"/>" id="higherClassID" />
						    </div>
						    <div class="col-md-10" id="wichClassRadio" style="display: none;" >
								<div class="col-md-4" align="right" style="padding-top: 7px;">कोणत्या वर्गात(अक्षरी व अंकी ):</div>
								<div class="col-md-2"><input type="text" name="wichClass" class="form-control" placeholder="In figure" id="wichClassID" value="<s:property value="wichClass"/>" data-required="true" ></div>
								<div class="col-md-2" align="right" style="padding-top: 7px;">अक्षरी:</div><div class="col-md-2"><input type="text" name="wichClassWords" class="form-control" placeholder="In Words" id="wichClassWordsID" value="<s:property value="wichClassWords"/>" data-required="true" ></div>
							</div>
						</td>
					</tr>
					 
					<tr>
						<td style="width: 3%;">विद्यार्थ्याने शाळेची थकबाकी भरलेला महिना</td>
						<td style="width: 7%;"><input type="text" name="duesMonths" class="form-control" value="<s:property value="duesMonths"/>" placeholder="In figure" data-required="true" ></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">कोणतीही फी सवलत घेतली.</td>
						<td style="width: 7%;">
						<div class="col-md-4">
							<input type="text" name="feeConcession" class="form-control" value="<s:property value="feeConcession"/>" id="feeConcessionID" placeholder="Fee Concession" />
						</div>
						<div class="col-md-8" id="concessionRadio" style="display: none;">
							<div class="col-md-4" align="right" style="padding-top: 7px;">अशा सवलतीचे स्वरूप:</div>
							<div class="col-md-4" >
								<input type="text" name="concession" class="form-control" value="<s:property value="concession"/>" placeholder="Concession" data-required="true" >
							</div>
						</div>
						</td>
					</tr>
				
					<tr>
						<td style="width: 3%;">शैक्षणिक सत्रात कार्यरत दिवसांची एकूण संख्या</td>
						<td style="width: 7%;">
							<div class="col-md-3">
								<input type="number" name="workingDays" class="form-control" value="<s:property value="workingDays"/>" placeholder="Working days" data-required="true" >
							</div>
							<div class="col-md-6" align="right" style="padding-top: 7px;">शाळेत कार्यरत कामकाजाच्या एकूण विद्यार्थ्यांची संख्या</div>
							<div class="col-md-3">
								<input type="number" name="presentDays" class="form-control" value="<s:property value="presentDays"/>" placeholder="In figure" data-required="true" >
							</div>
						</td>
					</tr>
				
					<tr>
						<td style="width: 3%;">एनसीसी कॅडेट / बॉय स्काऊट / गर्ल गाईड असो</td>
						<td style="width: 7%;"><input type="text" name="nccGuide" class="form-control" value="<s:property value="nccGuide"/>" placeholder="NCC Guide" data-required="true" ></td>
					</tr>
				
					<tr>
						<td style="width: 3%;">गेम्स खेळले किंवा बाह्य क्रियाकलाप ज्यामध्ये सामान्यत: विद्यार्थ्यांनी भाग घेतला</td>
						<td style="width: 7%;"><div class="col-md-6"><s:property value="physicalActivity"/></div></td>
					</tr>
				
					<tr>
						<td style="width: 3%;">सामान्य आचरण</td>
						<td style="width: 7%;">
							<div class="col-md-4">
								<input type="text" name="generalConduct" class="form-control" value="<s:property value="generalConduct"/>" placeholder="General Conduct" data-required="true" value="Good" >
							</div>
							<div class="col-md-3" align="right" style="padding-top: 7px;">शैक्षणिक प्रगती:</div>
							<div class="col-md-4">
								<input type="text" name="academicProgress" class="form-control" value="<s:property value="academicProgress"/>" placeholder="Academic progress" data-required="true" >
							</div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">प्रमाणपत्र देण्याची तारीख</td>
						<td style="width: 7%;">
						<div class="col-md-6">
							<div class="input-group date ui-datepicker">
			                   <input type="text" id="dateOfapplication" name="dateOfapplication" class="form-control" value="<s:property value="dateOfapplication"/>" placeholder="Date Of Application(dd/MM/yyyy)" data-required="true">
			                   <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			                </div>
			            </div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;"> शाळा सोडण्याची कारणे</td>
						<td style="width: 7%;"><input type="text" name="reasons" class="form-control" value="<s:property value="reasons"/>" placeholder="Reasons" data-required="true" ></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">इतर कोणत्याही टिप्पण्या</td>
						<td style="width: 7%;"><input type="text" name="remarks" class="form-control" value="<s:property value="remarks"/>" placeholder="Remarks" data-required="true" ></td>
					</tr>
					
				  </table>
			   </div>
			   
			   <div><p>टीपः वरील माहिती शाळा नोंदणी नुसार असल्याचे प्रमाणित केले आहे</p>
			</div>	
			
		  </s:iterator>
		</div>

		<div class="row" style="padding-top: 45px;" align="center">
			<div class="col-lg-4" >
				<b>वर्गशिक्षकाची स्वाक्षरी</b>
			</div>
		 		 
			<div class="col-lg-3" style="text-align: center;">
				<b>लेखनिक</b>
			</div>
			
			<div class="col-lg-5" style="text-align: center;">
				<b>मुख्याध्यापिकांची स्वाक्षरी</b>
			</div>	
		</div>
			
		<div  class="row" align="center" style="margin-top: 5%;">
			<button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
			<button type="button" class="btn btn-success" id="submit" onclick ="SubmitForm('submit');">Save</button>
		
		<%if(LeavingCertificatePrint == "Enabled"){ %>	
	        <button type="button" class="btn btn-warning" id="printSubmit" onclick ="PrintCertificate('printSubmit');">Print</button>
	    <%} %>      
	   
	   </div>
         
	</form>
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
