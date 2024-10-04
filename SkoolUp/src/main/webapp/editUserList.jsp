<%@page import="com.kovidRMS.form.StudentForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.form.LoginForm"%>
	<%@page import="java.util.List"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%
     	LoginForm loginform = (LoginForm) session.getAttribute("USER");
     
    	String userListEnable = (String) request.getAttribute("userListEnable");
     
     	List<LoginForm> searchUserList = (List<LoginForm>) request.getAttribute("searchUserList");
     	
     	List<LoginForm> signedUpUserList = (List<LoginForm>) request.getAttribute("signedUpUserList");
    %>

	<title> <% if(loginform.getMedium().equals("mr")){%>  युझर यादी - SkoolUp <%}else{ %> User List - SkoolUp <%} %> </title>
	
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
  
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  
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
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableUser(url){
			if(confirm("Disabled users cannot access application. Are you sure you want to disable this user?")){
				document.location = url;
			}
    	}
    	
    	function enableUser(url){
			if(confirm("This user is disabled. Are you sure you want to enable this user?")){
				document.location = url;
			}
    	}
  
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete user?")) {
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
       
         <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){  %>  युझर यादी <%}else{ %> User List<%} %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>  युझर यादी <%}else{ %> Home<%} %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>  युझर यादी  <%}else{ %> User List <%} %></li>
        </ol>
     
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3><i class="fa fa-table"></i> <% if(loginform.getMedium().equals("mr")){  %>  युझर यादी  <%}else{ %> User List <%} %> </h3>

            </div> <!-- /.portlet-header -->

            <div class="portlet-content">     
              
          <% if(loginform.getMedium().equals("mr")){%>
              
               <form id="loadSubmitID" action="SearchUser" method="POST" onsubmit="submitForm('loadSubmitID', 'submitID', 'SearchUser');">
            		<div class="row">
                       <div class="col-md-3">
                         <input type="text" class="form-control" name="searchUserName" style="width:100%;" placeholder="युझर शोधा " required="required">
                       </div>
                       <div class="col-md-2">
                          <button type="submit" class="btn btn-success" id="submitID">युझर शोधा </button>
                        </div>
                        <div class="col-md-2">
                          <a href="RenderEditUserList"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('loadSubmitID', 'viewSubmitID', 'RenderEditUserList');">सर्व युझर्स पहा</button></a>
                        </div>
                       </div>
             	</form>     
				<%
                  	if(userListEnable == null || userListEnable == ""){
                  %>
                  
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
                  	}else if(userListEnable.equals("userSearchListEnable")){
		            %> 
		            
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
		      <div class="table-responsive" style="margin-top:15px;">
              <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">नाव</th>
                      <th data-sortable="true">युझर नाव</th>
                      <th data-sortable="true">भूमिका</th>
                      <th data-sortable="true">ऍक्टिव्हिटी स्टेटस</th>
                      <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
                 
                   <% for(LoginForm form:searchUserList){ %>
                    <tr>
                     	<td><%=form.getFullName() %></td>
						
						<td><%=form.getUsername() %></td>
						
						<td><%=form.getRole() %></td>
										
						<td><%=form.getActivityStatus() %></td>
										 
						<td align="center">
							<a href="RenderEditUser?userID=<%=form.getUserID() %>">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="युझर एडिट करा" title="युझर एडिट करा" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<%if(form.getActivityStatus().equals("Active")) {%>
							<a href="javascript:disableUser('DisableUser?userID=<%=form.getUserID() %>&searchUserName=<%=form.getSearchUserName() %>')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
										onmouseout="this.src='images/delete_icon_1.png'" alt="युझर डीलीट करा" title="युझर डीलीट करा" />
							</a>
							<%}else{ %>	
							<a href="javascript:enableUser('EnableUser?userID=<%=form.getUserID() %>&searchUserName=<%=form.getSearchUserName() %>')">
								<img src="images/Ok.png" style="height:20px;" onmouseover="this.src='images/Ok.png'"
										onmouseout="this.src='images/Ok.png'" alt="युझर ऍक्टिव्ह करा" title="युझर ऍक्टिव्ह करा" />
							</a>
							<%} %>
						</td>
                       </tr>
                       <%} %>
                   </tbody>
                </table>
                </div>
                
                <%
                  	}else if(userListEnable.equals("userListEnable")){
                   %>
                   
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
		        <div class="table-responsive" style="margin-top:15px;">
		        <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	   <th data-sortable="true">नाव</th>
                      <th data-sortable="true">युझर नाव</th>
                      <th data-sortable="true">भूमिका</th>
                      <th data-sortable="true">ऍक्टिव्हिटी स्टेटस</th>
                       <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
                   <%-- <s:iterator value="signedUpUserList" var="UserForm"> --%>
                    <% for(LoginForm form:signedUpUserList){ %>
                    <tr>
                     	<td><%=form.getFullName() %></td>
						
						<td><%=form.getUsername() %></td>
						
						<td><%=form.getRole() %></td>
										
						<td><%=form.getActivityStatus() %></td>
										 
						<td align="center">
							
							<a href="RenderEditUser?userID=<%=form.getUserID() %>">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="युझर एडिट करा" title="युझर एडिट करा" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
							
							<%if(form.getActivityStatus().equals("Active")) {%>
							
								<a href="javascript:disableUser('DisableUser?userID=<%=form.getUserID() %>&searchUserName=<%=form.getSearchUserName() %>')">
									<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
										onmouseout="this.src='images/delete_icon_1.png'" alt="युझर डीलीट करा" title="युझर डीलीट करा" />
								</a>
							<%}else{ %>	
							
								<a href="javascript:enableUser('EnableUser?userID=<%=form.getUserID() %>&searchUserName=<%=form.getSearchUserName() %>')">
									<img src="images/Ok.png" style="height:20px;" onmouseover="this.src='images/Ok.png'"
										onmouseout="this.src='images/Ok.png'" alt="युझर ऍक्टिव्ह करा" title="युझर ऍक्टिव्ह करा" />
								</a>
							<%} %>
							
						</td>
                       </tr>
                       <%} %>
                  
                   </tbody>
                </table> 
               	</div>
                <%
                  	}
                %>  
          <% }else{ %>
          
             <form id="loadSubmitID"  action="SearchUser" method="POST" onsubmit="submitForm('loadSubmitID', 'submitID', 'SearchUser');">
            		<div class="row">
                       <div class="col-md-3">
                         <input type="text" class="form-control" name="searchUserName" style="width:100%;" placeholder="Search User" required="required">
                       </div>
                       <div class="col-md-2">
                          <button type="submit" class="btn btn-success" id="submitID">Search User</button>
                        </div>
                        <div class="col-md-2">
                          <a href="RenderEditUserList"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('loadSubmitID', 'viewSubmitID', 'RenderEditUserList');">View All Users</button></a>
                        </div>
                       </div>
             	</form>      
				<%
                  	if(userListEnable == null || userListEnable == ""){
                  %>
                  
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
                  	}else if(userListEnable.equals("userSearchListEnable")){
		            %> 
		            
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
		      <div class="table-responsive" style="margin-top:15px;">
              <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Name</th>
                      <th data-sortable="true">Username</th>
                      <th data-sortable="true">Role</th>
                      <th data-sortable="true">Activity Status</th>
                       <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                 <%-- <s:iterator value="searchUserList" var="UserForm"> --%>
                   <% for(LoginForm form:searchUserList){ %>
                    <tr>
                     	<td><%=form.getFullName() %></td>
						
						<td><%=form.getUsername() %></td>
						
						<td><%=form.getRole() %></td>
										
						<td><%=form.getActivityStatus() %></td>
										 
						<td align="center">
							
							<a href="RenderEditUser?userID=<%=form.getUserID() %>">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit User" title="Edit User" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<%if(form.getActivityStatus().equals("Active")) {%>
							<a href="javascript:disableUser('DisableUser?userID=<%=form.getUserID() %>&searchUserName=<%=form.getSearchUserName() %>')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
										onmouseout="this.src='images/delete_icon_1.png'" alt="Disable User" title="Disable User" />
							</a>
							<%}else{ %>	
							<a href="javascript:enableUser('EnableUser?userID=<%=form.getUserID() %>&searchUserName=<%=form.getSearchUserName() %>')">
								<img src="images/Ok.png" style="height:20px;" onmouseover="this.src='images/Ok.png'"
										onmouseout="this.src='images/Ok.png'" alt="Enable User" title="Enable User" />
							</a>
							<%} %>
						</td>
                       </tr>
                       <%} %>
                   </tbody>
                </table>
                </div>
                
                <%
                  	}else if(userListEnable.equals("userListEnable")){
                   %>
                   
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
		        <div class="table-responsive" style="margin-top:15px;">
		        <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Name</th>
                      <th data-sortable="true">Username</th>
                      <th data-sortable="true">Role</th>
                      <th data-sortable="true">Activity Status</th>
                       <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                   <%-- <s:iterator value="signedUpUserList" var="UserForm"> --%>
                    <% for(LoginForm form:signedUpUserList){ %>
                    <tr>
                     	<td><%=form.getFullName() %></td>
						
						<td><%=form.getUsername() %></td>
						
						<td><%=form.getRole() %></td>
										
						<td><%=form.getActivityStatus() %></td>
										 
						<td align="center">
							
							<a href="RenderEditUser?userID=<%=form.getUserID() %>">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit User" title="Edit User" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
							
							<%if(form.getActivityStatus().equals("Active")) {%>
							
								<a href="javascript:disableUser('DisableUser?userID=<%=form.getUserID() %>&searchUserName=<%=form.getSearchUserName() %>')">
									<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
										onmouseout="this.src='images/delete_icon_1.png'" alt="Disable User" title="Disable User" />
								</a>
							<%}else{ %>	
							
								<a href="javascript:enableUser('EnableUser?userID=<%=form.getUserID() %>&searchUserName=<%=form.getSearchUserName() %>')">
									<img src="images/Ok.png" style="height:20px;" onmouseover="this.src='images/Ok.png'"
										onmouseout="this.src='images/Ok.png'" alt="Enable User" title="Enable User" />
								</a>
							<%} %>
							
						</td>
                       </tr>
                       <%} %>
                  
                   </tbody>
                </table> 
               	</div>
                <%
                  	}
                %>
            
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
