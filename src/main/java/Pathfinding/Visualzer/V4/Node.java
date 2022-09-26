package Pathfinding.Visualzer.V4;

import javax.swing.JPanel;
import java.awt.Dimension;

public class Node implements Comparable<Node> {
    public Node fromNode;
    public int distance, cost;
    public int x, y;
    public boolean settled;

    public JPanel cell = new JPanel() {
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1, 1);
        }
    };

    public Node() {

    }

    @Override
    public int compareTo(Node n) {
        if (distance < n.distance) return -1;
        if (distance > n.distance) return 1;
        return 0;
    }
}