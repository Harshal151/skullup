<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Configure Academic Year - KovidRMS Admin</title>

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
      if ($(window).scrollTop() >= 550) {
    	  console.log("..."+$(window).scrollTop());
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
        document.location="welcome.j4sp";
     }
	
	function logoPicShow(){
		$('#logoID1').hide();
		$('#logoID').show();
		$('#logoClickID').click();
	}
    </script>
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableUser(url){
			if(confirm("Disabled users cannot access application. Are you sure you want to disable this user?")){
				document.location = url;
			}
    	}
    </script>
    
    <!-- Ends -->
    
    <!-- Delete user alert function -->
    
    <script type="text/javascript">
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete user?")) {
				document.location = url;
			}
		}
		
	</script>
    
     <%
    	String componentMsg = (String) request.getAttribute("componentMsg");
    	if(componentMsg == null || componentMsg == ""){
    		componentMsg = "dummy";
    	}
    	
    	String componentEdit = (String) request.getAttribute("componentEdit");
    	if(componentEdit == null || componentEdit == ""){
    		componentEdit = "add";
    	}
    %>
	
	<%
		LoginDAOInf daoinf = new LoginDAOImpl();
    	String AcademicYearName = daoinf.retrieveAcademicYearName1(); // Change made on 30-07-2024 10:27 AM
    %>
    
    <script type="text/javascript">
    
    var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
    
    	function openConfigDiv(){
    		$("#configDIvID").toggle();
    	}
    	
		function retrieveDivision(standardID){
			
			if(standardID == "-1"){
				alert("No standard is selected. Please select standard.");
				
				var array_element = "<select name='division' id='' class='form-control'"+
				"> <option value='-1'>Select Division</option></select>";
				
				document.getElementById("stdDivID").innerHTML = array_element;	
			}else{
				retrieveDivision1(standardID);
			}
			
		}
	
		function retrieveDivision1(standardID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
	
						var array_element = "<select name='division' class='form-control'"+
						"> <option value='-1'>Select Division</option>";
						
						var check = 0;
	
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
	
							array_element += "<option value='"+array.Release[i].divisionID+"'>"+array.Release[i].division+"</option>";
						}
	
						array_element += " </select>";
						
						if(check == 0){
							
							alert("No division found");
							
						}else{
							
							document.getElementById("stdDivID").innerHTML = array_element;	
														
						}
					
				}
			};
			xmlhttp.open("GET", "RetrieveDivisionListForStandard?standardID="
					+ standardID, true);
			xmlhttp.send();
		}
    </script>
    
</head>

<body>

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Configure Academic Year</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Configure Academic Year</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class=""></i>
                <div class="form-group">
                	<label for="academicYearList">Academic Year : <b><%=AcademicYearName%></b></label>
                	           
                </div>
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
		              
	       	<!-- Add and Update Div -->
		       <div class="col-md-12">
		              
		              <%
		              	if(componentEdit.equals("add")){
		              %>    
                <form id="validate-basic" action="ConfigureAcademicYear" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

				<div class="form-group">
                	<label for="Standard">Standard<span class="required">*</span></label>
                    <div class="form-group">
               			<s:select list="StandardList" headerKey="-1" headerValue="Select Standard" id="standardID" onchange="retrieveDivision(this.value);"  name="standardID" class="form-control" required="required"></s:select>
                	</div>
                </div>
                
                <div class="form-group">
                	<label for="Standard">Division<span class="required">*</span></label>
                    <div class="form-group" id="stdDivID">
               			<select name="divisionID" class="form-control">
               				<option value="-1">Select Division</option>
               			</select>
                	</div>
                </div>
				
				 <div class="form-group">
                	<label for="Standard">Class Teacher<span class="required">*</span></label>
                    <div class="form-group">
               			<s:select list="AppuserList" headerKey="-1" headerValue="Select Teacher" id="teacherID"  name="teacherID" class="form-control" required="required"></s:select>
                	</div>
                	
                </div>
				 <a href="javascript:openConfigDiv();">Configure</a>
                <div class="form-group" id="configDIvID" style="display: none;">
                <!--  <a>Configure</a> -->
					<div class="input-group date ui-datepicker">
                      
                      <tr><td>
                     		<div class="form-group">
			                	<label for="Standard">Subject<span class="required">*</span></label>
			                    <div class="form-group">
			               			<s:select list="SubjectList" headerKey="-1" headerValue="Select Subject" id="standardID"  name="standardID" class="form-control" required="required"></s:select>
			                	</div>
			                </div>
                     	</td>
                     </tr>
                     <tr>
                     	<td>
                     		<div class="form-group">
			                	<label for="Standard">Class Teacher<span class="required">*</span></label>
			                    <div class="form-group">
			               			<s:select list="AppuserList" headerKey="-1" headerValue="Select Teacher" id="teacherID"  name="teacherID" class="form-control" required="required"></s:select>
			                	</div>
			                </div>
                     	</td>
                     </tr>
                    
                  </div>
                </div> 
                
             <div class="form-group" align="center">
				  <button type="reset" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success">Add</button>
                </div>
                
              </form>
		      
		      <%
		              	}else{
	          %>
	          
	          <form id="validate-basic" action="EditconfigureAcademicYear" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="AcademicYearEditList" var="concessionForm">
				<%-- 		  
				<input type="hidden" name="AcademicYearID" value="<s:property value="AcademicYearID" />" >
				<input type="hidden" name="searchAcademicYear" value="<s:property value="searchAcademicYear" />" >
               
                <div class="form-group">
                  <label for="yearName">Academic Year Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="yearName" placeholder="Academic Year Name" value="<s:property value="yearName" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group">
                	<label for="Organization">Organization Name<span class="required">*</span></label>
                    <s:select list="organizationList" headerKey="-1" headerValue="Select Organization" id="organizationID"  name="organizationID" class="form-control" required="required"></s:select>
                </div>
                
				 <div class="form-group">
                  <label for="date-2">Start Date<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="startDate" name="startDate" value="<s:property value="startDate"/>" class="form-control" required="required" placeholder="Start Date" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">End Date<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="endDate" name="endDate" value="<s:property value="endDate"/>" required="required" class="form-control" placeholder="End Date" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div> --%>
				
				<div class="form-group" align="center">
				  <button type="reset" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success">Update Academic Year</button>
                </div>
                </s:iterator> 
              </form>
              
              <%
		           }
	          %>
	           
             </div>
                              
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
