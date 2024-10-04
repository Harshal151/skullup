<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="/struts-tags" prefix="s" %>
     <%@page import="com.kovidRMS.form.LoginForm"%>
<!DOCTYPE html>
<html class="no-js"> 
<head>
  <title>Register Books -  SkoolUp</title>
<!-- Favicon -->
 	<link rel="shortcut icon" href="images/favicon.jpg">
  <meta charset="utf-8">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width">

  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,700italic,400,600,700">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Oswald:400,300,700">
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
		LoginForm loginform = (LoginForm) session.getAttribute("USER");
		
	%>
<script type="text/javascript">
	$(document).ready(function(){
		$('#inventoryLiID').addClass("active");
		//alert("hiii");	
	});
</script>

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
			
          document.location="RenderManageBook";
        }
      
      function submitForm(copies,book){
    	  
    	 if(book == ""){
  			alert("Please add Book.");
  		}else{
  			submitForm1(copies)
  		} 
      }
      
      function submitForm1(copies){
     	var trTag = "";
			
		var trID = "";
		
		for(i=1;i<=copies; i++){
			
			trTag += "<tr id='"+trID+"' style='font-size: 14px;'>"
			+"<td style='text-align: center'><input type='text' id='newBarcodeID' required='required' class='form-control' name='newBarcode'></td>"
			+"<td style='text-align: center'><input type='text' id='newAccNumID' required='required' class='form-control' name='newAccNum'></td>";
					
			trTag += "</tr>";
		}
		
		
		//$("#divTableID").html(trTag);
		$(trTag).insertAfter($('#NewTRID'));
		$("#myModal").modal("show");  
	  	
		$(".fade").removeClass("modal-backdrop");  
      }  
      
      function submitFormNew(subID){
    	
    	  $('html, body').animate({
		        scrollTop: $('body').offset().top
		    }, 1000);
			
			$('body').css('background', '#FCFAF5');
			$(".container").css("opacity","0.2");
			$("#loader").show();
			$("body").find("button").attr("disabled","disabled"); //To disable My appo, view appo, search patient, add new pat buttons
		
			$("#loadStudentFOrm").attr("action","AddBooks");
	   		 $("#loadStudentFOrm").submit(); 
	   		  	  
	   	 	  $("#"+subID).attr("disabled", "disabled"); 
	 	 	 
	 	 	$("html, body").animate({ scrollTop: 0 }, "fast");
	    		$(".loadingImage").show();
	    		$(".container").css("opacity","0.1");
	    		$(".navbar").css("opacity","0.1");
	    	
	    	return true;
	    	
   	   }
      
    </script>

<script type="text/javascript" charset="UTF-8">
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		function BookCheck(Author, bookName, GenreID, PublicationID, EditionID, AccNumID, PagesID, DescriptionID,
				 BarcodeID, PublicationYearID, StatusID, DateInactiveID, LocationID, RegDateID, TypeID, SectionID,
				 HiddenValue, cupboardDivID, shelfDivID, submitID, ColNoID, vendorDivID, PriceID) {
		
			console.log('inside');
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var count = 0;
	
					var check = 0;
					
					for ( var i = 0; i < array.Release.length; i++) {

						check = array.Release[i].check;
						count = array.Release[i].value;
						//console.log(count);
					}
					
					if(check == 0){
						
						$("#CupboardID").attr('name','cupboardID');
						$("#DivCupboardNewID").hide();
						$("#DivCupboardID").show();
						
						$("#ShelvesID").attr('name','shelfID');
						$("#shelfdivID").show();
						$("#shelfdivNewID").hide();

						$("#vendorDivID").attr('name', 'vendorID');
						$("#VendorsdivID").show();
						$("#VendorsdivNewID").hide();
						/* 
						$("#"+AuthorID).attr("readonly",false);
						 
						$("#"+AuthorID).val("");*/
					
						$("#"+GenreID).attr("readonly",false);
						
						$("#"+GenreID).val("");
						
						$("#"+PublicationID).attr("readonly",false);
						
						$("#"+PublicationID).val("");
						
						$("#"+EditionID).attr("readonly",false);
						
						$("#"+EditionID).val("");
						
						$("#"+PagesID).attr("readonly",false);

						$("#"+PagesID).val("");
						
						$("#"+DescriptionID).attr("readonly",false);

						$("#"+DescriptionID).val("");
						
						$("#"+PublicationYearID).attr("readonly",false);

						$("#"+PublicationYearID).val("");
						
						$("#"+StatusID).attr("readonly",false);

						$("#"+StatusID).val("-1");
						
						$("#"+LocationID).attr("readonly",false);

						$("#"+LocationID).val("");
						
						$("#"+DateInactiveID).attr("readonly",false);

						$("#"+DateInactiveID).val("");
						
						$("#"+RegDateID).attr("readonly",false);

						$("#"+RegDateID).val("");
						
						$("#"+TypeID).attr("readonly",false);

						$("#"+TypeID).val("");
						
						$("#"+SectionID).attr("readonly",false);

						$("#"+SectionID).val("-1");
						
						$("#"+cupboardDivID).attr("readonly",false);

						$("#"+cupboardDivID).val("-1");
							
						$("#"+shelfDivID).attr("readonly",false);

						$("#"+shelfDivID).val("-1");
						
						$("#"+ColNoID).attr("readonly",false);

						$("#"+ColNoID).val("");
						
						$("#"+vendorDivID).attr("readonly",false);

						$("#"+vendorDivID).val("-1");
						
						$("#"+PriceID).attr("readonly",false);
						
						$("#"+PriceID).val("");
						
						$("#"+HiddenValue).val("0");
						
					}else{
						console.log('inside else');
						BookCheckNew(Author, bookName, GenreID, PublicationID, EditionID, AccNumID, PagesID, DescriptionID, BarcodeID, PublicationYearID, 
								StatusID, DateInactiveID, LocationID, RegDateID, TypeID, SectionID, HiddenValue, cupboardDivID, shelfDivID, submitID, ColNoID, vendorDivID, PriceID, count);
					}
				}
			};
			xmlhttp.open("GET", "RetrieveBookNameCount?name="
					+ bookName+"&author="+Author, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
			
		}
		
		
		function BookCheckNew(Author, bookName, GenreID, PublicationID, EditionID, AccNumID, PagesID, DescriptionID,
				 BarcodeID, PublicationYearID, StatusID, DateInactiveID, LocationID, RegDateID, TypeID, SectionID, HiddenValue, cupboardDivID, shelfDivID, submitID, ColNoID, vendorDivID, PriceID, countVal) {
			
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
	
					var array = JSON.parse(xmlhttp.responseText);
					
					var array_element = "<input type='number' name='' class='form-control'>";
					
					var array_element1 = "<lable><b>Shelves</b></lable><input type='hidden' name='shelfID' id='ShelvesHiddenID' class='form-control'>"
										+"<input type='text' name='' id='ShelveID' class='form-control' readonly='readonly'>";
										
					var array_element2 = "<lable><b>Cupboard</b></lable><input type='hidden' name='cupboardID' id='cupboardHiddenID' class='form-control'>"
										+"<input type='text' name='' id='cupboardNewID' class='form-control' readonly='readonly'>";
					
					var array_element3 = "<lable><b>Vendor</b></lable><input type='hidden' name='vendorID' id='vendorHiddenID' class='form-control'>"
											+"<input type='text' name='' id='vendorNewID' class='form-control' readonly='readonly'>";
						                	
					var check = 0;
						/* For division */
						for ( var i = 0; i < array.Release.length; i++) {
							
							check = array.Release[i].check;
							 
							$("#bookNameID").val(array.Release[i].name);
							$("#authorID").val(array.Release[i].author);
							
						if(check == 0){
							
							$("#CupboardID").attr('name','cupboardID');		
							$("#DivCupboardNewID").hide();
							$("#DivCupboardID").show();
							
							$("#ShelvesID").attr('name','shelfID');
							$("#shelfdivID").show();
							$("#shelfdivNewID").hide();

							$("#vendorDivID").attr('name', 'vendorID');
							$("#VendorsdivID").show();
							$("#VendorsdivNewID").hide();
						
							$("#"+GenreID).attr("readonly",false);
							
							$("#"+GenreID).val("");
							
							$("#"+PublicationID).attr("readonly",false);
							
							$("#"+PublicationID).val("");
							
							$("#"+EditionID).attr("readonly",false);
							
							$("#"+EditionID).val("");
							
							$("#"+PagesID).attr("readonly",false);

							$("#"+PagesID).val("");
							
							$("#"+DescriptionID).attr("readonly",false);

							$("#"+DescriptionID).val("");
							
							$("#"+PublicationYearID).attr("readonly",false);

							$("#"+PublicationYearID).val("");
							
							$("#"+StatusID).attr("readonly",false);

							$("#"+StatusID).val("-1");
							
							$("#"+LocationID).attr("readonly",false);

							$("#"+LocationID).val("");
							
							$("#"+DateInactiveID).attr("readonly",false);

							$("#"+DateInactiveID).val("");
							
							$("#"+RegDateID).attr("readonly",false);

							$("#"+RegDateID).val("");
							
							$("#"+TypeID).attr("readonly",false);

							$("#"+TypeID).val("");
							
							$("#"+SectionID).attr("readonly",false);

							$("#"+SectionID).val("-1");
							
							$("#"+cupboardDivID).attr("readonly",false);

							$("#"+cupboardDivID).val("-1");
							
							$("#"+shelfDivID).attr("readonly",false);

							$("#"+shelfDivID).val("-1");
							
							$("#"+ColNoID).attr("readonly",false);

							$("#"+ColNoID).val("");
							
							$("#"+vendorDivID).attr("readonly",false);

							$("#"+vendorDivID).val("-1");
							
							$("#"+PriceID).attr("readonly",false);
							
							$("#"+PriceID).val("");
							
							$("#"+HiddenValue).val("0");
							
						}else{
							
							var Cupboard;
							var CupboardName;
							var Shelf;
							var ShelfName;
							var ClassName;
							var Vendor;
							var VendorName;
							
							alert("Book name already exist, "+countVal+" copies are availabe. "+"Would you like to add extra copies of it?");
						
							$("#CupboardID").removeAttr('name');
							$("#DivCupboardNewID").show();
							$("#DivCupboardID").hide();
							
							$("#ShelvesID").removeAttr('name');
							$("#shelfdivNewID").show();
							$("#shelfdivID").hide();
							
							$("#vendorDivID").removeAttr('name');
							$("#VendorsdivNewID").show();
							$("#VendorsdivID").hide();
						
							$("#"+GenreID).attr("readonly",true);
							
							$("#"+GenreID).val(array.Release[i].genre);
							
							$("#"+PublicationID).attr("readonly",true);
							
							$("#"+PublicationID).val(array.Release[i].publication);
							
							$("#"+EditionID).attr("readonly",true);
							
							$("#"+EditionID).val(array.Release[i].edition);
							
							$("#"+PagesID).attr("readonly",true);

							$("#"+PagesID).val(array.Release[i].pages);
							
							$("#"+DescriptionID).attr("readonly",true);

							$("#"+DescriptionID).val(array.Release[i].description);
							
							$("#"+PublicationYearID).attr("readonly",true);

							$("#"+PublicationYearID).val(array.Release[i].publicationYear);
							
							$("#"+StatusID).attr("readonly",true);

							$("#"+StatusID).val(array.Release[i].status);
							
							$("#"+LocationID).attr("readonly",true);

							$("#"+LocationID).val(array.Release[i].location);
							
							$("#"+DateInactiveID).attr("readonly",true);

							$("#"+DateInactiveID).val(array.Release[i].dateInactive);
							
							$("#"+RegDateID).attr("readonly",true);

							$("i").removeClass("fa fa-calendar");
							
							$("#"+RegDateID).val(array.Release[i].regDate);
							
							$("#"+TypeID).attr("readonly",true);

							$("#"+TypeID).val(array.Release[i].type);
							
							$("#"+SectionID).attr("readonly",true);

							$("#"+SectionID).val(array.Release[i].section);
							
							$("#"+cupboardDivID).attr("readonly",true);
    						
							Cupboard = array.Release[i].cupboardID; 
							CupboardName = array.Release[i].cupboard;
							
							$("#"+shelfDivID).attr("readonly",true);
							
							Shelf = array.Release[i].shelfID
							ShelfName = array.Release[i].shelf;
							
							$("#"+vendorDivID).attr("readonly",true);
							
							Vendor = array.Release[i].vendorID
							VendorName = array.Release[i].vendor;
							
							$("#"+HiddenValue).val("1");
							
							document.getElementById("DivCupboardNewID").innerHTML = array_element2;
							$("#cupboardHiddenID").val(Cupboard);
							$("#cupboardNewID").val(CupboardName);
							
							document.getElementById("shelfdivNewID").innerHTML = array_element1;
							$("#ShelvesHiddenID").val(Shelf);
							$("#ShelveID").val(ShelfName);
							
							document.getElementById("VendorsdivNewID").innerHTML = array_element3;
							$("#vendorHiddenID").val(Vendor);
							$("#vendorNewID").val(VendorName);
							
							$("#"+ColNoID).attr("readonly",true);

							$("#"+ColNoID).val(array.Release[i].colNo);
							
							$("#"+PriceID).attr("readonly",true);

							$("#"+PriceID).val(array.Release[i].price);
							
							document.getElementById("noOfCopiesID").innerHTML = array_element;	
						}
					}
				}
			};
			xmlhttp.open("GET", "RetrieveBookName?name="
					+ bookName+"&author="+Author, true);
			xmlhttp.setRequestHeader("Content-type", "charset=UTF-8");
			xmlhttp.send();
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
        <h2 class="content-header-title">Register Books</h2>
        <ol class="breadcrumb">
          <li><a onclick="windowOpen();">Home</a></li>
          <li class="active">Register Books</li>
        </ol>
      </div> <!-- /.content-header -->

       <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">

          <div class="portlet">

            <div class="portlet-header">

              <h3>
                <i class="fa fa-tasks"></i>
                Books Details
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
			
           <form id="loadStudentFOrm" action="AddBooks" method="POST" data-validate="parsley" enctype="multipart/form-data" class="form parsley-form">

				<div id="myModal" class="modal fade" tabindex="-1" style="z-index: 1000" role="dialog">
					<div class="modal-dialog modal-md">
						<div class="modal-content">
							<div class="modal-body">
								<table border="1" id="TableTRID">
									
									<tr id="NewTRID">
										<td style="width: 20%;text-align: center;">Barcode</td>
								     	<td style="text-align: center;">Accession Number</td>
								    </tr>
								   
								</table>
							</div>
							<div class="modal-footer"style="text-align: center;">
								<button type="button" data-dismiss="modal" id="submitID" onclick="submitFormNew('submitID');">Close</button>
							</div>
						</div>
					</div>
				</div>
				
               <div class="form-group">
                  <label for="name">Book Name<span class="required">*</span></label>
                  <input type="hidden" id = "hiddenID" name ="hiddenValue">
                  <input type="text" id="bookNameID" placeholder="Book Name" required="required" name="name" class="form-control" data-required="true"> 
                </div>

             	<div class="form-group">
                  <label for="author">Author Name</label>
                  <input type="text" name="author" id="authorID" placeholder="Author Name" class="form-control" data-required="true" onchange="BookCheck(this.value, bookNameID.value,'genreID','publicationID','editionID','accNumID','pagesID','descriptionID','barcodeID','publicationYearID',
                		  'StatusID','dateInactiveID','locationID','regDateID','typeID', 'sectionID', 'hiddenID', 'CupboardID', 'ShelvesID', 'submitNewID', 'ColNoID', 'vendorDivID', 'PriceID')">
                </div>

				<div class="form-group">
                  <label for="author">Genre</label>
                    <s:select list="GenreList" class="form-control" id="genreID" headerKey="-1" headerValue="Select Genre" name="genre"></s:select>
                </div>
                
               <div class="form-group">
                  <label for="publication">Publication</label>
                  <input type="text" name="publication" id="publicationID" class="form-control" placeholder="Publication" data-required="true" >
                </div>
				
				<div class="form-group">
                  <label for="edition">Edition</label>
                  <input type="text" name="edition" id="editionID" class="form-control" placeholder="Edition" data-required="true" >
                </div>
             
				<div class="form-group">
                  <label for="author">Pages</label>
                  <input type="number" name="pages" id="pagesID" placeholder="Pages" class="form-control" data-required="true" >
                </div>

                <div class="form-group">
                  <label for="genre">Description</label>
                  <input type="text" name="description" id="descriptionID" class="form-control" placeholder="Description" data-required="true" >
                </div>

               <div class="form-group">
                  <label for="edition">Publication Year</label>
                  <input type="text" name="publicationYear" id="publicationYearID" class="form-control" placeholder="Publication Year" data-required="true" >
                </div>
                 
                <div class="form-group">
	               <label for="name">Register Date<span class="required">*</span></label>
	                <div class="input-group date ui-datepicker">
	                	<input type="text" name="regDate" id="regDateID" class="form-control" required="required" placeholder="Register Date(dd-mm-yyyy)" data-required="true">
				        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                </div>
                </div>

             	<div class="form-group">
                  <label for="author">Status</label>
                    <s:select list="#{'available':'Available','on-hold':'On-hold','issued':'Issued','withdrawn':'Withdrawn','damaged':'Damaged','lost':'Lost','maintenance':'Maintenance'}" 
                    class="form-control" id="StatusID" value="-1" headerKey="-1" headerValue="Select Status" name="Status"></s:select>
                </div>

                <div class="form-group">
	               <label for="genre">Date Inactive</label>
		             <div class="input-group date ui-datepicker">
		                <input type="text" name="dateInactive" id="dateInactiveID" class="form-control"  placeholder="Date Inactive(dd/mm/yyyy)" data-required="true" >
		                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
	                </div>
                </div>

               <div class="form-group">
                  <label for="publication">Type</label> 
                   <s:select list="BookTypeList" id="typeID" class="form-control" headerKey="-1" headerValue="Select Type" name="type"></s:select>
               </div>
				
                <div class="form-group" id ="DivCupboardID" >
                  <label for="author">Cupboard</label>
                    <s:select list="CupboardDetailsList" id="CupboardID" class="form-control" value="-1" onchange="retrieveShelves(this.value);"
                    headerKey="-1" headerValue="Select Cupboard" name="cupboardID"></s:select>
                </div>
           
            	<div class="form-group" id="DivCupboardNewID" style="display: none;">
                
                
                </div>
                
	            <div class="form-group" id ="shelfdivID">
	               <label for="Shelves">Shelves</label>
	                   <select name="shelfID" id="ShelvesID" class="form-control">
				              <option value="-1" >Select Shelf</option>
				       </select>
			    </div>
			    
			    <div class="form-group" id="shelfdivNewID" style="display: none;">
                
                
                </div>
                
				<div class="form-group">
                  <label for="publication">Section</label> 
                   <s:select list="SectionList" id="sectionID" class="form-control" headerKey="-1" headerValue="Select Section" name="section"></s:select>
               	</div>
               
				<div class="form-group">
                  <label for="edition">Call No.</label>
                  <input type="text" name="colNo" id="ColNoID" class="form-control" placeholder="Col No" data-required="true" >
                </div>
                
                <div class="form-group" id ="VendorsdivID">
                  <label for="publication">Vendors</label> 
                   <s:select list="VendorList" id="vendorDivID" class="form-control" headerKey="" headerValue="Select Vendors" name="vendorID"></s:select>
               	</div>
               	
               	<div class="form-group" id="VendorsdivNewID" style="display: none;">
                
                
                </div>
               	
               	<div class="form-group">
                  <label for="edition">Book Price</label>
                  <input type="double" name="price" id="PriceID" class="form-control" placeholder="Book Price" data-required="true" >
                </div>
                
                <div class="form-group">
                  <label for="edition">No Of Copies</label>
                  <input type="number" name="noOfCopies" id="noOfCopiesID" class="form-control" value="1" placeholder="No Of Copies" data-required="true" >
                </div>
                
                <div class="form-group" align="center">
				  <button type="button" onclick="windowOpen1();" class="btn btn-default">Cancel</button>
                  <button type="button" class="btn btn-success" id="submitNewID" onclick="submitForm(noOfCopiesID.value,bookNameID.value);">Add Books</button>
                </div>
                
               </form>

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
