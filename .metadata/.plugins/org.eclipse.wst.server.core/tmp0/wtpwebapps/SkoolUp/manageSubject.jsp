<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.kovidRMS.form.StudentForm"%>
    <%@page import="java.util.List"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="com.kovidRMS.form.LoginForm"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>

  <%
    	LoginForm loginform = (LoginForm) session.getAttribute("USER");
    
	  	List<StudentForm> searchSubjectList = (List<StudentForm>) request.getAttribute("searchSubjectList");
		
		String SubjectType = (String) request.getAttribute("SubjectType");
		
		String SubjectName = (String) request.getAttribute("SubjectName");
		
		List<StudentForm> ActivityList = (List<StudentForm>) request.getAttribute("ActivityList");
		
		String componentMsg = (String) request.getAttribute("componentMsg");
    	if(componentMsg == null || componentMsg == ""){
    		componentMsg = "dummy";
    	}
    	
    	String componentEdit = (String) request.getAttribute("componentEdit");
    	if(componentEdit == null || componentEdit == ""){
    		componentEdit = "add";
    	}
    	
	%> 
	
  <title><% if(loginform.getMedium().equals("mr")){ %>विषय व्यवस्थापन - SkoolUp <% }else{ %>Manage Subjects - SkoolUp<% } %></title>
  
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
	ul.actionMessage{
            list-style:none;
            font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
        }
        
        ul.errorMessage{
            list-style:none;
            font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
        }

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
		
        document.location="manageSubject.jsp";
     }
	
	function windowOpen2(){
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="ViewAllSubjects";
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
    	function disableSubject(url){
			if(confirm("Disabled Subject cannot access application. Are you sure you want to disable this Subject?")){
				document.location = url;3
			}
    	}
    	
    	
    	function deleteRow1(deleteID, TRID){
        	if(confirm("Are you sure you want to remove this row?")){
        		deleteRow2(deleteID, TRID);
        	}
        }
        
        </script>

    	<script type="text/javascript" charset="UTF-8">
    		var xmlhttp;
    		if (window.XMLHttpRequest) {
    			xmlhttp = new XMLHttpRequest();
    		} else {
    			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    		}
    	
    		function deleteRow2(deleteID, TRID) {
    		
    			xmlhttp.onreadystatechange = function() {
    				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
    	
    					var array = JSON.parse(xmlhttp.responseText);

    					var check = 0;
    	
    					for ( var i = 0; i < array.Release.length; i++) {

    						check = array.Release[i].check;
    						
    					}
    					
    					if(check == 1){
    						
    						$("#"+TRID+"").remove();
    						
    					}else{
    						alert("Failed to delete row. Please check server logs for more details.")
    					}
    					
    				}
    			};
    			xmlhttp.open("GET", "DeleteActivityRow?activityID="
    					+ deleteID, true);
    			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
    			xmlhttp.send();
    		}
    	
        </script>
    
    <!-- Ends -->
   
    <script type="text/javascript">
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete Subject?")) {
				document.location = url;
			}
		}
		
		//For Showing Activity Section
		
		 function ShowActivity(subjectType){
			
			 if(subjectType =="Co-scholastic"){
				
				 $("#activityID").show();
				 
			 }else {
				 
				 $("#activityID").hide();
			}
		} 
	
	var activityCounter = 1;

	function addActivityRow(activity){
		
		if(activity == ""){
			alert("Please insert activity name.");
		}else{
			
			addActivityRow1(activity);
		}
	}
	</script>
	
	<script type="text/javascript">
	function addActivityRow1(activity){
		
		var trID = "newExamTRID"+activityCounter;
		
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+activity+"<input type='hidden' name='newactivity' value='"+activity+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeActivityTR(\"" + trID + "\");'/></td>"
			  + "</tr>";
		
		$(trTag).insertAfter($('#activityTRID'));
		
		activityCounter++;
		
		$("#activitiesID").val("");
		
	}
	
	function removeActivityTR(trID){
		if(confirm("Are you sure you want to delete this row?")){
			$("#"+trID+"").remove();
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
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>विषय व्यवस्थापन<% }else{ %>Manage Subject <% } %> </h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ<% }else{ %>Home <% } %></a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>विषय व्यवस्थापन<% }else{ %>Manage Subject <% } %> </li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                <% if(loginform.getMedium().equals("mr")){ %> विषय यादी<% }else{ %>Standard List <% } %>
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
		       
		      <% if(loginform.getMedium().equals("mr")){ %>
		       		<div class="row">    
	            <div class="col-md-12">
	            	<form id="validate-basic" action="SearchSubjects" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'SearchSubjects');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" class="form-control" name="searchSubject" style="width:100%;" placeholder="विषय शोधा" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">विषय शोधा</button>
	                       </div>
	                       <div class="col-md-2">
	                          <a href="ViewAllSubjects"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('validate-basic', 'viewSubmitID', 'ViewAllSubjects');">सर्व विषय पहा</button></a>
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
                  
                <div class="table-responsive" style="margin-top:15px;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">विषय नाव</th>
                   	  <th data-sortable="true">विषय प्रकार</th>
                   	   <th data-sortable="true">ऍक्टिव्हिटी स्टेटस</th>
                      <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
               
                  <% for(StudentForm form:searchSubjectList){ %>
                    <tr>
                     	<td><%=form.getName() %></td>
						<td><%=form.getSubjectType() %></td>
						<td><%=form.getActivityStatus() %></td>
						
					<td align="center">
							
						<a href="RenderEditSubject?SubjectID=<%=form.getSubjectID() %>&searchSubject=<%=form.getSearchSubject() %>&subjectType=<%=form.getSubjectType() %>&name=<%=form.getName() %>">
							<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="विषय एडिट करा" title="विषय एडिट करा" />
						</a>&nbsp;&nbsp;&nbsp;&nbsp;
							
						<a href="javascript:disableSubject('DisableSubject?SubjectID=<%=form.getSubjectID() %>&searchSubject=<%=form.getSearchSubject() %>')">
							<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="विषय डीलीट करा" title="विषय  डीलीट करा" />
						</a>
					</td>
                   </tr>
                 <%} %>
                    
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
                <form id="validate-basic" action="AddSubject" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'AddSubject');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="name">विषय नाव<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="name" placeholder="विषय नाव" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
			
				<div class="form-group">
                  <label for="name">विषय प्रकार<span class="required">*</span></label>
                  <div>
                  	<s:select list="#{'Scholastic':'Scholastic','Co-scholastic':'Co-scholastic','Extra-curricular: Physical':'Extra-curricular: Physical','Extra-curricular: Creative':'Extra-curricular: Creative', 'Extra-curricular: Academic':'Extra-curricular: Academic', 'Personality Development & Life Skills':'Personality Development & Life Skills'}" 
                  	required="required" name="subjectType" class="form-control" value="" headerKey="" headerValue="विषय प्रकार" ></s:select>
                 </div>
                </div>
                
                <div class="form-group">
                  <label for="name">क्रम लावा<span class="required">*</span></label>
                  <div>
                  	<input type="number" name="sortOrder" placeholder="क्रम लावा" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="submitID">सेव करा</button>
                </div>
                
              </form>
		      
		      <%
		             }else{
	          %>
	          
	          <form id="validate-basic" action="EditSubject" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'EditSubject');" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="SubjectEditList" var="concessionForm">
						  
				<input type="hidden" name="SubjectID" value="<s:property value="SubjectID" />" >
				<input type="hidden" name="searchSubject" value="<s:property value="searchSubject" />" >
               
                <div class="form-group">
                  <label for="name">विषय नाव<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="name" placeholder="विषय नाव" value="<s:property value="name" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group">
                  <label for="name">विषय प्रकार<span class="required">*</span></label>
                  <div>
                  	<s:select list="#{'Scholastic':'Scholastic','Co-scholastic':'Co-scholastic','Extra-curricular: Physical':'Extra-curricular: Physical','Extra-curricular: Creative':'Extra-curricular: Creative','Extra-curricular: Academic':'Extra-curricular: Academic','Discipline':'Discipline','Personality Development':'Personality Development','Life Skills':'Life Skills'}" 
                  	required="required" name="subjectType" class="form-control" headerKey="" headerValue="विषय प्रकार" onchange="ShowActivity(this.value)" ></s:select>
                 </div>
                </div>
	              
	             <div class="form-group">
                  <label for="name">क्रम लावा<span class="required">*</span></label>
                  <div>
                  	<input type="number" name="sortOrder" placeholder="क्रम लावा" required="required" value="<s:property value="sortOrder" />" class="form-control" data-required="true" >
                 </div>
                </div>
                
	           <%if(SubjectName.equals("Computer") || SubjectType.equals("Co-scholastic")){ %>
               
	               <div class="form-group" id="activityID" > 
	              	<label for="name">उपक्रम विभाग:</label>
	              	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
					       <td>उपक्रम</td>
					       <td>कृती</td>
					    </tr>
					       	
					    <tr id="activityTRID">
					       <td>
					       		<input type="text" name="activity" id="activitiesID" placeholder="उपक्रम"> 
					       </td>
					          
						   <td> 
				       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
					       			onmouseout="this.src='images/addBill.png'" alt="ऍड उपक्रम" title="ऍड उपक्रम" 
					       			onclick="addActivityRow(activitiesID.value);"/>
							</td>
					    </tr>
					    
					<%
						
				       for(StudentForm studform:ActivityList){
					%>
			     
			     <tr id="newTRID<%=studform.getActivityID()%>">
			       	<td>
			       		<input type="hidden" name="editactivityID" value="<%=studform.getActivityID()%>">
			       		<input type="text" name="editactivity" class="form-control" value="<%=studform.getActivity()%>">
			       	</td>
			      
			       	<td>
			       		<img src='images/delete.png' style='height:24px;cursor: pointer;' onclick="deleteRow1(<%=studform.getActivityID()%>,'newTRID<%=studform.getActivityID()%>');" 
							alt='डीलीट करा' title='डीलीट करा'/>
					</td>
			       		
			    </tr>
			    
			 <%
				  	}
			 %>
					</table>
	              </div>
                <%} %>
                
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
	            	<form id="validate-basic" action="SearchSubjects" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'SearchSubjects');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" class="form-control" name="searchSubject" style="width:100%;" placeholder="Search Subjects" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">Search Subject</button>
	                       </div>
	                       <div class="col-md-2">
	                          <a href="ViewAllSubjects"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('validate-basic', 'viewSubmitID', 'ViewAllSubjects');">View All Subjects</button></a>
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
                  
                <div class="table-responsive" style="margin-top:15px;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Subject Name</th>
                   	  <th data-sortable="true">Subject Type</th>
                   	   <th data-sortable="true">Activity Status</th>
                      <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
               
                  <% for(StudentForm form:searchSubjectList){ %>
                    <tr>
                     	<td><%=form.getName() %></td>
						<td><%=form.getSubjectType() %></td>
						<td><%=form.getActivityStatus() %></td>
						
					<td align="center">
							
						<a href="RenderEditSubject?SubjectID=<%=form.getSubjectID() %>&searchSubject=<%=form.getSearchSubject() %>&subjectType=<%=form.getSubjectType() %>&name=<%=form.getName() %>">
							<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Subject" title="Edit Subject" />
						</a>&nbsp;&nbsp;&nbsp;&nbsp;
							
						<a href="javascript:disableSubject('DisableSubject?SubjectID=<%=form.getSubjectID() %>&searchSubject=<%=form.getSearchSubject() %>')">
							<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Disable Subject" title="Disable Subject" />
						</a>
					</td>
                   </tr>
                 <%} %>
                    
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
                <form id="validate-basic" action="AddSubject" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'AddSubject');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="name">Subject Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="name" placeholder="Subject Name" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
			
				<div class="form-group">
                  <label for="name">Subject Type<span class="required">*</span></label>
                  <div>
                  	<s:select list="#{'Scholastic':'Scholastic','Co-scholastic':'Co-scholastic','Extra-curricular: Physical':'Extra-curricular: Physical','Extra-curricular: Creative':'Extra-curricular: Creative','Extra-curricular: Compulsory':'Extra-curricular: Compulsory', 'Extra-curricular: Academic':'Extra-curricular: Academic', 'Personality Development & Life Skills':'Personality Development & Life Skills'}" 
                  	required="required" name="subjectType" class="form-control" value="" headerKey="" headerValue="Subject Type" ></s:select>
                 </div>
                </div>
				
				<div class="form-group">
                  <label for="name">Sort Order<span class="required">*</span></label>
                  <div>
                  	<input type="number" name="sortOrder" placeholder="Sort Order" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
                
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success">Add Subject</button>
                </div>
                
              </form>
		      
		      <%
		             }else{
	          %>
	          
	          <form id="validate-basic" action="EditSubject" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'submitID', 'EditSubject');" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="SubjectEditList" var="concessionForm">
						  
				<input type="hidden" name="SubjectID" value="<s:property value="SubjectID" />" >
				<input type="hidden" name="searchSubject" value="<s:property value="searchSubject" />" >
               
                <div class="form-group">
                  <label for="name">Subject Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="name" placeholder="Subject Name" value="<s:property value="name" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				<div class="form-group">
                  <label for="name">Subject Type<span class="required">*</span></label>
                  <div>
                  	<s:select list="#{'Scholastic':'Scholastic','Co-scholastic':'Co-scholastic','Extra-curricular: Physical':'Extra-curricular: Physical','Extra-curricular: Creative':'Extra-curricular: Creative','Extra-curricular: Compulsory':'Extra-curricular: Compulsory','Extra-curricular: Academic':'Extra-curricular: Academic','Discipline':'Discipline','Personality Development':'Personality Development','Life Skills':'Life Skills'}" 
                  	required="required" name="subjectType" class="form-control" headerKey="" headerValue="Subject Type" onchange="ShowActivity(this.value)" ></s:select>
                 </div>
                </div>
	             
	             <div class="form-group">
                  <label for="name">Sort Order<span class="required">*</span></label>
                  <div>
                  	<input type="number" name="sortOrder" placeholder="Sort Order" value="<s:property value="sortOrder" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
                 
	           <%if(SubjectName.equals("Computer") || SubjectType.equals("Co-scholastic")){ %>
               
	               <div class="form-group" id="activityID" > 
	              	<label for="name">Activity Section:</label>
	              	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
					       <td>Activity</td>
					       <td>Action</td>
					    </tr>
					       	
					    <tr id="activityTRID">
					       <td>
					       		<input type="text" name="activity" id="activitiesID" placeholder="Activity"> 
					       </td>
					          
						   <td> 
				       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
					       			onmouseout="this.src='images/addBill.png'" alt="Add Activity" title="Add Activity" 
					       			onclick="addActivityRow(activitiesID.value);"/>
							</td>
					    </tr>
					    
					<%
						
				       for(StudentForm studform:ActivityList){
					%>
			     
			     <tr id="newTRID<%=studform.getActivityID()%>">
			       	<td>
			       		<input type="hidden" name="editactivityID" value="<%=studform.getActivityID()%>">
			       		<input type="text" name="editactivity" class="form-control" value="<%=studform.getActivity()%>">
			       	</td>
			      
			       	<td>
			       		<img src='images/delete.png' style='height:24px;cursor: pointer;' onclick="deleteRow1(<%=studform.getActivityID()%>,'newTRID<%=studform.getActivityID()%>');" 
							alt='Delete row' title='Delete row'/>
					</td>
			       		
			    </tr>
			    
			 <%
				  	}
			 %>
					</table>
	              </div>
                <%} %>
                
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID">Update Subject</button>
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

  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  <script src="./js/libs/bootstrap.min.js"></script>

  <!-- Plugin JS -->
  <script src="./js/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="./js/plugins/datatables/DT_bootstrap.js"></script>
  <script src="./js/plugins/tableCheckable/jquery.tableCheckable.js"></script>
  <script src="./js/plugins/icheck/jquery.icheck.min.js"></script>

  <!-- App JS -->
  <script src="./js/target-admin.js"></script>
  


  
</body>
</html>
