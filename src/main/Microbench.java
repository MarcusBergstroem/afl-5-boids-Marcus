package main;

import main.model.BoidType;
import main.simulation.FlockSimulation;
import main.spatial.*;

public class Microbench {
    public static void main(String[] args) {
        System.out.println("Starting Microbench...");

        // NaiveSpatialIndex TESTS
        System.err.println("--- Testing NaiveSpatialIndex ---");
        SpatialIndex naive = new NaiveSpatialIndex();
        FlockSimulation simulation = new FlockSimulation(1200, 800);
        simulation.setSpatialIndex(naive);
        double sum = 0;
        double average = 0;
        double base = 150;

        // Tester op til 750 standard boids
        for (int n = 1; n < 6; n++) {
            double boidCount = n * base;
            for (int i = 0; i < boidCount; i++) {
                simulation.addBoid(BoidType.STANDARD);
            }

            for (int i = 0; i < 1000; i++) {
                simulation.update();
                sum += simulation.getLastIterationTimeMs();    
            }

            average = sum / 1000.0;
            sum = 0;
            System.out.printf("STANDARD BOIDS - Average iteration time with NaiveSpatialIndex at %.0f boids: %.3f ms%n", boidCount, average);
            average = 0;
        }

        simulation.setBoidCount(0);

        // Tester op til 750 50/50 Standard Avoids boids
        for (int n = 1; n < 6; n++) {
            double boidCount = n * base;
            for (int i = 0; i < boidCount/2; i++) {
                simulation.addBoid(BoidType.STANDARD);
            }
            for (int i = 0; i < boidCount/2; i++) {
                simulation.addBoid(BoidType.AVOIDS);
            }

            for (int i = 0; i < 1000; i++) {
                simulation.update();
                sum += simulation.getLastIterationTimeMs();    
            }

            average = sum / 1000.0;
            sum = 0;
            System.out.printf("STANDARD 50/50 AVOIDS - Average iteration time with NaiveSpatialIndex at %.0f boids: %.3f ms%n", boidCount, average);
            average = 0;
        }     
        
        simulation.setBoidCount(0);

        // Tester op til 750 33/33733 Standard Avoids Seeks boids
        for (int n = 1; n < 6; n++) {
            double boidCount = n * base;
            for (int i = 0; i < boidCount/3; i++) {
                simulation.addBoid(BoidType.STANDARD);
            }
            for (int i = 0; i < boidCount/3; i++) {
                simulation.addBoid(BoidType.AVOIDS);
            }
            for (int i = 0; i < boidCount/3; i++) {
                simulation.addBoid(BoidType.SEEKS);
            }
            for (int i = 0; i < 1000; i++) {
                simulation.update();
                sum += simulation.getLastIterationTimeMs();    
            }

            average = sum / 1000.0;
            sum = 0;
            System.out.printf("STANDARD SEEKS AVOIDS 33/33/33 - Average iteration time with NaiveSpatialIndex at %.0f boids: %.3f ms%n", boidCount, average);
            average = 0;
        }    
        simulation.setBoidCount(0);
        
        System.out.println("----- Finished Naive -----\n");
        
        // KDTreeSpatialIndex TESTS

        SpatialIndex kdTree = new KDTreeSpatialIndex();
        simulation.setSpatialIndex(kdTree);
        System.out.println("--- Testing KDTreeSpatialIndex ---");

        // Tester op til 750 Standard boids
        for (int n = 1; n < 6; n++) {
            double boidCount = n * base;
            for (int i = 0; i < boidCount; i++) {
                simulation.addBoid(BoidType.STANDARD);
            }
            for (int i = 0; i < 1000; i++) {
                simulation.update();
                sum += simulation.getLastIterationTimeMs();    
            }

            average = sum / 1000.0;
            sum = 0;
            System.out.printf("STANDARD - Average iteration time with KDtTree at %.0f boids: %.3f ms%n", boidCount, average);
            average = 0;
            simulation.setBoidCount(0);
        }

        // Tester op til 750 Standard Avoid boids
        for (int n = 1; n < 6; n++) {
            double boidCount = n * base;
            for (int i = 0; i < boidCount/2; i++) {
                simulation.addBoid(BoidType.STANDARD);
            }
            for (int i = 0; i < boidCount/2; i++) {
                simulation.addBoid(BoidType.AVOIDS);
            }
            for (int i = 0; i < 1000; i++) {
                simulation.update();
                sum += simulation.getLastIterationTimeMs();    
            }

            average = sum / 1000.0;
            sum = 0;
            System.out.printf("STANDARD 50/50 AVOIDS - Average iteration time with KDtTree at %.0f boids: %.3f ms%n", boidCount, average);
            average = 0;
            simulation.setBoidCount(0);
        }


        // Tester op til 750 Seek boids
        for (int n = 1; n < 6; n++) {
            double boidCount = n * base;
            for (int i = 0; i < boidCount; i++) {
                simulation.addBoid(BoidType.SEEKS);
            }
            for (int i = 0; i < 1000; i++) {
                simulation.update();
                sum += simulation.getLastIterationTimeMs();    
            }

            average = sum / 1000.0;
            sum = 0;
            System.out.printf("SEEKS - Average iteration time with NaiveSpatialIndex at %.0f boids: %.3f ms%n", boidCount, average);
            average = 0;
            simulation.setBoidCount(0);
        }
    }
}