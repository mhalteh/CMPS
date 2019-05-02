//Matthew Halteh
//mhalteh
//PA4

#include<stdio.h>
#include<stdlib.h>
#include"List.h"

// Structs

// Private NodeObj type
typedef struct NodeObj {
    int data;
    struct NodeObj* next;
    struct NodeObj* prev;

}
NodeObj;

// Private Node type
typedef NodeObj* Node;

// Private ListObj type
typedef struct ListObj {
    Node front;
    Node back;
    Node cursor;
    int index;
    int length;

} ListObj;

// Constructors-Destructors

// newNode()

// Creates new Node object
Node newNode(int data) {
    // Reserves memory space in memory for the size of Node objects
    Node N = malloc(sizeof(NodeObj));
    N -> data = data;
    N -> next = NULL;
    N -> prev = NULL;
    return(N);
}

// freeNode()
// Clears pointer reference from memory
void freeNode(Node* pN) {
    // Checks if pointer reference for Node is already NULL
    if(pN != NULL) {
        free(pN);
        pN = NULL;
    }
}

// newList()
// Creates new List object
List newList(void) {
    //List L;

    // Reserves memory space in memory for the size of List objects
    List L = malloc(sizeof(ListObj));
    L -> front = NULL;
    L -> back = NULL;
    L -> cursor = NULL;
    L -> length = 0;
    L -> index = -1;

    return(L);
}

// freeList()
// Method to clear List reference from memory
void freeList(List* pL) {
    if (pL != NULL && *pL != NULL) {
        clear(*pL);
        free(*pL);
        *pL = NULL;
    }
}

// Access functions

// length()
// Returns the value of the length of the List
int length(List L) {
    if (L == NULL) {
        printf("Calling the method length() on a list NULL\n");
        exit(1);
    }
    return L -> length;
}

// index()
// Returns the value of the index of List
int index(List L) {
    if (L == NULL) {
        printf("Calling the method index() on a list NULL\n");
        exit(1);
    }
    return L -> index;
}

// front()
// Returns the value of the front of List
int front(List L) {
    if (L -> front == NULL) {
        printf("Calling the method front() on a list NULL\n");
        exit(0);
    }
    else {
        return L -> front -> data;
    }
}

// back()
// Returns the value of the back of List
int back(List L) {
    if(L == NULL) {
        printf("Calling the method back() on a list NULL\n");
        exit(1);
    }

    return L -> back -> data;
}

// get()
// Returns the cursor data of List
int get(List L) {
    if(L == NULL) {
        printf("Calling the method get(List L) on a list NULL\n");
        exit(1);
    }

    return L -> cursor -> data;
}

// equals()
// Returns 1 if the two Lists are equal, returns 0
// otherwise
int equals(List A, List B) {
    int check = 1;

    // Checks if either Lists are NULL
    if(A == NULL || B == NULL) {
        printf("List Error: calling equals() on NULL List reference\n");
    }

    Node tempA = A -> front;
    Node tempB = B -> front;

    if(A -> length != B -> length) {
        return 0;
    }

    // Iterates through each Node in both Lists and compares data in them
    while(check && tempA != NULL) {
        check = (tempA -> data == tempB -> data);
        tempA = tempA -> next;
        tempB = tempB -> next;
    }

    return check;
}

//Manipulation Functions

// clear()
// Method that clears the whole List and returns it to starting state
void clear(List L) {
    if (L == NULL) {
        exit(1);
    }
    while (L->front != NULL) {
        deleteFront(L);
    }

    L -> front = NULL;
    L -> back = NULL;
    L -> cursor = NULL;

    L -> index = -1;
    L -> length = 0;
}

// moveFront()
// Moves cursor Node to front List
void moveFront(List L) {
    if (L == NULL) {
        printf("Error: 197");
        exit(1);
    }
    if (L->length > 0) {
        L -> cursor = L -> front;
        L -> index = 0;
    }
}

// moveBack()
// Move cursor Node to back List
void moveBack(List L) {
    if (L == NULL) {
        printf("Error: 197");
        exit(1);
    }
    if (L->length > 0) {
        L -> cursor = L -> back;
        L -> index = L -> length - 1;
    }
}


// if cursor is defined, moves it to the previous node if there is one
void movePrev(List L) {
  if (L == NULL) {
    exit(1);
  }
  if (L->cursor != NULL) {
    if (L->cursor != L->front) {
      L->cursor = L->cursor->prev;
      L->index = L->index - 1;
    } else {
      L->cursor = NULL;
      L->index = -1;
    }
  }
}

// if cursor is defined, moves it to the next node if there is one
void moveNext(List L) {
  if (L == NULL) {
    exit(1);
  }
  if (L->cursor != NULL) {
    if (L->cursor != L->back) {
      L->cursor = L->cursor->next;
      L->index = L->index + 1;
    }
    else {
      L->cursor = NULL;
      L->index = -1;
    }
  }
}

// prepend()
// Inserts a Node into the List. If List is not empty then insert at front of List
void prepend(List L, int data) {
    if (L == NULL) {
        printf("Calling the method prepend(List L, int data) on a list NULL\n");
        exit(1);
    }

    Node check = newNode(data);

    if(L -> length == 0) {
        L -> front = check;
        L -> back = check;
    }

    else {
        L -> front -> prev = check;
        check -> next = L -> front;
        L -> front = check;

        if (L -> index >= 0) {
            L -> index++;
        }
    }

    L -> length++;
}

// append()
// Inserts a Node into the List. If List is not empty then insert at back of List
void append(List L, int data) {
    if (L == NULL) {
        printf("Calling the method prepend(List L, int data) on a list NULL\n");
        exit(1);
    }

    Node check = newNode(data);

    if(L -> length == 0) {
        L -> front = check;
        L -> back = check;
    }

    else {
        L -> back -> next = check;
        check -> prev = L -> back;
        L -> back = check;
        //L -> index++; nicky did this
    }
    L -> length++;
}


void insertBefore(List L, int data) {
  if (L == NULL) {
    exit(1);
  }
  if (L->length == 0) {
    exit(1);
  }
  if (L->index < 0) {
    exit(1);
  }

  if (L->cursor == L->front) {
    prepend(L, data);
  } else {
    Node newnode = newNode(data);
    Node previous = L->cursor->prev;
    newnode->prev = previous;
    newnode->next = L->cursor;
    previous->next = newnode;
    L->cursor->prev = newnode;
    L->length = L->length + 1;
    L->index = L->index + 1;
  }
}

// insert new element after cursor Node
void insertAfter(List L, int data) {
  if (L == NULL) {
    exit(1);
  }
  if (L->length == 0) {
    exit(1);
  }
  if (L->index < 0) {
    exit(1);
  }

  if (L->cursor == L->back) {
    append(L, data);
  } else {
    Node newnode = newNode(data);
    Node next = L->cursor->next;
    newnode->next = next;
    newnode->prev = L->cursor;
    next->prev = newnode;
    L->cursor->next = newnode;
    L->length = L->length + 1;
  }
}


void deleteFront(List L) {
  if (L == NULL) {
    exit(1);
  }
  if (L->length == 0) {
    exit(1);
  }
  Node n = L->front;
  if (L->length > 1) {
    L->front = L->front->next;
  } else {
    L->front = L->back = NULL;
  }
  freeNode(&n);
  L->index = L->index - 1;
  L->length = L->length - 1;
}

void deleteBack(List L) {
  if (L == NULL) {
    exit(1);
  }
  if (L->length == 0) {
    exit(1);
  }
  Node n = L->back;
  if (L->cursor == L->back) {
    L->index = -1;
  }
  if (L->length > 1){
    L->back = L->back->prev;
  } else {
    L->front = L->back = NULL;
  }
  freeNode(&n);
  L->length = L->length - 1;
}

void delete(List L) {
  if (L == NULL) {
    exit(1);
  }
  if (L->length == 0) {
    exit(1);
  }
  if (L->index < 0) {
    exit(1);
  }
  if (L->cursor == L->front) {
    deleteFront(L);
  } else if (L->cursor == L->back) {
    deleteBack(L);
  } else {
    Node m = L->cursor;
    L->cursor->prev->next = L->cursor->next;
    L->cursor->next = L->cursor->prev;

    freeNode(&m);

    L->cursor = NULL;
    L->index = -1;
    L->length = L->length - 1;
  }
}

// printList()
// Prints List
void printList(FILE* out, List L) {

    Node N = L -> front;
    while (N != NULL) {
        fprintf(out, "%d ", N -> data);
        N = N -> next;
    }

}

// copyList()
// Creates another identical copy of this List
List copyList(List L) {

    List createList = newList();

    Node nextVal = L -> front;
    while (nextVal != NULL) {
        append(createList, nextVal -> data);
        nextVal = nextVal -> next;
    }

    return createList;
}
