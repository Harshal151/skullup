<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.kovidRMS.form.LoginForm"%>

<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%	
		LoginForm form = (LoginForm) session.getAttribute("USER");
	%>
	
<title> <% if(form.getMedium().equals("mr")){ %> युझर प्रोफाइल - SkoolUp <% }else{ %> User Profile - SkoolUp <% } %></title>

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
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
		//alert("hiii");	
	});
	
</script>

<style>

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
    jQuery(document).ready(function($) {
        $(".scroll").click(function(event){     
            event.preventDefault();
            $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
        });
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
	
	function submitForm(subID){
	  	  $("#loadStudentFOrm").attr("action","EditUserProfile");
		  	  $("#loadStudentFOrm").submit(); 
		  	  
	 	 	 $("#"+subID).attr("disabled", "disabled");
	   }
	
	function profilePicShow(){
		$('#profilePicID1').hide();
		$('#profilePicID').show();
		$('#profilePicClickID').click();
	}
	
	function signatureShow(){
		$('#signatureID1').hide();
		$('#signatureID').show();
		$('#signatureClickID').click();
		$("#signatureClickID").attr("required", true);
	}
	
</script>	

<script type="text/javascript">
	$(window).load( function() {
			
		$(".libraryClass").prop('disabled', true);
	});
</script>
	
</head>

<body >

<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="google_translate_element" style="display:none"></div>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

	<div class="content-header">
        <h2 class="content-header-title"><% if(form.getMedium().equals("mr")){ %> युझर प्रोफाइल <% }else{ %> User Profile<% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(form.getMedium().equals("mr")){ %> मुख्यपृष्ठ <% }else{ %> Home<% } %></a></li>
          <li class="active"><% if(form.getMedium().equals("mr")){ %> युझर प्रोफाइल <% }else{ %> User Profile<% } %></li>
        </ol>
      </div> <!-- /.content-header -->

    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
               <% if(form.getMedium().equals("mr")){ %> प्रोफाइल एडिट करा <% }else{ %>  Edit Profile Details <% } %>
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
			
			<s:iterator value="signedUpUserList" var="UserForm">
			
              <form id="loadStudentFOrm" action="EditUserProfile" method="POST" onsubmit="submitForm('loadStudentFOrm', 'submitID', 'EditUserProfile');" enctype="multipart/form-data" data-validate="parsley" class="form parsley-form">
				
				<input type="hidden" name="userID" value="<s:property value="userID"/>">
				
				
			<% if(form.getMedium().equals("mr")){%>
			
				<div class="form-group">
                   <label for="name">नाव<span class="required">*</span></label>
                  <div>
                  <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="पहिले नाव" required="required"  name="firstName" value="<s:property value="firstName"/>" class="form-control" >
                  <input type="text" id="name" style="width: 31%;display: inline-block;" placeholder="मधले नाव" name="middleName" value="<s:property value="middleName"/>" class="form-control" >
                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="आडनाव" required="required" name="lastName" value="<s:property value="lastName"/>" class="form-control" >
                  </div>
                </div>

             	<div class="form-group">
                  <label for="name">ई - मेल आयडी</label>
                  <input type="email" name="emailId" value="<s:property value="emailId"/>" placeholder="ई - मेल आयडी" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">मोबाईल</label>
                  <input type="number" name="mobile" value="<s:property value="mobile"/>" class="form-control" placeholder="मोबाईल" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">फोन</label>
                  <input type="text" name="phone" value="<s:property value="phone"/>" class="form-control" placeholder="फोन" data-required="true" >
                </div>
	
				<div class="form-group">
                  <label for="Username">युझर नाव<span class="required">*</span></label>
                  <input type="text" name="username" readonly="readonly" value="<s:property value="username"/>" required="required" class="form-control" placeholder="युझर नाव" data-required="true" >
                </div>
				
				<div class="form-group">
                  <label for="Password">पासवर्ड<span class="required">*</span></label>
                  <input type="password" name="password" value="<s:property value="password"/>" required="required" class="form-control" placeholder="पासवर्ड" data-required="true" >
                </div>
                
				<div class="form-group">
                  <label for="textarea-input">पत्ता</label>
                  <textarea data-required="true" data-minlength="5" name="address"  id="textarea-input" cols="10" rows="2" placeholder="पत्ता" class="form-control"><s:property value="address"/></textarea>
                </div>
				
                <div class="form-group">
                  <label for="name">शहर</label>
                  <input type="text" name="city" value="<s:property value="city"/>" placeholder="शहर" class="form-control" data-required="true" onfocus="this.value=''" onblur="this.value='Pune'" >
                </div>
                <div class="form-group">
                  <label for="name">राज्य</label>
                  <input type="text" name="state" value="<s:property value="state"/>" placeholder="राज्य" class="form-control" data-required="true" onfocus="this.value=''" onblur="this.value='Maharashtra'" >
                </div>

                <div class="form-group">
                  <label for="name">देश</label>
                  <input type="text" name="country" value="<s:property value="country"/>" readonly="readonly" placeholder="देश" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">पिन कोड</label>
                  <input type="text" name="pinCode" value="<s:property value="pinCode"/>" placeholder="पिन कोड" class="form-control" data-required="true" >
                </div>
                
                <div class="form-group">
                	<label for="name">भूमिका<span class="required">*</span></label>
                	<input type="text" readonly="readonly" class="form-control" name="role" value="<s:property value="role"/>"> 
                </div>
				 
				<% if(form.getRole().equals("librarian")){ %>
                    <div class="form-group" id="LibraryDivID">
	                	<label for="name">ग्रंथालय<span class="required">*</span></label>
	                	<s:select class="libraryClass form-control" list="LibraryList" headerKey="" headerValue="ग्रंथालय निवडा" name="libraryID" id="libraryID"></s:select>
	                </div>
                <% }%>
				 <div class="form-group"  >
                        
                        <div id="profilePicID1">
                         <div>
		                	<label for="Profile Pic">प्रोफाइल फोटो अपलोड करा</label>
		                </div>
                         
                          	<input  type="text" name="profilePicDBName" style="width: 50%;display: inline-block;" class="form-control" value="<s:property value="profilePicDBName"/>" readonly="readonly">
                         
                          	<button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" onclick="profilePicShow();">प्रोफाइल फोटो अपलोड करा</button>
                          
                        </div>
             
                     <div class="form-group" id="profilePicID" style="display: none;">
                        <label for="Profile Pic">प्रोफाइल फोटो अपलोड करा</label>
                          <s:file name="profilePic" class="form-control" id="profilePicClickID"></s:file>
                     </div>
				</div>
				
				<div class="form-group"  >
                   	<div id="signatureID1">
                         <div>
		                	<label for="Profile Pic">स्वाक्षरी अपलोड करा <span class="required">*</span></label>
		                 </div>
                         <input type="text" name="signatureDBName" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="signatureDBName"/>" readonly="readonly">
                         <button class="btn btn-default" style="display: inline-block;" type="button" onclick="signatureShow();">स्वाक्षरी अपलोड करा </button>
                    </div>
             		<div class="form-group" id="signatureID" style="display: none;">
                        <label for="Profile Pic">स्वाक्षरी अपलोड करा <span class="required">*</span></label>
                        <s:file name="signature" class="form-control" id="signatureClickID"></s:file>
                    </div>
				</div>
				
				 <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen();" class="btn btn-default">रद्द कर</button>
                  <button type="submit" class="btn btn-success" id="submitID" onclick="submitForm('submitID');">अपडेट करा</button>
                  
                </div>
                
			<% }else{ %>
				
                <div class="form-group">
                  <label for="name">Name<span class="required">*</span></label>
                  <div>
                  <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="FirstName" required="required"  name="firstName" value="<s:property value="firstName"/>" class="form-control" data-required="true" >
                  <input type="text" id="name" style="width: 31%;display: inline-block;" placeholder="MiddleName" name="middleName" value="<s:property value="middleName"/>" class="form-control" data-required="true" >
                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="LastName" required="required" name="lastName" value="<s:property value="lastName"/>" class="form-control" data-required="true" >
                  </div>
                </div>

             	<div class="form-group">
                  <label for="name">Email ID</label>
                  <input type="email" name="emailId" value="<s:property value="emailId"/>" placeholder="Email Address" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">Mobile</label>
                  <input type="number" name="mobile" value="<s:property value="mobile"/>" class="form-control" placeholder="Mobile" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">Phone</label>
                  <input type="text" name="phone" value="<s:property value="phone"/>" class="form-control" placeholder="Phone" data-required="true" >
                </div>
	
				<div class="form-group">
                  <label for="Username">Username<span class="required">*</span></label>
                  <input type="text" name="username" readonly="readonly" value="<s:property value="username"/>" required="required" class="form-control" placeholder="Username" data-required="true" >
                </div>
				
				<div class="form-group">
                  <label for="Password">Password<span class="required">*</span></label>
                  <input type="password" name="password" value="<s:property value="password"/>" required="required" class="form-control" placeholder="Username" data-required="true" >
                </div>
                
				<div class="form-group">
                  <label for="textarea-input">Address</label>
                  <textarea data-required="true" data-minlength="5" name="address"  id="textarea-input" cols="10" rows="2" placeholder="Address" class="form-control"><s:property value="address"/></textarea>
                </div>
				
                <div class="form-group">
                  <label for="name">City</label>
                  <input type="text" name="city" value="<s:property value="city"/>" placeholder="City" class="form-control" data-required="true" onfocus="this.value=''" onblur="this.value='Pune'" >
                </div>
                <div class="form-group">
                  <label for="name">State</label>
                  <input type="text" name="state" value="<s:property value="state"/>" placeholder="State" class="form-control" data-required="true" onfocus="this.value=''" onblur="this.value='Maharashtra'" >
                </div>

                <div class="form-group">
                  <label for="name">Country</label>
                  <input type="text" name="country" value="<s:property value="country"/>" readonly="readonly" placeholder="Country" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="name">PinCode</label>
                  <input type="text" name="pinCode" value="<s:property value="pinCode"/>" placeholder="PIN" class="form-control" data-required="true" >
                </div>
                
                <div class="form-group">
                	<label for="name">Role<span class="required">*</span></label>
                	<input type="text" readonly="readonly" class="form-control" name="role" value="<s:property value="role"/>"> 
                </div>
				 
				<% if(form.getRole().equals("librarian")){ %>
                    <div class="form-group" id="LibraryDivID">
	                	<label for="name">Library<span class="required">*</span></label>
	                	<s:select class="libraryClass form-control" list="LibraryList" headerKey="" headerValue="Select Library" name="libraryID" id="libraryID"></s:select>
	                </div>
                <% }%>
				 <div class="form-group"  >
                        
                        <div id="profilePicID1">
                         <div>
		                	<label for="Profile Pic">Upload Profile Pic</label>
		                </div>
                         
                          	<input  type="text" name="profilePicDBName" style="width: 50%;display: inline-block;" class="form-control" value="<s:property value="profilePicDBName"/>" readonly="readonly">
                         
                          	<button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" onclick="profilePicShow();">Upload Profile Pic</button>
                          
                        </div>
             
                     <div class="form-group" id="profilePicID" style="display: none;">
                        <label for="Profile Pic">Upload Profile Pic</label>
                          <s:file name="profilePic" class="form-control" id="profilePicClickID"></s:file>
                     </div>
				</div>
				
				<div class="form-group"  >
                   	<div id="signatureID1">
                         <div>
		                	<label for="Profile Pic">Upload Signature<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="signatureDBName" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="signatureDBName"/>" readonly="readonly">
                         <button class="btn btn-default" style="display: inline-block;" type="button" onclick="signatureShow();">Upload Signature</button>
                    </div>
             		<div class="form-group" id="signatureID" style="display: none;">
                        <label for="Profile Pic">Upload Signature<span class="required">*</span></label>
                        <s:file name="signature" class="form-control" id="signatureClickID"></s:file>
                    </div>
				</div>
				
				 <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID" onclick="submitForm('submitID');">Update User</button>
                 </div>
             <% } %>   
               </form>
               
               </s:iterator>

            </div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->
          
        </div> <!-- /.col -->

      </div> <!-- /.row -->

    </div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

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

  
</body>
</html>
