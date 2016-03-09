package com.ecannetwork.tech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecannetwork.core.app.domain.DomainFacade;
import com.ecannetwork.core.module.service.CommonService;
import com.ecannetwork.core.util.CoreConsts;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.dto.core.EcanDomainDTO;
import com.ecannetwork.dto.core.EcanDomainvalueDTO;
import com.ecannetwork.dto.core.EcanUser;
import com.ecannetwork.dto.tech.TechExamAnswer;
import com.ecannetwork.dto.tech.TechExamMain;
import com.ecannetwork.dto.tech.TechExamQuestion;
import com.ecannetwork.tech.controller.bean.RestResponse;

@Component("importExcel")
public class ImportExcel
{
	@Autowired
	private DomainFacade domainFacade;
	@Autowired
	private CommonService commonService;
	
	public ImportExcel()
	{

	}

	// 通过excel导入用户
	public List<EcanUser> impUsers(String fileName)
	{
		List<EcanUser> userList = null;
		long l = System.currentTimeMillis();

		FileInputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(fileName);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			userList = new ArrayList<EcanUser>();
			if (rows > 1)
			{
				for (int k = 1; k < rows; k++)
				{
					EcanUser user = new EcanUser();
					HSSFRow row = sheet.getRow(k);
					if (row != null)
					{
						HSSFCell cell_0 = row.getCell(0);
						if (cell_0 == null
								|| "".equals(cell_0.getStringCellValue()))
						{
							break;
						}
						int cells = 13;
						for (int i = 0; i <= cells; i++)
						{
							HSSFCell cell = row.getCell(i);
							if (cell == null)
							{
								continue;
							}

							switch (i)
							{
							case 0:
								user.setName(judeType(cell));
								break;
							case 1:
								user.setLoginName(judeType(cell));
								break;
							case 2:
								user.setLoginPasswd(judeType(cell));
								break;
							case 3:
								user.setNickName(judeType(cell));
								break;
							case 4:
								user.setBirthday(judeType(cell));
								break;
							case 5:
								user.setCardId(judeType(cell));
								break;
							case 6:
								user.setCaller(getDomainValue("CALLER",
										judeType(cell)));
								break;
							case 7:
								user.setSex(getDomainValue("SEX",
										judeType(cell)));
								break;
							case 8:
								user.setCompany(getDomainValue("COMPANY",
										judeType(cell)));
								break;
							case 9:
								user.setPosition(getDomainValue("POSITION",
										judeType(cell)));
								break;
							case 10:
								user.setRoleId(getDomainValue("ROLE",
										judeType(cell)));
								break;
							case 11:
								user.setMobile(judeType(cell));
								break;
							case 12:
								user.setTel(judeType(cell));
								break;
							case 13:
								user.setEmail(judeType(cell));
								break;
							default:
								break;
							}
						}
					}

					if (StringUtils.isBlank(user.getLoginName())
							|| StringUtils.isBlank(user.getLoginPasswd())
							|| StringUtils.isBlank(user.getCompany())
							|| StringUtils.isBlank(user.getRoleId())
							|| StringUtils.isBlank(user.getEmail()))
					{
						System.out.println("Skip User:\tname:" + user.getName()
								+ "\t:" + user.getLoginPasswd() + "\t:"
								+ user.getCompany() + "\t:" + user.getRoleId());
						continue;
					}
					userList.add(user);
				}
			}
			// inputStream.
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				inputStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("导入计算时间============"
				+ (System.currentTimeMillis() - l));
		return userList;
	}

	@SuppressWarnings(
	{ "unchecked" })
	public List<EcanUser> batchSave(String pathfileName)
	{
		List<EcanUser> imported = new ArrayList<EcanUser>();
		
		List<EcanUser> list = impUsers(pathfileName);
		System.out.println("import list size" + list);

		for (EcanUser u : list)
		{
			u.setStatus(EcanUser.STATUS.INACTIVE);
			List<EcanUser> uu = this.commonService.list(
					"from EcanUser t where t.loginName=?", u.getLoginName());
			if (uu != null && uu.size() > 0)
			{
				continue;
			} else
			{
				this.commonService.saveTX(u);
				imported.add(u);
			}
		}

		return imported;
	}

	public String judeType(HSSFCell cell)
	{
		switch (cell.getCellType())
		{
		case HSSFCell.CELL_TYPE_STRING:
		{
			return cell.getStringCellValue();
		}
		case HSSFCell.CELL_TYPE_BOOLEAN:
		{
			return (String.valueOf(cell.getBooleanCellValue()));
		}
		case HSSFCell.CELL_TYPE_FORMULA:
		{
			return (String.valueOf(cell.getCellFormula() + ""));
		}
		case HSSFCell.CELL_TYPE_NUMERIC:
			return (String.valueOf(cell.getNumericCellValue() + ""));
		default:
		{
			return (cell.getStringCellValue());
		}
		}
	}

	private String getDomainValue(String domain, String key)
	{
		String value = "";
		Map<String, EcanDomainDTO> domainMap = domainFacade.getDomainMap();
		if (domainMap.containsKey(domain))
		{
			EcanDomainDTO ecanDomain = (EcanDomainDTO) domainMap.get(domain);
			List<EcanDomainvalueDTO> domainValuesList = ecanDomain.getValues();
			for (EcanDomainvalueDTO ecanDomainvalueDTO : domainValuesList)
			{
				if (ecanDomainvalueDTO != null)
				{
					if (key.equals(ecanDomainvalueDTO.getDomainlabel()))
					{
						value = ecanDomainvalueDTO.getDomainvalue();
					}
				}
			}
		}
		return value;
	}
	
	// 通过excel导入考试试题
	public TechExamMain impExam(String fileName)
	{
		long l = System.currentTimeMillis();
		int count=1;
		FileInputStream inputStream = null;
		try
		{
			inputStream = new FileInputStream(fileName);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			
			HSSFSheet sheet0 = wb.getSheetAt(0);
			
			TechExamMain exam = new TechExamMain();
			
			//试题标题
			exam.setTitle(judeType(sheet0.getRow(0).getCell(0)));
			//单选分
			exam.setSingleScort((int)Double.parseDouble(judeType(sheet0.getRow(2).getCell(1))));
			//多选分
			exam.setMultiScort((int)Double.parseDouble(judeType(sheet0.getRow(3).getCell(1))));
			//及格分
			exam.setPassLevel((int)Double.parseDouble(judeType(sheet0.getRow(5).getCell(1))));
			//考试次数
			exam.setLeftCount((int)Double.parseDouble(judeType(sheet0.getRow(7).getCell(1))));
			//查看答案
			exam.setShowAnswer("Y".equals(judeType(sheet0.getRow(8).getCell(1)))?1:0);
			//随机数量
			exam.setRandomCount((int)Double.parseDouble(judeType(sheet0.getRow(9).getCell(1))));
			
			exam.setType(1);
			exam.setFlag(1);
			
			TechExamMain test = new TechExamMain();
			//试题标题
			test.setTitle(judeType(sheet0.getRow(0).getCell(0)));
			//单选分
			test.setSingleScort((int)Double.parseDouble(judeType(sheet0.getRow(2).getCell(1))));
			//多选分
			test.setMultiScort((int)Double.parseDouble(judeType(sheet0.getRow(3).getCell(1))));
			//及格分
			test.setPassLevel((int)Double.parseDouble(judeType(sheet0.getRow(5).getCell(1))));
			//考试次数
			test.setLeftCount((int)Double.parseDouble(judeType(sheet0.getRow(7).getCell(1))));
			//查看答案
			test.setShowAnswer("Y".equals(judeType(sheet0.getRow(8).getCell(1)))?1:0);
			//随机数量
			test.setRandomCount((int)Double.parseDouble(judeType(sheet0.getRow(9).getCell(1))));
			
			RestResponse resp =  RestResponse.success(null);
			resp.setProperty(test);
			
			HSSFSheet sheet = wb.getSheetAt(1);
			int rows = sheet.getPhysicalNumberOfRows();
			//TechExamMain exam = new TechExamMain();
			if (rows > 1)
			{
				TechExamQuestion question = new TechExamQuestion();
				for (int k = 1; k < rows; k++)
				{
					
					TechExamAnswer answer = new TechExamAnswer();
					answer.setId("answer-"+k);
					HSSFRow row = sheet.getRow(k);
					if(judeType(row.getCell(4)) == null || row.getCell(4).getCellType()==3){
						continue;
					}
					if (row != null)
					{
						if(row.getCell(0) != null && row.getCell(0).getCellType()!=3){
							
							question = new TechExamQuestion();
							question.setId("quest-"+count);
							question.setTitle(judeType(row.getCell(1)));
							question.setType(judeType(row.getCell(2)));
							question.setFlag(1);
							question.setMainId(exam.getId());
							exam.getQuestionList().add(question);
							count=0;
						}
						if(row.getCell(4) != null && row.getCell(4).getCellType()!=3){
							count++;
							answer.setId("--answer-"+k);
							answer.setIndex(count+"");
							answer.setIdx(judeType(row.getCell(3)));
							answer.setTitle(judeType(row.getCell(4)));
							answer.setOption(judeType(row.getCell(4)));
							answer.setIsRight(judeType(row.getCell(5)));
							answer.setQuesId(question.getId());
							question.getItems().add(answer);
							
						}
						
					}
				}
			}
			resp.setData(exam.getQuestionList());
			
			String beJson = JsonFactory.toJson(resp);
			
			String filePathString = File.separator + TechConsts.CREATE_EXAM_JSON
			+ File.separator ;
			
			String storeFileNameWithPath = CoreConsts.Runtime.APP_ABSOLUTE_PATH
			+ filePathString;
			
			PrintStreamFile(beJson, storeFileNameWithPath);
			
			return exam;
			// inputStream.
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				inputStream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("导入计算时间============"
				+ (System.currentTimeMillis() - l));
		return new TechExamMain();
	}
	
	 public void PrintStreamFile(String content,String filePath){
         try{
             FileOutputStream out=new FileOutputStream(filePath);
             PrintStream p=new PrintStream(out);
             p.println(content);
         } catch (FileNotFoundException e){
             e.printStackTrace();
         }
     }
	
	private static org.apache.commons.logging.Log log = LogFactory
			.getLog(ImportExcel.class);
	
	public static void main(String[] args){
		//TechExamMain exam = new TechExamMain();
		//System.out.println(exam.getId());
		//RestResponse resp =  RestResponse.success(null);
		ImportExcel imp = new ImportExcel();
		TechExamMain exam = imp.impExam("C:\\Users\\bluebirds\\Desktop\\demo\\考试试题模板草稿.xls");
		System.out.println(exam.getQuestionList().size());
		//exam.setQuestionList(null);
		//System.out.println(JsonFactory.toJson(exam));
		//resp.setData(exam.getQuestionList());
		//System.out.println(JsonFactory.toJson(resp));
		//System.out.println(examList.get(0).getQuestionList());
		/*for(TechExamQuestion quest:examList.get(0).getQuestionList()){
			for(TechExamAnswer answer:quest.getAnswerList()){
				System.out.println("quest:"+quest.getId()+"--"+quest.getTitle()+"----answer:"+answer.getId()+"---"+answer.getTitle()+"---"+answer.getIdx()+"---"+answer.getIsRight());
			}
		}*/
	}
}

