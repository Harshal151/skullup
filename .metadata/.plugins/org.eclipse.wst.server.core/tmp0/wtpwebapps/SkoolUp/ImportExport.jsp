<%@page import="javassist.expr.NewArray"%>
<%@page import="com.kovidRMS.daoImpl.ConfigurationDAOImpl"%>
<%@page import="com.kovidRMS.daoInf.ConfigurationDAOInf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
<title>Import/Export Section - SkoolUp</title>
<!-- Favicon -->
  <link rel="shortcut icon" href="images/favicon.jpg">
<meta charset="utf-8">
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Oswald:400,300,700">
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
<link rel="stylesheet"
	href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
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
	ul.actionMessage {
		list-style: none;
		font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
	}
	
	ul.errorMessage {
		list-style: none;
		font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
	}

	.fixed-header {
		position: fixed;
		top: 43px;
		z-index: 1;
		width: 100%;
		height: 107px;
	}
	
	.fixed-navbar {
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

	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	ConfigurationForm conform = new ConfigurationForm();
    	
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
		 
		 $('html, body').animate({
		        scrollTop: $('body').offset().top
		   }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); 
			
        document.location="welcome.jsp";
      }

	 function checkAcademicYear(academicYearNameID, subID){
 		
 	 	if(academicYearNameID == "-1"){
				alert("Please select AcademicYear name.");
			
	  			return false;
	  	}else{
	  		
	  		$('html, body').animate({
		        scrollTop: $('body').offset().top
		 	}, 1000);
			
	    	$("#validate-basic").attr("action","ExportStudentHistoryReportExcel");
			$("#validate-basic").submit();
			  	  
 			return true;
 		}
 	}
	 
	 function checkExportAcademicYear(academicYearNameID, subID){
		 
		 if(academicYearNameID == "-1"){
				alert("Please select AcademicYear name.");
			
	  			return false;
	  	}else{
	  		
	  		$('html, body').animate({
		        scrollTop: $('body').offset().top
		 	}, 1000);
			
	    	$("#validate-basic").attr("action","ExportConfiguration");
			$("#validate-basic").submit();
			  	 
			return true;
		}
	 	
	 }
	 
	 function checkAcademicYear1(academicYearNameID1, excelFileID, subID){
	 		
	 	 	if(academicYearNameID1 == "-1"){
				alert("Please select AcademicYear name.");
				return false;
		  	}else if(excelFileID ==""){
		  		alert("Please select excel sheet.");
		  		return false;
		  	}else{
		  		
		  		$('html, body').animate({
			        scrollTop: $('body').offset().top
			 	}, 1000);
				
		    	$("#validate-basic1").attr("action","ImportStudentHistoryReportExcel");
				$("#validate-basic1").submit();
				  	  
			  	return true;
	 		}
	 	}
	 
	  function checkImportAcademicYear(academicYearNameID1, excelFileID, subID){
	 		
	 	 	if(academicYearNameID1 == "-1"){
				alert("Please select AcademicYear name.");
				return false;
		  	}else if(excelFileID ==""){
		  		alert("Please select excel sheet.");
		  		return false;
		  	}else{
		  		
		  		$('html, body').animate({
			        scrollTop: $('body').offset().top
			 	}, 1000);
				
		    	$("#validate-basic1").attr("action","ImportConfiguration");
				$("#validate-basic1").submit();
				  	  
			  	return true;
	 		}
	 	}
	  
	  function submitForm(loadFormID, subID, Action){
		   	 
	  		$('html, body').animate({
			        scrollTop: $('body').offset().top
			 }, 1000);
				
		    $("#"+loadFormID).attr("action", Action);
			$("#"+loadFormID).submit(); 
			 	
		    return true;
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
					<h2 class="content-header-title">Import/Export Section</h2>
					<ol class="breadcrumb">
						<li><a onclick="windowOpen();">Home</a></li>
						<li class="active">Import/Export Section</li>
					</ol>
				</div>
				<!-- /.content-header -->

				<div class="row">
					<div class="col-sm-3"></div>
					<div class="col-sm-12">

						<div class="portlet">

							<div class="portlet-header">

								<h3>
									<i class="fa fa-tasks"></i> Import/Export
								</h3>

							</div>
							<!-- /.portlet-header -->

							<div class="portlet-content">

								<div>
									<s:if test="hasActionErrors()">
										<center>
											<font style="color: red; font-size: 16px;"><s:actionerror /></font>
										</center>
									</s:if>
									<s:else>
										<center>
											<font style="color: green; font-size: 16px;"><s:actionmessage /></font>
										</center>
									</s:else>
								</div>

								<form id="validate-basic" action="ExportStudentHistoryReportExcel" method="POST"
									data-validate="parsley" class="form parsley-form">

									<div class="row">
										<div class="col-md-2">
											<s:select list="AcademicYearNameList" required="required"
												class="form-control" headerKey="-1" name="academicYearID"
												id="AcademicYearNameID" headerValue="Select Academic Year">
											</s:select>
										</div>

										<div class="col-md-2">
											<button type="button" class="btn btn-warning" id="submitID"
												onclick="return checkAcademicYear(AcademicYearNameID.value, 'submitID');">Export
												Students Data</button>
										</div>

										<div class="col-md-2">
											<button type="button" class="btn btn-success" id="exportSubmitID"
												onclick="return checkExportAcademicYear(AcademicYearNameID.value, 'exportSubmitID');">Export
												Configuration Data</button>
										</div>

									</div>

								</form>

								<hr>

								<form id="validate-basic1" action="ImportStudentHistoryReportExcel" method="POST"
									enctype="multipart/form-data" data-validate="parsley" class="form parsley-form">

									<div class="row">
										<div class="col-md-2">
											<s:select list="AcademicYearNameList" required="required"
												class="form-control" headerKey="-1" name="academicYearID"
												id="AcademicYearNameID1" headerValue="Select Academic Year">
											</s:select>
										</div>

										<div class="col-md-4 col-sm-6 col-xs-12">
											<s:file name="excelFileName" class="form-control"
												id="excelFileID"></s:file>
										</div>

										<div class="col-md-2">
											<button type="button" class="btn btn-warning" id="studentSubmitID"
												onclick="return checkAcademicYear1(AcademicYearNameID1.value, excelFileID.value, 'studentSubmitID');">Process
												Students Data</button>
										</div>

										<div class="col-md-2">
											<button type="button" class="btn btn-success" id="configSubmitID"
												onclick="return checkImportAcademicYear(AcademicYearNameID1.value, excelFileID.value, 'configSubmitID');">Process
												Configuration Data</button>
										</div>

									</div>

								</form>

							<hr>
							
							<form id="validate-basic" action="ImportBooksHistoryReportExcel" method="POST" onsubmit="submitForm('validate-basic', 'bookSubmitID', 'ImportBooksHistoryReportExcel');" enctype="multipart/form-data" data-validate="parsley" class="form parsley-form">
								<div class="row">
				               		<div class="col-md-2">
										<s:select list="AcademicYearNameList" required="required" class="form-control" headerKey="-1" 
											name="academicYearID" id="AcademicYearNameID1" headerValue="Select Academic Year">
										</s:select>
									</div>
										
				               		<div class="col-md-3">
						                <s:select class="form-control" list="LibraryList" headerKey="" headerValue="Select Library" name="libraryID" id="libraryID"></s:select>
								   	</div>
								   	
								  	<div class="col-md-4 col-sm-6 col-xs-12">
					                   <s:file name="excelFileNameNew" class="form-control" id ="excelFileNewID"></s:file>
					                </div>
				               	
				               		<div class="col-md-3">
										<button type="submit" class="btn btn-warning" id="bookSubmitID">Process Book Data</button>
									</div>
								</div>
							</form>
			
							</div>
							<!-- /.portlet-content -->

						</div>
						<!-- /.portlet -->

					</div>
					<!-- /.col -->

				</div>
				<!-- /.row -->



			</div>
			<!-- /.content-container -->

		</div>
		<!-- /.content -->

	</div>
	<!-- /.container -->


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
