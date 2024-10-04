<%@page import="com.kovidRMS.daoInf.LoginDAOInf"%>
     <%@page import="com.kovidRMS.daoImpl.LoginDAOImpl"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="com.kovidRMS.form.LoginForm"%>
	<%@page import="java.util.List"%>
 	<%@page import="java.util.HashMap"%>
 	
<!DOCTYPE html>
<html class="no-js"> 
<head>

 	<%
     	LoginForm loginform = (LoginForm) session.getAttribute("USER");
     
    	String componentMsg = (String) request.getAttribute("componentMsg");
    	if(componentMsg == null || componentMsg == ""){
    		componentMsg = "dummy";
    	}
    	
    	String componentEdit = (String) request.getAttribute("componentEdit");
    	if(componentEdit == null || componentEdit == ""){
    		componentEdit = "add";
    	}
   	
    	LoginDAOInf daoInf = new LoginDAOImpl();
	
		HashMap<Integer, String> StandardList = daoInf.getStandard(loginform.getOrganizationID());
		
		List<LoginForm> searchAcademicYearList = (List<LoginForm>) request.getAttribute("searchAcademicYearList");
		
		List<LoginForm> attendanceList = (List<LoginForm>) request.getAttribute("attendanceList");
		
		List<LoginForm> attendance1List = (List<LoginForm>) request.getAttribute("attendance1List");
		
	 %>
	
	 <title><% if(loginform.getMedium().equals("mr")){ %>शैक्षणिक वर्ष व्यवस्थापन  <% }else{ %>Manage Academic Year - SkoolUp <% } %></title>
	
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
  <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">

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
              event.preventDefault();IN
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
	
	function editUserList(){
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
		  document.location="EditUserList";
	}
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
		
        document.location="RenderAcademicYear";
     }
	
	function windowOpen2(){
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="ViewAllAcademicYear";
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
    
		function rejectRequest(url) {
			if (confirm("Are you sure you want to delete user?")) {
				document.location = url;
			}
		}
		
    	function activateStudent(url){
			if(confirm("This will disable the earlier Academic Year and activate the present Academic Year. Are you sure you want to proceed?")){
				document.location = url;
			}
    	}
    </script>

<script type="text/javascript">
		$(window).load( function() {
			
			$(".monthClass").prop('readonly', true);
			
		});
	</script>
	
<script type="text/javascript">

	function addAttendance(Term){
		
		if(Term ==  "Term I"){
			
			$("#myModal").modal('show');
			$("#termID").val(Term);
			
		}else if(Term ==  "Term II"){
		
			$("#myModal3").modal('show');
			$("#termID").val(Term);
			
		}
	}
	
	function editAttendance(editTerm, academiYear){
		
		$("#myModal1").modal('show');
		$("#editTermID").val(editTerm);
		$("#editAcademiYearID").val(academiYear);
	
	}
	
	function editAttendance1(editTerm1, academiYear){
		
		$("#myModal2").modal('show');
		$("#editTermID1").val(editTerm1);
		$("#editAcademiYearID1").val(academiYear);
	
	}
	</script>
	
<script type="text/javascript">
	var AttendanceCounter = 1;
	function addAttendanceRow(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID, termID){
		var term = $("#"+termID).val();
		
		if(Month == "-1"){
			alert("Please select Month.");
		}else if(Workdays == ""){
			alert("Please add Working days.");
		}else{
			
			addAttendanceRow1(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID, term);
		}
	}

	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	function checkAttendanceAvailability(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID, termID, academicYearID, mode) {
	
		
	var term = $("#"+termID).val();
	var academicYear = $("#"+academicYearID).val();
	
	console.log("term is: "+term+"--"+academicYear);
	
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
				var array = JSON.parse(xmlhttp.responseText);
	
				var check = 0;
				var error ="";
				
				for ( var i = 0; i < array.Release.length; i++) {
	
					check = array.Release[i].check;
					error = array.Release[i].errMsg;
				}
				
				if(check == 1){
					console.log("inside if loop");
					
					alert("Attendance for this month & standard already exist. Please new details.")
					
				}else{
					
					if(mode =="edit"){
						addEditAttendanceRow1(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID);
					}else{
						addEdit1AttendanceRow1(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID);
					}
					
				}
				
			}
		};
		xmlhttp.open("GET", "CheckAttendanceAvailability?month="
				+ Month+"&term="+ term+"&standardID="+ standardID+"&academicYearID="+academicYear, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
	}

	function addAttendanceRow1(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID, term){
		
		var trID = "newExamTRID"+AttendanceCounter;
		
		var trTag = "";
		
		var stringToAppend = "*"+term + "$" + standardID + "$" + Month + "$" + Workdays;
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+Standard+"<input type='hidden' name='newStandard' value='"+Standard+"'></td>"
			  + "<td style='text-align:center;'>"+Month+"<input type='hidden' name='newMonth' value='"+Month+"'></td>"
			  + "<td style='text-align:center;'>"+Workdays+"<input type='hidden' name='newWorkdays' value='"+Workdays+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeExamTR(\"" + stringToAppend + "\",\"" + trID + "\");'/></td>"
			  + "</tr>";
		
		$(trTag).insertAfter($('#'+standardTRID));
		
		//appending values to term hiddenID 
		$('#addAttendanceID').val($('#addAttendanceID').val()+stringToAppend);
		
		AttendanceCounter++;
	
		$('#'+monthID).val("-1");
		$('#'+workingDaysID).val("");
		
	}
	
	function removeExamTR(stringToBeRemoved, trID){
		if(confirm("Are you sure you want to delete this row?")){
			
			var configText = $('#addAttendanceID').val();
			var newValue = configText.replace(stringToBeRemoved,'');
	    	
	    	//Updating new value to dummySettingTextID field
	    	$('#addAttendanceID').val(newValue);
	    	
			$("#"+trID+"").remove();
		}
	}

/*Edit Attendance */
 
 var AttendanceCounter1 = 1;
function addEditAttendanceRow(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID, termID, academicYearID){
	
	if(Month == "-1"){
		alert("Please select Month.");
	}else if(Workdays == ""){
		alert("Please add Working days.");
	}else{
		
		checkAttendanceAvailability(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID, termID, academicYearID, "edit");
		
	}
}

function addEditAttendanceRow1(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID){
	
	var trID = "newExamTRID"+AttendanceCounter1;
	
	var trTag = "";
	
	trTag += "<tr id='"+trID+"'>"
		  + "<td style='text-align:center;'>"+Standard+"<input type='hidden' name='newstandardID' value='"+standardID+"'><input type='hidden' name='newStandard' value='"+Standard+"'></td>"
		  + "<td style='text-align:center;'>"+Month+"<input type='hidden' name='newMonth' value='"+Month+"'></td>"
		  + "<td style='text-align:center;'>"+Workdays+"<input type='hidden' name='newWorkdays' value='"+Workdays+"'></td>"
		  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeExamTR1(\"" + trID + "\");'/></td>"
		  + "</tr>";
	
	$(trTag).insertAfter($('#'+standardTRID));
	
	AttendanceCounter1++;

	$('#'+monthID).val("-1");
	$('#'+workingDaysID).val("");
	
}

function removeExamTR1(trID){
	if(confirm("Are you sure you want to delete this row?")){
		
		$("#"+trID+"").remove();
	}
}

var AttendanceCounter2 = 1;
function addEdit1AttendanceRow(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID, termID, academicYearID){
	
	if(Month == "-1"){
		alert("Please select Month.");
	}else if(Workdays == ""){
		alert("Please add Working days.");
	}else{
		
		checkAttendanceAvailability(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID, termID, academicYearID, "edit1");
	}
}

function addEdit1AttendanceRow1(Standard, Month, Workdays, standardTRID, monthID, workingDaysID, standardID){
	
	var trID = "newExamTRID"+AttendanceCounter2;
	
	var trTag = "";
	
	trTag += "<tr id='"+trID+"'>"
		  + "<td style='text-align:center;'>"+Standard+"<input type='hidden' name='newstandardID' value='"+standardID+"'><input type='hidden' name='newStandard' value='"+Standard+"'></td>"
		  + "<td style='text-align:center;'>"+Month+"<input type='hidden' name='newMonth' value='"+Month+"'></td>"
		  + "<td style='text-align:center;'>"+Workdays+"<input type='hidden' name='newWorkdays' value='"+Workdays+"'></td>"
		  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeExamTR1(\"" + trID + "\");'/></td>"
		  + "</tr>";
	
	$(trTag).insertAfter($('#'+standardTRID));
	
	AttendanceCounter2++;

	$('#'+monthID).val("-1");
	$('#'+workingDaysID).val("");
	
}

function removeExamTR1(trID){
	if(confirm("Are you sure you want to delete this row?")){
	
		$("#"+trID+"").remove();
	}
}
</script>
	
<script type="text/javascript" charset="UTF-8">
/*For deleting the row from database with academicYearID*/
	
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
			xmlhttp.open("GET", "DeleteAttendanceRow?deleteID="
					+ deleteID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
    </script>
    
    <!-- Ends -->

</head>

<body>

<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">
<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<!-- Modal to add Standard attendance For Term-I  -->
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<title><% if(loginform.getMedium().equals("mr")){ %>उपस्थिती विभाग <% }else{ %>Attendance section <% } %></title>				
			</div>
			<div class="modal-body">
				<input type="hidden" name="newTerm" id ="termID">
				
				<table border="1" id="tableTRID">
					<tr>
					<% 	if(loginform.getMedium().equals("mr")){ %>
							<td style="width: 20%;text-align: center;">इयत्ता</td>
					     	<td style="text-align: center;">महिना</td>
					       	<td style="text-align: center;">कामाचे दिवस</td>
					       	<td style="text-align: center;">कृती</td>
					       	
					<%	}else{ %>
							<td style="width: 20%;text-align: center;">Standard</td>
					     	<td style="text-align: center;">Month</td>
					       	<td style="text-align: center;">Working Days</td>
					       	<td style="text-align: center;">Action</td>
					<%  } %>
					
					</tr>
					
					<% 
						for(Integer StandardFormName : StandardList.keySet()){
		         	%>
		         	<tr id="standardTRID<%=StandardFormName%>">
		         	<td style="text-align: center;">
		         		<option id = "standardID<%=StandardFormName%>" value="<%=StandardList.get(StandardFormName)%>"><%=StandardList.get(StandardFormName)%></option>
						<input type="hidden" name="standardID" id="standardTRID<%=StandardFormName%>">
					</td>
					<td style=" text-align: center;padding: 10px;">
				       <select id="monthID<%=StandardFormName%>" name="" class="form-control" style="width: 100px;">
				       <% if(loginform.getMedium().equals("mr")){ %>
				       		<option value="-1">महिना निवडा</option>
					       	<option value="January">जानेवारी</option>
				       		<option value="February">फेब्रुवारी</option>
				       		<option value="March">मार्च</option>
				       		<option value="April">एप्रिल</option>
				       		<option value="May">मे</option>
				       		<option value="June">जून</option>
				       		<option value="July">जुलै</option>
				       		<option value="August">ऑगस्ट</option>
				       		<option value="September">सप्टेंबर</option>
				       		<option value="October">ऑक्टोबर</option>
				       		<option value="November">नोव्हेंबर</option>
				       		<option value="December">डिसेंबर</option>
				       <% }else{ %>
					   		<option value="-1">Select Month</option>
					       	<option value="January">January</option>
				       		<option value="February">February</option>
				       		<option value="March">March</option>
				       		<option value="April">April</option>
				       		<option value="May">May</option>
				       		<option value="June">June</option>
				       		<option value="July">July</option>
				       		<option value="August">August</option>
				       		<option value="September">September</option>
				       		<option value="October">October</option>
				       		<option value="November">November</option>
				       		<option value="December">December</option>
				       <% } %>
					       	
				       	</select>
			       	</td>
				    <td style="text-align: center;padding: 10px;">
					     <div class="form-group" >
					   <% if(loginform.getMedium().equals("mr")){ %>
				       		<input type="number" id="workingDaysID<%=StandardFormName%>" name="" class="form-control" placeholder="कामाचे दिवस" data-required="true">
				       <% }else{ %>
					   		<input type="number" id="workingDaysID<%=StandardFormName%>" name="" class="form-control" placeholder="Working Days" data-required="true">
				       <% } %>
					         
						</div>	
					</td>
					      
					<td style="width: 10%; text-align: center;"> 
					   <% if(loginform.getMedium().equals("mr")){ %>
				       		
							<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       		onmouseout="this.src='images/addBill.png'" alt="उपस्थिती जोडा" title="उपस्थिती जोडा" 
				      		onclick="addAttendanceRow(standardID<%=StandardFormName%>.value, monthID<%=StandardFormName%>.value, workingDaysID<%=StandardFormName%>.value,
				       		 'standardTRID<%=StandardFormName%>','monthID<%=StandardFormName%>','workingDaysID<%=StandardFormName%>', '<%=StandardFormName%>', 'termID');"/>
				       		 
				       <% }else{ %>
					   	
					   		<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       		onmouseout="this.src='images/addBill.png'" alt="Add Attendance" title="Add Attendance" 
				      		onclick="addAttendanceRow(standardID<%=StandardFormName%>.value, monthID<%=StandardFormName%>.value, workingDaysID<%=StandardFormName%>.value,
				       		 'standardTRID<%=StandardFormName%>','monthID<%=StandardFormName%>','workingDaysID<%=StandardFormName%>', '<%=StandardFormName%>', 'termID');"/>
				       		 
				       <% } %>
				       
			       		
					</td>
				</tr>
					
				<% } %> 
				</table>
			</div>
			<div class="modal-footer"style="text-align: center;">
				<button type="button" data-dismiss="modal"> <% if(loginform.getMedium().equals("mr")){ %> बंद        <% }else{ %> Close  <% } %></button>
			</div>
		</div>
	</div>
	</div>
	<!-- End -->

<!-- Modal to add Standard attendance For Term-II  -->
	<div id="myModal3" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<title><% if(loginform.getMedium().equals("mr")){ %>उपस्थिती विभाग <% }else{ %>Attendance section <% } %></title>		
			</div>
			<div class="modal-body">
				<input type="hidden" name="newTerm" id ="termID">
				
				<table border="1" id="tableTRID">
					<tr>
						<% 	if(loginform.getMedium().equals("mr")){ %>
							<td style="width: 20%;text-align: center;">इयत्ता</td>
					     	<td style="text-align: center;">महिना</td>
					       	<td style="text-align: center;">कामाचे दिवस</td>
					       	<td style="text-align: center;">कृती</td>
					       	
					<%	}else{ %>
							<td style="width: 20%;text-align: center;">Standard</td>
					     	<td style="text-align: center;">Month</td>
					       	<td style="text-align: center;">Working Days</td>
					       	<td style="text-align: center;">Action</td>
					<%  } %>
					</tr>
					
					<% 
						for(Integer StandardFormName : StandardList.keySet()){
		         	%>
		         	<tr id="standardTRID<%=StandardFormName%>">
		         	<td style="text-align: center;">
		         		<option id = "standardID<%=StandardFormName%>" value="<%=StandardList.get(StandardFormName)%>"><%=StandardList.get(StandardFormName)%></option>
						<input type="hidden" name="standardID" id="standardTRID<%=StandardFormName%>">
					</td>
					<td style=" text-align: center;padding: 10px;">
				       <select id="monthID<%=StandardFormName%>" name="" class="form-control" style="width: 100px;">
					        <% if(loginform.getMedium().equals("mr")){ %>
				       		<option value="-1">महिना निवडा</option>
					       	<option value="January">जानेवारी</option>
				       		<option value="February">फेब्रुवारी</option>
				       		<option value="March">मार्च</option>
				       		<option value="April">एप्रिल</option>
				       		<option value="May">मे</option>
				       		<option value="June">जून</option>
				       		<option value="July">जुलै</option>
				       		<option value="August">ऑगस्ट</option>
				       		<option value="September">सप्टेंबर</option>
				       		<option value="October">ऑक्टोबर</option>
				       		<option value="November">नोव्हेंबर</option>
				       		<option value="December">डिसेंबर</option>
				       <% }else{ %>
					   		<option value="-1">Select Month</option>
					       	<option value="January">January</option>
				       		<option value="February">February</option>
				       		<option value="March">March</option>
				       		<option value="April">April</option>
				       		<option value="May">May</option>
				       		<option value="June">June</option>
				       		<option value="July">July</option>
				       		<option value="August">August</option>
				       		<option value="September">September</option>
				       		<option value="October">October</option>
				       		<option value="November">November</option>
				       		<option value="December">December</option>
				       <% } %>
				       	</select>
			       	</td>
				    <td style="text-align: center;padding: 10px;">
					     <div class="form-group" >
					     
					     <% if(loginform.getMedium().equals("mr")){ %>
				       		<input type="number" id="workingDaysID<%=StandardFormName%>" name="" class="form-control" placeholder="कामाचे दिवस" data-required="true">
				       	 <% }else{ %>
					   		<input type="number" id="workingDaysID<%=StandardFormName%>" name="" class="form-control" placeholder="Working Days" data-required="true">
				         <% } %>
				       
					   </div>	
					</td>
					      
					<td style="width: 10%; text-align: center;"> 
			       		
			       		<% if(loginform.getMedium().equals("mr")){ %>
				       		
				       		<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       		onmouseout="this.src='images/addBill.png'" alt="उपस्थिती जोडा" title="उपस्थिती जोडा" 
				       		onclick="addAttendanceRow(standardID<%=StandardFormName%>.value, monthID<%=StandardFormName%>.value, workingDaysID<%=StandardFormName%>.value,
				       		 'standardTRID<%=StandardFormName%>','monthID<%=StandardFormName%>','workingDaysID<%=StandardFormName%>', '<%=StandardFormName%>', 'termID');"/>
				        <% }else{ %>
					   	
					   		<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       		onmouseout="this.src='images/addBill.png'" alt="Add Attendance" title="Add Attendance" 
				       		onclick="addAttendanceRow(standardID<%=StandardFormName%>.value, monthID<%=StandardFormName%>.value, workingDaysID<%=StandardFormName%>.value,
				       		 'standardTRID<%=StandardFormName%>','monthID<%=StandardFormName%>','workingDaysID<%=StandardFormName%>', '<%=StandardFormName%>', 'termID');"/>
				       	<% } %>
				       
					</td>
				</tr>
					
				<% } %>
				</table>
			</div>
			<div class="modal-footer"style="text-align: center;">
				<button type="button" data-dismiss="modal"><% if(loginform.getMedium().equals("mr")){ %> बंद        <% }else{ %> Close  <% } %></button>
			</div>
		</div>
	</div>
	</div>
	<!-- End -->

 <div id="myModal1" class="modal fade" tabindex="-1" role="dialog">
<form id="validate-basic" action="EditAttendance" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'EditAttendance');" enctype="multipart/form-data" class="form parsley-form"> 
	<div class="modal-dialog modal-md">
	
		<div class="modal-content">
			<div class="modal-header">
				<% if(loginform.getMedium().equals("mr")){ %>उपस्थिती विभाग <% }else{ %>Attendance section <% } %>				
			</div>
			<div class="modal-body">
				<input type="hidden" name="editTerm" id="editTermID">
				<input type="hidden" name="academicYearID" id="editAcademiYearID">
				<table border="1" id="tableTRID">
					<tr>
						<% 	if(loginform.getMedium().equals("mr")){ %>
							<td style="width: 20%;text-align: center;">इयत्ता</td>
					     	<td style="text-align: center;">महिना</td>
					       	<td style="text-align: center;">कामाचे दिवस</td>
					       	<td style="text-align: center;">कृती</td>
					       	
					<%	}else{ %>
							<td style="width: 20%;text-align: center;">Standard</td>
					     	<td style="text-align: center;">Month</td>
					       	<td style="text-align: center;">Working Days</td>
					       	<td style="text-align: center;">Action</td>
					<%  } %>
					</tr>
					
					<% 
						for(Integer StandardFormName : StandardList.keySet()){
		         	%>
		         	<tr id="editstandardTRID<%=StandardFormName%>">
		         	
		         	<td style="text-align: center;">
		         		<option id = "editstandardID<%=StandardFormName%>" value="<%=StandardList.get(StandardFormName)%>"><%=StandardList.get(StandardFormName)%></option>
					</td>
					<td style=" text-align: center;padding: 10px;">
				       <select id="editmonthID<%=StandardFormName%>" name="" class="form-control" style="width: 100px;">
					       <% if(loginform.getMedium().equals("mr")){ %>
				       		<option value="-1">महिना निवडा</option>
					       	<option value="January">जानेवारी</option>
				       		<option value="February">फेब्रुवारी</option>
				       		<option value="March">मार्च</option>
				       		<option value="April">एप्रिल</option>
				       		<option value="May">मे</option>
				       		<option value="June">जून</option>
				       		<option value="July">जुलै</option>
				       		<option value="August">ऑगस्ट</option>
				       		<option value="September">सप्टेंबर</option>
				       		<option value="October">ऑक्टोबर</option>
				       		<option value="November">नोव्हेंबर</option>
				       		<option value="December">डिसेंबर</option>
				       <% }else{ %>
					   		<option value="-1">Select Month</option>
					       	<option value="January">January</option>
				       		<option value="February">February</option>
				       		<option value="March">March</option>
				       		<option value="April">April</option>
				       		<option value="May">May</option>
				       		<option value="June">June</option>
				       		<option value="July">July</option>
				       		<option value="August">August</option>
				       		<option value="September">September</option>
				       		<option value="October">October</option>
				       		<option value="November">November</option>
				       		<option value="December">December</option>
				       <% } %>
				       	</select>
			       	</td>
				    <td style="text-align: center;padding: 10px;">
					     <div class="form-group" >
					     <% if(loginform.getMedium().equals("mr")){ %>
					     	<input type="number" id="editworkingDaysID<%=StandardFormName%>" name="" class="form-control" placeholder="कामाचे दिवस" data-required="true">
				       	<% }else{ %>
					   		 <input type="number" id="editworkingDaysID<%=StandardFormName%>" name="" class="form-control" placeholder="Working Days" data-required="true">
				         <% } %>
				           
						</div>	
					</td>
					      
					<td style="width: 10%; text-align: center;"> 
					<% if(loginform.getMedium().equals("mr")){ %>
			       		<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       		onmouseout="this.src='images/addBill.png'" alt="उपस्थिती जोडा" title="उपस्थिती जोडा" 
				       		onclick="addEditAttendanceRow(editstandardID<%=StandardFormName%>.value, editmonthID<%=StandardFormName%>.value, editworkingDaysID<%=StandardFormName%>.value,
				       		 'editstandardTRID<%=StandardFormName%>','editmonthID<%=StandardFormName%>','editworkingDaysID<%=StandardFormName%>', '<%=StandardFormName%>', 'editTermID', 'editAcademiYearID');"/>
				    <% }else{ %>
				    	<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       		onmouseout="this.src='images/addBill.png'" alt="Add Attendance" title="Add Attendance" 
				       		onclick="addEditAttendanceRow(editstandardID<%=StandardFormName%>.value, editmonthID<%=StandardFormName%>.value, editworkingDaysID<%=StandardFormName%>.value,
				       		 'editstandardTRID<%=StandardFormName%>','editmonthID<%=StandardFormName%>','editworkingDaysID<%=StandardFormName%>', '<%=StandardFormName%>', 'editTermID', 'editAcademiYearID');"/>
				    <% } %>
					</td>
				</tr>
					
				<% } %>
				
				<% for(LoginForm form:attendanceList){ %>
			     
			     	<tr id="newTRID<%=form.getAttendanceID()%>">
			       		
			       		<td style=" text-align: center;">
			       			<input type="hidden" name="editTermID" value="<%=form.getAttendanceID()%>"><%=form.getStandard()%>
			       		</td>
			       		<td style=" text-align: center;padding: 10px;"><%=form.getMonth() %></td>
			       		<td style=" text-align: center;padding: 10px;">
			       			<input type="number" name="editworkingDaysID" class="form-control" value="<%=form.getWorkingDays()%>">
			       		</td>
			       	</tr>
			    <% } %>
				</table>
			</div>
			
			<div class="modal-footer" style="text-align: center;">
				<button type="submit" class="btn btn-success" id="submitID"><% if(loginform.getMedium().equals("mr")){ %> एडिट  <% }else{ %>Edit <% } %></button>
				<button type="button" data-dismiss="modal"><% if(loginform.getMedium().equals("mr")){ %>बंद <%  }else{ %>Close <% } %></button>
			</div>
		</div>
	</div>
	
	</form>
	</div> 
	
	
	<div id="myModal2" class="modal fade" tabindex="-1" role="dialog">
	<form id="validate-basic" action="EditAttendance" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'EditAttendance');" enctype="multipart/form-data" class="form parsley-form"> 
	<div class="modal-dialog modal-md">
	
		<div class="modal-content">
			<div class="modal-header">
				<% if(loginform.getMedium().equals("mr")){ %>उपस्थिती विभाग <% }else{ %>Attendance section <% } %>		
			</div>
			<div class="modal-body">
				<input type="hidden" name="editTerm" id ="editTermID1">
				<input type="hidden" name="academicYearID" id="editAcademiYearID1">
				<table border="1" id="tableTRID">
					<tr>
						<% 	if(loginform.getMedium().equals("mr")){ %>
							<td style="width: 20%;text-align: center;">इयत्ता</td>
					     	<td style="text-align: center;">महिना</td>
					       	<td style="text-align: center;">कामाचे दिवस</td>
					       	<td style="text-align: center;">कृती</td>
					       	
					<%	}else{ %>
							<td style="width: 20%;text-align: center;">Standard</td>
					     	<td style="text-align: center;">Month</td>
					       	<td style="text-align: center;">Working Days</td>
					       	<td style="text-align: center;">Action</td>
					<%  } %>
					</tr>
					
					<% 
						for(Integer StandardFormName : StandardList.keySet()){
		         	%>
		         	<tr id="edit1standardTRID<%=StandardFormName%>">
		         	
		         	<td style="text-align: center;">
		         		<option id = "edit1standardID<%=StandardFormName%>" value="<%=StandardList.get(StandardFormName)%>"><%=StandardList.get(StandardFormName)%></option>
						
					</td>
					<td style=" text-align: center;padding: 10px;">
				       <select id="edit1monthID<%=StandardFormName%>" name="" class="form-control" style="width: 100px;">
					   <% if(loginform.getMedium().equals("mr")){ %>
				       		<option value="-1">महिना निवडा</option>
					       	<option value="January">जानेवारी</option>
				       		<option value="February">फेब्रुवारी</option>
				       		<option value="March">मार्च</option>
				       		<option value="April">एप्रिल</option>
				       		<option value="May">मे</option>
				       		<option value="June">जून</option>
				       		<option value="July">जुलै</option>
				       		<option value="August">ऑगस्ट</option>
				       		<option value="September">सप्टेंबर</option>
				       		<option value="October">ऑक्टोबर</option>
				       		<option value="November">नोव्हेंबर</option>
				       		<option value="December">डिसेंबर</option>
				       <% }else{ %>
					       	<option value="-1">Select Month</option>
					       	<option value="January">January</option>
				       		<option value="February">February</option>
				       		<option value="March">March</option>
				       		<option value="April">April</option>
				       		<option value="May">May</option>
				       		<option value="June">June</option>
				       		<option value="July">July</option>
				       		<option value="August">August</option>
				       		<option value="September">September</option>
				       		<option value="October">October</option>
				       		<option value="November">November</option>
				       		<option value="December">December</option>
				       	<% } %>
				       	</select>
			       	</td>
				    <td style="text-align: center;padding: 10px;">
					     <div class="form-group" >
					       <% if(loginform.getMedium().equals("mr")){ %>
					     	<input type="number" id="edit1workingDaysID<%=StandardFormName%>" name="" class="form-control" placeholder="कामाचे दिवस" data-required="true">
				       	<% }else{ %>
					   		 <input type="number" id="edit1workingDaysID<%=StandardFormName%>" name="" class="form-control" placeholder="Working Days" data-required="true">
				         <% } %>
					        
						</div>	
					</td>
					      
					<td style="width: 10%; text-align: center;"> 
					<% if(loginform.getMedium().equals("mr")){ %>
						<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       		onmouseout="this.src='images/addBill.png'" alt="उपस्थिती जोडा" title="उपस्थिती जोडा" 
				       		onclick="addEdit1AttendanceRow(edit1standardID<%=StandardFormName%>.value, edit1monthID<%=StandardFormName%>.value, edit1workingDaysID<%=StandardFormName%>.value,
				       		 'edit1standardTRID<%=StandardFormName%>','edit1monthID<%=StandardFormName%>','edit1workingDaysID<%=StandardFormName%>', '<%=StandardFormName%>', 'editTermID1', 'editAcademiYearID1');"/>
					<% }else{ %>
					
						<img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" onmouseover="this.src='images/addBill1.png'" 
				       		onmouseout="this.src='images/addBill.png'" alt="Add Attendance" title="Add Attendance" 
				       		onclick="addEdit1AttendanceRow(edit1standardID<%=StandardFormName%>.value, edit1monthID<%=StandardFormName%>.value, edit1workingDaysID<%=StandardFormName%>.value,
				       		 'edit1standardTRID<%=StandardFormName%>','edit1monthID<%=StandardFormName%>','edit1workingDaysID<%=StandardFormName%>', '<%=StandardFormName%>', 'editTermID1', 'editAcademiYearID1');"/>
				       		 
					<% } %>
			       		
					</td>
				</tr>
					
				<% } 
				
				for(LoginForm form:attendance1List){ %>
			     
			     <tr id="newTRID<%=form.getAttendanceID()%>">
			       		
			       		<td style=" text-align: center;"><input type="hidden" name="editTermID" value="<%=form.getAttendanceID()%>"><%=form.getStandard()%></td>
			       		
			       		<td style=" text-align: center;padding: 10px;"> <%= form.getMonth() %> </td>
			       		
			       		<td style=" text-align: center;padding: 10px;">
			       			<input type="number" name="editworkingDaysID" class="form-control" value="<%=form.getWorkingDays()%>">
			       		</td>
			       	</tr>
			   <% } %>
				</table>
			</div>
			
			<div class="modal-footer" style="text-align: center;">
				<button type="submit" class="btn btn-success" id="submitID" ><% if(loginform.getMedium().equals("mr")){ %> एडिट  <% }else{ %>Edit <% } %></button>
				
				<button type="button" data-dismiss="modal"><% if(loginform.getMedium().equals("mr")){ %>बंद <%  }else{ %>Close <% } %></button>
			</div>
		</div>
	</div>
	
	</form>
	</div> 

<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
     
        <h2 class="content-header-title"><% if(loginform.getMedium().equals("mr")){ %>शैक्षणिक वर्ष व्यवस्थापन  <%  }else{ %>Manage Academic Year <% } %></h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();"><% if(loginform.getMedium().equals("mr")){ %>मुख्यपृष्ठ <%  }else{ %>Manage Academic Year <% } %>Home</a></li>
          <li class="active"><% if(loginform.getMedium().equals("mr")){ %>शैक्षणिक वर्ष व्यवस्थापन  <%  }else{ %>Manage Academic Year <% } %></li>
        </ol>
     
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3><i class="fa fa-table"></i><% if(loginform.getMedium().equals("mr")){ %>शैक्षणिक वर्ष   <% }else{ %>Academic Year <% } %> </h3>

            </div> <!-- /.portlet-header -->

            <div class="portlet-content"> 
            		<div>
		              	<s:if test="hasActionErrors()">
							<center>
								<font style="color: red; font-size:16px;" id="errorFontID"><s:actionerror /></font>
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
	            	<form id="validate-basic" action="SearchAcademicYear" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'SearchAcademicYear');">
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" name="searchAcademicYear" class="form-control" style="width:100%;" placeholder="शैक्षणिक वर्ष शोधा" required="required">
	                       </div>
	                       <div class="col-md-2">
	                          <button type="submit" class="btn btn-success" id="submitID">शैक्षणिक वर्ष शोधा</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllAcademicYear"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('validate-basic', 'viewSubmitID', 'ViewAllAcademicYear');">सर्व शैक्षणिक वर्षे पहा</button></a>
	                        </div>
	                   </div>
	             </form> 
	             </div>  
	             </div>  
	             <hr>
	              <!-- Search div -->
	              
	             <div class="col-md-7"> 
				 	<%
			            if(componentMsg.equals("available")){
			         %>
                  
                <div class="table-responsive" style="margin-top:15px;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">शैक्षणिक वर्षाचे नाव</th>
                   	  <th data-sortable="true">क्रियाकलाप स्थिती</th>
                   	  <th data-sortable="true">सक्रिय करा</th>
                      <th style="text-align:center;">कृती</th>
                  	</tr>
                  </thead>
                  <tbody>
                 
                 <% for(LoginForm form:searchAcademicYearList){ %>
                    <tr>
                     	<td><%=form.getYearName() %></td>
                     	
                     	<td><%=form.getActivityStatus() %></td>
						
						<% if(form.getActivityStatus().equals("DRAFT")){ %>
							<td align="center">
							
								<a href="javascript:activateStudent('ActivateAcademicYear?AcademicYearID=<%=form.getAcademicYearID() %>')">
									<img src="images/Ok.png" style="height:24px;" onmouseover="this.src='images/Ok.png'"
									onmouseout="this.src='images/Ok.png'" alt="शैक्षणिक वर्ष ऍक्टिव्ह करा" title="शैक्षणिक वर्ष ऍक्टिव्ह करा" />
								</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						<%}else{ %>
						
							<td></td>
						<%} %>
						
						<td align="center">
							
							<a href="RenderEditAcademicYear?AcademicYearID=<%=form.getAcademicYearID() %>&searchAcademicYear=<%=form.getSearchAcademicYear() %>">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
								onmouseout="this.src='images/user_1.png'" alt="शैक्षणिक वर्ष एडिट करा" title="शैक्षणिक वर्ष एडिट करा" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
							
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
		       <div class="col-md-5">
		              <%
		              	if(componentEdit.equals("add")){
		              %>    
                <form id="validate-basic" action="AddAcademicYear" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'addSubmitID', 'AddAcademicYear');" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="yearName">शैक्षणिक वर्षाचे नाव<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="yearName" placeholder="शैक्षणिक वर्षाचे नाव" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				 <div class="form-group">
                  <label for="date-2">सुरुवातीची तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="startDate" name="startDate" class="form-control" required="required" placeholder="प्रारंभ तारीख(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">टर्म १ शेवटची तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                     <input type="text" id="termIendDate" name="termIendDate" required="required" class="form-control" placeholder="Term I End Date(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">शेवटची तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="endDate" name="endDate" required="required" class="form-control" placeholder="शेवटची तारीख(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">वय मोजण्याची तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="ageFromDateID" name="ageFromDate" required="required" class="form-control" placeholder="वय मोजण्याची तारीख(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                
	                <a href="javascript:addAttendance('Term I');">टर्म १</a>
					
	            </div>
	            
	            <div class="form-group">
                
	                <a href="javascript:addAttendance('Term II');">टर्म  २</a>
					<input type="hidden" name="newAddAttendance" id="addAttendanceID">
	            </div>
                 
               <div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="addSubmitID">सेव करा</button>
                </div>
                
              </form>
		      
		      <% }else{  %>
	          
	          <form id="validate-basic" action="EditAcademicYear" method="POST" onsubmit="submitForm('validate-basic', 'editSubmitID', 'EditAcademicYear');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="AcademicYearEditList" var="concessionForm">
						  
				<input type="hidden" name="AcademicYearID" value="<s:property value="AcademicYearID" />" >
				<input type="hidden" name="searchAcademicYear" value="<s:property value="searchAcademicYear" />" >
               
                <div class="form-group">
                  <label for="yearName">शैक्षणिक वर्षाचे नाव<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="yearName" placeholder="शैक्षणिक वर्षाचे नाव" value="<s:property value="yearName" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				 <div class="form-group">
                  <label for="date-2">सुरुवातीची तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="startDate" name="startDate" value="<s:property value="startDate"/>" class="form-control" required="required" placeholder="प्रारंभ तारीख(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                 <div class="form-group">
                  <label for="date-2">टर्म १ शेवटची तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                     <input type="text" id="termIendDate" name="termIendDate" value="<s:property value="termIendDate"/>" required="required" class="form-control" placeholder="टर्म १ शेवटची तारीख(dd/mm/yyyy)" data-required="true"> 
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">शेवटची तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="endDate" name="endDate" value="<s:property value="endDate"/>" required="required" class="form-control" placeholder="शेवटची तारीख(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">वय मोजण्याची तारीख<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="ageFromDateID" name="ageFromDate" value="<s:property value="ageFromDate"/>" required="required" class="form-control" placeholder="वय मोजण्याची तारीख(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                
	                <a href="javascript:editAttendance('Term I', '<s:property value="AcademicYearID" />');">टर्म १</a>
					
	            </div>
	            
	            <div class="form-group">
                
	                <a href="javascript:editAttendance1('Term II', '<s:property value="AcademicYearID" />');">टर्म २</a>
					<input type="hidden" name="editAddAttendance" id="editAttendanceID">
	            </div>
               
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">रद्द करा</button>
                  <button type="submit" class="btn btn-success" id="editSubmitID">अपडेट करा</button>
                </div>
                </s:iterator>
              </form>
              
             <% } %>
              
             </div>
             
      <% }else{ %>
               
           <div class="row">    
	            <div class="col-md-12">
	            	<form id="validate-basic" action="SearchAcademicYear" method="POST" onsubmit="submitForm('validate-basic', 'submitID', 'SearchAcademicYear');" >
	            		<div class="row">
	                       <div class="col-md-3">
	                         <input type="text" name="searchAcademicYear" class="form-control" style="width:100%;" placeholder="Search Academic Year" required="required">
	                       </div>
	                       <div class="col-md-3">
	                          <button type="submit" class="btn btn-success" id="submitID" >Search Academic Year</button>
	                        </div>
	                        <div class="col-md-2">
	                          <a href="ViewAllAcademicYear"> <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="submitForm('validate-basic', 'viewSubmitID', 'ViewAllAcademicYear');">View All Academic Years</button></a>
	                        </div>
	                   </div>
	             </form> 
	             </div>  
	             </div>  
	             <hr>
	              <!-- Search div -->
	              
	             <div class="col-md-7"> 
				 	<%
			            if(componentMsg.equals("available")){
			         %>
                  
                <div class="table-responsive" style="margin-top:15px;">
              	<table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                   	  <th data-sortable="true">Academic Year Name</th>
                   	  <th data-sortable="true">Activity Status</th>
                   	  <th data-sortable="true">Activate</th>
                      <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                 
                 <% for(LoginForm form:searchAcademicYearList){ %>
                    <tr>
                     	<td><%=form.getYearName() %></td>
                     	
                     	<td><%=form.getActivityStatus() %></td>
						
						<% if(form.getActivityStatus().equals("DRAFT")){ %>
							<td align="center">
							
								<a href="javascript:activateStudent('ActivateAcademicYear?AcademicYearID=<%=form.getAcademicYearID() %>')">
									<img src="images/Ok.png" style="height:24px;" onmouseover="this.src='images/Ok.png'"
									onmouseout="this.src='images/Ok.png'" alt="Activate Academic Year" title="Activate Academic Year" />
								</a>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
						<%}else{ %>
						
							<td></td>
						<% } %>
						
						<td align="center">
							<a href="RenderEditAcademicYear?AcademicYearID=<%=form.getAcademicYearID() %>&searchAcademicYear=<%=form.getSearchAcademicYear() %>">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
								onmouseout="this.src='images/user_1.png'" alt="Edit Academic Year" title="Edit Academic Year" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
							
						</td>
                       </tr>
                       <%} %>
                  
                   </tbody>
                </table>
                </div>
                
                 <% }  %>
                    
		       </div>
		      <!-- ENds -->
               
               <!-- Add and Update Div -->
		       <div class="col-md-5">
		              <%
		              	if(componentEdit.equals("add")){
		              %>    
                <form id="validate-basic" action="AddAcademicYear" method="POST" data-validate="parsley" onsubmit="submitForm('validate-basic', 'addSubmitID', 'AddAcademicYear');" enctype="multipart/form-data" class="form parsley-form">

                <div class="form-group">
                  <label for="yearName">Academic Year Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="yearName" placeholder="Academic Year Name" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				 <div class="form-group">
                  <label for="date-2">Start Date<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="startDate" name="startDate" class="form-control" required="required" placeholder="Start Date(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">Term I End Date<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                     <input type="text" id="termIendDate" name="termIendDate" required="required" class="form-control" placeholder="Term I End Date(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">End Date<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="endDate" name="endDate" required="required" class="form-control" placeholder="End Date(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">Calculate Age From<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="ageFromDateID" name="ageFromDate" required="required" class="form-control" placeholder="Calculate Age From(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group row">
                	<div class="col-md-5">
                		<label>Attendance section:</label>
                	</div>
                	<div class="col-md-2">
                		<a href="javascript:addAttendance('Term I');">Term I</a>
                	</div>
                	<div class="col-md-3">
	                	<a href="javascript:addAttendance('Term II');">Term II</a>
						<input type="hidden" name="newAddAttendance" id="addAttendanceID">
					</div>
				</div>
	            
               <div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="addSubmitID" >Add Academic Year</button>
                </div>
                
              </form>
		      
		      <% }else{  %>
	          
	          <form id="validate-basic" action="EditAcademicYear" method="POST" onsubmit="submitForm('validate-basic', 'editSubmitID', 'EditAcademicYear');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

				<s:iterator value="AcademicYearEditList" var="concessionForm">
						  
				<input type="hidden" name="AcademicYearID" value="<s:property value="AcademicYearID" />" >
				<input type="hidden" name="searchAcademicYear" value="<s:property value="searchAcademicYear" />" >
               
                <div class="form-group">
                  <label for="yearName">Academic Year Name<span class="required">*</span></label>
                  <div>
                  	<input type="text" name="yearName" placeholder="Academic Year Name" value="<s:property value="yearName" />" required="required" class="form-control" data-required="true" >
                 </div>
                </div>
				
				 <div class="form-group">
                  <label for="date-2">Start Date<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="startDate" name="startDate" value="<s:property value="startDate"/>" class="form-control" required="required" placeholder="Start Date(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                 <div class="form-group">
                  <label for="date-2">Term I End Date<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                     <input type="text" id="termIendDate" name="termIendDate" value="<s:property value="termIendDate"/>" required="required" class="form-control" placeholder="Term I End Date(dd/mm/yyyy)" data-required="true"> 
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">End Date<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="endDate" name="endDate" value="<s:property value="endDate"/>" required="required" class="form-control" placeholder="End Date(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                  <label for="date-2">Calculate Age From<span class="required">*</span></label>

                  <div class="input-group date ui-datepicker">
                      <input type="text" id="ageFromDateID" name="ageFromDate" value="<s:property value="ageFromDate"/>" required="required" class="form-control" placeholder="Calculate Age From(dd/mm/yyyy)" data-required="true">
                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  </div>
                </div>
                
                <div class="form-group">
                
	                <a href="javascript:editAttendance('Term I', '<s:property value="AcademicYearID" />');">Term I</a>
					
	            </div>
	            
	            <div class="form-group">
                
	                <a href="javascript:editAttendance1('Term II', '<s:property value="AcademicYearID" />');">Term II</a>
					<input type="hidden" name="editAddAttendance" id="editAttendanceID">
	            </div>
               
				<div class="form-group" align="center">
				  <button type="reset" onclick="windowOpen2();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="editSubmitID">Update Academic Year</button>
                </div>
                </s:iterator>
              </form>
              
             <% } %>
             
          <% } %>     
             </div>
                      
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
  <script src="./js/plugins/datepicker/bootstrap-datepicker.js"></script>

  <!-- App JS -->
  <script src="./js/target-admin.js"></script>
  
</body>
</html>
