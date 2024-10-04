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
  <title>Report Card - SkoolUp</title>
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
 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"> </script>
 
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
		
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	ConfigurationForm conform = new ConfigurationForm();
    	
		int AcademicYearID = loginform.getAcademicYearID();
		
		String StandardName = daoinf.retrieveStandardName(loginform.getUserID(), AcademicYearID);
    	
    	int StandardID = daoinf.retrieveStandardID(loginform.getUserID(), AcademicYearID);
    	
		String DivisionName = daoinf.retrieveDivisionName(loginform.getUserID(), AcademicYearID);
    	
    	int DivisionID = daoinf.retrieveDivisionID(loginform.getUserID(), AcademicYearID);
		
    	HashMap<Integer, String> StandardList = daoinf.getStandard(loginform.getOrganizationID());
    	
    	String Term = (String) request.getAttribute("termValue");
    	
    	int divisionID = (Integer) request.getAttribute("divisionID");
    	
    	int standardID = (Integer) request.getAttribute("standardID");
    	
    	List<ConfigurationForm> studentsList = (List<ConfigurationForm>) request.getAttribute("studentsList");
    	
		String loadStudentSearch = (String) request.getAttribute("loadStudentSearch");
		
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
		
		String classTeacherCheck = (String) request.getAttribute("classTeacherCheck");
		
		if(classTeacherCheck == null || classTeacherCheck == ""){
			classTeacherCheck = "dummy";
		}
		
		String classTeacher10thCheck = (String) request.getAttribute("classTeacher10thCheck");
		
		if(classTeacher10thCheck == null || classTeacher10thCheck == ""){
			classTeacher10thCheck = "dummy";
		}
     %>

<script type="text/javascript">
	$(document).ready(function(){
		$('#reportLiID').addClass("active");
		//alert("hiii");	
	});
</script>

 <script type="text/javascript">

	
      function windowOpen(){
        document.location="welcome.jsp";
      }
      
      function windowOpen1(){
          document.location="RenderClassStudents";
        }
   
      function checkStandard(term){
  		
  		if(term == "-1" ){
  			alert("Please select term.");
  			return false;
  		}else{
  			$("#validate-basic").attr("action","LoadClassStudent");
  			$("#validate-basic").submit();
  			return true;
  		}
      }
 </script>
 
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
			}

		};
		xmlhttp.open("GET", "RetrieveDivisionListForStandard?standardID="
				+ standardID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
</script>

<script type="text/javascript">

	function submitReportForm(){
		$("#validate-basic").attr("action","AllStudentReportPDF");
		$("#validate-basic").submit();
	}
	
	function submitEmailReportForm(){
		$("#validate-basic").attr("action","AllStudentsEmailReportPDF");
		$("#validate-basic").submit();
	}
	
	/* To display loading image while page load */
	function showLoading(){
		$("html, body").animate({ scrollTop: 0 }, "fast");
		$(".loadingImage").show();
		$(".container").css("opacity","0.1");
		$(".navbar").css("opacity","0.1");
		return true;
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
        <h2 class="content-header-title">Report Card</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Student Details</li>
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
		  <form id="validate-basic" action="LoadClassStudent" method="POST" data-validate="parsley" class="form parsley-form" >
				
                <div class="row">
           
             		<div class="col-md-2">
                		<s:select list=" #{'Term I':'Term I','Term II':'Term II'}" class="form-control" headerKey="-1" id="termID" headerValue="Select Term"  name="term" ></s:select>
					</div>
             
					<div class="col-md-2">
						<input type="hidden" class="form-control" name="standardID" value="<%=StandardID%>" >
						<input type="text" class="form-control" name="standard" value="<%=StandardName%>" readonly="readonly">
							
					</div>
					
				   <div class="col-md-2">
				         <input type="hidden" class="form-control" name="divisionID" value="<%=DivisionID%>" >
						<input type="text" class="form-control" name="division" value="<%=DivisionName%>" readonly="readonly">	
		           </div>
				     
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" onclick="return checkStandard(termID.value)">Load Students</button>
						
					</div>
					
				</div>
			
			</form>
			
			<hr>
		
			<div class="tab-pane fade in active">
				<%
					if(loadStudentSearch.equals("Enabled")){
				%>
				
				<div class="row">
				
					<div class="col-sm-6 col-md-6 col-lg-6 col-xs-12" align="left" style="padding-top: 4%;">
						<button class="btn btn-success" type="button" onclick="submitReportForm();">Download All Reports</button>
					</div>
					<div class="col-sm-6 col-md-6 col-lg-6 col-xs-12" align="right">
						<span><font size="2px" color="red">Emailing all report might take some time. Kindly wait for some time after clicking on Email All Reports button. <b>DO NOT REFRESH PAGE OR CLOSE THE BROWSER.</b></font></span>
						<button class="btn btn-warning" type="button" id="reportID" onclick="submitEmailReportForm();">Email All Reports</button>
					</div>
				</div>
			
			<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
			
				<form id="validate-basic1" action="ConfigureClassStudentsStatus" method="POST" data-validate="parsley" onsubmit="return showLoading();" class="form parsley-form">
				
				<input type="hidden" class="form-control" name="standardID" value="<%=standardID%>" >
				<input type="hidden" class="form-control" name="divisionID" value="<%=divisionID %>" >
				<input type="hidden" class="form-control" name="term" value="<%=Term %>" >
				
				
				<div class="form-group">
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <th>Roll No</th>
	                          <th>Student Name</th>
	                      	  <th>Action</th>
	                       </tr>
	                      </thead>
	                  <tbody>
	                      
	                   <% 
	                   		String value = "";
				       		for(ConfigurationForm form:studentsList){
				       %>
				        
				       <tr id="newTRID<%=form.getStudentID()%><%=form.getAYClassID()%>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 6%;">
				       			<%=form.getRollNumber()%>
				       		</td>	
				       		
				       		<td style="text-align: left;">
					       		<input type="hidden" name="studID" value="<%=form.getRegistrationID()%>">
					       		<div class="col-md-8"><%=form.getStudentName()%></div>
					       
				       	    <% if(Term.equals("Term II")){ 
				       	    
				       	    	 value = "Present"; 
				       	    	 
				       	    	 String[] statusArray = {"Promoted to","Detained in","None of these"};
				       	    %>
				       	    <div class="col-md-4">
				       	    	<input type="hidden" name="result" value="<%=form.getResult()%>">
				       	    	
				       	    	<select class="form-control" id="statusID"  name="studentStatus" >
				       	    		<%
				       	    			for(int i = 0;i < statusArray.length; i++){
				       	    				if(statusArray[i].equals(form.getResult())){
				       	    		%>
				       	    			<option value="<%=form.getResult()%>" selected="selected"><%=form.getResult()%></option>
				       	    		<%
				       	    				}else{
				       	    		%>
				       	    			<option value="<%=statusArray[i]%>"><%=statusArray[i]%></option>
				       	    		<%
				       	    				}
				       	    			}
				       	    		%>
				       	    	</select>
				       	    </div>
				       	   
				       	    </td>
				       	   <% } %>
				       	      
				       		<td>
				       		 <%if(loginform.getBoard().equals("CBSE")){ %>
				       			<a href="ConfigureCBSCReport?studentID=<%=form.getStudentID()%>&term=<%=form.getTerm()%>&standardID=<%=StandardID%>&AYClassID=<%=form.getAYClassID()%>&registrationID=<%=form.getRegistrationID()%>&result=<%=form.getResult()%>">Report</a>
							 <% }else{ %>
							 	<a href="ConfigureSSCReport?studentID=<%=form.getStudentID()%>&term=<%=form.getTerm()%>&standardID=<%=StandardID%>&AYClassID=<%=form.getAYClassID()%>&registrationID=<%=form.getRegistrationID()%>&result=<%=form.getResult()%>">Report</a>
							 <% } %>				       			
				       			
				       			<a href="StudentExcelReport?studentID=<%=form.getStudentID()%>&term=<%=form.getTerm()%>&standardID=<%=StandardID%>&ayclassID=<%=form.getAYClassID()%>&registrationID=<%=form.getRegistrationID()%>&studentName=<%=form.getStudentName()%>">Excel Export</a>
				       			<a href="EmailStudentsReportPDF?studentID=<%=form.getStudentID()%>&term=<%=form.getTerm()%>&standardID=<%=StandardID%>&divisionID=<%=DivisionID%>&AYClassID=<%=form.getAYClassID()%>&registrationID=<%=form.getRegistrationID()%>&studentName=<%=form.getStudentName()%>&result=<%=form.getResult()%>">Email Reports</a>
				       		</td>
				       </tr> 
				        
				        <% 
				        	}
				        %>
	                  
	                      </tbody>
	                    </table>
		              </div> 
		              
		            <% if(value.equals("Present")){ %>  
			           <div class="form-group" align="center">
						  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
		              	  <button type="submit" class="btn btn-success" id="submitID" >Configure Status</button>
					   </div>
					<% } %>   
					
					 </form>
					    
			        </div>
			       <%
						}
			       %>
			       
				 </div>
			 
				 
			<% } %>
				
                
             </div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->
          
        </div> <!-- /.col -->

      </div> <!-- /.row -->


        
	</div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->


 <%--  <script src="./js/libs/jquery-1.10.1.min.js"></script> --%>
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