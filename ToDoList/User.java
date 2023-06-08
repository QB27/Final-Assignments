package ToDoList;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String userName = null;//用户名
    private String passWord = null;//密码
    ArrayList<Events> eventsVDate = null;//用来保存用户的事项

    public User(String userName, String passWord) {//注册时构造
        this.userName = userName;
        this.passWord = passWord;
        this.eventsVDate = new ArrayList<>();//空的时间链表
    }

    public User(String userName, String passWord, ArrayList<Events> events) {//默认构造
        this.userName = userName;
        this.passWord = passWord;
        this.eventsVDate = events;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public ArrayList<Events> getEventsVDate() {
        return eventsVDate;
    }

    public void setEventsVDate(ArrayList<Events> events) {
        this.eventsVDate = events;
    }
}
