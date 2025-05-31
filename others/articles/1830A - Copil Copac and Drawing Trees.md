Copil Copac received a list of $n - 1$ edges representing a tree with $n$ vertices. He wants to draw the tree using the following algorithm:

- **Step 0**: Draw the first vertex (vertex $1$). Proceed to Step 1.
- **Step 1**: Iterate through each edge in the order they appear in the input. If an edge connects a drawn vertex $u$ with an undrawn vertex $v$, then draw vertex $v$ and the edge between them. After checking all edges, go to Step 2.
- **Step 2**: If all vertices have been drawn, terminate the algorithm. Otherwise, return to Step 1.

The number of **readings** is defined as the number of times Step 1 is executed by Copil Copac.

You are required to determine how many readings are necessary to complete the drawing of the tree.

**Simplified Interpretation:**

You are given a tree with edges in a fixed input order. Starting from vertex 1, at each reading (i.e., one full iteration through the edge list), you can only draw an edge and its corresponding undrawn vertex if one of its endpoints has already been drawn. However, you can only do this effectively if the edge appears *after* the edge used to reach the parent node; otherwise, an additional reading is required. The goal is to compute the **minimum number of readings** needed to fully draw the tree.

**Approach Summary (as in the code):**

The solution is similar to a tree-based dynamic programming problem. A depth-first search is used to traverse the tree. For each node, we record how many readings were required to reach it. If the edge used to reach the child node comes *before* the edge used to reach the current node (i.e., out of order), an additional reading is counted. The final answer is the maximum number of readings needed among all vertices.

