package ToDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainMenu extends JFrame implements WindowListener, ColorManager {
    public final String LOCK = "锁对象";
    private final String TODAY = "2023年6月8日 ";
    final int FRAME_WIDTN = 1440;//窗口宽
    final int FRAME_HEIGHT = 960;//窗口高
    final int USERINFO_HEIGHT = 80;
    final String HEAD_PATH = "ToDoImage//";
    final String USER_FILE = "userDate.ser";
    ExecutorService pool = null;

    //数据
    //when saving date,we only need to save the userVDate.
    private ArrayList<User> userVDate = null;//注销功能专用   for deregistration
    private ArrayList<Events> eventsVDate = null;//事件链表 便于管理当前页面事件  point to user's eventsvdate
    private ArrayList<EventsPanel> eventsPanelsList = null;//事件容器链表 便于管理容器 for eventPanel
    private User user = null;//point to the user in uservdate


    //Panel
    MainP mainP = null;//总体面板
    //Label
    //ImageIcon
    ImageIcon question = null;
    ImageIcon LOGO = null;
    ImageIcon warning = null;

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {//退出提示
        int result = JOptionPane.showConfirmDialog(null, "确认要退出吗?", "ToDoList", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, question);
        System.out.println(result);
        if (result == 0) {
            saveDate();

            myDisposed();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


    public class MainP extends JPanel implements Serializable {//大容器
        UserInfoP userInfoP = null;//用户信息总面板
        UserActionP useractionP = null;//用户操作总面板

        MainP() {
            initMainP();
            initComp();
        }

        private void initMainP() {
            this.setLayout(new BorderLayout());
            this.setBackground(ColorManager.暗蓝色);//便于管理
            this.setPreferredSize(new Dimension(FRAME_WIDTN, FRAME_HEIGHT));
        }

        private void initComp() {
            userInfoP = new UserInfoP();
            useractionP = new UserActionP();

            this.add(userInfoP, BorderLayout.NORTH);
            this.add(useractionP, BorderLayout.SOUTH);
        }

        public void closeThread() {
            useractionP.closeThread();
        }
    }

    public class UserInfoP extends JPanel {
        //imageicon
        ImageIcon userHeadShot = null;

        //label
        JLabel userHeadShotL = null;
        JLabel userName = null;

        UserInfoP() {
            try {
                initImage();
            } catch (CantfindImageException e) {
                System.out.println(e.getMessage());
            }
            initPanel();
            initComp();
        }

        private void initPanel() {
            this.setLayout(null);
            this.setBackground(ColorManager.海滩蓝);
            this.setPreferredSize(new Dimension(FRAME_WIDTN, FRAME_HEIGHT - 880));
        }

        private void initComp() {
            userHeadShotL = new JLabel();
            userHeadShotL.setIcon(userHeadShot);
            userHeadShotL.setBounds(FRAME_WIDTN - 200, 20, 50, 50);

            userName = new JLabel(user.getUserName());
            userName.setBounds(FRAME_WIDTN - 130, 20, 100, 50);
            userName.setFont(new Font("Yu Gothic UI Semilight", Font.ITALIC, 20));
            userName.setForeground(ColorManager.纯黑色);
            userName.setHorizontalAlignment(JLabel.LEFT);
            userName.setVerticalAlignment(JLabel.CENTER);

            this.add(userName);
            this.add(userHeadShotL);
        }

        //图片初始化
        private void initImage() throws CantfindImageException {
            userHeadShot = new ImageIcon(HEAD_PATH + "userheadshot.png");
            if (userHeadShot.getDescription() == null) {
                throw new CantfindImageException();
            }

            Image temp = userHeadShot.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            userHeadShot = new ImageIcon(temp);
        }
    }

    public class UserActionP extends JPanel implements Serializable {
        //pane
        JTabbedPane menuCon = null;//选项卡网格
        //imageicon
        ImageIcon mainI = null;
        ImageIcon eventI = null;
        ImageIcon settingI = null;
        //Panel
        MenuP menuP = null;//主菜单
        EventP eventP = null;//管理代办事项菜单
        SettingsP settingsP = null;//设置面板

        UserActionP() {
            try {
                initImage();
            } catch (CantfindImageException e) {
                System.out.println(e.getMessage());
            }

            initPanel();
            initComp();
        }

        private void initPanel() {
            this.setPreferredSize(new Dimension(FRAME_WIDTN, FRAME_HEIGHT - USERINFO_HEIGHT));
            this.setBackground(ColorManager.海滩灰);
            this.setLayout(null);
        }

        private void initComp() {
            menuCon = new JTabbedPane(JTabbedPane.LEFT);//选项卡网格
            menuCon.setBounds(0, 0, FRAME_WIDTN, FRAME_HEIGHT - USERINFO_HEIGHT);
            menuCon.setBackground(ColorManager.海滩黄);
//            menuP = new MenuP();
//            eventP = new EventP();

            settingsP = new SettingsP();
            eventP = new EventP();
            menuP = new MenuP();

            menuCon.add(menuP, 0);
            menuCon.add(eventP, 1);
            menuCon.add(settingsP, 2);

            menuCon.setIconAt(0, mainI);
            menuCon.setIconAt(1, eventI);
            menuCon.setIconAt(2, settingI);

            this.add(menuCon);
        }

        public void closeThread() {
            eventP.closeThread();
            menuP.closeThread();
        }

        private void initImage() throws CantfindImageException {
            mainI = new ImageIcon(HEAD_PATH + "main.png");
            eventI = new ImageIcon(HEAD_PATH + "event.png");
            settingI = new ImageIcon(HEAD_PATH + "setting.png");
            if (mainI.getDescription() == null || eventI.getDescription() == null || settingI.getDescription() == null) {
                throw new CantfindImageException();
            }

            Image temp = mainI.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            mainI = new ImageIcon(temp);
            temp = eventI.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            eventI = new ImageIcon(temp);
            temp = settingI.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            settingI = new ImageIcon(temp);
        }
    }

    //事件收容并展示的Label
    //when we creat an eventspanel,we will creat an event before that and contain it in our eventsarraylist and contain this panel in our panelarraylist
    // so that the index of the panel equals the index of the event
    public class EventsPanel extends JPanel implements MouseListener {
        private final Color RESTCOLOR = new Color(91, 91, 91, 255);//不激活时的颜色
        //ImageIcon
        private ImageIcon activatedI = null;//激活时的图标
        private ImageIcon restI = null;//不激活图标
        private ImageIcon deleteI = null;//删除图标
        //Label
        private JLabel eventTitle = null;//展示标题
        private JLabel eventContent = null; //展示内容
        //color
        private Color normalColor = null;//默认颜色
        private Color pressedColor = null;//点击颜色
        //功能相关
        private JCheckBox isActivatedB = null;//是否被激活按钮
        private Events containE = null;
        private MyButton deleteB = null;//删除事件

        private JLabel contentS = null;
        //property属性
        private boolean isAlive;//当前容器状态 便于容器管理

        //if isAlive equals false,we need to delete it in the panelsarraylist and delete its contained event in eventsarraylist
        //构造
        EventsPanel(Events temp) {
            normalColor = ColorManager.浅蓝色;
            pressedColor = ColorManager.海滩蓝;
            containE = temp;
            isAlive = true;
            try {
                initImage();
            } catch (CantfindImageException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            initPanel();
            initComp();
            if (isActivatedB.isSelected()) {
                this.setBackground(normalColor);
            } else {
                this.setBackground(RESTCOLOR);
            }
        }

        //第二构造
//        EventsPanel(Events temp, JLabel showContent) {
//            contentS = showContent;
//            normalColor = myColor.浅蓝色;
//            pressedColor = myColor.海滩蓝;
//            containE = temp;
//            isAlive = true;
//            try {
//                initImage();
//            } catch (CantfindImageException e) {
//                System.out.println(e.getMessage());
//                System.exit(0);
//            }
//            initPanel();
//            initCompForMain();
//            if (isActivatedB.isSelected()) {
//                this.setBackground(normalColor);
//            } else {
//                this.setBackground(RESTCOLOR);
//            }
//        }


        //初始化容器
        private void initPanel() {
            this.setLayout(null);
            this.setPreferredSize(new Dimension(340, 150));//设置长宽
            this.setOpaque(false);
            this.addMouseListener(this);
        }

        //初始化组件1
        private void initComp() {
            //激活按钮
            isActivatedB = new JCheckBox();
            isActivatedB.setIcon(restI);
            isActivatedB.setSelectedIcon(activatedI);
            isActivatedB.setBounds(280, 35, 40, 40);
            //isActivatedB.setOpaque(false);
            isActivatedB.setContentAreaFilled(false);
            isActivatedB.addMouseListener(this);
            isActivatedB.setSelected(containE.isActivited());

            //删除
            deleteB = new MyButton(ColorManager.浅红色, ColorManager.深红色, ColorManager.中间红);
            deleteB.setIcon(deleteI);
            deleteB.setBounds(280, 90, 40, 40);
            deleteB.setContentAreaFilled(false);
            deleteB.addMouseListener(this);
            deleteB.setFocusable(false);

            //标题容器
            eventTitle = new JLabel(LoginMenu.defaultTab(containE.getEventTitle()));
            eventTitle.setBounds(10, 10, 200, 40);
            eventTitle.setFont(new Font("幼圆", Font.ITALIC, 30));

            //内容容器
            eventContent = new JLabel(LoginMenu.defaultTab(containE.getEventContent()));
            eventContent.setBounds(10, 60, 200, 70);
            eventContent.setFont(new Font("幼圆", Font.ITALIC, 30));

            this.add(deleteB);
            this.add(eventTitle);
            this.add(eventContent);
            this.add(isActivatedB);
        }

        //初始化组件2
//        private void initCompForMain() {
//            //激活按钮
//            isActivatedB = new JCheckBox();
//            isActivatedB.setIcon(restI);
//            isActivatedB.setSelectedIcon(activatedI);
//            isActivatedB.setBounds(280, 35, 40, 40);
//            //isActivatedB.setOpaque(false);
//            isActivatedB.setContentAreaFilled(false);
//            isActivatedB.addMouseListener(this);
//            isActivatedB.setSelected(containE.isActivited());
//
//            //标题容器
//            eventTitle = new JLabel(LoginMenu.defaultTab(containE.getEventTitle()));
//            eventTitle.setBounds(10, 10, 200, 40);
//            eventTitle.setFont(new Font("幼圆", Font.ITALIC, 30));
//
//            this.add(eventTitle);
//            this.add(isActivatedB);
//        }

        private void initImage() throws CantfindImageException {
            activatedI = new ImageIcon(HEAD_PATH + "filledcircle.png");
            restI = new ImageIcon(HEAD_PATH + "hollowcircle.png");
            deleteI = new ImageIcon(HEAD_PATH + "delete.png");

            if (activatedI.getDescription() == null || restI.getDescription() == null || deleteI.getDescription() == null) {
                throw new CantfindImageException();
            }
            Image temp = activatedI.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            activatedI = new ImageIcon(temp);

            temp = restI.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            restI = new ImageIcon(temp);

            temp = deleteI.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            deleteI = new ImageIcon(temp);
        }

        //自定义样式
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getBackground());//填充颜色
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            super.paintComponent(g);
            super.paintBorder(g);
        }

        public String getTitle() {
            return eventTitle.getText();
        }

        public boolean isAlive() {
            return isAlive;
        }

        public void setAlive(boolean alive) {
            isAlive = alive;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == isActivatedB) {
                if (!isActivatedB.isSelected()) {
                    containE.setActivited(false);
                    this.setBackground(RESTCOLOR);
                    System.out.println("事件休眠!");
                } else {
                    containE.setActivited(true);
                    this.setBackground(normalColor);
                    System.out.println("事件激活!");
                }
            } else if (e.getSource() == deleteB) {
                //删除自身
                int result = JOptionPane.showConfirmDialog(null, LoginMenu.defaultTab("确定要删除该事件提醒吗?删除后不可找回"), "ToDoList", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, warning);
                if (result == 0) {
                    this.setVisible(false);
                    isAlive = false;
                }
            } else if (e.getSource() == this) {
                //展示事件信息
                contentS.setText(LoginMenu.defaultTab(containE.getEventContent()));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getSource() == this) {
                if (isActivatedB.isSelected()) {
                    setBackground(pressedColor);
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getSource() == this) {
                if (isActivatedB.isSelected()) {
                    setBackground(normalColor);
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    //操作面板

    //主页面
    public class MenuP extends JPanel implements Serializable {
        private JLabel finishedEvents = null;
        private JLabel allEvents = null;
        private JLabel eventsContent = null;

        private JScrollPane scrollPane = null;
        private JPanel eventsP = null;
        private ShowInt showThread = new ShowInt();
        private ShowEvent showEvent = new ShowEvent();
        private ArrayList<JPanel> panelList = new ArrayList<>();

        MenuP() {
            initPanel();
            initComp();

            initTitle();
            showThread.start();
            showEvent.start();
        }

        private void initPanel() {
            this.setBackground(ColorManager.枫林白);
            this.setLayout(null);
        }

        private void initComp() {
            allEvents = new JLabel();
            allEvents.setFont(new Font("幼圆", Font.PLAIN, 30));
            allEvents.setBounds(150, 100, 200, 50);
            allEvents.setOpaque(false);

            finishedEvents = new JLabel();
            finishedEvents.setFont(new Font("幼圆", Font.PLAIN, 30));
            finishedEvents.setBounds(350, 100, 200, 50);
            finishedEvents.setOpaque(false);

            eventsContent = new JLabel();
            eventsContent.setBounds(700, 300, 500, 500);
            eventsContent.setFont(new Font("幼圆", Font.PLAIN, 30));
            eventsContent.setBorder(new MyJTextBorder(ColorManager.海滩蓝));
            eventsContent.setVerticalAlignment(JLabel.TOP);
            eventsContent.setHorizontalAlignment(JLabel.LEFT);
            eventsContent.setOpaque(false);

            eventsP = new JPanel();
            eventsP.setBorder(new MyJTextBorder(ColorManager.纯黑色));
            eventsP.setBackground(ColorManager.枫林白);
            eventsP.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            eventsP.setPreferredSize(new Dimension(380, 1200));

            scrollPane = new JScrollPane(eventsP);
            scrollPane.setBounds(140, 200, 380, 600);
            scrollPane.setBorder(null);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setOpaque(false);

            this.add(scrollPane);
            this.add(allEvents);
            this.add(eventsContent);
            this.add(finishedEvents);
        }

        private void initTitle() {
            int dateL = eventsVDate.size();
            for (int i = 0; i < dateL; i++) {
                if (eventsVDate.get(i).isActivited()) {
                    MenuPanel temp = new MenuPanel(eventsVDate.get(i), eventsContent);
                    panelList.add(temp);
                    eventsP.add(temp);
                }
            }
        }

        public class MenuPanel extends JPanel implements MouseListener {
            private Color normalColor = null;
            private Color pressedColor = null;

            private JLabel contentS = null;
            private JLabel eventTitle = null;
            private String title = null;
            private String eventContent = null;

            MenuPanel(Events temp, JLabel content) {
                contentS = content;
                eventContent = temp.getEventContent();
                title = temp.getEventTitle();
                normalColor = ColorManager.浅蓝色;
                pressedColor = ColorManager.海滩蓝;
                initPanel();
                initComp();
            }

            private void initPanel() {
                this.setBackground(normalColor);
                this.setPreferredSize(new Dimension(340, 150));
                this.addMouseListener(this);
                this.setLayout(null);
                this.setOpaque(false);
            }

            private void initComp() {
                eventTitle = new JLabel(LoginMenu.defaultTab(title));
                eventTitle.setBounds(20, 3000, 200, 100);
                eventTitle.setFont(new Font("幼圆", Font.PLAIN, 40));


                this.add(eventTitle);
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());//填充颜色
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
                super.paintBorder(g);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == this) {
                    contentS.setText(eventContent);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(normalColor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


        }

        //        private void initEventP() {
//            for (int i = 0; i < eventsVDate.size(); i++) {
//                if (eventsVDate.get(i).isActivited()) {
//                    eventsP.add(new EventsPanel(eventsVDate.get(i), eventsContent));
//                }
//            }
//        }
//
        public class ShowEvent extends Thread {
            public void run() {
                int pL = 0;
                int dateL = 0;
                while (true) {
                    synchronized (LOCK) {
                        if (isInterrupted()) {
                            System.out.println("主菜单面板线程已关闭!");
                            break;
                        }
                        dateL = eventsPanelsList.size();
                        panelList.clear();
                        for (int i = 0; i < dateL; i++) {
                            if (eventsPanelsList.get(i).isActivatedB.isSelected()) {
                                {
                                    panelList.add(new MenuPanel(eventsVDate.get(i), eventsContent));
                                }
                            }
                        }
                        pL = panelList.size();
                        eventsP.removeAll();
                        eventsP.setVisible(false);
                        for (int i = 0; i < pL; i++) {
                            eventsP.add(panelList.get(i));
                        }
                        eventsP.setVisible(true);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            System.out.println(e.getMessage());
                            break;
                        }
                    }
                }
            }
        }

        public class ShowInt extends Thread {
            public void run() {
                while (true) {
                    synchronized (LOCK) {
                        if (isInterrupted()) {
                            System.out.println("数字线程已关闭!");
                            break;
                        }
                        allEvents.setText("总事件数: " + eventsPanelsList.size());
                        //待办总数
                        int finishTemp = 0;
                        for (int i = 0; i < eventsPanelsList.size(); i++) {
                            if (eventsPanelsList.get(i).isActivatedB.isSelected()) {
                                finishTemp++;
                            }
                        }
                        finishedEvents.setText("待办事件数: " + finishTemp);
                    }
                }
            }
        }

        public void closeThread() {
            showThread.interrupt();
            showEvent.interrupt();
        }

    }

    //事件管理页面
    public class EventP extends JPanel implements Serializable, ActionListener {
        //Panel
        private JPanel upL = null;//上方功能区
        private JPanel downL = null;//下方功能区
        //要改成有滚动条的
        private JScrollPane scrollPane = null;//滚动窗格
        //Text
        JTextField searchT = null;//查找
        //button
        MyButton searchB = null;//查找按钮
        MyButton addB = null;//添加事项
        //imageicon
        ImageIcon searchI = null;//查找
        ImageIcon addI = null;//添加
        DeleteThread deleteThread = new DeleteThread();
        SearchThread searchThread = new SearchThread();
        EventActivatedThread eventActivatedThread = new EventActivatedThread();

        //添加事件弹窗
        public class AddDialog extends JDialog implements ActionListener {
            //数据
            private String[] hours = null;
            private String[] mins = null;
            //文字
            JLabel title = null;
            JLabel content = null;
            JLabel start = null;
            JLabel end = null;
            JLabel Shour = null;
            JLabel Smin = null;
            JLabel Ehour = null;
            JLabel Emin = null;

            //输入
            private JTextField eventTitle = null;
            private JTextArea eventContent = null;
            //开始时间
            private JComboBox hourS = null;
            private JComboBox minS = null;
            //结束时间
            private JComboBox hourE = null;
            private JComboBox minE = null;
            private JScrollPane scrollPane = null;

            //按钮
            MyButton Yes = null;
            MyButton No = null;

            //created event
            private Events temp = null;

            //界面初始化
            AddDialog() {
                initFinalDate();
                initPanel();
                initComp();

                this.setVisible(true);
            }

            //初始化面板
            private void initPanel() {
                this.setLayout(null);
                this.setBackground(ColorManager.雪山灰);
                this.setSize(400, 600);
                this.setIconImage(LOGO.getImage());
                this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                this.setAlwaysOnTop(true);
                this.setLocationRelativeTo(null);     //set defualt location to the center of screen
            }

            //初始化组件
            private void initComp() {
                Yes = new MyButton("创建", ColorManager.枫林白, ColorManager.深灰色, ColorManager.雪山橙);
                Yes.setBounds(80, 520, 80, 30);
                Yes.setFont(new Font("幼圆", Font.PLAIN, 15));
                Yes.addActionListener(this);

                No = new MyButton("取消", ColorManager.枫林白, ColorManager.深灰色, ColorManager.雪山橙);
                No.setBounds(210, 520, 80, 30);
                No.setFont(new Font("幼圆", Font.PLAIN, 15));
                No.addActionListener(this);

                //输入标题
                eventTitle = new JTextField();
                eventTitle.setBorder(new MyJTextBorder(ColorManager.纯黑色));
                eventTitle.setBounds(100, 20, 200, 40);
                eventTitle.setFont(new Font("幼圆", Font.PLAIN, 20));
                eventTitle.setOpaque(false);
//标题文字
                title = new JLabel("标题:");
                title.setFont(new Font("幼圆", Font.PLAIN, 15));
                title.setBounds(20, 20, 70, 40);
                title.setHorizontalAlignment(JLabel.LEFT);

                //输入内容
                eventContent = new JTextArea();
                eventContent.setFont(new Font("幼圆", Font.PLAIN, 20));
                eventContent.setPreferredSize(new Dimension(250, 1000));
                eventContent.setBorder(new MyJTextBorder(ColorManager.纯黑色));
                eventContent.setLineWrap(true);
                eventContent.setWrapStyleWord(true);
                eventContent.setOpaque(false);
//内容文字
                content = new JLabel("待办详情:");
                content.setFont(new Font("幼圆", Font.PLAIN, 15));
                content.setBounds(20, 70, 70, 40);
                content.setHorizontalAlignment(JLabel.LEFT);

                //开始时间
                //小时选项框
                hourS = new JComboBox(hours);
                hourS.setBounds(140, 400, 60, 20);
                hourS.setEditable(true);
//文字
                Shour = new JLabel("时");
                Shour.setFont(new Font("幼圆", Font.PLAIN, 15));
                Shour.setBounds(140, 420, 30, 30);
                Shour.setHorizontalAlignment(JLabel.LEFT);
//分钟选项框
                minS = new JComboBox(mins);
                minS.setBounds(300, 400, 60, 20);
                minS.setEditable(true);
//文字
                Smin = new JLabel("分");
                Smin.setFont(new Font("幼圆", Font.PLAIN, 15));
                Smin.setBounds(300, 420, 30, 30);
                Smin.setHorizontalAlignment(JLabel.LEFT);
//文字开始时间
                start = new JLabel("开始时间:");
                start.setFont(new Font("幼圆", Font.PLAIN, 15));
                start.setBounds(20, 400, 100, 40);
                start.setHorizontalAlignment(JLabel.LEFT);
                //结束时间
                //小时选项框
                hourE = new JComboBox(hours);
                hourE.setBounds(140, 460, 60, 20);
                hourE.setEditable(true);
//文字
                Ehour = new JLabel("时");
                Ehour.setFont(new Font("幼圆", Font.PLAIN, 15));
                Ehour.setBounds(140, 480, 30, 30);
                Ehour.setHorizontalAlignment(JLabel.LEFT);

//分钟选项框
                minE = new JComboBox(mins);
                minE.setBounds(300, 460, 60, 20);
                minE.setEditable(true);
//文字
                Emin = new JLabel("分");
                Emin.setFont(new Font("幼圆", Font.PLAIN, 15));
                Emin.setBounds(300, 480, 40, 40);
                Emin.setHorizontalAlignment(JLabel.LEFT);
//文字结束时间
                end = new JLabel("结束时间:");
                end.setFont(new Font("幼圆", Font.PLAIN, 15));
                end.setBounds(20, 460, 100, 40);
                end.setHorizontalAlignment(JLabel.LEFT);

                scrollPane = new JScrollPane(eventContent);
//                scrollPane.setBorder(new MyJTextBorder(myColor.纯黑色));
                scrollPane.setBounds(100, 70, 250, 300);
                scrollPane.setBorder(null);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                scrollPane.setOpaque(false);

                this.add(Yes);
                this.add(No);
                this.add(title);
                this.add(content);
                this.add(start);
                this.add(end);
                this.add(Shour);
                this.add(Smin);
                this.add(Ehour);
                this.add(Emin);
                this.add(eventTitle);
                this.add(scrollPane);
                this.add(hourS);
                this.add(minS);
                this.add(hourE);
                this.add(minE);
            }

            //初始化数据
            private void initFinalDate() {
                hours = new String[24];
                mins = new String[60];
                int i;
                for (i = 0; i < 24; i++) {
                    hours[i] = String.valueOf(i);
                }
                for (i = 0; i < 60; i++) {
                    mins[i] = String.valueOf(i);
                }
            }

            public Events getTemp() {
                return temp;
            }

            public void setTemp(Events temp) {
                this.temp = temp;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == Yes) {
                    //创建方法
                    Date temps = new Date();
                    Date tempe = new Date();
                    SimpleDateFormat tempsimple = new SimpleDateFormat("y年M月d日 H时m分s秒");
                    //creat start time
                    String starttime = TODAY + hourS.getSelectedItem() + "时" + minS.getSelectedItem() + "分" + "0秒";
                    //creat end time
                    String endtime = TODAY + hourE.getSelectedItem() + "时" + minE.getSelectedItem() + "分" + "0秒";
                    //translate into date
                    try {
                        temps = tempsimple.parse(starttime);
                        System.out.println("开始初始化完毕!");
                        tempe = tempsimple.parse(endtime);
                        System.out.println("结束初化完毕");
                    } catch (ParseException ex) {
                        System.out.println(ex);
                        System.exit(0);
                    }
                    //creat event
                    temp = new Events(eventTitle.getText(), eventContent.getText(), temps, tempe);
                    System.out.println("temp创建完成!");
                    //add to arraylist
                    eventsVDate.add(temp);
                    System.out.println("事件已添加!");
                    //creat eventpanel
                    EventsPanel tempp = new EventsPanel(temp);
                    System.out.println("面板创建完成!");
                    //add to arraylist
                    downL.setVisible(false);
                    downL.add(tempp);
                    downL.setVisible(true);
                    System.out.println("面板已显示!");
                    eventsPanelsList.add(tempp);
                    System.out.println("面板已添加!");
                    this.dispose();
                } else if (e.getSource() == No) {
                    this.dispose();
                }
            }
        }

        //Delete删除功能 1
        public class DeleteThread extends Thread {
            //锁
            public void run() {
                int dateL = 0;
                while (true) {
                    synchronized (LOCK) {
                        if (this.isInterrupted()) {
                            System.out.println("DeleteThread线程关闭成功!");
                            break;
                        }
                        dateL = eventsPanelsList.size();
                        for (int i = 0; i < dateL; i++) {
                            if (!eventsPanelsList.get(i).isAlive()) {
                                downL.remove(eventsPanelsList.get(i));
                                eventsPanelsList.remove(i);
                                eventsVDate.remove(i);
                                System.out.println("删除成功!");
                            }
                        }
                    }
                }
            }

        }

        //Search查找功能 2
        public class SearchThread extends Thread {
            //锁
            @Override
            public void run() {
                int dateL = 0;
                while (true) {
                    synchronized (LOCK) {
                        dateL = eventsPanelsList.size();
                        //检测中断标志
                        if (this.isInterrupted()) {
                            System.out.println("Search线程关闭成功!");
                            break;
                        }
                        //只有搜索内容不为空时才会实现
                        if (!searchT.getText().equals("")) {
                            for (int i = 0; i < dateL; i++) {
                                if (eventsVDate.get(i).getEventTitle().indexOf(searchT.getText()) < 0) {
                                    eventsPanelsList.get(i).setVisible(false);
                                } else {
                                    eventsPanelsList.get(i).setVisible(true);
                                }
                            }
                        } else {
                            for (int i = 0; i < dateL; i++) {
                                if (!eventsPanelsList.get(i).isVisible()) {
                                    eventsPanelsList.get(i).setVisible(true);
                                }
                            }
                        }
                    }
                }

            }
        }

        //EventActivated事件唤醒 3
        public class EventActivatedThread extends Thread {
            //锁
            @Override
            public void run() {
                int dateL = 0;
                while (true) {
                    synchronized (LOCK) {
                        if (this.isInterrupted()) {
                            System.out.println("Event线程关闭成功!");
                            break;
                        }
                        dateL = eventsVDate.size();
                        //System.out.println(dateL);
                        for (int i = 0; i < dateL; i++) {
                            if (!eventsVDate.get(i).isAlive()) {
                                if (eventsVDate.get(i).isActivited()) {
                                    System.out.println(eventsVDate.get(i).getEventTitle() + "...事件开始执行!" + dateL);
                                    pool.submit(eventsVDate.get(i));
                                    try {
                                        Thread.sleep(20);
                                    } catch (InterruptedException e) {
                                        System.out.println(e.getMessage());
                                        break;
                                    }
                                }
                                //为防止多次执行
                            }
                        }
                    }

                }
            }

        }

        //初始化
        EventP() {
            //new AddDialog();
            try {
                initI();
            } catch (CantfindImageException e) {
                System.out.println(e.getMessage());
                System.exit(0);
            }
            //initPanels

            initP();
            initC();
            initPanels();

            eventActivatedThread.start();
            deleteThread.start();
            searchThread.start();
        }

        //初始化panel
        private void initP() {
            this.setBackground(ColorManager.枫林白);
            this.setLayout(null);
        }

        //初始化组件
        private void initC() {

            upL = new JPanel();
            upL.setBounds(0, 0, 1360, 60);
            upL.setBackground(ColorManager.雪山灰);
            upL.setLayout(null);
            System.out.println(this.getWidth() + " " + this.getHeight());

            downL = new JPanel();
            downL.setPreferredSize(new Dimension(1360, 3000));//可以设计动态扩容
            downL.setBackground(ColorManager.枫林白);
            downL.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));//设置事件容器放置方式

            scrollPane = new JScrollPane(downL);
            scrollPane.setBounds(0, 60, 1360, 870);
            scrollPane.setBackground(ColorManager.枫林白);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//查找
            searchB = new MyButton(ColorManager.浅灰色, ColorManager.雪山灰, ColorManager.浅黄色);
            searchB.setBounds(10, 10, 40, 40);
            searchB.setIcon(searchI);

            searchT = new JTextField();
            searchT.setBorder(new MyJTextBorder(ColorManager.纯黑色));
            searchT.setBounds(60, 10, 200, 40);
            searchT.setOpaque(false);
            searchT.setToolTipText("查找事件标题");
//添加
            addB = new MyButton(ColorManager.浅灰色, ColorManager.雪山灰, ColorManager.浅黄色);
            addB.setBounds(upL.getWidth() - 60, 10, 40, 40);
            addB.setIcon(addI);
            addB.setToolTipText("添加事件");
            addB.addActionListener(this);

            // downL.add(new EventsPanel(new Events("测试", "测试内容", new Date(), new Date()), new Color(225, 170, 170), new Color(241, 110, 110)));
            upL.add(addB);
            upL.add(searchB);
            upL.add(searchT);
//            upL.setVisible(false);
            this.add(scrollPane);
            this.add(upL);
        }

        public void closeThread() {
            deleteThread.interrupt();
            eventActivatedThread.interrupt();
            searchThread.interrupt();
        }

        //eventspanel init
        private void initPanels() {
            int dateL = eventsVDate.size();
            ArrayList<EventsPanel> tempList = new ArrayList<>();//容器链表
            EventsPanel temp = null;
            for (int i = 0; i < dateL; i++) {
                temp = new EventsPanel(eventsVDate.get(i));
                tempList.add(temp);
                downL.add(temp);
            }
            temp = null;
            eventsPanelsList = tempList;
            tempList = null;
            System.out.println("面板初始化完毕!");
        }

        //添加事件功能
        private void addEvents() {
            //creat an adddialog
            new AddDialog();
        }

        private void initI() throws CantfindImageException {
            searchI = new ImageIcon(HEAD_PATH + "search.png");
            addI = new ImageIcon(HEAD_PATH + "add.png");
            if (searchI.getDescription() == null || addI.getDescription() == null) {
                throw new CantfindImageException();
            }
            Image temp = searchI.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            searchI = new ImageIcon(temp);

            temp = addI.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            addI = new ImageIcon(temp);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addB) {
                //添加事件
                addEvents();
                // System.out.println("add");
            }
//            } else if (e.getSource() == searchB) {
//                //搜索显示功能
//
//            }
        }
    }

    //设置页面
    public class SettingsP extends JPanel implements ActionListener, Serializable {
        //Label
        private JLabel logoL = null;
        private JLabel appDescription = null;
        //TextAera
        JTextArea userFeedBack = null;
        //Button
        MyButton exitB = null;//退出
        MyButton deregistrationB = null;//注销
        MyButton reLoginB = null;//重新登录
        MyButton sentB = null;//发送反馈


        //构造
        SettingsP() {
            initPanel();
            initComp();
        }

        //自身构造
        private void initPanel() {
            this.setBackground(ColorManager.枫林白);
            this.setLayout(null);
        }

        //组件
        private void initComp() {
            //logo
            logoL = new JLabel("MyToDoList");
            logoL.setIcon(LOGO);
            logoL.setBounds(20, 80, 330, 150);
            logoL.setFont(new Font("MV Boli", Font.ITALIC, 40));
            logoL.setVerticalTextPosition(JLabel.CENTER);
            logoL.setHorizontalTextPosition(JLabel.RIGHT);//文字相对图片位置
            logoL.setHorizontalAlignment(JLabel.CENTER);
            logoL.setVerticalAlignment(JLabel.TOP);//总体方位
//软件说明
            appDescription = new JLabel(LoginMenu.defaultTab("当前软件版本为: " + AppTest.VERSION + "仅为测试版本."));
            appDescription.setFont(new Font("幼圆", Font.ITALIC, 20));
            appDescription.setBounds(90, 130, 150, 300);
            appDescription.setHorizontalAlignment(JLabel.CENTER);
            appDescription.setVerticalAlignment(JLabel.CENTER);
//用户反馈
            userFeedBack = new JTextArea("如遇任何问题,可在下方反馈:");
            userFeedBack.setBounds(30, 600, 1200, 200);
            userFeedBack.setBorder(new MyJTextBorder(ColorManager.雪山灰));
            userFeedBack.setForeground(ColorManager.雪山黑);
            userFeedBack.setFont(new Font("幼圆", Font.PLAIN, 25));
            userFeedBack.setOpaque(false);
            //退出
            exitB = new MyButton("退出ToDoList", ColorManager.雪山灰, ColorManager.雪山黑, ColorManager.雪山橙);
            exitB.setBounds(900, 440, 150, 50);
            exitB.setFont(new Font("幼圆", Font.PLAIN, 20));
            exitB.addActionListener(this);
            exitB.setFocusable(false);
            exitB.setBorder(null);
            exitB.setVerticalAlignment(JButton.CENTER);
            exitB.setHorizontalAlignment(JButton.CENTER);
            //重新登录
            reLoginB = new MyButton("重新登录", ColorManager.雪山灰, ColorManager.雪山黑, ColorManager.雪山橙);
            reLoginB.setBounds(900, 320, 150, 50);
            reLoginB.setFont(new Font("幼圆", Font.PLAIN, 20));
            reLoginB.addActionListener(this);
            reLoginB.setFocusable(false);
            reLoginB.setBorder(null);
            reLoginB.setVerticalAlignment(JButton.CENTER);
            reLoginB.setHorizontalAlignment(JButton.CENTER);
            //注销 要提醒
            deregistrationB = new MyButton("注销", ColorManager.雪山灰, ColorManager.雪山黑, ColorManager.雪山橙);
            deregistrationB.setBounds(900, 200, 150, 50);
            deregistrationB.setFont(new Font("幼圆", Font.PLAIN, 20));
            deregistrationB.addActionListener(this);
            deregistrationB.setFocusable(false);
            deregistrationB.setBorder(null);
            deregistrationB.setVerticalAlignment(JButton.CENTER);
            deregistrationB.setHorizontalAlignment(JButton.CENTER);

            sentB = new MyButton("发送", ColorManager.雪山灰, ColorManager.雪山黑, ColorManager.雪山橙);
            sentB.setBounds(1100, 820, 130, 40);
            sentB.setFont(new Font("幼圆", Font.PLAIN, 20));
            sentB.addActionListener(this);
            sentB.setFocusable(false);
            sentB.setBorder(null);
            sentB.setVerticalAlignment(JButton.CENTER);
            sentB.setHorizontalAlignment(JButton.CENTER);

            this.add(sentB);
            this.add(exitB);
            this.add(reLoginB);
            this.add(deregistrationB);
            this.add(userFeedBack);
            this.add(appDescription);
            this.add(logoL);

        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exitB) {
                int result = JOptionPane.showConfirmDialog(null, "确认要退出吗?", "ToDoList", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, question);
                System.out.println(result);
                if (result == 0) {
                    saveDate();
                    myDisposed();
                }
            } else if (e.getSource() == deregistrationB) {
                int result = JOptionPane.showConfirmDialog(null, LoginMenu.defaultTab("确定要注销吗?注销后账号将无法找回!"), "ToDoList", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, warning);
                if (result == 0) {
                    deRegistration();
                    JOptionPane.showConfirmDialog(null, LoginMenu.defaultTab("注销成功!"), "ToDoList", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, warning);
                    new LoginMenu();
                    myDisposed();
                }
            } else if (e.getSource() == reLoginB) {
                int result = JOptionPane.showConfirmDialog(null, LoginMenu.defaultTab("确定要退出登录吗?"), "ToDoList", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, question);
                if (result == 0) {
                    saveDate();
                    myDisposed();
                    new LoginMenu();
                }
            } else if (e.getSource() == sentB) {
                if (userFeedBack.getText().equals("")) {
                    JOptionPane.showConfirmDialog(null, LoginMenu.defaultTab("内容不能为空!"), "ToDoList", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE, warning);
                } else {
                    JOptionPane.showConfirmDialog(null, LoginMenu.defaultTab("感谢反馈!"), "ToDoList", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE);
                }
            }
        }

        //注销操作
        private void deRegistration() {
            int dateL = userVDate.size();
            for (int i = 0; i < dateL; i++) {
                if (userVDate.get(i).getUserName().equals(user.getUserName())) {
                    userVDate.remove(i);
                    break;
                }
            }
            setUserDate();
        }
    }


    //注册时构造
    MainMenu(User user) {
        this.user = user;//获取用户信息
        getDate();//get eventsdate
        getUserDate();//反序列化 get usersvdate
        pool = Executors.newCachedThreadPool();//创建线程池
        try {
            iniImage();
        } catch (CantfindImageException e) {
            System.out.println(e.getMessage());
        }
        //框架初始化
        initFrame();
        //组件
        initComp();

        this.setVisible(true);
    }

    //再次启动构造
//    MainMenu(MainMenu cloneO) {
//        this.user = cloneO.getUser();
//        getUserDate();//反序列化
//        try {
//            iniImage();
//        } catch (CantfindImageException e) {
//            System.out.println(e.getMessage());
//        }
//        //框架初始化
//        initFrame();
//
//        this.mainP = cloneO.getMainP();
//        this.add(mainP);
//        this.pack();
//        this.setLocationRelativeTo(null);//放在pack后面
//        this.setVisible(true);
//    }

    private void initFrame() {
        this.setTitle("ToDoList " + AppTest.VERSION);
        this.setIconImage(LOGO.getImage());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//弹出确认框
        this.setResizable(false);
        this.addWindowListener(this);
    }

    private void initComp() {
        mainP = new MainP();

        this.add(mainP);
        this.pack();
        this.setLocationRelativeTo(null);//放在pack后面
    }

    //    public void speak() {
//        System.out.println(this.user.getUserName() + " " + this.user.getPassWord());
//    }

    //事件数据保存 date save
    private void saveDate() {
        int dateL = userVDate.size();
        //数据保存
        user.setEventsVDate(eventsVDate);
        for (int i = 0; i < dateL; i++) {
            if (userVDate.get(i).getUserName().equals(user.getUserName())) {
                userVDate.set(i, user);
                break;
            }
        }
        setUserDate();//直接储存了usersvdate
    }

    //事件数据获取
    private void getDate() {
        this.eventsVDate = user.getEventsVDate();
    }

    //用户数据反序列化
    //first step
    private void getUserDate() {
        File userDate = new File(USER_FILE);
        FileInputStream is = null;
        try {
            is = new FileInputStream(userDate);
            ObjectInputStream ois = new ObjectInputStream(is);
            userVDate = (ArrayList<User>) ois.readObject();
            ois.close();
            is.close();
        } catch (IOException e) {
            System.out.println("数据库为空!");
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    //用户数据序列化
    private void setUserDate() {
        File userDate = new File(USER_FILE);
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(userDate);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(userVDate);
            oos.close();
            os.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //图片的初始化
    private void iniImage() throws CantfindImageException {
        LOGO = new ImageIcon(HEAD_PATH + "list.png");
        question = new ImageIcon(HEAD_PATH + "question.png");
        warning = new ImageIcon(HEAD_PATH + "tips.png");

        if (LOGO.getDescription() == null || question.getDescription() == null || warning.getDescription() == null) {
            throw new CantfindImageException();
        }
        Image temp = question.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        question = new ImageIcon(temp);
        temp = LOGO.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        LOGO = new ImageIcon(temp);
        temp = warning.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        warning = new ImageIcon(temp);
    }

    //线程关闭
    private void threadClose() {
        pool.shutdown();
        System.out.println("线程池关闭成功!");
        mainP.closeThread();
    }

    //资源释放
    private void sourceDispose() {
        this.dispose();
    }

    private void myDisposed() {
        threadClose();
        sourceDispose();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MainP getMainP() {
        return mainP;
    }

    public void setMainP(MainP mainP) {
        this.mainP = mainP;
    }
}
