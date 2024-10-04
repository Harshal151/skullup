<!DOCTYPE html>
<html>
<head>

    <link href="css/jquery.multiselect.css" rel="stylesheet" type="text/css">

</head>

<body>

<h2>A demo of using multi-select with checkboxes</h2>
    <select name="basicOptgroup[]" multiple="multiple" class="2col active">
            <option value="cpp">C++</option>
             <option value="cpp">C++</option>
              <option value="cpp">C++</option>
               <option value="cpp">C++</option>
                <option value="cpp">C++</option>
                 <option value="cpp">C++</option>
            <optgroup label="Web">
                <option value="HTML">HTML</option>
                <option value="CSS">CSS</option>
                <option value="CSS3">CSS3</option>
                <option value="jQuery">jQuery</option>
                <option value="JavaScript">JavaScript</option>
            </optgroup>
            <optgroup label="High Level Languages">
                <option value="Java">Java</option>
                <option value="Python">Python</option>
            </optgroup>
           
    </select>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script> 
<script src="js/jquery.multiselect.js"></script>
<script>
$('select[multiple]').multiselect({
   
    placeholder: 'Select options'
});
</script>
</body>
</html>