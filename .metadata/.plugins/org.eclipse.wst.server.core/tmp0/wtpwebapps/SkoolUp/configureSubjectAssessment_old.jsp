<%@page import="javassist.expr.NewArray"%>
<%@page import="com.kovidRMS.daoImpl.ConfigurationDAOImpl"%>
<%@page import="com.kovidRMS.daoInf.ConfigurationDAOInf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
     <%@page import="com.kovidRMS.form.LoginForm"%>
     <%@page import="java.util.HashMap"%>
     <%@page import="com.kovidRMS.daoInf.StuduntDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.StudentDAOImpl"%>
     <%@page import="com.kovidRMS.form.StudentForm"%>
      <%@page import="com.kovidRMS.form.ConfigurationForm"%>
     <%@page import="java.util.List"%>
     
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Configure Subject Assessment - KovidRMS Admin</title>

  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="./css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  <link rel="stylesheet" href="./css/bootstrap.min.css">
    <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">
   <script type="text/javascript" src="https://use.fontawesome.com/4836edb23b.js"></script>

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
    if ($(window).scrollTop()  >= 650) {
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
    
    /* When Gradebased radio button has value 1 then disable the maxmark and scaleto columns*/
    function disable(GradeID,MaxMarkID,ScaleID){
    	
	    if (GradeID == "0") {
	    	
	    	$("#"+MaxMarkID).attr("readonly",false);
	    	
	    	$("#"+ScaleID).attr("readonly",false);
	    	
	      
	    } else {
			$("#"+MaxMarkID).attr("readonly",true);
	    	
	    	$("#"+ScaleID).attr("readonly",true);
	    	
	    }
    }
    
    /* End */
    
</script>

	<%
		LoginDAOInf daoinf = new LoginDAOImpl();
    	HashMap<Integer, String> StandardList = daoinf.getStandard();
    	
    	ConfigurationDAOInf daoInf1 = new ConfigurationDAOImpl();
    	
    	int AcademicYearID = 0;
		AcademicYearID = daoInf1.retriveAcademicYearID();
		
    	HashMap<Integer, String> ExamList = daoInf1.getExamList(AcademicYearID);
    	
    	LoginForm form = new LoginForm();
    	
    %>
    
    <%
		String loadSubjectSearch = (String) request.getAttribute("loadSubjectSearch");
	
		if(loadSubjectSearch == null || loadSubjectSearch == ""){
			loadSubjectSearch = "dummy";
		}
	%>
	 
<script type="text/javascript">
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
		//alert("hiii");	
	});
</script>

 <script type="text/javascript">
      function windowOpen(){
        document.location="welcome.jsp";
      }
   
      
      function retrieveDivision(standardID){
			
			if(standardID == "-1"){
				alert("No standard is selected. Please select standard.");
				
				var array_element = "<select name='divisionID' id='' class='form-control'"+
				"> <option value='-1'>Select Division</option></select>";
				
				document.getElementById("stdDivID").innerHTML = array_element;
				
				/* For Subject...*/
				var trID = "";
				trID += "<tr style='font-size: 14px;'>"
				    + "<td style='text-align:center'>"+subjectList+"</td>"
				  	+ "</tr>";
				
				document.getElementById("subDivID").innerHTML = array_element1;
				
				$("#newTBodyID").html("<tr id='newTRID'></tr>");
				
			}else{
				$("#newTBodyID").html("<tr id='newTRID'></tr>");
				
				retrieveDivision1(standardID);
			}
			
		}
	
		function retrieveDivision1(standardID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "<select name='divisionID' class='form-control'"+
						"> <option value='-1'>Select Division</option>";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
	
							array_element += "<option value='"+array.Release[i].divisionID+"'>"+array.Release[i].division+"</option>";
						}
	
						array_element += " </select>";
						
						if(check == 0){
							
							alert("No division found");
							
							var array_element = "<select name='divisionID' id='' class='form-control'"+
							"> <option value='-1'>Select Division</option></select>";
							
							document.getElementById("stdDivID").innerHTML = array_element;
							
						}else{
							
							document.getElementById("stdDivID").innerHTML = array_element;	
														
						}
						
						retrieveSubjectByStandardID(standardID);
				}
			};
			xmlhttp.open("GET", "RetrieveDivisionListForStandard?standardID="
					+ standardID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
		</script>
		
		<script type="text/javascript">
		
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		/* For retrieving Subjects list with there standardID */
		
		function retrieveSubjectByStandardID(standardID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var counter = 0;
						
						var subjectList = "";
						
						var TRTag = "";
						
						for ( var i = 0; i < array.Release.length; i++) {
							subjectList = array.Release[i].subjectList;
						}
						
						if(subjectList == null || subjectList == "" || subjectList == "undefined"){
							
							alert("No subject found. Please add subject to current standard.");
							
						}else{
							
							if(subjectList.includes(",")){
								
								var subArr = subjectList.split(",");
								
								for(var j = 0; j < subArr.length; j++){
									counter++;
									var trID = "newSubAssmntTRID"+counter;
									TRTag += "<tr id='"+trID+"' style='font-size: 14px;'>"
											+"<td><input type='text' class='form-control' name='newSubjectName' readonly='readonly' value='"+subArr[j]+"'></td>"
											+"<td><input type='radio' name='myRadio"+counter+"' value='1' onchange='disable(this.value,\"MaxMarkID"+counter+"\",\"ScaleID"+counter+"\");' onclick='storeValue(this.value,\"newGradeBaseID"+counter+"\");'>Yes&nbsp;<input type='radio' name='myRadio"+counter+"' value='0' onchange='disable(this.value,\"MaxMarkID"+counter+"\",\"ScaleID"+counter+"\");' onclick='storeValue(this.value,\"newGradeBaseID"+counter+"\");'>No"
											+"<input type='hidden' id='newGradeBaseID"+counter+"' name='newGradeBase'></td>"
											+"<td><input type='text' class='form-control' id='MaxMarkID"+counter+"' name='newMaximumMark' ></td>"
											+"<td><input type='text' class='form-control' id='ScaleID"+counter+"' name='newScaleTo' ></td>"
											+"<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeNewTR(\"" + trID + "\");'/></td>"
											+"</tr>"
									
								}
								
							}else{
								counter++;
								var trID = "newSubAssmntTRID"+counter;
								TRTag += "<tr id='"+trID+"' style='font-size: 14px;'>"
										+"<td><input type='text' class='form-control' name='newSubjectName' readonly='readonly' value='"+subjectList+"'></td>"
										+"<td><input type='radio' name='myRadio"+counter+"' value='1' onchange='disable(this.value,\"MaxMarkID"+counter+"\",\"ScaleID"+counter+"\");' onclick='storeValue(this.value,\"newGradeBaseID"+counter+"\");'>Yes&nbsp;<input type='radio' name='myRadio"+counter+"' value='0' onchange='disable(this.value,\"MaxMarkID"+counter+"\",\"ScaleID"+counter+"\");' onclick='storeValue(this.value,\"newGradeBaseID"+counter+"\");'>No"
										+"<input type='hidden' id='newGradeBaseID"+counter+"' name='newGradeBase'></td>"
										+"<td><input type='text' class='form-control' id='MaxMarkID"+counter+"' name='newMaximumMark' ></td>"
										+"<td><input type='text' class='form-control' id='ScaleID"+counter+"' name='newScaleTo' ></td>"
										+"<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeNewTR(\"" + trID + "\");'/></td>"
										+"</tr>"
							}
							
							$(TRTag).insertAfter($("#newTRID"));
						}
						
						
				}
			};
			xmlhttp.open("GET", "RetrieveSubjectListForStandard?standardID="
					+ standardID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
		function removeNewTR(trID){
			if(confirm("Are you sure you want to remove this row?")){	
				$("#"+trID).remove();
			}
		}
		
		function storeValue(gradeBase, hiddenInputID){
			$("#"+hiddenInputID).val(gradeBase);
		}
  </script>
  
  <script type="text/javascript">
  
  	function retrieveEditSubjectAssessmentDetails(standardID, divisionID, examID){
  		if(examID == "-1"){
  			$("#editTBodyID").html("<tr id='editTRID'></tr>");
  			alert("No exam type is selected. Please select exam type.");
  		}else if(standardID == "-1"){
  			$("#editTBodyID").html("<tr id='editTRID'></tr>");
  			alert("No standard is selected. Please select standard.");
  		}else if(divisionID == "-1"){
  			$("#editTBodyID").html("<tr id='editTRID'></tr>");
  			alert("No division is selected. Please select division.");
  		}else{
  			$("#editTBodyID").html("<tr id='editTRID'></tr>");
  			retrieveEditSubjectAssessmentDetails1(standardID, divisionID, examID);
  		}
  	}
  	
  	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
  
	function retrieveEditSubjectAssessmentDetails1(standardID, divisionID, examID) {
		
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				var array = JSON.parse(xmlhttp.responseText);
				
					var counter1 = 0;
					
					var check = 0;
					
					var subjectList = "";
					
					var TRTag = "";
					
					for ( var i = 0; i < array.Release.length; i++) {
						
						counter1++;
						
						check = array.Release[i].check;
						var totalMarks = array.Release[i].totalMarks;
						var subAssmntID = array.Release[i].subAssmntID;
						var subject = array.Release[i].subject;
						var scaleTo = array.Release[i].scaleTo;
						var gradeBased = array.Release[i].gradeBased;
						
						var trID = "editSubAssmntTRID"+subAssmntID;
						TRTag += "<tr id='"+trID+"' style='font-size: 14px;'><input type='hidden' readonly='readonly' name='editSubjectAssessmentID' value='"+subAssmntID+"'>"
								+"<td><input type='text' class='form-control' readonly='readonly' name='editSubjectName' value='"+subject+"'></td>";
								if(gradeBased == "0"){
									TRTag += "<td><input type='radio' name='myRadio1"+subAssmntID+"' value='1' onchange='restoreValue(this.value,\"editMaxMarkID"+subAssmntID+"\",\"editSceleToID"+subAssmntID+"\");'  onclick='storeValue(this.value,\"editGradeBaseID"+subAssmntID+"\");'>Yes&nbsp;<input type='radio' checked='checked' onchange='restoreValue(this.value,\"editMaxMarkID"+subAssmntID+"\",\"editSceleToID"+subAssmntID+"\");' name='myRadio1"+subAssmntID+"' value='0' onclick='storeValue(this.value,\"editGradeBaseID"+subAssmntID+"\");'>No"
										  +  "<input type='hidden' id='editGradeBaseID"+subAssmntID+"' value='"+gradeBased+"' name='editGradeBase'></td>"
										  +  "<td><input type='text' class='form-control' id='editMaxMarkID"+subAssmntID+"' name='editMaximumMark' value='"+totalMarks+"'></td>"
										  +  "<td><input type='text' class='form-control' id='editSceleToID"+subAssmntID+"' name='editScaleTo' value='"+scaleTo+"'></td>";
								}else{
									TRTag += "<td><input type='radio' name='myRadio1"+subAssmntID+"' value='1' onchange='restoreValue(this.value,\"editMaxMarkID"+subAssmntID+"\",\"editSceleToID"+subAssmntID+"\");' checked='checked' onclick='storeValue(this.value,\"editGradeBaseID"+subAssmntID+"\");'>Yes&nbsp;<input type='radio' name='myRadio1"+subAssmntID+"' value='0' onchange='restoreValue(this.value,\"editMaxMarkID"+subAssmntID+"\",\"editSceleToID"+subAssmntID+"\");' onclick='storeValue(this.value,\"editGradeBaseID"+subAssmntID+"\");'>No"
										  +  "<input type='hidden' id='editGradeBaseID"+subAssmntID+"' value='"+gradeBased+"' name='editGradeBase'></td>"
										  +  "<td><input type='text' class='form-control' readonly='readonly' id='editMaxMarkID"+subAssmntID+"' name='editMaximumMark' value='"+totalMarks+"'></td>"
										  +  "<td><input type='text' class='form-control' readonly='readonly' id='editSceleToID"+subAssmntID+"' name='editScaleTo' value='"+scaleTo+"'></td>";
								}
								//+"<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeNewTR(\"" + trID + "\");'/></td>"
								TRTag +="<td align='center'>NA</td>"
								+"</tr>";
					}
					
					if(check == 0){
						
						alert("No details found.");
						
						$("#editTBodyID").html("<tr id='editTRID'></tr>");
						
					}else{
						console.log(TRTag);
						$(TRTag).insertAfter($("#editTRID"));
					}
					
					
			}
		};
		xmlhttp.open("GET", "RetrieveSubjectAssessmentDetails?standardID="
				+ standardID+"&divisionID="+divisionID+"&ExaminationID="+examID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}
	
	
	function restoreValue(radioVal, maxMarkID, scaleToID){
		if(radioVal == "0"){
			$("#"+maxMarkID).attr("readonly",false);
	    	
	    	$("#"+scaleToID).attr("readonly",false);
		}else{
			$("#"+maxMarkID).attr("readonly",true);
	    	
	    	$("#"+scaleToID).attr("readonly",true);
	    	
			$("#"+maxMarkID).val("0");
	    	
	    	$("#"+scaleToID).val("0");
		}
	}
	
  </script>
  
  
    
</head>

<body>
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

      

      <div class="content-header">
        <h2 class="content-header-title">Configure Subject Assessment</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Subject Assessment</li>
        </ol>
      </div> <!-- /.content-header -->

      

      <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                Subject Assessment Details
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
			
              <form id="validate-basic" action="ConfigureSubjectAssessment" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
				
				 <div class="row">
                
				  	<div class="col-md-2">
						<s:select list="ExamList" headerKey="-1" value="-1" id="examID" headerValue="Select Exam"  name="ExaminationID" ></s:select>
					</div>
					
					<div class="col-md-2">
						<s:select list="StandardList" headerKey="-1" value="-1" headerValue="Select Standard"  name="standardID" id="SID" onchange="retrieveDivision(this.value);"></s:select>
					</div>
					
					<div class="col-md-2" >
			             <select name="divisionID" id="stdDivID">
			               	<option value="-1">Select Division</option>
			             </select>
		            </div>
		            
					<div class="col-md-2">
						<button type="button" class="btn btn-warning" onclick="retrieveEditSubjectAssessmentDetails(SID.value, stdDivID.value, examID.value)">Fetch</button>
					</div> 
				</div>
			
			<hr>
			
			<div class="tab-pane fade in active">
				
				<%
					if(loadSubjectSearch.equals("Enabled")){
				%>
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <th>Subject</th>
	                          
	                          <th>Grade Based</th>
	                          
	                          <th>Maximum Marks</th>
	                          
	                          <th>Scale to</th>
	                          
	                          <th>Action</th>
	                        </tr>
	                      </thead>
	                      
	                      <tbody id="newTBodyID">
	                      	 <s:iterator value="SubjectListByStandardID" >
	                  	  		<tr id="newTRID"></tr>
	                   		</s:iterator>
	                      
	                      </tbody>
	                      <tbody id="editTBodyID">
	                       	<s:iterator value="subjectAssessmentList" >
	                      		<tr id="editTRID"></tr>
	                      	</s:iterator>
	                      </tbody>
	                      
	                    </table>
		                
			        </div>
			        <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success">Save</button>
                </div>
			       <%
						}
			       %>  
			       </form>
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
  
</body>
</html>
