# Representation
| Comparison | Pros | Cons |
|------------|------|------|
| Adjacency List | easy to insert and delete with LinkedList, saves space, simple and clear to understand | slow when testing whether two vertices are adjacent |
| Adjacency Matrix | simpler to implement and adhere to | takes a lot of space and time to traverse, as it requires to go through all the vertices |
| Object & Pointers | easy to insert and delete | hard to get the full reference of the graph |

# BFS & DFS
| Comparison | Notion       | Data Structure | Backtracking | Edges on the Path | Speed  | Memory      | Optimality  |
|-----------:|:------------:|---------------:|-------------:|------------------:|-------:|------------:|------------:|
| BFS        | Vertex-based | Queue          | No           | Less (shortest)   | Slower | Inefficient | Find vertex closer to the source vertex |
| DFS        | Edge-based   | Stack          | Yes          | More              | Faster | Efficient   | Find vertex further from the source vertex |

# Algorithms
## BFS = DFS = Topological Sort = Strongly Connected Components = O(V+E)
<img width="264" alt="image" src="https://user-images.githubusercontent.com/84046974/191387067-fd600e00-2150-43be-a850-168cb01466de.png" align="left">
<img width="563" alt="image" src="https://user-images.githubusercontent.com/84046974/191387187-26747843-3b1c-470a-a415-91ac629b1ab6.png" align="right">

<img width="484" alt="image" src="https://user-images.githubusercontent.com/84046974/191387455-4ceafada-57ea-414f-b68a-1532b45db95c.png">

<img width="507" alt="image" src="https://user-images.githubusercontent.com/84046974/191387561-9cd3f9ca-b233-4322-a49c-e61dcdc21b0a.png" align="left">
<img width="209" alt="image" src="https://user-images.githubusercontent.com/84046974/191605385-91c1545e-fd65-4e01-bdf7-a3bc33a29176.png" aligh="right">


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
### Bellman-Ford = O(V<sup>4</sup>)

### Floyd-Warshall = Θ(V<sup>3</sup>) -- DP, connected or disconnected, faster than Dijkstra in dense graphs
* can be used to detect negative cycles: check the nodes distance from itself, should be 0, but will be negative
<img width="361" alt="image" src="https://user-images.githubusercontent.com/84046974/191389061-90cc56d6-8211-4ce8-a020-7593ad0bf570.png">

### Johnson = O(V<sup>2</sup>logV + VE) -- reweighting best for sparse graphs
<img width="699" alt="image" src="https://user-images.githubusercontent.com/84046974/191389539-b8c2b305-dc29-478c-b23e-bd7f2cac5813.png">

## Maximum Flow
### Supersource + Supersink
<img width="365" alt="image" src="https://user-images.githubusercontent.com/84046974/191389651-dfd254cf-7a4f-4c26-9b34-02eb5a1e7d76.png">

### Ford-Fulkerson = O(|max-flow|*E)
* Proof of Correctness
  * Termination - Each iteration increases the throughput of the flow by an integer & The sum of the capacities on the edges out of s is finite
  * Optimality - Let S = {v | v is reachable from s in G<sub>f</sub>}, T = V - S. We form a cut c(S,T) as s ∈ S, t ∈ T, S ∪ T = V and S ∩ T = Ø, where all edges leaving S are completely saturated and entering 0 flow. Then, we must have |f| = f<sub>out</sub>(S) - f<sub>in</sub>(S) = f<sub>out</sub>(S) = c(S,T)
<img height="260" alt="image" src="https://user-images.githubusercontent.com/84046974/199741637-d70def0c-f0c7-4ace-a3cb-ae69b98a414a.png" align="left">
<img height="105" alt="image" src="https://user-images.githubusercontent.com/84046974/191389716-ae94f1d5-2ecd-4a34-8b70-ba639b2786f1.png">
<img height="105" alt="image" src="https://user-images.githubusercontent.com/84046974/199737174-054c4282-7892-4080-8397-43bd4fa87f25.png">
  
### Edmonds-Karp = O(VE) * O(V+E) = O(VE<sup>2</sup>)
* Use BFS instead of DFS to avoid potential very long path (not ideal) -> find the shortest-length path
<img width="608" alt="image" src="https://user-images.githubusercontent.com/84046974/199742799-f529f925-7887-4ecb-b43a-9b7362e5f77a.png">
<img width="637" alt="image" src="https://user-images.githubusercontent.com/84046974/199743891-93fdae60-9cb9-48f7-b9d5-16228594b3eb.png">

### Dinic = O(V<sup>2</sup>E)
<img height="400" alt="image" src="https://user-images.githubusercontent.com/84046974/199747196-61271a0c-9e80-4488-9dca-d78c3addad2e.png" align="left">
<img width="460" alt="image" src="https://user-images.githubusercontent.com/84046974/199744663-0f0e586b-12f1-4149-967f-e9137bbe0aaa.png">
<img height="225" alt="image" src="https://user-images.githubusercontent.com/84046974/199747392-9b7e8339-a49f-4160-af3b-00db1e97a961.png">

## Maximum Bipartite Matching
* maximum matching in a bipartite graph == minimum vertex cover (in polynomial time)
<img width="787" alt="image" src="https://user-images.githubusercontent.com/84046974/191390872-9c20e879-04fc-4a77-8886-ef321cd6fbf8.png">
