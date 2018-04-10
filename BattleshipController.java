/**
 * @author		Chris Moore <cmoore12@radford.edu>
 * @version 	1.0
 * @since		2012-04-22
 */
public class BattleshipController {
static BattleshipModel model1;
static BattleshipModel model2;
static BattleshipView view1;
static BattleshipView view2;
static boolean player1turn = true;

	//creates two views, and begins game 
	public BattleshipController(){
		view1 = new BattleshipView();
		view2 = new BattleshipView();
		view2.setLocation(630,100);
		view1.setVisible(true);
		view2.setVisible(false);
		BattleshipController.beginGame();
	}

	
	public static void beginGame(){
		model1 = new BattleshipModel();
		model2 = new BattleshipModel();
		System.out.println("New Game\nTime to place your ships");
		view1.updateLabel(model1);
		view2.updateLabel(model2);
	}
	
	//method is called by view to determine a winner
	public static void winner(BattleshipModel winner){
		if (winner == model1){
			view1.winner(2);
		}
		else{
			view2.winner(1);
		}
		
	}
	
	
//called by view actionListener, determines what model to update with ship placement
public static boolean placeShip(String position){
  boolean validPlacement = false;
  if (player1turn == true){
	 if (model1.setModelPlacement(position)){
		 validPlacement = true;}
  }
  else{
	  if (model2.setModelPlacement(position)){
	     validPlacement = true;}
  }
  BattleshipController.doneChecking();
  view1.updateLabel(model1);
  view2.updateLabel(model2);
  return validPlacement;
}

//changes placement of ships between vertical and horizontal
public static void changeOrientation(){
	if (player1turn){
		model1.changeOrientation();
	}
	else{
		model2.changeOrientation();
	}
}

//when all ships are placed and game is ready to begin, both view modes are changed
  public static void doneChecking(){
	  if (model1.getShipSize()<=0 && model2.getShipSize()<=0){
		  view1.changeMode();
		  view2.changeMode();
	  } 
  }
	
//determines what model and view to update when a shot is fired  
public static void shotFired(String shotPosition){
	if (player1turn == true){
      model2.updateHit(shotPosition);
      view2.updateLabel(model2);
      view1.updateButtons(model2);
	}
	else{
	  model1.updateHit(shotPosition);
	  view1.updateLabel(model1);
	  view2.updateButtons(model1);
	}
	BattleshipController.changeTurn();
}	


public static boolean getTurn(){
	return player1turn;
}

//changes turn between players, opens and hides the appropriate windows
public static void changeTurn(){
	player1turn = !player1turn;
	if (player1turn == true){
		view2.setVisible(false);
		view1.showTurn(1);
		view1.setVisible(true);
	}
	else{
		view1.setVisible(false);
		view2.showTurn(2);
		view2.setVisible(true);
		
	}
	System.out.println("player1turn = " + player1turn);
}
}//end class