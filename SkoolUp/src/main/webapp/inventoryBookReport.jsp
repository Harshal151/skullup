<%@page import="javassist.expr.NewArray"%>
<%@page import="com.kovidRMS.daoImpl.*"%>
<%@page import="com.kovidRMS.daoInf.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="java.util.HashMap"%>
     <%@page import="com.kovidRMS.form.*"%>
     <%@page import="java.util.List"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Inventory Report - SkoolUp</title>
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

<script type="text/javascript">
	$(document).ready(function(){
		$('#reportLiID').addClass("active");
		//alert("hiii");	
	});

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
    
 </script>

<script type="text/javascript">

	function submitReportForm(subID, status, section){
		if(status == "-1" ){
  			alert("Please select status.");
  			return false;
  		}if(section == "-1" ){
  			alert("Please select section.");
  			return false;
  		}else{
  			
  			/* $('html, body').animate({
		        scrollTop: $('body').offset().top
		 }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); 
		
	  	 	$("html, body").animate({ scrollTop: 0 }, "fast");
	    	$(".loadingImage").show();
	    	$(".container").css("opacity","0.1");
	    	$(".navbar").css("opacity","0.1"); */
	    	
	    	if(subID == "download"){
	    		$("#validate-basic").attr("action","ExportInventoryReport");
	  			$("#validate-basic").submit();
	    	}else{
	    		$("#validate-basic").attr("action","PrintInventoryReport");
	  			$("#validate-basic").submit();	
	    	}
			  	  
		 	//$("#"+subID).attr("disabled", "disabled");
		 }
	}

</script>
	
	<script type="text/javascript">
	    function viewSchoolSections(selectVal){ 
	    	if(selectVal == "schoolSections"){
	    		
	    		$("#sectionDivID").show();
	    	}else{
	    		
	    		$("#sectionDivID").hide();
	    	}
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
        <h2 class="content-header-title">Inventory Report</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Inventory Report</li>
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
		  
		  <form id="validate-basic" action="ExportInventoryReport" method="POST" data-validate="parsley" class="form parsley-form">
				
                <div class="row">
                	<div class="col-md-2">
                    	<s:select list="AcademicYearNameList" class="form-control" headerKey="-1" headerValue="Select Academic Year" required= "required" name="academicYearID" ></s:select>
            		</div>
            		
               		<div class="col-md-1" style="width: 135px;">
                		 <label for="author">Select Section : </label>
                	</div>
                	
					<div class="col-md-2">
	                    <s:select list="SectionList" class="form-control" id="SectionID" value="-1" headerKey="-1" headerValue="Select Section" required= "required" name="section" onchange="viewSchoolSections(this.value);">
	                    </s:select>
					</div>
					
					<div class="col-md-2" style="display:none;" id="sectionDivID" >
                   		<s:select list="#{'primary':'For primary','S -':'For secondary','TC -':'For teachers', 'D ':'For donated', 'P P ':'For pre-primary','P. P. D.':'Donated for pre-primary', 'CD_primary':'For CD & DVD primary', 'P. D.':'Donated for primary', 'CD_secondary':'For CD & DVD secondary', 'S. D. ':'Donated for secondary'}" 
                  	 		class="form-control" id="sectionsID" headerKey="-1" headerValue="Select Criteria" name="schoolSection" required= "required"></s:select>
                   	</div>
                       
                	<div class="col-md-1" style="width: 135px;">
                		 <label for="author">Select Status : </label>
                	</div>
                	
                	<div class="col-md-2">
	                    <s:select list="#{'available':'Available','on-hold':'On-hold','issued':'Issued','withdrawn':'Withdrawn','damaged':'Damaged','lost':'Lost',
	                    	'maintenance':'Maintenance','all':'All'}" class="form-control" id="StatusID" value="-1" headerKey="-1" headerValue="Select Status"
	                    	 name="Status" required= "required">
	                    </s:select>
					</div>
					
					<div class="col-md-3">
						<div class="" align="right">
							<button class="btn btn-success" type="button" id="submitID" onclick="submitReportForm('download', StatusID.value, SectionID.value);">Download</button>
							
							<button class="btn btn-warning" type="button" id="" onclick="submitReportForm('print', StatusID.value, SectionID.value);">Print</button>
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
