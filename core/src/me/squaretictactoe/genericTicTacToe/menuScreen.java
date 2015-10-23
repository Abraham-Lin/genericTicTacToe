package me.squaretictactoe.genericTicTacToe;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class menuScreen extends Game
{
	private Stage stage;
	private TextButton selectMatrix[];
    private TextButton playAgain, menuButton;
	private TextButton.TextButtonStyle matrixStyle, buttonStyle;
    private int matrixSize = 9;
    private int boardSize;
    private GameBoard boardActor;

	@Override
	public void create()
	{
		Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.setTitle("Tic Tac Toe");

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

        selectTicTacToeBoardSize();

        for(int i = 0; i < matrixSize; i++)
        {
            final int tictactoeSize = i + 3;
            selectMatrix[i].addListener(new ChangeListener()
            {
                @Override
                public void changed(ChangeEvent event, Actor actor)
                {
                    clearMatrix(tictactoeSize);
                    boardSize = tictactoeSize;
                    boardActor = new GameBoard(tictactoeSize);
                    stage.addActor(boardActor);
                    stage.setKeyboardFocus(boardActor);
                }
            });
        }

        playAgain.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                if(boardActor != null)
                {
                    clearMatrix(boardSize);

                    boardActor.remove();
                    boardActor = new GameBoard(boardSize);
                    stage.addActor(boardActor);
                    stage.setKeyboardFocus(boardActor);
                }
            }
        });

        menuButton.addListener(new ChangeListener()
        {
            @Override
            public void changed(ChangeEvent event, Actor actor)
            {
                if(boardActor != null)
                    boardActor.remove();

                clearMatrix(3);

                for(int i = 0; i < matrixSize; i++)
                    stage.addActor(selectMatrix[i]);
            }
        });
	}

	private void selectTicTacToeBoardSize()
	{
        matrixStyle = new TextButton.TextButtonStyle();
        matrixStyle.font = new BitmapFont();
        matrixStyle.fontColor = new Color(Color.BLUE);
        matrixStyle.up = new Image(new Texture("square.png")).getDrawable();
        selectMatrix = new TextButton[matrixSize];

        int row = 0;
        int col = 0;
        int colSize = 3;
        float tileDimension = GameBoard.getTileDimension(colSize);

        for(int i = 0; i < matrixSize; i++)
        {
            selectMatrix[i] = new TextButton(String.valueOf(i + 3), matrixStyle);
            selectMatrix[i].setPosition(col * tileDimension, row * tileDimension);
            selectMatrix[i].setWidth(tileDimension);
            selectMatrix[i].setHeight(tileDimension);
            stage.addActor(selectMatrix[i]);

            if((i+1)%colSize == 0)
            {
                col = 0;
                row++;
            }
            else
                col++;
        }

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = new BitmapFont();
        buttonStyle.fontColor = new Color(Color.MAROON);
        buttonStyle.up = new Image(new Texture("square.png")).getDrawable();

        menuButton = new TextButton("Menu", buttonStyle);
        playAgain = new TextButton("Play Again", buttonStyle);

        stage.addActor(menuButton);
        stage.addActor(playAgain);
        setButtonParameters(row, colSize, tileDimension);
	}

    private void setButtonParameters(int row, int col, float tileDimension)
    {
        menuButton.setPosition(0, row * tileDimension);
        menuButton.setWidth(tileDimension * col/2);
        menuButton.setHeight(tileDimension);

        playAgain.setPosition(tileDimension * col / 2, row * tileDimension);
        playAgain.setWidth(tileDimension * col / 2);
        playAgain.setHeight(tileDimension);
    }

    private void clearMatrix(int size)
    {
        for(int i = 0; i < matrixSize; i++)
            selectMatrix[i].remove();

        float tileDimension = GameBoard.getTileDimension(size);
        setButtonParameters(size, size, tileDimension);
    }

	@Override
	public void render ()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
}
