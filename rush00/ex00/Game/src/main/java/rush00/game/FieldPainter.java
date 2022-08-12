package rush00.game;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

import java.io.IOException;
import java.util.HashMap;

public class FieldPainter {
    private final ColoredPrinter coloredPrinter;

    private final char enemyChar;
    private final char playerChar;
    private final char wallChar;
    private final char goalChar;
    private final char emptyChar;
    private final Ansi.BColor enemyColor;
    private final Ansi.BColor playerColor;
    private final Ansi.BColor wallColor;
    private final Ansi.BColor goalColor;
    private final Ansi.BColor emptyColor;

    public FieldPainter(String propetriesFile) {
        coloredPrinter = new ColoredPrinter.Builder(1, false).foreground(Ansi.FColor.BLACK).build();
        HashMap<String, String> properties = Parser.parseFile(propetriesFile);
        this.enemyChar = properties.get("enemy.char").charAt(0);
        this.playerChar = properties.get("player.char").charAt(0);
        this.wallChar = properties.get("wall.char").charAt(0);
        this.goalChar = properties.get("goal.char").charAt(0);
        this.emptyChar = properties.get("empty.char").charAt(0);
        this.enemyColor = Ansi.BColor.valueOf(properties.get("enemy.color"));
        this.playerColor = Ansi.BColor.valueOf(properties.get("player.color"));
        this.wallColor = Ansi.BColor.valueOf(properties.get("wall.color"));
        this.goalColor = Ansi.BColor.valueOf(properties.get("goal.color"));
        this.emptyColor = Ansi.BColor.valueOf(properties.get("empty.color"));

    }

    public void drawField(int[][] field) throws IOException {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                switch (field[i][j]) {
                    case 0:
                        coloredPrinter.setBackgroundColor(emptyColor);
                        coloredPrinter.print(emptyChar);
                        break;
                    case 1:
                        coloredPrinter.setBackgroundColor(playerColor);
                        coloredPrinter.print(playerChar);
                        break;
                    case 2:
                        coloredPrinter.setBackgroundColor(enemyColor);
                        coloredPrinter.print(enemyChar);
                        break;
                    case 3:
                        coloredPrinter.setBackgroundColor(goalColor);
                        coloredPrinter.print(goalChar);
                        break;
                    case 4:
                        coloredPrinter.setBackgroundColor(wallColor);
                        coloredPrinter.print(wallChar);
                        break;
                }
            }
            System.out.println();
        }
    }
}
