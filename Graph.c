//Matthew Halteh
//mhalteh
//PA4

#include<stdio.h>
#include<stdlib.h>
#include"Graph.h"
#include"List.h"

//Set values to colors (keep track of values)
#define WHITE 0
#define GRAY 1
#define BLACK 3

#define INF -1
#define NIL 0

typedef struct GraphObj {
    List *ith;

    int *color;
    int *parent;
    int *distance;

    int order;
    int size;
    int source;
} GraphObj;

/*** Constructors-Destructors ***/
Graph newGraph(int n) {
    Graph G = malloc(sizeof(struct GraphObj));
    G -> ith = calloc((n+1), sizeof(List));
    G -> color = calloc((n+1), sizeof(int));
    G -> parent = calloc((n+1), sizeof(int));
    G -> distance = calloc((n+1), sizeof(int));

    for(int i = 1; i < n+1; i++) {
        G -> ith[i] = newList();
        G -> color[i] = 0;
        G -> parent[i] = NIL;
        G -> distance[i] = INF;
    }

    G -> order = n;
    G -> size = WHITE;
    G -> source = NIL;
    return G;
}


//freeGraph() frees all dynamic memory associated with the Graph
//*pG, then sets the handle *pG to NULL
void freeGraph(Graph* pG) {
    for (int i = 1; i <= (*pG) -> order; i++) {
        freeList(&((*pG) -> ith[i]));
    }

    free((*pG) -> ith);
    free((*pG) -> color);
    free((*pG) -> parent);
    free((*pG) -> distance);
    free(*pG);
    *pG = NULL;
}

/*** Access functions ***/
int getOrder(Graph G) {
    return G -> order;
}

int getSize(Graph G) {
    return G -> size;
}

int getSource(Graph G) {
    return G -> source;
}

int getParent(Graph G, int u) {
    if (u < 1 || u > getOrder(G)) {
        exit(1);
    }
    return G -> parent[u];
}

int getDist(Graph G, int u) {
    if (u < 1 || u > getOrder(G)) {
        exit(1);
    } else if (getSource(G) == NIL) {
        return INF;
    }
    return G -> distance[u];
}


//getPath() appends to the List L the vertices of a shortest
//path in G from source to u, or appends to L the
//value NIL if no such path exists. getPath() has the
//precondition getSource(G)!=NIL, so BFS() must
//be called before getPath()
void getPath(List L, Graph G, int u) {
    int source = getSource(G);
    if (u < 1 || u > getOrder(G)) {
        exit(1);
    } else if (source == NIL) {
        exit(1);
    } else if (u == source) {
        append(L, source);
    } else if (G->parent[u] == NIL) {
        append(L, NIL);
    } else {
        getPath(L, G, G->parent[u]);
        append(L, u);
    }
}



/*** Manipulation procedures ***/

void makeNull(Graph G) {
    for (int i = 1; i <= getOrder(G); i++) {
        clear(G -> ith[i]);
    }
    G->size = 0;
}

void addList(List L, int a) {
     if (length(L) == 0) {
         append(L,a);
     }
     else {
         for (moveFront(L); index(L) >= 0; moveNext(L)) {
             if (get(L) > a) {
                 insertBefore(L,a);
                 return;
             }
         }
         append(L,a);
     }
}

/*
addEdge() inserts a new edge
joining u to v, i.e. u is added to the adjacency List of v, and v to the adjacency List of u. Your program is
required to maintain these lists in sorted order by increasing labels.
*/
void addEdge(Graph G, int u, int v) {
    if(G == NULL)
    {
        printf("ERROR: addEdge()");
        exit(1);
    }
    if(u < 1 || u > getOrder(G))
    {
        printf("ERROR: addEdge()");
        exit(1);
    }
    if(v < 1 || v > getOrder(G))
    {
        printf("ERROR: addEdge()");
        exit(1);
    }
    List A = G->ith[u];
    List B = G->ith[v];

    addList(A, v);
    addList(B, u);
    G->size++;
    // addArc(G, u, v);
    // addArc(G, v, u);
    // G->size--;
}


//Used for addEdge and addArc
/* PSUEDOCODE
     check if length of list = 0
         if true append (list,int)
     else create a loop to loop through the list to check if the index is = -1
         get the element in the list
         compare the element to the integer you passed
         if element < int
             insert before
             return
         append(list,int)

     g parent[v] = u
     append(g ith[u],v)
     gsize ++
*/


/*
addArc() inserts a new directed edge
from u to v, i.e. v is added to the adjacency List of u (but not u to the adjacency List of v). Both addEdge()
and addArc() have the precondition that their two int arguments must lie in the range 1 to getOrder(G).
*/
void addArc(Graph G, int u, int v) {
    if(G == NULL)
    {
        printf("ERROR: addArc()");
        exit(1);
    }
    if(u < 1 || u > getOrder(G))
    {

        printf("ERROR: addArc()");
        exit(1);
    }
    if(v < 1 || v > getOrder(G))
    {
        printf("ERROR: addArc()");
        exit(1);
    }
    addList(G->ith[u], v);
    G->size++;
}


/* PSEUDOCODE
for all X E √-{s}
    color[x] = w
    P[x] = NIL
    d[x] = INF
color[s] = g
d[s] = o
P[s] = NIL
Q = 0
Enqueue(Q,s)
while Q != 0
    x = Dequeue(Q)
    for all y E adj[x]
        if color[y] == w
            color[y] = g
            d[y] = d[x] + 1
            P[y] = x
            Enqueue(Q,y)
    color[x] = b
*/
void BFS(Graph G, int s) {
    for (int i = 1; i <= (G -> order); i++) {
        G -> color[i] = WHITE;
        G -> distance[i] = INF;
        G -> parent[i] = NIL;
    }

    G -> source = s;
    G -> color[s] = GRAY;
    G -> distance[s] = 0;
    G -> parent[s] = NIL;

    //Create queue
    List Q = newList();

    //Enqueue
    append(Q,s);

    while (length(Q) > 0) {
        //Dequeue
        int x = front(Q);
        deleteFront(Q);

        //for (int y = 1; y <  ) { //EDIT PARAMETER OF FOR LOOP HERE
        List next = G -> ith[x];
        for(moveFront(next); index(next) != -1; moveNext(next)) {
            int y = get(next);
            if (G -> color[y] == 0) {
                G -> color[y] = GRAY;
                G -> distance[y] = G -> distance[x] + 1;
                G -> parent[y] = x;
                append(Q,y);
            }
            //moveNext(Q);
        }
        G -> color[x] = BLACK;
    }
    freeList(&Q);
}


/*** Other operations ***/


void printGraph(FILE* out, Graph G) {
    int i;
    for (i = 1; i <= (G -> order); i++) {
        fprintf(out,"%d: ", i++);
        printList(out, G -> ith[i]);
        fprintf(out, "\n");
    }
}
