<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html class="no-js">
<head>
  <title>Login - SkoolUp</title>
  
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
  
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">

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
      $(window).scroll(function(){
      if ($(window).scrollTop() > 50) {
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
function login(submitButton) {
    var form = submitButton.form;
    // fill input names by code before submitting
    var inputs = $(form).find('input');
    $(inputs[0]).attr('name', 'username');
    $(inputs[1]).attr('name', 'password');
    form.submit();
}
</script>

</head>

<body class="account-bg">

<div class="row" style="margin-right: 0px">

 <!--  <div class="account-logo">
    <img src="./img/logo-login.png" alt="Target Admin">
  </div> -->

    	<div class="col-md-4 col-sm-4 col-lg-4 col-xs-12"  align="center" style="padding-top: 30px;">
         <div class="account-body" style="width: 85%;">	
			<!-- <h1 style=" text-align: center; padding-bottom: 0px; padding-top: 0px;margin-bottom: 10px;margin-top:0;color: #e74c3c;">Kovid EduTech</h1> -->
			<h3 style=" text-align: center; padding-bottom: 0px; padding-top: 0px;margin-bottom: 5px;margin-top:0;color: #0A2F45;font-size: 26px;">School Management System </h3>
	     	<h4 class="account-body-title" style="color: #0A2F45;">v1.0</h4>
	      	<h4 class="account-body-title" style="color: #ED6617;font-size: 25px;">Login</h4>
			
			<div>
				<s:if test="hasActionErrors()">
					<center>
						<font style="color: red; font-size:16px;"><s:actionerror /></font>
					</center>
				</s:if><s:else>
					<center>
						<font style="color: #0A2F45;font-size:16px;"><s:actionmessage /></font>
					</center>
				</s:else>
			</div>
		
	     <form class="form account-form" method="POST" action="Login">
			<div class="form-group">
	          <label for="login-username" class="placeholder-hidden">Username</label>
	          <input type="text" class="form-control" id="login-username" placeholder="Username*" tabindex="1" required="required">
	
	        </div> <!-- /.form-group -->
	
	        <div class="form-group">
	          <label for="login-password" class="placeholder-hidden">Password</label>
	          <input type="password" class="form-control" id="login-password" placeholder="Password*" tabindex="2" required="required">
	        </div> <!-- /.form-group -->
	
	        <div class="form-group clearfix">
	          <div class="pull-left">         
	            
	          </div>
	
	          <!-- <div class="pull-right">
	            <a href="./account-forgot.html">Forgot Password?</a>
	          </div> -->
	        </div> <!-- /.form-group -->
	
	        <div class="form-group">
	          <button type="submit" class="btn btn-primary btn-block btn-lg" tabindex="4" onclick="login(this)" >
	            Login &nbsp; <i class="fa fa-play-circle"></i>
	          </button>
	        </div> <!-- /.form-group -->
	
	      </form>
	      </div> <!-- /.account-body -->
	     
	      <!-- <div class="account-logo"> -->
    <img alt="banner logo" src="https://sevasadanschool.s3.ap-south-1.amazonaws.com/Logo/KBA-Original.png" class="img-responsive" style="width: 45%;">
  <!-- </div> -->
	      	
	    <footer id="" class="navbar-fixed-bottom hidden-xs hidden-sm" style="margin-left: 5%;margin-bottom: 1%;">
         <div class="pull-left" align="center">
         <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">Kovid BioAnalytics&reg;</a>  | All rights reserved | &copy;2020 
         </div>
       </footer> 
      </div>
      <div class="col-md-8 col-sm-8 col-lg-8 col-xs-12" align="center" style="padding-left: 0px;padding-bottom: 0px;padding-right: 0px;">
      	<img alt="banner logo" src="https://sevasadanschool.s3.ap-south-1.amazonaws.com/Logo/SkoolUpLoginPageNew.jpg" class="img-responsive">
   	  </div> 
    
<!-- 
  </div> /.account-wrapper -->
</div>
<!-- footer content -->
       <!-- <footer id="" class="navbar-fixed-bottom hidden-xs hidden-sm">
         <div class="pull-center" align="center">
           Powered by <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">www.kovidbioanalytics.com</a>
         </div>
         <div class="clearfix"></div>
         <div class="pull-center" align="center">
           &copy;2019 <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">Kovid Bioanalytics&reg;</a>
         </div>
       </footer>
       <footer id="" style="position: relative !important;" class="navbar-fixed-bottom hidden-lg hidden-md">
         <div class="pull-center" align="center">
           Powered by <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">www.kovidbioanalytics.com</a>
         </div>
         <div class="clearfix"></div>
         <div class="pull-center" align="center">
           &copy;2019 <a href="http://www.kovidbioanalytics.com" style="text-decoration: underline;" target="_blank">Kovid Bioanalytics&reg;</a>
         </div>
       </footer> -->
       <!-- /footer content -->
       
<%--   <script src="./js/libs/jquery-1.10.1.min.js"></script> --%>
  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  <script src="./js/libs/bootstrap.min.js"></script>

  <script src="./js/target-admin.js"></script>
 
  <script src="./js/target-account.js"></script>

</body>
</html>
