package org.worldsproject.game.runningman;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by atrus on 4/17/14.
 */
public class Man {
    //Here are our 4 images that the player is composed of.
    TextureRegion standing;
    TextureRegion running_1;
    TextureRegion running_2;
    TextureRegion running_3;

    //How often the frame changes
    private float next_change = .1f;
    private float current = 0f;
    private TextureRegion current_frame;

    //Status of the character
    private boolean jumping_left = false;
    private boolean jumping_right = false;

    //Some starting values for the character.
    private float x = 32;
    private float y = 64;

    //Some attributes to handle jumping_left.
    private boolean going_up = false;
    private float max_y = 200;

    //Our collision rectangle for the character.
    private Rectangle collision = new Rectangle(x+16, y, 32, 64);

    public Man(TextureRegion man, TextureRegion r1, TextureRegion r2, TextureRegion r3) {
        standing = man;
        running_1 = r1;
        running_2 = r2;
        running_3 = r3;

        current_frame = running_1;
    }

    public TextureRegion getStanding() {
        return standing;
    }

    public TextureRegion getRunning(float delta) {
        current += delta;

        if(current > next_change) {
            nextFrame();
            current = 0;
        }

        if(jumping_left) {
            if(going_up) {
                y += 5;

                if(y > max_y) {
                    going_up = false;
                }
            } else {
                y -= 2;

                if(y < 64) {
                    y = 64;
                    jumping_left = false;
                    next_change = 0.1f;
                }
            }
        }

        if(jumping_right) {
            if(going_up) {
                y += 1.5;

                if(y > max_y-20) {
                    going_up = false;
                }
            } else {
                y -= 1.5;

                if(y < 64) {
                    y = 64;
                    jumping_right = false;
                    next_change = 0.1f;
                }
            }
        }

        return current_frame;
    }

    private void nextFrame() {
        if(current_frame == running_1) {
            current_frame = running_2;
        } else if (current_frame == running_2) {
            current_frame = running_3;
        } else {
            current_frame = running_1;
        }
    }

    public void jump(boolean left) {
        if(left) {
            if (!jumping_left) {
                jumping_left = true;
                going_up = true;
            }
        } else {
            if(!jumping_right) {
                jumping_right = true;
                going_up = true;
            }
        }

        next_change = 0.3f;
    }

    public Rectangle getBounds() {
        collision.setY(y);
        return collision;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
