package hewson.logindemo2.vo;


/**
 * ClassName:    ServerResponse
 * Package:    springboot06mybatis.utils
 * Description:接口都用ServerResponse对象往前端返回数据
 * Datetime:    2020/3/9   21:41
 * Author:   hewson.chen@foxmail.com
 */

public class ServerResponse<T> {
    private int status;//状态 0：接口调用成功
    private T data;//泛型，当status=0，将返回的数据封装到data中
    private String msg;//给前端的提示信息
    private String success;

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    //构造方法
    public ServerResponse(){}

    //getter和setter
    public void setData(T data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
