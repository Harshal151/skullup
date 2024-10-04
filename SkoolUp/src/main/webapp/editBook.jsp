<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Edit Books - SkoolUp</title>
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
		
        document.location="RenderEditBooksList";
    }
	
	function windowOpen2(){
		
		$('html, body').animate({
	        scrollTop: $('body').offset().top
	   }, 1000);
		
		$('body').css('background', '#FCFAF5');
		$(".container").css("opacity","0.2");
		$("#loader").show();
		$("body").find("button").attr("disabled","disabled"); 
		
        document.location="RenderEditBooksList";
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
 
</head>

<body>

<img src="images/Preloader_2.gif" alt="Loading Icon" class = "loadingImage" id="loader">

<!-- icluding top menu bar -->
<jsp:include page="topMenu.jsp"></jsp:include>

<div class="container">

  <div class="content">

    <div class="content-container" style="z-index: 0;">

	<div class="content-header">
        <h2 class="content-header-title">Manage Books</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li><a onclick="windowOpen1();">Books Details</a></li>
          <li class="active">Manage Book</li>
        </ol>
      </div> <!-- /.content-header -->

    <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                Manage Books Details
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
			
			<s:iterator value="signedUpBooksList" var="UserForm">
			
              <form id="loadStudentFOrm" action="EditBook" method="POST" onsubmit="submitForm('loadStudentFOrm', 'submitID', 'EditBook');" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">
				
				<input type="hidden" name="BookID" value="<s:property value="BookID"/>">
				
               <div class="form-group">
                  <label for="name">Book Name<span class="required">*</span></label>
                  <input type="text" id="name" placeholder="Book Name" required="required" name="name" value="<s:property value="name"/>" class="form-control" data-required="true" >
                </div>

             	<div class="form-group">
                  <label for="author">Author Name</label>
                  <input type="text" name="author" value="<s:property value="author"/>" placeholder="Author Name" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="author">Genre</label>
                    <s:select list="GenreList" class="form-control" id="genreID" headerKey="-1" headerValue="Select Genre" name="genre"></s:select>
                </div>

               <div class="form-group">
                  <label for="publication">Publication</label>
                  <input type="text" name="publication" value="<s:property value="publication"/>" class="form-control" placeholder="Publication" data-required="true" >
                </div>
				
				<div class="form-group">
                  <label for="edition">Edition</label>
                  <input type="text" name="edition" value="<s:property value="edition"/>" class="form-control" placeholder="Edition" data-required="true" >
                </div>
               
               <div class="form-group">
                  <label for="name">Accession Number</label>
                  <input type="text" id="accNumID" placeholder="Accession Number" name="accNum" value="<s:property value="accNum"/>" class="form-control" data-required="true" >
                </div>

             	<div class="form-group">
                  <label for="author">Pages</label>
                  <input type="number" name="pages" value="<s:property value="pages"/>" placeholder="Pages" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="genre">Description</label>
                  <input type="text" name="description" value="<s:property value="description"/>" class="form-control" placeholder="Description" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="publication">Barcode</label>
                  <input type="text" name="barcode" value="<s:property value="barcode"/>" class="form-control" placeholder="Barcode" data-required="true" >
                </div>
				
				<div class="form-group">
                  <label for="edition">Publication Year</label>
                  <input type="text" name="publicationYear" value="<s:property value="publicationYear"/>" class="form-control" placeholder="Publication Year" data-required="true" >
                </div>
                 
                <div class="form-group">
	               <label for="name">Register Date</label>
	                <div class="input-group date ui-datepicker">
	                	<input type="text" name="regDate" value="<s:property value="regDate"/>" class="form-control" placeholder="Register Date(dd/mm/yyyy)" data-required="true">
				        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                </div>
                </div>

             	<div class="form-group">
                  <label for="author">Status</label>
                    <s:select list="#{'available':'Available','on-hold':'On-hold','issued':'Issued','withdrawn':'Withdrawn','damaged':'Damaged','lost':'Lost','maintenance':'Maintenance'}" 
                    class="form-control" headerKey="-1" headerValue="Select Status" name="Status"></s:select>
                </div>

                <div class="form-group">
	                <label for="genre">Date Inactive</label>
		            <div class="input-group date ui-datepicker">
		                <input type="text" name="dateInactive" value="<s:property value="dateInactive"/>" class="form-control" placeholder="Date Inactive(dd/mm/yyyy)" data-required="true" >
		                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                </div>
                </div>

               <div class="form-group">
                  <label for="publication">Type</label> 
                   <s:select list="BookTypeList"  id="typeID" class="form-control" headerKey="-1" headerValue="Select Type" name="type"></s:select>
               </div>
				
                 <div class="form-group">
                  <label for="author">Cupboard</label>
                    <s:select list="CupboardDetailsList" id="CupboardID" class="form-control"  onchange="retrieveShelves(this.value);"
                    headerKey="-1" headerValue="Select Cupboard" name="cupboardID"></s:select>
                </div>
                
                <div class="form-group">
                  	<label for="author">Shelves</label>
               		<s:select list="ShelvesDetailsList" id="ShelvesID" class="form-control"  headerKey="-1" headerValue="Select Shelf" name="shelfID"></s:select>
		        </div>
                
				<div class="form-group">
                  <label for="publication">Section</label> 
                   <s:select list="SectionList"  id="sectionID" class="form-control" headerKey="-1" headerValue="Select Section" name="section"></s:select>
               </div>
               
			   <div class="form-group">
                  <label for="edition">Call No.</label>
                  <input type="text" name="colNo" id="ColNoID" value="<s:property value="colNo"/>" class="form-control" placeholder="Col No" data-required="true" >
               </div>
               
			   <div class="form-group" id ="VendorsdivID">
                  <label for="publication">Vendors</label> 
                  <s:select list="VendorList" id="vendorDivID" class="form-control" headerKey="" headerValue="Select Vendors" name="vendorID"></s:select>
               </div>	
			   
			  <div class="form-group">
                  <label for="edition">Book Price</label>
                  <input type="double" name="price" id="PriceID" value="<s:property value="price"/>" class="form-control" placeholder="Book Price" data-required="true" >
              </div>
        
                <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen2();" class="btn btn-default">Cancel</button>
                  <button type="submit" class="btn btn-success" id="submitID" >Update Book</button>
                </div>
                
               </form>
               
               </s:iterator>

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
