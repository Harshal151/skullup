<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoInf.*"%>
     <%@page import="com.kovidRMS.daoImpl.*"%>
     <%@page import="java.util.*"%>

<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Configure Section - SkoolUp</title>
<!-- Favicon -->
 		<link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  
   <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">
   

  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/morris/morris.css">
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">
  <link rel="stylesheet" href="./js/plugins/select2/select2.css">
  <link rel="stylesheet" href="./js/plugins/fullcalendar/fullcalendar.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
  
 <link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" /> 
 <link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />

 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
 
  <script type="text/javascript">
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
		$('#administrationLiID').addClass("active");
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
	
	function windowOpen1(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="RenderConfigureSection";
     }
	
	function windowOpen2(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="ViewAllSections";
     }
	
	
	function submitForm(loadFormID, subID, Action){
   	 
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
	    	
	    	$("#"+loadFormID).attr("action", Action);
			$("#"+loadFormID).submit(); 
			  	  
		 	$("#"+subID).attr("disabled", "disabled");
		 	
	    	return true;
	   }
	
	function logoPicShow(){
		$('#logoID1').hide();
		$('#logoID').show();
		$('#logoClickID').click();
	}
    </script>
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableSection(url){
			if(confirm("Disabled Section cannot access application. Are you sure you want to disable this Section?")){
				document.location = url;
			}
    	}
    </script>
    
    <!-- Ends -->
    
    <!-- Delete user alert function -->
    
    <script type="text/javascript">
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete Section?")) {
				document.location = url;
			}
		}
	</script>
    
     <%
    	String componentMsg = (String) request.getAttribute("componentMsg");
    	if(componentMsg == null || componentMsg == ""){
    		componentMsg = "dummy";
    	}
    	
    	String componentEdit = (String) request.getAttribute("componentEdit");
    	if(componentEdit == null || componentEdit == ""){
    		componentEdit = "add";
    	}
    %>

<script type="text/javascript">
	$(function () {
		$('#standardID').multiselect({
			includeSelectAllOption: true
		});   
	});
</script>
    

</head>

<body>
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Manage Section</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Manage Section</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                Section List
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
	            	<form id="loadFormID" action="SearchSection" method="POST" onsubmit="submitForm('loadFormID', 'submitID', 'SearchSection');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" class="form-control" name="searchSection" style="width:100%;" placeholder="Search Section" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">Search Section</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllSections"> <button type="button" class="btn btn-warning" id=viewSubmitID onclick="submitForm('loadFormID', 'viewSubmitID', 'ViewAllSections');">View All Sections</button></a>
	                        </div>
	                    </div>
	             	</form> 
	             </div>  
	             </div>  
	              <hr>
	              <!-- Search div -->
	              
	             <div class="col-md-8"> 
				 	<%
			            if(componentMsg.equals("available")){
			         %>
                  
                <div class="table-responsive" style="margin-top:15px; overflow: auto;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Section</th>
                   	  <th data-sortable="true">Standard</th>
                   	  <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchSectionList" var="UserForm">
                    <tr>
                     	<td><s:property value="section" /></td>
						<td><s:property value="standard" /></td>
						<td align="center">
							<s:url id="approveURL" action="RenderEditSection">
								<s:param name="sectionID" value="%{sectionID}" />
								<s:param name="searchGenre" value="%{searchGenre}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Section" title="Edit Section" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableSectionURL" action="DisableSection">
								<s:param name="sectionID" value="%{sectionID}" />
								<s:param name="searchSection" value="%{searchSection}" />
							</s:url> 
							<s:a href="javascript:disableSection('%{disableSectionURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable Section" title="Disable Section" />
							</s:a>
						</td>
                       </tr>
                       </s:iterator>
                  
                   </tbody>
                </table>
                </div>
                
                 <%
			           }
                 %>
                    
		       </div>
		      <!-- ENds -->
               
               
               <!-- Add and Update Div -->
		       <div class="col-md-4">
		              
		        <%
		           if(componentEdit.equals("add")){
		        %>    
                <form id="validate-basic" action="AddSection" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'AddSection');" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="name">Section<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="section" placeholder="Section" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				<div class="form-group">
                  <label for="name">Standard<span class="required">*</span></label>
                  <div>
                  	<s:select list="StandardListNew" headerKey="-1" value="" id="standardID" required="required" name="standard" class="form-control" multiple="true"></s:select>
                 </div>
                </div>
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success">Add Section</button>
                </div>			
                
              </form>
		      
		      <%
		           }else{
	          %>
	          
	          <form id="validate-basic" action="EditSection" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'EditSection');" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="SectionEditList" var="concessionForm">
						  
				<input type="hidden" name="sectionID" value="<s:property value="sectionID" />" >
				<input type="hidden" name="searchSection" value="<s:property value="searchSection" />" >
               
                <div class="form-group">
                  <label for="name">Section<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="section" placeholder="Section" value="<s:property value="section" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
                
                <div class="form-group">
                  <label for="name">Standard<span class="required">*</span></label>
                  <div>
                  	<s:select list="StandardListNew" value="standardListValues" headerKey="-1" id="standardID" required="required" name="standard" class="form-control" multiple="true"></s:select>
                 </div>
                </div>
              
             	<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success">Update Section</button>
                </div>
              </s:iterator> 
             </form>
              
              <%
		           }
	          %>
	           
             </div>
                              
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
