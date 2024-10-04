<%@page import="com.kovidRMS.form.*"%>
<%@page import="com.kovidRMS.daoInf.*"%>
<%@page import="com.kovidRMS.daoImpl.*"%> 
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html class="no-js"> 
<head>
	<%
		LoginForm form = (LoginForm) session.getAttribute("USER");
	
		int userID = form.getUserID();
		
		LoginDAOInf daoInf = new LoginDAOImpl();
		
		HashMap<Integer, String> organizationList = daoInf.getOrganization();
	%>

	<% if(form.getMedium().equals("mr")){ %>
		<title>डॅशबोर्ड - SkoolUp</title>
	<%}else{ %>
		<title>Dashboard - SkoolUp</title>
	<%} %>

<!-- Favicon -->
  <link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

<script type="text/javascript">
	function submitChangeOrganizationForm(organizationID){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
    	
		$("#changeOrganizationID").val(organizationID);
		$("#changeOrganizationForm").submit();	
	}
</script>

</head>

<body>

  <div class="mainbar" id="fixedDiv">

  <div class="container">
  
	<ul class="nav navbar-nav navbar-right">     
	 
	 <%  if(form.getRole().equals("superAdmin")){  %>
	  	
		<li class="dropdown navbar-profile">
         <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;" style="color: white;">
          <label style="color: white"><% if(form.getMedium().equals("mr")){ %> संस्था  <%}else{ %> Organization <%} %> </label>
         
         	<span class="navbar-profile-label">rod@rod.me &nbsp;</span>
            <i class="fa fa-caret-down"></i>
          </a>
          
          <ul class="dropdown-menu" role="menu">

			<form action="ChangeOrganization" method="POST" id="changeOrganizationForm" >
				<input type="hidden" name="changedOrganizationID" id="changeOrganizationID" >
		
			<% for(Integer organization : organizationList.keySet()){ %>
				<li>
		            <a href="javascript:submitChangeOrganizationForm('<%= organization %>');"><i class="fa fa-user"></i>&nbsp;&nbsp;<%=organizationList.get(organization)%></a>
		        </li>
			<% } %>		
			
			</form>
          </ul>

        </li>
	
	<% } %>
			
        <li class="dropdown navbar-profile">
          <a class="dropdown-toggle" data-toggle="dropdown" href="javascript:;" style="color: white;">
           <!--  <img src="./img/avatars/avatar-1-xs.jpg" class="navbar-profile-avatar" alt=""> -->
           	<%
              	if(daoInf.retrieveProfilePic(form.getUserID()) == null || daoInf.retrieveProfilePic(form.getUserID()) == ""){ 
	       	%>
	              
	      	<img src="images/user.png" alt="Profile Pic" style="height: 30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= form.getFullName() %>
	               
	       	<%  }else if(daoInf.retrieveProfilePic(form.getUserID()).isEmpty()){ %>
	            	
	            	<img src="images/user.png" alt="Profile Pic" style="height: 30px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= form.getFullName() %>
	            	
	        <%  }else{ %>
	                
	      	<img src="<%= daoInf.retrieveProfilePic(form.getUserID()) %>" style="height: 30px;" alt="Profile Pic"><%= form.getFullName() %>
	                
	        <%
	            	}
	        %>
            <span class="navbar-profile-label">rod@rod.me &nbsp;</span>
            <i class="fa fa-caret-down"></i>
          </a>

          <ul class="dropdown-menu" role="menu">

			<li><% if(form.getMedium().equals("mr")){ %>
           		<a href="RenderEditProfile"><i class="fa fa-user"></i>&nbsp;&nbsp;माझे प्रोफाइल</a>
            <%}else{ %>
				<a href="RenderEditProfile"><i class="fa fa-user"></i>&nbsp;&nbsp;My Profile</a>
			<%} %>
              
            </li>
            
            <li class="divider"></li>

            <li>
            <% if(form.getMedium().equals("mr")){ %>
           		<a href="Logout"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;लॉगआउट</a>
            <%}else{ %>
				<a href="Logout"><i class="fa fa-sign-out"></i>&nbsp;&nbsp;Logout</a>
			<%} %>

            </li>

          </ul>

        </li>

      </ul>
    <button type="button" class="btn mainbar-toggle" data-toggle="collapse" data-target=".mainbar-collapse">
      <i class="fa fa-bars"></i>
    </button>

    <div class="mainbar-collapse collapse">

      <ul class="nav navbar-nav mainbar-nav">
	
      <%
       	if(form.getRole().equals("officeAdmin")){
      %> 
        <li id="leavingCertificateLiID">
          <a href="RenderloadLeavingStudent">
            <i class="fa fa-desktop"></i>
      		<% if(form.getMedium().equals("mr")){ %> लसी/टीसी  <%}else{ %> LC/TC <%} %>      
          </a>
        </li>
      
      <% }else if(form.getRole().equals("librarian")){  %> 
      
      	<li id="dashboardLiID">
            <a href="welcome.jsp">
              <i class="fa fa-desktop"></i>
              Dashboard
            </a>
         </li>
        <li class="dropdown " id="administrationLiID">
          <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
            <i class="fa fa-cogs"></i>
            Administration
            <span class="caret"></span>
          </a>

          <ul class="dropdown-menu">   
         	 <li>
	            <a href="RenderConfigureRules">
	             <i class="fa fa-building"></i> 
	              &nbsp;&nbsp;Rules
	            </a> 
              </li> 
              
             <li class="dropdown-submenu">
              <a tabindex="-1" href="#">
              <i class="fa fa-user"></i> 
              &nbsp;&nbsp;Configuration
              </a>

              <ul class="dropdown-menu">
                <li>
                  <a href="configurePVBookType.jsp">
                  <i class="fa fa-user"></i> 
                  	&nbsp;&nbsp;Manage Book Type
                  </a>
                </li>

                <li>
                  <a href="configurePVGenre.jsp">
                  <i class="fa fa-user"></i> 
                  &nbsp;&nbsp;Manage Genre
                  </a>
                </li> 
                
                <li>
                  <a href="RenderConfigureSection">
                  <i class="fa fa-user"></i> 
                  &nbsp;&nbsp;Manage Section
                  </a>
                </li>
                
                <li>
                  <a href="configurePVVendors.jsp">
                  <i class="fa fa-user"></i> 
                  &nbsp;&nbsp;Manage Vendors
                  </a>
                </li>
              </ul>
              
             </li>
             
            </ul>
          </li>
        <li class="dropdown "id="inventoryLiID">
	       <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
	          <i class="fa fa-book"></i>
	           Inventory
	           <span class="caret"></span>
	       </a>
	      <ul class="dropdown-menu">   
            
	        <li class="dropdown-submenu">
	           <a href="">
	            <i class="fa fa-book"></i> 
	             &nbsp;&nbsp;Books 
	            </a>    
	        <ul class="dropdown-menu">   
              <li>
	           <a href="RenderManageBook">
	             <i class="fa fa-book"></i> 
	              &nbsp;&nbsp;New Book
	           </a>
	          </li>
	              
	          <li>
	            <a href="RenderBookList">
	              <i class="fa fa-file-text-o"></i> 
	              &nbsp;&nbsp;Manage Book
	             </a>
	          </li>
	          
	          <li>
	           <a href="RenderMoveBook">
	             <i class="fa fa-book"></i> 
	              &nbsp;&nbsp;Move Books
	           </a>
	          </li>
	          
			</ul>
		   </li>
		   
		   <li class="">
	           <a href="RenderManageCupboardShelves">
	            <i class="fa fa-table"></i> 
	             &nbsp;&nbsp;Cupboards  
	            </a>    
	       </li>
	        
		  </ul>
	    </li>
       	<li class="dropdown " id="issueLiID">
          <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
            <i class="fa fa-desktop"></i>
            Issue
          </a>
          
          <ul class="dropdown-menu">   
            
	        <li class="">
	           <a href="RenderconfigureStudentIssue">
	            <i class="fa fa-user"></i> 
	             &nbsp;&nbsp;Students 
	            </a>    
	       </li>
		   
		   <li class="">
	           <a href="RenderconfigureStaffIssue">
	            <i class="fa fa-user"></i> 
	             &nbsp;&nbsp;Staff  
	            </a>    
	       </li>
	       
		  </ul>
        </li>
           
	     <li class="dropdown " id="reportLiID">
	          <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
	            <i class="fa fa-line-chart"></i>
	             Reports
	             <span class="caret"></span>
	          </a>
	          
	          <ul class="dropdown-menu">   
            	<li >
	              <a tabindex="-1" href="RenderInventoryReport">
	              <i class="fa fa-file-text-o"></i> 
	              &nbsp;&nbsp; Inventory Report
	              </a>
	            </li>
	           
	            <li>
                  <a href="RenderStudentsReport">
                  <i class="fa fa-file-text-o"></i> 
                  &nbsp;&nbsp;Student Report
                  </a>
                </li> 
                
                <li>
                  <a href="RenderStaffReport">
                  <i class="fa fa-file-text-o"></i> 
                  &nbsp;&nbsp;Staff Report
                  </a>
                </li> 
             </ul> 
       	</li>
       	   
      <% }else{ %> 
        
         <li id="dashboardLiID">
            <a href="welcome.jsp">
              <i class="fa fa-desktop"></i>
             <% if(form.getMedium().equals("mr")){ %> डॅशबोर्ड  <%}else{ %> Dashboard <%} %> 
            </a>
         </li>
         
       <%  if(form.getRole().equals("superAdmin")){  %> 

        <li class="dropdown " id="administrationLiID">
          <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
            <i class="fa fa-cogs"></i>
            <% if(form.getMedium().equals("mr")){ %> ऍडमिनिस्ट्रेशन  <%}else{ %> Administration <%} %> 
            <span class="caret"></span>
          </a>

          <ul class="dropdown-menu">   
            <li class="dropdown-submenu">
              <a tabindex="-1" href="#">
              <i class="fa fa-user"></i> 
              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> यूझर   <%}else{ %> Users <%} %> 
              </a>

              <ul class="dropdown-menu">
                <li>
                  <a href="addUser.jsp">
                  <i class="fa fa-user"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> युझर नोंदवा   <%}else{ %> Register User <%} %> 
                  </a>
                </li>

                <li>
                  <a href="editUserList.jsp">
                  <i class="fa fa-user"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %>युझर व्यवस्थापन  <%}else{ %> Manage User <%} %> 
                  </a>
                </li> 
              </ul>
              
             </li>
             <li>
	              <a href="manageOrganization.jsp">
	              <i class="fa fa-building"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> संस्था  <%}else{ %> Organization <%} %> 
	              </a> 
              </li>
              
              <li class="dropdown-submenu">
	              <a href="#">
	              <i class="fa fa-calendar"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> शैक्षणिक वर्ष   <%}else{ %> Academic Year <%} %> 
	              </a>
	              
	              <ul class="dropdown-menu">
                <li>
                  <a href="RenderAcademicYear">
                  <i class="fa fa-calendar"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> शैक्षणिक वर्ष व्यवस्थापन   <%}else{ %> Manage Academic Year <%} %> 
                  </a>
                </li>

                <li>
                  <a href="RenderconfigureAcademicYear">
                  <i class="fa fa-calendar"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> शैक्षणिक वर्ष कॉन्फीग्युर करा   <%}else{ %> Configure Academic Year <%} %> 
                  </a>
                </li> 
                
                <li>
                  <a href="RenderconfigureExamination">
                  <i class="fa fa-calendar"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> वेळापत्रक कॉन्फीग्युर करा   <%}else{ %> Configure Examinations <%} %> 
                  </a>
                </li> 
                
                <li>
                  <a href="RenderconfigureSubjectAssessment">
                  <i class="fa fa-calendar"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विषय मूल्यमापन कॉन्फीग्युर करा   <%}else{ %> Configure Subject Assessment <%} %> 
                  </a>
                </li> 
              </ul>
              </li> 
              
              <li class="dropdown-submenu">
	              <a tabindex="-1" href="#">
	              <i class="fa fa-users"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> इयत्ता  <%}else{ %> Classes <%} %> 
	              </a>
	              
	           <ul class="dropdown-menu">
                <li>
                  <a href="RenderManageStandard">
                  <i class="fa fa-users"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> इयत्ता व्यवस्थापन   <%}else{ %> Manage Standard <%} %> 
                  </a>
                </li>

                <li>
                  <a href="RenderDivision">
                  <i class="fa fa-users"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> तुकडी मूल्यमापन   <%}else{ %> Manage Division <%} %> 
                  </a>
                </li> 
              </ul>
              </li> 
              
              <li>
	              <a href="manageSubject.jsp">
	              <i class="fa fa-book"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विषय   <%}else{ %> Subjects <%} %> 
	              </a>
	          
              </li> 
              
              <li>
	              <a href="manageCommutation.jsp">
	              <i class="fa fa-bus"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> प्रवास   <%}else{ %> Commutation <%} %> 
	              </a> 
              </li>
              
              <li>
	              <a href="RenderManageTimeTable">
	              <i class="fa fa-calendar-o"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> वेळापत्रक व्यवस्थापन  <%}else{ %> Manage TimeTable <%} %> 
	              </a> 
              </li>
              
              <li>
	              <a href="RenderImportExport">
	              <i class="fa fa-calendar-o"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> आयात/निर्यात   <%}else{ %> Import/Export <%} %> 
	              </a> 
              </li>
              
             <!--  <li>
	              <a href="RenderElection">
	              <i class="fa fa-calendar-o"></i> 
	              &nbsp;&nbsp;Manage Voting
	              </a> 
              </li>  -->
              
            </ul>
            </li>
            
         <% }else if(form.getRole().equals("administrator")){ %>
        		
        	  <li class="dropdown " id="administrationLiID">
              <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
                <i class="fa fa-cogs"></i>
               <% if(form.getMedium().equals("mr")){ %> ऍडमिनिस्ट्रेशन  <%}else{ %> Administration <%} %> 
                <span class="caret"></span>
              </a>

              <ul class="dropdown-menu">   
                <li class="dropdown-submenu">
              <a tabindex="-1" href="#">
              <i class="fa fa-user"></i> 
              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> युझर  <%}else{ %> Users <%} %> 
              </a>

              <ul class="dropdown-menu">
                <li>
                  <a href="addUser.jsp">
                  <i class="fa fa-user"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> युझर नोंदणी   <%}else{ %> Register User <%} %>
                  </a>
                </li>

                <li>
                  <a href="editUserList.jsp">
                  <i class="fa fa-user"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %>युझर व्यवस्थापन  <%}else{ %> Manage User <%} %> 
                  </a>
                </li> 
              </ul>
              
             </li>
                  <li class="dropdown-submenu">
    	              <a href="#">
    	              <i class="fa fa-calendar"></i> 
    	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> शैक्षणिक वर्ष   <%}else{ %> Academic Year <%} %> 
    	              </a>
    	              
    	              <ul class="dropdown-menu">
                    <li>
                      <a href="RenderAcademicYear">
                      <i class="fa fa-calendar"></i> 
                      &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> शैक्षणिक वर्ष व्यवस्थापन   <%}else{ %> Manage Academic Year <%} %> 
                      </a>
                    </li>

                    <li>
                      <a href="RenderconfigureAcademicYear">
                      <i class="fa fa-calendar"></i> 
                      &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> शैक्षणिक वर्ष कॉन्फीग्युर करा   <%}else{ %> Configure Academic Year <%} %>
                      </a>
                    </li> 
                    
                    <li>
                      <a href="RenderconfigureExamination">
                      <i class="fa fa-calendar"></i> 
                      &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> वेळापत्रक कॉन्फीग्युर करा   <%}else{ %> Configure Examinations <%} %>
                      </a>
                    </li> 
                    
                    <li>
	                  <a href="RenderconfigureSubjectAssessment">
	                  <i class="fa fa-calendar"></i> 
	                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विषय मूल्यमापन कॉन्फीग्युर करा   <%}else{ %> Configure Subject Assessment <%} %> 
	                  </a>
	                </li>
	                  
                  </ul>
                  </li> 
                  
                <li class="dropdown-submenu">
	              <a tabindex="-1" href="#">
	              <i class="fa fa-users"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> इयत्ता  <%}else{ %> Classes <%} %> 
	              </a>
	              
	           <ul class="dropdown-menu">
                <li>
                  <a href="RenderManageStandard">
                  <i class="fa fa-users"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> इयत्ता व्यवस्थापन   <%}else{ %> Manage Standard <%} %> 
                  </a>
                </li>

                <li>
                  <a href="RenderDivision">
                  <i class="fa fa-users"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> तुकडी मूल्यमापन   <%}else{ %> Manage Division <%} %> 
                  </a>
                </li> 
              </ul>
              </li> 
              
              <li>
	              <a href="manageSubject.jsp">
	              <i class="fa fa-book"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विषय   <%}else{ %> Subjects <%} %> 
	              </a>
	          
              </li> 
              
              <li>
    	              <a href="manageCommutation.jsp">
    	              <i class="fa fa-bus"></i> 
    	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> प्रवास   <%}else{ %> Commutation <%} %> 
    	              </a> 
                  </li>
                  
                  <li>
    	              <a href="RenderManageTimeTable">
    	              <i class="fa fa-calendar-o"></i> 
    	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> वेळापत्रक व्यवस्थापन  <%}else{ %> Manage TimeTable <%} %>
    	              </a> 
                  </li>
                  
                  <li>
		              <a href="RenderImportExport">
		              <i class="fa fa-calendar-o"></i> 
		              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> आयात/निर्यात   <%}else{ %> Import/Export <%} %> 
		              </a> 
	              </li>
                  
             <!--  <li>
	              <a href="RenderElection">
	              <i class="fa fa-calendar-o"></i> 
	              &nbsp;&nbsp;Manage Voting
	              </a> 
              </li>  -->
              
                </ul>
                </li>
        <% } %>  
            
            <li class="dropdown " id="studentLiID">
	          <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
	            <i class="fa fa-graduation-cap"></i>
	            <% if(form.getMedium().equals("mr")){ %> विद्यार्थी  <%}else{ %> Students <%} %>  
	             <span class="caret"></span>
	          </a>
	          
	          <ul class="dropdown-menu">   
            
	            <li >
	              <a href="RenderaddStudent">
	              <i class="fa fa-user"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विद्यार्थी नोंदणी  <%}else{ %> Register Students <%} %> 
	              </a>
	            </li>
	           
	           <li >
	              <a href="RenderManageEditStudentList">
	              <i class="fa fa-user"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विद्यार्थी व्यवस्थापन  <%}else{ %> Manage Students <%} %> 
	              </a>
	            </li>
	            
	            <li >
	              <a href="RenderStudentAttendance">
	              <i class="fa fa-user"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विद्यार्थी उपस्थिती   <%}else{ %> Student Attendance <%} %> 
	              </a>
	            </li>
	            
	             <li >
	              <a href="RendertransferStudent">
	              <i class="fa fa-user"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विद्यार्थी ट्रान्स्फर  <%}else{ %> Transfer Students <%} %> 
	              </a>
	            </li>
	            
	            <li >
	              <a href="RenderBulkUpdateStudent">
	              <i class="fa fa-user"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विद्यार्थ्यांचे बल्क अपडेट  <%}else{ %> Bulk Update Students <%} %> 
	              </a>
	            </li>
	            
	           </ul>
	         </li>
        	
         <li class="dropdown "id="myExamLiID">
	          <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
	            <i class="fa fa-book"></i>
	            <% if(form.getMedium().equals("mr")){ %> परीक्षा   <%}else{ %> Examinations <%} %>  
	             <span class="caret"></spaSubjectsn>
	          </a>
	          
	          <ul class="dropdown-menu">   
            
	            <li class="dropdown-submenu">
	              <a tabindex="-1" href="#">
	              <i class="fa fa-book"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> माझे विषय   <%}else{ %> My Subjects <%} %> 
	              </a>
	              
	              <ul class="dropdown-menu">
	                <li>
	                  <a href="RenderConfigureMySubject">
	                  <i class="fa fa-file-text-o"></i> 
	                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> शैक्षणिक  <%}else{ %> Scholastic <%} %> 
	                  </a>
	                </li>
	
	                <li>
	                  <a href="RenderConfigureCoScholastic">
	                  <i class="fa fa-file-text-o"></i> 
	                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> सह-शैक्षणिक  <%}else{ %> Co-scholastic <%} %> 
	                  </a>
	                </li> 
	                
	                <li>
	                  <a href="RenderConfigureExtraCurricularActivity">
	                  <i class="fa fa-file-text-o"></i> 
	                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> अभ्यासेतर उपक्रम  <%}else{ %> Extra-curricular Activity <%} %> 
	                  </a>
	                </li>
	
	                <li>
	                  <a href="RenderConfigurePersonlityDevelopment">
	                  <i class="fa fa-file-text-o"></i> 
	                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> व्यक्तिमत्व विकास आणि जीवन कौशल्य   <%}else{ %> Personality Development & Life Skills <%} %> 
	                  </a>
	                </li>
              	</ul>
	          </li>
	           
	           <li >
	              <a tabindex="-1" href="RenderClassSubject">
	              <i class="fa fa-book"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> माझा वर्ग  <%}else{ %> My Class <%} %> 
	              </a>
	           </li>
	          </ul>
        	</li>
        	
        	<li class="dropdown " id="reportLiID">
	          <a href="#about" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown">
	            <i class="fa fa-line-chart"></i>
	             <% if(form.getMedium().equals("mr")){ %> अहवाल  <%}else{ %> Reports <%} %> 
	             <span class="caret"></span>
	          </a>
	          
	          <ul class="dropdown-menu">   
            
	            <li >
	              <a tabindex="-1" href="RenderClassStudents">
	              <i class="fa fa-file-text-o"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> प्रगतीपुस्तक   <%}else{ %> Report Card <%} %> 
	              </a>
	            </li>
	           
	            <li>
                  <a href="RenderReportCardHistory">
                  <i class="fa fa-file-text-o"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> मागील प्रगतिपुस्तके   <%}else{ %> Report-Card History <%} %> 
                  </a>
                </li> 
                
                <li>
                  <a href="RenderReportCardReview">
                  <i class="fa fa-file-text-o"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> प्रगतीपुस्तक आढावा/रिव्ह्यू   <%}else{ %> Report-Card Review <%} %> 
                  </a>
                </li> 
                
	           <li class="dropdown-submenu">
	              <a tabindex="-1" href="#">
	              <i class="fa fa-file-text-o"></i> 
	              &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> कस्टम रिपोर्ट   <%}else{ %>  Custom Report <%} %>
	              </a>
	              
	           <ul class="dropdown-menu">
                <li>
                  <a href="RenderStudentsCustomReport">
                  <i class="fa fa-file-text-o"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> विद्यार्थी  <%}else{ %> Students <%} %> 
                  </a>
                </li>

                <li>
                  <a href="RenderExamCustomReport">
                  <i class="fa fa-file-text-o"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> परीक्षा   <%}else{ %> Exams <%} %> 
                  </a>
                </li> 
                
                <li>
                  <a href="RenderConfigureConsolidatedSheet">
                  <i class="fa fa-file-text-o"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> एकत्रित पत्रके  <%}else{ %> Consolidated Sheet <%} %> 
                  </a>
                </li>
                
                <li>
                  <a href="RenderXthSTDStudentsReport">
                  <i class="fa fa-file-text-o"></i> 
                  &nbsp;&nbsp;<% if(form.getMedium().equals("mr")){ %> १० वी - इंटर्नल मार्क्स  रिपोर्ट   <%}else{ %> STD X - Internal Marks Report <%} %> 
                  </a>
                </li>
                
              </ul>
	        </li>
	      </ul>
        </li>
         
       <% } %> 	
    
     </ul>
  </div> <!-- /.navbar-collapse -->   

</div> <!-- /.container --> 

</div> <!-- /.mainbar -->

</body>
</html>
    