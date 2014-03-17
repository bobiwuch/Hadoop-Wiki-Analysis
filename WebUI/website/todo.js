$(document).ready(function(){
   //('#highlight-plugin').removeHighlight().highlight("Bobby");
   var query;
  
  $("#resultsTable").hide();   
  $("#results").hide();
  var startTime;
  var totalTime;
  $("button").click(function(){
    query = $("#searchBar").val();
    if(validQuery()){
      //serialize and send data

      var formData = $("#searchForm").serialize();
      /////no ajax
      //$("#resultsTable").show();
      
      $("#results").show();

      $('#results').removeHighlight();
      buildResultsTable();
      highlight();
      console.log(formData);
      //end no ajax/////
      startTime = Date.now();
  	    $.ajax({
  	        url: "sendQuery.php",
  	        data: formData,
  			    datatype: "html",
  			    type: "GET",
  			success: function(data){
  		    	console.log(data);
  		    	$("#results").empty();
            $("#results").show();
  		    	$("#results").append(data);
            $("#resultsTable tbody").empty();
            $("#resultsTable").show();
            buildResultsTable();
  		    	highlight();
            $("resultsTable").show();
  			}
  		}).done(function() {
          totalTime = (Date.now() - startTime)/1000;
          $('#queryTime').html("Query time was " + totalTime + "seconds.");
      });
    }
    else{
      alert("invalid query");
    }
   		return false;
  	});

    function validQuery(){
      //queryA = query.split(' ');
      //var myRegxp = /^([a-zA-Z0-9_-]+)$/;
      //if(myRegxp.test(query.replace(" ",""))==false){
       // return false;
      //}
      //else{
        return true;
      //}
    }
    function buildResultsTable(){
        var counter = 0;
        $('h1').each(function(){
        counter++
        var title = this.innerHTML.toString().toLowerCase().replace(" ", "");
        this.id = this.id+title;
        console.log(title);
        //put it in the table
        var self = this;
        $("#resultsTable tbody").append(
            '<tr><th class= "num">' + counter + '</th><th><a href="#' + title + '">'+this.innerHTML.toString()+'</a></th></tr>');
      });
    }

  	function setParent(el,newParent){
  		newParent.appendChild(el);
  	}


  	function highlight(){
  		var elements = ['div'];
		queryA = query.split(' ');
	    for (var str in queryA){
	    	if(!( queryA[str].toUpperCase() === 'AND' || queryA[str].toUpperCase() === 'OR')){
	   				$('#results').highlight(queryA[str]);
	   		}
	   		else if(queryA[str].toUpperCase() === 'NOT'){
	   			//skip
	   			str++;
	   		}
	    }
      //
  	}



});






