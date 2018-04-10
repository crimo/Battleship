/**
 * @author		Chris Moore <cmoore12@radford.edu>
 * @version 	1.0
 * @since		2012-04-22
 */
public class BattleshipModel {
  private boolean[][] occupied = new boolean[10][10];	//grid spaces occupied by a ship
  private boolean[][] hit = new boolean[10][10];  //grid spaces that have been fired upon
  private int shipsLeft =6; //total number of ships used
  private int totalHealth=0;  //used to determine total number of ship spaces occupying grid
  private boolean horizontal = true; //keeps track of horizontal or vertical placement
  
  public BattleshipModel(){
  this.initializeArrays();
  this.setTotalHealth();
}

public void initializeArrays(){
	for (int i=0; i<10; i++){
		for (int j=0; j<10; j++){
			this.setOccupied(i,j,false);
			this.setHit(i,j,false);
		}
	}
}


//updates hit array when a shot is fired
public void updateHit(String location){
    location = checklocationString(location);
    int row = parseRow(location);
    int column = parseColumn(location);
	this.setHit(row, column, true);
	if (occupied[row][column]==true){
		totalHealth--;	
	}
	if (totalHealth==0)
		BattleshipController.winner(this);
}

//finds the total amount of spaces each players ships occupy,
//used for determining a winner
public void setTotalHealth(){
	for (int i=shipsLeft-1; i>=0; i--){
		totalHealth += shipsLeft-i;
		//System.out.println(totalHealth);
	}
}

//converts location String into integer, places ship in appropriate array spaces
public boolean setModelPlacement(String location){
	//System.out.println("entering view setModelPlacement");
    location = checklocationString(location);
    int row = parseRow(location);
    int column = parseColumn(location);
    int shipSize = this.getShipSize();
	boolean goodPlacement = false;
	
	
	if (horizontal){ //code for horizontal ship placement
	
	  if (shipSize <= 10-column && shipSize>0){ //check to see if ship can fit horizontally
		  goodPlacement = true;
  		  for (int i=0; i<shipSize; i++){
	  		if (this.getOccupied(row, column+i)==true){
		  	  goodPlacement = false;	
			}
		  }
	    if (goodPlacement == true){  //places ship by changing occupied array positions to true
		  for (int i=0;i<shipSize;i++){
		    this.setOccupied(row, column+i, true);	
		  }
		  this.decrementSize();
		}
	  }		
	}
	
	else{  //code for vertical ship placement
		 //check to see if ship can fit vertically
		if (shipSize<=10-row)  {
			goodPlacement = true;
			for (int i=0; i<shipSize; i++){
				if(this.getOccupied(row+i, column)==true){
					goodPlacement = false;
				}
			}
		 
		  
		  if (goodPlacement==true){ //places ship by changing occupied array positions to true
		    for (int i=0;i<shipSize;i++){
			    this.setOccupied(row+i, column, true);
		    }
		    this.decrementSize();
		  }
		}  
	  }
		
	//System.out.println("returning " + goodPlacement);
	return goodPlacement;	
}

//decrements amount of ships left to place
private void decrementSize(){
	//System.out.println("entering decrement size");
	   this.shipsLeft--;
	   System.out.println("shipsleft = " + shipsLeft);
	  if (this.getShipSize()<=0){
		   BattleshipController.changeTurn();
	  }
	 // System.out.println("leaving decrementSize");
}


public void changeOrientation(){
	this.horizontal = !horizontal;
}

//if a shot is fired from grid location 0-9, a second digit is added to the String representing that location
private String checklocationString(String location){
	if (location.length()==1)
		location = "0" + location;
	return location;
}

private int parseRow(String location){
	int row = Integer.parseInt(location.substring(0,1));
	return row;
}
private int parseColumn(String location){
	int column = Integer.parseInt(location.substring(1));
	return column;
}
public int getShipSize(){
	   return this.shipsLeft;
}

    private void setOccupied(int row, int column, boolean status){
    	this.occupied[row][column] = status;
    }
    private void setHit(int row, int column, boolean status){
    	this.hit[row][column] = status;
    }
  
   public boolean getHit(int row, int column){
	   return this.hit[row][column];
   }
   public boolean getOccupied(int row, int column){
	   return this.occupied[row][column];
   }

   public void resetSize(){
	   this.shipsLeft = 5;
   }
}