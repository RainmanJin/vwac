package com.ecannetwork.tech.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.SqPassatB8;

/**
 * ipad培训:::迈腾B8对比数据
 * 
 * @author xiaolin
 * 
 */
@Controller
@RequestMapping("/b8")
public class B8Controller {
  
  @Autowired
  private CommonService commonService;
  
  /**
   * 娱乐信息系统
   * 
   */
  @RequestMapping("index")
  public String index() {
    return "tech/b8/index";
  }
  
  /**
   * 竞品对比
   * 
   */
  @RequestMapping("competition")
  public String competition() {
    return "tech/b8/competition";
  }
  
  // 娱乐信息系统
  @RequestMapping("search")
  @ResponseBody
  String search(Model model, @RequestParam(value = "isCheck", required = true) String isCheck,
                @RequestParam(value = "txtDate", required = true) String txtDate,
                HttpServletRequest request) {
    String typeclass = isCheck.equals("true") ? "other" : "vwac";
    List<SqPassatB8> list1 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=1 and item='0001'");
    List<SqPassatB8> list2 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=2 and item='0001'");
    List<SqPassatB8> list3 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=3 and item='0001'");
    List<SqPassatB8> list4 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=4 and item='0001'");
    List<SqPassatB8> list5 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=5 and item='0001'");
    List<SqPassatB8> list6 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=6 and item='0001'");
    List<SqPassatB8> list7 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=1 and item='0002'");
    List<SqPassatB8> list8 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=2 and item='0002'");
    List<SqPassatB8> list9 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=3 and item='0002'");
    List<SqPassatB8> list10 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=4 and item='0002'");
    List<SqPassatB8> list11 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=5 and item='0002'");
    List<SqPassatB8> list12 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=6 and item='0002'");
    List<SqPassatB8> list13 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=1 and item='0003'");
    List<SqPassatB8> list14 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=2 and item='0003'");
    List<SqPassatB8> list15 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=3 and item='0003'");
    List<SqPassatB8> list16 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=4 and item='0003'");
    List<SqPassatB8> list17 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=5 and item='0003'");
    List<SqPassatB8> list18 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' and score=6 and item='0003'");
    int[] s = new int[18];
    s[0] = list1.size();
    s[1] = list2.size();
    s[2] = list3.size();
    s[3] = list4.size();
    s[4] = list5.size();
    s[5] = list6.size();
    s[6] = list7.size();
    s[7] = list8.size();
    s[8] = list9.size();
    s[9] = list10.size();
    s[10] = list11.size();
    s[11] = list12.size();
    s[12] = list13.size();
    s[13] = list14.size();
    s[14] = list15.size();
    s[15] = list16.size();
    s[16] = list17.size();
    s[17] = list18.size();
    model.addAttribute("dblist", s);
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < s.length; i++) {
      sb.append(s[i] + ",");
    }
    String newStr = sb.toString();
    return newStr.substring(0, newStr.length() - 1);
  }
  
  // 竞品分析
  @RequestMapping("searchCom")
  @ResponseBody
  AjaxResponse searchCom(@RequestParam(value = "isCheck", required = true) String isCheck,
                         @RequestParam(value = "txtDate", required = true) String txtDate) {
    String htmlcode = getCompetitionHtml(isCheck, txtDate);
    return new AjaxResponse(true, htmlcode);
  }
  
  private String getFunHtml(String isCheck, String txtDate) {
    String typeclass = "";
    if (isCheck.length() == 4) {
      typeclass = "other";
    }
    else {
      typeclass = "vwac";
    }
    StringBuffer sb = new StringBuffer();
    sb.append("<table style=\"border-collapse:collapse;border:thin solid #333333;\"><tr style=\"text-align:center;height:30px\"><td style=\"width:300;border:thin solid #333333\">信息娱乐系统评分项</td> <td style=\"width:220;border:thin solid #333333\">结果</td> <td  style=\"width:220;border:thin solid #333333\">得分数量</td></tr>");
    List<SqPassatB8> list0 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass
        + "' and type='this' and Bigitem='001'  and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' ");
    int k1 = 0, k2 = 0, k3 = 0, k4 = 0, k5 = 0, k6 = 0, k7 = 0, k8 = 0, k9 = 0, k10 = 0, k11 = 0, k12 = 0, k13 = 0, k14 = 0, k15 = 0, k16 = 0, k17 = 0, k18 = 0;
    if (list0.size() > 0) {
      for (SqPassatB8 sp : list0) {
        if (sp.getScore().intValue() == 6 && sp.getItem().equals("0001")) {
          k1++;
        }
        else if (sp.getScore().intValue() == 5 && sp.getItem().equals("0001")) {
          k2++;
        }
        else if (sp.getScore().intValue() == 4 && sp.getItem().equals("0001")) {
          k3++;
        }
        else if (sp.getScore().intValue() == 3 && sp.getItem().equals("0001")) {
          k4++;
        }
        else if (sp.getScore().intValue() == 2 && sp.getItem().equals("0001")) {
          k5++;
        }
        else if (sp.getScore().intValue() == 1 && sp.getItem().equals("0001")) {
          k6++;
        }
        else if (sp.getScore().intValue() == 6 && sp.getItem().equals("0002")) {
          k7++;
        }
        else if (sp.getScore().intValue() == 5 && sp.getItem().equals("0002")) {
          k8++;
        }
        else if (sp.getScore().intValue() == 4 && sp.getItem().equals("0002")) {
          k9++;
        }
        else if (sp.getScore().intValue() == 3 && sp.getItem().equals("0002")) {
          k10++;
        }
        else if (sp.getScore().intValue() == 2 && sp.getItem().equals("0002")) {
          k11++;
        }
        else if (sp.getScore().intValue() == 1 && sp.getItem().equals("0002")) {
          k12++;
        }
        else if (sp.getScore().intValue() == 6 && sp.getItem().equals("0003")) {
          k13++;
        }
        else if (sp.getScore().intValue() == 5 && sp.getItem().equals("0003")) {
          k14++;
        }
        else if (sp.getScore().intValue() == 4 && sp.getItem().equals("0003")) {
          k15++;
        }
        else if (sp.getScore().intValue() == 3 && sp.getItem().equals("0003")) {
          k16++;
        }
        else if (sp.getScore().intValue() == 2 && sp.getItem().equals("0003")) {
          k17++;
        }
        else if (sp.getScore().intValue() == 1 && sp.getItem().equals("0003")) {
          k18++;
        }
      }
    }
    sb.append("<tr style=\"text-align:center;height:27px\"><td rowspan=\"6\" style=\"border:thin solid #333333\">全新信息娱乐系统总体印象</td> <td>非常好</td><td style=\"border:thin solid #333333\">"
        + k1 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">好</td><td style=\"border:thin solid #333333\">"
        + k2 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">较好</td><td style=\"border:thin solid #333333\">"
        + k3 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">较差</td><td style=\"border:thin solid #333333\">"
        + k4 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">差</td><td style=\"border:thin solid #333333\">"
        + k5 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">非常差</td><td style=\"border:thin solid #333333\">"
        + k6 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td rowspan=\"5\" style=\"border:thin solid #333333\">MIB II的新功能总体印象</td><td style=\"border:thin solid #333333\">完全非常赞同</td><td>"
        + k7 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">赞同</td><td style=\"border:thin solid #333333\">"
        + k8 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">偏向于不赞同</td><td style=\"border:thin solid #333333\">"
        + k9 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">不赞同</td> <td style=\"border:thin solid #333333\">"
        + k10 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">完全不赞同</td> <td style=\"border:thin solid #333333\">"
        + k11 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td rowspan=\"6\" style=\"border:thin solid #333333\">MIB II赢得客户的潜力预计</td><td style=\"border:thin solid #333333\">非常高</td><td>"
        + k12 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">高</td><td style=\"border:thin solid #333333\">"
        + k13 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">较高</td><td style=\"border:thin solid #333333\">"
        + k14 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">较低</td><td style=\"border:thin solid #333333\">"
        + k15 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">低</td> <td style=\"border:thin solid #333333\">"
        + k16 + "</td></tr>");
    sb.append("<tr style=\"text-align:center;height:27px\"><td style=\"border:thin solid #333333\">非常低</td> <td style=\"border:thin solid #333333\">"
        + k17 + "</td></tr></table>");
    return sb.toString();
  }
  
  private String getCompetitionHtml(String isCheck, String txtDate) {
    String typeclass = "";
    if (isCheck.length() == 4) {
      typeclass = "other";
    }
    else {
      typeclass = "vwac";
    }
    List<String> lst = new ArrayList<String>();
    String htmlcode = "";
    List<SqPassatB8> list0 = commonService.list("from " + SqPassatB8.class.getName()
        + " where  usertype = '" + typeclass + "' and type='competition' and startTime>'" + txtDate
        + " 00:00:00' and startTime< '" + txtDate + " 23:59:59' ");
    if (list0.size() > 0) {
      for (int i = 1; i < 10; i++) {
        List<SqPassatB8> dtcon1 = new ArrayList<SqPassatB8>();
        for (SqPassatB8 sp : list0) {
          if (sp.getCarType().equals(i + "")) {
            dtcon1.add(sp);
          }
        }
        if (dtcon1.size() > 0) {
          for (int j = 1; j < 5; j++) {
            List<SqPassatB8> dtcon2 = new ArrayList<SqPassatB8>();
            for (SqPassatB8 sp : dtcon1) {
              if (sp.getBigItem().equals("00" + j)) {
                dtcon2.add(sp);
              }
            }
            if (dtcon2.size() > 0) {
              for (int l = 1; l < 5; l++) {
                List<SqPassatB8> dtcon3 = new ArrayList<SqPassatB8>();
                for (SqPassatB8 sp : dtcon2) {
                  if (sp.getItem().equals(l + "")) {
                    dtcon3.add(sp);
                  }
                }
                if (dtcon3.size() > 0) {
                  double ss1 = 0.0;
                  for (SqPassatB8 sp : dtcon3) {
                    ss1 += sp.getScore();
                  }
                  ss1 /= dtcon3.size();// ss1是score的平均值
                  lst.add(cartypeset(dtcon3.get(0).getCarType()) + ","
                      + Bigitenset(dtcon3.get(0).getBigItem()) + ","
                      + itenset(dtcon3.get(0).getItem()) + "," + ss1);
                }
              }
            }
          }
        }
      }
    }
    int row1 = 0, row2 = 0;
    for (int i = 0; i < lst.size(); i++) {
      int col1 = 0, col2 = 0;
      for (String str : lst) {
        if (str.split(",")[0].equals(lst.get(i).split(",")[0])) {
          col1 = col1 + 1;
        }
        if (str.split(",")[0].equals(lst.get(i).split(",")[0])
            && str.split(",")[1].equals(lst.get(i).split(",")[1])) {
          col2 = col2 + 1;
        }
      }
      if (i == 0) {
        htmlcode += "<tr><td rowspan='" + col1 + "'>" + lst.get(i).split(",")[0]
            + "</td><td rowspan='" + col2 + "'>" + lst.get(i).split(",")[1] + "</td><td>"
            + lst.get(i).split(",")[2] + "</td><td>"
            + String.format("%.1f", Double.parseDouble(lst.get(i).split(",")[3])) + "</td></tr>";
        row1 = i + col1;
        row2 = i + col2;
      }
      else {
        if (row1 == i) {
          htmlcode += "<tr><td rowspan='" + col1 + "'>" + lst.get(i).split(",")[0]
              + "</td><td rowspan='" + col2 + "'>" + lst.get(i).split(",")[1] + "</td><td>"
              + lst.get(i).split(",")[2] + "</td><td>"
              + String.format("%.1f", Double.parseDouble(lst.get(i).split(",")[3])) + "</td></tr>";
          row1 = i + col1;
          row2 = i + col2;
        }
        else {
          if (row2 == i) {
            htmlcode += "<tr><td rowspan='" + col2 + "'>" + lst.get(i).split(",")[1] + "</td><td>"
                + lst.get(i).split(",")[2] + "</td><td>"
                + String.format("%.1f", Double.parseDouble(lst.get(i).split(",")[3]))
                + "</td></tr>";
            row2 = i + col2;
          }
          else {
            htmlcode += "<tr><td>" + lst.get(i).split(",")[2] + "</td><td>"
                + String.format("%.1f", Double.parseDouble(lst.get(i).split(",")[3]))
                + "</td></tr>";
          }
        }
      }
    }
    return htmlcode;
  }
  
  // 娱乐信息系统导出表到Excel
  @RequestMapping("funexport")
  public String funexport(Model model,
                          @RequestParam(value = "isCheck", required = true) String isCheck,
                          @RequestParam(value = "txtDate", required = true) String txtDate,
                          HttpServletRequest request) {
    // 生成excel数据，html格式
    String tableExcelString = getFunHtml(isCheck, txtDate);
    String fileName = "娱乐信息系统_" + txtDate + "_1.xls";// 文件名称
    // 解决中文文件名乱码问题
    if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
      try {
        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
      }
      catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0)
      try {
        fileName = URLEncoder.encode(fileName, "UTF-8");
      }
      catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    model.addAttribute("dataString", tableExcelString);
    HttpSession session = request.getSession();// 将文件名存储到session中
    session.setAttribute("fileName", fileName);
    return "tech/vwsurveyresult/excelallreport";
  }
  
  // 竞品对比导出表到Excel
  @RequestMapping("competitionexport")
  public String competitionexport(Model model,
                                  @RequestParam(value = "isCheck", required = true) String isCheck,
                                  @RequestParam(value = "txtDate", required = true) String txtDate,
                                  HttpServletRequest request) {
    // 生成excel数据，html格式
    String tableExcelString = "<table border='1' style='border-collapse: collapse; margin-top: 20px'//><tr align='center'><td><b style='font-weight: bold'>车型<//b><//td><td><b style='font-weight: bold'>大项<//b><//td><td><b style='font-weight: bold'>子项<//b><//td><td><b style='font-weight: bold'>分值<//b><//td><//tr><//table>"
        + getCompetitionHtml(isCheck, txtDate);
    String fileName = "竞品对比_" + txtDate + "_2.xls";// 文件名称
    // 解决中文文件名乱码问题
    if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0)
      try {
        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
      }
      catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0)
      try {
        fileName = URLEncoder.encode(fileName, "UTF-8");
      }
      catch (UnsupportedEncodingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    model.addAttribute("dataString", tableExcelString);
    HttpSession session = request.getSession();// 将文件名存储到session中
    session.setAttribute("fileName", fileName);
    return "tech/vwsurveyresult/excelallreport";
  }
  
  protected String cartypeset(String set) {
    if ("1".equals(set)) {
      return "全新迈腾";
    }
    else if ("2".equals(set)) {
      return "奔驰 C180L 运动型";
    }
    else if ("3".equals(set)) {
      return "宝马316li 时尚型";
    }
    else if ("4".equals(set)) {
      return "沃尔沃 S60L 智进版";
    }
    else if ("5".equals(set)) {
      return "丰田 凯美瑞 旗舰型";
    }
    else if ("6".equals(set)) {
      return "别克 君越 旗舰型";
    }
    else if ("7".equals(set)) {
      return "日产天籁";
    }
    else if ("8".equals(set)) {
      return "本田雅阁";
    }
    else if ("9".equals(set)) {
      return "福特蒙迪欧";
    }
    else {
      return "";
    }
  }
  
  protected String Bigitenset(String set) {
    if ("001".equals(set)) {
      return "驾驶室";
    }
    else if ("002".equals(set)) {
      return "后排座椅";
    }
    else if ("003".equals(set)) {
      return "后备箱";
    }
    else {
      return "其他";
    }
  }
  
  protected String itenset(String set) {
    if ("1".equals(set)) {
      return "第一项";
    }
    else if ("2".equals(set)) {
      return "第二项";
    }
    else if ("3".equals(set)) {
      return "第三项";
    }
    else {
      return "第四项";
    }
  }
  
}