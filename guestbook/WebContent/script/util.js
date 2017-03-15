//This JavaScript function always returns a random number between min and max (both included):
function getRndInteger(min, max) {
    return Math.floor(Math.random() * (max - min + 1) ) + min;
}

//yyyy-mm-dd 형식의 현재 날짜 반환
function today() {
	var temp = new Date();
	var yy = temp.getFullYear();
	var mm = ((temp.getMonth()+1)<10)?"0"+(temp.getMonth()+1):(temp.getMonth()+1);
	var dd = (temp.getDate()<10)?"0"+temp.getDate():temp.getDate();
	var d5 = yy + "-" + mm + "-" + dd;
	return d5;
}

//특정 날짜까지 남은 일수 계산해서 반환하는 함수
function dday(date) {
	var d1 = new Date(date); //지정된 날짜(오늘보다 미래 날짜)
	var d2 = new Date(); //오늘 날짜
	return ((d1.getTime() - d2.getTime())/1000/60/60/24).toFixed(0);
}

//특정 날짜까지 경과 일수 계산해서 반환하는 함수
function runningdays(date) {
	var d2 = new Date(); //오늘 날짜
	var d1 = new Date(date); //지정된 날짜(오늘보다 과거 날짜)
	return ((d2.getTime() - d1.getTime())/1000/60/60/24).toFixed(0);
}

//전달된 문자열들 전체를 하나의 문자열로 결합시키는 함수
function strAppend() {
	var target = "";
	for(var i=0; i<arguments.length; ++i){
        target += arguments[i];
    }
	return target;
}