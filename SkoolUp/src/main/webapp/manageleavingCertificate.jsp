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

	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		ConfigurationDAOInf configurationDAOInf = new ConfigurationDAOImpl();
    	
    	String AcademicYearName = daoinf.retrieveAcademicYearName(loginform.getOrganizationID());
    	
		int AcademicYearID = loginform.getAcademicYearID();
		
    	String loadStudentSearch = (String) request.getAttribute("loadStudentSearch");
		
		if(loadStudentSearch == null || loadStudentSearch == ""){
			loadStudentSearch = "dummy";
		}
		
    	List<ConfigurationForm> studentsList = (List<ConfigurationForm>) request.getAttribute("studentsList");
     %>
     
  <title><% if(loginform.getMedium().equals("mr")){ %>शाळा सोडल्याचे प्रमाणपत्र व्यवस्थापन - SkoolUp <% }else{ %>Manage Leaving Certificate - SkoolUp<% } %></title>
  
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
            width: 100px; /*the image width*/
      	    height: 100px; /*the image height*/
       	    left: 50%;
            top: 50%;
        	margin-left: -50px; /*half the image width*/
      		margin-top: -50px; /*half the image height*/
      }
       
</style>

<script type="text/javascript">
    $(window).scroll(function(){
	    if ($(window).scrollTop()  >= 1000) {
	        $('#fixedDiv').slideDown(10000);
	        $('#fixedNav').slideDown(10000);
	       $('#fixedDiv').addClass('fixed-header');
	       $('#fixedNav').addClass('fixed-navbar');
	    }
	    else {
	       $('#fixedDiv').removeClass('fixed-header');
	       $('#fixedNav').removeClass('fixed-navbar');
	    }
	});
</script>

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
   
      function checkStandard(standard, division, subID){
  		
  		if(standard == "-1" ){
  			alert("Please select standard.");
  			return false;
  		}else if(division == "-1" ){
  			alert("Please select division.");
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
	    	
	    	$("#validate-basic").attr("action","LoadLeavingStudent");
  			$("#validate-basic").submit();
			  	  
		 	$("#"+subID).attr("disabled", "disabled");
		 	
  			return true;
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

	function submitReportForm(subID){
		
    	$("#validate-basic").attr("action","AllStudentLeavingReportPDF");
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
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>शाळा सोडल्याचे प्रमाणपत्र <% }else{ %>Leaving Certificate<% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>विद्यार्थ्यांची माहिती<% }else{ %>Student Details<% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
               <% if(loginform.getMedium().equals("mr")){ %>विद्यार्थ्यांची माहिती<% }else{ %>Student Details<% } %>
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
		
		<% if(loginform.getMedium().equals("mr")){ %>
		
		  <form id="validate-basic" action="LoadLeavingStudent" method="POST" data-validate="parsley" class="form parsley-form">
				
                <div class="row">
             		<div class="col-md-2">
						<s:select list="AcademicYearNameList" class="form-control" headerKey="-1" id="inActiveID" headerValue="शैक्षणिक वर्ष  निवडा " name="academicYearID"></s:select>
					</div>
					
				   <div class="col-md-2">
					   <s:select list="StandardList" class="form-control" headerKey="-1"  headerValue="इयत्ता निवडा"  name="standardID" id="SID" onchange="retrieveDivision(this.value);"></s:select>
				   </div>
		           
		        <%
					if(loadStudentSearch.equals("Enabled")){
				%>
				<div class="col-md-2">
					<s:select list="DivisionList" name="divisionID" id="stdDivID" class="form-control" headerKey="-1" headerValue="तुकडी निवडा"></s:select>
				</div>
				<%
					}else{ %>
								
					 <div class="col-md-2">
				       <select name="divisionID" id="stdDivID" class="form-control">
			               <option value="-1">तुकडी निवडा</option>
			           </select>
		            </div>
		            
				<% } %>    
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" ID="submitID" onclick="return checkStandard(standardID.value, stdDivID.value, 'submitID')">विद्यार्थी पहा </button>
					</div>
					
				</div>
			
			</form>
			
			<hr>
			
			<div class="tab-pane fade in active">
			<%
				if(loadStudentSearch.equals("Enabled")){
			%>	
				<div class="row">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12" align="right">
						<button class="btn btn-success" type="button" id="downloadID" onclick="submitReportForm('downloadID');">सर्व प्रमाणपत्र डाउनलोड करा</button>
					</div>
				</div>
				
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <th>हजेरी क्रमांक</th>
	                        
	                          <th>विद्यार्थ्याचे नाव</th>
	                          
	                          <th>जीआर क्रमांक</th>
	                          
	                          <th>कृती</th>
	                          
	                      	</tr>
	                      </thead>
	                  <tbody>
	                      
	                   <% 
				       		for(ConfigurationForm form:studentsList){
				       			
				       %>
				        
				       <tr id="newTRID<%=form.getStudentID()%><%=form.getAYClassID()%>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 6%;">
				       			<%=form.getRollNumber()%>
				       		</td>	
				       		
				       		<td style="text-align: left;">
				       			<input type="hidden" name="newStudentID" value="<%=form.getStudentID()%>">
				       			<%=form.getStudentName()%>
				       		</td>
				       	
				       		<td style="text-align: left;">
				       			<input type="hidden" name="newGrNumber" value="<%=form.getGrNumber()%>">
				       			<%=form.getGrNumber()%>
				       		</td>
				       		
				       		<td>
				       			<a href="MarathiLeavingCertificate?studentID=<%=form.getStudentID()%>&AYClassID=<%=form.getAYClassID()%>&registrationID=<%=form.getRegistrationID()%>&studentName=<%=form.getStudentName()%>">Open</a>
				       		</td>
				       </tr> 
				        
				        <% 
				        	}
				        %>
	                  
	                      </tbody>
	                    </table>
		                
			        </div>
			 <% } %>    
				 </div>
				 
		<% }else{ %>
		   
		  <form id="validate-basic" action="LoadLeavingStudent" method="POST" data-validate="parsley" class="form parsley-form">
				
                <div class="row">
             		<div class="col-md-2">
						<s:select list="AcademicYearNameList" class="form-control" headerKey="-1" id="inActiveID" headerValue="Select Academic Year" name="academicYearID"></s:select>
					</div>
					
				   <div class="col-md-2">
					   <s:select list="StandardList" class="form-control" headerKey="-1"  headerValue="Select Standard"  name="standardID" id="SID" onchange="retrieveDivision(this.value);"></s:select>
				   </div>
		           
		        <%
					if(loadStudentSearch.equals("Enabled")){
				%>
				<div class="col-md-2">
					<s:select list="DivisionList" name="divisionID" id="stdDivID" class="form-control" headerKey="-1" headerValue="Select Division"></s:select>
				</div>
				<%
					}else{ %>
								
					 <div class="col-md-2">
				       <select name="divisionID" id="stdDivID" class="form-control">
			               <option value="-1">Select Division</option>
			           </select>
		            </div>
		            
				<% } %>    
					<div class="col-md-2">
						<button type="submit" class="btn btn-warning" ID="submitID" onclick="return checkStandard(standardID.value, stdDivID.value, 'submitID')">Load Students</button>
					</div>
					
				</div>
			
			</form>
			
			<hr>
			
			<div class="tab-pane fade in active">
		<%
			if(loadStudentSearch.equals("Enabled")){
		%>	
				<div class="row">
					<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12" align="right">
						<button class="btn btn-success" type="button" id="downloadID" onclick="submitReportForm('downloadID');">Download All Reports</button>
					</div>
				</div>
				
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <th>Roll No</th>
	                        
	                          <th>Student Name</th>
	                          
	                          <th>GR. No</th>
	                          
	                          <th>Action</th>
	                          
	                      	</tr>
	                      </thead>
	                  <tbody>
	                      
	                   <% 
				       		for(ConfigurationForm form:studentsList){
				       			
				       %>
				        
				       <tr id="newTRID<%=form.getStudentID()%><%=form.getAYClassID()%>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 6%;">
				       			<%=form.getRollNumber()%>
				       		</td>	
				       		
				       		<td style="text-align: left;">
				       			<input type="hidden" name="newStudentID" value="<%=form.getStudentID()%>">
				       			<%=form.getStudentName()%>
				       		</td>
				       	
				       		<td style="text-align: left;">
				       			<input type="hidden" name="newGrNumber" value="<%=form.getGrNumber()%>">
				       			<%=form.getGrNumber()%>
				       		</td>
				       		
				       		<td>
				       			<a href="LeavingCertificate?studentID=<%=form.getStudentID()%>&AYClassID=<%=form.getAYClassID()%>&registrationID=<%=form.getRegistrationID()%>&studentName=<%=form.getStudentName()%>">Open</a>
				       		</td>
				       </tr> 
				        
				        <% 
				        	}
				        %>
	                  
	                      </tbody>
	                    </table>
		                
			        </div>
			 <% } %>    
				 </div>
            
            <% } %>    
             </div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->
          
        </div> <!-- /.col -->

      </div> <!-- /.row -->


        
	</div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->


<%--   <script src="./js/libs/jquery-1.10.1.min.js"></script> --%>
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
