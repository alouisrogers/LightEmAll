import java.awt.Color;
import java.util.*;
import tester.*;
import javalib.impworld.WorldScene;
import javalib.worldimages.*;

class ExamplesLightEmAll {

  GamePiece tile = new GamePiece(true, true, true, true);
  GamePiece tileMT = new GamePiece(false, false, false, false);
  GamePiece tileL = new GamePiece(true, false, false, false);
  GamePiece tileR = new GamePiece(false, true, false, false);
  GamePiece tileT = new GamePiece(false, false, true, false);
  GamePiece tileB = new GamePiece(false, false, false, true);
  GamePiece tileLR = new GamePiece(true, true, false, false);
  GamePiece tileLT = new GamePiece(true, false, true, false);
  GamePiece tileLB = new GamePiece(true, false, false, true);
  GamePiece tileRT = new GamePiece(false, true, true, false);
  GamePiece tileRB = new GamePiece(false, true, false, true);
  GamePiece tileTB = new GamePiece(false, false, true, true);
  GamePiece tileLRT = new GamePiece(true, true, true, false);
  GamePiece tileLRB = new GamePiece(true, true, false, true);
  GamePiece tileRTB = new GamePiece(false, true, true, true);
  GamePiece tile00 = new GamePiece(0, 0);
  GamePiece tile10 = new GamePiece(1, 0);
  GamePiece tile01 = new GamePiece(0, 1);
  GamePiece tile11 = new GamePiece(1, 1);
  Edge edge0 = new Edge(this.tile, this.tileR);
  Edge edge1 = new Edge(this.tile, this.tileL);
  Edge edge2 = new Edge(this.tile, this.tileB);
  Edge edge3 = new Edge(this.tile, this.tileT);
  LightEmAll game = new LightEmAll(2, 2);
  LightEmAll game2 = new LightEmAll(2, 2);
  LightEmAll bigGame = new LightEmAll(4, 4);
  LightEmAll nonSquareGame = new LightEmAll(2, 1);
  Map<GamePiece, GamePiece> graphCycle = new HashMap<>();
  Map<GamePiece, GamePiece> graph = new HashMap<>();


  void initCondition() {
    tile = new GamePiece(true, true, true, true);
    tileMT = new GamePiece(false, false, false, false);
    tileL = new GamePiece(true, false, false, false);
    tileR = new GamePiece(false, true, false, false);
    tileT = new GamePiece(false, false, true, false);
    tileB = new GamePiece(false, false, false, true);
    tileLR = new GamePiece(true, true, false, false);
    tileLT = new GamePiece(true, false, true, false);
    tileLB = new GamePiece(true, false, false, true);
    tileRT = new GamePiece(false, true, true, false);
    tileRB = new GamePiece(false, true, false, true);
    tileTB = new GamePiece(false, false, true, true);
    tileLRT = new GamePiece(true, true, true, false);
    tileLRB = new GamePiece(true, true, false, true);
    tileRTB = new GamePiece(false, true, true, true);
    tile00 = new GamePiece(0, 0);
    tile10 = new GamePiece(1, 0);
    tile01 = new GamePiece(0, 1);
    tile11 = new GamePiece(1, 1);
    edge0 = new Edge(this.tileMT, this.tileL);
    edge1 = new Edge(this.tileMT, this.tileL);
    edge2 = new Edge(this.tileMT, this.tileB);
    edge3 = new Edge(this.tileMT, this.tileT);
    game = new LightEmAll(2, 2);
    nonSquareGame = new LightEmAll(2, 1);
    bigGame = new LightEmAll(4, 4);
    graphCycle = new HashMap<>();
    graphCycle.put(this.tile00, this.tile01);
    graphCycle.put(this.tile01, this.tile11);
    graphCycle.put(this.tile11, this.tile10);
    graphCycle.put(this.tile10, this.tile00);
    graph = new HashMap<>();
    graph.put(this.tile00, this.tile01);
    graph.put(this.tile01, this.tile11);
    graph.put(this.tile11, this.tile10);
    graph.put(this.tile10, this.tile10);



  }

  void testSameEdge(Tester t) {
    this.initCondition();
    Edge edge1 = new Edge(this.tile00, this.tile01);
    Edge edge2 = new Edge(this.tile01, this.tile00);
    Edge edge3 = new Edge(this.tile00, this.tile01);
    t.checkExpect(edge1.sameEdge(edge2), true);
    t.checkExpect(edge1.sameEdge(edge2), true);
    t.checkExpect(edge1.sameEdge(edge3), true);
    t.checkExpect(edge1.sameEdge(edge1), true);
  }

  void testInvalidConstructor(Tester t) {
    t.checkConstructorException(new IllegalArgumentException("Cannot have an empty board"),
        "LightEmAll", 0, 0, "");
    t.checkConstructorException(new IllegalArgumentException("Cannot have an empty board"),
        "LightEmAll", 0, 0);
  }

  void testMakePieces(Tester t) {
    this.initCondition();
    this.tile00.powerStation = true;
    t.checkExpect(this.game.makePieces(),
        new ArrayList<ArrayList<GamePiece>>(
            Arrays.asList(new ArrayList<GamePiece>(Arrays.asList(this.tile00, this.tile01)),
                new ArrayList<GamePiece>(Arrays.asList(this.tile10, this.tile11)))));
    this.initCondition();
    this.tile00.powerStation = true;
    t.checkExpect(this.nonSquareGame.makePieces(),
        new ArrayList<ArrayList<GamePiece>>(
            Arrays.asList(new ArrayList<GamePiece>(Arrays.asList(this.tile00)),
                new ArrayList<GamePiece>(Arrays.asList(this.tile10)))));
  }

  void testMakeScene(Tester t) {
    this.initCondition();
    WorldScene testSceneWin = this.game.getEmptyScene();
    WorldScene testSceneGame = this.game.getEmptyScene();
        
    //draw tiles already tested, so it can be invoked here
    WorldImage draw00 = this.game.board.get(0).get(0).draw();
    WorldImage draw01 = this.game.board.get(0).get(1).draw();
    WorldImage draw10 = this.game.board.get(1).get(0).draw();
    WorldImage draw11 = this.game.board.get(1).get(1).draw();
    
    WorldImage draw002 = this.game2.board.get(0).get(0).draw();
    this.game2.board.get(0).get(0).rotate();
    WorldImage draw012 = this.game2.board.get(0).get(1).draw();
    WorldImage draw102 = this.game2.board.get(1).get(0).draw();
    WorldImage draw112 = this.game2.board.get(1).get(1).draw();
    
    testSceneWin.placeImageXY(draw00, 0, 0);
    testSceneWin.placeImageXY(draw01, 0, 50);
    testSceneWin.placeImageXY(draw10, 50, 0);
    testSceneWin.placeImageXY(draw11, 50, 50);
    
    testSceneGame.placeImageXY(draw002, 0, 0);
    testSceneGame.placeImageXY(draw012, 0, 50);
    testSceneGame.placeImageXY(draw102, 50, 0);
    testSceneGame.placeImageXY(draw112, 50, 50);
    
    WorldImage infoWin = new TextImage("You Won", 4, FontStyle.BOLD, Color.GREEN);
    WorldImage infoGame = new TextImage("Moves: " + 0 + "Time: " 
    + 0, 4, FontStyle.BOLD, Color.BLACK);
    
    WorldImage resetButton = new OverlayImage(
        new TextImage("RESTART", 2, FontStyle.BOLD, Color.WHITE), 
        new RectangleImage(25, 50, OutlineMode.SOLID, Color.RED));
    

    WorldImage testFinalInformationWin = new OverlayOffsetImage(infoWin, 50, 0, resetButton);
    WorldImage testFinalInformationGame = new OverlayOffsetImage(infoGame, 50, 0, resetButton);

    WorldImage testInfoWin = new OverlayImage(testFinalInformationWin, 
        new RectangleImage(100, 100, OutlineMode.OUTLINE, Color.BLACK));
    
    WorldImage testInfoGame = new OverlayImage(testFinalInformationGame, 
        new RectangleImage(100, 100, OutlineMode.OUTLINE, Color.BLACK));
    testSceneWin.placeImageXY(testInfoWin, 50, 100);
    testSceneGame.placeImageXY(testInfoGame, 50, 100);
    
    t.checkExpect(testSceneWin, game.makeScene());
    t.checkExpect(testSceneGame, game2.makeScene());
    

  }

  void testConnect(Tester t) {
    this.initCondition();
    GamePiece tile00 = this.game.board.get(0).get(0);
    GamePiece tile10 = this.game.board.get(1).get(0);
    game.connect(tile00, tile10, new Random(0));
    t.checkExpect(tile00.right, true);
    t.checkExpect(tile10.left, true);
  }

  //NOT PART OF PART 3 - NOT BEING TESTED
  /*void testFractalGeneration(Tester t) {
  this.initCondition();
  this.game.path = new ArrayList<Edge>();
  ArrayList<GamePiece> column1 = new ArrayList<GamePiece>(
      Arrays.asList(this.tile00, this.tile01));
  ArrayList<GamePiece> column2 = new ArrayList<GamePiece>(
      Arrays.asList(this.tile10, this.tile11));

  this.game.board.set(0, column1);
  this.game.board.set(1, column2);
  this.game.fractalGeneration(new Random(0));
  t.checkExpect(this.tile00.edges.size(), 1);
  t.checkExpect(this.tile00.edges.get(0),
      new Edge(this.game.board.get(0).get(0), this.game.board.get(0).get(1)));
  LightEmAll board32 = new LightEmAll(3, 2);
  // constructor runs fractal generation
  t.checkExpect(board32.board.get(0).get(0).edges.size(), 1);
  t.checkExpect(board32.board.get(0).get(0).edges.get(0),
      new Edge(board32.board.get(0).get(0), board32.board.get(0).get(1)));
  t.checkExpect(board32.board.get(2).get(0).edges.size(), 2);
  t.checkExpect(board32.board.get(2).get(0).edges.get(0),
      new Edge(board32.board.get(1).get(0), board32.board.get(2).get(0)));
  t.checkExpect(board32.board.get(2).get(0).edges.get(1),
      new Edge(board32.board.get(2).get(0), board32.board.get(2).get(1)));

}*/

  void testContains(Tester t) {
    this.initCondition();
    ArrayList<Edge> testPath = new ArrayList<Edge>();
    testPath.add(this.edge0);
    testPath.add(this.edge1);

    t.checkExpect(game.contains(testPath, this.edge0), true);
    t.checkExpect(game.contains(testPath, this.edge2), false);

  }

  void testAllEdges(Tester t) {
    this.initCondition();
    Random rand = new Random(0);
  
    this.game.path = new ArrayList<Edge>();
    t.checkExpect(this.game.allEdges(rand).size(), 4);
    t.checkExpect(this.game.allEdges(rand).get(0), 
           new Edge(this.game.board.get(0).get(0), this.game.board.get(0).get(1), 19));
    t.checkExpect(this.game.allEdges(rand).get(1), 
        new Edge(this.game.board.get(0).get(0), this.game.board.get(1).get(0), 75));
    t.checkExpect(this.game.allEdges(rand).get(2),  
        new Edge(this.game.board.get(0).get(1), this.game.board.get(1).get(1), 82));
    t.checkExpect(this.game.allEdges(rand).get(3),  
        new Edge(this.game.board.get(1).get(0), this.game.board.get(1).get(1), 53));
    this.bigGame.path = new ArrayList<Edge>();
    t.checkExpect(this.bigGame.allEdges(rand).size(), 24);
    this.nonSquareGame.path = new ArrayList<>();
    t.checkExpect(this.nonSquareGame.allEdges(rand).size(), 1);
    t.checkExpect(this.nonSquareGame.allEdges(rand),
        new ArrayList<>(Arrays.asList(new Edge(this.nonSquareGame.board.get(0).get(0), 
            this.nonSquareGame.board.get(1).get(0), 23))));
  }

  void testFind(Tester t) {
    this.initCondition();
    HashMap<GamePiece, GamePiece> testMap = new HashMap<GamePiece, GamePiece>();
    testMap.put(tile00, tile00);
    testMap.put(tile11, tile01);
    testMap.put(tile01, tile10);
    testMap.put(tile10, tile10);
    t.checkExpect(this.game.find(testMap, tile00), tile00);
    t.checkExpect(this.game.find(testMap, tile01), tile10);
    t.checkExpect(this.game.find(testMap, tile10), tile10);
    t.checkExpect(this.game.find(testMap, tile11), tile10);    
  }

  void testUnion(Tester t) {
    this.initCondition();

    Map<GamePiece, GamePiece> testMap = new HashMap<GamePiece, GamePiece>();
    GamePiece piece1 = new GamePiece(2, 4);
    testMap.put(piece1, piece1);
    GamePiece piece2 = new GamePiece(3, 5);

    this.game.union(testMap, piece1, piece2);
    t.checkExpect(testMap.get(piece1), piece2);
    
    HashMap<GamePiece, GamePiece> testMap2 = new HashMap<GamePiece, GamePiece>();
    testMap2.put(tile00, tile00);
    testMap2.put(tile01, tile01);
    testMap2.put(tile10, tile10);
    testMap2.put(tile11, tile11);  
    
    this.game.union(testMap2, tile00, tile01);
    t.checkExpect(testMap2.get(tile00), tile01);
    t.checkExpect(this.game.find(testMap2, tile00), tile01);
    
    this.game.union(testMap2, tile00, tile10);
    t.checkExpect(testMap2.get(tile00), tile10);
    t.checkExpect(this.game.find(testMap2, tile00), tile10);
    
    this.game.union(testMap2, tile11, tile00);
    this.game.union(testMap2, tile01, tile11);
    t.checkExpect(testMap2.get(tile11), tile00);
    t.checkExpect(testMap2.get(tile01), tile11);
    
  }

  void testkruskalGeneration(Tester t) {
    this.initCondition();
    Random rand = new Random();
    this.bigGame.path = new ArrayList<>();
    this.bigGame.kruskalGeneration(rand);
    t.checkExpect(this.bigGame.path.size(), 15);    

  }

  void testwinCondition(Tester t) {
    this.initCondition();
    LightEmAll testConnected = new LightEmAll(2, 2);
    t.checkExpect(testConnected.winCondition(), false);
    testConnected.board.get(0).get(0).isPowered = true;
    testConnected.board.get(0).get(1).isPowered = true;
    testConnected.board.get(1).get(0).isPowered = true;
    testConnected.board.get(1).get(1).isPowered = true;
    t.checkExpect(testConnected.winCondition(), true);
    testConnected.board.get(1).get(0).isPowered = false;
    t.checkExpect(testConnected.winCondition(), false);
  }

  void testOnMouseClicked(Tester t) {
    this.initCondition();
    game.onMouseClicked(new Posn(50, 50));
    t.checkExpect(game.board.get(0).get(0).bottom, true);
  } 

  
  void testMovePowerSource(Tester t) {
    this.initCondition();
    game.movePowerSource("b");
    t.checkExpect(game.board.get(0).get(0).powerStation, true);
    
    this.initCondition();
    game.movePowerSource("down");
    t.checkExpect(game.board.get(0).get(0).powerStation, false);
    t.checkExpect(game.board.get(0).get(1).powerStation, true);
    
    this.initCondition();
    t.checkExpect(game.board.get(0).get(0).powerStation, true);
    game.movePowerSource("right");
    t.checkExpect(game.board.get(0).get(0).powerStation, true);
    t.checkExpect(game.board.get(1).get(0).powerStation, false);
    
  }
  
  void testResetPower(Tester t) {
    this.initCondition();
    this.game.board.get(0).get(0).isPowered();
    this.game.board.get(0).get(1).isPowered();
    
    this.game.resetPower();
    t.checkExpect(this.game.board.get(0).get(0).isPowered, false);
    t.checkExpect(this.game.board.get(0).get(1).isPowered, false);
    
  }
  
  void testPowerPath(Tester t) {
    this.initCondition();
    this.game.powerPath();
    t.checkExpect(this.game.getDiameter(), 3);
    t.checkExpect((this.game.getDiameter() / 2) + 1, 2);
    t.checkExpect(this.game.radius, 2);
    t.checkExpect(this.game.board.get(game.powerCol).get(game.powerRow).isPowered, true);
    t.checkExpect(this.game.board.get(0).get(1).isPowered, true);
  }
  
  void testResetSeen(Tester t) {
    this.initCondition();
    this.game.board.get(0).get(1).seen = true;
    
    this.game.resetSeen();
    t.checkExpect(this.game.board.get(0).get(1).seen, false);
  }
  
  void testFarthestFromPower(Tester t) {
    this.initCondition();
    t.checkExpect(this.game.farthestFromPower(), this.game.board.get(1).get(0));
    t.checkExpect(this.nonSquareGame.farthestFromPower(), this.nonSquareGame.board.get(1).get(0));
    t.checkExpect(this.bigGame.farthestFromPower(), this.bigGame.board.get(2).get(0));
  }
  
  void testDepthFrom(Tester t) {
    Map<GamePiece, Integer> testHash = this.game.depthFrom(this.game.board.get(1).get(1));
    t.checkExpect(testHash.size(), 4);
    t.checkExpect(testHash.get(this.game.board.get(1).get(1)), 0);
    t.checkExpect(testHash.get(this.game.board.get(0).get(0)), 2);
    t.checkExpect(testHash.get(this.game.board.get(1).get(0)), 1);
    t.checkExpect(testHash.get(this.game.board.get(0).get(1)), 1);
  }
  
  void testRadius(Tester t) {
    this.initCondition();
    t.checkExpect(this.nonSquareGame.getDiameter(), 1);
    
    t.checkExpect(this.game.getDiameter(), 3);

    t.checkExpect(this.bigGame.getDiameter(), 11);
    LightEmAll test = new LightEmAll(6, 6);
    t.checkExpect(test.getDiameter(), 20);

    LightEmAll test2 = new LightEmAll(2, 3);
    t.checkExpect(test2.getDiameter(), 5);

  }
  
  void testDrawTile(Tester t) {
    this.initCondition();
    WorldImage base = new OverlayImage(
        new RectangleImage(LightEmAll.TILESIZE, LightEmAll.TILESIZE, OutlineMode.OUTLINE,
            Color.BLACK),
        new RectangleImage(LightEmAll.TILESIZE, LightEmAll.TILESIZE, OutlineMode.SOLID,
            Color.DARK_GRAY));
    WorldImage vertical = new LineImage(new Posn(0, LightEmAll.TILESIZE / 2), Color.WHITE);
    WorldImage horizontal = new RotateImage(vertical, 90.0);
    t.checkExpect(this.tileMT.draw(), base);
    t.checkExpect(this.tileL.draw(),
        new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, horizontal, 0, 0, base));
    t.checkExpect(this.tileT.draw(),
        new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP, vertical, 0, 0, base));
    t.checkExpect(this.tileLT.draw(),
        new OverlayOffsetAlign(AlignModeX.CENTER, AlignModeY.TOP, vertical, 0, 0,
            new OverlayOffsetAlign(AlignModeX.LEFT, AlignModeY.MIDDLE, horizontal, 0, 0, base)));
  }

  void testRotate(Tester t) {
    this.initCondition();
    t.checkExpect(this.tileL.left, true);
    this.tileL.rotate();
    t.checkExpect(this.tileL.left, false);
    t.checkExpect(this.tileL.top, true);
    this.tileL.rotate();
    t.checkExpect(this.tileL.top, false);
    t.checkExpect(this.tileL.right, true);

    t.checkExpect(this.tile.top, true);
    t.checkExpect(this.tile.right, true);
    this.tile.rotate();
    t.checkExpect(this.tile.right, true);

    t.checkExpect(this.tileMT.bottom, false);
    t.checkExpect(this.tileMT.left, false);
    this.tileMT.rotate();
    t.checkExpect(this.tileMT.bottom, false);
    t.checkExpect(this.tileMT.left, false);

    this.initCondition();
    t.checkExpect(this.game.board.get(0).get(0).left, false);
    t.checkExpect(this.game.board.get(0).get(0).bottom, true);
    this.game.board.get(0).get(0).rotate();
    t.checkExpect(this.game.board.get(0).get(0).left, true);
    t.checkExpect(this.game.board.get(0).get(0).bottom, false);

    this.initCondition();
    t.checkExpect(this.game.board.get(0).get(0).left, false);
    t.checkExpect(this.game.board.get(0).get(0).bottom, true);
    for (int i = 0; i < 4; i++) {
      this.game.board.get(0).get(0).rotate();
    }
    t.checkExpect(this.game.board.get(0).get(0).right, false);
    t.checkExpect(this.game.board.get(0).get(0).bottom, true);
    t.checkExpect(this.game.board.get(0).get(0).left, false);

  }

  void testAddEdge(Tester t) {
    this.initCondition();
    this.tileMT.col = 0;
    this.tileMT.row = 0;
    this.tileB.col = 0;
    this.tileB.row = 1;
    t.checkExpect(this.tileMT.right, false);
    this.tileMT.addEdge(new Edge(this.tileMT, this.tileB));
    t.checkExpect(this.tileMT.edges,
        new ArrayList<Edge>(Arrays.asList(new Edge(this.tileMT, this.tileB))));
    t.checkExpect(this.tileMT.bottom, true);

    this.initCondition();
    this.tile.col = 4;
    this.tile.row = 3;
    this.tileTB.col = 5;
    this.tileTB.row = 3;
    t.checkExpect(this.tileTB.left, false);
    this.tileTB.addEdge(new Edge(this.tileTB, this.tile));
    t.checkExpect(this.tileTB.left, true);
  }
  
  void testGoesTo(Tester t) {
    Edge edge1 = new Edge(this.tile00, this.tile01);
    Edge edge2 = new Edge(this.tile01, this.tile00);
    t.checkExpect(this.tile00.goesTo(edge1), this.tile01);
    t.checkExpect(this.tile00.goesTo(edge2), this.tile01);
    Edge edge3 = new Edge(this.tile10, this.tile11);
    t.checkExpect(this.tile10.goesTo(edge3), this.tile11);
  }

  void testIsConnected(Tester t) {
    this.initCondition();
    this.tile00.bottom = true;
    this.tile01.top = true;
    this.tile01.right = true;
    this.tile11.left = true;
    this.tile10.bottom = true;
    this.tile11.top = true;
    t.checkExpect(this.tile00.isConnected(this.tile01), true);
    t.checkExpect(this.tile01.isConnected(this.tile11), true);
    t.checkExpect(this.tile11.isConnected(this.tile10), true);
    t.checkExpect(this.tile00.isConnected(this.tile10), false);
    t.checkExpect(this.tile00.isConnected(this.tile11), false);
    this.tile00.rotate();
    t.checkExpect(this.tile00.isConnected(this.tile10), false);
    this.tile10.rotate();
    this.tile00.rotate();
    this.tile00.rotate();
    t.checkExpect(this.tile00.isConnected(this.tile10), true);
  }
  
  void testLineColor(Tester t) {
    this.initCondition();
    this.game.board.get(0).get(1).lineColor(5, 9);
    t.checkExpect(this.game.board.get(0).get(1).lineColor, Color.ORANGE);
  }
  
  void testAddPowerSource(Tester t) {
    this.initCondition();
    t.checkExpect(this.tile.powerStation, false);
    this.tile.changePowerSource();
    t.checkExpect(this.tile.powerStation, true);
    this.tile.changePowerSource();
    t.checkExpect(this.tile.powerStation, false);

  }

  void testHeapSort(Tester t) {
    HeapSort<Integer> heapSort = new HeapSort<>();
    Comparator<Integer> comp = new CompareInt();

    List<Integer> list = new ArrayList<>(Arrays.asList(4, 6, 3, 7, 2, 11, 1, 19));
    List<Integer> list2 = new ArrayList<>(Arrays.asList(
        20,19,18,17,16,15,14,13,12,11,10,5,8,4,3,9,2,1,6,7));
    
    List<Integer> empty = new ArrayList<>();
    List<Integer> empty2 = new ArrayList<>();
    List<Integer> alreadySorted = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    List<Integer> alreadySorted2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    
    List<Integer> resultList2 = new ArrayList<Integer>(
        Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));
    heapSort.buildHeap(list, comp);
    t.checkExpect(list, 
        new ArrayList<Integer>(Arrays.asList(1,2,3,7,6,11,4,19)));
    t.checkExpect(heapSort.heapsort(list, comp), 
        new ArrayList<Integer>(Arrays.asList(1,2,3,4,6,7,11,19)));
    t.checkExpect(heapSort.heapsort(list2, comp), resultList2); 
    
    t.checkExpect(heapSort.heapsort(empty, comp), empty2);
    
    t.checkExpect(heapSort.heapsort(alreadySorted, comp), alreadySorted2);
    

  }

  void testPower(Tester t) {
    this.initCondition();
    this.tile00.isPowered();
    t.checkExpect(this.tile00.isPowered, true);
    
    this.tile01.powerOff();
    t.checkExpect(this.tile01.isPowered, false);
  }
   
  
  void testSeen(Tester t) {
    t.checkExpect(this.tile00.seen, false);
    this.tile00.seen();
    t.checkExpect(this.tile00.seen, true);
  }

  //XXX
  void testWorld(Tester t) {
    //LightEmAll testGame = new LightEmAll(2,1);
    LightEmAll testGame2 = new LightEmAll(16, 16);
    //LightEmAll testFinal = new LightEmAll(16, 16, "horizontal");
    //    this.bigGame.path = new ArrayList<Edge>();
    //    this.bigGame.kruskalGeneration(new Random(0));
    //testGame.bigBang(LightEmAll.TILESIZE * testGame.width, LightEmAll.TILESIZE * testGame.height);

    LightEmAll verticalGame = new LightEmAll(12, 12, "vertical");
    LightEmAll horizontalGame = new LightEmAll(15, 15, "horizontal");
    LightEmAll unbiasedGame = new LightEmAll(15, 15);
    verticalGame.bigBang(LightEmAll.TILESIZE * verticalGame.width,
        LightEmAll.TILESIZE * verticalGame.height + 100, 1);

  }



}
