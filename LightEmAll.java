import java.awt.Color;
import java.util.*;
import javalib.impworld.*;
import javalib.worldimages.*;

class Edge {
  GamePiece fromNode;
  GamePiece toNode;
  int weight;

  Edge(GamePiece fromNode, GamePiece toNode, int weight) {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.weight = weight;
  }

  Edge(GamePiece fromNode, GamePiece toNode) {
    this.fromNode = fromNode;
    this.toNode = toNode;
  }

  //TESTED
  //checks the extensional equality of this edge and another edge
  boolean sameEdge(Edge other) {
    return (this.toNode.equals(other.toNode)
        && this.fromNode.equals(other.fromNode))
        || (this.toNode.equals(other.fromNode) 
            && this.fromNode.equals(other.toNode));
  }

}

class LightEmAll extends World {
  // a list of columns of GamePieces,
  // i.e., represents the board in column-major order
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all nodes
  ArrayList<GamePiece> nodes;

  ArrayList<Edge> path;

  // the width and height of the board
  int width;
  int height;
  // the current location of the power station,
  // as well as its effective radius
  int powerRow;
  int powerCol;
  int radius;
  // size of the tiles
  public static final int TILESIZE = 50;

  int count;
  int time;

  String bias;

  //TESTED
  //constructor for non-square board sizes, does not randomize board currently
  LightEmAll(int width, int height) {
    if (width == 0 || height == 0) {
      throw new IllegalArgumentException("Cannot have an empty board");
    }
    else {
      this.bias = "";
      this.width = width;
      this.height = height;
      this.board = this.makePieces();
      this.path = new ArrayList<Edge>();
      this.fractalGeneration(new Random());
      this.radius = (this.getDiameter() / 2) + 1;
      this.time = 0;

    }
  }

  LightEmAll(int width, int height, String bias) {
    if (width == 0 || height == 0) {
      throw new IllegalArgumentException("Cannot have an empty board");
    }

    else {
      this.bias = bias;
      this.width = width;
      this.height = width;
      this.board = this.makePieces();
      this.path = new ArrayList<Edge>();
      this.kruskalGeneration(new Random());
      this.radius = (this.getDiameter() / 2) + 1;
      //this.randomizeBoard();
      this.time = 0;
    }
  }



  //TESTED
  // generates a board with a list of empty game piece unconnected
  ArrayList<ArrayList<GamePiece>> makePieces() {
    this.powerCol =  0;
    this.powerRow = 0;
    ArrayList<ArrayList<GamePiece>> board = new ArrayList<ArrayList<GamePiece>>();
    for (int column = 0; column < this.width; column++) {
      ArrayList<GamePiece> rowNodes = new ArrayList<GamePiece>();
      for (int row = 0; row < this.height; row++) {
        GamePiece tile = new GamePiece(column, row);
        if (column == this.powerCol && row == this.powerRow) {
          tile.changePowerSource();
        }
        rowNodes.add(tile);
      }
      board.add(rowNodes);
    }
    return board;
  }

  //TESTME
  @Override
  // draw the board by going though each tile and placing it in the correct board
  // position.
  // If the person has connected everything, draw the win screen
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();
    WorldImage info = new EmptyImage();
    for (int column = 0; column < board.size(); column++) {
      for (int row = 0; row < board.get(column).size(); row++) {
        scene.placeImageXY(this.board.get(column).get(row).draw(),
            LightEmAll.TILESIZE * column + LightEmAll.TILESIZE / 2,
            LightEmAll.TILESIZE * row + LightEmAll.TILESIZE / 2);
      }
    }

    if (this.winCondition()) {
      info = new TextImage("You Won", this.height * 2, FontStyle.BOLD, Color.GREEN);
    }
    else {
      info = new TextImage("Moves: " + count + "       "
          + "Time: " + time, this.height * 2, FontStyle.BOLD, Color.BLACK);
    }

    WorldImage resetButton = new OverlayImage(
        new TextImage("RESTART", this.height, FontStyle.BOLD, Color.WHITE), 
        new RectangleImage(scene.width / 4, 50, OutlineMode.SOLID, Color.RED));
    WorldImage finalInformation = new OverlayOffsetImage(info, scene.width / 2, 0, resetButton);
    WorldImage infoBar = 
        new OverlayImage(finalInformation,
            new RectangleImage(scene.width, 100, OutlineMode.OUTLINE, Color.BLACK)); 
    scene.placeImageXY(infoBar, scene.width / 2, scene.height - 50);
    return scene;

  }

  //TESTED
  // connects two game piece together creating an edge between them
  // Effect: add the edge to the path in the game and to the edges of
  // each piece
  public void connect(GamePiece piece1, GamePiece piece2, Random rand) {
    Edge connection = new Edge(piece1, piece2, rand.nextInt(100));
    this.path.add(connection);
    piece1.addEdge(connection);
    piece2.addEdge(connection);
  }

  //PART 2
  // accumulates a fractal pattern of edges
  public void fractalGeneration(Random rand) {
    this.fractalGenerationHelp(0, 0, this.width, this.height, rand);
  }

  //PART 2
  // creates a fractal pattern of edges for the board recursively into quadrants
  public void fractalGenerationHelp(int col, int row, int width, int height, Random rand) {

    if ((width == 2 && height > 2) || (width > 2 && height == 2) || (width > 2 && height > 2)) {
      fractalGenerationHelp(col, row, (width / 2), (height / 2), rand);
      fractalGenerationHelp(col + (width / 2), row, (int) (Math.ceil((width / 2.0))), 
          (height / 2), rand);
      fractalGenerationHelp(col, row + (height / 2), (width / 2),
          (int) (Math.ceil((height / 2.0))), rand);
      fractalGenerationHelp(col + (width / 2), row + (height / 2), (int) (Math.ceil((width / 2.0))),
          (int) (Math.ceil((height / 2.0))), rand);

      this.connect(this.board.get(col).get(row + ((height / 2) - 1)),
          this.board.get(col).get(row + (height / 2)), rand);
      this.connect(this.board.get(col + ((width / 2) - 1)).get(row + (height - 1)),
          this.board.get(col + (width / 2)).get(row + (height - 1)), rand);
      this.connect(this.board.get(col + (width - 1)).get(row + ((height / 2) - 1)),
          this.board.get(col + (width - 1)).get(row + (height / 2)),  rand);

    }

    else if (width == 2 && height == 2) {
      GamePiece piece00 = this.board.get(col).get(row);
      GamePiece piece01 = this.board.get(col).get(row + 1);
      GamePiece piece10 = this.board.get(col + 1).get(row);
      GamePiece piece11 = this.board.get(col + 1).get(row + 1);
      this.connect(piece00, piece01, rand);
      this.connect(piece01, piece11, rand);
      this.connect(piece11, piece10, rand);
    }
    else if (width == 2 && height == 1) {
      GamePiece piece00 = this.board.get(col).get(row);
      GamePiece piece10 = this.board.get(col + 1).get(row);
      this.connect(piece00, piece10, rand);
    }
    else if (width == 1 && height == 2) {
      GamePiece piece00 = this.board.get(col).get(row);
      GamePiece piece01 = this.board.get(col).get(row + 1);
      this.connect(piece00, piece01, rand);
    }
  }

  //TESTED
  //determines if the given path contains the given edge
  public boolean contains(ArrayList<Edge> path, Edge connection) {
    boolean contains = false;
    for (Edge edge : path) {
      contains = edge.sameEdge(connection) || contains;
    }
    return contains;
  }

  //TESTED
  //sets up all the edges based on the given bias and the given board size and parameters
  public ArrayList<Edge> allEdges(Random rand) {
    //first connect everything
    ArrayList<Edge> path = new ArrayList<Edge>();
    for (int col = 0; col < this.width; col++) {
      for (int row = 0; row < this.height; row++) {
        GamePiece curr = this.board.get(col).get(row);
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            if ((i != j) && (i != -1 * j) && col + i < this.width && col + i >= 0 
                && row + j < this.height && row + j >= 0) {
              GamePiece neighbor = this.board.get(col + i).get(row + j);
              Edge connection;
              if (this.bias.equals("vertical")) {
                if (curr.row - neighbor.row != 0) {
                  connection = new Edge(curr, neighbor, rand.nextInt(25));
                }
                else {
                  connection = new Edge(curr, neighbor, rand.nextInt(100));
                }
              }
              else if (this.bias.equals("horizontal")) {
                if (curr.col - neighbor.col != 0) {
                  connection = new Edge(curr, neighbor, rand.nextInt(25));
                }
                else {
                  connection = new Edge(curr, neighbor, rand.nextInt(100));
                }
              }
              else {
                connection = new Edge(curr, neighbor, rand.nextInt(100));
              }
              if (!(this.contains(path, connection))) { 
                path.add(connection);
              }
            }
          }
        }
      }
    }
    return path;
  }

  //TESTED
  //finds a given piece
  public GamePiece find(Map<GamePiece, GamePiece> graph, GamePiece piece) {
    //if this is a   
    if (graph.get(piece).equals(piece)) {
      return piece;
    }
    else {
      return find(graph, graph.get(piece));
    }

  }

  //TESTED
  //makes the given piece point to piece2 rather than what it previously pointed to
  public void union(Map<GamePiece, GamePiece> graph, GamePiece piece1, GamePiece piece2) {
    graph.replace(piece1, piece2);
  }

  //TESTME
  //generates the board pieces based on Kruskal's Algorithm
  public void kruskalGeneration(Random rand) {
    Map<GamePiece, GamePiece> mstHash = new HashMap<GamePiece, GamePiece>();

    //sets up the hashmap with each gamePiece pointing to itself
    for (int col = 0; col < this.width; col++) {
      for (int row = 0; row < this.height; row++) {
        GamePiece curr = this.board.get(col).get(row);
        mstHash.put(curr, curr);
      }
    }

    //the setup
    //makes new heapsort object
    HeapSort<Edge> heapSort = new HeapSort<>(); 

    Comparator<Edge> edgeComp = new CompareWeight();
    List<Edge> allEdges = this.allEdges(rand);
    List<Edge> sortedEdges = heapSort.heapsort(allEdges, edgeComp);

    //choosing the paths
    while (this.path.size() != mstHash.size() - 1) {
      Edge curr = sortedEdges.remove(0);

      if (this.find(mstHash, curr.fromNode).equals(this.find(mstHash, curr.toNode))) {
        //throw out to avoid cycle
      }

      else {
        this.path.add(curr);
        this.union(mstHash, 
            this.find(mstHash, curr.fromNode),
            this.find(mstHash, curr.toNode));
      }
    }

    this.pathToEdges();

  }

  //TESTED
  //loops through the edges in the generated path and 
  //initializes the pieces connected to them
  void pathToEdges() {
    for (Edge e: this.path) {
      e.fromNode.addEdge(e);
      e.toNode.addEdge(e);
    }
  }

  //TESTED
  // introduces a random number of rotation on each piece of the board to
  // set up a random game
  public void randomizeBoard() {
    for (int column = 0; column < this.width; column++) {
      for (int row = 0; row < this.height; row++) {
        GamePiece tile = this.board.get(column).get(row);
        Random rand = new Random();
        for (int i = 0; i < rand.nextInt(4); i++) {
          tile.rotate();
        }
      }
    }
  }

  //TESTED
  // check if each piece is connected to all of its neighbors
  public boolean winCondition() {
    boolean hasWon = true;
    for (int col = 0; col < this.width; col++) {
      for (int row = 0; row < this.height; row++) {
        hasWon = this.board.get(col).get(row).isPowered && hasWon;
      }
    }
    return hasWon;
  }

  //TESTED
  @Override
  // check where the person has click the mouse and rotate that tile
  public void onMouseClicked(Posn pos) {
    //first if statement prevents index out of bounds errors
    if (pos.y > this.height * LightEmAll.TILESIZE) {
      if (pos.x > this.width * LightEmAll.TILESIZE / 2
          && pos.y > this.height * LightEmAll.TILESIZE + 25
          && pos.y < this.height * LightEmAll.TILESIZE + 75)  {

        this.board = this.makePieces();
        this.path = new ArrayList<Edge>();
        this.kruskalGeneration(new Random());
        this.radius = (this.getDiameter() / 2) + 1;
        this.randomizeBoard();
        this.time = 0;
      }
    }

    else {

      int col = pos.x / LightEmAll.TILESIZE;
      int row = pos.y / LightEmAll.TILESIZE;
      board.get(col).get(row).rotate();
      count++;
      this.resetPower();
      this.powerPath();
    }
  }

  @Override
  public void onTick() {
    this.time += 1;
  }

  //TESTED
  // move the power source position along the wires
  void movePowerSource(String key) {
    GamePiece currSource = this.board.get(this.powerCol).get(this.powerRow);
    currSource.changePowerSource();
    if (key.equals("left") && this.powerCol > 0
        && currSource.isConnected(this.board.get(currSource.col - 1).get(currSource.row))) {
      this.powerCol--;
    }
    if (key.equals("right") && this.powerCol < this.width - 1
        && currSource.isConnected(this.board.get(currSource.col + 1).get(currSource.row))) {
      this.powerCol++;
    }
    if (key.equals("up") && this.powerRow > 0
        && currSource.isConnected(this.board.get(currSource.col).get(currSource.row - 1))) {
      this.powerRow--;
    }
    if (key.equals("down") && this.powerRow < this.height - 1
        && currSource.isConnected(this.board.get(currSource.col).get(currSource.row + 1))) {
      this.powerRow++;
    }
    this.board.get(this.powerCol).get(this.powerRow).changePowerSource();
    count++;
    this.resetPower();
    this.powerPath();
  }

  //TESTED
  @Override
  // takes input from the user to tell the power source where to move
  public void onKeyEvent(String key) {
    this.movePowerSource(key);
  }

  //TESTED
  // resets the power off all the pieces to power them off
  public void resetPower() {
    for (int column = 0; column < this.width; column++) {
      for (int row = 0; row < this.height; row++) {
        this.board.get(column).get(row).powerOff();
      }
    }
  }

  //TESTED
  // make it so that everything connected to the power source is powered on
  // within a finite given radius
  public void powerPath() {
    GamePiece powerSource = this.board.get(this.powerCol).get(this.powerRow);
    Map<GamePiece, Integer> radius = this.depthFrom(powerSource);
    Deque<GamePiece> worklist = new Deque<GamePiece>();
    worklist.addAtTail(this.board.get(this.powerCol).get(this.powerRow));
    while (worklist.size() > 0) {
      GamePiece curr = worklist.removeFromHead();
      curr.isPowered();
      curr.lineColor(radius.get(curr), this.radius);
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          if (!(i == 0 && j == 0) && curr.col + i >= 0 && curr.col + i < this.width
              && curr.row + j >= 0 && curr.row + j < this.height
              && radius.get(curr) < this.radius) {
            GamePiece neighbor = this.board.get(curr.col + i).get(curr.row + j);
            if (curr.isConnected(neighbor) && !neighbor.isPowered) {
              worklist.addAtTail(neighbor);
            }
          }
        }
      }
    }
  }
  
  //TESTED
  // reset the seen variable in each gamePiece
  public void resetSeen() {
    for (int column = 0; column < this.width; column++) {
      for (int row = 0; row < this.height; row++) {
        this.board.get(column).get(row).seen = false;
      }
    }
  }

  //TESTED
  // find the farthest piece from the power source
  public GamePiece farthestFromPower() {
    this.resetSeen();
    Deque<GamePiece> worklist = new Deque<GamePiece>();
    // initialize the worklist to contain the starting node
    GamePiece powCell = this.board.get(this.powerCol).get(this.powerRow);
    GamePiece farthestPiece = powCell;

    // find the farthest node from the power source
    worklist.addAtTail(powCell);
    while (worklist.size() > 0) {
      GamePiece next = worklist.removeFromHead();
      if (next.seen) {
        // throw it out
      }
      else {
        for (Edge edge : next.edges) {
          next.seen();
          worklist.addAtTail(next.goesTo(edge));
          farthestPiece = next;
        }
      }
    }

    return farthestPiece;
  }

  //TESTED
  // create a hashmap of all the nodes and their corresponding
  // distance from the given node
  public Map<GamePiece, Integer> depthFrom(GamePiece start) {
    this.resetSeen();

    Map<GamePiece, Integer> levelHash = new HashMap<GamePiece, Integer>();
    // A Queue or a Stack, depending on the algorithm
    ArrayDeque<GamePiece> worklist = new ArrayDeque<GamePiece>();
    // initialize the worklist to contain the starting node
    // compute the distance between the two farthest nodes in the graph
    levelHash.put(start, 0);
    worklist.addLast(start);
    while (worklist.size() > 0) {
      // Node next = the next item from the worklist
      GamePiece next = worklist.removeFirst();
      if (next.seen) {
        // discard it
      }
      else if (next.edges.size() > 0) {
        next.seen();
        for (Edge edge : next.edges) {
          GamePiece neighbor = next.goesTo(edge);
          if (!neighbor.seen) {
            worklist.addLast(neighbor);
            levelHash.put(neighbor, levelHash.get(next) + 1);
          }
        }
      }
    }
    return levelHash;
  }

  //TESTED
  // get the maximum distance from the farthest point from the power source
  // to the last reachable point in the graph
  public int getDiameter() {
    int maxLevel = 0;
    GamePiece start = this.farthestFromPower();
    Map<GamePiece, Integer> levelHash = this.depthFrom(start);
    for (int column = 0; column < this.width; column++) {
      for (int row = 0; row < this.height; row++) {
        int gamePieceLevel = levelHash.getOrDefault(this.board.get(column).get(row), 0);
        if (gamePieceLevel > maxLevel) {
          maxLevel = gamePieceLevel;
        }
      }
    }
    return maxLevel;
  }
}

class GamePiece {
  boolean seen;
  // in logical coordinates, with the origin
  // at the top-left corner of the screen
  int row;
  int col;
  // whether this GamePiece is connected to the
  // adjacent left, right, top, or bottom pieces
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  // whether the power station is on this piece
  boolean powerStation;
  boolean isPowered;

  Color lineColor;

  ArrayList<Edge> edges;

  //constructor initializing the type of piece based on the left right top and bottom values
  GamePiece(boolean left, boolean right, boolean top, boolean bottom) {
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    this.edges = new ArrayList<Edge>();
    this.seen = false;
    this.lineColor = Color.WHITE;
  }

  //constructor initializing the piece based on its location
  GamePiece(int col, int row) {
    this.left = false;
    this.right = false;
    this.bottom = false;
    this.top = false;
    this.col = col;
    this.row = row;
    this.edges = new ArrayList<Edge>();
    this.seen = false;
    this.lineColor = Color.WHITE;

  }

  //TESTED
  // draw the base image of the tile and add each connection if it is present
  WorldImage draw() {
    Color lineC;
    if (this.isPowered) {
      lineC = this.lineColor;
    }
    else {
      lineC = Color.WHITE;
    }
    WorldImage powerStation = new StarImage(15, 7, OutlineMode.SOLID, Color.CYAN);
    WorldImage tile = new OverlayImage(
        new RectangleImage(LightEmAll.TILESIZE, LightEmAll.TILESIZE, OutlineMode.OUTLINE,
            Color.BLACK),
        new RectangleImage(LightEmAll.TILESIZE, LightEmAll.TILESIZE, OutlineMode.SOLID,
            Color.DARK_GRAY));

    WorldImage vertWire = new LineImage(new Posn(0, LightEmAll.TILESIZE / 2), lineC);

    WorldImage horzWire = new RotateImage(vertWire, 90.0);
    if (this.left) {
      tile = new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, horzWire, 0, 0, tile);
    }
    if (this.top) {
      tile = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP, vertWire, 0, 0, tile);
    }
    if (this.right) {
      tile = new OverlayOffsetAlign(AlignModeX.RIGHT, AlignModeY.MIDDLE, horzWire, 0, 0, tile);
    }
    if (this.bottom) {
      tile = new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.BOTTOM, vertWire, 0, 0, tile);
    }
    if (this.powerStation) {
      tile = new OverlayImage(powerStation, tile);
    }
    return tile;
  }

  //TESTED
  // rotate the piece by 90 degrees by changing boolean connections to reflect the
  // rotation
  void rotate() {
    boolean top = this.top;
    boolean right = this.right;
    boolean left = this.left;
    boolean bottom = this.bottom;
    this.right = top;
    this.bottom = right;
    this.left = bottom;
    this.top = left;
  }

  //TESTED
  // add the given edge to the list of edges of the piece and
  // change the boolean direction to reflect the new connection
  void addEdge(Edge edge) {
    this.edges.add(edge);
    GamePiece toNode = this.goesTo(edge);
    if (this.col - toNode.col == -1) {
      this.right = true;
    }
    if (this.col - toNode.col == 1) {
      this.left = true;
    }
    if (this.row - toNode.row == -1) {
      this.bottom = true;
    }
    if (this.row - toNode.row == 1) {
      this.top = true;
    }
  }

  //TESTED
  // get the other side of the undirected edge
  public GamePiece goesTo(Edge edge) {
    if (edge.fromNode == this) {
      return edge.toNode;
    }
    else {
      return edge.fromNode;
    }
  }

  //TESTED
  // checks if the pieces are connected
  boolean isConnected(GamePiece node) {
    if (this.col - node.col == -1 && this.row - node.row == 0) {
      return this.right && node.left;
    }
    else if (this.col - node.col == 1 && this.row - node.row == 0) {
      return this.left && node.right;
    }
    else if (this.row - node.row == -1 && this.col - node.col == 0) {
      return this.bottom && node.top;
    }
    else if (this.row - node.row == 1 && this.col - node.col == 0) {
      return this.top && node.bottom;
    }
    else {
      return false;
    }
  }

  //determines the color that the line should render as based on the
  //radius of the line from the power station
  void lineColor(int lvl, int radius) {
    if (lvl < radius / 3) {
      this.lineColor = Color.YELLOW;
    }
    else if (lvl >= radius / 3 && lvl < (radius * 2) / 3) {
      this.lineColor = Color.ORANGE;
    }
    else {
      this.lineColor = Color.RED;
    }
  }

  //TESTED
  // make the gamepiece a powersource
  void changePowerSource() {
    this.powerStation = !this.powerStation;
  }

  //TESTED
  // powers the game piece off
  void powerOff() {
    this.isPowered = false;
  }

  //TESTED
  // powers the game piece on
  void isPowered() {
    this.isPowered = true;
  }

  //TESTED
  // changes the seen boolean to show that the cell has been seen
  void seen() {
    this.seen = true;
  }

}
