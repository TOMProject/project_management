
//公共的css
var pathName = document.location.pathname; 
var index = pathName.substr(1).indexOf("/");   
var result= pathName.substr(0,index+1);
document.write('<link type="text/css" rel="stylesheet" href='+result+'"/static/css/bootstrap.min.css"></link>');
document.write('<link type="text/css" rel="stylesheet" href='+result+'"/static/layui/css/layui.css"></link>');
