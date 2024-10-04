<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="java.util.*"%>
     <%@page import="com.kovidRMS.daoImpl.*"%>
	<%@page import="com.kovidRMS.daoInf.*"%>
	<%@page import="com.kovidRMS.form.*"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%
		LoginForm form = (LoginForm) session.getAttribute("USER");
	
		ConfigurationDAOInf daoInf = new ConfigurationDAOImpl();
	
		LibraryDAOInf daoInf1 = new LibraryDAOImpl();
		
		HashMap<Integer, String> ExamList = daoInf.retrieveExaminationListForTimeTable(form.getAcademicYearID());
	
		Integer StudentCount =  daoInf1.retriveStudentCount();
		Integer StudentsIssuedBooksCount =  daoInf1.retriveStudentsBooksIssuedCount("issued");
		Integer StudentsBooksDelayedCount =  daoInf1.retriveStudentsBooksDelayedCount();
		
		Integer StaffCount =  daoInf1.retriveStaffCount();
		Integer StaffIssuedBooksCount =  daoInf1.retriveStaffsBooksIssuedCount("issued");
		Integer StaffsBooksDelayedCount =  daoInf1.retriveStaffsBooksDelayedCount();
		
		Integer BookCount =  daoInf1.retriveBookCount();
	 	Integer AvailableBookCount =  daoInf1.retriveBookCountOfStatus("available");
		Integer OnHoldBookCount =  daoInf1.retriveBookCountOfStatus("on-hold");
		Integer IssuedBookCount =  daoInf1.retriveBookCountOfStatus("issued");
		Integer RetiredBookCount =  daoInf1.retriveBookCountOfStatus("withdrawn");
		Integer DamagedBookCount =  daoInf1.retriveBookCountOfStatus("damaged");
		Integer LostBookCount =  daoInf1.retriveBookCountOfStatus("lost");
		Integer MaintenanceBookCount =  daoInf1.retriveBookCountOfStatus("maintenance");
	%>
	
	<title><% if(form.getMedium().equals("mr")){ %>डॅशबोर्ड - SkoolUp<% }else{ %>Dashboard - SkoolUp <% } %></title>

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
		$('#dashboardLiID').addClass("active");
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
	    if ($(window).scrollTop() > 500) {
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

<script type="text/javascript">	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		function DelayedStudents() {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					
					var array = JSON.parse(xmlhttp.responseText);
					
					var check=0;
					var studentName;
					var bookName;
					var expectedReturnDate;
					
					var trTag = "";
					
					trTag += "<tr><td style='text-align:center;'>Student Name</td><td style='text-align:center;'>Book Name</td><td style='text-align:center;'>Expected Return Date</td></tr>"
					
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						studentName = array.Release[i].studentName
						bookName = array.Release[i].bookName; 
						expectedReturnDate = array.Release[i].expectedReturnDate;
						
						trTag += "<tr><td style='text-align:center;'>"+studentName+"</td>"
						  + "<td style='text-align:center;'>"+bookName+"</td>"
						  + "<td style='text-align:center;'>"+expectedReturnDate+"</td></tr>";
					}
					
					if(check == 0){
						
						alert("There is no delayed student found.");
						
					}else{
						
						$("#tableTRID").html(trTag);
						$("#myModal").modal("show");
					}
				}
			};
			xmlhttp.open("GET", "RetrieveDelayedStudents", true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	
</script>

<script type="text/javascript">	var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		function DelayedStaffs() {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					
					var array = JSON.parse(xmlhttp.responseText);
					
					var check=0;
					var staffName;
					var bookName;
					var expectedReturnDate;
					
					var trTag = "";
					
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						staffName = array.Release[i].staffName
						bookName = array.Release[i].bookName; 
						expectedReturnDate = array.Release[i].expectedReturnDate;
						
						trTag += "<tr><td style='text-align:center;'>Staff Name</td><td style='text-align:center;'>Book Name</td><td style='text-align:center;'>Expected Return Date</td></tr>"
							  + "<tr><td style='text-align:center;'>"+staffName+"</td>"
							  + "<td style='text-align:center;'>"+bookName+"</td>"
							  + "<td style='text-align:center;'>"+expectedReturnDate+"</td></tr>";
					}
						  
					if(check == 0){
						
						alert("There is no delayed staffs found.");
						
					}else{
						
						$("#tableTRID1").html(trTag);
						$("#myModal1").modal("show");
					}
				}
			};
			xmlhttp.open("GET", "RetrieveDelayedStaffs", true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
	
</script>

</head>

<body>
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header" align="center">
				<b>Student List</b>				
			</div>
			<div class="modal-body">
				<table border="1" id="tableTRID" align="center" class="table table-striped table-bordered table-hover table-highlight table-checkable" data-provide="datatable" 
					data-display-rows="10" data-info="true" data-search="true" data-length-change="true" data-paginate="true" style="font-size: 14px;">
					
				</table>
			</div>
			<div class="modal-footer" >
				<button type="button" data-dismiss="modal" align="center">Close</button>
			</div>
		</div>
	</div>
</div>

<div id="myModal1" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header" align="center">
				<b>Staff List</b>			
			</div>
			<div class="modal-body">
				<table border="1" id="tableTRID1" align="center">
				
				</table>
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" style="text-align: center;">Close</button>
			</div>
		</div>
	</div>
</div>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">
     
	<div class="form-group">
	 <div id="google_translate_element" style="display:none"></div> 
		<div>
			<%  
				for(Integer ExamFormName : ExamList.keySet()){
			%>
				<h1><%=ExamList.get(ExamFormName)%></h1>
				<div style="widows: 20px;text-align: center;"><b>Time Table For Primary</b></div>
				<div class="row">
					<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						
						<tr>
							<td style="widows: 20px;text-align: center;">Date</td>
							
							<% String finalStandardList =  daoInf.retriveStandarNameByStage("Primary",ExamFormName);
						
							String[] TimeTableDetails = finalStandardList.split(",");
	                       	
				            for (int i = 0; i < TimeTableDetails.length; i++) 
							{ 
				        		%>
						          <td style="widows: 20px;text-align: center;"><%=TimeTableDetails[i].trim()%></td>
				                <%
					        }%>
						</tr>
						
						<% List<String> DateList = daoInf.retrieveDateList(ExamFormName);
							//System.out.println("DateList: "+DateList);
							for(String DateFormName : DateList){ %>
							
							<tr>
								<td style="widows: 20px;text-align: center;"><%=DateFormName%></td>
								
								<% List<Integer>StandardIDList = daoInf.retrieveStandardIDList("Primary", ExamFormName, DateFormName);
									
									for(Integer StandardIDName : StandardIDList){
										
										List<String> SubjectList = daoInf.retrieveSubjectList(ExamFormName, DateFormName,StandardIDName);
										
										for(String SubjectFormName : SubjectList){%>
										
											<td style="widows: 20px;text-align: center;"><%=SubjectFormName%></td>
											
										<% }
									}
								%>
							</tr>
							
							
						<% } %> 
					</table>
				</div>
				
				<hr>
				
				<div class="row">
				<div style="widows: 20px;text-align: center;"><b>Time Table For Secondary</b></div>
					<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
							<td style="widows: 20px;text-align: center;">Date</td>
							
							<% String finalStandardList1 =  daoInf.retriveStandarNameByStage("Secondary", ExamFormName);
						
								String[] TimeTableDetails1 = finalStandardList1.split(",");
		                       	
					            for (int i = 0; i < TimeTableDetails1.length; i++) 
								{ 
					        		%>
							          <td style="widows: 20px;text-align: center;"><%=TimeTableDetails1[i].trim()%></td>
					                <%
						        }%>
						</tr>
						<% List<String> DateList1 = daoInf.retrieveDateList1(ExamFormName);
							//System.out.println("DateList: "+DateList);
							for(String DateFormName1 : DateList1){%>
							
							<tr>
								<td style="widows: 20px;text-align: center;"><%=DateFormName1%></td>
								
								<% List<Integer>StandardIDList = daoInf.retrieveStandardIDList("Secondary", ExamFormName, DateFormName1);
									
									for(Integer StandardIDName : StandardIDList){
										
										List<String> SubjectList = daoInf.retrieveSubjectList(ExamFormName, DateFormName1,StandardIDName);
										
										for(String SubjectFormName : SubjectList){%>
										
											<td style="widows: 20px;text-align: center;"><%=SubjectFormName%></td>
											
										<% }
									}
								%>
							</tr>
							
						<% } %> 
					</table>	
				</div>
			<%
				}
			%>
		</div>
	
	</div>
	
	<% if(form.getRole().equals("librarian")){ %>
		<div class="form-group">
			 <div>
				 <div style="font-size: 25px;widows: 20px;text-align: center;padding-bottom: 20px;"><b>Students</b></div>
				
				<div class="row">
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Total Student Count</p>
			            <h3 class="row-stat-value"><%=StudentCount %></h3>
			          </div>
			        </div> 
			
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label"><a href="RenderconfigureStudentIssue">Books Issued Count</a></p>
			            <h3 class="row-stat-value"><%=StudentsIssuedBooksCount %></h3>
			          </div> 
			        </div> 
			        
			        <div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label"><a href="javascript:DelayedStudents();">Books Delayed Count</a></p>
			            <h3 class="row-stat-value"><%= StudentsBooksDelayedCount%></h3>
			          </div>
			        </div>
	        	</div>
				
			<hr>
						
				<div style="font-size: 25px;widows: 20px;text-align: center;padding-bottom: 20px;"><b>Staff</b></div>
				
				<div class="row">
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Total Staff Count</p>
			            <h3 class="row-stat-value"><%=StaffCount %></h3>
			          </div>
			        </div> 
			
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label"><a href="RenderconfigureStaffIssue">Books Issued Count</a></p>
			            <h3 class="row-stat-value"><%=StaffIssuedBooksCount %></h3>
			          </div> 
			        </div> 
			        
			       <div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label"><a href="javascript:DelayedStaffs();">Books Delayed Count</a></p>
			            <h3 class="row-stat-value"><%=StaffsBooksDelayedCount %></h3>
			          </div>
			        </div>
	        	</div>
	        
			<hr>	
				
				<div style="font-size: 25px;widows: 20px;text-align: center;padding-bottom: 20px;"><b>Inventory</b></div>
				
				<div class="row">
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Total Books</a></p>
			            <h3 class="row-stat-value"><%=BookCount %></h3>
			           
			          </div>
			        </div> 
			
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Available Books</a></p>
			            <h3 class="row-stat-value"><%=AvailableBookCount %></h3>
			           </div> 
			        </div> 
			        
			       <div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">On-hold Books</a></p>
			            <h3 class="row-stat-value"><%=OnHoldBookCount %></h3>
			          </div>
			        </div>
	        	</div>
	        	
	        	<div class="row">
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Issued Books</a></p>
			            <h3 class="row-stat-value"><%=IssuedBookCount %></h3>
			          </div>
			        </div> 
			
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Retired Books</a></p>
			            <h3 class="row-stat-value"><%=RetiredBookCount %></h3>
			          </div> 
			        </div> 
			        
			       <div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Damaged Books</a></p>
			            <h3 class="row-stat-value"><%=DamagedBookCount %></h3>
			          </div>
			        </div>
	        	</div>
	        	
	        	<div class="row">
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Lost Books</a></p>
			            <h3 class="row-stat-value"><%=LostBookCount %></h3>
			           </div>
			        </div> 
			
					<div class="col-sm-6 col-md-4" align="center">
			          <div class="row-stat" style="width: 50%;">
			            <p class="row-stat-label">Maintenance Books</a></p>
			            <h3 class="row-stat-value"><%=MaintenanceBookCount %></h3>
			           </div>
			        </div>
	        	</div>
				
			</div>
		</div>
	
	<% } %>

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

  
</body>
</html>
