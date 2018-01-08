package com.reis.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.reis.game.contants.GameConstants;
import com.reis.game.entity.GameEntity;
import com.reis.game.entity.components.AiComponent;
import com.reis.game.entity.components.BodyComponent;
import com.reis.game.entity.components.MovementComponent;
import com.reis.game.entity.components.SpriteComponent;
import com.reis.game.input.InputHandler;
import com.reis.game.mechanics.collision.CollisionManager;
import com.reis.game.prototypes.AiData;

public class Main extends ApplicationAdapter {

	private Stage stage;
	private ShapeRenderer shapeRenderer;

	private GameEntity entity = new GameEntity(1);
	private GameEntity entity2 = new GameEntity(2);

	@Override
	public void create () {
		AiData data = new AiData();
		shapeRenderer = new ShapeRenderer();
		stage = new Stage(new ScreenViewport());
		entity.addComponent(new BodyComponent(entity));
		entity.addComponent(new SpriteComponent(entity, Color.WHITE));
		entity.addComponent(new AiComponent(entity, data));
		entity.addComponent(new MovementComponent(entity));

		entity2.setCoordinates(5, 5);
		entity2.addComponent(new BodyComponent(entity2));
		entity2.addComponent(new SpriteComponent(entity2, Color.YELLOW));

		((BodyComponent) entity2.getComponent(BodyComponent.class)).bindTiles();
		Gdx.input.setInputProcessor(InputHandler.INSTANCE);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawGrid();
		CollisionManager.update();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.addActor(entity);
		stage.addActor(entity2);
		stage.draw();
	}
	
	@Override
	public void dispose () {

	}

	private void drawGrid() {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.BLUE);
		int cols = (int) stage.getWidth() / GameConstants.TILE_SIZE;
		int rows = (int) stage.getHeight() / GameConstants.TILE_SIZE;
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				int x = i * GameConstants.TILE_SIZE;
				int y = j * GameConstants.TILE_SIZE;
				shapeRenderer.rect(x, y, GameConstants.TILE_SIZE, GameConstants.TILE_SIZE);
			}
		}
		shapeRenderer.end();
	}
}
