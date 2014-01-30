/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class UtilHttp {

	// Username and password - to access the pages
	// public static final String userName = "sheeja@digitalbrandgroup.com";
	// public static final String password = "jajacks!1";

	public static String httpConnection(String url, ArrayList params) {
		try {
			DefaultHttpClient default_http_client = new DefaultHttpClient();
			HttpPost http_post = new HttpPost(url);
			// http_post.addHeader("Accept", "text/xml");
			http_post.setHeader("Content-Type",
					"application/x-www-form-urlencoded");

			if (params != null) {
				UrlEncodedFormEntity url_encoded_form_entity = new UrlEncodedFormEntity(
						params);

				http_post.setEntity(url_encoded_form_entity);
				// Log.e("uri with params", ""
				// + UtilStream
				// .convertStreamToString(url_encoded_form_entity
				// .getContent()));
			}
			HttpResponse http_response = default_http_client.execute(http_post);
			HttpEntity http_entity = http_response.getEntity();

			return EntityUtils.toString(http_entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String httpGetConnection(String url, ArrayList params) {
		try {
			DefaultHttpClient default_http_client = new DefaultHttpClient();
			
			HttpGet http_post = new HttpGet(url);

			if (params != null) {
				UrlEncodedFormEntity url_encoded_form_entity = new UrlEncodedFormEntity(
						params);
				
				http_post = new HttpGet(url+"?"+EntityUtils.toString(url_encoded_form_entity));

				
				// Log.e("uri with params", ""
				// + UtilStream
				// .convertStreamToString(url_encoded_form_entity
				// .getContent()));
			}
			HttpResponse http_response = default_http_client.execute(http_post);
			HttpEntity http_entity = http_response.getEntity();

			return EntityUtils.toString(http_entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Bitmap downloadFile(String fileUrl) {

		try {
			URL myFileUrl = null;
			myFileUrl = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			System.out.println("Downloading ");
			InputStream is = conn.getInputStream();

			return BitmapFactory.decodeStream(is);

		} catch (Exception e) {

			UtilLog.printException(UtilHttp.class.getClass().getSimpleName(), e);
		}
		return null;
	}

	public static String getVideoRedirectUrl(String url, ArrayList params) {

		try {
			DefaultHttpClient default_http_client = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			HttpContext context = new BasicHttpContext();

			if (params != null) {
				UrlEncodedFormEntity url_encoded_form_entity = new UrlEncodedFormEntity(
						params);
				httpPost.setEntity(url_encoded_form_entity);
			}
			HttpResponse response = default_http_client.execute(httpPost,
					context);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new IOException(response.getStatusLine().toString());
			}
			HttpUriRequest currentReq = (HttpUriRequest) context
					.getAttribute(ExecutionContext.HTTP_REQUEST);
			HttpHost currentHost = (HttpHost) context
					.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
			String currentUrl = currentHost.toURI() + currentReq.getURI();
			return currentUrl;

		} catch (Exception e) {
			UtilLog.printException(UtilHttp.class.getClass().getSimpleName(), e);
		}
		return null;
	}

	public static String downloadAndSave(String url, ArrayList params,
			File filename, Context context) {
		String downloadedLocation = null;
		try {
			DefaultHttpClient default_http_client = new DefaultHttpClient(
					getTimeHttpParams());
			HttpPost http_post = new HttpPost(url);
			// http_post.addHeader("Accept", "text/xml");
			http_post.setHeader("Content-Type",
					"application/x-www-form-urlencoded");

			if (params != null) {
				UrlEncodedFormEntity url_encoded_form_entity = new UrlEncodedFormEntity(
						params);
				http_post.setEntity(url_encoded_form_entity);
			}
			HttpResponse http_response = default_http_client.execute(http_post);
			HttpEntity httpEntity = http_response.getEntity();
			// Log.v("Download and Save", "Download and Save");
			// System.out.println(EntityUtils.toString(httpEntity));
			// Log.v("Download and Save", "Download and Save");
			InputStream inputStream = httpEntity.getContent();

			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			byte bytes[] = new byte[1024];

			int currentSize;
			while ((currentSize = inputStream.read(bytes)) > 0) {
				fileOutputStream.write(bytes, 0, currentSize);
				fileOutputStream.flush();
				if (filename.length() > 30000) {
				}
			}
			fileOutputStream.close();
			if (filename.length() <= 1000) {
				filename.delete();
				return null;
			}
			downloadedLocation = filename.getAbsolutePath();

		} catch (Exception e) {
			UtilLog.printException(UtilHttp.class.getClass().getSimpleName(), e);
			filename.delete();
		}

		return downloadedLocation;
	}

	public static String downloadAndSave(String url, ArrayList params,
			File filename) {
		String downloadedLocation = null;
		try {
			// Log.v("downloadAndSave", "downloadAndSave");
			// System.out.println(url);
			// System.out.println(params);
			// Log.v("downloadAndSave", "downloadAndSave");
			// System.out.println(params);
			DefaultHttpClient default_http_client = new DefaultHttpClient(
					getTimeHttpParams());
			HttpPost http_post = new HttpPost(url);
			// http_post.addHeader("Accept", "text/xml");
			// http_post.setHeader("Content-Type",
			// "application/x-www-form-urlencoded");

			if (params != null) {
				UrlEncodedFormEntity url_encoded_form_entity = new UrlEncodedFormEntity(
						params);
				http_post.setEntity(url_encoded_form_entity);
			}
			HttpResponse http_response = default_http_client.execute(http_post);
			HttpEntity httpEntity = http_response.getEntity();

			InputStream inputStream = httpEntity.getContent();

			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			byte bytes[] = new byte[1024];

			int currentSize;
			while ((currentSize = inputStream.read(bytes)) > 0) {
				fileOutputStream.write(bytes, 0, currentSize);
				fileOutputStream.flush();
				if (filename.length() > 30000) {
				}
			}
			fileOutputStream.close();
			// Log.v("file length", "filelength");
			// System.out.println(filename.length());
			// Log.v("file length", "filelength");
			if (filename.length() <= 1000) {
				filename.delete();
				return null;
			}
			return filename.getAbsolutePath();

		} catch (Exception e) {
			UtilLog.printException(UtilHttp.class.getClass().getSimpleName(), e);
			filename.delete();

		}

		return null;
	}

	public static String getFileExtension(String path) {
		try {

			String fromDot = path.substring(path.lastIndexOf(".") + 1);
			if (!fromDot.equals(path)) {
				return fromDot;
			}
		} catch (Exception e) {
		}
		return "";

	}

	public static HttpParams getTimeHttpParams() {
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is
		// established.
		int timeoutConnection = 3000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT)
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 5000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		return httpParameters;
	}

	public static boolean writeToFile(String filename, byte[] bytes) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filename);

			fileOutputStream.write(bytes, 0, bytes.length);
			fileOutputStream.flush();

			fileOutputStream.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public static boolean downloadFileAndWrite(String fileUrl,String filename) {

		try {
			URL myFileUrl = null;
			myFileUrl = new URL(fileUrl);
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
		
			InputStream is = conn.getInputStream();
			writeToFile(filename, is);
			return true;

		} catch (Exception e) {

			UtilLog.printException(UtilHttp.class.getClass().getSimpleName(), e);
		}
		return false;
	}
	
	public static void writeToFile(String fileName,InputStream inputStream) throws Exception
	{
		
		
		FileOutputStream fileOutputStream = new FileOutputStream(fileName);
		byte bytes[] = new byte[1024];

		int currentSize;
		while ((currentSize = inputStream.read(bytes)) > 0) {
			fileOutputStream.write(bytes, 0, currentSize);
			fileOutputStream.flush();
			
		}
		
		fileOutputStream.close();
		inputStream.close();
	}
}
