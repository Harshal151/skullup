<%@page import="javassist.expr.NewArray"%>
<%@page import="com.kovidRMS.daoImpl.ConfigurationDAOImpl"%>
<%@page import="com.kovidRMS.daoInf.ConfigurationDAOInf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
     <%@page import="com.kovidRMS.form.LoginForm"%>
     <%@page import="java.util.HashMap"%>
     <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="com.kovidRMS.form.StudentForm"%>
     <%@page import="java.util.ArrayList"%>
	<%@page import="com.kovidRMS.form.ConfigurationForm"%>
	<%@page import="java.util.List"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>

<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
	
    	LoginDAOInf daoInf1 = new LoginDAOImpl();
	
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
		
		ConfigurationForm conform = new ConfigurationForm();
		
    	int AcademicYearID = 0;
		AcademicYearID = loginform.getAcademicYearID();
		System.out.println("Before...");
    	HashMap<Integer, String> ExamList = daoInf.getExamList(AcademicYearID);
    	System.out.println("after...");
    	
    	String ExamName = daoInf.retrieveExamName(AcademicYearID);
    	
    	int ExamID = daoInf.retrieveExamID(AcademicYearID);
    
	 	int GradeBased = (Integer)(request.getAttribute("GradeBased"));
		
	 	String loadStudentSearch = (String) request.getAttribute("loadStudentSearch");
	
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
		
		String SubjectType = (String)(request.getAttribute("SubjectType"));
		
		String Teacher = (String)(request.getAttribute("Teacher"));
		if(Teacher == null || Teacher == ""){
			Teacher = "dummy";
		}
		
		String classTeacherCheck = (String)(request.getAttribute("classTeacherCheck"));
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
		
		String ActivityHeader = (String) (request.getAttribute("ActivityHeader"));
	
    	List<ConfigurationForm> studentAssessmentList = (List<ConfigurationForm>) request.getAttribute("studentAssessmentList");
  %>
  
  <title><% if(loginform.getMedium().equals("mr")){ %>सह-शैक्षणिक विषय कॉन्फीग्युर करा  - SkoolUp<% }else{ %>Configure Co-scholastic Subject - SkoolUp <% } %></title>
  
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
   <%-- <script type="text/javascript" src="https://use.fontawesome.com/4836edb23b.js"></script> --%>

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
		$('#myExamLiID').addClass("active");
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
          document.location="RenderConfigureMySubject";
        }
      
     /* Load student standard and division ID */ 
     
      function checkStandard(sid, subject, exam){
    		
    	 	if(exam == "-1"){
				alert("Please select examination name.");
  			
	  			return false;
	  		}
    	  	else if(sid == "-1"){
    			alert("Please select Standard.");
    			
    			return false;
    		}else if(subject == "0"){
				alert("Please select subject.");
    			
    			return false;
    		}else{
    			 $("#loadStudentFOrm").attr("action","LoadStudentsByCoScholastic");
    			 $("#loadStudentFOrm").submit();
    			 
    			 $("html, body").animate({ scrollTop: 0 }, "fast");
    	    		$(".loadingImage").show();
    	    		$(".container").css("opacity","0.1");
    	    		$(".navbar").css("opacity","0.1");
    	    		return true;
    		}
    	}
      /* End */
      
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
	    	
    	  $("#loadStudentFOrm").attr("action","AddStudentAssessmentForCoScholastic");
    	  $("#loadStudentFOrm").submit();
    	  
    	  $("#"+subID).attr("disabled", "disabled");
    	  
  		return true;
      }
      
      function downloadExcel(sid){
  			
  		  $("#loadStudentFOrm").attr("action","StudentsCoScholasticReportExcel");
    	  $("#loadStudentFOrm").submit();
    	 
      } 
      
  </script>

<script type="text/javascript"> 

	function calculateTotal(className, totalMarksID, gradeID, hiddenID, outOfMarks, activityclassName, activitiesID, 
			studHiddenID, newSubjectAssmntID, SubjectID, newgradeBasedID, absentINputID, activityAssessmentInputID, absentID){
		var Marks = 0;
		var Total;
		var TotalMarks;
		var grade;
		var activities="";
		var marksStr = "";
		var activityTotal = 0;
		
		
		$("."+className).each(function () {
			
			if($(this).val() == ""){
				marksStr = "0";
				Marks = Marks + 0;
			}else{
				marksStr = $(this).val();
				 Marks = Marks + parseInt($(this).val());
				 
			}
			   //console.log(Marks);
		});
		 
		$("."+activityclassName).each(function () {
			if($(this).val() == ""){
				
				var arr = ($(this).val()).split("=");
				activities = activities + "@"+ $(this).val();
				
				activityTotal = activityTotal + 0;
			}else{
				var arr = ($(this).val()).split("=");
				activities = activities + "@"+ $(this).val();
				
				activityTotal = activityTotal + parseInt(arr[2]);
				
			}
		});
		
		$("#"+activitiesID).val(activities);
		
		if(marksStr == ""){
			
			$("#"+totalMarksID).val("");
			
			$("#"+gradeID).val(""); 
			
			
			$("#"+totalMarksID).removeAttr("name");
			
			$("#"+studHiddenID).removeAttr("name");
			
			$("#"+gradeID).removeAttr("name");
			
			$("#"+newSubjectAssmntID).removeAttr("name");
			
			$("#"+SubjectID).removeAttr("name");
			
			$("#"+newgradeBasedID).removeAttr("name");;
			
			$("#"+absentINputID).removeAttr("name");
			
			$("#"+absentINputID).val(0);
			
			$("#"+activityAssessmentInputID).removeAttr("name");
			
		}else{
			
			Total = (Marks/activityTotal) * 100;
			TotalMarks = Total.toFixed(2);
			
			$("#"+totalMarksID).val(TotalMarks);
			
			if (TotalMarks >= 91 && TotalMarks <= 100) {
				grade = "A1";
			} else if (TotalMarks >= 81 && TotalMarks <= 90) {
				grade = "A2";
			} else if (TotalMarks >= 71 && TotalMarks <= 80) {
				grade = "B1";
			} else if (TotalMarks >= 61 && TotalMarks <= 70) {
				grade = "B2";
			} else if (TotalMarks >= 51 && TotalMarks <= 60) {
				grade = "C1";
			} else if (TotalMarks >= 41 && TotalMarks <= 50) {
				grade = "C2";
			} else if (TotalMarks >= 33 && TotalMarks <= 40) {
				grade = "D";
			} else {
				grade = "E";
			}
			
			$("#"+gradeID).val(grade); 
		
			
			$("#"+totalMarksID).attr("name","marksObtainedArr");
			
			$("#"+studHiddenID).attr("name", "studID");
			
			$("#"+gradeID).attr("name", "gradeObtainedArr");
			
			$("#"+newSubjectAssmntID).attr("name", "newSubjAssmntID");
			
			$("#"+SubjectID).attr("name", "newSubjectID");
			
			$("#"+newgradeBasedID).attr("name", "newGradeBase");
			
			$("#"+absentINputID).attr("name", "newAbsentFlag");
			
			$("#"+absentINputID).val($("input[name='"+absentID+"']:checked").val());
			
			$("#"+activityAssessmentInputID).attr("name", "activityAssessmentArr");
			
		}
		
	}
	
	function editCalculateTotal(editClassName, totalMarksID, gradeID, editActivityclassName, editStudentActivityAssessmentInputID){
		var Marks = 0;
		var Total;
		var TotalMarks;
		var grade;
		var activities="";
		var marksStr = "";
		var activityTotal = 0;
		
		
		$("."+editClassName).each(function () {
			
			if($(this).val() == ""){
				marksStr = "";
				Marks = Marks + 0;
			}else{
				console.log(parseInt($(this).val()));
				marksStr = $(this).val();
				 Marks = Marks + parseInt($(this).val());
				
			}
			  
		});
		 
		$("."+editActivityclassName).each(function () {
			if($(this).val() == ""){
				activityTotal = activityTotal + 0;
			}else{
				var arr = ($(this).val()).split("=");
				console.log("****"+$(this).val());
				activityTotal = activityTotal + parseInt(arr[2]);
			}
			
		});
		
		if(marksStr == ""){
			
			$("#"+totalMarksID).val("");
			
			$("#"+gradeID).val("");
			
		}else{
			
			Total = (Marks/activityTotal) * 100;
			TotalMarks = Total.toFixed(2);
			 console.log("...."+TotalMarks);
			$("#"+totalMarksID).val(TotalMarks);
			
			if (TotalMarks >= 91 && TotalMarks <= 100) {
				grade = "A1";
			} else if (TotalMarks >= 81 && TotalMarks <= 90) {
				grade = "A2";
			} else if (TotalMarks >= 71 && TotalMarks <= 80) {
				grade = "B1";
			} else if (TotalMarks >= 61 && TotalMarks <= 70) {
				grade = "B2";
			} else if (TotalMarks >= 51 && TotalMarks <= 60) {
				grade = "C1";
			} else if (TotalMarks >= 41 && TotalMarks <= 50) {
				grade = "C2";
			} else if (TotalMarks >= 33 && TotalMarks <= 40) {
				grade = "D";
			} else {
				grade = "E";
			}
			
			$("#"+gradeID).val(grade); 
		
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

function retrieveSubject(examID, standardValue){

	if(examID == "-1"){
		alert("No exam is selected. Please select exam.");
		
		var array_element1 = "<select name='' id='editSubDivID' class='form-control'"+
		"> <option value='-1'>Select Subject</option></select>";
		
		document.getElementById("editSubjectDivID").innerHTML = array_element1;	
	
	}else if(standardValue == "-1"){
		alert("No standard is selected. Please select standard.");
		
		var array_element1 = "<select name='' id='editSubDivID' class='form-control'"+
		"> <option value='-1'>Select Subject</option></select>";
		
		document.getElementById("editSubjectDivID").innerHTML = array_element1;
		
	}else{
		
		retrieveSubject1(examID, standardValue);
	}

}

	function retrieveSubject1(examID,standardValue){

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);

				var array_element = "<select name='subjectID' id='editSubDivID' class='form-control'"+
				"> <option value='0'>Select Subject</option>";
				
				var check = 0;
				/* For division */
				for ( var i = 0; i < array.Release.length; i++) {
					
					check = array.Release[i].check;

					array_element += "<option value='"+array.Release[i].subjectID+"'>"+array.Release[i].subjectList+"</option>";
				}

				array_element += " </select>";
				
				if(check == 0){
					
					alert("No Subject found");
					
					var array_element = "<select name='' id='editSubDivID' class='form-control'"+
					"> <option value='0'>Select Subject</option></select>";
					
					document.getElementById("editSubjectDivID").innerHTML = array_element;
					
				}else{
				
					
					document.getElementById("editSubjectDivID").innerHTML = array_element;	
					
				}
			}
		};
		xmlhttp.open("GET", "RetrieveSubjectListByUserIDForCoScholastic?ExaminationID="
				+ examID+"&check="+standardValue, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	

	/* When absentFlag radio button has value 1 then disable the marksObtained columns*/
    function disable(AbsentID, MarksObtainedID, GradeBasedID, MarksObtainedClassName, ActivityAssessmentID, HiddenInputID, studentID, studHiddenID,newSubjectAssmntID,newTotalmarksID,SubjectID,newgradeBasedID,OutOfMarksClass, ActIDClass){
	    if (AbsentID == "0") {
	    	
	    	$("#"+MarksObtainedID).attr("readonly",false);
	    	$("#"+GradeBasedID).attr("readonly",false);
	    	$("."+MarksObtainedClassName).attr("readonly",false);
	    	$("#"+HiddenInputID).val(AbsentID);
	    	
	    } else {
	    	
	    	var i=0;
	    	var finalVal = "";
	    	
			$("#"+MarksObtainedID).attr("readonly",true);
			$("#"+GradeBasedID).attr("readonly",true);
			$("."+MarksObtainedClassName).attr("readonly",true);
			$("#"+MarksObtainedID).val("0");
			$("#"+GradeBasedID).val("");
			$("."+MarksObtainedClassName).val("0");
			$("#"+HiddenInputID).val(AbsentID);
			
			 $("#"+MarksObtainedID).attr("name","marksObtainedArr");
			
			$("#"+studHiddenID).attr("name", "studID");
					
			$("#"+GradeBasedID).attr("name", "gradeObtainedArr");
					
			$("#"+newSubjectAssmntID).attr("name", "newSubjAssmntID");
					
			$("#"+newTotalmarksID).attr("name", "newTotalMarksID");
					
			$("#"+SubjectID).attr("name", "newSubjectID");
					
			$("#"+newgradeBasedID).attr("name", "newGradeBase");
					
			$("#"+HiddenInputID).attr("name", "newAbsentFlag");
			
			$("#"+ActivityAssessmentID).attr("name","activityAssessmentArr");
			
			$("."+OutOfMarksClass).each(function(){
				var outMarks = $(this).val();
				var actID = $($("."+ActIDClass).get(i)).val();
				finalVal += "@"+ actID +"=0="+outMarks;
				i++;
				
			});
			
			$("#"+ActivityAssessmentID).val(finalVal);
			//$("#"+absentINputID).val(AbsentID);
	    }
	    
	    
    }
	
 function disable1(AbsentID, MarksObtainedID, GradeBasedID, MarksObtainedClassName, HiddenInputID,OutOfHiddenClass, ActivityHiddenClass, StudentActivityID,  StudentActivityHiddenClass){
	 //alert(AbsentID+".."+HiddenInputID);
	    if (AbsentID == "0") {
	    	
	    	$("#"+MarksObtainedID).attr("readonly",false);
	    	$("#"+GradeBasedID).attr("readonly",false);
	    	$("."+MarksObtainedClassName).attr("readonly",false);
	    	$("#"+HiddenInputID).val(AbsentID);
	    } else {
	    	
	    	var i=0;
	    	var finalVal = "";
	    	
			$("#"+MarksObtainedID).attr("readonly",true);
			$("#"+GradeBasedID).attr("readonly",true);
			$("."+MarksObtainedClassName).attr("readonly",true);
			$("#"+MarksObtainedID).val("0");
			$("."+MarksObtainedClassName).val("0");
			$("#"+GradeBasedID).val("");
			
			$("#"+HiddenInputID).val(AbsentID);
			
			$("."+StudentActivityHiddenClass).each(function(){
				
				var actID = $($("."+ActivityHiddenClass).get(i)).val();
				var outOfMarks = $($("."+OutOfHiddenClass).get(i)).val();
				var StudActID = $($("."+StudentActivityID).get(i)).val();
				finalVal = actID +"=0="+outOfMarks +"=" + StudActID;
				i++;
				
				$(this).val(finalVal);
			});
			
	    }
    }
	
    function storeValue(absentFlag, hiddenInputID){
		$("#"+hiddenInputID).val(absentFlag);
	}
    
    function restoreValue(radioVal, editMarksObtainedID, editGradeBasedID){
  		if(radioVal == "0"){
  			$("#"+editMarksObtainedID).attr("readonly",false);
  			$("#"+editGradeBasedID).attr("readonly",false);
  			
  	    }else{
  			$("#"+editMarksObtainedID).attr("readonly",true);
  			$("#"+editGradeBasedID).attr("readonly",true);
  	    	$("#"+editMarksObtainedID).val(AbsentID);
  	    	$("#"+editGradeBasedID).val(AbsentID);
  	    	$("#"+radioVal).val(AbsentID);
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
	
		function deleteRow(StudentAssessmentID, trID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var check = 0;
	
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						
					}
					console.log(check);
					if(check == 1){
						
						$("#"+trID+"").remove();
						
					}else{
						alert("Failed to delete row. Please check server logs for more details.")
					}
					
				}
			};
			xmlhttp.open("GET", "DeleteStudentAssessmentRow?StudentAssessmentID="
					+ StudentAssessmentID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	
   </script>
    
    <script type="text/javascript">
    
    	function resetStandrdSubjectValues(standardID){
    		$("#"+standardID).val("-1");
    		
    		var array_element1 = "<select name='' id='editSubDivID' class='form-control'"+
    		"> <option value='-1'>Select Subject</option></select>";
    		
    		document.getElementById("editSubjectDivID").innerHTML = array_element1;
    	}
    
    	
    	function appendValue(input, ActivityID, ActivityIDName, outOfMarks, SpanID){
    		
    		var inputValue = $(input).val();
    		//alert(inputValue);
    		
    		if(parseFloat(inputValue) <= parseFloat(outOfMarks)){
    			
    			var value = ActivityID + "=" + inputValue + "=" + outOfMarks;
        		
        		$("#"+ActivityIDName).val(value);
    			
    			$("#"+SpanID).html("");
    			
    		}else{
    			
    			$(input).val("");
    			
    			$("#"+SpanID).html("Value entered must be less than or equal to "+outOfMarks);
    		}
    		
    	}
    
    	
		function editAppendValue(input, editActivityID, editActivityIDName, editoutOfMarks, StudentActivityAssessmentID, SpanID){
    		
			var inputValue = $(input).val();
			
			if(parseFloat(inputValue) <= parseFloat(editoutOfMarks)){
				
				var editValue = editActivityID + "=" + inputValue + "=" + editoutOfMarks + "=" + StudentActivityAssessmentID;
	    		
	    		$("#"+editActivityIDName).val(editValue);
				
				$("#"+SpanID).html("");
				
			}else{
				
				$(input).val("");
				
				$("#"+SpanID).html("Value entered must be less than or equal to "+editoutOfMarks);
			}
			
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

<!-- MOdal to show student assessment history -->
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header" align="center">
				<h1><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थी अससेसमेंट हिस्टरी  <% }else{ %>Student Assessment History <% } %></h1>		
			</div>
			<div class="modal-body">
				<div class="row" style="margin-left: 15px;">
					<font style="font-size: 15px; font-weight: bold;" id="stdNameFontID"></font>
				</div>
				
				<div class="row" style="padding: 15px;" id="tableDIvID">
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal"><% if(loginform.getMedium().equals("mr")){ %>बंद करा  <% }else{ %>Close <% } %></button>
			</div>
		</div>
	</div>
	</div>
	<!-- End -->

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

	<div class="content-header">
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>माझे विषय   <% }else{ %>My Subject <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>माझे विषय   <% }else{ %>My Subject <% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
               <% if(loginform.getMedium().equals("mr")){ %>सह-शैक्षणिक विषय माहिती  <% }else{ %> Co-Scholastic Subject Details <% } %>
              </h3>

            </div> <!-- /.portlet-header -->

            <div class="portlet-content">
            
            <% if(Teacher.equals("Yes")){ %>
			
				<div style="margin-top:15px;">
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
			
			<%}else{ %>
			
            <div style="margin-top:15px;">
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
			
              <form id="loadStudentFOrm" action="LoadStudentsByCoScholastic" method="POST" data-validate="parsley" class="form parsley-form">
				
				<input type="hidden" class="form-control" id= "valueID" name= "value" value="Co-Scholastic" >
				
				<div class="row">
                
				  	<div class="col-md-2">
						<input type="hidden" class="form-control" id= "examID" name="ExaminationID" value="<%=ExamID%>" >
						<input type="text" class="form-control" name="examName" value="<%=ExamName%>" readonly="readonly">
					</div>
				
				<% if(loginform.getMedium().equals("mr")){ %>
					
					<div class="col-md-2">
						<s:select list="StandardListByCoScholastic" onchange="retrieveSubject(examID.value, this.value);" class="form-control" headerKey="-1" id="SID" headerValue="इयत्ता निवडा" name="standardDivName" ></s:select>
					</div>
				
		            <div class="col-md-2" id="editSubjectDivID">
		            	<s:select list="SubjectListByCoScholastic" class="form-control" headerKey="0" id="editSubDivID"  headerValue="विषय निवडा" name="subjectID" ></s:select>
		            </div>
					
					<div class="col-md-2">
						<button type="button" class="btn btn-warning" onclick="return checkStandard(SID.value, editSubDivID.value, examID.value);">विद्यार्थी पहा</button>
					</div>
					
					<div class="col-md-2">
						<button type="button" class="btn btn-success" onclick="downloadExcel(SID.value);">एक्स्पोर्ट एक्सेल</button>
					</div>
					
				<% }else{ %>
					<div class="col-md-2">
						<s:select list="StandardListByCoScholastic" onchange="retrieveSubject(examID.value, this.value);" class="form-control" headerKey="-1" id="SID" headerValue="Select Standard" name="standardDivName" ></s:select>
					</div>
				
		            <div class="col-md-2" id="editSubjectDivID">
		            	<s:select list="SubjectListByCoScholastic" class="form-control" headerKey="0" id="editSubDivID"  headerValue="Select Subject" name="subjectID" ></s:select>
		            </div>
					
					<div class="col-md-2">
						<button type="button" class="btn btn-warning" onclick="return checkStandard(SID.value, editSubDivID.value, examID.value);">Load Students</button>
					</div>
					
					<div class="col-md-2">
						<button type="button" class="btn btn-success" onclick="downloadExcel(SID.value);">Export Excel</button>
					</div>
					
				<% } %>
				
				</div>
			
			<div class="tab-pane fade in active" style="height:500px; overflow: auto;">
				
				<%
					if(loadStudentSearch.equals("Enabled")){
						
				%>
				
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
		              
		              	<thead>
	                        <tr>
	                        
	                          <th><% if(loginform.getMedium().equals("mr")){ %>हजेरी क्रमांक  <% }else{ %> Roll No <% } %></th>
	                        
	                          <th><% if(loginform.getMedium().equals("mr")){ %> विद्यार्थ्याचे नाव<% }else{ %> Student Name <% } %> </th>
	                          
	                          <th><% if(loginform.getMedium().equals("mr")){ %> अबसेन्ट <% }else{ %> Absent <% } %></th>
	                          
	                         <% 
	                         System.out.println("ActivityHeader...."+ActivityHeader);
	                     		if (ActivityHeader != null) {
	                     			System.out.println("inside if......"+ActivityHeader);
									if(ActivityHeader.contains(",")){
										
										String[] newList = ActivityHeader.split(",");
										
										for(int i=0;i<newList.length; i++){ 
											
											String[] newList1 = newList[i].split("\\$");
										%>
											<th><%= newList1[0]%></th>
											
										<% }
									}else{ 
									
										String[] newList = ActivityHeader.split("\\$");
										
									%>
										<th><%= newList[0]%></th>
										<!-- <th>Total Marks</th>    
	                      				<th>Grade</th> -->
									<% }
				
								}%>
	                      	 <th><% if(loginform.getMedium().equals("mr")){ %> एकूण गुण  <% }else{ %> Total Marks <% } %></th>
	                      	 <th><% if(loginform.getMedium().equals("mr")){ %> ग्रेड <% }else{ %> Grade <% } %></th>
	                          
	                        </tr>
	                      </thead>
		              
	                     <tbody>
	                     
		                   <s:iterator value="examinatrionList" >
		                  
		                   	<tr id="StudentTRID<s:property value="studentID"/>">
		                   		<td><s:property value="rollNumber"/><input type="hidden" name="" id="studentID<s:property value="studentID"/>" value="<s:property value="studentID"/>"> </td>
		                   		
		                   		<td><s:property value="studentName"/><input type="hidden" name="" id="subjectAssessmentID<s:property value="studentID"/>" value="<s:property value="subjectAssessmentID"/>" ><input type="hidden" name="" id="subjectID<s:property value="studentID"/>" value="<s:property value="subjectID"/>" > 
		                   		<input type="hidden" name="" id="newgradeBasedID<s:property value="studentID"/>" value="<%=GradeBased %>" >
		                   		</td>
		                   		
		                   		<td>
			                   		<div>
				                   		<input type="radio" name="newName<s:property value="studentID"/>"  value="1" id="absentFlag1ID<s:property value="studentID"/>"
				                   		onchange="disable(this.value, 'marksObtainedID<s:property value="studentID"/>','gradeBasedID<s:property value="studentID"/>','text-box<s:property value="studentID"/>', 'activitiesHiddenID<s:property value="studentID"/>', 'newAbsentFlagID<s:property value="studentID"/>',<s:property value="studentID"/>,'studentID<s:property value="studentID"/>','subjectAssessmentID<s:property value="studentID"/>','totalMarksID<s:property value="studentID"/>','subjectID<s:property value="studentID"/>','newgradeBasedID<s:property value="studentID"/>','OutOfMarksHidden<s:property value="studentID"/>','ActiviytyMarksHidden<s:property value="studentID"/>');" 
				                   		/>Yes
				                   		<input type="radio" name="newName<s:property value="studentID"/>"  value="0" id="absentFlag0ID<s:property value="studentID"/>"
		    							onchange="disable(this.value, 'marksObtainedID<s:property value="studentID"/>','gradeBasedID<s:property value="studentID"/>','text-box<s:property value="studentID"/>','activitiesHiddenID<s:property value="studentID"/>', 'newAbsentFlagID<s:property value="studentID"/>',<s:property value="studentID"/>,'studentID<s:property value="studentID"/>','subjectAssessmentID<s:property value="studentID"/>','totalMarksID<s:property value="studentID"/>','subjectID<s:property value="studentID"/>','newgradeBasedID<s:property value="studentID"/>','OutOfMarksHidden<s:property value="studentID"/>','ActiviytyMarksHidden<s:property value="studentID"/>');" 
		    							checked="checked" />No
		    							<input type="hidden" name="" value="0" id="newAbsentFlagID<s:property value="studentID"/>">  
			                   		</div>
		                   		</td>
		                   	
		                  	<% 
	                     		if (ActivityHeader != null) {
	                     			
									if(ActivityHeader.contains(",")){
										
										String[] newList = ActivityHeader.split(",");
										
										int spanCounter = 1;
										
										for(int i=0;i<newList.length; i++){ 
											
											spanCounter++;
											
											String[] newList1 = newList[i].split("\\$");
											
											String[] newList2 = newList1[1].split("=");
											
										%>
											
										<td><input type="number" class="text-box<s:property value="studentID"/>" id="totalObtainedMarksID<s:property value="studentID"/>" name="" onkeyup="appendValue(this, <%=newList2[1] %>, 'ActivityAssessmentID<%=newList2[1] %><s:property value="studentID"/>',<%=newList2[0] %>, 'spanID<s:property value="studentID"/><%=spanCounter%>')">
										<font style="color: red;" id = "spanID<s:property value="studentID"/><%=spanCounter%>"></font> 
										<input type="hidden" class="OutOfMarksHidden<s:property value="studentID"/>" id="OutOfMarksID<s:property value="studentID"/>" value=<%=newList2[0] %>> 
										<input type="hidden" class="ActiviytyMarksHidden<s:property value="studentID"/>" id="ActiviytyMarks<s:property value="studentID"/>" value=<%=newList2[1] %>> 
										<input type="hidden" class="activityHidden<s:property value="studentID"/>" id="ActivityAssessmentID<%=newList2[1] %><s:property value="studentID"/>" value="<%=newList2[1] %>=0=<%=newList2[0] %>">
										</td>
										<% }
										
									}else{
										
										String[] newList1 = ActivityHeader.split("\\$");
										String[] newList2 = newList1[1].split("=");
										
									%>
										<td><input type="number" class="text-box<s:property value="studentID"/>" id="totalObtainedMarksID<s:property value="studentID"/>" name="" onkeyup="appendValue(this, <%=newList2[1] %>, 'ActivityAssessmentID<%=newList2[1] %><s:property value="studentID"/>',<%=newList2[0] %>, 'spanID<s:property value="studentID"/>')"> 
										<font style="color: red;" id = "spanID<s:property value="studentID"/>"></font> 
										<input type="hidden" class="OutOfMarksHidden<s:property value="studentID"/>" id="OutOfMarksID<s:property value="studentID"/>" value=<%=newList2[0] %>> 
										<input type="hidden" class="ActiviytyMarksHidden<s:property value="studentID"/>" id="ActiviytyMarks<s:property value="studentID"/>" value=<%=newList2[1] %>> 
										<input type="hidden" class="activityHidden<s:property value="studentID"/>" id="ActivityAssessmentID<%=newList2[1] %><s:property value="studentID"/>" value="<%=newList2[1] %>=0=<%=newList2[0] %>">
										</td>
									<% }%>
										<input type= "hidden" name="" id="hiddenInputID<s:property value="studentID"/>">
									 	
								<%}%>	
		                   		<td><input type="text" class="" id="marksObtainedID<s:property value="studentID"/>" name="" readonly="readonly" onfocus="calculateTotal('text-box<s:property value="studentID"/>', 'marksObtainedID<s:property value="studentID"/>', 'gradeBasedID<s:property value="studentID"/>', 'hiddenInputID<s:property value="studentID"/>', 
		                   		OutOfMarksID<s:property value="studentID"/>.value, 'activityHidden<s:property value="studentID"/>','activitiesHiddenID<s:property value="studentID"/>','studentID<s:property value="studentID"/>','subjectAssessmentID<s:property value="studentID"/>',
		                   		'subjectID<s:property value="studentID"/>','newgradeBasedID<s:property value="studentID"/>','newAbsentFlagID<s:property value="studentID"/>', 'activitiesHiddenID<s:property value="studentID"/>', 'newName<s:property value="studentID"/>')"  > 
		                   		
		                   		<input type="hidden" id= "activitiesHiddenID<s:property value="studentID"/>" name =""/>
		                   		</td>
		                   		
	         					<td><input type="text" class="" id="gradeBasedID<s:property value="studentID"/>" readonly="readonly" name="" > </td> 
		                   	</tr>
		                   
		                   </s:iterator>
		                   <%
			          			
		                   		for(ConfigurationForm form: studentAssessmentList){
			              %>
		                 
		                   	<tr id="StudentTRID<%=form.getStudentID() %>">
		                   		<td><%=form.getRollNumber() %><input type="hidden" name="editStudID" value="<%=form.getStudentID() %>"><input type="hidden" name="editRegsitrationID" value="<%=form.getRegistrationID() %>"> </td>
		                   		
		                   		<td><%=form.getStudentName() %><input type="hidden" name="studAssessmntID" value="<%=form.getStudentAssessmentID() %>">  
		                   			
			                   		<input type="hidden" name="editScaleTo" value="<%=form.getScaleTo()%>">
			                   		<input type="hidden" name="editGradeBase" value="<%=form.getGradeBased()%>">
			                   		<input type="hidden" name="editSubjAssmntID" value="<%=form.getSubjectAssessmentID() %>">
			                   	</td>
		                   		<td>
		                   			<div>
		                   			<%
		                   				if(form.getAbsentFlag() == 1){
		                   					
		                   			%>
		                   			<input type="radio" name="name<%=form.getStudentID() %>"  checked="checked" value="1" 
		                   			onchange="disable1(this.value, 'edidmarksObtainedID<%=form.getStudentAssessmentID() %>','editgradeBasedID<%=form.getStudentAssessmentID() %>', 'text-box<%=form.getStudentAssessmentID() %>', 'absntFlagID<%=form.getStudentID() %>','editOutOfMarksHidden<%=form.getStudentAssessmentID() %>','editActivityMarksHidden<%=form.getStudentAssessmentID() %>','editStudentActivityAssessmentIDHidden<%=form.getStudentAssessmentID() %>','editactivityHidden<%=form.getStudentAssessmentID() %>');" 
		                   			 />Yes
		    						<input type="radio" name="name<%=form.getStudentID() %>"  value="0" 
		    						onchange="disable1(this.value, 'edidmarksObtainedID<%=form.getStudentAssessmentID() %>','editgradeBasedID<%=form.getStudentAssessmentID() %>', 'text-box<%=form.getStudentAssessmentID() %>', 'absntFlagID<%=form.getStudentID() %>','editOutOfMarksHidden<%=form.getStudentAssessmentID() %>','editActivityMarksHidden<%=form.getStudentAssessmentID() %>','editStudentActivityAssessmentIDHidden<%=form.getStudentAssessmentID() %>','editactivityHidden<%=form.getStudentAssessmentID() %>');"  />No
		                   			<%
		                   				}else{
		                   					
		                   			%>
		                   			<input type="radio" name="name<%=form.getStudentID() %>" value="1"
		                   			onchange="disable1(this.value, 'edidmarksObtainedID<%=form.getStudentAssessmentID() %>','editgradeBasedID<%=form.getStudentAssessmentID() %>','text-box<%=form.getStudentAssessmentID() %>', 'absntFlagID<%=form.getStudentID() %>','editOutOfMarksHidden<%=form.getStudentAssessmentID() %>','editActivityMarksHidden<%=form.getStudentAssessmentID() %>','editStudentActivityAssessmentIDHidden<%=form.getStudentAssessmentID() %>','editactivityHidden<%=form.getStudentAssessmentID() %>');"   />Yes
		    						<input type="radio" name="name<%=form.getStudentID() %>" checked="checked" value="0" 
		    						onchange="disable1(this.value, 'edidmarksObtainedID<%=form.getStudentAssessmentID() %>','editgradeBasedID<%=form.getStudentAssessmentID() %>','text-box<%=form.getStudentAssessmentID() %>', 'absntFlagID<%=form.getStudentID() %>','editOutOfMarksHidden<%=form.getStudentAssessmentID() %>','editActivityMarksHidden<%=form.getStudentAssessmentID() %>','editStudentActivityAssessmentIDHidden<%=form.getStudentAssessmentID() %>','editactivityHidden<%=form.getStudentAssessmentID() %>');"  />No
		    						<%
		                   				}
		    						%>
		    						<input type="hidden" id="absntFlagID<%=form.getStudentID() %>" name="editAbsentFlag" value="<%=form.getAbsentFlag()%>">
		                   		</div>
		                   	</td>
		                   		<% 
		                   		List<String> ActivityList = new ArrayList<String>();
		                   		int i=0;
		                   		
		                   		String[] newList = ActivityHeader.split(",");
		                   		
		                   	 	List<ConfigurationForm> studentActivityAssessmentAssessmentList = daoInf.retrievestudentActivityAssessmentAssessmentList(form.getStudentAssessmentID());	
	                   			
		                   	 	int spanCounter1 = 1;
		                   	 
		                   		for(ConfigurationForm form1: studentActivityAssessmentAssessmentList){
		                   		
		                   			spanCounter1++;
		                   			
		                   			if(form.getAbsentFlag() == 1){%>	
		                   				
		                   			<td>
										<input type="text" class="text-box<%=form.getStudentAssessmentID() %>" readonly="readonly" id="edidtotalObtainedMarksID<%=form.getStudentAssessmentID() %>" name="" value="<%=form1.getTotalMarks() %>" > 
										<input type="hidden" class="editOutOfMarksHidden<%=form.getStudentAssessmentID() %>" id="editOutOfMarksID<%=form.getStudentAssessmentID() %>" value=<%=form1.getOutOfMarks() %>> 	
										<input type="hidden" class="editActivityMarksHidden<%=form.getStudentAssessmentID() %>" id="editActivityMarksID<%=form.getStudentAssessmentID() %>" value=<%=form1.getActivityAssessmentID() %>> 									
										<input type="hidden" class="editactivityHidden<%=form.getStudentAssessmentID() %>" name="studentActivityAssessmentIDArr" id="editActivityAssessmentID<%=form.getStudentAssessmentID() %><%=form1.getStudentActivityAssessmentID() %>" value="<%=form1.getActivityAssessmentID() %>=<%=form1.getTotalMarks() %>=<%=form1.getOutOfMarks() %>=<%=form1.getStudentActivityAssessmentID() %>">
										<input type="hidden" class="editStudentActivityAssessmentIDHidden<%=form.getStudentAssessmentID() %>" name ="" id ="edidStudentActivityAssessmentID<%=form.getStudentAssessmentID() %>" value="<%=form1.getStudentActivityAssessmentID() %>" >
									</td>	
									
								<% }else{%>
		                   			
		                   		<td>
		                   			<input type="text" class="text-box<%=form.getStudentAssessmentID() %>" id="edidtotalObtainedMarksID<%=form1.getStudentActivityAssessmentID() %>" name="" value="<%=form1.getTotalMarks() %>" onkeyup="editAppendValue(this, <%=form1.getActivityAssessmentID() %>, 'editActivityAssessmentID<%=form.getStudentAssessmentID() %><%=form1.getStudentActivityAssessmentID() %>',<%=form1.getOutOfMarks() %>, <%=form1.getStudentActivityAssessmentID() %>, 'newSpanID<%=form.getStudentAssessmentID() %><%=spanCounter1 %>')" > 
									<font style="color: red;" id = "newSpanID<%=form.getStudentAssessmentID() %><%=spanCounter1 %>"></font>
									<input type="hidden" class="editOutOfMarksHidden<%=form.getStudentAssessmentID() %>" id="editOutOfMarksID<%=form.getStudentAssessmentID() %>" value=<%=form1.getOutOfMarks() %>> 	
									<input type="hidden" class="editActivityMarksHidden<%=form.getStudentAssessmentID() %>" id="editActivityMarksID<%=form.getStudentAssessmentID() %>" value=<%=form1.getActivityAssessmentID() %>> 									
									<input type="hidden" class="editactivityHidden<%=form.getStudentAssessmentID() %>" name="studentActivityAssessmentIDArr" id="editActivityAssessmentID<%=form.getStudentAssessmentID() %><%=form1.getStudentActivityAssessmentID() %>" value="<%=form1.getActivityAssessmentID() %>=<%=form1.getTotalMarks() %>=<%=form1.getOutOfMarks() %>=<%=form1.getStudentActivityAssessmentID() %>">
									<input type="hidden" class="editStudentActivityAssessmentIDHidden<%=form.getStudentAssessmentID() %>" name ="" id ="edidStudentActivityAssessmentID<%=form.getStudentAssessmentID() %>" value="<%=form1.getStudentActivityAssessmentID() %>" >
								</td>	
									
								<% }
								
		                   			String value = newList[i];
								%>
								
							<%} %>
								<%if(form.getAbsentFlag() == 1){ %>
		                   			
		                   			<td>
		                   				<input type="text" class="" readonly="readonly" name="editMarksObtainedArr" id="edidmarksObtainedID<%=form.getStudentAssessmentID() %>" value="<%=form.getMarksObtained() %>"  > 
		                   			</td>
			                   		
			                   		<td>
			                   			<input type="text" class="" name="editGradeObtainedArr" id="editgradeBasedID<%=form.getStudentAssessmentID() %>" readonly="readonly" value="<%=form.getGrade() %>" > 
			                   		</td> 	
		                   		
		                   		<%}else{ %>
		                   		
		                   		<td>
		                   			<input type="text" class="" name="editMarksObtainedArr" id="edidmarksObtainedID<%=form.getStudentAssessmentID() %>" value="<%=form.getMarksObtained() %>" onfocus="editCalculateTotal('text-box<%=form.getStudentAssessmentID() %>', 'edidmarksObtainedID<%=form.getStudentAssessmentID() %>', 
		                   			'editgradeBasedID<%=form.getStudentAssessmentID() %>', 'editactivityHidden<%=form.getStudentAssessmentID() %>','edidStudentActivityAssessmentArrID<%=form.getStudentID() %>' )">
		                   		</td>
			                   		
			                   	<td>
			                   		<input type="text" class="" name="editGradeObtainedArr" id="editgradeBasedID<%=form.getStudentAssessmentID() %>" value="<%=form.getGrade() %>" > 
			                   	</td> 
		                   		<%} %>
		                   
		                   </tr>
		                  
	                   		 <%	}%> 
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
				 </div>
				 </form>
           <% } %>     
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
