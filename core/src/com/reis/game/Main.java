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
import com.reis.game.entity.ai.controllers.ManualController;
import com.reis.game.entity.components.EntityControllerComponent;
import com.reis.game.entity.components.BodyComponent;
import com.reis.game.entity.components.CombatComponent;
import com.reis.game.entity.components.InteractionComponent;
import com.reis.game.entity.components.MovementComponent;
import com.reis.game.entity.components.SpriteComponent;
import com.reis.game.entity.player.Player;
import com.reis.game.input.InputHandler;
import com.reis.game.mechanics.battle.weapons.Bow;
import com.reis.game.mechanics.battle.weapons.Sword;
import com.reis.game.mechanics.collision.Collision;
import com.reis.game.mechanics.collision.CollisionListener;
import com.reis.game.mechanics.collision.CollisionManager;
import com.reis.game.mechanics.collision.CollisionTrigger;
import com.reis.game.prototypes.AiData;
import com.reis.game.resources.ResourceManager;
import com.reis.game.scene.dialog.DialogManager;
import com.reis.game.scene.dialog.DialogWindow;
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
	public GameEntity entity3 = new GameEntity(4);
	public CollisionTrigger trigger;
	public ResourceManager resourceManager;
	public CollisionManager collisionManager = new CollisionManager();
	public QuestManager questManager = new QuestManager();
	public DialogManager dialogManager = new DialogManager();
	public EventProcessor eventProcessor = new EventProcessor();

	public static Main getInstance() {
		return Main.instance;
	}

	@Override
	public void create () {
		Main.instance = this;

		resourceManager = new ResourceManager();
		State initialState = new State();
		questManager.loadQuests(initialState);
		dialogManager.loadDialogs();

		shapeRenderer = new ShapeRenderer();
		stage = new Stage(new ScreenViewport());

		CombatComponent playerCombatComponent = new CombatComponent(entity, 1);
		playerCombatComponent.setContactDamage(0);
		playerCombatComponent.setPrimaryDamageSource(new Sword());
		ManualController controller = new ManualController(entity);
		entity.addComponent(new BodyComponent(entity));
		entity.addComponent(playerCombatComponent);
		entity.addComponent(new SpriteComponent(entity, Color.WHITE));
		entity.addComponent(new EntityControllerComponent(entity, controller));
		entity.addComponent(new MovementComponent(entity));
		entity.addComponent(new InteractionComponent(entity));

		entity2.setCoordinates(5, 5);
		entity2.addComponent(new BodyComponent(entity2));
		entity2.addComponent(new SpriteComponent(entity2, Color.BLUE));
		entity2.addComponent(new CombatComponent(entity2, 2));

		entity3.setCoordinates(10, 5);
		entity3.addComponent(new BodyComponent(entity3));
		entity3.addComponent(new InteractionComponent(entity3));
		entity3.addComponent(new SpriteComponent(entity3, Color.MAGENTA));

		trigger = new CollisionTrigger(3);
		trigger.addComponent(new SpriteComponent(trigger, Color.GREEN));
		trigger.setCoordinates(10, 10);

		stage.addActor(entity);
		stage.addActor(entity2);
		stage.addActor(entity3);
		stage.addActor(trigger);

		((BodyComponent) entity2.getComponent(BodyComponent.class)).bindTiles();
		((BodyComponent) entity3.getComponent(BodyComponent.class)).bindTiles();
		((BodyComponent) trigger.getComponent(BodyComponent.class)).bindTiles();
		Gdx.input.setInputProcessor(new InputHandler(controller));
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

	public void addDialog(DialogWindow window) {
		this.stage.addActor(window);
	}
}
