<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib uri="/struts-tags" prefix="s" %>
 	<%@page import="java.util.List"%>
  <%@page import="com.kovidRMS.form.*"%>
  <%@page import="com.kovidRMS.daoImpl.*"%>
    <%@page import="com.kovidRMS.daoInf.*"%>
  
<!DOCTYPE html>
<html lang="en">

<head>
 
 <title>Set Election - SkoolUp</title>
 
  <!-- Favicon -->
 	<link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Oswald:400,300,700">
  <link rel="stylesheet" href="font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="./js/libs/css/ui-lightness/jquery-ui-1.9.2.custom.min.css">
  
   <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">
   

  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/morris/morris.css">
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">
  <link rel="stylesheet" href="./js/plugins/select2/select2.css">
  <link rel="stylesheet" href="./js/plugins/fullcalendar/fullcalendar.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
  
 <link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" /> 
 <link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />

	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LibraryDAOInf daoInf = new LibraryDAOImpl();
	
		String AcademicYearListDetails = (String) request.getAttribute("AcademicYearListDetails");
		
		if(AcademicYearListDetails == null || AcademicYearListDetails == ""){
			AcademicYearListDetails = "dummy";
		}
		
		String loadAcademicYear = (String) request.getAttribute("loadAcademicYear");
		
		if(loadAcademicYear == null || loadAcademicYear == ""){
			loadAcademicYear = "dummy";
		}
		
		List<LibraryForm> AcademicYearDetailsList = (List<LibraryForm>) request.getAttribute("AcademicYearDetailsList");
	
		String VotingStatus = (String) request.getAttribute("VotingStatus");
		
		String ActiveAcademicYear = (String) request.getAttribute("ActiveAcademicYear");
		
		if(ActiveAcademicYear == null || ActiveAcademicYear == ""){
			ActiveAcademicYear = "dummy";
		}
		
		List<LibraryForm> HeadGirlList = (List<LibraryForm>) request.getAttribute("HeadGirlList");
		
		List<LibraryForm> HeadBoyList = (List<LibraryForm>) request.getAttribute("HeadBoyList");
		
		List<LibraryForm> RedList = (List<LibraryForm>) request.getAttribute("RedList");
		
		List<LibraryForm> BlueList = (List<LibraryForm>) request.getAttribute("BlueList");
		
		List<LibraryForm> GreenList = (List<LibraryForm>) request.getAttribute("GreenList");
		
		List<LibraryForm> YellowList = (List<LibraryForm>) request.getAttribute("YellowList");
		
	%>
</head>

<script type="text/javascript">
	$(document).ready(function(){
		$('#administrationLiID').addClass("active");
		//alert("hiii");	
	});

	function showElectionDetails(){
	
			$("#AcademicYearDivID").show();
			$("#submitFormDivID").show();
	}
	
	/* For configuring HeadGirls */
	function addConfiguration(Value){
		    
		if(Value=="HeadGirl"){
			$("#HeadGirlNominationsID").show();
			$("#HeadBoyNominationsID").hide();
			$("#HouseCaptainRedDivID").hide();
			$("#HouseCaptainBlueDivID").hide();
			$("#HouseCaptainGreenDivID").hide();
			$("#HouseCaptainYellowDivID").hide();
			
		}else if(Value=="HeadBoy"){
			$("#HeadBoyNominationsID").show();
			$("#HeadGirlNominationsID").hide();
			$("#HouseCaptainRedDivID").hide();
			$("#HouseCaptainBlueDivID").hide();
			$("#HouseCaptainGreenDivID").hide();
			$("#HouseCaptainYellowDivID").hide();
			
		}else if(Value=="Red"){
			$("#HouseCaptainRedDivID").show();
			$("#HeadGirlNominationsID").hide();
			$("#HeadBoyNominationsID").hide();
			$("#HouseCaptainBlueDivID").hide();
			$("#HouseCaptainGreenDivID").hide();
			$("#HouseCaptainYellowDivID").hide();
			
		}else if(Value=="Blue"){
			$("#HouseCaptainBlueDivID").show();
			$("#HeadGirlNominationsID").hide();
			$("#HeadBoyNominationsID").hide();
			$("#HouseCaptainRedDivID").hide();
			$("#HouseCaptainGreenDivID").hide();
			$("#HouseCaptainYellowDivID").hide();
			
		}else if(Value=="Green"){
			$("#HouseCaptainGreenDivID").show();
			$("#HeadGirlNominationsID").hide();
			$("#HeadBoyNominationsID").hide();
			$("#HouseCaptainRedDivID").hide();
			$("#HouseCaptainBlueDivID").hide();
			$("#HouseCaptainYellowDivID").hide();
			
		}else if(Value=="Yellow"){
			$("#HouseCaptainYellowDivID").show();
			$("#HeadGirlNominationsID").hide();
			$("#HeadBoyNominationsID").hide();
			$("#HouseCaptainRedDivID").hide();
			$("#HouseCaptainBlueDivID").hide();
			$("#HouseCaptainGreenDivID").hide();
			
		}
  	}
  	
	
</script>

<script type="text/javascript">

function editReportsConfiguration(Value){
	     
	if(Value=="HeadGirl"){
		$("#HeadGirlResultsID").show();
		$("#HeadBoyResultsID").hide();
		$("#HouseCaptainRedResultsID").hide();
		$("#HouseCaptainBlueResultsID").hide();
		$("#HouseCaptainGreenResultsID").hide();
		$("#HouseCaptainYellowResultsID").hide();
		
		$("#HouseCaptainBlueDivID1").hide();
		$("#HouseCaptainRedDivID1").hide();
		$("#HeadGirlNominationsID1").hide();
		$("#HeadBoyNominationsID1").hide();
		$("#HouseCaptainGreenDivID1").hide();
		$("#HouseCaptainYellowDivID1").hide();
		
	}else if(Value=="HeadBoy"){
		$("#HeadBoyResultsID").show();
		$("#HeadGirlResultsID").hide();
		$("#HouseCaptainRedResultsID").hide();
		$("#HouseCaptainBlueResultsID").hide();
		$("#HouseCaptainGreenResultsID").hide();
		$("#HouseCaptainYellowResultsID").hide();
		
		$("#HouseCaptainBlueDivID1").hide();
		$("#HouseCaptainRedDivID1").hide();
		$("#HeadGirlNominationsID1").hide();
		$("#HeadBoyNominationsID1").hide();
		$("#HouseCaptainGreenDivID1").hide();
		$("#HouseCaptainYellowDivID1").hide();
		
	}else if(Value=="Red"){
		$("#HouseCaptainRedResultsID").show();
		$("#HeadGirlResultsID").hide();
		$("#HeadBoyResultsID").hide();
		$("#HouseCaptainBlueResultsID").hide();
		$("#HouseCaptainGreenResultsID").hide();
		$("#HouseCaptainYellowResultsID").hide();
		
		$("#HouseCaptainBlueDivID1").hide();
		$("#HouseCaptainRedDivID1").hide();
		$("#HeadGirlNominationsID1").hide();
		$("#HeadBoyNominationsID1").hide();
		$("#HouseCaptainGreenDivID1").hide();
		$("#HouseCaptainYellowDivID1").hide();
		
	}else if(Value=="Blue"){
		$("#HouseCaptainBlueResultsID").show();
		$("#HeadGirlResultsID").hide();
		$("#HeadBoyResultsID").hide();
		$("#HouseCaptainRedResultsID").hide();
		$("#HouseCaptainGreenResultsID").hide();
		$("#HouseCaptainYellowResultsID").hide();
		
		$("#HouseCaptainBlueDivID1").hide();
		$("#HouseCaptainRedDivID1").hide();
		$("#HeadGirlNominationsID1").hide();
		$("#HeadBoyNominationsID1").hide();
		$("#HouseCaptainGreenDivID1").hide();
		$("#HouseCaptainYellowDivID1").hide();
		
	}else if(Value=="Green"){
		$("#HouseCaptainGreenResultsID").show();
		$("#HeadGirlResultsID").hide();
		$("#HeadBoyResultsID").hide();
		$("#HouseCaptainRedResultsID").hide();
		$("#HouseCaptainBlueResultsID").hide();
		$("#HouseCaptainYellowResultsID").hide();
		
		$("#HouseCaptainBlueDivID1").hide();
		$("#HouseCaptainRedDivID1").hide();
		$("#HeadGirlNominationsID1").hide();
		$("#HeadBoyNominationsID1").hide();
		$("#HouseCaptainGreenDivID1").hide();
		$("#HouseCaptainYellowDivID1").hide();
		
	}else if(Value=="Yellow"){
		$("#HouseCaptainYellowResultsID").show();
		$("#HeadGirlResultsID").hide();
		$("#HeadBoyResultsID").hide();
		$("#HouseCaptainRedResultsID").hide();
		$("#HouseCaptainBlueResultsID").hide();
		$("#HouseCaptainGreenResultsID").hide();
		
		$("#HouseCaptainBlueDivID1").hide();
		$("#HouseCaptainRedDivID1").hide();
		$("#HeadGirlNominationsID1").hide();
		$("#HeadBoyNominationsID1").hide();
		$("#HouseCaptainGreenDivID1").hide();
		$("#HouseCaptainYellowDivID1").hide();
	}
}
	
</script>

<script type="text/javascript">

var xmlhttp;
if (window.XMLHttpRequest) {
	xmlhttp = new XMLHttpRequest();
} else {
	xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
}

function editHeadGirlConfiguration(academicYearID) {

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			
			var array = JSON.parse(xmlhttp.responseText);
			var trTag="";
			var check = 0;
			
			
			for ( var i = 0; i < array.Release.length; i++) {

				var headGirlName = array.Release[i].name;
				var headGirlID = array.Release[i].headGirlID;
				var candidateApproved = array.Release[i].candidateApproved;
				
				var hiddenID = "typeHeadGirlID"+headGirlID;
				
				var checkboxHiddenID = "checkboxHeadGirlID"+headGirlID;
				
				var trID = "trID"+headGirlID;
					
				check = array.Release[i].check;
				
				 trTag += "<tr id="+trID+"><td><input type='hidden' name='updateNameType' value='HeadGirl'><input type='hidden' name='houseColorID' value="+headGirlID+">"+headGirlName+"</td>";
				
					if(candidateApproved=="Yes"){
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='Yes' id='"+hiddenID+"'><input type='checkbox' checked='checked' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}else{
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='No' id='"+hiddenID+"'><input type='checkbox' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}
					
					trTag += "<td align='center'><img src='images/delete_icon_1.png' style='height:20px; cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeHeadGirlTR(\"" + headGirlID + "\", \"" + trID + "\");'/></td>";
					trTag += "</tr>";
					
			}
			
			if(check == 0){
				
			
				$("#HeadGirlNominationsID1").show();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainRedDivID1").hide();
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
			}else{
				
				$("#HeadGirlNominationsID1").show();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainRedDivID1").hide();
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
				$(trTag).insertAfter($('#HeadGirlTRID1'));
				
			}
		
		}
			
	};
	xmlhttp.open("GET", "RetrieveHeadGirlDetails?academicYearID="
			+ academicYearID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>

<script type="text/javascript">
    
    	function removeHeadGirlTR(headGirlID, TRID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeHeadGirlTR1(headGirlID, TRID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeHeadGirlTR1(headGirlID, TRID){ 
	    	
	    	xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//alert(AYClassID);
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
						}
	
						
						if(check == 0){
							alert("Exception occurred while removing row. Please check server logs for more details.");
						}else{
							$("#"+TRID+"").remove();
							alert("Row removed successfully.");
						}
						
				}
			};
			xmlhttp.open("GET", "RemoveHeadGirlRow?headGirlID="
					+ headGirlID, true);
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

function editHeadBoyConfiguration(academicYearID) {

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			var trTag="";
			var check = 0;
			
			for ( var i = 0; i < array.Release.length; i++) {

				var headBoyName = array.Release[i].name;
				var headBoyID = array.Release[i].headBoyID;
				var candidateApproved = array.Release[i].candidateApproved;
				
				var hiddenID = "typeHeadBoyID"+headBoyID;
				
				var checkboxHiddenID = "checkboxHeadBoyID"+headBoyID;
				
				var trID = "trID"+headBoyID;
				
				check = array.Release[i].check;
				
				 trTag += "<tr id="+trID+"><td><input type='hidden' name='updateNameType' value='HeadBoy'><input type='hidden' name='houseColorID' value="+headBoyID+">"+headBoyName+"</td>";
					
				 	if(candidateApproved=="Yes"){
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='Yes' id='"+hiddenID+"'><input type='checkbox' checked='checked' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}else{
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='No' id='"+hiddenID+"'><input type='checkbox' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}
					
					trTag += "<td align='center'><img src='images/delete_icon_1.png' style='height:20px; cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeHeadBoyTR(\"" + headBoyID + "\", \"" + trID + "\" );'/></td>";
					trTag += "</tr>";
				
			}
			
			if(check == 0){
				
				$("#HeadBoyNominationsID1").show();
				$("#HeadGirlNominationsID1").hide();
				$("#HouseCaptainRedDivID1").hide();
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
			}else{
				
				$("#HeadBoyNominationsID1").show();
				$("#HeadGirlNominationsID1").hide();
				$("#HouseCaptainRedDivID1").hide();
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
				$(trTag).insertAfter($('#HeadBoyTRID1'));
			}
		
		}
			
	};
	xmlhttp.open("GET", "RetrieveHeadBoyDetails?academicYearID="
			+ academicYearID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>

<script type="text/javascript">
    
    	function removeHeadBoyTR(headBoyID, TRID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeHeadBoyTR1(headBoyID, TRID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeHeadBoyTR(headBoyID, TRID){ 
	    	
	    	xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//alert(AYClassID);
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
						}
	
						
						if(check == 0){
							alert("Exception occurred while removing row. Please check server logs for more details.");
						}else{
							$("#"+TRID+"").remove();
							alert("Row removed successfully.");
						}
						
				}
			};
			xmlhttp.open("GET", "RemoveHeadBoyRow?headBoyID="
					+ headBoyID, true);
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

function editRedConfiguration(academicYearID) {

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			var trTag="";
			var check = 0;
			
			for ( var i = 0; i < array.Release.length; i++) {

				var houseCaptainRedName = array.Release[i].name;
				var houseCaptainRedID = array.Release[i].houseRedID;
				var candidateApproved = array.Release[i].candidateApproved;

				var hiddenID = "typehouseCaptainRedID"+houseCaptainRedID;
				
				var checkboxHiddenID = "checkboxhouseCaptainRedID"+houseCaptainRedID;
				
				var trID = "trID"+houseCaptainRedID;
				
				check = array.Release[i].check;
				
				 trTag += "<tr id="+trID+"><td><input type='hidden' name='updateNameType' value='Red'><input type='hidden' name='houseColorID' value="+houseCaptainRedID+">"+houseCaptainRedName+"</td>";

				 	if(candidateApproved=="Yes"){
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='Yes' id='"+hiddenID+"'><input type='checkbox' checked='checked' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}else{
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='No' id='"+hiddenID+"'><input type='checkbox' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}
				
					trTag += "<td align='center'><img src='images/delete_icon_1.png' style='height:20px; cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeHouseCaptainRedTR(\"" + houseCaptainRedID + "\", \"" + trID + "\");'/></td>";
					trTag += "</tr>";
				
			}
			
			if(check == 0){
			
				$("#HouseCaptainRedDivID1").show();
				$("#HeadGirlNominationsID1").hide();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
			}else{
				
				$("#HouseCaptainRedDivID1").show();
				$("#HeadGirlNominationsID1").hide();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
				$(trTag).insertAfter($('#HouseCaptainRedTRID1'));
			}
		
		}
			
	};
	xmlhttp.open("GET", "RetrieveHouseCaptainRedDetails?academicYearID="
			+ academicYearID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>

<script type="text/javascript">
    
    	function removeHouseCaptainRedTR(houseCaptainRedID, TRID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeHouseCaptainRedTR1(houseCaptainRedID, TRID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeHouseCaptainRedTR1(houseCaptainRedID, TRID){ 
	    	
	    	xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//alert(AYClassID);
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
						}
	
						
						if(check == 0){
							alert("Exception occurred while removing row. Please check server logs for more details.");
						}else{
							$("#"+TRID+"").remove();
							alert("Row removed successfully.");
						}
						
				}
			};
			xmlhttp.open("GET", "RemoveRedCaptainRow?houseCaptainRedID="
					+ houseCaptainRedID, true);
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

function editBlueConfiguration(academicYearID) {

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			var trTag="";
			var check = 0;
			
			for ( var i = 0; i < array.Release.length; i++) {

				var houseCaptainBlueName = array.Release[i].name;
				var houseCaptainBlueID = array.Release[i].houseBlueID;
				var candidateApproved = array.Release[i].candidateApproved;
				
				var hiddenID = "typehouseCaptainBlueID"+houseCaptainBlueID;
				
				var checkboxHiddenID = "checkboxhouseCaptainBlueID"+houseCaptainBlueID;
				
				var trID = "trID"+houseCaptainBlueID;
				
				check = array.Release[i].check;
				
				 trTag += "<tr id="+trID+"><td><input type='hidden' name='updateNameType' value='Blue'><input type='hidden' name='houseColorID' value="+houseCaptainBlueID+">"+houseCaptainBlueName+"</td>";

				 	if(candidateApproved=="Yes"){
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='Yes' id='"+hiddenID+"'><input type='checkbox' checked='checked' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}else{
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='No' id='"+hiddenID+"'><input type='checkbox' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}
				
					trTag += "<td align='center'><img src='images/delete_icon_1.png' style='height:20px; cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeHouseCaptainBlueTR(\"" + houseCaptainBlueID + "\", \"" + trID + "\");'/></td>";
					trTag += "</tr>";
				
			}
			
			if(check == 0){
				
				$("#HouseCaptainBlueDivID1").show();
				$("#HouseCaptainRedDivID1").hide();
				$("#HeadGirlNominationsID1").hide();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
			}else{
				
				$("#HouseCaptainBlueDivID1").show();
				$("#HouseCaptainRedDivID1").hide();
				$("#HeadGirlNominationsID1").hide();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
				$(trTag).insertAfter($('#HouseCaptainBlueTRID1'));
			}
		
		}
			
	};
	xmlhttp.open("GET", "RetrieveHouseCaptainBlueDetails?academicYearID="
			+ academicYearID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>

<script type="text/javascript">
    
    	function removeHouseCaptainBlueTR(houseCaptainBlueID, TRID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeHouseCaptainBlueTR1(houseCaptainBlueID, TRID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeHouseCaptainBlueTR1(houseCaptainBlueID, TRID){ 
	    	
	    	xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//alert(AYClassID);
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
						}
	
						
						if(check == 0){
							alert("Exception occurred while removing row. Please check server logs for more details.");
						}else{
							$("#"+TRID+"").remove();
							alert("Row removed successfully.");
						}
						
				}
			};
			xmlhttp.open("GET", "RemoveBlueCaptainRow?houseCaptainBlueID="
					+ houseCaptainBlueID, true);
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

function editGreenConfiguration(academicYearID) {

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			var trTag="";
			var check = 0;
			
			for ( var i = 0; i < array.Release.length; i++) {

				var houseCaptainGreenName = array.Release[i].name;
				var houseCaptainGreenID = array.Release[i].houseGreenID;
				var candidateApproved = array.Release[i].candidateApproved;
				
				var hiddenID = "typehouseCaptainGreenID"+houseCaptainGreenID;
				
				var checkboxHiddenID = "checkboxhouseCaptainGreenID"+houseCaptainGreenID;
				
				var checkboxHiddenID = "checkboxhouseCaptainBlueID"+houseCaptainGreenID;
				
				var trID = "trID"+houseCaptainGreenID;
				
				check = array.Release[i].check;
				
				 trTag += "<tr id="+trID+"><td><input type='hidden' name='updateNameType' value='Green'><input type='hidden' name='houseColorID' value="+houseCaptainGreenID+">"+houseCaptainGreenName+"</td>";

				 	if(candidateApproved=="Yes"){
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='Yes' id='"+hiddenID+"'><input type='checkbox' checked='checked' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}else{
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='No' id='"+hiddenID+"'><input type='checkbox' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}
				
					trTag += "<td align='center'><img src='images/delete_icon_1.png' style='height:20px; cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeHouseCaptainGreenTR(\"" + houseCaptainGreenID + "\", \"" + trID + "\");'/></td>";
					trTag += "</tr>";
				
			}
			
			if(check == 0){
				
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainRedDivID1").hide();
				$("#HeadGirlNominationsID1").hide();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainGreenDivID1").show();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
			}else{
				
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainRedDivID1").hide();
				$("#HeadGirlNominationsID1").hide();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainGreenDivID1").show();
				$("#HouseCaptainYellowDivID1").hide();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
				$(trTag).insertAfter($('#HouseCaptainGreenTRID1'));
			}
		
		}
			
	};
	xmlhttp.open("GET", "RetrieveHouseCaptainGreenDetails?academicYearID="
			+ academicYearID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>

<script type="text/javascript">
    
    	function removeHouseCaptainGreenTR(houseCaptainGreenID, TRID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeHouseCaptainGreenTR1(houseCaptainGreenID, TRID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeHouseCaptainGreenTR1(houseCaptainGreenID, TRID){ 
	    	
	    	xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//alert(AYClassID);
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
						}
	
						
						if(check == 0){
							alert("Exception occurred while removing row. Please check server logs for more details.");
						}else{
							$("#"+TRID+"").remove();
							alert("Row removed successfully.");
						}
						
				}
			};
			xmlhttp.open("GET", "RemoveGreenCaptainRow?houseCaptainGreenID="
					+ houseCaptainGreenID, true);
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

function editYellowConfiguration(academicYearID) {

	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			var array = JSON.parse(xmlhttp.responseText);
			var trTag="";
			var check = 0;
			
			for ( var i = 0; i < array.Release.length; i++) {

				var houseCaptainYellowName = array.Release[i].name;
				var houseCaptainYellowID = array.Release[i].houseYellowID;
				var candidateApproved = array.Release[i].candidateApproved;

				var hiddenID = "typehouseCaptainYellowID"+houseCaptainYellowID;
				
				var checkboxHiddenID = "checkboxhouseCaptainYellowID"+houseCaptainYellowID;
				
				var trID = "trID"+houseCaptainYellowID;
				
				check = array.Release[i].check;
				
				 trTag += "<tr id="+trID+"><td><input type='hidden' name='updateNameType' value='Yellow'><input type='hidden' name='houseColorID' value="+houseCaptainYellowID+">"+houseCaptainYellowName+"</td>";

				 	if(candidateApproved=="Yes"){
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='Yes' id='"+hiddenID+"'><input type='checkbox' checked='checked' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}else{
						
						trTag += "<td><input type='hidden' name='checkboxValue' value='No' id='"+hiddenID+"'><input type='checkbox' id ='"+checkboxHiddenID+"' onclick='setHiddenValue(\""+checkboxHiddenID+"\",\""+hiddenID+"\");'></td>";
					}
				
					trTag += "<td align='center'><img src='images/delete_icon_1.png' style='height:20px; cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeHouseCaptainYellowTR(\"" + houseCaptainYellowID + "\", \"" + trID + "\");'/></td>";
					trTag += "</tr>";
				
					
			}
			
			if(check == 0){
				
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainRedDivID1").hide();
				$("#HeadGirlNominationsID1").hide();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").show();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
			}else{
				
				$("#HouseCaptainBlueDivID1").hide();
				$("#HouseCaptainRedDivID1").hide();
				$("#HeadGirlNominationsID1").hide();
				$("#HeadBoyNominationsID1").hide();
				$("#HouseCaptainGreenDivID1").hide();
				$("#HouseCaptainYellowDivID1").show();
				
				$("#HeadGirlResultsID").hide();
				$("#HeadBoyResultsID").hide();
				$("#HouseCaptainRedResultsID").hide();
				$("#HouseCaptainBlueResultsID").hide();
				$("#HouseCaptainGreenResultsID").hide();
				$("#HouseCaptainYellowResultsID").hide();
				
				$(trTag).insertAfter($('#HouseCaptainYellowTRID1'));
			}
		
		}
			
	};
	xmlhttp.open("GET", "RetrieveHouseCaptainYellowDetails?academicYearID="
			+ academicYearID, true);
	xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
	xmlhttp.send();
}

</script>

<script type="text/javascript">
    
    	function removeHouseCaptainYellowTR(houseCaptainYellowID, TRID){
    		if(confirm("Are you sure you want to remove this row?")){
    			 removeHouseCaptainYellowTR1(houseCaptainYellowID, TRID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function  removeHouseCaptainYellowTR1(houseCaptainYellowID, TRID){ 
	    	
	    	xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//alert(AYClassID);
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
						}
	
						
						if(check == 0){
							alert("Exception occurred while removing row. Please check server logs for more details.");
						}else{
							$("#"+TRID+"").remove();
							alert("Row removed successfully.");
						}
						
				}
			};
			xmlhttp.open("GET", "RemoveYellowCaptainRow?houseCaptainYellowID="
					+ houseCaptainYellowID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
	    	
		}
    
</script>

<script type="text/javascript">
   
	function addHeadGirlConfigRow(headGirlName){
		
		if(headGirlName == ""){
			alert("Please enter head girl name.");
		}else{
	
			addHeadGirlConfigRow1(headGirlName);
		}
	}
    
    var divCounter = 1;
	
	function addHeadGirlConfigRow1(headGirlName){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+headGirlName+"<input type='hidden' name='nameType' value='HeadGirl'><input type='hidden' name='studentName' value='"+headGirlName+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HeadGirlTRID'));
		
		divCounter++;
		
		$("#headGirlID").val("");
		
	}
	
	
	function removeDivTR(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
    
        	//Updating new value to dummySettingTextID field
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>
 
 <script type="text/javascript">
   
	function editHeadGirlConfigRow(headGirlName){
		
		if(headGirlName == ""){
			alert("Please enter head girl name.");
		}else{
	
			editHeadGirlConfigRowNew(headGirlName);
		}
	}
    
    var divCounter = 1;
	
	function editHeadGirlConfigRowNew(headGirlName){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+headGirlName+"<input type='hidden' name='editNameType' value='HeadGirl'><input type='hidden' name='editStudentName' value='"+headGirlName+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR1(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HeadGirlTRID1'));
		
		divCounter++;
		
		$("#headGirlID1").val("");
		
	}
	
	
	function removeDivTR1(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
    
        	//Updating new value to dummySettingTextID field
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>
 
 <script type="text/javascript">
   
	function addHeadBoyConfigRow(headBoyName){
		
		if(headBoyName == ""){
			alert("Please enter head boy name.");
		}else{
	
			addHeadBoyConfigRow1(headBoyName);
		}
	}
    
    var divCounter = 1;
	
	function addHeadBoyConfigRow1(headBoyName){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+headBoyName+"<input type='hidden' name='nameType' value='HeadBoy'><input type='hidden' name='studentName' value='"+headBoyName+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeBoyDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HeadBoyTRID'));
		
		divCounter++;
		
		$("#headBoyID").val("");
		
	}
	
	
	function removeBoyDivTR(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>

<script type="text/javascript">
   
	function editHeadBoyConfigRow(headBoyName){
		
		if(headBoyName == ""){
			alert("Please enter head boy name.");
		}else{
	
			editHeadBoyConfigRowNew(headBoyName);
		}
	}
    
    var divCounter = 1;
	
	function editHeadBoyConfigRowNew(headBoyName){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+headBoyName+"<input type='hidden' name='editNameType' value='HeadBoy'><input type='hidden' name='editStudentName' value='"+headBoyName+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeBoyDivTR1(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HeadBoyTRID1'));
		
		divCounter++;
		
		$("#headBoyID1").val("");
		
	}
	
	
	function removeBoyDivTR1(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>

<script type="text/javascript">
   
	function addhouseCaptainRedConfigRow(houseCaptainRed){
		
		if(houseCaptainRed == ""){
			alert("Please enter student name.");
		}else{
	
			addhouseCaptainRedConfigRow1(houseCaptainRed);
		}
	}
    
    var divCounter = 1;
	
	function addhouseCaptainRedConfigRow1(houseCaptainRed){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+houseCaptainRed+"<input type='hidden' name='nameType' value='Red'><input type='hidden' name='studentName' value='"+houseCaptainRed+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeRedDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainRedTRID'));
		
		divCounter++;
		
		$("#houseCaptainRedID").val("");
		
	}
	
	
	function removeRedDivTR(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
        	
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>
 
 <script type="text/javascript">
   
	function edithouseCaptainRedConfigRow(houseCaptainRed){
		
		if(houseCaptainRed == ""){
			alert("Please enter student name.");
		}else{
	
			edithouseCaptainRedConfigRowNew(houseCaptainRed);
		}
	}
    
    var divCounter = 1;
	
	function edithouseCaptainRedConfigRowNew(houseCaptainRed){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+houseCaptainRed+"<input type='hidden' name='editNameType' value='Red'><input type='hidden' name='editStudentName' value='"+houseCaptainRed+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeRedDivTR1(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainRedTRID1'));
		
		divCounter++;
		
		$("#houseCaptainRedID1").val("");
		
	}
	
	function removeRedDivTR1(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
        	
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>
 
<script type="text/javascript">
   
	function addhouseCaptainBlueConfigRow(houseCaptainBlue){
		
		if(houseCaptainBlue == ""){
			alert("Please enter student name.");
		}else{
	
			addhouseCaptainBlueConfigRow1(houseCaptainBlue);
		}
	}
    
    var divCounter = 1;
	
	function addhouseCaptainBlueConfigRow1(houseCaptainBlue){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+houseCaptainBlue+"<input type='hidden' name='nameType' value='Blue'><input type='hidden' name='studentName' value='"+houseCaptainBlue+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeBlueDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainBlueTRID'));
		
		divCounter++;
		
		$("#houseCaptainBlueID").val("");
		
	}
	
	
	function removeBlueDivTR(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>   
 
 <script type="text/javascript">
   
	function edithouseCaptainBlueConfigRow(houseCaptainBlue){
		
		if(houseCaptainBlue == ""){
			alert("Please enter student name.");
		}else{
	
			edithouseCaptainBlueConfigRowNew(houseCaptainBlue);
		}
	}
    
    var divCounter = 1;
	
	function edithouseCaptainBlueConfigRowNew(houseCaptainBlue){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+houseCaptainBlue+"<input type='hidden' name='editNameType' value='Blue'><input type='hidden' name='editStudentName' value='"+houseCaptainBlue+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeBlueDivTR1(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainBlueTRID1'));
		
		divCounter++;
		
		$("#houseCaptainBlueID1").val("");
		
	}
	
	
	function removeBlueDivTR1(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>  
 
 <script type="text/javascript">
   
	function addhouseCaptainGreenConfigRow(houseCaptainGreen){
		
		if(houseCaptainGreen == ""){
			alert("Please enter student name.");
		}else{
	
			addhouseCaptainGreenConfigRow1(houseCaptainGreen);
		}
	}
    
    var divCounter = 1;
	
	function addhouseCaptainGreenConfigRow1(houseCaptainGreen){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+houseCaptainGreen+"<input type='hidden' name='nameType' value='Green'><input type='hidden' name='studentName' value='"+houseCaptainGreen+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeGreenDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainGreenTRID'));
				
		divCounter++;
		
		$("#houseCaptainGreenID").val("");
		
	}
	
	
	function removeGreenDivTR(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>
   

<script type="text/javascript">
   
	function edithouseCaptainGreenConfigRow(houseCaptainGreen){
		
		if(houseCaptainGreen == ""){
			alert("Please enter student name.");
		}else{
	
			edithouseCaptainGreenConfigRowNew(houseCaptainGreen);
		}
	}
    
    var divCounter = 1;
	
	function edithouseCaptainGreenConfigRowNew(houseCaptainGreen){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+houseCaptainGreen+"<input type='hidden' name='editNameType' value='Green'><input type='hidden' name='editStudentName' value='"+houseCaptainGreen+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeGreenDivTR1(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainGreenTRID1'));
				
		divCounter++;
		
		$("#houseCaptainGreenID1").val("");
		
	}
	
	
	function removeGreenDivTR1(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>   
    
<script type="text/javascript">
   
	function addhouseCaptainYellowConfigRow(houseCaptainYellow){
		
		if(houseCaptainYellow == ""){
			alert("Please enter student name.");
		}else{
	
			addhouseCaptainYellowConfigRow1(houseCaptainYellow);
		}
	}
    
    var divCounter = 1;
	
	function addhouseCaptainYellowConfigRow1(houseCaptainYellow){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+houseCaptainYellow+"<input type='hidden' name='nameType' value='Yellow'><input type='hidden' name='studentName' value='"+houseCaptainYellow+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeYellowDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainYellowTRID'));
				
		divCounter++;
		
		$("#houseCaptainYellowID").val("");
		
	}
	
	
	function removeYellowDivTR(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>


<script type="text/javascript">
   
	function edithouseCaptainYellowConfigRow(houseCaptainYellow){
		
		if(houseCaptainYellow == ""){
			alert("Please enter student name.");
		}else{
	
			edithouseCaptainYellowConfigRowNew(houseCaptainYellow);
		}
	}
    
    var divCounter = 1;
	
	function edithouseCaptainYellowConfigRowNew(houseCaptainYellow){
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+houseCaptainYellow+"<input type='hidden' name='editNameType' value='Yellow'><input type='hidden' name='editStudentName' value='"+houseCaptainYellow+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeYellowDivTR1(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainYellowTRID1'));
				
		divCounter++;
		
		$("#houseCaptainYellowID1").val("");
		
	}
	
	
	function removeYellowDivTR1(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
    	
	}
	
</script>


<script type="text/javascript">
	function submitForm(subID){
		  $("#loadAcademicFormNew").attr("action","ConfigureAcademicYear");
		
		  	  $("#loadAcademicFormNew").submit(); 
		  	  
		 	 $("#"+subID).attr("disabled", "disabled");
	 }
	
	function submitForm1(subID1){
		  $("#loadAcademicFormNew1").attr("action","UpdateConfigureAcademicYear");
		
		  	  $("#loadAcademicFormNew1").submit(); 
		  	  
		 	 $("#"+subID1).attr("disabled", "disabled");
	 }
	
	function windowOpen1(){
      document.location="RenderAcademicYear";
   	}
	
	//function to set checkbox value into hidden field
	function setHiddenValue(checkboxID, hiddenID){
		if($("#"+checkboxID).is(":checked")){
			$("#"+hiddenID).val("Yes");
		}else{
			$("#"+hiddenID).val("No");
		}
	}
	
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
    
    /* Chrome, Safari, Edge, Opera */
	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
	  -webkit-appearance: none;
	  margin: 0;
	}
	
	/* Firefox */
	input[type=number] {
	  -moz-appearance: textfield;
	}
</style>

<body class="fix-header fix-sidebar">
	
	<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

    <!-- Preloader - style you can find in spinners.css -->
    <div class="preloader">
        <svg class="circular" viewBox="25 25 50 50">
			<circle class="path" cx="50" cy="50" r="20" fill="none" stroke-width="2" stroke-miterlimit="10" /> </svg>
    </div>
    <!-- Main wrapper  -->
    <div id="main-wrapper">
      <!-- Page wrapper  -->
        <div class="page-wrapper">
            <!-- Bread crumb -->
            <div class="row page-titles">
                <div class="col-md-5 align-self-center">
                    <h3 class="text-primary">Set Election</h3> 
                </div>
               
             <div class="col-md-5 align-self-center">
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
									
            </div>
            <!-- End Bread crumb -->
            <!-- Container fluid  -->
            <div class="container-fluid">
                <!-- Start Page Content -->
               
              <form id="SearchAcademicForm" action="LoadAcademicYear" method="POST" >
               	<div class="row">
                    <div class="col-md-3">
                         <button type="button" class="btn btn-primary m-b-10 m-l-5" onclick="showElectionDetails();">Add New Academic Year</button>
					</div>
					
					
					<% if(AcademicYearListDetails.equals("Yes")){	%>
							
					
						<div class="col-md-3" style="padding-left: 89px;">Search Existing Year:</div>
						<div class="col-md-3">
							<s:select class="form-control" list="AcademicYearList" id="roleID" headerKey="-1" headerValue="Select Existing Year" name="academicYearID" ></s:select>
						</div>
						<div class="col-md-3">
	                        <button type="submit" class="btn btn-primary m-b-10 m-l-5" >Search</button>
						</div>
					<%} %>
				</div>
			</form>
			
			<form id="loadAcademicFormNew" action="ConfigureAcademicYear" method="POST" >		
                <div class="row">
                 <div class="col-lg-6">
                        <div class="card" id="AcademicYearDivID" style="display: none;">
                            
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <td><label>Academic Year: </label></td>
                                                <td><input type="text" name="name" class="form-control" id="AcademicYearID" placeholder="Name" required="required"></td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Head Girl: </td>
                                                <td><a href="javascript:addConfiguration('HeadGirl')">Nominations</a></td>
                                                
                                            </tr>
                                            <tr>
                                                <td>Head Boy: </td>
                                                <td><a href="javascript:addConfiguration('HeadBoy')">Nominations</a></td>
                                                
                                            </tr>
                                            <tr>
                                                <td>House Captain (Red): </td>
                                                <td><a href="javascript:addConfiguration('Red')">Nominations</a></td>
                                                
                                            </tr>
                                             <tr>
                                                <td>House Captain (Blue): </td>
                                                <td><a href="javascript:addConfiguration('Blue')">Nominations</a></td>
                                                
                                            </tr>
                                             <tr>
                                                <td>House Captain (Green): </td>
                                                <td><a href="javascript:javascript:addConfiguration('Green')">Nominations</a></td>
                                             
                                            </tr>
                                             <tr>
                                                <td>House Captain (Yellow): </td>
                                                <td><a href="javascript:addConfiguration('Yellow')">Nominations</a></td>
                                               
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /# Head Girl Nominations column -->
                    <div class="col-lg-6" id="HeadGirlNominationsID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>Head Girl Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HeadGirlTRID">
											<td>
											    <input type="text" name="" class="form-control" id="headGirlID" placeholder="Student Name" required="required" >
											</td>
											<td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
						  					 	onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="addHeadGirlConfigRow(headGirlID.value);"/>
											</td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                
              <!-- /# Head Boy Nominations column -->  
                	<div class="col-lg-6" id="HeadBoyNominationsID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>Head Boy Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HeadBoyTRID">
											<td>
										   		<input type="text" name="" class="form-control" id="headBoyID" placeholder="Student Name" required="required" >
											</td>
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="addHeadBoyConfigRow(headBoyID.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
               
                    <!-- /# House Captain (Red) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainRedDivID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Red) Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead> 
                                    <tbody>
                                        <tr id="HouseCaptainRedTRID">
											 <td>
										 		<input type="text" name="" class="form-control" id="houseCaptainRedID" placeholder="Student Name" required="required" >
											</td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  								  onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
														title="Add Configuration" onclick="addhouseCaptainRedConfigRow(houseCaptainRedID.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Blue) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainBlueDivID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Blue) Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainBlueTRID">
											 <td>
										  		<input type="text" name="" class="form-control" id="houseCaptainBlueID" placeholder="Student Name" required="required" >
											 </td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="addhouseCaptainBlueConfigRow(houseCaptainBlueID.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Green) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainGreenDivID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Green) Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainGreenTRID">
											 <td>
										   		<input type="text" name="" class="form-control" id="houseCaptainGreenID" placeholder="Student Name" required="required" >
											</td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="addhouseCaptainGreenConfigRow(houseCaptainGreenID.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Yellow) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainYellowDivID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Yellow) Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainYellowTRID">
											 <td>
										   		<input type="text" name="" class="form-control" id="houseCaptainYellowID" placeholder="Student Name" required="required" >
											</td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="addhouseCaptainYellowConfigRow(houseCaptainYellowID.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                </div>
                
                <div class="row" id="submitFormDivID" style="display: none;">
                <div class="col-md-12" align="center">
		            <button type="button" onclick="windowOpen1();" class="btn btn-success">Cancel</button>
		            <button type="submit" class="btn btn-primary " id="submitID" onclick="submitForm('submitID');">Save</button>  
		        </div>
                    
				</div>
				
              </form>  
                    <!-- /# column -->
                
          <% if(loadAcademicYear.equals("Yes")){ %>
             
            <s:iterator value="AcademicYearDetailsList"> 
            <!-- UpdateConfigureAcademicYear -->
               	<form id="loadAcademicFormNew1" action="UpdateConfigureAcademicYear" method="POST" >
               	 
               	 <%
               	 if(ActiveAcademicYear.equals("Active")){ 
               		
               	 %>
               	  <div class="row"> 
					<div class="col-md-6" >
			        </div>
					<% if(VotingStatus.equals("Done")){ 
						
					%>
			          <div class="col-md-3" >
			               <button type="submit" class="btn btn-primary m-b-10 m-l-5" onclick="window.location='ConfigureVotingStatus?academicYearID=<s:property value="academicYearID" />&votingStatus=Voting'">Start Voting</button>
					 </div>
							 
				   <% }else if(VotingStatus.equals("Voting")){
					
				%>	
					 <div class="col-md-3" >
			              <button type="submit" class="btn btn-danger m-b-10 m-l-5" onclick="window.location='ConfigureVotingStatus?academicYearID=<s:property value="academicYearID" />&votingStatus=Done'">End Voting</button>
					 </div>
			      <% } %>  
			    </div>	
		       <%} %> 	
                
                <div class="row" style="margin-top: 15px;">
                 <div class="col-lg-6">
                        <div class="card" >
                            
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                    <input type="hidden" name="academicYearID" value="<s:property value="academicYearID" />" id="hiddenID">
                                        <thead>
                                            <tr>
                                                <td><label>Academic Year: </label></td>
                                                <td><s:property value="name" /></td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Head Girl: </td>
                                                <td><a href="javascript:editHeadGirlConfiguration(<s:property value="academicYearID" />)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('HeadGirl')">Reports</a></td>
                                            </tr>
                                            <tr>
                                                <td>Head Boy: </td>
                                                <td><a href="javascript:editHeadBoyConfiguration(<s:property value="academicYearID" />)">Nominations</a></td>
                                            	<td><a href="javascript:editReportsConfiguration('HeadBoy')">Reports</a></td>
                                            </tr>
                                            <tr>
                                                <td>House Captain (Red): </td>
                                                <td><a href="javascript:editRedConfiguration(<s:property value="academicYearID" />)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('Red')">Reports</a></td>
                                            </tr>
                                             <tr>
                                                <td>House Captain (Blue): </td>
                                                <td><a href="javascript:editBlueConfiguration(<s:property value="academicYearID" />)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('Blue')">Reports</a></td>
                                            </tr>
                                             <tr>
                                                <td>House Captain (Green): </td>
                                                <td><a href="javascript:javascript:editGreenConfiguration(<s:property value="academicYearID" />)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('Green')">Reports</a></td>
                                            </tr>
                                             <tr>
                                                <td>House Captain (Yellow): </td>
                                                <td><a href="javascript:editYellowConfiguration(<s:property value="academicYearID" />)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('Yellow')">Reports</a></td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /# Head Girl Nominations column -->
                    <div class="col-lg-6" id="HeadGirlNominationsID1" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>Head Girl Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HeadGirlTRID1">
											<td>
											    <input type="text" name="" class="form-control" id="headGirlID1" placeholder="Student Name" required="required" >
											</td>
											<td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
						  					 	onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="editHeadGirlConfigRow(headGirlID1.value);"/>
											</td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                
              <!-- /# Head Boy Nominations column -->  
                	<div class="col-lg-6" id="HeadBoyNominationsID1" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>Head Boy Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HeadBoyTRID1">
											<td>
										   		<input type="text" name="" class="form-control" id="headBoyID1" placeholder="Student Name" required="required" >
											</td>
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="editHeadBoyConfigRow(headBoyID1.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
               
                    <!-- /# House Captain (Red) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainRedDivID1" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Red) Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead> 
                                    <tbody>
                                        <tr id="HouseCaptainRedTRID1">
											 <td>
										 		<input type="text" name="" class="form-control" id="houseCaptainRedID1" placeholder="Student Name" required="required" >
											</td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  								  onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
														title="Add Configuration" onclick="edithouseCaptainRedConfigRow(houseCaptainRedID1.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Blue) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainBlueDivID1" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Blue) Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainBlueTRID1">
											 <td>
										  		<input type="text" name="" class="form-control" id="houseCaptainBlueID1" placeholder="Student Name" required="required" >
											 </td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="edithouseCaptainBlueConfigRow(houseCaptainBlueID1.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Green) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainGreenDivID1" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Green) Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainGreenTRID1">
											 <td>
										   		<input type="text" name="" class="form-control" id="houseCaptainGreenID1" placeholder="Student Name" required="required" >
											</td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Configuration" onclick="edithouseCaptainGreenConfigRow(houseCaptainGreenID1.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Yellow) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainYellowDivID1" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Yellow) Nominations</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainYellowTRID1">
											 <td>
										    	<input type="text" name="" class="form-control" id="houseCaptainYellowID1" placeholder="Student Name" required="required" >
											</td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
														title="Add Configuration" onclick="edithouseCaptainYellowConfigRow(houseCaptainYellowID1.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                     <!------------------------ All Students Reports ------------------------------>
                    
                    <div class="col-lg-6" id="HeadGirlResultsID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>Head Girl Election Results</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Votes</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                       	<% for(LibraryForm form : HeadGirlList){ %>
                                 	
			                             <tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
			                            
			                             <%} %>
										
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
               
              <!-- /# Head Boy Nominations column -->  
                	<div class="col-lg-6" id="HeadBoyResultsID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>Head Boy Election Results</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th>Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <% for(LibraryForm form:HeadBoyList){ %>
	                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
	                                    <%} %>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
               
                    <!-- /# House Captain (Red) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainRedResultsID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Red) Election Results</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Votes</td>
                                         </tr>
                                    </thead> 
                                    <tbody>
                                        <% for(LibraryForm form:RedList){ %>
	                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
	                                    <%} %>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Blue) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainBlueResultsID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Blue) Election Results</b></h4>
						</div>
                            
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Votes</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <% for(LibraryForm form:BlueList){ %>
	                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
	                                    <%} %>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Green) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainGreenResultsID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Green) Election Results</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Votes</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <% for(LibraryForm form:GreenList){ %>
	                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
	                                    <%} %>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                    <!-- /# House Captain (Yellow) Nominations column -->  
                	<div class="col-lg-6" id="HouseCaptainYellowResultsID" style="display: none;">
                        <div class="card">
                        <div class="card-title">
                           <h4><b>House Captain (Yellow) Election Results</b></h4>
						</div>
                           
                        <div class="card-body">
                             <div class="table-responsive">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Votes</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                       <% for(LibraryForm form:YellowList){ %>
	                                 	<tr><td><%=form.getName() %>:</td><td><%=form.getVoteCount() %></td></tr>
	                                    <%} %>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                    
                </div>
                
               <%if(ActiveAcademicYear=="Active"){ %> 
                <div class="row" id="submitFormDivID1">
	                <div class="col-md-12" align="center">
			            <button type="button" onclick="windowOpen1();" class="btn btn-success">Cancel</button>
			       		<button type="submit" class="btn btn-primary " id="submitID1" onclick="submitForm1('submitID1');">Save</button>
			       </div>
              	</div>
				<%} %>
				
              </form> 
             </s:iterator>
            <% }  %>
                <!-- /# row -->
                <!-- End PAge Content -->
            </div>
            <!-- End Container fluid  -->
           
        </div>
        <!-- End Page wrapper  -->
    </div>
    <!-- End Wrapper -->
   
    <!-- All Jquery -->
  <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>
  
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
  
  <script type="text/javascript" src="js/bootstrap-3.0.3.min.js"></script>  
  <script type="text/javascript" src="js/bootstrap-multiselect.js"></script>

</body>

</html>