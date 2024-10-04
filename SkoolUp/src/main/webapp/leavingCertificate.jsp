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
  <title>Leaving Certificate Page - SkoolUp</title>
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

	<%
		LoginForm form = (LoginForm) session.getAttribute("USER");

		int userID = form.getUserID();
		
		LoginDAOInf daoInf = new LoginDAOImpl();
		
		String AcademicYearName = daoInf.retrieveAcademicYearName(form.getOrganizationID());
		
		String LeavingCertificatePrint = (String) request.getAttribute("LeavingCertificatePrint");
		
		if(LeavingCertificatePrint == null || LeavingCertificatePrint == ""){
			LeavingCertificatePrint = "dummy";
		}
		
		ConfigurationDAOInf daoInf2 = new ConfigurationDAOImpl();
		
		String lastSerialNo = daoInf2.retrieveLeavingCertLastSerialNo();
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
  		
    	document.location="LoadLeavingStudent";
    	
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

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Leaving Certificate Page</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li><a href="RenderloadLeavingStudent">Student Details</a></li>
          <li class="active">Leaving Certificate Page</li>
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
					<div class="col-lg-4">U-DISE No.27251400611</div>
				                          
				   <div class="col-lg-4">CBSE Affiliation No.1130690</div>
				
					<div class="col-lg-4">School Code.30596</div>
			 </div>
		      
		     <div class="row">
				<div class="col-lg-12" style="text-align: center;font-weight: bold;">
	             	<h1 style=" padding-top: 10px;  padding-bottom: 10px; ">School Leaving Certificate</h1>
	        	</div>
        	 </div>	        
			<div><p>(No change in any entry in this certificate is to be made except
				by the authority issuing it and any infringement liable to involve the
				imposition of penalty.)</p>
			</div>	
			<!-- Last Serial No -->
			<div class="row col-md-12">
				<h4>
					<u>
						Last Serial No.: <%=lastSerialNo %>
					</u>
				</h4>
			</div>	
			<!-- Serial No end -->
			<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		       <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
					<tr>
						<td style="width: 3%;">Sr No.</td>
						<td style="width: 7%;">
							<div class="col-md-4">
								<input type="text" name="serialNo" class="form-control" value="<s:property value="serialNo"/>" placeholder="Serial No" data-required="true" >
							</div>
							<div class="col-md-4" align="right" style="padding-top: 7px;">Book No.:</div>
							<div class="col-md-4">
								<input type="text" name="bookNo" class="form-control" value="<s:property value="bookNo"/>" placeholder="Book No." data-required="true" >
							</div>
						</td>
					</tr>
					<tr>
						<td style="width: 3%;">Student ID.</td>
						<td style="width: 7%;">
							<input type="number" name="studentNo" class="form-control" value="<s:property value="studentNo"/>" placeholder="Student Number" 
							data-required="true" maxlength="19" oninput="javascript: if (this.value.length > this.maxLength) this.value = this.value.slice(0, this.maxLength);" >
						</td>
					</tr>
					<tr>
						<td style="width: 3%;">Admission No.</td>
						<td style="width: 7%;"><s:property value="grNumber"/></td>
					</tr>
					<tr>
						<td style="width: 3%;">Name of pupil in full</td>
						<td style="width: 7%;"><s:property value="studentName"/></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Mother’s full name</td>
						<td style="width: 7%;"><s:property value="motherName"/></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Father’s full name</td>
						<td style="width: 7%;"><s:property value="fatherName"/></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">UID(Aadhar card) No of Pupil</td>
						<td style="width: 7%;"><s:property value="aadhaar"/></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Date of birth (in Christian Era)</td>
						<td style="width: 7%;"><div class="col-md-3"><s:property value="dateOfBirth"/><br>(dd/MM/yyyy)</div>
							<div class="col-md-2" align="right" style="padding-top: 7px;">In Words:</div>
							<div class="col-md-7" style="padding-top: 7px;"><s:property value="dateOfBirthInWords"/></div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Place of birth. (Tal.Dist.State)</td>
						<td style="width: 7%;">
							<input type="text" name="birthPlace" class="form-control" value="<s:property value="birthPlace"/>" placeholder="Place Of Birth" data-required="true" >
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Nationality</td>
						<td style="width: 7%;">
							<div class="col-md-4">
								<input type="text" name="nationality" class="form-control" value="<s:property value="nationality"/>" placeholder="Nationality" data-required="true" value="India">
							</div>
							<div class="col-md-4" align="right" style="padding-top: 7px;">Mother Tongue:</div>
							<div class="col-md-4">
								<input type="text" name="motherTongue" class="form-control" value="<s:property value="motherTongue"/>" placeholder="Mother Tongue" data-required="true" value="Marathi">
							</div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Religion, Caste, Sub Caste</td>
						<td style="width: 7%;">
							<div class="col-md-2">Religion:&nbsp;<s:property value="religion"/></div>
							<div class="col-md-2" align="right" style="padding-top: 7px;">Caste:</div>
							<div class="col-md-3"><input type="text" name="caste" class="form-control" value="<s:property value="caste"/>" placeholder="Caste" data-required="true" ></div>
							<div class="col-md-2" align="right" style="padding-top: 7px;">Sub Caste:</div>
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
						<td style="width: 3%;">Whether the candidate belongs to Schedule Caste or Schedule Tribe</td>
						<td style="width: 7%;">
							<input type="radio" name="categoryYes" value="Yes" id="categoryYesID" onclick="categoryCheck(this.value);"/>Yes
						    <input type="radio" name="categoryYes" value="No" id="categoryNoID" onclick="categoryCheck(this.value);"/>No
						    <input type="hidden" name="category" value="<s:property value="category"/>" id="categoryID" />
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Last school attended</td>
						<td style="width: 7%;">
							<div class="col-md-6">
								<input type="text" name="lastSchoolAttended" class="form-control" value="<s:property value="lastSchoolAttended"/>" placeholder="Last School Attended" data-required="true" >
							</div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Date of admission in the school & Class</td>
						<td style="width: 7%;">
						  <div class="col-md-6">
							<div class="input-group date ui-datepicker">
			                   <input type="text" id="admissionDate" name="admissionDate" value="<s:property value="admissionDate"/>" class="form-control" placeholder="Date Of Admission(dd/MM/yyyy)" data-required="true">
			                   <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			                </div>
			              </div>
			              <div class="col-md-2" align="right" style="padding-top: 7px;">Class:</div>
			              <div class="col-md-4">
			              	<input type="text" name="firstClass" class="form-control" value="<s:property value="firstClass"/>" placeholder="Class" data-required="true" >
			              </div>
			            </td>
					</tr>
					
					<tr>
					 <td style="width: 3%;">Class in which the pupil last studied</td>
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
						<td style="width: 3%;">School/Board annual examination last taken with result</td>
						<td style="width: 7%;">
						<div class="col-md-5">
							<input type="text" name="result" class="form-control" value="<s:property value="result"/>" placeholder="School/Board" data-required="true" value="CBSC">
						</div>
						</td>
					</tr>
					 
					<tr>
						<td style="width: 3%;">Subjects</td>
						<td style="width: 7%;"><s:property value="subject"/></td>
					</tr>
					
		<script type="text/javascript">
		
			function radioCheck(radioValue) {
					
				$("#higherClassID").val(radioValue);
				   
				if(radioValue == "Promoted"){
					
					$("#wichClassRadio").show();
				}else if(radioValue == "Detained"){
					
					$("#wichClassRadio").show();
				}else{
					
					$("#wichClassRadio").hide();
					$("#wichClassID").val(""); 
					$("#wichClassWordsID").val("");
				}
			}
			
				document.addEventListener('DOMContentLoaded', function() {
					var Details = $("#higherClassID").val();
					
					$("input[name = higherClassYes][value = "+Details+"]").prop("checked", true);
				
					if(Details == "Promoted"){
						$("#wichClassRadio").show();
					}else if(Details == "Detained"){
						$("#wichClassRadio").show();
					}else{
						
						$("#wichClassRadio").hide();
						$("#wichClassID").val(""); 
						$("#wichClassWordsID").val("");
					}
				});
				
		</script>
	
					<tr>
						<td style="width: 3%;">Whether qualified for promotion to the higher class</td>
						<td style="width: 11%;">
							<div class="col-md-2" >
								<input type="radio" name="higherClassYes" value="Promoted" id="higherClassYesID" onclick="radioCheck(this.value);" />Promoted to
						        <input type="radio" name="higherClassYes" value="Detained" id="higherClassNoID" onclick="radioCheck(this.value);" />Detained in
						        <input type="radio" name="higherClassYes" value="NA" id="higherClassNoID" onclick="radioCheck(this.value);" />NA
						        
						        <input type="hidden" name="higherClass" value="<s:property value="higherClass"/>" id="higherClassID" />
						    </div>
						    <div class="col-md-10" id="wichClassRadio" style="display: none;" >
								<div class="col-md-4" align="right" style="padding-top: 7px;">To which class in figure:</div>
								<div class="col-md-2"><input type="text" name="wichClass" class="form-control" placeholder="In figure" id="wichClassID" value="<s:property value="wichClass"/>" data-required="true" ></div>
								<div class="col-md-2" align="right" style="padding-top: 7px;">In Words:</div><div class="col-md-2"><input type="text" name="wichClassWords" class="form-control" placeholder="In Words" id="wichClassWordsID" value="<s:property value="wichClassWords"/>" data-required="true" ></div>
							</div>
						</td>
					</tr>
					 
					<tr>
						<td style="width: 3%;">Month upto which the pupil has paid school dues</td>
						<td style="width: 7%;"><input type="text" name="duesMonths" class="form-control" value="<s:property value="duesMonths"/>" placeholder="In figure" data-required="true" ></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Any fee concession availed of.</td>
						<td style="width: 7%;">
						<div class="col-md-4">
							<input type="text" name="feeConcession" class="form-control" value="<s:property value="feeConcession"/>" id="feeConcessionID" placeholder="Fee Concession" />
						</div>
						<div class="col-md-8" id="concessionRadio" style="display: none;">
							<div class="col-md-4" align="right" style="padding-top: 7px;">The nature of such concession:</div>
							<div class="col-md-4" >
								<input type="text" name="concession" class="form-control" value="<s:property value="concession"/>" placeholder="Concession" data-required="true" >
							</div>
						</div>
						</td>
					</tr>
				
					<tr>
						<td style="width: 3%;">Total No. of working days in the academic session</td>
						<td style="width: 7%;">
							<div class="col-md-3">
								<input type="number" name="workingDays" class="form-control" value="<s:property value="workingDays"/>" placeholder="Working days" data-required="true" >
							</div>
							<div class="col-md-6" align="right" style="padding-top: 7px;">Total No. of working days pupil present in the school</div>
							<div class="col-md-3">
								<input type="number" name="presentDays" class="form-control" value="<s:property value="presentDays"/>" placeholder="In figure" data-required="true" >
							</div>
						</td>
					</tr>
				
					<tr>
						<td style="width: 3%;">Whether NCC Cadet/Boy Scout/Girl guide</td>
						<td style="width: 7%;"><input type="text" name="nccGuide" class="form-control" value="<s:property value="nccGuide"/>" placeholder="NCC Guide" data-required="true" ></td>
					</tr>
				
					<tr>
						<td style="width: 3%;">Games played or extracurricular activities in which the pupil usually took part</td>
						<td style="width: 7%;"><div class="col-md-6"><s:property value="physicalActivity"/></div></td>
					</tr>
				
					<tr>
						<td style="width: 3%;">General conduct</td>
						<td style="width: 7%;">
							<div class="col-md-4">
								<input type="text" name="generalConduct" class="form-control" value="<s:property value="generalConduct"/>" placeholder="General Conduct" data-required="true" value="Good" >
							</div>
							<div class="col-md-3" align="right" style="padding-top: 7px;">Academic progress:</div>
							<div class="col-md-4">
								<input type="text" name="academicProgress" class="form-control" value="<s:property value="academicProgress"/>" placeholder="Academic progress" data-required="true" >
							</div>
						</td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Date of issue of certificate</td>
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
						<td style="width: 3%;">Reasons for leaving the school</td>
						<td style="width: 7%;"><input type="text" name="reasons" class="form-control" value="<s:property value="reasons"/>" placeholder="Reasons" data-required="true" ></td>
					</tr>
					
					<tr>
						<td style="width: 3%;">Any other remarks</td>
						<td style="width: 7%;"><input type="text" name="remarks" class="form-control" value="<s:property value="remarks"/>" placeholder="Remarks" data-required="true" ></td>
					</tr>
					
				  </table>
			   </div>
			   
			   <div><p>Note: Certified that the above information is in accordance with the school register</p>
			</div>	
			
		  </s:iterator>
		</div>

		<div class="row" style="padding-top: 45px;" align="center">
			<div class="col-lg-4" >
				<b>Signature of <br>Class Teacher</b>
			</div>
		 		 
			<div class="col-lg-3" style="text-align: center;">
				<b>Checked <br> by</b>
			</div>
			
			<div class="col-lg-5" style="text-align: center;">
				<b>Signature of Principal <br> with date & School Seal</b>
			</div>	
		</div>
			
		<div  class="row" align="center" style="margin-top: 5%;">
			<button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
			<button type="button" class="btn btn-success" id="submitID" onclick ="SubmitForm('submitID');">Save</button>
		
		<%if(LeavingCertificatePrint == "Enabled"){ %>	
	        <button type="button" class="btn btn-warning" id="printSubmitID" onclick ="PrintCertificate('printSubmitID');">Print</button>
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
