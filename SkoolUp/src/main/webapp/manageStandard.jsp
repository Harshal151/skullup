<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="java.util.List"%>
     <%@page import="java.util.HashMap"%>
      <%@page import="com.kovidRMS.form.LoginForm"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>
 <%
    	LoginForm loginform = (LoginForm) session.getAttribute("USER");
    
    	StuduntDAOInf daoinf = new StudentDAOImpl();
    
    	HashMap<Integer, String> subjectTypeList = daoinf.retrieveScholasticsubjectList(loginform.getOrganizationID());
    	HashMap<Integer, String> CoscholasticsubjectTypeList = daoinf.retrieveCoscholasticsubjectList(loginform.getOrganizationID());
    	HashMap<Integer, String> AcademicsubjectTypeList = daoinf.retrieveAcademicsubjectList(loginform.getOrganizationID());
    	HashMap<Integer, String> PhysicalsubjectTypeList = daoinf.retrievePhysicalsubjectTypeList(loginform.getOrganizationID());
    	HashMap<Integer, String> CreativesubjectTypeList = daoinf.retrieveCreativesubjectTypeList(loginform.getOrganizationID());
    	HashMap<Integer, String> CompulsoryActivitiesList = daoinf.retrieveCompulsorysubjectTypeList(loginform.getOrganizationID());
    	HashMap<Integer, String> PersonalitysubjectTypeList = daoinf.retrievePersonalitysubjectList(loginform.getOrganizationID());
    	
    	String componentMsg = (String) request.getAttribute("componentMsg");
    	if(componentMsg == null || componentMsg == ""){
    		componentMsg = "dummy";
    	}
    	
    	String componentEdit = (String) request.getAttribute("componentEdit");
    	if(componentEdit == null || componentEdit == ""){
    		componentEdit = "add";
    	}
    	
     %>
    
	 <title><% if(loginform.getMedium().equals("mr")){ %>इयत्ता व्यवस्थापन - SkoolUp <% }else{ %>Manage Standard - SkoolUp<% } %></title>
	
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
  <link href="css/jquery.multiselect.css" rel="stylesheet" type="text/css">

	<link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" />
	<link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />
	
	<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>   
  
    
  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js">
  </script>
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
	
	function windowOpen1(){
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="RenderManageStandard";
     }
	
	function windowOpen2(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="ViewAllStandards";
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
	
	function logoPicShow(){
		$('#logoID1').hide();
		$('#logoID').show();
		$('#logoClickID').click();
	}
    </script>
    
    <!-- Disable User start -->
    
    <script type="text/javascript">
    	function disableStandard(url){
			if(confirm("Disabled Standard cannot access application. Are you sure you want to disable this Standard?")){
				document.location = url;
			}
    	}
   
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete Standard?")) {
				document.location = url;
			}
		}
	
      $(function () {
          $('#subjectID').multiselect({
              includeSelectAllOption: true
          });
       });
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
       
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>इयत्ता व्यवस्थापन<% }else{ %>Manage Standard <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %> इयत्ता व्यवस्थापन<% }else{ %>Manage Standard <% } %></li>
        </ol>
       
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3><i class="fa fa-table"></i> <% if(loginform.getMedium().equals("mr")){ %> इयत्ता यादी<% }else{ %>Standard List <% } %> </h3>

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
        
        <% if(loginform.getMedium().equals("mr")){ %>
		               
	            <div class="row">    
	            <div class="col-md-12">
	            	<form id="validate-basic" action="SearchStandard" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'SearchStandard');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" class="form-control" name="searchStandard" style="width:100%;" placeholder="इयत्ता शोधा" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">इयत्ता शोधा</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllStandards"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('validate-basic', 'viewSubmitID', 'ViewAllStandards');">सर्व इयत्ता पहा</button></a>
	                        </div>
	                       </div>
	             </form> 
	             </div>  
	             </div>  
	              <hr>
	              <!-- Search div -->
	              
	             <div class="col-md-8"> 
				 	<%
			            if(componentMsg.equals("available")){
			         %>
                  
                <div class="table-responsive" style="margin-top:15px; overflow: auto;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">इयत्ता </th>
                   	  <th data-sortable="true">विषय यादी</th>
                      <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchStandardList" var="UserForm">
                    <tr>
                     	<td><s:property value="standard" /></td>
						<td style="width: 20%;">
							<s:property value="subjectList" />
						</td>
						<td align="center">
							<s:url id="approveURL" action="RenderEditStandard">
								<s:param name="StandardID" value="%{StandardID}" />
								<s:param name="searchStandard" value="%{searchStandard}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="इयत्ता एडिट करा" title="इयत्ता एडिट करा" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableStandardURL" action="DisableStandard">
								<s:param name="StandardID" value="%{StandardID}" />
								<s:param name="searchStandard" value="%{searchStandard}" />
							</s:url> 
							<s:a href="javascript:disableStandard('%{disableStandardURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="इयत्ता डीलीट करा" title="इयत्ता डीलीट करा" />
							</s:a>
						</td>
                       </tr>
                       </s:iterator>
                  
                   </tbody>
                </table>
                </div>
                
                 <%
			           }
                 %>
                    
		       </div>
		      <!-- ENds -->
               
               
               <!-- Add and Update Div -->
		       <div class="col-md-4">
		              
		              <%
		              	if(componentEdit.equals("add")){
		              %>    
                <form id="validate-basic" action="AddStandard" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'AddStandard');" class="form parsley-form">

                <div class="form-group">
                  <label for="name">इयत्ता<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="standard" placeholder="इयत्ता" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group">
              		<label for="category">वर्ग निवडा</label>
              		<div>
                  		<s:select list="#{'Pre-Primary':'Pre-Primary','Primary':'प्राथमिक','Secondary':'माध्यमिक'}" required="required" name="stage" class="form-control" headerKey="" value="" headerValue="वर्ग निवडा"></s:select>
              		</div>
              	</div>
				
				<div class="form-group">
				<div><label for="name">विषय यादी<span class="required">*</span></label></div>
					
				    <s:select list="subjectTypeList" headerKey="-1" id="subjectID" cssClass="form-control" cssStyle="width: 100%" name="subjectList" multiple="true" >
				    	<s:optgroup label="Co-scholastic" list="CoscholasticsubjectTypeList" />
	    				<s:optgroup label="Extra-curricular: Physical" list="PhysicalsubjectTypeList" />
	    				<s:optgroup label="Extra-curricular: Creative" list="CreativesubjectTypeList" />
	    				<s:optgroup label="Extra-curricular: Compulsory" list="CompulsoryActivitiesList" />
	    				<s:optgroup label="Extra-curricular: Academic" list="AcademicsubjectTypeList" />
	    				<s:optgroup label="Personality Development & Life Skills" list="PersonalitysubjectTypeList" />
					</s:select>
				     
				 </div>
	            <div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="submitID">सेव करा</button>
                </div>			
                
              </form>
		      
		      <%
		              	}else{
	          %>
	          
	          <form id="validate-basic" action="EditStandard" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'EditStandard');" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="StandardEditList" var="concessionForm">
						  
				<input type="hidden" name="StandardID" value="<s:property value="StandardID" />" >
				<input type="hidden" name="searchStandard" value="<s:property value="searchStandard" />" >
               
                <div class="form-group">
                  <label for="name">इयत्ता<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="standard" placeholder="इयत्ता" value="<s:property value="standard" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group">
              		<label for="category">वर्ग निवडा</label>
              		<div>
                  		<s:select list="#{'Pre-Primary':'Pre-Primary','Primary':'प्राथमिक','Secondary':'माध्यमिक'}" required="required" name="stage" class="form-control" headerKey="-1" headerValue="वर्ग निवडा"></s:select>
              		</div>
              	</div>
              
              <div class="form-group">
	              <div><label for="name">विषय यादी<span class="required">*</span></label></div>
	              <s:select list="subjectTypeList" headerKey="-1" id="subjectID" value="subjectListValues" name="subjectList" multiple="true" >
					<s:optgroup label="Co-scholastic" list="CoscholasticsubjectTypeList" />
					<s:optgroup label="Extra-curricular: Physical" list="PhysicalsubjectTypeList" />
		    		<s:optgroup label="Extra-curricular: Creative" list="CreativesubjectTypeList" />
		    		<s:optgroup label="Extra-curricular: Compulsory" list="CompulsoryActivitiesList" />
		    		<s:optgroup label="Extra-curricular: Academic" list="AcademicsubjectTypeList" />
		    		<s:optgroup label="Personality Development & Life Skills" list="PersonalitysubjectTypeList" />
				</s:select>
			</div>
				
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="submitID">अपडेट करा</button>
                </div>
                </s:iterator> 
              </form>
              
              <%
		           }
	          %>
	           
             </div>
        
        <% }else{ %>
        
        	<div class="row">    
	            <div class="col-md-12">
	            	<form id="validate-basic" action="SearchStandard" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'SearchStandard');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" class="form-control" name="searchStandard" style="width:100%;" placeholder="Search Standards" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">Search Standard</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllStandards"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('validate-basic', 'viewSubmitID', 'ViewAllStandards');" >View All Standards</button></a>
	                        </div>
	                     </div>
	             	</form> 
	             </div>  
	           </div>  
	              <hr>
	              <!-- Search div -->
	              
	             <div class="col-md-8"> 
				 	<%
			            if(componentMsg.equals("available")){
			         %>
                  
                <div class="table-responsive" style="margin-top:15px; overflow: auto;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Standard</th>
                   	  <th data-sortable="true">Subject List</th>
                      <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchStandardList" var="UserForm">
                    <tr>
                     	<td><s:property value="standard" /></td>
						<td style="width: 20%;">
							<s:property value="subjectList" />
						</td>
						<td align="center">
							<s:url id="approveURL" action="RenderEditStandard">
								<s:param name="StandardID" value="%{StandardID}" />
								<s:param name="searchStandard" value="%{searchStandard}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Organization" title="Edit Standard" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							<s:url id="disableStandardURL" action="DisableStandard">
								<s:param name="StandardID" value="%{StandardID}" />
								<s:param name="searchStandard" value="%{searchStandard}" />
							</s:url> 
							<s:a href="javascript:disableStandard('%{disableStandardURL}')">
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable Standard" title="Disable Standard" />
							</s:a>
						</td>
                       </tr>
                       </s:iterator>
                  
                   </tbody>
                </table>
                </div>
                
                 <%
			           }
                 %>
                    
		       </div>
		      <!-- ENds -->
               
               
               <!-- Add and Update Div -->
		       <div class="col-md-4">
		              
		              <%
		              	if(componentEdit.equals("add")){
		              %>    
                <form id="validate-basic" action="AddStandard" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'AddStandard');" class="form parsley-form">

                <div class="form-group">
                  <label for="name">Standard<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="standard" placeholder="Standard" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group">
              		<label for="category">Stage</label>
              		<div>
                  		<s:select list="#{'Pre-Primary':'Pre-Primary','Primary':'Primary','Secondary':'Secondary'}" required="required" name="stage" class="form-control" headerKey="" value="" headerValue="Select Stage"></s:select>
              		</div>
              	</div>
				
				<div class="form-group">
				<div><label for="name">Subject List<span class="required">*</span></label></div>
					
				    <s:select list="subjectTypeList" headerKey="-1" id="subjectID" cssClass="form-control" cssStyle="width: 100%" name="subjectList" multiple="true" >
				    	<s:optgroup label="Co-scholastic" list="CoscholasticsubjectTypeList" />
	    				<s:optgroup label="Extra-curricular: Physical" list="PhysicalsubjectTypeList" />
	    				<s:optgroup label="Extra-curricular: Creative" list="CreativesubjectTypeList" />
	    				<s:optgroup label="Extra-curricular: Compulsory" list="CompulsoryActivitiesList" />
	    				<s:optgroup label="Extra-curricular: Academic" list="AcademicsubjectTypeList" />
	    				<s:optgroup label="Personality Development & Life Skills" list="PersonalitysubjectTypeList" />
					</s:select>
				     
				 </div>
	            <div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID" >Add Standard</button>
                </div>			
                
              </form>
		      
		      <%
		              	}else{
	          %>
	          
	          <form id="validate-basic" action="EditStandard" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'EditStandard');" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="StandardEditList" var="concessionForm">
						  
				<input type="hidden" name="StandardID" value="<s:property value="StandardID" />" >
				<input type="hidden" name="searchStandard" value="<s:property value="searchStandard" />" >
               
                <div class="form-group">
                  <label for="name">Standard<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="standard" placeholder="Standard" value="<s:property value="standard" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group">
              		<label for="category">Stage</label>
              		<div>
                  		<s:select list="#{'Pre-Primary':'Pre-Primary','Primary':'Primary','Secondary':'Secondary'}" required="required" name="stage" class="form-control" headerKey="-1" headerValue="Select Stage"></s:select>
              		</div>
              	</div>
              
              <div class="form-group">
              <div><label for="name">Subject List<span class="required">*</span></label></div>
              <s:select list="subjectTypeList" headerKey="-1" id="subjectID" value="subjectListValues" name="subjectList" multiple="true" >
				<s:optgroup label="Co-scholastic" list="CoscholasticsubjectTypeList" />
				<s:optgroup label="Extra-curricular: Physical" list="PhysicalsubjectTypeList" />
	    		<s:optgroup label="Extra-curricular: Creative" list="CreativesubjectTypeList" />
	    		<s:optgroup label="Extra-curricular: Compulsory" list="CompulsoryActivitiesList" />
	    		<s:optgroup label="Extra-curricular: Academic" list="AcademicsubjectTypeList" />
	    		<s:optgroup label="Personality Development & Life Skills" list="PersonalitysubjectTypeList" />
			</s:select>
					
				 </div>
				
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID" >Update Standard</button>
                </div>
                </s:iterator> 
              </form>
              
              <%
		           }
	          %>
	           
             </div>
          
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
  
  	<%-- <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script> --%>
    <script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>   
    <script type="text/javascript" src="js/bootstrap-multiselect.js"></script>

  <!--[if lt IE 9]>
  <script src="./js/libs/excanvas.compiled.js"></script>
  <![endif]-->
  
  <!-- Plugin JS -->
  <script src="./js/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="./js/plugins/datatables/DT_bootstrap.js"></script>
  <script src="./js/plugins/tableCheckable/jquery.tableCheckable.js"></script>
  <script src="./js/plugins/icheck/jquery.icheck.min.js"></script>
 <%--  <script src="/js/jquery.multiselect.js"></script> --%>
 
  <!-- App JS -->
  <script src="./js/target-admin.js"></script>
  


  
</body>
</html>
