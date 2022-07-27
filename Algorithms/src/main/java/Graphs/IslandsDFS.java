package Graphs;

import java.util.HashSet;

public class IslandsDFS {
    public static int numIslands(char[][] grid) {
        HashSet<String> ones = new HashSet<>();
        int islands = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] != '0' && !ones.contains(i + "," + j))
                    islands++;
                dfs(grid, i, j, ones);           }        }
        return islands;    }

    private static void dfs(char[][] grid, int r, int c, HashSet<String> ones) {
        if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length || grid[r][c] == '0' || ones.contains(r + "," + c))
        return;
        ones.add(r + "," + c);
        dfs(grid, r + 1, c, ones);
        dfs(grid, r - 1, c, ones);
        dfs(grid, r, c + 1, ones);
        dfs(grid, r, c - 1, ones);
    }}
