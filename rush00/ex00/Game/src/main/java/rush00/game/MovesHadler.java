package rush00.game;

import rush00.chaseLogic.EnemiesHandler;
import rush00.chaseLogic.Enemy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovesHadler {
    private final int[][] map;
    private int playerX;
    private int playerY;
    private int goalX;
    private int goalY;
    private int enemiesCount;
    private EnemiesHandler enemiesHandler;

    public MovesHadler(int[][] map) {
        this.map = map;
        parseMap();
    }

    private void parseMap() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemiesCount = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (map[i][j] == 1) {
                    playerX = j;
                    playerY = i;
                } else if (map[i][j] == 2) {
                    enemies.add(new Enemy(j, i));
                    enemiesCount++;
                } else if (map[i][j] == 3) {
                    goalX = j;
                    goalY = i;
                }
            }
        }
        enemiesHandler = new EnemiesHandler(enemies);
    }

    public int[][] getMap() {
        return map;
    }

    public boolean playerMove(int move) {
        switch (move) {
            case 87:
            case 119:
                if (playerY != 0 && (map[playerY - 1][playerX] == 0 || map[playerY - 1][playerX] == 3)) {
                    map[playerY - 1][playerX] = 1;
                    map[playerY][playerX] = 0;
                    playerY--;
                    return true;
                } else if(playerY != 0 && (map[playerY - 1][playerX] == 2)){
                    System.out.println("Game over, you lose!");
                    System.exit(0);
                }
                break;
            case 83:
            case 115:
                if (playerY != map.length - 1 && (map[playerY + 1][playerX] == 0 || map[playerY + 1][playerX] == 3)) {
                    map[playerY + 1][playerX] = 1;
                    map[playerY][playerX] = 0;
                    playerY++;
                    return true;
                } else if(playerY != map.length - 1 && (map[playerY + 1][playerX] == 2)){
                    System.out.println("Game over, you lose!");
                    System.exit(0);
                }
                break;
            case 97:
            case 65:
                if (playerX != 0 && (map[playerY][playerX - 1] == 0 || map[playerY][playerX - 1] == 3)) {
                    map[playerY][playerX - 1] = 1;
                    map[playerY][playerX] = 0;
                    playerX--;
                    return true;
                } else if(playerX != 0 && map[playerY][playerX - 1] == 2){
                    System.out.println("Game over, you lose!");
                    System.exit(0);
                }
                break;
            case 100:
            case 68:
                if (playerX != map[playerY].length - 1 &&
                        (map[playerY][playerX + 1] == 0 || map[playerY][playerX + 1] == 3)) {
                    map[playerY][playerX + 1] = 1;
                    map[playerY][playerX] = 0;
                    playerX++;
                    return true;
                } else if(playerX != map[playerY].length - 1 && map[playerY][playerX + 1] == 2){
                    System.out.println("Game over, you lose!");
                    System.exit(0);
                }
                break;
            case 57:
                System.out.println("Player gave up, game over!");
                System.exit(0);
            default:
                System.out.println("invalid input, press w,a,s,d to continue or 9 to give up");
                return false;
        }
        return false;
    }

    public boolean checkWin() {
        return playerX == goalX && playerY == goalY;
    }

    public void enemiesMoves(String profile, FieldPainter fieldPainter, Scanner scanner) throws IOException {
        if (profile.equals("dev")) {
            for (int i = 0; i < enemiesCount; i++) {
                System.out.println("enemy move number " + (i + 1) + "/" + enemiesCount);
                while (true) {
                    String line = scanner.nextLine();
                    if (line.equals("9")) {
                        System.out.println("Give Up");
                        System.exit(0);
                    }
                    if (line.equals("8")) {
                        break;
                    }
                    System.out.println("Invalid Input, press 8 to continue or 9 to give up");
                }
                enemiesHandler.devMoves(map, playerX, playerY, i);
                fieldPainter.drawField(map);
            }
        } else {
            enemiesHandler.prodMoves(map, playerX, playerY);
            fieldPainter.drawField(map);
        }
    }
}
