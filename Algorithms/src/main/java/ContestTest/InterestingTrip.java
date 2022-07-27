package ContestTest;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class InterestingTrip {

    static int citiesN;
    static int[][] matrix;
    static int maxDistance;
    static int ttlRoadsCount;
    static int startCity; // Integer.parseInt(tokens[0]) - 1;
    static int endCity;   // Integer.parseInt(tokens[1]) - 1;
    static boolean[] visited;
    static int minRoadsToResult = -1;

    public static void interestingTrip() {

        citiesN = 7;// Integer.parseInt(br.readLine());
        int[] x = new int[citiesN];
        int[] y = new int[citiesN];


        x[0] = 0;
        y[0] = 0;
        x[1] = 0;
        y[1] = 2;
        x[2] = 2;
        y[2] = 2;
        x[3] = 0;
        y[3] = -2;
        x[4] = 2;
        y[4] = -2;
        x[5] = 2;
        y[5] = -1;
        x[6] = 2;
        y[6] = 1;

        /*x[0] = 0;y[0] = 0;
        x[1] = 2;y[1] = 0;
        x[2] = 0;y[2] = 2;
        x[3] = 2;y[3] = 2;*/

        maxDistance = 2;
        startCity = 7 - 1; // Integer.parseInt(tokens[0]) - 1;
        endCity = 1 - 1;   // Integer.parseInt(tokens[1]) - 1;

        //swap(x, y, 0, startCity);
        //swap(x, y, x.length - 1, endCity);

        matrix = new int[citiesN][citiesN];
        for (int i = 0; i < citiesN; i++) {
            for (int j = 0; j < citiesN; j++) {
                int distance = Math.abs(x[j] - x[i]) + Math.abs(y[j] - y[i]);
                if (distance <= maxDistance && i != j) {
                    matrix[i][j] = 1;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }

        if (matrix[startCity][endCity] == 1) {
            System.out.println(1);
            return;
        }

        if (citiesN <= startCity || citiesN <= endCity){
            System.out.println(-1);
        }

        System.out.println(BFS(startCity));
    }

    /*private static void swap(int[] x, int[] y, int source, int startCity) {
        int temp = x[source];
        x[source] = x[startCity];
        x[startCity] = temp;

        temp = y[source];
        y[source] = y[startCity];
        y[startCity] = temp;
    }*/

    static int BFS(int start) {
        // Visited vector to so that
        // a vertex is not visited more than once
        // Initializing the vector to false as no
        // vertex is visited at the beginning
        visited = new boolean[citiesN];
        Arrays.fill(visited, false);

        int[] minRoads = new int[citiesN];
        //очередь по которой бегаем до конца и назад если не дошли куда надо и ищем новые ответвления, очеред в каждый момент времени содержит текущий путь
        Queue<Integer> q = new PriorityQueue<>();
        q.add(start);
        ttlRoadsCount++;

        // Set source as visited
        visited[start] = true;

        int nextToVis;
        int sameDistTrackBack = 0;
        while (!q.isEmpty()) {
            int vis = q.remove();
            if (sameDistTrackBack > 0){
                sameDistTrackBack--;
            } else ttlRoadsCount--;


            sameDistTrackBack = 0;
            //начиная с некой вершины ищем новые ответвления, которые не посещали
            while ((nextToVis = getAdjUnvisitedVertex(vis)) != -1) {
                visited[nextToVis] = true;
                q.add(nextToVis);
                if (sameDistTrackBack == 0) {
                    ttlRoadsCount++;
                    sameDistTrackBack++;
                } else sameDistTrackBack++;
                //проверяем, если дошли до цели
                if (nextToVis == endCity) {
                    if (minRoadsToResult == -1 || minRoadsToResult > ttlRoadsCount) minRoadsToResult = ttlRoadsCount;
                }
            }
        }
        return minRoadsToResult;
    }

    private static int getAdjUnvisitedVertex(int v) {
        for (int j = 0; j < citiesN; j++) {
            if (matrix[v][j] == 1 && visited[j] == false) {
                return j; //возвращает первую найденную вершину
            }
        }
        return -1;
    }
}
