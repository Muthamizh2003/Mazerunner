package Mazerunner;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Maze {
    char[][] maze;

    public Maze(int row, int column) {
        this.maze = new char[row][column];
        create(row, column);
    }

    private void create(int row, int column) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                maze[i][j] = '0';
            }
        }
    }

    public void print() {
        for (int i = 0; i < maze.length; i++) {
            System.out.print("\n");
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j] + " ");
            }
        }
        System.out.println();
    }

    public void placemonsters() {
        Random random = new Random();
        int numMonsters = random.nextInt(maze.length * maze[0].length / 3) + 1;
        System.out.println("Number of monsters to be placed: " + numMonsters);
        int monsPlaced = 0;
        while (monsPlaced < numMonsters) {
            int randomRow = random.nextInt(maze.length);
            int randomCol = random.nextInt(maze[0].length);
            if (maze[randomRow][randomCol] == '0' && !(randomRow == 0 && randomCol == 0)
                    && !(randomRow == maze.length - 1 && randomCol == maze[0].length - 1)) {
                maze[randomRow][randomCol] = 'M';
                monsPlaced++;
            }
        }
    }

    public void placetreasure() {
        Random random = new Random();
        int randomRow, randomCol;
        boolean validPlacement;
        do {
            randomRow = random.nextInt(maze.length);
            randomCol = random.nextInt(maze[0].length);
            validPlacement = isValidTreasurePosition(randomRow, randomCol);
        } while (!validPlacement);
        maze[randomRow][randomCol] = 'T';
    }

    public boolean isValidTreasurePosition(int row, int col) {
        if (maze[row][col] != '0') return false;
        if ((row == 0 && col == 0) || (row == maze.length - 1 && col == maze[0].length - 1)) return false;
        int minDistance = (maze.length + maze[0].length) / 2;
        return Math.abs(row) + Math.abs(col) >= minDistance;
    }

    private static class Node {
        int row, col, steps;
        Node prev;

        Node(int row, int col, int steps, Node prev) {
            this.row = row;
            this.col = col;
            this.steps = steps;
            this.prev = prev;
        }
    }

    public int shortestpath(int row, int col) {
        int rowlen = maze.length;
        int collen = maze[0].length;
        boolean[][] visited = new boolean[rowlen][collen];
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<Node> queue = new LinkedList<>();
        visited[row][col] = true;
        queue.add(new Node(row, col, 0, null));
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            for (int[] direction : directions) {
                int nextRow = cur.row + direction[0];
                int nextCol = cur.col + direction[1];
                if (inboundary(nextRow, nextCol) && !visited[nextRow][nextCol] && maze[nextRow][nextCol] != 'M') {
                    if (maze[nextRow][nextCol] == 'T') {
                        shortmazepath(cur);
                        return cur.steps + 1;
                    }
                    queue.add(new Node(nextRow, nextCol, cur.steps + 1, cur));
                    visited[nextRow][nextCol] = true;
                }
            }
        }
        return -1;
    }

    private boolean inboundary(int i, int j) {
        return i >= 0 && i < maze.length && j >= 0 && j < maze[0].length;
    }

    private void shortmazepath(Node node) {
        Node cur = node;
        while (cur != null) {
            if (maze[cur.row][cur.col] != 'T') {
                maze[cur.row][cur.col] = 'P';
            }
            cur = cur.prev;
        }
        System.out.println("Maze path is:");
        print();
    }
}
