
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@page import="java.util.List"%>
<%@page import="com.kovidRMS.form.*"%>
<%@page import = "java.text.SimpleDateFormat"%>
<%@page import = "java.util.Date"%>
	
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Books List - SkoolUp</title>
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
 
	ul.actionMessage{
            list-style:none;
            font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
     }
        
     ul.errorMessage{
            list-style:none;
            font-family: "Helvetica Neue", Roboto, Arial, "Droid Sans", sans-serif;
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
    	String BookListEnable = (String) request.getAttribute("BookListEnable");
     
     	List<LibraryForm> searchBooksList = (List<LibraryForm>) request.getAttribute("searchBooksList");
     	
     	List<LibraryForm> signedUpBooksList = (List<LibraryForm>) request.getAttribute("signedUpBooksList");
     	
     	String SearchCriteria = (String) request.getAttribute("SearchCriteria");
     	if(SearchCriteria == null || SearchCriteria == ""){
     		SearchCriteria = "dummy";
		}
    %>
    
<script type="text/javascript">

	$(window).load( function() {
		var criteria = '<%=SearchCriteria%>';
	
		console.log("criteria: "+criteria);
		
		if(criteria == "Yes"){
			console.log("inside");
			$("#searchBooksNameID").attr('required', false);
    		$("#searchBooksNameID").removeAttr('name');
    		$("#searchDivID").hide();
    		$("#sectionsID").attr('name', 'searchBooksName');
    		$("#sectionDivID").show();
    	}else{
    		$("#searchBooksNameID").attr('required', true);
    		$("#searchBooksNameID").attr('name', 'searchBooksName');
    		$("#searchDivID").show();
    		$("#sectionsID").removeAttr('name');
    		$("#sectionDivID").hide();
    	}

	});
	
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
	
    	function updateBook(bookID){
			
			$("#tableTRID").find("tr:gt(1)").remove();
	  		
	  		$("#bookhiddenID").val(bookID);
	  		
	  		$("#myModal").modal('show');
	  		
	  		viewEditConfiguration(bookID);
	  	}
    
    </script>

<script type="text/javascript">

function addConfigRow(status, date, bookID){
	
	if(status == "-1"){
		alert("Please select status.");
	}else if(date == ""){
		alert("Please add date.");
	}else{

		addConfigRow1(status, date, bookID);
	}
}

</script>

<script type="text/javascript">

 var divCounter = 1;
	
	function addConfigRow1(status, date, bookID){
		
		var trID = "newDIvTRID"+divCounter;
		
		var trTag = "";
		
		var stringToAppend = "*" + status + "$" + date + "$" + bookID ;
	
		trTag += "<tr id='"+trID+"'>"
			  + "<td style='text-align:center;'>"+status+"<input type='hidden' name='newstatus' value='"+status+"'></td>"
			  + "<td style='text-align:center;'>"+date+"<input type='hidden' name='newdate' value='"+date+"'></td>"
			  + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeDivTR(\"" + stringToAppend + "\",\"" + trID + "\");'/></td>"
			  + "<tr>";
	
		$(trTag).insertAfter($('#divisionTRID'));
			console.log(stringToAppend);	
		//appending values to editConf 
		$('#confID').val($('#confID').val()+stringToAppend);
		
		divCounter++;
		
		$("#subDivID").val("-1");
		$("#statusDateID").val("");
		
		
	}
	
	function removeDivTR(stringToBeRemoved, trID){
		
		if(confirm("Are you sure you want to delete this row?")){
		
    		var configText = $('#confID').val();
    		var newValue = configText.replace(stringToBeRemoved,'');
        	
        	//Updating new value to dummySettingTextID field
        	$('#confID').val(newValue);
        	
        	$("#"+trID+"").remove();	
		}	
	}
    
</script>

<!--View Configuration Edit list from database by bookID  -->
    <script type="text/javascript">
  
    var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
    
    function viewEditConfiguration(bookID){ 
    	
    	xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(AYClassID);
				var array = JSON.parse(xmlhttp.responseText);
				
					var array_element = "";
					
					var check = 0;
					/* For division */
					for ( var i = 0; i < array.Release.length; i++) {
						
						check = array.Release[i].check;
						var status = array.Release[i].status;
						var statusDate = array.Release[i].statusDate;
						var statusID = array.Release[i].statusID;
						
						var trID = "statusTRID"+statusID;
						
						//alert(array.Release[i].subDivID + ' '+array.Release[i].subject);
						array_element += "<tr id='"+trID+"' style='font-size: 14px;'>"
									   + "<td style='text-align:center'>"+status+"</td>"
									   + "<td style='text-align:center'>"+statusDate+"</td>"
									   + "<td align='center'><img src='images/delete.png' style='height:24px;cursor: pointer;' alt='Remove row' title='Remove row' onclick='removeStatusRow(\"" + trID + "\",\"" + statusID + "\");'/></td>"
									   + "</tr>";
					}

					if(check == 0){
						
						$("#myModal").modal("show");
						
					}else{
						
						$(array_element).insertAfter($("#divisionTRID"));
						
						$("#myModal").modal("show");			
					}
					
			}
		};
		xmlhttp.open("GET", "RetrieveStatusByBookID?bookID="
				+ bookID, true);
		xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
		xmlhttp.send();
    	
	}
    
    </script>
    
    <!-- Function to remove StatusRow row based on StatusID -->
    <script type="text/javascript">
    
    	function removeStatusRow(TRID, statusID){
    		if(confirm("Are you sure you want to remove this row?")){
    			removeStatusRow1(TRID, statusID);
    		}
    	}
    
	    var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	    
	    function removeStatusRow1(TRID, statusID){ 
	    	
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
			xmlhttp.open("GET", "RemoveStatusRow?statusID="
					+ statusID, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
	    	
		}
    
    </script>
    <!--End -->
   
    <script type="text/javascript">
	    function viewSchoolSections(selectVal){ 
	    	if(selectVal == "Sections"){
	    		$("#searchBooksNameID").attr('required', false);
	    		$("#searchBooksNameID").removeAttr('name');
	    		$("#searchDivID").hide();
	    		$("#sectionsID").attr('name', 'searchBooksName');
	    		$("#sectionDivID").show();
	    	}else{
	    		$("#searchBooksNameID").attr('required', true);
	    		$("#searchBooksNameID").attr('name', 'searchBooksName');
	    		$("#searchDivID").show();
	    		$("#sectionsID").removeAttr('name');
	    		$("#sectionDivID").hide();
	    	}
	    }
	    
	    function checkAcademicYear(yearVal, subID){
	    	if(yearVal == "-1"){
	    		alert("Please select Academic Year.");
	  			return false;
	  			
	  		}else{
	  			
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
		    	
	  			$("#validate-basic").attr("action","RenderEditBooksList");
	  			$("#validate-basic").submit();
	  			
	  			$("#"+subID).attr("disabled", "disabled");
	  		}
	    }
    </script>
    
</head>

<body>
<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<!-- MOdal to add new subject teacher configuration -->
	<div id="myModal" class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				Update Status				
			</div>
			<form id="loadStudentFOrm" action="UpdateBook" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
			<div class="modal-body">
				<table border="1" id="tableTRID">
					<tr>
						<td style="text-align: center;">Status</td>
						<td style="text-align: center;">Date</td>
						<td>Action</td>
					</tr>
					<tr id="divisionTRID">
					    <td >
					        <div class="form-group" id="statusDIvID">
					        <input type="hidden" id="bookhiddenID" name="bookID">
								<select class="form-control" id="subDivID">
								    <option value="-1">Select Status</option>
								  	<option value="available">Available</option>
							     	<option value="on-hold">On-hold</option>
							     	<option value="issued">Issued</option>
							     	<option value="withdrawn">Withdrawn</option>
							     	<option value="damaged">Damaged</option>
							     	<option value="lost">Lost</option>
							     	<option value="maintenance">Maintenance</option>
							     </select>
							</div>
					    </td>
					    
					    <td>
					    <%
						    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
							SimpleDateFormat dateToBeFormatted = new SimpleDateFormat("yyyy-MM-dd");
							
							Date date = new java.util.Date();
							
							String NewDate = dateFormat.format(date);
						
					    %>
					    	<div class="form-group" id="dateDIvID">
					    		<div class="input-group date ui-datepicker">
			                      <input type="text" id="statusDateID" name="" class="form-control" 
			                      value="<%= NewDate %>" placeholder="Status Date(dd/mm/yyyy)" data-required="true">
			                      <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			                  	</div>
			               </div>
					    </td>
					   <td align="center"><img src="images/addBill.png" style="height:24px;margin-top:5px;cursor: pointer;" 
  							onmouseover="this.src='images/addBill1.png'" onmouseout="this.src='images/addBill.png'" alt="Add Configuration"
								title="Add Configuration" onclick="addConfigRow(subDivID.value, statusDateID.value, bookhiddenID.value);"/>
					  </td>
					</tr>
				</table>
				
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-success" >Add</button>
				<button type="button" data-dismiss="modal">Close</button>
			</div>
			</form>
		</div>
	</div>
	</div>
	<!-- End -->
	
<div class="container">

  <div class="content" style="min-height: 0px;">

    <div class="content-container" style="z-index: 0;">

      <div class="content-header">
        <h2 class="content-header-title">Books List</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Books List</li>
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
            
            	<form action="SearchBooks" method="POST" id="validate-basic" onsubmit="submitForm('validate-basic', 'submitID', 'SearchBooks');">
            		<div class="row">
            		
            		   <div class="col-md-2 col-sm-3 col-xs-6">
                    		<s:select list="AcademicYearNameList" class="form-control" headerKey="-1" headerValue="Select Academic Year" required="required" name="academicYearID" id="YearID"></s:select>
            		   </div>
                       <div class="col-md-2 col-sm-3 col-xs-6">
                    		<s:select list="#{'BookName':'Book Name','Sections':'Sections','AuthorName':'Author Name','Genre':'Genre','Type':'Type','AccessionNumber':'Accession Number','Barcode':'Barcode','Status':'Status'}" 
                    		class="form-control" headerValue="Select Criteria" required= "required" name="searchCriteria" onchange="viewSchoolSections(this.value);"></s:select>
                       </div>
                        
                       <div class="col-md-2 col-sm-3 col-xs-6" style="display:none;" id="sectionDivID" >
                    		<s:select list="#{'primary':'For primary','S -':'For secondary','TC -':'For teachers', 'D ':'For donated', 'P P ':'For pre-primary','P. P. D.':'Donated for pre-primary', 'CD_primary':'For CD & DVD primary', 'P. D.':'Donated for primary', 'CD_secondary':'For CD & DVD secondary', 'S. D. ':'Donated for secondary'}" 
                  	 		class="form-control" id="sectionsID" headerValue="Select Criteria" name="schoolSection" ></s:select>
                       </div>
                     
                       <div class="col-md-3" id="searchDivID">
                         <input type="text" class="form-control" name="searchBooksName" style="width:100%;" id ="searchBooksNameID" placeholder="Search Books" required="required">
                       </div>
                       
                       <div class="col-md-2">
                          <button type="submit" class="btn btn-success" id="submitID">Search Books</button>
                        </div>
                        <div class="col-md-2">
                         <button type="button" class="btn btn-warning" id="viewSubmitID" onclick="checkAcademicYear(YearID.value, 'viewSubmitID');">View All Books</button>
                        </div>
                    </div>
             	</form>      
				<%
                  	if(BookListEnable == null || BookListEnable == ""){
                  %>
                  
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
		             
		            <%
                  		}else if(BookListEnable.equals("userSearchListEnable")){
		            %> 
		            
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
										
		      <div class="table-responsive" style="margin-top:15px;">
              <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                       <th data-sortable="true">ID</th>
                   	   <th data-sortable="true" style="width: 150px;">Accession Number</th>
                   	   <th data-sortable="true">Book Name</th>
                       <th data-sortable="true">Author</th>
                       <th data-sortable="true">Genre</th>
                       <th data-sortable="true">Type</th>
                       <th data-sortable="true">Barcode</th>
                       <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                 <%-- <s:iterator value="searchUserList" var="UserForm"> --%>
                   <% for(LibraryForm formVal:searchBooksList){ %>
                    <tr>
                    	<td><%=formVal.getBookID() %></td>
                    	
                     	<td><%=formVal.getAccNum() %></td>
                     	
                     	<td><%=formVal.getName() %></td>
						
						<td><%=formVal.getAuthor() %></td>
						
						<td><%=formVal.getGenre() %></td>
						
						<td><%=formVal.getType() %></td>
						
						<td><%=formVal.getBarcode() %></td>
										
						<td align="center">
							
							<a href="RenderEditBook?BookID=<%=formVal.getBookID() %>">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
										onmouseout="this.src='images/user_1.png'" alt="Edit Book" title="Edit Book" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
							
							<a id="confID" href="javascript:updateBook('<%=formVal.getBookID() %>')" >
									<img src="images/update.png" style="height:20px;" onmouseover="this.src='images/update.png'"
										onmouseout="this.src='images/update.png'" alt="Update Status" title="Update Status" />
							</a>
							
						</td>
                       </tr>
                       <%} %>
                   </tbody>
                </table>
                </div>
                
                <%
                  	}else if(BookListEnable.equals("BookListEnable")){
                   %>
                   
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
		        <div class="table-responsive" style="margin-top:15px;">
		        <table class="table table-striped table-bordered table-hover table-highlight table-checkable" 
                data-provide="datatable" data-display-rows="10" data-info="true" data-search="true" data-length-change="true"
                data-paginate="true" >
                  <thead>
                    <tr>
                       <th data-sortable="true">ID</th>
                   	   <th data-sortable="true" style="width: 150px;">Accession Number</th>
                   	   <th data-sortable="true">Book Name</th>
                       <th data-sortable="true">Author</th>
                       <th data-sortable="true">Genre</th>
                       <th data-sortable="true">Type</th>
                       <th data-sortable="true">Barcode</th>
                       <th style="text-align:center;">Action</th>
                  	</tr>
                  </thead>
                  <tbody>
                   <%-- <s:iterator value="signedUpUserList" var="UserForm"> --%>
                    <% for(LibraryForm formVal:signedUpBooksList){ %>
                    <tr>
                    	<td><%=formVal.getBookID() %></td>
                    	
                     	<td><%=formVal.getAccNum() %></td>
						
						<td><%=formVal.getName() %></td>
						
						<td><%=formVal.getAuthor() %></td>
						
						<td><%=formVal.getGenre() %></td>
						
						<td><%=formVal.getType() %></td>
						
						<td><%=formVal.getBarcode() %></td>
										 
						<td align="center">
							
							<a href="RenderEditBook?BookID=<%=formVal.getBookID() %>">
								<img src="images/user_1.png" style="height:24px;" onmouseover="this.src='images/user_2.png'"
									onmouseout="this.src='images/user_1.png'" alt="Edit Book" title="Edit Book" />
							</a>&nbsp;&nbsp;&nbsp;&nbsp;
							
							<a id="confID" href="javascript:updateBook('<%=formVal.getBookID() %>')" >
									<img src="images/update.png" style="height:20px;" onmouseover="this.src='images/update.png'"
										onmouseout="this.src='images/update.png'" alt="Update Status" title="Update Status" />
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
  
</body>
</html>
