package ToDoList;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.Date;

public class Events implements Serializable, Runnable {
    final String HEAD_PATH = "ToDoImage\\";
    private ImageIcon eventFlag = null;
    //标题
    private String eventTitle = null;
    //内容
    private String eventContent = null;
    //是否完成
    private boolean isStart = false;//是否开始
    private boolean isDone = false;//是否已经做完
    private boolean isDelete = false;
    private boolean isActivited = false;//是否被激活 激活则计时 否则不计时
    private boolean isAlive = false;
    private Date startTime = null;
    private Date completeTime = null;//指定的完成时间


    Events(String title, String content, Date sTime, Date eTime) {

        try {
            initImage();
        } catch (CantfindImageException e) {
            System.out.println(e.getMessage());
        }
        eventTitle = title;
        eventContent = content;
        startTime = sTime;
        completeTime = eTime;
        isActivited = true;
    }

    public void run() {
        isAlive = true;
        Date currentTime = null;
        long toS = -1;//与开始时间的差值
        long toE = -1;//与结束时间的差值
        System.out.println("事件" + getEventTitle() + "激活!");
        while (true) {
            synchronized (this.getClass()) {
                if (isActivited) {
                    currentTime = new Date();//获取当前时间
                    toS = startTime.getTime() - currentTime.getTime();//小于零说明事件要开始做了
                    toE = completeTime.getTime() - currentTime.getTime();//小于零说明事件完成的时间已经到了
                    //System.out.println(startTime + " " + completeTime);
                    if (toS <= 0 && isStart == false) {
                        isStart = true;
                        JOptionPane.showConfirmDialog(null, LoginMenu.defaultTab(this.getEventTitle() + "要开始做啦!!!"), "TIPS", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, eventFlag);
                    }
                    if (toE <= 0 && isDone == false) {
                        isDone = true;
                        JOptionPane.showConfirmDialog(null, LoginMenu.defaultTab(this.getEventTitle() + "时间到啦!!!"), "TIPS", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, eventFlag);
                    }
                    if (isDone && isStart) {
                        isActivited = false;
                        break;
                    }
                } else {
                    System.out.println("事件" + getEventTitle() + "休眠!");//主动
                    break;
                }
            }
        }
        System.out.println("事件" + getEventTitle() + "结束!");//被动
        isAlive = false;
    }

    private void initImage() throws CantfindImageException {
        eventFlag = new ImageIcon(HEAD_PATH + "eventstart.png");
        if (eventFlag.getDescription() == null) {
            throw new CantfindImageException();
        }
        Image temp = eventFlag.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        eventFlag = new ImageIcon(temp);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isActivited() {
        return isActivited;
    }

    public void setActivited(boolean activited) {
        isActivited = activited;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }
}
