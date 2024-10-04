<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.kovidRMS.daoImpl.*"%>
    <%@page import="com.kovidRMS.daoInf.*"%>
    <%@page import="java.util.List"%>
  <%@page import="com.kovidRMS.form.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Favicon icon -->
    <link rel="icon" type="image/png" sizes="16x16" href="images/logo.png">
    <title>School Voting System</title>
    <!-- Bootstrap Core CSS -->
    <link href="css/lib/bootstrap/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->

    <link href="css/lib/calendar2/semantic.ui.min.css" rel="stylesheet">
    <link href="css/lib/calendar2/pignose.calendar.min.css" rel="stylesheet">
    <link href="css/lib/owl.carousel.min.css" rel="stylesheet" />
    <link href="css/lib/owl.theme.default.min.css" rel="stylesheet" />
    <link href="css/helper.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

	<%
	LoginForm loginform = (LoginForm) session.getAttribute("USER");
	
	LoginDAOInf daoinf = new LoginDAOImpl();

	VotingDAOInf votedao = new VotingDAOImpl();
	
		String AcademicYearName = daoinf.retrieveAcademicYearName(loginform.getOrganizationID());
		
		int AcademicYearID =  daoinf.retrieveAcademicYearID(loginform.getOrganizationID());
		
		List<VotingForm>HeadGirlList = votedao.retreiveHeadGirlsList(AcademicYearID);
		
		List<VotingForm>HeadBoyList = votedao.retreiveHeadBoysList(AcademicYearID);
		
		List<VotingForm>RedList = votedao.retreiveHouseCaptainRedList(AcademicYearID);
		
		List<VotingForm>BlueList = votedao.retreiveHouseCaptainBlueList(AcademicYearID);
		
		List<VotingForm>GreenList = votedao.retreiveHouseCaptainGreenList(AcademicYearID);
		
		List<VotingForm>YellowList = votedao.retreiveHouseCaptainYellowList(AcademicYearID);
		
	%>
</head>
	
<script type="text/javascript">

function Results(Value){     
	     
	if(Value=="HeadGirl"){
		
		$("#HeadGirlID").toggle();
		
	}else if(Value=="HeadBoy"){
		
		$("#HeadBoyID").toggle();
		
	}else if(Value=="RedCaptain"){
		
		$("#RedCaptainID").toggle();
		
	}else if(Value=="BlueCaptain"){
		
		$("#BlueCaptainID").toggle();
		
	}else if(Value=="GreenCaptain"){
		
		$("#GreenCaptainID").toggle();
		
	}else if(Value=="YellowCaptain"){
		
		$("#YellowCaptainID").toggle();
		
	}
}
</script>
	
<body>

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

    <!-- Preloader - style you can find in spinners.css -->
    <div class="preloader">
        <svg class="circular" viewBox="25 25 50 50">
			<circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" /> </svg>
    </div>
    <!-- Main wrapper  -->
    <div id="main-wrapper">
       
        <!-- Page wrapper  -->
        
 <div class="page-wrapper">
	   <div class="row page-titles">
                <div class="col-md-5 align-self-center">
                    <h3 class="text-primary">Voting View</h3> </div>
                <div class="col-md-7 align-self-center">
                    
                </div>
            </div>
            <!-- Bread crumb -->
            <!-- Bread crumb -->
          
            <!-- End Bread crumb -->
            <!-- Container fluid  -->
            <%if(AcademicYearName!=""){%>
            <div class="container-fluid">
                <!-- Start Page Content -->
              <form id="loadAcademicFormNew" action=GenerateReport method="POST" >		
				 <div class="col-md-5 align-self-center">
                    <h3 class="text-primary">Academic year : <input type="hidden" name ="academicYearID" value="<%=AcademicYearID %>" ><%=AcademicYearName %></h3> 
                 </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <span><img src="images/HeadGirlIcon.png"  alt="SevaSadan" class="dark-logo" /></span>
                                </div>
                                <div class="media-body media-text-right">
                                <% String HeadGirlName = votedao.retrieveHeadGirlName(AcademicYearID);
                                // System.out.println("HeadGirlName:"+HeadGirlName);
                                 if(HeadGirlName.contains(",0")){ 
                                	 System.out.println("HeadGirl:"+HeadGirlName);
                                	 HeadGirlName = ""; %>
                                
                                <h2 align="left"><a href="javascript:Results('HeadGirl');">&nbsp;Head Girl:<%=HeadGirlName %></a></h2>
                                	 
                                 <%}else{
                                	 String HeadGirl[] = HeadGirlName.split(",");%>
                               
                               		<h2 align="left"><a href="javascript:Results('HeadGirl');">&nbsp;Head Girl:&nbsp;<%=HeadGirl[0] %>&nbsp;(<%=HeadGirl[1] %>)</a></h2>
                                
                                 <% } %> 
                                 <p class="m-b-0"> </p>
                                </div>
                            </div>
                            
                            <div class="media" id="HeadGirlID" style="display: none;">
                               <table class="table table-striped">
                                <tr style="text-align: center; font-size: 16px;"><td><b>Head Girl Election Results</b></td><td></td></tr>
                                 <% for(VotingForm form:HeadGirlList){ %>
                                 	
                                 <tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
                                 <%} %>
                                    
                                   <p class="m-b-0"> </p>
                                 </table>
                              </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <span><img src="images/HeadBoyIcon.png"  alt="SevaSadan" class="dark-logo" /></i></span>
                                </div>
                                <div class="media-body media-text-right">
                                <%String HeadBoyName = votedao.retrieveHeadBoyName(AcademicYearID);
                                
                                if(HeadBoyName.contains(",0")){ 
                                	HeadBoyName = ""; %>
                               
                               <h2 align="left"><a href="javascript:Results('HeadBoy');">&nbsp;Head Boy:<%=HeadBoyName %></a></h2>
                               	 
                                <%}else{
                               	 String HeadBoy[] = HeadBoyName.split(",");%>
                              
                              	<h2 align="left"><a href="javascript:Results('HeadBoy');">&nbsp;Head Boy:&nbsp;<%=HeadBoy[0] %>&nbsp;(<%= HeadBoy[1]%>)</a></h2>
                               
                                <% } %> 
                                
                                    <p class="m-b-0"> </p>
                                </div>
                            </div>
                            
                            <div class="media" id="HeadBoyID" style="display: none;">
                                 <table class="table table-striped">
                                 	<tr style="text-align: center; font-size: 16px;"><td><b>Head Boy Election Results</b></td><td></td></tr>
                                 	
                                 	<% for(VotingForm form:HeadBoyList){ %>
                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
                                    <%} %>
                                    
                                    <p class="m-b-0"> </p>
                                  </table>
                              </div>
                        </div>
                    </div>
                                     
                </div>
				<div class="row">
				 <div class="col-md-6">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <span><img src="images/HouseCaptainRed.png"  alt="SevaSadan" class="dark-logo" /> </span>
                                </div>
                                <div class="media-body media-text-right">
                                
                                <%String RedCaptainName = votedao.retrieveRedCaptainName(AcademicYearID);
                               
                                if(RedCaptainName.contains(",0")){ 
                                	RedCaptainName = ""; %>
                               
                               		<h2 align="left"><a href="javascript:Results('RedCaptain');">&nbsp;Red Captain:<%=RedCaptainName %></a></h2>
                               	 
                                <%}else{
                                	String RedCaptain[] = RedCaptainName.split(",");%>
                                	
                              		<h2 align="left"><a href="javascript:Results('RedCaptain');">&nbsp;Red Captain:&nbsp;<%=RedCaptain[0] %>&nbsp;(<%=RedCaptain[1] %>)</a></h2>
                              	
                              	<%  }%>
                                    
                                    <p class="m-b-0"> </p>
                                </div>
                            </div>
                            
                            <div class="media" id="RedCaptainID" style="display: none;">
                                 <table class="table table-striped">
                                 	<tr style="text-align: center; font-size: 16px;"><td><b>Red Captain Election Results</b></td><td></td></tr>
                                 	
                                 	<% for(VotingForm form:RedList){ %>
                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
                                    <%} %>
                                    
                                    <p class="m-b-0"> </p>
                                  </table>
                            </div>
                        </div>
                    </div>
					 <div class="col-md-6">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <span><img src="images/HouseCaptainBlue.png"  alt="SevaSadan" class="dark-logo" /> </span>
                                </div>
                                <div class="media-body media-text-right">
                                 <%String BlueCaptainName = votedao.retrieveBlueCaptainName(AcademicYearID);
                                 
                                 if(BlueCaptainName.contains(",0")){ 
                                	 BlueCaptainName = ""; %>
                                
                                	<h2 align="left"><a href="javascript:Results('BlueCaptain');">&nbsp;Blue Captain:<%=BlueCaptainName %></a></h2>
                                	 
                                 <%}else{
                                 String BlueCaptain[] = BlueCaptainName.split(",");%>
                                
                                	<h2 align="left"><a href="javascript:Results('BlueCaptain');">&nbsp;Blue Captain:&nbsp;<%=BlueCaptain[0] %>&nbsp;(<%=BlueCaptain[1] %>)</a></h2>
                                	 
                                <%} %>
                                 	
                              	<p class="m-b-0"></p>
                                </div>
                            </div>
                            
                             <div class="media" id="BlueCaptainID" style="display: none;">
                                 <table class="table table-striped">
                                 	<tr style="text-align: center; font-size: 16px;"><td><b>Blue Captain Election Results</b></td><td></td></tr>
                                 	
                                 	<% for(VotingForm form:BlueList){ %>
                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
                                    <%} %>
                                    
                                    <p class="m-b-0"> </p>
                                  </table>
                              </div>
                        </div>
                    </div>
					
				</div>
				<div class="row">
					 <div class="col-md-6">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <span><img src="images/HouseCaptainGreen.png"  alt="SevaSadan" class="dark-logo" /> </span>
                                </div>
                                <div class="media-body media-text-right">
                                <%String GreenCaptainName = votedao.retrieveGreenCaptainName(AcademicYearID);
                                
                                if(GreenCaptainName.contains(",0")){ 
                                	GreenCaptainName = ""; %>
                               
                               	<h2 align="left"><a href="javascript:Results('GreenCaptain');">&nbsp;Green Captain:<%=GreenCaptainName %></a></h2>
                               	 
                                <%}else{
                                	String GreenCaptain[] = GreenCaptainName.split(",");%>
                                	
                                	<h2 align="left"><a href="javascript:Results('GreenCaptain');">&nbsp;Green Captain:&nbsp;<%=GreenCaptain[0] %>&nbsp;(<%=GreenCaptain[1] %>)</a></h2>
                               <% } %>
                                    
                                    <p class="m-b-0"></p>
                                </div>
                            </div>
                            
                            <div class="media" id="GreenCaptainID" style="display: none;">
                                 <table class="table table-striped">
                                 	<tr style="text-align: center; font-size: 16px;"><td><b>Green Captain Election Results</b></td><td></td></tr>
                                 	
                                 	<% for(VotingForm form:GreenList){ %>
                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
                                    <%} %>
                                    
                                    <p class="m-b-0"> </p>
                                  </table>
                              </div>
                        </div>
						
                    </div>
					 <div class="col-md-6">
                        <div class="card p-30">
                            <div class="media">
                                <div class="media-left meida media-middle">
                                    <span><img src="images/HouseCaptainYellow.png"  alt="SevaSadan" class="dark-logo" /> </span>
                                </div>
                                <div class="media-body media-text-right">
                                 <%String YellowCaptainName = votedao.retrieveYellowCaptainName(AcademicYearID);
                                 
                                 if(YellowCaptainName.contains(",0")){ 
                                	 YellowCaptainName = ""; %>
                                
                                	<h2 align="left"><a href="javascript:Results('YellowCaptain');">&nbsp;Yellow Captain:<%=YellowCaptainName %></a></h2>
                                	 
                                 <%}else{
                                	 String YellowCaptain[] = YellowCaptainName.split(",");%>
                                	 
                                	 <h2 align="left"><a href="javascript:Results('YellowCaptain');">&nbsp;Yellow Captain:&nbsp;<%=YellowCaptain[0] %>&nbsp;(<%=YellowCaptain[1] %>)</a></h2>
                                  
                                  <%}%>
                                   
                                    <p class="m-b-0"> </p>
                                </div>
                              </div>  
                               <div class="media" id="YellowCaptainID" style="display: none;">
                                 <table class="table table-striped">
                                 	<tr style="text-align: center; font-size: 16px;"><td><b>Yellow Captain Election Results</b></td><td></td></tr>
                                 	
                                 	<% for(VotingForm form:YellowList){ %>
                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
                                    <%} %>
                                    
                                    <p class="m-b-0"> </p>
                                  </table>
                              </div>
                            
                        </div>
                    </div>
				</div>
				<div class="row">
					<button type="submit" class="btn btn-primary btn-flat m-b-30 m-t-30" style="width: 200px; margin-left: 40%;" >Export full report</button>
				</div>
			</form>
                <!-- End PAge Content -->
            </div>
            
            <%} %>
            <!-- End Container fluid  -->
            <!-- footer -->
           
            <!-- End footer -->
        </div>
        <!-- End Page wrapper  -->
    </div>
    <!-- End Wrapper -->
    <!-- All Jquery -->
    <script src="js/lib/jquery/jquery.min.js"></script>
    <!-- Bootstrap tether Core JavaScript -->
    <script src="js/lib/bootstrap/js/popper.min.js"></script>
    <script src="js/lib/bootstrap/js/bootstrap.min.js"></script>
    <!-- slimscrollbar scrollbar JavaScript -->
    <script src="js/jquery.slimscroll.js"></script>
    <!--Menu sidebar -->
    <script src="js/sidebarmenu.js"></script>
    <!--stickey kit -->
    <script src="js/lib/sticky-kit-master/dist/sticky-kit.min.js"></script>
    <!--Custom JavaScript -->


    <!-- Amchart -->
     <script src="js/lib/morris-chart/raphael-min.js"></script>
    <script src="js/lib/morris-chart/morris.js"></script>
    <script src="js/lib/morris-chart/dashboard1-init.js"></script>


	<script src="js/lib/calendar-2/moment.latest.min.js"></script>
    <!-- scripit init-->
    <script src="js/lib/calendar-2/semantic.ui.min.js"></script>
    <!-- scripit init-->
    <script src="js/lib/calendar-2/prism.min.js"></script>
    <!-- scripit init-->
    <script src="js/lib/calendar-2/pignose.calendar.min.js"></script>
    <!-- scripit init-->
    <script src="js/lib/calendar-2/pignose.init.js"></script>

    <script src="js/lib/owl-carousel/owl.carousel.min.js"></script>
    <script src="js/lib/owl-carousel/owl.carousel-init.js"></script>

    <!-- scripit init-->

    <script src="js/scripts.js"></script>

</body>

</html>