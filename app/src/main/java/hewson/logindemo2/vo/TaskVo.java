package hewson.logindemo2.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TaskVo implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.TaskId
     *
     * @mbg.generated
     */
    private Integer taskid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.PublishUserId
     *
     * @mbg.generated
     */
    private String publishuserid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.FinisherId
     *
     * @mbg.generated
     */
    private String finisherid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_Title
     *
     * @mbg.generated
     */
    private String tTitle;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_Money
     *
     * @mbg.generated
     */
    private Double tMoney;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_Detail
     *
     * @mbg.generated
     */
    private String tDetail;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_Address
     *
     * @mbg.generated
     */
    private String tAddress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_jingdu
     *
     * @mbg.generated
     */
    private BigDecimal tJingdu;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_weidu
     *
     * @mbg.generated
     */
    private BigDecimal tWeidu;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_isNew
     *
     * @mbg.generated
     */
    private Boolean tIsnew;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_isDone
     *
     * @mbg.generated
     */
    private Boolean tIsdone;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_isDestroy
     *
     * @mbg.generated
     */
    private Boolean tIsdestroy;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_CreateTime
     *
     * @mbg.generated
     */
    private Date tCreatetime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_BeginTime
     *
     * @mbg.generated
     */
    private Date tBegintime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_task.T_FinishTime
     *
     * @mbg.generated
     */
    private Date tFinishtime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_task
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.TaskId
     *
     * @return the value of t_task.TaskId
     *
     * @mbg.generated
     */
    public Integer getTaskid() {
        return taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.TaskId
     *
     * @param taskid the value for t_task.TaskId
     *
     * @mbg.generated
     */
    public void setTaskid(Integer taskid) {
        this.taskid = taskid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.PublishUserId
     *
     * @return the value of t_task.PublishUserId
     *
     * @mbg.generated
     */
    public String getPublishuserid() {
        return publishuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.PublishUserId
     *
     * @param publishuserid the value for t_task.PublishUserId
     *
     * @mbg.generated
     */
    public void setPublishuserid(String publishuserid) {
        this.publishuserid = publishuserid == null ? null : publishuserid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.FinisherId
     *
     * @return the value of t_task.FinisherId
     *
     * @mbg.generated
     */
    public String getFinisherid() {
        return finisherid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.FinisherId
     *
     * @param finisherid the value for t_task.FinisherId
     *
     * @mbg.generated
     */
    public void setFinisherid(String finisherid) {
        this.finisherid = finisherid == null ? null : finisherid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_Title
     *
     * @return the value of t_task.T_Title
     *
     * @mbg.generated
     */
    public String gettTitle() {
        return tTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_Title
     *
     * @param tTitle the value for t_task.T_Title
     *
     * @mbg.generated
     */
    public void settTitle(String tTitle) {
        this.tTitle = tTitle == null ? null : tTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_Money
     *
     * @return the value of t_task.T_Money
     *
     * @mbg.generated
     */
    public Double gettMoney() {
        return tMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_Money
     *
     * @param tMoney the value for t_task.T_Money
     *
     * @mbg.generated
     */
    public void settMoney(Double tMoney) {
        this.tMoney = tMoney;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_Detail
     *
     * @return the value of t_task.T_Detail
     *
     * @mbg.generated
     */
    public String gettDetail() {
        return tDetail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_Detail
     *
     * @param tDetail the value for t_task.T_Detail
     *
     * @mbg.generated
     */
    public void settDetail(String tDetail) {
        this.tDetail = tDetail == null ? null : tDetail.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_Address
     *
     * @return the value of t_task.T_Address
     *
     * @mbg.generated
     */
    public String gettAddress() {
        return tAddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_Address
     *
     * @param tAddress the value for t_task.T_Address
     *
     * @mbg.generated
     */
    public void settAddress(String tAddress) {
        this.tAddress = tAddress == null ? null : tAddress.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_jingdu
     *
     * @return the value of t_task.T_jingdu
     *
     * @mbg.generated
     */
    public BigDecimal gettJingdu() {
        return tJingdu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_jingdu
     *
     * @param tJingdu the value for t_task.T_jingdu
     *
     * @mbg.generated
     */
    public void settJingdu(BigDecimal tJingdu) {
        this.tJingdu = tJingdu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_weidu
     *
     * @return the value of t_task.T_weidu
     *
     * @mbg.generated
     */
    public BigDecimal gettWeidu() {
        return tWeidu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_weidu
     *
     * @param tWeidu the value for t_task.T_weidu
     *
     * @mbg.generated
     */
    public void settWeidu(BigDecimal tWeidu) {
        this.tWeidu = tWeidu;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_isNew
     *
     * @return the value of t_task.T_isNew
     *
     * @mbg.generated
     */
    public Boolean gettIsnew() {
        return tIsnew;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_isNew
     *
     * @param tIsnew the value for t_task.T_isNew
     *
     * @mbg.generated
     */
    public void settIsnew(Boolean tIsnew) {
        this.tIsnew = tIsnew;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_isDone
     *
     * @return the value of t_task.T_isDone
     *
     * @mbg.generated
     */
    public Boolean gettIsdone() {
        return tIsdone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_isDone
     *
     * @param tIsdone the value for t_task.T_isDone
     *
     * @mbg.generated
     */
    public void settIsdone(Boolean tIsdone) {
        this.tIsdone = tIsdone;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_isDestroy
     *
     * @return the value of t_task.T_isDestroy
     *
     * @mbg.generated
     */
    public Boolean gettIsdestroy() {
        return tIsdestroy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_isDestroy
     *
     * @param tIsdestroy the value for t_task.T_isDestroy
     *
     * @mbg.generated
     */
    public void settIsdestroy(Boolean tIsdestroy) {
        this.tIsdestroy = tIsdestroy;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_CreateTime
     *
     * @return the value of t_task.T_CreateTime
     *
     * @mbg.generated
     */
    public Date gettCreatetime() {
        return tCreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_CreateTime
     *
     * @param tCreatetime the value for t_task.T_CreateTime
     *
     * @mbg.generated
     */
    public void settCreatetime(Date tCreatetime) {
        this.tCreatetime = tCreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_BeginTime
     *
     * @return the value of t_task.T_BeginTime
     *
     * @mbg.generated
     */
    public Date gettBegintime() {
        return tBegintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_BeginTime
     *
     * @param tBegintime the value for t_task.T_BeginTime
     *
     * @mbg.generated
     */
    public void settBegintime(Date tBegintime) {
        this.tBegintime = tBegintime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_task.T_FinishTime
     *
     * @return the value of t_task.T_FinishTime
     *
     * @mbg.generated
     */
    public Date gettFinishtime() {
        return tFinishtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_task.T_FinishTime
     *
     * @param tFinishtime the value for t_task.T_FinishTime
     *
     * @mbg.generated
     */
    public void settFinishtime(Date tFinishtime) {
        this.tFinishtime = tFinishtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_task
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskid=").append(taskid);
        sb.append(", publishuserid=").append(publishuserid);
        sb.append(", finisherid=").append(finisherid);
        sb.append(", tTitle=").append(tTitle);
        sb.append(", tMoney=").append(tMoney);
        sb.append(", tDetail=").append(tDetail);
        sb.append(", tAddress=").append(tAddress);
        sb.append(", tJingdu=").append(tJingdu);
        sb.append(", tWeidu=").append(tWeidu);
        sb.append(", tIsnew=").append(tIsnew);
        sb.append(", tIsdone=").append(tIsdone);
        sb.append(", tIsdestroy=").append(tIsdestroy);
        sb.append(", tCreatetime=").append(tCreatetime);
        sb.append(", tBegintime=").append(tBegintime);
        sb.append(", tFinishtime=").append(tFinishtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}