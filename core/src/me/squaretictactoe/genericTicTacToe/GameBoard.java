package me.squaretictactoe.genericTicTacToe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameBoard extends Actor
{
    private int boardStates[];
    private int boardSize;
    private Texture emptyTile, oTile, xTile;
    private Sprite emptySprite, oSprite, xSprite;
    private Sprite spriteArray[];
    private float tileDimension;
    private GenericTicTacToe ticTacToeBoard;
    private final static int EMPTY = 0;
    private final static int PLAYER = 1;
    private final static int COMPUTER = 2;
    private boolean gameOver;
    private Pixmap pixmap;
    private Texture winningLine;
    private int gameState;

    public GameBoard(int bSize)
    {
        this.boardSize = bSize;
        tileDimension = getTileDimension(boardSize);

        emptyTile = new Texture("square.png");
        oTile = new Texture("alphabet_O.png");
        xTile = new Texture("alphabet_X.png");
        emptySprite = new Sprite(emptyTile);
        oSprite = new Sprite(oTile);
        xSprite = new Sprite(xTile);
        spriteArray = new Sprite[]{emptySprite,oSprite,xSprite};

        ticTacToeBoard = new GenericTicTacToe(boardSize);
        gameOver = false;

        boardStates = new int[boardSize*boardSize];
        pixmap = new Pixmap(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        winningLine = new Texture(pixmap);

        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                int index = i*boardSize + j;
                boardStates[index] = EMPTY;
            }
        }

        //initial move - uncomment below lines if computer should make first move
        //int index = ticTacToeBoard.getComputerMove();
        //boardStates[index] = COMPUTER;

        setBounds(0,0,tileDimension*boardSize,tileDimension*boardSize);
        setTouchable(Touchable.enabled);

        addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                int index = (int) x / (int) tileDimension + ((int) y / (int) tileDimension * boardSize);

                if (boardStates[index] == EMPTY && gameOver == false)
                {
                    boardStates[index] = PLAYER;
                    ticTacToeBoard.setPlayerMove(index);

                    gameState = ticTacToeBoard.getGameState();
                    if (gameState == GenericTicTacToe.GAME_INPLAY)
                    {
                        index = ticTacToeBoard.getComputerMove();
                        boardStates[index] = COMPUTER;
                    }

                    gameState = ticTacToeBoard.getGameState();
                    if (gameState != GenericTicTacToe.GAME_INPLAY)
                        gameOver = true;

                    System.out.println("gameState: " + gameState);
                }

                super.clicked(event, x, y);
            }
        });
    }

    public static float getTileDimension(int boardSize)
    {
        if(Gdx.graphics.getWidth() <= Gdx.graphics.getHeight())
            return Gdx.graphics.getWidth()/(boardSize+1);
        else
            return Gdx.graphics.getHeight()/(boardSize+1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        for(int i = 0; i < boardSize; i++)
        {
            for(int j = 0; j < boardSize; j++)
            {
                int index = i*boardSize + j;
                batch.draw(spriteArray[boardStates[index]],j * tileDimension, i * tileDimension, tileDimension, tileDimension);
            }
        }

        if(gameOver == true && (gameState == GenericTicTacToe.GAME_PLAYER_WINS || gameState == GenericTicTacToe.GAME_COMPUTER_WINS) )
        {
            int winningArray[] = ticTacToeBoard.getWinningIndices();
            float x1, x2, y1, y2;
            int row, col;

            col = winningArray[0]/boardSize;
            row = winningArray[0]%boardSize;
            x1 = (row * tileDimension) + tileDimension/2;
            y1 = Gdx.graphics.getHeight() - ((col * tileDimension) + tileDimension/2);

            col = winningArray[boardSize-1]/boardSize;
            row = winningArray[boardSize-1]%boardSize;
            x2 = (row * tileDimension) + tileDimension/2;
            y2 = Gdx.graphics.getHeight() - ((col * tileDimension) + tileDimension/2);

            pixmap.setColor(Color.BLUE);
            pixmap.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            winningLine.draw(pixmap, 0, 0);

            batch.draw(winningLine,0,0);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

}
