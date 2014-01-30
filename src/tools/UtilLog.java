/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;


import android.util.Log;

/**
 *
 * @author leo
 */
public class UtilLog {
	   private static final String TAG = "photoguessaro";

	    public static void d(String msg) {
	        android.util.Log.d(TAG,TAG+"    " +msg);
	    }
	    public static void e(String msg) {
	        android.util.Log.e(TAG,TAG+"    " +msg);
	    }
	    
	    public static void i(String msg) {
	        android.util.Log.i(TAG,TAG+"    " +msg);
	    }
    public static void printException(Object className, Exception exception) {
//        Log.e("error start", String.format("***************ClassName %s***********", className.toString()));
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        exception.printStackTrace(pw);
//        Log.v("error", "" + sw.toString());
//        Log.e("error end", "****************************************");
       // logFile(className.toString(), sw.toString());
    }

//    public static void logFile(String className, String exception) {
//        try {
//
//            File file = new File(LocationConstants.LOCATION_LOG_FILE);
//            if (!file.exists()) {
//                if (!file.mkdirs()) {
//                    return;
//                }
//            }
//
//            FileOutputStream log = new FileOutputStream(file.getAbsolutePath() + "/log.txt", true);
//
//            PrintStream printStream = new PrintStream(log);
//
//            printStream.println("CLASS : " + className);
//
//            printStream.println("TIME : " + (new Date().toString().substring(11, 19)));
//            printStream.println("\n------------------ERROR START-----------------------------------------\n");
//          
//            printStream.println(exception);
//            printStream.println("\n------------------ERROR END-----------------------------------------\n");
//            printStream.println();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public static void printStackFlow(String TAG,String functionName,Exception e)
            
    {
        Log.e(TAG,functionName);    
        e.printStackTrace();
        System.out.println("\n\n");
    }
    
    
    public static void printFormatted(String message, Object... args)
    {
    	System.out.println(String.format(message, args));
    }
   
}
