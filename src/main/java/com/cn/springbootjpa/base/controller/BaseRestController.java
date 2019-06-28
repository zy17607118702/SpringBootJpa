

package com.cn.springbootjpa.base.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cn.springbootjpa.base.common.Result;
import com.cn.springbootjpa.base.exception.ApplicationException;
import com.cn.springbootjpa.base.exception.SystemException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public abstract class BaseRestController implements MessageSourceAware {

    /* error message code prefix */
    /**
     * error message code prefix
     */
    public static String ERROR_MESSAGE_CODE_PREFIX = "3101";

    protected MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody Result exceptionHandler(HttpServletRequest request, HttpServletResponse response,
            Exception e) {
        log.error("Error while doing restful request.", e);
        e.printStackTrace();
        return resultError(1, handleException(e).getLocalizedMessage());

    }

    private Exception handleException(Exception e) {
        // TODO: translate error message to NLS.
        if (e instanceof DataAccessException) {
            e = translateDataAccessException((DataAccessException) e);
        } else if (e instanceof TransactionException) {
            e = new SystemException("SE0005");
        } else if (e instanceof HibernateException) {
            e = translateHibernateException(e);
        }

        // localize message.
        if (e instanceof ApplicationException) {
            String code = ((ApplicationException) e).getCode();
            String[] params = ((ApplicationException) e).getParams();
            String message = code;
            try {
                // TODO 有待修改
                message = messageSource.getMessage(code, params, Locale.getDefault());
            } catch (Exception ee) {
                log.warn("No message found with key:" + code);
            }
            String tmpCode = code.toUpperCase();
            if (code != null && (tmpCode.startsWith("AE") || tmpCode.startsWith("AW") || tmpCode.startsWith("AI"))) {
                e = new ApplicationException(ERROR_MESSAGE_CODE_PREFIX + code + ": " + message);
            } else {
                if (!(message.startsWith(ERROR_MESSAGE_CODE_PREFIX))) {
                    e = new ApplicationException(ERROR_MESSAGE_CODE_PREFIX + "XXXX: " + message);
                }
            }

        } else if (e instanceof SystemException) {
            String code = ((SystemException) e).getCode();
            String[] params = ((SystemException) e).getParams();
            String message = code;
            try {
                message = messageSource.getMessage(code, params, Locale.getDefault());
            } catch (Exception ee) {
                log.warn("No message found with key:" + code);
            }
            e = new SystemException(ERROR_MESSAGE_CODE_PREFIX + code + ": " + message);
        }
        return e;

    }

    /**
     * 返回带有错误代码和信息的结果
     * 
     * @param errorCode
     * @param errorMessage
     * @return
     */
    protected Result resultError(Integer errorCode, String errorMessage) {
        return new Result(errorCode, errorMessage);
    }

    protected Result resultOk(String message) {
        return new Result(0, message);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * <p>
     * Translates data accessing related Spring framework exception to system
     * specific exception.
     * 
     * @param e exception from hibernate framework.
     * @return System exception wrapped up hibernate exception.
     */
    private SystemException translateDataAccessException(DataAccessException e) {
        Throwable cause = e.getCause();
        SystemException se = new SystemException("SE0002");
        if (cause instanceof ConstraintViolationException) {
            se = new SystemException("SE0004");
        } else if (cause instanceof JDBCException) {
            se = new SystemException("SE0002");
        }
        return se;
    }

    /**
     * <p>
     * Translates OR-Mapping hibernate framework exception to system specific
     * exception.
     * 
     * @param e exception from hibernate framework.
     * @return System exception wrapped up hibernate exception.
     */
    private SystemException translateHibernateException(Exception e) {
        SystemException se = new SystemException("SE0002");

        if (e instanceof ObjectNotFoundException) {
            se = new SystemException("SE0003", new String[] { ((ObjectNotFoundException) e).getEntityName() });
        }

        return se;
    }

}