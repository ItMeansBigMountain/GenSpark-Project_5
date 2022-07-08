package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


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
    private static final int speed = 20;
    public ArrayList<Rectangle> body;
    private String direction;
    private Stack<Goblin> targets = new Stack<>();
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
    public void checkColission(Goblin t ,  int index) {
        Rectangle r3 = this.body.get(0);

        //INTERSECTION!!
        if ( t != null) {
            if (r3.intersects(new Rectangle( t.getPosx(), t.getPosy()))) {
                System.out.println("here!!!!!");
                this.targets.pop();

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


        for (int i = 1; i < this.targets.size(); i++) {
            checkColission(this.targets.get(i) , i);
        }

    }

    private void drawHuman(Graphics g) {
        moveHuman();

        // draw moved human
        Graphics2D g2d = (Graphics2D) g;


        //Draw Goblin
        if (this.targets.size() > 0) {
            for(int x = 0; x < this.targets.size(); x++)
            {
                g2d.setPaint(Color.red);
                g2d.drawRect(this.targets.get(x).getPosx(), this.targets.get(x).getPosy(), rec_width, rec_height);
                g2d.fillRect(this.targets.get(x).getPosx(), this.targets.get(x).getPosy(), rec_width, rec_height);
            }

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

    public Stack<Goblin> getTargets() {
        return this.targets;
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
        this.targets.push(goblin);
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

        // System.out.println("rendering human?");
        if (this.targets.size() > 0) {
            for (int x = 0; x < this.targets.size(); x++) {
                this.targets.get(x).follow_human();
            }
        }

    }
}