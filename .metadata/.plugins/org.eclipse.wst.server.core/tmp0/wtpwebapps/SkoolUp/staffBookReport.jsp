<%@page import="javassist.expr.NewArray"%>
<%@page import="com.kovidRMS.daoImpl.*"%>
<%@page import="com.kovidRMS.daoInf.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="java.util.*"%>
     <%@page import="com.kovidRMS.form.*"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Staffs Report - SkoolUp</title>
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
	<link href="css/jquery.multiselect.css" rel="stylesheet" type="text/css">

	<link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" />
	<link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />
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
   

 </script>

<script type="text/javascript">

	function submitReportForm(subID, classID, student, status){
		if(classID == "-1" ){
  			alert("Please select class.");
  			return false;
  			
  		}else if(student == "-1" ){
  			alert("Please select student.");
  			return false;
  			
  		}else if(status == "-1" ){
  			alert("Please select status.");
  			return false;
  			
  		}else{
  			
  			$("#validate-basic").attr("action","ExportStaffsReport");
  			$("#validate-basic").submit();
  			 
  		}
	}

</script>
	
<script type="text/javascript">
	
	function retrieveStaff(role){
			
		if(role == "-1"){
			alert("No class is selected. Please select class.");
				
			var array_element = "<select name='' id='' class='form-control'"+
			"> <option value='-1'>Select Staff</option></select>";
				
			document.getElementById("StudID").innerHTML = array_element;
	
		}else{
				
			retrieveStaff1(role);
		}
	}
	
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function retrieveStaff1(role) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "<select name='staffString' class='form-control' onchange='checkStaffs(role);' "+
						"> <option value='all'>All</option><option value='-1'>Select Staff</option>";
						
						var check = 0;
						var StudentID=0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
							StaffID = array.Release[i].staffID;
							array_element += "<option value='"+StaffID+"'>"+array.Release[i].staffName+"</option>";
						}
	
						array_element += " </select>";
						
						if(check == 0){
							
							alert("No Staff found");
							
							var array_element = "<select name='' id='' class='form-control'"+
							"> <option value='-1'>Select Staff</option></select>";
							
							document.getElementById("StudID").innerHTML = array_element;
						
						}else{
							
							document.getElementById("StudID").innerHTML = array_element;	
						}
				}
			};
			xmlhttp.open("GET", "RetrieveStaffDetailsByRole?role="
					+ role+"&status=Report", true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
</script>

<script type="text/javascript">
	function checkStaffs(roleID){
	
		if(roleID == "-1"){
			alert("Please select role.");
			return false;
			
		}else{
			
			return true;	
		}
	}

	function checkStatus(student, classID){
		
		if(student == "-1"){
			alert("Please select student.");
			return false;
		
		}else if(classID == "-1"){
			alert("Please select class.");
			return false;
		
		}else{
			
			return true;	
			
		}
	}
	
</script>

</head>

<body>
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Staffs Report</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Staffs Report</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                Book Details
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
		  
		  <form id="validate-basic" action="ExportStaffsReport" method="POST" data-validate="parsley" class="form parsley-form">
				
                <div class="row">
               		<div class="col-md-2">
                    	<s:select list="AcademicYearNameList" class="form-control" headerKey="-1" headerValue="Select Academic Year" required= "required" name="academicYearID" ></s:select>
            		</div>
            		
               		<div class ="col-md-3">
	                 	<s:select class="form-control" list="#{'Teaching':'Teaching', 'NonTeaching':'Non-Teaching'}" id="roleID" value="-1" headerKey="-1" 
	                 		headerValue="Select Role" name="role" onchange="retrieveStaff(this.value);" required= "required"></s:select>
	                </div>
	             	
	          		<div class ="col-md-2">
		              <select name="staffString" id="StudID" class="form-control" onchange="checkStaffs(roleID.value);" required= "required">
					      <option value="-1" >Select Staff</option>
					  </select>
				    </div>
				    
                	<div class="col-md-2">
	                    <s:select list="#{'issued':'Issued', 'returned':'Returned', 'all':'All'}" class="form-control" id="StatusID" value="-1" headerKey="-1" 
	                   	 	onchange="checkStatus(StudID.value, roleID.value);" headerValue="Select Status" name="Status" required= "required">
	                    </s:select>
					</div>
					
					<div class="col-md-3">
						<div class="col-sm-12 col-md-12 col-lg-12 col-xs-12" align="right">
							<button class="btn btn-success" type="button" id="submitID" onclick="submitReportForm('submitID', roleID.value, StudID.value, StatusID.value);">Download Books Report</button>
						</div>
					</div>
					
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
  
  <script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>  
  <script type="text/javascript" src="js/bootstrap-multiselect.js"></script>
  
</body>
</html>
