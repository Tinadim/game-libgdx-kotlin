package com.reis.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.reis.game.contants.GameConstants;
import com.reis.game.entity.GameEntity;
import com.reis.game.entity.components.AiComponent;
import com.reis.game.entity.components.BodyComponent;
import com.reis.game.entity.components.CombatComponent;
import com.reis.game.entity.components.MovementComponent;
import com.reis.game.entity.components.SpriteComponent;
import com.reis.game.entity.player.Player;
import com.reis.game.input.InputHandler;
import com.reis.game.mechanics.collision.Collision;
import com.reis.game.mechanics.collision.CollisionListener;
import com.reis.game.mechanics.collision.CollisionManager;
import com.reis.game.mechanics.collision.CollisionTrigger;
import com.reis.game.prototypes.AiData;
import com.reis.game.state.State;
import com.reis.game.state.events.EventProcessor;
import com.reis.game.state.quests.QuestManager;
import com.reis.game.util.Filter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Main extends ApplicationAdapter {

	private static Main instance = null;

	public Stage stage;
	public ShapeRenderer shapeRenderer;
	public GameEntity entity = Player.INSTANCE;
	public GameEntity entity2 = new GameEntity(2);
	public CollisionTrigger trigger;
	public CollisionManager collisionManager = new CollisionManager();
	public QuestManager questManager = new QuestManager();
	public EventProcessor eventProcessor = new EventProcessor();

	public static Main getInstance() {
		return Main.instance;
	}

	@Override
	public void create () {
		Main.instance = this;

		State initialState = new State();
		questManager.loadQuests(initialState);

		AiData data = new AiData();
		shapeRenderer = new ShapeRenderer();
		stage = new Stage(new ScreenViewport());
		entity.addComponent(new BodyComponent(entity, buildCollisionListener()));
		entity.addComponent(new CombatComponent(entity));
		entity.addComponent(new SpriteComponent(entity, Color.WHITE));
		entity.addComponent(new AiComponent(entity, data));
		entity.addComponent(new MovementComponent(entity));

		entity2.setCoordinates(5, 5);
		entity2.addComponent(new BodyComponent(entity2));
		entity2.addComponent(new SpriteComponent(entity2, Color.BLUE));
		entity2.addComponent(new CombatComponent(entity2));

		trigger = new CollisionTrigger(3);
		trigger.addComponent(new SpriteComponent(trigger, Color.GREEN));
		trigger.setCoordinates(10, 10);

		stage.addActor(entity);
		stage.addActor(entity2);
		stage.addActor(trigger);

		((BodyComponent) entity2.getComponent(BodyComponent.class)).bindTiles();
		((BodyComponent) trigger.getComponent(BodyComponent.class)).bindTiles();
		Gdx.input.setInputProcessor(InputHandler.INSTANCE);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawGrid();
		stage.act(Gdx.graphics.getDeltaTime());
		collisionManager.update();
		eventProcessor.update();
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

	private CollisionListener buildCollisionListener() {
		return new CollisionListener() {
			@Nullable
			@Override
			public Filter<GameEntity> getFilter() {
				return null;
			}

			@Override
			public void onCollisionStarted(@NotNull Collision collision) {
				GameEntity enemy = collision.getCollidedWith();
				MovementComponent movement = collision.getEntity().getComponent(MovementComponent.class);
				CombatComponent component = enemy.getComponent(CombatComponent.class);
				if (component != null) {
					component.applyTouchDamageToEntity(collision.getEntity(), new Vector2(movement.getVelocity()).scl(-1f));
				}
			}

			@Override
			public void onCollisionEnded(@NotNull Collision collision) {
				System.out.println("Collision with player ended");
			}
		};
	}
}
