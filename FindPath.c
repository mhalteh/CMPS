//Matthew Halteh
//mhalteh
//PA4

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"List.h"
#include"Graph.h"

#define MAX_LEN 160

int main(int argc, char* argv[]) {
    FILE *input, *output;

    char line[MAX_LEN];

    if (argc != 3) {
        printf("Not right amount of args");
        exit(1);
    }

    input = fopen(argv[1], "r");
    output = fopen(argv[2], "w");

    if (input == NULL) {
        printf("Input file is empty");
        exit(1);
    }

    int a;
    fscanf(input,"%d",&a);
    Graph G = newGraph(a);
}
