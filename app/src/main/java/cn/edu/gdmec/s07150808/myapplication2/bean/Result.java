package cn.edu.gdmec.s07150808.myapplication2.bean;

/**
 * Created by chen on 2017/2/27.
 * name Result 结果的处理类
 */
public class Result {


    private int code ;
    private String text;

    public Result(int resultCode ,String msg){
            this.code = resultCode;
           this.text = msg;
    }
    public Result (int resultCode){
        this.code = resultCode;
    }
    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }


}
