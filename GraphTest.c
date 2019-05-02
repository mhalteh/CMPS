//Matthew Halteh
//mhalteh
//PA4

#include <stdio.h>
#include"List.h"
#include"Graph.h"

void testA(void) {
    Graph A = newGraph(100);

    if (getSize(A) != 0) {
        return 1;
    }
    addArc(A, 21, 1);
    addArc(A, 21, 2);
    addArc(A, 21, 3);
    addArc(A, 1, 21);
    addArc(A, 1, 22);
    if (getSize(A) != 5) {
        return 2;
    }
    BFS(A, 32);
    if (getSize(A) != 5) {
        return 3;
    }
    addArc(A, 22, 1);
    if (getSize(A) != 6) {
        return 4;
    }
    return 0;
}

void testB(void) {
    if (getSource(A) != NIL) {
        return 1;
    }
    BFS(A, 11);
    if (getSource(A) != 11) {
        return 2;
    }
    BFS(A, 22);
    if (getSource(A) != 22) {
        return 3;
    }
    return 0;
}

void testC(void) {
    addArc(A, 41, 4);
    addArc(A, 41, 3);
    addArc(A, 11, 2);
    addArc(A, 2, 41);
    addArc(A, 4, 2);
    addArc(A, 3, 11);
    BFS(A, 11);
    if (getParent(A, 11) != NIL) {
        return 2;
    }
    if (getParent(A, 1) != 11) {
        return 3;
    }
    if (getParent(A, 5) != NIL) {
        return 4;
    }
    return 0;
}

void testD(void) {
    addArc(A, 37, 4);
    addArc(A, 37, 3);
    addArc(A, 30, 2);
    addArc(A, 2, 23);
    addArc(A, 4, 2);
    addArc(A, 3, 30);
    BFS(A, 37);
    if (getDist(A, 64) != 0) {
        return 2;
    }
    if (getDist(A, 2) != 2) {
        return 3;
    }
    BFS(A, 4);
    if (getDist(A, 30) != 4) {
        return 4;
    }
    if (getDist(A, 23) != INF) {
        return 5;
    }
    BFS(A, 99);
    if (getDist(A, 37) != INF) {
        return 6;
    }
    return 0;
}
