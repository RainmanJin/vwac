package com.ecannetwork.tech.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.i18n.I18N;
import com.ecannetwork.core.module.controller.AjaxResponse;
import com.ecannetwork.core.module.controller.DateBindController;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.dto.tech.MwSktChapter;
import com.ecannetwork.dto.tech.MwSktItem;
import com.ecannetwork.dto.tech.MwSktcourse;
import com.ecannetwork.tech.service.TestDbService;

@Controller
@RequestMapping("/retailknowhow")
public class RetailknowhowController extends DateBindController {
  
  @Autowired
  private CommonService commonService;
  
  @Autowired
  private TestDbService dbService;
  
  private Date tempDate;
  
  @RequestMapping("index")
  public String index(Model model) {
    List<MwSktcourse> list = commonService.list("from " + MwSktcourse.class.getName()
        + " t order by CreateTime desc");// 列表
    model.addAttribute("dblist", list);
    return "tech/retailknowhow/index";
  }
  
  @RequestMapping("del")
  public @ResponseBody
  AjaxResponse del(Model model, @RequestParam(value = "id", required = true) String id) {
    MwSktcourse mwSktcourse = (MwSktcourse) commonService.get(id, MwSktcourse.class);
    if (mwSktcourse != null) {
      commonService.deleteTX(MwSktcourse.class, id);
      return new AjaxResponse(true, "i18n.commit.del.success");
    }
    return new AjaxResponse(true, "i18n.commit.del.success");
  }
  
  @RequestMapping("delques")
  public @ResponseBody
  AjaxResponse delques(Model model, @RequestParam(value = "id", required = true) String id) {
    MwSktChapter mwsktchapter = (MwSktChapter) commonService.get(id, MwSktChapter.class);
    if (mwsktchapter != null) {
      commonService.deleteTX(MwSktChapter.class, id);
      return new AjaxResponse(true, "i18n.commit.del.success");
    }
    return new AjaxResponse(true, "i18n.commit.del.success");
  }
  
  @RequestMapping("delsktitem")
  public @ResponseBody
  AjaxResponse delsktitem(Model model, @RequestParam(value = "id", required = true) String id) {
    MwSktItem mwsktitem = (MwSktItem) commonService.get(id, MwSktItem.class);
    if (mwsktitem != null) {
      commonService.deleteTX(MwSktItem.class, id);
      return new AjaxResponse(true, "i18n.commit.del.success");
    }
    return new AjaxResponse(true, "i18n.commit.del.success");
  }
  
  @RequestMapping("add")
  public String add(Model model) {
    return "tech/retailknowhow/save";
  }
  
  @RequestMapping("addques")
  public String addques(Model model) {
    return "tech/retailknowhow/saveques";
  }
  
  @RequestMapping("addsktitem")
  public String addsktitem(Model model) {
    // 绑定所属上级下拉列表数据
    List<MwSktItem> mwsktitemlist = this.commonService.list("from " + MwSktItem.class.getName()
        + " t ");
    model.addAttribute("mwsktitemlist", mwsktitemlist);
    return "tech/retailknowhow/savesktitem";
  }
  
  @RequestMapping("editsktitem")
  public String editsktitem(Model model, @RequestParam(value = "id", required = true) String id) {
    MwSktItem mwsktitem = (MwSktItem) commonService.get(id, MwSktItem.class);
    model.addAttribute("dto", mwsktitem);
    return "tech/retailknowhow/savesktitem";
  }
  
  @RequestMapping("editques")
  public String editques(Model model, @RequestParam(value = "id", required = true) String id) {
    MwSktChapter mwsktchapter = (MwSktChapter) commonService.get(id, MwSktChapter.class);
    model.addAttribute("dto", mwsktchapter);
    return "tech/retailknowhow/saveques";
  }
  
  @RequestMapping("edit")
  public String edit(Model model, @RequestParam(value = "id", required = true) String id) {
    MwSktcourse mwSktcourse = (MwSktcourse) commonService.get(id, MwSktcourse.class);
    model.addAttribute("dto", mwSktcourse);
    tempDate = mwSktcourse.getdtCreateTime();
    return "tech/retailknowhow/save";
  }
  
  @RequestMapping("sktchapterlist")
  public String sktchapterlist(Model model, @RequestParam(value = "id", required = true) String id) {
    List<MwSktChapter> list = commonService.list("from " + MwSktChapter.class.getName()
        + " t order by CreateTime desc where id = " + id);// 列表
    model.addAttribute("dblist", list);
    return "tech/retailknowhow/sktchapterlist";
  }
  
  @RequestMapping("sktitemlist")
  public String sktitemlist(Model model, @RequestParam(value = "id", required = true) String id) {
    List<MwSktItem> list = commonService.list("from " + MwSktItem.class.getName()
        + " t order by sort asc where id = " + id);// 列表
    model.addAttribute("dblist", list);
    return "tech/retailknowhow/sktitemlist";
  }
  
  @RequestMapping("save")
  public @ResponseBody
  AjaxResponse save(Model model, @Valid MwSktcourse mwSktcourse, BindingResult result,
                    HttpServletRequest request) {
    // 获取是否验证checkbox的值。当选中时，值为on，否则为null
    String checkNcodeString = request.getParameter("islocked");
    if (checkNcodeString == null) {
      mwSktcourse.setislocked(0);
    }
    else {
      mwSktcourse.setislocked(1);
    }
    
    if (mwSktcourse.getId() == null || mwSktcourse.getId() == "") {
      mwSktcourse.setdtCreateTime(new Date());
      commonService.saveTX(mwSktcourse);// 保存
      return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
    }
    else {
      mwSktcourse.setdtCreateTime(tempDate);
      commonService.updateTX(mwSktcourse);// 更新
      return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
    }
  }
  
  @RequestMapping("quessave")
  public @ResponseBody
  AjaxResponse quessave(Model model, @Valid MwSktChapter mwsktchapter, BindingResult result,
                        HttpServletRequest request) {
    // 获取是否验证checkbox的值。当选中时，值为on，否则为null
    String checkNcodeString = request.getParameter("islock");
    if (checkNcodeString == null) {
      mwsktchapter.setIslock(0);
    }
    else {
      mwsktchapter.setIslock(1);
    }
    
    if (mwsktchapter.getId() == null || mwsktchapter.getId() == "") {
      mwsktchapter.setCreateTime(new Date());
      commonService.saveTX(mwsktchapter);// 保存
      return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
    }
    else {
      mwsktchapter.setCreateTime(tempDate);
      commonService.updateTX(mwsktchapter);// 更新
      return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
    }
  }
  
  @RequestMapping("sktitemsave")
  public @ResponseBody
  AjaxResponse sktitemsave(Model model, @Valid MwSktItem mwsktitem, BindingResult result,
                           HttpServletRequest request) {
    if (mwsktitem.getId() == null || mwsktitem.getId() == "") {
      commonService.saveTX(mwsktitem);// 保存
      return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
    }
    else {
      commonService.updateTX(mwsktitem);// 更新
      return new AjaxResponse(true, I18N.parse("i18n.commit.success"));
    }
  }
  
}