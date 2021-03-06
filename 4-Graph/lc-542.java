class Solution {
    public int[][] updateMatrix_(int[][] grid, int n, int m, boolean[][] vis) {       
        LinkedList<Integer> que = new LinkedList<>();
        int[][] dir = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(grid[i][j] == 0) {
                    vis[i][j] = true;
                    que.addLast(i * m + j);
                }
            }
        }
        
        while (que.size() != 0) {
            int size = que.size();
            while (size-- > 0) {
                int idx = que.removeFirst();
                int sr = idx / m, sc = idx % m;
                 
                for (int d = 0; d < 4; d++) {
                    int r = sr + dir[d][0];
                    int c = sc + dir[d][1];

                    if (r >= 0 && c >= 0 && r < n && c < m && !vis[r][c]) {  
                        vis[r][c] = true; 
                        grid[r][c] = grid[sr][sc] + 1;
                        que.addLast(r * m + c);
                    }
                }
            }
        }
        return grid;
    }
    
    
    public int[][] updateMatrix(int[][] mat) {
        int n = mat.length, m = mat[0].length;
        if(n == 0 || m == 0)
            return mat;
        boolean[][] vis = new boolean[n][m];
        return updateMatrix_(mat, n, m, vis);
    }
}