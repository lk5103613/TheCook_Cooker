package com.john.guo.util;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONException;

public class UploadUtil {

	public static String post(File file, String urlServer, String serverImgName)
			throws ClientProtocolException, IOException, JSONException {
		HttpClient httpclient = new DefaultHttpClient();
		// ����ͨ��Э��汾
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		// File path= Environment.getExternalStorageDirectory(); //ȡ��SD����·��

		// String pathToOurFile = path.getPath()+File.separator+"ak.txt";
		// //uploadfile
		// String urlServer = "http://192.168.1.88/test/upload.php";

		HttpPost httppost = new HttpPost(urlServer);
		// File file = new File(pathToOurFile);

		MultipartEntity mpEntity = new MultipartEntity(); // �ļ�����
		ContentBody cbFile = new FileBody(file);
		mpEntity.addPart("filename", cbFile); // <input type="file"
												// name="userfile" /> ��Ӧ��
		ContentBody cbStr = new StringBody(serverImgName);
		mpEntity.addPart("serverImgName", cbStr);

		httppost.setEntity(mpEntity);
		System.out.println("executing request " + httppost.getRequestLine());

		HttpResponse response = httpclient.execute(httppost);
		HttpEntity resEntity = response.getEntity();

		System.out.println(response.getStatusLine());// ͨ��Ok
		String json = "";
		String path = "";
		if (resEntity != null) {

			// json=EntityUtils.toString(resEntity,"utf-8");

		}
		if (resEntity != null) {
			resEntity.consumeContent();
		}

		httpclient.getConnectionManager().shutdown();
		return path;
	}

}
