/**
 * 格式化金钱显示方式
 * @param number 传进来的金钱
 * @returns 0.00
 */
function getMoney(number){
	if(number.toString().indexOf(".")>0){
//		number = number.split[0]+number.split('.')[1].subString(0,2);
	}else{
		number += ".00";
	}
	return number;
}