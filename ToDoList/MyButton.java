package ToDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

//圆角矩形按钮

public class MyButton extends JButton implements Serializable, MouseListener {
    private boolean over = false;
    private Color pressedColor = null;
    private Color overColor = null;
    private Color normalColor = null;


    MyButton( Color normalcolor, Color pressedcolor, Color overcolor) {
        this.setBorder(null);
        this.setContentAreaFilled(false);//设置透明
        this.normalColor = normalcolor;
        this.pressedColor = pressedcolor;
        this.overColor = overcolor;
        this.setBackground(normalColor);
        this.addMouseListener(this);
    }
    MyButton(String s, Color normalcolor, Color pressedcolor, Color overcolor) {
        this.setText(s);
        this.setBorder(null);
        this.setContentAreaFilled(false);//设置透明
        this.normalColor = normalcolor;
        this.pressedColor = pressedcolor;
        this.overColor = overcolor;
        this.setBackground(normalColor);
        this.addMouseListener(this);
    }


    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());//填充颜色
        g2d.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 30, 30);
        super.paintComponent(g);
        super.paintBorder(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        setBackground(pressedColor);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (over) {
            setBackground(overColor);
        } else {
            setBackground(normalColor);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setBackground(overColor);
        over = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setBackground(normalColor);
        over = false;
    }
}
