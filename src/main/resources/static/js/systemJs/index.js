$(function(){
	/**
	 * 点击菜单显示iframe
	 */
	$(".iframe").click(function(){
		var url= $(this).attr('src');
		console.log("afdfdf"+url );
		$("#userIframe").attr('src',url);
	})
	
	
	
})

function logout(){
		$.ajax({
			type:"GET",
			url:"/logout",
			success:function(mydata){
				console.log(mydata)
				if(mydata.code == "0000"){
					window.location.href="/login"
				}
			}
		});
	}