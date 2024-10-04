<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoInf.*"%>
     <%@page import="com.kovidRMS.daoImpl.*"%>
     <%@page import="java.util.*"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Configure Book Type - SkoolUp</title>
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
		
        document.location="configurePVBookType.jsp";
     }
	
	function windowOpen2(){
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="ViewAllBookTypes";
     }
	
	function logoPicShow(){
		$('#logoID1').hide();
		$('#logoID').show();
		$('#logoClickID').click();
	}
    </script>
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableBookType(url){
			if(confirm("Disabled Book Type cannot access application. Are you sure you want to disable this Book Type?")){
				document.location = url;
			}
    	}
    </script>
    
    <!-- Ends -->
    
    <!-- Delete user alert function -->
    
    <script type="text/javascript">
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete Book Type?")) {
				document.location = url;
			}
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


</head>

<body>
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Manage Book Type</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Manage Book Type</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                Book Type List
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
	            	<form id="loadFormID" action="SearchBookType" method="POST" onsubmit="submitForm('loadFormID', 'submitID', 'SearchBookType');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" class="form-control" name="searchBookType" style="width:100%;" placeholder="Search Book Type" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">Search Book Type</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllBookTypes"> <button type="button" id="viewSubmitID" class="btn btn-warning" onclick="submitForm('loadFormID', 'viewSubmitID', 'ViewAllBookTypes');">View All Book Types</button></a>
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
                   	  <th data-sortable="true">Book Type</th>
                   	  <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchBookTypeList" var="UserForm">
                    <tr>
                     	<td><s:property value="type" /></td>
					
						<td align="center">
							<s:url id="approveURL" action="RenderEditBookType">
								<s:param name="bookTypeID" value="%{bookTypeID}" />
								<s:param name="searchBookType" value="%{searchBookType}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Book Type" title="Edit Book Type" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableBookTypeURL" action="DisableBookType">
								<s:param name="bookTypeID" value="%{bookTypeID}" />
								<s:param name="searchBookType" value="%{searchBookType}" />
							</s:url> 
							<s:a href="javascript:disableBookType('%{disableBookTypeURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable Book Type" title="Disable Book Type" />
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
                <form id="validate-basic" action="AddBookType" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'AddBookType');" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="name">Book Type<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="type" placeholder="Book Type" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID" >Add Book Type</button>
                </div>			
                
              </form>
		      
		      <%
		           }else{
	          %>
	          
	          <form id="validate-basic" action="EditBookType" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'EditBookType');" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="BookTypeEditList" var="concessionForm">
						  
				<input type="hidden" name="bookTypeID" value="<s:property value="bookTypeID" />" >
				<input type="hidden" name="searchBookType" value="<s:property value="searchBookType" />" >
               
                <div class="form-group">
                  <label for="name">Book Type<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="type" placeholder="Book Type" value="<s:property value="type" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
              
             	<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID">Update Book Type</button>
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
