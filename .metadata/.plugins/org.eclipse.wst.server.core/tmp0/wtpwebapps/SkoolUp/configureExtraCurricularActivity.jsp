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
		
    	int AcademicYearID = loginform.getAcademicYearID();
		
    	HashMap<Integer, String> ExamList = daoInf.getExamList(AcademicYearID);
    	
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
		
    	List<ConfigurationForm> studentAssessmentList = (List<ConfigurationForm>) request.getAttribute("studentAssessmentList");
    %>
    
  <title><% if(loginform.getMedium().equals("mr")){ %>अभ्यासेतर विषय कॉन्फीग्युर करा  - SkoolUp<% }else{ %>Configure Extra-Curricular Subject - SkoolUp <% } %></title>
  
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
    		
    	 if(sid == "-1"){
    			alert("Please select Standard.");
    			
    			return false;
    		}else if(subject == "0"){
				alert("Please select subject.");
    			
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
    	    	
    	    	$("#loadStudentFOrm").attr("action","LoadStudentsForExtraCurricular");
    	    	$("#loadStudentFOrm").submit();
    	    	  
    	    	  $("#loadSubmitID").attr("disabled", "disabled");
    	    	  
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
			
    	  $("#loadStudentFOrm").attr("action","AddStudentAssessmentForExtraCurricular");
    	  $("#loadStudentFOrm").submit();
    	  
    	  $("#"+subID).attr("disabled", "disabled");
    	
    		return true;
      }
      
  </script>

<script type="text/javascript"> 

	 function assignName(input, studentID, studHiddenID, gradeBasedID, newSubjectAssmntID, newTotalmarksID, SubjectID, newgradeBasedID, absentINputID, absentID, absentVal, check){
		//alert(input.value);
		var marksVal = $("#"+input).val();
		
		var gradeVal = $("#"+gradeBasedID).val();
		
		if(check == "Marks"){
			if(marksVal == ""){
				
				$("#"+input).removeAttr("name");
				
				$("#"+studHiddenID).removeAttr("name");
				
				$("#"+gradeBasedID).removeAttr("name");
				
				$("#"+newSubjectAssmntID).removeAttr("name");
				
				$("#"+newTotalmarksID).removeAttr("name");
				
				$("#"+SubjectID).removeAttr("name");
				
				$("#"+newgradeBasedID).removeAttr("name");;
				
				$("#"+absentINputID).removeAttr("name");
				
				$("#"+absentINputID).val(0);
			
			}else{
			
				$("#"+input).attr("name","marksObtainedArr");
				
				$("#"+studHiddenID).attr("name", "studID");
				
				$("#"+gradeBasedID).attr("name", "gradeObtainedArr");
				
				$("#"+newSubjectAssmntID).attr("name", "newSubjAssmntID");
				
				$("#"+newTotalmarksID).attr("name", "newTotalMarksID");
				
				$("#"+SubjectID).attr("name", "newSubjectID");
				
				$("#"+newgradeBasedID).attr("name", "newGradeBase");
				
				$("#"+absentINputID).attr("name", "newAbsentFlag");
				
				$("#"+absentINputID).val($("input[name='"+absentID+"']:checked").val());
				
			}
		}else{
			
			if(gradeVal == ""){
				
				$("#"+input).removeAttr("name");
				
				$("#"+studHiddenID).removeAttr("name");
				
				$("#"+gradeBasedID).removeAttr("name");
				
				$("#"+newSubjectAssmntID).removeAttr("name");
				
				$("#"+newTotalmarksID).removeAttr("name");
				
				$("#"+SubjectID).removeAttr("name");
				
				$("#"+newgradeBasedID).removeAttr("name");
				
				$("#"+absentINputID).removeAttr("name");
				
				$("#"+absentINputID).removeAttr("name");
				
				$("#"+absentINputID).val(0);
				
			}else{
				$("#"+input).attr("name","marksObtainedArr");
				
				$("#"+studHiddenID).attr("name", "studID");
				
				$("#"+gradeBasedID).attr("name", "gradeObtainedArr");
				
				$("#"+newSubjectAssmntID).attr("name", "newSubjAssmntID");
				
				$("#"+newTotalmarksID).attr("name", "newTotalMarksID");
				
				$("#"+SubjectID).attr("name", "newSubjectID");
				
				$("#"+newgradeBasedID).attr("name", "newGradeBase");
				
				$("#"+absentINputID).attr("name", "newAbsentFlag");
				
				$("#"+absentINputID).val($("input[name='"+absentID+"']:checked").val());
				
			}
		}
		
	 }
		
		 function assignName1(input, studentID, studHiddenID, gradeBasedID, newSubjectAssmntID, newTotalmarksID, SubjectID, newgradeBasedID, absentINputID, absentID, absentVal, check){
				//alert(input.value);
				var marksVal = $("#"+input).val();
				alert("Inside...");
				
					$("#"+input).attr("name","marksObtainedArr");
							
					$("#"+studHiddenID).attr("name", "studID");
							
					$("#"+gradeBasedID).attr("name", "gradeObtainedArr");
							
					$("#"+newSubjectAssmntID).attr("name", "newSubjAssmntID");
							
					$("#"+newTotalmarksID).attr("name", "newTotalMarksID");
							
					$("#"+SubjectID).attr("name", "newSubjectID");
							
					$("#"+newgradeBasedID).attr("name", "newGradeBase");
							
					$("#"+absentINputID).attr("name", "newAbsentFlag");
							
					$("#"+absentINputID).val($("input[name='"+absentID+"']:checked").val());
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

	if(standardValue == "-1"){
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
		xmlhttp.open("GET", "RetrieveSubjectListByUserIDForExtraCurricular?ExaminationID="
				+ examID+"&check="+standardValue, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	

	/* When absentFlag radio button has value 1 then disable the marksObtained columns*/
    function disable(AbsentID, MarksObtainedID, GradeBasedID, HiddenInputID, studentID, studHiddenID,newSubjectAssmntID,newTotalmarksID,SubjectID,newgradeBasedID){
	    if (AbsentID == "0") {
	    	
	    	$("#"+MarksObtainedID).attr("readonly",false);
	    	$("#"+GradeBasedID).attr("readonly",false);
	    	$("#"+HiddenInputID).val(AbsentID);
	    	
			$("#"+MarksObtainedID).removeAttr("name");
			
			$("#"+GradeBasedID).removeAttr("name");
			
			$("#"+HiddenInputID).removeAttr("name");
			
			$("#"+studHiddenID).removeAttr("name");
					
			$("#"+newSubjectAssmntID).removeAttr("name");
					
			$("#"+newTotalmarksID).removeAttr("name");
					
			$("#"+SubjectID).removeAttr("name");
					
			$("#"+newgradeBasedID).removeAttr("name");
			
			$("#"+HiddenInputID).removeAttr("name");			
					
			//$("#"+absentINputID).val(AbsentID);
	    	
	    } else {
			$("#"+MarksObtainedID).attr("readonly",true);
			$("#"+GradeBasedID).attr("readonly",true);
			$("#"+MarksObtainedID).val("0");
			$("#"+GradeBasedID).val("");
			$("#"+HiddenInputID).val(AbsentID);
			
			$("#"+MarksObtainedID).attr("name","marksObtainedArr");
			
			$("#"+studHiddenID).attr("name", "studID");
					
			$("#"+GradeBasedID).attr("name", "gradeObtainedArr");
					
			$("#"+newSubjectAssmntID).attr("name", "newSubjAssmntID");
					
			$("#"+newTotalmarksID).attr("name", "newTotalMarksID");
					
			$("#"+SubjectID).attr("name", "newSubjectID");
					
			$("#"+newgradeBasedID).attr("name", "newGradeBase");
					
			$("#"+HiddenInputID).attr("name", "newAbsentFlag");
					
			//$("#"+absentINputID).val(AbsentID);
	    }
	    
	    
    }
	
 function disable1(AbsentID, MarksObtainedID, GradeBasedID, HiddenInputID){
	 //alert(AbsentID+".."+HiddenInputID);
	    if (AbsentID == "0") {
	    	
	    	$("#"+MarksObtainedID).attr("readonly",false);
	    	$("#"+GradeBasedID).attr("readonly",false);
	    	$("#"+HiddenInputID).val(AbsentID);
	    } else {
			$("#"+MarksObtainedID).attr("readonly",true);
			$("#"+GradeBasedID).attr("readonly",true);
			$("#"+MarksObtainedID).val("0");
			$("#"+GradeBasedID).val("");
			$("#"+HiddenInputID).val(AbsentID);
			
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

<script type="text/javascript">
	
var subjectCounter = 1;

function addRetestRow1(NewRetestAnchorID, RetestID, marksObtainedID, StudentTRID){
	
	if(confirm("Are you sure you want add retest for this student?")){	
	
		$("#"+RetestID).val(1);
	 
		$("#"+marksObtainedID).removeAttr("name");
		 
		var trID = "newSubjectTRID"+subjectCounter;
			
		var trTag = "";
			
		trTag += "<tr id='"+trID+"'>"
			  + "<td colspan='4' align='right'><b>Marks Obtained</b></td>"
			  + "<td style='text-align:center;'><input type='number' class='form-control' name='editMarksObtainedArr'></td>"
			  + "</tr>";
			
		$(trTag).insertAfter($('#'+StudentTRID));
			
		subjectCounter++;
			
		$("#"+NewRetestAnchorID).removeAttr("href"); 
	}	
} 

</script>
 
<script type="text/javascript">
 
var subjectCounter1 = 1;
 
 function addRetestRow(RetestAnchorID, RetestID, gradeBasedID, StudentTRID, SubjectType){
	 
	 if(confirm("Are you sure you want add retest for this student?")){
			
		 $("#"+RetestID).val(1);
		 
		 $("#"+gradeBasedID).removeAttr("name");
		 
			var trID = "newSubjectTRID"+subjectCounter1;
			
			var trTag = "";
			 
		   if(SubjectType == "1"){
			   
				trTag += "<tr id='"+trID+"'>"
					  + "<td colspan='3' align='right'><b>Grades</b></td>"
					  + "<td style='text-align:center;'><select name='editGradeObtainedArr' id='' class='form-control'>"
					  + "<option value=''>Select Grade</option>"
					  + "<option value='G'>G</option>"
					  + "<option value='S'>S</option>"
					  + "<option value='NI'>NI</option>"
					  +"</select></td>"
					  + "</tr>";
				
				$(trTag).insertAfter($('#'+StudentTRID));
				subjectCounter1++;
				
		   }else{
					
			   trTag += "<tr id='"+trID+"'>"
			      + "<td colspan='3' align='right'><b>Grades</b></td>"
				  + "<td style='text-align:center;'><select name='editGradeObtainedArr' class='form-control'>"
				  + "<option value=''>Select Grade</option>"
				  + "<option value='A1'>A1</option>"
				  + "<option value='A2'>A2</option>"
				  + "<option value='B1'>B1</option>"
				  + "<option value='B2'>B2</option>"
				  + "<option value='C1'>C1</option>"
				  + "<option value='C2'>C2</option>"
				  + "<option value='D'>D</option>"
				  + "<option value='E'>E</option>"
				  + "</select></td>"
				  + "</tr>";
				
				$(trTag).insertAfter($('#'+StudentTRID));
				subjectCounter1++;
			}
		   
		   	$("#"+RetestAnchorID).removeAttr("href"); 
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
	
		function viewStudentAssessmentHistroy(studentID, subjectAssessmentID, isGradeBased) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var check = 0;
					var studentName = "";
					var marksObtained = 0;
					var marksScaled = 0;
					var grade = "";
					var activityStatus = "";
					var StudentAssessmentID = 0;
					
					var trTag = "";
					
					var tableTag = "";
					
					var trID = "History"+StudentAssessmentID;
					
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						
						StudentAssessmentID = array.Release[i].StudentAssessmentID;
						studentName = array.Release[i].studentName;
						marksObtained = array.Release[i].marksObtained;
						marksScaled = array.Release[i].marksScaled;
						grade = array.Release[i].grade;
						activityStatus = array.Release[i].activityStatus;
						
						if(isGradeBased == "1"){
							tableTag = "<table id='' class='table table-striped table-bordered dt-responsive nowrap'><tr style='font-size: 15px;'><th style='text-align: center'>Grade</th><th style='text-align: center'>Activity Status</th><th style='text-align: center'>Action</th></tr>";
							
							trTag += "<tr id='"+trID+"' style='font-size: 14px;'>"
								+"<td style='text-align: center'>"+grade+"</td>"
								+"<td style='text-align: center'>"+activityStatus+"</td>";
								
								if(activityStatus=="Inactive"){
									
									trTag += "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeRow(\"" + StudentAssessmentID + "\",\"" + trID + "\");'/></td>";
								
								}else{
									
									trTag += "<td></td>";
								}
							trTag += "</tr>";
							
						}else{
							tableTag = "<table id='' class='table table-striped table-bordered dt-responsive nowrap'><tr style='font-size: 15px;'><th style='text-align: center'>Marks Obtained</th><th style='text-align: center'>Activity Status</th></tr>";
							
							trTag += "<tr id='"+trID+"' style='font-size: 14px;'>"
								+"<td style='text-align: center'>"+marksObtained+"</td>"
								+"<td style='text-align: center'>"+activityStatus+"</td>";
							
								if(activityStatus=="Inactive"){
								
									trTag += "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeRow(\"" + StudentAssessmentID + "\",\"" + trID + "\");'/></td>";
							
								}else{
									
									trTag += "<td></td>";
								}
							
							trTag += "</tr>";
						}
						
					}
					
					tableTag += trTag + "</table>";
					
					if(check == 1){
						
						$("#stdNameFontID").html("Student Name: "+studentName);
						
						$("#tableDIvID").html(tableTag);
						
						$("#myModal").modal('show');
						
					}else{
						alert("No history found for selected student.");
					}
					
				}
			};
			xmlhttp.open("GET", "RetrieveStudentAssessmentHistroy?studentID="
					+ studentID+"&subjectAssessmentID="+subjectAssessmentID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	
		function removeRow(StudentAssessmentID, trID){
		   	if(confirm("Are you sure you want to remove this row?")){
		   		
		   		deleteRow(StudentAssessmentID, trID);
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
               <% if(loginform.getMedium().equals("mr")){ %>अभ्यासेतर उपक्रम विषय माहिती  <% }else{ %> Extra-curricular Activity Subject Details <% } %>
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
			
              <form id="loadStudentFOrm" action="LoadStudentsForExtraCurricular" method="POST" data-validate="parsley" class="form parsley-form">
				
				<input type="hidden" class="form-control" id= "valueID" name="value" value="Extra-curricular" >
				
				<div class="row">
                 
                 	<div class="col-md-2">
						<input type="hidden" class="form-control" id= "examID" name="ExaminationID" value="<%=ExamID%>" >
						<input type="text" class="form-control" name="examName" value="<%=ExamName%>" readonly="readonly">
					</div>
					
                 <% if(loginform.getMedium().equals("mr")){ %>
                 	
					<div class="col-md-2">
						<s:select list="StandardListForExtraCurricular" onchange="retrieveSubject(examID.value, this.value);" class="form-control" headerKey="-1" id="SID" headerValue="इयत्ता निवडा" name="standardDivName" ></s:select>
					</div>
				
		            <div class="col-md-2" id="editSubjectDivID">
		            	
						<s:select list="SubjectListForExtraCurricularActivity" class="form-control" headerKey="0" id="editSubDivID"  headerValue="विषय निवडा" name="subjectID" ></s:select>
		            </div>
					
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" id="loadSubmitID" onclick="return checkStandard(SID.value, editSubDivID.value, examID.value);">विद्यार्थी पहा</button>
					</div>
					
                 <% }else{ %>
                 
					<div class="col-md-2">
						<s:select list="StandardListForExtraCurricular" onchange="retrieveSubject(examID.value, this.value);" class="form-control" headerKey="-1" id="SID" headerValue="Select Standard" name="standardDivName" ></s:select>
					</div>
				
		            <div class="col-md-2" id="editSubjectDivID">
		            	<s:select list="SubjectListForExtraCurricularActivity" class="form-control" headerKey="0" id="editSubDivID"  headerValue="Select Subject" name="subjectID" ></s:select>
		            </div>
					
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" id="loadSubmitID" onclick="return checkStandard(SID.value, editSubDivID.value, examID.value);">Load Students</button>
					</div>
					
                 <% } %>
					
				</div>
			
			<div class="tab-pane fade in active">
				
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
	                          
	                         <% if(GradeBased== 1){ %>
	                         
	                          <th><% if(loginform.getMedium().equals("mr")){ %> ग्रेड <% }else{ %> Grade <% } %></th>
	                          
	                          <%}else{ %>
	                          
	                          <th><% if(loginform.getMedium().equals("mr")){ %> गुण प्राप्त झाले <% }else{ %> Marks Obtained <% } %></th>
	                         
	                          <th><% if(loginform.getMedium().equals("mr")){ %> Out of <% }else{ %> Out of <% } %></th>
	                          
	                          <%} %>
	                          
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
				                   		onchange="disable(this.value, 'marksObtainedID<s:property value="studentID"/>','gradeBasedID<s:property value="studentID"/>', 'newAbsentFlagID<s:property value="studentID"/>',<s:property value="studentID"/>,'studentID<s:property value="studentID"/>','subjectAssessmentID<s:property value="studentID"/>','totalMarksID<s:property value="studentID"/>','subjectID<s:property value="studentID"/>','newgradeBasedID<s:property value="studentID"/>');" 
				                   		/>Yes
				                   		<input type="radio" name="newName<s:property value="studentID"/>"  value="0" id="absentFlag0ID<s:property value="studentID"/>"
		    							onchange="disable(this.value, 'marksObtainedID<s:property value="studentID"/>','gradeBasedID<s:property value="studentID"/>', 'newAbsentFlagID<s:property value="studentID"/>',<s:property value="studentID"/>,'studentID<s:property value="studentID"/>','subjectAssessmentID<s:property value="studentID"/>','totalMarksID<s:property value="studentID"/>','subjectID<s:property value="studentID"/>','newgradeBasedID<s:property value="studentID"/>');" 
		    							checked="checked" />No
		    							<input type="hidden" name="" value="0" id="newAbsentFlagID<s:property value="studentID"/>">  
			                   		</div>
		                   		</td>
		                   	
		                   	<% if(GradeBased== 1){ %>
		                   		<% if(SubjectType == "1"){%>
		                   			
		                   			<td>
		                   			<input type="hidden" class="form-control" readonly="readonly" id="marksObtainedID<s:property value="studentID"/>" name="">
		 			       			<input type="hidden" name="" id="totalMarksID<s:property value="studentID"/>" value="<s:property value="totalMarks"/>" >
		                   			<select class="form-control"  id="gradeBasedID<s:property value="studentID"/>" name="" onchange="assignName('marksObtainedID<s:property value="studentID"/>',<s:property value="studentID"/>,'studentID<s:property value="studentID"/>','gradeBasedID<s:property value="studentID"/>','subjectAssessmentID<s:property value="studentID"/>','totalMarksID<s:property value="studentID"/>','subjectID<s:property value="studentID"/>','newgradeBasedID<s:property value="studentID"/>', 'newAbsentFlagID<s:property value="studentID"/>','newName<s:property value="studentID"/>', 1, 'Grade');">
			                   			<option value="">Select Grade</option>
			                   			<option value="G">G</option>
			                   			<option value="S">S</option>
			                   			<option value="NI">NI</option>
			                   			<option value="-">-</option>
									</select>
		                   			</td>
		                   			
		                   			<%}else{ %>
		                   			
		                   			<td>
		                   			<input type="hidden" class="form-control" readonly="readonly" id="marksObtainedID<s:property value="studentID"/>" name="">
		 			       			<input type="hidden" name="" id="totalMarksID<s:property value="studentID"/>" value="<s:property value="totalMarks"/>" >
		                   			<select class="form-control" id="gradeBasedID<s:property value="studentID"/>" name="" onchange="assignName('marksObtainedID<s:property value="studentID"/>',<s:property value="studentID"/>,'studentID<s:property value="studentID"/>','gradeBasedID<s:property value="studentID"/>','subjectAssessmentID<s:property value="studentID"/>','totalMarksID<s:property value="studentID"/>','subjectID<s:property value="studentID"/>','newgradeBasedID<s:property value="studentID"/>', 'newAbsentFlagID<s:property value="studentID"/>','newName<s:property value="studentID"/>', 1, 'Grade');" >
		                   				<option value="">Select Grade</option>
		                   				<option value="A1">A1</option>
		                   				<option value="A2">A2</option>
		                   				<option value="B1">B1</option>
		                   				<option value="B2">B2</option>
		                   				<option value="C1">C1</option>
		                   				<option value="C2">C2</option>
		                   				<option value="D">D</option>
		                   				<option value="E">E</option>
		                   				<option value="-">-</option>
		                   			</select>
		                   			
		                   			</td>
		                   		<%} %>
		     
		                   	<%}else{ %>
		                   		
		                   		<td><input type="number" class="form-control" id="marksObtainedID<s:property value="studentID"/>" name="" onkeyup="assignName('marksObtainedID<s:property value="studentID"/>',<s:property value="studentID"/>,'studentID<s:property value="studentID"/>','gradeBasedID<s:property value="studentID"/>','subjectAssessmentID<s:property value="studentID"/>','totalMarksID<s:property value="studentID"/>','subjectID<s:property value="studentID"/>','newgradeBasedID<s:property value="studentID"/>', 'newAbsentFlagID<s:property value="studentID"/>','newName<s:property value="studentID"/>','0', 'Marks');"> </td>
		                   		
		                   		<td><s:property value="totalMarks"  /><input type="hidden" name="" id="totalMarksID<s:property value="studentID"/>" value="<s:property value="totalMarks"/>" > 
		                   		
		                   		<input type="hidden" class="form-control" id="gradeBasedID<s:property value="studentID"/>" readonly="readonly" name="" >
		                   		
		                   		</td>
	         					
		                   	<%} %>
		                   		 
		                   	</tr>
		                   
		                   </s:iterator>
		                   <%
			                   	List<String> gradeList = new ArrayList<String>();
				                 gradeList.add("G");
				                 gradeList.add("S");
				                 gradeList.add("NI");
				                 gradeList.add("-");
				                 
				                 List<String> gradeList1 = new ArrayList<String>();
		                   			gradeList1.add("A1");
		                   			gradeList1.add("A2");
		                   			gradeList1.add("B1");
		                   			gradeList1.add("B2");
		                   			gradeList1.add("C1");
		                   			gradeList1.add("C2");
		                   			gradeList1.add("D");
		                   			gradeList1.add("E");
		                   			gradeList1.add("-");
		                   			
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
		                   			onchange="disable1(this.value, 'edidmarksObtainedID<%=form.getStudentID() %>','editgradeBasedID<%=form.getStudentID() %>', 'absntFlagID<%=form.getStudentID() %>');" 
		                   			 />Yes
		    						<input type="radio" name="name<%=form.getStudentID() %>"  value="0" 
		    						onchange="disable1(this.value, 'edidmarksObtainedID<%=form.getStudentID() %>','editgradeBasedID<%=form.getStudentID() %>', 'absntFlagID<%=form.getStudentID() %>');"  />No
		                   			<%
		                   				}else{
		                   			%>
		                   			<input type="radio" name="name<%=form.getStudentID() %>" value="1"
		                   			onchange="disable1(this.value, 'edidmarksObtainedID<%=form.getStudentID() %>','editgradeBasedID<%=form.getStudentID() %>', 'absntFlagID<%=form.getStudentID() %>');"   />Yes
		    						<input type="radio" name="name<%=form.getStudentID() %>" checked="checked" value="0" 
		    						onchange="disable1(this.value, 'edidmarksObtainedID<%=form.getStudentID() %>','editgradeBasedID<%=form.getStudentID() %>', 'absntFlagID<%=form.getStudentID() %>');"  />No
		    						<%
		                   				}
		    						%>
		    						<input type="hidden" id="absntFlagID<%=form.getStudentID() %>" name="editAbsentFlag" value="<%=form.getAbsentFlag()%>">
		                   		</div>
		                   	</td>
		                   		<% if(GradeBased == 1){ %>
		                   		 
		                   			<% if(SubjectType == "1"){
			                   			if(form.getAbsentFlag() == 1){	
			                   		%>
			                   		<td>
			                   				<input type="hidden" name="editMarksObtainedArr" value="<%=form.getMarksObtained() %>" >
			                   				<input type="hidden" name="editTotalMarks" value="<%=form.getTotalMarks()%>">
			                   			<select class="form-control" readonly="readonly" id="editgradeBasedID<%=form.getStudentID() %>" name="editGradeObtainedArr" >
				                   			<option value="">Select Grade</option>
				                   			
				                   			<%
							       				for(String grade:gradeList){
							       					if(grade.equals(form.getGrade())){
							       						
							       			%>
							       				<option value="<%=grade %>" selected="selected"><%=grade %></option>
							       			<%
							       					}else{
							       			%>
							       				<option value="<%=grade %>"><%=grade %></option>
							       			<%
							       					}
							       				}
							       			%>
										 </select>
			                   			</td>
			                   		<%
			                   			}else{
			                   		%>
			                   		<td>
			                   				<input type="hidden" name="editMarksObtainedArr" value="<%=form.getMarksObtained() %>" >
			                   				<input type="hidden" name="editTotalMarks" value="<%=form.getTotalMarks()%>">
			                   			<select class="form-control" id="editgradeBasedID<%=form.getStudentID() %>" name="editGradeObtainedArr" >
				                   			<option value="">Select Grade</option>
				                   			
				                   			<%
							       				for(String grade:gradeList){
							       					if(grade.equals(form.getGrade())){
							       						
							       			%>
							       				<option value="<%=grade %>" selected="selected"><%=grade %></option>
							       			<%
							       					}else{
							       			%>
							       				<option value="<%=grade %>"><%=grade %></option>
							       			<%
							       					}
							       				}
							       			%>
										 </select>
			                   			</td>
			                   		<%
			                   			}
			                   		%>
			                   			
			                   		<%}else{ 
			                   			if(form.getAbsentFlag() == 1){
			                   		%>
			                   		<td>
				                   			<input type="hidden" name="editMarksObtainedArr" value="<%=form.getMarksObtained() %>" >
				                   			<input type="hidden" name="editTotalMarks" value="<%=form.getTotalMarks()%>">
			                   			 <select class="form-control" readonly="readonly" id="editgradeBasedID<%=form.getStudentID() %>" name="editGradeObtainedArr" >
			                   				<option value="">Select Grade</option>
			                   				<%
							       				for(String grade1:gradeList1){
							       					if(grade1.equals(form.getGrade())){
							       						
							       			%>
							       				<option value="<%=grade1 %>" selected="selected"><%=grade1 %></option>
							       			<%
							       					}else{
							       			%>
							       				<option value="<%=grade1 %>"><%=grade1 %></option>
							       			<%
							       					}
							       				}
							       			%>
			                   			 </select>
			                   		  </td>
			                   		<%
			                   			}else{
			                   		%>
			                   		<td>
				                   			<input type="hidden" name="editMarksObtainedArr" value="<%=form.getMarksObtained() %>" >
				                   			<input type="hidden" name="editTotalMarks" value="<%=form.getTotalMarks()%>">
			                   			 <select class="form-control" id="editgradeBasedID<%=form.getStudentID() %>" name="editGradeObtainedArr" >
			                   				<option value="">Select Grade</option>
			                   				<%
							       				for(String grade1:gradeList1){
							       					if(grade1.equals(form.getGrade())){
							       						
							       			%>
							       				<option value="<%=grade1 %>" selected="selected"><%=grade1 %></option>
							       			<%
							       					}else{
							       			%>
							       				<option value="<%=grade1 %>"><%=grade1 %></option>
							       			<%
							       					}
							       				}
							       			%>
			                   			 </select>
			                   		  </td>
			                   		<%
			                   			}
			                   		%>
			                   			
			                   		<%} %>
		                   			<td>
				                   		<a id ="retestID<%=form.getStudentID() %>" href="javascript:addRetestRow('retestID<%=form.getStudentID() %>','editRetestID<%=form.getStudentID() %>','editgradeBasedID<%=form.getStudentID() %>','StudentTRID<%=form.getStudentID() %>','<%=SubjectType%>');">
					                   			<img src="images/addBill2.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
								       			onmouseout="this.src='images/addBill2.png'" alt="Add Restest Marks" title="Add Restest Marks" />
										</a>
										
						       			<input type="hidden" id = "editRetestID<%=form.getStudentID() %>" name="editRetestArr" value="0">
						       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
							       			onmouseout="this.src='images/addBill.png'" alt="View History" title="View History" 
						       			onclick="viewStudentAssessmentHistroy('<%=form.getRegistrationID() %>','<%=form.getSubjectAssessmentID() %>','<%=GradeBased%>');"/>
		                   			</td>
		                   	
		                   		<%}else{
		                   			if(form.getAbsentFlag() == 1){	
		                   		%>
		                   		<td><input type="number" class="form-control" readonly="readonly" name="editMarksObtainedArr" id="edidmarksObtainedID<%=form.getStudentID() %>" value="<%=form.getMarksObtained() %>"  > </td>
		                   		<%
		                   			}else{
		                   		%>
		                   		<td><input type="number" class="form-control" name="editMarksObtainedArr" id="edidmarksObtainedID<%=form.getStudentID() %>" value="<%=form.getMarksObtained() %>"  > </td>
		                   		<%
		                   		}
		                   		%>
		                   		
		                   		<td><%=form.getTotalMarks() %> <input type="hidden" name="editTotalMarks" value="<%=form.getTotalMarks()%>">
		                   		<input type="hidden" class="form-control" name="editGradeObtainedArr" id="editgradeBasedID<%=form.getStudentID() %>" readonly="readonly" value="<%=form.getGrade() %>" >
		                   		</td>
		                   	
		                   		<td>
			                   		<a id ="newRetestID<%=form.getStudentID() %>" href="javascript:addRetestRow1('newRetestID<%=form.getStudentID() %>','editRetestID<%=form.getStudentID() %>','edidmarksObtainedID<%=form.getStudentID() %>','StudentTRID<%=form.getStudentID() %>');">
				                   		<img src="images/addBill2.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
							       			onmouseout="this.src='images/addBill2.png'" alt="Add Restest Marks" title="Add Restest Marks" /> 
						       		</a>
						       		
					       			<input type="hidden" id = "editRetestID<%=form.getStudentID() %>" name="editRetestArr" value="0">
					       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
						       			onmouseout="this.src='images/addBill.png'" alt="View History" title="View History" 
						       			onclick="viewStudentAssessmentHistroy('<%=form.getRegistrationID() %>','<%=form.getSubjectAssessmentID() %>','<%=GradeBased%>');"/>
		                   		</td>
		                   		<%} %>
		                   		
		                   	</tr>
		                  <%} %> 
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
              <%} %>  
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
