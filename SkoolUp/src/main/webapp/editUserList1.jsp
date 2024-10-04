<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Tables Advanced - Target Admin</title>

  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="./css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  <link rel="stylesheet" href="./css/bootstrap.min.css">

  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
  <script type="text/javascript" src="https://use.fontawesome.com/4836edb23b.js"></script>

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
  </script>
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
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
		//alert("hiii");	
	});

	function editUserList(){
		  document.location="EditUserList";
	}
	function windowOpen(){
        document.location="welcome.jsp";
      }
    </script>
    
     <%
    	String userListEnable = (String) request.getAttribute("userListEnable");
    %>

</head>

<body>

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">User List</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">User List</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                User List
              </h3>

            </div> <!-- /.portlet-header -->

            <div class="portlet-content">     
            
            	<form action="SearchUser" method="POST">
                       <div class="col-md-3">
                         <input type="text" name="searchUserName" style="width:100%;" placeholder="Search User" required="required">
                       </div>
                       <div class="col-md-2">
                          <button type="submit" class="btn btn-success">Search User</button>
                        </div>
                        <div class="col-md-2">
                          <a href="RenderEditUserList"> <button type="button" class="btn btn-warning" >View All Users</button></a>
                        </div>
             </form>      

              <div class="table-responsive">
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
              <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Name</th>
                      <th data-sortable="true">Username</th>
                      <th data-sortable="true">EmailID</th>
                      <th data-sortable="true">Activity Status</th>
                       <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchUserList" var="UserForm">
                    <tr>
                     	<td><s:property value="fullName" /></td>
						
						<td><s:property value="username" /></td>
						
						<td><s:property value="emailId" /></td>
										
						<td><s:property value="activityStatus" /></td>
										 
						<td align="center">
							<s:url id="approveURL" action="RenderEditUser">
								<s:param name="userID" value="%{userID}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit User" title="Edit User" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableUserURL" action="DisableUser">
								<s:param name="userID" value="%{userID}" />
								<s:param name="searchUserName" value="%{searchUserName}" />
							</s:url> 
							<s:a href="javascript:disableUser('%{disableUserURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable User" title="Disable User" />
							</s:a>
						</td>
                       </tr>
                       </s:iterator>
                  
                   </tbody>
                </table>
                
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
		           
		        <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Name</th>
                      <th data-sortable="true">Username</th>
                       <th data-sortable="true">EmailID</th>
                      <th data-sortable="true">Activity Status</th>
                      <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                   <s:iterator value="signedUpUserList" var="UserForm">
                    <tr>
                     	<td><s:property value="fullName" /></td>
						
						<td><s:property value="username" /></td>
						
						<td><s:property value="emailId" /></td>
										
						<td><s:property value="activityStatus" /></td>
										 
						<td align="center">
							<s:url id="approveURL" action="RenderEditUser">
								<s:param name="userID" value="%{userID}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit User" title="Edit User" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableUserURL" action="DisableUser">
								<s:param name="userID" value="%{userID}" />
								<s:param name="searchUserName" value="%{searchUserName}" />
							</s:url> 
							<s:a href="javascript:disableUser('%{disableUserURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable User" title="Disable User" />
							</s:a>
						</td>
                       </tr>
                       </s:iterator>
                  
                   </tbody>
                </table> 
                <%
                  	}
                %>
                 
		             
              </div> <!-- /.table-responsive -->
              
			</div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->

        </div> <!-- /.col -->

      </div> <!-- /.row -->
	 </div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

<script src="./js/libs/jquery-1.10.1.min.js"></script>
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
