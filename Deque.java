import tester.*;

interface IPred<T> {

  // applies the given method to a T and returns a boolean
  boolean apply(T t);
}

class StartsWithA implements IPred<String> {
  /*
   * Template Fields none Methods apply(String) - boolean Methods on Fields none
   */
  @Override
  // checks whether the string meets the given condition
  public boolean apply(String str) {
    /*
     * Template Fields none Methods apply(String) - boolean Methods on Fields none
     * Parameters str - String Methods On Parameters equals - boolean substring(int,
     * int) - String
     */
    return str.substring(0, 1).equals("a") || str.substring(0, 1).equals("A");
  }
}

class FindCDE implements IPred<String> {
  /*
   * Template Fields none Methods apply(String) - boolean Methods on Fields none
   */
  @Override
  // checks whether the string meets the given condition
  public boolean apply(String str) {
    /*
     * Template Fields none Methods apply(String) - boolean Methods on Fields none
     * Parameters str Methods on Parameters equals - boolean
     */
    return str.equals("cde");
  }
}

class Find10 implements IPred<Integer> {
  /*
   * Template Fields none Methods apply(int) - boolean Methods on Fields none
   */
  @Override
  // checks whether the given integer meets the condition
  public boolean apply(Integer num) {
    /*
     * Template Fields none Methods apply(String) - boolean Methods on Fields none
     * Parameters num - int Methods On Parameters none
     */
    return num == 10;
  }

}

class Deque<T> {
  Sentinel<T> header;

  Deque() {
    this.header = new Sentinel<T>();
  }

  Deque(Sentinel<T> header) {
    this.header = header;
  }

  /*
   * Template Fields header - sentinel Methods: size() - int addAtTail(T) - deque
   * addAtHead(T) - deque removeFromHead() - T removeFromTail() - T find(IPred<T>)
   * - ANode<T> Methods on Fields: size() - int addToNext(T) - void
   * removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
   */

  // get the size of the deck
  int size() {
    /*
     * Template Fields header - sentinel Methods: size() - int addAtTail(T) - deque
     * addAtHead(T) - deque removeFromHead() - T removeFromTail() - T find(IPred<T>)
     * - ANode<T> Methods on Fields: size() - int addToNext(T) - void
     * removeFromHelp() - T findHelp(IPred<T>) - ANode<T> Parameters none Methods on
     * Parameters none
     */
    return this.header.next.size();
  }

  // add a node to the top of the deck
  Deque<T> addAtHead(T value) {
    /*
     * Template Fields header - sentinel Methods: size() - int addAtTail(T) - deque
     * addAtHead(T) - deque removeFromHead() - T removeFromTail() - T find(IPred<T>)
     * - ANode<T> Methods on Fields: size() - int addToNext(T) - void
     * removeFromHelp() - T findHelp(IPred<T>) - ANode<T> Parameters value - T
     * Methods on Parameters none
     */
    this.header.addToNext(value);
    return this;
  }

  // add a node to end of the deque
  Deque<T> addAtTail(T value) {
    /*
     * Template Fields header - sentinel Methods: size() - int addAtTail(T) - deque
     * addAtHead(T) - deque removeFromHead() - T removeFromTail() - T find(IPred<T>)
     * - ANode<T> Methods on Fields: size() - int addToNext(T) - void
     * removeFromHelp() - T findHelp(IPred<T>) - ANode<T> Parameters value - T
     * Methods on Parameters none
     */
    this.header.prev.addToNext(value);
    return this;
  }

  // remove the node at the top of the deck
  T removeFromHead() {
    /*
     * Template Fields header - sentinel Methods: size() - int addAtTail(T) - deque
     * addAtHead(T) - deque removeFromHead() - T removeFromTail() - T find(IPred<T>)
     * - ANode<T> Methods on Fields: size() - int addToNext(T) - void
     * removeFromHelp() - T findHelp(IPred<T>) - ANode<T> Parameters none Methods on
     * Parameters none
     */
    return this.header.next.removeFromHelp();
  }

  // remove the node at the bottom of the deck
  T removeFromTail() {
    /*
     * Template Fields header - sentinel Methods: size() - int addAtTail(T) - deque
     * addAtHead(T) - deque removeFromHead() - T removeFromTail() - T find(IPred<T>)
     * - ANode<T> Methods on Fields: size() - int addToNext(T) - void
     * removeFromHelp() - T findHelp(IPred<T>) - ANode<T> Parameters none Methods on
     * Parameters none
     */
    return this.header.prev.removeFromHelp();
  }

  // get the first node that meets the given condition
  ANode<T> find(IPred<T> p) {
    /*
     * Template Fields header - sentinel Methods: size() - int addAtTail(T) - deque
     * addAtHead(T) - deque removeFromHead() - T removeFromTail() - T find(IPred<T>)
     * - ANode<T> Methods on Fields: size() - int addToNext(T) - void
     * removeFromHelp() - T findHelp(IPred<T>) - ANode<T> Parameters p - IPred<T>
     * Methods on Parameters apply(T) - boolean
     */
    return this.header.next.findHelp(p);
  }

  // remove the given node from the deck
  Deque<T> removeNode(ANode<T> node) {
    /*
     * Template Fields header - sentinel Methods: size() - int addAtTail(T) - deque
     * addAtHead(T) - deque removeFromHead() - T removeFromTail() - T find(IPred<T>)
     * - ANode<T> Methods on Fields: size() - int addToNext(T) - void
     * removeFromHelp() - T findHelp(IPred<T>) - ANode<T> Parameters node - ANode<T>
     * Methods on Parameters size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T>
     */
    if (node.size() == 0) {
      return this;
    }
    else {
      node.removeFromHelp();
      return this;
    }
  }

}

abstract class ANode<T> {
  ANode<T> next;
  ANode<T> prev;

  ANode(ANode<T> next, ANode<T> prev) {
    this.next = next;
    this.prev = prev;
  }

  /*
   * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
   * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
   * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
   * findHelp(IPred<T>) - ANode<T>
   */

  // default for Node: add the number of nodes in the rest
  // of the deck
  int size() {
    /*
     * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
     * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
     * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T> Parameters none Methods on Paramters none
     */
    return 1 + this.next.size();
  }

  // moves the pointer to remove a node
  abstract T removeFromHelp();

  // returns the node who's data matches the given predicate
  abstract ANode<T> findHelp(IPred<T> p);

  // moves the pointers to add node with the given data
  abstract void addToNext(T value);
}

class Sentinel<T> extends ANode<T> {
  Sentinel() {
    super(null, null);
    this.prev = this;
    this.next = this;
  }

  /*
   * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
   * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
   * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
   * findHelp(IPred<T>) - ANode<T>
   */

  // The size of a sentinel is zero
  int size() {
    /*
     * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
     * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
     * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T> Parameters none Methods on Parameters none
     */
    return 0;
  }

  // create the new node with data
  void addToNext(T value) {
    /*
     * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
     * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
     * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T> Parameters value - T Methods On Parameters none
     */
    new Node<T>(value, this.next, this);

  }

  // you cannot remove the sentinel node
  T removeFromHelp() {
    /*
     * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
     * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
     * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T> Parameters none Methods on Parameters none
     */
    throw new RuntimeException("Cannot remove sentinel node");
  }

  // the deque does not contain a matching item so return the sentinel
  ANode<T> findHelp(IPred<T> p) {
    /*
     * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
     * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
     * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T> Parameters p - IPred<T> Methods on Parameters
     * apply(T) - boolean
     */
    return this;
  }

}

class Node<T> extends ANode<T> {
  T data;

  Node(T data) {
    super(null, null);
    this.data = data;
  }

  Node(T data, ANode<T> next, ANode<T> prev) {
    super(next, prev);
    if (next == null || prev == null) {
      throw new IllegalArgumentException("Nodes cannot be null");
    }
    else {
      this.data = data;
      next.prev = this;
      prev.next = this;
    }
  }

  /*
   * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
   * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
   * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
   * findHelp(IPred<T>) - ANode<T>
   */

  // Create a new node at this position
  @Override
  void addToNext(T value) {
    /*
     * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
     * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
     * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T> Parameters value - T Methods on Parameters none
     */
    new Node<T>(value, this.next, this);
  }

  // change the pointers to remove this node
  @Override
  T removeFromHelp() {
    /*
     * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
     * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
     * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T> Parameters none Methods on Parameters none
     */
    this.prev.next = this.next;
    this.next.prev = this.prev;
    return this.data;
  }

  // get the node that satisfys the predicate in the
  // rest of the deck
  @Override
  ANode<T> findHelp(IPred<T> p) {
    /*
     * Template Fields next - ANode<T> prev - ANode<T> Methods: size() - int
     * addToNext(T) - void removeFromHelp() - T findHelp(IPred<T>) - ANode<T>
     * Methods on Fields size() - int addToNext(T) - void removeFromHelp() - T
     * findHelp(IPred<T>) - ANode<T> Parameters p - IPred<T> Methods on Parameters
     * apply(T) - boolean
     */
    if (p.apply(this.data)) {
      return this;
    }
    else {
      return this.next.findHelp(p);
    }
  }

}

class ExamplesDeque {
  Sentinel<String> sentinel = new Sentinel<String>();
  Sentinel<Integer> sentinelN = new Sentinel<Integer>();
  Deque<Integer> MtDeque;
  Deque<String> deque = new Deque<String>(sentinel);
  Deque<Integer> dequeN = new Deque<Integer>(sentinelN);
  Node<String> node1;
  Node<String> node2;
  Node<String> node3;
  Node<String> node4;
  Node<Integer> node1N;
  Node<Integer> node2N;
  Node<Integer> node3N;
  Node<Integer> node4N;
  Node<Integer> node5N;
  Node<Integer> node6N;
  Node<Integer> node7N;
  Node<Integer> node8N;

  IPred<String> startsWithA = new StartsWithA();
  IPred<String> isCDE = new FindCDE();
  IPred<Integer> find10 = new Find10();

  void initStringDeque() {
    node1 = new Node<String>("abc", this.sentinel, this.sentinel);
    node2 = new Node<String>("bcd", this.sentinel, this.node1);
    node3 = new Node<String>("cde", this.sentinel, this.node2);
    node4 = new Node<String>("def", this.sentinel, this.node3);
  }

  void initNumDeque() {
    node1N = new Node<Integer>(1, this.sentinelN, this.sentinelN);
    node2N = new Node<Integer>(2, this.sentinelN, this.node1N);
    node3N = new Node<Integer>(3, this.sentinelN, this.node2N);
    node4N = new Node<Integer>(4, this.sentinelN, this.node3N);
    node5N = new Node<Integer>(5, this.sentinelN, this.node4N);
    node6N = new Node<Integer>(6, this.sentinelN, this.node5N);
    node7N = new Node<Integer>(7, this.sentinelN, this.node6N);
    node8N = new Node<Integer>(8, this.sentinelN, this.node7N);
  }

  void initMt() {
    this.MtDeque = new Deque<Integer>();
  }

  void testNodeConstrutor(Tester t) {
    Node<Integer> testNode = new Node<Integer>(0);
    t.checkExpect(testNode.next, null);
    t.checkExpect(testNode.prev, null);
    Node<Integer> testNode2 = new Node<Integer>(1, new Sentinel<Integer>(), testNode);
    t.checkExpect(testNode2.next instanceof Sentinel, true);
    t.checkExpect(((Node<Integer>) (testNode2.prev)).data, 0);
    t.checkConstructorException(new IllegalArgumentException("Nodes cannot be null"), "Node", 0,
        null, null);
  }

  void testSentinalConstructor(Tester t) {
    Sentinel<Integer> testSen = new Sentinel<Integer>();
    t.checkExpect(testSen.next instanceof Sentinel, true);
    t.checkExpect(testSen.prev instanceof Sentinel, true);
  }

  void testDequeConstructor(Tester t) {
    this.initNumDeque();
    Deque<Integer> testDeque = new Deque<Integer>();
    t.checkExpect(testDeque.header instanceof Sentinel, true);
    Deque<Integer> testDeque2 = new Deque<Integer>(this.sentinelN);
    t.checkExpect(testDeque.header instanceof Sentinel, true);
    t.checkExpect(((Node<Integer>) (testDeque2.header.next)).data, 1);
  }

  void testSize(Tester t) {
    this.initStringDeque();
    t.checkExpect(this.deque.size(), 4);
    this.initNumDeque();
    t.checkExpect(this.dequeN.size(), 8);
    this.dequeN.addAtTail(9);
    t.checkExpect(this.dequeN.size(), 9);
    this.initMt();
    t.checkExpect(this.MtDeque.size(), 0);
  }

  void testSizeANode(Tester t) {
    this.initStringDeque();
    t.checkExpect(this.deque.header.next.size(), 4);
    t.checkExpect(this.sentinel.size(), 0);
    t.checkExpect(this.node4.size(), 1);
    this.initNumDeque();
    t.checkExpect(this.dequeN.header.next.size(), 8);
    t.checkExpect(this.dequeN.header.size(), 0);
    t.checkExpect(this.node4N.size(), 5);

  }

  void testAddAtHead(Tester t) {
    this.initStringDeque();
    this.deque.addAtHead("xyz");
    t.checkExpect(((Node<String>) (this.deque.header.next)).data, "xyz");
    this.initMt();
    this.MtDeque.addAtHead(1);
    t.checkExpect(((Node<Integer>) (this.MtDeque.header.next)).data, 1);
  }

  void testAddAtTail(Tester t) {
    this.initStringDeque();
    this.deque.addAtTail("xyz");
    t.checkExpect(((Node<String>) (this.deque.header.prev)).data, "xyz");
    this.deque.addAtTail("gfi");
    t.checkExpect(((Node<String>) (this.deque.header.prev)).data, "gfi");
    this.initNumDeque();
    this.dequeN.addAtTail(9);
    t.checkExpect(((Node<Integer>) (this.dequeN.header.prev)).data, 9);
  }

  void testAddToNext(Tester t) {
    this.initNumDeque();
    this.node1N.addToNext(15);
    t.checkExpect(((Node<Integer>) (this.node1N.next)).data, 15);
    t.checkExpect(((Node<Integer>) (this.node2N.prev)).data, 15);
    this.initNumDeque();
    this.sentinelN.addToNext(1);
    t.checkExpect(((Node<Integer>) (this.sentinelN.next)).data, 1);
    t.checkExpect(((Node<Integer>) (this.sentinelN.prev)).data, 8);
  }

  void testRemoveFromHead(Tester t) {
    this.initStringDeque();
    t.checkExpect(this.deque.removeFromHead(), "abc");
    t.checkExpect(((Node<String>) (this.deque.header.next)).data, "bcd");
    t.checkExpect(this.deque.removeFromHead(), "bcd");
    t.checkExpect(((Node<String>) (this.deque.header.next)).data, "cde");
    t.checkExpect(this.deque.removeFromHead(), "cde");
    t.checkExpect(((Node<String>) (this.deque.header.next)).data, "def");
  }

  void testRemoveFromTail(Tester t) {
    this.initStringDeque();
    t.checkExpect(this.deque.removeFromTail(), "def");
    t.checkExpect(((Node<String>) (this.deque.header.prev)).data, "cde");
    t.checkExpect(this.deque.removeFromTail(), "cde");
    t.checkExpect(((Node<String>) (this.deque.header.prev)).data, "bcd");
    t.checkExpect(this.deque.removeFromTail(), "bcd");
    t.checkExpect(((Node<String>) (this.deque.header.prev)).data, "abc");
  }

  void testRemoveFromHelp(Tester t) {
    this.initNumDeque();
    t.checkExpect(this.node3N.removeFromHelp(), 3);
    t.checkExpect(((Node<Integer>) (this.node2N.next)).data, 4);
    t.checkExpect(this.node8N.removeFromHelp(), 8);
    t.checkExpect(((this.node7N.next instanceof Sentinel)), true);
  }

  void testFind(Tester t) {
    this.initStringDeque();
    t.checkExpect(this.deque.find(this.startsWithA), this.node1);
    this.initStringDeque();
    t.checkExpect(this.deque.find(this.isCDE), this.node3);
    this.initStringDeque();
    t.checkExpect(this.dequeN.find(this.find10), this.sentinelN);
  }

  void testFindHelp(Tester t) {
    this.initStringDeque();
    t.checkExpect(this.sentinel.findHelp(this.isCDE), this.sentinel);
    t.checkExpect(this.node1.findHelp(this.isCDE), this.node3);
    t.checkExpect(this.node4.findHelp(this.startsWithA), this.sentinel);
  }

  void testRemove(Tester t) {
    this.initNumDeque();
    this.dequeN.removeNode(this.node4N);
    t.checkExpect(((Node<Integer>) (this.node3N.next)).data, 5);
    this.dequeN.removeNode(this.node5N);
    t.checkExpect(((Node<Integer>) (this.node3N.next)).data, 6);
  }

  void testApply(Tester t) {
    t.checkExpect(this.find10.apply(10), true);
    t.checkExpect(this.find10.apply(7), false);
    t.checkExpect(this.startsWithA.apply("Ah"), true);
    t.checkExpect(this.startsWithA.apply("ah"), true);
    t.checkExpect(this.startsWithA.apply("ha"), false);
  }
}