package com.reis.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.reis.game.contants.GameConstants;
import com.reis.game.entity.GameEntity;
import com.reis.game.entity.ai.controllers.AI;
import com.reis.game.entity.ai.controllers.ManualController;
import com.reis.game.entity.components.AnimationComponent;
import com.reis.game.entity.components.EntityControllerComponent;
import com.reis.game.entity.components.BodyComponent;
import com.reis.game.entity.components.CombatComponent;
import com.reis.game.entity.components.InteractionComponent;
import com.reis.game.entity.components.MovementComponent;
import com.reis.game.entity.components.SpriteComponent;
import com.reis.game.entity.player.Player;
import com.reis.game.graphics.AnimationCache;
import com.reis.game.graphics.TextureManager;
import com.reis.game.input.InputHandler;
import com.reis.game.mechanics.battle.weapons.TestWeapon;
import com.reis.game.mechanics.collision.CollisionManager;
import com.reis.game.mechanics.collision.CollisionTrigger;
import com.reis.game.prototypes.AnimationProto;
import com.reis.game.resources.ResourceManager;
import com.reis.game.scene.dialog.DialogManager;
import com.reis.game.scene.dialog.DialogWindow;
import com.reis.game.state.State;
import com.reis.game.state.events.EventProcessor;
import com.reis.game.state.quests.QuestManager;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {

	private static Main instance = null;

	public Stage stage;
	public ShapeRenderer shapeRenderer;
	public GameEntity player = Player.INSTANCE;
	public GameEntity entity2 = new GameEntity(2);
	public GameEntity femaleVillager = new GameEntity(4);
	public CollisionTrigger trigger;
	public ResourceManager resourceManager;
	public CollisionManager collisionManager = new CollisionManager();
	public QuestManager questManager = new QuestManager();
	public DialogManager dialogManager = new DialogManager();
	public EventProcessor eventProcessor = new EventProcessor();
	public AnimationCache animationCache = new AnimationCache();
	public TextureManager textureManager = new TextureManager();

	public com.reis.game.event.EventProcessor newEventProcessor = new com.reis.game.event.EventProcessor();

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

		CombatComponent playerCombatComponent = new CombatComponent(player, 1);
		playerCombatComponent.setContactDamage(0);
		playerCombatComponent.setPrimaryDamageSource(new TestWeapon());
		ManualController controller = new ManualController(player);
        player.addComponent(new BodyComponent(player));
        player.addComponent(playerCombatComponent);
        player.addComponent(new SpriteComponent(player, Color.WHITE));
        player.addComponent(new EntityControllerComponent(player, controller));
        player.addComponent(new MovementComponent(player));
        player.addComponent(new InteractionComponent(player));

		entity2.setCoordinates(5, 5);
		entity2.addComponent(new BodyComponent(entity2));
		entity2.addComponent(new SpriteComponent(entity2, Color.BLUE));
		entity2.addComponent(new CombatComponent(entity2, 2));

        AI ai = new AI(femaleVillager, new com.reis.game.entity.ai.states.State());
		femaleVillager.setCoordinates(0, 1);
		femaleVillager.addComponent(new BodyComponent(femaleVillager));
		femaleVillager.addComponent(new InteractionComponent(femaleVillager));
		femaleVillager.addComponent(new AnimationComponent(femaleVillager, buildMockAnimation()));
        femaleVillager.addComponent(new EntityControllerComponent(femaleVillager, ai));

		trigger = new CollisionTrigger(3);
		trigger.addComponent(new SpriteComponent(trigger, Color.GREEN));
		trigger.setCoordinates(10, 10);

		stage.addActor(player);
		stage.addActor(entity2);
		stage.addActor(femaleVillager);
		stage.addActor(trigger);

		((BodyComponent) entity2.getComponent(BodyComponent.class)).bindTiles();
		((BodyComponent) femaleVillager.getComponent(BodyComponent.class)).bindTiles();
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

	private AnimationProto.AnimationData buildMockAnimation() {
		AnimationProto.AnimationPrototype ad0 = AnimationProto.AnimationPrototype
				.newBuilder()
				.setActionClassName("Movement")
				.setEntityOrientation(0)
				.setAtlasName("female villager/female_villager.atlas")
				.setAnimationName("female_villager_0")
                .setFrameOffsetX(8 + 21 * -0.5f)
				.setFrameDuration(0.25f)
				.build();

		AnimationProto.AnimationPrototype ad1 = AnimationProto.AnimationPrototype
				.newBuilder()
				.setActionClassName("Movement")
				.setEntityOrientation(1)
				.setAtlasName("female villager/female_villager.atlas")
				.setAnimationName("female_villager_1")
                .setFrameOffsetX(8 + 21 * -0.5f)
				.setFrameDuration(0.25f)
				.build();

		AnimationProto.AnimationPrototype ad2 = AnimationProto.AnimationPrototype
				.newBuilder()
				.setActionClassName("Movement")
				.setEntityOrientation(2)
				.setAtlasName("female villager/female_villager.atlas")
				.setAnimationName("female_villager_2")
                .setFrameOffsetX(8 + 21 * -0.5f)
				.setFrameDuration(0.25f)
				.build();

		AnimationProto.AnimationPrototype ad3 = AnimationProto.AnimationPrototype
				.newBuilder()
				.setActionClassName("Movement")
				.setEntityOrientation(3)
				.setAtlasName("female villager/female_villager.atlas")
				.setAnimationName("female_villager_3")
                .setFrameOffsetX(8 + 21 * -0.5f)
				.setFrameDuration(0.25f)
				.build();

		AnimationProto.AnimationPrototype ad4 = AnimationProto.AnimationPrototype
				.newBuilder()
				.setActionClassName("Idle")
				.setEntityOrientation(0)
				.setAtlasName("female villager/female_villager.atlas")
				.setAnimationName("female_villager_0")
                .setFrameOffsetX(8 + 21 * -0.5f)
				.setFrameDuration(0.25f)
				.build();

		AnimationProto.AnimationPrototype ad5 = AnimationProto.AnimationPrototype
				.newBuilder()
				.setActionClassName("Idle")
				.setEntityOrientation(1)
				.setAtlasName("female villager/female_villager.atlas")
				.setAnimationName("female_villager_1")
                .setFrameOffsetX(8 + 21 * -0.5f)
				.setFrameDuration(0.25f)
				.build();

		AnimationProto.AnimationPrototype ad6 = AnimationProto.AnimationPrototype
				.newBuilder()
				.setActionClassName("Idle")
				.setEntityOrientation(2)
				.setAtlasName("female villager/female_villager.atlas")
				.setAnimationName("female_villager_2")
                .setFrameOffsetX(8 + 21 * -0.5f)
				.setFrameDuration(0.25f)
				.build();

		AnimationProto.AnimationPrototype ad7 = AnimationProto.AnimationPrototype
				.newBuilder()
				.setActionClassName("Idle")
				.setEntityOrientation(3)
				.setAtlasName("female villager/female_villager.atlas")
				.setAnimationName("female_villager_3")
                .setFrameOffsetX(8 + 21 * -0.5f)
				.setFrameDuration(0.25f)
				.build();

		ArrayList<AnimationProto.AnimationPrototype> data = new ArrayList<AnimationProto.AnimationPrototype>();
		data.add(ad0);
		data.add(ad1);
		data.add(ad2);
		data.add(ad3);
		data.add(ad4);
		data.add(ad5);
		data.add(ad6);
		data.add(ad7);

		AnimationProto.AnimationData animationData = AnimationProto.AnimationData
				.newBuilder()
				.addAllAnimationPrototype(data)
				.build();
		return animationData;
	}

	public void addDialog(DialogWindow window) {
		this.stage.addActor(window);
	}
}
