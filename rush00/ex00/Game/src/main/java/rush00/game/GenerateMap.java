package rush00.game;

public class GenerateMap {

    public static int[][] MapCreate(int size, int enemy, int walls){
        int[][] map;
        if (size < 0 || enemy < 0 || walls < 0){
            throw new IllegalParametersException("Invalid input");
        }
        map = CreateMapHelp(size, enemy, walls);
        while (!checkMap(map, size))
            map = CreateMapHelp(size, enemy, walls);
        CleanWay(map, size);
        return map;
    }

    private static int[][] CreateMapHelp(int size, int enemy, int walls){
        int i = 0;
        int[][] map = new int[size][size];
        int in = (int) (Math.random() * (size * size));
        map[in / size][in % size] = 3;
        while(true){
            in = (int) (Math.random() * (size * size));
            if (map[in / size][in % size] == 0) {
                map[in / size][in % size] = 1;
                break;
            }
        }
        while(i < enemy && SearchZerro(map, size)){
            in = (int) (Math.random() * (size * size));
            if (map[in / size][in % size] == 0) {
                map[in / size][in % size] = 2;
                i++;
            }
        }
        if (i < enemy){
            throw new IllegalParametersException("Too many objects");
        }
        i = 0;
        while(i < walls && SearchZerro(map, size)){
            in = (int) (Math.random() * (size * size));
            if (map[in / size][in % size] == 0){
                map[in / size][in % size] = 4;
                i++;
            }
        }
        if (i < walls){
            throw new IllegalParametersException("Too many objects");
        }
        return map;
    }

    private static boolean SearchZerro(int[][] map, int size){
        for (int f = 0; f < size; f++) {
            for (int c = 0; c < size; c++) {
                if (map[f][c] == 0)
                    return true;
            }
        }
        return false;
    }

    private static int[] SearchPoint(int i, int size, int[][] map){
        int point[] = new int[2];
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

    private static void CleanWay(int[][] map, int size){
        for (int f = 0; f < size; f++) {
            for (int c = 0; c < size; c++) {
                if (map[f][c] == 9)
                    map[f][c] = 0;
            }
        }
    }
    private static boolean checkMap(int[][] map, int size){
        int[] player = new int[2];
        if (map == null)
            return false;

        int c = 0;
        player = SearchPoint(1, size, map);
        if (CheckSuccess(map, size, 1))
            return true;
        if(player[0] + 1 < size && player[0] + 1 > 0 && map[player[0] + 1][player[1]] == 0)
            map[player[0] + 1][player[1]] = 9;
        if(player[1] + 1 < size && player[1] + 1 > 0 && map[player[0]][player[1] + 1] == 0)
            map[player[0]][player[1] + 1] = 9;
        if(player[0] - 1 < size && player[0] - 1 > 0 && map[player[0] - 1][player[1]] == 0)
            map[player[0] - 1][player[1]] = 9;
        if(player[1] - 1 < size && player[1] - 1 > 0 && map[player[0]][player[1] - 1] == 0)
            map[player[0]][player[1] - 1] = 9;
        while (check(map,size) > c){
            c = check(map,size);
            search9(map, size);
            if(CheckSuccess(map, size, 9))
                return true;
        }
        return false;
    }

    private static void search9(int[][] map, int size){
        for (int i = 0; i < size; i++){
            for (int c = 0; c < size; c++){
                if(i + 1 < size && map[i + 1][c] == 9 &&  map[i][c] == 0)
                    map[i][c] = 9;
                if(c + 1 < size && map[i][c + 1] == 9 &&  map[i][c] == 0)
                    map[i][c] = 9;
                if(i - 1 > 0 && map[i - 1][c] == 9 &&  map[i][c] == 0)
                    map[i][c] = 9;
                if(c - 1 > 0 && map[i][c - 1] == 9 &&  map[i][c] == 0)
                    map[i][c] = 9;
            }
        }
    }

    private static boolean CheckSuccess(int[][] map, int size, int i){
        int[] end = SearchPoint(3, size, map);
        if(end[0] + 1 < size && end[0] + 1 > 0 && map[end[0] + 1][end[1]] == i)
            return true;
        if(end[1] + 1 < size && end[1] + 1 > 0 && map[end[0]][end[1] + 1] == i)
            return true;
        if(end[0] - 1 < size && end[0] - 1 > 0 && map[end[0] - 1][end[1]] == i)
            return true;
        if(end[1] - 1 < size && end[1] - 1 > 0 && map[end[0]][end[1] - 1] == i)
            return true;
        return false;
    }

    private static int check(int[][] map, int size){
        int b = 0;
        for(int i = 0; i < size; i++){
            for (int c = 0; c < size; c++){
                if (map[i][c] == 9)
                    b++;
            }
        }
        return b;
    }

}
