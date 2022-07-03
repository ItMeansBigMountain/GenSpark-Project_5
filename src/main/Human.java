package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


import static main.Rectangle.rec_height;
import static main.Rectangle.rec_width;

public class Human extends JPanel {

    //GAME STATISTICS ATTRIBUTES
    private int combatLVL = 10;
    private int health = 10;
    private int strength = 10;
    private int attack = 10;
    private int defence = 10;
    private int compassion = 10;
    private int intelligence = 10;
    private ArrayList<Object> inventory = new ArrayList<>();


    //GUI ATTRIBUTES
    private static final Color c = new Color(115, 162, 78);
    private static final int start = 250;
    private static final int speed = 25;
    private ArrayList<Rectangle> body;
    private String direction;
    private Goblin target;
    private Main window;


    //CONSTRUCTOR
    public Human(main.Main window, Random random) {
        //GUI INIT
        this.window = window;
        this.body = new ArrayList<>();
        body.add(new Rectangle(start, start));
        this.direction = "right";

        //STATISTICS INIT
        this.health = 10;
        this.combatLVL = (this.defence + this.attack + this.strength + this.intelligence + this.compassion) / 5;
        this.attack = random.ints(3, 9 + 50).findFirst().getAsInt();
        this.strength = random.ints(5, 7 + 50).findFirst().getAsInt();
        this.defence = random.ints(3, 7 + 50).findFirst().getAsInt();
        this.intelligence = random.ints(2, 10 + 50).findFirst().getAsInt();
        this.compassion = random.ints(2, 9 + 50).findFirst().getAsInt();
        this.inventory = new ArrayList<>();

    }


    //FUNCTIONAL FUNCTIONS
    public void checkColission() {
        Rectangle r3 = this.body.get(0);

        //INTERSECTION!!
        if (this.target != null) {
            if (r3.intersects(new Rectangle(this.target.getPosx(), this.target.getPosy()))) {
                this.target = null;
                System.out.println("here!!!!!");
                this.target = null;

                //GAME OVER!!!!!
//                System.out.println("You lose!");
//                this.window.setVisible(false);
//
//                JFrame parent = new JFrame("Game over!");
//                JOptionPane.showMessageDialog(parent, "Your score: " + this.body.size());
//
//                this.window.dispatchEvent(new WindowEvent(this.window, WindowEvent.WINDOW_CLOSING));
//                System.exit(0);
            }
        }
    }

    public void moveHuman() {

        ArrayList<Rectangle> newLst = new ArrayList<>();

        Rectangle first = this.body.get(0);
        Rectangle head = new Rectangle(first.getPosx(), first.getPosy());

        switch (this.direction) {
            case "right" -> head.setPosx(speed);
            case "left" -> head.setPosx(-speed);
            case "up" -> head.setPosy(-speed);
            case "down" -> head.setPosy(speed);
        }
        newLst.add(head);

        for (int i = 1; i < this.body.size(); i++) {
            Rectangle previous = this.body.get(i - 1);
            Rectangle newRec = new Rectangle(previous.getPosx(), previous.getPosy());
            newLst.add(newRec);
        }


        this.body = newLst;
        checkColission();
    }

    private void drawHuman(Graphics g) {
        moveHuman();

        // draw moved human
        Graphics2D g2d = (Graphics2D) g;


        if (this.target != null) {
            g2d.setPaint(Color.red);
            g2d.drawRect(this.target.getPosx(), this.target.getPosy(), rec_width, rec_height);
            g2d.fillRect(this.target.getPosx(), this.target.getPosy(), rec_width, rec_height);
        }

        g2d.setPaint(Color.blue);
        for (Rectangle rec : this.body) {
            g2d.drawRect(rec.getPosx(), rec.getPosy(), rec_width, rec_height);
            g2d.fillRect(rec.getPosx(), rec.getPosy(), rec_width, rec_height);
        }
    }


    //GETTERS
    public int getCombatLVL() {
        return this.combatLVL;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getDefence() {
        return this.defence;
    }


    public int getIntelligence() {
        return this.intelligence;
    }

    public int getHealth() {
        return this.health;
    }

    public int getCompassion() {
        return this.compassion;
    }

    public Goblin getTarget() {
        return this.target;
    }

    public String getDirection() {
        return this.direction;
    }


    //SETTERS
    public void setCombatLVL(int combatLVL) {
        this.combatLVL = combatLVL;
    }

    public void setAttack(int attack) {
        this.strength = attack;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDefence(int defence) {
        this.strength = defence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCompassion(int compassion) {
        this.compassion = compassion;
    }

    public void setTarget(Goblin goblin) {
        this.target = goblin;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    //J FRAMES REPAINT PER FRAME
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(c);
        drawHuman(g);
    }
}