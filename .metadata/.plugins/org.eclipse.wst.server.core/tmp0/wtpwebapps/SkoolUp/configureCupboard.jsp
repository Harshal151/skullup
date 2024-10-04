<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="java.util.*"%>
    <%@page import="com.kovidRMS.daoInf.*"%>
    <%@page import="com.kovidRMS.daoImpl.*"%>
    <%@page import="com.kovidRMS.form.*"%>
      
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Configure Cupboard & Shelves - SkoolUp</title>
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

 <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
 
  <script type="text/javascript">
  $(document).ready(function(){
		$('#inventoryLiID').addClass("active");
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
          	left: 50%;
           	top: 25%;
         	margin-left: -50px; /*half the image width*/
          	margin-top: 10%; /*half the image height*/
          	color: black;
           	z-index: 1;
            display: none;
    	} 
  </style>

  <%

	  	LibraryDAOInf daoInf = new LibraryDAOImpl();
	
		String componentMsg = (String) request.getAttribute("componentMsg");
		if(componentMsg == null || componentMsg == ""){
			componentMsg = "dummy";
		}
		
	  	List<LibraryForm> CupboardList = (List<LibraryForm>) request.getAttribute("CupboardList");
	  	List<LibraryForm> ShelfList = (List<LibraryForm>) request.getAttribute("ShelfList");
	  
	  	String CupboardListCheck = (String) request.getAttribute("CupboardListCheck");
	  	
	  	if(CupboardListCheck == null || CupboardListCheck == ""){
	  		CupboardListCheck = "dummy";
		}
	  	
		String ShelfListCheck = (String) request.getAttribute("ShelfListCheck");
	  	
	  	if(ShelfListCheck == null || ShelfListCheck == ""){
	  		ShelfListCheck = "dummy";
		}
  	
  %>
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

    </script>
    
<script type="text/javascript">
	
   function updateCupboard(cupboardID, cupboardName){
		
		$("#cupboardNameID").val(cupboardName);
		$("#editCupboardNameID").val(cupboardName);
		$("#cupboardhiddenID").val(cupboardID);
		$("#ShelfcupboardhiddenID").val(cupboardID);
		$("#updateID").show();
		$("#addCupboardID").hide();
		$("#ShelfClassID").show();
		
		$("#shelfID").val("");
		$("#genreID").val("-1");
		
		retrieveShelfList(cupboardID);
	}
   
   function updateShelf(shelfID, shelfName, shelfGenre){
	    
		$("#ShelfhiddenID").val(shelfID);
		$("#editShelfhiddenID").val(shelfName);
		$("#shelfID").val(shelfName);	
		$("#genreID").val(shelfGenre);
		$("#updateShelfID").show();
		$("#addShelfID").hide();
		$("#ShelfClassID").show();
	}
   
	</script>

<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		function CupboardCheck(cupboardName) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var check=0;
					var cupboardID = 0;
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						cupboardID = array.Release[i].cupboardID
					}
					
					if(check == 0){
						
						$("#addCupboardID").show();
						$("#updateID").hide();
						
					}else{
						
						alert("Entered cupboard Name already exist.");
						
						$("#cupboardNameID").val(cupboardName);
						$("#editCupboardNameID").val(cupboardName);
						$("#cupboardhiddenID").val(cupboardID);
						$("#ShelfcupboardhiddenID").val(cupboardID);
						$("#updateID").show();
						$("#addCupboardID").hide();
						$("#ShelfClassID").show();
						
						$("#shelfID").val("");
						$("#genreID").val("-1");
						  
						retrieveShelfList(cupboardID);
					}
				}
			};
			xmlhttp.open("GET", "RetrieveCupboardName?name="
					+ cupboardName, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		
		}
	
</script>
	
<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		function ShelfCheck(shelfName, cupboardID) {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					
					var array = JSON.parse(xmlhttp.responseText);
					
					var check=0;
					var cupboard;
					var shelfID;
					var shelfGenre;
					
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						shelfID = array.Release[i].shelfID
						shelfGenre = array.Release[i].genre; 
						cupboard = array.Release[i].cupboard;
					}
					
					if(check == 0){
						
						$("#updateShelfID").hide();
						$("#addShelfID").show();
						
					}else{
						
						alert("Entered shelf Name is already exist for cupboard "+cupboard+".");
						
						$("#ShelfhiddenID").val(shelfID);
						$("#editShelfhiddenID").val(shelfName);
						$("#shelfID").val(shelfName);	
						$("#genreID").val(shelfGenre);
						$("#updateShelfID").show();
						$("#addShelfID").hide();
						$("#ShelfClassID").show();
					}
				}
			};
			xmlhttp.open("GET", "RetrieveShelfName?name="
					+ shelfName + "&cupboardID=" + cupboardID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		
		}
		
</script>
	
	<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveShelfList(cupboardID) {
		
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);

					var teacherName = "";
					
					var check = 0;
					
					
					var tableTag = "<div class='col-md-6 col-lg-6 col-sm-6 col-xs-6' style='margin-top:5%;'><table class='table table-striped table-bordered table-hover table-highlight table-checkable' "
	                			+"data-provide='datatable' data-display-rows='10' data-info='true' data-search='true' data-length-change='true'"
	                    		+"data-paginate='true' border='1' style='font-size: 14px;'>"
								+ "<thead><tr style='padding:5px;'><th data-sortable='true'></th><th data-sortable='true'>Shelves</th><th data-sortable='true'>Genre</th>"
								+ "<th style='text-align:center;'>Action</th></tr></thead><tbody>";
								 
					for ( var i = 0; i < array.Release.length; i++) {

						var ShelfID = array.Release[i].shelfID;
						var Shelf = array.Release[i].shelfName;
						var Genre = array.Release[i].genreName;
						check = array.Release[i].check;
						
						tableTag += "<tr id='ShelfTR"+ShelfID+"'><td><input type='radio' name='radioValue' id ='checkboxID' onchange='updateShelf(\"" + ShelfID + "\",\"" + Shelf + "\",\"" + Genre + "\")'></td>";

						tableTag += "<td>"+Shelf+"</td>";
	                     	
						tableTag += "<td>"+Genre+"</td>";
							 	
						tableTag += "<td align='center'><img src='images/delete_icon_1.png' style='height:20px; cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeShelfTR(\"" + ShelfID + "\", \"ShelfTR" + ShelfID + "\");'/></td>"
						tableTag += "</tr>";
					}
					
					tableTag += "</tbody></table><div class='col-md-6 col-lg-6 col-sm-6 col-xs-6' style='margin-top:15px;'>";
					
					if(check == 0){
						
						alert("No Shelves added. Please add shelves");
					
						$("#divTableID").html("");
						
					}else{
						$("#divTableID").html(tableTag);
					}
				
				}
					
			};
			xmlhttp.open("GET", "RetrieveShelfDetails?cupboardID="
					+ cupboardID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
		}
		
	</script>
	
	<script type="text/javascript">
    
    	function removeShelfTR(shelfID, TRID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeShelfRow1(shelfID, TRID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeShelfRow1(shelfID, TRID){ 
	    	
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
			xmlhttp.open("GET", "RemoveShelfRow?shelfID="
					+ shelfID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
	    	
		}
    
	    function removeCupboard(cupboardID, TRID){
    		if(confirm("Please ensure that shelf details are removed for this cupboard, then only you can remove this row. Are you sure you want to remove this row ?")){
    			removeCupboardRow1(cupboardID, TRID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeCupboardRow1(cupboardID, TRID){ 
	    	
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
			xmlhttp.open("GET", "RemoveCupboardRow?cupboardID="
					+ cupboardID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
	    	
		}
	    
	    
	    function submitForm(subID, action){  
	    	  $('html, body').animate({
			        scrollTop: $('body').offset().top
			    }, 1000);
				
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
		
	    	 $("#validate-basic").attr("action", action);
			  	  $("#validate-basic").submit(); 
			  	  
		 	 	 $("#"+subID).attr("disabled", "disabled");
		 	 	 
		 	 	 $("html, body").animate({ scrollTop: 0 }, "fast");
		    		$(".loadingImage").show();
		    		$(".container").css("opacity","0.1");
		    		$(".navbar").css("opacity","0.1");
		    		return true;
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
        <h2 class="content-header-title">Configure Cupboard & Shelves</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Cupboard & Shelves</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                Cupboard & Shelves List
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
		              
	           <div class ="row">   
                <form id="validate-basic" action="ConfigureCupboard" method="POST" data-validate="parsley" onsubmit="submitForm('submitID', 'ConfigureCupboard');" enctype="multipart/form-data" class="form parsley-form">

                <div class ="col-md-2">
                  <label for="name">Add Cupboard :</label>
                 </div>
                 <div class ="col-md-4">
                 	<input type="hidden" id="cupboardhiddenID" name="cupboardID">
                 	<input type="hidden" id="editCupboardNameID" name="editName" value="Add">
                  	<input type="text" name="name" id= "cupboardNameID" placeholder="Cupboard Name" onkeyup="CupboardCheck(this.value)"
                  	 required="required" class="form-control" data-required="true" >
                 </div>
              
				<div class ="col-md-6" id ="addCupboardID">
				  <button type="submit" id="submitID" class="btn btn-success">Add Cupboard</button>
                </div>
               <div class ="col-md-6" id ="updateID" style="display: none;">
				  <button type="submit" id="submitID" class="btn btn-success">Update Cupboard</button>
                </div> 
                
              </form>
		     </div> 
		      <%
		        if(CupboardListCheck.equals("Yes")){
	          %>
	          <div class="row">
	          
	          <div class="col-md-6 col-lg-6 col-sm-6 col-xs-6" style="margin-top:5%;">
              <table class="table table-striped table-bordered table-hover table-highlight table-checkable" data-info="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                    	<th data-sortable="true"></th>
                    	<th data-sortable="true">Cupboard Name</th>
                   	   	<th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                 <%-- <s:iterator value="searchUserList" var="UserForm"> --%>
                   <% for(LibraryForm form:CupboardList){ %>
                    <tr id="<%=form.getCupboardID() %>">
                    	<td><input type="radio" name="radioValue" id ="checkboxID" onchange="updateCupboard('<%=form.getCupboardID()%>', '<%=form.getName() %>')"></td>
                     	<td><%=form.getName() %></td>
                     	
						<td align="center">
							
							<a id="confID" href="javascript:removeCupboard('<%=form.getCupboardID() %>', '<%=form.getCupboardID() %>')" >
								<img src="images/delete_icon_1.png" style="height:20px;" onmouseover="this.src='images/delete_icon_2.png'"
									onmouseout="this.src='images/delete_icon_1.png'" alt="Delete Cupboard" title="Delete Cupboard" />
							</a>
						</td>
                    </tr>
                  <%} %>
                  
                   </tbody>
                </table>
                </div>
                <div class="col-md-6 col-lg-6 col-sm-6 col-xs-6"></div>
              </div>
                
             <%} %>
                
             <hr>
            <div class="form-group" id="ShelfClassID" style="display: none;"> 
             <div class ="row">   
                <form id="validate-basic" action="ConfigureShelf" method="POST" data-validate="parsley" enctype="multipart/form-data" onsubmit="submitForm('submitID', 'ConfigureShelf');" class="form parsley-form">

                <div class ="col-md-2">
                  <label for="name">Add Shelf<span class="required">* : </span></label>
                 </div>
                 <div class ="col-md-2">
                 	<input type="hidden" id="ShelfcupboardhiddenID" name="cupboardID">
                 	<input type="hidden" id="ShelfhiddenID" name="shelfID">
                 	<input type="hidden" id="editShelfhiddenID" name="editName" value="Add">
                  	<input type="text" name="name" id= "shelfID" placeholder="Shelf Name" required="required" onkeyup="ShelfCheck(this.value, ShelfcupboardhiddenID.value)"
                  		class="form-control" data-required="true" >
                 </div>
              
              	<div class ="col-md-2">
                  <s:select list="GenreList" class="form-control" id="genreID" value="-1" headerKey="-1" headerValue="Select Genre" name="genre"></s:select>
                </div>
                 
				<div class ="col-md-6" id ="addShelfID">
				  <button type="submit" id="submitID" class="btn btn-success">Add Shelf</button>
                </div>
               <div class ="col-md-6" id ="updateShelfID" style="display: none;">
				  <button type="submit" id="submitID" class="btn btn-success">Update Shelf</button>
                </div> 
                
              </form>
		     </div> 
		     
	          <div class="row" id="divTableID">
             
	         </div>
	                   
			</div> <!-- /.portlet-content -->

          </div> <!-- /.portlet -->

        </div> <!-- /.col -->

      </div> <!-- /.row -->
	 </div> <!-- /.content-container -->
      
  </div> <!-- /.content -->

</div> <!-- /.container -->

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
