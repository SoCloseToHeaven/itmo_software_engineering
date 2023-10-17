package com.soclosetoheaven.client.ui.model;

import com.soclosetoheaven.common.model.Coordinates;
import com.soclosetoheaven.common.model.Dragon;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DragonMapModel extends JLabel {

    private final Dragon dragon;


    private static final int DEFAULT_WIDTH = 190;

    private static final int DEFAULT_HEIGHT = 145;

    private static final int ANIMATION_CHANGE_TIME = 400;

    private static final int UPDATE_TIMER_DELAY = 100;


    private static final Icons[] FLYING_ANIMATION_ICONS = {
            Icons.FLYING_FIRST_STAGE,
            Icons.FLYING_SECOND_STAGE,
            Icons.FLYING_THIRD_STAGE};
    public DragonMapModel(Dragon dragon) {
        setVisible(false);
        setEnabled(false);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.dragon = dragon;
        this.setIcon(Icons.LAYING.getIcon());
    }

    public Dragon getDragon() {
        return dragon;
    }

    public void updateDragon(Dragon updated) {
        // TODO: Добавить обратные иконки при отрицательном deltaX
        if (updated.getCoordinates().equals(dragon.getCoordinates())) {
            dragon.update(updated);
            return;
        }
        final int startX = getX();
        final int startY = getY();
        dragon.update(updated);
        final int newX = dragon.getCoordinates().getX();
        final int newY = dragon.getCoordinates().getY();

        final int deltaX = newX - startX;
        final int deltaY = newY - startY;

        final int zero = 0;

        final int stepX = (int) Math.floor(deltaX / (double) UPDATE_TIMER_DELAY);
        final int stepY = (int) Math.floor(deltaY / (double) UPDATE_TIMER_DELAY);

        final int stepsCount = (int) Math.floor(Math.max(
                (stepX != zero) ? deltaX / (double) stepX : zero,
                (stepY != zero) ? deltaY / (double) stepY : zero
                )
        );

        final int inaccurateXEnd = startX + (stepX*stepsCount);
        final int inaccurateYEnd = startY + (stepY*stepsCount);


        AtomicInteger counter = new AtomicInteger();


        Timer endTimer = new Timer(ANIMATION_CHANGE_TIME, (e) -> {
            DragonMapModel.this.setIcon(Icons.PREPARING.getIcon());
            Timer innerTimer = new Timer(ANIMATION_CHANGE_TIME, (ee) -> {
                DragonMapModel.this.setIcon(Icons.IDLE.getIcon());
            });
            innerTimer.setRepeats(false);
            innerTimer.start();
        });
        endTimer.setRepeats(false);

        Timer animationTimer = new Timer(ANIMATION_CHANGE_TIME / UPDATE_TIMER_DELAY, (e) -> {
            int index = counter.get();
            DragonMapModel.this.setIcon(FLYING_ANIMATION_ICONS[index % FLYING_ANIMATION_ICONS.length].getIcon());
        });


        Timer movingTimer = new Timer(UPDATE_TIMER_DELAY, (e) -> {
            if (counter.getAndIncrement() >= stepsCount) {
                animationTimer.stop();
                endTimer.start();
                ((Timer) e.getSource()).stop();
            }
            int x = getX();
            int y = getY();

            int nextX = x + stepX;
            int nextY = y + stepY;

            boolean isXEndCord = (x == inaccurateXEnd || y == newX);
            boolean isYEndCord = (y == inaccurateYEnd || y == newY);

            if (isXEndCord && isYEndCord) {
                DragonMapModel.this.setLocation(newX, newY);
                animationTimer.stop();
                endTimer.start();
                ((Timer) e.getSource()).stop();
            } else if (isXEndCord){
                DragonMapModel.this.setLocation(x, nextY);
            } else if (isYEndCord) {
                DragonMapModel.this.setLocation(nextX, y);
            } else {
                DragonMapModel.this.setLocation(nextX, nextY);
            }
        });

        Timer preparingTimer = new Timer(ANIMATION_CHANGE_TIME, (e) -> {
            DragonMapModel.this.setIcon(Icons.PREPARING.getIcon());
            animationTimer.start();
            movingTimer.start();
        });

        preparingTimer.setRepeats(false);

        preparingTimer.start();
    }

    public void prepareForRemoving(Container container) {
        this.setEnabled(false);
        this.setIcon(Icons.PREPARING.getIcon());
        this.repaint();
        Timer timer = new Timer(ANIMATION_CHANGE_TIME, (e) -> {
            DragonMapModel.this.setIcon(Icons.LAYING.getIcon());
            this.repaint();
            Timer innerTimer = new Timer(ANIMATION_CHANGE_TIME, (ee) ->  {
                this.setVisible(false);
                container.remove(DragonMapModel.this);
            });
            innerTimer.setRepeats(false);
            innerTimer.start();
        });
        timer.setRepeats(false);
        timer.start();
    }



    public void defaultPaint() {
        Coordinates coordinates = dragon.getCoordinates();
        setLocation(coordinates.getX(), coordinates.getY());
        setVisible(true);
        Timer timer = new Timer(ANIMATION_CHANGE_TIME, (e) -> {
            setIcon(Icons.PREPARING.getIcon());
            Timer newTimer = new Timer(ANIMATION_CHANGE_TIME, (ee) -> {
                setIcon(Icons.IDLE.getIcon());
                DragonMapModel.this.setEnabled(true);
            });
            newTimer.setRepeats(false);
            newTimer.start();
        });
        timer.setRepeats(false);
        timer.start();
    }




    public enum Icons {

        IDLE("/images/dragon-idle.png"),
        FLYING_FIRST_STAGE("/images/dragon-first-stage-flying.png"),
        FLYING_SECOND_STAGE("/images/dragon-second-stage-flying.png"),
        FLYING_THIRD_STAGE("/images/dragon-third-stage-flying.png"),
        LAYING("/images/dragon-laying.png"),
        PREPARING("/images/dragon-preparing.png");

        Icons(String pathToResource) {
            this.icon = new ImageIcon(getClass().getResource(pathToResource));
        }
        private final ImageIcon icon;

        public ImageIcon getIcon() {
            return icon;
        }
    }
}