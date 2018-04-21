<%-- 
    Document   : newjsp
    Created on : Apr 19, 2018, 2:46:21 PM
    Author     : it353s836
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script type="text/javascript">
var req;
var target;
var isIE;

function initRequest(url) {
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
}

function validateUserId() {
    if (!target) target = document.getElementById("userid");
    var url = "../validate?id=" + escape(target.value);    
    initRequest(url);
    req.onreadystatechange = processRequest;
    req.open("GET", url, true); 
    req.send(null);
}

function processRequest() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var message = req.responseXML.getElementsByTagName("valid")[0].childNodes[0].nodeValue;
            setMessageUsingDOM(message);
            var submitBtn = document.getElementById("submit_btn");
            if (message == "false") {
              submitBtn.disabled = true;
            } else {
              submitBtn.disabled = false;
            }
        }
    }
}

function setMessageUsingInline(message) {
    mdiv = document.getElementById("userIdMessage");
    if (message == "false") {
       mdiv.innerHTML = "<div style=\"color:red\">Invalid User Id</div>";
    } else {
       mdiv.innerHTML = "<div style=\"color:green\">Valid User Id</div>";
    }  
}

 function setMessageUsingDOM(message) {
     var userMessageElement = document.getElementById("userIdMessage");
     var messageText;
     if (message == "false") {
         userMessageElement.style.color = "red";
         messageText = "Invalid User Id";
     } else {
         userMessageElement.style.color = "green";
         messageText = "Valid User Id";
     }
     var messageBody = document.createTextNode(messageText);
     // if the messageBody element has been created simple replace it otherwise
     // append the new element
     if (userMessageElement.childNodes[0]) {
         userMessageElement.replaceChild(messageBody, userMessageElement.childNodes[0]);
     } else {
         userMessageElement.appendChild(messageBody);
     }
 }

function disableSubmitBtn() {
    var submitBtn = document.getElementById("submit_btn");
    submitBtn.disabled = true;
}
</script>
    <body>
        <h1>Hello World!</h1>
    </body>
</html>
