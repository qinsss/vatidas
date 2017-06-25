function allChoice(text){
	var inputs = document.getElementsByTagName("input");
	var allChoice_button = document.getElementById("allChoice_button");
	if(text=="全选"){
		for (var i = 0 ; i < inputs.length ; i++ ){
			//获取所checkbox
			if(inputs[i].type == "checkbox"){
					inputs[i].checked=true;
				}
		}
		allChoice_button.innerHTML="取消全选";
	}else if(text=="取消全选"){
		for (var i = 0 ; i < inputs.length ; i++ ){
			//获取所checkbox
			if(inputs[i].type == "checkbox"){
					inputs[i].checked=false;
				}
		}
		allChoice_button.innerHTML="全选";
	}
}
