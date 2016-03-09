
 var months = new Array("一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二");
                        var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
                        var days = new Array("日", "一", "二", "三", "四", "五", "六");
                        var classTemp;
                        var today = new getToday();
                        var year = today.year;
                        var month = today.month;
                        var day = today.day;
                        var newCal;

                        function getDays(month, year) {
                            if (1 == month) return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
                            else return daysInMonth[month];
                        }

                        function getToday() {
                            this.now = new Date();
                            this.year = this.now.getFullYear();
                            this.month = this.now.getMonth();
                            this.day = this.now.getDate();
                        }

                        function Calendar() {
                            $(".course").html("");
                            newCal = new Date(year, month, 1);
                            today = new getToday();
                            var startDay = newCal.getDay();
                            var endDay = getDays(newCal.getMonth(), newCal.getFullYear());
                            var daily = 0;
                            if ((today.year == newCal.getFullYear()) && (today.month == newCal.getMonth())) {
                                day = today.day;
                            }
							else
							{
								day=0;
							}
                            var caltable = document.all.caltable.tBodies.calendar;
                            var intDaysInMonth = getDays(newCal.getMonth(), newCal.getFullYear());
                            var isweek=false;
                            for (var intWeek = 0; intWeek < caltable.rows.length; intWeek++)
                                for (var intDay = 0; intDay < caltable.rows[intWeek].cells.length; intDay++) {
                                    var cell = caltable.rows[intWeek].cells[intDay];
                                    var montemp = (newCal.getMonth() + 1) < 10 ? ("0" + (newCal.getMonth() + 1)) : (newCal.getMonth() + 1);
                                    if ((intDay == startDay) && (0 == daily)) { daily = 1; }
                                    var daytemp = daily < 10 ? ("0" + daily) : (daily);
                                    var d = "<" + newCal.getFullYear() + "-" + montemp + "-" + daytemp + ">";
                                    if (day == daily) cell.className = "DayNow";
                                    else if (intDay == 6) cell.className = "DaySat";
                                    else if (intDay == 0) cell.className = "DaySun";
                                    else cell.className = "Day";
                                    if ((daily > 0) && (daily <= intDaysInMonth)) {
                                        //task add
										if(intDay==0||intDay==6){
											 cell.innerHTML=daily;
									    }
										else{
                                            cell.innerHTML = chkTask(daily);
										}
                                        daily++;
                                    } else {
                                        cell.className = "CalendarTD";
                                        cell.innerText = "";
                                    }
                                }
                            document.all.year.value = year;
                            document.all.month.value = month + 1;

                        }

                        function subMonth() {
                            if ((month - 1) < 0) {
                                month = 11;
                                year = year - 1;
                            } else {
                                month = month - 1;
                            }
                            $(".nextmonth").show();
                            Calendar();
                        }

                        function addMonth() {
                            var tmp = month + 1;
                            if ((tmp >= new Date().getMonth() + 1) && (year == new Date().getFullYear())) {
                                $(".nextmonth").hide();
                            }
                            if ((month + 1) > 11) {
                                month = 0;
                                year = year + 1;
                            } else {
                                month = month + 1;
                            }
                            Calendar();
                        }

                        function setDate() {
                            if (document.all.month.value < 1 || document.all.month.value > 12) {
                                alert("月的有效范围在1-12之间!");
                                return;
                            }
                            year = Math.ceil(document.all.year.value);
                            month = Math.ceil(document.all.month.value - 1);
                            Calendar();
                        }

                        function buttonOver() {
                            // var obj = window.event.srcElement;
                            //                  if (obj.innerText != "") {
                            //                      obj.runtimeStyle.cssText = "background-color:#e5e5e5";
                            //                      //$("#showTask").show();
                            //                  }
                        }

                        function buttonOut() {
                            // var obj = window.event.srcElement;
                            //                  window.setTimeout(function() { obj.runtimeStyle.cssText = ""; }, 100);
                        }
                        var cookiekey = 'task_' + year;
                        var tmpVal;
                        function chkTask(day) {
                            cookiekey = 'task_' + year + '_' + (month + 1);
                            //var val = $.cookie(cookiekey);
                            if (!tmpVal) {
                                $.ajax({
                                    url: "../../ashx/websvr.ashx?cmd=GetLearningTask",
                                    data: "{year:'" + year + "',month:0}",
                                    type: "Post",
                                    contentType: "application/json;utf-8",
                                    dataType: 'json',
                                    cache: false,
                                    async: false,
                                    success: function (json) {
                                        //$.cookie(cookiekey, json.d);
                                        tmpVal = json;
                                    }
                                });
                            }
                            //alert(cookiekey);
                            return ShowText(day, tmpVal);
                        }


                        function ShowText(tday, data) {
                            if (data == "") {
                                return tday;
                            } else {
                                if (day == tday) {
                                    showTask(null, tday);
                                }
                                var dataObjects = eval(data);
								var w=theWeek(new Date(year,month,tday));
								var md=weekToDate(year,w).split("|");
		var stmd=md[0].split('-');
		var endmd=md[1].split('-');
		var sql="select * from json where (year==" + parseInt(year) + "&&((month==" + stmd[0] + "&&day>=" + stmd[1] + ")&&(month==" + endmd[0] + "&&day<=" + endmd[1] + ")))";
		if(stmd[0]!=endmd[0])
		{
			sql="select * from json where (year==" + parseInt(year) + "&&((month==" + stmd[0] + "&&day>=" + stmd[1] + ")||(month==" + endmd[0] + "&&day<=" + endmd[1] + ")))";
		}
		var match = jsonsql.query(sql, dataObjects);
                                //var tmpm = parseInt(month) + 1;
                               // var match = jsonsql.query("select * from json where (year==" + parseInt(year) + "&&month==" + tmpm + "&&day==" + tday + ")", dataObjects);
                                //alert(tmpm + "_" + tday + "_" + match);
                                if (match != undefined && match != "") {
									var cuday=(tday==day?" currday":"");
                                    var html = "<div class=\"showbox "+cuday+"\" onclick='showTask(this," + tday + ")'>" + tday + "</div>";
                                    return html;
                                }
                                return tday;
                            }
                        }
                        function showTask(obj, tday) {
                            //var val = $.cookie(cookiekey);
							//alert(tmpVal);
                            if (tmpVal) {
                                var dataObjects = eval(tmpVal);
                                var w=theWeek(new Date(year,month,tday));
								var md=weekToDate(year,w).split("|");
		var stmd=md[0].split('-');
		var endmd=md[1].split('-');
		var sql="select * from json where (year==" + parseInt(year) + "&&((month==" + stmd[0] + "&&day>=" + stmd[1] + ")&&(month==" + endmd[0] + "&&day<=" + endmd[1] + ")))";
		if(stmd[0]!=endmd[0])
		{
			sql="select * from json where (year==" + parseInt(year) + "&&((month==" + stmd[0] + "&&day>=" + stmd[1] + ")||(month==" + endmd[0] + "&&day<=" + endmd[1] + ")))";
		}
		var match = jsonsql.query(sql, dataObjects);
                                //alert(tmpm + "_" + tday + "_" + match);
                                if (match != undefined && match != "") {
                                    var html = "";
                                    for (var i = 0; i < match.length; i++) {
                                        html += "<li><a href='planview.aspx?type=0&id=" + match[i].id + "'><p>" + (i + 1) + ". " + match[i].planname + "</p><span></span></a></li>";
                                    }
                                    $(".course").html("");
                                    $(".course").append(html);
                                }
                            }

                        }

                        function loadTrTitle()
                        {
                           document.write("<TD class=DaySunTitle id=diary >" + days[0] + "</TD>");
                    for (var intLoop = 1; intLoop < days.length - 1; intLoop++)
                        document.write("<TD class=DayTitle id=diary>" + days[intLoop] + "</TD>");
                    document.write("<TD class=DaySatTitle id=diary>" + days[intLoop] + "</TD>");
                        }

                        function loadWeek()
                        {
                        for (var intWeeks = 0; intWeeks < 6; intWeeks++) {
                      document.write("<tr style='cursor:hand'>");
                      for (var intDays = 0; intDays < days.length; intDays++) document.write("<TD class=CalendarTD  onMouseover='buttonOver();' onMouseOut='buttonOut();'></TD>");
                      document.write("</tr>");
                  }
                        }
function theWeek(date){
//当前date
var now = new Date(date);

//当前日，在本年中第几日
var currentDayOfYear = 0;

var currentDayOfWeekIsLastDay = false;

var firstDayOfYearIsFirstDayOfWeek = false;

//是否为润年，即能被4整除  
var isFullYear = false;

//当前年份
var year = now.getFullYear();
//当前月份
var month = now.getMonth();

//当前日
var day = now.getDate();

//当前星期几
var week = now.getDay();

//为闰年，设isFullYear为true
if(year%4==0){
	isFullYear = true;
}


//循环计算天数
for(var i=0;i<daysInMonth.length;i++){
	//判断数组月份是否小于等于当前月份
	if(i<month){
		//是闰年和2月份
		if(isFullYear && i==1)
			currentDayOfYear = currentDayOfYear + 29;
		else
			currentDayOfYear = currentDayOfYear + daysInMonth[i];
		
	}
	if(i==month)
		currentDayOfYear = currentDayOfYear + day;
}

//设置本年1月1日
var firstDayOfYear = new Date();
firstDayOfYear.setYear(year);
firstDayOfYear.setMonth(0);
firstDayOfYear.setDate(1); 

if(firstDayOfYear.getDay()==0){
	firstDayOfYearIsFirstDayOfWeek = true;
}

var weeksOfYear = currentDayOfYear;

//本星期是否为最后一日，否，则减去本兴起所有日
if(!currentDayOfWeekIsLastDay){
	weeksOfYear = weeksOfYear + firstDayOfYear.getDay();
}

//是否第一个星期为第一日（即星期日），否，则减去本星期所有日
if(!firstDayOfYearIsFirstDayOfWeek){
	weeksOfYear = weeksOfYear + (7-week-1);
}

return weeksOfYear/7;
}
function weekToDate(y,w){
    var yearStart = new Date(parseInt(y),0,1)    //设置该年1.1.
    var firstDay = yearStart.getDay();    //星期
    var yearEnd = new Date(parseInt(y),11,31)    //设置该年12.31.
    var endDay = yearEnd.getDay();        //星期

    if(firstDay>=0&&firstDay<=4)  {other = firstDay ;}
        else {other = firstDay - 7}
//	if(firstDay>=0&&firstDay<=4)  {other = firstDay-1 ;}
//        else {other = firstDay - 8}
//-------------------------------------------------------------------------------------
     
//-------------------------------------------------------------------------------------
//时间调整,得出要计算周的起/始时间.    
    //距离当年1.1.的总天数
    days = (parseInt(w)-1)*7 - other;
    //转换成Ms.......
    var oneMinute = 60 * 1000;
    var oneHour = oneMinute * 60;
    var oneDay = oneHour * 24;    
    //1.1.至1/1/70的毫秒数
    var dateInMs = yearStart.getTime();
    //当前所选周第一天离1/1/70的毫秒数.
    dateInMs += oneDay * days
    //日期调整(设置1/1/70至今的毫秒数)
    yearStart.setTime(dateInMs);
    //当前所选周最后一天处理,同上.
    var weekEnd = new Date(parseInt(y),0,1)
    var dateInMs1 = weekEnd.getTime ();
    dateInMs1 += oneDay * (days + 6);
    weekEnd.setTime(dateInMs1)
//-------------------------------------------------------------------------------------
 
//-------------------------------------------------------------------------------------
//月和日的处理,一位变两位,如:1->01.    
    var month = yearStart.getMonth() + 1
    //if(month<10) { month = "0" + month;}
    var day = yearStart.getDate();
    //if(day<10) { day = "0" + day;}
    var month1 = weekEnd.getMonth() + 1;
   // if(month1<10) { month1 = "0" + month1;}
    var  day1 = weekEnd.getDate();
    //if(day1<10) { day1 = "0" + day1;}
//-------------------------------------------------------------------------------------
     
//-------------------------------------------------------------------------------------.
   // var yearweek=yearStart.getFullYear() + "-" + month + "-" + day+"|"+weekEnd.getFullYear() + "-" + month1 + "-" + day1;
//-------------------------------------------------------------------------------------   
   var yearweek= month + "-" + day+"|"+month1 + "-" + day1;
   return yearweek;
}