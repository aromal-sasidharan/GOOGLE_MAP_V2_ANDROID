/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package tools.asynchronous;

/**
 *
 * @author leo
 */
public interface BackgroundListener {

    public Object[] startBackgroundWork();
    public void endBackgroundWork(Object ...obj);



}
