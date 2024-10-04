<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.kovidRMS.form.LoginForm"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%
     	LoginForm loginform = (LoginForm) session.getAttribute("USER");
     
    	String componentMsg = (String) request.getAttribute("componentMsg");
    	if(componentMsg == null || componentMsg == ""){
    		componentMsg = "dummy";
    	}
    	
    	String componentEdit = (String) request.getAttribute("componentEdit");
    	if(componentEdit == null || componentEdit == ""){
    		componentEdit = "add";
    	}
    %>

  <title><% if(loginform.getMedium().equals("mr")){ %>प्रवास  व्यवस्थापन - SkoolUp <% }else{ %>Manage Commutation - SkoolUp<% } %></title>
  
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
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
  

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
  </script>
 
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
	function windowOpen2(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="ViewAllCommutations";
     }
	
	function windowOpen1(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="manageCommutation.jsp";
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
	
    	function disableCommutation(url){
			if(confirm("Disabled Commutation cannot access application. Are you sure you want to disable this Commutation?")){
				document.location = url;
			}
    	}
    
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete Commutation?")) {
				document.location = url;
			}
		}
		
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
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>प्रवास  व्यवस्थापन  <% }else{ %>Manage Commutation<% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ - SkoolUp <% }else{ %>Home<% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>प्रवास  व्यवस्थापन  <% }else{ %>Manage Commutation<% } %></li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                <% if(loginform.getMedium().equals("mr")){ %>प्रवास  यादी  <% }else{ %>Commutation List<% } %>
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
		         
		         	 <div class="row">    
	            <div class="col-md-12">
	            	<form id="validate-basic" action="SearchCommutations" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'SearchCommutations');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" name="searchCommutation" class="form-control" style="width:100%;" placeholder="ड्राइव्हर नाव / मोबाइल नं. / वेहिकल नं." required="required">
	                       </div>
	                        <div class="col-md-2 col-sm-3 col-xs-6">
                          		<s:select list="#{'DriverName':'ड्राइव्हर नाव','MobileNo':'मोबाइल नं.','VehicleNo':'वेहिकल नं.'}" class="form-control" name="searchCriteria"></s:select>
                        	</div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">प्रवास शोधा</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllCommutations"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('validate-basic', 'viewSubmitID', 'ViewAllCommutations');">सर्व प्रवास पहा</button></a>
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
                   	  <th data-sortable="true">ड्राइव्हर नाव</th>
                   	   <th data-sortable="true">वेहिकल नंबर</th>
                   	   <th data-sortable="true">मोबाइल नंबर</th>
                      <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchCommutationList" var="UserForm">
                    <tr>
                     	<td><s:property value="nameOfDriver" /></td>
                     	<td><s:property value="vehicleRegNumber" /></td>
                     	<td><s:property value="driverMobile" /></td>
                    
						<td align="center">
							<s:url id="approveURL" action="RenderEditCommutation">
								<s:param name="commutationID" value="%{commutationID}" />
								<s:param name="searchCommutation" value="%{searchCommutation}" />
								<s:param name="searchCriteria" value="%{searchCriteria}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="एडिट करा" title="एडिट करा" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableCommutationURL" action="DisableCommutation">
								<s:param name="commutationID" value="%{commutationID}" />
								<s:param name="searchCommutation" value="%{searchCommutation}" />
								<s:param name="searchCriteria" value="%{searchCriteria}" />
							</s:url> 
							<s:a href="javascript:disableCommutation('%{disableCommutationURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="डीलीट करा" title="डीलीट करा" />
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
                <form id="validate-basic" action="AddCommutation" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'AddCommutation');" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="nameOfDriver">ड्राइव्हर नाव<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="nameOfDriver" placeholder="ड्राइव्हर नाव" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
                <div class="form-group">
                  <label for="name">वेहिकल नंबर<span class="required">*</span></label>
                  <input type="text" name="vehicleRegNumber" class="form-control" required="required" placeholder="वेहिकल नंबर" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="Username">मोबाइल नंबर<span class="required">*</span></label>
                  <input type="text" name="driverMobile" class="form-control" required="required" placeholder="मोबाइल नंबर" data-required="true" >
                </div>
				
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="submit">सेव करा</button>
                </div>
                
              </form>
		      
		      <%
		              	}else{
	          %>
	          
	          <form id="validate-basic" action="EditCommutation" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'EditCommutation');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="CommutationEditList" var="concessionForm">
						  
				<input type="hidden" name="commutationID" value="<s:property value="commutationID" />" >
				<input type="hidden" name="searchCommutation" value="<s:property value="searchCommutation" />" >
				<input type="hidden" name="searchCriteria" value="<s:property value="searchCriteria" />" >
               
               <div class="form-group">
                  <label for="nameOfDriver">ड्राइव्हर नाव<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="nameOfDriver" placeholder="ड्राइव्हर नाव" value="<s:property value="nameOfDriver" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
                <div class="form-group">
                  <label for="name">वेहिकल नंबर<span class="required">*</span></label>
                  <input type="text" name="vehicleRegNumber" required="required" value="<s:property value="vehicleRegNumber" />" class="form-control" placeholder="वेहिकल नंबर" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="Username">मोबाइल नंबर<span class="required">*</span></label>
                  <input type="text" name="driverMobile" required="required" value="<s:property value="driverMobile" />" class="form-control" placeholder="मोबाइल नंबर" data-required="true" >
                </div>
				
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="submit">अपडेट करा</button>
                </div>
                </s:iterator> 
              </form>
              
              <%
		           }
	          %>
	           
             </div>
		 <% }else{ %>
		         
		       <div class="row">    
	            <div class="col-md-12">
	            	<form action="SearchCommutations" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'SearchCommutations');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" name="searchCommutation" class="form-control" style="width:100%;" placeholder="Enter DriverName / Mobile no. /Vehicle no." required="required">
	                       </div>
	                        <div class="col-md-2 col-sm-3 col-xs-6">
                          		<s:select list="#{'DriverName':'Driver Name','MobileNo':'Mobile No.','VehicleNo':'Vehicle No.'}" class="form-control" name="searchCriteria"></s:select>
                        	</div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">Search Commutation</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllCommutations"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('validate-basic', 'viewSubmitID', 'ViewAllCommutations');" >View All Commutations</button></a>
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
                   	  <th data-sortable="true">Driver Name</th>
                   	   <th data-sortable="true">Vehicle Number</th>
                   	   <th data-sortable="true">Mobile Number</th>
                      <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchCommutationList" var="UserForm">
                    <tr>
                     	<td><s:property value="nameOfDriver" /></td>
                     	<td><s:property value="vehicleRegNumber" /></td>
                     	<td><s:property value="driverMobile" /></td>
                    
						<td align="center">
							<s:url id="approveURL" action="RenderEditCommutation">
								<s:param name="commutationID" value="%{commutationID}" />
								<s:param name="searchCommutation" value="%{searchCommutation}" />
								<s:param name="searchCriteria" value="%{searchCriteria}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Organization" title="Edit Commutation" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableCommutationURL" action="DisableCommutation">
								<s:param name="commutationID" value="%{commutationID}" />
								<s:param name="searchCommutation" value="%{searchCommutation}" />
								<s:param name="searchCriteria" value="%{searchCriteria}" />
							</s:url> 
							<s:a href="javascript:disableCommutation('%{disableCommutationURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable Commutation" title="Disable Commutation" />
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
                <form id="validate-basic" action="AddCommutation" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'AddCommutation');" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="nameOfDriver">Driver Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="nameOfDriver" placeholder="Driver Name" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
                <div class="form-group">
                  <label for="name">Vehicle Number<span class="required">*</span></label>
                  <input type="text" name="vehicleRegNumber" class="form-control" required="required" placeholder="Vehicle Number" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="Username">Mobile Number<span class="required">*</span></label>
                  <input type="text" name="driverMobile" class="form-control" required="required" placeholder="Mobile Number" data-required="true" >
                </div>
				
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID">Add Commutation</button>
                </div>
                
              </form>
		      
		      <%
		              	}else{
	          %>
	          
	          <form id="validate-basic" action="EditCommutation" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'EditCommutation');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="CommutationEditList" var="concessionForm">
						  
				<input type="hidden" name="commutationID" value="<s:property value="commutationID" />" >
				<input type="hidden" name="searchCommutation" value="<s:property value="searchCommutation" />" >
				<input type="hidden" name="searchCriteria" value="<s:property value="searchCriteria" />" >
               
               <div class="form-group">
                  <label for="nameOfDriver">Driver Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="nameOfDriver" placeholder="Driver Name" value="<s:property value="nameOfDriver" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
                <div class="form-group">
                  <label for="name">Vehicle Number<span class="required">*</span></label>
                  <input type="text" name="vehicleRegNumber" required="required" value="<s:property value="vehicleRegNumber" />" class="form-control" placeholder="Vehicle Number" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="Username">Mobile Number<span class="required">*</span></label>
                  <input type="text" name="driverMobile" required="required" value="<s:property value="driverMobile" />" class="form-control" placeholder="Mobile Number" data-required="true" >
                </div>
				
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID">Update Commutation</button>
                </div>
                </s:iterator> 
              </form>
              
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

<%-- <script src="./js/libs/jquery-1.10.1.min.js"></script> --%>
  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  <script src="./js/libs/bootstrap.min.js"></script>

  <!--[if lt IE 9]>
  <script src="./js/libs/excanvas.compiled.js"></script>
  <![endif]-->
  
  <!-- Plugin JS -->
  <script src="./js/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="./js/plugins/datatables/DT_bootstrap.js"></script>
  <script src="./js/plugins/tableCheckable/jquery.tableCheckable.js"></script>
  <script src="./js/plugins/icheck/jquery.icheck.min.js"></script>

  <!-- App JS -->
  <script src="./js/target-admin.js"></script>
  


  
</body>
</html>
