<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoImpl.*"%>
    <%@page import="com.kovidRMS.daoInf.*"%>
    <%@page import="java.util.*"%>
  <%@page import="com.kovidRMS.form.*"%>

<!DOCTYPE html>
<html lang="en">

<head>
     <title>Voting System - SkoolUp</title>
 
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
	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LoginDAOInf daoinf = new LoginDAOImpl();
	
		VotingDAOInf votedao = new VotingDAOImpl();
	
		String AcademicYearName = daoinf.retrieveAcademicYearName(loginform.getOrganizationID());
	
		int AcademicYearID =  daoinf.retrieveAcademicYearID(loginform.getOrganizationID());
		
		List<VotingForm> HeadBoysList =  votedao.retreiveHeadBoysList(AcademicYearID);
		
		List<VotingForm> HeadGirlsList =  votedao.retreiveHeadGirlsList(AcademicYearID);
		
		List<VotingForm> HouseCaptainRedList =  votedao.retreiveHouseCaptainRedList(AcademicYearID);
		
		List<VotingForm> HouseCaptainBlueList =  votedao.retreiveHouseCaptainBlueList(AcademicYearID);
		
		List<VotingForm> HouseCaptainGreenList =  votedao.retreiveHouseCaptainGreenList(AcademicYearID);
		
		List<VotingForm> HouseCaptainYellowList =  votedao.retreiveHouseCaptainYellowList(AcademicYearID);
		
		String VotingStatus = (String) request.getAttribute("VotingStatus");
		
		if(VotingStatus == null || VotingStatus == ""){
			VotingStatus = "dummy";
		}
		
		/* For House Blue Voting Page */
		String BlueHouseVoting = (String) request.getAttribute("BlueHouseVoting");
		
		if(BlueHouseVoting == null || BlueHouseVoting == ""){
			BlueHouseVoting = "disable";
			
		}else{
	
	%>

		<script type="text/javascript">
	
			document.addEventListener('DOMContentLoaded', function() {
		
				$(".card-body a[href='#house-blue']").click();
				
				$("#house-red").removeClass('active');
				
		});
		</script>
		
	<%} %>

	<%
	/* For House Green Voting Page */
		String GreenHouseVoting = (String) request.getAttribute("GreenHouseVoting");
		
		if(GreenHouseVoting == null || GreenHouseVoting == ""){
			GreenHouseVoting = "disable";
			
		}else{
	
	%>

		<script type="text/javascript">
	
			document.addEventListener('DOMContentLoaded', function() {
		
				$(".card-body a[href='#house-green']").click();
				
				$("#house-red").removeClass('active');
		});
		</script>
		
	<%} %>
	
	<%
	/* For House Yellow Voting Page */
		String YellowHouseVoting = (String) request.getAttribute("YellowHouseVoting");
		
		if(YellowHouseVoting == null || YellowHouseVoting == ""){
			YellowHouseVoting = "disable";
			
		}else{
	
	%>

		<script type="text/javascript">
	
			document.addEventListener('DOMContentLoaded', function() {
		
				$(".card-body a[href='#house-yellow']").click();
				
				$("#house-red").removeClass('active');
		});
		</script>
		
	<%} %>
	
</head>

<script type="text/javascript">
	
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
		//alert("hiii");	
	});

	function checkRadioValue(headBoy, headGirl, houseCaptainRed){
	
		if(headBoy==""){
			alert("Please select Head Boy.");
		
		}else if(headGirl==""){
			alert("Please select Head Girl.");
		
		}else if(houseCaptainRed==""){
			alert("Please select House Captain Red.");
			
		}else{
			return true;
		}
	
	}

	
	function checkRadioValueBlue(headBoy, headGirl, houseCaptainBlue){
		
		if(headBoy==""){
			alert("Please select Head Boy.");
		
		}else if(headGirl==""){
			alert("Please select Head Girl.");
		
		}else if(houseCaptainBlue==""){
			alert("Please select House Captain Blue.");
			
		}else{
			return true;
		}
	
	}
	
	
	function checkRadioValueGreen(headBoy, headGirl, houseCaptainGreen){
		
		if(headBoy==""){
			alert("Please select Head Boy.");
		
		}else if(headGirl==""){
			alert("Please select Head Girl.");
		
		}else if(houseCaptainGreen==""){
			alert("Please select House Captain Green.");
			
		}else{
			return true;
		}
	
	}
	
	
	function checkRadioValueYellow(headBoy, headGirl, houseCaptainYellow){
		
		if(headBoy==""){
			alert("Please select Head Boy.");
		
		}else if(headGirl==""){
			alert("Please select Head Girl.");
		
		}else if(houseCaptainYellow==""){
			alert("Please select House Captain Yellow.");
			
		}else{
			return true;
		}
	
	}
	
</script>


<body class="fix-header fix-sidebar">
    <!-- Preloader - style you can find in spinners.css -->
    <div class="preloader">
        <svg class="circular" viewBox="25 25 50 50">
			<circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" /> </svg>
    </div>
    <!-- Main wrapper  -->
    <div id="main-wrapper">
        <!-- header header  -->
        <div class="header">
            <nav class="navbar top-navbar navbar-expand-md navbar-light">
                <!-- Logo -->
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.html">
                        <!-- Logo icon -->
                        <b><img src="images/logo2.jpg"  width="300" height="75" alt="SevaSadan" class="dark-logo" /></b>
                        <!--End Logo icon -->
                        <!-- Logo text -->
                    </a>
                </div>
               <!-- End Logo -->
                <div class="navbar-collapse">
                   <ul class="navbar-nav mr-auto mt-md-0">
                       
                   </ul>
                   <ul class="navbar-nav my-lg-0">
                       <li class="nav-item dropdown">
                            <button type="button" class="btn btn-danger m-b-10 m-l-5"><a href="Logout">Logout</a></button>
                       </li>
                   </ul>
               </div>
                
            </nav>
        </div>
        <!-- End header header -->
        <!-- Left Sidebar  -->
        <div class="left-sidebar">
            <!-- Sidebar scroll-->
            <div class="scroll-sidebar">
                <!-- Sidebar navigation-->
                <nav class="sidebar-nav">
                    <ul id="sidebarnav" style="padding-top: 10px;">
                        <li class="nav-devider"></li>
                        <li> <a class="" href="welcome.jsp" aria-expanded="false"><i class="fa fa-tachometer"></i><span class="hide-menu">Dashboard</span></a>
                        </li>
                        <li> <a class="" href="RenderAcademicYear" aria-expanded="false"><i class="fa fa-envelope"></i><span class="hide-menu">Set Election</span></a>
                        </li>
                        <li> <a class="" href="RenderVoting" aria-expanded="false"><i class="fa fa-bar-chart"></i><span class="hide-menu">Voting</span></a>
                        </li>
                    </ul>
                </nav>
                <!-- End Sidebar navigation -->
            </div>
            <!-- End Sidebar scroll-->
        </div>
        <!-- End Left Sidebar  -->
        <!-- Page wrapper  -->
 <div class="page-wrapper">
 
  		
	   <div class="row page-titles">
                <div class="col-md-5 align-self-center">
                    <h3 class="text-primary">Voting : <input type="hidden" name ="academicYearID" value="<%=AcademicYearID %>" ><%=AcademicYearName%></h3> </div>
                <div class="col-md-7 align-self-center">
                    
                </div> 
            </div>
            <!-- Bread crumb -->
            <!-- Bread crumb -->
          
            <!-- End Bread crumb -->
            <!-- Container fluid  -->
            <%if(VotingStatus.equals("Voting")){ %>
               <div class="container-fluid">
                  
                 <!-- Start Page Content -->
             
                <div class="row" id="votingDivID">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-body">
                               <!-- Nav tabs -->
                                <ul class="nav nav-tabs" role="tablist">
                                    <li class="nav-item" style="color: #ef5350; font-weight: bold;"> <a class="nav-link active" data-toggle="tab" href="#house-red" role="tab"><span class="hidden-sm-up"><i class="ti-home"></i></span> <span class="hidden-xs-down"><font color="#ef5350" size="4">House Red</font></span></a> </li>
                                    <li class="nav-item" style=" font-weight: bold;"> <a class="nav-link" data-toggle="tab" href="#house-blue" role="tab"><span class="hidden-sm-up"><i class="ti-user"></i></span> <span class="hidden-xs-down"><font color="#1976d2" size="4">House Blue</font></span></a> </li>
                                    <li class="nav-item" style=" font-weight: bold;"> <a class="nav-link" data-toggle="tab" href="#house-green" role="tab"><span class="hidden-sm-up"><i class="ti-email"></i></span> <span class="hidden-xs-down"><font color="#1e7e34" size="4">House Green</font></span></a> </li>
									<li class="nav-item" style=" font-weight: bold;"> <a class="nav-link" data-toggle="tab" href="#house-yellow" role="tab"><span class="hidden-sm-up"><i class="ti-email"></i></span> <span class="hidden-xs-down"><font color=" #ffb22b" size="4">House Yellow</font></span></a> </li>
                                </ul>
                                <!-- Tab panes -->
                                <div class="tab-content tabcontent-border">
                                 
                                    <div class="tab-pane active" id="house-red" role="tabpanel">
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
                                      <form id="loadHouseRedForm" action="ConfigureRedHouseVoting" method="POST" >
                                   	 	<div class="col-md-12">
											<div class="p-20" style="padding: 40px !important">
                                           <h3><u>Vote for Head Boy :</u></h3>
                                            <%
												for(VotingForm form : HeadBoysList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioBoyID" value="<%=form.getHeadBoyID()%>" name="headBoyID" required="required" >&nbsp;<%=form.getName()%></label>
													<br>
											<% } %>
											
											<h3><u>Vote for Head Girl :</u></h3>		
											<%
												for(VotingForm form : HeadGirlsList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioGirlID" name="headGirlID" value="<%=form.getHeadGirlID() %>" required="required" >&nbsp;<%=form.getName()%></label>
													<br>
											
											<% } %>
											
                                            <h3><u>Vote for House Captain :</u></h3>
                                            <%
												for(VotingForm form : HouseCaptainRedList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioRedID" name="houseCaptainRedID" value="<%=form.getHouseCaptainRedID() %>" required="required" >&nbsp;<%=form.getName()%></label>
													<br>
											<% } %>
												
											</div>
											<center><button type="submit" class="btn btn-danger m-b-10 m-l-5" >Submit Votes</button></center>
                                        </div>
                                       </form>
									 </div>
                                  
                                    <div class="tab-pane  p-20" id="house-blue" role="tabpanel">
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
                                    	<form id="loadHouseBlueForm" action="ConfigureBlueHouseVoting" method="POST" >
										 <div class="col-md-12">
											<div class="p-20">
                                            <h3><u>Vote for Head Boy :</u></h3>
											 <%
												for(VotingForm form : HeadBoysList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioBoyID" value="<%=form.getHeadBoyID()%>" name="headBoyID" required="required" >&nbsp;<%=form.getName()%></label>
													<br>
											<% } %>
											<h3><u>Vote for Head Girl :</u></h3>
											<%
												for(VotingForm form : HeadGirlsList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioGirlID" name="headGirlID" value="<%=form.getHeadGirlID() %>" required="required" >&nbsp;<%=form.getName()%></label>
													<br>
											<% } %>
											
                                            <h3><u>Vote for House Captain :</u></h3>
											 <%
												for(VotingForm form : HouseCaptainBlueList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioBlueID" name="houseCaptainBlueID" value="<%=form.getHouseCaptainBlueID() %>" required="required" >&nbsp;<%=form.getName()%></label>
												<br>
											<% } %>
											</div>
											<center><button type="submit" class="btn btn-info m-b-10 m-l-5">Sumbit Votes</button></center>
                                        </div>
                                        </form>
									</div>
				
                                    <div class="tab-pane p-20" id="house-green" role="tabpanel">
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
                                    	<form id="loadHouseGreenForm" action="ConfigureGreenHouseVoting" method="POST" >
										 <div class="col-md-12">
											<div class="p-20">
                                            <h3><u>Vote for Head Boy :</u></h3>
											 <%
												for(VotingForm form : HeadBoysList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioBoyID" value="<%=form.getHeadBoyID()%>" name="headBoyID" required="required" >&nbsp;<%=form.getName()%></label>
													<br>
											<% } %>
												
                                            <h3><u>Vote for Head Girl :</u></h3>
											<%
												for(VotingForm form : HeadGirlsList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id= "radioGirlID" name="headGirlID" value="<%=form.getHeadGirlID() %>" required="required" >&nbsp;<%=form.getName()%></label>
													<br>
											
											<% } %>
                                            <h3><u>Vote for House Captain :</u></h3>
											 <%
												for(VotingForm form : HouseCaptainGreenList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioGreenID" name="houseCaptainGreenID" value="<%=form.getHouseCaptainGreenID() %>" required="required" >&nbsp;<%=form.getName()%></label>
											
												<br>
											<% } %>
											</div>
											<center><button type="submit" style="background-color: #1e7e34; border-color: #1c7430;" class="btn btn-success m-b-10 m-l-5">Submit Votes</button></center>
                                        </div>
                                        </form>
									</div>
								
								<div class="tab-pane p-20" id="house-yellow" role="tabpanel">
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
									<form id="loadHouseYellowForm" action="ConfigureYellowHouseVoting" method="POST" >
										<div class="col-md-12">
											<div class="p-20">
                                          	<h3><u>Vote for Head Boy :</u></h3> 
											<%
												for(VotingForm form : HeadBoysList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioBoyID" value="<%=form.getHeadBoyID()%>" name="headBoyID" required="required" >&nbsp;<%=form.getName()%></label>
												<br>
											<% } %>
													
											<h3><u>Vote for Head Girl :</u></h3>
											<%
												for(VotingForm form : HeadGirlsList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioGirlID" name="headGirlID" value="<%=form.getHeadGirlID() %>" required="required" >&nbsp;<%=form.getName()%></label>
												<br>
											<% } %>
                                            <h3><u>Vote for House Captain :</u></h3>
											 <%
												for(VotingForm form : HouseCaptainYellowList){ %>
												
	                                            	<label style="font-size: 18px;"><input type="radio" id="radioYellowID" name="houseCaptainYellowID" value="<%=form.getHouseCaptainYellowID() %>" required="required" >&nbsp;<%=form.getName()%></label>
												<br>
											<% } %>
											</div>
											<center><button type="submit" class="btn btn-warning m-b-10 m-l-5" >Submit Votes</button></center>
                                        </div>
                                        </form>
									</div>
								</div>
                            </div>
                        </div>
                    </div>
                    
                <!-- End PAge Content -->
            </div>
        </div>
        
     <% }%>   
        
        <!-- End Page wrapper  -->
    </div>
    <!-- End Wrapper -->
   
    <!-- All Jquery -->
 
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