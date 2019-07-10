/**
 * 
 */
package com.cn.springbootjpa.mq.msgbo;

import com.cn.springbootjpa.base.bo.BaseBo;
import com.cn.springbootjpa.mq.msgentity.TiIfsMqsOutMsg;

/**
 * spel表达式只能写在接口上 前端请求的是接口不是实现类
 * 
 * @author zhangyang
 *
 */

public interface TiIfsMqsOutMsgBo extends BaseBo<TiIfsMqsOutMsg, Integer> {
}
