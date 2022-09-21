# BFS & DFS
| Comparison | Notion       | Data Structure | Backtracking | Edges on the Path | Speed  | Memory      | Optimality  |
|-----------:|:------------:|---------------:|-------------:|------------------:|-------:|------------:|------------:|
| BFS        | Vertex-based | Queue          | No           | Less (shortest)   | Slower | Inefficient | Find vertex closer to the source vertex |
| DFS        | Edge-based   | Stack          | Yes          | More              | Faster | Efficient   | Find vertex further from the source vertex |

# Algorithms
<img width="264" alt="image" src="https://user-images.githubusercontent.com/84046974/191387067-fd600e00-2150-43be-a850-168cb01466de.png" align="left">
<img width="563" alt="image" src="https://user-images.githubusercontent.com/84046974/191387187-26747843-3b1c-470a-a415-91ac629b1ab6.png" align="right">

<img width="484" alt="image" src="https://user-images.githubusercontent.com/84046974/191387455-4ceafada-57ea-414f-b68a-1532b45db95c.png">
<img width="507" alt="image" src="https://user-images.githubusercontent.com/84046974/191387561-9cd3f9ca-b233-4322-a49c-e61dcdc21b0a.png">

## MST - Kruskal - Prim
### Kruskal = O(ElogV) -- Greedy, edge dominated, better for sparse graphs
<img width="465" alt="image" src="https://user-images.githubusercontent.com/84046974/191387862-df887a42-1412-4ae8-8cd9-542ff170c946.png">

### Prim = O(E+VlogV) -- Greedy, vertex dominated, better for dense graphs
<img width="287" alt="image" src="https://user-images.githubusercontent.com/84046974/191387949-6aa07ad2-9861-47bf-907e-909a254f5162.png">

## Single-Source Shortest Path
### Bellman-Ford = O(VE) -- DP, negative allowed but no negative cycles, slower, directed only, checks all paths
<img width="589" alt="image" src="https://user-images.githubusercontent.com/84046974/191388505-0fd2b4eb-76ce-4ef2-acf4-5bc209129002.png">

### DAG (Topological Sort) = O(V+E) -- Greedy, negative cycles allowed, use DFS to get the path
<img width="366" alt="image" src="https://user-images.githubusercontent.com/84046974/191388590-e535061f-4842-446f-9065-74ca6d8a9ae7.png">

### Dijkstra = O(ElogV) -- Greedy, non-negative, faster, directed or undirected
<img width="264" alt="image" src="https://user-images.githubusercontent.com/84046974/191388626-3f346366-30f5-4144-ae63-c2999abba664.png">

## All-Pairs Shortest Path
### Bellman-Ford = O(V^4)

### Floyd-Warshall = Θ(V^3) -- DP, connected or disconnected, faster than Dijkstra in dense graphs
* can be used to detect negative cycles: check the nodes distance from itself, should be 0, but will be negative
<img width="361" alt="image" src="https://user-images.githubusercontent.com/84046974/191389061-90cc56d6-8211-4ce8-a020-7593ad0bf570.png">

### Johnson = O(V^2logV + VE) -- reweighting best for sparse graphs
<img width="699" alt="image" src="https://user-images.githubusercontent.com/84046974/191389539-b8c2b305-dc29-478c-b23e-bd7f2cac5813.png">

## Maximum Flow
### Supersource + Supersink
<img width="365" alt="image" src="https://user-images.githubusercontent.com/84046974/191389651-dfd254cf-7a4f-4c26-9b34-02eb5a1e7d76.png">

### Ford-Fulkerson = O(|max-flow|*E)
<img width="460" alt="image" src="https://user-images.githubusercontent.com/84046974/191389716-ae94f1d5-2ecd-4a34-8b70-ba639b2786f1.png">

### Edmonds-Karp = O(VE) * O(V+E) = O(VE^2)
* Use BFS instead of DFS to avoid potential very long path (not ideal) -> find the shortest-length path
