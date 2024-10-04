<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.kovidRMS.form.LoginForm"%>
<%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
<%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
<%@page import="java.util.*"%>

<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%
		LoginForm form = (LoginForm) session.getAttribute("USER");
		
		LoginDAOInf daoInf = new LoginDAOImpl();
	
		HashMap<Integer, String> LibraryList = daoInf.retrieveLibraryList(form.getOrganizationID());
	%>
	
	<title><% if(form.getMedium().equals("mr")){ %>युझर नोंदवा  <%}else{ %> Register User - SkoolUp <%} %></title>

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

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
 
<script type="text/javascript">
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
		//alert("hiii");	
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
       
        /* Chrome, Safari, Edge, Opera */
	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
	  -webkit-appearance: none;
	  margin: 0;
	}
	
	/* Firefox */
	input[type=number] {
	  -moz-appearance: textfield;
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
    jQuery(document).ready(function($) {
        $(".scroll").click(function(event){     
            event.preventDefault();
            $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
        });
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
			$("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
			
    	  document.location="welcome.jsp";
      }
      
      function windowOpen1(){
			$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
	
          document.location="addUser.jsp";
        }
      
      function submitForm(subID){
    	  	$('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
	
	    	$("#loadStudentFOrm").attr("action","AddUser");
		 	$("#loadStudentFOrm").submit(); 
		  	  
	 	 	 $("#"+subID).attr("disabled", "disabled");
	 	 	 
	 	 	 $("html, body").animate({ scrollTop: 0 }, "fast");
	    		$(".loadingImage").show();
	    		$(".container").css("opacity","0.1");
	    		$(".navbar").css("opacity","0.1");
	    		return true;
	   }
      
      function libraryShow(role) {
		if(role == "librarian"){
			$("#LibraryDivID").show();
			$("#libraryID").attr("required", true);
			 
		}else{
			$("#libraryID").val("");
			$("#LibraryDivID").hide();
			$("#libraryID").removeAttr("required", false);
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
       
		<h2 class="content-header-title"><% if(form.getMedium().equals("mr")){ %> युझर नोंदवा  <%}else{ %> Register User<%} %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(form.getMedium().equals("mr")){ %> मुख्यपृष्ठ <%}else{ %> Home <%} %></a></li>
          <li class="active"><% if(form.getMedium().equals("mr")){ %> युझर नोंदवा  <%}else{ %> Register User <%} %></li>
        </ol>

      </div> <!-- /.content-header -->

       <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
              	<% if(form.getMedium().equals("mr")){ %> युझर तपशील  <% }else{ %> User Details <% } %>
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
			
              <form id="loadStudentFOrm" action="AddUser" method="POST" data-validate="parsley" enctype="multipart/form-data" onsubmit="submitForm('submitID');" class="form parsley-form">
 			
 			<% if(form.getMedium().equals("mr")){ %>
 			
 				<div class="form-group">
                  <label for="name">नाव<span class="required">*</span></label>
                  <div>
                  <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="पहिले नाव" required="required"  name="firstName" class="form-control" >
                  <input type="text" id="name" style="width: 31%;display: inline-block;" placeholder="मधले नाव" name="middleName" class="form-control" >
                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="आडनाव" required="required" name="lastName" class="form-control" >
                  </div>
                </div>

             	<div class="form-group">
                  <label for="name">ई - मेल आयडी</label>
                  <input type="email" name="emailId" placeholder="ईमेल आयडी" class="form-control" data-required="true"  >
                </div>

                <div class="form-group">
                  <label for="name">मोबाईल</label>
                  <input type="number" name="mobile" class="form-control" placeholder="मोबाईल" data-required="true" onKeyPress="if(this.value.length==10) return false;">
                </div>

                <div class="form-group">
                  <label for="name">फोन</label>
                  <input type="text" name="phone" class="form-control" placeholder="फोन" data-required="true" onKeyPress="if(this.value.length==10) return false;">
                </div>
	
				<div class="form-group">
                  <label for="Username">युझर नाव<span class="required">*</span></label>
                  <input type="text" name="username" required="required" class="form-control" placeholder="युझर नाव" >
                </div>
				
				<div class="form-group">
                  <label for="Password">पासवर्ड <span class="required">*</span></label>
                  <input type="password" name="password" required="required" class="form-control" placeholder="पासवर्ड " >
                </div>
                
				<div class="form-group">
                  <label for="textarea-input">पत्ता</label>
                  <textarea data-required="true" data-minlength="5" name="address" id="textarea-input" cols="10" rows="2" placeholder="पत्ता" class="form-control"></textarea>
                </div>
				
                <div class="form-group">
                  <label for="name">शहर</label>
                  <input type="text" name="city" value="पुणे" placeholder="शहर" class="form-control" >
                </div>
                <div class="form-group">
                  <label for="name">राज्य</label>
                  <input type="text" name="state" value="महाराष्ट्र" placeholder="राज्य" class="form-control" >
                </div>

                <div class="form-group">
                  <label for="name">देश</label>
                  <input type="text" name="country" value="भारत" readonly="readonly" placeholder="देश" class="form-control" >
                </div>

                <div class="form-group">
                  <label for="name">पिन कोड</label>
                  <input type="text" name="pinCode" placeholder="पिन कोड" class="form-control" >
                </div>
                
                <div class="form-group">
                	<label for="name">भूमिका<span class="required">*</span></label>
                    <% if(form.getRole().equals("superAdmin")){ %>
                    	<s:select class="form-control" list="#{'administrator':'प्रशासक','teacher':'शिक्षक','officeAdmin':'कार्यालय प्रशासन', 'librarian':'ग्रंथपाल', 'NonTeaching':'शिक्षकेत्तर ', 'superAdmin':'सुपर अ‍ॅडमीन'}" value="" headerKey="-1" 
                   		 required="required" headerValue="भूमिका निवडा" name="role" onclick="libraryShow(this.value);"></s:select>
                   	<% }else{ %>
                   		<s:select class="form-control" list="#{'administrator':'प्रशासक','teacher':'शिक्षक','officeAdmin':'कार्यालय प्रशासन', 'librarian':'ग्रंथपाल', 'NonTeaching':'शिक्षकेत्तर '}" value="" headerKey="-1" 
                   		 required="required" headerValue="भूमिका निवडा" name="role" onclick="libraryShow(this.value);"></s:select>
                   	<% } %>
                </div>
                
				<div class="form-group" style="display: none;" id="LibraryDivID">
                	<label for="name">ग्रंथालय<span class="required">*</span></label>
                	
                	<select id="libraryID" class="form-control" name="libraryID" >
					     <option value="-1">ग्रंथालय निवडा</option>
					     
					<% for(Integer libraryID : LibraryList.keySet()){ %>
						 
						 <option value="<%=libraryID%>"><%=LibraryList.get(libraryID)%></option>
					<% } %>
				    </select> 
                 </div>
                 
				<div class="form-group">
                   <label for="Profile Pic">प्रोफाइल फोटो अपलोड करा</label>
                   <div class="col-md-6 col-sm-6 col-xs-12">
                       <s:file name="profilePic" class="form-control"></s:file>
                   </div>
                </div>
                
                <div class="form-group">
					<label for="Profile Pic"> स्वाक्षरी अपलोड करा <span class="required">*</span></label>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:file name="signature" class="form-control" required="required"></s:file>
					</div>
				</div>
                      
				 <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="submitID" style="background: #ED6617;">सेव करा</button>
                 </div>
 			
 			<% }else{ %>
 			
               <div class="form-group">
                  <label for="name">Name<span class="required">*</span></label>
                  <div>
                  <input type="text" id="name" style="width: 33%; display: inline-block;" placeholder="FirstName" required="required"  name="firstName" class="form-control" >
                  <input type="text" id="name" style="width: 31%;display: inline-block;" placeholder="MiddleName" name="middleName" class="form-control" >
                  <input type="text" id="name" style="width: 33%;display: inline-block;" placeholder="LastName" required="required" name="lastName" class="form-control" >
                  </div>
                </div>

             	<div class="form-group">
                  <label for="name">Email ID</label>
                  <input type="email" name="emailId" placeholder="Email Address" class="form-control" data-required="true"  >
                </div>

                <div class="form-group">
                  <label for="name">Mobile</label>
                  <input type="number" name="mobile" class="form-control" placeholder="Mobile" data-required="true" onKeyPress="if(this.value.length==10) return false;">
                </div>

                <div class="form-group">
                  <label for="name">Phone</label>
                  <input type="text" name="phone" class="form-control" placeholder="Phone" data-required="true" onKeyPress="if(this.value.length==10) return false;">
                </div>
	
				<div class="form-group">
                  <label for="Username">Username<span class="required">*</span></label>
                  <input type="text" name="username" required="required" class="form-control" placeholder="Username" >
                </div>
				
				<div class="form-group">
                  <label for="Password">Password<span class="required">*</span></label>
                  <input type="password" name="password" required="required" class="form-control" placeholder="Password" >
                </div>
                
				<div class="form-group">
                  <label for="textarea-input">Address</label>
                  <textarea data-required="true" data-minlength="5" name="address" id="textarea-input" cols="10" rows="2" placeholder="Address" class="form-control"></textarea>
                </div>
				
                <div class="form-group">
                  <label for="name">City</label>
                  <input type="text" name="city" value="Pune" placeholder="City" class="form-control" >
                </div>
                <div class="form-group">
                  <label for="name">State</label>
                  <input type="text" name="state" value="Maharashtra" placeholder="State" class="form-control" >
                </div>

                <div class="form-group">
                  <label for="name">Country</label>
                  <input type="text" name="country" value="India" readonly="readonly" placeholder="Country" class="form-control" >
                </div>

                <div class="form-group">
                  <label for="name">PinCode</label>
                  <input type="text" name="pinCode" placeholder="PIN" class="form-control" >
                </div>
                
                <div class="form-group">
                	<label for="name">Role<span class="required">*</span></label>
                    <% if(form.getRole().equals("superAdmin")){ %>
                    	<s:select class="form-control" list="#{'administrator':'Administrator','teacher':'Teacher','officeAdmin':'Office Admin', 'librarian':'Librarian', 'NonTeaching':'Non-Teaching', 'superAdmin':'Super Admin'}" value="" headerKey="" 
                   		 required="required" headerValue="Select Role" name="role" onclick="libraryShow(this.value);"></s:select>
                   	<% }else{ %>
                   		<s:select class="form-control" list="#{'administrator':'Administrator','teacher':'Teacher', 'officeAdmin':'Office Admin', 'librarian':'Librarian', 'NonTeaching':'Non-Teaching'}" value="" headerKey="" 
                   		 required="required" headerValue="Select Role" name="role" onclick="libraryShow(this.value);"></s:select>
                   	<% } %>
                </div>
				
				 <div class="form-group" style="display: none;" id="LibraryDivID">
                	<label for="name">Library<span class="required">*</span></label>
                	
                	<select id="libraryID" class="form-control" name="libraryID" >
					     <option value="-1">Select Library</option>
					     
					<% for(Integer libraryID : LibraryList.keySet()){ %>
						 
						 <option value="<%=libraryID%>"><%=LibraryList.get(libraryID)%></option>
					<% } %>
				    </select> 
                 </div>
                
				<div class="form-group">
                   <label for="Profile Pic">Upload Profile Pic</label>
                   <div class="col-md-6 col-sm-6 col-xs-12">
                       <s:file name="profilePic" class="form-control"></s:file>
                   </div>
                </div>
                
                <div class="form-group">
					<label for="Profile Pic">Upload Signature<span class="required">*</span></label>
					<div class="col-md-6 col-sm-6 col-xs-12">
						<s:file name="signature" class="form-control" required="required"></s:file>
					</div>
				</div>
                      
				 <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID" style="background: #ED6617;">Add User</button>
                 </div>
               
            <% } %>
            
               </form>

            </div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->
          
        </div> <!-- /.col -->

      </div> <!-- /.row -->
       
    </div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  <script src="./js/libs/bootstrap.min.js"></script>

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
