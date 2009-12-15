/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cititool.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Administrator
 */
public class SwingThreadPool {
    
    private static ExecutorService pool=Executors.newCachedThreadPool();

    public static void execute(Runnable task){
        pool.execute(task);
    }

}
