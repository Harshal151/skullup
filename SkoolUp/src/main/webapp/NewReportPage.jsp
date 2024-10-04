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
      <%@page import="com.kovidRMS.util.ConfigXMLUtil"%> 
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Report Page - SkoolUp</title>
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
 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"> </script>
 
 <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.min.js"></script>
<script type="text/javascript" src="https://html2canvas.hertzen.com/dist/html2canvas.js"></script>

	<%
		LoginForm form = (LoginForm) session.getAttribute("USER");

		int userID = form.getUserID();
		
		LoginDAOInf daoInf = new LoginDAOImpl();
		
		String AcademicYearName = daoInf.retrieveAcademicYearName(form.getOrganizationID());
		
		String GradeValue = (String) request.getAttribute("GradeValue");
		
		String Gender = (String) request.getAttribute("gender");
		
		String loadStudents = (String) request.getAttribute("StudentsFound");
		
		if(loadStudents == null || loadStudents == ""){
			loadStudents = "dummy";
		}
		
		String classTeacher10thCheck = (String) request.getAttribute("classTeacher10thCheck");
		
		if(classTeacher10thCheck == null || classTeacher10thCheck == ""){
			classTeacher10thCheck = "dummy";
		}
		
		String StdDivName = (String) request.getAttribute("StdDivName");
	
		String statusValue = (String) request.getAttribute("statusValue");
		
    	List<ConfigurationForm> PersonalityDevelopmentGradeListNew = (List<ConfigurationForm>) request.getAttribute("PersonalityDevelopmentGradeListNew");
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

  <script type="text/javascript">
  	$(document).ready(function(){
		$('#reportLiID').addClass("active");
	});
  
    jQuery(document).ready(function($) {
        $(".scroll").click(function(event){     
            event.preventDefault();
            $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
        });
    });
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
     
</style>

<script type="text/javascript">
	
      function windowOpen(){
        document.location="welcome.jsp";
      }
  	
      function windowOpen1(){
         document.location="RenderClassStudents";
      }
      
	function gradeCkeck(gradeValue) {
			
		if(confirm("This student has scored E grade in "+gradeValue+". Are you sure you want to print this student's report?")){
			$("#validate-basic").submit();	
		}  
	}
</script>
	
<script type="text/javascript">
var margin = {
		  top: 0.05,
		  left: 0.05,
		  right: 0.05,
		  bottom: 0.05
		};

	var doc = new jsPDF(); 
	var specialElementHandlers = { 
		'#editor': function (element, renderer) {
			return true;
		} 
	}; 
	
	$(document).ready(function() { 
		   
		$('#btn').click(function () { 
			
			/*const AmiriRegular = "fonts/ARIALUNI.TTF";

			doc.addFileToVFS('ARIALUNI.TTF', AmiriRegular);
			doc.addFont('ARIALUNI.TTF', 'ARIALUNI', 'normal');

			doc.setFont('ARIALUNI'); // set font
			doc.setFontSize(20);

			//doc.text(100, 10, { align: 'right', lang: 'mr' });

			// Save the PDF
			doc.save('Test.pdf');
			
			 const myFont = "fonts/ARIALUNI.TTF"; // load the *.ttf font file as binary string*/

			// add the font to jsPDF
			//doc.addFileToVFS("ARIALUNI.TTF", myFont);
		//	doc.addFont("MyFont.ttf", "MyFont", "normal");
			doc.text(10, 10, 'TIMES_ROMAN');
			doc.setFont("TIMES_ROMAN", "NORMAL");
			doc.setFontSize(20);
			doc.setPage(1);
			doc.setLineWidth(0.05);
			doc.fromHTML($('#content').html(), 15, 15,  {
				'width': 50, 
				'elementHandlers': specialElementHandlers 
			},
			
			function(bla){doc.save('saveInCallback.pdf');},
			margin); 
		}); 
	});
	
	
	function getPDF(){
	
		var HTML_Width = $(".canvas_div_pdf").width();
		var HTML_Height = $(".canvas_div_pdf").height();
		var top_left_margin = 15;
		var PDF_Width = HTML_Width+(top_left_margin*2);
		var PDF_Height = (PDF_Width*1.5)+(top_left_margin*2);
		var canvas_image_width = HTML_Width;
		var canvas_image_height = HTML_Height;
		
		var totalPDFPages = Math.ceil(HTML_Height/PDF_Height)-1;
		
	
		html2canvas($(".canvas_div_pdf")[0],{allowTaint:true}).then(function(canvas) {
			canvas.getContext('2d');
			
			console.log(canvas.height+"  "+canvas.width);
			
			
			var imgData = canvas.toDataURL("image/jpeg", 1.0);
			var pdf = new jsPDF('p', 'pt',  [PDF_Width, PDF_Height]);
		    pdf.addImage(imgData, 'JPG', top_left_margin, top_left_margin,canvas_image_width,canvas_image_height);
			
			
			for (var i = 1; i <= totalPDFPages; i++) { 
				pdf.addPage(PDF_Width, PDF_Height);
				pdf.addImage(imgData, 'JPG', top_left_margin, -(PDF_Height*i)+(top_left_margin*4),canvas_image_width,canvas_image_height);
			}
			
		    pdf.save("StudentReportCard.pdf");
	    });
	};
</script>

</head>

<body onload="changeLanguageByButtonClick('<%=form.getMedium()%>');">

<img src="images/loading.gif" alt="Loading..." class="loadingImage" style="z-index:1;display: none;">
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="google_translate_element" style="display:none"></div>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Report Page</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li><a href="RenderClassStudents">Student Details</a></li>
          <li class="active">Report Page</li>
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

            <div class="canvas_div_pdf portlet-content" >
			
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
		    
		<form id="validate-basic" action="StudentReportPDF" method="POST" data-validate="parsley" class="form parsley-form" enctype="multipart/form-data">
		  <input type="hidden" name="standardID" value="<s:property value="standardID"/>">
		<div id="content" >
		<div class="row">  
		  
			 <div class="col-lg-2" style="text-align: center;"> 
				<img src= "<%= daoInf.retrieveBoardLogo(form.getOrganizationID()) %>" style="height: 100px;" alt="Board Logo">
			</div>
			  
			<div class="col-lg-8" style="text-align: center;font-weight: bold;">
			  	
			    <%= daoInf.retrieveOrganizationTagLine(form.getOrganizationID()) %>
			  	<br>
			    <%= daoInf.retrieveOrganizationName(form.getOrganizationID()) %>
			    <br>
				<%= daoInf.retrieveOrganizationAddress(form.getOrganizationID()) %>
				<br>
				<%= daoInf.retrieveOrganizationPhone(form.getOrganizationID()) %>
				
			</div>	
				
			<div class="col-lg-2" style="text-align: center;">
				<img src="<%= daoInf.retrieveOrganizationLogo(form.getOrganizationID()) %>" style="height: 100px;" alt="Organization Logo" >
			</div>
		
		</div>
		
		<hr style="border: 1px solid gray;">
		
		<div class="row" id= "content"style="margin: 0px;">
			<s:iterator  value="studentDetailsList">
		      <input type="hidden" name="studentName" value="<s:property value="studentName"/>">
		      <input type="hidden" name="studentID" value="<s:property value="studentID"/>">
		      <input type="hidden" name="registrationID" value="<s:property value="registrationID"/>">
		      <input type="hidden" name="AYClassID" value="<s:property value="AYClassID"/>">
		      <input type="hidden" name="term" value="<s:property value="term"/>">
		      <input type="hidden" name="result" value="<s:property value="result"/>">
		      
		      	<div class="row">
					<div class="col-lg-12" style="text-align: center;font-weight: bold;">
	             		 <s:property value="term"/> निकाल पत्रक  :- <%=AcademicYearName%>
	        		</div>
        		</div>	        
				<%-- <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;"> --%>
				<div class="row" style="font-weight: bold;">
				<% 	if(Gender=="Female"){%>
						<div class="col-lg-4" >Student's Name :- <s:property value="studentName"/></div>
				<%	}else{%>
						<div class="col-lg-4" >Student's Name :- <s:property value="studentName"/></div>
				<%	} %>
					
				   	<div class="col-lg-3">इ. तु. :- <s:property value="standard"/>&nbsp;&nbsp;<s:property value="division"/></div>
				                          
				   	<div class="col-lg-2" >परीक्षा क्रमांक  : <s:property value="rollNumber"/></div>
				</div>
				<div class="row" style="font-weight: bold;">
				<%-- <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;"> --%>
				
				   	<div class="col-lg-4" >जन्मदिनांक : <s:property value="dateOfBirth"/></div>
				   	
				   	<div class="col-lg-3"></div>
				                          
				   	<div class="col-lg-3"></div>
				    
				  	<div class="col-lg-2" >रजि. क्र. : <s:property value="grNumber"/></div>
			   
			 	</div>
			</s:iterator>
		</div>
		  	<%
					if(loadStudents.equals("Found")){
			%>
		  	<div class="row">
		    <div class="col-lg-12" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		     <!--  <b>Scholastic Areas : </b>    -->
		    <table id="" class="table table-striped table-bordered dt-responsive nowrap" >
		    
			    <thead>
					<tr>
						<th style="text-align: center;">अ. क्र</th>
						
						<th>विषय</th>
			            
			            <th style="text-align: center;">श्रेणी</th>
			            
			            <th style="width:5%;text-align: center;">वर्णनात्मक नोंदी </th>
			            
					</tr>
				</thead>
				
				<tbody>
				<%  int counter1 = 1; %>
					<s:iterator  value="ScholasticGradeList">
					
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 8%;">
				       			<%= counter1++ %>
				       		</td>
				       		
				       		<td style="text-align: left;width: 40%;">
				       			<s:property value="subject"/>
				       		</td>
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="grade"/>
				       		</td>
				       </tr> 
				        
				       </s:iterator>
				</tbody>
			</table>
		</div>  
		
		</div>
		  	<%}else if(loadStudents.equals("NotFound")){ %>
		    <div class="row">
		    <div class="col-lg-12" style="background:white;margin-left:80px;margin-right:0px;margin-bottom:20px;padding-top: 20px;width: 82%;">
		     <!--  <b>Scholastic Areas : </b>    -->
		    <table id="" class="table table-striped table-bordered dt-responsive nowrap" >
		    
			    <thead>
					<tr>
						<th style="text-align: center;">अ. क्र</th>
						
						<th>विषय</th>
			             
			   			<th style="text-align:center">श्रेणी</th>
			   			
			 			<th style="width: 50%;text-align: center;">वर्णनात्मक नोंदी </th>
			        </tr>
				</thead>
				
				<tbody>
				<%  int counter1 = 1; %>
				
					<s:iterator  value="ScholasticGradeList">
					
				       <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;width: 6%;">
				       		<td style="text-align: center;width: 8%;">
				       			<%= counter1++ %>
				       		</td>
				       		
				       		<td style="text-align: left;width: 10%;">
				       			<s:property value="subject"/>
				       		</td>
				       		
				       		<td style="text-align: center;width: 10%;">
				       			<s:property value="grade"/>
				       		</td>
				  
							<td style="text-align: center;width: 10%;">
				       			
				       		</td>
				       </tr> 
				        
				       </s:iterator>
				</tbody>
			</table>
		</div>  
		
		</div>
		<%} %>
		
		<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
			<s:if test="%{getNewAttendanceList().isEmpty()}"> </s:if>
			<s:else>
			<table id="" class="table table-striped table-bordered dt-responsive nowrap"  >
		    	<tbody>
					<tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;">
				     	<th style="width: 11%;">महिना </th>
				     <s:iterator  value="NewAttendanceList">
				     	<td style="text-align: left;">
				       		<s:property value="workingMonth"/>
				       	</td>
				      </s:iterator> 
				      <td style="text-align: left;">
							 पुढील वर्षी शाळा सुरु<br>
						 
				      </td>
				  	  <td style="text-align: left;">
				       	  पुढील वर्षाची 
				       
				      </td>
				   </tr> 
				    <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;">
				        <th style="width: 11%;">कामाचे दिवस </th>
				         <s:iterator  value="NewAttendanceList">
				     	  <td style="text-align: left;">
				       		<s:property value="workingDays"/>
				       	  </td>
				       	  </s:iterator> 
				       	  <td> 
				       	  	<div class="input-group date ui-datepicker">
		                      <input type="text" id="dateOfStartSchool" name="dateOfBirth" class="form-control" required="required" placeholder="dd/MM/yyyy" data-required="true">
		                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
		                  	</div>
		                  </td>
		                  <td>
		                  	इ.९  व  तु.अ
		                  </td>
				   </tr> 
				   <tr id="newTRID<s:property value="studentID"/>" style="font-size: 14px;">
				       <th style="width: 11%;">हजर दिवस </th>
				        <s:iterator  value="NewAttendanceList">
				     	  <td style="text-align: left;">
				       		<s:property value="presentDays"/>
				       	  </td>
				       </s:iterator> 
				        
				   </tr> 
				</tbody>
			</table>
			</s:else>
		</div>
			<div class="row" style="padding-top: 45px;">
			<div class="col-lg-3" >
				<b>वर्गशिक्षकाची स्वाक्षरी </b>
			</div>
		 		 
			<div class="col-lg-4" style="text-align: center;">
				<b>मुख्याध्यापिकांची स्वाक्षरी</b>
			</div>
			
			<div class="col-lg-5" style="text-align: center;">
				<b>तपासनीस  स्वाक्षरी</b>
			</div>	
			</div>
		</div>
			<div id="editor"></div>
			<%if(GradeValue == null || GradeValue == ""){ %>
				<div  class="row" align="center" style="margin-top: 5%;">
					  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
	                  <button type="button" id="btn" class="btn btn-success">Print</button>
	            </div>
				
			<%}else{ %>
			
				<div  class="row" align="center" style="margin-top: 5%;">
					  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
	                  <button type="button" id="btn" onclick="gradeCkeck('<%=GradeValue %>');" class="btn btn-success">Print</button>
	            </div>
            <%} %>
            
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
