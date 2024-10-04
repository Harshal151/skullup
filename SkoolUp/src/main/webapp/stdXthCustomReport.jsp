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
    <%@page import="java.util.Map.Entry"%> 
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>My Class - SkoolUp</title>
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
		$('#reportLiID').addClass("active");
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
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
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
        document.location="welcome.jsp";
      }
   
      function checkStandard(subject){
    	
    	 if(subject == "-1"){
  				alert("Please select Subject.");
  				return false;
  			
    	  }else{
    		 
    		  var x = $("#editSubDivID option:selected").text();
    		  $("#newSubjectID").val(x);
    			
  				$("#validate-basic").attr("action","XthSTDStudentsCustomReportExcel");
  				$("#validate-basic").submit();
  				return true;		
  			}
  	}
    
	function submitReportForm1(){
		$("#validate-basic").attr("action","XthSTDStudentsCustomReportExcel");
		$("#validate-basic").submit();
	}
</script>

<script type="text/javascript">

var xmlhttp;
if (window.XMLHttpRequest) {
	xmlhttp = new XMLHttpRequest();
} else {
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

function retrieveSubject(standardValue){

	if(standardValue == "-1"){
		alert("No standard is selected. Please select standard.");
		
		var array_element1 = "<select name='' id='editSubDivID' class='form-control'"+
		"> <option value='-1'>Select Subject</option></select>";
		
		document.getElementById("editSubjectDivID").innerHTML = array_element1;
		
	}else{
		
		var x = $("#SID option:selected").text();
		$("#newSID").val(x);
		retrieveSubject1(standardValue);
	}

}

	function retrieveSubject1(standardValue){
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var array_element = "<select name='subjectID' id='editSubDivID' class='form-control'"+
				"> <option value='0'>Select Subject</option>";
				
				var check = 0;
				/* For division */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

					array_element += "<option value='"+array.Release[i].subjectID+"'>"+array.Release[i].subjectName+"</option>";
				}

				array_element += " </select>";
				
				if(check == 0){
					
					alert("No Subject found");
					
					var array_element = "<select name='' id='editSubDivID' class='form-control'"+
					"> <option value='0'>Select Subject</option></select>";
					Scaled
					document.getElementById("editSubjectDivID").innerHTML = array_element;
					
				}else{
				
					
					document.getElementById("editSubjectDivID").innerHTML = array_element;	
					
				}
			}
		};
		xmlhttp.open("GET", "RetrieveXthSTDSubjectListByUserIDandStandardID?check="
				+ standardValue, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}

</script>
</head>

<body onload="changeLanguageByButtonClick('<%=loginform.getMedium()%>');">

<img src="images/loading.gif" alt="Loading..." class="loadingImage" style="z-index:1;display: none;">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="google_translate_element" style="display:none"></div> 

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      

      <div class="content-header">
        <h2 class="content-header-title">My Class</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">My Class</li>
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
		   
		    <%
		    	if(classTeacherCheck.equals("Yes")){	
		    %>
		    	  
		   	<form id="validate-basic" action="XthSTDStudentsCustomReportExcel" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
				
                <div class="row">
               
               		<div class="col-md-2">
						<s:hidden name="standardDivision" id="newSID"></s:hidden>
						<s:select list="StandardListByTeacher" onchange="retrieveSubject(this.value);" class="form-control" headerKey="-1" id="SID" headerValue="Select Standard" name="standardDivName" ></s:select>
					</div>
				
					<div class="col-md-2" id="editSubjectDivID">
						<s:hidden name="subject" id="newSubjectID"></s:hidden>
						<s:select list="subjectNameList" class="form-control" headerKey="-1" id="editSubDivID"  headerValue="Select Subject" name="subjectID" ></s:select>
		            </div>
		            
					<div class="col-md-2">
						<button class="btn btn-success" type="submit" onclick="return checkStandard(editSubDivID.value)">Excel Report</button>
					</div>
					
				</div>
			
			</form>
			
		<% } %>	 
			
			<hr>
			
			<div class="tab-pane fade in active">
		
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
