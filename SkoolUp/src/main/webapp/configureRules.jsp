<%@page import="com.kovidRMS.daoImpl.LibraryDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="com.kovidRMS.form.*"%>
     <%@page import="java.util.*"%>
     <%@page import="com.kovidRMS.daoInf.*"%>
     <%@page import="com.kovidRMS.daoImpl.*"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Rules Section - SkoolUp</title>
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

<style type="text/css">
	ul.actionMessage{
            list-style:none;
            font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
        }
        
        ul.errorMessage{
            list-style:none;
            font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
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

	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LibraryDAOInf daoInf = new LibraryDAOImpl();
	
		String Rules = (String) request.getAttribute("Rules");
		if(Rules == null || Rules == ""){
			Rules = "dummy";
		}
	
		List<LibraryForm> PrimaryRulesEditList = (List<LibraryForm>) request.getAttribute("PrimaryRulesEditList");
     	
     	List<LibraryForm> SecondaryRulesEditList = (List<LibraryForm>) request.getAttribute("SecondaryRulesEditList");
		
		HashMap<String, String> GenreList = daoInf.retrieveGenreList();
		
	%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
		//alert("hiii");	
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
    		
          document.location="RenderConfigureRules";
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

</head>

<body>

<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

       <div class="content-header">
        <h2 class="content-header-title">Rules Section</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Rules Section</li>
        </ol>
      </div> <!-- /.content-header -->

       <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                Rules
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
		    
			<%
		         if(Rules.equals("Add")){
		      %> 
		      <form id="loadStudentFOrm" action="ConfigureRules" method="POST" onsubmit="submitForm('loadStudentFOrm', 'submitID', 'ConfigureRules');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
		      
              <div class="form-group" align="center">
                <div style="padding-bottom: 10px;"><b>Staff Rules:</b><input type="hidden" name="ruleForArr" id="ruleForID" class="form-control" value="Staff Rules" data-required="true" ></div>
                <div class="row">
                	<input type="hidden" name="genreArr" value="">
	               	<div class="col-md-6">
	               	 <label for="name">Number of books allowed :</label>
	                </div>
	                <div class="col-md-4" style="width: 25%; ">
	                  <input type="number" id="bookCountID" name="bookCountArr" class="form-control" data-required="true" >
	                </div>
                </div>
				<div class="row">
	               <div class="col-md-6">
                  	<label for="name">Number of days:</label>
                   </div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
                  	<input type="number" id="issueDaysID" name="issueDaysArr" class="form-control" data-required="true" >
                	</div>
                </div>
               <div class="row">
	               <div class="col-md-6">
                  <label for="name">Fine per day:</label>
                   </div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
                  <input type="number" name="finePerDayArr" class="form-control"  data-required="true" >
                  <input type="hidden" id="rulesID">
                </div>
                </div>
			 </div>
			<hr>
			
			<div class="form-group" align="center">
				<div style="padding-bottom: 10px;"><b>Primary Classes:</b></div>
               	
               	<%  
					for(String GenreFormName : GenreList.keySet()){
				%>
				<div class="row">
				  <div class="col-md-6">
	                  <label for="name">Genre Name :</label>
	               </div>
				  <div class="col-md-3">
					<%= GenreFormName%><input type="hidden" name="ruleForArr" id="ruleForID1" class="form-control" value="Primary Classes" data-required="true" ><input type="hidden" name="genreArr" value="<%= GenreFormName%>">
				  </div>
				  <div class="col-md-3">
					
				  </div>
				</div>
				<div class="row">
		           <div class="col-md-6">
	                  <label for="name">Number of books allowed :</label>
	               </div>
		           <div class="col-md-4" style="width: 25%;">
	                 <input type="number" name="bookCountArr" id="bookCountID1" class="form-control" data-required="true" >
	               </div>
				</div>
             	<div class="row">
	               <div class="col-md-6">
                  <label for="name">Number of days:</label>
                  </div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
                  <input type="number" name="issueDaysArr" id="issueDaysID1" class="form-control" data-required="true" >
                </div>
				</div>
               <div class="row">
	               <div class="col-md-6">
                  <label for="name">Fine per day:</label>
                  </div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
                  <input type="number" name="finePerDayArr" class="form-control" data-required="true" >
                  <input type="hidden" id="rulesID1">
                </div>
                </div>
				<hr>		         				
				<%
					}
				%>
               	
               	
			</div>
			<hr>
			<div class="form-group" align="center">
				<div style="padding-bottom: 10px;" ><b>Secondary Classes:</b></div>
               	<%  
					for(String GenreFormName : GenreList.keySet()){
				%>
				<div class="row">
				  <div class="col-md-6">
	                  <label for="name">Genre Name :</label>
	               </div>
				  <div class="col-md-3">
					<%= GenreFormName%><input type="hidden" name="ruleForArr" id="ruleForID2" class="form-control" value="Secondary Classes" data-required="true" ><input type="hidden" name="genreArr" value="<%= GenreFormName%>">
				  </div>
				  <div class="col-md-3">
					
				  </div>
				</div>
               	<div class="row">
	               <div class="col-md-6" >
	               <label for="name">Number of books allowed :</label>
					</div>
	               <div class="col-md-4" style="width: 25%;">
               	 	<input type="number" name="bookCountArr" id="bookCountID2" class="form-control" data-required="true" >
               	 </div>
               	</div> 
               	<div class="row">
	               <div class="col-md-6">
               	 <label for="name">Number of days:</label>
               	 	</div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
               	 	<input type="number" name="issueDaysArr" id="issueDaysID2" class="form-control" data-required="true" >
               	 </div>
               	</div> 
               	<div class="row">
	               <div class="col-md-6"> 
               	<label for="name">Fine per day:</label>
               	</div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
               	 	<input type="number" name="finePerDayArr" class="form-control" data-required="true" >
                   </div>
               	</div>
               <hr>
              <%} %>
				
				</div>
               
                <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID">Add Rules</button>
                </div>
            </form>   
             
            <%}else{ %>
            
           <form id="loadStudentFOrm1" action="ConfigureEditRules" method="POST" onsubmit="submitForm('loadStudentFOrm1', 'submitID', 'ConfigureEditRules');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
         
			<s:iterator value="StaffRulesEditList" var="concessionForm">
			
			 <div class="form-group" align="center">
                <div style="padding-bottom: 10px;"><b>Staff Rules:</b><input type="hidden" name="ruleForEditArr" id="ruleForID" class="form-control" value="<s:property value="ruleFor" />" data-required="true" ></div>
               	<input type="hidden" name="ruleForNewID" value="<s:property value="ruleForID"/>">
                <div class="row">
                <input type="hidden" name="genreArr" value="">
	              <div class="col-md-6">
	               	 <label for="name">Number of books allowed :</label>
	                </div>
	                <div class="col-md-4" style="width: 25%; ">
	                  <input type="number" id="bookCountID" name="bookCountEditArr" value="<s:property value="bookCount" />" class="form-control" data-required="true" >
	                </div>
                </div>
				<div class="row">
	               <div class="col-md-6">
                  	<label for="name">Number of days:</label>
                   </div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
                  	<input type="number" id="issueDaysID" name="issueDaysEditArr" value="<s:property value="issueDays" />" class="form-control" data-required="true" >
                	</div>
                </div>
               <div class="row">
	               <div class="col-md-6">
                  <label for="name">Fine per day:</label>
                   </div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
                  <input type="number" name="finePerDayEditArr" class="form-control" value="<s:property value="finePerDay" />" data-required="true" >
                  <input type="hidden" id="rulesID">
                </div>
                </div>
			 </div>
			 </s:iterator>
			<hr>
			
			<div class="form-group" align="center">
				<div style="padding-bottom: 10px;"><b>Primary Classes:</b></div>
               	
               	<%  for(LibraryForm form:PrimaryRulesEditList){ %>
				
				<div class="row">
				  <div class="col-md-6">
	                  <label for="name">Genre Name :</label>
	               </div>
				  <div class="col-md-3">
					<%=form.getGenre() %><input type="hidden" name="genreEditArr" value="<%=form.getGenre() %>">
					<input type="hidden" name="ruleForNewID" value="<%=form.getRuleForID() %>">
					<input type="hidden" name="ruleForEditArr" id="ruleForID1" value="<%=form.getRuleFor() %>">
				  </div>
				  <div class="col-md-3">
					
				  </div>
				</div>
				
               	<div class="row">
	               <div class="col-md-6">
                  <label for="name">Number of books allowed :</label>
                 </div>
	               <div class="col-md-4" style="width: 25%;">
                  <input type="number" name="bookCountEditArr" id="bookCountID1" value="<%=form.getBookCount() %>" class="form-control" data-required="true" >
                </div>
				</div>
             	<div class="row">
	               <div class="col-md-6">
                  <label for="name">Number of days:</label>
                  </div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
                  <input type="number" name="issueDaysEditArr" id="issueDaysID1" value="<%=form.getIssueDays() %>" class="form-control" data-required="true" >
                </div>
				</div>
               <div class="row">
	               <div class="col-md-6">
                  <label for="name">Fine per day:</label>
                  </div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
                  <input type="number" name="finePerDayEditArr" class="form-control" value="<%=form.getFinePerDay() %>" data-required="true" >
                  <input type="hidden" id="rulesID1">
                </div>
                </div>
              <hr>
              <%} %>
            </div>
		
			<hr>
			
			<div class="form-group" align="center">
				<div style="padding-bottom: 10px;" ><b>Secondary Classes:</b></div>
               	
               <%  for(LibraryForm form:SecondaryRulesEditList){ %>
               
				<div class="row">
				  <div class="col-md-6">
	                  <label for="name">Genre Name :</label>
	               </div>
				  <div class="col-md-3">
					<%=form.getGenre() %><input type="hidden" name="genreEditArr" value="<%=form.getGenre() %>">
					<input type="hidden" name="ruleForNewID" value="<%=form.getRuleForID() %>">
					<input type="hidden" name="ruleForEditArr" id="ruleForID2" value="<%=form.getRuleFor() %>">
				  </div>
				  <div class="col-md-3">
				  </div>
				</div>
				
               	<div class="row">
	               <div class="col-md-6" >
	               <label for="name">Number of books allowed :</label>
					</div>
	               <div class="col-md-4" style="width: 25%;">
               	 	<input type="number" name="bookCountEditArr" id="bookCountID2" value="<%=form.getBookCount() %>" class="form-control" data-required="true" >
               	 </div>
               	</div> 
               	<div class="row">
	               <div class="col-md-6">
               	 <label for="name">Number of days:</label>
               	 	</div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
               	 	<input type="number" name="issueDaysEditArr" id="issueDaysID2" value="<%=form.getIssueDays() %>" class="form-control" data-required="true" >
               	 </div>
               	</div> 
               	<div class="row">
	               <div class="col-md-6"> 
               	<label for="name">Fine per day:</label>
               	</div>
	               <div class="col-md-4" style="width: 25%; padding-top: 10px;">
               	 	<input type="number" name="finePerDayEditArr" class="form-control" value="<%=form.getFinePerDay() %>" data-required="true" >
                   </div>
               	</div>
               	<hr>
              <%} %> 
		   </div>
                
           <div class="form-group" align="center">
			  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
              <button type="submit" class="btn btn-success" id="submitID" >Update Rules</button>
           </div>
         </form>	
          
	<%} %>
			
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
