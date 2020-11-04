import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;
    private final int DOT_SIZE = 16; //сколько будет занимать одна ячейка змейки и яблока
    private final int ALL_DOTS = 400; //сколько игровых единиц может поместиться на игровом поле (20 в ширину, 20 в длину)
    private Image dot;
    private Image apple;
    private int appleX; //позиция яблока
    private int appleY; //позиция ялока
    private int[] x = new int[ALL_DOTS]; //положения змейки по x
    private int[] y = new int[ALL_DOTS]; //положения змейки по y
    private int dots; //размер змейки
    private Timer timer; //таймер
    //переменные отвечающие за текущее направление змейки (ниже)
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.orange); //цвет игрового поля
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true); //чтобы нажатие на клавиши было сконнекчено с игровым полем
    }

    public void initGame() { //метод, который инициализирует игровой процесс
        dots = 3; //начальный размер змейки
        for (int i = 0; i < dots; i++) { //положения старта змейки
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, (ActionListener) this); //этот класс будет отвечать за обработку каждого вызова таймера, змейка двигается со скорость. 250ms
        timer.start();
        createApple(); //метод для создания яблока
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages() { //метод для загрузки картинок
        ImageIcon iia = new ImageIcon("apple.jpg");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("snake.jpg");
        dot = iid.getImage();
    }

    public void move() { //метод, двигающий змейку
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) {
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    @Override
    protected void paintComponent(Graphics g) { //перерисовка компонентов
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this); //this - кто перерисовывает
            for (int i = 0; i < dots; i++) { //перерисовка змейки
                g.drawImage(dot, x[i], y[i], this);
            }
        }else {
            String str = "Game Over";
            //Font f = new Font("Arial", 14, Font.BOLD); //шрифт
            g.setColor(Color.black);
            //g.setFont(f);
            g.drawString(str, 120, SIZE/2);
        }
    }

    public void checkApple() { //столкнулись мы с яблоком или нет
        if (x[0] == appleX && y[0] == appleY) {
            dots++; //увеличивается размерность змейки
            createApple(); //создается новое яблоко
        }
    }

    public void checkCollisions() { //проверка на столкновение змейки с собой и с границами игрового поля
        for (int i = dots; i > 0; i--) { //столкновение самой с собой
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
        if (x[0] > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //метод будет выхываться каждый раз, когда будет тикать таймер
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter { //класс для обработки нажатия клавиш

        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode(); //код клавиши, которая была нажата
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                up = true;
                right = false;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
