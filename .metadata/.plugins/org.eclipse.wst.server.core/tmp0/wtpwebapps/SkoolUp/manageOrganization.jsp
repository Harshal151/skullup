<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.kovidRMS.form.LoginForm"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%	
		LoginForm form = (LoginForm) session.getAttribute("USER");
		
		String componentMsg = (String) request.getAttribute("componentMsg");
		if(componentMsg == null || componentMsg == ""){
			componentMsg = "dummy";
		}
		
		String componentEdit = (String) request.getAttribute("componentEdit");
		if(componentEdit == null || componentEdit == ""){
			componentEdit = "add";
		}
		
		String BoardVal = (String) request.getAttribute("Board");
		if(BoardVal == null || BoardVal == ""){
			BoardVal = "";
		}
	%>
	
	<% if(form.getMedium().equals("mr")){ %>
	 <title>संस्था व्यवस्थापित करा - SkoolUp</title>
	<% }else{ %>
	 <title>Manage Organization - SkoolUp</title>
	<% } %>
	 
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
  
	<!-- board wise medium div hide & show -->
	
	<script type="text/javascript">
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
		//alert("hiii");	
	});
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
			
        document.location="manageOrganization.jsp";
     }
	
	function windowOpen2(){
		
		 $('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); 
			
        document.location="ViewAllOrganizations";
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
		
		$("#logoClickID").attr("required", true);
	}
	
	/* For BoardLogo */
	function logoPicShow1(){
		$('#logoID2').hide();
		$('#logoID3').show();
		$('#logoClickID1').click();
		$("#logoClickID").attr("required", true);
	}
	
	function signatureShow(divID){
		if(divID == "signatureID1")
		{
			$('#signatureID1').hide();
			$('#signatureID').show();
		}else if(divID == "signatureID2")
		{
			$('#signatureID2').hide();
			$('#signatureID3').show();
		}else if(divID == "signatureID4")
		{
			$('#signatureID4').hide();
			$('#signatureID5').show();
		}
		
		$('#signatureClickID').click();
		$("#signatureClickID").attr("required", true);
	}
	
	function sealShow(divID){
		if(divID == "sealID1")
		{
			$('#sealID1').hide();
			$('#sealID').show();
		}else if(divID == "sealID2")
		{
			$('#sealID2').hide();
			$('#sealID3').show();	
		}else if(divID == "sealID4")
		{
			$('#sealID4').hide();
			$('#sealID5').show();	
		}
		$('#sealClickID').click();
		$("#sealClickID").attr("required", true);
	}
	
    	function disableOrganization(url){
			if(confirm("Disabled Organization cannot access application. Are you sure you want to disable this Organization?")){
				document.location = url;
			}
    	}

		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete Organization?")) {
				document.location = url;
			}
		}
	
</script>

<script type="text/javascript">
	var LibraryCounter = 1;

	function addLibraryRow(library){
		
		if(library == ""){
			alert("Please insert library.");
		}else{
			
			addLibraryRow1(library);
		}
	}
	</script>
	
	<script type="text/javascript">
	function addLibraryRow1(library){
		
		var trID = "newlibraryTRID"+LibraryCounter;
		
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+library+"<input type='hidden' name='newLibrary' value='"+library+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeLibraryTR(\"" + trID + "\");'/></td>"
			  + "</tr>";
		
		$(trTag).insertAfter($('#libraryNameTRID'));
		
		LibraryCounter++;
		
		$("#libraryNameID").val("");
		
	}
	
	function removeLibraryTR(trID){
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
       <% if(form.getMedium().equals("mr")){ %>
	        <h2 class="content-header-title">संस्था व्यवस्थापित करा</h2>
	        <ol class="breadcrumb">
	          <li><a onclick="windowOpen();">मुख्यपृष्ठ</a></li>
	          <li class="active">संस्था व्यवस्थापित करा</li>
	        </ol>
        <% }else{ %>
	        <h2 class="content-header-title">Manage Organization</h2>
	        <ol class="breadcrumb">
	          <li><a onclick="windowOpen();">Home</a></li>
	          <li class="active">Manage Organization</li>
	        </ol>
        
        <%} %>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3><i class="fa fa-table"></i><% if(form.getMedium().equals("mr")){ %>संस्था यादी <% }else{ %>Organization List <%} %> </h3>

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
		     
		     <% if(form.getMedium().equals("mr")){ %>
		               
	            <div class="row">    
	            <div class="col-md-12">
	            	<form id="loadFormID" action="SearchOrganizations" method="POST" onsubmit="submitForm('loadFormID', 'submitID', 'SearchOrganizations');" >
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" name="searchOrganization" class="form-control" style="width:100%;" placeholder="संस्था शोधा" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">संस्था शोधा</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllOrganizations"> <button type="button" id="viewSubmitID" class="btn btn-warning" onclick="submitForm('loadFormID', 'viewSubmitID', 'ViewAllOrganizations');" >सर्व संस्था पहा</button></a>
	                        </div>
	                     </div>
	             </form> 
	             </div>  
	             </div>  
	              <hr>
	              <!-- Search div -->
	              
	             <div class="col-md-6"> 
				 	<%
			            if(componentMsg.equals("available")){
			         %>
                  
                <div class="table-responsive" style="margin-top:15px;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">संस्थेचे नाव</th>
                      <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchOrganizationList" var="UserForm">
                    <tr>
                     	<td><s:property value="name" /></td>
						
						<td align="center">
							<s:url id="approveURL" action="RenderEditOrganization">
								<s:param name="OrganizationsID" value="%{OrganizationsID}" />
								<s:param name="searchOrganization" value="%{searchOrganization}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="संस्था एडिट  करा" title="संस्था एडिट  करा" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							
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
		       <div class="col-md-6">
		             
		      <%
		         if(componentEdit.equals("add")){
	          %>    
                <form id="validate-basic" action="AddOrganization" method="POST" onsubmit="submitForm('validate-basic', 'addSubmitID', 'AddOrganization');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="name">संस्थेचे नाव<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="name" placeholder="संस्थेचे नाव" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				<div class="form-group">
                  <label for="name">संघटना बोर्ड <span class="required">*</span></label>
                  <div>
                  	<s:radio list="#{'SSC':'एसएससी','CBSE':'सीबीएसई'}" name="board" required="required" ></s:radio>
                  </div>
                </div>
              
                <div class="form-group">
                  <label for="name">संस्था माध्यम<span class="required">*</span></label>
                  <div>
                 	 <s:radio list="#{'mr':'मराठी','en':'इंग्रजी'}" name="medium" class="sscID" ></s:radio> 
                 </div>
                </div>
                
				<div class="form-group">
                   <label for="Profile Pic">लोगो अपलोड करा<span class="required">*</span></label>
                   	<input type="file" name="logo" required="required" class="form-control">
                </div>
                
                <div class="form-group">
                   <label for="Profile Pic">बोर्ड लोगो अपलोड करा<span class="required">*</span></label>
                   	<input type="file" name="boardLogo" required="required" class="form-control">
                </div>
                
                <div class="form-group">
                  <label for="textarea-input">पत्ता</label>
                  <textarea data-required="true" data-minlength="5" name="address" id="textarea-input" cols="10" rows="2" placeholder="पत्ता" class="form-control"></textarea>
                </div>
                
                <div class="form-group">
                  <label for="name">फोन</label>
                  <input type="text" name="phone" class="form-control" placeholder="फोन" data-required="true" onKeyPress="if(this.value.length==10) return false;">
                </div>
                
             	<div class="form-group">
                  <label for="name">ई - मेल आयडी<span class="required">*</span></label>
                  <input type="email" name="email" required="required" placeholder="ईमेल आयडी" class="form-control" data-required="true" >
                </div>

				<div class="form-group">
                  	<label for="name">ईमेल पासवर्ड<span class="required">*</span></label>
                   	<input type="password" name="emailPass" required="required" class="form-control" placeholder="पासवर्ड" data-required="true">
                </div>

                <div class="form-group">
                  <label for="name">वेबसाईट</label>
                  <input type="text" name="website" class="form-control" placeholder="वेबसाईट" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="Username">टॅगलाइन</label>
                  <input type="text" name="tagline" class="form-control" placeholder="टॅगलाइन" data-required="true" >
                </div>
			
				<div class="form-group">
					<label for="Profile Pic">मुख्याध्यापकांची सही<span class="required">*</span></label>
					<s:file name="signature" class="form-control" required="required"></s:file>
				</div>
				
				<div class="form-group">
					<label for="Profile Pic">प्राचार्यांचा शिक्का<span class="required">*</span></label>
					<s:file name="seal" class="form-control" required="required"></s:file>
				</div>
				
				<div class="form-group" > 
	              	<label for="date-2">ग्रंथालय विभाग:</label>
	              	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
					       <td>ग्रंथालय नाव</td>
					       <td align="center">कृती</td>
					   </tr>
					       	
					   <tr id="libraryNameTRID">
					       <td>
					       		<input type="text" name="library" placeholder="ग्रंथालय नाव" id="libraryNameID" class="form-control" data-required="true" > 
					       </td>
					          
						   <td style="width: 10px;" align="center"> 
				       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
					       			onmouseout="this.src='images/addBill.png'" alt="ग्रंथालय ऍड करा" title="ग्रंथालय ऍड करा" onclick="addLibraryRow(libraryNameID.value);"/>
						  </td>
					  </tr>
				   </table>
              </div>
              
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="addSubmitID">संघटना सेव करा</button>
                </div>
                
              </form>
		      
		      <%
		            }else{
	          %>
	          
	          <form id="validate-basic" action="EditOrganization" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'editSubmitID', 'EditOrganization');" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="OrganizationEditList" var="concessionForm">
						  
				<input type="hidden" name="OrganizationsID" value="<s:property value="OrganizationsID" />" >
				<input type="hidden" name="searchOrganization" value="<s:property value="searchOrganization" />" >
               
                <div class="form-group">
                  <label for="name">संस्थेचे नाव<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="name" placeholder="संस्थेचे नाव" value="<s:property value="name" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				<div class="form-group">
                  <label for="name">संघटना बोर्ड <span class="required">*</span></label>
                  <div>
                  	<s:radio list="#{'SSC':'एसएससी','CBSE':'सीबीएसई'}" name="board" required="required"></s:radio>
                  </div>
                </div>
                <div class="form-group">
                  <label for="name">संस्था माध्यम<span class="required">*</span></label>
                  <div>
                 	 <s:radio list="#{'mr':'मराठी','en':'इंग्रजी'}" name="medium" class="sscID" ></s:radio> 
                 </div>
                </div>
             
				<div class="form-group"  >
                        
                        <div id="logoID1">
                         <div>
		                	<label for="Profile Pic">लोगो अपलोड करा<span class="required">*</span></label>
		                </div>
                         
                          	<input  type="text" name="logoDBName" style="width: 50%;display: inline-block;" required="required" class="form-control" value="<s:property value="logoDBName"/>" readonly="readonly">
                         
                          	<button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" onclick="logoPicShow();">लोगो अपलोड करा</button>
                          
                        </div>
             
                     <div class="form-group" id="logoID" style="display: none;">
                        <label for="Logo">लोगो अपलोड करा<span class="required">*</span></label>
                          <s:file name="logo" class="form-control" id="logoClickID"></s:file>
                     </div>
				</div>
				
				<div class="form-group"  >
                        
                        <div id="logoID2">
                         <div>
		                	<label for="boardLogo Pic">बोर्ड लोगो अपलोड करा<span class="required">*</span></label>
		                </div>
                         
                          	<input  type="text" name="boardLogoDBName" style="width: 50%;display: inline-block;" required="required" class="form-control" value="<s:property value="boardLogoDBName"/>" readonly="readonly">
                         
                          	<button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" onclick="logoPicShow1();">बोर्ड लोगो अपलोड करा</button>
                          
                        </div>
             
                     <div class="form-group" id="logoID3" style="display: none;">
                        <label for="Logo">बोर्ड लोगो अपलोड करा<span class="required">*</span></label>
                          <s:file name="boardLogo" class="form-control" id="logoClickID1"></s:file>
                     </div>
				</div>
                
                <div class="form-group">
                  <label for="textarea-input">पत्ता</label>
                  <s:textarea data-required="true" data-minlength="5" name="address" cols="10" rows="2" placeholder="पत्ता" class="form-control"></s:textarea>
                </div>
                
                <div class="form-group">
                  <label for="name">फोन</label>
                  <input type="text" name="phone" value="<s:property value="phone"/>" class="form-control" placeholder="फोन" data-required="true" onKeyPress="if(this.value.length==10) return false;">
                </div>
                
             	<div class="form-group">
                  <label for="name">ई - मेल आयडी<span class="required">*</span></label>
                  <input type="email" name="email" value="<s:property value="email"/>" required="required" placeholder="ईमेल आयडी" class="form-control" data-required="true" >
                </div>

				<div class="form-group">
                  	<label for="name">ईमेल पासवर्ड<span class="required">*</span></label>
                   	<input type="password" name="emailPass" required="required" value="<s:property value="emailPass"/>" class="form-control" placeholder="पासवर्ड" data-required="true">
                </div>
                
                <div class="form-group">
                  <label for="name">वेबसाईट</label>
                  <input type="text" name="website" value="<s:property value="website"/>" class="form-control" placeholder="वेबसाईट" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="Username">टॅगलाइन</label>
                  <input type="text" name="tagline" value="<s:property value="tagline"/>" class="form-control" placeholder="टॅगलाइन" data-required="true" >
                </div>
				
				<div class="form-group"  >
                   	<div id="signatureID1">
                         <div>
		                	<label for="Profile Pic">मुख्याध्यापकांची सही<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="signatureDBName" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="signatureDBName"/>" readonly="readonly">
                         <button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" id="signatureID1" onclick="signatureShow(this.id);">स्वाक्षरी अपलोड करा</button>
                    </div>
             		<div class="form-group" id="signatureID" style="display: none;">
                        <label for="Profile Pic">मुख्याध्यापकांची सही<span class="required">*</span></label>
                        <s:file name="signature" class="form-control" id="signatureClickID" ></s:file>
                    </div>
				</div>
				
				<div class="form-group"  >
                   	<div id="sealID1">
                         <div>
		                	<label for="Profile Pic">प्राचार्यांचा शिक्का<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="sealDBName" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="sealDBName"/>" readonly="readonly">
                         <button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" id="sealID1" onclick="sealShow(this.id);">सील अपलोड  करा</button>
                    </div>
             		<div class="form-group" id="sealID" style="display: none;">
                        <label for="Profile Pic">प्राचार्यांचा शिक्का<span class="required">*</span></label>
                        <s:file name="seal" class="form-control" id="sealClickID" ></s:file>
                    </div>
				</div>
				
				<div class="form-group" > 
	              	<label for="date-2">ग्रंथालय विभाग:</label>
	              	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
					       <td>ग्रंथालय नाव</td>
					       <td align="center">कृती</td>
					   </tr>
					       	
					   <tr id="libraryNameTRID">
					       <td>
					       		<input type="text" name="library" placeholder="ग्रंथालय नाव" id="libraryNameID" class="form-control" data-required="true" > 
					       </td>
					          
						   <td style="width: 10px;" align="center"> 
				       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
					       			onmouseout="this.src='images/addBill.png'" alt="ग्रंथालय ऍड करा" title="ग्रंथालय ऍड करा" onclick="addLibraryRow(libraryNameID.value);"/>
						  </td>
					  </tr>
					  <s:iterator value="LibraryList" var="concessionForm">
					
					     <tr id="newTRID<s:property value="libraryID" />">
					       	<td>
					       		<input type="hidden" name="editLibraryID" value="<s:property value="libraryID" />">
					       		<input type="text" name="editLibrary" placeholder="ग्रंथालय नाव" class="form-control" value="<s:property value="library" />">
					       	</td>
					      
					       	<td style="width: 10px;" align="center">
					       		<img src='images/delete.png' style='height:24px;cursor: pointer;' onclick="deleteRow1(<s:property value="libraryID" />,'newTRID<s:property value="libraryID" />');" 
									alt='डीलीट करा' title='डीलीट करा'/>
							</td>
					    </tr>
				    </s:iterator>
				   </table>
              </div>
              
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="editSubmitID" >संस्था अपडेट करा</button>
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
	            	<form id="loadFormID" action="SearchOrganizations" method="POST" onsubmit="submitForm('loadFormID', 'submitID', 'SearchOrganizations');" >
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" name="searchOrganization" class="form-control" style="width:100%;" placeholder="Search Organizations" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">Search Organization</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllOrganizations"> <button type="button" id="viewSubmitID" class="btn btn-warning" onclick="submitForm('loadFormID', 'viewSubmitID', 'ViewAllOrganizations');" >View All Organizations</button></a>
	                        </div>
	                     </div>
	             </form> 
	             </div>  
	             </div>  
	              <hr>
	              <!-- Search div -->
	              
	             <div class="col-md-6"> 
				 	<%
			            if(componentMsg.equals("available")){
			         %>
                  
                <div class="table-responsive" style="margin-top:15px;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Organization Name</th>
                      <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                  <s:iterator value="searchOrganizationList" var="UserForm">
                    <tr>
                     	<td><s:property value="name" /></td>
						
						<td align="center">
							<s:url id="approveURL" action="RenderEditOrganization">
								<s:param name="OrganizationsID" value="%{OrganizationsID}" />
								<s:param name="searchOrganization" value="%{searchOrganization}" />
							</s:url> 
							<s:a href="%{approveURL}">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Organization" title="Edit Organization" />
							</s:a>&nbsp;&nbsp;&nbsp;&nbsp;
							
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
		       <div class="col-md-6">
		             
		      <%
		         if(componentEdit.equals("add")){
	          %>    
                <form id="validate-basic" action="AddOrganization" method="POST" onsubmit="submitForm('validate-basic', 'addSubmitID', 'AddOrganization');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="name">Organization Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="name" placeholder="Organization Name" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				<div class="form-group">
                  <label for="name">Organization Board<span class="required">*</span></label>
                  <div>
                  	<s:radio list="#{'SSC':'SSC','CBSE':'CBSE'}" name="board" required="required" value=""></s:radio>
                  </div>
                </div>
              
                <div class="form-group">
                  <label for="name">Organization Medium<span class="required">*</span></label>
                  <div>
                 	 <s:radio list="#{'mr':'Marathi','en':'English'}" name="medium" class="sscID" value="" ></s:radio> 
                 </div>
                </div>
                
				<div class="form-group">
                   <label for="Profile Pic">Upload Logo<span class="required">*</span></label>
                   	<input type="file" name="logo" required="required" class="form-control">
                </div>
                
                <div class="form-group">
                   <label for="Profile Pic">Upload Board Logo<span class="required">*</span></label>
                   	<input type="file" name="boardLogo" required="required" class="form-control">
                </div>
                
                <div class="form-group">
                  <label for="textarea-input">Address</label>
                  <textarea data-required="true" data-minlength="5" name="address" id="textarea-input" cols="10" rows="2" placeholder="Address" class="form-control"></textarea>
                </div>
                
                <div class="form-group">
                  <label for="name">Phone</label>
                  <input type="text" name="phone" class="form-control" placeholder="Phone" data-required="true" onKeyPress="if(this.value.length==10) return false;">
                </div>
                
             	<div class="form-group">
                  <label for="name">Email ID<span class="required">*</span></label>
                  <input type="email" name="email" required="required" placeholder="Email Address" class="form-control" data-required="true" >
                </div>

				<div class="form-group">
                  	<label for="name">Email Password<span class="required">*</span></label>
                   	<input type="password" name="emailPass" required="required" class="form-control" placeholder="Password" data-required="true">
                </div>

                <div class="form-group">
                  <label for="name">Website</label>
                  <input type="text" name="website" class="form-control" placeholder="Website" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="Username">Tagline</label>
                  <input type="text" name="tagline" class="form-control" placeholder="Tagline" data-required="true" >
                </div>
				
				 <div class="form-group">
                  <label for="name">Sign On Report Card<span class="required">*</span></label>
                  <div>
                 	 <s:radio list="#{'principal':'Principal','sectionHead':'Section Heads'}" name="signOnRC" class="signOnRC" value="" ></s:radio> 
                 </div>
                </div>
                
				<div class="form-group">
					<label for="Profile Pic">Principal's Signature<span class="required">*</span></label>
					<s:file name="signature" class="form-control" required="required"></s:file>
				</div>
				
				<div class="form-group">
					<label for="Profile Pic">Principal's Seal<span class="required">*</span></label>
					<s:file name="seal" class="form-control" required="required"></s:file>
				</div>
				
				<div class="form-group">
					<label for="primanry head">Primary Headmistress Signature<span class="required">*</span></label>
					<s:file name="signaturePrimary" class="form-control" required="required"></s:file>
				</div>
				
				<div class="form-group">
					<label for="Profile Pic">Primary Headmistress Seal<span class="required">*</span></label>
					<s:file name="sealPrimary" class="form-control" required="required"></s:file>
				</div>
				
				<div class="form-group">
					<label for="primanry head">Secondary Headmistress Signature<span class="required">*</span></label>
					<s:file name="signatureSecondary" class="form-control" required="required"></s:file>
				</div>
				
				<div class="form-group">
					<label for="Profile Pic">Secondary Headmistress Seal<span class="required">*</span></label>
					<s:file name="sealSecondary" class="form-control" required="required"></s:file>
				</div>
				
				<div class="form-group" > 
	              	<label for="date-2">Library Section:</label>
	              	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
					       <td>Library Name</td>
					       <td align="center">Action</td>
					   </tr>
					       	
					   <tr id="libraryNameTRID">
					       <td>
					       		<input type="text" name="library" placeholder="Library Name" id="libraryNameID" class="form-control" data-required="true" > 
					       </td>
					          
						   <td style="width: 10px;" align="center"> 
				       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
					       			onmouseout="this.src='images/addBill.png'" alt="Add Library" title="Add Library" onclick="addLibraryRow(libraryNameID.value);"/>
						  </td>
					  </tr>
				   </table>
              </div>
              
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="addSubmitID">Add Organization</button>
                </div>
                
              </form>
		      
		  <% }else{  %>
	          
	          <form id="validate-basic" action="EditOrganization" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'editSubmitID', 'EditOrganization');" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="OrganizationEditList" var="concessionForm">
						  
				<input type="hidden" name="OrganizationsID" value="<s:property value="OrganizationsID" />" >
				<input type="hidden" name="searchOrganization" value="<s:property value="searchOrganization" />" >
               
                <div class="form-group">
                  <label for="name">Organization Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="name" placeholder="Organization Name" value="<s:property value="name" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				<div class="form-group">
                  <label for="name">Organization Board<span class="required">*</span></label>
                  <div>
                  	<s:radio list="#{'SSC':'SSC','CBSE':'CBSE'}" name="board" required="required"></s:radio>
                  </div>
                </div>
                <div class="form-group">
                  <label for="name">Organization Medium<span class="required">*</span></label>
                  <div>
                 	 <s:radio list="#{'mr':'Marathi','en':'English'}" name="medium" class="sscID" ></s:radio> 
                 </div>
                </div>
             
				<div class="form-group"  >
                        
                        <div id="logoID1">
                         <div>
		                	<label for="Profile Pic">Upload Logo<span class="required">*</span></label>
		                </div>
                         
                          	<input  type="text" name="logoDBName" style="width: 50%;display: inline-block;" required="required" class="form-control" value="<s:property value="logoDBName"/>" readonly="readonly">
                         
                          	<button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" onclick="logoPicShow();">Upload Logo</button>
                          
                        </div>
             
                     <div class="form-group" id="logoID" style="display: none;">
                        <label for="Logo">Upload Logo Pic<span class="required">*</span></label>
                          <s:file name="logo" class="form-control" id="logoClickID"></s:file>
                     </div>
				</div>
				
				<div class="form-group"  >
                        
                        <div id="logoID2">
                         <div>
		                	<label for="boardLogo Pic">Upload Board Logo<span class="required">*</span></label>
		                </div>
                         
                          	<input  type="text" name="boardLogoDBName" style="width: 50%;display: inline-block;" required="required" class="form-control" value="<s:property value="boardLogoDBName"/>" readonly="readonly">
                         
                          	<button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" onclick="logoPicShow1();">Upload Board Logo</button>
                          
                        </div>
             
                     <div class="form-group" id="logoID3" style="display: none;">
                        <label for="Logo">Upload Board Logo Pic<span class="required">*</span></label>
                          <s:file name="boardLogo" class="form-control" id="logoClickID1"></s:file>
                     </div>
				</div>
                
                <div class="form-group">
                  <label for="textarea-input">Address</label>
                  <s:textarea data-required="true" data-minlength="5" name="address" cols="10" rows="2" placeholder="Address" class="form-control"></s:textarea>
                </div>
                
                <div class="form-group">
                  <label for="name">Phone</label>
                  <input type="text" name="phone" value="<s:property value="phone"/>" class="form-control" placeholder="Phone" data-required="true" onKeyPress="if(this.value.length==10) return false;">
                </div>
                
             	<div class="form-group">
                  <label for="name">Email ID<span class="required">*</span></label>
                  <input type="email" name="email" value="<s:property value="email"/>" required="required" placeholder="Email Address" class="form-control" data-required="true" >
                </div>

				<div class="form-group">
                  	<label for="name">Email Password<span class="required">*</span></label>
                   	<input type="password" name="emailPass" required="required" value="<s:property value="emailPass"/>" class="form-control" placeholder="Password" data-required="true">
                </div>
                
                <div class="form-group">
                  <label for="name">Website</label>
                  <input type="text" name="website" value="<s:property value="website"/>" class="form-control" placeholder="Website" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="Username">Tagline</label>
                  <input type="text" name="tagline" value="<s:property value="tagline"/>" class="form-control" placeholder="Tagline" data-required="true" >
                </div>
				
				<div class="form-group">
                  <label for="name">Sign On Report Card<span class="required">*</span></label>
                  <div>
                 	 <s:radio list="#{'principal':'Principal','sectionHead':'Section Heads'}" name="signOnRC"></s:radio> 
                 </div>
                </div>
                
                <div class="form-group"  >
                   	<div id="signatureID1">
                         <div>
		                	<label for="Profile Pic">Principal's Signature<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="signatureDBName" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="signatureDBName"/>" readonly="readonly">
                         <button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" id = "signatureID1" onclick="signatureShow(this.id);">Upload Signature</button>
                    </div>
             		<div class="form-group" id="signatureID" style="display: none;">
                        <label for="Profile Pic">Principal's Signature<span class="required">*</span></label>
                        <s:file name="signature" class="form-control" id="signatureClickID" ></s:file>
                    </div>
				</div>
				
				<div class="form-group"  >
                   	<div id="sealID1">
                         <div>
		                	<label for="Profile Pic">Principal's Seal<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="sealDBName" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="sealDBName"/>" readonly="readonly">
                         <button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" id = "sealID1" onclick="sealShow(this.id);">Upload Seal</button>
                    </div>
             		<div class="form-group" id="sealID" style="display: none;">
                        <label for="Profile Pic">Principal's Seal<span class="required">*</span></label>
                        <s:file name="seal" class="form-control" id="sealClickID" ></s:file>
                    </div>
				</div>
                
				<div class="form-group"  >
                   	<div id="signatureID2">
                         <div>
		                	<label for="Profile Pic">Primary Headmistress Signature<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="signatureDBPrimary" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="signatureDBPrimary"/>" readonly="readonly">
                         <button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" id = "signatureID2" onclick="signatureShow(this.id);">Upload Signature</button>
                    </div>
             		<div class="form-group" id="signatureID3" style="display: none;">
                        <label for="Profile Pic">Primary Headmistress Signature<span class="required">*</span></label>
                        <s:file name="signaturePrimary" class="form-control" id="signatureClickID" ></s:file>
                    </div>
				</div>
				
				<div class="form-group"  >
                   	<div id="sealID2">
                         <div>
		                	<label for="Profile Pic">Primary Headmistress Seal<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="sealDBPrimary" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="sealDBPrimary"/>" readonly="readonly">
                         <button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" id = "sealID2" onclick="sealShow(this.id);">Upload Seal</button>
                    </div>
             		<div class="form-group" id="sealID3" style="display: none;">
                        <label for="Profile Pic">Primary Headmistress Seal<span class="required">*</span></label>
                        <s:file name="sealPrimary" class="form-control" id="sealClickID" ></s:file>
                    </div>
				</div>
				
				<div class="form-group"  >
                   	<div id="signatureID4">
                         <div>
		                	<label for="Profile Pic">Secondary Headmistress Signature<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="signatureDBSecondary" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="signatureDBSecondary"/>" readonly="readonly">
                         <button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" id = "signatureID4" onclick="signatureShow(this.id);">Upload Signature</button>
                    </div>
             		<div class="form-group" id="signatureID5" style="display: none;">
                        <label for="Profile Pic">Secondary Headmistress Signature<span class="required">*</span></label>
                        <s:file name="signatureSecondary" class="form-control" id="signatureClickID" ></s:file>
                    </div>
				</div>
				
				<div class="form-group"  >
                   	<div id="sealID4">
                         <div>
		                	<label for="Profile Pic">Secondary Headmistress Seal<span class="required">*</span></label>
		                 </div>
                         <input type="text" name="sealDBSecondary" class="form-control" style="width: 50%;display: inline-block;" value="<s:property value="sealDBSecondary"/>" readonly="readonly">
                         <button class="btn btn-default" style="width: 30%;display: inline-block;" type="button" id = "sealID4" onclick="sealShow(this.id);">Upload Seal</button>
                    </div>
             		<div class="form-group" id="sealID5" style="display: none;">
                        <label for="Profile Pic">Secondary Headmistress Seal<span class="required">*</span></label>
                        <s:file name="sealSecondary" class="form-control" id="sealClickID" ></s:file>
                    </div>
				</div>
				
				<div class="form-group" > 
	              	<label for="date-2">Library Section:</label>
	              	<table id="datatable-responsive" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
						<tr>
					       <td>Library Name</td>
					       <td align="center">Action</td>
					   </tr>
					       	
					   <tr id="libraryNameTRID">
					       <td>
					       		<input type="text" name="library" placeholder="Library Name" id="libraryNameID" class="form-control" data-required="true" > 
					       </td>
					          
						   <td style="width: 10px;" align="center"> 
				       			<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
					       			onmouseout="this.src='images/addBill.png'" alt="Add Library" title="Add Library" onclick="addLibraryRow(libraryNameID.value);"/>
						  </td>
					  </tr>
					  
					 <s:iterator value="LibraryList" var="concessionForm">
					
					     <tr id="newTRID<s:property value="libraryID" />">
					       	<td>
					       		<input type="hidden" name="editLibraryID" value="<s:property value="libraryID" />">
					       		<input type="text" name="editLibrary" placeholder="Library Name" class="form-control" value="<s:property value="library" />">
					       	</td>
					      
					       	<td style="width: 10px" align="center">
					       		<img src='images/delete.png' style='height:24px;cursor: pointer;' onclick="deleteRow1(<s:property value="libraryID" />,'newTRID<s:property value="libraryID" />');" 
									alt='Delete row' title='Delete row'/>
							</td>
					    </tr>
				    </s:iterator>
				   </table>
              </div>
              
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="editSubmitID" >Update Organization</button>
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

  <!-- Plugin JS -->
  <script src="./js/plugins/datatables/jquery.dataTables.min.js"></script>
  <script src="./js/plugins/datatables/DT_bootstrap.js"></script>
  <script src="./js/plugins/tableCheckable/jquery.tableCheckable.js"></script>
  <script src="./js/plugins/icheck/jquery.icheck.min.js"></script>

  <!-- App JS -->
  <script src="./js/target-admin.js"></script>
  
</body>
</html>
