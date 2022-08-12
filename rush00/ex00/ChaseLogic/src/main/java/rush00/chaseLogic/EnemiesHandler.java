package rush00.chaseLogic;

import java.util.ArrayList;

public class EnemiesHandler {
    private final ArrayList<Enemy> enemies;

    public EnemiesHandler(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void prodMoves(int[][] map, int playerX, int playerY) {
        ArrayList<Thread> th = new ArrayList<>();
        int i = 0;
        for (Enemy enemy : enemies) {
            th.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    enemy.setMap(searchRoot(enemy, map, playerX, playerY));
                }
            }));
            th.get(i).start();
            i++;
        }
        i = 0;
        for (Thread thread: th){
            try {
                th.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        for (Enemy enemy : enemies) {
            makeMove(enemy, enemy.getMap(), playerX, playerY, map);
        }
    }

    public void devMoves(int[][] map, int playerX, int playerY, int index) {
        int[][] mapEnemy = searchRoot(enemies.get(index), map, playerX, playerY);
        makeMove(enemies.get(index), mapEnemy, playerX, playerY, map);
    }

    private int[][] searchRoot(Enemy enemy, int[][] map, int playerX, int playerY){
        int[][] root = new int[map.length][map.length];
        for (int i = 0; i < map.length; i++){
            for (int h = 0; h < map.length; h++){
                root[i][h] = map[i][h];
            }
        }
        int f = 0;
        int c = enemy.getX();
        int i = enemy.getY();
        if(i + 1 < root[i].length &&  root[i + 1][c] == 0)
            root[i + 1][c] = -1;
        if(c + 1 < root[c].length &&  root[i][c + 1] == 0)
            root[i][c + 1] = -1;
        if(i - 1 >= 0 &&  root[i - 1][c] == 0)
            root[i - 1][c] = -1;
        if(c - 1 >= 0 &&  root[i][c - 1] == 0)
            root[i][c - 1] = -1;
        while (searchWay(root) > f){
            if (CheckSuccess(root, playerX, playerY)){
                cleanMap(root, playerX, playerY);
                return root;
            }
            f = searchWay(root);
            createWay(root);
        }
        return root;
    }

    private static int[] SearchPoint(int i, int size, int[][] map){
        int[] point = new int[2];
        for (int f = 0; f < size; f++) {
            for (int c = 0; c < size; c++) {
                if(map[f][c] == i) {
                    point[0] = f;
                    point[1] = c;
                    return point;
                }
            }
        }
        return null;
    }

    private static void cleanMap(int[][] root, int x, int y){
        int max = -2147483648;
        int i = y;
        int c = x;
        while (true) {
            int[] point = SearchPoint(1, root.length, root);
            if (i + 1 < root.length && root[i + 1][c] < 0) {
                if (root[i + 1][c] > max) {
                    max = root[i + 1][c];
                }
            }
            if (c + 1 < root.length && root[i][c + 1] < 0) {
                if (root[i][c + 1] > max) {
                    max = root[i][c + 1];
                }
            }
            if (i - 1 >= 0 && root[i - 1][c] < 0) {
                if (root[i - 1][c] > max) {
                    max = root[i - 1][c];
                }
            }
            if (c - 1 >= 0 && root[i][c - 1] < 0) {
                if (root[i][c - 1] > max) {
                    max = root[i][c - 1];
                }
            }
            if(i + 1 < root.length && root[i + 1][c] == max){
                root[i + 1][c] = 9;
                i = i + 1;
            } else if (c + 1 < root.length && root[i][c + 1] == max){
                root[i][c + 1] = 9;
                c = c + 1;
            } else if (i - 1 >= 0 && root[i - 1][c] == max){
                root[i - 1][c] = 9;
                i = i - 1;
            } else if (c - 1 >= 0 && root[i][c - 1] == max){
                root[i][c - 1] = 9;
                c = c - 1;
            }
            if (max == -1)
                break;
        }
        for (int k = 0; k < root.length; k++){
            for (int b = 0; b < root.length; b++){
                if(root[k][b] < 0){
                    root[k][b] = 0;
                }
            }
        }
    }

    private static boolean CheckSuccess(int[][] map, int x, int y){
        int size = map.length;
        int[] end = new int[2];
        end[0] = y;
        end[1] = x;
        if(end[0] + 1 < size && map[end[0] + 1][end[1]] < 0)
            return true;
        if(end[1] + 1 < size && map[end[0]][end[1] + 1] < 0)
            return true;
        if(end[0] - 1 >= 0 && map[end[0] - 1][end[1]] < 0)
            return true;
        if(end[1] - 1 >= 0 && map[end[0]][end[1] - 1] < 0)
            return true;
        return false;
    }

    private void createWay(int[][] map){
        for (int i = 0; i < map.length; i++){
            for (int c = 0; c < map.length; c++){
                if(i + 1 < map.length && map[i + 1][c] < 0 && map[i][c] == 0) {
                    map[i][c] = map[i + 1][c] - 1;
                }
                if(c + 1 < map.length && map[i][c + 1] < 0 &&  map[i][c] == 0) {
                    map[i][c] = map[i][c + 1] - 1;
                }
                if(i - 1 > 0 && map[i - 1][c] < 0 &&  map[i][c] == 0) {
                    map[i][c] = map[i - 1][c] - 1;
                }
                if(c - 1 > 0 && map[i][c - 1] < 0 &&  map[i][c] == 0) {
                    map[i][c] = map[i][c - 1] - 1;
                }
            }
        }
    }

    private int searchWay(int[][] map){
        int count = 0;
        for(int y = 0; y < map.length; y++){
            for (int x = 0; x < map.length; x++){
                if (map[x][y] < 0)
                    count++;
            }
        }
        return count;
    }

    private void makeMove(Enemy enemy, int[][] root, int playerX, int playerY, int[][] map) {
        if (Math.abs(enemy.getX() - playerX) == 1 && enemy.getY() == playerY ||
                Math.abs(enemy.getY() - playerY) == 1 && enemy.getX() == playerX) {
            System.out.println("Game over, you lose!");
            System.exit(0);
        }
        if(enemy.getY() + 1 < root.length && root[enemy.getY() + 1][enemy.getX()] == 9
                && map[enemy.getY() + 1][enemy.getX()] == 0){
            map[enemy.getY() + 1][enemy.getX()] = 2;
            map[enemy.getY()][enemy.getX()] = 0;
            root[enemy.getY() + 1][enemy.getX()] = 2;
            root[enemy.getY()][enemy.getX()] = 0;
            enemy.setY(enemy.getY() + 1);
        } else if (enemy.getX() + 1 < root.length && root[enemy.getY()][enemy.getX() + 1] == 9
                && map[enemy.getY()][enemy.getX() + 1] == 0){
            map[enemy.getY()][enemy.getX() + 1] = 2;
            map[enemy.getY()][enemy.getX()] = 0;
            root[enemy.getY()][enemy.getX() + 1] = 2;
            root[enemy.getY()][enemy.getX()] = 0;
            enemy.setX(enemy.getX() + 1);
        } else if (enemy.getY() - 1 >= 0 && root[enemy.getY() - 1][enemy.getX()] == 9
                && map[enemy.getY() - 1][enemy.getX()] == 0){
            map[enemy.getY() - 1][enemy.getX()] = 2;
            map[enemy.getY()][enemy.getX()] = 0;
            root[enemy.getY() - 1][enemy.getX()] = 2;
            root[enemy.getY()][enemy.getX()] = 0;
            enemy.setY(enemy.getY() - 1);
        } else if (enemy.getX() - 1 >= 0 && root[enemy.getY()][enemy.getX() - 1] == 9
                && map[enemy.getY()][enemy.getX() - 1] == 0){
            map[enemy.getY()][enemy.getX() - 1] = 2;
            map[enemy.getY()][enemy.getX()] = 0;
            root[enemy.getY()][enemy.getX() - 1] = 2;
            root[enemy.getY()][enemy.getX()] = 0;
            enemy.setX(enemy.getX() - 1);
        }
    }
}