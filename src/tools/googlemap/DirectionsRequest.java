package tools.googlemap;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;

import tools.UtilHttp;
import tools.asynchronous.BackgroundHandler;
import tools.asynchronous.BackgroundListener;

public class DirectionsRequest implements BackgroundListener {

	ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

	public interface ResponseListner {
		public void onResponse(String response, Exception exception);
	}

	ResponseListner aResponseListner;

	public DirectionsRequest(boolean sensor, String origin, String destination,
			ResponseListner responseListner) {

		params.add(new BasicNameValuePair(Constants.PARAMETER_ORIGIN, origin));
		params.add(new BasicNameValuePair(Constants.PARAMETER_DESTINATION,
				destination));
		params.add(new BasicNameValuePair(Constants.PARAMETER_SENSOR, String
				.valueOf(sensor)));
		aResponseListner = responseListner;
		new BackgroundHandler().execute(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object[] startBackgroundWork() {
		// TODO Auto-generated method stub

		try {
			return new Object[] {
					UtilHttp.httpGetConnection(Constants.DIRECTIONS_API_URI,
							params), null };
		} catch (Exception exception) {
			return new Object[] { "", exception };
		}

	}

	@Override
	public void endBackgroundWork(Object... obj) {
		// TODO Auto-generated method stub
		Exception exception = null;
		exception = (obj[1] != null) ? (Exception) obj[1] : null;

		aResponseListner.onResponse((String) obj[0], exception);
	}

}
