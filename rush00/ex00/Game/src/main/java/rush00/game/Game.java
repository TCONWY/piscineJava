package rush00.game;

import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Game {
    public static void main(String[] args) throws IOException, InterruptedException {
        Arguments jargs = new Arguments();
        JCommander jCommander = JCommander.newBuilder().addObject(jargs).build();
        jCommander.parse(args);
        String profile = jargs.getProfile();
        FieldPainter fieldPainter = new FieldPainter("../src/main/resources/application-" + profile + ".properties");
        int[][] field = GenerateMap.MapCreate(jargs.getSize(), jargs.getEnemiesCount(), jargs.getWallsCount());
        MovesHadler movesHadler = new MovesHadler(field);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int move = 0;
            boolean legalMove = false;
            fieldPainter.drawField(movesHadler.getMap());
            while (!legalMove) {
                String line = scanner.nextLine();
                if (line.length() == 1){
                    move = line.charAt(0);
                    legalMove = movesHadler.playerMove(move);
                }
                else{
                    System.out.println("invalid input, press w,a,s,d to continue or 9 to give up");
                }
            }
            fieldPainter.drawField(movesHadler.getMap());
            if (movesHadler.checkWin()) {
                System.out.println("You win! Congratulations!");
                break;
            }
            sleep(500);
            movesHadler.enemiesMoves(jargs.getProfile(), fieldPainter, scanner);
        }
    }
}
