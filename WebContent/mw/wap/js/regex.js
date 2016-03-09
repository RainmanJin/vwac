

//校验普通电话、传真号码：可以“+”开头，除数字外，可含有“-” 
function isTel(s)   
{   
    var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;   
    if (!patrn.exec(s)) return false   
    return true   
} 

//校验用户名
function isUserName(s)   
{   
   var patrn=/^[a-zA-Z\u0391-\uFFE5]+$/;   
    if (!patrn.exec(s)) return false   
    return true   
}    

//校验手机号码：必须以数字开头，除数字外，可含有“-” 
function isMobil(s)   
{   
     if(!/^(\d{11})$/.test(s))
     {
       return false;
     }
     return true;
}
function isAddress(s) {
    if (!/^([\u4E00-\u9FA5\w- #]{2,100})$/.test(s)) {
        return false;
    }
    return true;
}
//校验邮政编码 
function isPostalCode(s)   
{    
     if(!/^(\d){6}$/.test(s))
     {
       return false;
     }
    return true   
}   

//校验邮箱
function isEmail(s)   
{    
    if (!/^[\w-]+(\.[\w-]+)*@[\w-]+(\.(\w)+)*(\.(\w){2,3})$/.test(s))
    {   
      return false;
    }
    return true   

}   

//校验网址
function isUrl(s)
{
    var regExp = /(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i;
    if(!s.match(regExp))return false;  
    return true; 
}

//校验正整数
function isPositiveInteger(s)
{
      var regExp = /^[0-9]*[1-9][0-9]*$/;
      return regExp.test(s)
}

//校验数字
function IsNumeral(s)
{
    var regExp = /^[0-9]+.?[0-9]*$/;   
     return regExp.test(s)
}
//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
} 
//护照
function isPassport(s)
{
      var regExp = /^(P|p\d{7})|(G|g\d{8})$/; //  /^(P|p\d{7})|(G|g\d{8})$/ ) 
      return regExp.test(s)
} 
var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子   
var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值.10代表X   
function IdCardValidate(idCard) {   
    idCard = trim(idCard.replace(/ /g, ""));   
    if (idCard.length == 15) {   
        return isValidityBrithBy15IdCard(idCard);   
    } else if (idCard.length == 18) {   
        var a_idCard = idCard.split("");// 得到身份证数组   
        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   
            return true;   
        }else {   
            return false;   
        }   
    } else {   
        return false;   
    }   
}   
/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
 * @param a_idCard 身份证号码数组  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0; // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {   
        a_idCard[17] = 10;// 将最后位为x的验证码替换为10方便后续操作   
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];// 加权求和   
    }   
    valCodePosition = sum % 11;// 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}  
 /**  
  * 验证18位数身份证号码中的生日是否是有效生日  
  * @param idCard 18位书身份证字符串  
  * @return  
  */  
function isValidityBrithBy18IdCard(idCard18){   
    var year =  idCard18.substring(6,10);   
    var month = idCard18.substring(10,12);   
    var day = idCard18.substring(12,14);   
    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if(temp_date.getFullYear()!=parseFloat(year)   
          ||temp_date.getMonth()!=parseFloat(month)-1   
          ||temp_date.getDate()!=parseFloat(day)){   
            return false;   
    }else{   
        return true;   
    }   
}   
  /**  
   * 验证15位数身份证号码中的生日是否是有效生日  
   * @param idCard15 15位书身份证字符串  
   * @return  
   */  
  function isValidityBrithBy15IdCard(idCard15){   
      var year =  idCard15.substring(6,8);   
      var month = idCard15.substring(8,10);   
      var day = idCard15.substring(10,12);   
      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
      if(temp_date.getYear()!=parseFloat(year)   
              ||temp_date.getMonth()!=parseFloat(month)-1   
              ||temp_date.getDate()!=parseFloat(day)){   
                return false;   
        }else{   
            return true;   
        }   
  }    
/**  
 * 通过身份证判断是男是女  
 * @param idCard 15/18位身份证号码   
 * @return 'female'-女、'male'-男  
 */  
function maleOrFemalByIdCard(idCard){   
    idCard = trim(idCard.replace(/ /g, ""));// 对身份证号码做处理。包括字符间有空格。   
    if(idCard.length==15){   
        if(idCard.substring(14,15)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else if(idCard.length ==18){   
        if(idCard.substring(14,17)%2==0){   
            return 'female';   
        }else{   
            return 'male';   
        }   
    }else{   
        return null;   
    }
}