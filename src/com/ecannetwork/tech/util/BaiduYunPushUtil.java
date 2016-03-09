package com.ecannetwork.tech.util;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.ecannetwork.core.util.Configs;
import com.ecannetwork.core.util.JsonFactory;
import com.ecannetwork.dto.tech.TechUserMessage;

@Component("baiduYunPush")
public class BaiduYunPushUtil {

	/**
	 * 百度云推送
	 * @param title
	 * @param content
	 * @param channelid
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	public static void pushMsgToSingleDevice(String notification,String channelid,int deviceType,String keyType) throws PushClientException,
	PushServerException {
		// 1. get apiKey and secretKey from developer console
		//String apiKey = "9AP19dzxhVmFOq06q0PX4seS";
		String apiKey = Configs.get("baidu.push.apiKey."+keyType);
		//String secretKey = "w28RS1vV57B1jfVYcI9VjidEL8IeGGic";
		String secretKey = Configs.get("baidu.push.secretKey."+keyType);
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
		
		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
		BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				//System.out.println(event.getMessage());
			}
		});

		try {
			// 4. specify request arguments
			//创建 Android的通知
			/*JSONObject notification = new JSONObject();
			notification.put("title", title);
			notification.put("description",content);
			notification.put("notification_builder_id", 0);
			notification.put("notification_basic_style", 4);
			notification.put("open_type", 1);
			notification.put("url", "http://push.baidu.com");
			JSONObject jsonCustormCont = new JSONObject();
			jsonCustormCont.put("key", "value"); //自定义内容，key-value
			notification.put("custom_content", jsonCustormCont);*/
			int deployStatus = 2;
			String tem_status = Configs.get("baidu.push.deploy.status");
			if(tem_status!=null){
				deployStatus = Integer.parseInt(tem_status);
			}
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
			//.addChannelId("4519321383017010259")
			//.addChannelId("3843094822427635775")
			.addChannelId(channelid)
			.addMsgExpires(new Integer(3600)). // message有效时间
			addMessageType(1).// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
			addMessage(notification).addDeployStatus(deployStatus).//TODO 生产需要修改为2
			addDeviceType(deviceType);// deviceType => 3:android, 4:ios
			// 5. http request
			PushMsgToSingleDeviceResponse response = pushClient
					.pushMsgToSingleDevice(request);
			// Http请求结果解析打印
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime());
		} catch (PushClientException e) {
			/*
			 * ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
			 */
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMessage: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}
	}
	
	/**
	 * IOS推送
	 * @param title
	 * @param content
	 * @param channelid
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	/*public static void IOSPushNotificationToSingleDevice(String title,String content,String channelid)throws PushClientException,PushServerException {
		
		// 1. get apiKey and secretKey from developer console
		 String apiKey = "xxxxxxxxxxxxxxxx";
		 String secretKey = "xxxxxxxxxxxxxxxxxxxxxxxxxx";
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);

		// 2. build a BaidupushClient object to access released interfaces
		BaiduPushClient pushClient = new BaiduPushClient(pair,
				BaiduPushConstants.CHANNEL_REST_URL);

		// 3. register a YunLogHandler to get detail interacting information
		// in this request.
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. specify request arguments
			// make IOS Notification
			JSONObject notification = new JSONObject();
			JSONObject jsonAPS = new JSONObject();
			jsonAPS.put("alert", "Hello Baidu Push");
			jsonAPS.put("sound", "ttt"); // 设置通知铃声样式，例如"ttt"，用户自定义。
			notification.put("aps", jsonAPS);
			notification.put("key1", "value1");
			notification.put("key2", "value2");

			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
					.addChannelId("xxxxxxxxxxxxxxxxxx")
					.addMsgExpires(new Integer(3600)). // 设置message的有效时间
					addMessageType(1).// 1：通知,0:透传消息.默认为0 注：IOS只有通知.
					addMessage(notification.toString()).addDeployStatus(2). // IOS,
																			// DeployStatus
																			// => 1: Developer
																			// 2: Production.
					addDeviceType(4);// deviceType => 3:android, 4:ios
			// 5. http request
			PushMsgToSingleDeviceResponse response = pushClient
					.pushMsgToSingleDevice(request);
			// Http请求结果解析打印
			System.out.println("msgId: " + response.getMsgId() + ",sendTime: "
					+ response.getSendTime());
		} catch (PushClientException e) {
			
			 * ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,'true' 表示抛出, 'false' 表示捕获。
			 
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format(
						"requestId: %d, errorCode: %d, errorMessage: %s",
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
			}
		}
	}*/
	
	public static void main(String[] args){
		try {
			pushMsgToSingleDevice(null, "3843094822427635775", 3,"ios");
		} catch (PushClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PushServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
