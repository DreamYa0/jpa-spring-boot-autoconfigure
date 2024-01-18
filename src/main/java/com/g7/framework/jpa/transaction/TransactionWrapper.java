package com.g7.framework.jpa.transaction;

import com.g7.framework.framwork.exception.BusinessException;
import com.g7.framework.framwork.exception.meta.CommonErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

/**
 * @author dreamyao
 * @title 编程式事物基础类
 * @date 2021/11/14 下午20:43
 * @since 1.1.0
 */
public class TransactionWrapper {

    private static final Logger logger = LoggerFactory.getLogger(TransactionWrapper.class);
    private static final String TX_ID = "TxId";

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 编程事务
     * <p>
     * 有异常则外层捕获 BusinessException
     * @return 方法执行自定义返回值
     * @author dreamyao
     */
    public <T> T transaction(final Callback<T> callback) {

        MDC.put(TX_ID, UUID.randomUUID().toString().replaceAll("-", ""));

        return transactionTemplate.execute(status -> {

            try {

                return callback.execute();

            } catch (BusinessException e) {
                logger.info("business exception caught in transaction : {}", e.getMessage());
                status.setRollbackOnly();
                throw e;
            } catch (Throwable e) {

                logger.error("TransactionWrapper transaction error : {}", e.getMessage());
                status.setRollbackOnly();

                BusinessException businessException = new BusinessException(CommonErrorCode.TRANSACTION_EXCEPTION);
                businessException.initCause(e);
                throw businessException;
            }
        });
    }
}



