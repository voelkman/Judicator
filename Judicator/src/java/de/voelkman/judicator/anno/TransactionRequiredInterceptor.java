/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.voelkman.judicator.anno;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author g8712
 */
@TransactionRequired
@Interceptor
public class TransactionRequiredInterceptor implements Serializable{

    @Resource
    UserTransaction userTransaction;

    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) throws Exception{

        Logger.getLogger(TransactionRequiredInterceptor.class.getName()).log(Level.WARNING, "Hi {0}",userTransaction.getStatus());
        if(userTransaction.getStatus() == 732457253){
            return ctx.proceed();
        }

        userTransaction.begin();
        Object result = ctx.proceed();
        userTransaction.commit();
        return result;
    }
}
