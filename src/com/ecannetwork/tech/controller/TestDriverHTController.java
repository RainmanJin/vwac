package com.ecannetwork.tech.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.JDKDSASigner.stdDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ecannetwork.core.app.auth.AuthFacade;
import com.ecannetwork.core.app.user.service.UserService;
import com.ecannetwork.core.module.db.dao.CommonDAO;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.MD5;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.GetresultfieldHT;
import com.ecannetwork.dto.tech.MwServicecost;
import com.ecannetwork.dto.tech.MwTestdriver;
import com.ecannetwork.dto.tech.MwTestdriverfield;
import com.ecannetwork.dto.tech.MwTestdriverpg;
import com.ecannetwork.dto.tech.MwTestdrivertime;
import com.ecannetwork.tech.controller.bean.RestResponse;
import com.ecannetwork.tech.controller.bean.RestResponseList;
import com.ecannetwork.dto.core.EcanUser;
import com.sun.xml.bind.v2.model.core.ID;

@RequestMapping("testdriverht")
@Controller
public class TestDriverHTController {
	@Autowired
	private CommonService commonService;
	@Autowired
	private UserService userService;
	@Autowired
	private AuthFacade authFacade;
	private CommonDAO commonDAO;
	
	@RequestMapping("getcourse")
	public @ResponseBody RestResponseList GetCourse(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriver> list=null;
		if (id!=null&&!id.equals("")&&Integer.valueOf(id)>0) {
			list = (List<MwTestdriver>) commonService.list("from MwTestdriver t where t.id=?", id);
		}else if (code!=null&&!code.equals("")) {
			list = (List<MwTestdriver>) commonService.list("from MwTestdriver t where t.CCode=?", code);
		}
		
		if (list != null && list.size() > 0) {
			resp.setData(list);
			resp.setTotal(list.size() + "");
			resplist.setList(resp);
			resplist.setRespStatus(resp.getRespStatus());
			resp.setRespStatus(null);
		}

		return resplist;
	}
	
	@RequestMapping("getuser")
	public @ResponseBody RestResponseList GetUser(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "cid") String cid,
			@RequestParam(value = "placeid") String placeid,
			@RequestParam(value = "chexiid") String chexiid,
			@RequestParam(value = "studentid") String studentid,
			@RequestParam(value = "parentid") String parentid,
			@RequestParam(value = "pg") String pg,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<GetresultfieldHT> rtn=new ArrayList<GetresultfieldHT>();
		MwTestdriver mod=null;
		if (Integer.valueOf(id)>0) {
			mod=(MwTestdriver)commonService.get(id, MwTestdriver.class);
		}else if (!code.equals("")) {
			List<MwTestdriver> list=(List<MwTestdriver>)commonService.list("from MwTestdriver t where t.CCode=?", code);
			if (list!=null&list.size()>0) {
				mod=list.get(0);
			}
		}
		if (mod!=null) {
			String uids=mod.getStudents();
			uids=uids.substring(0,uids.length()-1);
			if (!uids.equals("")) {
				String[] unames=uids.split(",");
				String idcodtion="";
				if (unames.length==1) {
					idcodtion="("+unames[0]+")";
				}
				if (unames.length>1) {
					for (int i = 0; i < unames.length; i++) {
						if (i==0) {
							idcodtion="("+unames[i];
						}else if (i==unames.length-1) {
							idcodtion +=","+unames[i]+")";
						}else {
							idcodtion +=","+unames[i];
						}
					}
				}
				List<EcanUser> tempList=(List<EcanUser>)commonService.list("from EcanUser t where t.id in "+idcodtion, null);
				if (tempList!=null&&tempList.size()>0) {
					List<String> names=new ArrayList<String>();
					for (EcanUser ecanUser : tempList) {
						if (ecanUser.getName()!=null&&!ecanUser.getName().equals("")) {
							names.add(ecanUser.getName());
						}
					}
					String[] arruids=uids.split(",");
					for (int i = 0; i < arruids.length; i++) {
						if(arruids[i]=="") continue;
						GetresultfieldHT ht=new GetresultfieldHT();
						ht.setId(arruids[i]);
						ht.setName(names.get(i));
						
						int pg2=isExitPG2(cid, code, placeid, chexiid, studentid, Integer.valueOf(parentid));
					    ht.setA1(pg2+"");
						int sj=isExitSJ(cid, code, placeid, chexiid, studentid, Integer.valueOf(parentid));
						ht.setA2(sj+"");
						rtn.add(ht);
					}
				}
			}

		}
		resp.setData(rtn);
		resp.setTotal(rtn.size() + "");
		resplist.setList(resp);
		resplist.setRespStatus(resp.getRespStatus());
		resp.setRespStatus(null);
		return resplist;
	}
	
	private int isExitSJ(String cid,String code,String placeid,String chexiid,String studentid,int parentid){
		List<MwTestdriver> list1=null;
		if (Integer.valueOf(cid)>0) {
			list1=(List<MwTestdriver>)commonService.list("from MwTestdriver where id="+cid, null);
		} else if (!code.equals("")) {
			list1=(List<MwTestdriver>)commonService.list("from MwTestdriver where CCode="+code, null);
		}
		String ids="";
		if (list1!=null&&list1.size()>0) {
			if (list1.size()==1) {
				ids="("+list1.get(0).getId()+")";
			}else {
				for (int i = 0; i < list1.size(); i++) {
					if (i==0) {
						ids="("+list1.get(i).getId();
					}else if (i==list1.size()-1) {
						ids+=","+list1.get(i).getId()+")";
					}else {
						ids+=","+list1.get(i).getId();
					}
				}
			}
		}
		String hql="from MwTestdrivertime where changdiId="+placeid;
		if (chexiid!=null&&!chexiid.equals("")) {
			hql+=" and chexiId="+chexiid;
		}
		if (studentid!=null&&!studentid.equals("")) {
			hql+=" and studentId ="+studentid;
		}
		hql+=" and cid in "+ids;
		List<MwTestdrivertime> list2=(List<MwTestdrivertime>)commonService.list(hql, null);
		if (list2!=null&&list2.size()>0) {
			return list2.size();
		}else {
			return 0;
		}
	}
	
	private int isExitPG2(String cid,String code,String placeid,String chexiid,String studentid,int parentid) {
		String zd=GetPgFeild(cid, code, placeid);
		String parentzd=GetPgParentFeild(zd, parentid);
		int cx=GetCheXiFeild(Integer.valueOf(cid), code);
		
		List<MwTestdriver> list1=null;
		if (Integer.valueOf(cid)>0) {
			list1=(List<MwTestdriver>)commonService.list("from MwTestdriver where id="+cid, null);
		} else if (!code.equals("")) {
			list1=(List<MwTestdriver>)commonService.list("from MwTestdriver where CCode="+code, null);
		}
		String ids="";
		if (list1!=null&&list1.size()>0) {
			if (list1.size()==1) {
				ids="("+list1.get(0).getId()+")";
			}else {
				for (int i = 0; i < list1.size(); i++) {
					if (i==0) {
						ids="("+list1.get(i).getId();
					}else if (i==list1.size()-1) {
						ids+=","+list1.get(i).getId()+")";
					}else {
						ids+=","+list1.get(i).getId();
					}
				}
			}
		}
		String hql="from MwTestdriverpg where changdiId="+placeid+" and fid in "+"("+parentzd+")";
		if (chexiid!=null&&!chexiid.equals("")) {
			hql+=" and chexiId="+chexiid;
		}
		if (studentid!=null&&!studentid.equals("")) {
			hql+=" and studentId ="+studentid;
		}
		hql+=" and cid in "+ids;
		List<MwTestdriverpg> list2=(List<MwTestdriverpg>)commonService.list(hql, null);
		if (list2!=null&&list2.size()>0) {
			return list2.size();
		}else {
			return 0;
		}
	}
	
	private String GetPgFeild(String cid,String code,String placeid) {
		String zd="";
		String hql="from MwTestdriver where 1=1";
		if (cid!=null&&!cid.equals("")&&Integer.valueOf(cid)>0) {
			hql +=" and id="+cid;
		}else if (code!=null&&!code.equals("")) {
			hql +=" and CCode="+code;
		}
		List<MwTestdriver> list=(List<MwTestdriver>)commonService.list(hql, null);
		if (list!=null&&list.size()>0) {
			zd=list.get(0).getPgfield();
			zd=SplitString(zd, "|", 3)[Integer.valueOf(placeid)];
			zd=zd.substring(0, zd.length()-1);
		}
		return zd;
	}
	
	private String GetPgParentFeild(String pgfield,int parentid) {
		pgfield=pgfield.substring(0,pgfield.length()-1);
		if (pgfield=="") {
			pgfield="0";
		}
		List<MwTestdriverfield> list=(List<MwTestdriverfield>)commonService.list("from MwTestdriverfield where parentId="+parentid+" and id in "+pgfield, null);
		String parentzd="0";
		if (list!=null&&list.size()>0) {
			int feildcount=list.size();
			for (MwTestdriverfield mwTestdriverfield : list) {
				parentzd +=","+mwTestdriverfield.getId();
			}
			parentzd=parentzd.substring(0, parentzd.length()-1);
		}
		return parentzd;
	}
	
	private int GetCheXiFeild(int cid,String code) {
		String hql="from MwTestdriver where 1=1";
		if (cid>0) {
			hql+=" and id="+cid;
		} else if (code!=null&&!code.equals("")) {
			hql+=" and CCode="+code;
		}
		int zd=1;
		List<MwTestdriver> list=(List<MwTestdriver>)commonService.list(hql, null);
		if (list!=null&&list.size()>0) {
			zd=list.get(0).getChexi().split(",").length;
			zd=zd==0?1:zd;
		}
		return zd;
	}
	
	private String[] SplitString(String strContent,String strSplit,int count) {
		String[] result=new String[count];
		String[] splited=strContent.split(strSplit);
		for (int i = 0; i < count; i++) {
			if (i<splited.length) {
				result[i]=splited[i];
			}else {
				result[i]="";
			}
		}
		return result;
	}
	
	@RequestMapping("getplace")
	public @ResponseBody RestResponseList Getplace(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriver> list=null;
		if (id!=null&&!id.equals("")&&Integer.valueOf(id)>0) {
			list = (List<MwTestdriver>) commonService.list("from MwTestdriver t where t.id=?", id);
		}else if (code!=null&&!code.equals("")) {
			list = (List<MwTestdriver>) commonService.list("from MwTestdriver t where t.CCode=?", code);
		}
		
		if (list != null && list.size() > 0&&list.get(0).getChangdi()!=null&&!list.get(0).getChangdi().equals("")) {
			String[] arruids=list.get(0).getChangdi().split(",");
			String[] arrplaces = new String[] { "公路", "运动", "越野" };
			list=new ArrayList<MwTestdriver>();
			for (int i = 0; i < arruids.length; i++) {
				MwTestdriver mwTestdriver=new MwTestdriver();
				mwTestdriver.setId(arruids[i]);
				mwTestdriver.setCName(arrplaces[i]);
				list.add(mwTestdriver);
			}
			resp.setData(list);
			resp.setTotal(arruids.length + "");
			resplist.setList(resp);
			resplist.setRespStatus(resp.getRespStatus());
			resp.setRespStatus(null);
		}
		return resplist;
	}
	
	//字段数未完成
	@RequestMapping("getcar")
	public @ResponseBody RestResponseList Getcar(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriver> list=null;
		if (id!=null&&!id.equals("")&&Integer.valueOf(id)>0) {
			list = (List<MwTestdriver>) commonService.list("from MwTestdriver t where t.id=?", id);
		}else if (code!=null&&!code.equals("")) {
			list = (List<MwTestdriver>) commonService.list("from MwTestdriver t where t.CCode=?", code);
		}
		
		if (list != null && list.size() > 0&&list.get(0).getChexi()!=null&&!list.get(0).getChexi().equals("")) {
			String[] arruids=list.get(0).getChexi().split(",");
			String conditonString="";
			for (int i = 0; i < arruids.length; i++) {
				if (arruids.length==1) {
					conditonString="("+arruids[0]+")";
				}
				else {
					if (i==0) {
						conditonString="("+arruids[i];
					}else if (i==arruids.length-1) {
						conditonString +=","+arruids[i]+")";
					}else {
						conditonString +=",";
					}
				}
			}
			List<EcanDomainvalueDTO> domainlist = (List<EcanDomainvalueDTO>) commonService.list("from EcanDomainvalueDTO t where t.id in ?", conditonString);
			for (int i = 0; i < domainlist.size(); i++) {
				MwTestdriver dr=new MwTestdriver();
				dr.setId(domainlist.get(i).getId());
				dr.setCName(domainlist.get(i).getDomainlabel());
				dr.setPgfield(domainlist.get(i).getValue().toString());
			}
			resp.setData(domainlist);
			resp.setTotal(domainlist.size() + "");
			resplist.setList(resp);
			resplist.setRespStatus(resp.getRespStatus());
			resp.setRespStatus(null);
		}
		return resplist;
	}
	
	@RequestMapping("getcoutarecord")
	public @ResponseBody RestResponseList Getcoutarecord(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "placeid") String placeid,
			@RequestParam(value = "cid") String cid,
			@RequestParam(value = "chexiId") String chexiId,
			@RequestParam(value = "studentId") String studentId,
			@RequestParam(value = "CTeacherid") String CTeacherid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		String timehql="from MwTestdrivertime t where changdiId="+placeid+" and cid="+cid;
		if (chexiId!=null&&!chexiId.equals("")&&!chexiId.equals("0")) {
			timehql +=" and chexiId"+chexiId;
		}
		if (studentId!=null&&!studentId.equals("")&&!studentId.equals("0")) {
			timehql +=" and studentId"+studentId;
		}
		if (CTeacherid!=null&&!CTeacherid.equals("")&&!CTeacherid.equals("0")) {
			timehql +=" and CTeacherid"+CTeacherid;
		}
		List<MwTestdrivertime> timeList=(List<MwTestdrivertime>)commonService.list(timehql, null);
		timehql="from MwTestdriverpg d where changdiId="+placeid+" and cid="+cid;
		if (chexiId!=null&&!chexiId.equals("")&&!chexiId.equals("0")) {
			timehql +=" and chexiId"+chexiId;
		}
		if (studentId!=null&&!studentId.equals("")&&!studentId.equals("0")) {
			timehql +=" and studentId"+studentId;
		}
		if (CTeacherid!=null&&!CTeacherid.equals("")&&!CTeacherid.equals("0")) {
			timehql +=" and CTeacherid"+CTeacherid;
		}
		List<MwTestdriverpg> pglist=(List<MwTestdriverpg>)commonService.list(timehql, null);
		List<GetresultfieldHT> gfList=new ArrayList<GetresultfieldHT>();
		GetresultfieldHT ht=new GetresultfieldHT();
		if (timeList!=null&&timeList.size()>0) {
			ht.setA1(timeList.size()+"");
		}
		if (pglist!=null&&pglist.size()>0) {
			ht.setA2(pglist.size()+"");
		}
		resp.setData(gfList);
		resp.setTotal(gfList.size() + "");
		resplist.setList(resp);
		resplist.setRespStatus(resp.getRespStatus());
		resp.setRespStatus(null);
		return resplist;
	}
	
	@RequestMapping("getcoutarecord2")
	public @ResponseBody RestResponseList Getcoutarecord2(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "cid") String cid,
			@RequestParam(value = "placeid") String placeid,
			@RequestParam(value = "chexiid") String chexiid,
			@RequestParam(value = "studentid") String studentid,
			@RequestParam(value = "parentid") String parentid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<GetresultfieldHT> list=new ArrayList<GetresultfieldHT>();
		int pg=isExitPG2(cid, code, placeid, chexiid, studentid, Integer.valueOf(parentid));
		int sj=isExitSJ(cid, code, placeid, chexiid, studentid, Integer.valueOf(parentid));
		GetresultfieldHT ht=new GetresultfieldHT();
		ht.setA1(pg+"");
		ht.setA2(sj+"");
		list.add(ht);
		resp.setData(list);
		resp.setTotal(list.size() + "");
		resplist.setList(resp);
		resplist.setRespStatus(resp.getRespStatus());
		resp.setRespStatus(null);
		return resplist;
	}
	
	@RequestMapping("savetime")
	public @ResponseBody RestResponse SaveTime(Model model,
			@RequestParam(value = "cid") String cid,
			@RequestParam(value = "changdiId") String changdiId,
			@RequestParam(value = "changdi") String changdi,
			@RequestParam(value = "studentId") String studentId,
			@RequestParam(value = "student") String student,
			@RequestParam(value = "CCourseid") String CCourseid,
			@RequestParam(value = "CCourse") String CCourse,
			@RequestParam(value = "CTeacherid") String CTeacherid,
			@RequestParam(value = "CTeacher") String CTeacher,
			@RequestParam(value = "chexiId") String chexiId,
			@RequestParam(value = "chexi") String chexi,
			@RequestParam(value = "useTime") String useTime,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		String hql="from MwTestdrivertime t where  t.cid=" + cid + " and t.changdiId='" + changdiId + "' and t.studentId='" + studentId + "' and t.CCourseid='" + CCourseid + "' and t.CTeacherid='" + CTeacherid + "' and t.chexiId='" + chexiId + "' and t.useTime='" + useTime+"'";
		List<MwTestdrivertime> mwTestdrivertime=(List<MwTestdrivertime>)commonService.list(hql, null);
		if (mwTestdrivertime!=null&&mwTestdrivertime.size()>0) {
			return null;
		}
		else {
			MwTestdrivertime testdrivertime=new MwTestdrivertime();
			testdrivertime.setChexiId(chexiId);
			testdrivertime.setChexi(chexi);
			testdrivertime.setChangdiId(Integer.valueOf(changdiId));
            testdrivertime.setChangdi(changdi);
            testdrivertime.setStudent(student);
            testdrivertime.setStudentId(studentId);
            testdrivertime.setCreateTime(new Date());
            testdrivertime.setCid(Integer.valueOf(cid));
            testdrivertime.setCCourseid(CCourseid);
            testdrivertime.setCCourse(CCourse);
            testdrivertime.setCTeacherid(CTeacherid);
            testdrivertime.setCTeacher(CTeacher);
            testdrivertime.setStartTime(new Date());
            testdrivertime.setEndTime(new Date());       
            testdrivertime.setUseTime(Double.valueOf(useTime));
            commonService.saveOrUpdateTX(testdrivertime);
            resp.setData(null);
			return resp;
		}
	}
	
	//未完成
	@RequestMapping("getfield")
	public @ResponseBody RestResponseList getfield(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "parentid") String parentid,
			@RequestParam(value = "placeid") String placeid,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriverfield> rtn=new ArrayList<MwTestdriverfield>();
		MwTestdriver mod=null;
		List<MwTestdriver> list=null;
		if (id!=null&&!id.equals("")) {
			list=(List<MwTestdriver>)commonService.list("from MwTestdriver t where t.id=?", id);
		}else if (code!=null&&!code.equals("")) {
			list=(List<MwTestdriver>)commonService.list("from MwTestdriver t where t.CCode=?", code);
		}
		if (list!=null&&list.size()>0) {
			mod=list.get(0);
			if (mod!=null) {
				String pgfield=mod.getPgfield();
				if (!pgfield.equals("")) {
					String[] arruids=pgfield.split("|");
					if (placeid!=null&&!placeid.equals("")&&Integer.valueOf(placeid)>arruids.length) {
						
					} else {
						String[] infields=new String[]{"0"};
						if (parentid!=null&&!parentid.equals("")&&Integer.valueOf(parentid)>0) {
							List<MwTestdriverfield> field=(List<MwTestdriverfield>)commonService.list(MwTestdriverfield.class);
							if (field!=null&&field.size()>0) {
								if (field.get(0).getId()==parentid) {
									String tmp=field.get(0).getId().replace(",,", ",");
									infields=tmp.split(",");
								}
							}
						}
						String[] arrplace=arruids[Integer.valueOf(placeid)].split(",");
						for (int i = 0; i < arrplace.length; i++) {
							Integer fid=Integer.valueOf(arrplace[i]);
							MwTestdriverfield mwTestdriverfield=(MwTestdriverfield)commonService.get(arrplace[i], MwTestdriverfield.class);
							rtn.add(mwTestdriverfield);
						}
					}
				}
			}
		}
		
		resp.setData(rtn);
		resp.setTotal(rtn.size() + "");
		resplist.setList(resp);
		resplist.setRespStatus(resp.getRespStatus());
		resp.setRespStatus(null);
		return resplist;
	}
	
	@RequestMapping("saveestimate")
	public @ResponseBody RestResponse Saveestimate(Model model,
			@RequestParam(value = "cid") String cid,
			@RequestParam(value = "changdiId") String changdiId,
			@RequestParam(value = "changdi") String changdi,
			@RequestParam(value = "studentId") String studentId,
			@RequestParam(value = "student") String student,
			@RequestParam(value = "CCourseid") String CCourseid,
			@RequestParam(value = "CCourse") String CCourse,
			@RequestParam(value = "CTeacherid") String CTeacherid,
			@RequestParam(value = "CTeacher") String CTeacher,
			@RequestParam(value = "chexiId") String chexiId,
			@RequestParam(value = "chexi") String chexi,
			@RequestParam(value = "field") String field,
			@RequestParam(value = "fvale") String fvale,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		
		String[] arrfield = field.split(",");
		String[] arrvalue = fvale.split(",");
				
		int i=0;
		for (String s : arrfield){
			MwTestdriverpg mwTestdriverpg = new MwTestdriverpg();
			mwTestdriverpg.setChexi(chexi);
			mwTestdriverpg.setChangdiId(Integer.valueOf(changdiId));
			mwTestdriverpg.setChangdi(changdi);
			mwTestdriverpg.setStudent(student);
			mwTestdriverpg.setStudentId(studentId);
			mwTestdriverpg.setCreateTime(new Date());
			mwTestdriverpg.setCid(Integer.valueOf(cid));
			mwTestdriverpg.setCCourseid(CCourseid);
			mwTestdriverpg.setCCourse(CCourse);
			mwTestdriverpg.setCTeacherid(CTeacherid);
			mwTestdriverpg.setCTeacher(CTeacher);
			mwTestdriverpg.setChexiId(chexiId);
			mwTestdriverpg.setFid(Integer.valueOf(s));	
			if (i<=arrvalue.length-1) {
				mwTestdriverpg.setFvalue(Integer.valueOf(arrvalue[i]));
			}			
            if(Integer.valueOf(s)>0) commonService.saveOrUpdateTX(mwTestdriverpg);
            i++;
		}
		resp.setData(null);
		return resp;
	}
	
	@RequestMapping("getmyestimate")
	public @ResponseBody RestResponseList Getmyestimate(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "placeid") String placeid,
			@RequestParam(value = "changdiid") String changdiid,
			@RequestParam(value = "chexiid") String chexiid,
			@RequestParam(value = "studentid") String studentid,
			@RequestParam(value = "teacherid") String teacherid,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriver> list=null;

	    list=(List<MwTestdriver>)commonService.list("from MwTestdriver t where t.id=? and t.CCode", id,code);
	    if (list!=null&&list.size()>0) {
			String ids="";
			if (list.size()==1) {
				ids="("+list.get(0).getId()+")";
			}else {
				for (int i = 0; i < list.size(); i++) {
					if (i==0) {
						ids="("+list.get(i).getId();
					}else if (i==list.size()-1) {
						ids +=","+list.get(i).getId()+")";
					}else {
						ids +=","+list.get(i).getId();
					}
				}
			}
			if (!ids.equals("")) {
				List<MwTestdriverpg> pglisList=(List<MwTestdriverpg>)commonService.list("from MwTestdriverpg t where t.cid in ? and t.changdiId=? and t.chexiId=? and t.studentId=? and t.CTeacherid=?", ids,changdiid,chexiid,studentid,teacherid);
				if (pglisList!=null&&pglisList.size()>0) {
					resp.setData(pglisList);
					resp.setTotal(pglisList.size() + "");
					resplist.setList(resp);
					resplist.setRespStatus(resp.getRespStatus());
					resp.setRespStatus(null);
				}
			}
		}
		
		return resplist;
	}
	
	@RequestMapping("getestimate")
	public @ResponseBody RestResponseList Getestimate(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "changdiid") String changdiid,
			@RequestParam(value = "chexiid") String chexiid,
			@RequestParam(value = "studentid") String studentid,
			@RequestParam(value = "parentid") String parentid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriver> list=(List<MwTestdriver>)commonService.list("from MwTestdriver t where t.id=? and t.CCode", id,code);
		List<MwTestdriverfield> fdList=(List<MwTestdriverfield>)commonService.list("from MwTestdriverfield t where t.parentId=?",parentid);
		String cids="",fids="";
		if (list!=null&&list.size()>0) {
			if (list.size()==1) {
				cids="("+list.get(0).getId()+")";
			}else {
				for (int i = 0; i < list.size(); i++) {
					if (i==0) {
						cids="("+list.get(i).getId();
					}else if (i==list.size()-1) {
						cids +=","+list.get(i).getId()+")";
					}else {
						cids +=","+list.get(i).getId();
					}
				}
			}
		}
		
		if (fdList!=null&&fdList.size()>0) {
			if (fdList.size()==1) {
				fids="("+fdList.get(0).getId()+")";
			}else {
				for (int i = 0; i < fdList.size(); i++) {
					if (i==0) {
						fids="("+fdList.get(i).getId();
					}else if (i==fdList.size()-1) {
						fids +=","+fdList.get(i).getId()+")";
					}else {
						fids +=","+fdList.get(i).getId();
					}
				}
			}
		}
		
		List<MwTestdriverpg> pgList=(List<MwTestdriverpg>)commonService.list("from MwTestdriverpg t where t.fid in ? and t.cid in ? and t.changdiId=? and t.chexiId=? and t.studentId=?",fids,cids,changdiid,chexiid,studentid);
		
		if (pgList!=null&&pgList.size()>0) {
			resp.setData(pgList);
			resp.setTotal(pgList.size() + "");
			resplist.setList(resp);
			resplist.setRespStatus(resp.getRespStatus());
			resp.setRespStatus(null);
		}
		return resplist;
	}
	
	@RequestMapping("getestimate2")
	public @ResponseBody RestResponseList Getestimate2(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "changdiid") String changdiid,
			@RequestParam(value = "chexiid") String chexiid,
			@RequestParam(value = "studentid") String studentid,
			@RequestParam(value = "parentid") String parentid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriver> driverlistList=null;
		if (id!=null&&!id.equals("")) {
			driverlistList=(List<MwTestdriver>)commonService.list("from MwTestdriver t where id=?", id);
		}else if (code!=null&&!code.equals("")) {
			driverlistList=(List<MwTestdriver>)commonService.list("from MwTestdriver t where CCode=?", id);
		}
		String dids="";
		if (driverlistList!=null&&driverlistList.size()>0) {
			if (driverlistList.size()==1) {
				dids="("+driverlistList.get(0).getId()+")";
			}else {
				for (int i = 0; i < driverlistList.size(); i++) {
					if (i==0) {
						dids="("+driverlistList.get(i).getId();
					}else if (i==driverlistList.size()-1) {
						dids +=","+driverlistList.get(i).getId()+")";
					}else {
						dids +=","+driverlistList.get(i).getId();
					}
				}
			}
		}
		
		List<MwTestdriverfield> fdlist=(List<MwTestdriverfield>)commonService.list("from MwTestdriverfield f where parentId=?", parentid);
		String fids="";
		if (fdlist!=null&&fdlist.size()>0) {
			if (fdlist.size()==1) {
				fids="("+fdlist.get(0).getId()+")";
			} else {
				for (int i = 0; i < fdlist.size(); i++) {
					if (i==0) {
						fids="("+fdlist.get(i);
					}else if (i==fdlist.size()-1) {
						fids +=","+fdlist.get(i).getId()+")";
					}else {
						fids +=","+fdlist.get(i).getId();
					}
				}
			}
		}
		String hql="from MwTestdriverpg p where fid in "+fids+" and cid in "+dids;
		if (changdiid!=null&&!changdiid.equals("")) {
			hql+=" and changdiId="+changdiid;
		}
		if (chexiid!=null&&!chexiid.equals("")) {
			hql+=" and chexiId="+chexiid;
		}
		if (studentid!=null&&!studentid.equals("")) {
			hql+=" and studentId="+studentid;
		}
		List<MwTestdriverpg> pglist=(List<MwTestdriverpg>)commonService.list(hql, null);
		
		resp.setData(pglist);
		resp.setTotal(pglist.size() + "");
		resplist.setList(resp);
		resplist.setRespStatus(resp.getRespStatus());
		resp.setRespStatus(null);
		return resplist;
	}
	
	@RequestMapping("getracerank")
	public @ResponseBody RestResponseList Getracerank(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "changdiid") String changdiid,
			@RequestParam(value = "chexiid") String chexiid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriver> list=(List<MwTestdriver>)commonService.list("from MwTestdriver t where t.id=? and t.CCode", id,code);
		String cids="";
		if (list!=null&&list.size()>0) {
			if (list.size()==1) {
				cids="("+list.get(0).getId()+")";
			}else {
				for (int i = 0; i < list.size(); i++) {
					if (i==0) {
						cids="("+list.get(i).getId();
					}else if (i==list.size()-1) {
						cids +=","+list.get(i).getId()+")";
					}else {
						cids +=","+list.get(i).getId();
					}
				}
			}
		}
		List<MwTestdrivertime> tList=(List<MwTestdrivertime>)commonService.list("from MwTestdrivertime t where t.cid in ? and t.changdiId=? and t.chexiId=?", cids,changdiid,chexiid);
		if (tList!=null&&tList.size()>0) {
			resp.setData(tList);
			resp.setTotal(tList.size() + "");
			resplist.setList(resp);
			resplist.setRespStatus(resp.getRespStatus());
			resp.setRespStatus(null);
		}
		return resplist;
	}
	
	@RequestMapping("getdriverlog")
	public @ResponseBody RestResponseList Getdriverlog(Model model,
			@RequestParam(value = "id") String id,
			@RequestParam(value = "code") String code,
			@RequestParam(value = "studentId") String studentId,
			@RequestParam(value = "chexiid") String chexiid,
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password) {

		RestResponse resp = this.validateUser(username, password);
		RestResponseList resplist = new RestResponseList();
		List<MwTestdriver> list=(List<MwTestdriver>)commonService.list("from MwTestdriver t where t.id=? and t.CCode", id,code);
		String cids="";
		if (list!=null&&list.size()>0) {
			if (list.size()==1) {
				cids="("+list.get(0).getId()+")";
			}else {
				for (int i = 0; i < list.size(); i++) {
					if (i==0) {
						cids="("+list.get(i).getId();
					}else if (i==list.size()-1) {
						cids +=","+list.get(i).getId()+")";
					}else {
						cids +=","+list.get(i).getId();
					}
				}
			}
		}
		List<MwTestdrivertime> tList=(List<MwTestdrivertime>)commonService.list("from MwTestdrivertime t where t.cid in ? and t.chexiId=? and t.studentId=?", cids,chexiid,studentId);
		if (tList!=null&&tList.size()>0) {
			resp.setData(tList);
			resp.setTotal(tList.size() + "");
			resplist.setList(resp);
			resplist.setRespStatus(resp.getRespStatus());
			resp.setRespStatus(null);
		}
		return resplist;
	}
	
	private RestResponse validateUser(String username, String password) {
		EcanUser user = (EcanUser) this.commonService.listOnlyObject(
				"from EcanUser t where t.loginName = ?", username); // username

		if (user == null) {
			return RestResponse.authedFailedWithErrorUserIDOrPasswd();
		} else {
			String md5Passwd = MD5.encode(user.getLoginPasswd());
			if (!StringUtils.equals(password, md5Passwd)) {
				return RestResponse.authedFailedWithErrorUserIDOrPasswd();
			} else {
				if (!StringUtils.equals(user.getStatus(),
						EcanUser.STATUS.ACTIVE)) {
					return RestResponse.authedFailedWithUserStatus();
				} else {
					user.setLoginPasswd(null);

					return RestResponse.success(user);
				}
			}
		}

	}
}
