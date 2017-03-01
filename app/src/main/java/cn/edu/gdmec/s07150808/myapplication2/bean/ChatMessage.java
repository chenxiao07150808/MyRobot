package cn.edu.gdmec.s07150808.myapplication2.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chen on 2017/2/27.
 */
public class ChatMessage {
    /*消息的类型 判断布局的选择*/
    private Type type;
    /*消息的内容*/
    private String msg ;
    /*日期*/
    private Date date;
   /*日期的字符串格式*/
    private String dateStr;
    /*发送人*/
    private String name;

    public enum Type{
        INPUR,
        OUTPUT
    }
    public ChatMessage(){

    }
    /*对类的属性进行实例化*/
    public ChatMessage(Type type , String msg){
        this.type = type;
        this.msg  = msg;
        /*不清楚*/
        setDate(new Date());
    }
    /*getDateStr \ getDate实现get方法*/
    public String getDateStr()
    {
        return dateStr;
    }

    public Date getDate()
    {
        return date;
    }
    public void setDate(Date date)
    {
        this.date = date;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateStr = df.format(date);

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

}
