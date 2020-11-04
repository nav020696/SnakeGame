import javax.swing.*;

public class MainWindow extends JFrame{

    public MainWindow(){
        setTitle("Змейка"); //параметры класса JFrame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //когда будем нажимать на крестик, программа прекратит работу
        setSize(320, 345); //размер окна 345 потому что 320 + 25 верхняя плашка с title
        setLocation(400, 400); //где окно появится
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mv = new MainWindow(); //экземпляр класса
    }
}
