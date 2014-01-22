package info.androidhive.googlemapsv2;

import android.app.Activity;
import android.os.Handler;

public class DelayedCall implements Runnable{
	
	Object[]  arguments;
	Handler mHandler = new Handler();
	long milliSecondsDelay;
	public interface RunonUiInterfaceListner
	{
		public void onUiRun();
	}
	public interface CallBack
	{
		public void onCallBack(Object... args);
	}
	CallBack callBack;
	public DelayedCall() {
		// TODO Auto-generated constructor stub
	}
	
	public void setCallback(long delayedMillis, final CallBack callBack, Object...args )
	{
		this.callBack = callBack; 
		this.arguments = args;
		this.milliSecondsDelay = delayedMillis;
	}
	
	public  void callBackAfter( )
	{
		
		mHandler.postDelayed(this, this.milliSecondsDelay);
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		this.callBack.onCallBack(this.arguments);
		
		
	}
	
	public static DelayedCall delayedCall(long delayedMillis,CallBack callBack, Object...args)
	{
		DelayedCall delayedCall = new DelayedCall();
		delayedCall.setCallback(delayedMillis, callBack, args);
		delayedCall.callBackAfter();
		return delayedCall;
	}
	
	public static void runOnUiThread(Activity activity, final RunonUiInterfaceListner runonUiInterfaceListner)
	{
		activity.runOnUiThread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				runonUiInterfaceListner.onUiRun();
			}
		});
	}
	

}
