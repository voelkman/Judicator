/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator;

import de.voelkman.judicator.anno.MVTestInterceptor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author g8712
 */
@MVTestInterceptor
@Interceptor
public class TestInterceptor {

    @AroundInvoke
    public Object test(InvocationContext context){
        Logger.getAnonymousLogger().log(Level.WARNING, "called {0}", context.getMethod().getName());
        try {
            context.proceed();
        } catch (Exception ex) {
            Logger.getLogger(TestInterceptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getAnonymousLogger().log(Level.WARNING, "Done", context.getMethod().getName());
        return null;
    }
}
