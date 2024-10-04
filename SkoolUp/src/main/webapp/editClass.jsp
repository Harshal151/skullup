<%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="java.util.List"%>
     <%@page import="com.kovidRMS.form.LoginForm"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Manage Class - SkoolUp</title>
<!-- Favicon -->
  <link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="./css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">
   <script type="text/javascript" src="https://use.fontawesome.com/4836edb23b.js"></script>

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
    $(window).scroll(function(){
    if ($(window).scrollTop() > 550) {
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
        document.location="welcome.jsp";
      }
      
      function addSetting(){
  		
  		var standard = $("#standardNameID").val();
  		
  		var SubjectID = $("#SubjectID").val();
  		
  		if(standard == ""){
  			alert('Please enter standard name.');
  		}else if(SubjectID == ""){
  			alert('Please enter Subject name.');
  		}else{
  			$("#myModal").modal('show');
  		}
  		
  	}
   
    </script>
    
    <!-- Function to div rows -->
    <script type="text/javascript">
    	var divCounter = 1;
    	
    	function addDivRow(division){
    		
    		var trID = "newDIvTRID"+divCounter;
    		
    		var trTag = "";
    		
    		var stringToAppend = "*"+division;
    		
    		trTag += "<tr id='"+trID+"'>"
    			  + "<td style='text-align:center;'>"+division+"</td>"
    			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + stringToAppend + "\",\"" + trID + "\");'/></td>"
    			  + "<tr>";
    			  
    		$(trTag).insertAfter($('#divisionTRID'));
    				
    		//appending values to dummySettingTextID text box
    		$('#dummySettingTextID').val($('#dummySettingTextID').val()+stringToAppend);
    		
    		divCounter++;
    		
    		$("#divisionID").val("");
    		
    	}
    	
    	function removeDivTR(stringToBeRemoved, trID){
    		
    		if(confirm("Are you sure you want to delete this row?")){
    		
	    		var divisionText = $('#dummySettingTextID').val();
	    		var newValue = divisionText.replace(stringToBeRemoved,'');
	        	
	        	//Updating new value to dummySettingTextID field
	        	$('#dummySettingTextID').val(newValue);
	        	
	        	$("#"+trID+"").remove();
	        	
    		}
        	
    	}
    </script>
    <!-- Ends -->
    
    <!-- Function to add standard row  -->
    <script type="text/javascript">
    	var standardCounter = 1;
    	
    	function addStandardRow(standard, subject, division){
    		if(standard == ""){
    			alert("Please enter standard.");
    		}else if(subject == ""){
    			alert("Please select subject.");
    		}else{
    			addStandardRow1(standard, subject, division);
    		}
    	}
    	
    	function addStandardRow1(standard, subject, division){
    		
    		var trID = "newStdTRID"+standardCounter;
    		
    		var sub = "";
    		
    		$('#SubjectID option:selected').each(function() {
    			sub += $(this).val()+",";
			});
    		
    		sub = sub.replace(/,\s*$/, "");
    		
    		//alert(sub);
			
			if(sub.includes("-1")){
				if(sub.includes(",")){
					sub = sub.replace("-1,","");
				}else{
					sub = sub.replace("-1","");	
				}
			}
    		
    		var trTag = "";
    		
    		trTag += "<tr id='"+trID+"'>"
    			  + "<td style='text-align:center;'>"+standard+"<input type='hidden' name='standardName' value='"+standard+"'></td>"
    			  + "<td style='text-align:center;'>"+sub+"<input type='hidden' name='subjectName' value='"+sub+"'><input type='hidden' name='divisionName' value='"+division+"'></td>"
    			  + "<td style='text-align:center;'><a href='javascript:viewDivision(\"" + division + "\")'>View Division</a></td>"
    			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeStandardTR(\"" + trID + "\");'/></td>"
    			  + "</tr>";
    		
    		$(trTag).insertAfter($('#standardTRID'));
    		standardCounter++;
    		
    		$("#standardNameID").val("");
    		$("#SubjectID").val("");
    		$("#dummySettingTextID").val("");
    		
    	}
    	
    	function removeStandardTR(trID){
    		if(confirm("Are you sure you want to delete this row?")){
    			$("#"+trID+"").remove();
    		}
    	}
    </script>
    <!-- Ends -->
    
    <!-- function to view div list -->
    <script type="text/javascript">
    	function viewDivision(division){
    		
    		//check wther division string starts with *, if so,
    		//remove first * and proceed further
    		if(division.startsWith("*")){
    			division = division.substr(1);
    		}
    		
    		//check whether division string contains * after removing 
    		//first *, if so, then split division string by * and display
    		// one by one divisions else display only single division
    		if(division.includes("*")){
    			
    			var divisionArr = division.split("*");
    			
    			var tableTag = "<table border='1' style='font-size: 14px;'>"
    						 + "<tr style='padding:5px;'><th>Division</th></tr>";
    						 
    				for(var i = 0; i < divisionArr.length; i++){
    					tableTag += "<tr><td style='padding:5px;'>"+divisionArr[i]+"</td></tr>";
    				}
    				
    				tableTag += "</table>";
    			
    		}else{
    			
    			var tableTag = "<table border='1' style='font-size: 14px;'>"
					 + "<tr style='padding:5px;'><th>Division</th></tr>"
					 + "<tr><td style='padding:5px;'>"+division+"</td></tr></table>";
    			
    		}
    		
    		$("#divTableID").html(tableTag);
    		
    		$("#viewDivModalID").modal("show");
    		
    	}
    </script>
    <!-- Ends -->

	<!-- Retrieving subject Name list -->
    
    <%
    	LoginForm form = (LoginForm) session.getAttribute("USER");
    
    	StuduntDAOInf daoinf = new StudentDAOImpl();
    	List<String> subjectList = daoinf.retrievesubjectList();
    %>
    
    <!-- Ends -->

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

</head>

<body>
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				DIVISION				
			</div>
			<div class="modal-body">
				<table border="1">
					<tr>
						<th>Divbision</th>
						<th>ACtion</th>
					</tr>
					<tr id="divisionTRID">
						<td><input type="text" id="divisionID" name="division" value="<s:property value='division'/>"></td>
						<td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
  								  onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Div"
									title="Add Div" onclick="addDivRow(divisionID.value);"/></td>
					</tr>
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>	

<div id="viewDivModalID" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				DIVISION List				
			</div>
			<div class="modal-body">
				
				<div id="divTableID"></div>
				
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>				
<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      

      <div class="content-header">
        <h2 class="content-header-title">Manage Class</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Manage Class</li>
        </ol>
      </div> <!-- /.content-header -->

      

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                Class Details
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
			  <div class="row">    
	            <div class="col-md-12">
	            	<form action="EditClass" method="POST">
	            	
	            		<div class="form-group">
	            		<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	            		<thead>
	            			<tr>
			            		<th>Standard Name</th>
			            		<th>Subject List</th>
			  					<th>Division</th>
			  					<th></th>
	            			</tr>
	            		</thead>
	            		<tr id="standardTRID">
	            			<td>
	            				<input type="text" name="standard" value="<s:property value='standard'/>" placeholder="Standard Name" id="standardNameID">
	            			</td>
	            			<td>
	            				<div class="form-group">
									 <s:select list="subjectList" id="SubjectID" class="form-control" required="reqiured" ></s:select>
				                </div>
	            			</td>
	            			<td>
        						<a data-toggle="modal" href="javascript:addSetting();">Add Division</a>
        						<input type="hidden" id="dummySettingTextID">
	            			</td>
	            			
	            			<td> <img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
  								  onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Standard"
									title="Add Standard" onclick="addStandardRow(standardNameID.value, SubjectID.value, dummySettingTextID.value);"/>
							</td>
	            		</tr>
	            		</table>
        			  
                    </div>
	                	<div class="form-group">
	                        <div class="col-md-12" align="center">
	                          <button type="button" onclick="windowOpen();" class="btn btn-primary">Cancel</button>
	                          <button type="submit"  class="btn btn-success ">Update Class</button>
	                        </div>
	                 	</div>
	                 	</form>
            </div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->
          
        </div> <!-- /.col -->

      </div> <!-- /.row -->

	</div>
	</div>

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
