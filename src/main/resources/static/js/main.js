
$(document).ready(function () {
	$("#URL-form").submit(function (event) {

		var valid= validateUrl();	
		$('#url-result-table').hide();
		$('#link-table').hide();

		if( valid ){
			$('#urlstatebody').html('');
			$('#error-div').html('');
			$("#submiturl").prop("disabled", true);
			$("#submiturl").prop("value", "Please wait...");

			//it stops submitting the form, instead it is handled manually
			event.preventDefault();
			getPageInfo();
		}
	});
});

function validateUrl(){
	
	var url = $("#url").val();
	if ( !url.startsWith('https://')){
		alert("url is not a valid url, it must start as https://");
		return false;
	}
	return true;
}

function getPageInfo() {
	
	var url = $("#url").val()
	
	$.ajax({
        type: "GET",
        url: "/scout24/getHtmlInfo",
        data: {url:url},
        cache: true,
        timeout: 600000,
        success: function (data) {
        	
        	$('#url-result-table').show();
            var trHTML = '';
            
            var headingTag	=	''
            	$.each( data.headingsGroup , function(key,value) {
            		headingTag += '<li>' + value.headingName +' - ' + value.count + ' </li>'
                	console.log('<li> '+ value.headingName +' - ' + value.count + ' </li>')
                	});	
            
            trHTML +=
                '<tr><td>'
                + data.url
                + '</td><td>'
                + data.htmlVersion 
                + '</td><td>'
                + data.pageTitle 
                + '</td><td><ul>'
                +	headingTag
                + '</ul></td><td><ul><li>I-Links - '
                + data.internalDomainLinkCount
                + '</li>'
                + '<li>E-Links - '
                + data.externalDomainLinkCount
                + '</li>'
                + '</ul></td><td>'
                + data.isloginForm
                + '</td></tr>';

            console.log("SUCCESS : ", data);
            $('#urlinfobody').html(trHTML);
            
            fetchUnreachableLink( data.links )
        },
        error: function (e) {
        	var divHTML = '';
        	
        	if( (typeof e.responseJSON.url == 'undefined') ){
        		divHTML += e.responseJSON.status + ' - ' + e.responseJSON.error + ' - ' + e.responseJSON.exception +' - '+e.responseJSON.message 
        	}else{
        		divHTML += e.responseJSON.url + ' - ' + e.responseJSON.errorCode + ' - ' + e.responseJSON.message;
        	}
        	$('#error-div').html(divHTML);

            console.log("ERROR : ", e);
            
            $("#submiturl").prop("disabled", false);
	       	$("#submiturl").prop("value", "Check Status");
        },
        complete:function(){
        	$("#submiturl").prop("disabled", false);
	       	$("#submiturl").prop("value", "Check Status");
        }
    });
}


function fetchUnreachableLink ( links ){
	
	$.each( links , function(key,value) {
		console.log(" Value :" + value);
		
	var request  =	$.ajax({
	        type: "GET",
	        url: "/scout24/fetch/unreachable/link",
	        data: {link:value},
	        cache: true,
	        timeout: 600000,
	        success: function (data) {
	        	
	        	$('#link-table').show();
	            var urlHTML = '';
	            urlHTML +=
	                '<tr><td>'
	                + data.url
	                + '</td><td>'
	                + data.isReachable 
	                + '</td><td>'
	                + data.statusCode +" - " + data.statusMessage
	                + '</td><td><a href="'+ data.url + '" target="_blank">'
	                + 'Result' + '</a>'
	                + '</td></tr>';
	            
	       	   $('#urlstatebody').append(urlHTML); 
	            
	        },
	        error: function (e) {
	        	$("#submiturl").prop("disabled", false);
		       	$("#submiturl").prop("value", "Check Status");
	        }
	    });
	});
} 