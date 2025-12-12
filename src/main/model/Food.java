package main.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import main.simulation.Vector2D;

public class Food {

    private Vector2D position;


    public Food(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void render(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        
  
        AffineTransform oldTransform = g2d.getTransform();
        
        g2d.translate(position.x(), position.y());

        g2d.fillRect(0, 0, 8, 8);
        
        g2d.setTransform(oldTransform);
    }
}
