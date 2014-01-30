/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tools.asynchronous;

import android.os.AsyncTask;


/**
 *
 * @author leo
 */
public class BackgroundHandler extends AsyncTask<Object, Object[], Object[]>{
    BackgroundListener downloadListener;
    

    @Override
    protected Object[] doInBackground(Object... obj) {
        downloadListener = (BackgroundListener)obj[0];
        
        
        return downloadListener.startBackgroundWork();
        
    }

    @Override
    protected void onPostExecute(Object result[]) {
        downloadListener.endBackgroundWork(result);
    }
    

}
