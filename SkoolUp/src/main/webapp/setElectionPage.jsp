<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="com.kovidRMS.daoInf.*"%>
<%@page import="com.kovidRMS.daoImpl.*"%>
<%@page import="java.util.*"%>
<%@page import="com.kovidRMS.form.*"%>
    
<!DOCTYPE html>
<html class="no-js"> 
<head>

	<%
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
		LibraryDAOInf daoInf = new LibraryDAOImpl();
	
		LoginDAOInf daoinf = new LoginDAOImpl();
		
		String AcademicYearName = daoinf.retrieveAcademicYearName(loginform.getOrganizationID());
		
		int AcademicYearID =  daoinf.retrieveAcademicYearID(loginform.getOrganizationID());
		
		HashMap<Integer, String> StandardDivisionList = daoinf.getStandardDivisionList(loginform.getAcademicYearID());
		
		String loadAcademicYear = (String) request.getAttribute("loadAcademicYear");
		
		if(loadAcademicYear == null || loadAcademicYear == ""){
			loadAcademicYear = "dummy";
		}
	
		String VotingStatus = (String) request.getAttribute("VotingStatus");
		
		List<LibraryForm> HeadGirlList = (List<LibraryForm>) request.getAttribute("HeadGirlList");
		
		List<LibraryForm> HeadBoyList = (List<LibraryForm>) request.getAttribute("HeadBoyList");
		
		List<LibraryForm> RedList = (List<LibraryForm>) request.getAttribute("RedList");
		
		List<LibraryForm> BlueList = (List<LibraryForm>) request.getAttribute("BlueList");
		
		List<LibraryForm> GreenList = (List<LibraryForm>) request.getAttribute("GreenList");
		
		List<LibraryForm> YellowList = (List<LibraryForm>) request.getAttribute("YellowList");
    %>
    
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
  <link rel="stylesheet" href="./css/bootstrap.min.css">
  <link href="css/jquery.multiselect.css" rel="stylesheet" type="text/css">

	<link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" />
	<link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />
  <link rel="stylesheet" href="./js/plugins/datepicker/datepicker.css">
   <link rel="stylesheet" href="./js/plugins/fullcalendar/fullcalendar.css">

  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">
 
  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment.min.js"></script>
  
<script type="text/javascript">
      jQuery(document).ready(function($) {
          $(".scroll").click(function(event){     
              event.preventDefault();
              $('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
          });
      });
      
      $(document).ready(function(){
  		$('#administrationLiID').addClass("active");
  		//alert("hiii");	
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
   
	function addHeadGirlConfigRow(classID, headGirl){
		
		if(classID == "-1"){
			alert("Please select class.");
		}else if(headGirl == "-1"){
			alert("Please select head girl.");
		}else{
	
			addHeadGirlConfigRow1(classID, headGirl);
		}
	}
    
    var divCounter = 1;
	
	function addHeadGirlConfigRow1(classID, headGirl){
		
		var headGirlName = $('#headGirlID option:selected').text();
		var className = $('#headGirlClassID option:selected').text();
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+className+"<input type='hidden' name='nameType' value='HeadGirl'></td>"
			  + "<td>"+headGirlName+"<input type='hidden' name='studentName' value='"+headGirlName+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HeadGirlTRID'));
		
		divCounter++;
		
		$("#headGirlID").val("-1");
		$("#headGirlClassID").val("-1");
		
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
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
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
   
	function addHeadBoyConfigRow(classID, headBoy){
		
		if(classID == "-1"){
			alert("Please select class.");
		}else if(headBoy == "-1"){
			alert("Please select head boy.");
		}else{
	
			addHeadBoyConfigRow1(classID, headBoy);
		}
	}
    
    var divCounter = 1;
	
	function addHeadBoyConfigRow1(classID, headBoy){
		
		var headBoyName = $('#headBoyID option:selected').text();
		var className = $('#headBoyClassID option:selected').text();
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
		 	  + "<td>"+className+"<input type='hidden' name='nameType' value='HeadBoy'></td>"
			  + "<td>"+headBoyName+"<input type='hidden' name='studentName' value='"+headBoyName+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HeadBoyTRID'));
		
		divCounter++;
		
		$("#headBoyClassID").val("-1");
		$("#headBoyID").val("-1");
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
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HeadBoyTRID1'));
		
		divCounter++;
		
		$("#headBoyID1").val("");
	}
	
</script>

<script type="text/javascript">
   
	function addhouseCaptainRedConfigRow(classID, houseCaptain){
		
		if(classID == "-1"){
			alert("Please select class.");
		}else if(houseCaptain == "-1"){
			alert("Please select student.");
		}else{
	
			addhouseCaptainRedConfigRow1();
		}
	}
    
    var divCounter = 1;
	
	function addhouseCaptainRedConfigRow1(){
		
		var redHouseCaptain = $('#houseCaptainRedID option:selected').text();
		var className = $('#captainRedClassID option:selected').text();
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+className+"<input type='hidden' name='nameType' value='Red'></td>"
			  + "<td>"+redHouseCaptain+"<input type='hidden' name='studentName' value='"+redHouseCaptain+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainRedTRID'));
		
		divCounter++;
		
		$("#houseCaptainRedID").val("-1"); 
		$("#captainRedClassID").val("-1");
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
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainRedTRID1'));
		
		divCounter++;
		
		$("#houseCaptainRedID1").val("");
		
	}
	
</script>
 
<script type="text/javascript">
   
	function addhouseCaptainBlueConfigRow(classID, houseCaptain){
		
		if(classID == "-1"){
			alert("Please select class.");
		}else if(houseCaptain == "-1"){
			alert("Please select student.");
		}else{
	
			addhouseCaptainBlueConfigRow1();
		}
	}
    
    var divCounter = 1;
	
	function addhouseCaptainBlueConfigRow1(){
		
		var houseCaptainBlue = $('#houseCaptainBlueID option:selected').text();
		var className = $('#captainBlueClassID option:selected').text();
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
		      + "<td>"+className+"<input type='hidden' name='nameType' value='Blue'></td>"
			  + "<td>"+houseCaptainBlue+"<input type='hidden' name='studentName' value='"+houseCaptainBlue+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainBlueTRID'));
		
		divCounter++;
		
		$("#captainBlueClassID").val("-1");
		$("#houseCaptainBlueID").val("-1");
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
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainBlueTRID1'));
		
		divCounter++;
		
		$("#houseCaptainBlueID1").val("");
	}
	
</script>  
 
 <script type="text/javascript">
   
	function addhouseCaptainGreenConfigRow(classID, houseCaptain){
		
		if(classID == "-1"){
			alert("Please select student.");
		}else if(houseCaptain == "-1"){
			alert("Please select student.");
		}else{
	
			addhouseCaptainGreenConfigRow1();
		}
	}
    
    var divCounter = 1;
	
	function addhouseCaptainGreenConfigRow1(){
		
		var houseCaptainGreen = $('#houseCaptainGreenID option:selected').text();
		var className = $('#captainGreenClassID option:selected').text();
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+className+"<input type='hidden' name='nameType' value='Green'></td>"
			  + "<td>"+houseCaptainGreen+"<input type='hidden' name='studentName' value='"+houseCaptainGreen+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainGreenTRID'));
				
		divCounter++;
		
		$("#houseCaptainGreenID").val("-1");
		$("#captainGreenClassID").val("-1");
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
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainGreenTRID1'));
				
		divCounter++;
		
		$("#houseCaptainGreenID1").val("");
	}
	
</script>   
    
<script type="text/javascript">
   
	function addhouseCaptainYellowConfigRow(classID, houseCaptain){
		
		if(classID == "-1"){
			alert("Please select class.");
		}else if(houseCaptain == "-1"){
			alert("Please select student.");
		}else{
	
			addhouseCaptainYellowConfigRow1();
		}
	}
    
    var divCounter = 1;
	
	function addhouseCaptainYellowConfigRow1(){
		
		var houseCaptainYellow = $('#houseCaptainYellowID option:selected').text();
		var className = $('#captainYellowClassID option:selected').text();
		
		var trID = "newDIvTRID"+divCounter;
	
		var trTag = "";
		
		trTag += "<tr id='"+trID+"'>"
			  + "<td>"+className+"<input type='hidden' name='nameType' value='Yellow'></td>"
			  + "<td>"+houseCaptainYellow+"<input type='hidden' name='studentName' value='"+houseCaptainYellow+"'></td>"
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainYellowTRID'));
				
		divCounter++;
		
		$("#captainYellowClassID").val("-1");
		$("#houseCaptainYellowID").val("-1");
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
			  + "<td style='text-align:center;'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + trID + "\");'/></td>"
			  + "<tr>";
		
		$(trTag).insertAfter($('#HouseCaptainYellowTRID1'));
				
		divCounter++;
		
		$("#houseCaptainYellowID1").val("");
	}
	
	function removeDivTR(trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
        	$("#"+trID+"").remove();
        	
		}
	}
</script>

<script type="text/javascript">
	function submitForm(subID){
		  $("#loadAcademicFormNew").attr("action","ConfigureVotingDetails");
		
		  	  $("#loadAcademicFormNew").submit(); 
		  	  
		 	 $("#"+subID).attr("disabled", "disabled");
	 }
	
	function submitForm1(subID1){
		  $("#loadAcademicFormNew1").attr("action","UpdateVotingDetails");
		
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
	
	function retrieveStudents(classID, studentDivID){
			
		if(classID == "-1"){
			alert("No class is selected. Please select class.");
				
			var array_element = "<select name='' id='' class='form-control'"+
			"> <option value='-1'>Select Student</option></select>";
				
			document.getElementById(studentDivID).innerHTML = array_element;
		}else{
				
			retrieveStudents1(classID, studentDivID);
		}
	}
	
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		function retrieveStudents1(classID, studentDivID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "<select name='studentID' class='form-control' id='headGirlID'"+
						"> <option value='-1'>Select Student</option>";
						
						var check = 0;
						var StudentID=0;
						
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
							StudentID = array.Release[i].studentID;
							array_element += "<option value='"+StudentID+"'>"+array.Release[i].studentName+"</option>";
						}
	
						array_element += " </select>";
						
						if(check == 0){
							
							alert("No Student found");
							
							var array_element = "<select name='' id='' class='form-control'"+
							"> <option value='-1'>Select Student</option></select>";
							
							document.getElementById(studentDivID).innerHTML = array_element;
						
						}else{
							
							document.getElementById(studentDivID).innerHTML = array_element;	
						}
				}
			};
			xmlhttp.open("GET", "RetrieveStudentDetailsByClassID?ayclassID="
					+ classID+"&status=Student", true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
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
      
        <h2 class="content-header-title">Election List</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Election List</li>
        </ol>
       
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
               	Election List
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
				<center>
					<font style="color: red;font-size:16px;" id="errFontID"><s:actionmessage /></font>
				</center>
		    </div>
		     
             <form id="loadAcademicFormNew" action="ConfigureVotingDetails" method="POST" >		
                <div class="row">
                <input type="hidden" name="academicYearID" value="<%= AcademicYearID%>">
                
                 <div class="col-lg-6">
                        <div class="card" id="AcademicYearDivID" >
                            
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <td><label>Active Academic Year: </label></td>
                                                <td><label><%= AcademicYearName %></label></td>
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
                                        	<th>Class</th>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HeadGirlTRID">
											<td>
												<select id="headGirlClassID" required="required" class="form-control" name="ayclassID" onchange="retrieveStudents(this.value, 'headGirlID');">
													<option value='-1'>Select Class</option> 
												<% for(Integer student : StandardDivisionList.keySet()){ %>
													<option value="<%= student %>"><%= StandardDivisionList.get(student) %></option>
									            <% } %>
							            		</select>
							                </td>
							                <td>
												<select name="studentID" id="headGirlID" class="form-control" >
											    	<option value="-1" >Select Student</option>
												</select>
							                </td>
											<td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
						  					 	onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Head Girl" onclick="addHeadGirlConfigRow(headGirlClassID.value, headGirlID.value);"/>
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
                                        	<th>Class</th>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HeadBoyTRID">
											<td>
												<select id="headBoyClassID" required="required" class="form-control" name="ayclassID" onchange="retrieveStudents(this.value, 'headBoyID');">
													<option value='-1'>Select Class</option> 
												<% for(Integer student : StandardDivisionList.keySet()){ %>
													<option value="<%= student %>"><%= StandardDivisionList.get(student) %></option>
									            <% } %>
							            		</select>
							                </td>
							                <td>
												<select name="studentID" id="headBoyID" class="form-control" >
											    	<option value="-1" >Select Student</option>
												</select>
							                </td>
											
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Head Boy" onclick="addHeadBoyConfigRow(headBoyClassID.value, headBoyID.value);"/>
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
                                        	<th>Class</th>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead> 
                                    <tbody>
                                        <tr id="HouseCaptainRedTRID">
											 <td>
												<select id="captainRedClassID" required="required" class="form-control" name="ayclassID" onchange="retrieveStudents(this.value, 'houseCaptainRedID');">
													<option value='-1'>Select Class</option> 
												<% for(Integer student : StandardDivisionList.keySet()){ %>
													<option value="<%= student %>"><%= StandardDivisionList.get(student) %></option>
									            <% } %>
							            		</select>
							                </td>
							                <td>
												<select name="studentID" id="houseCaptainRedID" class="form-control" >
											    	<option value="-1" >Select Student</option>
												</select>
							                </td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Red House Captain" onclick="addhouseCaptainRedConfigRow(captainRedClassID.value, houseCaptainRedID.value);"/>
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
                                        	<th>Class</th>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainBlueTRID">
											<td>
												<select id="captainBlueClassID" required="required" class="form-control" name="ayclassID" onchange="retrieveStudents(this.value, 'houseCaptainBlueID');">
													<option value='-1'>Select Class</option> 
												<% for(Integer student : StandardDivisionList.keySet()){ %>
													<option value="<%= student %>"><%= StandardDivisionList.get(student) %></option>
									            <% } %>
							            		</select>
							                </td>
							                <td>
												<select name="studentID" id="houseCaptainBlueID" class="form-control" >
											    	<option value="-1" >Select Student</option>
												</select>
							                </td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Blue House Captain" onclick="addhouseCaptainBlueConfigRow(captainBlueClassID.value, houseCaptainBlueID.value);"/>
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
                                        	<th>Class</th>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainGreenTRID">
											 
											 <td>
												<select id="captainGreenClassID" required="required" class="form-control" name="ayclassID" onchange="retrieveStudents(this.value, 'houseCaptainGreenID');">
													<option value='-1'>Select Class</option> 
												<% for(Integer student : StandardDivisionList.keySet()){ %>
													<option value="<%= student %>"><%= StandardDivisionList.get(student) %></option>
									            <% } %>
							            		</select>
							                </td>
							                <td>
												<select name="studentID" id="houseCaptainGreenID" class="form-control" >
											    	<option value="-1" >Select Student</option>
												</select>
							                </td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Green House Captain" onclick="addhouseCaptainGreenConfigRow(captainGreenClassID.value, houseCaptainGreenID.value);"/>
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
                                        	<th>Class</th>
                                           	<th >Students Name</td>
											<th style="text-align:center;">Action</td>
                                         </tr>
                                    </thead>
                                    <tbody>
                                        <tr id="HouseCaptainYellowTRID">
											<td>
												<select id="captainYellowClassID" required="required" class="form-control" name="ayclassID" onchange="retrieveStudents(this.value, 'houseCaptainYellowID');">
													<option value='-1'>Select Class</option> 
												<% for(Integer student : StandardDivisionList.keySet()){ %>
													<option value="<%= student %>"><%= StandardDivisionList.get(student) %></option>
									            <% } %>
							            		</select>
							                </td>
							                <td>
												<select name="studentID" id="houseCaptainYellowID" class="form-control" >
											    	<option value="-1" >Select Student</option>
												</select>
							                </td>
										   
										   <td style="text-align:center;"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
					  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
												title="Add Yellow House Captain" onclick="addhouseCaptainYellowConfigRow(captainYellowClassID.value, houseCaptainYellowID.value);"/>
										  </td>
										</tr>
                                     </tbody>
                                 </table>
                              </div>
                          </div>
                        </div>
                    </div>
                </div>
                
                <div class="row" id="submitFormDivID">
	                <div class="col-md-12" align="center">
			            <button type="button" onclick="windowOpen1();" class="btn btn-success">Cancel</button>
			            <button type="submit" class="btn btn-primary " id="submitID" onclick="submitForm('submitID');">Save</button>  
			        </div>
				</div>
				
              </form>  
             
               <% if(loadAcademicYear.equals("Yes")){ %>
             
            <!-- UpdateConfigureAcademicYear -->
               	<form id="loadAcademicFormNew1" action="UpdateVotingDetails" method="POST" >
               	
               	  <div class="row"> 
					<div class="col-md-6" >
			        </div>
					<% if(VotingStatus.equals("Done")){ 
						
					%>
			          <div class="col-md-3" >
			               <button type="submit" class="btn btn-primary m-b-10 m-l-5" onclick="window.location='ConfigureVotingStatus?academicYearID=<%=AcademicYearID %>&votingStatus=Voting'">Start Voting</button>
					 </div>
							 
				   <% }else if(VotingStatus.equals("Voting")){
					
				%>	
					 <div class="col-md-3" >
			              <button type="submit" class="btn btn-danger m-b-10 m-l-5" onclick="window.location='ConfigureVotingStatus?academicYearID=<%=AcademicYearID %>&votingStatus=Done'">End Voting</button>
					 </div>
			      <% } %>  
			    </div>	
		  
                
                <div class="row" style="margin-top: 15px;">
                 <div class="col-lg-6">
                        <div class="card" >
                            
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                    <input type="hidden" name="academicYearID" value="<%=AcademicYearID %>" id="hiddenID">
                                        <thead>
                                            <tr>
                                                <td><label>Academic Year: </label></td>
                                                <td><s:property value="name" /></td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Head Girl: </td>
                                                <td><a href="javascript:editHeadGirlConfiguration(<%=AcademicYearID %>)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('HeadGirl')">Reports</a></td>
                                            </tr>
                                            <tr>
                                                <td>Head Boy: </td>
                                                <td><a href="javascript:editHeadBoyConfiguration(<%=AcademicYearID %>)">Nominations</a></td>
                                            	<td><a href="javascript:editReportsConfiguration('HeadBoy')">Reports</a></td>
                                            </tr>
                                            <tr>
                                                <td>House Captain (Red): </td>
                                                <td><a href="javascript:editRedConfiguration(<%=AcademicYearID %>)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('Red')">Reports</a></td>
                                            </tr>
                                             <tr>
                                                <td>House Captain (Blue): </td>
                                                <td><a href="javascript:editBlueConfiguration(<%=AcademicYearID %>)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('Blue')">Reports</a></td>
                                            </tr>
                                             <tr>
                                                <td>House Captain (Green): </td>
                                                <td><a href="javascript:javascript:editGreenConfiguration(<%=AcademicYearID %>)">Nominations</a></td>
                                                <td><a href="javascript:editReportsConfiguration('Green')">Reports</a></td>
                                            </tr>
                                             <tr>
                                                <td>House Captain (Yellow): </td>
                                                <td><a href="javascript:editYellowConfiguration(<%=AcademicYearID %>)">Nominations</a></td>
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
             
                <div class="row" id="submitFormDivID1">
	                <div class="col-md-12" align="center">
			            <button type="button" onclick="windowOpen1();" class="btn btn-success">Cancel</button>
			       		<button type="submit" class="btn btn-primary " id="submitID1" onclick="submitForm1('submitID1');">Save</button>
			       </div>
              	</div>
				<%} %>
				
              </form> 
			</div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->

        </div> <!-- /.col -->

      </div> <!-- /.row -->
	 </div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

 <script src="./js/libs/jquery-ui-1.9.2.custom.min.js"></script>

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
