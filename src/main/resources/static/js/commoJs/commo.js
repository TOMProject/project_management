
//公共的js 文件引用
var pathName = document.location.pathname; 
var index = pathName.substr(1).indexOf("/");   
var result= pathName.substr(0,index+1);
document.write('<script type="text/javascript" src='+result+'"/static/js/jquery.min.js"></script>');
document.write('<script type="text/javascript" src='+result+'"/static/js/bootstrap.min.js"></script>');
document.write('<script type="text/javascript" src='+result+'"/static/js/jquery.validate.min.js"></script>');
document.write('<script type="text/javascript" src='+result+'"/static/js/messages_zh.min.js"></script>');
document.write('<script type="text/javascript" src='+result+'"/static/layui/layui.js"></script>');




 