package gt.maxzhao.ittest.utils;

import lombok.Data;

/**
 * @author maxzhao
 */
public class ResultOJ {
    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String DEFAULT_MSG = "";
    private String msg;
    private String status;
    private Object data;

    private ResultOJ() {
    }

    private ResultOJ(String msg, String status, Object data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public static ResultOJ getDefaultResultOJ(Object data) {
        return ResultOJ.getSuccessResultOJ(DEFAULT_MSG, data);
    }

    public static ResultOJ getSuccessResultOJ(String msg, Object data) {
        return new ResultOJ(msg, SUCCESS_STATUS, data);
    }

    public static ResultOJ getFailResultOJ(String msg, Object data) {
        return new ResultOJ(msg, FAIL_STATUS, data);
    }
}
