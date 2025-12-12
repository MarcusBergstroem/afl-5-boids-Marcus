package main.behavior;


import main.model.Boid;
import main.model.Food;
import main.simulation.*;
import java.util.List;

public class SeeksBehavior implements BehaviorStrategy {
    
    FlockBehavior flockBehavior = new FlockBehavior();

    private static List<Food> foods = List.of(
        new Food(new Vector2D(333, 350)),
        new Food(new Vector2D(666, 350))
    );

    @Override
    public Forces calculateForces(Boid boid, List<Boid> neighbors) {
        
        if (neighbors.isEmpty()) {
            return new Forces();
        }

        FlockWeights weights = getFlockWeights();

        Vector2D separation = flockBehavior.calculateSeparation(boid, neighbors, weights);
        Vector2D alignment = flockBehavior.calculateAlignment(boid, neighbors, weights);
        Vector2D cohesion = Vector2D.ZERO; //flockBehavior.calculateCohesion(boid, neighbors, weights);

        Vector2D seeking = calculateSeeking(boid, weights);

        // Tilføjer seeking til cohesion
        separation = new Vector2D(
            cohesion.x() + seeking.x()*2,
            cohesion.y() + seeking.y()*2
        );

        return new Forces(separation, alignment, cohesion);
    }

    @Override
    public FlockWeights getFlockWeights() {
        return FlockWeights.standard();
    }

    private Vector2D calculateSeeking(Boid boid, FlockWeights weights) {
        double centerX = 0, centerY = 0;
        int count = 0;

        double bPosX = boid.getX();
        double bPosY = boid.getY();

        for (Food food : foods) {
            double dx = bPosX - food.getPosition().x();
            double dy = bPosY - food.getPosition().y();

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 0 && distance < 100) {
                centerX += food.getPosition().x();
                centerY += food.getPosition().y();
                count++;
            }
        }

        if (count > 0) {
            centerX /= count;
            centerY /= count;

            double steerX = centerX - boid.getX();
            double steerY = centerY - boid.getY();

            double magnitude = Math.sqrt(steerX * steerX + steerY * steerY);
            if (magnitude > 0) {
                steerX = (steerX / magnitude) * 2.0;
                steerY = (steerY / magnitude) * 2.0;

                steerX -= boid.getVx();
                steerY -= boid.getVy();

                double force = Math.sqrt(steerX * steerX + steerY * steerY);
                if (force > 0.03) {
                    steerX = (steerX / force) * 0.03;
                    steerY = (steerY / force) * 0.03;
                }

                return new Vector2D(steerX * weights.cohesion(), steerY * weights.cohesion());
            }
        }

        return Vector2D.ZERO;
    }

    public static List<Food> getFoods() {
        return foods;
    }
       
}
