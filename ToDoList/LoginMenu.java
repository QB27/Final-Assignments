package ToDoList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;

public class LoginMenu extends JFrame implements MouseListener {
    final String HEAD_PATH = "ToDoImage\\";
    final int FRAME_WIDTH = 1360;//窗口宽度
    final int FRAME_HEIGHT = 800;//窗口高度
    final int LEFT_WIDTH = 960;//左侧宽度
    final int RIGHT_WIDTH = 400;//右侧

    final String USER_FILE = "userDate.ser";//用户数据文件
    User tempUser = null;
    private ArrayList<User> userVDate = new ArrayList<>();//用户数据反序列化
    //Layout
    CardLayout cardl = new CardLayout();
    //Panel
    JPanel leftP = null;//左侧
    JPanel rightContainer = null;//右侧容器
    JPanel imageP = null;//图片管理面板
    LoginP loginP = null;//登录界面
    RegisterP registerP = null;//注册界面

    //测试用
    JButton 登录界面 = null;
    JButton 注册界面 = null;

    //ImageIcon
    ImageIcon LOGO = null;
    ImageIcon coffee = null;//咖啡图片
    ImageIcon work = null;//工作图片
    ImageIcon warning = null;//警告图片
    ImageIcon notebook = null;//表图片

    //Label
    JLabel coffeeL = null;//图片容器
    JLabel workL = null;
    JLabel notebookL = null;
    JLabel warningL = null;
    JLabel backL = null;

    //内部类
    //登录界面
    class LoginP extends JPanel implements ActionListener, MouseListener {
        //Icon
        ImageIcon userI = null;
        ImageIcon passI = null;
        ImageIcon loginI = null;
        ImageIcon back = null;

        //Label
        JLabel userL = null;
        JLabel passL = null;
        JLabel loginL = null;
        JLabel logoL = null;
        JLabel tips = null;
        //Text
        JTextField userName = null;
        JTextField passWord = null;
        //Button
        MyButton login = null;
        MyButton register = null;


        LoginP() {

            try {
                initImage();
            } catch (CantfindImageException e) {
                System.out.println(e.getMessage());
                System.out.println("0");
                System.exit(0);
            }


            initPanel();
            initComp();
        }

        private void initPanel() {//初始化
            this.setLayout(null);
            this.setBackground(ColorManager.浅绿色);
        }

        private void initComp() {//组件
//Label
            userL = new JLabel("用户名");
            userL.setIcon(userI);
            userL.setBounds(10, 300, 120, 40);
            userL.setFont(new Font("幼圆", Font.ITALIC, 15));
            userL.setVerticalTextPosition(JLabel.CENTER);
            userL.setHorizontalTextPosition(JLabel.LEFT);//文字相对图片位置
            userL.setHorizontalAlignment(JLabel.CENTER);
            userL.setVerticalAlignment(JLabel.CENTER);//总体方位

            passL = new JLabel("密码");
            passL.setIcon(passI);
            passL.setBounds(15, 360, 120, 40);
            passL.setFont(new Font("幼圆", Font.ITALIC, 15));
            passL.setVerticalTextPosition(JLabel.CENTER);
            passL.setHorizontalTextPosition(JLabel.LEFT);//文字相对图片位置
            passL.setHorizontalAlignment(JLabel.CENTER);
            passL.setVerticalAlignment(JLabel.CENTER);//总体方位

            loginL = new JLabel();
            loginL.setIcon(loginI);
            loginL.setBounds(160, 200, 80, 80);
            loginL.setHorizontalAlignment(JLabel.CENTER);
            loginL.setVerticalAlignment(JLabel.CENTER);//总体方位

            logoL = new JLabel("MyToDoList");
            logoL.setIcon(LOGO);
            logoL.setBounds(20, 80, 330, 150);
            logoL.setFont(new Font("MV Boli", Font.ITALIC, 40));
            logoL.setVerticalTextPosition(JLabel.CENTER);
            logoL.setHorizontalTextPosition(JLabel.RIGHT);//文字相对图片位置
            logoL.setHorizontalAlignment(JLabel.CENTER);
            logoL.setVerticalAlignment(JLabel.CENTER);//总体方位

            backL = new JLabel();
            backL.setBounds(0, 0, RIGHT_WIDTH, FRAME_HEIGHT);
            backL.setIcon(back);

            tips = new JLabel();
            tips.setBounds(50, 500, 300, 80);
            tips.setFont(new Font("幼圆", Font.PLAIN, 22));

            //Text
            userName = new JTextField();
            userName.setBounds(150, 300, 220, 35);
            userName.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            userName.setOpaque(false);
            userName.setForeground(ColorManager.纯黑色);
            userName.setBorder(new MyJTextBorder(ColorManager.暗蓝色));

            passWord = new JTextField();
            passWord.setBounds(150, 360, 220, 35);
            passWord.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            passWord.setOpaque(false);
            passWord.setForeground(ColorManager.纯黑色);
            passWord.setBorder(new MyJTextBorder(ColorManager.暗蓝色));
//Button
            login = new MyButton("Login", ColorManager.浅红色, ColorManager.深红色, ColorManager.中间红);
            login.setBounds(100, 420, 100, 30);
            login.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            login.addActionListener(this);
            login.setFocusable(false);
            login.setBorder(null);
            login.setVerticalAlignment(JButton.CENTER);
            login.setHorizontalAlignment(JButton.CENTER);


            register = new MyButton("Signup", ColorManager.浅红色, ColorManager.深红色, ColorManager.中间红);
            register.setBounds(220, 420, 100, 30);
            register.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            register.addActionListener(this);
            register.setFocusable(false);
            register.setBorder(null);
            register.setVerticalAlignment(JButton.CENTER);
            register.setHorizontalAlignment(JButton.CENTER);

            this.add(tips);
            this.add(register);
            this.add(login);
            this.add(passWord);
            this.add(userName);
            this.add(logoL);
            this.add(loginL);
            this.add(passL);
            this.add(userL);
            this.add(backL);
        }

        private void initImage() throws CantfindImageException {//图片初始化


            userI = new ImageIcon(HEAD_PATH + "user.png");
            passI = new ImageIcon(HEAD_PATH + "password.png");
            loginI = new ImageIcon(HEAD_PATH + "login.png");
            back = new ImageIcon(HEAD_PATH + "loginback.png");


            if (userI.getDescription() == null || passI.getDescription() == null || loginI.getDescription() == null || back.getDescription() == null) {
                throw new CantfindImageException();
            }

            Image temp = userI.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            userI = new ImageIcon(temp);


            temp = LOGO.getImage().getScaledInstance(85, 85, Image.SCALE_SMOOTH);
            LOGO = new ImageIcon(temp);

            temp = passI.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            passI = new ImageIcon(temp);

            temp = loginI.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            loginI = new ImageIcon(temp);

            temp = back.getImage().getScaledInstance(RIGHT_WIDTH, FRAME_HEIGHT, Image.SCALE_SMOOTH);
            back = new ImageIcon(temp);
        }

        //登录操作
        private void login() {
            tips.setForeground(Color.green);
            tips.setText(defaultTab("登陆中..."));
            int result = checkID(userName.getText(), passWord.getText());//get index
            if (result == -1) {
                tips.setForeground(Color.RED);
                tips.setText(defaultTab("登陆失败,请重新输入!"));
            } else {
                tips.setForeground(Color.green);
                tips.setText(defaultTab("登陆成功!"));
                //相关初始化代码
                tempUser = userVDate.get(result);
                MainMenu new1 = new MainMenu(tempUser);//两个页面的userVDate 地址不一样 对应的user也不一样
                tempUser = null;
                myDisposed();
            }
        }

        //在数据库中检索用户
        private int checkID(String userName, String passWord) {
            int dateL = userVDate.size();
            if (dateL < 1) {
                return -1;//直接返回不存在
            } else {
                for (int i = 0; i < dateL; i++) {
                    if (userVDate.get(i).getUserName().equals(userName) && userVDate.get(i).getPassWord().equals(passWord)) {
                        return i;
                    }
                }
            }
            return -1;//全部检索完 还是不存在
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == register) {
                //清空前一个页面的信息
                userName.setText("");
                passWord.setText("");
                tips.setText("");
                //切换页面
                cardl.show(rightContainer, "R");
            } else if (e.getSource() == login) {
                login();
                getAllUserDate();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    //内部类
    //注册界面
    class RegisterP extends JPanel implements ActionListener, MouseListener {

        //Icon
        ImageIcon registerI = null;//注册界面图标
        ImageIcon userI = null;//用户图标
        ImageIcon passI = null;//密码图标
        ImageIcon confirmPI = null;
        ImageIcon backI = null;
        //Label
        JLabel userL = null;
        JLabel passL = null;
        JLabel confirmPL = null;
        JLabel registerL = null;
        JLabel backL = null;
        JLabel tips = null;

        //Button
        MyButton register = null;
        MyButton back = null;

        //Text
        JTextField userName = null;
        JTextField passWord = null;
        JTextField confirmW = null;

        RegisterP() {

            try {
                initImage();
            } catch (CantfindImageException e) {
                System.out.println(e.getMessage());
                System.out.println("1");
                System.exit(0);
            }
            initPanel();
            initComp();
        }

        //初始化
        private void initPanel() {
            this.setLayout(null);
            this.setBackground(ColorManager.浅黄色);
        }

        //组件
        private void initComp() {
//Label
            userL = new JLabel("用户名");
            userL.setIcon(userI);
            userL.setBounds(10, 260, 120, 40);
            userL.setFont(new Font("幼圆", Font.ITALIC, 15));
            userL.setVerticalTextPosition(JLabel.CENTER);
            userL.setHorizontalTextPosition(JLabel.LEFT);//文字相对图片位置
            userL.setHorizontalAlignment(JLabel.CENTER);
            userL.setVerticalAlignment(JLabel.CENTER);//总体方位

            passL = new JLabel("密码");
            passL.setIcon(passI);
            passL.setBounds(15, 320, 120, 40);
            passL.setFont(new Font("幼圆", Font.ITALIC, 15));
            passL.setVerticalTextPosition(JLabel.CENTER);
            passL.setHorizontalTextPosition(JLabel.LEFT);//文字相对图片位置
            passL.setHorizontalAlignment(JLabel.CENTER);
            passL.setVerticalAlignment(JLabel.CENTER);//总体方位

            confirmPL = new JLabel("确认密码");
            confirmPL.setBounds(15, 380, 120, 40);
            confirmPL.setIcon(confirmPI);
            confirmPL.setFont(new Font("幼圆", Font.ITALIC, 15));
            confirmPL.setVerticalTextPosition(JLabel.CENTER);
            confirmPL.setHorizontalTextPosition(JLabel.LEFT);//文字相对图片位置
            confirmPL.setHorizontalAlignment(JLabel.CENTER);
            confirmPL.setVerticalAlignment(JLabel.CENTER);//总体方位

            registerL = new JLabel();
            registerL.setIcon(registerI);
            registerL.setBounds(160, 160, 80, 80);
            registerL.setHorizontalAlignment(JLabel.CENTER);
            registerL.setVerticalAlignment(JLabel.CENTER);//总体方位

            backL = new JLabel();
            backL.setIcon(backI);
            backL.setBounds(0, 0, RIGHT_WIDTH, FRAME_HEIGHT);

            tips = new JLabel();
            tips.setBounds(50, 520, 300, 80);
            tips.setFont(new Font("幼圆", Font.PLAIN, 22));

            //Button
            register = new MyButton("signup", ColorManager.浅红色, ColorManager.深红色, ColorManager.中间红);
            register.setBounds(100, 460, 100, 30);
            register.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            register.addActionListener(this);
            register.setFocusable(false);
            register.setBorder(null);
            register.setVerticalAlignment(JButton.CENTER);
            register.setHorizontalAlignment(JButton.CENTER);

            back = new MyButton("Back", ColorManager.浅红色, ColorManager.深红色, ColorManager.中间红);
            back.setBounds(220, 460, 100, 30);
            back.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            back.addActionListener(this);
            back.setFocusable(false);
            back.setBorder(null);
            back.setVerticalAlignment(JButton.CENTER);
            back.setHorizontalAlignment(JButton.CENTER);

            //Text
            userName = new JTextField();
            userName.setOpaque(false);//设置透明
            userName.setBounds(150, 260, 220, 40);
            userName.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            userName.setForeground(ColorManager.纯黑色);
            userName.setBorder(new MyJTextBorder(ColorManager.暗蓝色));

            passWord = new JTextField();
            passWord.setOpaque(false);//设置透明
            passWord.setBounds(150, 325, 220, 40);
            passWord.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            passWord.setForeground(ColorManager.纯黑色);
            passWord.setBorder(new MyJTextBorder(ColorManager.暗蓝色));

            confirmW = new JTextField();
            confirmW.setOpaque(false);//设置透明
            confirmW.setBounds(150, 390, 220, 40);
            confirmW.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 20));
            confirmW.setForeground(ColorManager.纯黑色);
            confirmW.setBorder(new MyJTextBorder(ColorManager.暗蓝色));

            this.add(tips);
            this.add(userName);
            this.add(passWord);
            this.add(confirmW);
            this.add(registerL);
            this.add(back);
            this.add(register);
            this.add(confirmPL);
            this.add(userL);
            this.add(passL);
            this.add(backL);
        }

        //图片初始化
        private void initImage() throws CantfindImageException {
            userI = new ImageIcon(HEAD_PATH + "user.png");
            passI = new ImageIcon(HEAD_PATH + "password.png");
            registerI = new ImageIcon(HEAD_PATH + "register.png");
            confirmPI = new ImageIcon(HEAD_PATH + "confirmpassW.png");
            backI = new ImageIcon(HEAD_PATH + "registerback.png");
            if (userI.getDescription() == null || passI.getDescription() == null || registerI.getDescription() == null || confirmPI.getDescription() == null) {
                throw new CantfindImageException();
            }
            Image temp = userI.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            userI = new ImageIcon(temp);
            temp = passI.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            passI = new ImageIcon(temp);
            temp = registerI.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            registerI = new ImageIcon(temp);
            temp = confirmPI.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
            confirmPI = new ImageIcon(temp);
            temp = backI.getImage().getScaledInstance(RIGHT_WIDTH, FRAME_HEIGHT, Image.SCALE_SMOOTH);
            backI = new ImageIcon(temp);

        }

        //检测是否已经存在该用户名
        private boolean isExists(String userName) {
            int dateL = userVDate.size();//获取数组长度
            if (dateL < 1) {//小于一则没有数据
                return false;
            } else {
                for (int i = 0; i < dateL; i++) {
                    if (userVDate.get(i).getUserName().equals(userName)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private void register() throws IOException {
            String userN = userName.getText();
            String passW = passWord.getText();
            String confiW = confirmW.getText();
            //判断用户名和密码是否符合规则
            if (userN.equals("") || passW.equals("") || confiW.equals("")) {
                tips.setForeground(Color.red);
                tips.setText(defaultTab("用户名或密码不能为空"));
                return;
            } else if (!passW.equals(confiW)) {
                tips.setForeground(Color.red);
                tips.setText(defaultTab("密码和确认密码不一致!"));
                return;
            } else if (!(userN.matches("\\w*") && passW.matches("\\w*") && confiW.matches("\\w*"))) {//正则表达式
                tips.setForeground(Color.red);
                tips.setText(defaultTab("用户名或密码只能由字母、数字或 _ 组成"));
                return;
            } else {
                if (isExists(userN)) {
                    tips.setForeground(Color.red);
                    tips.setText(defaultTab("用户名已存在,请重新输入!"));
                } else {
                    tempUser = new User(userN, passW);
                    userVDate.add(tempUser);
                    tips.setForeground(Color.green);
                    tips.setText(defaultTab("用户创建成功!"));
                    tempUser = null;
                }
            }

        }


        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == back) {
                //清空页面信息
                userName.setText("");
                passWord.setText("");
                confirmW.setText("");
                tips.setText("");
                //切换页面
                cardl.show(rightContainer, "L");
            } else if (e.getSource() == register) {
                try {
                    register();
                    //进行一次数据序列化
                    outPutUserDate();
                } catch (IOException eq) {
                    System.out.println(eq.getMessage());
                }

            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    LoginMenu() {
        //数据初始化
        try {
            userDateInit();//文件创建
            inPutUserDate();//数据反序列化
        } catch (IOException e) {
            System.out.println("用户数据初始化异常!");
            System.out.println(e.getMessage());
            System.exit(0);
        }
        //图片初始化
        try {
            //fontNamg(); //获取字体名字
            initImage();
        } catch (CantfindImageException e) {
            System.out.println(e.getMessage());//正式软件时写弹窗 点击确定后程序关闭
            System.out.println("2");
            System.exit(0);//图片初始化失败 不允许
        }

        initPane();//初始化窗口
        initComp();//初始化组件

        this.setVisible(true);
    }

    //窗口初始化
    private void initPane() {
//应该先写一个mypanel类 然后再用pack()
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("ToDoList " + AppTest.VERSION);
        this.setIconImage(LOGO.getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//默认关闭
        this.setResizable(false);//不可自定义大小
        this.getContentPane().setBackground(ColorManager.暗蓝色);//设置背景颜色 方便调整控件
        this.setLocationRelativeTo(null);//居中
        this.setLayout(new BorderLayout());
    }

    private void initComp() {

        //左边面板
        leftP = new JPanel();
        leftP.setPreferredSize(new Dimension(960, 800));
        leftP.setLayout(null);
        leftP.setBackground(ColorManager.浅红色);

        //图片管理面板
        imageP = new JPanel();
        imageP.setBounds(180, 100, 700, 700);
        imageP.setLayout(new GridLayout(3, 2, 20, 20));//3排2列
        imageP.setBackground(ColorManager.浅红色);

        //右边的容器
        rightContainer = new JPanel();
        rightContainer.setPreferredSize(new Dimension(400, 800));
        rightContainer.setLayout(cardl);//设置放置方式

        //登录界面
        loginP = new LoginP();
        //注册界面
        registerP = new RegisterP();

        //图片容器
        workL = new JLabel();
        coffeeL = new JLabel();
        warningL = new JLabel();
        notebookL = new JLabel();


        // initTestComp();//测试用
        coffeeL.setIcon(coffee);
        notebookL.setIcon(notebook);
        workL.setIcon(work);
        warningL.setIcon(warning);


        rightContainer.add(registerP, "R");//注册添加到容器
        rightContainer.add(loginP, "L");//登录添加到容器
        cardl.show(rightContainer, "L");//默认显示

        //添加Label
        imageP.add(coffeeL);
        imageP.add(notebookL);
        imageP.add(workL);
        imageP.add(warningL);


        leftP.add(imageP);


        this.getContentPane().add(rightContainer, BorderLayout.EAST);
        this.getContentPane().add(leftP, BorderLayout.WEST);
    }

    //图片初始化
    private void initImage() throws CantfindImageException {
        coffee = new ImageIcon(HEAD_PATH + "coffee.png");
        work = new ImageIcon(HEAD_PATH + "work.png");
        notebook = new ImageIcon(HEAD_PATH + "notebook.png");
        warning = new ImageIcon(HEAD_PATH + "warning.png");
        LOGO = new ImageIcon(HEAD_PATH + "list.png");


        if (coffee.getDescription() == null || work.getDescription() == null || notebook.getDescription() == null || warning.getDescription() == null || LOGO.getDescription() == null) {
            throw new CantfindImageException();
        }

        Image temp = coffee.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        coffee = new ImageIcon(temp);

        temp = work.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        work = new ImageIcon(temp);

        temp = notebook.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        notebook = new ImageIcon(temp);

        temp = warning.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        warning = new ImageIcon(temp);

    }

    private void userDateInit() throws IOException {
        File userDate = new File(USER_FILE);//获取文件
        if (!userDate.createNewFile()) {//创建数据
            System.out.println("用户数据库已创建!");
        } else {
            System.out.println("用户数据库创建成功!");
        }
    }


//    private void initTestComp() {
//        登录界面 = new JButton("获取所有用户数据");
//        登录界面.setBounds(550, 300, 220, 60);
//        登录界面.addMouseListener(this);
//        leftP.add(登录界面);
//
//        注册界面 = new JButton("注册界面");
//        注册界面.setBounds(600, 500, 130, 60);
//        注册界面.addMouseListener(this);
//        leftP.add(注册界面);
//    }//测试用

    //监听
    @Override
    public void mouseClicked(MouseEvent e) {
//        Object temp = e.getSource();
//        //记得改
//        if (temp == 登录界面) {
//            getAllUserDate();
//
//        } else if (temp == 注册界面) {
//        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void inPutUserDate() throws IOException {//反序列化 顺便初始化
        File userDate = new File(USER_FILE);
        FileInputStream is = new FileInputStream(userDate);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(is);//如果文件是空的 那直接抛出异常
            //如果成功了 那直接把文件里的数据反序列化
            userVDate = (ArrayList<User>) ois.readObject();
            ois.close();
            is.close();

        } catch (EOFException e) {
            System.out.println(e.getMessage());
            System.out.println("数据读取完毕");//直接把空list写入文件
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            //如果是第一次 那么虚拟数据库长度为0 主动序列化数据 方便下次
            if (userVDate.size() < 1) {
                System.out.println("首次创建数据!");
                outPutUserDate();
            }
        }
    }

    private void outPutUserDate() throws IOException {//序列化
        File userDate = new File(USER_FILE);
        FileOutputStream os = new FileOutputStream(userDate);//直接覆盖
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(userVDate);
            oos.close();
            os.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getAllUserDate() {
        int dateL = userVDate.size();
        for (int i = 0; i < dateL; i++) {
            System.out.println(userVDate.get(i).getUserName() + " " + userVDate.get(i).getPassWord());
        }
    }

    private void myDisposed() {
        this.dispose();
    }

    //自动换行
    public static String defaultTab(String content) {
        return "<html>" + content + "</html>";
    }

    //获取字体名字
    private void fontNamg() {
        String[] fontnames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (int i = 0; i < fontnames.length; i++) {
            System.out.println(fontnames[i]);
        }
    }


}
