package Pathfinding.Visualzer.V4;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class Driver {
    private static int rows = 100, columns = 100;
    private static int startY = 20, startX = 20, goalY = 10, goalX = 10;
    private static Node startNode, goalNode;

    private static Node[][] nodes = new Node[rows][columns];

    public static void main(String[] args) throws InterruptedException {
        JFrame f = new JFrame();
        f.setLayout(new GridLayout(rows, columns));

        Node startNode = new Node();
        startNode.fromNode = null;
        PriorityQueue<Node> unsettled = new PriorityQueue<>();

        for (int yy = 0; yy < rows; yy++) {
            for (int xx = 0; xx < columns; xx++) {
                nodes[yy][xx] = new Node();
                Node n = nodes[yy][xx];

                if (xx == startX && yy == startY) { // if starting node
                    startNode = n;
                    n.distance = 0;
                    startNode.cell.setBackground(Color.RED);
                    unsettled.add(startNode);
                    System.out.println("Added starting node.");
                } else {
                    if (xx == goalX && yy == goalY) {
                        goalNode = n;
                        goalNode.cell.setBackground(Color.CYAN);
                    } else {
                        n.cell.setBackground(Color.WHITE);
                    }
                    
                    n.distance = Integer.MAX_VALUE;
                }
                n.settled = false;
                n.x = xx;
                n.y = yy;
                n.cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                f.add(n.cell);
            }
        }
        f.setSize(1000, 1000);
        f.setVisible(true);
        System.out.println("Finished filling grid...");
        Thread.sleep(5000);

        Node finalNode = null;
        while (unsettled.size() > 0 && !goalNode.settled) {
            Node curNode = unsettled.remove();
            ArrayList<Node> neighbors = getNeighbors(curNode);

            for (Node neighbor : neighbors) {
                int newDistance = neighbor.cost + curNode.distance;
                if (neighbor.distance < newDistance) {
                    neighbor.distance = newDistance;
                    neighbor.fromNode = curNode;
                }
                if (!neighbor.settled) {
                    unsettled.add(neighbor);
                }
            }

            finalNode = curNode;
            curNode.cell.setBackground(Color.BLACK);
            System.out.println("Finished node: " + curNode.x + ", " + curNode.y);
            unsettled.remove(curNode);
            curNode.settled = true;
            Thread.sleep(20);
        }
        System.out.println("COMPLETE!");

        Node tempNode = finalNode;
        while (tempNode != null && !(tempNode.x == startX && tempNode.y == startY)) {
            tempNode.cell.setBackground(Color.YELLOW);
            System.out.println(tempNode.x + ", " + tempNode.y);
            tempNode = tempNode.fromNode;
        }
        System.out.println("Drew path.");
    }

    private static ArrayList<Node> getNeighbors(Node n) {
        ArrayList<Node> neighbors = new ArrayList<Node>();

        for (int xx = -1; xx < 1; xx++) {
            for (int yy = -1; yy < 1; yy++) {
                if (xx == 0 && yy == 0) continue; // if it is the node we are testing, ignore it
                if (isOnMap(n.x + xx, n.y + yy)) {
                    if (Math.abs(xx) == 1 && Math.abs(yy) == 1) { // Corner
                        nodes[n.y + yy][n.x + xx].cost = 14;
                    } else { // Edge (horizontal/vertical)
                        nodes[n.y + yy][n.x + xx].cost = 10;
                    }

                    neighbors.add(nodes[n.y + yy][n.x + xx]);
                }
            }
        }

        return neighbors;
    }

    private static boolean isOnMap(int x, int y) {
        return x >= 0 && y >= 0 && x < columns && y < rows;
    }
}