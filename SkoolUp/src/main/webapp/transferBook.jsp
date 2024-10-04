<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@page import="java.util.HashMap"%>
    <%@page import="com.kovidRMS.daoInf.*"%>
    <%@page import="com.kovidRMS.daoImpl.*"%>
    <%@page import="com.kovidRMS.form.*"%>
    <%@page import="java.util.*"%>
      
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Move Books - SkoolUp</title>
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
	<link href="css/jquery.multiselect.css" rel="stylesheet" type="text/css">

	<link  rel="stylesheet" type="text/css" href="css/bootstrap-3.0.3.min.css" />
	<link  rel="stylesheet" type="text/css" href="css/bootstrap-multiselect.css" />
  <!-- Plugin CSS -->
  <link rel="stylesheet" href="./js/plugins/morris/morris.css">
  <link rel="stylesheet" href="./js/plugins/icheck/skins/minimal/blue.css">
  <link rel="stylesheet" href="./js/plugins/select2/select2.css">
  <link rel="stylesheet" href="./js/plugins/fullcalendar/fullcalendar.css">

  <!-- App CSS -->
  <link rel="stylesheet" href="./css/target-admin.css">
  <link rel="stylesheet" href="./css/custom.css">

  <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"> </script>
  
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
  
  $(document).ready(function(){
		$('#inventoryLiID').addClass("active");
		//alert("hiii");	
	});
  
  </script>
  
  <%
	
  	List<LibraryForm> BooksDetailsList = (List<LibraryForm>) request.getAttribute("BooksDetailsList");
	
	String loadBooks = (String) request.getAttribute("loadBooks");
	
	if(loadBooks == null || loadBooks == ""){
		loadBooks = "dummy";
	}
	

  %>
    
<script type="text/javascript">
	
	function windowOpen(){
	    document.location="welcome.jsp";
	}
	
   function movecheckbox(bookID){
	   
	   $("#MoveBookDivID").show();
		
		if (document.getElementById('checkboxValueID').checked){
			
			var HiddenID = $("#hiddenCheckboxID").val();
			
			var StringToAppended = HiddenID + "," + bookID;
			
			$("#hiddenCheckboxID").val(StringToAppended);
			
		}else{
			 
			var currentString = ","+bookID
			
			var HiddenID = $("#hiddenCheckboxID").val();
			
			var NewString = HiddenID.replace(currentString, "");
			
			$("#hiddenCheckboxID").val(NewString);
		}
		
		
	}
   
</script>

<script type="text/javascript">
	
		function retrieveShelves(cupboardID){
			
			if(cupboardID == "-1"){
				alert("No cupboard is selected. Please select cupboard.");
				
				var array_element = "<select name='' id='' class='form-control'"+
				"> <option value='-1'>Select Shelf</option></select>";
				
				document.getElementById("ShelvesID").innerHTML = array_element;
	
			}else{
				
				retrieveShelves1(cupboardID);
			}
			
		}
	
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveShelves1(cupboardID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "<select name='shelfID' class='form-control'"+
						"> <option value='-1'>Select Shelf</option>";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
	
							array_element += "<option value='"+array.Release[i].shelfID+"'>"+array.Release[i].shelfName+"</option>";
						}
	
						array_element += " </select>";
						
						if(check == 0){
							
							alert("No Shelf found");
							
							var array_element = "<select name='' id='' class='form-control'"+
							"> <option value='-1'>Select Shelf</option></select>";
							
							document.getElementById("ShelvesID").innerHTML = array_element;
							
						}else{
							
							document.getElementById("ShelvesID").innerHTML = array_element;	
														
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
	
		function retrieveMoveShelves(cupboardID){
			
			if(cupboardID == "-1"){
				alert("No cupboard is selected. Please select cupboard.");
				
				var array_element = "<select name='' id='' class='form-control'"+
				"> <option value='-1'>Select Shelf</option></select>";
				
				document.getElementById("ShelvesMoveID").innerHTML = array_element;
	
			}else{
				
				retrieveMoveShelves1(cupboardID);
			}
			
		}
	
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	
		function retrieveMoveShelves1(cupboardID) {
	
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
						var array_element = "<select name='shelfID' class='form-control'"+
						"> <option value='-1'>Select Shelf</option>";
						
						var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
	
							array_element += "<option value='"+array.Release[i].shelfID+"'>"+array.Release[i].shelfName+"</option>";
						}
	
						array_element += " </select>";
						
						if(check == 0){
							
							alert("No Shelf found");
							
							var array_element = "<select name='' id='' class='form-control'"+
							"> <option value='-1'>Select Shelf</option></select>";
							
							document.getElementById("ShelvesMoveID").innerHTML = array_element;
							
						}else{
							
							document.getElementById("ShelvesMoveID").innerHTML = array_element;	
														
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
	function checkBooks(cupboardID, shelfID){
    	if(cupboardID == "-1" ){
    		alert("Please select cupboard.");
    		return false;
    	}else if(shelfID == "-1" ){
  			alert("Please select shelf.");
  			return false;
  		}else{
  			$("#validate-basic").attr("action","LoadBooksDetails");
  			$("#validate-basic").submit();
  			return true;
  		}
      }
	
	
	function checkMoveBooks(cupboardMoveID, shelfMoveID, checkboxValue){
    	if(cupboardMoveID == "-1" ){
    		alert("Please select cupboard.");
    		return false;
    	}else if(shelfMoveID == "-1" ){
  			alert("Please select shelf.");
  			return false;
  		}else if(checkboxValue == "" ){
  			alert("Please select checkBox.");
  			return false;
  		}else{
  			$("#validate-basic").attr("action","MoveBooks");
  			$("#validate-basic").submit();
  			return true;
  		}
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
        <h2 class="content-header-title">Move Books</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Move Books</li>
        </ol>
      </div> <!-- /.content-header -->

      <div class="row">

        <div class="col-md-12">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-table"></i>
                Books List
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
                <form id="validate-basic" action="LoadBooksDetails" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

                <div class="col-md-2">
                  <label for="author">Cupboard :</label>
                </div>
                <div class="col-md-3">
                    <s:select list="CupboardDetailsList" id="CupboardID" class="form-control" onchange="retrieveShelves(this.value);"
                    headerKey="-1" headerValue="Select Cupboard" name="cupboardID"></s:select>
                </div>
                
                <%
			
					if(loadBooks.equals("Enabled")){
				%>
				
				 <div class="col-md-2">
                  	<label for="author">Shelves :</label>
                 </div>
                <div class="col-md-3">	
               		<s:select list="ShelvesDetailsList" id="ShelvesID" class="form-control"  headerKey="-1" headerValue="Select Shelf" name="shelfID"></s:select>
		        </div>
		        
				<%
					}else{
				%>
                <div class="col-md-2">
                  	<label for="author">Shelves :</label>
                </div>
                <div class="col-md-3">	
                    <select name="shelfID" id="ShelvesID" class="form-control">
			              <option value="-1">Select Shelf</option>
			        </select>
		       </div>
		       
		       <%} %>
		       
		      <div class="col-md-2">
				<button type="submit" class="btn btn-warning" onclick="return checkBooks(CupboardID.value, ShelvesID.value)">Load Books</button>
			  </div>
                
              </form>
		     </div> 
		     
	         <hr>
			
			<div class="tab-pane fade in active">
				<%
			
					if(loadBooks.equals("Enabled")){
						
				%>
				
				<div class="row" style="background:white;margin-left:0px;margin-right:0px;margin-bottom:20px;padding-top: 20px;">
		          
		              <table id="" class="table table-striped table-bordered dt-responsive nowrap" cellspacing="0" width="100%">
	                     <thead>
	                        <tr>
	                          <th></th>
	                          <th data-sortable="true" style="text-align: center;">Accession Number</th>
		                   	  <th data-sortable="true" style="text-align: center;">Book Name</th>
		                      <th data-sortable="true" style="text-align: center;">Genre</th>
		                      <th data-sortable="true" style="text-align: center;">Section</th>
		                      <th data-sortable="true" style="text-align: center;">Type</th>
	                          
	                      	</tr>
	                      </thead>
	                  <tbody>
	                      
	                   <% 
				       		for(LibraryForm form : BooksDetailsList){
				       			
				       %>
				        
				       <tr id="newTRID<%=form.getBookID()%>" style="font-size: 14px;width: 6%;">
				       		
				       		<td style="text-align: center;width: 6%;">
				       			<input type="checkbox" name="checkboxValue" id ="checkboxValueID" value="<%=form.getBookID()%>" onchange="movecheckbox(this.value)">
				       		</td>
				       		
				       		<td style="text-align: center;width: 20%;">
				       			<%=form.getAccNum()%>
				       		</td>	
				       		
				       		<td style="text-align: center;width: 20%;">
				       			<%=form.getName()%>
				       		</td>
				       	
				       		<td style="text-align: le;width: 20%;">
				       			<%=form.getGenre()%>
				       		</td>	
				       		
				       		<td style="text-align: center;width: 20%;">
				       			<%=form.getSection()%>
				       		</td>
				       		
				       		<td style="text-align: center;width: 20%;">
				       			<%=form.getType()%>
				       		</td>
				       </tr> 
				        
				        <% 
				        	}
				        %>
	                  	</tbody>
	                    </table>
		                
			        </div>
			       <%
						}
			       %>
			      
				 </div>
             
            <div id="MoveBookDivID" style="display: none;">   
            <hr>
				<div class="row" >
              	<label for="author" style="padding-left: 1%;color:  blue;">Move To :</label>
              </div>
              <form id="validate-basic" action="MoveBooks" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
              <div class="row">
                <div class="col-md-2">
                  <label for="author">Cupboard :</label>
                </div>
                <div class="col-md-3">
                	<input type="hidden" id ="hiddenCheckboxID" name="checkboxValue">
                    <s:select list="CupboardDetailsList" id="CupboardMoveID" class="form-control" value="-1" onchange="retrieveMoveShelves(this.value);"
                    headerKey="-1" headerValue="Select Cupboard" name="cupboardID"></s:select>
                </div>
                
                 <div class="col-md-2">
                  	<label for="author">Shelves :</label>
                 </div>
                <div class="col-md-3">	
                    <select name="shelfID" id="ShelvesMoveID" class="form-control">
			              <option value="-1">Select Shelf</option>
			        </select>
		       </div>
		       
		      <div class="col-md-2">
				<button type="submit" class="btn btn-warning" onclick="return checkMoveBooks(CupboardMoveID.value, ShelvesMoveID.value, hiddenCheckboxID.value)">Move Books</button>
			  </div>
             </div>   
              </form>
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
