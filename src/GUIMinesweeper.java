import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GUIMinesweeper implements ItemListener {
    JButton[][] buttons;
    int ButtonSize = 40;
    final String EASY = "Easy";
    final String MEDIUM = "Medium";
    final String HARD = "Hard";
    //final String CUSTOM = "Custom";
    JPanel cards;
    Minefield gameField;
    JTextField gameState;
    JPanel gamePanel;
    JPanel buttonPanel;
    JFrame win;
    JComboBox<String> cb;
    //Builds the Games to make the Window big enough for a Hard game
    public static void main(String[] a){
        new GUIMinesweeper(16, 16, 100);
    }

    GUIMinesweeper(int height,int length,int mineCount){
        win = new JFrame("Minesweeper");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel topPanel = mkTopPanels();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        gamePanel = new JPanel(new GridLayout(1,1));
        buttonPanel = mkSmallGrid(height,length,mineCount);
        gamePanel.add(buttonPanel);
        mainPanel.add(gamePanel);
        win.add(mainPanel);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.pack();
        win.setVisible(true);
        update();
        reset();

    }
    //builds the Game Grid
    JPanel mkSmallGrid(int height,int length, int mineCount){
        JPanel res = new JPanel(new GridLayout(length,height));
        gameField = new Minefield(height, length, mineCount);
        buttons = new JButton[height][length];
        for(int i=0; i < height; i++){
            for(int j=0; j < length; j++){
                final int a = i;
                final int b = j;
                buttons[i][j] = new JButton();
                buttons[i][j].setPreferredSize(new Dimension(ButtonSize+5,ButtonSize));
                buttons[i][j].addActionListener(e->leftClickedHandler(a,b));
                buttons[i][j].addMouseListener(new MouseAdapter() {
                     public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isRightMouseButton(e)) {rightClickedHandler(a,b);}
                     }
                });
                res.add(buttons[i][j]);
            }
        }
        return res;
    }
    //Control and Difficulty Settings
    JPanel mkTopPanels(){
        JPanel topBox = new JPanel(new GridLayout(0,1));
        JPanel res = new JPanel(new FlowLayout());
        JButton resetButton = new JButton("New Game / Reset");
        resetButton.addActionListener(e -> {reset();});
        res.add(resetButton);
        JButton startButton = new JButton("Exit");
        startButton.addActionListener(e->System.exit(0));
        res.add(startButton);
        JPanel comboBoxPane = new JPanel();
        String[] comboBoxItems = { EASY, MEDIUM, HARD/*, CUSTOM*/ };
        cb = new JComboBox<>(comboBoxItems);
        cb.setEditable(false);
        cb.addItemListener(this);
        comboBoxPane.add(cb);

        comboBoxPane.add(cb);
        JTextField heightEZ = new JTextField("height: 9");
        heightEZ.setEditable(false);
        JTextField lengthEZ = new JTextField("length: 9");
        lengthEZ.setEditable(false);
        JTextField minesEZ = new JTextField("mines: 10");
        minesEZ.setEditable(false);
        JTextField heightME = new JTextField("height: 12");
        heightME.setEditable(false);
        JTextField lengthME = new JTextField("length: 12");
        lengthME.setEditable(false);
        JTextField minesME = new JTextField("mines: 30");
        minesME.setEditable(false);
        JTextField heightHA = new JTextField("height: 16");
        heightHA.setEditable(false);
        JTextField lengthHA = new JTextField("length: 16");
        lengthHA.setEditable(false);
        JTextField minesHA = new JTextField("mines: 100");
        minesHA.setEditable(false);
        JPanel card1 = new JPanel();
        card1.add(heightEZ);
        card1.add(lengthEZ);
        card1.add(minesEZ);
        JPanel card2 = new JPanel();
        card2.add(heightME);
        card2.add(lengthME);
        card2.add(minesME);
        JPanel card3 = new JPanel();
        card3.add(heightHA);
        card3.add(lengthHA);
        card3.add(minesHA);
        /*JPanel card4 = new JPanel();
        card4.add(new JTextField("height"));
        card4.add(new JTextField("width"));
        card4.add(new JTextField("mines"));*/

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1, EASY);
        cards.add(card2, MEDIUM);
        cards.add(card3, HARD);
       // cards.add(card4, CUSTOM);

        topBox.add(comboBoxPane);
        topBox.add(cards, BorderLayout.CENTER);
        topBox.add(res);
        JPanel gameStatePanel = new JPanel();
        gameState = new JTextField();
        gameState.setEditable(false);
        gameStatePanel.add(gameState);
        topBox.add(gameStatePanel);
        return topBox;
    }
    //ItemListener for the ComboBox
    public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
    //handle the left/right click
    void leftClickedHandler(int height, int length) {
        if (!(gameField.gameOver || gameField.gameWon)){
            gameField.leftClicked(height, length);
            update();
        }
    }
    void rightClickedHandler(int height, int length) {
        if (!(gameField.gameOver || gameField.gameWon)) {
            gameField.rightClicked(height, length);
            if(gameField.isBlocked(height, length)){buttons[height][length].setBackground(Color.GREEN);}
            else{buttons[height][length].setBackground(null);}
            textUpdate();
        }
    }
    //Updates the Game (Visually)
    void update(){
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[0].length; j++) {
                if (gameField.getOpen(i,j)) {
                    buttons[i][j].setEnabled(false);
                    if(gameField.isMine(i,j)){
                        buttons[i][j].setBackground(Color.RED);
                    }
                    else{
                        if(gameField.searchNearbyMines(i,j)!= 0) {
                            buttons[i][j].setText(Integer.toString(gameField.searchNearbyMines(i, j)));
                        }
                    }
                }
            }
        }
        textUpdate();
    }
    //text update (is called after every Click)
    void textUpdate(){
        if(gameField.openCounter()!=0 && !(gameField.gameWon || gameField.gameOver)&&gameField.blockedCounter()==0){gameState.setText("Find the mines!");}
        if(gameField.openCounter()!=0 && !(gameField.gameWon || gameField.gameOver)&&gameField.blockedCounter()!=0){gameState.setText("Mines Left:" + (gameField.mineCounter()-gameField.blockedCounter()));}
        if(gameField.openCounter()==0){gameState.setText("lets Start!");}
        if(gameField.gameWon){
            gameState.setText("Won!");
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[0].length; j++) {
                    if (gameField.isMine(i, j)) {
                        buttons[i][j].setText("X");
                    }
                }
            }
        }
        if(gameField.gameOver){
            gameState.setText("Game Over!");
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[0].length; j++) {
                    if (gameField.isMine(i, j)) {
                        buttons[i][j].setText("X");
                    }
                }
            }
        }
    }
    //reset Game/new Game builds the new Game according to the difficulty
    void reset(){
        for (JButton[] button : buttons) {
            for (int j = 0; j < buttons[0].length; j++) {
                button[j].setText(" ");
                button[j].setEnabled(true);
                button[j].setBackground(null);
            }
        }
        gameField.newGame();
        gamePanel.removeAll();
        String difficulty= cb.getSelectedItem().toString();
        if(difficulty.equals(EASY)){buttonPanel = mkSmallGrid(9, 9,10);}
        if(difficulty.equals(MEDIUM)){buttonPanel = mkSmallGrid(12, 12,30);}
        if(difficulty.equals(HARD)){buttonPanel = mkSmallGrid(16, 16,100);}
        gamePanel.add(buttonPanel);
        textUpdate();
    }
}