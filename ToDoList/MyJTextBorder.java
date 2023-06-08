package ToDoList;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.io.Serializable;

//圆角矩形文本边框
public class MyJTextBorder extends AbstractBorder implements Serializable {
    Color color;

    MyJTextBorder(Color color) {
        this.color = color;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawRoundRect(0, 0, c.getWidth()-4, c.getHeight()-4, 30, 30);//文本框水平、垂直、宽、高、弧度宽、高
    }
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 10, 4, 10);//设置文本里的文字位置，分别为字体往下偏、往右偏、往上偏、往左偏
    }
}
