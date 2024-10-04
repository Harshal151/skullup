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
  <title>Student Attendance - SkoolUp</title>
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

	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	
		int AcademicYearID = loginform.getAcademicYearID();
		
		String StandardName = daoinf.retrieveStandardName(loginform.getUserID(), AcademicYearID);
    	
    	int StandardID = daoinf.retrieveStandardID(loginform.getUserID(), AcademicYearID);
    	
		String DivisionName = daoinf.retrieveDivisionName(loginform.getUserID(), AcademicYearID);
		
    	int DivisionID = daoinf.retrieveDivisionID(loginform.getUserID(), AcademicYearID);
		
    	HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	
    	String workingDays = (String)(request.getAttribute("workingDays"));
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
      
      function windowOpen1(){
          document.location="RenderStudentAttendance";
        }
      
   
      function checkStandard(term,month){
  		
  		if(term == "-1" ){
  			alert("Please select term.");
  			return false;
  		}else if(month == "-1" ){
  			alert("Please select month.");
  			return false;
  		}else{
  			
  			$("#loadStudentFOrm").attr("action","LoadStudentForAttendance");
  		    $("#loadStudentFOrm").submit();
  		    
  		  $("html, body").animate({ scrollTop: 0 }, "fast");
  		$(".loadingImage").show();
  		$(".container").css("opacity","0.1");
  		$(".navbar").css("opacity","0.1");
  		return true;
  		
  		}
  		
      }
  	
      function checkStandard1(term,month){
    
   		$("#loadStudentFOrm").attr("action","StudentsAttendanceReportExcel");
	    $("#loadStudentFOrm").submit();
    
      }
      
 </script>
	 
	<%
		List<StudentForm> studentsList = (List<StudentForm>) request.getAttribute("studentsList");
	
		List<StudentForm> NewStudentsList = (List<StudentForm>) request.getAttribute("NewStudentsList");
	
		String loadStudentSearch = (String) request.getAttribute("loadStudentSearch");
	
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
		
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
		
	%>

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
			
			var array_element = "<select name='divisionID' id='' class='form-control'"+
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
				
					var array_element = "<select name='divisionID' class='form-control'"+
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
						
						var array_element = "<select name='divisionID' id='' class='form-control'"+
						"> <option value='-1'>Select Division</option></select>";
						
						document.getElementById("stdDivID").innerHTML = array_element;
						
					}else{
						
						document.getElementById("stdDivID").innerHTML = array_element;	
													
					}
			}return true;

		};
		xmlhttp.open("GET", "RetrieveDivisionListForStandard?standardID="
				+ standardID, true);
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
		
	function retrieveworkingMonth(term, standardID){
		
		if(term == "-1"){
			alert("No term is selected. Please select term.");
			
			var array_element = "<select name='workingMonth' id='' class='form-control'"+
			"> <option value='-1'>Select Month</option></select>";
			
			document.getElementById("monthID").innerHTML = array_element;
		
		}else{
			retrieveworkingMonth1(term, standardID);
		}
		
	}

	function retrieveworkingMonth1(term, standardID) {

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "<select name='workingMonth' class='form-control'"+
					"> <option value='-1'>Select Month</option>";
					
					var check = 0;
					/* For division */
					for ( var i = 0; i < array.Release.length; i++) {
						
						check = array.Release[i].check;

						array_element += "<option value='"+array.Release[i].workingMonth+"'>"+array.Release[i].workingMonth+"</option>";
					}

					array_element += " </select>";
					
					if(check == 0){
						
						alert("No workingMonth found");
						
						var array_element = "<select name='workingMonth' id='' class='form-control'"+
						"> <option value='-1'>Select Month</option></select>";
						
						document.getElementById("monthID").innerHTML = array_element;
						
					}else{
						
						document.getElementById("monthID").innerHTML = array_element;	
													
					}
			}

		};
		xmlhttp.open("GET", "RetrieveworkingMonthForAttendance?term="
				+ term + "&standardID=" + standardID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	
	function submitForm(subID){
  	  $("#loadStudentFOrm").attr("action","AddAttendance");
  	  $("#loadStudentFOrm").submit();
    
  	 $("#"+subID).attr("disabled", "disabled");
	
  	$("html, body").animate({ scrollTop: 0 }, "fast");
	$(".loadingImage").show();
	$(".container").css("opacity","0.1");
	$(".navbar").css("opacity","0.1");
	return true;
	
	}
</script>

<script type="text/javascript"> 

	function assignName(input, studentID, studHiddenID, check, WorkingDays, SpanID){
		//alert(input.value);
		var attendanceVal = $("#"+input).val();
		
		if(check == "Attendance"){
			
			if(attendanceVal == ""){
				
				$("#"+input).removeAttr("name","daysPresentArr");
				
				$("#"+studHiddenID).removeAttr("name", "studID");
				
			}else{
				
				if(attendanceVal <= WorkingDays){
					
					$("#"+SpanID).html("");
					$("#"+input).attr("name","daysPresentArr");
					$("#"+studHiddenID).attr("name", "studID");
					$("#submitID").attr("disabled", false);
					
				}else{
					
					$("#"+SpanID).html("Value entered must be less than or equal to "+WorkingDays);
					//alert("Value entered must be less than or equal to "+WorkingDays);
					$("#submitID").attr("disabled", true);
				}
			}
		}
	}
	
	function assignNameNew(input, WorkingDays, SpanID){
		//alert(input.value);
		var attendanceVal = $("#"+input).val();
		
		if(attendanceVal <= WorkingDays){
					
			$("#"+SpanID).html("");
			$("#submitID").attr("disabled", false);
					
		}else{
					
			$("#"+SpanID).html("Value entered must be less than or equal to "+WorkingDays);
			$("#submitID").attr("disabled", true);
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

<body onload="changeLanguageByButtonClick('<%=loginform.getMedium()%>');">

<img src="images/loading.gif" alt="Loading..." class="loadingImage" style="z-index:1;display: none;">
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="google_translate_element" style="display:none"></div>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      

      <div class="content-header">
        <h2 class="content-header-title">Student Attendance</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Student Attendance</li>
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
		  <form id="loadStudentFOrm" action="LoadStudentForAttendance" method="POST" data-validate="parsley" class="form parsley-form">
				
                <div class="row">
                
	                <div class="col-md-2">
						<input type="hidden" class="form-control" id= "standardID" name="standardID" value="<%=StandardID%>" >
						<input type="text" class="form-control" name="standard" value="<%=StandardName%>" readonly="readonly">
								
					</div>
						
					<div class="col-md-2">
					    <input type="hidden" class="form-control" name="divisionID" value="<%=DivisionID%>" >
						<input type="text" class="form-control" name="division" value="<%=DivisionName%>" readonly="readonly">	
			        </div>
		        
               		<div class="col-md-2">
                		
				  		<s:select list=" #{'Term I':'Term I','Term II':'Term II'}" class="form-control" headerKey="-1" id="termID" headerValue="Select Term"  name="term" onchange="retrieveworkingMonth(this.value, standardID.value);" ></s:select>
					</div>
					
	              	<div class="col-md-2" > 
	               	<%
						if(loadStudentSearch.equals("Enabled")){
					%>
						<s:select list="MonthList" name="workingMonth" id="monthID" class="form-control" headerKey="-1" headerValue="Select Month"></s:select>
					<%
						}else{
							
					%>
					
						 <select name="workingMonth"  id="monthID" class="form-control">
				            <option value="-1">Select Month</option>
				         </select>
					<%
						}
					%>
               		</div>
		        
			        <div class="col-md-2">
						<button type="button" class="btn btn-success" onclick="return checkStandard(termID.value,monthID.value)">Load Students</button>
							
					</div>
					
					<div class="col-md-2">
						<button type="button" class="btn btn-warning" onclick="checkStandard1(termID.value)">Attendance Report</button>
					</div>
					
				</div>
			
			<hr>
			
			<div class="tab-pane fade in active">
				<%
					if(loadStudentSearch.equals("Enabled")){
				%>
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <th>Roll No</th>
	                        
	                          <th>Student Name</th>
	                          
	                          <th>Present Days</th>
				       	
	                      	</tr>
	                      </thead>
	                  <tbody>
	                      
	                   <% 
				       		for(StudentForm form : studentsList){
				       			
				       %>
				        
				       <tr id="newTRID<%=form.getStudentID()%>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: left;width: 6%;">
				       		<input type="hidden" name="" id="studentID<%=form.getStudentID() %>" value="<%=form.getStudentID()%>">
				       			<%=form.getRollNumber()%>
				       		</td>	
				       		
				       		<td style="text-align: left;width: 25%;">
				       			<%=form.getStudentName()%>
				       		</td>
		                   	<td style="text-align: left;width: 15%;">
		                   		<input type="number" class="form-control" id="daysPresentID<%=form.getStudentID() %>" name="" onkeyup="assignName('daysPresentID<%=form.getStudentID() %>',<%=form.getStudentID() %>,'studentID<%=form.getStudentID() %>','Attendance',<%=workingDays %>,'spanID<%=form.getStudentID() %>');"> 
				       			<font style="color: red;" id = "spanID<%=form.getStudentID() %>"></font>
				       		</td>
					     	
					   </tr> 
				        
				        <% 
				        	}
				        %>
	                  
	                   <% 
				       		for(StudentForm form:NewStudentsList){
				       			
				       %>
				        
				       <tr id="newTRID<%=form.getStudentID()%>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: left;width: 6%;">
				       		<input type="hidden" name="studAttndsID" value="<%=form.getStudentAttendanceID() %>">
				       			<%=form.getRollNumber()%>
				       		</td>	
				       		
				       		<td style="text-align: left;width: 25%;">
				       			<%=form.getStudentName()%>
				       		</td>
		                   	<td style="text-align: left;width: 15%;">
		                   		<input type="number" class="form-control" name="editdaysPresentArr" id="editdaysPresentArrID<%=form.getStudentID() %>" value="<%=form.getDaysPresent() %>" onkeyup="assignNameNew('editdaysPresentArrID<%=form.getStudentID() %>',<%=workingDays %>,'newSpanID<%=form.getStudentID() %>');" > 
								<font style="color: red;" id = "newSpanID<%=form.getStudentID() %>"></font>
							</td>
					     	
					  </tr> 
				        
				        <% 
				        	}
				        %>
	                      </tbody>
	                    </table>
		                
			        </div>
			        <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" id = "submitID" class="btn btn-success" id="submitID" onclick="submitForm('submitID');">Save</button>
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
